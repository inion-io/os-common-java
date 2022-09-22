package io.inion.os.common.discovery.registry;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.types.SiClass;
import io.inion.os.common.discovery.registry.SiCellRegistryEntryClass.SiCellRegistryEntryClassObject;

@CellType(
    objectClass = SiCellRegistryEntryClassObject.class,
    type = SiCellRegistryEntryClass.CELL_TYPE,
    uuid = SiCellRegistryEntryClass.CELL_UUID
)
public interface SiCellRegistryEntryClass extends SiClass<SiCell<?, ?>> {

  String CELL_TYPE = "cell-registry-entry-class";

  String CELL_UUID = "0AE4F7B2-F8E2-4F14-942A-99D49AE48592";

  class SiCellRegistryEntryClassObject extends SiClassObject<SiCell<?, ?>> implements
      SiCellRegistryEntryClass {

  }
}
