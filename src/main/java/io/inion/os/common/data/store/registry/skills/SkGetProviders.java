package io.inion.os.common.data.store.registry.skills;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.data.store.SiDataStoreProvider;
import io.inion.os.common.data.store.registry.SiDataStoreProviderRegistryEntry;
import io.inion.os.common.exception.CellRunException;
import io.inion.os.common.types.SiList;
import java.util.ArrayList;

@CellType(
    objectClass = SkGetProviders.SkGetProvidersObject.class,
    type = SkGetProviders.CELL_TYPE,
    uuid = SkGetProviders.CELL_UUID
)
public interface SkGetProviders extends SiCell<SkGetProviders, SiList<SiDataStoreProvider<?>>> {

  String CELL_TYPE = "data-store-provider-registry-get-providers-skill";

  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  class SkGetProvidersObject extends
      SiCellObject<SkGetProviders, SiList<SiDataStoreProvider<?>>> implements
      SkGetProviders {

    @Override
    public SkGetProviders run() throws CellRunException {

      SiList<SiDataStoreProvider<?>> list = createTransientCell(SiList.class,
          new ArrayList<SiDataStoreProvider<?>>());

      getController().getSubCells(SiDataStoreProviderRegistryEntry.class).forEach(subCell -> {
        list.getCellValue().add(subCell.getCellValue());
      });

      setCellValue(list);

      return getSelf();
    }
  }
}
