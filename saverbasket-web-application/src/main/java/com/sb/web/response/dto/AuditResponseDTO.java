package com.sb.web.response.dto;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

public class AuditResponseDTO {

	@ApiModelProperty(position = 0)  
    private String auditAction;
    
	@ApiModelProperty(position = 1)  
    private String auditUser;

	@ApiModelProperty(position = 2)  
    private Date auditDate;
    
    public String getAuditAction() {
        return auditAction;
    }

    public void setAuditAction(String auditAction) {
        this.auditAction = auditAction;
    }

    public String getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(String auditUser) {
        this.auditUser = auditUser;
    }

    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }
}
