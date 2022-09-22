package io.inion.os.common.discovery.registry.skills;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.Cell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.discovery.registry.SiCellRegistryEntry;
import io.inion.os.common.discovery.registry.SiCellRegistryEntryClass;
import io.inion.os.common.discovery.registry.SiCellRegistryEntryTypeName;
import io.inion.os.common.discovery.registry.SiCellRegistryEntryTypeUUID;
import io.inion.os.common.exception.CellRunException;
import io.inion.os.common.types.SiList;
import java.util.UUID;

@CellType(
    objectClass = SkProcessClasses.SkProcessClassesObject.class,
    type = SkProcessClasses.CELL_TYPE,
    uuid = SkProcessClasses.CELL_UUID
)
public interface SkProcessClasses extends SiCell<SkProcessClasses, Void> {

  String CELL_TYPE = "cell-registry-process-classes-skill";

  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  SkProcessClasses setClasses(SiList<Class<?>> classes);

  class SkProcessClassesObject extends SiCellObject<SkProcessClasses, Void> implements
      SkProcessClasses {

    @Cell(isTransient = true)
    private SiList<Class<?>> classes;


    @Override
    public SkProcessClasses run() throws CellRunException {
      checkRunValuesForNull(classes);

      classes.getCellValue().forEach(cellClass -> {
        String type = cellClass.getAnnotation(CellType.class).type();
        String uuid = cellClass.getAnnotation(CellType.class).uuid();
        boolean component = cellClass.getAnnotation(CellType.class).component();

        if (component) {
          buildCell((Class<SiCell<?, ?>>) cellClass, root().getSelf(), type);
        }

        SiCellRegistryEntry entry = createCell(
            SiCellRegistryEntry.class, getController(), type);

        entry.getSubCell(SiCellRegistryEntryTypeName.class).setCellValue(type);
        entry.getSubCell(SiCellRegistryEntryTypeUUID.class).setCellValue(UUID.fromString(uuid));
        entry.getSubCell(SiCellRegistryEntryClass.class).setCellValue((Class<SiCell<?, ?>>) cellClass);
      });

      return getSelf();
    }

    @Override
    public SkProcessClasses setClasses(SiList<Class<?>> classes) {
      return swapSubCell("classes", classes);
    }
  }
}
