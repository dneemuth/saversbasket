package com.sb.web.events;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import com.sb.web.entities.SystemSetting;
import com.sb.web.repositories.SystemSettingRepository;
import com.sb.web.utils.SaversBasketConstants;
@Component
public class OnInitializeDataEvent {	
	
	@Autowired
    private DataSource dataSource;
	@Autowired
	private SystemSettingRepository systemSettingRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void loadData() {
    	SystemSetting systemSetting = systemSettingRepository.findBySystemKey(SaversBasketConstants.RUN_INITIAL_SEQUENCE_LOAD);
        if (systemSetting != null && systemSetting.getSystemValue().equalsIgnoreCase("1")) {
            return;
        }
        else {
            ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("initialize-data.sql"));
        resourceDatabasePopulator.execute(dataSource);
        }
    }

}
