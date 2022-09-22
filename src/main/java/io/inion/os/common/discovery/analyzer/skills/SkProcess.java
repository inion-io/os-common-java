package io.inion.os.common.discovery.analyzer.skills;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.discovery.analyzer.SiCellAnalyzerRegistry;
import io.inion.os.common.discovery.analyzer.SiCellAnalyzerRegistryEntry;
import io.inion.os.common.discovery.analyzer.SiCellAnalyzerRegistryLazyCell;
import io.inion.os.common.exception.CellRunException;

@CellType(
    objectClass = SkProcess.SkProcessObject.class,
    type = SkProcess.CELL_TYPE,
    uuid = SkProcess.CELL_UUID
)
public interface SkProcess extends SiCell<SkProcess, SiCell<?, ?>> {

  String CELL_TYPE = "cell-analyzer-registry-process-skill";

  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  class SkProcessObject extends SiCellObject<SkProcess, SiCell<?, ?>> implements
      SkProcess {

    @Override
    public SkProcess run() throws CellRunException {
      checkRunValuesForNull(getCellValue());

      if (!((SiCellAnalyzerRegistry) getController()).isFinished().getCellValue()) {
        SiCellAnalyzerRegistryLazyCell entry = getController().buildCell(
            SiCellAnalyzerRegistryLazyCell.class);
        entry.setCellValue(getCellValue());
      } else {

        getController().getSubCells(SiCellAnalyzerRegistryEntry.class).forEach(analyzer -> {
          try {
            analyzer.getCellValue().setCell(getCellValue()).run();
          } catch (CellRunException e) {
            log().error("TODO: Exception Handling", e);
          }
        });
      }

      return getSelf();
    }
  }
}
