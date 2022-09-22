package io.inion.os.common.types;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.types.SiInteger.SiIntegerObject;

@CellType(
    objectClass = SiIntegerObject.class,
    type = "integer",
    uuid = "5F30064A-2628-40EF-BFC2-F220A4754D49"
)
public interface SiInteger extends SiCell<SiInteger, Integer> {

  SiInteger setCellValue(int value);

  class SiIntegerObject extends SiCellObject<SiInteger, Integer> implements SiInteger {

    @Override
    public SiInteger setCellValue(int value) {
      this.cellValue = value;
      return getSelf();
    }

    @Override
    public SiInteger setCellValueAsString(String value) {
      this.cellValue = Integer.parseInt(value);
      return getSelf();
    }

    @Override
    public String toString() {
      return hasCellValue() ? String.valueOf(getCellValue()) : "0";
    }
  }
}
