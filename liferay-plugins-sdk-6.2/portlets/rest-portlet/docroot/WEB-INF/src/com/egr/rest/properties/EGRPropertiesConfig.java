/*
 * File Name: EGRPropertiesConfig.java
 * 
 * Created by: Ernesto Rendon on Sep 19, 2015 4:35:40 PM.
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

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.liferay.portal.kernel.util.PropsUtil;

/**
 * 
 * A instance of class type EGRPropertiesConfig is used to configuration class to load 
 * the properties files is specified in Liferay portal-ext.properties 
 * 
 * @author Ernesto Rendon
 * @version 1.0
 */
@Configuration
public class EGRPropertiesConfig implements IPropertiesConstants {
	
	private static Logger _logger = LoggerFactory.getLogger(EGRPropertiesConfig.class);
	
	@Bean
	public PropertyPlaceholderConfigurer cpmProperties() {
		String pathToProperties = PropsUtil.get(PROPFILE__LIFERAY_TOMCAT);
		if (pathToProperties == null) {
			throw new IllegalArgumentException("pathToProperties not found in portal-ext.properties");
		}
		File f = new File(pathToProperties);
		if (!f.exists()) {
			throw new IllegalArgumentException("File not found for '"+PROPFILE__LIFERAY_TOMCAT+"' in path="+pathToProperties);
		}
		PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
		FileSystemResource fsr = new FileSystemResource(f);
		Resource[] resources = new FileSystemResource[] {fsr};
		ppc.setLocations(resources);
		
		_logger.info("loading properties file '"+PROPFILE__LIFERAY_TOMCAT+"'");
		return ppc;
	}

}
