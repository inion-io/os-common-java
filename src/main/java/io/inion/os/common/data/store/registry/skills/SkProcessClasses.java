package io.inion.os.common.data.store.registry.skills;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.data.store.SiDataStore;
import io.inion.os.common.data.store.SiDataStoreProvider;
import io.inion.os.common.data.store.registry.SiDataStoreProviderRegistryEntry;
import io.inion.os.common.exception.CellRunException;
import io.inion.os.common.types.SiList;

@CellType(
    objectClass = SkProcessClasses.SkProcessClassesObject.class,
    type = SkProcessClasses.CELL_TYPE,
    uuid = SkProcessClasses.CELL_UUID
)
public interface SkProcessClasses extends SiCell<SkProcessClasses, SiList<Class<?>>> {

  String CELL_TYPE = "data-store-provider-registry-process-classes-skill";

  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  class SkProcessClassesObject extends SiCellObject<SkProcessClasses, SiList<Class<?>>> implements
      SkProcessClasses {

    @Override
    public SkProcessClasses run() throws CellRunException {
      checkRunValuesForNull(getCellValue());

      // TODO: doppelte getCellValue() Verwendung muss überdacht und smarter gemacht werden!

      getCellValue().getCellValue().forEach(cellClass -> {
        if (SiDataStoreProvider.class.isAssignableFrom(cellClass)) {
          String type = cellClass.getAnnotation(CellType.class).type();

          SiDataStoreProvider<?> dataStoreProvider = (SiDataStoreProvider<?>) buildCell(
              (Class<SiCell<?, ?>>) cellClass, root().getSelf(), type);

          SiDataStoreProviderRegistryEntry entry = getController().buildCell(
              SiDataStoreProviderRegistryEntry.class);

          entry.setCellValue(dataStoreProvider);
        }
      });

      return getSelf();
    }
  }
}
