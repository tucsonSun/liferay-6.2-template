/*
 * File Name: RouteContextLiferay.java
 * 
 * Created by: Ernesto Rendon on Sep 20, 2015 4:18:54 PM.
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

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.egr.rest.commands.interfaces.RouteContextInterface;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

/**
 * 
 * A instance of class type RouteContextLiferay is used to ...
 * 
 * @author Ernesto Rendon
 * @version 1.0
 */
@SuppressWarnings("serial")
public class RouteContextLiferay extends RouteContextImpl {
	
   private static Logger _logger = LoggerFactory.getLogger(RouteContextLiferay.class);

   /**
    * 
    * Constructor
    * @param request
    * @param pathParameters
    */
   public RouteContextLiferay(HttpServletRequest request, Map<String,String> pathParameters) {
	   super(request,pathParameters);
	   
	   ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
	   if (themeDisplay != null) {
		  super.put(WebKeys.THEME_DISPLAY,themeDisplay);
	   }
	   //
	   // Put the user and companyId into the context
	   //
	   try {
	       User user = PortalUtil.getUser(request);
	       Long companyId = PortalUtil.getCompanyId(request);
	       
	       if (user != null) {
	         super.put(RouteContextInterface.USER, user);
	       }
	       super.put(RouteContextInterface.COMPANY_ID, companyId);
	      
	   } catch (Exception e) {
		   _logger.error("Constructor failed because \n"+e);
	   }
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
}
