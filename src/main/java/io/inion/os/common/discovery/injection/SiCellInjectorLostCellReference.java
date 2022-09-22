package io.inion.os.common.discovery.injection;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.discovery.injection.SiCellInjectorLostCellReference.SiCellInjectorLostCellReferenceObject;

@CellType(
    objectClass = SiCellInjectorLostCellReferenceObject.class,
    type = SiCellInjectorLostCellReference.CELL_TYPE,
    uuid = SiCellInjectorLostCellReference.CELL_UUID
)
public interface SiCellInjectorLostCellReference extends
    SiCell<SiCellInjectorLostCellReference, SiCell<?, ?>> {

  String CELL_TYPE = "cell-injector-lost-cell-reference";

  String CELL_UUID = "0AE4F7B2-F8E2-4F14-942A-99D49AE48592";

  class SiCellInjectorLostCellReferenceObject extends
      SiCellObject<SiCellInjectorLostCellReference, SiCell<?, ?>> implements
      SiCellInjectorLostCellReference {

  }
}
