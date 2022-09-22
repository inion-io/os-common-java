package io.inion.os.common;

import io.inion.os.common.annotation.CellType;

@CellType(
    objectClass = SiCellReference.SiCellReferenceObject.class,
    type = SiCellReference.CELL_TYPE,
    uuid = SiCellReference.CELL_UUID
)
public interface SiCellReference extends SiCell<SiCellReference, SiCell<?, ?>> {

  String CELL_TYPE = "cell-reference";

  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  class SiCellReferenceObject extends SiCellObject<SiCellReference, SiCell<?, ?>> implements
      SiCellReference {
  }
}
