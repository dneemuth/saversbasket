package com.sb.web.response.dto;

import java.math.BigDecimal;
import java.util.Map;

public class AddProductResponseDTO {
	
	private Integer numberProductsInBasket = 0;
	private BigDecimal totalPrice;
	
	private boolean validated;
	private boolean productAlreadyPresent;
    private Map<String, String> errorMessages;
    
    private boolean isUserAuthenticated;

    private BigDecimal monetaryReward;

	public Integer getNumberProductsInBasket() {
		return numberProductsInBasket;
	}

	public void setNumberProductsInBasket(Integer numberProductsInBasket) {
		this.numberProductsInBasket = numberProductsInBasket;
	}

	public boolean isValidated() {
		return validated;
	}

	public void setValidated(boolean validated) {
		this.validated = validated;
	}	

	public Map<String, String> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(Map<String, String> errorMessages) {
		this.errorMessages = errorMessages;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public boolean isUserAuthenticated() {
		return isUserAuthenticated;
	}

	public void setUserAuthenticated(boolean isUserAuthenticated) {
		this.isUserAuthenticated = isUserAuthenticated;
	}

	public BigDecimal getMonetaryReward() {
		return monetaryReward;
	}

	public void setMonetaryReward(BigDecimal monetaryReward) {
		this.monetaryReward = monetaryReward;
	}

	public boolean isProductAlreadyPresent() {
		return productAlreadyPresent;
	}

	public void setProductAlreadyPresent(boolean productAlreadyPresent) {
		this.productAlreadyPresent = productAlreadyPresent;
	}	

}
