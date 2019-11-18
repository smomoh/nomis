/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.report.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.nomis.nomisutils.ExcelWriter;
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.dao.ChildStatusIndexDao;
import com.fhi.kidmap.dao.ChildStatusIndexDaoImpl;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.HivRiskAssessmentChecklistDao;
import com.fhi.kidmap.dao.HivRiskAssessmentChecklistDaoImpl;
import com.fhi.kidmap.dao.HouseholdVulnerabilityAssessmentDao;
import com.fhi.kidmap.dao.HouseholdVulnerabilityAssessmentDaoImpl;
import com.fhi.kidmap.dao.NutritionAssessmentDao;
import com.fhi.kidmap.dao.NutritionAssessmentDaoImpl;
import com.fhi.kidmap.dao.OvcDao;
import com.fhi.kidmap.dao.OvcDaoImpl;
import com.fhi.kidmap.dao.PartnersDao;
import com.fhi.kidmap.dao.PartnersDaoImpl;
import com.fhi.kidmap.report.CustomReports;
import com.fhi.kidmap.report.HouseholdReports;
import com.fhi.kidmap.report.ReportDownloadManager;
import com.fhi.kidmap.report.ReportUtility;
import com.fhi.kidmap.report.SummaryStatisticsReportGenerator;
import com.fhi.nomis.logs.NomisLogManager;
import com.fhi.nomis.nomisutils.AccessManager;
import com.fhi.nomis.nomisutils.DateManager;
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
public class ReportDownloadAction extends org.apache.struts.action.Action
{
    
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
        ReportDownloadForm rdform=(ReportDownloadForm)form;
        HttpSession session=request.getSession();
        AppUtility appUtil=new AppUtility();
        DaoUtil util=new DaoUtil();
        DateManager dm=new DateManager();
        //ReportUtility rutil=new ReportUtility();
        ReportDownloadManager rdg=new ReportDownloadManager();
        //CustomReports customReport=new CustomReports();
        //PartnersDao pdao=new PartnersDaoImpl();
        //OrganizationUnitGroupAssignmentDao ougadao=new OrganizationUnitGroupAssignmentDaoImpl();

        String requiredAction=rdform.getActionName();
        String stateCode=rdform.getStateCode();
        String lgaCode=rdform.getLgaCode();
        String orgCode=rdform.getOrgCode();
        String orgUnitGroupId=rdform.getOrgUnitGroupId();
        String reportType=rdform.getReportType();
        AccessManager acm=new AccessManager();
        /*if(reportType !=null)
        {
            if(requiredAction !=null && !requiredAction.equalsIgnoreCase("download"))
            requiredAction=reportType;
        }*/
        String partnerCode=rdform.getPartnerCode();
        int startMth=appUtil.getMonthAsNumber(rdform.getStartMth());
        int startYear=rdform.getStartYear();
        int endMth=appUtil.getMonthAsNumber(rdform.getEndMth());
        int endYear=rdform.getEndYear();
        String[] dateParams={startMth+"",startYear+"",endMth+"",endYear+""};
        String startDate=util.getStartDate(dateParams);
        String endDate=util.getEndDate(dateParams);
        String[] selectedIndicators=rdform.getIndicators();
        String[] selectedStates=rdform.getStates();
        System.err.println("requiredAction is "+requiredAction);
        //System.err.println("reportType is "+reportType);
        List paramList=new ArrayList();
        List listOfMonths=dm.generateMonths(1);
        List listOfYears=DateManager.generateYears();
        session.setAttribute("generatedYears", listOfYears);
        session.setAttribute("generatedMonths", listOfMonths);
        List yearList=DateManager.generateYears();
        session.setAttribute("yearList", yearList);
        
        List orgUnitGroupAssignmentList=new ArrayList();
        request.setAttribute("ssorgUnitGroupList", orgUnitGroupAssignmentList);
        //List orgUnitGroupAssignmentParentList=new ArrayList();
        //String userName=appUtil.getCurrentUser(session);
        
