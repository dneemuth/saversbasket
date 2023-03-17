package com.sb.web.response.dto;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

public class BillingResponseDTO {
	
	@ApiModelProperty(position = 0)  
	private String currency;
	    
	@ApiModelProperty(position = 1)    
	private String paymentNote;
	    
	@ApiModelProperty(position = 2)   
	private Integer balance;    
	    
	@ApiModelProperty(position = 3) 
	private Date paymentEffectedDate;
	    
	@ApiModelProperty(position = 4)     
	private String transactionReference;
	    
	@ApiModelProperty(position = 5) 
	private CampaignResponseDTO campaignDTO;
	    
	@ApiModelProperty(position = 6) 
	private PaymentTypeResponseDTO paymentTypeDTO;
	    
	@ApiModelProperty(position = 7)    
	private SubscriptionPlanResponseDTO subscriptionPlanDTO;
	    
	@ApiModelProperty(position = 7) 
    private UserResponseDTO userDataDTO;

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getPaymentNote() {
		return paymentNote;
	}

	public void setPaymentNote(String paymentNote) {
		this.paymentNote = paymentNote;
	}

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	public Date getPaymentEffectedDate() {
		return paymentEffectedDate;
	}

	public void setPaymentEffectedDate(Date paymentEffectedDate) {
		this.paymentEffectedDate = paymentEffectedDate;
	}

	public String getTransactionReference() {
		return transactionReference;
	}

	public void setTransactionReference(String transactionReference) {
		this.transactionReference = transactionReference;
	}

	public CampaignResponseDTO getCampaignDTO() {
		return campaignDTO;
	}

	public void setCampaignDTO(CampaignResponseDTO campaignDTO) {
		this.campaignDTO = campaignDTO;
	}

	public PaymentTypeResponseDTO getPaymentTypeDTO() {
		return paymentTypeDTO;
	}

	public void setPaymentTypeDTO(PaymentTypeResponseDTO paymentTypeDTO) {
		this.paymentTypeDTO = paymentTypeDTO;
	}

	public SubscriptionPlanResponseDTO getSubscriptionPlanDTO() {
		return subscriptionPlanDTO;
	}

	public void setSubscriptionPlanDTO(SubscriptionPlanResponseDTO subscriptionPlanDTO) {
		this.subscriptionPlanDTO = subscriptionPlanDTO;
	}

	public UserResponseDTO getUserDataDTO() {
		return userDataDTO;
	}

	public void setUserDataDTO(UserResponseDTO userDataDTO) {
		this.userDataDTO = userDataDTO;
	}
	
	

}
