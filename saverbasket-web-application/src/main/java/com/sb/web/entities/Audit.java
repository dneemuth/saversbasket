/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sb.web.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author dilneemuth
 */

@Entity(name="audit")
public class Audit implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "audit_generator")
    @SequenceGenerator(name = "audit_generator", sequenceName = "audit_seq", allocationSize = 1)
    private Integer idAudit;
    
    @Size(max = 50)   
    private String username;
    
    @Size(max = 50)
    private String userType;
    
    @Size(max = 100)
    private String action;
    
    @Size(max = 50)
    private String targetUser;    

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    
    @Size(max = 45)
    private String userIp;

	public Integer getIdAudit() {
		return idAudit;
	}
	
	public Audit() {
	}
	
	public Audit(String username, String userType , String action, String targetUser, Date date, String userIp) {
		this.username = username;
		this.userType = userType;
		this.action = action;
		this.targetUser = targetUser;
		this.date = date;
		this.userIp = userIp;
	}

	public void setIdAudit(Integer idAudit) {
		this.idAudit = idAudit;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getTargetUser() {
		return targetUser;
	}

	public void setTargetUser(String targetUser) {
		this.targetUser = targetUser;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
    
	
    
}
