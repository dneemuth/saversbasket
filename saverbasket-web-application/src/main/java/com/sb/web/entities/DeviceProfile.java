/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sb.web.entities;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.sb.web.entities.converters.BooleanToStringConverter;
import com.sb.web.enumerations.DeviceType;

/**
 *
 * @author dilneemuth
 */
@Entity(name="deviceprofile")
public class DeviceProfile implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idDeviceProfile")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "device_profile_generator")
    @SequenceGenerator(name = "device_profile_generator", sequenceName = "device_profile_seq", allocationSize = 1)
    private Integer idDeviceProfile;
    @Size(max = 10)
    @Column(name = "phoneNumber")
    private String phoneNumber;
    @Size(max = 200)
    @Column(name = "fcmIdentifier")
    private String fcmIdentifier;
    @Size(max = 50)
    @Column(name = "mobileOS")
    private String mobileOS;
    @Size(max = 250)
    @Column(name = "mobileQRCode")
    private String mobileQRCode;
    @Size(max = 250)
    @Column(name = "mobileIPLocation")
    private String mobileIPLocation;
    @Size(max = 50)
    @Column(name = "lastLocationDetected")
    private String lastLocationDetected;
    @Column(name = "lastTimeDeviceDetected")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastTimeDeviceDetected;
    @Column(name = "deviceType")
    @Enumerated(value = EnumType.STRING)
    private DeviceType deviceType;
    @Column(name = "notificationToken")
    private String notificationToken;    
    @Column(name = "deviceId", nullable = false)
    private String deviceId;    
    @Column(name = "isRefreshActive")
    @Convert(converter=BooleanToStringConverter.class)
    private Boolean isRefreshActive;
    @OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "idToken", referencedColumnName = "idToken", nullable = true)    
    private RefreshToken refreshToken;
    /** Points to the user of this account. */
    @OneToOne(mappedBy = "idDeviceProfile", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private User user;

    public DeviceProfile() {
    }

    public DeviceProfile(Integer idDeviceProfile) {
        this.idDeviceProfile = idDeviceProfile;
    }

    public Integer getIdDeviceProfile() {
        return idDeviceProfile;
    }

    public void setIdDeviceProfile(Integer idDeviceProfile) {
        this.idDeviceProfile = idDeviceProfile;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

 
    public String getMobileOS() {
        return mobileOS;
    }

    public void setMobileOS(String mobileOS) {
        this.mobileOS = mobileOS;
    }

    public String getMobileQRCode() {
        return mobileQRCode;
    }

    public void setMobileQRCode(String mobileQRCode) {
        this.mobileQRCode = mobileQRCode;
    } 

    public DeviceType getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

	public String getNotificationToken() {
		return notificationToken;
	}

	public void setNotificationToken(String notificationToken) {
		this.notificationToken = notificationToken;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

   public Boolean getRefreshActive() {
        return isRefreshActive;
    }

    public void setRefreshActive(Boolean refreshActive) {
        isRefreshActive = refreshActive;
    }

	public RefreshToken getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(RefreshToken refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getFcmIdentifier() {
		return fcmIdentifier;
	}

	public void setFcmIdentifier(String fcmIdentifier) {
		this.fcmIdentifier = fcmIdentifier;
	}

	public String getMobileIPLocation() {
		return mobileIPLocation;
	}

	public void setMobileIPLocation(String mobileIPLocation) {
		this.mobileIPLocation = mobileIPLocation;
	}

	public String getLastLocationDetected() {
		return lastLocationDetected;
	}

	public void setLastLocationDetected(String lastLocationDetected) {
		this.lastLocationDetected = lastLocationDetected;
	}

	public Date getLastTimeDeviceDetected() {
		return lastTimeDeviceDetected;
	}

	public void setLastTimeDeviceDetected(Date lastTimeDeviceDetected) {
		this.lastTimeDeviceDetected = lastTimeDeviceDetected;
	}

	

    public Boolean getIsRefreshActive() {
		return isRefreshActive;
	}

	public void setIsRefreshActive(Boolean isRefreshActive) {
		this.isRefreshActive = isRefreshActive;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idDeviceProfile != null ? idDeviceProfile.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DeviceProfile)) {
            return false;
        }
        DeviceProfile other = (DeviceProfile) object;
        if ((this.idDeviceProfile == null && other.idDeviceProfile != null) || (this.idDeviceProfile != null && !this.idDeviceProfile.equals(other.idDeviceProfile))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.mavenproject7.DeviceProfile[ idDeviceProfile=" + idDeviceProfile + " ]";
    }
    
}
