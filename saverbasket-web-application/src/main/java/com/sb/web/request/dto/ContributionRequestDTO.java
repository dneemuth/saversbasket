package com.sb.web.request.dto;

import com.sb.web.enumerations.ContributionType;

public class ContributionRequestDTO {
	
	private Integer userIdToBeCredited;
	private ContributionType contributionType;
	private Integer numberOfContributions;
	private String scannedBarCode;
	
	public Integer getUserIdToBeCredited() {
		return userIdToBeCredited;
	}
	public void setUserIdToBeCredited(Integer userIdToBeCredited) {
		this.userIdToBeCredited = userIdToBeCredited;
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
	public String getScannedBarCode() {
		return scannedBarCode;
	}
	public void setScannedBarCode(String scannedBarCode) {
		this.scannedBarCode = scannedBarCode;
	}	
	
}
