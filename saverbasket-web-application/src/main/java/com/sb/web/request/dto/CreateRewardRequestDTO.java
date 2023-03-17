package com.sb.web.request.dto;

import javax.validation.constraints.NotEmpty;

public class CreateRewardRequestDTO {
	
	@NotEmpty(message="Enter Reward Name.")
	private String rewardName;
	
	@NotEmpty(message="Enter Gift Details.")
	private String giftDetails;	
	
	private String rewardPromoEndDate;

	public String getRewardName() {
		return rewardName;
	}

	public void setRewardName(String rewardName) {
		this.rewardName = rewardName;
	}

	public String getGiftDetails() {
		return giftDetails;
	}

	public void setGiftDetails(String giftDetails) {
		this.giftDetails = giftDetails;
	}

	public String getRewardPromoEndDate() {
		return rewardPromoEndDate;
	}

	public void setRewardPromoEndDate(String rewardPromoEndDate) {
		this.rewardPromoEndDate = rewardPromoEndDate;
	}

	
}
