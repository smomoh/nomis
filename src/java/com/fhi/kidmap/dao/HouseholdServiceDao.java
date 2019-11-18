/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.HouseholdService;
import java.util.List;

/**
 *
 * @author Siaka
 */
public interface HouseholdServiceDao 
{
    public void saveHouseholdService(HouseholdService hhs) throws Exception;
    public void updateHouseholdService(HouseholdService hhs) throws Exception;
    public void markedForDelete(HouseholdService hhs) throws Exception;
    public void deleteHouseholdService(HouseholdService hhs) throws Exception;
    public void deleteAllHouseholdServices(String hhUniqueId) throws Exception;
    public HouseholdService getHouseholdService(String caregiverId, String serviceDate) throws Exception;
    public List getAllServicesPerHousehold(String uniqueId) throws Exception;
    public List getAllHouseholdServices() throws Exception;
    public List getHouseholdServiceList(String additionalQuery) throws Exception;
    public int getNumberOfServicesPerServiceRecord(HouseholdService hhs) throws Exception;
    public void setCaregiverIdAndRecipientTypeInHhs() throws Exception;
    public void changeCaregiverId(String oldCaregiverId, String newHhUniqueId,String newCaregiverId) throws Exception;
    public List getAllServicesPerCaregiver(String caregiverId) throws Exception;
    public void changeHhUniqueIdInHouseholdService(String oldHhUniqueId,String newHhUniqueId);
    public List getOvcServicesByOvcIdAndEndDate(String hhUniqueId,String endDate) throws Exception;
    public void fixHhUniqueIdInHouseholdService() throws Exception;
    public List getServiceByCBOAndPeriod(String orgCode,String begDate,String endDate) throws Exception;
    public List getServiceByPeriod(String begDate,String endDate) throws Exception;
    public List getNumberOfCaregiversServedPerMonthByCBO(String indicatorName,String stateCode,String startDate,String endDate) throws Exception;
    public int getNumberOfHouseholdsProvidedHES(String additionalQuery,String currentlyEnrolledQuery,String startDate,String endDate) throws Exception;
    public List getListOfHouseholdsProvidedHES(String additionalQuery,String currentlyEnrolledQuery,String startDate,String endDate) throws Exception;
    public List getNumberOfCaregiversServedHESPerMonthByCBO(String indicatorName,String stateCode,String startDate,String endDate,String hesType) throws Exception;
    public int getNumberOfCaregiversTestedForHiv(String additionalQuery,String startDate,String endDate) throws Exception;
    public List getListOfCaregiversTestedForHiv(String additionalQuery,String startDate,String endDate) throws Exception;
    public int getNumberOfCaregiversProvidedHES(String additionalQuery,String startDate,String endDate,String hesType) throws Exception;
    public List getListOfCaregiversProvidedHES(String additionalQuery,String startDate,String endDate,String hesType) throws Exception;
    public List getHouseholdServicesPerCaregiver(String caregiverId, String startDate,String endDate) throws Exception;
    public List getListOfHouseholdServiceRecordsWithoutCaregiverId() throws Exception;
    public List getAllServicesPerCaregiverOrderdByServiceDate(String caregiverId,String order) throws Exception;
    public List getListOfHouseholdServiceRecordsMalformedHhUniqueId() throws Exception;
    public HouseholdService getServicesByServiceCode(HouseholdService service);
    public int getNoOfCaregiversNewlyEnrolledAndServedWithinTheReportPeriod(String additionalQuery,String sex,String startDate,String endDate) throws Exception;
}
