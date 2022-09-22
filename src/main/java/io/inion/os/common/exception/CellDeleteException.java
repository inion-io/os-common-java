package io.inion.os.common.exception;

/**
 * @author Marcus Bock
 */
public class CellDeleteException extends CellException {

	public CellDeleteException() {
		super();
	}

	public CellDeleteException(String message) {
		super(message);
	}

	public CellDeleteException(String message, Throwable cause) {
		super(message, cause);
	}

	public CellDeleteException(Throwable cause) {
		super(cause);
	}

}