package com.sb.web.response.dto;

import java.math.BigDecimal;
import java.util.Date;

public class CreditUpdateForUserResponse {
	
	private BigDecimal totalCredits;
	private Integer userContributions;
	private Boolean userAnonymous;
	private Date lastCreditDate;

	public BigDecimal getTotalCredits() {
		return totalCredits;
	}

	public void setTotalCredits(BigDecimal totalCredits) {
		this.totalCredits = totalCredits;
	}

	public Boolean getUserAnonymous() {
		return userAnonymous;
	}

	public void setUserAnonymous(Boolean userAnonymous) {
		this.userAnonymous = userAnonymous;
	}

	public Integer getUserContributions() {
		return userContributions;
	}

	public void setUserContributions(Integer userContributions) {
		this.userContributions = userContributions;
	}

	public Date getLastCreditDate() {
		return lastCreditDate;
	}

	public void setLastCreditDate(Date lastCreditDate) {
		this.lastCreditDate = lastCreditDate;
	}

}
