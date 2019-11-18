/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report.controller;

import com.fhi.kidmap.business.User;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.nomis.nomisutils.LoadUpInfo;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.OvcServiceDao;
import com.fhi.kidmap.dao.OvcServiceDaoImpl;
import com.fhi.kidmap.dao.PartnersDao;
import com.fhi.kidmap.dao.PartnersDaoImpl;
import com.fhi.kidmap.report.CohortAnalysisReportManager;
import com.fhi.kidmap.report.ReportUtility;
import com.fhi.nomis.nomisutils.AccessManager;
import com.fhi.nomis.nomisutils.DateManager;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author smomoh
 */
public class CohortAnalysisReportAction extends org.apache.struts.action.Action {

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
        CohortAnalysisReportForm reportForm=(CohortAnalysisReportForm)form;
        CohortAnalysisReportManager carm=new CohortAnalysisReportManager();
        DateManager dm=new DateManager();
        ReportUtility rutil=new ReportUtility();
        HttpSession session=request.getSession();
        AppUtility appUtil=new AppUtility();
        User user=appUtil.getUser(session);
        AccessManager acm=new AccessManager();
        acm.setStateListForReports(session);
        
        DaoUtil util=new DaoUtil();
        PartnersDao pdao=new PartnersDaoImpl();
        
        OvcServiceDao serviceDao=new OvcServiceDaoImpl();
        
        List lgaList=new ArrayList();
        List cboList=new ArrayList();
        List wardList=new ArrayList();
        List listOfDates=new ArrayList();
        List paramList=new ArrayList();
        
        List emptyList=new ArrayList();
        String requiredAction;
        String stateCode=reportForm.getStateCode();
        String orgUnitGroupId=reportForm.getOrgUnitGroupId();
        String lgaCode=reportForm.getLgaCode();
        String cboCode=reportForm.getCboCode();
        String wardCode=reportForm.getWardCode();
        String partnerCode=reportForm.getPartnerCode();
        String startMth=reportForm.getStartMth();
        String startYear=reportForm.getStartYear();
        String endMth=reportForm.getEndMth();
        String endYear=reportForm.getEndYear();

        requiredAction=reportForm.getActionName();

        List partnerList=pdao.getAllPartners();
        session.setAttribute("partnerList", partnerList);
        List orgUnitGroupList=new ArrayList();
        if(lgaCode ==null || lgaCode.equalsIgnoreCase("all"))
        {
            orgUnitGroupList=ReportUtility.getOrgUnitGroup(stateCode);
        }
        session.setAttribute("ssorgUnitGroupList", orgUnitGroupList);
        
        LoadUpInfo loadup=new LoadUpInfo();
        loadup.getAllStates(session);
        loadup.setParamAttributes(request);
        String[] reqParamArray=null;
        String reqParam=request.getParameter("q");
        List indicatorList=carm.getCohortAnalysisIndicators();
                
        request.setAttribute("cohortAnalysisIndictors", indicatorList);
        if(reqParam !=null && reqParam.indexOf(":") !=-1)
        {
            reqParamArray=reqParam.split(":");
            requiredAction=reqParamArray[2];
            //System.err.println("reqParam is "+reqParam);
        }

        if(requiredAction==null)
        {
            List listOfMonths=dm.generateMonths(1);
            List listOfYears=DateManager.generateYears();
            session.setAttribute("cohortlgaList", emptyList);
            session.setAttribute("cohortcboList", emptyList);
            session.setAttribute("cohortwardList", emptyList);
            session.setAttribute("cohortStartDates", listOfYears);
            session.setAttribute("cohortStartMonths", listOfMonths);
            session.setAttribute("cohortEndMonths", emptyList);
            session.setAttribute("cohortEndYears", emptyList);
            reportForm.reset(mapping, request);
            return mapping.findForward("criteriaPage");
        }
        
