package io.inion.os.common.discovery.injection.skills;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.Cell;
import io.inion.os.common.annotation.CellReference;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.discovery.injection.SiCellInjector;
import io.inion.os.common.exception.CellRunException;
import io.inion.os.common.utils.CellHelper;

@CellType(
    objectClass = SkCheckAndInject.SkCheckAndInjectObject.class,
    type = SkCheckAndInject.CELL_TYPE,
    uuid = SkCheckAndInject.CELL_UUID
)
public interface SkCheckAndInject extends SiCell<SkCheckAndInject, Void> {

  String CELL_TYPE = "cell-injector-check-and-inject-skill";

  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  SkCheckAndInject setCell(SiCell<?, ?> cell);

  class SkCheckAndInjectObject extends SiCellObject<SkCheckAndInject, Void> implements
      SkCheckAndInject {

    private SiCell<?, ?> cell;

    @Override
    @SuppressWarnings("unchecked")
    public SkCheckAndInject run() throws CellRunException {
      checkRunValuesForNull(cell);

      if (CellHelper.hasField(cell.getClass(), Cell.class)) {
        ((SiCellInjector)getController()).injectCells(cell);
      }

      if (CellHelper.hasField(cell.getClass(), CellReference.class)) {
        ((SiCellInjector)getController()).injectCellReferences(cell);
      }

      return getSelf();
    }

    @Override
    public SkCheckAndInject setCell(SiCell<?, ?> cell) {
      this.cell = cell;
      return getSelf();
    }
  }
}
