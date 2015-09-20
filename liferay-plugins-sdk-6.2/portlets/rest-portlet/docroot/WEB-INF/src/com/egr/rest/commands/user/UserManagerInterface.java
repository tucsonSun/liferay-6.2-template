/*
 * File Name: UserManagerInterface.java 
 *
 * Created by: Ernesto Rendon on Sep 19, 2015 4:55:54 PM.
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

import java.io.Serializable;

import com.liferay.portal.model.User;

/**
 * A instance of class type UserManagerInterface is used to ...
 * 
 * @author Ernesto Rendon
 * @version 1.0
 */
public interface UserManagerInterface  extends Serializable {


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
	/**
	 * Method checks  if the user is OMNI
	 */
	public boolean isUserOMNIAdmin(User user);
	//
	// accessor methods
	//

	//
	// inner classes
	//

}
