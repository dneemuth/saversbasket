package com.sb.web.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import com.sb.web.entities.audit.Auditable;
import com.sb.web.enumerations.PlanType;

@Entity(name="plan")
public class Plan  extends Auditable<Integer> implements Serializable{

    private static final long serialVersionUID = 1L;
	
	@Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plan_generator")
    @SequenceGenerator(name = "plan_generator", sequenceName = "plan_seq", allocationSize = 1)
    private Integer idPlan;	
	 
    @Column(nullable = false, unique = true, length = 50)
    private String planName;    
    
    @Column(nullable = true, length = 100)
    private String planDescription;    
	
	@OneToMany(mappedBy="plan")
	private List<Subscription> subscriptions;
	
	@Enumerated(EnumType.ORDINAL)
	private PlanType planType;
	
	@Column(name = "planDuration")
    private Integer planDuration;
	
	@Column(name = "numberOfCreditsAllowed")
    private Integer numberOfCreditsAllowed;
	
	@Column(name = "numberOfBasketsAllowed")
    private Integer numberOfBasketsAllowed;
	
	@Column(name = "numberOfItemsPerBasketAllowed")
    private Integer numberOfItemsPerBasketAllowed;
	
	@Column(name = "numberOfBasketComparisonsAllowed")
    private Integer numberOfBasketComparisonsAllowed;
	
	@Column(name = "numberOfDailyScansAllowed")
	private Integer numberOfDailyScansAllowed;
	
	@Column(name = "pricePerMonth")
    private BigDecimal pricePerMonth;	

	public String getPlanDescription() {
		return planDescription;
	}

	public void setPlanDescription(String planDescription) {
		this.planDescription = planDescription;
	}

	public Integer getIdPlan() {
		return idPlan;
	}

	public void setIdPlan(Integer idPlan) {
		this.idPlan = idPlan;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public List<Subscription> getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(List<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
	}

	public Integer getNumberOfCreditsAllowed() {
		return numberOfCreditsAllowed;
	}

	public void setNumberOfCreditsAllowed(Integer numberOfCreditsAllowed) {
		this.numberOfCreditsAllowed = numberOfCreditsAllowed;
	}

	public Integer getNumberOfBasketsAllowed() {
		return numberOfBasketsAllowed;
	}

	public void setNumberOfBasketsAllowed(Integer numberOfBasketsAllowed) {
		this.numberOfBasketsAllowed = numberOfBasketsAllowed;
	}

	public Integer getNumberOfItemsPerBasketAllowed() {
		return numberOfItemsPerBasketAllowed;
	}

	public void setNumberOfItemsPerBasketAllowed(Integer numberOfItemsPerBasketAllowed) {
		this.numberOfItemsPerBasketAllowed = numberOfItemsPerBasketAllowed;
	}

	public BigDecimal getPricePerMonth() {
		return pricePerMonth;
	}

	public void setPricePerMonth(BigDecimal pricePerMonth) {
		this.pricePerMonth = pricePerMonth;
	}
	
	public Integer getNumberOfBasketComparisonsAllowed() {
		return numberOfBasketComparisonsAllowed;
	}

	public void setNumberOfBasketComparisonsAllowed(Integer numberOfBasketComparisonsAllowed) {
		this.numberOfBasketComparisonsAllowed = numberOfBasketComparisonsAllowed;
	}
	
	public PlanType getPlanType() {
		return planType;
	}

	public void setPlanType(PlanType planType) {
		this.planType = planType;
	}

	public Integer getPlanDuration() {
		return planDuration;
	}

	public void setPlanDuration(Integer planDuration) {
		this.planDuration = planDuration;
	}
	
	public Integer getNumberOfDailyScansAllowed() {
		return numberOfDailyScansAllowed;
	}

	public void setNumberOfDailyScansAllowed(Integer numberOfDailyScansAllowed) {
		this.numberOfDailyScansAllowed = numberOfDailyScansAllowed;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idPlan == null) ? 0 : idPlan.hashCode());
		result = prime * result + ((numberOfBasketsAllowed == null) ? 0 : numberOfBasketsAllowed.hashCode());
		result = prime * result + ((numberOfCreditsAllowed == null) ? 0 : numberOfCreditsAllowed.hashCode());
		result = prime * result
				+ ((numberOfItemsPerBasketAllowed == null) ? 0 : numberOfItemsPerBasketAllowed.hashCode());
		result = prime * result + ((planDescription == null) ? 0 : planDescription.hashCode());
		result = prime * result + ((planName == null) ? 0 : planName.hashCode());
		result = prime * result + ((pricePerMonth == null) ? 0 : pricePerMonth.hashCode());
		result = prime * result + ((subscriptions == null) ? 0 : subscriptions.hashCode());
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
		Plan other = (Plan) obj;
		if (idPlan == null) {
			if (other.idPlan != null)
				return false;
		} else if (!idPlan.equals(other.idPlan))
			return false;
		if (numberOfBasketsAllowed == null) {
			if (other.numberOfBasketsAllowed != null)
				return false;
		} else if (!numberOfBasketsAllowed.equals(other.numberOfBasketsAllowed))
			return false;
		if (numberOfCreditsAllowed == null) {
			if (other.numberOfCreditsAllowed != null)
				return false;
		} else if (!numberOfCreditsAllowed.equals(other.numberOfCreditsAllowed))
			return false;
		if (numberOfItemsPerBasketAllowed == null) {
			if (other.numberOfItemsPerBasketAllowed != null)
				return false;
		} else if (!numberOfItemsPerBasketAllowed.equals(other.numberOfItemsPerBasketAllowed))
			return false;
		if (planDescription == null) {
			if (other.planDescription != null)
				return false;
		} else if (!planDescription.equals(other.planDescription))
			return false;
		if (planName == null) {
			if (other.planName != null)
				return false;
		} else if (!planName.equals(other.planName))
			return false;
		if (pricePerMonth == null) {
			if (other.pricePerMonth != null)
				return false;
		} else if (!pricePerMonth.equals(other.pricePerMonth))
			return false;
		if (subscriptions == null) {
			if (other.subscriptions != null)
				return false;
		} else if (!subscriptions.equals(other.subscriptions))
			return false;
		return true;
	}

    
}
