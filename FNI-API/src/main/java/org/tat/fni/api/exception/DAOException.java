package org.tat.fni.api.exception;

public class DAOException extends RuntimeException {
	private static final long serialVersionUID = -4074043042642025225L;

	private String errorCode;

	public DAOException(String errorCode, String message, Throwable throwable) {
		super(message, throwable);
		this.errorCode = errorCode;
	}

	public DAOException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
