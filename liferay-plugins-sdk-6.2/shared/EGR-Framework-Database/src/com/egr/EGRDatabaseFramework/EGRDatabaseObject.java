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

import com.egr.EGRDatabaseFramework.EGRDatabaseConnection.DatabaseType;
import com.egr.EGRDatabaseFramework.interfaces.EGRSelectableObject;
import com.egr.EGRDatabaseFramework.interfaces.EGRUpdateObject;
import com.webobjects.eoaccess.EOUtilities;
import com.webobjects.eocontrol.EOClassDescription;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.eocontrol.EOGlobalID;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSLog;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;
import com.webobjects.foundation.NSTimestamp;

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
public abstract class EGRDatabaseObject extends com.webobjects.eocontrol.EOGenericRecord implements EGRSelectableObject {

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

	/**
	 * Constructor
	 * @param classDescription
	 */
	public EGRDatabaseObject(EOClassDescription classDescription) {
		super(classDescription);
	}
//
// WO API
//
	/** Override of method updates create information when EGRDatabaseObject is type EGRUpdateObject. **/
	public void awakeFromInsertion(EOEditingContext ec) {
		super.awakeFromInsertion(ec);
		//
		// Update created info for EGRUpdateObject
		//
		if (this instanceof EGRUpdateObject) {
			EGRUpdateObject udpateObject = (EGRUpdateObject)this;
			udpateObject.setCreatedBy(udpateObject.personName());
			udpateObject.setCreatedDate(new NSTimestamp());
		}
	}	

	/** Override of method updates modified information when EGRDatabaseObject is type EGRUpdateObject. **/
	public void validateForSave() throws ValidationException {
		//
		// Update created info for EGRUpdateObject
		//
		if (this instanceof EGRUpdateObject) {
			EGRUpdateObject udpateObject = (EGRUpdateObject)this;
			if (udpateObject.createdBy() == null) udpateObject.setCreatedBy(udpateObject.personName());
			if (udpateObject.createdDate() == null) udpateObject.setCreatedDate(new NSTimestamp());
			
			udpateObject.setModifiedBy(udpateObject.personName());
			udpateObject.setModifiedDate(new NSTimestamp());
		}
		super.validateForSave();
	}
//
// state indicator methods
//	
	/**
	 * Method checks if there are any values in the EOEditingContext different from the committed snapshot saved in the database.
	 * If variable dictionaryOfChanges is greater then 0 that means this.editingContext() has changes that haven't been saved/committed.
	 */
	public boolean isEOEditingContextSnapshotDirty() {
		NSDictionary<?,?> dictionaryOfChanges=changesFromSnapshot(editingContext().committedSnapshotForObject(this));
		return dictionaryOfChanges.count() > 0;
	}
	
	/**
	 * Method returns true of the EOEnterprise Object has not been saved to the database and still has a temporary globalID.
	 * Else the EOEnterprise Object has a globalID and has been saved to the database.
	 */
	public boolean isTemporary() {
		EOGlobalID eoGlobalId = editingContext().globalIDForObject(this);
		return (eoGlobalId.isTemporary());
	}
//
// action methods
//	
	/**
	 * Method discards the snapshots for the object identified by the EOGlobalID in the editingContext.
	 * This causes the object to be refetched the next time the object is accessed. 
	 * @return void
	 */
	public void refetchThisObject() { editingContext().invalidateObjectsWithGlobalIDs(new NSArray<EOGlobalID>(editingContext().globalIDForObject(this))); }
	
    /**
     * Method discards the snapshots for the NSArray of objects identified by the EOGlobalID in the editingContext.
	 * This causes the objects to be refetched the next time the objects is accessed. 
	 * 
 	 * @param eoArray the NSArray to be invalidated.
 	 * @return void
     */
 	public static void refetchThisArray(EOEditingContext ec, NSArray<? extends EOEnterpriseObject> eoArray) {
 		for (EOEnterpriseObject aObject : eoArray) {
 			ec.invalidateObjectsWithGlobalIDs(new NSArray<EOGlobalID>(ec.globalIDForObject(aObject)));
		}
 	}
	
//
// misc.
//
	/**
	 * Method uses the EOUtilities to give the primary key array for a given enterprise object.
	 * NOTE: Method assumes that the primary key is type Object (maybe it's safe to assume type Numeric????)
	 * NOTE: Method assumes that there is only 1 key value for the primary key dictionary. (Primary keys are returned in a dictionary.)
	 */
	public Object primaryKeyForObject() {
        //you don't have an EC! Bad EO. We can do nothing.
        if (editingContext() == null) throw new IllegalArgumentException("EOEditingContext is not allowed to be null. Please insert the EGRDatabaseObject into a editingContext. ");
        //no primaryKey assigned yet
        if (isTemporary()) throw new IllegalArgumentException("EGRDatabaseObject has not been saved to the database. No primaryKey assigned yet. ");
		NSDictionary<String, Object> primaryKeyDictionary = EOUtilities.primaryKeyForObject(editingContext(), this);
		NSArray<Object> primaryKeyArray = primaryKeyDictionary.allValues();
		if (primaryKeyArray.count() == 1) return primaryKeyArray.objectAtIndex(0);
		else if (primaryKeyArray.count() > 1) { 
			NSLog.err.appendln("EGRDatabaseObject.primaryKey: Found more than one primary key for object. " + primaryKeyArray); 
			return null;
		}
		else return null;
	}
	
