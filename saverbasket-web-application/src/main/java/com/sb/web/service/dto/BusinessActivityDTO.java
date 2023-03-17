package com.sb.web.service.dto;

import java.util.Collection;

import io.swagger.annotations.ApiModelProperty;

public class BusinessActivityDTO {
	
	@ApiModelProperty(position = 0)     
    private String activityDescription;
    
	@ApiModelProperty(position = 1)  
    private Collection<BusinessDTO> businessCollection;

	public String getActivityDescription() {
		return activityDescription;
	}

	public void setActivityDescription(String activityDescription) {
		this.activityDescription = activityDescription;
	}

	public Collection<BusinessDTO> getBusinessCollection() {
		return businessCollection;
	}

	public void setBusinessCollection(Collection<BusinessDTO> businessCollection) {
		this.businessCollection = businessCollection;
	}
	
	

}
