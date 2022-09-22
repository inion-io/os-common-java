package io.inion.os.common.data.store.skills;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.data.store.SiDataStore;
import io.inion.os.common.data.store.SiDataStoreManager;
import io.inion.os.common.data.store.SiDataStoreProvider;
import io.inion.os.common.exception.CellRunException;
import io.inion.os.common.types.SiList;

@CellType(
    objectClass = SkProcessSave.SkProcessSaveObject.class,
    type = SkProcessSave.CELL_TYPE,
    uuid = SkProcessSave.CELL_UUID
)
public interface SkProcessSave extends SiCell<SkProcessSave, SiCell<?, ?>> {

  String CELL_TYPE = "data-store-manager-process-save-skill";

  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  class SkProcessSaveObject extends SiCellObject<SkProcessSave, SiCell<?, ?>> implements
      SkProcessSave {

    @Override
    public SkProcessSave run() throws CellRunException {
      checkRunValuesForNull(getCellValue());

      SiList<SiDataStore<?>> stores = ((SiDataStoreManager) getController()).getStores();

      if (stores != null) {
        stores.getCellValue().forEach(store -> {
          try {
            store.processSave(getCellValue());
          } catch (Exception e) {
            log().debug(e);
          }
        });
      }

      return getSelf();
    }
  }
}
