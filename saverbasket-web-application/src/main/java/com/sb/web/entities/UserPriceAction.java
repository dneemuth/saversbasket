package com.sb.web.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.sb.web.entities.audit.Auditable;
import com.sb.web.entities.converters.BooleanToStringConverter;

@Entity(name="userpriceaction")
public class UserPriceAction  extends Auditable<Integer> implements Serializable {

    private static final long serialVersionUID = 1L;
	
	@Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idUserPriceAction")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_price_action_generator")
    @SequenceGenerator(name = "user_price_action_generator", sequenceName = "user_price_action_seq", allocationSize = 1) 
    private Integer idUserPriceAction;
	
	@Column(name = "confirmed")
	@Convert(converter=BooleanToStringConverter.class)
	private Boolean confirmed;
	
	@Column(name = "dissaproved")
	@Convert(converter=BooleanToStringConverter.class)
	private Boolean dissaproved;	
	
	@Size(max = 100)
    @Column(name = "userActionComment")
    private String userActionComment;
	
	@ManyToOne(optional = true,cascade = CascadeType.ALL)
    @JoinColumn(name = "idPrice")
    private Price price;
	
	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	public boolean isDissaproved() {
		return dissaproved;
	}

	public void setDissaproved(boolean dissaproved) {
		this.dissaproved = dissaproved;
	}

	public String getUserActionComment() {
		return userActionComment;
	}

	public void setUserActionComment(String userActionComment) {
		this.userActionComment = userActionComment;
	}
	

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public Boolean getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
	}

	public Boolean getDissaproved() {
		return dissaproved;
	}

	public void setDissaproved(Boolean dissaproved) {
		this.dissaproved = dissaproved;
	}
	
	public Integer getIdUserPriceAction() {
		return idUserPriceAction;
	}

	public void setIdUserPriceAction(Integer idUserPriceAction) {
		this.idUserPriceAction = idUserPriceAction;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((confirmed == null) ? 0 : confirmed.hashCode());
		result = prime * result + ((dissaproved == null) ? 0 : dissaproved.hashCode());
		result = prime * result + ((idUserPriceAction == null) ? 0 : idUserPriceAction.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((userActionComment == null) ? 0 : userActionComment.hashCode());
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
		UserPriceAction other = (UserPriceAction) obj;
		if (confirmed == null) {
			if (other.confirmed != null)
				return false;
		} else if (!confirmed.equals(other.confirmed))
			return false;
		if (dissaproved == null) {
			if (other.dissaproved != null)
				return false;
		} else if (!dissaproved.equals(other.dissaproved))
			return false;
		if (idUserPriceAction == null) {
			if (other.idUserPriceAction != null)
				return false;
		} else if (!idUserPriceAction.equals(other.idUserPriceAction))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (userActionComment == null) {
			if (other.userActionComment != null)
				return false;
		} else if (!userActionComment.equals(other.userActionComment))
			return false;
		return true;
	}

}
