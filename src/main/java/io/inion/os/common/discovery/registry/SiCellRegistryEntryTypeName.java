package io.inion.os.common.discovery.registry;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.discovery.registry.SiCellRegistryEntryTypeName.SiCellRegistryEntryTypeNameObject;

@CellType(
    objectClass = SiCellRegistryEntryTypeNameObject.class,
    type = SiCellRegistryEntryTypeName.CELL_TYPE,
    uuid = SiCellRegistryEntryTypeName.CELL_UUID
)
public interface SiCellRegistryEntryTypeName extends SiCell<SiCellRegistryEntryTypeName, String> {

  String CELL_TYPE = "cell-registry-entry-type-name";

  String CELL_UUID = "0AE4F7B2-F8E2-4F14-942A-99D49AE48592";

  class SiCellRegistryEntryTypeNameObject extends
      SiCellObject<SiCellRegistryEntryTypeName, String> implements
      SiCellRegistryEntryTypeName {
  }
}
