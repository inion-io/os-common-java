package io.inion.os.common.discovery.injection.skills;

import io.inion.os.common.SiCell;
import io.inion.os.common.SiCellReference;
import io.inion.os.common.annotation.CellReference;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.discovery.injection.SiCellInjector;
import io.inion.os.common.discovery.injection.SiCellInjectorLazyCellReference;
import io.inion.os.common.discovery.injection.SiCellInjectorLostCellReference;
import io.inion.os.common.exception.CellRunException;
import io.inion.os.common.utils.CellHelper;
import java.util.UUID;

@CellType(
    objectClass = SkInjectCellReferences.SkInjectCellReferencesObject.class,
    type = SkInjectCellReferences.CELL_TYPE,
    uuid = SkInjectCellReferences.CELL_UUID
)
public interface SkInjectCellReferences extends SiCell<SkInjectCellReferences, Void> {

  String CELL_TYPE = "cell-injector-inject-cell-references-skill";

  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  SkInjectCellReferences setCell(SiCell<?, ?> cell);

  class SkInjectCellReferencesObject extends SiCellObject<SkInjectCellReferences, Void> implements
      SkInjectCellReferences {

    private SiCell<?, ?> cell;
    @Override
    @SuppressWarnings("unchecked")
    public SkInjectCellReferences run() throws CellRunException {
      checkRunValuesForNull(cell);

      CellHelper.getFields(cell.getClass(), CellReference.class, null).forEach(field -> {
        if (!((SiCellInjector) getController()).isFinished().getCellValue()) {
          SiCellInjectorLazyCellReference entry = getController().createCell(
              SiCellInjectorLazyCellReference.class);
          entry.setCellValue(cell);

          return;
        }

        Class<SiCell<?, ?>> cellClass = (Class<SiCell<?, ?>>) field.getType();

        String cellUUID = field.getAnnotation(CellReference.class).uuid();
        String cellType = field.getAnnotation(CellReference.class).type();

        SiCell<?, ?> injectCell;

        if (cellType != null && !cellType.isEmpty()) {
          injectCell = findSubCell(root().getSelf(), cellType);
        } else if (cellUUID != null && !cellUUID.isEmpty()) {
          injectCell = findSubCell(root().getSelf(), UUID.fromString(cellUUID));
        } else {
          injectCell = getSubCell(cellClass, root().getSelf());
        }

        if (injectCell != null) {
          SiCellReference cellReference = createCell(SiCellReference.class, cell);
          cellReference.setCellValue(injectCell);

          try {
            field.setAccessible(true);
            field.set(cell, injectCell);
            cellReference.setControllerField(field);
          } catch (IllegalAccessException illegalAccessException) {
            log().info("TODO: Exception Handling!", illegalAccessException);
          }
        } else if (((SiCellInjector) getController()).isFinished().getCellValue()) {
          SiCellInjectorLostCellReference entry = getController().createCell(
              SiCellInjectorLostCellReference.class);
          entry.setCellValue(cell);
        }
      });

      return getSelf();
    }

    @Override
    public SkInjectCellReferences setCell(SiCell<?, ?> cell) {
      this.cell = cell;
      return getSelf();
    }
  }
}
