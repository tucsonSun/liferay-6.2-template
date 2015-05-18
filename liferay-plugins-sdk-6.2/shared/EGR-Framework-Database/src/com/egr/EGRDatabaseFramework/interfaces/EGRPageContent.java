/*
 * File Name: EGRPageContent.java 
 *
 * Created by: Ernesto Rendon on Nov 7, 2012 9:10:34 PM.
 *
 * Copyright (c) 2013 EGR Software Inc.
 * 3839 E. Cholla St. Phoenix, Arizona, 85028, U.S.A.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * EGR Software Inc. You shall not disclose such confidential
 * information and shall use it only in accordance with the terms of
 * the license agreement you entered into with EGR Software Inc..
 */
package com.egr.EGRDatabaseFramework.interfaces;

/**
 * A instance of class type EGRPageContent is used to ...
 * 
 * @author Ernesto Rendon
 * @version 1.0
 */
public interface EGRPageContent {
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
// accessor methods
//
	public Integer fiscalYear();
	public void setFiscalYear(Integer fiscalYear);
	
	public String sectionName();
	public void setSectionName(String value);
	
	public String woComponentName();
	public void setWoComponentName(String woComponentName);
	
	public String value();
	public void setValue(String value);
//
// inner classes
//

}
