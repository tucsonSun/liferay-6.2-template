/*
 * File Name: EGRDateUtilities.java
 * 
 * Created by: Ernesto Rendon on Dec 25, 2010 10:43:19 PM.
 * 
 * Copyright (c) 2010 EGR Software Inc. 3839 E. Cholla St. Phoenix, Arizona,
 * 85028, U.S.A. All rights reserved.
 * 
 * This software is the confidential and proprietary information of EGR Software
 * Inc. You shall not disclose such confidential information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with EGR Software Inc.
 */

package com.egr.EGRUtilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import com.webobjects.foundation.NSForwardException;
import com.webobjects.foundation.NSLog;
import com.webobjects.foundation.NSTimestamp;


public class EGRDateUtilities {
	
	/**
	 * Method attempts to format aTimestamp with the specified format. If the format fails then the method returns aTimestamp toString.
	 * @param aTimestamp NSTimestamp
     * @param formatPattern String
     * @return String
     * 
     * Format:							Result:
     * "yyyy.MM.dd G 'at' HH:mm:ss z" 	2001.07.04 AD at 12:08:56 PDT
     * "EEE, MMM d, ''yy" 				Wed, Jul 4, '01
     * "EEE, MMMMM dd, ''yy" 				Wed, July 04, '01
     * "h:mm a" 						12:08 PM
     * "hh 'o''clock' a, zzzz" 			12 o'clock PM, Pacific Daylight Time
     * "K:mm a, z" 						0:08 PM, PDT
     * "yyyyy.MMMMM.dd GGG hh:mm aaa" 	02001.July.04 AD 12:08 PM
     * "EEE, d MMM yyyy HH:mm:ss Z" 	Wed, 4 Jul 2001 12:08:56 -0700
     * "yyMMddHHmmssZ" 					010704120856-0700
     * "yyyy-MM-dd'T'HH:mm:ss.SSSZ" 	2001-07-04T12:08:56.235-0700
	 */
	public static String formattedTime(NSTimestamp aTimestamp, String formatPattern) { 
		if (aTimestamp == null) throw new IllegalArgumentException("aTimestamp is not allowed to be null");
		if (formatPattern == null || formatPattern.length() == 0) throw new IllegalArgumentException("formattedTime :: format is not set correctly");

		//Exmaple : yyyy/MM/dd HH:mm:ss
		SimpleDateFormat formatter = new SimpleDateFormat(formatPattern);
		try {
			return formatter.format(aTimestamp);
		} catch (NumberFormatException e) {
			NSLog.out.appendln("EGRDateUtilities.formattedTime: could not format time because.... \n" +e);
			return aTimestamp.toString();
		}
	}
	
    /**
     * Method returns true if startTimestamp is before aTimestamp.
     * @param timestamp
     * @param startTimestamp
     * @return Boolean
     */
     public static Boolean isDateBeforeStartDate(NSTimestamp aTimestamp, NSTimestamp startTimestamp) { return aTimestamp.before(startTimestamp); }

     /**
      * Method returns true if endTimestamp is after aTimestamp.
      * @param aTimestamp
      * @param endTimestamp
      * @return Boolean
      */
     public static Boolean isDateAfterEndDate(NSTimestamp aTimestamp, NSTimestamp endTimestamp) { return aTimestamp.after(endTimestamp); }
    
     /**
      * Method returns true if aTimestamp is between startTimestamp and endTimestamp
      * @param startTimestamp
      * @param endTimestamp
      * @param aTimestamp
      * @return Boolean
      */
     public static Boolean isDateBetween(NSTimestamp startTimestamp, NSTimestamp endTimestamp, NSTimestamp aTimestamp) { 
    	 return aTimestamp.after(startTimestamp) && aTimestamp.before(endTimestamp); 
     }

	/**
	 * Method returns the end of day for the specified timeStamp.
	 * @param aTimeStamp
	 * @return End of day 23:59:59 time
	 */
	public static NSTimestamp endOfDayForTimestamp(NSTimestamp aTimeStamp) {
		
		if (aTimeStamp == null ) throw new IllegalArgumentException("endOfDay :: timeStamp is not allowed to be null");
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(aTimeStamp);
		int year = calendar.get(GregorianCalendar.YEAR);
		int month = calendar.get(GregorianCalendar.MONTH) + 1;
		int day = calendar.get(GregorianCalendar.DATE);
		int hour = calendar.get(GregorianCalendar.HOUR);
		int second = calendar.get(GregorianCalendar.SECOND);
		TimeZone timeZone = calendar.getTimeZone();
		// year, month, date, hour, minute, second, timezone
		return new NSTimestamp(year, month, day, 23, 59, 59, timeZone);
	}

	/**
	 * Returns the start of day for the specified timeStamp.
	 * @param aTimeStamp
	 * @return Start of day 00:00:00
	 */
	public static NSTimestamp startOfDayForTimestamp(NSTimestamp aTimeStamp) {
		if (aTimeStamp == null ) throw new IllegalArgumentException("startOfDay :: timeStamp is not allowed to be null");
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(aTimeStamp);
		int year = calendar.get(GregorianCalendar.YEAR);
		int month = calendar.get(GregorianCalendar.MONTH) + 1;
		int day = calendar.get(GregorianCalendar.DATE);
		int hour = calendar.get(GregorianCalendar.HOUR);
		int second = calendar.get(GregorianCalendar.SECOND);
		TimeZone timeZone = calendar.getTimeZone();
		// year, month, date, hour, minute, second, timezone
		return new NSTimestamp(year, month, day, 0, 0, 0, timeZone);
	}
	
	/**
	 * Method used to convert a stringDate into an actual Date object
	 * Example formatPattern strings
	 * "yyyy.MM.dd G 'at' HH:mm:ss z" 	2001.07.04 AD at 12:08:56 PDT
	 * "EEE, MMM d, ''yy" 	Wed, Jul 4, '01
	 * "h:mm a" 	12:08 PM
	 * 
	 * @param stringDate
	 * @param formatPattern
	 * @return
	 */
	public static NSTimestamp convertStringToDate(String stringDate, String formatPattern) {
		SimpleDateFormat formatter = new SimpleDateFormat(formatPattern);
		try {
			return new NSTimestamp(formatter.parse(stringDate));
		} catch (ParseException e) {
			//NSLog.err.appendln("EGRDateUtilities.convertStringToDate: could not covert string to date because.... \n" +e);	
			throw e; 
		}
	}
	
	/**
	 * Method converts long seconds into a readable display string 
	 * @param seconds
	 * @return
	 */
	public static String calculateTimeFromSeconds(long seconds) {
	    int day = (int) TimeUnit.SECONDS.toDays(seconds);
	    long hours = TimeUnit.SECONDS.toHours(seconds) -
	                 TimeUnit.DAYS.toHours(day);
	    long minute = TimeUnit.SECONDS.toMinutes(seconds) - 
	                  TimeUnit.DAYS.toMinutes(day) -
	                  TimeUnit.HOURS.toMinutes(hours);
	    long second = TimeUnit.SECONDS.toSeconds(seconds) -
	                  TimeUnit.DAYS.toSeconds(day) -
	                  TimeUnit.HOURS.toSeconds(hours) - 
	                  TimeUnit.MINUTES.toSeconds(minute);
	    return "Day " + day + " Hour " + hours + " Minute " + minute + " Seconds " + second;
	}
}