        rdform.setStateList(util.getStateListFromHhe());
        acm.setStateListForReports(session);
        //session.setAttribute("stateList", acm.getStateListFromUser(userName));
        System.err.println("requiredAction is "+requiredAction);
        if(requiredAction==null)
        {
            List indicatorList=rdg.getReportDownloadIndicators();
            session.setAttribute("downloadIndicatorList", indicatorList);
            request.setAttribute("ssorgUnitGroupList", orgUnitGroupAssignmentList);
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("loadReportType"))
        {
            if(reportType.equalsIgnoreCase("hhindicators"))
            {
                 List indicatorList=rdg.getReportDownloadIndicatorsForHouseholds();
                 session.setAttribute("downloadIndicatorList", indicatorList);
                 request.setAttribute("ssorgUnitGroupList", orgUnitGroupAssignmentList);
                 return mapping.findForward(SUCCESS);
            }
            else if(reportType.equalsIgnoreCase("cgindicators"))
            {
                 List indicatorList=rdg.getReportDownloadIndicatorsForCaregivers();
                 session.setAttribute("downloadIndicatorList", indicatorList);
                 request.setAttribute("ssorgUnitGroupList", orgUnitGroupAssignmentList);
                 return mapping.findForward(SUCCESS);
            }
            else if(reportType.equalsIgnoreCase("indicatorvalues"))
            {
                List indicatorList=rdg.getReportDownloadIndicators();
                session.setAttribute("downloadIndicatorList", indicatorList);
                request.setAttribute("ssorgUnitGroupList", orgUnitGroupAssignmentList);
                return mapping.findForward(SUCCESS);
            }
            else if((reportType.equalsIgnoreCase("csi") || reportType.equalsIgnoreCase("ovcVulnerabilityScoreByCsi") || reportType.equalsIgnoreCase("hva") || reportType.equalsIgnoreCase("vcnassesmnt")) || reportType.equalsIgnoreCase("hivRiskAssessmentList"))
            {
                session.removeAttribute("downloadIndicatorList");
                request.setAttribute("ssorgUnitGroupList", orgUnitGroupAssignmentList);
                return mapping.findForward(SUCCESS);
            }
            else if(reportType.equalsIgnoreCase("vcradet") || reportType.equalsIgnoreCase("cgradet")|| reportType.equalsIgnoreCase("vclist") || reportType.equalsIgnoreCase("quarterlyReport"))
            {
                session.removeAttribute("downloadIndicatorList");
                request.setAttribute("ssorgUnitGroupList", orgUnitGroupAssignmentList);
                return mapping.findForward(SUCCESS);
            }
        }
        
