/*
 * File Name: EGRHandlerResolver.java 
 *
 * Created by: Ernesto Rendon on May 29, 2015 1:56:11 PM.
 *
 * Copyright � CopperPoint Mutual Insurance.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * CopperPoint Mutual Insurance and its successors and assigns. ("Confidential Information").  
 * You shall not disclose such Confidential Information to any third party and shall use it 
 * only in accordance with the terms of the license or other written agreement you entered into
 * with CopperPoint Mutual Insurance.
 *
 */
package com.egr.external.ws.performance.SOAP;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;

/**
 * 
 * A instance of class type EGRPreformanceLoggingSOAPHandler is used to load Handlers into SOAP calls.
 * 
 * @author Ernesto Rendon
 */
public class EGRHandlerResolver implements HandlerResolver {

	
	/**
	 * Overrode in order to load custom handlers.
	 * @see javax.xml.ws.handler.HandlerResolver#getHandlerChain(javax.xml.ws.handler.PortInfo)
	 */
	@SuppressWarnings("rawtypes")
	public List<Handler> getHandlerChain(PortInfo portInfo) {
		List<Handler> handlerChain = new ArrayList<Handler>();
		EGRPreformanceLoggingSOAPHandler aHandler = new EGRPreformanceLoggingSOAPHandler();
		handlerChain.add(aHandler);
		return handlerChain;
	}
}
