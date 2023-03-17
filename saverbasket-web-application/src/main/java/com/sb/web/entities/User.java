/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sb.web.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.sb.web.annotations.AuditingTargetField;
import com.sb.web.entities.audit.Auditable;
import com.sb.web.entities.converters.BooleanToStringConverter;
import com.sb.web.enumerations.AuthenticationType;
import com.sb.web.enumerations.UserBadge;

/**
 *
 * @author dilneemuth
 */
@Entity(name="user")
public class User  extends Auditable<Integer> implements Serializable {

    private static final long serialVersionUID = 1L;
  
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idUser")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    @SequenceGenerator(name = "user_generator", sequenceName = "user_seq", allocationSize = 1) 
    private Integer idUser;

    @Size(max = 200)
    @NotNull(message = "username should not be empty.")    
    @Column(name = "username")    
    private String username;    
  
    @Basic(optional = false)
    @NotNull(message = "Email Address should not be empty.") 
    @Size(min = 1, max = 100)
    @Column(name = "email")
    @AuditingTargetField
    private String email;    
  
    @Basic(optional = false)
    @NotNull(message = "Password should not be empty.")
    @Size(min = 1, max = 100)
    @Column(name = "password")
    private String password;
    
    @Size(max = 20)
    @Column(name = "mobilePhoneNumber")
    private String mobilePhoneNumber;   
    
    @Size(max = 20)
    @Column(name = "homePhoneNumber")
    private String homePhoneNumber;    
    
    @Size(max = 200)
    @Column(name = "userProfilePictureUrl")
    private String userProfilePictureUrl;    
    
    @Column(name = "dateUserRegistered")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUserRegistered;   
    
    @Column(name = "lastTimeLogged")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastTimeLogged;             
 
    @Convert(converter=BooleanToStringConverter.class)
    private Boolean enabled; 
    
    @Convert(converter=BooleanToStringConverter.class)
    private Boolean verified; 
    
    @Enumerated(EnumType.ORDINAL)
    private UserBadge userBadge;    
    
    @Size(max = 150)
    @Column(name = "referrerCode")
    private String referrerCode;  
    
    @Size(max = 150)
    @Column(name = "userReferalCode")
    private String userReferalCode;     
   
    @Size(max = 20)
    @Column(name = "country")
    private String country;
    
    @Size(max = 3)
    @Column(name = "language")
    private String language;
    
    @Column(name = "dateOfBirth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    
    @Size(max = 1)
    @Column(name = "gender")
    private String gender;
    
   // @OneToOne(optional = false, mappedBy = "user", cascade =  CascadeType.ALL)
    
    @OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "idDeviceProfile", referencedColumnName = "idDeviceProfile", nullable = true)
    private DeviceProfile idDeviceProfile;    
    
    @OneToMany(mappedBy = "user", cascade =  CascadeType.ALL)
    private List<Basket> baskets;  
    
