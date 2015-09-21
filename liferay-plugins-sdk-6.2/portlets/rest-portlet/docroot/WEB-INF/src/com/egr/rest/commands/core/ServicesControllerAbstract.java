/*
 * File Name: ServicesControllerAbstract.java 
 *
 * Created by: Ernesto Rendon on Sep 20, 2015 5:47:48 PM.
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
package com.egr.rest.commands.core;

import java.io.StringWriter;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.egr.rest.commands.interfaces.CommandResult;
import com.egr.rest.commands.interfaces.RouteInterface;

/**
 * A instance of class type ServicesControllerAbstract is used to ...
 * 
 * @author Ernesto Rendon
 * @version 1.0
 */
public abstract class ServicesControllerAbstract {
	
	private static final Logger _logger = LoggerFactory.getLogger(ServicesControllerAbstract.class);
	private static final String URI_PREFIX = "/delegate/services/api";
	private static final String PASSWORD_REGEX = "[A-Za-z0-9\\d!@#$%^&()\\[\\]{}\\-_]*?";
	private static final String JSON_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
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
	/**
	 * Method adds cache buster headers to response
	 * @param response
	 */
	public void appendCacheHEADERStoResponse(HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0); // Proxies.
	}

	/**
	 * Method removes fixed portion of the URI when matching on URLs to find the correct commands
	 * NOTE looking for string "/delegate/services/api"
	 * @param uri
	 * @return
	 */
	protected String stripUriPrefix(String uri) {
		int ndx = uri.indexOf(URI_PREFIX);
		if (ndx == -1) {
			return uri;
		}
		return uri.substring(ndx + URI_PREFIX.length());
	}
	
	/**
	 * Method removes sensitive values from JSON before it is output to the logs
	 * @param json
	 * @return
	 */
	protected static String cleanseJson(String json) {
		String value = json.replaceAll("\"password\":\"" + PASSWORD_REGEX + "\"", "\"password\":\"xxxxxx\"");
		value = value.replaceAll("\"confirmPassword\":\"" + PASSWORD_REGEX + "\"", "\"confirmPassword\":\"xxxxxx\"");
		return value;
	}

	/**
	 * Method converts JSON to Java Object. 
	 * NOTE: Not using built-in spring converter because want ability to capture/trace any
	 * errors here as opposed nested errors inside of spring code.
	 * NOTE: SimpleDateFormat is not thread safe so set a new one each time
	 * @param json
	 * @param routeInterface
	 * @return
	 * @throws Exception
	 */
	protected Object convert_JSON_to_JavaObject(String json, RouteInterface routeInterface) throws Exception {
		Class<?> clazz = routeInterface.getInputClass();
		clazz = (clazz != null ? clazz : Object.class);
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat(JSON_DATE_FORMAT));
		return mapper.readValue(json, clazz);
	}

	/**
	 * Method converts CommandResult to JSON
	 * NOTE: SimpleDateFormat is not thread safe so set a new one each time
	 * @param commandResult
	 * @param logRequests
	 * @return
	 */
	protected String covert_CommandResult_to_JSON(CommandResult commandResult, Boolean logRequests) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat(JSON_DATE_FORMAT));
		StringWriter sw = new StringWriter();
		try {
			mapper.writeValue(sw, commandResult);
			String json = sw.toString();
			if (logRequests) {
				_logger.info(String.format("JSON returned=%s", cleanseJson(json)));
			}
			return json;
		} catch (Exception e) {
			_logger.error(e.toString());
			return String.format("{ 'success' : false, 'data' : null, 'message' : '%s' }", e.getMessage());
		}
	}

	//
	// abstract/interface methods
	//

	//
	// accessor methods
	//

	//
	// inner classes
	//

}
