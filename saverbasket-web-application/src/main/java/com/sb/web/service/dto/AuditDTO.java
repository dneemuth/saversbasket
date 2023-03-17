/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sb.web.service.dto;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

/**
 *
 * @author dilneemuth
 */

public class AuditDTO {
    
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
