package io.inion.os.common.types;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import java.util.ArrayList;
import java.util.List;

@CellType(
    objectClass = SiList.SiListObject.class,
    type = "list",
    uuid = "4FAE8365-9642-48FF-9BE1-8AE16CF5D771"
)
public interface SiList<T> extends SiCell<SiList<T>, List<T>> {

  void add(T value);

  void add(SiList<T> list);

  void remove(T value);

  class SiListObject<T> extends SiCellObject<SiList<T>, List<T>> implements
      SiList<T> {

    @Override
    public void add(T value) {
      this.cellValue.add(value);
    }

    @Override
    public void add(SiList<T> list) {
      if (!list.hasCellValue()) {
        return;
      }

      this.cellValue.addAll(list.getCellValue());
    }

    @Override
    public void remove(T value) {
      this.cellValue.remove(value);
    }

    @Override
    public void afterCreate() {
      this.cellValue = new ArrayList<>();
    }

    @Override
    public SiList<T> setCellValueAsString(String value) {

      try {
        JsonArray valueJsonArray = JsonParser.parseString(value).getAsJsonArray();

        for (int a = 0; a < valueJsonArray.size(); a++) {
          JsonObject entryJsonObject = valueJsonArray.get(a).getAsJsonObject();
          String cellType = entryJsonObject.get(SiCell.PROPERTY_CELL_TYPE).getAsString();

          SiCell<?, ?> cell = createTransientCell(cellType);
          cell.restore(entryJsonObject);

          this.cellValue.add((T) cell);
        }

      } catch (Exception e) {
        log().error(e);
      }

      return getSelf();
    }

    @Override
    public String getCellValueAsString() {

      JsonArray valueJsonArray = new JsonArray();

      if (hasCellValue()) {
        for (T entry : cellValue) {
          if (entry instanceof SiCell) {
            valueJsonArray.add(((SiCell<?, ?>) entry).toJsonObject());
          } else {
            // TODO: Eintrag der Liste darf nur SiCell entsprechen!!!
          }
        }
      }

      return new Gson().toJson(valueJsonArray);
    }

    @Override
    public boolean hasCellValue() {
      return this.cellValue != null && this.cellValue.size() > 0;
    }
  }
}
