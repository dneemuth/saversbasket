package com.sb.web.response.dto;

import java.util.Collection;

import io.swagger.annotations.ApiModelProperty;

public class DeviceProfileResponseDTO {
	
	@ApiModelProperty(position = 0)     
    private String phoneNumber;
    
	@ApiModelProperty(position = 1)   
    private String gcmIdentifier;
    
	@ApiModelProperty(position = 2)    
    private String mobileOS;
    
	@ApiModelProperty(position = 3) 
    private String mobileQRCode;
    
	@ApiModelProperty(position = 4) 
    private Collection<UserResponseDTO> userCollection;

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

	public Collection<UserResponseDTO> getUserCollection() {
		return userCollection;
	}

	public void setUserCollection(Collection<UserResponseDTO> userCollection) {
		this.userCollection = userCollection;
	}
	
	

}
