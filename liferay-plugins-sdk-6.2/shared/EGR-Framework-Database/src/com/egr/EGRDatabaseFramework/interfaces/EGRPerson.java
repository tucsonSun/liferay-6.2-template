/*
 * File Name: EGRPerson.java
 * 
 * Created by: Ernesto Rendon on Feb 25, 2012 3:44:09 PM.
 * 
 * Copyright (c) 2011 EGR Software Inc. 3839 E. Cholla St. Phoenix, Arizona,
 * 85028, U.S.A. All rights reserved.
 * 
 * This software is the confidential and proprietary information of EGR Software
 * Inc. You shall not disclose such confidential information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with EGR Software Inc.
 */
package com.egr.EGRDatabaseFramework.interfaces;

import java.util.Date;


/**
 * A instance of class type EGRPerson is used to ...
 * 
 * @author Ernesto Rendon
 * @version 1.0
 * @since 1.0
 */
public interface EGRPerson extends EGRUpdateObject {
//
// WOAPI
//
	
//
// actions
//
	
//
// abstract/interface methods
//
	public Object primaryKeyForObject();
	public void deleteObject();
//
// misc.
//
	public void recordFailedLoginAttempt();
	public void recordSuccessfulLogin();
//
//	accessors
//
	public String firstName();
	public String lastName();
	public String firstNameLastName();
	public String emailAddress();
	
	public String loginId();
	public void setLoginId(String loginId);
	
	public String password();
	public void setPassword(String password);
	
	public Date lastLoginDate();
	public void setLastLoginDate(Date lastLoginDate);
	
	public Date lastFailedLoginDate();
	public void setLastFailedLoginDate(Date failedDate);
	
	public Integer numberBadLoginAttempts();
	public void setNumberBadLoginAttempts(Integer value);
	
	public Boolean isActive();
	public void setIsActive(Boolean active);
}
