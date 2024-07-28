package com.atashgah.auth;

import com.atashgah.dto.UserLoginResponse;

public class AuthenticationResponse {
	
	private String jwt;
	private UserLoginResponse user;

	public AuthenticationResponse(String jwt, UserLoginResponse user) {
		
		this.jwt = jwt;
		this.user = user;
	}
	
	public String getJwt() {
		return jwt;
	}
	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
	public UserLoginResponse getUser() {
		return user;
	}
	public void setUser(UserLoginResponse user) {
		this.user = user;
	}
	
	

}
