/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sb.web.service.dto;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;

/**
 *
 * @author dilneemuth
 */
public class PriceDTO  {

  
	@ApiModelProperty(position = 0) 
    private BigDecimal oldPrice;
	
	@ApiModelProperty(position = 1) 
    private BigDecimal newPrice;
	
	@ApiModelProperty(position = 2) 
    private String currencyCode;
	
	@ApiModelProperty(position = 3) 
    private Long datePriceAdded;
	
	@ApiModelProperty(position = 4) 
    private ProductDTO productidProduct;

    public PriceDTO() {
    }


	public BigDecimal getOldPrice() {
		return oldPrice;
	}

	public void setOldPrice(BigDecimal oldPrice) {
		this.oldPrice = oldPrice;
	}


	public BigDecimal getNewPrice() {
		return newPrice;
	}

	public void setNewPrice(BigDecimal newPrice) {
		this.newPrice = newPrice;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}


	public Long getDatePriceAdded() {
		return datePriceAdded;
	}

	public void setDatePriceAdded(Long datePriceAdded) {
		this.datePriceAdded = datePriceAdded;
	}

	public ProductDTO getProductidProduct() {
		return productidProduct;
	}

	public void setProductidProduct(ProductDTO productidProduct) {
		this.productidProduct = productidProduct;
	}

    
   
}
