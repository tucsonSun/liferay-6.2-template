/*
 * File Name: EGRUpdateObject.java 
 *
 * Created by: Ernesto Rendon on Aug 16, 2012 6:43:46 AM.
 *
 * Copyright (c) 2011 EGR Software Inc.
 * 3839 E. Cholla St. Phoenix, Arizona, 85028, U.S.A.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * EGR Software Inc. You shall not disclose such confidential
 * information and shall use it only in accordance with the terms of
 * the license agreement you entered into with EGR Software Inc..
 */
package com.egr.EGRDatabaseFramework.interfaces;

import java.util.Date;

/**
 * A instance of class type EGRUpdateObject is used to ...
 * 
 * @author Ernesto Rendon
 * @version 1.0
 * @since 1.0
 */
public interface EGRUpdateObject {

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
	public String personName();
//
// abstract/interface methods
//

//
// accessor methods
//
	public String modifiedBy();
	public void setModifiedBy(String personName);
	
	public Date modifiedDate();
	public void setModifiedDate(Date date);
	
	
	public String createdBy();
	public void setCreatedBy(String personName);
	
	public Date createdDate();
	public void setCreatedDate(Date date);
//
// inner classes
//
}
