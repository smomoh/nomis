/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.OvcService;
import com.fhi.kidmap.report.ServiceReport;
import java.util.List;

/**
 *
 * @author HP USER
 */
public interface OvcServiceDao 
{
     public void saveOvcService(OvcService ovcService,boolean saveHiv,boolean saveBirthRegistration) throws Exception;
     public OvcService[] getOvcService(String ovcId) throws Exception;
     public void updateOvcService(OvcService ovcService,boolean saveHiv,boolean saveBirthRegistration) throws Exception;
     public void markedForDelete(OvcService ovcService) throws Exception;
     public void deleteOvcService(String ovcId, String dateAccessed) throws Exception;
     public void deleteService(OvcService ovcService) throws Exception;
     public OvcService getOvcServiceForTheMth(String ovcId, String serviceDate) throws Exception;
     public List getOvcServicePerMth(List paramList) throws Exception;
     public List getListOfOvcServed(String additionalQuery) throws Exception;
     public List getAllServiceRecords() throws Exception;
     public List getAllServiceRecords(int surveyNo) throws Exception;
     public List getOvcIdsFromServices() throws Exception;
     public List getOvcIdsFromServices(int surveyNo) throws Exception;
     public List getOvcServices(String ovcId) throws Exception ;
     public int getOvcServiceCount(String ovcId) throws Exception ;
     public List getServiceRecords(String startDate,String endDate,String additionalOrgQuery) throws Exception;
     public void deleteOvcServices(String ovcId) throws Exception;
     public List getOvcMthlyService(List paramList) throws Exception;
     public void changeOvcId(String oldId,String newId) throws Exception;
     public int getNumberOfServicesPerServiceRecord(OvcService service) throws Exception;
     public void setNumberOfServicesPerServiceRecord() throws Exception;
     public boolean isNumberOfServicesGreaterThanZeroAndLessThanThree(String ovcId) throws Exception;
     public boolean isNumberOfServicesMoreThanTwo(String ovcId) throws Exception;
     public List getDistinctOvcIdsFromOvcService(String begDate,String endDate) throws Exception;
     public List getOvcServicesByOvcIdAndPeriod(String ovcId,String begDate,String endDate) throws Exception;
     public List getOvcServicesByOvcIdAndAdditionalServiceQuery(String ovcId,String additionalServiceQuery) throws Exception;
     public List getOvcServicesByOvcIdAndAdditionalServiceQueryByNumberOfServices(String ovcId,String additionalServiceQuery) throws Exception;
     public List getServiceByPeriod(String begDate,String endDate) throws Exception;
     public List getServiceByStateAndPeriod(String stateCode,String begDate,String endDate) throws Exception;
     public List getServiceByCBOAndPeriod(String orgCode,String begDate,String endDate) throws Exception;
     public List getNumberOfOvcServedPerMonthByCBO() throws Exception;
     public List getNumberOfOvcServedPerMonthByCBO(String indicatorName,String stateCode,String startDate,String endDate,boolean currentlyEnrolled) throws Exception;
     public List getNumberOfOvcProvidedPsychosocialSupportServicesPerMonthByCBO(String stateCode,String startDate,String endDate) throws Exception;
     public List getNumberOfOvcProvidedNutritionalServicesPerMonthByCBO(String stateCode,String startDate,String endDate) throws Exception;
     public List getNumberOfOvcProvidedHealthServicesPerMonthByCBO(String stateCode,String startDate,String endDate) throws Exception;
     public List getNumberOfOvcProvidedHIVServicesPerMonthByCBO(String stateCode,String startDate,String endDate) throws Exception;
     public List getNumberOfOvcProvidedEducationalServicesPerMonthByCBO(String stateCode,String startDate,String endDate) throws Exception;
     public List getNumberOfOvcProvidedProtectionServicesPerMonthByCBO(String stateCode,String startDate,String endDate) throws Exception;
     public List getNumberOfOvcProvidedShelterServicesPerMonthByCBO(String stateCode,String startDate,String endDate) throws Exception;
     public List getNumberOfOvcProvidedEconomicStrengtheningServicesPerMonthByCBO(String stateCode,String startDate,String endDate) throws Exception;
     public List getNumberOfOvcReferredForServicesPerMonthByCBO(String stateCode,String startDate,String endDate) throws Exception;
     public List getNumberOfHIVPositiveOvcServedPerMonthByCBO(String stateCode,String startDate,String endDate) throws Exception;
     public List getNumberOfHIVNegativeOvcServedPerMonthByCBO(String stateCode,String startDate,String endDate) throws Exception;
     public List getNumberOfHIVUnknownOvcServedPerMonthByCBO(String stateCode,String startDate,String endDate) throws Exception;
     public List getNumberOfOvcSupportedToAccessHivServicesPerMonthByCBO(String indicatorName,String stateCode,String startDate,String endDate) throws Exception;
     public List getNumberOfOvcProvidedBirthRegistrationServicesPerMonthByCBO(String stateCode,String startDate,String endDate) throws Exception;
     public List getNumberOfOvcProvidedLegalServicesPerMonthByCBO(String stateCode,String startDate,String endDate) throws Exception;
     public List getHivServicesByOrgUnitCodeAndPeriod(String orgUnitCode,String begDate,String endDate) throws Exception;
     public List getOvcServicesByOvcIdAndEndDate(String ovcId,String endDate) throws Exception;
     
