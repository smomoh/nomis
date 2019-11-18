/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.BirthRegistration;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface BirthRegistrationDao 
{
    public void saveBirthRegistration(BirthRegistration br) throws Exception;
    public void updateBirthRegistration(BirthRegistration br) throws Exception;
    public void markedForDelete(BirthRegistration br) throws Exception;
    public void deleteBirthRegistration(BirthRegistration br) throws Exception;
    public BirthRegistration getBirthRegistration(String recordId) throws Exception;
    public BirthRegistration getBirthRegistration(String clientId,String birthRegistrationStatus) throws Exception;
    public BirthRegistration getBirthRegistrationByIdAndDate(String clientId,String dateOfRegistration) throws Exception;
    public List getBirthRegistrationRecords(String recordId) throws Exception;
    public int getCountOfBirthRegistration(String pointOfUpdate) throws Exception;
    public BirthRegistration getBirthRegistrationByIdAndPointOfUpdate(String clientId,String pointOfUpdate) throws Exception;
    public BirthRegistration getBirthRegistrationByIdDateAndPointOfService(String clientId,String dateOfRegistration,String pointOfUpdate) throws Exception;
    public List getDistinctClientIdFromBirthRegistration(String communityCode) throws Exception;
    public void setActiveBirthRegistration(String clientId) throws Exception;
    public List getBirthRegistrationRecordsByCliedId(String clientId) throws Exception;
    public BirthRegistration getActiveBirthRegistration(String clientId) throws Exception;
    public int getNumberOfOvcWithBirthRegistration(String additionalQuery) throws Exception;
    public int getNumberOfOvcWithoutBirthRegistration(String additionalQuery) throws Exception;
    public int getNumberOfOvcWithBirthRegistrationAtBaseline(String additionalQuery,boolean currentlyEnrolled) throws Exception;
    public List getListOfOvcIdsWithoutBirthRegistration(String additionalQuery) throws Exception;
    public List getListOfOvcWithoutBirthRegistration(String additionalQuery) throws Exception;
    public List getListOfOvcWithBirthRegistration(String additionalQuery) throws Exception;
    public List getListOfOvcWithBirthRegistrationAtBaseline(String additionalQuery,boolean currentlyEnrolled) throws Exception;
    public List getNumberOfOvcCurrentlyEnrolledWithBirthRegistration(String indicatorName,String stateCode) throws Exception;
    public int getNumberOfOvcWithBirthRegistrationByPointOfUpdate(String additionalQuery,String pointOfUpdate,boolean currentlyEnrolled) throws Exception;
    public List getListOfOvcWithBirthRegistrationByPointOfUpdate(String additionalQuery,String pointOfUpdate,boolean currentlyEnrolled) throws Exception;
}
