package com.sb.web.receipt;

import java.util.List;

import com.sb.web.service.dto.ProcessedReceiptDTO;

public interface TranscriptionStrategy {
	
	public ProcessedReceiptDTO transcriptReceipt(List<String> receiptLines);

}
