package com.sb.web.response.dto;

import java.math.BigDecimal;
import java.util.List;

import com.sb.web.service.dto.ProductDTO;

public class RetailerBasketResponse {
	
	private String retailerName;
	private List<ProductDTO> productsTracked;
	private List<ProductDTO> suggestedProductsFromStore;
	private BigDecimal totalBasketPricePerRetailer;	
	private BigDecimal totalSuggestedBasketPricePerRetailer;
	private Integer totalProductItemsInBasket;
	
	private String businessLogoUrl;	
	private String businessName;
	
	//predicted savings
	private BigDecimal predictedSavings;
	
	
	public BigDecimal calculateBasketPriceForFoundProducts() {
		BigDecimal basketPrice = new BigDecimal(0);		
		
		if (productsTracked != null && productsTracked.size() > 0) {
			for (ProductDTO product : productsTracked) {	
				  if (product != null && product.getUpdatedprice() !=null && product.getQuantity()!= null && product.getQuantity().intValue() > 0) {
					  basketPrice = basketPrice.add(product.getUpdatedprice().multiply(new BigDecimal(product.getQuantity())));
				  } 
				}
		}
		return basketPrice;
	}
	
	public BigDecimal calculateBasketPriceForSuggestedProducts() {
		BigDecimal basketSuggestedPrice = new BigDecimal(0);		
		
		if (suggestedProductsFromStore != null && suggestedProductsFromStore.size() > 0) {
			for (ProductDTO suggestedProduct : suggestedProductsFromStore) {	
				  if (suggestedProduct != null && suggestedProduct.getUpdatedprice() !=null 
						  && suggestedProduct.getQuantity()!= null && suggestedProduct.getQuantity().intValue() > 0) {
					  basketSuggestedPrice = basketSuggestedPrice.add(suggestedProduct.getUpdatedprice().multiply(new BigDecimal(suggestedProduct.getQuantity())));
				  } 
				}
		}
		return basketSuggestedPrice;
	}
	
	
	public String getBusinessLogoUrl() {
		return businessLogoUrl;
	}
	
	public void setBusinessLogoUrl(String businessLogoUrl) {
		this.businessLogoUrl = businessLogoUrl;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getRetailerName() {
		return retailerName;
	}
	
	public void setRetailerName(String retailerName) {
		this.retailerName = retailerName;
	}
	
	public List<ProductDTO> getProductsTracked() {
		return productsTracked;
	}
	public void setProductsTracked(List<ProductDTO> productsTracked) {
		this.productsTracked = productsTracked;
	}


	public BigDecimal getTotalSuggestedBasketPricePerRetailer() {
		totalSuggestedBasketPricePerRetailer = calculateBasketPriceForSuggestedProducts();
		return totalSuggestedBasketPricePerRetailer;
	}

	public void setTotalSuggestedBasketPricePerRetailer(BigDecimal totalSuggestedBasketPricePerRetailer) {
		this.totalSuggestedBasketPricePerRetailer = totalSuggestedBasketPricePerRetailer;
	}

	public BigDecimal getTotalBasketPricePerRetailer() {
		totalBasketPricePerRetailer = calculateBasketPriceForFoundProducts();		
		return totalBasketPricePerRetailer;
	}


	public void setTotalBasketPricePerRetailer(BigDecimal totalBasketPricePerRetailer) {
		this.totalBasketPricePerRetailer = totalBasketPricePerRetailer;
	}


	public List<ProductDTO> getSuggestedProductsFromStore() {
		return suggestedProductsFromStore;
	}


	public void setSuggestedProductsFromStore(List<ProductDTO> suggestedProductsFromStore) {
		this.suggestedProductsFromStore = suggestedProductsFromStore;
	}

	public Integer getTotalProductItemsInBasket() {
		if (productsTracked != null &&productsTracked.size() > 0) {
			this.totalProductItemsInBasket = productsTracked.size();
		} else {
			this.totalProductItemsInBasket = 0;
		}		
		return totalProductItemsInBasket;
	}

	public void setTotalProductItemsInBasket(Integer totalProductItemsInBasket) {
		this.totalProductItemsInBasket = totalProductItemsInBasket;
	}

	public BigDecimal getPredictedSavings() {
		return predictedSavings;
	}

	public void setPredictedSavings(BigDecimal predictedSavings) {
		this.predictedSavings = predictedSavings;
	}
	
}
