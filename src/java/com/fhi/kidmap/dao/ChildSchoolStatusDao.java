/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.ChildSchoolStatus;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface ChildSchoolStatusDao 
{
    public void saveChildSchoolStatus(ChildSchoolStatus csc) throws Exception;
    public void updateChildSchoolStatus(ChildSchoolStatus csc) throws Exception;
    public void markedForDelete(ChildSchoolStatus sat) throws Exception;
    public void deleteChildSchoolStatus(ChildSchoolStatus csc) throws Exception;
    public List getAllChildSchoolStatusRecords() throws Exception;
    public ChildSchoolStatus getChildSchoolStatus(String ovcId) throws Exception;
    public ChildSchoolStatus getChildSchoolStatus(String ovcId,String dateOfAssessment) throws Exception;
    public List getChildSchoolStatusRecordsPerCaregiver(String caregiverId) throws Exception;
    public List getChildSchoolStatusRecordsPerOvc(String ovcId) throws Exception;
    //public List getChildSchoolStatusRecords(String additionalQuery,String startDate,String endDate) throws Exception;
    public int getNumberOfOvcRegularlyAttendingSchool(String additionalQuery,String startDate,String endDate,boolean currentlyEnrolled) throws Exception;
    public List getListOfOvcRegularlyAttendingSchool(String additionalQuery,String startDate,String endDate,boolean currentlyEnrolled) throws Exception;
    public int getNumberOfOvcCurrentlyInSchool(String additionalQuery,boolean currentlyEnrolled) throws Exception;
    public List getListOfOvcCurrentlyInSchool(String additionalQuery,boolean currentlyEnrolled) throws Exception;
    public int getNumberOfOvcCurrentlyOutOfSchool(String additionalQuery,boolean currentlyEnrolled) throws Exception;
    public List getListOfOvcCurrentlyOutOfSchool(String additionalQuery,boolean currentlyEnrolled) throws Exception;
    public void changeOvcId(String oldId,String newId) throws Exception;
    //public List getListOfOvcNotCurrentlyInSchool(String additionalQuery,boolean currentlyEnrolled) throws Exception;
}
