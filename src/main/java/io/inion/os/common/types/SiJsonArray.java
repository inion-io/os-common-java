package io.inion.os.common.types;

import com.google.gson.JsonArray;
import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;

@CellType(
    objectClass = SiJsonArray.SiJsonArrayObject.class,
    type = "json-array",
    uuid = "4FAE8365-9642-48FF-9BE1-8AE16CF5D771"
)
public interface SiJsonArray extends SiCell<SiJsonArray, JsonArray> {

  class SiJsonArrayObject extends SiCellObject<SiJsonArray, JsonArray> implements SiJsonArray {

    @Override
    public String toString() {
      return cellValue != null ? cellValue.toString() : "[]";
    }
  }
}
