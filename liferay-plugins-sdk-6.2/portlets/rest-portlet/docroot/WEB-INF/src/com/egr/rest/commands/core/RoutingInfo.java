/*
 * File Name: RoutingInfo.java
 * 
 * Created by: Ernesto Rendon on Sep 19, 2015 1:47:17 PM.
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

import java.util.Map;

import com.egr.rest.commands.interfaces.RouteInterface;

/**
 * 
 * A instance of class type RoutingInfo is used to ...
 * 
 * @author Ernesto Rendon
 * @version 1.0
 */
public class RoutingInfo {

	private Map<String, String> _pathParameters;
	private RouteInterface _routeInterface;

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
	public RouteInterface getRouteInterface() {
		return _routeInterface;
	}

	public void setRouteInterface(RouteInterface routeInterface) {
		_routeInterface = routeInterface;
	}
	
	public Map<String, String> getPathParameters() {
		return _pathParameters;
	}

	public void setPathParameters(Map<String, String> pathParameters) {
		_pathParameters = pathParameters;
	}
}