     public int getNumberOfOvcTestedHivPerCohort(String additionalQueryCriteria,String startDate,String endDate) throws Exception;
     public List getListOfOvcTestedHivPerCohort(String additionalQueryCriteria,String startDate,String endDate) throws Exception;
     public int getNumberOfOvcWithdrawnFromProgramTestedHiv(String additionalQueryCriteria,String startDate,String endDate) throws Exception;
     public List getListOfOvcWithdrawnFromProgramTestedHiv(String additionalQueryCriteria,String startDate,String endDate) throws Exception;
     
     public int getNumberOfOvcReferredForHIVServices(String additionalQueryCriteria) throws Exception;
     public List getServiceRecordsWithBirthRegistrationDetails(String additionalQueryCriteria) throws Exception;
     public int getNumberOfOvcProvidedBirthRegistrationServices(String additionalQuery) throws Exception;
     public List saveHivStatusOfOvcUnknownProvidedHTC() throws Exception;
     public List getNoOfOvcWithdrawnButServedWithinReportPeriodPerMonthByCBO(String indicatorName,String stateCode,String startDate,String endDate,String reasonWithdrawnFromProgram) throws Exception;
     public List getListOfOvcWithoutServiceRecords(String additionalQuery,String startDate,String endDate) throws Exception;
     public int getNoOfOvcWithoutServiceRecords(String additionalQuery,String startDate,String endDate) throws Exception;
     public OvcService getLastServiceForOvc(String ovcId) throws Exception;
     public int getNumberOfOvcProvidedPsychosocialSupportServices(String additionalQuery,String serviceType) throws Exception;
     public List getListOfOvcProvidedPsychosocialSupportServices(String additionalQuery,String serviceType) throws Exception;
     public void updateOvcServiceWithoutDateCheck(OvcService ovcService,boolean saveHiv,boolean saveBirthRegistration) throws Exception;
     public List getServiceRecordsWithHivInformation() throws Exception;
     public OvcService getOvcServiceWithoutHivInformation(String ovcId, String serviceDate) throws Exception;
     public List getListOfOvcServedByDomainAndHivStatus(String additionalQuery, String domainName) throws Exception;
     public int getNumberOfServedByDomainAndHivStatus(String additionalQuery, String domainName) throws Exception;
     public List getDistinctOvcIdForHivRecordsUpdate(boolean knownStatusOnly) throws Exception;
     public List getOvcServedByDomainAndHivStatusForReportDownload(String additionalQuery, String indicatorName,String startDate,String endDate,String domainName,String hivStatus) throws Exception;
     public List getOvcServedByHivStatusForReportDownload(String additionalQuery, String indicatorName,String startDate,String endDate,String hivStatus) throws Exception;
     public List getDistinctServiceDatesPerOvc(String ovcId) throws Exception;
     public OvcService getHTCReferralServiceRecordById(String ovcId,String startDate) throws Exception;
     public int getNumberOfOvcEnrolledInKidsClub(String additionalQuery, String startDate,String endDate) throws Exception;
     public List getListOfOvcEnrolledInKidsClub(String additionalQuery, String startDate,String endDate) throws Exception;
     public int getNumberOfOvcWithdrawnButServedWithinTheReportPeriodByHivStatus(String additionalQuery,String hivStatus,boolean onART, String startDate,String endDate) throws Exception;
     public List getListOfOvcWithdrawnButServedWithinTheReportPeriodByHivStatus(String additionalQuery,String hivStatus,boolean onART, String startDate,String endDate) throws Exception;
     public List getHTCReferralServiceRecordById(String ovcId) throws Exception;
     public List getServiceRecordsWithKnownHivStatusById(String ovcId) throws Exception;
     public List getHTCReferralServiceRecordsFromOvcService() throws Exception;
     public int getNoOfOvcServedWithinTheReportPeriodThatHasBirthCert(String additionalQuery,String startDate,String endDate,boolean currentlyEnrolled) throws Exception;
     public List getListOfOvcServedWithinTheReportPeriodThatHasBirthCert(String additionalQuery,String startDate,String endDate,boolean currentlyEnrolled) throws Exception;
     public int getNumberOfHouseholdsWhoseOvcWereServedWithinReportingPeriod(String additionalQuery,String startDate,String endDate,boolean currentlyEnrolled) throws Exception;
     public int getNumberOfGraduatedHouseholdsWhoseOvcWereServedWithinReportingPeriod(String additionalQuery,String startDate,String endDate,boolean currentlyEnrolled) throws Exception;
     public int getNumberOfOvcLinkedToGovtForPostViolenceServices(String additionalQuery, String startDate,String endDate) throws Exception;
     public List getListOfOvcLinkedToGovtForPostViolenceServices(String additionalQuery, String startDate,String endDate) throws Exception;
     public int getNumberOfAdolescentProvidedHivPreventionServices(String additionalQuery, String startDate,String endDate) throws Exception;
     public List getListOfAdolescentProvidedHivPreventionServices(String additionalQuery, String startDate,String endDate) throws Exception;
     public OvcService getServicesByServiceCode(OvcService service);
     public int getNoOfOvcNewlyEnrolledAndServedWithinTheReportPeriod(String additionalQuery,String sex,int startAge,int endAge,String startDate,String endDate) throws Exception;
     public List getListOfOvcAbusedOrExploited(String additionalQuery, String startDate,String endDate) throws Exception;
     public int getNumberOfOvcAbusedOrExploited(String additionalQuery, String startDate,String endDate) throws Exception;
     public int getNumberOfOvcUnknownOrNegativeHivStatusServedWithinReportPeriod(String additionalQueryCriteria,String startDate,String endDate,int startAge,int endAge,boolean currentlyEnrolled) throws Exception;
     public int getNumberOfActiveOvcWithUnknownOrNegativeHivStatusServedWithinReportPeriod(String additionalQueryCriteria,String startDate,String endDate,int startAge,int endAge,boolean currentlyEnrolled) throws Exception;
     public int getNumberOfGraduatedOvcWithUnknownOrNegativeHivStatusServedWithinReportPeriod(String additionalQueryCriteria,String startDate,String endDate,int startAge,int endAge,boolean currentlyEnrolled) throws Exception;
}
