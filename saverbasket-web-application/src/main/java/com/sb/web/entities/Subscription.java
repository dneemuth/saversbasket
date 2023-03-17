package com.sb.web.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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

@Entity(name="subscription")
public class Subscription  extends Auditable<Integer> implements Serializable {

    private static final long serialVersionUID = 1L;
	
	@Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subscription_generator")
    @SequenceGenerator(name = "subscription_generator", sequenceName = "subscription_seq", allocationSize = 1) 
    private Integer idSubscription;	
	
	 @ManyToOne
     @JoinColumn(name="idAccount", nullable=true)
     private Account account;	 
	 
	// the user's account
     @OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
     @JoinColumn(name = "idPurchase", referencedColumnName = "idPurchase", nullable = true)
     private Purchase purchase;
	 	 
	 @ManyToOne
     @JoinColumn(name="idPlan", nullable=true)
     private Plan plan;	

	@Column(name = "dateSubscriptionPurchased")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateSubscriptionPurchased;	
	
	@Column(name = "dateSubscriptionStart")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateSubscriptionStart;		
	
	@Column(name = "dateSubscriptionEnd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateSubscriptionEnd;


	public Integer getIdSubscription() {
		return idSubscription;
	}


	public void setIdSubscription(Integer idSubscription) {
		this.idSubscription = idSubscription;
	}

	public Date getDateSubscriptionPurchased() {
		return dateSubscriptionPurchased;
	}

	public void setDateSubscriptionPurchased(Date dateSubscriptionPurchased) {
		this.dateSubscriptionPurchased = dateSubscriptionPurchased;
	}

	public Date getDateSubscriptionStart() {
		return dateSubscriptionStart;
	}

	public void setDateSubscriptionStart(Date dateSubscriptionStart) {
		this.dateSubscriptionStart = dateSubscriptionStart;
	}

	public Date getDateSubscriptionEnd() {
		return dateSubscriptionEnd;
	}

	public void setDateSubscriptionEnd(Date dateSubscriptionEnd) {
		this.dateSubscriptionEnd = dateSubscriptionEnd;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Purchase getPurchase() {
		return purchase;
	}

	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}
	
}
