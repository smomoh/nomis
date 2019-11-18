/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.nomis.business.ReportTemplate;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface CustomIndicatorsReportDao 
{
    public void saveReportTemplate(ReportTemplate rt) throws Exception;
    public void saveOrUpdateReportTemplate(ReportTemplate rt) throws Exception;
    public void updateReportTemplate(ReportTemplate rt) throws Exception;
    public void deleteReportTemplate(ReportTemplate rt) throws Exception;
    public List getAllReportTemplates() throws Exception;
    public List getAllReportTemplateByState(String state) throws Exception;
    public List getAllReportTemplateByLga(String state,String lga) throws Exception;
    public List getAllReportTemplateByCbo(String state,String lga,String cbo) throws Exception;
    public ReportTemplate getReportTemplate(ReportTemplate rt) throws Exception;
    public ReportTemplate getReportTemplate(String state,String lga,String cbo,String indicatorName,String otherDisaggregation,String period) throws Exception;
    public List getDistinctPeriods() throws Exception;
    public List getDistinctStates() throws Exception;
    public List getDistinctStatesByPeriod(String period) throws Exception;
    public ReportTemplate getReportTemplate(String lga,String cbo,String partnerCode,String indicatorName, String merCode,String otherDisaggregation,String period) throws Exception;
    public int deleteAllReportTemplates(String state) throws Exception;
    public List getDistinctStatesByPartner(String partnerCode) throws Exception;
    public List getDistinctStatesByPeriodAndPartner(String partnerCode,String period) throws Exception;
    public List getReportTemplatesByStatePartnerAndPeriod(String state,String partnerCode,String period) throws Exception;
}
