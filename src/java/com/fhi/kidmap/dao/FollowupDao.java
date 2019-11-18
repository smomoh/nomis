/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.FollowupSurvey;
import java.util.List;

/**
 *
 * @author HP USER
 */
public interface FollowupDao {
    public void saveFollowup(FollowupSurvey followup,boolean saveHiv,boolean saveBirthRegistration) throws Exception;
    public FollowupSurvey getFollowup(String ovcId, String dateOfSurvey) throws Exception;
    public List getFollowup() throws Exception;
    public void markedForDelete(FollowupSurvey followup) throws Exception;
    public void deleteFollowup(FollowupSurvey followup) throws Exception;
    public void deleteOvcFollowups(String ovcId) throws Exception;
    public void updateFollowup(FollowupSurvey followup,boolean saveHiv,boolean saveBirthRegistration) throws Exception;
    public List getDistinctFollowupIds(String additionalQueryCriteria) throws Exception;
    public List getDistinctFollowup(String additionalQueryCriteria) throws Exception;
    public List getFollowupRecordsByOvcId(String ovcId) throws Exception;
    public List getFollowupRecords(String orgQueryCriteria, String startDate,String endDate) throws Exception;
    public void changeOvcId(String oldId,String newId) throws Exception;
    public List getFollowupSurvey(String additionalQueryCriteria) throws Exception;
    public void normalizeFollowupAssessment() throws Exception;
    public List getNoOfOVCWithUpdatedBirthRegistrationAtFollowup(String stateCode,String startDate,String endDate) throws Exception;
    public List getNoOfOVCWithUpdatedHIVStatusAtFollowup(String stateCode,String startDate,String endDate) throws Exception;
    public List getNoOfHIVPositiveOVCIdentifiedAtFollowup(String stateCode,String startDate,String endDate) throws Exception;
    public List getNumberOfOVCEnrolledInSchoolAtFollowupPerMth(String indicatorName,String state,String startDate,String endDate) throws Exception;
    public List getNumberOfActiveOVCEnrolledInSchoolAtFollowupPerMth(String indicatorName,String state,String startDate,String endDate) throws Exception;
    public int getNoOfOVCFollowedupPerCohort(String additionalQuery) throws Exception;
    public int getNoOfHivPositiveOVCFollowedupPerCohort(String additionalQuery) throws Exception;
    public int getNoOfHivNegativeOVCFollowedupPerCohort(String additionalQuery) throws Exception;
    public int getNoOfHivUnknownOVCFollowedupPerCohort(String additionalQuery) throws Exception;
    public int getNoOfOVCNotFollowedupPerCohort(String additionalQuery) throws Exception;
    public List getListOfOVCNotFollowedupPerCohort(String additionalQuery) throws Exception;
    public List getListOfOVCFollowedupPerCohort(String additionalQuery) throws Exception;
    public List getListOfHivPositiveOVCFollowedupPerCohort(String additionalQuery) throws Exception;
    public List getListOfHivNegativeOVCFollowedupPerCohort(String additionalQuery) throws Exception;
    public List getListOfHivUnknownOVCFollowedupPerCohort(String additionalQuery) throws Exception;
    public List getDistinctListOVCIdsFromFollowup(String additionalQuery) throws Exception;
    public List getFollowupRecordsByCaregiverId(String caregiverId) throws Exception;
    public void changeCaregiverId(String oldCaregiverId, String newCaregiverId) throws Exception;
    public List getFollowedupRecordsWithBirthRegistrationDetails(String additionalQuery) throws Exception;
    public List getFollowedupRecordsWithSchoolDetails(String ovcId) throws Exception;
    public int getNumberOfFollowedupRecordsWithBirthRegistrationDetails(String additionalQueryCriteria) throws Exception;
    public int getNumberOfActiveOVCEnrolledInSchoolAtFollowup(String additionalQuery) throws Exception;
    public List getListOfActiveOVCEnrolledInSchoolAtFollowup(String additionalQuery) throws Exception;
    public int getNumberOfActiveOVCNotInSchoolAtEnrollmentAndFollowup(String additionalQuery) throws Exception;
    public FollowupSurvey getLastFollowupSurveyForOvc(String ovcId) throws Exception;
    public List getFollowedupRecordsOrderedByDateDesc(String ovcId) throws Exception;
    public FollowupSurvey getMostRecentFollowupRecord(String ovcId) throws Exception;
    public List getFollowupRecordsWithCSIByOvcId(String ovcId) throws Exception;
    public List getDistinctOvcIdAndDateOfFollowup() throws Exception;
    public List getFollowupRecords(String ovcId, String dateOfSurvey) throws Exception;
    public List getFollowedupRecordsWithKnownHivStatusOrderedByDateDesc(String ovcId) throws Exception;
}
