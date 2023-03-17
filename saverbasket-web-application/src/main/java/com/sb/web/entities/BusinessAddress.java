package com.sb.web.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

@Entity(name="businessaddress")
public class BusinessAddress  extends Address  {

    private static final long serialVersionUID = 1L;
      
    @Size(max = 20)
    @Column(name = "phoneNumber")
    private String phoneNumber;
    
    @Size(max = 20)
    @Column(name = "fax")
    private String fax;
    
    @Size(max = 100)
    @Column(name = "email")
    private String email;
    
    @Size(max = 150)
    @Column(name = "facebookPage")
    private String facebookPage;    
    
    @Size(max = 150)
    @Column(name = "homePage")
    private String homePage;
    
    @Size(max = 200)
    @Column(name = "openingHours")
    private String openingHours;
         
    @ManyToOne(fetch =FetchType.LAZY, cascade =  CascadeType.ALL)
    @JoinColumn(name = "idBusiness", referencedColumnName = "idBusiness")  
    private Business business;   

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFacebookPage() {
		return facebookPage;
	}

	public void setFacebookPage(String facebookPage) {
		this.facebookPage = facebookPage;
	}

	public String getHomePage() {
		return homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

	public String getOpeningHours() {
		return openingHours;
	}

	public void setOpeningHours(String openingHours) {
		this.openingHours = openingHours;
	}    

}
