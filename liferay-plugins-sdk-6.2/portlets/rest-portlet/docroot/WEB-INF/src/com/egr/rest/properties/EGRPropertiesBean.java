/*
 * File Name: EGRPropertiesBean.java
 * 
 * Created by: Ernesto Rendon on Sep 19, 2015 3:03:11 PM.
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;


/**
 * 
 * A instance of class type EGRPropertiesBean is used to load properties files.
 * 
 * @author Ernesto Rendon
 * @version 1.0
 */
@Configuration
public class EGRPropertiesBean implements IPropertiesConstants {
	
	private static Logger _logger = LoggerFactory.getLogger(EGRPropertiesBean.class);
	
	@Bean(name="uiMessagesProperties")
	public ResourceBundleMessageSource setResourceBundle(){
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames(new String[]{PROP_FILE__UI_MESSAGES, PROPFILE__UI_DISPLAY});
		
		_logger.info("loading properties files '"+PROP_FILE__UI_MESSAGES+"'  '"+PROPFILE__UI_DISPLAY+"'");
		return messageSource;
	}
	
	@Bean(name="someCommonProperties")
	public PropertiesFactoryBean loadCommonProperties(){
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource(PROPFILE__SOME_COMMON));
		
		_logger.info("loading properties file '"+PROPFILE__SOME_COMMON+"'");
		return propertiesFactoryBean;
	}
}
