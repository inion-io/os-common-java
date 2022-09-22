package io.inion.os.common.logging;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@CellType(
    objectClass = SiLogManager.SiLogManagerObject.class,
    type = SiLogManager.CELL_TYPE,
    uuid = SiLogManager.CELL_UUID
)
public interface SiLogManager extends SiCell<SiLogManager, Void> {

  String CELL_TYPE = "log-manager";

  String CELL_UUID = "D93364D5-C2AC-4DC4-B4CA-4BA22FDD59B9";

  SiLogger getLogger(SiCell<?, ?> cell);

  class SiLogManagerObject extends SiCellObject<SiLogManager, Void> implements SiLogManager {

    private Map<SiCell<?, ?>, SiLogger> cache;

    @Override
    public SiLogManager create() {
      // TODO: Cache implementieren
      cache = new HashMap<>();
      setCreated(true);

      return this.getSelf();
    }

    @Override
    public SiLogger getLogger(SiCell<?, ?> cell) {
      SiLogger logger = cache.get(cell);

      if (logger == null) {
        Logger value = LogManager.getLogger(cell.getCellClass());
        logger = buildCell(SiLogger.class);
        logger.setCellValue(value);

        cache.put(cell, logger);
      }

      return logger;
    }
  }
}
