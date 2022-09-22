package io.inion.os.common.types;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.types.SiBoolean.SiBooleanObject;

@CellType(
    objectClass = SiBooleanObject.class,
    type = "boolean",
    uuid = "9FF8DA22-9C7E-46DB-B782-1D04FC5BFD26"
)
public interface SiBoolean extends SiCell<SiBoolean, Boolean> {

  SiBoolean setCellValue(boolean value);

  class SiBooleanObject extends SiCellObject<SiBoolean, Boolean> implements SiBoolean {

    @Override
    public SiBoolean setCellValue(boolean value) {
      this.cellValue = value;
      return getSelf();
    }

    @Override
    public SiBoolean setCellValueAsString(String value) {
      try {
        this.cellValue = Boolean.valueOf(value);
      } catch (Exception e) {
        this.cellValue = false;
      }

      return getSelf();
    }
  }
}
