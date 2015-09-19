/*
 * File Name: CommandInterface.java
 * 
 * Created by: Ernesto Rendon on Sep 19, 2015 8:58:10 AM.
 * 
 * Copyright (c) 2015 EGR Software Inc. 3019 E. Cortez, Arizona, 85028, U.S.A.
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information of EGR Software
 * Inc. You shall not disclose such confidential information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with EGR Software Inc.
 */
package com.egr.rest.commands.interfaces;


/**
 * 
 * A instance of class type CommandInterface is used to ...
 * 
 * @author Ernesto Rendon
 * @version 1.0
 */
public interface CommandInterface {
	
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
	public CommandResult execute(ContextInterface context);
	//
	// accessor methods
	//
}
