/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.Caregiver;
import com.fhi.kidmap.business.FollowupSurvey;
import com.fhi.kidmap.business.HivStatusUpdate;
import com.fhi.kidmap.business.Ovc;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface HivStatusUpdateDao 
{
    public void saveHivStatusUpdate(HivStatusUpdate hsu) throws Exception;
    public void updateHivStatusUpdate(HivStatusUpdate hsu) throws Exception;
    public void updateHivStatusOnly(HivStatusUpdate hsu) throws Exception;
    public void updateActiveHivStatusUpdate(HivStatusUpdate hsu) throws Exception;
    public void deleteHivStatusUpdate(HivStatusUpdate hsu) throws Exception;
    public HivStatusUpdate getHivStatusUpdate(String recordId) throws Exception;
    public List getAllHivStatusUpdatesPerClient(String clientId) throws Exception;
    public List getAllHivStatusUpdates() throws Exception;
    public void setActiveHivStatus(List clientIdList);
    public void setActiveHivStatus(String clientId) throws Exception;
    public HivStatusUpdate getHivStatusUpdatesByClientIdAndDateOfStatus(String clientId,String dateOfCurrentStatus) throws Exception;
    public int getNumberOfHivStatusUpdatesByClientType(String clientType,String pointOfUpdate) throws Exception;
    public HivStatusUpdate getActiveHivStatusUpdatesByClientId(String clientId) throws Exception;
    public List getBeneficiaryListPerHousehold(String beneficiaryType,String hhUniqueId) throws Exception;
    public List getHivStatusUpdates(String additionalQueryCriteria) throws Exception;
    public List getOvcHivStatusUpdates(String additionalQueryCriteria) throws Exception;
    public List getCaregiverHivStatusUpdates(String additionalQueryCriteria) throws Exception;
    public void deleteAllHivStatusUpdatesPerClient(String clientId) throws Exception;
    public void changeClientId(String oldClientId,String newClientId) throws Exception;
    public List getHivStatusUpdatesByPointOfUpdateAndHivStatus(String pointOfUpdate,String hivStatus) throws Exception;
    public List getHivStatusUpdatesByPointOfUpdate(String pointOfUpdate) throws Exception;
    public List getHivStatusRecordsFromOvcService(String communityCode) throws Exception;
    public List getHivStatusRecordsFromOvcFollowup(String communityCode) throws Exception;
    public List getHivStatusRecordsFromOvcEnrollment(String communityCode) throws Exception;
    public List getDistinctClientIdByPointOfUpdate(String pointOfUpdate,boolean knownStatusOnly) throws Exception;
    public List getHivStatusUpdatesByClientIdAndPointOfUpdate(String clientId,String pointOfUpdate) throws Exception;
    public int getNumberOfHivPositiveOvcEnrolledOnART(String additionalQueryCriteria,String startDate,String endDate,boolean currentlyEnrolled) throws Exception;
    public List getListOfHivPositiveOvcEnrolledOnART(String additionalQueryCriteria,String startDate,String endDate,boolean currentlyEnrolled) throws Exception;
    public void updateHivTreatmentStatus(List hivOnTreatmentList) throws Exception;
    public HivStatusUpdate getHivStatusUpdateByClientIdDateOfStatusAndPointOfUpdate(String clientId,String dateOfCurrentStatus,String pointOfUpdate) throws Exception;
    public List getOvcHivStatusByStatusAndClientType(String hivStatus, String beneficiaryType) throws Exception;
    public List getHivStatusUpdateWithDefaultLastHivTrackingDateByCommunity(String communityCode) throws Exception;
    public int getNumberOfOvcTestedAndReceivedResult(String additionalQueryCriteria,String startDate,String endDate,boolean currentlyEnrolled) throws Exception;
    public List getListOfOvcTestedAndReceivedResult(String additionalQueryCriteria,String startDate,String endDate,boolean currentlyEnrolled) throws Exception;
    public int getNumberOfHivPositiveOvcIdentifiedWithinTheReportPeriod(String additionalQueryCriteria,String startDate,String endDate,boolean currentlyEnrolled) throws Exception;
    public List getListOfHivPositiveOvcIdentifiedWithinTheReportPeriod(String additionalQueryCriteria,String startDate,String endDate,boolean currentlyEnrolled) throws Exception;
    public int getNumberOfHivPositiveOvcIdentifiedAndServedWithinTheReportPeriod(String additionalQueryCriteria,String startDate,String endDate,boolean currentlyEnrolled,boolean onTreatment) throws Exception;
    public int updateCaregiverHivStatusWithBaselineHivStatus() throws Exception;
    public int updateCaregiverHivStatusWithHivStatusAtService() throws Exception;
    public int updateHivStatusWithCorrectLabel() throws Exception;
    public int updateClientTypeOnCaregiverHivStatusUpdate() throws Exception;
    public int updateClientTypeOnOvcHivStatusUpdate() throws Exception;
    public int updateCaregiverActiveHivStatusWithPreviousHivStatus() throws Exception;
    public List getListOfHivPositiveOvcIdentifiedAndServedWithinTheReportPeriod(String additionalQueryCriteria,String startDate,String endDate,boolean currentlyEnrolled,boolean onTreatment) throws Exception;
    public List getHouseholdAndCaregiverHivStatusRecords(String hivStatus) throws Exception;
}
