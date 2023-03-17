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

import com.sb.web.entities.audit.Auditable;
import com.sb.web.enumerations.ContributionType;
import com.sb.web.enumerations.CreditType;


/**
*
* @author dilneemuth
*/
@Entity(name="credit")
public class Credit  extends Auditable<Integer> implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idCredit")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "credit_generator")
    @SequenceGenerator(name = "credit_generator", sequenceName = "credit_seq", allocationSize = 1)
    private Integer idCredit;	
	
	@Enumerated(EnumType.ORDINAL)
    private ContributionType contributionType;
	
	@Enumerated(EnumType.ORDINAL)
	private CreditType creditType;
	
	@Column(name = "points")
	private BigDecimal points;

	public Integer getIdCredit() {
		return idCredit;
	}

	public void setIdCredit(Integer idCredit) {
		this.idCredit = idCredit;
	}

	public ContributionType getContributionType() {
		return contributionType;
	}

	public void setContributionType(ContributionType contributionType) {
		this.contributionType = contributionType;
	}

	public CreditType getCreditType() {
		return creditType;
	}

	public void setCreditType(CreditType creditType) {
		this.creditType = creditType;
	}

	public BigDecimal getPoints() {
		return points;
	}

	public void setPoints(BigDecimal points) {
		this.points = points;
	}

	@Override
	public int hashCode() {
		return Objects.hash(contributionType, creditType, idCredit, points);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Credit other = (Credit) obj;
		return contributionType == other.contributionType && creditType == other.creditType
				&& Objects.equals(idCredit, other.idCredit) && Objects.equals(points, other.points);
	}
	
}
