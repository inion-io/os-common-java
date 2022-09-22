package io.inion.os.common.data.store;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.Cell;
import io.inion.os.common.exception.CellDestroyException;
import io.inion.os.common.exception.CellSaveException;
import io.inion.os.common.types.SiList;
import io.inion.os.common.types.SiString;

public interface SiDataStoreProvider<C extends SiCell<?, ?>> extends SiCell<C, Void> {

  String CELL_TYPE = "data-store-provider";

  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  SiString getName();

  SiString getDisplayName();

  SiString getDescription();

  SiString getKey();

  SiList<SiDataStore<?>> getStores();

  abstract class SiDataStoreProviderObject<C extends SiCell<?, ?>> extends
      SiCellObject<C, Void> implements SiDataStoreProvider<C> {

  }
}
