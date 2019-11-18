/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.OvcSchool;
import java.util.List;

/**
 *
 * @author HP USER
 */
public interface OvcSchoolDao {
    public List getSchoolAttendanceList(String stateName,String schoolName,String[] param) throws Exception;
    public List getSchoolListFromEnrollment(String stateName) throws Exception;
    public List getSchoolListPerState(String state_id) throws Exception;
    public OvcSchool getSchoolDetails(int school_id) throws Exception;
    public List getSchoolList(String state_id) throws Exception;
    public List getOvcSchools() throws Exception;
    public List getOvcCountPerSchool(String[] params) throws Exception;
    public OvcSchool getOvcSchool(String state,String lga,String schoolName) throws Exception;
    public void cleanSchoolNamesInSchoolRecords() throws Exception;
    public void cleanSchoolNamesInOvcRecords() throws Exception;
    public List getSchoolListForExport(String state_id,String lga_id) throws Exception;
    public OvcSchool getSchool(String schoolName) throws Exception;
    public void saveOvcSchool(OvcSchool ovcSchool) throws Exception;
    public void updateOvcSchool(OvcSchool ovcSchool) throws Exception;
    public void markedForDelete(OvcSchool ovcSchool) throws Exception;
    public void deleteOvcSchool(OvcSchool ovcSchool) throws Exception;


}
