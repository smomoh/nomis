/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.Ovc;
import java.util.List;

/**
 *
 * @author HP USER
 */
public interface OvcDao {
    public void saveOvc(Ovc ovc,boolean saveHiv,boolean saveBirthRegistration) throws Exception;
    public void updateOvc(Ovc ovc,boolean saveHiv,boolean saveBirthRegistration) throws Exception;
    public void markedForDelete(Ovc ovc) throws Exception;
    public void deleteOvc(Ovc ovc) throws Exception;
    public void deleteOvc(String ovcId) throws Exception;
    public void deleteAllOvcRecords(String ovcId) throws Exception;
    public void saveOvcHIVStatus(Ovc ovc) throws Exception;
    public Ovc getOvc(String ovcId) throws Exception;
    public List getOvcs() throws Exception;
    public List searchOvc(String ovcId) throws Exception;
    public List getOvcList() throws Exception;
    public List getOvcList(String startDate,String endDate,String additionalOrgQuery) throws Exception;
    public List getOvcCount(String startDate,String endDate) throws Exception;
    public List getOvcRecords(String[] params) throws Exception;
    public List getNumberOfOvcEnrolledPerMonthByCBO(String indicatorName,String stateCode, boolean currentlyEnrolled) throws Exception;
    public List getNumberOfHivPositiveOvcCurrentlyEnrolledPerMonthByCBO(String indicatorName,String stateCode,String startDate,String endDate,boolean currentlyEnrolled) throws Exception;
    public List getNumberOfHivNegativeOvcCurrentlyEnrolledPerMonthByCBO(String indicatorName,String stateCode,String startDate,String endDate,boolean currentlyEnrolled) throws Exception;
    public List getNumberOfHivUnknownOvcCurrentlyEnrolledPerMonthByCBO(String indicatorName,String stateCode,String startDate,String endDate,boolean currentlyEnrolled) throws Exception;
    public List getOVC(String ovcId) throws Exception;
    public List getOVCByPartOfNames(String partOfName) throws Exception;
    public List getOvcByNameAndAge(String fname,String lname,int age,String ageunit) throws Exception;
    public List getOvcByNameAndCaregiver(String fname,String lname,String caregiverName) throws Exception;
    public List getOvcWithNoCaregiverId() throws Exception;
    public List getDistinctCboCode() throws Exception;
    public void changeOvcId(String oldId,String newId) throws Exception;
    public List getListOfCompatibilityOvc(String cboCode) throws Exception;
    public String updateOvcCurrentAge() throws Exception;
    public int getNoOfOvcsPerCaregiver(String additionalQuery,String caregiverUniqueId) throws Exception;
    public List getListOfOvcsPerCaregiver(String additionalQuery,String caregiverUniqueId) throws Exception;
    public List getOvcPerHousehold(String hhUniqueId) throws Exception;
    public List getDistinctCboCodeFromCompatibilityOvc() throws Exception;
    public List getOvcList(String additionalOrgQuery) throws Exception;
    public int getNoOfOvcWithoutHHUniqueId() throws Exception;
    public List getOvcWithoutHHUniqueId() throws Exception;
    public void setHHUniqueId() throws Exception;
    public List getUniqueOvcId() throws Exception;
    public List getOvcs(String stateCode) throws Exception;
    public String updateOvcCurrentAge(List stateList) throws Exception;
    public String updateOvcCurrentAge(List stateList,int startAge,int endAge) throws Exception;
    public List getCountOfOvcsByCurrentAge(String logic,int currentAge) throws Exception;
    public List getCurrentAgeFromEnrollment() throws Exception;
    public List getListOfOvcsByCurrentAge(String stateCode,String logic,int currentAge) throws Exception;
    public void normalizeBaselineAssessment() throws Exception;
    public List getNumberOfOvcEnrolledPerMonthByCBO() throws Exception;
    public int getNumberOfOvcEnrolledByOrgUnit(String orgUnitCode) throws Exception;
    public List getNumberOfOvcWithBirthCertificateAtBaseline(String stateCode,String startDate,String endDate) throws Exception;
    public List getOfOvcWithKnownHIVStatusAtBaseline(String stateCode,String startDate,String endDate) throws Exception;
    public List getNumberOfOvcNewlyEnrolledPerMonthByCBO(String indicatorName,String stateCode,String startDate,String endDate) throws Exception;
    public List getNumberOfHivPositiveOvcAtBaseline(String stateCode,String startDate,String endDate) throws Exception;
    public int getNumberOfOvcEnrolledPerCBO(String stateCode,String lgaCode,String cboCode) throws Exception;
    public List getNumberOfOvcOutOfSchoolAtEnrollmentPerMonthByCBO(String stateCode,String startDate,String endDate) throws Exception;
    public List getNumberOfOvcInSchoolAtEnrollmentPerMonthByCBO(String indicatorName,String stateCode,String startDate,String endDate) throws Exception;
    public int getNumberOfOvcOutOfSchoolPerCBO(String stateCode,String lgaCode,String cboCode) throws Exception;
    public int getNumberOfOvcOutOfSchoolByAgeGroupAndPerCBO(String stateCode,String lgaCode,String cboCode,String logic,int currentAge) throws Exception;
    public List getNumberOfHivPositiveOvcEnrolledPerMonthByCBO(String indicatorName,String stateCode,String startDate,String endDate) throws Exception;
    public List getNumberOfHivNegativeOvcEnrolledPerMonthByCBO(String stateCode,String startDate,String endDate) throws Exception;
    public List getNumberOfHivUnknownOvcEnrolledPerMonthByCBO(String stateCode,String startDate,String endDate) throws Exception;
    public int getNumberOfOvcEnrolledPerCohort(String additionalQuery) throws Exception;
    public List getListOfOvcEnrolledPerCohort(String additionalQuery) throws Exception;
    public List getOvcListPerHousehold(String hhUniqueId) throws Exception;
    public void changeCaregiverId(String oldCaregiverId, String newCaregiverId) throws Exception;
    public List getNumberOfOvcInChildHeadedHouseholdsPerMonthByCBO(String stateCode,String startDate,String endDate) throws Exception;
    public List getNumberOfOvcVulnerableToHIVAtEnrollmentPerMonthByCBO(String stateCode,String startDate,String endDate) throws Exception;
    public List getNumberOfOvcThatDroppedOutOfSchoolPerMonthByCBO(String stateCode,String startDate,String endDate) throws Exception;
    public void deleteAllOvcInHousehold(String hhUniqueId) throws Exception;
    public List getOvcListPerCaregiver(String caregiverId) throws Exception;
    public int getNoOfOvcThatAreHIVPositive(String additionalQueryCriteria) throws Exception;
    public List getListOfOvcThatAreHIVPositive(String additionalQueryCriteria) throws Exception;
    public int getNumberOfOvcEnrolledFromHouseholdsWithChronicallyIllMembers(String additionalQueryCriteria) throws Exception;
    public List getListOfOvcByOvcId(List listOfOvcIds) throws Exception;
    public List getActiveOvcFromWithdrawalRecords() throws Exception;
    public List getNumberOfHivUnknownOvcCurrentlyEnrolledAndProvidedHTCPerMonthByCBO(String indicatorName,String stateCode,String startDate,String endDate) throws Exception;
    public List getNumberOfHIVPositiveOvcIdentifiedWithinTheReportingPeriodPerCBO(String indicatorName,String stateCode,String startDate,String endDate) throws Exception;
    public List getNumberOfHIVNegativeOvcIdentifiedWithinTheReportingPeriodPerCBO(String indicatorName,String stateCode,String startDate,String endDate) throws Exception;
    public List getNumberOfHIVUnknownOvcIdentifiedWithinTheReportingPeriodPerCBO(String indicatorName,String stateCode,String startDate,String endDate) throws Exception;
    public List getNumberOfOvcCurrentlyEnrolledAndInSchoolAtEnrollmentPerMonthByCBO(String indicatorName,String stateCode,String startDate,String endDate) throws Exception;
    public int getNumberOfActiveOvcThatWereInSchoolAtEnrollment(String additionalQuery) throws Exception;
    public List getListOfActiveOvcThatWereInSchoolAtEnrollment(String additionalQuery) throws Exception;
    public int getNumberOfActiveOvcThatAreCurrentlyOutOfSchool(String additionalQuery) throws Exception;
    public List getListOfActiveOvcOutOfSchool(String additionalQuery) throws Exception;
    public int getNumberOfAdolescentsWhoseHouseholdsWereProvidedEconomicStrengthening(String additionalQuery,String startDate,String endDate,String currentlyEnrolledQuery)  throws Exception;
    public List getListOfAdolescentsWhoseHouseholdsWereProvidedEconomicStrengthening(String additionalQuery,String startDate,String endDate,String currentlyEnrolledQuery) throws Exception;
    public int getNumberOfHivPosOvcCurrentlyEnrolledBasedOnVulnerability(String additionalQuery,int startValue,int endValue) throws Exception;
    public int getNumberOfOvcWhoseHouseholdsWereProvidedEconomicStrengthening(String additionalQuery,String startDate,String endDate,String currentlyEnrolledQuery,int startAge,int endAge) throws Exception;
    public List getListOfOvcWhoseHouseholdsWereProvidedEconomicStrengthening(String additionalQuery,String startDate,String endDate,String currentlyEnrolledQuery,int startAge,int endAge) throws Exception;
    public List getListOfHivPosOvcCurrentlyEnrolledBasedOnVulnerability(String additionalQuery,int startValue,int endValue) throws Exception;
    public int getNumberOfOvcCurrentlyEnrolledInHouseholdsWithBaselineAssessment(String additionalQuery) throws Exception;
    public List getListOfOvcCurrentlyEnrolledInHouseholdsWithBaselineAssessment(String additionalQuery) throws Exception;
    public List getListOfOvcEnrolledWithinTheReportPeriod(String additionalQuery,String startDate,String endDate) throws Exception;
    public int getNumberOfOvcCurrentlyEnrolledByHivStatus(String additionalQuery,String hivStatus) throws Exception;
    public int getNumberOfOvcCurrentlyEnrolled(String additionalQuery) throws Exception;
    public List getListOfOvcThatHasNoHIVRiskAssessmentRecord(String additionalQuery) throws Exception;
    public int getNumberOfOvcThatHasNoHIVRiskAssessmentRecord(String additionalQuery) throws Exception;
    public List getNoOfOvcWithdrawnFromProgramWithinReportPeriodPerMonthByCBO(String indicatorName,String stateCode,String startDate,String endDate,String reasonWithdrawnFromProgram) throws Exception;
    public List getOvcWithYesWithdrawalStatus() throws Exception;
    public List getListOfOvcEnrolledBeforeTheStartOfReportPeriod(String additionalQuery,String startDate,boolean currentlyEnrolled) throws Exception;
    public List getListOfOvcNewlyEnrolled(String additionalQuery,String startDate,String endDate,boolean currentlyEnrolled) throws Exception;
    public List getListOfOvcCurrentlyEnrolled(String additionalQuery) throws Exception;
    public List getDistinctOvcIdsByCommunity(String communityCode) throws Exception;
    public int getNumberOfRecordsWithIncorrectCurrentAgeUnit() throws Exception;
    public List getListOfOvcByCommunity(String communityCode) throws Exception;
    public List getRecordsWithoutCurrentAgeUnitsByCommunity(String communityCode) throws Exception;
    public List getDistinctCommunityCodesWithIncorrectCurrentAgeUnit() throws Exception;
    public int setCurrentAgeUnitByCommunity(String communityCode) throws Exception;
    public List getListOfOvcWithBirthCertificateAtBaseline() throws Exception;
    public int getNumberOfOvcPerHousehold(String hhUniqueId, boolean currentlyEnrolled) throws Exception;
    public List getListOfOvcWithoutHivRecordsByCommunity(String communityCode) throws Exception;
    public List getListOfOvcWithoutHivRecords() throws Exception;
    public List searchOvcByEnrollmentId(String ovcId) throws Exception;
    public List getListOfOvcWithoutLastServiceDateByCommunity(String communityCode) throws Exception;
    public List getOvcs(String stateCode,int startAge, int endAge) throws Exception;
    public Ovc getOvcWithUpdatedWithdrawalStatus(Ovc ovc) throws Exception;
}
