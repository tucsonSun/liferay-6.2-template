/*
 * File Name: MyFirstController.java
 * 
 * Created by: Ernesto Rendon on Jun 26, 2015 8:05:13 PM.
 * 
 * Copyright (c) 2015 EGR Software Inc. 3019 E. Cortez, Arizona, 85028, U.S.A.
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information of EGR Software
 * Inc. You shall not disclose such confidential information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with EGR Software Inc.
 */

package com.egr.myFirst.portal;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

/**
 * This class is used as Controller for the VIEW mode of portlet
 * 
 * @author Ernesto Rendon
 */
@Controller
@RequestMapping(value = "VIEW")
public class MyFirstController {

	private static Logger _logger = LoggerFactory.getLogger(MyFirstController.class);

	/**
	 * This is the default render method. This method will be called when the
	 * portlet is first loaded or when the page is refreshed.
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RenderMapping
	public String displayAgent(RenderRequest request, RenderResponse response) {
		
		_logger.trace("Inside default render method of MyFirstController");
		return "view";
	}

}
