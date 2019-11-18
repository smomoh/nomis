/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import java.util.List;

/**
 *
 * @author COMPAQ USER
 */
public interface EnrollmentReportDao {
    public String[] getEnrollmentDates(String[] params);
    //public List getOvcEnrolledSummStatisticsPerMth(String sex,String[] param) throws Exception;//reqparams
    public List getOvcEnrolledSummStatistics(String sex,String[] param) throws Exception;
    public List getSummaryStatisticList(String[] params,String[] reqparams) throws Exception;
    //public List getSummaryStatisticListPerMth(String[] params,String[] reqparams) throws Exception;
    public List getOvcMthlySummaryData(String[] params) throws Exception;
    public List getOvcMthlyEconStrengtheningData(String[] params) throws Exception;
    public List getNoOfCSOReportingOVCServicesForTheMth(String[] params) throws Exception;
    public List getNoOfLGAReportingOVCServicesForTheMth(String[] params) throws Exception;
    public List getNoOfStateReportingOVCServicesForTheMth(String[] params) throws Exception;
    public String[] getDatePeriod(String[] params);
    public List getOvcCount(String month, String year) throws Exception;
    public List getLgas(String dateFrom, String dateTo) throws Exception ;
    public List fetchOvcCount(String lgaName, String dateFrom, String dateTo) throws Exception;
    public List getCsiAtEnrolment(String ovcId) throws Exception;
    //public List getListOfHousholdProvidedBasicExternalSupport(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery) throws Exception;
    public List getSummaryStaticsCountWithoutGenderDisaggregation(String[] params);
 }
