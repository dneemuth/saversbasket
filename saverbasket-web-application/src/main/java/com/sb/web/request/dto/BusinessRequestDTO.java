package com.sb.web.request.dto;

import io.swagger.annotations.ApiModelProperty;


public class BusinessRequestDTO {
	
	@ApiModelProperty(position = 0) 
	private String email;
	      
	@ApiModelProperty(position = 1) 
	private String latitude;
    
	@ApiModelProperty(position = 2) 
	private String longitude;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

		
	
}
