package io.inion.os.common.types;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import java.util.HashMap;
import java.util.Map.Entry;

@CellType(
    objectClass = SiHashMap.SiHashMapObject.class,
    type = "hashmap",
    uuid = "4FAE8365-9642-48FF-9BE1-8AE16CF5D771"
)
public interface SiHashMap<K extends SiCell<?, ?>, V extends SiCell<?, ?>> extends
    SiCell<SiHashMap<K, V>, HashMap<K, V>> {

  class SiHashMapObject<K extends SiCell<?, ?>, V extends SiCell<?, ?>> extends
      SiCellObject<SiHashMap<K, V>, HashMap<K, V>> implements SiHashMap<K, V> {

    @Override
    public void afterCreate() {
      this.cellValue = new HashMap<>();
    }

    @Override
    public String getCellValueAsString() {

      JsonArray valueJsonArray = new JsonArray();

      if (hasCellValue()) {
        for (Entry<? extends SiCell<?, ?>, ? extends SiCell<?, ?>> entry : cellValue.entrySet()) {
          JsonObject entryJsonObject = new JsonObject();
          entryJsonObject.add("key", entry.getKey().toJsonObject());
          entryJsonObject.add("value", entry.getValue().toJsonObject());
          valueJsonArray.add(entryJsonObject);
        }
      }

      return new Gson().toJson(valueJsonArray);
    }

    @Override
    public SiHashMap<K, V> setCellValueAsString(String value) {
      try {
        JsonArray valueJsonArray = JsonParser.parseString(value).getAsJsonArray();

        for (int a = 0; a < valueJsonArray.size(); a++) {
          JsonObject entryJsonObject = valueJsonArray.get(a).getAsJsonObject();
          JsonObject entryKeyJsonObject = entryJsonObject.getAsJsonObject("key");
          JsonObject entryValueJsonObject = entryJsonObject.getAsJsonObject("value");

          String entryKeyCellTypeName = entryKeyJsonObject.get("cellTypeName").getAsString();
          String entryValueCellTypeName = entryValueJsonObject.get("cellTypeName").getAsString();

          SiCell<?, ?> keyCell = createTransientCell(entryKeyCellTypeName);
          SiCell<?, ?> valueCell = createTransientCell(entryValueCellTypeName);

          keyCell.restore(entryKeyJsonObject);
          valueCell.restore(entryValueJsonObject);

          this.cellValue.put((K) keyCell, (V) valueCell);
        }

      } catch (JsonSyntaxException syntaxException) {
        log().error("TODO: Exception Handling", syntaxException);
      }

      return getSelf();
    }
  }
}
