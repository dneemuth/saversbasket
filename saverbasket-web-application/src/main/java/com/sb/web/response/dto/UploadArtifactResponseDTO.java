package com.sb.web.response.dto;

import java.util.Date;

public class UploadArtifactResponseDTO {
	

    private Integer idUserContribution;	 	 
	 	
	private Integer contributedByUserId;	

	private Date dateContributionAdded;	
	
	private String artifactUrl;
	
	private String contentType;

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

	public String getArtifactUrl() {
		return artifactUrl;
	}

	public void setArtifactUrl(String artifactUrl) {
		this.artifactUrl = artifactUrl;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	

}
