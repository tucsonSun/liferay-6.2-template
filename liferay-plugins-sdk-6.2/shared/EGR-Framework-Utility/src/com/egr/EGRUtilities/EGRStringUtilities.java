/*
 * File Name: EGRStringUtilities.java
 * 
 * Created by: Ernesto Rendon on Dec 25, 2010 10:58:26 PM.
 * 
 * Copyright (c) 2010 EGR Software Inc. 3839 E. Cholla St. Phoenix, Arizona,
 * 85028, U.S.A. All rights reserved.
 * 
 * This software is the confidential and proprietary information of EGR Software
 * Inc. You shall not disclose such confidential information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with EGR Software Inc.
 */
package com.egr.EGRUtilities;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;



public class EGRStringUtilities extends Object {

	protected static Logger _logger = LoggerFactory.getLogger(EGRStringUtilities.class);
	
	public static final String SPECIAL_CHARACTERS = "!@#$%^&*()~`-=_+[]{}|:\";',./<>?";
	public static final int MIN_PASSWORD_LENGTH = 5;
	public static final int MAX_PASSWORD_LENGTH = 20;
	
	public static boolean isAcceptablePassword(String password) {
		String trimmedPassword = password.trim();
	    int length = trimmedPassword.length();
		if (password == null || password.length() == 0) {
	        System.out.println("password is not allowed to be empty");
	        return false;
	    }
		if (!password.equals(trimmedPassword)) {
			System.out.println("password is not allowed to contain empty spaces");
			return false;
		}
	    if(length < MIN_PASSWORD_LENGTH || length > MAX_PASSWORD_LENGTH) {
	        System.out.println("wrong size, it must have at least "+MIN_PASSWORD_LENGTH+" characters and less than "+MAX_PASSWORD_LENGTH+".");
	        return false;
	    }
	    char[] charArray = trimmedPassword.toCharArray();
	    for(char aChar : charArray) {
	        if (Character.isUpperCase(aChar)) {
	            System.out.println(aChar + " is uppercase.");
	        } else
	        if (Character.isLowerCase(aChar)) {
	            System.out.println(aChar + " is lowercase.");
	        } else
	        if (Character.isDigit(aChar)) {
	            System.out.println(aChar + " is digit.");
	        } else
	        if (SPECIAL_CHARACTERS.indexOf(String.valueOf(aChar)) >= 0) {
	            System.out.println(aChar + " is valid symbol.");
	        } else {
	            System.out.println(aChar + " is an invalid character in the password.");
	            return false;
	        }
	    }
	    return true;
	}
	/**
	 * Method returns a count of how many words are in the passed in inputString.
	 * IMPORTANT: Keep method in sync with egrWOTextPanel.js 
	 * @param inputString
	 * @return
	 */
	public static int wordCount(String inputString) {
		//if empty input return zero
		if (inputString == null || inputString.length() == 0)
			return 0;
		//
		//inputString is not empty so trim before counting
		String trimedString = inputString.trim();
		//check if trimedString isEmpty
		if (trimedString.isEmpty()) 
			return 0;
		//
		//count words that are separated by whitespace " ".
		//split separates string around whitespace
		String [] wordArray = trimedString.split("\\s+");
		return wordArray.length;
	}  	
	/**
	 * Method returns a count on a string length
	 * IMPORTANT: Keep method in sync with egrWOTextPanel.js 
	 * @param inputString
	 * @return
	 */
	public static int characterCount(String inputString) {
		//if empty input return zero
		if (inputString == null || inputString.length() == 0) return 0;
		//count string length
		else return inputString.length();
	} 
	
	/**
	 * Convert CamelCase strings into human-readable strings.
	 * 
	 * Example Source String: 
	 * [lowercase] [Class] [MyClass] [HTML] [PDFLoader] [AString] [SimpleXMLParser] [GL11Version] [99Bottles]        
	 * [May5] [BFG9000] [9000BFG] [this.value] [anotherTest] [another123Test] [234anotherTest] [234AnotherTest]
	 * 
	 * Example Result String:
	 * [Lowercase] [Class] [My Class] [HTML] [PDF Loader] [A String] [Simple XML Parser] [GL 11 Version] [99 Bottles] 
	 * [May 5] [BFG 9000] [9000 BFG] [This Value] [Another Test] [Another 123 Test] [234another Test] [234 Another Test]
	 * 
	 * @param sourceStr
	 * @return
	 */
	public static String convertCamelCaseStrToPrettyTxtStr(String sourceStr) {
		if (sourceStr == null)
			throw new IllegalArgumentException("Error parameter sourceStr is not allowed to be null");
		String sourceCopy = "";
		// Regex explanation:
		// Words beginning with UpperCase followed by LowerCase letters OR
		// Words beginning with non-letters followed by Word with UpperCase OR
		// Words beginning with UpperCase or LowerCase followed by non-letters
		String regex = "(?<=[A-Z])(?=[A-Z][a-z])|(?<=[^A-Z])(?=[A-Z])|(?<=[A-Za-z])(?=[^A-Za-z])";
		sourceCopy = sourceStr.replaceAll(regex, " ");

		// Replace dot paths with empty space
		while (sourceCopy.contains(".")) {
			sourceCopy = sourceCopy.replace(".", "");
		}
		sourceCopy = upperCaseFirstLetterEveryWordSeperatedBy(sourceCopy, " ", false);
		return sourceCopy;
	}
	           
