/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report.controller;

import com.fhi.kidmap.business.Lgas;
import com.fhi.kidmap.business.User;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.OrganizationUnitGroupAssignmentDao;
import com.fhi.kidmap.dao.OrganizationUnitGroupAssignmentDaoImpl;
import com.fhi.kidmap.dao.PartnersDao;
import com.fhi.kidmap.dao.PartnersDaoImpl;
import com.fhi.kidmap.report.CustomReports;
import com.fhi.kidmap.report.DatimPDFForm;
import com.fhi.kidmap.report.DatimReport;
import com.fhi.kidmap.report.DatimReportTemplate;
import com.fhi.kidmap.report.FY18IndicatorReportGenerator;
import com.fhi.kidmap.report.MERPdfReport;
import com.fhi.kidmap.report.ReportUtility;
import com.fhi.nomis.nomisutils.AccessManager;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.nomis.nomisutils.DateManager;
import com.fhi.nomis.nomisutils.ExcelWriter;

import com.nomis.business.ReportHeaderTemplate;
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
public class DatimReportAction extends org.apache.struts.action.Action {

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
        DatimReportForm reportForm=(DatimReportForm)form;
        ReportUtility rutil=new ReportUtility();
        CustomReports customReport=new CustomReports();
        HttpSession session=request.getSession();
        AppUtility appUtil=new AppUtility();
        User user=appUtil.getUser(session);
        AccessManager acm=new AccessManager();
        
        PartnersDao pdao=new PartnersDaoImpl();
        OrganizationUnitGroupAssignmentDao ougadao=new OrganizationUnitGroupAssignmentDaoImpl();
        DaoUtil util=new DaoUtil();
        
        List cboList=new ArrayList();
        List paramList=new ArrayList();
        String requiredAction;
        String formStateCode=reportForm.getState();
        String lgaCode=reportForm.getLgaCode();
        String cboCode=reportForm.getCbo();
        String wardCode="All";
        String userName=appUtil.getCurrentUser(session);
        String orgUnitGroup=reportForm.getOrgUnitGroup();
        DateManager dm=new DateManager();
        int startMonth=reportForm.getActiveMonth1();//.getEnroll_month();
        int startYear=reportForm.getActiveYear1();//.getEnroll_year();
        int endMonth=reportForm.getActiveMonth2();//.getEnroll_month2();
        int endYear=reportForm.getActiveYear2();//.getEnroll_year2();
        String partnerCode=reportForm.getPartnerCode();
        String reportType=reportForm.getReportType();
        String period="01 "+dm.getMonthAsString(startMonth)+" "+startYear+" to "+dm.getMonthAsString(endMonth)+" "+endYear;
        request.setAttribute("datimReportPeriod", period);
        //String preparedDataSourceChkBox=reportForm.getPreparedDataSourceChkBox();
        //String ageSegType=reportForm.getReportType();
        //String periodSelected=reportForm.getPeriodChkBox();
        List orgUnitGroupAssignmentParentList=new ArrayList();
        if(formStateCode !=null)
        {
            if(formStateCode.equalsIgnoreCase("All"))
            orgUnitGroupAssignmentParentList=ougadao.getDistinctParentIdFromOrganizationUnitGroupAssignments();
            else
            orgUnitGroupAssignmentParentList.add(formStateCode);
        }
        /*if(periodSelected==null)
        {
            endMonth=startMonth;
            endYear=startYear;
        }*/

        requiredAction=reportForm.getActionName();
        System.err.println("requiredAction is "+requiredAction);
        
