package com.sb.web.response.dto;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;

public class CompareBasketResponseDTO {
	
	private List<RetailerBasketResponse> retailerBasketResponses;
	
	//execution time in MS
	private long executionTimeElapsed;
	
	//rewards
	private BigDecimal monetaryReward;
	
	//execute comparison flag
	private boolean executeComparison;
	
	//maximum basket comparison reached
	private boolean maxBasketComparisonReached;

	//credits needed to perform task.
	private Integer neededCredits = new Integer(NumberUtils.INTEGER_ZERO);	
	
	//flag to check if user authenticated
	private boolean userAuthenticated;

	public List<RetailerBasketResponse> getRetailerBasketResponses() {
		return retailerBasketResponses;
	}

	public void setRetailerBasketResponses(List<RetailerBasketResponse> retailerBasketResponses) {
		this.retailerBasketResponses = retailerBasketResponses;
	}

	public long getExecutionTimeElapsed() {
		return executionTimeElapsed;
	}

	public void setExecutionTimeElapsed(long executionTimeElapsed) {
		this.executionTimeElapsed = executionTimeElapsed;
	}

	public BigDecimal getMonetaryReward() {
		return monetaryReward;
	}

	public void setMonetaryReward(BigDecimal monetaryReward) {
		this.monetaryReward = monetaryReward;
	}

	public boolean isExecuteComparison() {
		return executeComparison;
	}

	public void setExecuteComparison(boolean executeComparison) {
		this.executeComparison = executeComparison;
	}

	public Integer getNeededCredits() {
		return neededCredits;
	}

	public void setNeededCredits(Integer neededCredits) {
		this.neededCredits = neededCredits;
	}

	public boolean isUserAuthenticated() {
		return userAuthenticated;
	}

	public void setUserAuthenticated(boolean userAuthenticated) {
		this.userAuthenticated = userAuthenticated;
	}

	public boolean isMaxBasketComparisonReached() {
		return maxBasketComparisonReached;
	}

	public void setMaxBasketComparisonReached(boolean maxBasketComparisonReached) {
		this.maxBasketComparisonReached = maxBasketComparisonReached;
	}

}
