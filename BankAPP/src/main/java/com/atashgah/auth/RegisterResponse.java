package com.atashgah.auth;

public class RegisterResponse {
	
	private String jwt;
	
	private String message;
	
	public RegisterResponse (String jwt,String message) {
		this.jwt=jwt;
		this.message=message;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
