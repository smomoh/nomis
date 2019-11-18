/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.DeletedRecord;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface DeletedRecordDao 
{
    public void saveDeletedRecord(DeletedRecord dr) throws Exception;
    public void removeDeletedRecord(DeletedRecord dr) throws Exception;
    public void createDeletedRecord(String recordId,String newId,String typeOfRecord,String dateRecordCreated) throws Exception;
    public DeletedRecord getDeletedRecord(String recordId,String typeOfRecord,String dateRecordCreated) throws Exception;
    public List getDeletedRecordByDateDeleted(String startDate,String endDate) throws Exception;
    public void removeDeletedRecord(String recordId, String dateRecordCreated,String recordType,boolean update) throws Exception;
    public List getDistinctMthAndYrFromDateCreated(String startDate,String endDate) throws Exception;
    public List getDistinctDateCreated(String startDate,String endDate) throws Exception;
    public List getDistinctPeriodsFromDateCreated(String startDateDeleted,String endDateDeleted) throws Exception;
    public List getAllDeletedRecord() throws Exception;
    public List getRecordsByDateCreated(String startDate,String endDate) throws Exception;
    public int getNumberOfRecordsByDateCreated(String startDate,String endDate) throws Exception;
}
