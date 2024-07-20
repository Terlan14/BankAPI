package com.atashgah.auth;

public class AuthenticationRequest {
	private String pin;
	private String password;
	
	
	
	public AuthenticationRequest() {
		
	}
	public AuthenticationRequest(String pin, String password) {
		
		this.pin = pin;
		this.password = password;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