	/**
	 * Method returns the next sequence number for the specified eoModelName and sequenceName.
	 * @param sequenceName
	 * @param eoModelName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Integer nextValueForSequenceNamed(String eoModelName, String sequenceName) {
		DatabaseType databaseType = EGRDatabaseConnection.getDatabaseTypeFromSystemProperties();
		//find databaseType		
		NSArray<NSMutableDictionary<String, Integer>> resultList = null;
		String sqlString = null;
		EOEditingContext ec = new EOEditingContext();
		int nextval = -9999;
		switch (databaseType) {        
		    case POSTGRESQL: 	
				sqlString = "SELECT NEXTVAL ('"+sequenceName+"')";
				resultList = EOUtilities.rawRowsForSQL(ec, eoModelName, sqlString, new NSArray<String>("NEXTVAL"));
				return resultList.objectAtIndex(0).objectForKey("NEXTVAL");
				
		    case ORACLE: 
				sqlString = "SELECT "+sequenceName+".NEXTVAL FROM DUAL";
				resultList = EOUtilities.rawRowsForSQL(ec, eoModelName, sqlString, new NSArray<String>("NEXTVAL"));
				return resultList.objectAtIndex(0).objectForKey("NEXTVAL");
				
		    case OPENBASE: 		
				sqlString =  "SELECT PK FROM EO_PK_TABLE WHERE NAME='"+sequenceName+"' FOR UPDATE";
				resultList = EOUtilities.rawRowsForSQL(ec, eoModelName, sqlString, new NSArray<String>("NEXTVAL"));
				nextval = resultList.objectAtIndex(0).objectForKey("NEXTVAL").intValue() + 1;
				EOUtilities.rawRowsForSQL(ec, eoModelName, "UPDATE EO_PK_TABLE SET PK = "+nextval+" WHERE NAME = '"+sequenceName+"'", null);
				return resultList.objectAtIndex(0).objectForKey("NEXTVAL");
		    	
		    case SYBASE:  		
				sqlString =  "SELECT PK FROM EO_PK_TABLE HOLDLOCK WHERE NAME='"+sequenceName+"'";
				resultList = EOUtilities.rawRowsForSQL(ec, eoModelName, sqlString, new NSArray<String>("NEXTVAL"));
				nextval = resultList.objectAtIndex(0).objectForKey("NEXTVAL").intValue() + 1;
				EOUtilities.rawRowsForSQL(ec, eoModelName, "UPDATE EO_PK_TABLE SET PK = "+nextval+" WHERE NAME = '"+sequenceName+"'", null);
				return resultList.objectAtIndex(0).objectForKey("NEXTVAL");
		    	
		    case SQLSERVER: 	
				sqlString =  "SELECT PK FROM EO_PK_TABLE WITH (UPDLOCK,ROWLOCK) WHERE NAME='"+sequenceName+"'";
				resultList = EOUtilities.rawRowsForSQL(ec, eoModelName, sqlString, new NSArray<String>("NEXTVAL"));
				nextval = resultList.objectAtIndex(0).objectForKey("NEXTVAL").intValue() + 1;
				EOUtilities.rawRowsForSQL(ec, eoModelName, "UPDATE EO_PK_TABLE SET PK = "+nextval+" WHERE NAME = '"+sequenceName+"'", null);
				return resultList.objectAtIndex(0).objectForKey("NEXTVAL");
    
		    default:	
		    	throw new IllegalArgumentException("Unsupported database type'"+databaseType+"' for launch argument '-"+EGRDatabaseConnection.DB_DATABASE_TYPE+"'.");
		}
	}
				
//
// abstract/interface methods
//
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
