/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.HouseholdFollowupAssessment;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface HouseholdFollowupAssessmentDao
{
    public void saveHouseholdFollowupAssessment(HouseholdFollowupAssessment hhfa) throws Exception;
    public void updateHouseholdFollowupAssessment(HouseholdFollowupAssessment hhfa) throws Exception;
    public void markedForDelete(HouseholdFollowupAssessment hhfa) throws Exception;
    public void deleteHouseholdFollowupAssessment(HouseholdFollowupAssessment hhfa) throws Exception;
    public void deleteAllAssessmentPerHousehold(String hhUniqueId) throws Exception;
    public HouseholdFollowupAssessment getHouseholdFollowupAssessment(String recordId) throws Exception;
    public HouseholdFollowupAssessment getHouseholdFollowupAssessmentByIdAndDate(String hhUniqueId,String dateOfAssessment) throws Exception;
    public List getListOfAssessmentsPerHousehold(String hhUniqueId) throws Exception;
    public List getAllHouseholdFollowupAssessments() throws Exception;
    public String generateUniqueId() throws Exception;
    public List getListOfAssessments(String additionalCriteria) throws Exception;
    public int getNumberOfHouseholdFollowupAssessmentsPerCohort(String additionalCriteria) throws Exception;
    public int getNumberOfHouseholdWithoutFollowupAssessmentsPerCohort(String additionalCriteria) throws Exception;
    public List getListOfHouseholdFollowupAssessmentsPerCohort(String additionalCriteria) throws Exception;
    public List getListOfHouseholdWithoutFollowupAssessmentsPerCohort(String additionalCriteria) throws Exception;
    public List getFollowupAssessments(String hhUniqueId) throws Exception;
    public List getFollowupAssessmentsAscOrder(String hhUniqueId) throws Exception;
    public List getFollowupAssessmentsWithoutScoresByCommunityCode(String communityCode) throws Exception;
    public HouseholdFollowupAssessment getHouseholdFollowupAssessmentByUniqueIdAndHnvaId(String hhUniqueId,int hhvaId) throws Exception;
    public int getNumberOfHouseholdsWithFollowupAssessement(String additionalQuery,boolean currentlyEnrolled) throws Exception;
    public List getListOfHouseholdsWithFollowupAssessement(String additionalQuery,boolean currentlyEnrolled) throws Exception;
    public int getFollowupVulnerabilityScore(HouseholdFollowupAssessment hhfa) throws Exception;
    public List getFollowupAssessmentsDescOrder(String hhUniqueId) throws Exception;
    public HouseholdFollowupAssessment getMostRecentHouseholdFollowupAssessment(String hhUniqueId) throws Exception;
}
