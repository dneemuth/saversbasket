package com.sb.web.response.dto;

import java.util.List;

import com.sb.web.service.dto.ProductDTO;

public class SearchBasketResponseDTO {
	
	private List<ProductDTO> products;
	private Integer numberOfPages;
	private Long numberOfTotalElements;
	private Integer maxTotalElementsPerPage;
	
	//execution time in MS
	private long executionTimeElapsed;
	
	private boolean userAnonymous;

	public List<ProductDTO> getProducts() {
		return products;
	}

	public void setProducts(List<ProductDTO> products) {
		this.products = products;
	}

	public boolean isUserAnonymous() {
		return userAnonymous;
	}

	public void setUserAnonymous(boolean userAnonymous) {
		this.userAnonymous = userAnonymous;
	}

	public Integer getNumberOfPages() {
		return numberOfPages;
	}

	public void setNumberOfPages(Integer numberOfPages) {
		this.numberOfPages = numberOfPages;
	}

	public Long getNumberOfTotalElements() {
		return numberOfTotalElements;
	}

	public void setNumberOfTotalElements(Long numberOfTotalElements) {
		this.numberOfTotalElements = numberOfTotalElements;
	}

	public Integer getMaxTotalElementsPerPage() {
		return maxTotalElementsPerPage;
	}

	public void setMaxTotalElementsPerPage(Integer maxTotalElementsPerPage) {
		this.maxTotalElementsPerPage = maxTotalElementsPerPage;
	}

	public long getExecutionTimeElapsed() {
		return executionTimeElapsed;
	}

	public void setExecutionTimeElapsed(long executionTimeElapsed) {
		this.executionTimeElapsed = executionTimeElapsed;
	}
	
}
