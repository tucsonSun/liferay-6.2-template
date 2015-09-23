/*
 * File Name: AuthenticatorImpl.java
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.egr.rest.commands.interfaces.AuthenticatorInterface;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;

@Component("authenticatorImpl")
public class AuthenticatorImpl implements AuthenticatorInterface {

	private static final Logger _logger = LoggerFactory.getLogger(AuthenticatorImpl.class);

	
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
	 * @see com.egr.rest.commands.interfaces.AuthenticatorInterface#authenticate(com.egr.rest.commands.core.HolderObj)
	 */
	@Override
	public boolean authenticate(HolderObj holderObj) {
		User user = null;
		try {
			user = PortalUtil.getUser(holderObj.getRequest());
			if (user == null) {
				return !holderObj.getGenericRouteInterface().isRouteAuthenticated();
			} else {
				return AND_ALL_Authenticators(holderObj, user);
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
	private boolean AND_ALL_Authenticators(HolderObj holderObj, User user) {
		List<AuthenticatorInterface> authenticators = holderObj.getGenericRouteInterface().getAuthenticators();
		for (AuthenticatorInterface authenticator : authenticators) {
			boolean canAccess = false;
			try {
				canAccess = authenticator.authenticate(holderObj);
			} catch (Exception e) {
				_logger.error(e.toString());
				canAccess = false;
			}
			if (!canAccess) {

				_logger.info(String.format("Request to access uri=%s, method=%s, user=%s, denied by=%s", holderObj.getRoutingUri(), holderObj.getGenericRouteInterface().getHttpMethod().toString(), user.getEmailAddress(), authenticator.getClass().getName()));
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
