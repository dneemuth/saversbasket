package com.sb.web.service.dto;

import javax.validation.constraints.NotEmpty;

public class ContributePriceDTO {
	
	
	@NotEmpty(message="Enter product name.")
	private String productName;
	
	//@Min(value=1,message = "Enter a valid business.")
	private Long idBusiness;	
	
	//@NotEmpty(message="Enter product creation time.")
	private String productDateCreated;
		
	//@NotEmpty(message="Enter product price noted time.")
	private String productPriceNoted;

	private Integer idProduct;
	
	private String encryptedProductKey;
	
	@NotEmpty(message="Enter valid price.")
	private String price;
	
	@NotEmpty(message="Enter new valid price.")
	private String contributedPrice;
	
	private Integer idUser;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Long getIdBusiness() {
		return idBusiness;
	}

	public void setIdBusiness(Long idBusiness) {
		this.idBusiness = idBusiness;
	}

	public String getProductDateCreated() {
		return productDateCreated;
	}

	public void setProductDateCreated(String productDateCreated) {
		this.productDateCreated = productDateCreated;
	}

	public Integer getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(Integer idProduct) {
		this.idProduct = idProduct;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getContributedPrice() {
		return contributedPrice;
	}

	public void setContributedPrice(String contributedPrice) {
		this.contributedPrice = contributedPrice;
	}

	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public String getProductPriceNoted() {
		return productPriceNoted;
	}

	public void setProductPriceNoted(String productPriceNoted) {
		this.productPriceNoted = productPriceNoted;
	}

	public String getEncryptedProductKey() {
		return encryptedProductKey;
	}

	public void setEncryptedProductKey(String encryptedProductKey) {
		this.encryptedProductKey = encryptedProductKey;
	}
	

}
