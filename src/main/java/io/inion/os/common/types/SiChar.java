package io.inion.os.common.types;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.types.SiChar.SiCharObject;

@CellType(
    objectClass = SiCharObject.class,
    type = "char",
    uuid = "BB4F7F49-63DC-4AFD-B841-0FDCD97C5653"
)
public interface SiChar extends SiCell<SiChar, Character> {

  SiChar setCellValue(char value);

  class SiCharObject extends SiCellObject<SiChar, Character> implements SiChar {

    @Override
    public SiChar setCellValue(char value) {
      this.cellValue = value;
      return getSelf();
    }
  }
}
