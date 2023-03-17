package com.sb.web.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name="systemsetting")
public class SystemSetting implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "system_setting_generator")
    @SequenceGenerator(name = "system_setting_generator", sequenceName = "system_setting_seq", allocationSize = 1)
    private Integer idSystemSetting;
    
    @Size(max = 250)   
    private String systemKey;
    
    @Size(max = 250)   
    private String systemValue;
    
    @Size(max = 100)
    private String settingByUser;    

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateSettingCreated;

	public Integer getIdSystemSetting() {
		return idSystemSetting;
	}

	public void setIdSystemSetting(Integer idSystemSetting) {
		this.idSystemSetting = idSystemSetting;
	}

	public String getSystemKey() {
		return systemKey;
	}

	public void setSystemKey(String systemKey) {
		this.systemKey = systemKey;
	}

	public String getSystemValue() {
		return systemValue;
	}

	public void setSystemValue(String systemValue) {
		this.systemValue = systemValue;
	}

	public String getSettingByUser() {
		return settingByUser;
	}

	public void setSettingByUser(String settingByUser) {
		this.settingByUser = settingByUser;
	}

	public Date getDateSettingCreated() {
		return dateSettingCreated;
	}

	public void setDateSettingCreated(Date dateSettingCreated) {
		this.dateSettingCreated = dateSettingCreated;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dateSettingCreated, idSystemSetting, settingByUser, systemKey, systemValue);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SystemSetting other = (SystemSetting) obj;
		return Objects.equals(dateSettingCreated, other.dateSettingCreated)
				&& Objects.equals(idSystemSetting, other.idSystemSetting)
				&& Objects.equals(settingByUser, other.settingByUser) && Objects.equals(systemKey, other.systemKey)
				&& Objects.equals(systemValue, other.systemValue);
	}  
}
