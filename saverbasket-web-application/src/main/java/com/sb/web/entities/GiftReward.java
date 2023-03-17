package com.sb.web.entities;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name="giftreward")
public class GiftReward extends Reward {	
	private static final long serialVersionUID = 1L;

	@Column(name = "giftDetails")
	private String giftDetails;
	 
	@Column(name = "giftPictureUrl")
	private String giftPictureUrl;

	public String getGiftDetails() {
		return giftDetails;
	}

	public void setGiftDetails(String giftDetails) {
		this.giftDetails = giftDetails;
	}

	public String getGiftPictureUrl() {
		return giftPictureUrl;
	}

	public void setGiftPictureUrl(String giftPictureUrl) {
		this.giftPictureUrl = giftPictureUrl;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(giftDetails, giftPictureUrl);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		GiftReward other = (GiftReward) obj;
		return Objects.equals(giftDetails, other.giftDetails) && Objects.equals(giftPictureUrl, other.giftPictureUrl);
	} 
	
}
