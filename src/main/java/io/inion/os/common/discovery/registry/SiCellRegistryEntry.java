package io.inion.os.common.discovery.registry;

import com.google.gson.JsonObject;
import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.Cell;
import io.inion.os.common.annotation.CellType;

@CellType(
    objectClass = SiCellRegistryEntry.SiCellRegistryEntryObject.class,
    type = SiCellRegistryEntry.CELL_TYPE,
    uuid = SiCellRegistryEntry.CELL_UUID
)
public interface SiCellRegistryEntry extends SiCell<SiCellRegistryEntry, JsonObject> {

  String CELL_TYPE = "cell-registry-entry";

  String CELL_UUID = "0AE4F7B2-F8E2-4F14-942A-99D49AE48592";

  class SiCellRegistryEntryObject extends SiCellObject<SiCellRegistryEntry, JsonObject> implements
      SiCellRegistryEntry {

    @Cell
    private SiCellRegistryEntryClass entryClass;
    @Cell
    private SiCellRegistryEntryTypeName entryTypeName;
    @Cell
    private SiCellRegistryEntryTypeUUID entryTypeUUID;
  }
}
