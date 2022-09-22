package io.inion.os.common.types;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.types.SiLong.SiLongObject;

@CellType(
    objectClass = SiLongObject.class,
    type = "long",
    uuid = "CA2DF407-D423-4689-8884-3773B6FC0D67"
)
public interface SiLong extends SiCell<SiLong, Long> {

  SiLong setCellValue(long value);

  class SiLongObject extends SiCellObject<SiLong, Long> implements SiLong {

    @Override
    public SiLong setCellValue(long value) {
      this.cellValue = value;
      return getSelf();
    }
  }
}
