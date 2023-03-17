package com.sb.web.response.dto;

import java.util.Collection;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

public class SubscriptionPlanResponseDTO {
	
	@ApiModelProperty(position = 0)   
    private String subscriptionName;
	
	@ApiModelProperty(position = 1) 
    private String subscriptionDescription;
       
	@ApiModelProperty(position = 2) 
    private Date subscriptionDatePurchased;
    
	@ApiModelProperty(position = 3) 
    private Date subscriptionStartDate;
    
	@ApiModelProperty(position = 4) 
    private Date subscriptionEndDate;
    
	@ApiModelProperty(position = 5) 
    private Collection<UserResponseDTO> userCollection;
    
	@ApiModelProperty(position = 6) 
    private Collection<BillingResponseDTO> billingCollection;

	public String getSubscriptionName() {
		return subscriptionName;
	}

	public void setSubscriptionName(String subscriptionName) {
		this.subscriptionName = subscriptionName;
	}

	public String getSubscriptionDescription() {
		return subscriptionDescription;
	}

	public void setSubscriptionDescription(String subscriptionDescription) {
		this.subscriptionDescription = subscriptionDescription;
	}

	public Date getSubscriptionDatePurchased() {
		return subscriptionDatePurchased;
	}

	public void setSubscriptionDatePurchased(Date subscriptionDatePurchased) {
		this.subscriptionDatePurchased = subscriptionDatePurchased;
	}

	public Date getSubscriptionStartDate() {
		return subscriptionStartDate;
	}

	public void setSubscriptionStartDate(Date subscriptionStartDate) {
		this.subscriptionStartDate = subscriptionStartDate;
	}

	public Date getSubscriptionEndDate() {
		return subscriptionEndDate;
	}

	public void setSubscriptionEndDate(Date subscriptionEndDate) {
		this.subscriptionEndDate = subscriptionEndDate;
	}

	public Collection<UserResponseDTO> getUserCollection() {
		return userCollection;
	}

	public void setUserCollection(Collection<UserResponseDTO> userCollection) {
		this.userCollection = userCollection;
	}

	public Collection<BillingResponseDTO> getBillingCollection() {
		return billingCollection;
	}

	public void setBillingCollection(Collection<BillingResponseDTO> billingCollection) {
		this.billingCollection = billingCollection;
	}
	
	
}
