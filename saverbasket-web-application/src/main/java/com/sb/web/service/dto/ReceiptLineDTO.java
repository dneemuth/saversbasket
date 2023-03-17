package com.sb.web.service.dto;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

public class ReceiptLineDTO {
	
	private BigDecimal price;
	private String description;
	private MultipartFile file;
	
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	

}
