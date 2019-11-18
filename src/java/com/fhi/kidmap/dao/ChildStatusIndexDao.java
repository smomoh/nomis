/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.ChildStatusIndex;
import java.util.List;

/**
 *
 * @author HP USER
 */
public interface ChildStatusIndexDao {
    public void updateChildStatusIndex(ChildStatusIndex childStatusIndex) throws Exception;
    public void updateChildStatusIndexAndReorderAssessmentNumber(ChildStatusIndex childStatusIndex) throws Exception;
    public void markedForDelete(ChildStatusIndex childStatusIndex) throws Exception;
    public void deleteChildStatusIndex(ChildStatusIndex childStatusIndex) throws Exception;
    public void deleteChildStatusIndexWithoutReordering(ChildStatusIndex childStatusIndex) throws Exception;
    public void deleteAllCsiRecordsPerOvc(String ovcId) throws Exception;
    public void saveChildStatusIndex(ChildStatusIndex childStatusIndex) throws Exception;
    public int saveOrUpdateChildStatusIndex(ChildStatusIndex childStatusIndex) throws Exception;
    public void saveChildStatusIndexAndReorderAssessmentNumber(ChildStatusIndex childStatusIndex) throws Exception ;
    public List getCsiOvcList(String[] param,int factorIndex,int csiScore) throws Exception;
    public List getCsiIndicatorCount(int factorIndex,String[] param) throws Exception;
    public ChildStatusIndex getChildStatusIndex(String ovcId, String csiDate) throws Exception;
    public List getCsiAsList(String ovcId, String csiDate) throws Exception;
    public ChildStatusIndex[] getChildStatusIndex(String ovcId) throws Exception;
    public List getCsiForExport(String startDate,String endDate,String additionalOrgQuery) throws Exception;
    public List getCsiListOrderedByDateAsc(String ovcId) throws Exception;
    //public void reorderAssessmentNumber(List list) throws Exception;
    public List getCSIRecordsByovcIdFromCSI(String ovcId) throws Exception;
    //public void saveCSIWithReorderedAssessmentNumber(List csiList) throws Exception;
    public List getDistinctOvcIdsFromCsi() throws Exception;
    public List getDistinctOvcIdsFromCsi(String orgCode) throws Exception;
    //public int getCsiTotal(ChildStatusIndex csi) throws Exception;
    public int getNoOfCsi(String ovcId) throws Exception;
    public List getSurveyNumbers() throws Exception;
    public List getCsiWithSurveyNo(int surveyNo) throws Exception;
    public void deleteChildStatusIndex(String ovcId, String csiDate) throws Exception;
    public void changeOvcId(String oldId,String newId) throws Exception;
    public int getTotalCsiScore(ChildStatusIndex childStatusIndex) throws Exception;
    public List getDistinctOvcIdAndCsiDateAsList(String orgCode) throws Exception;
    public List getCSIByCommunityCode(String communityCode) throws Exception;
    public List getChildStatusIndexListByOvcIdAndDate(String ovcId, String csiDate) throws Exception ;
    public void updateCsiDateOfEntry() throws Exception;
    public List getChildStatusIndexRecords(String additionalQuery) throws Exception;
    public List getChildStatusIndexRecordForDownload(String stateCode,String startDate,String endDate) throws Exception;
    public List getDistinctOvcIdsFromCsiByAdditionalQuery(String additionalQuery) throws Exception;
    public List getTotalCsiScoreForAllOvc(String stateCode) throws Exception;
    public List getNumberOfOvcSickWithNoAccessToHealthCareAtBaseline(String stateCode,String startDate,String endDate) throws Exception;
    public List getNumberOfOvcSickWithLimitedAccessToHealthCareAtBaseline(String stateCode,String startDate,String endDate) throws Exception;
    public List getNumberOfOvcWhoAreMostVulnerable(String indicatorName,String stateCode,String startDate,String endDate,int surveyNumber) throws Exception;
    public List getNumberOfOvcWhoAreMoreVulnerable(String indicatorName,String stateCode,String startDate,String endDate,int surveyNumber) throws Exception;
    public List getNumberOfOvcWhoAreVulnerable(String indicatorName,String stateCode,String startDate,String endDate,int surveyNumber) throws Exception;
    public void reorderAssessmentNumber(String ovcId) throws Exception;
    public ChildStatusIndex getChildStatusIndex(int id) throws Exception;
    public List getDistinctOvcIdsFromCsiByCommunityCode(String communityCode) throws Exception;
    public ChildStatusIndex getMostRecentCsiScore(String ovcId) throws Exception ;
    public List getCsiListOrderedByDateDesc(String ovcId) throws Exception;
    public ChildStatusIndex getBaselineChildStatusIndex(String ovcId) throws Exception;
}
