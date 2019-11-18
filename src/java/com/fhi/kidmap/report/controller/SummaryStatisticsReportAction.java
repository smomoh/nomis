/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report.controller;

import com.fhi.kidmap.business.Indicators;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.User;
import com.fhi.kidmap.dao.OvcServiceDao;
import com.fhi.kidmap.dao.OvcServiceDaoImpl;
import com.fhi.kidmap.report.IndicatorWarehouse;
import com.fhi.kidmap.report.OvcRecords;
import com.fhi.kidmap.report.ReportUtility;
import com.fhi.kidmap.report.SummaryStatisticsReportManager;
import com.fhi.nomis.OperationsManagement.PartnerManager;
import com.fhi.nomis.nomisutils.AccessManager;
import com.fhi.nomis.nomisutils.DateManager;
import com.fhi.nomis.nomisutils.ExcelWriter;
import com.fhi.nomis.nomisutils.NomisConstant;
import com.fhi.nomis.nomisutils.OptionsAttributeManager;
import com.fhi.nomis.nomisutils.ResourceManager;
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
public class SummaryStatisticsReportAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    private static final String PARAMPAGE = "paramPage";
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
        SummaryStatisticsReportForm reportForm=(SummaryStatisticsReportForm)form;
        
        OvcServiceDao serviceDao=new OvcServiceDaoImpl();
        
        OvcRecords records=new OvcRecords();
        //ReportUtility rutil=new ReportUtility();
        AppUtility appUtil=new AppUtility();
        HttpSession session=request.getSession();
        AccessManager acm=appUtil.getAccessManager();
        DateManager dm=new DateManager();
        List partnerList=acm.getPartnerListForReports(session);
        acm.setStateListForReportsByPartnerCode(session,partnerList);
        //acm.setStateListForReports(session);
        User user=appUtil.getUser(session);
        
        
        List wardList=new ArrayList();
        List listOfDates=new ArrayList();
        List paramList=new ArrayList();
        List ageList=new ArrayList();
        List emptyList=new ArrayList();
        String requiredAction;
        String stateCode=reportForm.getSumstatPerMth_state();
        String lgaCode=reportForm.getSumstatPerMth_lga();
        String orgUnitGroupId=reportForm.getOrgUnitGroupId();
        String cboCode=reportForm.getSumstatPerMth_cbo();
        String wardCode=reportForm.getWard();
        String partnerCode=reportForm.getPartnerCode();
        String enrollmentStartMth=reportForm.getEnrollmentStartMth();
        String enrollmentStartYear=reportForm.getEnrollmentStartYear();
        String enrollmentEndMth=reportForm.getEnrollmentEndMth();
        String enrollmentEndYear=reportForm.getEnrollmentEndYear();
        String serviceStartMth=reportForm.getServiceStartMth();
        String serviceStartYear=reportForm.getServiceStartYear();
        String serviceEndMth=reportForm.getServiceEndMth();
        String serviceEndYear=reportForm.getServiceEndYear();
        String ovcStartAge=reportForm.getOvcStartAge();
        String ovcEndAge=reportForm.getOvcEndAge();
        
        if(partnerCode !=null && !partnerCode.equalsIgnoreCase("All"))
        {
            List singleList=new ArrayList();
            singleList.add(PartnerManager.getPartner(partnerCode));
            acm.setStateListForReportsByPartnerCode(session,singleList);
        }

        //String reportType=reportForm.getReportType();
        int fmonth=reportForm.getSumstat_month();
        int fyear=reportForm.getSumstat_year();
        
        String id=request.getParameter("id");
        requiredAction=reportForm.getActionName();
        if(id !=null)
        requiredAction=id;
        
        System.err.println("requiredAction in summary statistics action is "+requiredAction);
        //session.removeAttribute("summstatlgaList");
        //pdao.getAllPartners();
        List orgUnitGroupList=new ArrayList();
        if(lgaCode ==null || lgaCode.equalsIgnoreCase("all"))
        {
            orgUnitGroupList=ReportUtility.getOrgUnitGroup(stateCode);
            System.err.println("orgUnitGroupList size is "+orgUnitGroupList.size());
        }
        session.setAttribute("ssorgUnitGroupList", orgUnitGroupList);
        session.setAttribute("partnerList", partnerList);
        //session.setAttribute("sumstatwardList", emptyList);
        //session.removeAttribute("summaryStatistics");
        //session.removeAttribute("summaryStatisticsPerMth");

        
        String[] reqParamArray=null;
        String reqParam=request.getParameter("reqParam");
        
        SummaryStatisticsReportManager ssrm=new SummaryStatisticsReportManager();
        //request.setAttribute("sunstatIndictors", getIndicatorTables());
        List list=ssrm.getSummaryStatisticsIndicators();
        
        request.setAttribute("sunstatIndictors", list);
        if(reqParam !=null && reqParam.indexOf(":") !=-1)
        {
            reqParamArray=reqParam.split(":");
            requiredAction=reqParamArray[3];
            //System.err.println("reqParam is "+reqParam);
        }
        /*if(TaskManager.isDatabaseLocked())
        {
            request.setAttribute("sumstatButtonDisabled", "true");
            request.setAttribute("sumstatmsg", "System busy. Try again later");
            return mapping.findForward(PARAMPAGE);  
        }*/
        if(requiredAction==null)
        {
            serviceDao.setNumberOfServicesPerServiceRecord();
            ageList.clear();
            ageList=appUtil.getAgeList(0,25);
            List listOfMonths=dm.generateMonths(1);
            List listOfYears=DateManager.generateYears();
            session.setAttribute("generatedStartDates", listOfYears);
            session.setAttribute("generatedMonths", listOfMonths);
            session.setAttribute("serviceStartYears", listOfYears);
            session.setAttribute("serviceStartMonths", listOfMonths);

            session.setAttribute("enrollmentEndMonths", emptyList);
            session.setAttribute("enrollmentEndYears", emptyList);
            session.setAttribute("serviceEndMonths", emptyList);
            session.setAttribute("serviceEndYears", emptyList);
            session.setAttribute("serviceEndYears", emptyList);
            session.setAttribute("serviceEndMonths", emptyList);
            session.setAttribute("ageList", ageList);
            session.removeAttribute("summaryStatistics");
            //session.removeAttribute("summaryStatisticsPerMth");
            //if(session.getAttribute("endAgeList")==null)
            //{
                List endAgeList=new ArrayList();
                endAgeList.addAll(ageList);
                endAgeList.add("24+");
                session.setAttribute("endAgeList", endAgeList);
            //}
            reportForm.reset(mapping, request);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equals("stateList"))
        {
            session.removeAttribute("summaryStatistics");
            session.setAttribute("summstatlgaList", new ArrayList());
            session.setAttribute("summstatcboList", new ArrayList());
            session.setAttribute("sumstatwardList", new ArrayList());
            if(orgUnitGroupId==null || orgUnitGroupId.equalsIgnoreCase("All"))
            generateLgaList(stateCode,request);//
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equals("allLga"))
        {
            session.removeAttribute("summaryStatistics");
            session.setAttribute("summstatlgaList", new ArrayList());
            session.setAttribute("summstatcboList", new ArrayList());
            session.setAttribute("sumstatwardList", new ArrayList());
            if(orgUnitGroupId==null || orgUnitGroupId.equalsIgnoreCase("All"))
            generateLgaList(stateCode,request);//
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equals("endAge"))
        {
            session.removeAttribute("summaryStatistics");
            List endAgeList=new ArrayList();
            if(ovcStartAge !=null)
            {
                if(ovcStartAge.equalsIgnoreCase("All") || ovcStartAge.indexOf("+") !=-1)
                ageList.add("All");
                else
                {
                    int startAge=Integer.parseInt(ovcStartAge);
                    endAgeList=appUtil.getAgeListAbove18(startAge);
                }
                session.setAttribute("endAgeList", endAgeList);
            }
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equals("enrollmentEndDates"))
        {
            session.removeAttribute("summaryStatistics");
            String startMth=reportForm.getEnrollmentStartMth();
            String startYear=reportForm.getEnrollmentStartYear();
            
            if(startMth !=null && !startMth.equalsIgnoreCase("All") && startYear !=null && !startYear.equalsIgnoreCase("All"))
            {
                startMth="1";
                List listOfMonths=dm.generateMonths(appUtil.getMonthAsNumber(startMth));
                List listOfYears=dm.generatDate(Integer.parseInt(startYear));
                session.setAttribute("enrollmentEndMonths", listOfMonths);
                session.setAttribute("enrollmentEndYears", listOfYears);
                session.setAttribute("serviceStartYears", listOfYears);
                session.setAttribute("serviceStartMonths", listOfMonths);  
            }
            else
            {
                reportForm.setEnrollmentStartMth("All");
                session.setAttribute("enrollmentEndMonths", emptyList);
                session.setAttribute("enrollmentEndYears", emptyList);
            }
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equals("serviceEndDates"))
        {
            session.removeAttribute("summaryStatistics");
            String startMth=reportForm.getServiceStartMth();
            String startYear=reportForm.getServiceStartYear();

            if(startMth !=null && !startMth.equalsIgnoreCase("All") && startYear !=null && !startYear.equalsIgnoreCase("All"))
            {
                List listOfMonths=dm.generateMonths(appUtil.getMonthAsNumber(startMth));
                List listOfYears=dm.generatDate(Integer.parseInt(startYear));
                session.setAttribute("serviceEndYears", listOfYears);
                session.setAttribute("serviceEndMonths", listOfMonths);
            }
            else
            {
                reportForm.setServiceStartMth("All");
                session.setAttribute("serviceEndYears", emptyList);
                session.setAttribute("serviceEndMonths", emptyList);
            }
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equals("lga"))
        {
            session.removeAttribute("summaryStatistics");
            generateLgaList(stateCode,request); 
            session.setAttribute("summstatcboList", new ArrayList());
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equals("cbo"))
        {
            session.removeAttribute("summaryStatistics");
            List orgList =new ArrayList();
            if(user !=null)
            orgList =acm.getListOfCBOsAssignedToUser(user,stateCode,lgaCode);
            session.setAttribute("sumstatwardList", new ArrayList());
            //List orgList =util.getUserAssignedCBOs(stateCode,lgaCode,session);
            session.setAttribute("summstatcboList", orgList);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equals("ward"))
        {
            session.removeAttribute("summaryStatistics");
            if(user !=null)
            wardList =acm.getListOfCommunitiesAssignedToUserByCBO(user,stateCode,lgaCode,cboCode);
            //wardList=util.getUserAssignedCommunities(stateCode,lgaCode,cboCode,session);
            session.setAttribute("sumstatwardList", wardList);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equals("loadenrollmentdates"))
        {
            listOfDates =dm.generatDate(2004);
            request.setAttribute("secondenrollmentdates", listOfDates);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equals("excelReportTable"))
        {
            String orgUnitParam="";
            List mainReportList=new ArrayList();
            List metadataInfoList=new ArrayList();
            List reportList=(List)session.getAttribute("summaryStatistics");
            List householdReportList=(List)session.getAttribute("orgAssessmentList");
            String[] param=(String[])session.getAttribute("summaryStatparams");
            String period=(String)session.getAttribute("period");
            String agegroup=(String)session.getAttribute("agegroup");
            if(param !=null)
            {
                orgUnitParam="State: "+param[5]+" LGA: "+param[6]+" CBO: "+param[17]+" Ward/Community: "+param[20];
            }
            metadataInfoList.add(orgUnitParam);
            metadataInfoList.add(period);
            metadataInfoList.add(agegroup);
            
            if(reportList !=null && !reportList.isEmpty())
            mainReportList.addAll(reportList);
            if(householdReportList !=null && !householdReportList.isEmpty())
            mainReportList.addAll(householdReportList);
            if(mainReportList !=null && !mainReportList.isEmpty())
            {
                String fileName="SummaryStatList_"+appUtil.getCurrentDate();
                OutputStream os=response.getOutputStream();
                ExcelWriter ew=new ExcelWriter();
                WritableWorkbook wworkbook=ew.writeListOfIndicatorsTableToExcel(os, mainReportList,metadataInfoList);
                
                System.err.println("reportList.size() in summstatList is "+mainReportList.size());
                    fileName="SummaryStatList_"+appUtil.getCurrentDate();
                    response.setContentType("application/vnd.ms-excel");
                        
                        response.setHeader("Content-Disposition", "attachment; filename="+fileName+".xls");
                        if(wworkbook !=null)
                        {
                            wworkbook.write();
                            wworkbook.close();
                        }
                        os.close();
                
                return null;
            }
            return null;
        }
        else if(requiredAction.equals("summstatreport"))
        {
            if(orgUnitGroupId !=null && !orgUnitGroupId.equalsIgnoreCase("all"))
            {
                lgaCode="orgUnitGroup";
                cboCode=orgUnitGroupId;
            }
            paramList.add(stateCode);
            paramList.add(lgaCode);
            paramList.add(cboCode);
            paramList.add(fmonth);
            paramList.add(fyear);

            paramList.add(appUtil.getMonthAsNumber(enrollmentStartMth));
            paramList.add(enrollmentStartYear);
            paramList.add(appUtil.getMonthAsNumber(enrollmentEndMth));
            paramList.add(enrollmentEndYear);
            paramList.add(appUtil.getMonthAsNumber(serviceStartMth));
            paramList.add(serviceStartYear);
            paramList.add(appUtil.getMonthAsNumber(serviceEndMth));
            paramList.add(serviceEndYear);
            paramList.add(ovcStartAge);
            paramList.add(ovcEndAge);
            paramList.add(partnerCode);
            paramList.add(wardCode);
            String[] indicators=reportForm.getIndicatorIndexes();
            if(indicators==null)
            {
                request.setAttribute("sumstatmsg", "You must select at least one indicator");
                return mapping.findForward("paramPage");
            }
            String userGroupId=null;
            if(user !=null)
            userGroupId=user.getUserGroupId();
            records.getOvcCount(session, "All", paramList,indicators,userGroupId);
            reportForm.reset(mapping, request);
        }
        else if(requiredAction.equals("summstatList"))
        {
            if(!ResourceManager.displayBeneficiaryRecordsInHTML())
            return mapping.findForward(OptionsAttributeManager.OPSNOTALLOWED);  
            String target="summstatOvcList";
            reqParam=request.getParameter("reqParam");
            String params[]=reqParam.split(":");
            String indicatorCode=params[1];
            //System.err.println("indicatorCode in summstatList is "+indicatorCode); indicatorCode.equalsIgnoreCase("cgactiserrp") || indicatorCode.equalsIgnoreCase("cgsergradrp") || 
            if(indicatorCode.equalsIgnoreCase("cgiverserve") || indicatorCode.equalsIgnoreCase("cgivehivacc") || indicatorCode.equalsIgnoreCase("cgnenrserrp"))
            target="caregiverList";
            else if(indicatorCode.equalsIgnoreCase("cgscrnfortb") )
            target="caregiverRegister"; 
            else if(indicatorCode.equalsIgnoreCase("hhenrolled") || indicatorCode.equalsIgnoreCase("hhemergneed"))
            target="householdList";
            
            
            String title=null;
            OvcRecords record=new OvcRecords();
            if(params.length < 5)
            {
                title=params[0]+" ("+params[2]+")";
                target=record.getSummStatisticsList(session,params,"all");
                //System.err.println("target 1 in summstatList is "+target);
            }
            else if (params.length >4)
            {
                title=params[4]+" in "+params[5]+" ("+params[3]+")";
                target=record.getSummStatisticsList(session,params,"monthly");
                //System.err.println("target 2 in summstatList is "+target);
            }
            //if(indicatorCode.equalsIgnoreCase("hhemergneed"))
            //target="householdList";
            System.err.println("target in summstatList is "+target);
            
            session.setAttribute("summListTitle", title);
            return mapping.findForward(target);
        }
        else if(requiredAction.equals("dowloadInExcel"))
        {
            if(!ResourceManager.downloadBeneficiaryRecordsInExcel())
            return mapping.findForward(OptionsAttributeManager.OPSNOTALLOWED);
            String type="Ovc";
            String state="multiple";
            String fileName=type+"_SummaryStatList_"+appUtil.getCurrentDate();
            reqParam=request.getParameter("reqParam");
            String params[]=reqParam.split(":");
            String indicatorCode=params[1];//
            //IndicatorWarehouse indw=new IndicatorWarehouse();
            
            List ovcList=new ArrayList();
            String title=null;
            OvcRecords record=new OvcRecords();
            if(params.length < 5)
            {
                title=params[0]+" ("+params[2]+")";
                ovcList=record.getSummStatisticsListForExcelDownload(session,params,"all");
                //System.err.println("target 1 in summstatList is "+target);
            }
                    OutputStream os=response.getOutputStream();
                    ExcelWriter ew=new ExcelWriter();
                    WritableWorkbook wworkbook=null;
                    String indicatorName="indicatorCode";
                    IndicatorWarehouse indw=new IndicatorWarehouse();
                    Indicators ind=indw.getIndicatorById(indicatorCode);
                    if(ind !=null)
                    indicatorName=ind.getIndicatorName();
                    if(ind !=null)
                    {
                        String indicatorType=ind.getIndicatorType();
                        if(indicatorType !=null)
                        {
                            if(indicatorType.equalsIgnoreCase(NomisConstant.OVC_TYPE))
                            {
                                wworkbook=ew.writeOvcDataFromListOfIndicatorsToExcel(indicatorName,os,ovcList);
                                type="Ovc";
                            }
                            else if(indicatorType.equalsIgnoreCase(NomisConstant.Caregiver_TYPE))
                            {
                                wworkbook=ew.writeCaregiverListToExcel(indicatorName,os,ovcList);
                                type="Caregiver";
                            }
                            else if(indicatorType.equalsIgnoreCase(NomisConstant.HOUSEHOLD_TYPE))
                            {
                                wworkbook=ew.writeHouseholdEnrollmentRecordsToExcel(indicatorName,os,ovcList);
                                type="Household";
                            } 
                        }
                    }
                    
                System.err.println("ovcList.size() in summstatList is "+ovcList.size());
                fileName=type+"_SummaryStatList_"+appUtil.getCurrentDate();
                response.setContentType("application/vnd.ms-excel");
                    //response.setHeader("Content-Disposition", "attachment; filename=Indicator_values.xls");
                    response.setHeader("Content-Disposition", "attachment; filename="+fileName+".xls");
                  if(wworkbook !=null)
                    {
                        wworkbook.write();
                        wworkbook.close();
                    }
                    os.close();
            return null;
        }
       return mapping.findForward(SUCCESS);
    }
    private void generateLgaList(String stateCode,HttpServletRequest request)
    {
        List emptyList=new ArrayList();
        AppUtility appUtil=new AppUtility();
        User user=appUtil.getUser(request.getSession());
        AccessManager acm=new AccessManager();
        HttpSession session=request.getSession();
        try
        {
            List lgaList=new ArrayList();
            if(user !=null)
            lgaList =acm.getListOfLgasAssignedToUser(user,stateCode);//.getUserAssignedLgas(stateCode,session);//List lgaList =util.getUserAssignedLgas(stateCode,session);
            session.setAttribute("summstatlgaList", lgaList);
            session.setAttribute("summstatcboList", emptyList);
            session.setAttribute("sumstatwardList", emptyList);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }   
}
