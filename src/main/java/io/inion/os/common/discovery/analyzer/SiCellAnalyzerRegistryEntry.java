package io.inion.os.common.discovery.analyzer;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;

@CellType(
    objectClass = SiCellAnalyzerRegistryEntry.SiCellAnalyzerRegistryEntryObject.class,
    type = SiCellAnalyzerRegistryEntry.CELL_TYPE,
    uuid = SiCellAnalyzerRegistryEntry.CELL_UUID
)
public interface SiCellAnalyzerRegistryEntry extends
    SiCell<SiCellAnalyzerRegistryEntry, SiCellAnalyzer> {

  String CELL_TYPE = "cell-analyzer-registry-entry";

  String CELL_UUID = "D93364D5-C2AC-4DC4-B4CA-4BA22FDD59B9";

  class SiCellAnalyzerRegistryEntryObject extends
      SiCellObject<SiCellAnalyzerRegistryEntry, SiCellAnalyzer> implements
      SiCellAnalyzerRegistryEntry {
  }
}
