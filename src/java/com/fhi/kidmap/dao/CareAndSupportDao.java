/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.CareAndSupportChecklist;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface CareAndSupportDao 
{
    public void saveCareAndSupportCheclist(CareAndSupportChecklist csc) throws Exception;
    public void updateCareAndSupportCheclist(CareAndSupportChecklist csc) throws Exception;
    public void deleteCareAndSupportCheclist(CareAndSupportChecklist csc) throws Exception;
    public CareAndSupportChecklist getCareAndSupportCheclist(int recordId) throws Exception;
    public List getCareAndSupportCheclistByUniqueId(String uniqueId) throws Exception;
    public List getAllCareAndSupportCheclist() throws Exception;
    public CareAndSupportChecklist getCareAndSupportCheclist(String uniqueId, String dateOfAssessment) throws Exception;
    public List getCareAndSupportChecklistByPeriod(String additionalQuery,String startDate,String endDate,String reportOrder) throws Exception;
    public List getCareAndSupportForExport(String additionalQuery,String startDate,String endDate,String reportOrder) throws Exception;
    public int getNumberOfOvcTestedAndReceivedResult(String additionalQuery,String startDate,String endDate,boolean currentlyEnrolled) throws Exception;
    public List getListOfOvcTestedAndReceivedResult(String additionalQuery,String startDate,String endDate,boolean currentlyEnrolled) throws Exception;
    public int getNumberOfNewlyTestedHIVPositiveOvcLinkedToTrestmentWithinTheReportPeriod(String additionalQuery,String startDate,String endDate,boolean currentlyEnrolled) throws Exception;
    public List getListOfNewlyTestedHIVPositiveOvcLinkedToTrestmentWithinTheReportPeriod(String additionalQuery,String startDate,String endDate,boolean currentlyEnrolled) throws Exception;
    public int getNumberOfHIVPositiveOvcWhoReportedAdherenceToTreatmentWithinTheReportPeriod(String additionalQuery,String startDate,String endDate,boolean skippedARV,boolean currentlyEnrolled) throws Exception;
    public List getListOfHIVPositiveOvcWhoReportedAdherenceToTreatmentWithinTheReportPeriod(String additionalQuery,String startDate,String endDate,boolean skippedARV,boolean currentlyEnrolled) throws Exception;
    public void markedForDelete(CareAndSupportChecklist csc) throws Exception;
}
