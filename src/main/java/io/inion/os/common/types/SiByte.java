package io.inion.os.common.types;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.types.SiByte.SiByteObject;

@CellType(
    objectClass = SiByteObject.class,
    type = "Byte",
    uuid = "28BE8C34-D9CA-4ED6-99ED-E53E07E9A180"
)
public interface SiByte extends SiCell<SiByte, Byte> {

  SiByte setCellValue(byte value);

  class SiByteObject extends SiCellObject<SiByte, Byte> implements SiByte {

    @Override
    public SiByte setCellValue(byte value) {
      this.cellValue = value;
      return getSelf();
    }
  }
}
