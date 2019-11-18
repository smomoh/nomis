/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.GenderNormCohortAttendance;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface GenderNormCohortAttendanceDao 
{
    public void saveGenderNormCohortAttendance(GenderNormCohortAttendance gnca) throws Exception;
    public void updateGenderNormCohortAttendance(GenderNormCohortAttendance gnca) throws Exception;
    public void markedForDelete(GenderNormCohortAttendance gnca) throws Exception;
    public void deleteGenderNormCohortAttendance(GenderNormCohortAttendance gnca) throws Exception;
    public GenderNormCohortAttendance getGenderNormCohortAttendance(int recordId) throws Exception;
    public GenderNormCohortAttendance getGenderNormCohortAttendance(String clientId,String dateOfAttendance) throws Exception;
    public List getListOfGenderNormCohortAttendanceByClientId(String clientId,String reportOrder) throws Exception;
    public List getAllGenderNormCohortAttendance() throws Exception;
    public List getListOfGenderNormCohortAttendance(String additionalQuery,String startDate,String endDate,String reportOrder) throws Exception;
    public List getListOfCaregiverGenderNormCohortAttendance(String additionalQuery,String startDate,String endDate,String reportOrder) throws Exception;
    public List getListOfOvcGenderNormCohortAttendance(String additionalQuery,String startDate,String endDate,String reportOrder) throws Exception;
}