    // the user's account
    @OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "idAccount", referencedColumnName = "idAccount", nullable = true)
    private Account account;
    
    // the user's account
    @OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "idUser", referencedColumnName = "idVerificationToken", nullable = true)
    private VerificationToken verificationToken;
       
    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "UserRole", joinColumns = @JoinColumn(name = "idUser"), inverseJoinColumns = @JoinColumn(name = "idRole"))
    private List<Role> roles;      
  
    @OneToMany(mappedBy = "user", cascade =  CascadeType.ALL)
    private List<Address> addresses;
    
    @OneToMany(mappedBy = "user", cascade =  CascadeType.ALL)
    private List<Voucher> vouchers;
    
     // the user's privacy
    @OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "idPrivacyInformation", referencedColumnName = "idPrivacyInformation", nullable = true)
    private PrivacyInformation privacyInformation;
    
    // Lottery raffle
    @OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "idLotteryRaffle", referencedColumnName = "idLotteryRaffle", nullable = true)
    private LotteryRaffle lotteryRaffle;
    
    @OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "idProfile", referencedColumnName = "idProfile", nullable = true)
    private Profile profile; 
    
    @Enumerated(EnumType.STRING)
	@Column(name = "authType")   
	private AuthenticationType authType;    

    public User() {
    	super();
        this.enabled=false;
    }

    public User(Integer idUser) {
        this.idUser = idUser;
    }

    public User(Integer idUser, String email, String password) {
        this.idUser = idUser;
        this.email = email;
        this.password = password;
    }
     
    public Integer getIdUser() {
        return idUser;
    }

	public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserProfilePictureUrl() {
		return userProfilePictureUrl;
	}

	public void setUserProfilePictureUrl(String userProfilePictureUrl) {
		this.userProfilePictureUrl = userProfilePictureUrl;
	}

	public Date getDateUserRegistered() {
		return dateUserRegistered;
	}

	public void setDateUserRegistered(Date dateUserRegistered) {
		this.dateUserRegistered = dateUserRegistered;
	}
	
	public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public DeviceProfile getIdDeviceProfile() {
        return idDeviceProfile;
    }

    public void setIdDeviceProfile(DeviceProfile idDeviceProfile) {
        this.idDeviceProfile = idDeviceProfile;
    }
    

    public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public List<Basket> getBaskets() {
		return baskets;
	}

	public void setBaskets(List<Basket> baskets) {
		this.baskets = baskets;
	}
	

	public Date getLastTimeLogged() {
		return lastTimeLogged;
	}

	public void setLastTimeLogged(Date lastTimeLogged) {
		this.lastTimeLogged = lastTimeLogged;
	}

	
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	

	public UserBadge getUserBadge() {
		return userBadge;
	}

	public void setUserBadge(UserBadge userBadge) {
		this.userBadge = userBadge;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}
	

	public VerificationToken getVerificationToken() {
		return verificationToken;
	}

	public void setVerificationToken(VerificationToken verificationToken) {
		this.verificationToken = verificationToken;
	}

	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

	public String getMobilePhoneNumber() {
		return mobilePhoneNumber;
	}

	public void setMobilePhoneNumber(String mobilePhoneNumber) {
		this.mobilePhoneNumber = mobilePhoneNumber;
	}

	public String getHomePhoneNumber() {
		return homePhoneNumber;
	}

	public void setHomePhoneNumber(String homePhoneNumber) {
		this.homePhoneNumber = homePhoneNumber;
	}

	public List<Voucher> getVouchers() {
		return vouchers;
	}

	public void setVouchers(List<Voucher> vouchers) {
		this.vouchers = vouchers;
	}
	
	public PrivacyInformation getPrivacyInformation() {
		return privacyInformation;
	}

	public void setPrivacyInformation(PrivacyInformation privacyInformation) {
		this.privacyInformation = privacyInformation;
	}
	
	public String getReferrerCode() {
		return referrerCode;
	}

	public void setReferrerCode(String referrerCode) {
		this.referrerCode = referrerCode;
	}

	public String getUserReferalCode() {
		return userReferalCode;
	}

	public void setUserReferalCode(String userReferalCode) {
		this.userReferalCode = userReferalCode;
	}	

	public LotteryRaffle getLotteryRaffle() {
		return lotteryRaffle;
	}

	public void setLotteryRaffle(LotteryRaffle lotteryRaffle) {
		this.lotteryRaffle = lotteryRaffle;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public AuthenticationType getAuthType() {
		return authType;
	}

	public void setAuthType(AuthenticationType authType) {
		this.authType = authType;
	}

	@Override
    public String toString() {
        return "com.mycompany.mavenproject7.User[ idUser=" + idUser + " ]";
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		result = prime * result + ((addresses == null) ? 0 : addresses.hashCode());
		result = prime * result + ((baskets == null) ? 0 : baskets.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
		result = prime * result + ((dateUserRegistered == null) ? 0 : dateUserRegistered.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((enabled == null) ? 0 : enabled.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((homePhoneNumber == null) ? 0 : homePhoneNumber.hashCode());
		result = prime * result + ((idDeviceProfile == null) ? 0 : idDeviceProfile.hashCode());
		result = prime * result + ((idUser == null) ? 0 : idUser.hashCode());
		result = prime * result + ((language == null) ? 0 : language.hashCode());
		result = prime * result + ((lastTimeLogged == null) ? 0 : lastTimeLogged.hashCode());
		result = prime * result + ((mobilePhoneNumber == null) ? 0 : mobilePhoneNumber.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((privacyInformation == null) ? 0 : privacyInformation.hashCode());
		result = prime * result + ((referrerCode == null) ? 0 : referrerCode.hashCode());
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
		result = prime * result + ((userBadge == null) ? 0 : userBadge.hashCode());
		result = prime * result + ((userProfilePictureUrl == null) ? 0 : userProfilePictureUrl.hashCode());
		result = prime * result + ((userReferalCode == null) ? 0 : userReferalCode.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		result = prime * result + ((verificationToken == null) ? 0 : verificationToken.hashCode());
		result = prime * result + ((verified == null) ? 0 : verified.hashCode());
		result = prime * result + ((vouchers == null) ? 0 : vouchers.hashCode());
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
		User other = (User) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		if (addresses == null) {
			if (other.addresses != null)
				return false;
		} else if (!addresses.equals(other.addresses))
			return false;
		if (baskets == null) {
			if (other.baskets != null)
				return false;
		} else if (!baskets.equals(other.baskets))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (dateOfBirth == null) {
			if (other.dateOfBirth != null)
				return false;
		} else if (!dateOfBirth.equals(other.dateOfBirth))
			return false;
		if (dateUserRegistered == null) {
			if (other.dateUserRegistered != null)
				return false;
		} else if (!dateUserRegistered.equals(other.dateUserRegistered))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (enabled == null) {
			if (other.enabled != null)
				return false;
		} else if (!enabled.equals(other.enabled))
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (homePhoneNumber == null) {
			if (other.homePhoneNumber != null)
				return false;
		} else if (!homePhoneNumber.equals(other.homePhoneNumber))
			return false;
		if (idDeviceProfile == null) {
			if (other.idDeviceProfile != null)
				return false;
		} else if (!idDeviceProfile.equals(other.idDeviceProfile))
			return false;
		if (idUser == null) {
			if (other.idUser != null)
				return false;
		} else if (!idUser.equals(other.idUser))
			return false;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		if (lastTimeLogged == null) {
			if (other.lastTimeLogged != null)
				return false;
		} else if (!lastTimeLogged.equals(other.lastTimeLogged))
			return false;
		if (mobilePhoneNumber == null) {
			if (other.mobilePhoneNumber != null)
				return false;
		} else if (!mobilePhoneNumber.equals(other.mobilePhoneNumber))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (privacyInformation == null) {
			if (other.privacyInformation != null)
				return false;
		} else if (!privacyInformation.equals(other.privacyInformation))
			return false;
		if (referrerCode == null) {
			if (other.referrerCode != null)
				return false;
		} else if (!referrerCode.equals(other.referrerCode))
			return false;
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (!roles.equals(other.roles))
			return false;
		if (userBadge != other.userBadge)
			return false;
		if (userProfilePictureUrl == null) {
			if (other.userProfilePictureUrl != null)
				return false;
		} else if (!userProfilePictureUrl.equals(other.userProfilePictureUrl))
			return false;
		if (userReferalCode == null) {
			if (other.userReferalCode != null)
				return false;
		} else if (!userReferalCode.equals(other.userReferalCode))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		if (verificationToken == null) {
			if (other.verificationToken != null)
				return false;
		} else if (!verificationToken.equals(other.verificationToken))
			return false;
		if (verified == null) {
			if (other.verified != null)
				return false;
		} else if (!verified.equals(other.verified))
			return false;
		if (vouchers == null) {
			if (other.vouchers != null)
				return false;
		} else if (!vouchers.equals(other.vouchers))
			return false;
		return true;
	}	
    
}
