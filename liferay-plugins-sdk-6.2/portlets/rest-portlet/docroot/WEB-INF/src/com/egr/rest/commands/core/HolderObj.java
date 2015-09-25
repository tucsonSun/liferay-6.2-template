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

import com.egr.rest.commands.interfaces.CommandInputInterface;
import com.egr.rest.commands.interfaces.GenericRouteInterface;
import com.egr.rest.commands.interfaces.RouteContextInterface;

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
	protected GenericRouteInterface _genericRouteInterface;
	protected CommandInputInterface _commandInputInterface;
	protected RouteContextInterface _routeContextInterface;
	
	/**
	 * Constructor
	 * @param request
	 * @param routingUri
	 * @param commandName
	 * @param genericRouteInterface
	 * @param commandInputInterface
	 * @param context
	 */
	public HolderObj(HttpServletRequest request, String routingUri, String commandName, GenericRouteInterface genericRouteInterface, CommandInputInterface commandInputInterface, RouteContextInterface context) {
		setRequest(request);
		setRoutingUri(routingUri);
		setCommandName(commandName);
		setGenericRouteInterface(genericRouteInterface);
		setCommandInputInterface(commandInputInterface);
		setRouteContextInterface(context);
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
	public GenericRouteInterface getGenericRouteInterface() {
		return _genericRouteInterface;
	}
	public void setGenericRouteInterface(GenericRouteInterface genericRouteInterface) {
		_genericRouteInterface = genericRouteInterface;
	}
	public CommandInputInterface getCommandInputInterface() {
		return _commandInputInterface;
	}
	public void setCommandInputInterface(CommandInputInterface commandInputInterface) {
		_commandInputInterface = commandInputInterface;
	}
	public RouteContextInterface getRouteContextInterface() {
		return _routeContextInterface;
	}
	public void setRouteContextInterface(RouteContextInterface routeContextInterface) {
		_routeContextInterface = routeContextInterface;
	}
	//
	// inner classes
	//

}
