package com.sb.web.service.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class BusinessDTO {
	
	@ApiModelProperty(position = 0) 
    private Integer idBusiness;
	
	@ApiModelProperty(position = 1) 
	private String registeredName;
	
	@ApiModelProperty(position = 2) 
	private String businessLogoUrl;

	public Integer getIdBusiness() {
		return idBusiness;
	}

	public void setIdBusiness(Integer idBusiness) {
		this.idBusiness = idBusiness;
	}

	public String getRegisteredName() {
		return registeredName;
	}

	public void setRegisteredName(String registeredName) {
		this.registeredName = registeredName;
	}

	public String getBusinessLogoUrl() {
		return businessLogoUrl;
	}

	public void setBusinessLogoUrl(String businessLogoUrl) {
		this.businessLogoUrl = businessLogoUrl;
	}

}
