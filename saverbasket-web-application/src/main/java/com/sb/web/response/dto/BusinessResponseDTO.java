package com.sb.web.response.dto;

import java.util.Collection;

import io.swagger.annotations.ApiModelProperty;

public class BusinessResponseDTO {
	
	@ApiModelProperty(position = 0) 
	private String registeredName;
	
	@ApiModelProperty(position = 1)
    private String longitude;
  
	@ApiModelProperty(position = 2)   
    private String latitude;
    
	@ApiModelProperty(position = 3) 
    private Integer radius;
    
	@ApiModelProperty(position = 4) 
    private Integer status;
	      
	@ApiModelProperty(position = 5) 
	private Collection<CampaignResponseDTO> campaignCollection;
	    
	@ApiModelProperty(position = 6) 
	private BusinessActivityResponseDTO businessActivity;
	    
	@ApiModelProperty(position = 7) 
	private UserResponseDTO idUser;

	public String getRegisteredName() {
		return registeredName;
	}

	public void setRegisteredName(String registeredName) {
		this.registeredName = registeredName;
	}

	

	public BusinessActivityResponseDTO getBusinessActivity() {
		return businessActivity;
	}

	public void setBusinessActivity(BusinessActivityResponseDTO businessActivity) {
		this.businessActivity = businessActivity;
	}

	public UserResponseDTO getIdUser() {
		return idUser;
	}

	public void setIdUser(UserResponseDTO idUser) {
		this.idUser = idUser;
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

	public Collection<CampaignResponseDTO> getCampaignCollection() {
		return campaignCollection;
	}

	public void setCampaignCollection(Collection<CampaignResponseDTO> campaignCollection) {
		this.campaignCollection = campaignCollection;
	}

	
	
}
