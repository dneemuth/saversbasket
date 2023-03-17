package com.sb.web.response.dto;

import java.util.Collection;

import io.swagger.annotations.ApiModelProperty;

public class PaymentTypeResponseDTO {
	

	@ApiModelProperty(position = 0)     
    private String paymentTypeDescription;
    
	@ApiModelProperty(position = 1)  
    private Collection<BillingResponseDTO> billingCollection;

	public String getPaymentTypeDescription() {
		return paymentTypeDescription;
	}

	public void setPaymentTypeDescription(String paymentTypeDescription) {
		this.paymentTypeDescription = paymentTypeDescription;
	}

	public Collection<BillingResponseDTO> getBillingCollection() {
		return billingCollection;
	}

	public void setBillingCollection(Collection<BillingResponseDTO> billingCollection) {
		this.billingCollection = billingCollection;
	}
	
	

}
