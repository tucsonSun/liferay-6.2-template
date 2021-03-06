/*
 * File Name: HasLiferayPermission.java
 * 
 * Created by: Ernesto Rendon on Sep 19, 2015 5:21:32 PM.
 * 
 * Copyright (c) 2015 EGR Software Inc. 3019 E. Cortez, Arizona, 85028, U.S.A.
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information of EGR Software
 * Inc. You shall not disclose such confidential information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with EGR Software Inc.
 */
package com.egr.rest.commands.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.egr.rest.commands.core.HolderObj;
import com.egr.rest.commands.interfaces.AuthenticatorInterface;
import com.egr.rest.commands.interfaces.RouteContextInterface;
import com.egr.rest.commands.perms.ServicesUtil;
import com.egr.rest.commands.perms.UserPermissionInfo;
import com.liferay.portal.model.User;

/**
 * 
 * A instance of class type HasLiferayPermission is used to ...
 * 
 * @author Ernesto Rendon
 * @version 1.0
 */
public class HasLiferayPermission implements AuthenticatorInterface {

	private static final Logger _logger = LoggerFactory.getLogger(HasLiferayPermission.class);

	private String _portlet = "unknown";
	private String _permission = "unknown";

	@Override
	public boolean authenticate(HolderObj holderObj) {

		/*
		 * In the current implementation permissions are granted through
		 * site roles -- thus the permission checks need a site id. This logic
		 * is invoked only for authenticated users -- if the site id is not in
		 * session determine the site id and save it in session
		 */
		User user = holderObj.getRouteContextInterface().getEntity(RouteContextInterface.USER);

		if (user == null) {
			_logger.error("Liferay permission checker finds no user in request");
			return false;
		}

		boolean canAccess = false;
		try {
			UserPermissionInfo userPermissionInfo = UserPermissionInfo.getUserPermissionInfo(holderObj.getRequest());
			canAccess = userPermissionInfo.hasPermission(_portlet, _permission);
		} catch (Exception e) {
			_logger.error(e.toString());
			canAccess = false;
		} finally {
			ServicesUtil.disablePermissionChecker();
		}

		if (!canAccess) {
			_logger.info(String.format("Liferay permission check fails, uri=%s, user=%s, portlet=%s, perm=%s", holderObj.getRoutingUri(), user.getEmailAddress(), _portlet, _permission));
		}
		return canAccess;
	}

	public String getPortlet() {
		return _portlet;
	}

	public void setPortlet(String portlet) {
		this._portlet = portlet;
	}

	public String getPermission() {
		return _permission;
	}

	public void setPermission(String permission) {
		this._permission = permission;
	}

}
