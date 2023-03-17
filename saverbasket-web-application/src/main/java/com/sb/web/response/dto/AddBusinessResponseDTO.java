package com.sb.web.response.dto;

import java.math.BigDecimal;
import java.util.Map;

public class AddBusinessResponseDTO {
	
	private boolean validated;
    private Map<String, String> errorMessages;
    
    private boolean userAuthenticated;
    
    private BigDecimal monetaryReward;

	public boolean isValidated() {
		return validated;
	}

	public void setValidated(boolean validated) {
		this.validated = validated;
	}

	public Map<String, String> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(Map<String, String> errorMessages) {
		this.errorMessages = errorMessages;
	}

	public boolean isUserAuthenticated() {
		return userAuthenticated;
	}

	public void setUserAuthenticated(boolean userAuthenticated) {
		this.userAuthenticated = userAuthenticated;
	}

	public BigDecimal getMonetaryReward() {
		return monetaryReward;
	}

	public void setMonetaryReward(BigDecimal monetaryReward) {
		this.monetaryReward = monetaryReward;
	}

	


}
