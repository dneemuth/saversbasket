package com.sb.web.response.dto;

public class DashboardResponseDTO {
	
	private Integer numberOfUsersRegistered;
	private Integer numberOfBusinessRegistered;
	private Integer numberOfProductsUploaded;
	private Integer numberOfRegisteredNewsletter;

	public Integer getNumberOfUsersRegistered() {
		return numberOfUsersRegistered;
	}

	public void setNumberOfUsersRegistered(Integer numberOfUsersRegistered) {
		this.numberOfUsersRegistered = numberOfUsersRegistered;
	}

	public Integer getNumberOfBusinessRegistered() {
		return numberOfBusinessRegistered;
	}

	public void setNumberOfBusinessRegistered(Integer numberOfBusinessRegistered) {
		this.numberOfBusinessRegistered = numberOfBusinessRegistered;
	}

	public Integer getNumberOfProductsUploaded() {
		return numberOfProductsUploaded;
	}

	public void setNumberOfProductsUploaded(Integer numberOfProductsUploaded) {
		this.numberOfProductsUploaded = numberOfProductsUploaded;
	}

	public Integer getNumberOfRegisteredNewsletter() {
		return numberOfRegisteredNewsletter;
	}

	public void setNumberOfRegisteredNewsletter(Integer numberOfRegisteredNewsletter) {
		this.numberOfRegisteredNewsletter = numberOfRegisteredNewsletter;
	}	
	
	
	
	
}
