/*
 * File Name: HTMLParserExample1.java 
 *
 * Created by: Ernesto Rendon on Oct 5, 2013 12:06:49 PM.
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
package com.egr.EGRUtilities.html;

/**
 * A instance of class type HTMLParserExample1 is used to ...
 * 
 * @author Ernesto Rendon
 * @version 1.0
 */

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
 
 

public class EGRHTMLParser {

	protected static Logger _logger = LoggerFactory.getLogger(EGRHTMLParser.class);
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

	/**
	 * Method returns a Document object that represents the HTML response for the specified URL.
	 * Reference http://jsoup.org/cookbook/extracting-data/selector-syntax
	 * Reference http://jsoup.org/apidocs/org/jsoup/nodes/Element.html
	 * @param String url
	 * @return Document
	 */
	public static Document requestHTMLDocumentforURL(String url) {
		Response response = null;
		try {
			response = Jsoup.connect(url)
					.userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
					.timeout(10000)
					.execute();
			
			_logger.info("EGRHTMLParser.requestHTMLDocumentforURL exectued request for url :: '"+url+"'");
			//Pause for 9 seconds for response NOTE: Sleep will not work if your inside another tread
            //Thread.sleep(9000);
			return response.parse();
		} catch (IOException e) {
			_logger.error("request to 'url' failed", e);
		} 
		return null;
	}
	
	public static ArrayList<Element> selectTagsOnDocument(Document document, String tagPattern) {
		return new ArrayList<Element>(document.select(tagPattern));
	}
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
