package io.inion.os.common.discovery.injection.skills;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.discovery.injection.SiCellInjector;
import io.inion.os.common.discovery.injection.SiCellInjectorLazyCellReference;
import io.inion.os.common.discovery.injection.skills.SkFinish.SkFinishObject;
import java.util.ArrayList;
import java.util.List;

@CellType(
    objectClass = SkFinishObject.class,
    type = SkFinish.CELL_TYPE,
    uuid = SkFinish.CELL_UUID
)
public interface SkFinish extends SiCell<SkFinish, Void> {

  String CELL_TYPE = "cell-injector-finish-skill";

  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  class SkFinishObject extends SiCellObject<SkFinish, Void> implements SkFinish {


    @Override
    public SkFinish run() {
      getController().getSubCells(SiCellInjectorLazyCellReference.class).forEach(subCell -> {
        ((SiCellInjector) getController()).injectCellReferences(subCell.getCellValue());
      });

      List<Class<?>> initializedCells = new ArrayList<>();

      getController().getSubCells(SiCellInjectorLazyCellReference.class).forEach(subCell -> {

        if (initializedCells.contains(((SiCell) subCell.getCellValue()).getCellClass())) {
          return;
        }

        subCell.getCellValue().afterCellsSet();

        initializedCells.add(((SiCell) subCell.getCellValue()).getCellClass());
      });

      initializedCells.clear();

      try {
        getController().destroySubCells(SiCellInjectorLazyCellReference.class);
      } catch (Exception e) {
        log().error(e);
      }

      return getSelf();
    }
  }
}
