package com.sb.web.service.dto;

import java.util.Date;

import com.sb.web.enumerations.ContributionStatus;
import com.sb.web.enumerations.ContributionType;

public class UserContributionDTO {
	

    private Integer idUserContribution;	 	 
	 	
	private Integer contributedByUserId;	

	private Date dateContributionAdded;	

	private ContributionType contributionType; 

	private ContributionStatus contributionStatus;	

	private Date updatedDateContributionStatus;	

	private String artifactUrl;
	
	private Integer linkedBusinessId;

	public Integer getIdUserContribution() {
		return idUserContribution;
	}

	public void setIdUserContribution(Integer idUserContribution) {
		this.idUserContribution = idUserContribution;
	}

	public Integer getContributedByUserId() {
		return contributedByUserId;
	}

	public void setContributedByUserId(Integer contributedByUserId) {
		this.contributedByUserId = contributedByUserId;
	}

	public Date getDateContributionAdded() {
		return dateContributionAdded;
	}

	public void setDateContributionAdded(Date dateContributionAdded) {
		this.dateContributionAdded = dateContributionAdded;
	}

	public ContributionType getContributionType() {
		return contributionType;
	}

	public void setContributionType(ContributionType contributionType) {
		this.contributionType = contributionType;
	}

	public ContributionStatus getContributionStatus() {
		return contributionStatus;
	}

	public void setContributionStatus(ContributionStatus contributionStatus) {
		this.contributionStatus = contributionStatus;
	}

	public Date getUpdatedDateContributionStatus() {
		return updatedDateContributionStatus;
	}

	public void setUpdatedDateContributionStatus(Date updatedDateContributionStatus) {
		this.updatedDateContributionStatus = updatedDateContributionStatus;
	}

	public String getArtifactUrl() {
		return artifactUrl;
	}

	public void setArtifactUrl(String artifactUrl) {
		this.artifactUrl = artifactUrl;
	}

	public Integer getLinkedBusinessId() {
		return linkedBusinessId;
	}

	public void setLinkedBusinessId(Integer linkedBusinessId) {
		this.linkedBusinessId = linkedBusinessId;
	}

	
	

}
