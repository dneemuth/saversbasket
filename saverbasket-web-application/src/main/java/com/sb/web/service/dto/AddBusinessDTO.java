package com.sb.web.service.dto;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

public class AddBusinessDTO {

	private MultipartFile file;

	@NotEmpty(message = "Enter business name.")
	private String businessName;

	// @NotEmpty(message="Enter business category.")
	private Integer idBusinessCategory;

	/**
	 * Address details
	 */
	@NotEmpty(message = "Enter address.")
	private String address1;

	@NotEmpty(message = "Enter district.")
	private String idDistrict;

	// @NotEmpty(message="Enter phone number.")
	private String phoneNumber;

	private String email;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public Integer getIdBusinessCategory() {
		return idBusinessCategory;
	}

	public void setIdBusinessCategory(Integer idBusinessCategory) {
		this.idBusinessCategory = idBusinessCategory;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getIdDistrict() {
		return idDistrict;
	}

	public void setIdDistrict(String idDistrict) {
		this.idDistrict = idDistrict;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
