package com.sb.web.enumerations;

public enum PaymentStatus {

	PAID("P"), NOT_PAID("NP") , PENDING("PG");
	
    private final String value;

    private PaymentStatus(String value) {
        this.value = value;
    }

	public String getValue() {
		return value;
	}
}
