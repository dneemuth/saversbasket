package com.sb.web.service.dto;

import java.util.List;

public class ProcessedReceiptDTO {
	
	private List<ProcessedReceiptLineDTO> processedReceiptLines;

	public List<ProcessedReceiptLineDTO> getProcessedReceiptLines() {
		return processedReceiptLines;
	}

	public void setProcessedReceiptLines(List<ProcessedReceiptLineDTO> processedReceiptLines) {
		this.processedReceiptLines = processedReceiptLines;
	}
	
	

}
