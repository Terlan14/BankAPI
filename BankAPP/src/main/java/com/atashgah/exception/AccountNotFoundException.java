package com.atashgah.exception;

import org.springframework.http.HttpStatus;

public class AccountNotFoundException extends RuntimeException {
	public AccountNotFoundException(String message) {
		super(message);
	}
	
}
