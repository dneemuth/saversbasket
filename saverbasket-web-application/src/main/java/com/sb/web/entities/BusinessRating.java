package com.sb.web.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.sb.web.entities.audit.Auditable;

@Entity(name="businessrating")
public class BusinessRating  extends Auditable<Integer> implements Serializable {

    private static final long serialVersionUID = 1L;
	
	@Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idBusinessRating")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "business_rating_generator")
    @SequenceGenerator(name = "business_rating_generator", sequenceName = "business_rating_seq", allocationSize = 1)
    private Integer idBusinessRating;
	
	@Size(max = 200)
    @Column(name = "ratingComment")
    private String ratingComment;
	
	@Column(name = "qualityofService")
	private Integer qualityofService;	
	
	@ManyToOne(fetch =FetchType.LAZY, cascade =  CascadeType.ALL)
	@JoinColumn(name = "idBusiness", referencedColumnName = "idBusiness") 
    private Business business;
	

	public Integer getIdBusinessRating() {
		return idBusinessRating;
	}

	public void setIdBusinessRating(Integer idBusinessRating) {
		this.idBusinessRating = idBusinessRating;
	}

	public String getRatingComment() {
		return ratingComment;
	}

	public void setRatingComment(String ratingComment) {
		this.ratingComment = ratingComment;
	}

	public Integer getQualityofService() {
		return qualityofService;
	}

	public void setQualityofService(Integer qualityofService) {
		this.qualityofService = qualityofService;
	}

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((business == null) ? 0 : business.hashCode());
		result = prime * result + ((idBusinessRating == null) ? 0 : idBusinessRating.hashCode());
		result = prime * result + ((qualityofService == null) ? 0 : qualityofService.hashCode());
		result = prime * result + ((ratingComment == null) ? 0 : ratingComment.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BusinessRating other = (BusinessRating) obj;
		if (business == null) {
			if (other.business != null)
				return false;
		} else if (!business.equals(other.business))
			return false;
		if (idBusinessRating == null) {
			if (other.idBusinessRating != null)
				return false;
		} else if (!idBusinessRating.equals(other.idBusinessRating))
			return false;
		if (qualityofService == null) {
			if (other.qualityofService != null)
				return false;
		} else if (!qualityofService.equals(other.qualityofService))
			return false;
		if (ratingComment == null) {
			if (other.ratingComment != null)
				return false;
		} else if (!ratingComment.equals(other.ratingComment))
			return false;
		return true;
	}
    
    
    

}
