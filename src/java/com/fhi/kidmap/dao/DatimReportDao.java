/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.report.DatimReportTemplate;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface DatimReportDao 
{
    public void saveDatimReportTemplate(DatimReportTemplate drt) throws Exception;
    public void saveOrUpdateDatimReportTemplate(DatimReportTemplate drt) throws Exception;
    public void updateDatimReportTemplate(DatimReportTemplate drt) throws Exception;
    public void deleteDatimReportTemplate(DatimReportTemplate drt) throws Exception;
    public List getAllDatimReportTemplates() throws Exception;
    public List getAllDatimReportTemplateByState(String state) throws Exception;
    public List getAllDatimReportTemplateByLga(String state,String lga) throws Exception;
    public List getAllDatimReportTemplateByCbo(String state,String lga,String cbo) throws Exception;
    public DatimReportTemplate getDatimReportTemplate(DatimReportTemplate drt) throws Exception;
    public DatimReportTemplate getDatimReportTemplate(String state,String lga,String cbo,String partnerCode,String period) throws Exception;
    public List getDistinctPeriods() throws Exception;
    public List getDistinctStates() throws Exception;
    public List getDistinctStatesByPeriod(String period) throws Exception;
    public DatimReportTemplate getDatimReportTemplate(String lga,String cbo,String partnerCode, String period) throws Exception;
    public int deleteAllDatimReportTemplates(String state) throws Exception;
    public List getDistinctStatesByPartner(String partnerCode) throws Exception;
    public List getDistinctStatesByPeriodAndPartner(String partnerCode,String period) throws Exception;
    public DatimReportTemplate getDatimReportTemplate(String state,String lga,String period) throws Exception;
    public List getDistinctLgas(String stateName) throws Exception;
}
