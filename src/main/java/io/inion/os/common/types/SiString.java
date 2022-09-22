package io.inion.os.common.types;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.types.SiString.SiStringObject;

@CellType(
    objectClass = SiStringObject.class,
    type = "string",
    uuid = "21EEC5E5-9A52-497A-82AA-0C81314E6B44"
)
public interface SiString extends SiCell<SiString, String> {

  class SiStringObject extends SiCellObject<SiString, String> implements SiString {

    @Override
    public String toString() {
      return hasCellValue() ? getCellValue() : "";
    }
  }
}
