package com.sb.web.service.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

public class AddProductDTO {	
	
	private MultipartFile file;
	
	@NotEmpty(message="Enter product name.")
	private String productName;
	
	//@NotEmpty(message="Enter product description.")
	//private String productDescription;
	
	@Min(value=1,message = "Enter a valid business.")
	private Integer idBusiness;
	
	
	//@NotEmpty(message="Enter product creation time.")
	private String productDateCreated;
	
	//private Integer quantityInStock;
	
	@Min(value=1,message = "Enter a valid product type.")
	private Integer idProductType;
		
	@NotEmpty(message="Enter valid price.")
	private String price;

	private Integer priceDaysValidity;
	
	private Integer idUser;
	
	private String uploadedPictureUrl;	
	
	@NotEmpty(message="Enter valid product barcode.")
	private String productBarcode;
	
	private String modeOfEntry;
	
	private String pictureUrlFromScanning;
	
	public String getProductDateCreated() {
		return productDateCreated;
	}

	public void setProductDateCreated(String productDateCreated) {
		this.productDateCreated = productDateCreated;
	}

	public Integer getIdBusiness() {
		return idBusiness;
	}

	public void setIdBusiness(Integer idBusiness) {
		this.idBusiness = idBusiness;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	public Integer getIdProductType() {
		return idProductType;
	}

	public void setIdProductType(Integer idProductType) {
		this.idProductType = idProductType;
	}

	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public String getUploadedPictureUrl() {
		return uploadedPictureUrl;
	}

	public void setUploadedPictureUrl(String uploadedPictureUrl) {
		this.uploadedPictureUrl = uploadedPictureUrl;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public Integer getPriceDaysValidity() {
		return priceDaysValidity;
	}

	public void setPriceDaysValidity(Integer priceDaysValidity) {
		this.priceDaysValidity = priceDaysValidity;
	}

	public String getProductBarcode() {
		return productBarcode;
	}

	public void setProductBarcode(String productBarcode) {
		this.productBarcode = productBarcode;
	}

	public String getModeOfEntry() {
		return modeOfEntry;
	}

	public void setModeOfEntry(String modeOfEntry) {
		this.modeOfEntry = modeOfEntry;
	}

	public String getPictureUrlFromScanning() {
		return pictureUrlFromScanning;
	}

	public void setPictureUrlFromScanning(String pictureUrlFromScanning) {
		this.pictureUrlFromScanning = pictureUrlFromScanning;
	}
	
}
