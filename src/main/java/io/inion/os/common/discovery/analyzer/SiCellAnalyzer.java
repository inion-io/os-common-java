package io.inion.os.common.discovery.analyzer;

import io.inion.os.common.SiCell;
import io.inion.os.common.exception.CellRunException;

public interface SiCellAnalyzer extends SiCell<SiCellAnalyzer, Void> {

  String CELL_TYPE = "cell-analyzer";

  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  SiCellAnalyzer setCell(SiCell<?, ?> cell);

  abstract class SiCellAnalyzerObject extends SiCellObject<SiCellAnalyzer, Void> implements
      SiCellAnalyzer {

    protected SiCell<?, ?> cell;

    public abstract void doRun();

    @Override
    public SiCellAnalyzer run() throws CellRunException {
      if (cell == null) {
        return getSelf();
      }

      doRun();

      return getSelf();
    }

    @Override
    public SiCellAnalyzer setCell(SiCell<?, ?> cell) {
      this.cell = cell;
      return getSelf();
    }
  }
}
