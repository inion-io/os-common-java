package io.inion.os.common.types;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;

@CellType(
    objectClass = SiJsonObject.SiJsonObjectObject.class,
    type = "json-object",
    uuid = "4FAE8365-9642-48FF-9BE1-8AE16CF5D771"
)
public interface SiJsonObject extends SiCell<SiJsonObject, JsonObject> {

  class SiJsonObjectObject extends SiCellObject<SiJsonObject, JsonObject> implements SiJsonObject {

    @Override
    public SiJsonObject setCellValueAsObject(Object value) {
      if (value instanceof String) {
        return setCellValueAsString((String) value);
      }
      return super.setCellValueAsObject(value);
    }

    @Override
    public SiJsonObject setCellValueAsString(String value) {
      try {
        setCellValue(JsonParser.parseString(value).getAsJsonObject());
      } catch (JsonSyntaxException ignored) {}

      return getSelf();
    }

    @Override
    public String toString() {
      return cellValue != null ? new Gson().toJson(cellValue) : "{}";
    }
  }
}
