/*
 * File Name: EGRSelectableObject.java 
 *
 * Created by: Ernesto Rendon on Mar 20, 2012 8:14:45 PM.
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

/**
 * A instance of class type EGRSelectableObject is used to indicate if an object is selected. Typically used for lists...
 * 
 * @author Ernesto Rendon
 * @version 1.0
 * @since 1.0
 */
public interface EGRSelectableObject {
	
//
// WO API
//

//
// state indicator methods
//
	public boolean isSelected();
	public void setIsSelected(boolean isSelected);
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

//
// inner classes
//
	
}
