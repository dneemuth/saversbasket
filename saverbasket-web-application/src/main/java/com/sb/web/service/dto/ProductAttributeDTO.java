/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sb.web.service.dto;

/**
 *
 * @author dilneemuth
 */

public class ProductAttributeDTO  {
  
    private String productMapKey;
    
    private String productMapValue;
   
    private ProductDTO idProduct;

	public String getProductMapKey() {
		return productMapKey;
	}

	public void setProductMapKey(String productMapKey) {
		this.productMapKey = productMapKey;
	}

	public String getProductMapValue() {
		return productMapValue;
	}

	public void setProductMapValue(String productMapValue) {
		this.productMapValue = productMapValue;
	}

	public ProductDTO getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(ProductDTO idProduct) {
		this.idProduct = idProduct;
	}
    
    
    
}
