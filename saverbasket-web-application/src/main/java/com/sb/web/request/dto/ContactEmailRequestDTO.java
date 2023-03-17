package com.sb.web.request.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class ContactEmailRequestDTO {
	
	@NotEmpty(message="Enter name.")
	private String name;
	
	@NotEmpty(message="Enter email address")
	@Email
	private String email;
	
	@NotEmpty(message="Enter mail subject.")
	private String subject;
	
	@NotEmpty(message="Enter mail message.")
	private String message;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
