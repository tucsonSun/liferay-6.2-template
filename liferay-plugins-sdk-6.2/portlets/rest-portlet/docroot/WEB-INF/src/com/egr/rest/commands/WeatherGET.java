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
import com.egr.rest.commands.core.ServicesControllerAbstract;
import com.egr.rest.commands.interfaces.CommandInputInterface;
import com.egr.rest.commands.interfaces.RouteContextInterface;
import com.egr.rest.commands.model.WeatherModel;

/**
 * A instance of class type WeatherCMD is used to ...
 * 
 * @author Ernesto Rendon
 * @version 1.0
 */
@Component("weatherGET")
public class WeatherGET implements CommandInputInterface {
	@Resource
	GlobalWeatherSoap _globalWeatherSoap;
	
	private static final Logger _logger = LoggerFactory.getLogger(WeatherGET.class);
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

		String list = _globalWeatherSoap.getCitiesByCountry("United States");
		_logger.info("cities= "+list);
		
		String jsonResult = ServicesControllerAbstract.convert_XML_to_JSON(list);
		_logger.info(jsonResult);
		
		String abc = _globalWeatherSoap.getWeather("Phoenix", "United States");
		
		WeatherModel weatherModel = new WeatherModel();
		weatherModel.setStatus("this is the GET");
		
		return new CommandOutput<WeatherModel>().setSucceeded(true).setData(weatherModel);
//
//		} catch (Exception e) {
//			_logger.error(ServicesUtil.exceptionToString(e));
//			return new CommandOutput().setSucceeded(false);
//		}
	}
	//
	// accessor methods
	//

	//
	// inner classes
	//

}
