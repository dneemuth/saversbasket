package com.sb.web.enumerations;

public enum EmailStatus {
	
	SENT(0), NOT_SENT(1) , PENDING(2);
	
    private final int value;

    private EmailStatus(int value) {
        this.value = value;
    }

	public int getValue() {
		return value;
	}
}
