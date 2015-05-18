/*
 * File Name: EGRExecuteOSCommand.java
 * 
 * Created by: Ernesto Rendon on Dec 25, 2010 10:57:45 PM.
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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


/**
 * 
 * Instances of EGRExecuteOSCommand are used to execute OS commands via command line.  
 * UNIX command example : String[] {"ls -al", "/home/ernesto/"}
 * 
 * @author Ernesto Rendon
 * @version 1.0
 * @since 1.0
 */
public class EGRExecuteOSCommand {
	
	protected ArrayList<String> _commandArray;
	

	public EGRExecuteOSCommand (String command) {
		ArrayList<String> list = new ArrayList<String>();
		list.add(command);
		setCommandArray(list);
	}
	
	public EGRExecuteOSCommand (ArrayList<String> commandArray) {
		setCommandArray(commandArray);
	}
//
// WO API
//
	
//
// state indicator methods
//

//
// action methods
//
	public void execCommand(ArrayList<String> commandArray) {		
		Process process;
		try {
			// Get runtime
	        java.lang.Runtime rt = java.lang.Runtime.getRuntime();
	        // Start a new process: command 
	        process = rt.exec((String[]) commandArray.toArray());
	        
			// process the errorStream and inputStream 
			new Thread(new SyncPipe(process.getErrorStream(), System.err)).start();
			new Thread(new SyncPipe(process.getInputStream(), System.out)).start();

			//
			 // You can or maybe should wait for the process to complete
			int returnCode = process.waitFor();
			System.out.println("Process exited with code = " + returnCode + " for command(s) '" + commandArray + "'");
			
//	        // Get process' output: its InputStream
//	        java.io.InputStream inStream = process.getInputStream();
//	        java.io.BufferedReader reader = new java.io.BufferedReader(new InputStreamReader(inStream));
//	        // And print each line
//	        String s = null;
//	        while ((s = reader.readLine()) != null) {
//	            System.out.println(s);
//	        }
//	        inStream.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}	
//
// accessor methods
//
	public ArrayList<String> commandArray() { return _commandArray; }
	public void setCommandArray(ArrayList<String> commandArray) { _commandArray = commandArray; }
	
//
// inner classes
//	
	/**
	 * 
	 * Instances of SyncPipe are used to read the process errorStream and 
	 * process inPutStream  because if not read then can cause application 
	 * to hang if there is an error (mostly in Windows).
	 * 
	 * @author Ernesto Rendon
	 * @version 1.0
	 * @since 1.0
	 */
	protected class SyncPipe implements Runnable {
		
		public final OutputStream _ostrm;
		public final InputStream _istrm;
		
		public SyncPipe(InputStream istrm, OutputStream ostrm) {
			_istrm = istrm;
			_ostrm = ostrm;
		}

		public void run() {
			try {
				final byte[] buffer = new byte[1024];
				for (int length = 0; (length = _istrm.read(buffer)) != -1;) {
					_ostrm.write(buffer, 0, length);
				}
				_istrm.close();
				_ostrm.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
