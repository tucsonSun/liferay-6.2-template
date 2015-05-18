/*
 * File Name: EGRJavaReflectionUtilities.java 
 *
 * Created by: Ernesto Rendon on Mar 18, 2012 2:57:43 PM.
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
package com.egr.EGRUtilities;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A instance of class type EGRJavaReflectionUtilities is used to ...
 * 
 * @author Ernesto Rendon
 * @version 1.0
 * @since 1.0
 */
public class EGRJavaReflectionUtilities {
	
	protected static Logger _logger = LoggerFactory.getLogger(EGRFileUtilities.class);

//
// WO API
//

//
// state indicator methods
//
	/**
	 * Method uses Java Reflection at run time to inspect the interfaces the targetObject implements.
	 * If the interface specified matches the targetObject implemented interface then it returns true.
	 * @param targetObject
	 * @param interfaceClass
	 * @return
	 */
	public static boolean targetObjectImplementsInterface(Object targetObject, Class<?> interfaceClass) {
		Class<?>[] interfaces = targetObject.getClass().getInterfaces();
		for (Class<?> className : interfaces) {			
			if (className.equals(interfaceClass)) 
				return true;
		}
		return false;
	}
//
// action methods
//


//
// misc.
//
    /**
     * Method will find the constructor for the target class name specified with the given Class<?>[] constructorParameterClasses and Object[] constructorParameterValues.
     *
     * Example below creates a FY sensitive delegate directAction based on the passed in FY and context 
	 *	static public Delegate delegateForFiscalYear(Integer fiscalYear, WOContext context) throws Exception {
	 *		String twoDigitFY = fiscalYear.toString().substring(2);
	 *		String delegateClassName = "com.somewhere.somePackge.foundation.fy"+twoDigitFY+".app.FY"+twoDigitFY+"ApplicationNameDirectActionDelegate";
	 * 		return (Delegate)invokeNewInstanceForTargetClassName(delegateClassName,new Class[] {WOContext.class},new Object[] {context});
	 *	}
     * @throws Exception 
	 */
    public static Object invokeNewInstanceForTargetClassName (String constructorClassName, Class<?>[] constructorParameterClasses, Object[] constructorParameterValues) throws Exception {
		if (constructorClassName == null || constructorClassName.length() == 0)
			throw new IllegalArgumentException("constructorClassName is not allowed to be null");
	
		try {
			 Class<?> classForTargetName = Class.forName(constructorClassName);
	        Constructor<?> constructor = classForTargetName.getConstructor(constructorParameterClasses);
	        return constructor.newInstance(constructorParameterValues);
		} catch (IllegalAccessException e) {
			StringBuilder message = new StringBuilder();
			message.append("EGRJavaReflectionUtilities.invokeNewInstanceForTargetClassName: constructorClassName '"+constructorClassName+"' causes an IllegalAccessException. \n");
			message.append("If you get the following error java.lang.IllegalAccessException: Class com.egr.EGRUtilities.EGRJavaReflectionUtilities can not access a member of class (com.egr.SomeClassName) with modifiers \"public\" \n");
			message.append("It's likely because your class (com.egr.SomeClassName) is not public. You just need to add a public keyword in front of the class definition. \n");
			
			_logger.error(message.toString());
			//don't handle exception just pass it forward
			throw e;
		} catch (Exception e) {
			//don't handle exception just pass it forward
			throw e;
		}
    }
//	/**
//	 * Method creates callback action for the WOComponent
//	 * @param page
//	 * @param methodName
//	 * @param methodParameterClasses
//	 * @param methodParameterValues
//	 * @return Method returns the resulting object from the method name or returns null
//	 */
//	public static Object invokeCallBackActionOnWOComponent(WOComponent page, String methodName, Class<?>[] methodParameterClasses, Object[] methodParameterValues) {
//		return invokeMethodOnTargetObject(page, methodName, methodParameterClasses, methodParameterValues);
//	}
	/**
	 * Method uses Java reflection at run time to invoke a method on a targetObject. 
	 * Method invoked will have the same parameters as specified in the Class<?>[] methodParameters.
	 * If Class<?>[] methodParameters is null then method defaults to looking for method with name but with no parameters.
	 * 
	 * @param targetObject
	 * @param methodName
	 * @param methodParameterClasses - array of parameter classes that is used by methodName
	 * @param methodParameterValues - array of parameter values that is used by methodName
	 * @return Method returns the resulting object from the method name or returns null
	 * @throws Exception 
	 */
	public static Object invokeMethodOnTargetObject(Object targetObject, String methodName, Class<?>[] methodParameterClasses, Object[] methodParameterValues) throws Exception {
		Object resultObject = null;
		if (methodName == null || methodName.length() == 0)
			throw new IllegalArgumentException("invokeMethodOnTargetObject :: methodName is not allowed to be null");
		
		if (targetObject == null)
			throw new IllegalArgumentException("invokeMethodOnTargetObject :: targetObject is not allowed to be null");
		
		try {
			Method method =  methodOnTargetObject(targetObject, methodName, methodParameterClasses);
			resultObject = method.invoke(targetObject, methodParameterValues);
		} catch (IllegalAccessException ex) {
			StringBuffer message = new StringBuffer();
			message.append("EGRJavaReflectionUtilities.invokeMethodOnTargetObject: method with name '"+methodName+"' causes an IllegalAccessException. \n");
			message.append("If you get the following error java.lang.IllegalAccessException: Class com.egr.EGRUtilities.EGRJavaReflectionUtilities can not access a member of class (com.egr.SomeClassName) with modifiers \"public\" \n");
			message.append("It's likely because your class (com.egr.SomeClassName) is not public. You just need to add a public keyword in front of the class definition. \n");
			_logger.error(message.toString());
			//don't handle exception just pass it forward
			throw ex;
		} catch (Exception ex) {
			//don't handle exception just pass it forward
			throw ex;
		}
		
		return resultObject;
	}

