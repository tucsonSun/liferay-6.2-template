/*
 * File Name: EGRFileUtilities.java
 * 
 * Created by: Ernesto Rendon on Dec 25, 2010 10:57:54 PM.
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * Instances of EGRFileUtilities are used to handle files
 * 
 * @author Ernesto Rendon
 * @version 1.0
 * @since 1.0
 */
public class EGRFileUtilities {
		
	protected static Logger _logger = LoggerFactory.getLogger(EGRFileUtilities.class);
	/**
	 * Method returns a file size as string using sensible file size units.
	 * @param fileSize
	 * @return string that contains readable file size
	 */
	public static String readableFileSize(Long fileSize) {
		if (fileSize == null) throw new IllegalArgumentException("fileSize is not allowed to be null");
		
		if(fileSize <= 0) return "0";
	    final String[] FILE_UNITS = new String[] { "bytes", "KB", "MB", "GB", "TB", "PB" };
	    int fileUnitIndex = (int) (Math.log10(fileSize)/Math.log10(1024));
	    return new DecimalFormat("#,##0.#").format(fileSize/Math.pow(1024, fileUnitIndex)) + " " + FILE_UNITS[fileUnitIndex];
	}
	/**
	 * Method copies a source file to a destination file
	 * @param sourceFile
	 * @param destFile
	 * @throws IOException
	 * 
	 * NOTE: WHEN YOU UPGRAED TO Java 1.7 replace this entire method with one line
	 * 	Files.copy( sourceFile.toPath(), destFile.toPath() );
	 * 
	 */
	public static void copyFile(File sourceFile, File destFile) throws IOException {
		if (sourceFile == null) throw new IllegalArgumentException("sourceFile is not allowed to be null");
		if (destFile == null) throw new IllegalArgumentException("destFile is not allowed to be null");
		if (!sourceFile.exists()) throw new IllegalArgumentException("sourceFile does not exist");
		
	    FileChannel sourceStream = null;
	    FileChannel destinationStream = null;
	    try {
	        sourceStream = new FileInputStream(sourceFile).getChannel();
	        destinationStream = new FileOutputStream(destFile).getChannel();
	       
	        // destFile does't exist create it.
		    if(!destFile.exists()) {
		        destFile.createNewFile();
		        System.out.println("EGRFileUtilities.copyFile: file not found in path '" + destFile.getAbsolutePath()+"' creating file.");
		    }
		    // Copy the file from the sourceStream to destinationStream
		    destinationStream.transferFrom(sourceStream, 0, sourceStream.size());
	    } catch (IOException e) {
	    	throw e;
		}
	    finally {
	    	// close the file streams
	    	if (sourceStream != null) sourceStream.close();
			if (destinationStream != null) destinationStream.close();
			System.out.println("File copied from " + sourceFile + " to " + destFile);
	    }
	}
	
