/*
 * File Name: EGRDatabaseObject.java
 * 
 * Created by: Ernesto Rendon on Feb 25, 2012 3:40:34 PM.
 * 
 * Copyright (c) 2011 EGR Software Inc. 3839 E. Cholla St. Phoenix, Arizona,
 * 85028, U.S.A. All rights reserved.
 * 
 * This software is the confidential and proprietary information of EGR Software
 * Inc. You shall not disclose such confidential information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with EGR Software Inc.
 */

package com.egr.EGRDatabaseFramework;



import java.util.Date;

import javax.xml.bind.ValidationException;

import com.egr.EGRDatabaseFramework.interfaces.EGRSelectableObject;
import com.egr.EGRDatabaseFramework.interfaces.EGRUpdateObject;

/**
 * A java abstract class that represents a EOGenericRecord database record. A EOGenericRecord
 * is a generic enterprise object class that implements the EOEnterpriseObject interface to 
 * provide the basic enterprise object behavior. 
 * 
 * This class should be the base class for all database objects.
 * 
 * @author Ernesto Rendon
 * @version 1.0
 * @since 1.0
 */
public abstract class EGRDatabaseObject implements EGRSelectableObject {

	/**
	 * Implements EGRSelectableObject interface
	 */
	protected boolean _isSelected;
	
	/**
	 * Constructor
	 */
	public EGRDatabaseObject() {
        super();
	}


//
// WO API
//
	/** Override of method updates create information when EGRDatabaseObject is type EGRUpdateObject. **/
	public void awakeFromInsertion() {
		//
		// Update created info for EGRUpdateObject
		//
		if (this instanceof EGRUpdateObject) {
			EGRUpdateObject udpateObject = (EGRUpdateObject)this;
			udpateObject.setCreatedBy(udpateObject.name());
			udpateObject.setCreatedDate(new Date());
		}
	}	

	/** Override of method updates modified information when EGRDatabaseObject is type EGRUpdateObject. **/
	public void validateForSave() throws ValidationException {
		//
		// Update created info for EGRUpdateObject
		//
		if (this instanceof EGRUpdateObject) {
			EGRUpdateObject udpateObject = (EGRUpdateObject)this;
			if (udpateObject.createdBy() == null) udpateObject.setCreatedBy(udpateObject.name());
			if (udpateObject.createdDate() == null) udpateObject.setCreatedDate(new Date());
			
			udpateObject.setModifiedBy(udpateObject.name());
			udpateObject.setModifiedDate(new Date());
		}
	}
//
// state indicator methods
//		
	/**
	 * Method returns true of the EOEnterprise Object has not been saved to the database and still has a temporary globalID.
	 * Else the EOEnterprise Object has a globalID and has been saved to the database.
	 */
	public boolean isTemporary() {
		return primaryKeyForObject() != null;
	}
//
// action methods
//	

	
//
// misc.
//
	
//	/**
//	 * Method returns the next sequence number for the specified eoModelName and sequenceName.
//	 * @param sequenceName
//	 * @param eoModelName
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	public static Integer nextValueForSequenceNamed(String eoModelName, String sequenceName) {
//		DatabaseType databaseType = EGRDatabaseConnection.getDatabaseTypeFromSystemProperties();
//		//find databaseType		
//		NSArray<NSMutableDictionary<String, Integer>> resultList = null;
//		String sqlString = null;
//		EOEditingContext ec = new EOEditingContext();
//		int nextval = -9999;
//		switch (databaseType) {        
//		    case POSTGRESQL: 	
//				sqlString = "SELECT NEXTVAL ('"+sequenceName+"')";
//				resultList = EOUtilities.rawRowsForSQL(ec, eoModelName, sqlString, new NSArray<String>("NEXTVAL"));
//				return resultList.objectAtIndex(0).objectForKey("NEXTVAL");
//				
//		    case ORACLE: 
//				sqlString = "SELECT "+sequenceName+".NEXTVAL FROM DUAL";
//				resultList = EOUtilities.rawRowsForSQL(ec, eoModelName, sqlString, new NSArray<String>("NEXTVAL"));
//				return resultList.objectAtIndex(0).objectForKey("NEXTVAL");
//				
//		    case OPENBASE: 		
//				sqlString =  "SELECT PK FROM EO_PK_TABLE WHERE NAME='"+sequenceName+"' FOR UPDATE";
//				resultList = EOUtilities.rawRowsForSQL(ec, eoModelName, sqlString, new NSArray<String>("NEXTVAL"));
//				nextval = resultList.objectAtIndex(0).objectForKey("NEXTVAL").intValue() + 1;
//				EOUtilities.rawRowsForSQL(ec, eoModelName, "UPDATE EO_PK_TABLE SET PK = "+nextval+" WHERE NAME = '"+sequenceName+"'", null);
//				return resultList.objectAtIndex(0).objectForKey("NEXTVAL");
//		    	
//		    case SYBASE:  		
//				sqlString =  "SELECT PK FROM EO_PK_TABLE HOLDLOCK WHERE NAME='"+sequenceName+"'";
//				resultList = EOUtilities.rawRowsForSQL(ec, eoModelName, sqlString, new NSArray<String>("NEXTVAL"));
//				nextval = resultList.objectAtIndex(0).objectForKey("NEXTVAL").intValue() + 1;
//				EOUtilities.rawRowsForSQL(ec, eoModelName, "UPDATE EO_PK_TABLE SET PK = "+nextval+" WHERE NAME = '"+sequenceName+"'", null);
//				return resultList.objectAtIndex(0).objectForKey("NEXTVAL");
//		    	
//		    case SQLSERVER: 	
//				sqlString =  "SELECT PK FROM EO_PK_TABLE WITH (UPDLOCK,ROWLOCK) WHERE NAME='"+sequenceName+"'";
//				resultList = EOUtilities.rawRowsForSQL(ec, eoModelName, sqlString, new NSArray<String>("NEXTVAL"));
//				nextval = resultList.objectAtIndex(0).objectForKey("NEXTVAL").intValue() + 1;
//				EOUtilities.rawRowsForSQL(ec, eoModelName, "UPDATE EO_PK_TABLE SET PK = "+nextval+" WHERE NAME = '"+sequenceName+"'", null);
//				return resultList.objectAtIndex(0).objectForKey("NEXTVAL");
//    
//		    default:	
//		    	throw new IllegalArgumentException("Unsupported database type'"+databaseType+"' for launch argument '-"+EGRDatabaseConnection.DB_DATABASE_TYPE+"'.");
//		}
//	}
				
//
// abstract/interface methods
//
	public abstract void refetchThisObject();
	public abstract Object primaryKeyForObject();
	public abstract void deleteObject();
	public boolean isSelected() {return _isSelected;}
	public void setIsSelected(boolean isSelected) {_isSelected=isSelected;}
//
// accessor methods
//
	
//
// inner classes
//	
}
