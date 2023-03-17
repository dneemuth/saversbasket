package com.sb.web.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import com.sb.web.entities.audit.Auditable;
import com.sb.web.entities.converters.BooleanToStringConverter;

@Entity(name="follower")
public class Follower  extends Auditable<Integer> implements Serializable {

    private static final long serialVersionUID = 1L;
	
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "follower_generator")
    @SequenceGenerator(name = "follower_generator", sequenceName = "follower_seq", allocationSize = 1)
    private Integer idFollower;	

    @Column(name = "userFollowing")
    private Integer userFollowing;
    
    @Convert(converter=BooleanToStringConverter.class)
    private Boolean followStatus; 
    
    @ManyToOne(fetch =FetchType.LAZY, cascade =  CascadeType.ALL)
    @JoinColumn(name = "idBusiness", referencedColumnName = "idBusiness")  
    private Business business;    
        
	public Integer getIdFollower() {
		return idFollower;
	}

	public void setIdFollower(Integer idFollower) {
		this.idFollower = idFollower;
	}

	public Integer getUserFollowing() {
		return userFollowing;
	}

	public void setUserFollowing(Integer userFollowing) {
		this.userFollowing = userFollowing;
	}

	public Boolean getFollowStatus() {
		return followStatus;
	}

	public void setFollowStatus(Boolean followStatus) {
		this.followStatus = followStatus;
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
		result = prime * result + ((followStatus == null) ? 0 : followStatus.hashCode());
		result = prime * result + ((idFollower == null) ? 0 : idFollower.hashCode());
		result = prime * result + ((userFollowing == null) ? 0 : userFollowing.hashCode());
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
		Follower other = (Follower) obj;
		if (business == null) {
			if (other.business != null)
				return false;
		} else if (!business.equals(other.business))
			return false;
		if (followStatus == null) {
			if (other.followStatus != null)
				return false;
		} else if (!followStatus.equals(other.followStatus))
			return false;
		if (idFollower == null) {
			if (other.idFollower != null)
				return false;
		} else if (!idFollower.equals(other.idFollower))
			return false;
		if (userFollowing == null) {
			if (other.userFollowing != null)
				return false;
		} else if (!userFollowing.equals(other.userFollowing))
			return false;
		return true;
	}
    
}
