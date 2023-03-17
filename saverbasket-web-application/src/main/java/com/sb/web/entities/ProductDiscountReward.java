package com.sb.web.entities;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity(name="productdiscountreward")
public class ProductDiscountReward extends Reward {
	private static final long serialVersionUID = 1L;	
	
	 @OneToMany(mappedBy = "productDiscountReward", cascade =  CascadeType.ALL)
	 private List<Voucher> vouchers;

	public List<Voucher> getVouchers() {
		return vouchers;
	}

	public void setVouchers(List<Voucher> vouchers) {
		this.vouchers = vouchers;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(vouchers);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductDiscountReward other = (ProductDiscountReward) obj;
		return Objects.equals(vouchers, other.vouchers);
	}
	 
}
