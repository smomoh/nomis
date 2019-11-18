/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.ReportQuery;
import java.util.List;

/**
 *
 * @author Siaka
 */
public interface ReportQueryDao 
{
    public void saveReportQuery(ReportQuery rq) throws Exception;
    public void updateReportQuery(ReportQuery rq) throws Exception;
    public void deleteReportQuery(ReportQuery rq) throws Exception;
    public ReportQuery getReportQuery(String queryId) throws Exception;
    public List getReportQueries() throws Exception;
    public ReportQuery getReportQueriesByQueryName(String queryName) throws Exception;
}
