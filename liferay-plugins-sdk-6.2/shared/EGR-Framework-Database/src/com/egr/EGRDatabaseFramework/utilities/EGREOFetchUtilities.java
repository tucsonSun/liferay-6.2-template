/*
 * File Name: EGREOFetchUtilities.java
 * 
 * Created by: Ernesto Rendon on Mar 7, 2012 7:23:07 AM.
 * 
 * Copyright (c) 2011 EGR Software Inc. 3839 E. Cholla St. Phoenix, Arizona,
 * 85028, U.S.A. All rights reserved.
 * 
 * This software is the confidential and proprietary information of EGR Software
 * Inc. You shall not disclose such confidential information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with EGR Software Inc.
 */

package com.egr.EGRDatabaseFramework.utilities;



/**
 * A instance of class type EGREOFetchUtilities is used to fetch EGRDatabaseObject objects
 * 
 * @author Ernesto Rendon
 * @version 1.0
 * @since 1.0
 */
public class EGREOFetchUtilities {
		
//
// WO API
//

//
// state indicator methods
//

//
// action methods
//
	
//	/**
//	 * Method fetches list of EGRDatabaseObject with fetch parameters.
//	 * The name of an entity for which to fetch records or objects. This is the only mandatory element.
//	 * An EOQualifier, indicating which properties to select by and how to do so.
//	 * An array of EOSortOrderings, which indicate how the selected records or objects should be ordered when fetched.
//	 * An indicator of whether to produce distinct results or not. Normally if a record or object is selected several times, such as when forming a join, it appears several times in the fetched results. An EOFetchSpecification that makes distinct selections causes duplicates to be filtered out, so each record or object selected appears exactly once in the result set.
//	 * An indicator of whether to fetch deeply or not. This is used with inheritance hierarchies when fetching for an entity with sub entities. A deep fetch produces all instances of the entity and its sub entities, while a shallow fetch produces instances only of the entity in the fetch specification.
//	 * A fetch limit indicating how many objects to fetch before giving the user or program an opportunity to intervene.
//	 * A listing of relationships for which the destination of the relationship should be prefetched along with the entity being fetched. Proper use of this feature allows for substantially increased performance in some cases.
//	 * A dictionary of hintsDict, which an EODatabaseContext or other object can use to optimize or alter the results of the fetch.
//	 * A fetch limit integer
//	 * A EOEditingContext instance
//	 */
//	@SuppressWarnings("unchecked")
//	public static synchronized <D> NSArray<D> fetchObjectsMatchingFetchSpecs(String entityName,
//																			EOQualifier fetchQualifier,
//																			NSArray<EOSortOrdering> sortOrderingArray,
//																			boolean usesDistinct,
//																			boolean isDeep,
//																			NSDictionary<?,?> hintsDict,
//																			int fetchLimitNum,
//																			EOEditingContext ec) {	
//		// Create EOFetchSpecification object
//		EOFetchSpecification eoFetchSpec = new EOFetchSpecification(entityName, fetchQualifier, sortOrderingArray, usesDistinct, isDeep, hintsDict);
//		// If specified set fetch limit
//		if (fetchLimitNum > 0) {
//			eoFetchSpec.setFetchLimit(fetchLimitNum);
//		}
//		// existing objects are overwritten with fetched values when they've
//		// been updated or changed
//		eoFetchSpec.setRefreshesRefetchedObjects(true);
//
//		NSArray<D> fetchedArray = null;
//		try {
//			ec.lock();
//			fetchedArray = ec.objectsWithFetchSpecification(eoFetchSpec);
//		} catch (Exception e) {
//			ec.unlock();
//			e.printStackTrace();
//			//NSLog.out.appendln("EGREOFetchUtilities.fetchObjectsMatchingFetchSpecifications: Exception fetching objects.");
//			// don't handle exception just pass it forward
//			throw new NSForwardException(e);
//		}
//		ec.unlock();
//
//		return fetchedArray;
//	}	
//	
//	/**
//	 * Method fetches a single EGRDatabaseObject with fetch parameters.
//	 * @param entityName
//	 * @param fetchQualifier
//	 * @param sortOrderingArray
//	 * @param fetchLimitNum
//	 * @param EOEditingContext
//	 * @return
//	 */
//	public static synchronized <D> D fetchDistinctObjectMatchingFetchSpecs(String entityName, 
//															EOQualifier fetchQualifier,
//															NSArray<EOSortOrdering> sortOrderingArray,
//															int fetchLimitNum,
//															EOEditingContext ec) {	
//		
//		NSArray<D> fetchedArray = fetchObjectsMatchingFetchSpecs(entityName, fetchQualifier, sortOrderingArray, true, true, null, fetchLimitNum, ec);
//		if (fetchedArray.count() > 1){
//			NSLog.out.appendln("EGREOFetchUtilities.fetchObjectMatchingFetchSpecs: found more than one object matching specifications");
//			return null;
//		}
//		return fetchedArray.count() == 1 ? fetchedArray.objectAtIndex(0) : null;
//	}
//	
//	/**
//	 * Method fetches a single EGRDatabaseObject with fetch parameters (not distinct).
//	 * @param entityName
//	 * @param fetchQualifier
//	 * @param sortOrderingArray
//	 * @param fetchLimitNum
//	 * @param EOEditingContext
//	 * @return
//	 */
//	public static synchronized <D> D fetchObjectMatchingFetchSpecs(String entityName, 
//															EOQualifier fetchQualifier,
//															NSArray<EOSortOrdering> sortOrderingArray,
//															int fetchLimitNum,
//															EOEditingContext ec) {	
//		
//		NSArray<D> fetchedArray = fetchObjectsMatchingFetchSpecs(entityName, fetchQualifier, sortOrderingArray, false, true, null, fetchLimitNum, ec);
//		return !fetchedArray.isEmpty() ? fetchedArray.objectAtIndex(0) : null;
//	}
//	
//	/**
//	 * Method fetches a single distinct EGRDatabaseObject with fetch parameters.
//	 * @param entityName
//	 * @param fetchQualifier
//	 * @param sortOrderingArray
//	 * @param fetchLimitNum
//	 * @param EOEditingContext
//	 * @return
//	 */
//	public static <D> NSArray<D> fetchDistinctObjectsMatchingFetchSpecs(String entityName, EOQualifier fetchQualifier, NSArray<EOSortOrdering> sortOrderingArray, int fetchLimitNum, EOEditingContext ec) {
//		return fetchObjectsMatchingFetchSpecs(entityName, fetchQualifier, sortOrderingArray, true, true, null, fetchLimitNum, ec);
//	}
//	
//	/**
//	 * Method returns a list of objects matching the fetch specifications (not distinct)
//	 * @param entityName
//	 * @param fetchQualifier
//	 * @param sortOrderingArray
//	 * @param fetchLimitNum
//	 * @param EOEditingContext
//	 * @return
//	 */
//	public static <D> NSArray<D> fetchObjectsMatchingFetchSpecs(String entityName, EOQualifier fetchQualifier, NSArray<EOSortOrdering> sortOrderingArray, int fetchLimitNum, EOEditingContext ec) {
//		return fetchObjectsMatchingFetchSpecs(entityName, fetchQualifier, sortOrderingArray, false, true, null, fetchLimitNum, ec);
//	}
//	
//	
//	/**
//	 * Method returns all objects for entity with name
//	 * @param entityName
//	 * @param sortOrderingArray
//	 * @param fetchLimitNum
//	 * @param EOEditingContext
//	 * @return
//	 */
//	public static <D> NSArray<D> fetchAllObjectsForEntityNamed(String entityName, NSArray<EOSortOrdering> sortOrderingArray, int fetchLimitNum, EOEditingContext ec) {
//		return fetchObjectsMatchingFetchSpecs(entityName, null, sortOrderingArray, false, true, null, fetchLimitNum, ec);
//	}
	
	
	
//
// misc.
//

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