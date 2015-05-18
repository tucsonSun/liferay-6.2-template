/*
 * File Name: EGREmailUtilities.java 
 *
 * Created by: Ernesto Rendon on Mar 11, 2012 9:17:40 AM.
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

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A instance of class type EGREmailUtilities is used to ...
 * 
 * @author Ernesto Rendon
 * @version 1.0
 * @since 1.0
 */
public class EGREmailUtilities {

//
// WO API
//

//
// state indicator methods
//
	
	
	
//	^						#start of the line
//	  [_A-Za-z0-9-\\+]+		#  must start with string in the bracket [ ], must contains one or more (+)
//	  (						#   start of group #1
//	    \\.[_A-Za-z0-9-]+	#     follow by a dot "." and string in the bracket [ ], must contains one or more (+)
//	  )*					#   end of group #1, this group is optional (*)
//	    @					#     must contains a "@" symbol
//	     [A-Za-z0-9-]+      #       follow by string in the bracket [ ], must contains one or more (+)
//	      (					#         start of group #2 - first level TLD checking
//	       \\.[A-Za-z0-9]+  #           follow by a dot "." and string in the bracket [ ], must contains one or more (+)
//	      )*				#         end of group #2, this group is optional (*)
//	      (					#         start of group #3 - second level TLD checking
//	       \\.[A-Za-z]{2,}  #           follow by a dot "." and string in the bracket [ ], with minimum length of 2
//	      )					#         end of group #3
//	$						#end of the line
	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	/**
	 * Method returns true if the array of emailAddresses are of valid format
	 */
	public static boolean isValidEmailAddress(ArrayList<String> emailAddresses) {
		for (String emailAddress : emailAddresses) {
			if (!isValidEmailAddress(emailAddress))
				return false;
		}
		return true;
	}
	
    /**
     * Method returns if the passed in emailAddress of valid format
     */
	public static boolean isValidEmailAddress(String emailAddress) {
		// a null string is invalid
		if (emailAddress == null)
			return false;

		// a string without a "@" is an invalid email address
		if (emailAddress.indexOf("@") < 0)
			return false;

		// a string without a "." is an invalid email address
		if (emailAddress.indexOf(".") < 0)
			return false;

		if (lastEmailFieldTwoCharsOrMore(emailAddress) == false)
			return false;
		
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(emailAddress);
		return matcher.matches();
	}
	/**
	 * Returns true if the last email field (i.e., the country code, or
	 * something like .com, .net, .org, .biz, .cc, etc.) is two chars or more in length,
	 * which it really must be to be legal.
	 */
	protected static boolean lastEmailFieldTwoCharsOrMore(String emailAddress) {
		if (emailAddress == null)
			return false;
		StringTokenizer st = new StringTokenizer(emailAddress, ".");
		String lastToken = null;
		while (st.hasMoreTokens()) {
			lastToken = st.nextToken();
		}

		if (lastToken.length() >= 2) {
			return true;
		} else {
			return false;
		}
	}
//
// action methods
//
	
//    /**
//     * Method used to printout the Headers info for debug tracking. 
//     * @param request
//     * @return String
//     */
//    public static String printRequestHeaders(WORequest request) {
//    	if(request != null) {
//	    	String headers = request.headers().toString();
//	    	StringBuilder msg = new StringBuilder();
//	    	msg.append("Developer Troubleshooting Information:\n");
//	    	msg.append("\n*************** Start Header Info ********************\n");
//	    	msg.append("headers :: " + headers);
//	    	msg.append("\n*************** End Header Info **********************\n");
//	    	msg.append("\n\n");
//	    	return msg.toString();
//    	}
//    	else return "";
//    }
	
//
// misc.
//	    

//
// accessor methods
//

//
// inner classes
//

}
