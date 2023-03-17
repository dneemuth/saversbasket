package com.sb.web.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.sb.web.entities.audit.Auditable;
import com.sb.web.enumerations.VoucherStatus;

@Entity(name="voucher")
public class Voucher  extends Auditable<Integer> implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idVoucher")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "voucher_generator")
    @SequenceGenerator(name = "voucher_generator", sequenceName = "voucher_seq", allocationSize = 1) 
    private Integer idVoucher;
    
    /** voucher number */ 
    @Column(nullable = false, length = 50)
    private String voucherNumber;
    
    @Column(name = "amountCredits")
   	private BigDecimal amountCredits;
    
    @Column(name = "expiryDate")
    private Date expiryDate;
    
    @Column(nullable = false, length = 180)
    private String consumeDetails;
    
    @Enumerated(EnumType.ORDINAL)
	private VoucherStatus voucherStatus;     
     
    @Column(name = "dateVoucherRedeemed")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateVoucherRedeemed; 
    
    @Column(name = "businessVoucherConsumedAt")
    private Integer businessVoucherConsumedAt;    
       
    @JoinColumn(name = "idUser", referencedColumnName = "idUser")
    @ManyToOne(optional = true)
    private User user;
    
    @JoinColumn(name = "idReward", referencedColumnName = "idReward")
    @ManyToOne(optional = true)
    private ProductDiscountReward productDiscountReward;

	public Integer getIdVoucher() {
		return idVoucher;
	}

	public void setIdVoucher(Integer idVoucher) {
		this.idVoucher = idVoucher;
	}

	public String getVoucherNumber() {
		return voucherNumber;
	}

	public void setVoucherNumber(String voucherNumber) {
		this.voucherNumber = voucherNumber;
	}

	public BigDecimal getAmountCredits() {
		return amountCredits;
	}

	public void setAmountCredits(BigDecimal amountCredits) {
		this.amountCredits = amountCredits;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getConsumeDetails() {
		return consumeDetails;
	}

	public void setConsumeDetails(String consumeDetails) {
		this.consumeDetails = consumeDetails;
	}

	public VoucherStatus getVoucherStatus() {
		return voucherStatus;
	}

	public void setVoucherStatus(VoucherStatus voucherStatus) {
		this.voucherStatus = voucherStatus;
	}

	public Date getDateVoucherRedeemed() {
		return dateVoucherRedeemed;
	}

	public void setDateVoucherRedeemed(Date dateVoucherRedeemed) {
		this.dateVoucherRedeemed = dateVoucherRedeemed;
	}

	public Integer getBusinessVoucherConsumedAt() {
		return businessVoucherConsumedAt;
	}

	public void setBusinessVoucherConsumedAt(Integer businessVoucherConsumedAt) {
		this.businessVoucherConsumedAt = businessVoucherConsumedAt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ProductDiscountReward getProductDiscountReward() {
		return productDiscountReward;
	}

	public void setProductDiscountReward(ProductDiscountReward productDiscountReward) {
		this.productDiscountReward = productDiscountReward;
	}

	@Override
	public int hashCode() {
		return Objects.hash(amountCredits, businessVoucherConsumedAt, consumeDetails, dateVoucherRedeemed, expiryDate,
				idVoucher, productDiscountReward, user, voucherNumber, voucherStatus);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Voucher other = (Voucher) obj;
		return Objects.equals(amountCredits, other.amountCredits)
				&& Objects.equals(businessVoucherConsumedAt, other.businessVoucherConsumedAt)
				&& Objects.equals(consumeDetails, other.consumeDetails)
				&& Objects.equals(dateVoucherRedeemed, other.dateVoucherRedeemed)
				&& Objects.equals(expiryDate, other.expiryDate) && Objects.equals(idVoucher, other.idVoucher)
				&& Objects.equals(productDiscountReward, other.productDiscountReward)
				&& Objects.equals(user, other.user) && Objects.equals(voucherNumber, other.voucherNumber)
				&& voucherStatus == other.voucherStatus;
	}	
}
