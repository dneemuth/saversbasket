package com.sb.web.service.dto;

import javax.validation.Valid;

public class RegisterEmailDTO {

	@Valid
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
