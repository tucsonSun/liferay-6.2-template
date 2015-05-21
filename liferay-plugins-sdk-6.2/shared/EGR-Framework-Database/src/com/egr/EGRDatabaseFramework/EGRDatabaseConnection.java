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

import java.util.HashMap;


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
	
	
}
