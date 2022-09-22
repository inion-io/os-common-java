package io.inion.os.common.data.store.skills;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.data.store.SiDataStore;
import io.inion.os.common.data.store.SiDataStoreManager;
import io.inion.os.common.data.store.SiDataStoreProvider;
import io.inion.os.common.exception.CellRunException;
import io.inion.os.common.types.SiList;
import java.util.ArrayList;

@CellType(
    objectClass = SkGetStores.SkGetProvidersObject.class,
    type = SkGetStores.CELL_TYPE,
    uuid = SkGetStores.CELL_UUID
)
public interface SkGetStores extends SiCell<SkGetStores, SiList<SiDataStore<?>>> {

  String CELL_TYPE = "data-store-manager-get-stores-skill";

  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  class SkGetProvidersObject extends
      SiCellObject<SkGetStores, SiList<SiDataStore<?>>> implements SkGetStores {

    @Override
    public SkGetStores run() throws CellRunException {

      SiList<SiDataStore<?>> list = createTransientCell(SiList.class,
          new ArrayList<SiDataStore<?>>());

      SiList<SiDataStoreProvider<?>> providers = ((SiDataStoreManager) getController()).getProviders();

      if (providers != null) {
        providers.getCellValue().forEach(provider -> {
          list.add(provider.getStores());
        });
      }

      setCellValue(list);

      return getSelf();
    }
  }
}
