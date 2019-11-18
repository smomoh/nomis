/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.Service1;
import com.fhi.kidmap.report.CsiReport;
import com.fhi.kidmap.report.FollowupSurveyReport;
import com.fhi.kidmap.report.ReferralReport;
import com.fhi.kidmap.report.ServiceReport;
import java.util.List;

/**
 *
 * @author Siaka
 */
public interface ReportTableDao 
{
    public void saveFollowupSurveyReport(FollowupSurveyReport fusReport) throws Exception;
    public void saveCsiReport(CsiReport csiReport) throws Exception;
    public void saveReferralReport(ReferralReport referralReport) throws Exception;
    public void saveServiceReport(ServiceReport serviceReport) throws Exception;
    public void dropReportTable(String tableName) throws Exception;
    public void saveService1(Service1 service1) throws Exception;
    public List getServicesFromService1()  throws Exception;
    public void updateService1(Service1 service1) throws Exception;
    
}
