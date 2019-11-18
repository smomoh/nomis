/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.SchoolAttendance;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface SchoolAttendanceDao 
{
    public void saveSchoolAttendance(SchoolAttendance sa) throws Exception;
    public void updateSchoolAttendance(SchoolAttendance sa) throws Exception;
    public void markedForDelete(SchoolAttendance sa) throws Exception;
    public void deleteSchoolAttendance(SchoolAttendance sa) throws Exception;
    public List getAllSchoolAttendanceRecords() throws Exception;
    public SchoolAttendance getSchoolAttendance(int recordId) throws Exception;
    public SchoolAttendance getSchoolAttendance(String ovcId,String dateOfAssessment) throws Exception;
    public List getSchoolAttendanceRecordsPerCaregiver(String caregiverId) throws Exception;
    public List getSchoolAttendanceRecordsPerOvc(String ovcId) throws Exception;
    public List getSchoolAttendanceRecords(String additionalQuery,String startDate,String endDate) throws Exception;
    public int getNumberOfOvcRegularlyAttendingSchool(String additionalQuery,String startDate,String endDate,boolean currentlyEnrolled) throws Exception;
    public List getListOfOvcRegularlyAttendingSchool(String additionalQuery,String startDate,String endDate,boolean currentlyEnrolled) throws Exception;
}
