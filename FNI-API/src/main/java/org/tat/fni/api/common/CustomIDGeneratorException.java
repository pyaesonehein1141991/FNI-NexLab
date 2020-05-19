package org.tat.fni.api.common;

import org.springframework.transaction.TransactionSystemException;

public class CustomIDGeneratorException extends TransactionSystemException {
	private String errorCode;

	public CustomIDGeneratorException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public CustomIDGeneratorException(String errorCode, String message, Throwable throwable) {
		super(message, throwable);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}
}
