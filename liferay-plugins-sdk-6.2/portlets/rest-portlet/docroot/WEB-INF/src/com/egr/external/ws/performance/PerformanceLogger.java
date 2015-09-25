/*
 * File Name: PerformanceLogger.java
 * 
 * Created by: Ernesto Rendon on Sep 24, 2015 5:33:57 PM.
 * 
 * Copyright (c) 2015 EGR Software Inc. 3019 E. Cortez, Arizona, 85028, U.S.A.
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information of EGR Software
 * Inc. You shall not disclose such confidential information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with EGR Software Inc.
 */
package com.egr.external.ws.performance;


import java.util.Hashtable;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
/**
 * A instance of class type PerformanceLogger is used to log performance.
 * 
 * @author Ernesto Rendon
 */
public class PerformanceLogger {	
	
	
	public Logger _logger;
	public String _loggerPrefex;
	/**
	 * 
	 * Constructor
	 * PerformanceLogger
	 * @param logger
	 * @param loggerPrefex
	 */
	public PerformanceLogger(Logger logger, String loggerPrefex) {
		setLogger(logger);
		setLoggerPrefex(loggerPrefex);
	}



	//
	// state indicator methods
	//

	//
	// action methods
	//

	//
	// misc.
	//
	/**
	 * Method implements logging in a standard performance format
	 * @param parm1
	 * @param parm2
	 * @param diffInMilli
	 */
	public void logPerformanceTime(Long startTimeInNano, String parm1, Hashtable<String, String> parm2) {
		String parm2Output = "";
		Iterator<String> iterator = parm2.keySet().iterator();
		String nextDelimiter = "";

		while (iterator.hasNext()) {
			String key = iterator.next();
			String value = parm2.get(key);
			nextDelimiter = iterator.hasNext() ? ", " : "";
			parm2Output = parm2Output + (key + "= " + value + nextDelimiter);
		}

		logPerformanceTimeFactory(startTimeInNano, parm1, parm2Output);
	}
	/**
	 * Method implements logging in a standard performance format
	 * @param parm1
	 * @param parm2
	 * @param diffInMilli
	 */
	public void logPerformanceTime(Long startTimeInNano, String parm1, String parm2) {
		logPerformanceTimeFactory(startTimeInNano, parm1, parm2);
	}
	/**
	 * Method factory implements logging in a standard performance format
	 * @param startTimeInNano
	 * @param parm1
	 * @param parm2
	 */
	protected void logPerformanceTimeFactory(Long startTimeInNano, String parm1, String parm2) {
		Long diff = null;
		Long diffInMilli = null;
		String output = "";
		try {
			//calculated time	
			diff = System.nanoTime() - startTimeInNano;
			diffInMilli = TimeUnit.NANOSECONDS.toMillis(diff);
		}
		catch (Exception e) {}

		if (diffInMilli == null)
			output = "\t"+String.format("\t%s \t%s \t%s", parm1, parm2, "NA");		
		else
			output = "\t"+String.format("\t%s \t%s \t%s", parm1, parm2, diffInMilli);
		
		Marker LOGGER_PREFX = MarkerFactory.getMarker(getLoggerPrefex());
		getLogger().debug(LOGGER_PREFX + output);
	}
	//
	// abstract/interface implementation 
	//
	public Logger getLogger() {return _logger;}
	public void setLogger(Logger logger) {_logger = logger;}

	public String getLoggerPrefex() {return _loggerPrefex;}
	public void setLoggerPrefex(String loggerPrefex) {_loggerPrefex = loggerPrefex;}

	//
	// accessor methods
	//

}
