package io.inion.os.common.discovery.analyzer.skills;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.discovery.analyzer.SiCellAnalyzerRegistry;
import io.inion.os.common.discovery.analyzer.SiCellAnalyzerRegistryLazyCell;

@CellType(
    objectClass = SkFinish.SkFinishObject.class,
    type = SkFinish.CELL_TYPE,
    uuid = SkFinish.CELL_UUID
)
public interface SkFinish extends SiCell<SkFinish, Void> {

  String CELL_TYPE = "cell-analyzer-registry-finish-skill";

  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  class SkFinishObject extends SiCellObject<SkFinish, Void>
      implements SkFinish {

    @Override
    public SkFinish run() {
      getController().getSubCells(SiCellAnalyzerRegistryLazyCell.class).forEach(subCell -> {
        ((SiCellAnalyzerRegistry) getController()).process(subCell.getCellValue());
      });

      try {
        getController().destroySubCells(SiCellAnalyzerRegistryLazyCell.class);
      } catch (Exception e) {
        log().error(e);
      }

      return getSelf();
    }
  }
}
