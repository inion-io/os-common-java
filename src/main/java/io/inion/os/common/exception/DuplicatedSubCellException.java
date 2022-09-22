package io.inion.os.common.exception;

/**
 * @author Marcus Bock
 */
public class DuplicatedSubCellException extends CellException {

	public DuplicatedSubCellException() {
		super();
	}

	public DuplicatedSubCellException(String message) {
		super(message);
	}

	public DuplicatedSubCellException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicatedSubCellException(Throwable cause) {
		super(cause);
	}

}