/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sb.web.service.dto;

import java.util.Collection;

import javax.persistence.ManyToMany;

/**
 *
 * @author dilneemuth
 */

public class RetailerDTO  {
	
	private Integer idRetailer;
   
    private String registeredName;
   
    private String businessQRCode;
 
    private String longitude;
  
    private String latitude;
    
    private Integer radius;
  
    private Integer status;
    
    private String address;
   
    private String phoneNumber;
   
    private String email;
  
    private String fbPage;
    
    
    private Collection<ProductDTO> productCollection;

	public String getRegisteredName() {
		return registeredName;
	}

	public void setRegisteredName(String registeredName) {
		this.registeredName = registeredName;
	}

	public String getBusinessQRCode() {
		return businessQRCode;
	}

	public void setBusinessQRCode(String businessQRCode) {
		this.businessQRCode = businessQRCode;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getFbPage() {
		return fbPage;
	}

	public void setFbPage(String fbPage) {
		this.fbPage = fbPage;
	}

	public Collection<ProductDTO> getProductCollection() {
		return productCollection;
	}

	public void setProductCollection(Collection<ProductDTO> productCollection) {
		this.productCollection = productCollection;
	}

	public Integer getIdRetailer() {
		return idRetailer;
	}

	public void setIdRetailer(Integer idRetailer) {
		this.idRetailer = idRetailer;
	}

   
    
}
