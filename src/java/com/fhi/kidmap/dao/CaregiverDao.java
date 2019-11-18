/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.Caregiver;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface CaregiverDao
{
    public void saveCaregiver(Caregiver c) throws Exception;
    public void updateCaregiver(Caregiver c) throws Exception;
    public void markedForDelete(Caregiver cgiver) throws Exception;
    public void deleteCaregiver(Caregiver cgiver) throws Exception;
    public void deleteAllCaregiversInHousehold(String hhUniqueId) throws Exception;
    public int getNoOfCaregivers(String hhUniqueId) throws Exception;
    public Caregiver getCaregiverByCaregiverId(String caregiverId) throws Exception;
    public String generateCaregiverId(String hhUniqueId) throws Exception;
    public Caregiver getCaregiverByName(String hhUniqueId,String firstName,String lastName) throws Exception;
    public List searchCaregiversFromTheSameHouseholdByPartsOfName(String hhUniqueId,String searchString) throws Exception;
    public List getListOfCaregivers(String additionalQuery) throws Exception;
    public List getListOfCaregiversFromSameHousehold(String hhUniqueId) throws Exception;
    public Caregiver setHouseholdheadStatus(Caregiver cgiver) throws Exception;
    public int getNoOfCaregiversPerCohort(String additionalQuery) throws Exception;
    public int getNoOfCaregiversFollowedupPerCohort(String additionalQuery) throws Exception;
    public int getNoOfHivPositiveCaregiversFollowedupPerCohort(String additionalQuery) throws Exception;
    public int getNoOfHivNegativeCaregiversFollowedupPerCohort(String additionalQuery) throws Exception;
    public int getNoOfHivUnknownCaregiversFollowedupPerCohort(String additionalQuery) throws Exception;
    public List getListOfCaregiversPerCohort(String additionalQuery) throws Exception;
    public List getListOfCaregiversFollowedupPerCohort(String additionalQuery) throws Exception;
    public List getListOfHivPositiveCaregiversFollowedupPerCohort(String additionalQuery) throws Exception;
    public List getListOfHivNegativeCaregiversFollowedupPerCohort(String additionalQuery) throws Exception;
    public List getListOfHivUnknownCaregiversFollowedupPerCohort(String additionalQuery) throws Exception;
    public List getHouseholdMembers(String caregiverId) throws Exception;
    public List getCaregiversFromSameCommunity(String communityCode) throws Exception;
    public Caregiver getCaregiverInstance(String caregiverId);
    public List getDistinctCaregiverIds(String additionalQuery) throws Exception;
    public int getNoOfCaregiversCurrentlyEnrolled(String additionalQuery) throws Exception;
    public List getListOfCaregiversCurrentlyEnrolled(String additionalQuery) throws Exception;
    public List getActiveCaregiversFromWithdrawalRecords() throws Exception;
    public void changeCaregiverId(String oldId,String newId) throws Exception;
    public Caregiver getCaregiverByHhUniqueIdAndCaregiverId(String hhUniqueId,String caregiverId) throws Exception;
    public int getNumberOfHivPosCaregiverCurrentlyEnrolledBasedOnVulnerability(String additionalQuery,int startValue,int endValue) throws Exception;
    public List getListOfHivPosCaregiverCurrentlyEnrolledBasedOnVulnerability(String additionalQuery,int startValue,int endValue) throws Exception;
    public int getNoOfCaregiversCurrentlyEnrolledBasedOnHivStatus(String additionalQuery,String hivStatus) throws Exception;
    public List getListOfCaregiversCurrentlyEnrolledBasedOnHivStatus(String additionalQuery,String hivStatus) throws Exception;
    public int getNoOfCaregiversCurrentlyEnrolledOnART(String additionalQuery) throws Exception;
    public int getNoOfCaregiversCurrentlyEnrolledInCare(String additionalQuery) throws Exception;
    public List getListOfCaregiversCurrentlyEnrolledInCareOrOnART(String additionalQuery,String careStatus) throws Exception;
    public int getNoOfCaregiversWithoutServiceRecords(String additionalQuery,String startDate,String endDate) throws Exception;
    public List getListOfCaregiversWithoutServiceRecords(String additionalQuery,String startDate,String endDate) throws Exception;
    public List getCaregiversWithYesWithdrawalStatus() throws Exception;
    public List getDistinctCaregiverIdsByCommunity(String communityCode) throws Exception;
    public void saveCaregiverHIVStatus(Caregiver cgiver) throws Exception;
    public int updateCaregiverHivStatusWithCorrectLabel() throws Exception;
    public Caregiver getCaregiverByHhUniqueIdAndHhHeadStatus(String hhUniqueId,String householdHeadStatus) throws Exception;
    public int processHiVStatusForCaregiversWithoutHIVStatusRecordInHivStatusUpdate() throws Exception;
}
