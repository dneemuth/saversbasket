package com.sb.web.receipt;

import java.util.List;

import com.sb.web.service.dto.ProcessedReceiptDTO;

public class TranscriptionContext {
	
	  private TranscriptionStrategy transcriptionStrategy;

	  //Client will set what CompressionStrategy to use by calling this method
	  public void setTranscriptionStrategy( TranscriptionStrategy transcriptionStrategy )
	  {
	  this.transcriptionStrategy = transcriptionStrategy;
	  }

	  public ProcessedReceiptDTO createTranscription(List<String> receiptLines)
	  {
		   return transcriptionStrategy.transcriptReceipt(receiptLines);
	  }
	  
	  /**
	  ArrayList<File> fileList = new ArrayList<File>();
	  fileList.add(new File("D:\\Javafiles\\Javadoc.txt"));
	  fileList.add(new File("D:\\Javafiles\\Release.txt"));

	  CompressionContext ctx = null;

	  ctx = new CompressionContext();
	  ctx.setCompressionStrategy(new ZipCompressionStrategy());
	  ctx.createArchive(fileList,"Javafiles");

	  ctx = new CompressionContext();
	  ctx.setCompressionStrategy(new RarCompressionStrategy());
	  ctx.createArchive(fileList,"Javafiles");
	  */

}
