/*
 * File Name: CommandResult.java
 * 
 * Created by: Ernesto Rendon on Sep 19, 2015 10:40:38 AM.
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

import com.egr.rest.commands.interfaces.CommandResultInterface;

/**
 * 
 * A instance of class type CommandResult is used to ...
 * 
 * @author Ernesto Rendon
 * @version 1.0
 * @param <T>
 */
public class CommandResult<T> implements CommandResultInterface<T> {

	private Boolean _succeeded = true;
	private T _data = null;
	private String _message = "";
	private Boolean _cached = false;

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
	public boolean isSucceeded() {
		return _succeeded;
	}

	public CommandResult<T> setSucceeded(Boolean succeeded) {
		_succeeded = succeeded;

		if (succeeded == null || !_succeeded)
			setMessage(CommandResultInterface.DEFAULT_ERROR_MESSAGE);
		else if (_succeeded)
			setMessage(CommandResultInterface.DEFAULT_SUCCESS);

		return this;
	}

	public T getData() {
		return _data;
	}

	public CommandResult<T> setData(T data) {
		_data = data;
		return this;
	}

	public String getMessage() {
		return _message;
	}

	public CommandResult<T> setMessage(String message) {
		_message = message;
		return this;
	}

	public boolean isCached() {
		return _cached;
	}

	public CommandResult<T> setCached(Boolean cached) {
		_cached = cached;
		return this;
	}
}
