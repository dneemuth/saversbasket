package com.sb.web.service.dto;

import java.math.BigDecimal;

import com.opencsv.bean.CsvBindByName;

public class ProcessedReceiptLineDTO {
	
	@CsvBindByName(column = "price")
	private BigDecimal price;
	  
	@CsvBindByName(column = "description")
	private String description;
	
	public ProcessedReceiptLineDTO() {
    }

    public ProcessedReceiptLineDTO(BigDecimal price, String description) {
        this.price = price;
        this.description = description;
     
    }
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
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
		ProcessedReceiptLineDTO other = (ProcessedReceiptLineDTO) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		return true;
	}
	
	
	
	
}
