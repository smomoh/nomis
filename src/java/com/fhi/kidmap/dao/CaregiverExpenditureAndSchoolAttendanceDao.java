/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.CaregiverExpenditureAndSchoolAttendance;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface CaregiverExpenditureAndSchoolAttendanceDao 
{
    public void saveCaregiverExpenditureAndSchoolAttendance(CaregiverExpenditureAndSchoolAttendance ceasa) throws Exception;
    public void updateCaregiverExpenditureAndSchoolAttendance(CaregiverExpenditureAndSchoolAttendance ceasa) throws Exception;
    public void deleteCaregiverExpenditureAndSchoolAttendance(CaregiverExpenditureAndSchoolAttendance ceasa) throws Exception;
    public CaregiverExpenditureAndSchoolAttendance getCaregiverExpenditureAndSchoolAttendance(String caregiverId,String dateOfAssessment) throws Exception;
    public CaregiverExpenditureAndSchoolAttendance getCaregiverExpenditureAndSchoolAttendance(int recordId) throws Exception;
    public List getCaregiverExpenditureAndSchoolAttendanceList(String additionalQuery) throws Exception;
    public int getNumberOfHouseholdsThatAreAbleToPayForUnexpectedExpenses(String additionalQuery,String startDate,String endDate,boolean currentlyEnrolled) throws Exception;
    public List getListOfHouseholdsThatAreAbleToPayForUnexpectedExpenses(String additionalQuery,String startDate,String endDate,boolean currentlyEnrolled) throws Exception;
    public List getRecordsWithOvcId(String additionalQuery) throws Exception;
    public void markedForDelete(CaregiverExpenditureAndSchoolAttendance ceasa) throws Exception;
}
