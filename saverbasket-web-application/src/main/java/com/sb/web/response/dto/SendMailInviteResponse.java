package com.sb.web.response.dto;

import java.math.BigDecimal;

public class SendMailInviteResponse {
	
	private boolean inviteSent;
	private Integer numberOfInviteSent;
	private BigDecimal creditedReward;
	
	public boolean isInviteSent() {
		return inviteSent;
	}
	public void setInviteSent(boolean inviteSent) {
		this.inviteSent = inviteSent;
	}
	public Integer getNumberOfInviteSent() {
		return numberOfInviteSent;
	}
	public void setNumberOfInviteSent(Integer numberOfInviteSent) {
		this.numberOfInviteSent = numberOfInviteSent;
	}
	public BigDecimal getCreditedReward() {
		return creditedReward;
	}
	public void setCreditedReward(BigDecimal creditedReward) {
		this.creditedReward = creditedReward;
	}
	
}
