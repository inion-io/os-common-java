package io.inion.os.common.exception;

import io.inion.os.common.SiCell;

/**
 * @author Marcus Bock
 */
public class CellRunException extends CellException {

  // TODO: cell.getName() mit cell.getLongName() ersetzen

  public CellRunException(SiCell<?, ?> cell) {
    super("Error when calling the run method from cell " + cell.getCellClass().getName()
        + ". Sub cells and values not properly setted.");
  }

  public CellRunException(SiCell<?, ?> cell, String message) {
    super("Error when calling the run method from cell " + cell.getCellClass().getName()
        + ". " + message);
  }

  public CellRunException(SiCell<?, ?> cell, String message, Throwable cause) {
    super("Error when calling the run method from cell " + cell.getCellClass().getName()
        + ". " + message, cause);
  }

  public CellRunException(SiCell<?, ?> cell, Throwable cause) {
    super("Error when calling the run method from cell " + cell.getCellClass().getName() + ".",
        cause);
  }
}