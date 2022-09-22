package io.inion.os.common.discovery.injection;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.discovery.injection.SiCellInjectorLazyCellReference.SiCellInjectorLazyCellReferenceObject;

@CellType(
    objectClass = SiCellInjectorLazyCellReferenceObject.class,
    type = SiCellInjectorLazyCellReference.CELL_TYPE,
    uuid = SiCellInjectorLazyCellReference.CELL_UUID
)
public interface SiCellInjectorLazyCellReference extends
    SiCell<SiCellInjectorLazyCellReference, SiCell<?, ?>> {

  String CELL_TYPE = "cell-injector-lazy-cell-reference";

  String CELL_UUID = "0AE4F7B2-F8E2-4F14-942A-99D49AE48592";

  class SiCellInjectorLazyCellReferenceObject extends
      SiCellObject<SiCellInjectorLazyCellReference, SiCell<?, ?>> implements
      SiCellInjectorLazyCellReference {

  }
}
