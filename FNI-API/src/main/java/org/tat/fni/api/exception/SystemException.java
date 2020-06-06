package org.tat.fni.api.exception;

import org.springframework.transaction.TransactionSystemException;

public class SystemException extends TransactionSystemException {
	private static final long serialVersionUID = -4310366412683752065L;
	private String errorCode;
	private Object response;
	private int statusCode;

	public SystemException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public SystemException(int errorCode, String message) {
		super(message);
		this.statusCode = errorCode;
	}

	public SystemException(String errorCode, String message, Throwable throwable) {
		super(message, throwable);
		throwable.printStackTrace();
		this.errorCode = errorCode;
	}

	public SystemException(String errorCode, Object response, String message) {
		super(message);
		this.errorCode = errorCode;
		this.response = response;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public Object getResponse() {
		return response;
	}

	public int getStatusCode() {
		return statusCode;
	}

}
