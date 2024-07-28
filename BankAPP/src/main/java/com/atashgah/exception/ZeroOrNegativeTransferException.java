package com.atashgah.exception;

public class ZeroOrNegativeTransferException extends Throwable {
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ZeroOrNegativeTransferException(String message) {
		this.message=message;
	}
	

}
