package io.inion.os.common.data.store;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.Cell;
import io.inion.os.common.exception.CellDeleteException;
import io.inion.os.common.exception.CellDestroyException;
import io.inion.os.common.exception.CellSaveException;
import io.inion.os.common.types.SiString;

public interface SiDataStore<C extends SiCell<?, ?>> extends SiCell<C, Void> {

  String CELL_TYPE = "data-store";

  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  void processSave(SiCell<?, ?> cell) throws CellSaveException;

  void processDelete(SiCell<?, ?> cell) throws CellDeleteException;

  SiString getName();

  SiString getDisplayName();

  SiString getDescription();

  C setName(SiString name);

  C setDisplayName(SiString displayName);

  C setDescription(SiString description);

  abstract class SiDataStoreObject<C extends SiCell<?, ?>> extends
      SiCellObject<C, Void> implements SiDataStore<C> {

    @Cell
    private SiString name;
    @Cell
    private SiString displayName;
    @Cell
    private SiString description;

    @Override
    public SiString getName() {
      return this.name;
    }

    @Override
    public SiString getDisplayName() {
      return this.displayName;
    }

    @Override
    public SiString getDescription() {
      return this.description;
    }

    @Override
    public C setName(SiString name) {
      return swapSubCell(this.name, name);
    }

    @Override
    public C setDisplayName(SiString displayName) {
      return swapSubCell(this.displayName, displayName);
    }

    @Override
    public C setDescription(SiString description) {
      return swapSubCell(this.description, description);
    }
  }
}
