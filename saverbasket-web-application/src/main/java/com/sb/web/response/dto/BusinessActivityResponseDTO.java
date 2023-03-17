package com.sb.web.response.dto;

import java.util.Collection;

import io.swagger.annotations.ApiModelProperty;

public class BusinessActivityResponseDTO {
	
	@ApiModelProperty(position = 0)    
    private String activityDescription;
    
	@ApiModelProperty(position = 1)  
    private Collection<BusinessResponseDTO> businessCollection;

	public String getActivityDescription() {
		return activityDescription;
	}

	public void setActivityDescription(String activityDescription) {
		this.activityDescription = activityDescription;
	}

	public Collection<BusinessResponseDTO> getBusinessCollection() {
		return businessCollection;
	}

	public void setBusinessCollection(Collection<BusinessResponseDTO> businessCollection) {
		this.businessCollection = businessCollection;
	}


	
}
