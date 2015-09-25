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
import com.egr.rest.commands.interfaces.CommandInputInterface;
import com.egr.rest.commands.interfaces.GenericRouteInterface;
import com.egr.rest.commands.interfaces.GenericRouterListInterface;

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
	@Qualifier("authenticatorImpl")
	private AuthenticatorInterface _authenticatorInterface;
	
	@Autowired
	@Qualifier("genericRouterListContainerImpl")
	private GenericRouterListInterface _genericRouterListContainerInterface;
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
	 * Method is a helper that executes the command for GET/DELETE/PUT/POST and returns a CommandOutput object
	 * @param jsonInput
	 * @param routingUri
	 * @param request
	 * @return CommandOutput
	 */
	protected CommandOutput<?> process_REST_call_for_JSON_ROUTE(String jsonInput, String routingUri, HttpServletRequest request) {
		_logger.trace("Starting command execution handler");

		if (_logRequests) {
			_logger.info(String.format("Processing request begins for %s uri=%s", request.getMethod(), routingUri));
			if (jsonInput != null && jsonInput.length() > 0) {
				_logger.info(stripPasswordsFromJson(jsonInput));
			}
		}

		RoutingInfo routingInfo = _genericRouterListContainerInterface.getRoutingInfo(routingUri, request.getMethod());

		if (routingInfo == null || routingInfo.getGenericRouteInterface() == null || routingInfo.getGenericRouteInterface().getCommandName() == null) {
			return new CommandOutput<Object>().setSucceeded(false).setMessage(CommandOutput.DEFAULT_ROUTE_NOT_FOUND);
		}

		GenericRouteInterface genericRouteInterface = routingInfo.getGenericRouteInterface();
		String commandName = genericRouteInterface.getCommandName();
		Object commandObject = _applicationContext.getBean(commandName);

		if (commandObject == null) {
			_logger.error(String.format("A error happend. CommandInput for '%s' and uri=%s not found.", commandName, routingUri));
			return new CommandOutput<Object>().setSucceeded(false).setMessage(CommandOutput.DEFAULT_ROUTE_NOT_FOUND);
		}

		if (!(commandObject instanceof CommandInputInterface)) {
			_logger.error(String.format("A error happend. CommandInput named '%s' for uri=%s is not a instanceof CommandInputInterface", commandName, routingUri));
			return new CommandOutput<Object>().setSucceeded(false).setMessage(CommandOutput.DEFAULT_ROUTE_NOT_FOUND);

		}

		CommandInputInterface commandInputInterface = (CommandInputInterface) commandObject;
		RouteContextLiferay routeContext = new RouteContextLiferay(request, routingInfo.getPathParameters());
		CommandOutput<?> result = null;

		try {
			if (jsonInput != null && jsonInput.length() > 0) {
				Object input = convert_JSON_to_JavaBean(jsonInput, genericRouteInterface);
				String classFullPathName = genericRouteInterface.getInputClass().getName();
				routeContext.put(classFullPathName, input);
			}
			// NOTE: the authentication occurs after parsing of input because
			// some authentication logic will be dependent on what is being created/updated
			HolderObj ho = new HolderObj(request, routingUri, commandName, genericRouteInterface, commandInputInterface, routeContext);
			boolean authenticated = _authenticatorInterface.authenticate(ho);

			if (!authenticated) {
				_logger.error(String.format("Authentication fails for named command %s for uri=%s", commandName, routingUri));
				return new CommandOutput<Object>().setSucceeded(false).setMessage(CommandOutput.DEFAULT_NOT_AUTHORIZED_MESSAGE);
			}
			result = commandInputInterface.execute(routeContext);
		}
		// assumption is that most of the IllegalArgumentExceptions are being thrown by
		// validation logic and that the error messages would be useful to JS code
		// running on the client so send that message on through ....
		catch (IllegalArgumentException e) {
			_logger.error(e.toString());
			return new CommandOutput<Object>().setSucceeded(false).setMessage(e.getMessage());
		}
		// .... but for all other exceptions send a generic error message
		catch (Exception e) {
			_logger.error(e.toString());
			return new CommandOutput<Object>().setSucceeded(false);
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
	protected static String stripPasswordsFromJson(String json) {
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
	 * @param genericRouteInterface
	 * @return
	 * @throws Exception
	 */
	protected Object convert_JSON_to_JavaBean(String json, GenericRouteInterface genericRouteInterface) throws Exception {
		Class<?> clazz = genericRouteInterface.getInputClass();
		clazz = (clazz != null ? clazz : Object.class);
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat(JSON_DATE_FORMAT));
		return mapper.readValue(json, clazz);
	}

	/**
	 * Method converts CommandOutput to JSON
	 * NOTE: SimpleDateFormat is not thread safe so set a new one each time
	 * @param commandOutput
	 * @param logRequests
	 * @return
	 */
	protected String covert_CommandOutput_to_JSON(CommandOutput<?> commandOutput) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat(JSON_DATE_FORMAT));
		StringWriter sw = new StringWriter();
		try {
			mapper.writeValue(sw, commandOutput);
			String json = sw.toString();
			if (_logRequests) {
				_logger.info(String.format("The JSON object returned=%s", stripPasswordsFromJson(json)));
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