	/**
	 * Method will capitalize the first letter in a word separated by the specified delimiter 
	 * @param sourceStr
	 * @param stringDelimiter
	 * @param lowerCaseRestOfSubstring
	 * @return
	 */
	public static String upperCaseFirstLetterEveryWordSeperatedBy(String sourceStr, String stringDelimiter, boolean lowerCaseRestOfSubstring) {
		String sourceCopy = "";
		ArrayList<String> list = stringToArray(sourceStr, stringDelimiter);

		ListIterator<String> e = list.listIterator();
		while (e.hasNext()) {
			String nextStr = e.next();
			if (lowerCaseRestOfSubstring)
				sourceCopy = sourceCopy + nextStr.substring(0, 1).toUpperCase() + nextStr.substring(1).toLowerCase();
			else
				sourceCopy = sourceCopy + nextStr.substring(0, 1).toUpperCase() + nextStr.substring(1);
			// Add the separator back to the built string
			if (e.hasNext())
				sourceCopy = sourceCopy + stringDelimiter;
		}
		return sourceCopy;
	}
	/**
	 * Method splits a strings by the specified delimiter. Example :
	 * <code>"Fields of corn!"</code> with delimiter <code>" "</code> Result
	 * array : <code>"Fields" "of" "corn!"</code>
	 */
	public static ArrayList<String> stringToArray(String str, String delimiter) {
		ArrayList<String> strings = new ArrayList<String>();
		String[] splitted = str.split(delimiter);
		for (int i = 0; i < splitted.length; i++) {
			String element = splitted[i].trim();
			strings.add(element);
		}

		return strings;
	}
	/**
	 * Method merges a strArray into a string
	 */
	public static String arrayToString(ArrayList<String> strArray, String delimiter) {
		String joined = Joiner.on(delimiter).join(strArray);
		return joined;
	}	
	/**
	 * Method will sort an array case insensitive in ascending order
	 * @param array
	 * @return ascending order list
	 */
	public static ArrayList<String> sortArrayAscendingOrder(ArrayList<String> unsortedArray) {
		if (unsortedArray == null)  throw new IllegalArgumentException("Error parameter unsortedArray is not allowed to be null");
		if (unsortedArray.size() <=1 ) return unsortedArray; // do nothing if 0 or 1 length
		
		Collections.sort(unsortedArray, String.CASE_INSENSITIVE_ORDER);
		return unsortedArray;
	}
	/**
	 * Method will sort an array case insensitive in descending order
	 * @param array
	 * @return ascending order list
	 */
	public static ArrayList<String> sortArrayDescendingOrder(ArrayList<String> unsortedArray) {
		if (unsortedArray == null)  throw new IllegalArgumentException("Error parameter unsortedArray is not allowed to be null");
		if (unsortedArray.size() <=1 ) return unsortedArray; // do nothing if 0 or 1 length

		// Collections.sort(list, Collections.reverseOrder());
		Collections.sort(unsortedArray, new Comparator<String>() {
			@Override
			public int compare(String strA, String strB) {
				return strB.compareToIgnoreCase(strA);
			}
		});
		return unsortedArray;
	}
	/**
	 * Method encodes a string to URL value (escapes special characters)
	 * @param unencodedURLValue
	 * @return URL string
	 */
	public static String encodeURL(String unencodedURLValue) {
		String encodedValue = null;
		try {
		     encodedValue = URLEncoder.encode(unencodedURLValue, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			_logger.error("encodeURL failed!",e);
		}
		return encodedValue;
	}
	/**
	 * Method decodes a URL to a readable string (special characters escaped to regular string value)
	 * @param encodedURLValue
	 * @return
	 */
	public static String decodeURL(String encodedURLValue) {
		String decodedValue = null;
		try {
			decodedValue = URLDecoder.decode(encodedURLValue, "UTF-8");
		} catch (UnsupportedEncodingException e) { 
			_logger.error("decodeURL failed!",e);
		}
		return decodedValue;
	}
	
	
//	 /**
//	  * Method parses the string argument as a boolean. The boolean returned represents 
//	  * the value true if the string argument is not null and is equal, 
//	  * ignoring case, to the BOOLEAN.True
//	 * @return 
//	  */
//	 public boolean parseStringToBoolean(String str) {
//		return ERXValueUtilities.booleanValue(str);
//	 }
	 
//	/**
//	 * Return a not null string.
//	 * 
//	 * @param s
//	 *            String
//	 * @return empty string if it is null otherwise the string passed in as
//	 *         parameter.
//	 */
//
//	public static String nonNull(String s) {
//
//		if (s == null) {
//			return "";
//		}
//		return s;
//	}
}