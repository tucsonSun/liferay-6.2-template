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
import com.egr.rest.commands.model.WeatherModel;
import com.liferay.portal.model.User;

/**
 * A instance of class type WeatherCMD is used to ...
 * 
 * @author Ernesto Rendon
 * @version 1.0
 */
@Component("weatherPOST")
public class WeatherPOST implements CommandInputInterface {
	
	@Resource(name="globalWeatherSoapId")
	GlobalWeatherSoap _globalWeatherSoap;
	
	private static final Logger _logger = LoggerFactory.getLogger(WeatherPOST.class);
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
	
	public CommandOutput<WeatherModel> execute(RouteContextInterface context) {
		try {
			User user = context.getEntity("USER");
			WeatherModel weatherModel = context.getEntity(WeatherModel.class.getName());

			return new CommandOutput<WeatherModel>().setSucceeded(true).setData(weatherModel).setIsXMLResult(true);
		} catch (Exception e) {
			_logger.error(e.toString());
			return new CommandOutput<WeatherModel>().setSucceeded(false);
		}
	}
	//
	// accessor methods
	//

	//
	// inner classes
	//

}
