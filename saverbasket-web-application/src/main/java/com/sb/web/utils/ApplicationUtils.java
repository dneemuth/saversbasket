package com.sb.web.utils;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

public class ApplicationUtils {
	
	/**
	 * convenience method to format pom version into proper format for dispaly
	 * 
	 * @param pomVersion
	 * @return
	 */
	public static String generateAppVersion(String pomVersion) {
		StringBuilder appVersion = new StringBuilder();
		appVersion.append(" v ");
		appVersion.append(pomVersion);
		return appVersion.toString();
	}
	
	/**
	 * utility to convert LocalDate to Date type
	 * 
	 * @param dateToConvert
	 * @return
	 */
	public static Date convertToDateViaInstant(LocalDate dateToConvert) {
	    return java.util.Date.from(dateToConvert.atStartOfDay()
	      .atZone(ZoneId.systemDefault())
	      .toInstant());
	}
	
	/**
	 * utility to check that provided date falls in current month
	 * 
	 * @param givenDate
	 * @return
	 */
	public static boolean inCurrentMonth(Date givenDate) {
		ZoneId timeZone = ZoneOffset.UTC; // Use whichever time zone makes sense for your use case
	    LocalDateTime givenLocalDateTime = LocalDateTime.ofInstant(givenDate.toInstant(), timeZone);
	    YearMonth currentMonth = YearMonth.now(timeZone);
	    return currentMonth.equals(YearMonth.from(givenLocalDateTime));		
	}
	
	/**
	 * utility to strip name from full email address
	 * 
	 * @param providedEmail
	 * @return trimmed email name
	 */
	public static String getStrippedEmailName(String providedEmail)
	{
	   return providedEmail.substring(0, providedEmail.indexOf("@") );
	}

}
