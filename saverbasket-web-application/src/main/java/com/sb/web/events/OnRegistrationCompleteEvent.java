package com.sb.web.events;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.sb.web.entities.User;
import com.sb.web.enumerations.DeviceType;

public class OnRegistrationCompleteEvent extends ApplicationEvent {
 
	private static final long serialVersionUID = 1L;
	
	private String appUrl;
    private Locale locale;
    private User user;
    private DeviceType deviceType; // 0 - desktop , 1 - mobile
 
    public OnRegistrationCompleteEvent(
      User user, Locale locale, String appUrl,DeviceType deviceType) {
        super(user);
        
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
        this.deviceType = deviceType;
    }

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public DeviceType getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}
    // standard getters and setters    
}
