/*
 * File Name: EGRDatabaseConnection.java
 * 
 * Created by: Ernesto Rendon on Feb 25, 2012 3:58:42 PM.
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

import com.webobjects.eoaccess.EOAttribute;
import com.webobjects.eoaccess.EOEntity;
import com.webobjects.eoaccess.EOModel;
import com.webobjects.eoaccess.EOModelGroup;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSLog;
import com.webobjects.foundation.NSMutableDictionary;

import er.extensions.jdbc.ERXJDBCConnectionAnalyzer;


/**
 * A instance of class type EGRDatabaseConnection is used to to setup 
 * a connection to the database provided via launch parameters. 
 * 
 * 
 * 
 * Database					JDBC Driver Designation							Connection URL Pattern
 * MySql 					com.mysql.jdbc.Driver 							jdbc:mysql://:/?user=&password= 	
 * PostgreSQL 				org.postgresql.Driver 							jdbc:postgresql://:/?user=&password= 
 * IBMDB2 					COM.ibm.db2.jdbc.app.DB2Driver 					jdbc:db2://:/?user=&password= 	
 * MSSQL(MicrosoftDriver) 	com.microsoft.jdbc.sqlserver.SQLServerDriver 	jdbc:microsoft:sqlserver://:;databaseName=?user=&password= 	
 * MSSQL(Weblogic)			weblogic.jdbc.mssqlserver4.Driver 				jdbc:weblogic:mssqlserver4:@: 	
 * MSSQL(SprintaDriver)		com.inet.tds.TdsDriver 							jdbc:inetdae::?database=?user=&password= 	
 * MicrosoftSQLServer(JTurboDriver)	com.ashna.jturbo.driver.Driver 			jdbc:JTurbo://://user=/password= 	
 * OracleThin				oracle.jdbc.driver.OracleDriver 				jdbc:oracle:thin:/@:: 	
 * Oracle(OranhoDriver)		com.inet.pool.PoolDriver 						jdbc:inetpool:inetora:?database=&user=&password=&sid= 	
 * Sybase(jConnect5.2)		ncom.sybase.jdbc2.jdbc.SybDriver 				jdbc:sybase:Tds::?user=&password= 	
 * PointBaseEmbeddedServer	com.pointbase.jdbc.jdbcUniversalDriver 			jdbc:pointbase://embedded:/?user=&password= 	
 * Cloudscape				COM.cloudscape.core.JDBCDriver 					jdbc:cloudscape:?user=&password= 	 
 * Open Base				com.openbase.jdbc.ObDriver						jdbc:openbase://localhost/mydatabase 
 * SQLServer(Microsoft)		com.microsoft.jdbc.sqlserver.SQLServerDriver	jdbc:sqlserver://myserver:1433 
 * SQLServer(Merant) com.merant.datadirect.jdbc.sqlserver.SQLServerDriver	jdbc:sqlserver://myserver:1433 
 * 
 * 
 * @author Ernesto Rendon
 * @version 1.0
 * @since 1.0
 */
public class EGRDatabaseConnection {
	
	public static final String DB_URL = "DBURL";
	public static final String DB_USER_NAME = "DBUserName";
	public static final String DB_USER_PASSWORD = "DBPassword";
	public static final String DB_DATABASE_TYPE = "DBDatabase";
	
