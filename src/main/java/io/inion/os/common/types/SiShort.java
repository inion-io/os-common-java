package io.inion.os.common.types;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.types.SiShort.SiShortObject;

@CellType(
    objectClass = SiShortObject.class,
    type = "short",
    uuid = "0E4D089C-2505-428F-A9BD-3CADE16CB2F7"
)
public interface SiShort extends SiCell<SiShort, Short> {

  SiShort setCellValue(short value);

  class SiShortObject extends SiCellObject<SiShort, Short> implements SiShort {

    @Override
    public SiShort setCellValue(short value) {
      this.cellValue = value;
      return getSelf();
    }
  }
}
