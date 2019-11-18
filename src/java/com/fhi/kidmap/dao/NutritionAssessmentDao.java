/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.NutritionAssessment;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface NutritionAssessmentDao
{
    public void saveNutritionAssessment(NutritionAssessment na) throws Exception;
    public void updateNutritionAssessment(NutritionAssessment na) throws Exception;
    public void markedForDelete(NutritionAssessment na) throws Exception;
    public void deleteNutritionAssessment(NutritionAssessment na) throws Exception;
    public NutritionAssessment getNutritionAssessment(int id) throws Exception;
    public NutritionAssessment getNutritionAssessment(String ovcId, String dateOfAssessment) throws Exception;
    public List getNutritionAssessmentsByOvcId(String ovcId) throws Exception;
    public List getAllNutritionAssessments() throws Exception;
    public List getNutritionAssessmentsWithOvcDetails(String additionalQueryCriteria) throws Exception;
    public void setNutritionAssessmentsWithDateOfLastWeight(NutritionAssessment na) throws Exception;
    public void assignAssessmentNumber(NutritionAssessment na) throws Exception;
    public int getAssessmentNumber(NutritionAssessment na) throws Exception;
    public List getNutritionAssessmentsForExport(String additionalQueryCriteria) throws Exception;
    public List getDistinctAssessmentNo() throws Exception;
    public List getNutritionalAssessmentByCommunityCode(String communityCode) throws Exception;
    public List getDistinctOvcIdByCommunityCode(String communityCode) throws Exception;
    public List getDistinctOvcIdAndDateOfAssessmentByCommunityCode(String communityCode) throws Exception;
    public List getNutritionAssessmentByDateOfAssessment(String ovcId, String dateOfAssessment) throws Exception;
    public int getNumberofSeverelyMalnourishedOvc(String additionalQueryCriteria) throws Exception;
    public int getNumberofModeratelyMalnourishedOvc(String additionalQueryCriteria) throws Exception;
    public int getNumberofWellNourishedOvc(String additionalQueryCriteria) throws Exception;
    public int getNumberOfOvcWhoAreOverweight(String additionalQueryCriteria) throws Exception;
    public int getNumberOfOvcThatAreObesse(String additionalQueryCriteria) throws Exception;
    public int getNumberOfOvcThatAreMorbidityObesse(String additionalQueryCriteria) throws Exception;
    public List getNutritionAssessmentsByassessmentNumber(int assessmentNumber) throws Exception;
    public int cleanupBMI() throws Exception;
    public void cleanupNutritionAssessment(List naRecordsList) throws Exception;
    public List getNutritionAssessmentsForBMIReset(int assessmentNumber) throws Exception;
    public List getDistinctOvcIdFromNutritionAssessmentRecord() throws Exception;
    //public List getNutritionAssessmentsByOvcId(String ovcId) throws Exception;
    public void setActiveAssessmentRecordPerCommunity(String communityCode) throws Exception;
    public void changeOvcId(String oldId,String newId) throws Exception;
    public List getListofSeverelyMalnourishedOvc(String additionalQueryCriteria) throws Exception;
    public List getListofModeratelyMalnourishedOvc(String additionalQueryCriteria) throws Exception;
    public List getListofWellNourishedOvc(String additionalQueryCriteria) throws Exception;
    public List getListofOvcWithNutrionalAssessmentByCohort(String additionalQueryCriteria,String startDate,String endDate) throws Exception;
    public int getNumberofSeverelyMalnourishedOvcIdentifiedAndServedWithinTheReportPeriod(String additionalQueryCriteria,String startDate,String endDate,boolean curretlyEnrolled) throws Exception;
    public List getListofSeverelyMalnourishedOvcIdentifiedAndServedWithinTheReportPeriod(String additionalQueryCriteria,String startDate,String endDate,boolean curretlyEnrolled) throws Exception;
}