	public static final String EGR_DATABASE_OBJECT_MODEL_NAME = "EGRDatabaseObjectModel";
	public static final String EGR_PROTOTYPES_MODEL_NAME = "EGRPrototypes";	
	
	
//	/**
//	 * Constructor
//	 */
//	public EGRDatabaseConnection() {
//		openDataBaseConnectionAction();
//	}
	
//
// WO API
//
	
//
// state indicator methods
//	
	
//
// action methods
//	
	/**
	 * Method sets all the database connection properties using system properties via launch properties.
	 * <pre>
	 * If database connection properties can not be found 
	 * 1) Then program is terminated
	 * 2) stack trace is printed to the console 
	 *
	 *	-DBURL connectionUrl (e.g. "jdbc:oracle:thin:@169.254.120.27:1521:XE")
	 *	-DBUserName username (e.g. app_erPortfolio)
	 *	-DBPassword password (e.g. app_erPortfolio)
	 *	-DBDatabase database (e.g. Oracle)
	 *
	 *	-DBURL connectionUrl (e.g. "jdbc:postgresql://192.168.1.111:5432/ERPORTFOLIO")
	 *	-DBUserName username (e.g. app_erPortfolio)
	 *	-DBPassword password (e.g. app_erPortfolio)
	 *	-DBDatabase database (e.g. PostgreSQL)
	 * </pre> 
	 */
	public static void setAllDatabaseConnectionPropertiesAction() {
		try {
			String url = System.getProperty(DB_URL);
			String username = System.getProperty(DB_USER_NAME);
			String userPassword = System.getProperty(DB_USER_PASSWORD);
			DatabaseType databaseType = getDatabaseTypeFromSystemProperties();
			
			if (url == null) throw new IllegalArgumentException("EGRDatabaseConnection: '-"+DB_URL+"' argument missing.");
			if (username == null)  throw new IllegalArgumentException("EGRDatabaseConnection: '-"+DB_USER_NAME+"- argument missing.");
			if (userPassword == null) throw new IllegalArgumentException("EGRDatabaseConnection: '-"+DB_USER_PASSWORD+"- argument missing.");

			
			NSLog.out.appendln("++++++ EGRDatabaseConnection: specifed database type is '"+databaseType+"' ++++++");
			// NOTE: ERendon 7/4/2012 This is to address a WebObjects bug for EOModel always requiring a connection properties in the EOModel. 
			// Instead we want to specify the connection properties via launch arguments.
			//
			//Database PlugIns There's been a long-standing totally annoying (what I consider a) bug in EOF that you have to 
			//open a database connection to be able to retrieve jdbc2Info, which is required to do things like SQL generation. 
			//If you've ever noticed when you made a new model in EOModeler, or when you try to generate SQL in Entity Modeler, 
			//it would yell at you if it couldn't connect to your DB, that's why. jdbc2Info contains things like database type 
			//information, which is almost always static with your database version. 
			//
			// Basically add a newDBConnectDictionary using the specified system properties 
			// Using launch arguments : -DBURL -DBUserName -DBPassword -DBDatabase 
			NSMutableDictionary<String, Object> newDBConnectDictionary = new NSMutableDictionary<String, Object>();	
			newDBConnectDictionary.setObjectForKey(url, "URL");
			newDBConnectDictionary.setObjectForKey(username, "username");
			newDBConnectDictionary.setObjectForKey(userPassword, "password");
			//
			//
			//specify the JDBC Driver based on the databaseType    
	    	if (databaseType.equals(DatabaseType.POSTGRESQL))	newDBConnectDictionary.setObjectForKey("org.postgresql.Driver", "driver");
	    	else if (databaseType.equals(DatabaseType.ORACLE))	newDBConnectDictionary.setObjectForKey("oracle.jdbc.driver.OracleDriver", "driver");  
	    	else if (databaseType.equals(DatabaseType.OPENBASE))newDBConnectDictionary.setObjectForKey("com.openbase.jdbc.ObDriver", "driver"); 
	    	else if (databaseType.equals(DatabaseType.SYBASE)) 	newDBConnectDictionary.setObjectForKey("ncom.sybase.jdbc2.jdbc.SybDriver", "driver");
	    	else if (databaseType.equals(DatabaseType.SQLSERVER)) newDBConnectDictionary.setObjectForKey("com.microsoft.jdbc.sqlserver.SQLServerDriver", "driver");
		    else {
		    	NSLog.out.appendln("Can not find the JDBC driver for databaseType = '"+databaseType+"'");
		    	newDBConnectDictionary= null; //set newDBConnectDictionary to null because cann't find the JDBC Driver 
		    }
	    	//
	    	//
	    	//specify the JDBC Plugin based on the databaseType   
	    	if (databaseType.equals(DatabaseType.POSTGRESQL))	newDBConnectDictionary.setObjectForKey("PostgresqlPlugIn", "plugin");
	    	else if (databaseType.equals(DatabaseType.ORACLE))	newDBConnectDictionary.setObjectForKey("EROraclePlugIn", "plugin");  
	    	else if (databaseType.equals(DatabaseType.OPENBASE))throw new IllegalArgumentException("Project Wonder does not have a JDBC framework plugin for this databaseType. Not sure what to do...");
	    	else if (databaseType.equals(DatabaseType.SYBASE)) 	throw new IllegalArgumentException("Project Wonder does not have a JDBC framework plugin for this databaseType. Not sure what to do...");
	    	else if (databaseType.equals(DatabaseType.SQLSERVER)) throw new IllegalArgumentException("Project Wonder does not have a JDBC framework plugin for this databaseType. Not sure what to do...");
		    else {
		    	NSLog.out.appendln("Can not find the JDBC plugin for databaseType = '"+databaseType+"'");
		     	newDBConnectDictionary= null; //set newDBConnectDictionary to null because cann't find the JDBC Driver 
		    }
	    	//
	    	// Create EGRPrototype entity For the databaseType specified in launch arguments
	    	EOEntity egrPrototypeEntityForDBType = createEGRPrototypeEntityForDBType(databaseType);
			// Then set the "jdbc2Info" with the  EGRPrototype entity 
			NSMutableDictionary<String, ?> jdbc2InfoDict = (NSMutableDictionary<String, ?>) egrPrototypeEntityForDBType.userInfo().objectForKey("jdbc2Info");
			newDBConnectDictionary.setObjectForKey(jdbc2InfoDict, "jdbc2Info");
			// Add the newDBConnectDictionary to the EOModelGroup
			NSArray<EOModel> models = EOModelGroup.defaultGroup().models();
			for (EOModel eoModel : models) {
				NSMutableDictionary<String, Object> connectionDictionary = new NSMutableDictionary<String, Object>(eoModel.connectionDictionary());
				connectionDictionary.addEntriesFromDictionary(newDBConnectDictionary);
				eoModel.setConnectionDictionary(connectionDictionary);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println();
			NSLog.out.appendln("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			NSLog.out.appendln("Failed to setup a database properties via launch parameters. ");
			NSLog.out.appendln("Please add launch parameters  -"+DB_URL+", -"+DB_USER_NAME+" -"+DB_USER_PASSWORD+" and -"+DB_DATABASE_TYPE);
			NSLog.out.appendln("Terminating the applcaiton :: System.exit(1)");
			NSLog.out.appendln("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println();
			System.exit(1);
		}
	}
	/**
	 * Method attempts to make a connection the to the database IP address or Domain using the connection properties
	 * @param eoModelName
	 */
	public static void testConnectionToDatabaseAction(String eoModelName) {
		try {
			if (eoModelName == null) throw new IllegalArgumentException("eoModelName is not allowed to be null");
			ERXJDBCConnectionAnalyzer connectionAnalyzer = new ERXJDBCConnectionAnalyzer(EOModelGroup.defaultGroup().modelNamed(eoModelName));
			System.out.println();
			NSLog.out.appendln("++++++ --- Start --- Attempting to test connection to database --- Start --- ++++++ ");
			connectionAnalyzer.dumpExtensionDirectories();
			connectionAnalyzer.testConnection();
			//connectionAnalyzer.analyzeConnection();
			NSLog.out.appendln("++++++ --- End --- Attempting to test connection to database --- End --- ++++++ ");
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println();
			NSLog.out.appendln("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			NSLog.out.appendln("Failed to connect to database IP address or Domain using properties. ");
			NSLog.out.appendln("Terminating the applcaiton :: System.exit(1)");
			NSLog.out.appendln("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println();
			System.exit(1);
		}
	}
	/**
	 * Method return the database type specified via launch parameters.
	 * Defaults to database type "PostgreSQL" if not specified. 
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static DatabaseType getDatabaseTypeFromSystemProperties() throws IllegalArgumentException {
		String dbSystemPropertyString = System.getProperty(DB_DATABASE_TYPE);
		DatabaseType databaseType = null;
		if (dbSystemPropertyString != null)
			databaseType = DatabaseType.toDatabaseType(System.getProperty(DB_DATABASE_TYPE));
		else { 
			databaseType = DatabaseType.POSTGRESQL;
			NSLog.out.appendln("EGRDatabaseConnection.getDatabaseTypeFromSystemProperties: databaseType not specifed defaulting to ...."+databaseType);
		}
		
		NSArray<DatabaseType> supportedDatabaseTypesArray = new NSArray<DatabaseType>(DatabaseType.values());
		if (!supportedDatabaseTypesArray.containsObject(databaseType)) 
			throw new IllegalArgumentException("Unsupported database type'"+databaseType+"' for argument '-"+DB_DATABASE_TYPE+"'.");
		
		return databaseType;
	}
//
// misc.
//	
    static public enum DatabaseType { 
    	ORACLE("Oracle"), SQLSERVER("SQLServer"), OPENBASE("OpenBase"), SYBASE("Sybase"), POSTGRESQL("PostgreSQL"), NO_TYPE("DatabaseType_Not_Found");
       	protected final String dbType;
    	/**   
    	 * Constructor
    	 * @param dbType
    	 */
       	DatabaseType(String dbType){  
    		this.dbType = dbType;   
    	}  

    	public String toString(){ return this.dbType;  }
    	
        public static DatabaseType toDatabaseType(String value) {
            try {
                return valueOf(value);
            } 
            catch (Exception ex) {
                return NO_TYPE;
            }
        } 
    } 
	/**
	 * Method creates a EOEntity with all the EGRPrototype attributes based on the database type 
	 * @param databaseType
	 * @return EOEntity
	 */
	protected static EOEntity createEGRPrototypeEntityForDBType(DatabaseType databaseType) {
		EOModel prototypeModel = EOModelGroup.defaultGroup().modelNamed(EGR_PROTOTYPES_MODEL_NAME);
		EOEntity jdbcEntity = prototypeModel.entityNamed("EOJDBCPrototypes");
		EOEntity egrPrototypeEntity = prototypeModel.entityNamed("EO"+ databaseType +"Prototypes");
		
		// The EOJDBCPrototypes replaces all EOAttributes with database specific EOAttributes
		// NOTE: THIS ASSUMES 1-to-1 index between jdbcEntity eoAttributes and prototypeEntity eoAttributes lists
		NSArray<EOAttribute> jdbcEntityEOAttributeList = jdbcEntity.attributes();
		NSArray<EOAttribute> prototypeEntityEOAttributeList = egrPrototypeEntity.attributes();
		
		if (jdbcEntityEOAttributeList.count()>0 && jdbcEntityEOAttributeList.count() != prototypeEntityEOAttributeList.count())
			throw new IllegalArgumentException("EGRDatabaseConnection: 1-to-1 index between jdbcEntity eoAttributes and prototypeEntity eoAttributes lists is being violated.");
		
		for (int i = 0; i < jdbcEntityEOAttributeList.count(); i++) {	
			EOAttribute jdbcEntityEOAttribute = jdbcEntityEOAttributeList.objectAtIndex(i);
			EOAttribute prototypeEntityEOAttribute = prototypeEntityEOAttributeList.objectAtIndex(i);
			
			
			//REMOVE the EOAttribute
			jdbcEntity.removeAttribute(jdbcEntityEOAttribute);
			
			//CREATE a newEOAttribute and set all values from the EGRPrototype EOAttribute for the databaseType
			EOAttribute newEOAttribute = new EOAttribute();
			newEOAttribute.setAdaptorValueConversionMethodName(prototypeEntityEOAttribute.adaptorValueConversionMethodName());
			newEOAttribute.setAllowsNull(prototypeEntityEOAttribute.allowsNull());
			newEOAttribute.setColumnName(prototypeEntityEOAttribute.columnName());
			newEOAttribute.setClassName(prototypeEntityEOAttribute.className());
			
			newEOAttribute.setExternalType(prototypeEntityEOAttribute.externalType());
			newEOAttribute.setDefinition(prototypeEntityEOAttribute.definition());
			newEOAttribute.setName(prototypeEntityEOAttribute.name());
			newEOAttribute.setFactoryMethodArgumentType(prototypeEntityEOAttribute.factoryMethodArgumentType());
			
			newEOAttribute.setParameterDirection(prototypeEntityEOAttribute.parameterDirection());
			newEOAttribute.setPrecision(prototypeEntityEOAttribute.precision());
			newEOAttribute.setReadFormat(prototypeEntityEOAttribute.readFormat());
			newEOAttribute.setReadOnly(prototypeEntityEOAttribute.isReadOnly());
			newEOAttribute.setScale(prototypeEntityEOAttribute.scale());
			newEOAttribute.setServerTimeZone(prototypeEntityEOAttribute.serverTimeZone());
			newEOAttribute.setUserInfo(prototypeEntityEOAttribute.userInfo());
			//not sure if setValueClassName needed since it is marked as deprecated in API
			newEOAttribute.setValueFactoryMethodName(prototypeEntityEOAttribute.valueFactoryMethodName());
			newEOAttribute.setValueType(prototypeEntityEOAttribute.valueType());
			newEOAttribute.setWidth(prototypeEntityEOAttribute.width());
			newEOAttribute.setWriteFormat(prototypeEntityEOAttribute.writeFormat());
			
			jdbcEntity.addAttribute(newEOAttribute);		
		}
		return egrPrototypeEntity; 
	}
//
// accessor methods
//
		
}
