package com.sb.web.service.dto;

import java.util.List;

public class ReceiptTicketDTO {
	
	private List<ReceiptLineDTO> receiptList;
	
	private Integer linkedBusinessId;
	private String dateproductUploaded;

	public List<ReceiptLineDTO> getReceiptList() {
		return receiptList;
	}

	public void setReceiptList(List<ReceiptLineDTO> receiptList) {
		this.receiptList = receiptList;
	}

	public Integer getLinkedBusinessId() {
		return linkedBusinessId;
	}

	public void setLinkedBusinessId(Integer linkedBusinessId) {
		this.linkedBusinessId = linkedBusinessId;
	}

	public String getDateproductUploaded() {
		return dateproductUploaded;
	}

	public void setDateproductUploaded(String dateproductUploaded) {
		this.dateproductUploaded = dateproductUploaded;
	}
	
	
	

}
