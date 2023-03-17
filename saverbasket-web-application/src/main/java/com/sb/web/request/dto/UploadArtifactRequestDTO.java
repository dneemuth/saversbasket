package com.sb.web.request.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

public class UploadArtifactRequestDTO {
	
	private MultipartFile file;
	
	@NotEmpty(message="Enter file uploaded date.")
	private String dateFileUploaded;
	
	@Min(value=1,message = "Enter a valid business.")
	private Integer idBusiness;
	
	private String content;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getDateFileUploaded() {
		return dateFileUploaded;
	}

	public void setDateFileUploaded(String dateFileUploaded) {
		this.dateFileUploaded = dateFileUploaded;
	}

	public Integer getIdBusiness() {
		return idBusiness;
	}

	public void setIdBusiness(Integer idBusiness) {
		this.idBusiness = idBusiness;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
	
	

}
