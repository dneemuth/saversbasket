package com.sb.web.request.dto;

import javax.validation.constraints.NotEmpty;

public class SignUpUserRequest {
	
    @NotEmpty(message = "username should not be empty.")    
    private String username;  
    
    @NotEmpty(message = "Email Address should not be empty.") 
    private String email; 
    
    @NotEmpty(message = "Password should not be empty.")
    private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
    

}
