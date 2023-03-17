/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sb.web.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.sb.web.entities.audit.Auditable;
import com.sb.web.entities.converters.BooleanToStringConverter;
import com.sb.web.enumerations.BasketStatus;

/**
 *
 * @author dilneemuth
 */
@Entity(name="basketitem")
public class BasketItem  extends Auditable<Integer>  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idBasketItem")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "basket_item_generator")
    @SequenceGenerator(name = "basket_item_generator", sequenceName = "basket_item_seq", allocationSize = 1)
    private Integer idBasketItem;
    
    @Enumerated(EnumType.ORDINAL)
    private BasketStatus basketStatus;
    
    @Column(name = "idProduct")
    private Integer idProduct;
    
    @Column(name = "dateProductAdded")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateProductAdded;
    
    @Column(name = "quantity")
    private Integer quantity;
     
    @ManyToOne(fetch =FetchType.LAZY, cascade =  CascadeType.ALL)
    @JoinColumn(name = "idBasket")   
    private Basket basket;
       
    @Convert(converter=BooleanToStringConverter.class)
    private Boolean isActive;  
 
    public BasketItem() {
    }

    public BasketItem(Integer idBasketItem) {
        this.idBasketItem = idBasketItem;
    }

    public Integer getIdBasketItem() {
		return idBasketItem;
	}

	public void setIdBasketItem(Integer idBasketItem) {
		this.idBasketItem = idBasketItem;
	}

	public BasketStatus getBasketStatus() {
		return basketStatus;
	}

	public void setBasketStatus(BasketStatus basketStatus) {
		this.basketStatus = basketStatus;
	}

	public Integer getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }

    public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	
	public Date getDateProductAdded() {
		return dateProductAdded;
	}

	public void setDateProductAdded(Date dateProductAdded) {
		this.dateProductAdded = dateProductAdded;
	}

	public Basket getBasket() {
		return basket;
	}

	public void setBasket(Basket basket) {
		this.basket = basket;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Override
    public String toString() {
        return "com.mycompany.mavenproject7.Basket[ idBasketItem=" + idBasketItem + " ]";
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((basket == null) ? 0 : basket.hashCode());
		result = prime * result + ((basketStatus == null) ? 0 : basketStatus.hashCode());
		result = prime * result + ((dateProductAdded == null) ? 0 : dateProductAdded.hashCode());
		result = prime * result + ((idBasketItem == null) ? 0 : idBasketItem.hashCode());
		result = prime * result + ((idProduct == null) ? 0 : idProduct.hashCode());
		result = prime * result + ((isActive == null) ? 0 : isActive.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
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
		BasketItem other = (BasketItem) obj;
		if (basket == null) {
			if (other.basket != null)
				return false;
		} else if (!basket.equals(other.basket))
			return false;
		if (basketStatus != other.basketStatus)
			return false;
		if (dateProductAdded == null) {
			if (other.dateProductAdded != null)
				return false;
		} else if (!dateProductAdded.equals(other.dateProductAdded))
			return false;
		if (idBasketItem == null) {
			if (other.idBasketItem != null)
				return false;
		} else if (!idBasketItem.equals(other.idBasketItem))
			return false;
		if (idProduct == null) {
			if (other.idProduct != null)
				return false;
		} else if (!idProduct.equals(other.idProduct))
			return false;
		if (isActive == null) {
			if (other.isActive != null)
				return false;
		} else if (!isActive.equals(other.isActive))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		return true;
	}
	
	
    
}
