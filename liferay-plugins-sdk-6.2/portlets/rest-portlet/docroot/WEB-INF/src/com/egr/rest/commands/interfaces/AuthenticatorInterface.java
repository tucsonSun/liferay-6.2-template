/*
 * File Name: AuthenticatorInterface.java
 * 
 * Created by: Ernesto Rendon on Sep 19, 2015 1:28:46 PM.
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

import com.egr.rest.commands.core.HolderObj;

/**
 * 
 * A instance of class type AuthenticatorInterface is used to ...
 * 
 * @author Ernesto Rendon
 * @version 1.0
 */
public interface AuthenticatorInterface {
	
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
	public boolean authenticate(HolderObj holderObj);
	//
	// accessor methods
	//
}
