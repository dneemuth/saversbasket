package com.sb.web.entities;

import java.io.Serializable;

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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import com.sb.web.entities.audit.Auditable;
import com.sb.web.entities.converters.BooleanToStringConverter;
import com.sb.web.enumerations.AllowNotificationSchedule;

@Entity(name="privacyinformation")
public class PrivacyInformation  extends Auditable<Integer> implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idPrivacyInformation")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "privacy_information_generator")
    @SequenceGenerator(name = "privacy_information_generator", sequenceName = "privacy_info_seq", allocationSize = 1)
    private Integer idPrivacyInformation;

    @Convert(converter=BooleanToStringConverter.class)
    private Boolean acceptMailAdverts;  
    
    @Convert(converter=BooleanToStringConverter.class)
    private Boolean acceptAlertNotification;  
    
    @Convert(converter=BooleanToStringConverter.class)
    private Boolean muteAllNotifications;  
    
    @Convert(converter=BooleanToStringConverter.class)
    private Boolean allowNotifications;  
    
    @Enumerated(EnumType.ORDINAL)
    private AllowNotificationSchedule allowNotificationSchedule;
    
    /** Points to the user of this privacy account. */
    @OneToOne(mappedBy = "privacyInformation", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private User user; 
 
	public Integer getIdPrivacyInformation() {
		return idPrivacyInformation;
	}

	public void setIdPrivacyInformation(Integer idPrivacyInformation) {
		this.idPrivacyInformation = idPrivacyInformation;
	}

	public Boolean getAcceptMailAdverts() {
		return acceptMailAdverts;
	}

	public void setAcceptMailAdverts(Boolean acceptMailAdverts) {
		this.acceptMailAdverts = acceptMailAdverts;
	}

	public Boolean getAcceptAlertNotification() {
		return acceptAlertNotification;
	}

	public void setAcceptAlertNotification(Boolean acceptAlertNotification) {
		this.acceptAlertNotification = acceptAlertNotification;
	}

	public Boolean getMuteAllNotifications() {
		return muteAllNotifications;
	}

	public void setMuteAllNotifications(Boolean muteAllNotifications) {
		this.muteAllNotifications = muteAllNotifications;
	}

	public AllowNotificationSchedule getAllowNotificationSchedule() {
		return allowNotificationSchedule;
	}

	public void setAllowNotificationSchedule(AllowNotificationSchedule allowNotificationSchedule) {
		this.allowNotificationSchedule = allowNotificationSchedule;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Boolean getAllowNotifications() {
		return allowNotifications;
	}

	public void setAllowNotifications(Boolean allowNotifications) {
		this.allowNotifications = allowNotifications;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((acceptAlertNotification == null) ? 0 : acceptAlertNotification.hashCode());
		result = prime * result + ((acceptMailAdverts == null) ? 0 : acceptMailAdverts.hashCode());
		result = prime * result + ((allowNotificationSchedule == null) ? 0 : allowNotificationSchedule.hashCode());
		result = prime * result + ((allowNotifications == null) ? 0 : allowNotifications.hashCode());
		result = prime * result + ((idPrivacyInformation == null) ? 0 : idPrivacyInformation.hashCode());
		result = prime * result + ((muteAllNotifications == null) ? 0 : muteAllNotifications.hashCode());
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
		PrivacyInformation other = (PrivacyInformation) obj;
		if (acceptAlertNotification == null) {
			if (other.acceptAlertNotification != null)
				return false;
		} else if (!acceptAlertNotification.equals(other.acceptAlertNotification))
			return false;
		if (acceptMailAdverts == null) {
			if (other.acceptMailAdverts != null)
				return false;
		} else if (!acceptMailAdverts.equals(other.acceptMailAdverts))
			return false;
		if (allowNotificationSchedule != other.allowNotificationSchedule)
			return false;
		if (allowNotifications == null) {
			if (other.allowNotifications != null)
				return false;
		} else if (!allowNotifications.equals(other.allowNotifications))
			return false;
		if (idPrivacyInformation == null) {
			if (other.idPrivacyInformation != null)
				return false;
		} else if (!idPrivacyInformation.equals(other.idPrivacyInformation))
			return false;
		if (muteAllNotifications == null) {
			if (other.muteAllNotifications != null)
				return false;
		} else if (!muteAllNotifications.equals(other.muteAllNotifications))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
	

}
