/*
 * File Name: EGRPreformanceLoggingSOAPHandler.java
 * 
 * Created by: Ernesto Rendon on Sep 24, 2015 5:38:46 PM.
 * 
 * Copyright (c) 2015 EGR Software Inc. 3019 E. Cortez, Arizona, 85028, U.S.A.
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information of EGR Software
 * Inc. You shall not disclose such confidential information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with EGR Software Inc.
 */
package com.egr.external.ws.performance.SOAP;

import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NodeList;

import com.egr.external.ws.performance.PerformanceLogger;
import com.sun.xml.internal.messaging.saaj.soap.impl.TextImpl;


/**
 * 
 * A instance of class type EGRPreformanceLoggingSOAPHandler is used to log SOAP exection time.
 * 
 * @author Ernesto Rendon
 */
public class EGRPreformanceLoggingSOAPHandler implements SOAPHandler<SOAPMessageContext> {

	protected static Logger _logger = LoggerFactory.getLogger(EGRPreformanceLoggingSOAPHandler.class);
	protected static String LOG_PREFEX = "PERFORMANCE_SERVICE";
	protected static String KEY_PARM2 = "PARAM2";
	
	
	@Override
	public boolean handleMessage(SOAPMessageContext messageContext) {	
		Boolean outboundProperty = (Boolean) messageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		
		//
		// Outbound message
		//
		if (outboundProperty.booleanValue()) {	
			// Set soap outbound parameters
			String parm2 = findOutboundParms(messageContext);
			messageContext.put(KEY_PARM2, parm2);
			// Set timestamp
			String aKey = getUniqueMapKey(messageContext);
			messageContext.put(aKey, System.nanoTime());
			// Set the property scope as the APPLICATION scope
			messageContext.setScope(aKey, MessageContext.Scope.APPLICATION);
		} 
		//
		// Inbound message
		//
		else {
			logServiceCallTime(messageContext);
		}
		return true;
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		return true;
	}

	@Override
	public void close(MessageContext context) {
	}

	@Override
	public Set<QName> getHeaders() {
		return null;
	}
	
	/**
	 * Helper Method will find all the SOAP params for the outbound call and return them as a string.
	 * @param messageContext
	 * @return
	 */
	protected String findOutboundParms(SOAPMessageContext messageContext) {
		try {
			String outBoundParms = "";
			SOAPEnvelope msg = messageContext.getMessage().getSOAPPart().getEnvelope();
			 // get soap body from soap message
			SOAPBody body = msg.getBody();
			NodeList bodyChildrenNodes = body.getChildNodes();
			
			SOAPElement serviceNode = (SOAPElement)bodyChildrenNodes.item(0);
			SOAPElement argumentParentNode = (SOAPElement)serviceNode.getFirstChild();
			NodeList argumentNodes = argumentParentNode.getChildNodes();
			for (int i = 0; i < argumentNodes.getLength(); i++) {
				SOAPElement se = (SOAPElement)argumentNodes.item(i);
				TextImpl value = (TextImpl)se.getFirstChild();
	        	//System.out.println(se.getLocalName()+" "+value.getData());
	        	String nextDelimiter = (i+1 < argumentNodes.getLength()) ? ", " : "";
	        	outBoundParms = outBoundParms + value.getData()+nextDelimiter;
			}
			return outBoundParms;
		} catch (Exception e) {
			//unable to process logging. But dont want to print out exception....
		}
		return "NA";
	}
	
	/**
	 * Helper Method will perform the logging of the SOAP call. 
	 * @param messageContext
	 */
	protected void logServiceCallTime(SOAPMessageContext messageContext) {
		if (messageContext == null) {
			return; //do nothing if messageContext is null
		}
    
		try {			
			QName opn = (QName) messageContext.get(MessageContext.WSDL_OPERATION);
			String wsdlOperation = opn.getLocalPart();
		    String aKey = getUniqueMapKey(messageContext);
		    Long startTimeInNano = (Long) messageContext.get(aKey);
		    String parm2 = (String) messageContext.get(KEY_PARM2);
		    
		    PerformanceLogger pl = new PerformanceLogger(_logger, LOG_PREFEX);
			pl.logPerformanceTime(startTimeInNano, wsdlOperation.trim(), parm2);

		} catch (Exception e) {
			System.out.println("EGRPreformanceLoggingSOAPHandler.logServiceCallTime: e = " + e);
			//unable to process logging. But dont want to print out exception....
		}
	}
	
	/**
	 * Method will generate a keyString based on the SOAP service name
	 * @param messageContext
	 * @return 
	 */
	protected String getUniqueMapKey(SOAPMessageContext messageContext) {
		QName svcn = (QName) messageContext.get(MessageContext.WSDL_SERVICE);
		String wsdlService = svcn.getLocalPart();
		QName opn = (QName) messageContext.get(MessageContext.WSDL_OPERATION);
		String wsdlOperation = opn.getLocalPart();
		String uniqueKey =  wsdlService.trim()+"-"+wsdlOperation.trim();
		return uniqueKey;
	}

}
