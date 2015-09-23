/*
 * File Name: ServicesController.java
 * 
 * Created by: Ernesto Rendon on Sep 20, 2015 6:20:01 PM.
 * 
 * Copyright (c) 2015 EGR Software Inc. 3019 E. Cortez, Arizona, 85028, U.S.A.
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information of EGR Software
 * Inc. You shall not disclose such confidential information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with EGR Software Inc.
 */
package com.egr.rest.commands.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * A instance of class type ServicesController is used to ...
 * 
 * @author Ernesto Rendon
 * @version 1.0
 */
@Controller
@RequestMapping("/services")
public class ServicesController extends ServicesControllerAbstract {

	private static final Logger _logger = LoggerFactory.getLogger(ServicesController.class);

	/**
	 * Method handles REST GET or DELETE paths and returns JSON
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/api/**", headers = { "Accept=application/json" }, method = { RequestMethod.GET, RequestMethod.DELETE })
	@ResponseBody
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		_logger.trace("Starting GET or DELETE request handler");
		appendCacheHEADERStoResponse(response);

		CommandOutput<?> commandOutput = process_REST_call_for_JSON_ROUTE(null, stripUriPrefix(request.getRequestURI()), request);
		//TODO: FIX Caching is not implemented
		if (!commandOutput.isCached()) {
			return covert_CommandOutput_to_JSON(commandOutput);
		} else {
			// NOTE: cached results should be a string
			return commandOutput.getData().toString();
		}
	}

	/**
	 * Method handles REST POST or PUT paths and returns JSON
	 * @param json
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/api/**", headers = { "Accept=application/json" }, method = { RequestMethod.POST, RequestMethod.PUT })
	@ResponseBody
	public String execute(@RequestBody String json, HttpServletRequest request, HttpServletResponse response) {
		_logger.trace("Starting POST or PUT request handler");
		appendCacheHEADERStoResponse(response);
		CommandOutput<?> commandOutput = process_REST_call_for_JSON_ROUTE(json, stripUriPrefix(request.getRequestURI()), request);
		return covert_CommandOutput_to_JSON(commandOutput);
	}


}
