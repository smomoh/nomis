/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report.controller;

import com.fhi.kidmap.business.User;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.report.CustomReports;
import com.fhi.kidmap.report.DatimPDFForm;
import com.fhi.kidmap.report.DatimReportTemplate;
import com.fhi.kidmap.report.ReportUtility;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.nomis.nomisutils.ExcelWriter;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jxl.write.WritableWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author smomoh
 */
public class DatimGeneratedDataDownloadAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
        DatimGeneratedDataDownloadForm reportForm=(DatimGeneratedDataDownloadForm)form;
        ReportUtility rutil=new ReportUtility();
        CustomReports customReport=new CustomReports();
        HttpSession session=request.getSession();
        AppUtility appUtil=new AppUtility();
        
        User user=appUtil.getUser(session);
        DaoUtil util=new DaoUtil();
        
        //List cboList=new ArrayList();
        //List paramList=new ArrayList();
        String requiredAction;
        String stateName=reportForm.getState();
        String lgaName=reportForm.getLgaCode();
        String cboCode=reportForm.getCbo();
        String period=reportForm.getPeriod();
        String userName=appUtil.getCurrentUser(session);
        
        String reportType=reportForm.getReportType();
        requiredAction=reportForm.getActionName();
        System.err.println("requiredAction is "+requiredAction);
        
        List datimStateList=util.getDatimReportDaoInstance().getDistinctStates();
        session.setAttribute("datimReportStates", datimStateList);
        getLgaList(session,stateName);
        List periodList=util.getDatimReportDaoInstance().getDistinctPeriods();
        if(periodList==null)
        periodList=new ArrayList();
        session.setAttribute("datgenperiodList", periodList);
        request.setAttribute("datimReportPeriod", period);
        if(requiredAction==null)
        {
            reportForm.reset(mapping, request);
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equals("report"))
        {
            //CustomReports customReport=new CustomReports();
            ExcelWriter ew=new ExcelWriter();
            String target=SUCCESS;
                        
            System.err.println("reportType is "+reportType);
            if(reportType.equalsIgnoreCase("datim2017"))
            {
                
                DatimReportTemplate dform=util.getDatimReportDaoInstance().getDatimReportTemplate(stateName, lgaName, period);
                session.setAttribute("datim2017Form",dform);
                target="datim2017";
                reportForm.reset(mapping, request);
                return mapping.findForward(target);
            }
            else if(reportType.equalsIgnoreCase("writedatim2017ToPDF"))
            {
                DatimPDFForm dpdff=new DatimPDFForm();
        
                if(stateName==null || stateName.equalsIgnoreCase("All"))
                {
                    //List stateList=util.getHheStateList();
                    if(datimStateList !=null)
                    {
                        for(int i=0; i<datimStateList.size(); i++)
                        {
                            stateName=(String)datimStateList.get(i);
                            List datimFormList=customReport.getDatimFormListFromGeneratedData(stateName,period);
                            dpdff.writeDatim2017FormToPDF(datimFormList); 
                            //util.getDatimReportDaoInstance().getAllDatimReportTemplateByLga(stateName, lgaName);
                              
                        }
                    }
                }
                else
                {
                    List datimFormList=customReport.getDatimFormListFromGeneratedData(stateName,period);
                    dpdff.writeDatim2017FormToPDF(datimFormList); 
                }
                request.setAttribute("datimpdfmsg","PDF files written to "+appUtil.getDbRootDirectory()+"\\Reports\\Datim2017");
                target="datimReportView";
                reportForm.reset(mapping, request);
                return mapping.findForward(target);
            }
            else if(reportType.equalsIgnoreCase("datim2017ExcelDownload"))
            {
                String fileName="Datim_2017_report";
                List datimFormList=new ArrayList();
                List datimExcelList=new ArrayList();
                
                if(stateName==null || stateName.equalsIgnoreCase("All"))
                {
                    //get all the states and loop to get their datim forms
                   
                    if(datimStateList !=null)
                    {
                        for(int i=0; i<datimStateList.size(); i++)
                        {
                            stateName=(String)datimStateList.get(i);
                            datimFormList.addAll(util.getDatimReportDaoInstance().getAllDatimReportTemplateByState(stateName));
                            
                        }
                    }
                }
                else
                {
                    datimFormList.addAll(util.getDatimReportDaoInstance().getAllDatimReportTemplateByState(stateName));
                    
                }
                datimExcelList=customReport.getDatim2017ForExcel(datimFormList);
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment; filename="+fileName+".xls");
                OutputStream os=response.getOutputStream();
                WritableWorkbook wworkbook=ew.writeDatim2017ReportToExcel(os, datimExcelList);
                                
                if(wworkbook !=null)
                {
                    wworkbook.write();
                    wworkbook.close();
                }
                os.close();
            }
            reportForm.reset(mapping, request);          
            return mapping.findForward(target); 
        }
        
        return mapping.findForward(SUCCESS);
    }
    private List getLgaList(HttpSession session,String stateName)
    {
        List lgaList=new ArrayList();
        try
        {
            DaoUtil util=new DaoUtil();
            lgaList=util.getDatimReportDaoInstance().getDistinctLgas(stateName);
            if(lgaList==null)
            lgaList=new ArrayList();
            session.setAttribute("datimReportLgaList", lgaList);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return lgaList;
    }
}
