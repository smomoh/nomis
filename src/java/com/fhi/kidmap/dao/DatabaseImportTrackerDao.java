/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.nomis.nomisutils.DatabaseImportTracker;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface DatabaseImportTrackerDao
{
    public void saveDatabaseImportTracker(DatabaseImportTracker dit) throws Exception;
    public void updateDatabaseImportTracker(DatabaseImportTracker dit) throws Exception;
    public void deleteDatabaseImportTracker(DatabaseImportTracker dit) throws Exception;
    public DatabaseImportTracker getDatabaseImportTracker(int recordId) throws Exception;
    public List getAllDatabaseImportTracker() throws Exception;
    public List getDistinctUserNamesFromDatabaseImportTracker() throws Exception;
    public List getDatabaseImportTracker(String userName) throws Exception;
    public List getDatabaseImportTrackersByPeriod(String startDate,String endDate) throws Exception;
    public List getDistinctImportDateFromDatabaseImportTracker() throws Exception;
    public List getDatabaseImportTrackersByUserNameAndPeriod(String userName,String startDate,String endDate) throws Exception;
    public List getDatabaseImportTracker(String userName,boolean responseSent) throws Exception;
}
