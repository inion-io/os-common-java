package io.inion.os.common.exception;

/**
 * @author Marcus Bock
 */
public class CellException extends Exception {

	public CellException() {
		super();
	}

	public CellException(String message) {
		super(message);
	}

	public CellException(String message, Throwable cause) {
		super(message, cause);
	}

	public CellException(Throwable cause) {
		super(cause);
	}

}