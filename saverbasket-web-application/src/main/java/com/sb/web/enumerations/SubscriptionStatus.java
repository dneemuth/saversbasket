package com.sb.web.enumerations;

public enum SubscriptionStatus {

	ACTIVE("ACT"), INACTIVE("INACT"), EXPIRED("EXP") ,RENEW("RNW"), CANCELLED("CNL");

	private final String value;
	
	private SubscriptionStatus(String value) {
	    this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
