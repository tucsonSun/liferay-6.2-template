/*
 * File Name: CommandResultInterface.java 
 *
 * Created by: Ernesto Rendon on Sep 23, 2015 10:41:35 AM.
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
package com.egr.rest.commands.interfaces;

import com.egr.rest.commands.core.CommandResult;

/**
 * A instance of class type CommandResultInterface is used to ...
 * 
 * @author Ernesto Rendon
 * @version 1.0
 */
public interface CommandResultInterface {

	public static final String DEFAULT_ROUTE_NOT_FOUND = "Sorry, your requested route could not be determined";
	public static final String DEFAULT_ERROR_MESSAGE = "Whoops, your request caused an error while processing.";
	public static final String DEFAULT_AUTHERROR_MESSAGE = "Sorry, your request is not authorized.";
	public static final String DEFAULT_SUCCESS = "Yippie, your request succeeded.";
	//
	// JAVA API
	//

	//
	// WO API
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
	public boolean isSucceeded();
	public CommandResult setSucceeded(Boolean succeeded);
	
	public Object getData();
	public CommandResult setData(Object data);

	public String getMessage();
	public CommandResult setMessage(String message);

	public boolean isCached();
	public CommandResult setCached(Boolean cached);

	//
	// inner classes
	//

}
