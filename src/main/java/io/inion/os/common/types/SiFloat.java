package io.inion.os.common.types;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.types.SiFloat.SiFloatObject;

@CellType(
    objectClass = SiFloatObject.class,
    type = "float",
    uuid = "72F21CFC-6B34-4ACE-B0DB-4AB388FEC627"
)
public interface SiFloat extends SiCell<SiFloat, Float> {

  SiFloat setCellValue(float value);

  class SiFloatObject extends SiCellObject<SiFloat, Float> implements SiFloat {

    @Override
    public SiFloat setCellValue(float value) {
      this.cellValue = value;
      return getSelf();
    }
  }
}
