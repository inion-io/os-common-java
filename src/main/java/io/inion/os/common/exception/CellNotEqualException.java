package io.inion.os.common.exception;

/**
 * @author Marcus Bock
 */
public class CellNotEqualException extends CellException {

	public CellNotEqualException() {
		super();
	}

	public CellNotEqualException(String message) {
		super(message);
	}

	public CellNotEqualException(String message, Throwable cause) {
		super(message, cause);
	}

	public CellNotEqualException(Throwable cause) {
		super(cause);
	}

}