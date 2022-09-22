package io.inion.os.common.discovery.registry;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.discovery.registry.SiCellRegistryReferenceInjectionEntry.SiCellRegistryReferenceEntryObject;

@CellType(
    objectClass = SiCellRegistryReferenceEntryObject.class,
    type = SiCellRegistryReferenceInjectionEntry.CELL_TYPE,
    uuid = SiCellRegistryReferenceInjectionEntry.CELL_UUID
)
public interface SiCellRegistryReferenceInjectionEntry extends
    SiCell<SiCellRegistryReferenceInjectionEntry, SiCell<?,?>> {

  String CELL_TYPE = "cell-registry-reference-injection-entry";

  String CELL_UUID = "0AE4F7B2-F8E2-4F14-942A-99D49AE48592";

  class SiCellRegistryReferenceEntryObject extends
      SiCellObject<SiCellRegistryReferenceInjectionEntry, SiCell<?,?>> implements
      SiCellRegistryReferenceInjectionEntry {
  }
}
