package com.sb.web.response.dto;

import java.util.Date;

import com.sb.web.service.dto.BusinessDTO;

import io.swagger.annotations.ApiModelProperty;

public class CampaignResponseDTO {
	
	@ApiModelProperty(position = 0) 
    private String campaignName;
    
	@ApiModelProperty(position = 1) 
    private Integer campaignDuration;
    
	@ApiModelProperty(position = 2) 
    private Date campaignDateCreated;
    
	@ApiModelProperty(position = 3) 
    private Date campaignDateEnd;
    
	@ApiModelProperty(position = 4) 
    private Integer costingPrice;
    
	@ApiModelProperty(position = 5) 
    private Integer campaignApproved;  

	@ApiModelProperty(position = 6) 
	private BusinessDTO business;

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

	public Date getCampaignDateCreated() {
		return campaignDateCreated;
	}

	public void setCampaignDateCreated(Date campaignDateCreated) {
		this.campaignDateCreated = campaignDateCreated;
	}

	public Date getCampaignDateEnd() {
		return campaignDateEnd;
	}

	public void setCampaignDateEnd(Date campaignDateEnd) {
		this.campaignDateEnd = campaignDateEnd;
	}

	public Integer getCostingPrice() {
		return costingPrice;
	}

	public void setCostingPrice(Integer costingPrice) {
		this.costingPrice = costingPrice;
	}

	public Integer getCampaignApproved() {
		return campaignApproved;
	}

	public void setCampaignApproved(Integer campaignApproved) {
		this.campaignApproved = campaignApproved;
	}

	public BusinessDTO getBusiness() {
		return business;
	}

	public void setBusiness(BusinessDTO business) {
		this.business = business;
	}

	

}