        List partnerList=pdao.getAllPartners();
        reportForm.setPartnerList(partnerList);
        reportForm.setStateList(acm.getStateListForReports(session));
        //reportForm.setStateList(util.getUserAssignedStates(session));
        session.setAttribute("partnerList", partnerList);
        if(requiredAction==null)
        {
            reportForm.setOrganizationList(new ArrayList());
            reportForm.setOrganizationUnitGroupList(new ArrayList());
            reportForm.reset(mapping, request);
            return mapping.findForward("paramPage");
        }
        else if(requiredAction.equals("lga"))
        {
            List orgUnitGroupList=new ArrayList();
            List lgaList=new ArrayList();
            
            orgUnitGroupList=ReportUtility.getOrgUnitGroup(formStateCode);
            reportForm.setOrganizationUnitGroupList(orgUnitGroupList);
            if(user !=null)
            lgaList =acm.getListOfLgasAssignedToUser(user,formStateCode);
            reportForm.setLgaList(lgaList);
            reportForm.setOrganizationList(new ArrayList());
            return mapping.findForward("paramPage");

        }
        else if(requiredAction.equals("cbo"))
        {
            List orgUnitGroupList=new ArrayList();
            //List lgaList=new ArrayList();
            List orgList=new ArrayList();
            if(lgaCode==null || lgaCode.equalsIgnoreCase("All"))
            {
                orgUnitGroupList=ReportUtility.getOrgUnitGroup(formStateCode);
                reportForm.setOrganizationUnitGroupList(orgUnitGroupList);
            }
            else
            reportForm.setOrganizationUnitGroupList(new ArrayList());
            
            if(user !=null)
            orgList =acm.getListOfCBOsAssignedToUser(user,formStateCode,lgaCode);
            reportForm.setOrganizationList(orgList);
            return mapping.findForward("paramPage"); 
        }
        else if(requiredAction.equals("report"))
        {
            if(orgUnitGroup !=null && !orgUnitGroup.equalsIgnoreCase("All"))
            lgaCode="orgUnitGroup";
            ExcelWriter ew=new ExcelWriter();
            String target="merReport";
            List mainList=new ArrayList();
            
            System.err.println("requiredAction is "+requiredAction+"");
            //List datim2017HivList=new ArrayList();
            if(cboCode==null || cboCode.equalsIgnoreCase("All"))
            cboCode=orgUnitGroup;
            paramList.add(formStateCode);
            paramList.add(lgaCode);
            paramList.add(cboCode);
            paramList.add(wardCode);
            paramList.add(startMonth);
            paramList.add(startYear);
            paramList.add(endMonth);
            paramList.add(endYear);
            paramList.add(partnerCode);
            String[] param=rutil.getLabelParam(paramList);
            System.err.println("reportType is "+reportType);
            if(reportType.equalsIgnoreCase("datim2017"))
            {
                DatimReport datimReport=new DatimReport();
                DatimReportTemplate dform=datimReport.getDatim2018Form(paramList);
                session.setAttribute("datim2017Form",dform);
                target="datim2017";
                return mapping.findForward(target);
            }
            else if(reportType.equalsIgnoreCase("mer2018"))
            {
                FY18IndicatorReportGenerator fy18report=new FY18IndicatorReportGenerator();
                List reportList=fy18report.getIndicatorValues(paramList);
                ReportHeaderTemplate rht=customReport.getReportHeaderTemplate(paramList);
                request.setAttribute("mer2018rht", rht);
                session.setAttribute("mer2018ReportList",reportList);
                target="mer2018";
                return mapping.findForward(target);
            }
            else if(reportType.equalsIgnoreCase("writedatim2017ToPDF"))
            {
                DatimReport datimReport=new DatimReport();
                if(formStateCode==null || formStateCode.equalsIgnoreCase("All"))
                {
                    List stateList=util.getHheStateList();
                    if(stateList !=null)
                    {
                        for(int i=0; i<stateList.size(); i++)
                        {
                            String stateCode=(String)stateList.get(i);
                            paramList.set(0, stateCode);
                            datimReport.writeDatim2017ToPDF(paramList);    
                        }
                    }
                }
                else
                {
                    datimReport.writeDatim2017ToPDF(paramList);
                }
                request.setAttribute("datimpdfmsg","PDF files written to "+appUtil.getDbRootDirectory()+"\\Reports\\Datim2017");
                target="datim2017";
                return mapping.findForward(target);
            }
            else if(reportType.equalsIgnoreCase("datim2017ExcelDownload"))
            {
                String fileName="Datim_2017_report";
                List datimFormList=new ArrayList();
                List datimExcelList=new ArrayList();
                String stateName=null;
                if(formStateCode==null || formStateCode.equalsIgnoreCase("All"))
                {
                    //get all the states and loop to get their datim forms
                    List stateList=util.getHheStateList();
                    
                    if(stateList !=null)
                    {
                        for(int i=0; i<stateList.size(); i++)
                        {
                            String stateCode=(String)stateList.get(i);
                            //set the individual state code and get the datim forms
                            paramList.set(0, stateCode);
                            datimFormList.addAll(customReport.getDatimFormList(paramList)); 
                        }
                    }
                }
                else
                {
                    //String stateCode=(String)paramList.get(0);
                    datimFormList.addAll(customReport.getDatimFormList(paramList));
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
            else if(reportType.equalsIgnoreCase("newUSAIDReport"))
            {
                /*DatimReportTemplate_Mer1718 dform=customReport.getDatim2017Form(paramList);
                session.setAttribute("datim2017Form",dform);
                target="datim2017";*/
                return mapping.findForward(target);
            }
            else
            {
                mainList=customReport.getMERReport(paramList);
                session.setAttribute("merReportList", mainList);
                session.setAttribute("merReportParam", param);
                if(reportType.equalsIgnoreCase("mer"))
                return mapping.findForward("merReport");
                target="merReport";
            }
            
            session.setAttribute("merReportParam", param);
            
            return mapping.findForward(target); 
        }
        
        else if(requiredAction.equals("reportToFile"))
        {
            OutputStream os=response.getOutputStream();
            
            appUtil.createReportDirectory();
            String stateCode=formStateCode;
            List lgaList=util.getLgaListFromHhe(stateCode);
            List orgUnitGroupList=new ArrayList();
            List excelList=new ArrayList();
            String stateName=null;
            Lgas lga=null;
            paramList.add(stateCode);
            paramList.add(lgaCode);
            paramList.add(cboCode);
            paramList.add(wardCode);
            paramList.add(startMonth);
            paramList.add(startYear);
            paramList.add(endMonth);
            paramList.add(endYear);
            paramList.add(partnerCode);
            ExcelWriter ew=new ExcelWriter();
            if(reportType.equalsIgnoreCase("datim2017"))
            {
                customReport.writeDatim2017ToPDF(paramList);
            }
            else if(reportType.equals("datim2017ExcelDownload"))
            {
                String fileName="Datim 2017 report";
                List datimFormList=customReport.getDatimFormList(paramList);
                List datimExcelList=customReport.getDatim2017ForExcel(datimFormList);
                response.setContentType("application/vnd.ms-excel");
                //response.setHeader("Content-Disposition", "attachment; filename=Indicator_values.xls");
                response.setHeader("Content-Disposition", "attachment; filename="+fileName+".xls");
                WritableWorkbook wworkbook=ew.writeDatim2017ReportToExcel(os, datimExcelList);
                                
                if(wworkbook !=null)
                {
                    wworkbook.write();
                    wworkbook.close();
                }
                os.close();
            }
            else
            {
                for(int j=0; j<lgaList.size(); j++)
                {
                    lga=(Lgas)lgaList.get(j);
                    if(lga==null)
                    continue;
                    if(formStateCode.equalsIgnoreCase("All"))
                    stateCode=lga.getState_code();
                    if(stateCode==null)
                    continue;
                    stateName=util.getStateName(stateCode);
                    lgaCode=lga.getLga_code();

                        String fileName=null;
                        MERPdfReport mpr=new MERPdfReport();
                        DatimPDFForm datimForm=new DatimPDFForm();

                            cboCode="All";
                            paramList.add(stateCode);
                            paramList.add(lgaCode);
                            paramList.add(cboCode);
                            paramList.add(wardCode);
                            paramList.add(startMonth);
                            paramList.add(startYear);
                            paramList.add(endMonth);
                            paramList.add(endYear);
                            paramList.add(partnerCode);
                            System.err.println("State "+stateCode+" cbo "+lgaCode);
                            String[] param=rutil.getLabelParam(paramList);
                            List mainList=new ArrayList();
                            fileName=util.getLgaName(lgaCode);//.getOrganizationName(cboCode);
                            if(fileName==null)
                            fileName=stateCode+"_"+lgaCode;
                            if(reportType.equalsIgnoreCase("mer"))
                            {
                                mainList=customReport.getMERReport(paramList);
                                mpr.writePDFReportToFile( mainList, param, fileName);
                            }
                            /*else if(reportType.equalsIgnoreCase("datim"))
                            {
                                mainList=customReport.getDatimReport(paramList);
                                datimForm.writePDFFormToFile(mainList, param, fileName);
                                //excelList.addAll(datimForm.writeToExcel(mainList,stateName,fileName));
                            }*/

                            paramList.clear();
                            session.setAttribute("merReportList", mainList);
                            session.setAttribute("merReportParam", param);
                }
            }
            
            ew.writeDatimReportToExcel(excelList);
            if(reportType.equalsIgnoreCase("mer"))
            return mapping.findForward("merReport");
            else if(reportType.equalsIgnoreCase("datim"))
            {
                request.setAttribute("msg", "Report written to "+appUtil.getReportDirectory());
                return mapping.findForward("datimReportView");
            }
        }
        
        reportForm.reset(mapping, request);
        return mapping.findForward(SUCCESS);
    }
}
