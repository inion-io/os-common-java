package io.inion.os.common.data.store.registry;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.data.store.SiDataStoreProvider;

@CellType(
    objectClass = SiDataStoreProviderRegistryEntry.SiDataStoreProviderRegistryEntryObject.class,
    type = SiDataStoreProviderRegistryEntry.CELL_TYPE,
    uuid = SiDataStoreProviderRegistryEntry.CELL_UUID
)
public interface SiDataStoreProviderRegistryEntry extends
    SiCell<SiDataStoreProviderRegistryEntry, SiDataStoreProvider<?>> {

  String CELL_TYPE = "data-store-provider-registry-entry";

  String CELL_UUID = "D93364D5-C2AC-4DC4-B4CA-4BA22FDD59B9";

  class SiDataStoreProviderRegistryEntryObject extends
      SiCellObject<SiDataStoreProviderRegistryEntry, SiDataStoreProvider<?>> implements
      SiDataStoreProviderRegistryEntry {
  }
}
