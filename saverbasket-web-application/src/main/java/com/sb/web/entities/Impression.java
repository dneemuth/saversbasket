package com.sb.web.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import com.sb.web.entities.audit.Auditable;
import com.sb.web.enumerations.ImpressionType;

@Entity(name="impression")
public class Impression  extends Auditable<Integer> implements Serializable {

    private static final long serialVersionUID = 1L;
	
	@Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idImpression")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "impression_generator")
    @SequenceGenerator(name = "impression_generator", sequenceName = "impression_seq", allocationSize = 1)
    private Integer idImpression;
	
	@Enumerated(EnumType.ORDINAL)
	private ImpressionType impressionType;

    
    @ManyToOne
    @JoinColumn(name = "idProduct")   
    private Product product;

	public long getIdImpression() {
		return idImpression;
	}

	public void setIdImpression(Integer idImpression) {
		this.idImpression = idImpression;
	}

	public ImpressionType getImpressionType() {
		return impressionType;
	}

	public void setImpressionType(ImpressionType impressionType) {
		this.impressionType = impressionType;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idImpression ^ (idImpression >>> 32));
		result = prime * result + ((impressionType == null) ? 0 : impressionType.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
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
		Impression other = (Impression) obj;
		if (idImpression != other.idImpression)
			return false;
		if (impressionType != other.impressionType)
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		return true;
	}

}
