/*
 * File Name: WeatherCMD.java 
 *
 * Created by: Ernesto Rendon on Sep 19, 2015 8:54:34 AM.
 *
 * Copyright (c) 2015 EGR Software Inc.
 * 3019 E. Cortez St. Phoenix, Arizona, 85028, U.S.A.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * EGR Software Inc. You shall not disclose such confidential
 * information and shall use it only in accordance with the terms of
 * the license agreement you entered into with EGR Software Inc..
 */
package com.egr.rest.commands;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.egr.external.ws.globalweather.GlobalWeatherSoap;
import com.egr.rest.commands.core.CommandOutput;
import com.egr.rest.commands.interfaces.CommandInputInterface;
import com.egr.rest.commands.interfaces.RouteContextInterface;

/**
 * A instance of class type WeatherCMD is used to ...
 * 
 * @author Ernesto Rendon
 * @version 1.0
 */
@Component("weatherforCityGET")
public class WeatherforCityGET implements CommandInputInterface {
	@Resource
	GlobalWeatherSoap _globalWeatherSoap;

	private static final Logger _logger = LoggerFactory.getLogger(WeatherforCityGET.class);

	//
	// JAVA API
	//

	//
	// state indicator methods
	//

	//
	// action methods
	//

	//
	// misc.
	//

	//
	// abstract/interface methods
	//
	@Override
	public Boolean useORAuthenticator() {
		return false;
	}

	public CommandOutput<String> execute(RouteContextInterface context) {
		try {
			String countryName = context.getEntity("countryName");
			if (countryName == null || countryName.length() == 0) {
				_logger.error("countryName is not allowed to be null");
				return new CommandOutput<String>().setSucceeded(false);
			}
			String cityName = context.getEntity("cityName");
			if (cityName == null || cityName.length() == 0) {
				_logger.error("cityName is not allowed to be null");
				return new CommandOutput<String>().setSucceeded(false);
			}
			countryName = java.net.URLDecoder.decode(countryName, "UTF-8");
			cityName = java.net.URLDecoder.decode(cityName, "UTF-8");
			_logger.info(String.format("Calling SOAP with parm '%s' and '%s'", countryName, cityName));
			String resultXMLStr = _globalWeatherSoap.getWeather(cityName, countryName);			
			return new CommandOutput<String>().setSucceeded(true).setData(resultXMLStr).setIsXMLResult(true);
		} catch (Exception e) {
			_logger.error(this.getClass().getSimpleName()+" failed because... "+e);
			return new CommandOutput<String>().setSucceeded(false);
		}
	}
	//
	// accessor methods
	//

	//
	// inner classes
	//

}
