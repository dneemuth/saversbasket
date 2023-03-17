package com.sb.web.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:aws-ses-config.properties")
@PropertySources({
	   @PropertySource("classpath:aws-ses-config.properties"),
	   @PropertySource("classpath:application-aws.properties")
	})
public class CustomPropertyConfig {
	
	@Value("${mailFrom}")
	public String mailFrom;

	@Value("${awsAccessKey}")
	public String awsAccessKey;

	@Value("${awsSecretKey}")
	public String awsSecretKey;
	
	@Value("${saversbasket-hostname-cookie}")
	public String applicationUrl;

	public String getMailFrom() {
		return mailFrom;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	public String getAwsAccessKey() {
		return awsAccessKey;
	}

	public void setAwsAccessKey(String awsAccessKey) {
		this.awsAccessKey = awsAccessKey;
	}

	public String getAwsSecretKey() {
		return awsSecretKey;
	}

	public void setAwsSecretKey(String awsSecretKey) {
		this.awsSecretKey = awsSecretKey;
	}

	public String getApplicationUrl() {
		return applicationUrl;
	}

	public void setApplicationUrl(String applicationUrl) {
		this.applicationUrl = applicationUrl;
	}	  

}
