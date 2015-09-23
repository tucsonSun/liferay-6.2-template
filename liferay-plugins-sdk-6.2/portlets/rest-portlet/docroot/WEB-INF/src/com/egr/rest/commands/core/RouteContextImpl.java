/*
 * File Name: RouteContextImpl.java
 * 
 * Created by: Ernesto Rendon on Sep 19, 2015 1:59:01 PM.
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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.egr.rest.commands.interfaces.RouteContextInterface;

/**
 * 
 * A instance of class type RouteContextImpl is used to ...
 * 
 * @author Ernesto Rendon
 * @version 1.0
 */
@SuppressWarnings({ "serial", "rawtypes" })
public class RouteContextImpl extends HashMap implements RouteContextInterface {

	private static Logger _logger = LoggerFactory.getLogger(RouteContextImpl.class);
	private HttpServletRequest _request;
	private HashSet _updates = new HashSet();
	/**
	 * 
	 * Constructor
	 * @param request
	 * @param pathParameters
	 */
	@SuppressWarnings("unchecked")
	public RouteContextImpl(HttpServletRequest request, Map<String, String> pathParameters) {
		_request = request;
		super.put("_request", request);
		super.put("_session", request.getSession());
		super.put("_application", request.getSession().getServletContext());
		if (pathParameters != null) {
			super.put("_pathParameters", pathParameters);
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
	/**
	 * Method.... 
	 */
	public void unload() {
		Set updates = getUpdates();
		for (Iterator iter = updates.iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			Object value = super.get(key);
			if (value == null)
				continue;
			_request.setAttribute(key, value);
		}
	}
	/**
	 * 
	 * Overrode in order to ...
	 * @see java.util.HashMap#get(java.lang.Object)
	 */
	public Object get(Object key) {
		Object object = super.get(key);
		if (object != null)
			return object;

		Map pathParameters = (Map) super.get("_pathParameters");
		object = pathParameters.get(key);
		if (object != null) {
			return object;
		}

		object = _request.getAttribute(key.toString());
		if (object != null)
			return object;

		object = _request.getParameterValues(key.toString());
		if (object != null) {
			String[] array = (String[]) object;
			if (array.length > 1)
				return array;
			else
				return array[0];
		}

		object = _request.getSession().getAttribute(key.toString());
		if (object != null)
			return object;

		object = _request.getSession().getServletContext().getAttribute(key.toString());
		return object;
	}
	
	/**
	 * 
	 * Overrode in order to ...
	 * @see java.util.HashMap#put(java.lang.Object, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public Object put(Object key, Object value) {
		_updates.add(key);
		return super.put(key, value);
	}
	//
	// abstract/interface methods
	//
	/**
	 * 
	 * Overrode in order to ...
	 * @see com.egr.rest.commands.interfaces.RouteContextInterface#getEntity(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getEntity(String key) {
		Object entity = get(key);
		if (entity != null) {
			try {
				return (T) entity;
			} catch (ClassCastException e) {
				_logger.error("Cannot cast %s to desired type in context", entity.getClass().getName());
				return null;
			}
		} else {
			return null;
		}
	}
	//
	// accessor methods
	//
	public HttpServletRequest getRequest() {
		return _request;
	}

	public Set getUpdates() {
		return _updates;
	}
}
