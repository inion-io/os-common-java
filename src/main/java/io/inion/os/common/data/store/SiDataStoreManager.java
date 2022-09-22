package io.inion.os.common.data.store;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.Cell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.data.store.registry.SiDataStoreProviderRegistry;
import io.inion.os.common.data.store.skills.SkGetStores;
import io.inion.os.common.data.store.skills.SkProcessDelete;
import io.inion.os.common.data.store.skills.SkProcessSave;
import io.inion.os.common.discovery.messages.SiDiscoveryInitialized;
import io.inion.os.common.exception.CellDeleteException;
import io.inion.os.common.exception.CellDestroyException;
import io.inion.os.common.exception.CellRunException;
import io.inion.os.common.exception.CellSaveException;
import io.inion.os.common.messaging.annotation.Event;
import io.inion.os.common.types.SiList;
import io.inion.os.common.types.SiProperty;
import io.inion.os.common.types.SiString;

@CellType(
    objectClass = SiDataStoreManager.SiDataStoreManagerObject.class,
    type = SiDataStoreManager.CELL_TYPE,
    uuid = SiDataStoreManager.CELL_UUID
)
public interface SiDataStoreManager extends SiCell<SiDataStoreManager, Void> {

  String CELL_TYPE = "data-store-manager";

  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  void processSave(SiCell<?, ?> cell) throws CellSaveException;

  void processDelete(SiCell<?, ?> cell) throws CellDeleteException;

  SiList<SiDataStoreProvider<?>> getProviders();

  SiList<SiDataStore<?>> getStores();

  class SiDataStoreManagerObject extends SiCellObject<SiDataStoreManager, Void>
      implements SiDataStoreManager {

    @Cell
    private SiDataStoreProviderRegistry dataStoreProviderRegistry;
    @Cell
    private SkProcessSave skProcessSave;
    @Cell
    private SkProcessDelete skProcessDelete;
    @Cell
    private SkGetStores skGetStores;

    @Override
    public void processSave(SiCell<?, ?> cell) throws CellSaveException {
      try {
        skProcessSave
            .createTransientInstance()
            .setCellValue(cell)
            .runAndDestroy();
      } catch (CellRunException e) {
        log().error(e);

        throw new CellSaveException(e);
      }
    }

    @Override
    public void processDelete(SiCell<?, ?> cell) throws CellDeleteException {
      try {
        skProcessDelete
            .createTransientInstance()
            .setCellValue(cell)
            .runAndDestroy();
      } catch (CellRunException e) {
        log().error(e);

        throw new CellDeleteException(e);
      }
    }

    @Override
    public SiList<SiDataStoreProvider<?>> getProviders() {
      return dataStoreProviderRegistry.getProviders();
    }

    @Override
    public SiList<SiDataStore<?>> getStores() {
      try {
        return skGetStores
            .createTransientInstance()
            .returnAndDestroy();
      } catch (CellRunException e) {
        log().error(e);
      }

      return null;
    }
  }
}
