/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.report.HouseholdUniqueServiceReport;

/**
 *
 * @author smomoh
 */
public interface HouseholdUniqueServiceReportDao 
{
    public void saveServiceReport(HouseholdUniqueServiceReport hhs) throws Exception;
    public HouseholdUniqueServiceReport getServiceReportByIdAndPeriod(String ovc_Id,String startDate,String endDate) throws Exception;
}
