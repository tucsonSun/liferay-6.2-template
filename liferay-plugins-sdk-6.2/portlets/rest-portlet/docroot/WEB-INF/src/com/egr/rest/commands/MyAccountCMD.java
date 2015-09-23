/*
 * File Name: MyAccountCMD.java
 * 
 * Created by: Ernesto Rendon on Sep 19, 2015 3:42:19 PM.
 * 
 * Copyright (c) 2015 EGR Software Inc. 3019 E. Cortez, Arizona, 85028, U.S.A.
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information of EGR Software
 * Inc. You shall not disclose such confidential information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with EGR Software Inc.
 */
package com.egr.rest.commands;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.egr.rest.commands.core.CommandResult;
import com.egr.rest.commands.interfaces.CommandInterface;
import com.egr.rest.commands.interfaces.RouteContextInterface;
import com.egr.rest.commands.model.WeatherModel;
import com.egr.rest.commands.perms.UserPermissionInfo;
import com.liferay.portal.model.User;

/**
 * 
 * A instance of class type MyAccountCMD is used to ...
 * 
 * @author Ernesto Rendon
 * @version 1.0
 */
@Component("myAccountCMD")
public class MyAccountCMD implements CommandInterface {

	public static final String REQUEST_KEY ="_request";
	public static final String ACCOUNTS_SESSION_KEY = "__accounts__";
	private static final Logger _logger = LoggerFactory.getLogger(MyAccountCMD.class);
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
	@SuppressWarnings("unchecked")
	public CommandResult execute(RouteContextInterface context) {

		User user = context.getEntity(RouteContextInterface.USER);
		Validate.notNull(user, "MyAccounts requires a logged in user");

		try {
			String emailAddress = user.getEmailAddress();
			_logger.info("emailAddress= "+emailAddress);
			HttpServletRequest request = context.getEntity(REQUEST_KEY);
			UserPermissionInfo userPermissionInfo = UserPermissionInfo.getUserPermissionInfo(request);
			context.put("id", emailAddress);
			HttpSession session = context.getEntity("_session");

			if (session != null) {
				_logger.info("session obj= "+session.toString());
				session.setAttribute(ACCOUNTS_SESSION_KEY, "0123456789");
			}
			List<WeatherModel> someList = new ArrayList<WeatherModel>();

			return new CommandResult().setSucceeded(true).setData(someList);

		} catch (Exception e) {
			_logger.error(e.toString());
			return new CommandResult().setSucceeded(false);
		}
	}
	//
	// accessor methods
	//

	//
	// inner classes
	//
}
