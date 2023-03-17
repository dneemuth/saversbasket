package com.sb.web.request.dto;

import com.sb.web.enumerations.ContributionType;

public class CreditRequestDTO {
	
	private Integer creditedUserId;
	private ContributionType contributionType;
	private Integer numberOfContributions;
	
	public Integer getCreditedUserId() {
		return creditedUserId;
	}
	public void setCreditedUserId(Integer creditedUserId) {
		this.creditedUserId = creditedUserId;
	}
	public ContributionType getContributionType() {
		return contributionType;
	}
	public void setContributionType(ContributionType contributionType) {
		this.contributionType = contributionType;
	}
	public Integer getNumberOfContributions() {
		return numberOfContributions;
	}
	public void setNumberOfContributions(Integer numberOfContributions) {
		this.numberOfContributions = numberOfContributions;
	}	

}
