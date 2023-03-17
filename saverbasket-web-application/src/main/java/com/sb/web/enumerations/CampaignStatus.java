package com.sb.web.enumerations;

public enum CampaignStatus {

	APPROVED(0), NOT_APPROVED(1), APPROVAL_PENDING(2);
	
    private final int value;

    private CampaignStatus(int value) {
        this.value = value;
    }

	public int getValue() {
		return value;
	}

}
