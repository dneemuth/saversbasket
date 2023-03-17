package com.sb.web.events;

import java.math.BigDecimal;

import org.springframework.context.ApplicationEvent;

import com.sb.web.entities.User;

public class OnGrantCreditsEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	private User user;
	private BigDecimal credits;
	
	public OnGrantCreditsEvent( User user, BigDecimal credits) {
		super(user);
		
		this.user = user;
		this.credits = credits;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public BigDecimal getCredits() {
		return credits;
	}
	public void setCredits(BigDecimal credits) {
		this.credits = credits;
	}
	
}
