package com.sb.web.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.sb.web.enumerations.PaymentStatus;
import com.sb.web.enumerations.PaymentType;

@Entity(name="purchase")
public class Purchase implements Serializable {

    private static final long serialVersionUID = 1L;
	
	@Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_generator")
    @SequenceGenerator(name = "purchase_generator", sequenceName = "purchase_seq", allocationSize = 1)
    private Integer idPurchase;
	
	/** Points to the user of this account. */
	@OneToOne(mappedBy = "purchase", cascade = CascadeType.ALL)
	private Subscription subscription;
	
	@Column(name = "billAmount")
    private BigDecimal billAmount;
	
	@Enumerated(EnumType.ORDINAL)
	private PaymentType paymentType;	
	
	@Column(name = "datePurchaseEffected")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datePurchaseEffected;	
	
	@Column(name = "datePurchaseClosed")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datePurchaseClosed;	
	
	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;

	public Integer getIdPurchase() {
		return idPurchase;
	}

	public void setIdPurchase(Integer idPurchase) {
		this.idPurchase = idPurchase;
	}

	public BigDecimal getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(BigDecimal billAmount) {
		this.billAmount = billAmount;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public Date getDatePurchaseEffected() {
		return datePurchaseEffected;
	}

	public void setDatePurchaseEffected(Date datePurchaseEffected) {
		this.datePurchaseEffected = datePurchaseEffected;
	}

	public Date getDatePurchaseClosed() {
		return datePurchaseClosed;
	}

	public void setDatePurchaseClosed(Date datePurchaseClosed) {
		this.datePurchaseClosed = datePurchaseClosed;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Subscription getSubscription() {
		return subscription;
	}

	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}
	

}
