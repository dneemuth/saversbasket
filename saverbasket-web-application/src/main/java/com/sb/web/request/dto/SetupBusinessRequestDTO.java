package com.sb.web.request.dto;

import io.swagger.annotations.ApiModelProperty;

public class SetupBusinessRequestDTO  {
	
	
	@ApiModelProperty(position = 0) 
	private String registeredName;
	
	@ApiModelProperty(position = 1) 
	private String address;
	
	@ApiModelProperty(position = 2) 
    private String businessQrCode;
	      
	@ApiModelProperty(position = 3)
    private String longitude;
  
	@ApiModelProperty(position = 4)   
    private String latitude;
    
	@ApiModelProperty(position = 5) 
    private Integer radius;
    
	@ApiModelProperty(position = 6) 
    private Integer status;
	
	@ApiModelProperty(position = 7) 
    private Integer activity;

	public String getRegisteredName() {
		return registeredName;
	}

	public void setRegisteredName(String registeredName) {
		this.registeredName = registeredName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBusinessQrCode() {
		return businessQrCode;
	}

	public void setBusinessQrCode(String businessQrCode) {
		this.businessQrCode = businessQrCode;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public Integer getRadius() {
		return radius;
	}

	public void setRadius(Integer radius) {
		this.radius = radius;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getActivity() {
		return activity;
	}

	public void setActivity(Integer activity) {
		this.activity = activity;
	}
}
