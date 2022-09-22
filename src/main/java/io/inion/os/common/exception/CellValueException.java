package io.inion.os.common.exception;

/**
 * @author Marcus Bock
 */
public class CellValueException extends CellException {

	public CellValueException() {
		super();
	}

	public CellValueException(String message) {
		super(message);
	}

	public CellValueException(String message, Throwable cause) {
		super(message, cause);
	}

	public CellValueException(Throwable cause) {
		super(cause);
	}

}