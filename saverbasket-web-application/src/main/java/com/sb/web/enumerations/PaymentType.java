package com.sb.web.enumerations;

public enum PaymentType {

	PAYPAL(0), CREDIT_CARD(1) , COLLECT_ON_DELIVERY(2), MCB_JUICE(3);
	
    private final int value;

    private PaymentType(int value) {
        this.value = value;
    }

	public int getValue() {
		return value;
	}
}
