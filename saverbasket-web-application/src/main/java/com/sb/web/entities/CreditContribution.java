package com.sb.web.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.sb.web.entities.audit.Auditable;
import com.sb.web.enumerations.ContributionType;
import com.sb.web.enumerations.CreditContributionStatus;

@Entity(name="creditcontribution")
public class CreditContribution  extends Auditable<Integer> implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idCreditContribution")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "credit_contribution_generator")
    @SequenceGenerator(name = "credit_contribution_generator", sequenceName = "credit_contribution_seq", allocationSize = 1)
    private Integer idRewardContribution;
    
    @Enumerated(EnumType.ORDINAL)
    private ContributionType contributionType;
    
    @Enumerated(EnumType.ORDINAL)
    private CreditContributionStatus creditContributionStatus;
    
    @Column(name = "creditGranted")
    private BigDecimal creditGranted;
    
    @Size(max = 100)
    @Column(name = "creditDetails")
    private String creditDetails;
    
    @Column(name = "numberOfContributions")
    private Integer numberOfContributions;
    
    @Column(name = "creditAddedForUser")
    private Integer creditAddedForUser;

	public Integer getIdRewardContribution() {
		return idRewardContribution;
	}

	public void setIdRewardContribution(Integer idRewardContribution) {
		this.idRewardContribution = idRewardContribution;
	}

	public ContributionType getContributionType() {
		return contributionType;
	}

	public void setContributionType(ContributionType contributionType) {
		this.contributionType = contributionType;
	}

	public CreditContributionStatus getCreditContributionStatus() {
		return creditContributionStatus;
	}

	public void setCreditContributionStatus(CreditContributionStatus creditContributionStatus) {
		this.creditContributionStatus = creditContributionStatus;
	}

	public BigDecimal getCreditGranted() {
		return creditGranted;
	}

	public void setCreditGranted(BigDecimal creditGranted) {
		this.creditGranted = creditGranted;
	}

	public String getCreditDetails() {
		return creditDetails;
	}

	public void setCreditDetails(String creditDetails) {
		this.creditDetails = creditDetails;
	}

	public Integer getNumberOfContributions() {
		return numberOfContributions;
	}

	public void setNumberOfContributions(Integer numberOfContributions) {
		this.numberOfContributions = numberOfContributions;
	}

	public Integer getCreditAddedForUser() {
		return creditAddedForUser;
	}

	public void setCreditAddedForUser(Integer creditAddedForUser) {
		this.creditAddedForUser = creditAddedForUser;
	}

	@Override
	public int hashCode() {
		return Objects.hash(contributionType, creditAddedForUser, creditContributionStatus, creditDetails,
				creditGranted, idRewardContribution, numberOfContributions);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CreditContribution other = (CreditContribution) obj;
		return contributionType == other.contributionType
				&& Objects.equals(creditAddedForUser, other.creditAddedForUser)
				&& creditContributionStatus == other.creditContributionStatus
				&& Objects.equals(creditDetails, other.creditDetails)
				&& Objects.equals(creditGranted, other.creditGranted)
				&& Objects.equals(idRewardContribution, other.idRewardContribution)
				&& Objects.equals(numberOfContributions, other.numberOfContributions);
	}


}