	/**
	 * Method recursively copies all files and folders in a source directory from source to destination directory
	 * @param sourceDirectory
	 * @param destinationDirectory
	 * @throws IOException
	 */
	public static void copyDirectory(File sourceDirectory, File destinationDirectory) throws IOException {
		if (sourceDirectory == null) throw new IllegalArgumentException("sourceDirectory is not allowed to be null");
		if (destinationDirectory == null) throw new IllegalArgumentException("destinationDirectory is not allowed to be null");
		if (!sourceDirectory.exists()) throw new IllegalArgumentException("sourceDirectory does not exist");
		
		if (sourceDirectory.isDirectory()) {
			//if directory not exists, create it
			if (!destinationDirectory.exists()) {
				destinationDirectory.mkdir();
				System.out.println("Directory copied from " + sourceDirectory + "  to " + destinationDirectory);
			}
			//list all the directory contents
			String[] children = sourceDirectory.list();
			for (int i = 0; i < children.length; i++) {
				//recursive copy
				copyDirectory(new File(sourceDirectory, children[i]), new File(destinationDirectory, children[i]));
			}
		} else {
			copyFile(sourceDirectory, destinationDirectory);
		}
	}	
	/**
	 * Method recursively deletes all files and subdirectories under specified directory. Returns true if all 
	 * deletions were successful. If a deletion fails, the method stops attempting to delete and returns false.
	 * @param directory
	 * @return Returns true if all deletions were successful
	 */
	public static boolean deleteDirectory(File directory) {
		if (directory == null) throw new IllegalArgumentException("directory is not allowed to be null");
		//if directory handle children
		if (directory.isDirectory()) {
			//list all the directory contents
			String[] children = directory.list();
			for (int i = 0; i < children.length; i++) {
				//recursive delete
				boolean success = deleteDirectory(new File(directory, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// The directory is now empty so delete it
		return directory.delete();
	}
	/**
	 * Method deletes the file for the specified filePath string if it exists.
	 * @param filePath 
	 * @return  returns true if file deleted successfully
	 */
	public static boolean deleteFile(String filePath) {
		if (filePath == null) throw new IllegalArgumentException("filePath is not allowed to be null");
		File file = new File(filePath);
		if (file.exists()) return file.delete();
		else return false;
	}
	/**
	 * Method will move a source file to the destination path with name
	 * @param sourceFile
	 * @param destinationPathAndName
	 */
	public static void moveFile(File sourceFile, String destinationPathAndName) {
		if (sourceFile == null) throw new IllegalArgumentException("sourceFile is not allowed to be null");
		if (destinationPathAndName == null) throw new IllegalArgumentException("destinationPathAndName is not allowed to be null");
		
		// Destination directory and fileName
		File destinationFile = new File(destinationPathAndName);
		// Move file to new directory
		boolean wasMoved = sourceFile.renameTo(destinationFile);
		if (wasMoved)	
			_logger.info("File '"+sourceFile.getName()+"' was moved to "+ destinationPathAndName);
		else 
			_logger.error("Error!!! File was not successfully moved to "+ destinationPathAndName);
	}
//    /**
//     * Method most direct way of converting a File to a String in Java. The String will have the platform default encoding
//     *
//     * @param filePathAndName The file to be turned into a String
//     * @return  The file as String encoded in the platform
//     * default encoding
//     */
//	public static String writeFileToString(String filePathAndName) {
//		if (filePathAndName == null) throw new IllegalArgumentException("filePathAndName is not allowed to be null");
//		if (!new File(filePathAndName).exists()) throw new IllegalArgumentException("file '"+filePathAndName+"' does not exist");
//		
//		StringBuilder msgBuffer = null;
//		DataInputStream inStream = null;
//		File file = new File(filePathAndName);
//		
//		try {
//			byte[] bufferArray = new byte[(int) file.length()];
//			inStream = new DataInputStream(new FileInputStream(file));
//			// Change the encoding
//			//InputStreamReader isr = new InputStreamReader(inStream,"ISO-8859-1");
//			inStream.readFully(bufferArray);
//			msgBuffer = new StringBuilder(new String(bufferArray));
//		} catch (IOException e) {
//			throw new RuntimeException("IO problem when attempting to writeFileToString \n", e);
//		} finally {
//			try {
//				inStream.close();
//				System.gc();//garbage collect
//			} catch (IOException e) { /* ignore it */
//			}
//		}
//		return msgBuffer.toString();
//	}	
	/**
	 * Method will write to a file specified file content.
	 * @param file
	 * @param fileContent
	 * @param appendToEndOfFile boolean if true, then data will be written to the end of the file rather than the beginning. 
	 * @return File
	 */
	public static File writeStringToFile(File file, String fileContent, Boolean appendToEndOfFile){
		if (file == null) throw new IllegalArgumentException("file is not allowed to be null");
		if (!file.exists()) throw new IllegalArgumentException("file '"+file.getAbsolutePath()+"' does not exist");
		if (appendToEndOfFile == null) throw new IllegalArgumentException("appendToEndOfFile is not allowed to be null");
		
		FileWriter fileWriter;// declare a print stream object
		try {
			// Create a new file output stream
			fileWriter = new FileWriter(file, appendToEndOfFile);
			fileWriter.write(fileContent);
			 //close the FileWriter
			fileWriter.close();
		} catch (Exception e) {
			_logger.error("EGRFileUtilities.writeTextFile: Exception while writing to file to path '" +file.getAbsolutePath()+"' \n", e);
		}
		finally {
			System.gc();//garbage collect
		}
		return file;
	}	
//	/**
//	 * Method creates a file from a passed in WOComponent.
//	 * @param page
//	 * @param destinationPathAndName
//	 * @return
//	 */
//	public File writeWOComponentToFile( page, String destinationPathAndName) {
//		if (page == null) throw new IllegalArgumentException("page is not allowed to be null");
//		if (destinationPathAndName == null) throw new IllegalArgumentException("destinationFilePath is not allowed to be null");
//		
//		File file = new File(destinationPathAndName);
//		FileOutputStream fileOutputStream = null;
//		try {
//			WOResponse response = page.generateResponse();
//			NSData content = response.content();
//			fileOutputStream = new FileOutputStream(file);
//			fileOutputStream.write(content.bytes(), 0, content.length());
//			fileOutputStream.close();
//			return file; 
//		} catch (IOException e) {		
//			NSLog.err.appendln("EGRFileUtilities.createFileFromWOComponent Exception creating file from page '" + page.getClass().getName()+ "' at location '"+destinationPathAndName+"' \n" + e);
//			//don't handle exception just pass it forward
//			throw new NSForwardException(e);
//		}
//		finally {
//			System.gc();//garbage collect
//		}
//	}
//	/**
//	 * Method will return the contents of the file in a byte array.
//	 * @param file
//	 * @return
//	 * @throws IOException
//	 */
//	public static byte[] getBytesFromFile(File file) { return getNSDataFromFile(file).bytes(); }
//	
//	/**
//	 * Method will return the contents of the file in a NSData.
//	 * @param file
//	 * @return
//	 * @throws IOException
//	 */
//	public static NSData getNSDataFromFile(File file) {
//		if (file == null) throw new IllegalArgumentException("file is not allowed to be null");
//		if (!file.exists()) throw new IllegalArgumentException("file does not exist");
//		
//		 // Create the byte array to hold the data
//		NSData nsdata = null;
//		try {
//			FileInputStream fileInputStream = new FileInputStream(file);
//			nsdata = new NSData(fileInputStream, 1024);
//		    // Close the input stream and return bytes
//		    fileInputStream.close();
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//			NSLog.err.appendln("EGRFileUtilities.getNSDataFromFile: Exception while getting btyes from file '" +file.getName()+"' \n" +e);
//		}
//		finally {
//			System.gc();//garbage collect
//		}
//		
//	    return nsdata;
//	}
	
}
