package com.sb.web.request.dto;

import io.swagger.annotations.ApiModelProperty;

public class SetupCampaignRequestDTO {
	
	@ApiModelProperty(position = 0) 
    private String campaignName;
	
	@ApiModelProperty(position = 1) 
	private Integer campaignDuration;	
	
	/**
	 * Text Ad representation
	 */
	@ApiModelProperty(position = 2)   
    private String description;
    
	@ApiModelProperty(position = 3) 
    private String normalPrice;    
    
	@ApiModelProperty(position = 4) 
    private String promotionalPrice;
    
	@ApiModelProperty(position = 5) 
    private String productReference;

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public Integer getCampaignDuration() {
		return campaignDuration;
	}

	public void setCampaignDuration(Integer campaignDuration) {
		this.campaignDuration = campaignDuration;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNormalPrice() {
		return normalPrice;
	}

	public void setNormalPrice(String normalPrice) {
		this.normalPrice = normalPrice;
	}

	public String getPromotionalPrice() {
		return promotionalPrice;
	}

	public void setPromotionalPrice(String promotionalPrice) {
		this.promotionalPrice = promotionalPrice;
	}

	public String getProductReference() {
		return productReference;
	}

	public void setProductReference(String productReference) {
		this.productReference = productReference;
	}
	
	

}
