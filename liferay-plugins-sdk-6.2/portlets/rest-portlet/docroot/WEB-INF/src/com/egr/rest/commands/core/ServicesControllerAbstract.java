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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

import com.egr.rest.commands.interfaces.AuthenticatorInterface;
import com.egr.rest.commands.interfaces.CommandInterface;
import com.egr.rest.commands.interfaces.CommandResult;
import com.egr.rest.commands.interfaces.RouteInterface;
import com.egr.rest.commands.interfaces.RouterInterface;

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
	
	@Value("${services.requests.logging:true}")
	private boolean _logRequests;

	@Autowired
	private ApplicationContext _applicationContext;

	@Autowired
	@Qualifier("simpleAuthenticator")
	private AuthenticatorInterface _authenticatorInterface;
	
	@Autowired
	@Qualifier("simpleRouter")
	private RouterInterface _routerInterface;
	//
	// JAVA API
	//

	//
	// state indicator methods
	//

	//
	// action methods
	//
	/**
	 * Method is a helper that executes the command for GET/DELETE/PUT/POST
	 * @param json
	 * @param routingUri
	 * @param request
	 * @return
	 */
	protected CommandResult process_REST_call_for_JSON_ROUTE(String json, String routingUri, HttpServletRequest request) {
		_logger.trace("Starting command execution handler");

		if (_logRequests) {
			_logger.info(String.format("Processing request begins for %s uri=%s", request.getMethod(), routingUri));
			if (json != null && json.length() > 0) {
				_logger.info(cleanseJson(json));
			}
		}

		RoutingInfo routingInfo = _routerInterface.getRoutingInfo(routingUri, request.getMethod());

		if (routingInfo == null || routingInfo.getRouteInterface() == null || routingInfo.getRouteInterface().getCommandName() == null) {
			return new CommandResult().setSucceeded(false).setMessage(CommandResult.DEFAULT_ROUTE_NOT_FOUND);
		}

		RouteInterface routeInterface = routingInfo.getRouteInterface();
		String commandName = routeInterface.getCommandName();
		Object commandObject = _applicationContext.getBean(commandName);

		if (commandObject == null) {
			_logger.error(String.format("Could not locate command %s for uri=%s", commandName, routingUri));
			return new CommandResult().setSucceeded(false).setMessage("Requested command could not be located");
		}

		if (!(commandObject instanceof CommandInterface)) {
			_logger.error(String.format("Named command %s for uri=%s is not a ICommand object", commandName, routingUri));
			return new CommandResult().setSucceeded(false).setMessage("Requested command could not be located");

		}

		CommandInterface commandInterface = (CommandInterface) commandObject;
		ContextLiferayCommand context = new ContextLiferayCommand(request, routingInfo.getPathParameters());
		CommandResult result = null;

		try {
			if (json != null && json.length() > 0) {
				Object input = convert_JSON_to_JavaObject(json, routeInterface);
				String classFullPathName = routeInterface.getInputClass().getName();
				context.put(classFullPathName, input);
			}
			// NOTE: authentication must occur after parsing of input because
			// some authentication logic will be dependent on what is being
			// created/updated
			HolderObj ho = new HolderObj(request, routingUri, commandName, routeInterface, commandInterface, context);
			boolean authenticated = _authenticatorInterface.authenticate(ho);

			if (!authenticated) {
				_logger.error(String.format("Authentication fails for named command %s for uri=%s", commandName, routingUri));
				return new CommandResult().setSucceeded(false);
			}
			result = commandInterface.execute(context);
		}
		// assumption is that most of the IllegalArgumentExceptions are being thrown by
		// validation logic and that the error messages would be useful to JS code
		// running on the client so send that message on through ....
		catch (IllegalArgumentException e) {
			_logger.error(e.toString());
			return new CommandResult().setSucceeded(false).setMessage(e.getMessage());
		}
		// .... but for all other exceptions send a generic error message
		catch (Exception e) {
			_logger.error(e.toString());
			return new CommandResult().setSucceeded(false);
		}

		if (_logRequests) {
			_logger.debug(String.format("Processing request completes for %s uri=%s", request.getMethod(), routingUri));
		}

		return result;
	}
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
	protected String covert_CommandResult_to_JSON(CommandResult commandResult) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat(JSON_DATE_FORMAT));
		StringWriter sw = new StringWriter();
		try {
			mapper.writeValue(sw, commandResult);
			String json = sw.toString();
			if (_logRequests) {
				_logger.info(String.format("The JSON object returned=%s", cleanseJson(json)));
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
