/*
 * File Name: SimpleRoute.java
 * 
 * Created by: Ernesto Rendon on Sep 20, 2015 1:41:47 PM.
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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;

import com.egr.rest.commands.interfaces.AuthenticatorInterface;
import com.egr.rest.commands.interfaces.RouteInterface;

/**
 * 
 * A instance of class type SimpleRoute is used to ...
 * 
 * @author Ernesto Rendon
 * @version 1.0
 */
public class SimpleRoute implements RouteInterface {

	private String _uri;
	private HttpMethod _httpMethod;
	private String _commandName;
	private Class<?> _inputClass;
	private Boolean _isRouteAuthenticated = true;
	private List<AuthenticatorInterface> _authenticators = new ArrayList<AuthenticatorInterface>();

	/**
	 * 
	 * Constructor
	 */
	public SimpleRoute() {
		super();
	}

	/**
	 * 
	 * Constructor
	 * @param uri
	 * @param httpMethod
	 * @param commandName
	 */
	public SimpleRoute(String uri, HttpMethod httpMethod, String commandName) {
		Validate.notNull(uri, "URI cannot be null in route definition");
		Validate.notNull(httpMethod, "HttpMethod cannot be null in route definition");
		Validate.notNull(commandName, "Command name now allowed to be null in route definition");
		_uri = uri;
		_httpMethod = httpMethod;
		_commandName = commandName;
	}

	/**
	 * 
	 * Constructor used for REST routes that accept input objects via the route.
	 * NOTE: That class name will be used to store input in command context
	 * @param uri
	 * @param httpMethod
	 * @param commandName
	 * @param inputClass
	 */
	public SimpleRoute(String uri, HttpMethod httpMethod, String commandName, Class<?> inputClass) {
		this(uri, httpMethod, commandName);
		Validate.notNull(inputClass);
		_inputClass = inputClass;
	}
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
	public String getUri() {
		return _uri;
	}

	@Override
	public void setUri(String uri) {
		_uri = uri;
	}

	@Override
	public HttpMethod getHttpMethod() {
		return _httpMethod;
	}

	@Override
	public void setHttpMethod(HttpMethod httpMethod) {
		_httpMethod = httpMethod;
	}

	public void setMethod(String method) {
		_httpMethod = HttpMethod.valueOf(method);
	}

	@Override
	public String getCommandName() {
		return _commandName;
	}

	@Override
	public void setCommandName(String commandName) {
		_commandName = commandName;
	}

	@Override
	public Class<?> getInputClass() {
		return _inputClass;
	}

	@Override
	public void setInputClass(Class<?> inputClass) {
		_inputClass = inputClass;
	}

	@Override
	public List<AuthenticatorInterface> getAuthenticators() {
		return _authenticators;
	}

	@Override
	public void setAuthenticators(List<AuthenticatorInterface> authenticators) {
		_authenticators = authenticators;
	}

	@Override
	public Boolean isRouteAuthenticated() {
		return _isRouteAuthenticated;
	}

	@Override
	public void setIsRouteAuthenticated(Boolean isRouteAuthenticated) {
		_isRouteAuthenticated = isRouteAuthenticated;
	}
	//
	// accessor methods
	//
}
