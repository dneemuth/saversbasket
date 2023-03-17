/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sb.web.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

import com.sb.web.entities.audit.Auditable;
import com.sb.web.entities.converters.BooleanToStringConverter;
import com.sb.web.enumerations.BusinessCategory;
import com.sb.web.enumerations.BusinessStatus;

/**
 *
 * @author dilneemuth
 */
@Entity(name="business")
public class Business extends Auditable<Integer> implements Serializable {

    private static final long serialVersionUID = 1L;
      
    @Id   
    @Column(name = "idBusiness")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "business_generator")
    @SequenceGenerator(name = "business_generator", sequenceName = "business_seq", initialValue = 1, allocationSize = 1)
    private Integer idBusiness;
    
    @Size(max = 100)
    @Column(name = "registeredName")
    private String registeredName;
    
    @Size(max = 200)
    @Column(name = "businessLogoUrl")
    private String businessLogoUrl;
    
    @Enumerated(EnumType.ORDINAL)
    private BusinessCategory businessCategory;
    
    @Size(max = 100)
    @Column(name = "businessQRCode")
    private String businessQRCode;   
    
    @Enumerated(EnumType.ORDINAL)
    private BusinessStatus businessStatus;    
       
    @Size(max = 50)
    @Column(name = "businessRegistrationNumber")
    private String businessRegistrationNumber;
    
    @Column(name = "businessPercentageDiscount")
    private BigDecimal businessPercentageDiscount;    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idBusiness")
    @ElementCollection(fetch = FetchType.LAZY)
    private List<Product> productList;
    
    @ManyToOne(optional = true,cascade = CascadeType.ALL)
    @JoinColumn(name = "idAccount")
    private Account account;
    
    @Column(name = "dateBusinessCreated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateBusinessCreated;
    
    @Column(name = "businessCreatedBy")
    private Integer businessCreatedBy;
  
    @OneToMany(mappedBy = "business",cascade = CascadeType.ALL)   
    private List<BusinessAddress> businessAddresses;
    
    @OneToMany(mappedBy = "business", cascade =  CascadeType.ALL)
    private List<BusinessRating> businessRatings;    
    
    @OneToMany(mappedBy = "business",cascade = CascadeType.ALL)   
    private List<Follower> followers;
    
    @Convert(converter=BooleanToStringConverter.class)
    private Boolean sponsored; 
    
    @Convert(converter=BooleanToStringConverter.class)
    private Boolean verified; 
    
    @Convert(converter=BooleanToStringConverter.class)
    private Boolean partOfSvbNetwork;
    
    @Embedded
    private GeoLocationTag geoLocationTag;
    

    public Business() {
    }

    public List<BusinessAddress> getBusinessAddresses() {
		return businessAddresses;
	}


	public void setBusinessAddresses(List<BusinessAddress> businessAddresses) {
		this.businessAddresses = businessAddresses;
	}


	public BigDecimal getBusinessPercentageDiscount() {
		return businessPercentageDiscount;
	}

	public void setBusinessPercentageDiscount(BigDecimal businessPercentageDiscount) {
		this.businessPercentageDiscount = businessPercentageDiscount;
	}

	public Integer getIdBusiness() {
		return idBusiness;
	}

	public void setIdBusiness(Integer idBusiness) {
		this.idBusiness = idBusiness;
	}

	public String getRegisteredName() {
        return registeredName;
    }

    public void setRegisteredName(String registeredName) {
        this.registeredName = registeredName;
    }

    public BusinessCategory getBusinessCategory() {
		return businessCategory;
	}

	public void setBusinessCategory(BusinessCategory businessCategory) {
		this.businessCategory = businessCategory;
	}

	public String getBusinessQRCode() {
        return businessQRCode;
    }

    public void setBusinessQRCode(String businessQRCode) {
        this.businessQRCode = businessQRCode;
    }


    public String getBusinessLogoUrl() {
		return businessLogoUrl;
	}

	public void setBusinessLogoUrl(String businessLogoUrl) {
		this.businessLogoUrl = businessLogoUrl;
	}

	public BusinessStatus getBusinessStatus() {
		return businessStatus;
	}

	public void setBusinessStatus(BusinessStatus businessStatus) {
		this.businessStatus = businessStatus;
	}
	  

    public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

	public List<Follower> getFollowers() {
		return followers;
	}

	public void setFollowers(List<Follower> followers) {
		this.followers = followers;
	}

	public Boolean getSponsored() {
		return sponsored;
	}

	public void setSponsored(Boolean sponsored) {
		this.sponsored = sponsored;
	}

	@XmlTransient
    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }


    public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}


	public Date getDateBusinessCreated() {
		return dateBusinessCreated;
	}

	public void setDateBusinessCreated(Date dateBusinessCreated) {
		this.dateBusinessCreated = dateBusinessCreated;
	}

	public Integer getBusinessCreatedBy() {
		return businessCreatedBy;
	}

	public void setBusinessCreatedBy(Integer businessCreatedBy) {
		this.businessCreatedBy = businessCreatedBy;
	}
	
	
	public String getBusinessRegistrationNumber() {
		return businessRegistrationNumber;
	}

	public void setBusinessRegistrationNumber(String businessRegistrationNumber) {
		this.businessRegistrationNumber = businessRegistrationNumber;
	}
	
	public GeoLocationTag getGeoLocationTag() {
		return geoLocationTag;
	}

	public void setGeoLocationTag(GeoLocationTag geoLocationTag) {
		this.geoLocationTag = geoLocationTag;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idBusiness != null ? idBusiness.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Business)) {
            return false;
        }
        Business other = (Business) object;
        if ((this.idBusiness == null && other.idBusiness != null) || (this.idBusiness != null && !this.idBusiness.equals(other.idBusiness))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.mavenproject7.Business[ idBusiness=" + idBusiness + " ]";
    }

	public List<BusinessRating> getBusinessRatings() {
		return businessRatings;
	}

	public void setBusinessRatings(List<BusinessRating> businessRatings) {
		this.businessRatings = businessRatings;
	}

	public Boolean getPartOfSvbNetwork() {
		return partOfSvbNetwork;
	}

	public void setPartOfSvbNetwork(Boolean partOfSvbNetwork) {
		this.partOfSvbNetwork = partOfSvbNetwork;
	}   
    
    
}
