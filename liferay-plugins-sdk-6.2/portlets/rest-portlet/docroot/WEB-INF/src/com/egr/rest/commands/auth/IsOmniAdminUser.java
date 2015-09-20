/*
 * File Name: IsOmniAdminUser.java 
 *
 * Created by: Ernesto Rendon on Sep 19, 2015 1:16:51 PM.
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
package com.egr.rest.commands.auth;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.copperpoint.services.core.commands.ICommandKeys;
import com.egr.rest.commands.interfaces.AuthenticatorInterface;
import com.egr.rest.commands.interfaces.CommandInterface;
import com.egr.rest.commands.interfaces.ContextInterface;
import com.egr.rest.commands.interfaces.RouteInterface;
import com.egr.rest.commands.user.UserManagerImpl;
import com.liferay.portal.model.User;

/**
 * A instance of class type IsOmniAdminUser is used to ...
 * 
 * @author Ernesto Rendon
 * @version 1.0
 */
public class IsOmniAdminUser implements AuthenticatorInterface {

	private static final Logger _logger = LoggerFactory.getLogger(IsOmniAdminUser.class);

	@Autowired
	UserManagerImpl _userManager;

	//
	// JAVA API
	//

	@Override
	public boolean authenticate(HttpServletRequest request, String uri, String commandName, RouteInterface route, CommandInterface command, ContextInterface context) {

		User user = context.getEntity(ICommandKeys.USER);
		Long companyId = (Long) context.getEntity(ICommandKeys.COMPANY_ID);
		if (user == null) {
			_logger.info("user not found in context");
			return false;
		}
		if (companyId == null) {
			_logger.info("companyId not found in context");
			return false;
		}

		boolean isUserOMNIAdmin = _userManager.isUserOMNIAdmin(user);

		if (isUserOMNIAdmin) {
			_logger.info(String.format(IsOmniAdminUser.class.getSimpleName()+" user permission check passed. uri=%s, user=%s", uri, user.getEmailAddress()));
			return true;
		} else {
			_logger.info(String.format(IsOmniAdminUser.class.getSimpleName()+" user permission check failied. uri=%s, user=%s", uri, user.getEmailAddress()));
			return false;
		}
	}

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

	//
	// inner classes
	//

}