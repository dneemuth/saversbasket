/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sb.web.service.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.sb.web.enumerations.ProductEntrySource;

/**
 *
 * @author dilneemuth
 */

public class ProductDTO    {
   
	private Integer idProduct;	
	
	private String encryptedProductKey;
	
	private String productName;
	
	private String productBarCode;
	
	private Integer idBasketItem;
	
	private Integer linkedIdBusiness;
	
	private Date dateProductAdded;
	
	private String productDescription;
	
	private Integer productInStock;
	
	private BigDecimal updatedprice;
	
	private String skuIdentifier;
	
	private Integer filterLevel;
	
	private Integer quantity =  new Integer(0);
	
	private String productKey;
	
	private String imageUrl;
	
	private String businessLogoUrl;
	
	private String businessName;
	
	/*
	 * Keep track of user ids
	 */
	private Integer idUser;
	
	private String username;
	
	/**
	 * Date price added
	 */
	private Date datePriceNoted;
	
	
	/**
	 * Date price End
	 */
	private Date datePriceEnd;
	
	
	/**
	 * currency code
	 */
	private String currencyCode;
	
	
	/**
	 * Flag to indicated that product is already added to basket
	 */
	private boolean addedToBasket;
	
	/**
	 * Flag to indicate that price is much lower (displayed in GREEN)
	 */
	private boolean isLowPrice;
	
	
	/**
	 * Flag for confidence level
	 */
	private BigDecimal relevance;
	
	/**
	 * Product picture
	 */
	private String productPictureUrl;
	
	/**
	 * Difference between last price and previous price.
	 */
	private BigDecimal percentageDifferencePrice;
	
	
	/**
	 * SKU Identifiers
	 */
	private String type;
	private String subType;
	private String brand;
	private String amount;
	private String measure;
	
	/**
	 * product info source
	 */
	private ProductEntrySource productEntrySource;
	
	
	/**
	 * Flag to indicate that product already exists in svb database
	 */
	private boolean productPresentInDatabase;
	
	/**
	 * Load same products with same barcode
	 */
	private List<ProductDTO> sameProducts;
	
	
	public boolean isLowPrice() {
		return isLowPrice;
	}


	public void setLowPrice(boolean isLowPrice) {
		this.isLowPrice = isLowPrice;
	}


	

	public Integer getLinkedIdBusiness() {
		return linkedIdBusiness;
	}


	public void setLinkedIdBusiness(Integer linkedIdBusiness) {
		this.linkedIdBusiness = linkedIdBusiness;
	}


	public String getBusinessName() {
		return businessName;
	}


	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}


	public String getBusinessLogoUrl() {
		return businessLogoUrl;
	}


	public void setBusinessLogoUrl(String businessLogoUrl) {
		this.businessLogoUrl = businessLogoUrl;
	}


	public String getProductKey() {
		return productKey;
	}


	public void setProductKey(String productKey) {
		this.productKey = productKey;
	}


	public Integer getFilterLevel() {
		return filterLevel;
	}


	public void setFilterLevel(Integer filterLevel) {
		this.filterLevel = filterLevel;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getSubType() {
		return subType;
	}


	public void setSubType(String subType) {
		this.subType = subType;
	}


	public String getBrand() {
		return brand;
	}


	public void setBrand(String brand) {
		this.brand = brand;
	}



	public String getAmount() {
		return amount;
	}


	public void setAmount(String amount) {
		this.amount = amount;
	}


	public String getMeasure() {
		return measure;
	}


	public void setMeasure(String measure) {
		this.measure = measure;
	}


	public String getSkuIdentifier() {
		return skuIdentifier;
	}


	public void setSkuIdentifier(String skuIdentifier) {
		this.skuIdentifier = skuIdentifier;
	}



	public Integer getIdBasketItem() {
		return idBasketItem;
	}


	public void setIdBasketItem(Integer idBasketItem) {
		this.idBasketItem = idBasketItem;
	}


	public Integer getIdProduct() {
		return idProduct;
	}


	public void setIdProduct(Integer idProduct) {
		this.idProduct = idProduct;
	}


	public Date getDateProductAdded() {
		return dateProductAdded;
	}


	public void setDateProductAdded(Date dateProductAdded) {
		this.dateProductAdded = dateProductAdded;
	}


	public String getProductDescription() {
		return productDescription;
	}


	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}


	public Integer getProductInStock() {
		return productInStock;
	}


	public void setProductInStock(Integer productInStock) {
		this.productInStock = productInStock;
	}

	public BigDecimal getUpdatedprice() {
		return updatedprice;
	}

	public void setUpdatedprice(BigDecimal updatedprice) {
		this.updatedprice = updatedprice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public boolean isAddedToBasket() {
		return addedToBasket;
	}
	
	public void setAddedToBasket(boolean addedToBasket) {
		this.addedToBasket = addedToBasket;
	}

	public BigDecimal getRelevance() {
		return relevance;
	}

	public void setRelevance(BigDecimal relevance) {
		this.relevance = relevance;
	}

	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public Date getDatePriceNoted() {
		return datePriceNoted;
	}

	public void setDatePriceNoted(Date datePriceNoted) {
		this.datePriceNoted = datePriceNoted;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}	

	public Date getDatePriceEnd() {
		return datePriceEnd;
	}

	public void setDatePriceEnd(Date datePriceEnd) {
		this.datePriceEnd = datePriceEnd;
	}
		
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}	

	public String getProductBarCode() {
		return productBarCode;
	}

	public void setProductBarCode(String productBarCode) {
		this.productBarCode = productBarCode;
	}	
	
	public String getProductPictureUrl() {
		return productPictureUrl;
	}

	public void setProductPictureUrl(String productPictureUrl) {
		this.productPictureUrl = productPictureUrl;
	}	

	public String getEncryptedProductKey() {
		return encryptedProductKey;
	}

	public void setEncryptedProductKey(String encryptedProductKey) {
		this.encryptedProductKey = encryptedProductKey;
	}	

	public ProductEntrySource getProductEntrySource() {
		return productEntrySource;
	}

	public void setProductEntrySource(ProductEntrySource productEntrySource) {
		this.productEntrySource = productEntrySource;
	}	

	public boolean isProductPresentInDatabase() {
		return productPresentInDatabase;
	}

	public void setProductPresentInDatabase(boolean productPresentInDatabase) {
		this.productPresentInDatabase = productPresentInDatabase;
	}

	public List<ProductDTO> getSameProducts() {
		return sameProducts;
	}

	public void setSameProducts(List<ProductDTO> sameProducts) {
		this.sameProducts = sameProducts;
	}	

	public BigDecimal getPercentageDifferencePrice() {
		return percentageDifferencePrice;
	}

	public void setPercentageDifferencePrice(BigDecimal percentageDifferencePrice) {
		this.percentageDifferencePrice = percentageDifferencePrice;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idProduct == null) ? 0 : idProduct.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductDTO other = (ProductDTO) obj;
		if (idProduct == null) {
			if (other.idProduct != null)
				return false;
		} else if (!idProduct.equals(other.idProduct))
			return false;
		return true;
	}  

}
