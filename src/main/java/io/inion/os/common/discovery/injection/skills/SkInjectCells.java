package io.inion.os.common.discovery.injection.skills;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.Cell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.exception.CellRunException;
import io.inion.os.common.types.SiProperty;
import io.inion.os.common.types.SiString;
import io.inion.os.common.utils.CellHelper;
import io.inion.os.common.utils.comparator.CellFieldOrderComparator;
import java.lang.reflect.Field;
import java.util.List;

@CellType(
    objectClass = SkInjectCells.SkInjectCellsObject.class,
    type = SkInjectCells.CELL_TYPE,
    uuid = SkInjectCells.CELL_UUID
)
public interface SkInjectCells extends SiCell<SkInjectCells, Void> {

  String CELL_TYPE = "cell-injector-inject-cells-skill";

  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  SkInjectCells setCell(SiCell<?, ?> cell);

  class SkInjectCellsObject extends SiCellObject<SkInjectCells, Void> implements
      SkInjectCells {

    private SiCell<?, ?> cell;

    @Override
    @SuppressWarnings("unchecked")
    public SkInjectCells run() throws CellRunException {
      checkRunValuesForNull(cell);

      List<Field> fields = CellHelper.getFields(cell.getClass(), Cell.class, new CellFieldOrderComparator());

      fields.forEach(field -> {
        Class<SiCell<?, ?>> cellClass = (Class<SiCell<?, ?>>) field.getType();

        String value = field.getAnnotation(Cell.class).value();
        String name = field.getAnnotation(Cell.class).name();
        String type = field.getAnnotation(Cell.class).type();
        String prop = field.getAnnotation(Cell.class).prop();
        boolean isTransient = field.getAnnotation(Cell.class).isTransient();

        if (isTransient) {
          return;
        }

        if (name == null || name.isEmpty()) {
          name = field.getName();
        }

        SiCell<?, ?> injectCell;

        if (type != null && !type.isEmpty()) {
          injectCell = createCell(type, cell, name);
        } else {
          injectCell = createCell(cellClass, cell, name);
        }

        if (injectCell != null) {
          if (value != null && !value.isEmpty()) {
            injectCell.setCellValueAsString(value);
          }
          if (prop != null && !prop.isEmpty()) {
            SiProperty property = root().getProperty(createTransientCell(SiString.class, prop));

            if (property.hasCellValue()) {
              // TODO: Dieser Fall von doppelten getCellValue().getCellValue() muss noch behoben werden!!!
              injectCell.setCellValueAsString(property.getCellValue().getCellValue());
            }
          }

          try {
            field.setAccessible(true);
            field.set(cell, injectCell);
            injectCell.setControllerField(field);
          } catch (IllegalAccessException illegalAccessException) {
            log().info("TODO: Exception Handling!", illegalAccessException);
          }
        }
      });

      return getSelf();
    }

    @Override
    public SkInjectCells setCell(SiCell<?, ?> cell) {
      this.cell = cell;
      return getSelf();
    }
  }
}
