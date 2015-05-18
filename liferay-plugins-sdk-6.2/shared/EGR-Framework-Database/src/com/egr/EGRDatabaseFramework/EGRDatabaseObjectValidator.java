/*
 * File Name: EGRDatabaseObjectValidator.java 
 *
 * Created by: Ernesto Rendon on Oct 6, 2013 3:06:04 PM.
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
package com.egr.EGRDatabaseFramework;

import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSValidation;

/**
 * A instance of class type EGRDatabaseObjectValidator is used to ...
 * 
 * @author Ernesto Rendon
 * @version 1.0
 */
public abstract class EGRDatabaseObjectValidator<T> {

	static public final String VALIDATION_FOR_SAVE_ATTEMPT_FAILED = "Validation for save attempt failed...";
	static public final String VALIDATION_FOR_SUBMIT_ATTEMPT_FAILED = "Validation for submit attempt failed...";
	
	protected T _item;
	protected NSMutableArray<String> _validationArray = new NSMutableArray<String>();
	
	/**
	 * Constructor
	 * @param item
	 */
	public EGRDatabaseObjectValidator(T item) {
		setItem(item);
	}
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
// abstract/interface methods
//
	/**
	 * Method should check if EO is valid to save to database
	 * @throws NSValidation.ValidationException
	 */
	abstract public void validateForSave() throws NSValidation.ValidationException;

	/**
	 * Method should check if EO is valid for a submit 
	 * @throws NSValidation.ValidationException
	 */
	abstract public void validateForSubmit() throws NSValidation.ValidationException;	
//
// accessor methods
//
	public NSMutableArray<String> validationArray() { return _validationArray; }
	public void setValidationArray(NSMutableArray<String> validationArray) { _validationArray = validationArray; }
//
// accessor methods
//
	public T item() {return _item;}
	public void setItem(T item) {_item = item;}
//
// inner classes
//

}
