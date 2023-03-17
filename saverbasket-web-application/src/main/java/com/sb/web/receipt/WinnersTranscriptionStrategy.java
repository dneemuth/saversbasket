package com.sb.web.receipt;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;

import com.sb.web.service.dto.ProcessedReceiptDTO;
import com.sb.web.service.dto.ProcessedReceiptLineDTO;

public class WinnersTranscriptionStrategy implements TranscriptionStrategy {
	
	
	public static boolean isNumeric(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        double d = Double.parseDouble(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}

	@Override
	public ProcessedReceiptDTO transcriptReceipt(List<String> receiptLines) {
		ProcessedReceiptDTO processedReceiptDTO = new ProcessedReceiptDTO();
		
		List<ProcessedReceiptLineDTO> processedReceiptLinesprocessedReceiptLines = new ArrayList<ProcessedReceiptLineDTO>();
		for (String receiptLine : receiptLines) {
			ProcessedReceiptLineDTO processedReceiptLineDTO = null;
			
			if (receiptLine.contains("TOTAL")) {
		    	  break;
		     }
			
			StringTokenizer tokenizer = new StringTokenizer(receiptLine, " ");
			boolean stopTranscription = false;
		    while (tokenizer.hasMoreElements() && !stopTranscription) {
		       String receiptToken = tokenizer.nextToken();
		       
		       if (textContainsPrice(receiptToken) && isNumeric(receiptToken)) {	
		    	   
		    	   processedReceiptLineDTO = new ProcessedReceiptLineDTO();
		    	   BigDecimal price = new BigDecimal(receiptToken);
		    	   
		    	   // check position of price
		    	   int lastPositionPrice = receiptLine.lastIndexOf(receiptToken);
		    	   String extractedDescription = receiptLine.substring(2, lastPositionPrice).trim();
		    	   
		    	   processedReceiptLineDTO.setPrice(price);
		    	   processedReceiptLineDTO.setDescription(extractedDescription);
				}
		    }
		    
		    if (processedReceiptDTO != null 
		    		&& processedReceiptLineDTO.getPrice() != null 
		    			&& processedReceiptLineDTO.getDescription() != null) {
		    	 processedReceiptLinesprocessedReceiptLines.add(processedReceiptLineDTO);
		    }
		    
		}	
		processedReceiptDTO.setProcessedReceiptLines(processedReceiptLinesprocessedReceiptLines);
		
		return processedReceiptDTO;
	}
	
	private boolean textContainsPrice(String textToProcess) {
		if (textToProcess.contains(".")) {
			return true;
		}
		return false;
	}

}
