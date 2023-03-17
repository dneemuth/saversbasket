package com.sb.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sb.web.entities.SystemSetting;

public interface SystemSettingRepository  extends JpaRepository<SystemSetting, Integer> {
	
	SystemSetting findBySystemKey(String systemKey);

}
