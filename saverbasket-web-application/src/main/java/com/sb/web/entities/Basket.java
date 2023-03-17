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

@Entity(name="basket")
public class Basket  extends Auditable<Integer> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idBasket")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "basket_generator")
    @SequenceGenerator(name = "basket_generator", sequenceName = "basket_seq", allocationSize = 1)
    private Integer idBasket;
	
	@Size(max = 50)
    @Column(name = "basketName")
    private String basketName;
 
    @ManyToOne
    @JoinColumn(name = "idUser")   
    private User user;
 
    @OneToMany(mappedBy = "basket", cascade =  CascadeType.ALL)
    private List<BasketItem> basketItems;
 
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateBasketRegistered;
    
    @Convert(converter=BooleanToStringConverter.class)
    private Boolean active;  
    
    @Convert(converter=BooleanToStringConverter.class)
    private Boolean locked;
    
    @Column(name = "dateAccountLocked")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAccountLocked;  
    
    @Column(name = "basketLockedByUser")
    private Integer basketLockedByUser;    
    
    @Column(name = "estimatedBudgetAmount")
    private BigDecimal estimatedBudgetAmount;    
    
    @Column(name = "actualBudgetAmount")
    private BigDecimal actualBudgetAmount;

	public Integer getIdBasket() {
		return idBasket;
	}

	public void setIdBasket(Integer idBasket) {
		this.idBasket = idBasket;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	

	public String getBasketName() {
		return basketName;
	}

	public void setBasketName(String basketName) {
		this.basketName = basketName;
	}

	public List<BasketItem> getBasketItems() {
		return basketItems;
	}

	public void setBasketItems(List<BasketItem> basketItems) {
		this.basketItems = basketItems;
	}

	public Date getDateBasketRegistered() {
		return dateBasketRegistered;
	}

	public void setDateBasketRegistered(Date dateBasketRegistered) {
		this.dateBasketRegistered = dateBasketRegistered;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}


	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public Date getDateAccountLocked() {
		return dateAccountLocked;
	}

	public void setDateAccountLocked(Date dateAccountLocked) {
		this.dateAccountLocked = dateAccountLocked;
	}

	public Integer getBasketLockedByUser() {
		return basketLockedByUser;
	}

	public void setBasketLockedByUser(Integer basketLockedByUser) {
		this.basketLockedByUser = basketLockedByUser;
	}

	public BigDecimal getEstimatedBudgetAmount() {
		return estimatedBudgetAmount;
	}

	public void setEstimatedBudgetAmount(BigDecimal estimatedBudgetAmount) {
		this.estimatedBudgetAmount = estimatedBudgetAmount;
	}

	public BigDecimal getActualBudgetAmount() {
		return actualBudgetAmount;
	}

	public void setActualBudgetAmount(BigDecimal actualBudgetAmount) {
		this.actualBudgetAmount = actualBudgetAmount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((active == null) ? 0 : active.hashCode());
		result = prime * result + ((actualBudgetAmount == null) ? 0 : actualBudgetAmount.hashCode());
		result = prime * result + ((basketItems == null) ? 0 : basketItems.hashCode());
		result = prime * result + ((basketLockedByUser == null) ? 0 : basketLockedByUser.hashCode());
		result = prime * result + ((basketName == null) ? 0 : basketName.hashCode());
		result = prime * result + ((dateAccountLocked == null) ? 0 : dateAccountLocked.hashCode());
		result = prime * result + ((dateBasketRegistered == null) ? 0 : dateBasketRegistered.hashCode());
		result = prime * result + ((estimatedBudgetAmount == null) ? 0 : estimatedBudgetAmount.hashCode());
		result = prime * result + ((idBasket == null) ? 0 : idBasket.hashCode());
		result = prime * result + ((locked == null) ? 0 : locked.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		Basket other = (Basket) obj;
		if (active == null) {
			if (other.active != null)
				return false;
		} else if (!active.equals(other.active))
			return false;
		if (actualBudgetAmount == null) {
			if (other.actualBudgetAmount != null)
				return false;
		} else if (!actualBudgetAmount.equals(other.actualBudgetAmount))
			return false;
		if (basketItems == null) {
			if (other.basketItems != null)
				return false;
		} else if (!basketItems.equals(other.basketItems))
			return false;
		if (basketLockedByUser == null) {
			if (other.basketLockedByUser != null)
				return false;
		} else if (!basketLockedByUser.equals(other.basketLockedByUser))
			return false;
		if (basketName == null) {
			if (other.basketName != null)
				return false;
		} else if (!basketName.equals(other.basketName))
			return false;
		if (dateAccountLocked == null) {
			if (other.dateAccountLocked != null)
				return false;
		} else if (!dateAccountLocked.equals(other.dateAccountLocked))
			return false;
		if (dateBasketRegistered == null) {
			if (other.dateBasketRegistered != null)
				return false;
		} else if (!dateBasketRegistered.equals(other.dateBasketRegistered))
			return false;
		if (estimatedBudgetAmount == null) {
			if (other.estimatedBudgetAmount != null)
				return false;
		} else if (!estimatedBudgetAmount.equals(other.estimatedBudgetAmount))
			return false;
		if (idBasket == null) {
			if (other.idBasket != null)
				return false;
		} else if (!idBasket.equals(other.idBasket))
			return false;
		if (locked == null) {
			if (other.locked != null)
				return false;
		} else if (!locked.equals(other.locked))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
}
