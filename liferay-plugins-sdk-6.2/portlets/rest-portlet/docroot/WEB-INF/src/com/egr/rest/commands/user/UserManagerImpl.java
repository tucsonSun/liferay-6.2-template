/*
 * File Name: UserManagerImpl.java 
 *
 * Created by: Ernesto Rendon on Sep 19, 2015 4:58:22 PM.
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
package com.egr.rest.commands.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;

/**
 * A instance of class type UserManagerImpl is used to ...
 * 
 * @author Ernesto Rendon
 * @version 1.0
 */
@Service
public class UserManagerImpl implements UserManagerInterface {
	private static Logger _logger = LoggerFactory.getLogger(UserManagerImpl.class);

	@Value("#{someCommonProperties['org.role.prefix']}")
	private String orgRolePrefix;


	/**
	 * Method checks if the user is OMNI Admin
	 * @see com.egr.rest.commands.user.UserManagerInterface#isUserOMNIAdmin(com.liferay.portal.model.User)
	 */
	public boolean isUserOMNIAdmin(User user) {
		if (user == null)
			return false;
		try {
			PrincipalThreadLocal.setName(user.getUserId());
			PermissionChecker permissionChecker = PermissionCheckerFactoryUtil.create(user);
			PermissionThreadLocal.setPermissionChecker(permissionChecker);
			return permissionChecker.isOmniadmin();
		} catch (Exception e) {
			_logger.error("Method isUserOMNIAdmin failed to determine if user is OMNI Admin because... " + e);
		} finally {
			if (null != PermissionThreadLocal.getPermissionChecker()) {
				PermissionThreadLocal.setPermissionChecker(null);
			}
		}
		return false;
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

	//
	// inner classes
	//

}
