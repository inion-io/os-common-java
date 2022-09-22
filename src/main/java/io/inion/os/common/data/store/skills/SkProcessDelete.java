package io.inion.os.common.data.store.skills;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.data.store.SiDataStore;
import io.inion.os.common.data.store.SiDataStoreManager;
import io.inion.os.common.data.store.skills.SkProcessDelete.SkProcessDeleteObject;
import io.inion.os.common.exception.CellRunException;
import io.inion.os.common.types.SiList;

@CellType(
    objectClass = SkProcessDeleteObject.class,
    type = SkProcessDelete.CELL_TYPE,
    uuid = SkProcessDelete.CELL_UUID
)
public interface SkProcessDelete extends SiCell<SkProcessDelete, SiCell<?, ?>> {

  String CELL_TYPE = "data-store-manager-process-destroy-skill";

  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  class SkProcessDeleteObject extends SiCellObject<SkProcessDelete, SiCell<?, ?>> implements
      SkProcessDelete {

    @Override
    public SkProcessDelete run() throws CellRunException {
      checkRunValuesForNull(getCellValue());

      SiList<SiDataStore<?>> stores = ((SiDataStoreManager) getController()).getStores();

      if (stores != null) {
        stores.getCellValue().forEach(store -> {
          try {
            store.processDelete(getCellValue());
          } catch (Exception e) {
            log().debug(e);
          }
        });
      }

      return getSelf();
    }
  }
}
