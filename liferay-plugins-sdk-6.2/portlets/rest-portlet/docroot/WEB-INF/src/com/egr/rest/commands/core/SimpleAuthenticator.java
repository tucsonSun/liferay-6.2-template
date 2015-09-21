/*
 * File Name: SimpleAuthenticator.java
 * 
 * Created by: Ernesto Rendon on Sep 20, 2015 5:29:12 PM.
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

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.egr.rest.commands.interfaces.AuthenticatorInterface;
import com.egr.rest.commands.interfaces.CommandInterface;
import com.egr.rest.commands.interfaces.ContextInterface;
import com.egr.rest.commands.interfaces.RouteInterface;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;

@Component("simpleAuthenticator")
public class SimpleAuthenticator implements AuthenticatorInterface {

	private static final Logger _logger = LoggerFactory.getLogger(SimpleAuthenticator.class);

	
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
	 * @see com.egr.rest.commands.interfaces.AuthenticatorInterface#authenticate(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String, com.egr.rest.commands.interfaces.RouteInterface, com.egr.rest.commands.interfaces.CommandInterface, com.egr.rest.commands.interfaces.ContextInterface)
	 */
	@Override
	public boolean authenticate(HttpServletRequest request, String uri, String commandName, RouteInterface route, CommandInterface command, ContextInterface context) {
		User user = null;
		try {
			user = PortalUtil.getUser(request);
			if (user == null) {
				return !route.isRouteAuthenticated();
			} else {
				return processALLAuthenticators_AND(request, uri, commandName, route, command, context, user);
			}
		} catch (Exception e) {
			_logger.error(e.toString());
			return false;
		}
	}

	/**
	 * Method will run thru all authenticators and make sure they are all TRUE. Basically AND logic.
	 * @param request
	 * @param uri
	 * @param commandName
	 * @param route
	 * @param command
	 * @param context
	 * @param user
	 * @return
	 */
	private boolean processALLAuthenticators_AND(HttpServletRequest request, String uri, String commandName, RouteInterface route, CommandInterface command, ContextInterface context, User user) {
		List<AuthenticatorInterface> authenticators = route.getAuthenticators();
		for (AuthenticatorInterface authenticator : authenticators) {
			boolean canAccess = false;
			try {
				canAccess = authenticator.authenticate(request, uri, commandName, route, command, context);
			} catch (Exception e) {
				_logger.error(e.toString());
				canAccess = false;
			}
			if (!canAccess) {

				_logger.info(String.format("Request to access uri=%s, method=%s, user=%s, denied by=%s", uri, route.getHttpMethod().toString(), user.getEmailAddress(), authenticator.getClass().getName()));
				return false;
			}
		}
		return true;
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