        else if(requiredAction.equalsIgnoreCase("download"))
        {
            String reportPeriod=util.getMonthAsString(startMth)+" "+startYear+" - "+util.getMonthAsString(endMth)+" "+endYear;
            util.getMonthAsString(endMth);
            //dateParams
            //System.err.println("reportType in download is "+reportType);
            //getListOfOvcThatHasBeenAssessedOnHivRiskAssessment(additionalQuery,startDate,endDate,currentlyEnrolledQuery)
            if(reportType.equalsIgnoreCase("ageAnalysisReportDownload"))
            {
                return mapping.findForward("ageAnalysis");
            }
            /*else if(reportType.equalsIgnoreCase("quarterlyReport"))
            {
                try
                {
                    String fileName="Quaterly_Report_multiple"+DateManager.getCurrentDate();
                    CustomIndicatorsReportDao cirdao=new CustomIndicatorsReportDaoImpl();
                    List mainList=new ArrayList();
                    if(selectedStates !=null && selectedStates.length>0)
                    {
                        paramList=getParamList(stateCode,lgaCode,orgCode,startMth,startYear,endMth,endYear);
                        OvcQuarterlyReport quarterlyReport=new OvcQuarterlyReport();
                        List lgaList=null;
                        List list=null;
                        if(selectedStates.length==1)
                        {
                            stateCode=selectedStates[0];
                            paramList.set(0, stateCode);
                            String stateName=util.getStateName(stateCode);
                            if(stateName !=null)
                            {
                                list=cirdao.getAllReportTemplateByState(stateName);
                                if(list !=null)
                                mainList.add(list);
                                stateName=stateName.replaceAll(" ", "_");
                            }
                            //mainList.add(quarterlyReport.getQuarterlyReport(paramList, stateCode)); 
                            
                            fileName="Quaterly_Report_"+stateName+DateManager.getCurrentDate();
                        }
                        else
                        {
                            String stateName=null;
                            for(int j=0; j<selectedStates.length; j++)
                            {
                               stateCode=selectedStates[j];
                               stateName=util.getStateName(stateCode);
                               list=cirdao.getAllReportTemplateByState(stateName);
                               if(list !=null)
                               mainList.add(list);
                               //mainList.add(quarterlyReport.getQuarterlyReport(paramList, stateCode)); 
                               System.err.println("Quaterly_Report size is "+mainList.size()); 
                            }
                        }
                    }
                    
                    
                    response.setContentType("application/vnd.ms-excel");
                    response.setHeader("Content-Disposition", "attachment; filename="+fileName+".xls");
                    OutputStream os=response.getOutputStream();
                    ExcelWriter ew=new ExcelWriter();
                    WritableWorkbook wworkbook=ew.writeRevisedQuarterlyReportTemplateToExcel(os, mainList) ;
                    //WritableWorkbook wworkbook=ew.writeQuarterlyReportToExcel(os,mainList);
                    if(wworkbook !=null)
                    {
                        wworkbook.write();
                        wworkbook.close();
                    }
                    os.close();
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
                rdform.reset(mapping, request);
                return null;
            }*/
            /*else if(reportType.equalsIgnoreCase("quarterlyReport"))
            {
                try
                {
                    String fileName="Quaterly_Report_multiple"+DateManager.getCurrentDate();
                    CustomIndicatorsReportDao cirdao=new CustomIndicatorsReportDaoImpl();
                    List mainList=new ArrayList();
                    if(selectedStates !=null && selectedStates.length>0)
                    {
                        paramList=getParamList(stateCode,lgaCode,orgCode,startMth,startYear,endMth,endYear);
                        OvcQuarterlyReport quarterlyReport=new OvcQuarterlyReport();
                        List lgaList=null;
                        if(selectedStates.length==1)
                        {
                            stateCode=selectedStates[0];
                            paramList.set(0, stateCode);
                            String stateName=util.getStateName(stateCode);
                            if(stateName !=null)
                            stateName=stateName.replaceAll(" ", "_");
                            lgaList=util.getHouseholdEnrollmentDaoInstance().getDistinctLgaCodesByStateCode(stateCode);
                            for(int i=0; i<lgaList.size(); i++)
                            {
                              lgaCode=(String)lgaList.get(i);
                              paramList.set(1, lgaCode);
                              mainList.add(quarterlyReport.getQuarterlyReport(paramList, stateCode)); 
                            }
                            fileName="Quaterly_Report_"+stateName;
                        }
                        else
                        {
                            for(int j=0; j<selectedStates.length; j++)
                            {
                               stateCode=selectedStates[j];
                               paramList.set(0, stateCode);
                               lgaList=util.getHouseholdEnrollmentDaoInstance().getDistinctLgaCodesByStateCode(stateCode);
                               for(int i=0; i<lgaList.size(); i++)
                                {
                                  lgaCode=(String)lgaList.get(i);
                                  paramList.set(1, lgaCode);
                                  mainList.add(quarterlyReport.getQuarterlyReport(paramList, stateCode)); 
                                }
                               System.err.println("Quaterly_Report size is "+mainList.size()); 
                            }
                        }
                    }
                    
                    List list=cirdao.getAllReportTemplates();
                    if(list !=null)
                    mainList.add(list);
                    response.setContentType("application/vnd.ms-excel");
                    response.setHeader("Content-Disposition", "attachment; filename="+fileName+".xls");
                    OutputStream os=response.getOutputStream();
                    ExcelWriter ew=new ExcelWriter();
                    WritableWorkbook wworkbook=ew.writeRevisedQuarterlyReportTemplateToExcel(os, mainList) ;
                    //WritableWorkbook wworkbook=ew.writeQuarterlyReportToExcel(os,mainList);
                    if(wworkbook !=null)
                    {
                        wworkbook.write();
                        wworkbook.close();
                    }
                    os.close();
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
                rdform.reset(mapping, request);
                return null;
            }*/
            else if(reportType.equalsIgnoreCase("hivRiskAssessmentList"))
            {
                String fileName="HivRiskAssessmentList_multiple_orgunit"+appUtil.getCurrentDate();
                try
                {
                    HivRiskAssessmentChecklistDao hrdao=new HivRiskAssessmentChecklistDaoImpl();
                    List mainList=new ArrayList();
                    if(selectedStates !=null && selectedStates.length>0)
                    {
                        if(selectedStates.length==1)
                        {
                            stateCode=selectedStates[0];
                            String stateName=util.getStateName(stateCode);
                            if(stateName !=null)
                            stateName=stateName.replaceAll(" ", "_");
                            fileName="HivRiskAssessmentList_"+stateName;
                            String additionalQuery=" and hhe.stateCode='"+stateCode+"'";
                            mainList.addAll(hrdao.getHivRiskAssessmentList(additionalQuery, startDate, endDate,false,false));
                        }
                        else
                        {
                            for(int j=0; j<selectedStates.length; j++)
                            {
                               stateCode=selectedStates[j];
                               String additionalQuery=" and hhe.stateCode='"+stateCode+"'";
                               mainList.addAll(hrdao.getHivRiskAssessmentList(additionalQuery, startDate, endDate,false,false));
                               System.err.println("HivRiskAssessmentList size is "+mainList.size()); 
                            }
                        }
                    }
                    response.setContentType("application/vnd.ms-excel");
                    response.setHeader("Content-Disposition", "attachment; filename="+fileName+".xls");
                    OutputStream os=response.getOutputStream();
                    ExcelWriter ew=new ExcelWriter();
                    WritableWorkbook wworkbook=ew.writeHivRiskAssessmentDataToExcel(os,mainList);
                    if(wworkbook !=null)
                    {
                        wworkbook.write();
                        wworkbook.close();
                    }
                    os.close();
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
                rdform.reset(mapping, request);
                return null;
            }
            else if(reportType.equalsIgnoreCase("indicatorvalues"))
            {
                String fileName="Indicator_values_multiple_orgunit"+appUtil.getCurrentDate();
                try
                {
                    List mainList=new ArrayList();
                    if(selectedStates !=null && selectedStates.length>0)
                    {
                        if(selectedStates.length==1)
                        {
                            stateCode=selectedStates[0];
                            String stateName=util.getStateName(stateCode);
                            if(stateName !=null)
                            stateName=stateName.replaceAll(" ", "_");
                            fileName=util.getStateName(stateCode);
                        }
                        mainList=getReportForDownload(selectedStates, selectedIndicators,stateCode,startDate,endDate);
                    }

                    response.setContentType("application/vnd.ms-excel");
                    //response.setHeader("Content-Disposition", "attachment; filename=Indicator_values.xls");
                    response.setHeader("Content-Disposition", "attachment; filename="+fileName+".xls");
                    OutputStream os=response.getOutputStream();
                    ExcelWriter ew=new ExcelWriter();

                    WritableWorkbook wworkbook=ew.writeIndicatorValuesToExcel(os,mainList,reportPeriod);

                    if(wworkbook !=null)
                    {
                        wworkbook.write();
                        wworkbook.close();
                    }
                    os.close();
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
                rdform.reset(mapping, request);
                return null;
            }//
            else if(reportType.equalsIgnoreCase("vcnassesmnt"))
            {
                String fileName="Nutritional_Assessment_multiple_orgunit"+appUtil.getCurrentDate();
                try
                {
                    List mainList=new ArrayList();
                    if(selectedStates !=null && selectedStates.length>0)
                    {
                        if(selectedStates.length==1)
                        {
                            stateCode=selectedStates[0];
                            String stateName=util.getStateName(stateCode);
                            if(stateName !=null)
                            stateName=stateName.replaceAll(" ", "_");
                            fileName=util.getStateName(stateCode)+" Nutritional_Assessment";
                        }
                        for(int j=0; j<selectedStates.length; j++)
                        {
                           stateCode=selectedStates[j];
                           String additionalQuery=" and hhe.stateCode='"+stateCode+"'";
                           NutritionAssessmentDao nadao=new NutritionAssessmentDaoImpl();
                           mainList.addAll(nadao.getListofOvcWithNutrionalAssessmentByCohort(additionalQuery, startDate, endDate));
                           System.err.println("NA mainList size is "+mainList.size()); 
                        }
                    }
                    response.setContentType("application/vnd.ms-excel");
                    response.setHeader("Content-Disposition", "attachment; filename="+fileName+".xls");
                    OutputStream os=response.getOutputStream();
                    ExcelWriter ew=new ExcelWriter();
                    WritableWorkbook wworkbook=ew.writeOvcNutritionAssessmentValuesToExcel(os,mainList);
                    if(wworkbook !=null)
                    {
                        wworkbook.write();
                        wworkbook.close();
                    }
                    os.close();
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
                rdform.reset(mapping, request);
                return null;
            }
            else if(reportType.equalsIgnoreCase("cgindicators"))
            {
                String fileName="Caregiver_Indicators_multiple_orgunit"+appUtil.getCurrentDate();
                try
                {
                    List mainList=new ArrayList();
                    if(selectedStates !=null && selectedStates.length>0)
                    {
                        if(selectedStates.length==1)
                        {
                            stateCode=selectedStates[0];
                            String stateName=util.getStateName(stateCode);
                            if(stateName !=null)
                            stateName=stateName.replaceAll(" ", "_");
                            fileName=util.getStateName(stateCode)+" Caregiver_Indicators";
                        }
                        mainList=getReportForDownload(selectedStates, selectedIndicators,stateCode,startDate,endDate);
                    }
                    response.setContentType("application/vnd.ms-excel");
                    response.setHeader("Content-Disposition", "attachment; filename="+fileName+".xls");
                    OutputStream os=response.getOutputStream();
                    ExcelWriter ew=new ExcelWriter();
                    WritableWorkbook wworkbook=ew.writeCaregiverIndicatorValuesToExcel(os,mainList,reportPeriod);
                    if(wworkbook !=null)
                    {
                        wworkbook.write();
                        wworkbook.close();
                    }
                    os.close();
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
                rdform.reset(mapping, request);
                return null;
            }
            else if(reportType.equalsIgnoreCase("hhindicators"))
            {
                String fileName="Household_Indicators_multiple_orgunit"+appUtil.getCurrentDate();
                try
                {
                    List mainList=new ArrayList();
                    if(selectedStates !=null && selectedStates.length>0)
                    {
                        if(selectedStates.length==1)
                        {
                            stateCode=selectedStates[0];
                            String stateName=util.getStateName(stateCode);
                            if(stateName !=null)
                            stateName=stateName.replaceAll(" ", "_");
                            fileName=util.getStateName(stateCode)+" Household_Indicators";
                        }
                        mainList=getReportForDownload(selectedStates, selectedIndicators,stateCode,startDate,endDate);
                    }
                    response.setContentType("application/vnd.ms-excel");
                    response.setHeader("Content-Disposition", "attachment; filename="+fileName+".xls");
                    OutputStream os=response.getOutputStream();
                    ExcelWriter ew=new ExcelWriter();
                    WritableWorkbook wworkbook=ew.writeHouseholdIndicatorValuesToExcel(os,mainList);
                    if(wworkbook !=null)
                    {
                        wworkbook.write();
                        wworkbook.close();
                    }
                    os.close();
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
                rdform.reset(mapping, request);
                return null;
            }
            else if(reportType.equalsIgnoreCase("vclist"))
            {
                String fileName="OVC_List_"+appUtil.getCurrentDate();
                try
                {
                    List mainList=new ArrayList();
                    OvcDao dao=new OvcDaoImpl();
                    if(selectedStates !=null && selectedStates.length>0)
                    {
                        
                        for(int j=0; j<selectedStates.length; j++)
                        {
                            stateCode=selectedStates[j];
                            String[] params={stateCode,"All","All","All"};
                            String orgUnitCriteria=util.getSummStatQueryCriteria(params);
                            mainList.addAll(dao.getListOfOvcNewlyEnrolled(orgUnitCriteria, startDate, endDate, false));
                        }
                    }

                    response.setContentType("application/vnd.ms-excel");
                    //response.setHeader("Content-Disposition", "attachment; filename=Indicator_values.xls");
                    response.setHeader("Content-Disposition", "attachment; filename="+fileName+".xls");
                    OutputStream os=response.getOutputStream();
                    ExcelWriter ew=new ExcelWriter();
                    WritableWorkbook wworkbook=ew.writeOvcDataFromListOfIndicatorsToExcel("List of OVC",os, mainList) ;
                    //WritableWorkbook wworkbook=ew.writeOVCListToExcel(os,mainList);

                    if(wworkbook !=null)
                    {
                        wworkbook.write();
                        wworkbook.close();
                    }
                    os.close();
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
                rdform.reset(mapping, request);
                return null;
            }
            else if(reportType.equalsIgnoreCase("vcradet"))
            {
                String fileName="OVC_Radet_"+appUtil.getCurrentDate();
                try
                {
                    List mainList=new ArrayList();
                    
                    if(selectedStates !=null && selectedStates.length>0)
                    {
                        SummaryStatisticsReportGenerator ssg=new SummaryStatisticsReportGenerator();
                        String servicePeriodQuery=util.getOvcServiceDateQuery(startDate,endDate);
                        for(int j=0; j<selectedStates.length; j++)
                        {
                            stateCode=selectedStates[j];
                            String[] params={stateCode,"All","All","All"};
                            String orgUnitCriteria=util.getSummStatQueryCriteria(params);
                            List ovcList=ssg.getListOfOvcServed("Both gender",orgUnitCriteria,servicePeriodQuery,"");
                            if(ovcList !=null)
                            {
                                for(Object obj:ovcList)
                                {
                                    Ovc ovc=(Ovc)obj;
                                    if(ovc.getDateOfWithdrawal() !=null && ovc.getDateOfWithdrawal().indexOf("-") !=-1)
                                    {
                                        if(DateManager.compareDates(ovc.getDateOfWithdrawal(),startDate))
                                        {
                                            continue;
                                        }
                                    }
                                    mainList.add(ovc);
                                }
                            }
                            
                        }
                    }

                    response.setContentType("application/vnd.ms-excel");
                    //response.setHeader("Content-Disposition", "attachment; filename=Indicator_values.xls");
                    response.setHeader("Content-Disposition", "attachment; filename="+fileName+".xls");
                    OutputStream os=response.getOutputStream();
                    ExcelWriter ew=new ExcelWriter();

                    WritableWorkbook wworkbook=ew.writeOVCRadetToExcel(os,mainList);

                    if(wworkbook !=null)
                    {
                        wworkbook.write();
                        wworkbook.close();
                    }
                    os.close();
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
                rdform.reset(mapping, request);
                return null;
            }
            else if(reportType.equalsIgnoreCase("cgradet"))
            {
                String fileName="Caregiver_Radet_"+appUtil.getCurrentDate();
                try
                {
                    List mainList=new ArrayList();
                    
                    if(selectedStates !=null && selectedStates.length>0)
                    {
                        HouseholdReports hhr=new HouseholdReports();
                        SummaryStatisticsReportGenerator ssg=new SummaryStatisticsReportGenerator();
                        String servicePeriodQuery=util.getHhsPeriodQuery(startDate,endDate);
                        for(int j=0; j<selectedStates.length; j++)
                        {
                            stateCode=selectedStates[j];
                            String[] params={stateCode,"All","All","All"};
                            String orgUnitCriteria=util.getSummStatQueryCriteria(params);
                             
                            List reportList=ssg.getListOfCaregiversServed("Both gender",orgUnitCriteria+servicePeriodQuery,"",false,false);
                            /*List hhsList=hhr.processCaregiverList(reportList,session);
                            if(hhsList !=null)
                            {
                                String dateOfWithdrawal=null;
                                for(Object obj:hhsList)
                                {
                                    HouseholdService hhs=(HouseholdService)obj;
                                    if(hhs.getCgiver() !=null)
                                    {
                                        dateOfWithdrawal=hhs.getCgiver().getDateOfWithdrawal();
                                        if(dateOfWithdrawal !=null && dateOfWithdrawal.indexOf("-") !=-1)
                                        {
                                            if(DateManager.compareDates(dateOfWithdrawal,startDate))
                                            {
                                                continue;
                                            }
                                        }
                                     }
                                     mainList.add(hhs);
                                 }
                             }*/
                            mainList.addAll(hhr.processCaregiverList(reportList,session));
                        }
                    }
                    
                    
                    response.setContentType("application/vnd.ms-excel");
                    //response.setHeader("Content-Disposition", "attachment; filename=Indicator_values.xls");
                    response.setHeader("Content-Disposition", "attachment; filename="+fileName+".xls");
                    OutputStream os=response.getOutputStream();
                    ExcelWriter ew=new ExcelWriter();

                    WritableWorkbook wworkbook=ew.writeCaregiverRadetToExcel(os,mainList);

                    if(wworkbook !=null)
                    {
                        wworkbook.write();
                        wworkbook.close();
                    }
                    os.close();
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
                rdform.reset(mapping, request);
                return null;
            }
            else if(reportType.equalsIgnoreCase("csi"))
            {
               String fileName="CSI_multiple_orgunit"+appUtil.getCurrentDate();
                try
                {
                    List mainList=new ArrayList();
                    ChildStatusIndexDao csidao=new ChildStatusIndexDaoImpl();
                    if(selectedStates !=null && selectedStates.length>0)
                    {
                        if(selectedStates.length==1)
                        {
                            stateCode=selectedStates[0];
                            String stateName=util.getStateName(stateCode);
                            if(stateName !=null)
                            stateName=stateName.replaceAll(" ", "_");
                            fileName=util.getStateName(stateCode);
                            fileName="CSI_"+fileName;
                        }
                        for(int j=0; j<selectedStates.length; j++)
                        {
                            stateCode=selectedStates[j];
                            mainList.addAll(csidao.getChildStatusIndexRecordForDownload(stateCode,startDate,endDate));
                        }
                    }

                    response.setContentType("application/vnd.ms-excel");
                    //response.setHeader("Content-Disposition", "attachment; filename=Indicator_values.xls");
                    response.setHeader("Content-Disposition", "attachment; filename="+fileName+".xls");
                    OutputStream os=response.getOutputStream();
                    ExcelWriter ew=new ExcelWriter();

                    WritableWorkbook wworkbook=ew.writeCSIValuesToExcel(os,mainList);

                    if(wworkbook !=null)
                    {
                        wworkbook.write();
                        wworkbook.close();
                    }
                    os.close();
                }
                catch(Exception ex)
                {
                    NomisLogManager.logStackTrace(ex);
                    //ex.printStackTrace();
                }
                rdform.reset(mapping, request);
                return null;
            }
            else if(reportType.equalsIgnoreCase("ovcVulnerabilityScoreByCsi"))
            {
                String fileName="CSI_multiple_orgunit"+appUtil.getCurrentDate();
                try
                {
                    List mainList=new ArrayList();
                    ChildStatusIndexDao csidao=new ChildStatusIndexDaoImpl();
                    if(selectedStates !=null && selectedStates.length>0)
                    {
                        if(selectedStates.length==1)
                        {
                            stateCode=selectedStates[0];
                            String stateName=util.getStateName(stateCode);
                            if(stateName !=null)
                            stateName=stateName.replaceAll(" ", "_");
                            fileName=util.getStateName(stateCode);
                            fileName="CSI_"+fileName;
                        }
                        for(int j=0; j<selectedStates.length; j++)
                        {
                            stateCode=selectedStates[j];
                            mainList.addAll(csidao.getTotalCsiScoreForAllOvc(stateCode));
                        }
                    }

                    response.setContentType("application/vnd.ms-excel");
                    //response.setHeader("Content-Disposition", "attachment; filename=Indicator_values.xls");
                    response.setHeader("Content-Disposition", "attachment; filename="+fileName+".xls");
                    OutputStream os=response.getOutputStream();
                    ExcelWriter ew=new ExcelWriter();

                    WritableWorkbook wworkbook=ew.writeCSITotalScoresForVulnerabilityStatusToExcel(os,mainList);

                    if(wworkbook !=null)
                    {
                        wworkbook.write();
                        wworkbook.close();
                    }
                    os.close();
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
                rdform.reset(mapping, request);
                return null;
            }
            else if(reportType.equalsIgnoreCase("hva"))
            {
               String fileName="HVA_multiple_orgunit"+appUtil.getCurrentDate();
                try
                {
                    List mainList=new ArrayList();
                    HouseholdVulnerabilityAssessmentDao hvadao=new HouseholdVulnerabilityAssessmentDaoImpl();
                    if(selectedStates !=null && selectedStates.length>0)
                    {
                        if(selectedStates.length==1)
                        {
                            stateCode=selectedStates[0];
                            String stateName=util.getStateName(stateCode);
                            if(stateName !=null)
                            stateName=stateName.replaceAll(" ", "_");
                            fileName=util.getStateName(stateCode);
                            fileName="HVA_"+fileName;
                        }
                        for(int j=0; j<selectedStates.length; j++)
                        {
                            stateCode=selectedStates[j];
                            mainList.addAll(hvadao.getHouseholdVulnerabilityAssessmentRecordsForDownload(stateCode,startDate,endDate));
                        }
                    }

                    response.setContentType("application/vnd.ms-excel");
                    //response.setHeader("Content-Disposition", "attachment; filename=Indicator_values.xls");
                    response.setHeader("Content-Disposition", "attachment; filename="+fileName+".xls");
                    OutputStream os=response.getOutputStream();
                    ExcelWriter ew=new ExcelWriter();

                    WritableWorkbook wworkbook=ew.writeHVAValuesToExcel(os,mainList);

                    if(wworkbook !=null)
                    {
                        wworkbook.write();
                        wworkbook.close();
                    }
                    os.close();
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
                rdform.reset(mapping, request);
                return null;
            }
            
      }
        rdform.reset(mapping, request);
        return mapping.findForward(SUCCESS);
    }
    public List getReportForDownload(String[] selectedStates, String[] selectedIndicators,String stateCode,String startDate,String endDate)
    {
        ReportDownloadManager rdg=new ReportDownloadManager();
        List mainList=new ArrayList();
        for(int j=0; j<selectedStates.length; j++)
        {
            stateCode=selectedStates[j];
            //if(sumbyorgunitgroup)
            if(selectedIndicators !=null && selectedIndicators.length>0)
            {
                for(int i=0; i<selectedIndicators.length; i++)
                {
                    mainList.add(rdg.getReportDownloadData(selectedIndicators[i], stateCode,startDate,endDate));

                }
            }
        }
        return mainList;
    }
    private List getParamList(String stateCode,String lgaCode,String cboCode,int startMth,int startYr,int endMth,int endYr)
    {
        List paramList=new ArrayList();
        
        paramList.add(stateCode);
        paramList.add(lgaCode);
        paramList.add(cboCode);
        paramList.add("All");
        paramList.add(startMth);
        paramList.add(startYr);
        paramList.add(endMth);
        paramList.add(endYr);
        paramList.add("All");
        
        return paramList;
    }
}
