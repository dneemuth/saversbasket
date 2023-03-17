/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sb.web.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.sb.web.entities.audit.Auditable;

/**
 *
 * @author dilneemuth
 */
@Entity(name="productattribute")
public class ProductAttribute  extends Auditable<Integer> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idProductAttribute")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_attribute_generator")
    @SequenceGenerator(name = "product_attribute_generator", sequenceName = "product_attribute_seq", allocationSize = 1)
    private Integer idProductAttribute;
    @Size(max = 50)
    @Column(name = "productMapKey")
    private String productMapKey;
    @Size(max = 250)
    @Column(name = "productMapValue")
    private String productMapValue;
    @JoinColumn(name = "idProduct", referencedColumnName = "idProduct")
    @ManyToOne(optional = false)
    private Product idProduct;

    public ProductAttribute() {
    }

    public ProductAttribute(Integer idProductAttribute) {
        this.idProductAttribute = idProductAttribute;
    }

    public Integer getIdProductAttribute() {
        return idProductAttribute;
    }

    public void setIdProductAttribute(Integer idProductAttribute) {
        this.idProductAttribute = idProductAttribute;
    }

    public String getProductMapKey() {
        return productMapKey;
    }

    public void setProductMapKey(String productMapKey) {
        this.productMapKey = productMapKey;
    }

    public String getProductMapValue() {
        return productMapValue;
    }

    public void setProductMapValue(String productMapValue) {
        this.productMapValue = productMapValue;
    }

    public Product getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Product idProduct) {
        this.idProduct = idProduct;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idProduct == null) ? 0 : idProduct.hashCode());
		result = prime * result + ((idProductAttribute == null) ? 0 : idProductAttribute.hashCode());
		result = prime * result + ((productMapKey == null) ? 0 : productMapKey.hashCode());
		result = prime * result + ((productMapValue == null) ? 0 : productMapValue.hashCode());
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
		ProductAttribute other = (ProductAttribute) obj;
		if (idProduct == null) {
			if (other.idProduct != null)
				return false;
		} else if (!idProduct.equals(other.idProduct))
			return false;
		if (idProductAttribute == null) {
			if (other.idProductAttribute != null)
				return false;
		} else if (!idProductAttribute.equals(other.idProductAttribute))
			return false;
		if (productMapKey == null) {
			if (other.productMapKey != null)
				return false;
		} else if (!productMapKey.equals(other.productMapKey))
			return false;
		if (productMapValue == null) {
			if (other.productMapValue != null)
				return false;
		} else if (!productMapValue.equals(other.productMapValue))
			return false;
		return true;
	}

   
    
}
