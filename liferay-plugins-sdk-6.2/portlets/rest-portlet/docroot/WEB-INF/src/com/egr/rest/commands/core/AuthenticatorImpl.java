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
				Boolean useORAuthenticator = holderObj.getCommandInterface().useORAuthenticator();
				if (useORAuthenticator)
					return executeORAuthenticators(holderObj, user);
				else
					return executeANDAuthenticators(holderObj, user);
			}
		} catch (Exception e) {
			_logger.error(e.toString());
			return false;
		}
	}
	
	/**
	 * Method processes all authenticators with AND logic
	 * @param holderObj
	 * @param user
	 * @return
	 */
	private Boolean executeANDAuthenticators(HolderObj holderObj, User user) {
		List<AuthenticatorInterface> authenticators = holderObj.getGenericRouteInterface().getAuthenticators();
		for (AuthenticatorInterface authenticator : authenticators) {
			boolean isValid = false;
			try {
				isValid = authenticator.authenticate(holderObj);
			} catch (Exception e) {
				_logger.error(e.toString());
				isValid = false;
			}
			
			if (isValid) {
				_logger.info(String.format(authenticator.getClass().getSimpleName()+" authenticator check passed for: uri=%s, user=%s", holderObj.getRoutingUri(), user.getEmailAddress()));
			} else {
				_logger.info(String.format(authenticator.getClass().getSimpleName()+" authenticator check denied for: uri=%s, user=%s", holderObj.getRoutingUri(), user.getEmailAddress()));
				return false;
			}
		}
		return true;
	}

	/**
	 * Method processes all authenticators with OR logic
	 * @param holderObj
	 * @param user
	 * @return
	 */
	private Boolean executeORAuthenticators(HolderObj holderObj, User user) {
		List<AuthenticatorInterface> authenticators = holderObj.getGenericRouteInterface().getAuthenticators();
		for (AuthenticatorInterface authenticator : authenticators) {
			boolean isValid = false;
			try {
				isValid = authenticator.authenticate(holderObj);
			} catch (Exception e) {
				_logger.error(e.toString());
				isValid = false;
			}
			
			if (isValid) {
				_logger.info(String.format(authenticator.getClass().getSimpleName()+" user permission check passed. uri=%s, user=%s", holderObj.getRoutingUri(), user.getEmailAddress()));
				return true;
			} else {
				_logger.info(String.format(authenticator.getClass().getSimpleName()+" user permission check denied. uri=%s, user=%s", holderObj.getRoutingUri(), user.getEmailAddress()));
			}
		}
		return false;
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
