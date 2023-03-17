package com.sb.web.service.dto;

import java.util.Date;

public class NewsletterDTO {

	private Integer idNewsletter;    
  
    private String email;    
    
    private Date dateNewsletterRegistered; 
  
    private String status;

	public Integer getIdNewsletter() {
		return idNewsletter;
	}

	public void setIdNewsletter(Integer idNewsletter) {
		this.idNewsletter = idNewsletter;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDateNewsletterRegistered() {
		return dateNewsletterRegistered;
	}

	public void setDateNewsletterRegistered(Date dateNewsletterRegistered) {
		this.dateNewsletterRegistered = dateNewsletterRegistered;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
    
    

}
