package com.sb.web.response.dto;

import java.util.Map;

public class ContactEmailResponseDTO {
	
	private boolean emailSentStatus;
	private Map<String, String> errorMessages;
	

	public boolean isEmailSentStatus() {
		return emailSentStatus;
	}

	public void setEmailSentStatus(boolean emailSentStatus) {
		this.emailSentStatus = emailSentStatus;
	}

	public Map<String, String> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(Map<String, String> errorMessages) {
		this.errorMessages = errorMessages;
	}
	
	
	
	

}
