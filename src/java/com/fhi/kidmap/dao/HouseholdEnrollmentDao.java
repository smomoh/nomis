/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.HouseholdEnrollment;
import java.util.List;

/**
 *
 * @author Siaka
 */
public interface HouseholdEnrollmentDao
{
    public void saveHouseholdEnrollment(HouseholdEnrollment hhe) throws Exception;
    public void updateHouseholdEnrollment(HouseholdEnrollment hhe) throws Exception;
    public void markedForDelete(HouseholdEnrollment hhe) throws Exception;
    public void deleteHouseholdEnrollment(HouseholdEnrollment hhe) throws Exception;
    public void deleteHouseholdEnrollmentOnly(HouseholdEnrollment hhe) throws Exception;
    public List getListOfHouseholdEnrollment(String additionalQuery,String sortOrder) throws Exception;
    public HouseholdEnrollment getHouseholdEnrollment(String hhUniqueId) throws Exception;
    public int getNoOfHouseholdsEnrolled() throws Exception;
    public List getListOfHouseholdsWithoutPartner(String additionalOrgQuery) throws Exception;
    public List getListOfHouseholdsEnrolledWithoutPartner(String additionalQuery) throws Exception;
    public List getDistinctOrgCodes() throws Exception;
    public HouseholdEnrollment getHouseholdEnrollmentByNameAndUniqueId(String hhUniqueId,String hhFirstname,String hhSurname) throws Exception;
    public String updateHheCurrentAge() throws Exception;
    public List getListOfHouseholdEnrollmentByCommunityCode(String communityCode) throws Exception;
    public List getListOfHouseholdEnrollmentByOrgCode(String orgCode) throws Exception;
    public List getHVIStateList() throws Exception;
    public List getHVIOrgList(String lgaId) throws Exception;
    public List getHVILgaList(String stateId) throws Exception;
    public List getHVIWardList(String cboId) throws Exception;
    public List getDistinctLgaCodes() throws Exception;
    public List getDistinctStateCodes() throws Exception;
    public List getDistinctCommunityCodes() throws Exception;
    public int getNumberOfHouseholdsEnrolled(String additionalQuery) throws Exception;
    public String generateNewHhUniqueId(String existingHhUniqueId) throws Exception;
    public List getHVIOrgListByStateCode(String stateCode) throws Exception;
    public List getHouseholdMembers(String hhUniqueId) throws Exception;
    public void deleteHouseholdMembers(String hhUniqueId) throws Exception;
    public List getDistinctCommunityCodes(String stateCode,String lgaCode,String orgCode) throws Exception;
    public int updateHouseholdHeadNameWithCaregiverName() throws Exception;
    public List getDistinctCommunityCodesPerState(String stateCode) throws Exception;
    public List getDistinctOrgCodesPerLga(String stateCode,String lgaCode) throws Exception;
    public HouseholdEnrollment getHouseholdEnrollmentInstance(String hhUniqueId);
    public int getNumberOfHouseholdsCurrentlyEnrolled(String additionalQuery) throws Exception;
    public List getListOfHouseholdIdsCurrentlyEnrolled(String additionalQuery) throws Exception;
    public List getActiveHouseholdsFromWithdrawalRecords() throws Exception;
    public List getDistinctStateCodesFromHouseholdEnrollmentRecords(String lgaCode) throws Exception;
    public List searchHouseholdsBySerialNumber(String serialNo) throws Exception;
    public List correctLgaCodesInHouseholdEnrollmentRecords() throws Exception;
    public List getDistinctLgaAndCboAndCommunityCodesFromHouseholdEnrollmentRecords(String stateCode) throws Exception;
    public List getDistinctHouseholdUniqueIdByCommunityCode(String communityCode) throws Exception;
    public List getListOfHouseholdsByHhuniqueIdPart(String stateLgaCboCode) throws Exception;
    public List getNumberOfHouseholdsEnrolledPerMonthByCBO(String indicatorName,String stateCode, boolean currentlyEnrolled) throws Exception;
    public List getNumberOfHouseholdsNewlyEnrolledPerMonthByCBO(String indicatorName,String stateCode, String startDate, String endDate,boolean currentlyEnrolled) throws Exception;
    public List getDistinctYearOfAssessment() throws Exception;
    public void deleteHouseholdsByStateLgaCBO(String stateLgaCboCode) throws Exception;
    public List getHouseholdsWithYesWithdrawalStatus() throws Exception;
    public List getListOfHouseholdsCurrentlyEnrolled(String additionalQuery) throws Exception;
    public List getHouseholdsWithIncorrectDateOfAssessment() throws Exception;
    public int getHouseholdVulnerabilityScore(HouseholdEnrollment hhe) throws Exception;
    public int getNumberOfHouseholdsWithBaselineAssessement(String additionalQuery,boolean currentlyEnrolled) throws Exception;
    public List getListOfHouseholdsWithBaselineAssessement(String additionalQuery,boolean currentlyEnrolled) throws Exception;
    public int getNumberOfHouseholdsByBaselineVulnerabilityStatus(String additionalQuery,boolean currentlyEnrolled,int startValue, int endValue) throws Exception;
    public List getListOfHouseholdsByBaselineVulnerabilityStatus(String additionalQuery,boolean currentlyEnrolled,int startValue, int endValue) throws Exception;
    public HouseholdEnrollment getHouseholdEnrollmentByUniqueIdAndHnvaId(String hhUniqueId,int hhvaId) throws Exception;
    public List getDistinctPartnerCodes() throws Exception;
    public List getDistinctPartnerCodesByCommunity(String communityCode) throws Exception;
    public List getDistinctStateCodesByPartner(String partnerCode) throws Exception;
    public List getDistinctRecordsByPartner(String partnerCode) throws Exception;
    public List getListOfHouseholdEnrollmentWithIncompleteVulnerabilityScoreByCommunityCode(String communityCode,int baselineAssessmentScore) throws Exception;
    public int getHVAThematicAreaCount(String hvaProperty,int score,String queryCriteria) throws Exception;
    public List getDistinctLgaCodesByStateCode(String stateCode) throws Exception;
    public HouseholdEnrollment getHouseholdEnrollmentByEnrollmentId(String hhEnrollmentId) throws Exception;
    public List getDistinctCBOCodesByLga(String lgaCode) throws Exception;
    public void testLoggingWithException();
    public void saveHouseholdHeadHIVStatus(HouseholdEnrollment hhe) throws Exception;
    public List getDistinctPartnerCodesByState(String stateCode) throws Exception;
    public List getDistinctLgaCodesByStateAndPartnerCodes(String stateCode,String partnerCode) throws Exception;
}
