/*
 * File Name: SimpleRouter.java
 * 
 * Created by: Ernesto Rendon on Sep 20, 2015 3:30:58 PM.
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

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import com.egr.rest.commands.interfaces.RouteInterface;
import com.egr.rest.commands.interfaces.RouterInterface;

/**
 * 
 * A instance of class type SimpleRouter is used to ...
 * 
 * @author Ernesto Rendon
 * @version 1.0
 */
@Component("simpleRouter")
public class SimpleRouter implements RouterInterface {

	@Resource(name = "routes")
	private List<BasicRestRoute> _restRouteList;

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
	 * 
	 * Overrode in order to ...
	 * @see com.egr.rest.commands.interfaces.RouterInterface#getRoutingInfo(java.lang.String, java.lang.String)
	 */
	public RoutingInfo getRoutingInfo(String requestUri, String httpMethod) {

		AntPathMatcher matcher = new AntPathMatcher();
		Map<String, String> pathParams = null;

		for (RouteInterface routeInterface : _restRouteList) {
			String routeUri = routeInterface.getUri();
			if (httpMethod.equals(routeInterface.getHttpMethod().toString()) && matcher.match(routeUri, requestUri)) {
				pathParams = matcher.extractUriTemplateVariables(routeUri, requestUri);
				RoutingInfo routingInfo = new RoutingInfo();
				routingInfo.setRouteInterface(routeInterface);
				routingInfo.setPathParameters(pathParams);
				return routingInfo;
			}
		}

		return null;
	}
	//
	// misc.
	//

	//
	// abstract/interface methods
	//
	
	//
	// accessor methods
	//
	

}
