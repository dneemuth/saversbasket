package com.sb.web.response.dto;

import java.math.BigDecimal;
import java.util.Map;

public class ContributedPriceResponseDTO {
	
	
	private boolean validated;
    private Map<String, String> errorMessages;
    
    private boolean userAuthenticated;
    private BigDecimal creditedReward;

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

	public BigDecimal getCreditedReward() {
		return creditedReward;
	}

	public void setCreditedReward(BigDecimal creditedReward) {
		this.creditedReward = creditedReward;
	}

	

}
