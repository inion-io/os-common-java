package io.inion.os.common.exception;

/**
 * @author Marcus Bock
 */
public class CellSaveException extends CellException {

	public CellSaveException() {
		super();
	}

	public CellSaveException(String message) {
		super(message);
	}

	public CellSaveException(String message, Throwable cause) {
		super(message, cause);
	}

	public CellSaveException(Throwable cause) {
		super(cause);
	}

}