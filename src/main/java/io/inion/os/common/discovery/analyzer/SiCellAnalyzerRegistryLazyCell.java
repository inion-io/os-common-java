package io.inion.os.common.discovery.analyzer;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;

@CellType(
    objectClass = SiCellAnalyzerRegistryLazyCell.SiCellAnalyzerRegistryLazyCellObject.class,
    type = SiCellAnalyzerRegistryLazyCell.CELL_TYPE,
    uuid = SiCellAnalyzerRegistryLazyCell.CELL_UUID
)
public interface SiCellAnalyzerRegistryLazyCell extends
    SiCell<SiCellAnalyzerRegistryLazyCell, SiCell<?, ?>> {

  String CELL_TYPE = "cell-analyzer-registry-lazy-cell";

  String CELL_UUID = "D93364D5-C2AC-4DC4-B4CA-4BA22FDD59B9";

  class SiCellAnalyzerRegistryLazyCellObject extends
      SiCellObject<SiCellAnalyzerRegistryLazyCell, SiCell<?, ?>> implements
      SiCellAnalyzerRegistryLazyCell {

  }
}
