package com.sb.web.enumerations;

public enum ContributionStatus {
	
	PENDING("P"), PROCESSED("PR") , SUCCESS("PG") , FAILED("FG");
	
    private final String value;

    private ContributionStatus(String value) {
        this.value = value;
    }

	public String getValue() {
		return value;
	}

}
