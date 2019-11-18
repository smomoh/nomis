/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.HivRiskAssessmentChecklist;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface HivRiskAssessmentChecklistDao 
{
    public void saveHivRiskAssessmentChecklist(HivRiskAssessmentChecklist hrac) throws Exception;
    public void updateHivRiskAssessmentChecklist(HivRiskAssessmentChecklist hrac) throws Exception;
    public void markedForDelete(HivRiskAssessmentChecklist hrac) throws Exception;
    public void deleteHivRiskAssessmentChecklist(HivRiskAssessmentChecklist hrac) throws Exception;
    public HivRiskAssessmentChecklist getHivRiskAssessmentChecklist(int recordId) throws Exception;
    public HivRiskAssessmentChecklist getHivRiskAssessmentChecklist(String ovcId,String dateOfAssessment) throws Exception;
    public List getHivRiskAssessmentChecklist(String additionalQuery) throws Exception;
    public int getNumberOfOvcThatHasBeenAssessedOnHivRiskAssessment(String additionalQuery,String startDate,String endDate,boolean atRisk,boolean currentlyEnrolled) throws Exception;
    public List getHivRiskAssessmentList(String additionalQuery,String startDate,String endDate,boolean atRisk,boolean currentlyEnrolled) throws Exception;
    public List getListOfOvcThatHasBeenAssessedOnHivRiskAssessment(String additionalQuery,String startDate,String endDate,boolean atRisk,boolean currentlyEnrolled) throws Exception;
    public List getHivRiskAssessmentChecklistByOvcId(String ovcId) throws Exception;
    public void changeOvcId(String oldId,String newId) throws Exception;
    public HivRiskAssessmentChecklist getLastHivRiskAssessmentForOvc(String ovcId) throws Exception;
    public int getNumberOfOvcNeverAssessedForHivRiskAssessment(String additionalQuery,String startDate,String endDate,String currentlyEnrolledQuery) throws Exception;
    public List getListOfOvcNeverAssessedForHivRiskAssessment(String additionalQuery,String startDate,String endDate,String currentlyEnrolledQuery) throws Exception;
    public int getNumberOfOvcByHivStatusFromHivRiskAssessment(String additionalQuery,String startDate,String endDate,String currentlyEnrolledQuery,String hivStatus) throws Exception;
    public List getListOfOvcByHivStatusFromHivRiskAssessment(String additionalQuery,String startDate,String endDate,String currentlyEnrolledQuery,String hivStatus) throws Exception;
    public int getNumberOfOvcWhoseCaregiversKnowTheirHivStatus(String additionalQuery,String startDate,String endDate,String currentlyEnrolledQuery,String hivStatusAnswer) throws Exception;
    public List getListOfOvcWhoseCaregiversKnowTheirHivStatus(String additionalQuery,String startDate,String endDate,String currentlyEnrolledQuery,String hivStatusAnswer) throws Exception;
    public List getListOfOvcNotAtRiskWithUnknownHivStatus() throws Exception;
    public int getNumberOfOvcAssessedForHivRiskAndReferredForTesting(String additionalQuery,String startDate,String endDate,boolean currentlyEnrolled) throws Exception;
    public List getListOfOvcAssessedForHivRiskAndReferredForTesting(String additionalQuery,String startDate,String endDate,boolean currentlyEnrolled) throws Exception;
    public int getNoOfOvcAssessedForHivRiskByHivStatus(String additionalQuery,String startDate,String endDate,String hivStatus,boolean currentlyEnrolled) throws Exception;
    public int getNoOfOvcAssessedForHIVRiskAndServed(String additionalQuery,String startDate,String endDate,String hivStatus,boolean atRisk,boolean currentlyEnrolled) throws Exception;
    public List getListOfOvcAssessedForHIVRiskAndServed(String additionalQuery,String startDate,String endDate,String hivStatus,boolean atRisk,boolean currentlyEnrolled) throws Exception;
    public List getListOfHivRiskAssessmentWithBlankHivStatus(String communityCode) throws Exception;
    public int getNoOfHivUnknownOvcAssessedForHIVRiskAndServed(String additionalQuery,String startDate,String endDate,boolean atRisk,boolean currentlyEnrolled) throws Exception;
    public int getNoOfHivNegativeOvcAssessedForHIVRiskAndServed(String additionalQuery,String startDate,String endDate,boolean atRisk,boolean currentlyEnrolled) throws Exception;
    public List getAllHivRiskAssessmentByCommunity(String communityCode) throws Exception;
}
