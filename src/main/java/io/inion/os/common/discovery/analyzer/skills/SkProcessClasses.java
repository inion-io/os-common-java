package io.inion.os.common.discovery.analyzer.skills;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.discovery.analyzer.SiCellAnalyzer;
import io.inion.os.common.discovery.analyzer.SiCellAnalyzerRegistryEntry;
import io.inion.os.common.exception.CellRunException;
import io.inion.os.common.types.SiList;

@CellType(
    objectClass = SkProcessClasses.SkProcessClassesObject.class,
    type = SkProcessClasses.CELL_TYPE,
    uuid = SkProcessClasses.CELL_UUID
)
public interface SkProcessClasses extends SiCell<SkProcessClasses, SiList<Class<?>>> {

  String CELL_TYPE = "cell-analyzer-registry-process-classes-skill";

  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  class SkProcessClassesObject extends SiCellObject<SkProcessClasses, SiList<Class<?>>> implements
      SkProcessClasses {

    @Override
    public SkProcessClasses run() throws CellRunException {
      checkRunValuesForNull(getCellValue());

      getCellValue().getCellValue().forEach(cellClass -> {
        if (SiCellAnalyzer.class.isAssignableFrom(cellClass)) {
          String cellType = cellClass.getAnnotation(CellType.class).type();

          SiCellAnalyzer analyzer = (SiCellAnalyzer) buildCell((Class<SiCell<?, ?>>) cellClass,
              root().getSelf(), cellType);

          SiCellAnalyzerRegistryEntry analyzerRegistryEntry = getController().buildCell(
              SiCellAnalyzerRegistryEntry.class);

          analyzerRegistryEntry.setCellValue(analyzer);
        }
      });

      return getSelf();
    }
  }
}
