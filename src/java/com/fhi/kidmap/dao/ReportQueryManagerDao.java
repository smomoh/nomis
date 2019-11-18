/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.nomis.business.ReportQueryManager;
import java.util.List;

/**
 *
 * @author Siaka
 */
public interface ReportQueryManagerDao 
{
    public void saveReportQueryManager(ReportQueryManager rqm) throws Exception;
    public void updateReportQueryManager(ReportQueryManager rqm) throws Exception;
    public void deleteReportQueryManager(ReportQueryManager rqm) throws Exception;
    public List getAllReportQueryManagers() throws Exception;
    public ReportQueryManager getReportQueryManager(String uniqueId) throws Exception;
    public List getReportQueryManagers(String type) throws Exception;
}
