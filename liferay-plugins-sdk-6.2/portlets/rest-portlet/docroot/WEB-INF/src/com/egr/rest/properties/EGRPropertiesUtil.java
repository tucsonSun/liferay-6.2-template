/*
 * File Name: EGRPropertiesUtil.java
 * 
 * Created by: Ernesto Rendon on Sep 19, 2015 5:12:50 PM.
 * 
 * Copyright (c) 2015 EGR Software Inc. 3019 E. Cortez, Arizona, 85028, U.S.A.
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information of EGR Software
 * Inc. You shall not disclose such confidential information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with EGR Software Inc.
 */
package com.egr.rest.properties;

import java.io.FileInputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liferay.portal.kernel.util.PropsUtil;

/**
 * 
 * A instance of class type EGRPropertiesUtil is used to load properties files
 * 
 * @author Ernesto Rendon
 */
public class EGRPropertiesUtil implements IPropertiesConstants {

	private static Properties _properties;
	private static Logger _logger = LoggerFactory.getLogger(EGRPropertiesUtil.class);

	/**
	 * 
	 * Constructor
	 * EGRPropertiesUtil
	 */
	public EGRPropertiesUtil() {
		super();//Empty constructor needed for mock testing
		load();
	}
	
	//
	// state indicator methods
	//

	//
	// action methods
	//
	public static boolean load() {
		String pathToProperties = PropsUtil.get(PROPFILE__LIFERAY_TOMCAT);
		if (pathToProperties == null) {
			throw new IllegalArgumentException("CPM property file setting not in portal-ext.properties");
		}
		try {
			_properties.load(new FileInputStream(pathToProperties));
			return true;
		} catch (Exception e) {
			_logger.error("Error while loading the properties file '"+PROPFILE__LIFERAY_TOMCAT+"' " + e.getMessage());
		}
		return false;
	}
	//
	// misc.
	//
	public static String getStringProperty(String key) {
		return getProperties().getProperty(key);
	}
	
	public static String getStringPropertyWithDefault(String key, String defaultValue) {
		String value = getStringProperty(key);
		return value != null ? value : defaultValue;
	}
	
	/**
	 * Method converts string value to TRUE/true to Boolean true else defaults to Boolean False
	 * @param key
	 * @return Boolean
	 */
	public static Boolean getBooleanProperty(String key) {
		try {
			String value = getProperties().getProperty(key);
			return Boolean.parseBoolean(value);
		} catch (Exception e) {
			return false;
		}
	}
	
	public static Boolean getBooleanPropertyWithDefault(String key, Boolean defaultValue) {
		Boolean value = getBooleanProperty(key);
		return value != null ? value : defaultValue;
	}
	//
	// abstract/interface implementation 
	//

	//
	// accessor methods
	//
	public static Properties getProperties() {
		if (_properties == null) {
			_properties = new Properties();
			load();
		}
		_logger.info("loading properties file '"+PROPFILE__LIFERAY_TOMCAT+"'");
		return _properties;
	}
	public static void setProperties(Properties properties) {_properties = properties;}
	//
	// inner classes
	//
}
