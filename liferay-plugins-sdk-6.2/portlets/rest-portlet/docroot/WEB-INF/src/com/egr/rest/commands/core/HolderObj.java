/*
 * File Name: HolderObj.java 
 *
 * Created by: Ernesto Rendon on Sep 20, 2015 6:56:19 PM.
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

import javax.servlet.http.HttpServletRequest;

import com.egr.rest.commands.interfaces.CommandInterface;
import com.egr.rest.commands.interfaces.ContextInterface;
import com.egr.rest.commands.interfaces.RouteInterface;

/**
 * A instance of class type HolderObj is used to ...
 * 
 * @author Ernesto Rendon
 * @version 1.0
 */
public class HolderObj {
	
	protected HttpServletRequest _request;
	protected String _routingUri;
	protected String _commandName;
	protected RouteInterface _routeInterface;
	protected CommandInterface _commandInterface;
	protected ContextInterface _contextInterface;
	
	/**
	 * Constructor
	 * @param request
	 * @param routingUri
	 * @param commandName
	 * @param routeInterface
	 * @param commandInterface
	 * @param context
	 */
	public HolderObj(HttpServletRequest request, String routingUri, String commandName, RouteInterface routeInterface, CommandInterface commandInterface, ContextInterface context) {
		setRequest(request);
		setRoutingUri(routingUri);
		setCommandName(commandName);
		setRouteInterface(routeInterface);
		setCommandInterface(commandInterface);
		setContextInterface(context);
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

	//
	// accessor methods
	//
	public HttpServletRequest getRequest() {
		return _request;
	}
	public void setRequest(HttpServletRequest request) {
		_request = request;
	}
	public String getRoutingUri() {
		return _routingUri;
	}
	public void setRoutingUri(String routingUri) {
		_routingUri = routingUri;
	}
	public String getCommandName() {
		return _commandName;
	}
	public void setCommandName(String commandName) {
		_commandName = commandName;
	}
	public RouteInterface getRouteInterface() {
		return _routeInterface;
	}
	public void setRouteInterface(RouteInterface routeInterface) {
		_routeInterface = routeInterface;
	}
	public CommandInterface getCommandInterface() {
		return _commandInterface;
	}
	public void setCommandInterface(CommandInterface commandInterface) {
		_commandInterface = commandInterface;
	}
	public ContextInterface getContextInterface() {
		return _contextInterface;
	}
	public void setContextInterface(ContextInterface contextInterface) {
		_contextInterface = contextInterface;
	}
	//
	// inner classes
	//

}