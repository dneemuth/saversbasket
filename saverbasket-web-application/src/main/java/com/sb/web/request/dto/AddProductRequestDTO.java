package com.sb.web.request.dto;

import java.math.BigDecimal;

public class AddProductRequestDTO {
	
	private String productName;
	private String productDescription;
	
	//SKU details
	private Integer productType;
	private Integer productSubType;
	private Integer quantity;
	private Integer brand;
	private Integer measure;
	
	//link retailer
	private Integer retailer;
	
	//price
	private BigDecimal price;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public Integer getProductSubType() {
		return productSubType;
	}

	public void setProductSubType(Integer productSubType) {
		this.productSubType = productSubType;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getBrand() {
		return brand;
	}

	public void setBrand(Integer brand) {
		this.brand = brand;
	}

	public Integer getMeasure() {
		return measure;
	}

	public void setMeasure(Integer measure) {
		this.measure = measure;
	}

	public Integer getRetailer() {
		return retailer;
	}

	public void setRetailer(Integer retailer) {
		this.retailer = retailer;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	
	
	
}
