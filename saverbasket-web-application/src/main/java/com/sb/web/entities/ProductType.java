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
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.sb.web.entities.audit.Auditable;

/**
 *
 * @author dilneemuth
 */
@Entity(name="producttype")
public class ProductType  extends Auditable<Integer> implements Serializable {

    private static final long serialVersionUID = 1L;
   
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idProductType")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_type_generator")
    @SequenceGenerator(name = "product_type_generator", sequenceName = "product_type_seq", allocationSize = 1)
    private Integer idProductType;
    
    @Size(max = 100)
    @Column(name = "name")
    private String name;
    
    @Size(max = 2000)
    @Column(name = "description")
    private String description;
    
    @Size(max = 5)
    @Column(name = "code")
    private String code;

    public ProductType() {
    }
      
    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ProductType(Integer idProductType) {
        this.idProductType = idProductType;
    }

    public Integer getIdProductType() {
        return idProductType;
    }

    public void setIdProductType(Integer idProductType) {
        this.idProductType = idProductType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProductType != null ? idProductType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductType)) {
            return false;
        }
        ProductType other = (ProductType) object;
        if ((this.idProductType == null && other.idProductType != null) || (this.idProductType != null && !this.idProductType.equals(other.idProductType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.mavenproject7.ProductType[ idProductType=" + idProductType + " ]";
    }
    
}
