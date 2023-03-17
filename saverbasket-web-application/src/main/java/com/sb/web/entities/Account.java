/**
 * Copyright 2009-2010 reward4j.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.sb.web.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sb.web.entities.audit.Auditable;
import com.sb.web.enumerations.AccountType;

/**
 * The {@code Account} represents a user's account.
 */
@Entity(name="account")
public class Account  extends Auditable<Integer> implements Serializable {

  private static final long serialVersionUID = 1L;
    
    
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_generator")
  @SequenceGenerator(name = "account_generator", sequenceName = "account_seq", allocationSize = 1)  
  @Column(name = "idAccount")
  private Long idAccount;  //...

  /** Name of this account. */ 
  @Column(nullable = false, length = 100)
  private String name;

  /** Points to the user of this account. */
  @OneToOne(mappedBy = "account", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
  private User user;
   
  @OneToMany(mappedBy = "account", cascade = CascadeType.ALL) 
  private List<Business> businessList;  
  
  @Column(name = "dateAccountCreated")
  @Temporal(TemporalType.TIMESTAMP)
  private Date dateAccountCreated;  
  
  @Column(nullable = true)
  @OneToMany(mappedBy="account", cascade = CascadeType.ALL)
  private List<Subscription> subscriptions;  
  
  @Column(nullable = true)
  @OneToMany(mappedBy="account", cascade = CascadeType.ALL)
  private List<Campaign> campaigns;  
  
  @Enumerated(EnumType.ORDINAL)
  private AccountType accountType;   
  
  @Column(name = "budgetedAmount")
  private BigDecimal budgetedAmount;
  
  @Size(max = 200)
  @Column(name = "apiKey")
  private String apiKey;

  /**
   * Constructor.
   */
  public Account() {
  }

  /**
   * Constructor.
   *
   * @param name
   */
  public Account(final String name) {
    if (null == name)
      throw new IllegalArgumentException("name must not be null");

    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

    

  public Long getIdAccount() {
	return idAccount;
	}
	
	public void setIdAccount(Long idAccount) {
		this.idAccount = idAccount;
	}
	
	public List<Subscription> getSubscriptions() {
		return subscriptions;
	}
	
	public void setSubscriptions(List<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
	}
	
	public AccountType getAccountType() {
		return accountType;
	}
	
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
	
	public List<Campaign> getCampaigns() {
		return campaigns;
	}

	public void setCampaigns(List<Campaign> campaigns) {
		this.campaigns = campaigns;
	}

	public Date getDateAccountCreated() {
	return dateAccountCreated;
	}
	
	public void setDateAccountCreated(Date dateAccountCreated) {
		this.dateAccountCreated = dateAccountCreated;
	}
	
	public List<Business> getBusinessList() {
		return businessList;
	}
	
	public void setBusinessList(List<Business> businessList) {
		this.businessList = businessList;
	}


	public BigDecimal getBudgetedAmount() {
		return budgetedAmount;
	}

	public void setBudgetedAmount(BigDecimal budgetedAmount) {
		this.budgetedAmount = budgetedAmount;
	}

public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

@Override
  public boolean equals(Object obj) {

    if (obj == null) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    if (obj.getClass() != getClass()) {
      return false;
    }

    Account rhs = (Account) obj;

    return new EqualsBuilder().append(this.name, rhs.name).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(this.name).toHashCode();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).append("name", this.name).toString();
  }

}
