package com.atashgah.exception;

public class AccountAlreadyActiveException extends RuntimeException {
	
	private String message;
	
	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public AccountAlreadyActiveException(String message) {
		this.message=message;
	}

}