	/**
	 * Method uses Java reflection at run time to return a method object on a targetObject. 
	 * 
	 * @param targetObject
	 * @param methodName
	 * @param methodParameterClasses - array of parameter classes that is used by methodName
	 * @return Method returns the resulting object from the method name or returns null
	 * @throws NoSuchMethodException 
	 */
	public static Method methodOnTargetObject(Object targetObject, String methodName, Class<?>[] methodParameterClasses) throws NoSuchMethodException {
		Method resultObject = null;
		if (methodName == null || methodName.length() == 0)
			throw new IllegalArgumentException("methodForTargetObject :: methodName is not allowed to be null");
		
		if (targetObject == null)
			throw new IllegalArgumentException("methodForTargetObject :: targetObject is not allowed to be null");
				
		try {
			Class<?>  aClass = targetObject.getClass(); ///obtain class object
			Method method = aClass.getMethod(methodName, methodParameterClasses);
			resultObject = method;
		} catch (NoSuchMethodException ex) {
			_logger.error("EGRJavaReflectionUtilities.methodForTargetObject: method with name '"+methodName+"' not found on targetObject "+ targetObject.getClass().getName());
			//don't handle exception just pass it forward
			throw ex;
		} catch (Exception ex) {
			//don't handle exception just pass it forward
			throw ex;
		}
		
		return resultObject;
	}
	
	/**
	 * Method uses Java Reflection to inspect a specified keyPath at run time. 
	 * The keyPath method invoked on the targetObject N times until the final method is reached.
	 * Then  Step thru the keys N times to find the result.
	 * 
	 * NOTE: KeyPath assumes not argument methods!!!
	 *  Set parameter classes array - new Class[] {} or null
	 *  Set parameter values array - new Object[] {} or null
	 * 
	 * @param keyPath - Methods separated by dot notation
	 * @param targetObject
	 * @return targetObject method value
	 * @throws Exception 
	 */
	public static Object valueForKeyPathOnTargetObject(String keyPath, Object targetObject) throws Exception {
		if (keyPath == null || keyPath.length() == 0)
			throw new IllegalArgumentException("valueForKeyPathOnTargetObject :: keyPath is not allowed to be null");
		
		if (targetObject == null)
			throw new IllegalArgumentException("valueForKeyPathOnTargetObject :: targetObject is not allowed to be null");
		
		//
		//Separate the keys
		ArrayList<String> keys = EGRStringUtilities.stringToArray(keyPath, ".");
		//
		//Working object that stores the resultingObject from walking the relationship path 
		Object resultObject = targetObject;
		//
		// Step thru the keys N times to find the result
		//
		try {
			Iterator<String> itr = keys.iterator();
			while (itr.hasNext()) {
				String key = itr.next();
				resultObject =  invokeMethodOnTargetObject(resultObject, key, new Class[] {}, new Object[] {});
				// stop stepping thru the relationship path because value is null at this point
				if (resultObject == null) 
					break;
			}
		} catch (Exception ex) {
			_logger.error("EGRJavaReflectionUtilities.invokeMethod: keyPath with name '"+keyPath+"' not found on targetObject "+ targetObject.getClass().getName());
			//don't handle exception just pass it forward
			throw ex;
		}
		return resultObject;
	}
//
// accessor methods
//

//
// inner classes
//

}
