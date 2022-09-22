package io.inion.os.common.types;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.types.SiDouble.SiDoubleObject;

@CellType(
    objectClass = SiDoubleObject.class,
    type = "double",
    uuid = "FAD30A62-9983-41AF-A462-932807D90DD4"
)
public interface SiDouble extends SiCell<SiDouble, Double> {

  SiDouble setCellValue(double value);

  class SiDoubleObject extends SiCellObject<SiDouble, Double> implements SiDouble {

    @Override
    public SiDouble setCellValue(double value) {
      this.cellValue = value;
      return getSelf();
    }
  }
}