        else if(requiredAction.equals("enrollmentEndDates"))
        {
                      
            if(startMth !=null && !startMth.equalsIgnoreCase("All") && startYear !=null && !startYear.equalsIgnoreCase("All"))
            {
                startMth="1";
                List listOfMonths=dm.generateMonths(appUtil.getMonthAsNumber(startMth));
                List listOfYears=DateManager.generateYears();
                //List listOfYears=appUtil.generatDate(Integer.parseInt(startYear));
                session.setAttribute("cohortEndMonths", listOfMonths);
                session.setAttribute("cohortEndYears", listOfYears);  
            }
            else
            {
                reportForm.setStartMth("All");
                session.setAttribute("cohortEndMonths", emptyList);
                session.setAttribute("cohortEndYears", emptyList);
            }
            reportForm.reset(mapping, request);
            return mapping.findForward("criteriaPage");
        }
        else if(requiredAction.equals("allLga"))
        {
            session.setAttribute("cohortlgaList", emptyList);
            session.setAttribute("cohortcboList", emptyList);
            session.setAttribute("cohortwardList", emptyList);
            if(orgUnitGroupId==null || orgUnitGroupId.equalsIgnoreCase("All"))
            generateLgaList(stateCode,request);
        }
        else if(requiredAction.equals("lga"))
        {
            if(user !=null)
            lgaList =acm.getListOfLgasAssignedToUser(user,stateCode);
            session.setAttribute("cohortlgaList", lgaList);
            session.setAttribute("cohortcboList", emptyList);
            session.setAttribute("cohortwardList", emptyList);
            reportForm.reset(mapping, request);
        }
        else if(requiredAction.equals("cbo"))
        {
            List orgList =new ArrayList();
            if(user !=null)
            orgList =acm.getListOfCBOsAssignedToUser(user,stateCode,lgaCode);
            session.setAttribute("cohortcboList", orgList);
            session.setAttribute("cohortwardList", emptyList);
            reportForm.reset(mapping, request);
            return mapping.findForward("criteriaPage");
        }
        else if(requiredAction.equals("ward"))
        {
            if(user !=null)
            wardList =acm.getListOfCommunitiesAssignedToUserByCBO(user,stateCode,lgaCode,cboCode);
            session.setAttribute("cohortwardList", wardList);
            reportForm.reset(mapping, request);
            return mapping.findForward("criteriaPage");
        }
        else if(requiredAction.equals("loadenrollmentdates"))
        {
            listOfDates =DateManager.generateYears();
            request.setAttribute("secondenrollmentdates", listOfDates);
            reportForm.reset(mapping, request);
            return mapping.findForward("criteriaPage");
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
            paramList.add(wardCode);
            paramList.add(partnerCode);
            paramList.add(appUtil.getMonthAsNumber(startMth));
            paramList.add(startYear);
            paramList.add(appUtil.getMonthAsNumber(endMth));
            paramList.add(endYear);
            paramList.add(util.getStateName(stateCode));
            paramList.add(util.getLgaName(lgaCode));
            paramList.add(util.getOrganizationName(cboCode));
            paramList.add(util.getWardName(wardCode));
            List reportList=new ArrayList();
                        
            String[] indicators=reportForm.getSelectedIndicatorIds();
            if(indicators !=null)
            {
                for(int i=0; i<indicators.length; i++)
                {
                    reportList.addAll(carm.getCohortAnalysisReport(indicators[i],paramList,"number",null));
                }
            }
            String cohortperiod=startMth+" "+startYear+" to "+endMth+" "+endYear;
            request.setAttribute("cohortreport", reportList);
            session.setAttribute("cohortorgparam", paramList);
            session.setAttribute("cohortperiod", cohortperiod);
            session.setAttribute("cohortParamList", paramList);
            reportForm.reset(mapping, request);
            return mapping.findForward("report");
        }
        else if(requiredAction.equals("list"))
        {
            List reportList=new ArrayList();
            String indicatorId=reqParamArray[0];
            String[] criteria=reqParam.split(":");
            String gender=criteria[1];
            //System.err.println("gender is "+gender);
            String category=criteria[3];
            paramList=(List)session.getAttribute("cohortParamList");
            String title="Cohort report list";
            if(paramList !=null)
            {
                List mainList=carm.getCohortAnalysisReport(indicatorId,paramList,"list",gender);
                reportList.addAll((List)mainList.get(0));
                title=mainList.get(1).toString();
            }
            if(category.equalsIgnoreCase("household"))
            request.setAttribute("hviRecords", reportList);
            else if(category.equalsIgnoreCase("caregiver"))
            request.setAttribute("cgiverList", reportList);
            else if(category.equalsIgnoreCase("ovc"))
            request.setAttribute("ovcList", reportList);
            request.setAttribute("cohortlisttitle", title);
            reportForm.reset(mapping, request);
            return mapping.findForward(category);
        }
       return mapping.findForward("criteriaPage");
    }
    private void generateLgaList(String stateCode,HttpServletRequest request)
    {
        AppUtility appUtil=new AppUtility();
        User user=appUtil.getUser(request.getSession());
        AccessManager acm=new AccessManager();
        HttpSession session=request.getSession();
        try
        {
            List lgaList=new ArrayList();
            if(user !=null)
            lgaList =acm.getListOfLgasAssignedToUser(user,stateCode);
            List emptyList=new ArrayList();
            session.setAttribute("cohortlgaList", lgaList);
            session.setAttribute("cohortcboList", emptyList);
            session.setAttribute("cohortwardList", emptyList);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
