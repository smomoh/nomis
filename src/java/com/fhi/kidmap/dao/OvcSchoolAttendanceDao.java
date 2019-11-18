/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.OvcSchoolAttendance;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface OvcSchoolAttendanceDao 
{
    public void saveSchoolAttendance(OvcSchoolAttendance sat) throws Exception;
    public void updateSchoolAttendance(OvcSchoolAttendance sat) throws Exception;
    public void markedForDelete(OvcSchoolAttendance sat) throws Exception;
    public void deleteSchoolAttendance(OvcSchoolAttendance sat) throws Exception;
    public List getAllSchoolAttendanceRecords() throws Exception;
    public OvcSchoolAttendance getOvcSchoolAttendance(String ovcId) throws Exception;
    public OvcSchoolAttendance getOvcSchoolAttendance(String ovcId,String dateOfAssessment) throws Exception;
    public List getOvcSchoolAttendanceRecordsPerCaregiver(String caregiverId) throws Exception;
    public List getOvcSchoolAttendanceRecordsPerOvc(String ovcId) throws Exception;
    //public List getOvcSchoolAttendanceRecords(String additionalQuery,String startDate,String endDate) throws Exception;
    public int getNumberOfOvcRegularlyAttendingSchool(String additionalQuery,String startDate,String endDate,boolean currentlyEnrolled) throws Exception;
    public List getListOfOvcRegularlyAttendingSchool(String additionalQuery,String startDate,String endDate,boolean currentlyEnrolled) throws Exception;
    public int getNumberOfOvcCurrentlyInSchool(String additionalQuery,boolean currentlyEnrolled) throws Exception;
    public List getListOfOvcCurrentlyInSchool(String additionalQuery,boolean currentlyEnrolled) throws Exception;
    public int getNumberOfOvcCurrentlyOutOfSchool(String additionalQuery,boolean currentlyEnrolled) throws Exception;
    public List getListOfOvcCurrentlyOutOfSchool(String additionalQuery,boolean currentlyEnrolled) throws Exception;
    public void changeOvcId(String oldId,String newId) throws Exception;
}
