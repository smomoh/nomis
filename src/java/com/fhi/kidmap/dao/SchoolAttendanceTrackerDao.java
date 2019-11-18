/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.SchoolAttendanceTracker;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface SchoolAttendanceTrackerDao 
{
    public void saveSchoolAttendanceTracker(SchoolAttendanceTracker sat) throws Exception;
    public void updateSchoolAttendanceTracker(SchoolAttendanceTracker sat) throws Exception;
    public void markedForDelete(SchoolAttendanceTracker sat) throws Exception;
    public void deleteSchoolAttendanceTracker(SchoolAttendanceTracker sat) throws Exception;
    public List getAllSchoolAttendanceTrackerRecords() throws Exception;
    public SchoolAttendanceTracker getSchoolAttendanceTracker(int recordId) throws Exception;
    public SchoolAttendanceTracker getSchoolAttendanceTracker(String ovcId,String dateOfAssessment) throws Exception;
    public List getSchoolAttendanceTrackerRecordsPerCaregiver(String caregiverId) throws Exception;
    public List getSchoolAttendanceTrackerRecordsPerOvc(String ovcId) throws Exception;
    public List getSchoolAttendanceTrackerRecords(String additionalQuery,String startDate,String endDate) throws Exception;
    public int getNumberOfOvcRegularlyAttendingSchool(String additionalQuery,String startDate,String endDate,boolean currentlyEnrolled) throws Exception;
    public List getListOfOvcRegularlyAttendingSchool(String additionalQuery,String startDate,String endDate,boolean currentlyEnrolled) throws Exception;
}
