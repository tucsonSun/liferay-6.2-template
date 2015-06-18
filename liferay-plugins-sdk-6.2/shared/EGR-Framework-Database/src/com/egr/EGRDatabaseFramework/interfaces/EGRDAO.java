/*
 * File Name: EGRDAO.java
 * 
 * Created by: Ernesto Rendon on Jun 17, 2015 7:36:08 PM.
 * 
 * Copyright (c) 2015 EGR Software Inc. 3019 E. Cortez, Arizona, 85028, U.S.A.
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information of EGR Software
 * Inc. You shall not disclose such confidential information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with EGR Software Inc.
 */
package com.egr.EGRDatabaseFramework.interfaces;

import java.util.ArrayList;

import com.egr.EGRDatabaseFramework.EGRDatabaseObject;

/**
 * 
 * A instance of class type EGRDAO is used to ...
 * 
 * @author Ernesto Rendon
 * @version 1.0
 */
public interface EGRDAO {
	
	public boolean insert(EGRDatabaseObject object)  throws Exception;
	
	public boolean delete(EGRDatabaseObject object)  throws Exception;
	
	public boolean update(EGRDatabaseObject object)  throws Exception;
	
	public boolean insert(ArrayList<EGRDatabaseObject> list)  throws Exception;
	
	public boolean delete(ArrayList<EGRDatabaseObject> list)  throws Exception;
	
	public boolean update(ArrayList<EGRDatabaseObject> list)  throws Exception;
}
