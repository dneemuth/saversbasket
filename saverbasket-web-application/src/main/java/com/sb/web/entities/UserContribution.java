package com.sb.web.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import com.sb.web.entities.audit.Auditable;
import com.sb.web.enumerations.ContributionStatus;
import com.sb.web.enumerations.ContributionType;

@Entity(name="usercontribution")
public class UserContribution  extends Auditable<Integer> implements Serializable {

    private static final long serialVersionUID = 1L;
	
	@Id
    @Basic(optional = false)
    @Column(name = "idUserContribution")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_contrib_generator")
	@SequenceGenerator(name = "user_contrib_generator", sequenceName = "user_contrib_seq", allocationSize = 1) 
    private Integer idUserContribution;	 	
	 
    @Column(name = "creditsGained")
	private BigDecimal creditsGained;	
	 
	@Column(name = "contributedByUserId")
	private Integer contributedByUserId;	
	
	@Column(name = "dateContributionAdded")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateContributionAdded;	
	
	@Enumerated(EnumType.ORDINAL)
	private ContributionType contributionType; 
	
	@Enumerated(EnumType.STRING)
	private ContributionStatus contributionStatus;	
	
	@Column(name = "updatedDateContributionStatus")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDateContributionStatus;	
	
    @Size(max = 200)
    @Column(name = "artifactUrl")
	private String artifactUrl;
    
    @Size(max = 13)
    @Column(name = "scannedBarCode")
    private String scannedBarCode;
    
    @Min(value=1,message = "Enter a valid business.")
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

	public String getArtifactUrl() {
		return artifactUrl;
	}

	public void setArtifactUrl(String artifactUrl) {
		this.artifactUrl = artifactUrl;
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

	public Integer getLinkedBusinessId() {
		return linkedBusinessId;
	}

	public void setLinkedBusinessId(Integer linkedBusinessId) {
		this.linkedBusinessId = linkedBusinessId;
	}

	public BigDecimal getCreditsGained() {
		return creditsGained;
	}

	public void setCreditsGained(BigDecimal creditsGained) {
		this.creditsGained = creditsGained;
	}

	public String getScannedBarCode() {
		return scannedBarCode;
	}

	public void setScannedBarCode(String scannedBarCode) {
		this.scannedBarCode = scannedBarCode;
	}

}
