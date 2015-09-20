/*
 * File Name: RouteInterface.java
 * 
 * Created by: Ernesto Rendon on Sep 19, 2015 1:44:45 PM.
 * 
 * Copyright (c) 2015 EGR Software Inc. 3019 E. Cortez, Arizona, 85028, U.S.A.
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information of EGR Software
 * Inc. You shall not disclose such confidential information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with EGR Software Inc.
 */
package com.egr.rest.commands.interfaces;

import java.util.List;

/**
 * 
 * A instance of class type RouteInterface is used to ...
 * 
 * @author Ernesto Rendon
 * @version 1.0
 */
public interface RouteInterface {

	public enum HttpMethod { GET, DELETE, PUT, POST }

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

	//
	// accessor methods
	//
	public String getCommandName();
	public void setCommandName(String commandName);
	
	public String getUri();
	public void setUri(String uri);

	public HttpMethod getHttpMethod();
	public void setHttpMethod(HttpMethod httpMethod);

	public Class<?> getInputClass();
	public void setInputClass(Class<?> inputClass);
	
	public String getInputClassName();
	public void setInputClassName(String inputClassName);

	public List<AuthenticatorInterface> getAuthenticators();
	public void setAuthenticators(List<AuthenticatorInterface> authenticators);
	
	Boolean isRouteAuthenticated();
	public void setIsRouteAuthenticated(Boolean isRouteAuthenticated);
}