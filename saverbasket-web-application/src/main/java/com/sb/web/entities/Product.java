/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sb.web.entities;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

import com.sb.web.entities.audit.Auditable;
import com.sb.web.entities.converters.BooleanToStringConverter;
import com.sb.web.enumerations.ProductEntrySource;
import com.sb.web.enumerations.ProductStatus;

/**
 *
 * @author dilneemuth
 */
@Entity(name="product")
public class Product  extends Auditable<Integer> implements Serializable {

    private static final long serialVersionUID = 1L;
   
    @Id
    @Basic(optional = false)
    @Column(name = "idProduct")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_generator")
    @SequenceGenerator(name = "product_generator", sequenceName = "product_seq", allocationSize = 1)
    private Integer idProduct;    
    
    @Column(name = "dateProductApproved")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateProductApproved;
    
    @Size(max = 100)
    @Column(name = "skuIdentifier")
    private String skuIdentifier;
    
    @Column(name = "productInStock")
    private Integer productInStock; 
      
    @Size(max = 260)
    @Column(name = "productKey")
    private String productKey;
    
    @Enumerated(EnumType.ORDINAL)
    private ProductStatus productStatus;    
    
    @Size(max = 200)
    @Column(name = "productStatusComment")
    private String productStatusComment;
    
    @Size(max = 15)
    @Column(name = "productBarCode")
    private String productBarCode;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProduct")
    private List<Price> priceList;
    
    @JoinColumn(name = "idBusiness", referencedColumnName = "idBusiness")
    @ManyToOne(optional = false)
    private Business idBusiness;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProduct", orphanRemoval = true)
    private List<ProductAttribute> productAttributeList;
    
    @OneToMany(mappedBy = "idImpression", cascade =  CascadeType.ALL)
    private List<Impression> impressions;
    
    @OneToMany(mappedBy = "idWatcher", cascade =  CascadeType.ALL)
    private List<Watcher> watchers;
    
    @OneToMany(mappedBy = "idUserProductAction", cascade = CascadeType.ALL) 
    private List<UserProductAction> userProductActions;
     
    @Column(name = "sponsored")
    @Convert(converter=BooleanToStringConverter.class)
    private Boolean sponsored;
    
    @Column(name = "duplicate")
    @Convert(converter=BooleanToStringConverter.class)
    private Boolean duplicate;
    
    @Column(name = "dateProductAdded")
    @Temporal(TIMESTAMP)
    protected Date dateProductAdded;
    
    @Embedded
    private GeoLocationTag geoLocationTag;
    
    @Enumerated(EnumType.ORDINAL)
    private ProductEntrySource productEntrySource;

    public Product() {
    }

	public List<Watcher> getWatchers() {
		return watchers;
	}

	public void setWatchers(List<Watcher> watchers) {
		this.watchers = watchers;
	}

	public List<Impression> getImpressions() {
		return impressions;
	}

	public void setImpressions(List<Impression> impressions) {
		this.impressions = impressions;
	}

	public Integer getProductInStock() {
		return productInStock;
	}

	public void setProductInStock(Integer productInStock) {
		this.productInStock = productInStock;
	}

	public String getProductKey() {
		return productKey;
	}

	public void setProductKey(String productKey) {
		this.productKey = productKey;
	}

	public Product(Integer idProduct) {
        this.idProduct = idProduct;
    }

    public Integer getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }


	public String getSkuIdentifier() {
		return skuIdentifier;
	}

	public void setSkuIdentifier(String skuIdentifier) {
		this.skuIdentifier = skuIdentifier;
	}
   
    public List<Price> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<Price> priceList) {
        this.priceList = priceList;
    }

    public Business getIdBusiness() {
        return idBusiness;
    }

    public void setIdBusiness(Business idBusiness) {
        this.idBusiness = idBusiness;
    }

    public ProductStatus getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(ProductStatus productStatus) {
		this.productStatus = productStatus;
	}

	public Date getDateProductApproved() {
		return dateProductApproved;
	}

	public void setDateProductApproved(Date dateProductApproved) {
		this.dateProductApproved = dateProductApproved;
	}

	public Boolean getSponsored() {
		return sponsored;
	}

	public void setSponsored(Boolean sponsored) {
		this.sponsored = sponsored;
	}

	public String getProductStatusComment() {
		return productStatusComment;
	}

	public void setProductStatusComment(String productStatusComment) {
		this.productStatusComment = productStatusComment;
	}

	public List<UserProductAction> getUserProductActions() {
		return userProductActions;
	}

	public void setUserProductActions(List<UserProductAction> userProductActions) {
		this.userProductActions = userProductActions;
	}

	public List<ProductAttribute> getProductAttributeList() {
        return productAttributeList;
    }

    public void setProductAttributeList(List<ProductAttribute> productAttributeList) {
        this.productAttributeList = productAttributeList;
    }
    
    public Date getDateProductAdded() {
		return dateProductAdded;
	}

	public void setDateProductAdded(Date dateProductAdded) {
		this.dateProductAdded = dateProductAdded;
	}

	public Boolean getDuplicate() {
		return duplicate;
	}

	public void setDuplicate(Boolean duplicate) {
		this.duplicate = duplicate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getProductBarCode() {
		return productBarCode;
	}

	public void setProductBarCode(String productBarCode) {
		this.productBarCode = productBarCode;
	}
	
	public GeoLocationTag getGeoLocationTag() {
		return geoLocationTag;
	}

	public void setGeoLocationTag(GeoLocationTag geoLocationTag) {
		this.geoLocationTag = geoLocationTag;
	}
	
	public ProductEntrySource getProductEntrySource() {
		return productEntrySource;
	}

	public void setProductEntrySource(ProductEntrySource productEntrySource) {
		this.productEntrySource = productEntrySource;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idProduct != null ? idProduct.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.idProduct == null && other.idProduct != null) || (this.idProduct != null && !this.idProduct.equals(other.idProduct))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.mavenproject7.Product[ idProduct=" + idProduct + " ]";
    }
    
}
