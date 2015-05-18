/*
 * File Name: EGRThread.java 
 *
 * Created by: Ernesto Rendon on Oct 6, 2013 6:47:28 AM.
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
package com.egr.EGRUtilities.thread;

import com.egr.EGRUtilities.EGRDateUtilities;

/**
 * A instance of class type EGRThread is used to ...
 * 
 * @author Ernesto Rendon
 * @version 1.0
 */
public abstract class EGRThread extends Thread {
	
	public static final long FIVE_MINUTES = 5*60*1000;			// 5 minutes
	public static final long ONE_HOUR = 1*60*60*1000;			// 1 hour
	public static final long FIFTEEN_HOURS = 15*60*60*1000;		// 15 hours
	public static final long TWENTY_FOUR_HOURS = 24*60*60*1000;	// 24 hours
	public static final long FOUTY_EIGHT_HOURS = 48*60*60*1000;	// 48 hours
	public static final long ONE_WEEK = 168*60*60*1000;			// One Week 
	public static final long TWO_WEEKS = 336*60*60*1000;		// Two Weeks
	public static final String THREAD_NAME = "EGRThread";
	
	protected boolean _isThreadSleeping = false;
	protected boolean _isThreadRunning = true;
//
// JAVA API
//
	public EGRThread() {
		super();
		setName(THREAD_NAME);
	}
//
// WO API
//

//
// state indicator methods
//

//
// misc.
//
	protected String threadName() {return getClass().getSimpleName();}
	public String threadTimeOutDisplay() { return EGRDateUtilities.calculateTimeFromSeconds(threadTimeOutInMilliSeconds()/1000); }
//
// abstract/interface methods
//
	public abstract void run();
	public abstract long threadTimeOutInMilliSeconds();
//
// accessor methods
//
	public boolean isThreadSleeping() {return _isThreadSleeping;}
	public void setIsThreadSleeping(boolean isThreadSleeping) {_isThreadSleeping = isThreadSleeping;}

	public boolean isThreadRunning() {return _isThreadRunning;}
	public void setIsThreadRunning(boolean isThreadRunning) {_isThreadRunning = isThreadRunning;}
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
