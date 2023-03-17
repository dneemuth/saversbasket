package com.sb.web.bootstrap;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.sb.web.configuration.WebConfig;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan("com.sb.web")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
@EnableAutoConfiguration(exclude = LiquibaseAutoConfiguration.class)
@Transactional
class SetupDataLoaderTest {
	
	@Autowired
	private SetupDataLoader setupDataLoader;

	@Test
	@Rollback(true)
	public void initializeAdminInformation_ok() {
		setupDataLoader.initializeAdminInformation();
	}

}
