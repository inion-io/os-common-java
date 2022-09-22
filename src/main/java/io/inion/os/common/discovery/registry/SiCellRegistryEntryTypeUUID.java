package io.inion.os.common.discovery.registry;

import io.inion.os.common.annotation.CellType;
import io.inion.os.common.types.SiUUID;
import io.inion.os.common.discovery.registry.SiCellRegistryEntryTypeUUID.SiCellRegistryEntryTypeUUIDObject;

@CellType(
    objectClass = SiCellRegistryEntryTypeUUIDObject.class,
    type = SiCellRegistryEntryTypeUUID.CELL_TYPE,
    uuid = SiCellRegistryEntryTypeUUID.CELL_UUID
)
public interface SiCellRegistryEntryTypeUUID extends SiUUID {

  String CELL_TYPE = "cell-registry-entry-type-uuid";

  String CELL_UUID = "0AE4F7B2-F8E2-4F14-942A-99D49AE48592";

  class SiCellRegistryEntryTypeUUIDObject extends SiUUIDObject implements
      SiCellRegistryEntryTypeUUID {

  }
}
