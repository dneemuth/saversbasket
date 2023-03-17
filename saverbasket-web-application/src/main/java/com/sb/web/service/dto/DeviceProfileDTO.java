package com.sb.web.service.dto;

import java.util.Collection;

import io.swagger.annotations.ApiModelProperty;

public class DeviceProfileDTO {
	
	@ApiModelProperty(position = 0)     
    private String phoneNumber;
    
	@ApiModelProperty(position = 1)   
    private String gcmIdentifier;
    
	@ApiModelProperty(position = 2)    
    private String mobileOS;
    
	@ApiModelProperty(position = 3) 
    private String mobileQRCode;
    
	@ApiModelProperty(position = 4) 
    private Collection<UserDataDTO> userCollection;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getGcmIdentifier() {
		return gcmIdentifier;
	}

	public void setGcmIdentifier(String gcmIdentifier) {
		this.gcmIdentifier = gcmIdentifier;
	}

	public String getMobileOS() {
		return mobileOS;
	}

	public void setMobileOS(String mobileOS) {
		this.mobileOS = mobileOS;
	}

	public String getMobileQRCode() {
		return mobileQRCode;
	}

	public void setMobileQRCode(String mobileQRCode) {
		this.mobileQRCode = mobileQRCode;
	}

	public Collection<UserDataDTO> getUserCollection() {
		return userCollection;
	}

	public void setUserCollection(Collection<UserDataDTO> userCollection) {
		this.userCollection = userCollection;
	}
	
	

}
