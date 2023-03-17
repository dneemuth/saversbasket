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

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.sb.web.entities.audit.Auditable;
import com.sb.web.entities.converters.BooleanToStringConverter;

/**
 *
 * @author dilneemuth
 */
@Entity(name="price")
public class Price  extends Auditable<Integer> implements Serializable {

    private static final long serialVersionUID = 1L;
   
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idPrice")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "price_generator")
    @SequenceGenerator(name = "price_generator", sequenceName = "price_seq", allocationSize = 1)
    private long idPrice;
    
    @Column(name = "score")
    private BigDecimal score;
    
    @Column(name = "price")
    private BigDecimal price;
    
    @Size(max = 3)
    @Column(name = "currencyCode")
    private String currencyCode;
    
    @Column(name = "promoPrice")
    @Convert(converter=BooleanToStringConverter.class)
    private Boolean promoPrice;
    
    @Column(name = "vatIncluded")
    @Convert(converter=BooleanToStringConverter.class)
    private Boolean vatIncluded;
    
    @Column(name = "startPriceDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startPriceDate;
    
    @Column(name = "endPriceDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endPriceDate;  
    
    @Column(name = "datePriceTimeNoted")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datePriceTimeNoted;    
    
    @Column(name = "datePriceTimeAdded")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datePriceTimeAdded;  
      
    @JoinColumn(name = "idProduct", referencedColumnName = "idProduct")
    @ManyToOne(optional = false)
    private Product idProduct; 
    
    
    @OneToMany(mappedBy = "price", cascade = CascadeType.ALL) 
    private List<UserPriceAction> userPriceActions;

    public Price() {
    }

    public Price(long idPrice) {
        this.idPrice = idPrice;
    }

    public long getIdPrice() {
		return idPrice;
	}

	public void setIdPrice(long idPrice) {
		this.idPrice = idPrice;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Product getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Product idProduct) {
        this.idProduct = idProduct;
    }

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public List<UserPriceAction> getUserPriceActions() {
		return userPriceActions;
	}

	public void setUserPriceActions(List<UserPriceAction> userPriceActions) {
		this.userPriceActions = userPriceActions;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public boolean isPromoPrice() {
		return promoPrice;
	}

	public void setPromoPrice(boolean promoPrice) {
		this.promoPrice = promoPrice;
	}

	public Date getStartPriceDate() {
		return startPriceDate;
	}

	public void setStartPriceDate(Date startPriceDate) {
		this.startPriceDate = startPriceDate;
	}

	public Date getEndPriceDate() {
		return endPriceDate;
	}

	public void setEndPriceDate(Date endPriceDate) {
		this.endPriceDate = endPriceDate;
	}

	public Boolean getPromoPrice() {
		return promoPrice;
	}

	public void setPromoPrice(Boolean promoPrice) {
		this.promoPrice = promoPrice;
	}

	public Date getDatePriceTimeNoted() {
		return datePriceTimeNoted;
	}

	public void setDatePriceTimeNoted(Date datePriceTimeNoted) {
		this.datePriceTimeNoted = datePriceTimeNoted;
	}

	public Boolean getVatIncluded() {
		return vatIncluded;
	}

	public void setVatIncluded(Boolean vatIncluded) {
		this.vatIncluded = vatIncluded;
	}		

	public Date getDatePriceTimeAdded() {
		return datePriceTimeAdded;
	}

	public void setDatePriceTimeAdded(Date datePriceTimeAdded) {
		this.datePriceTimeAdded = datePriceTimeAdded;
	}

	@Override
    public String toString() {
        return "com.mycompany.mavenproject7.Price[ idPrice=" + idPrice + " ]";
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currencyCode == null) ? 0 : currencyCode.hashCode());
		result = prime * result + ((datePriceTimeNoted == null) ? 0 : datePriceTimeNoted.hashCode());
		result = prime * result + ((endPriceDate == null) ? 0 : endPriceDate.hashCode());
		result = prime * result + (int) (idPrice ^ (idPrice >>> 32));
		result = prime * result + ((idProduct == null) ? 0 : idProduct.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((promoPrice == null) ? 0 : promoPrice.hashCode());
		result = prime * result + ((score == null) ? 0 : score.hashCode());
		result = prime * result + ((startPriceDate == null) ? 0 : startPriceDate.hashCode());
		result = prime * result + ((userPriceActions == null) ? 0 : userPriceActions.hashCode());
		result = prime * result + ((vatIncluded == null) ? 0 : vatIncluded.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Price other = (Price) obj;
		if (currencyCode == null) {
			if (other.currencyCode != null)
				return false;
		} else if (!currencyCode.equals(other.currencyCode))
			return false;
		if (datePriceTimeNoted == null) {
			if (other.datePriceTimeNoted != null)
				return false;
		} else if (!datePriceTimeNoted.equals(other.datePriceTimeNoted))
			return false;
		if (endPriceDate == null) {
			if (other.endPriceDate != null)
				return false;
		} else if (!endPriceDate.equals(other.endPriceDate))
			return false;
		if (idPrice != other.idPrice)
			return false;
		if (idProduct == null) {
			if (other.idProduct != null)
				return false;
		} else if (!idProduct.equals(other.idProduct))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (promoPrice == null) {
			if (other.promoPrice != null)
				return false;
		} else if (!promoPrice.equals(other.promoPrice))
			return false;
		if (score == null) {
			if (other.score != null)
				return false;
		} else if (!score.equals(other.score))
			return false;
		if (startPriceDate == null) {
			if (other.startPriceDate != null)
				return false;
		} else if (!startPriceDate.equals(other.startPriceDate))
			return false;
		if (userPriceActions == null) {
			if (other.userPriceActions != null)
				return false;
		} else if (!userPriceActions.equals(other.userPriceActions))
			return false;
		if (vatIncluded == null) {
			if (other.vatIncluded != null)
				return false;
		} else if (!vatIncluded.equals(other.vatIncluded))
			return false;
		return true;
	}
	
	
    
}
