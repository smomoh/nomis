/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report.controller;

import com.fhi.kidmap.business.CareplanAchievement;
import com.fhi.kidmap.business.User;
import com.fhi.kidmap.dao.CareplanAchievementDao;
import com.fhi.kidmap.dao.CareplanAchievementDaoImpl;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.HouseholdVulnerabilityAssessmentDao;
import com.fhi.kidmap.dao.HouseholdVulnerabilityAssessmentDaoImpl;
import com.fhi.kidmap.dao.PartnersDao;
import com.fhi.kidmap.dao.PartnersDaoImpl;
import com.fhi.kidmap.report.ReportUtility;
import com.fhi.nomis.nomisutils.AccessManager;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.nomis.nomisutils.DateManager;
import com.fhi.nomis.nomisutils.NomisConstant;
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
public class CareplanAchievementReportAction extends org.apache.struts.action.Action 
{

    /* forward name="success" path="" */
    private static final String GRADUATIONLISTPARAMPAGE = "graduationListParamPage";
    private static String PARAMPAGE="paramPage";
    private static String REPORTPAGE="report";

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
        CareplanAchievementReportForm cparform=(CareplanAchievementReportForm)form;
        
        //HouseholdReports hhr=new HouseholdReports();
        AppUtility appUtil=new AppUtility();
        DaoUtil util=new DaoUtil();
        AccessManager acm=appUtil.getAccessManager();
        String requiredAction=cparform.getActionName();
        
        String stateCode=cparform.getState();
        String lgaCode=cparform.getLga();
        String orgCode=cparform.getOrganization();
        String orgUnitGroupId=cparform.getOrgUnitGroupId();
        String wardCode=cparform.getWard();
        HttpSession session=request.getSession();
        
        PartnersDao pdao=new PartnersDaoImpl();
        HouseholdVulnerabilityAssessmentDao hvadao=new HouseholdVulnerabilityAssessmentDaoImpl();
        
        List lgaList=new ArrayList();
        List cboList=new ArrayList();
        List wardList=new ArrayList();
        User user=appUtil.getUser(session);
        
        List stateList=new ArrayList();
        List partnerList=new ArrayList();
        partnerList=pdao.getAllPartners();
        int startMth=cparform.getStartMth();
        int endMth=cparform.getEndMth();
        int startYear=cparform.getStartYear();
        int endYear=cparform.getEndYear();
        int assessmentNo=cparform.getAssessmentNo();
        
        String pageName=cparform.getPageName();
        String pageId=request.getParameter("id");
        
        if(pageId !=null)
        PARAMPAGE=GRADUATIONLISTPARAMPAGE;
        else if(pageName !=null)
        PARAMPAGE=GRADUATIONLISTPARAMPAGE;
        System.err.println("pageName is "+pageName+" and pageId is "+pageId+" and PARAMPAGE is "+PARAMPAGE);
        List yearList=DateManager.generateYears();
        session.setAttribute("yearList", yearList);
        //String thematicArea=request.getParameter("TA");
        //String downloadInExcel=cparform.getDownloadInExcel();
        //if(thematicArea !=null)
        //requiredAction="hvaList";
        List orgUnitGroupList=new ArrayList();
        if(lgaCode ==null || lgaCode.equalsIgnoreCase("all"))
        {
            orgUnitGroupList=ReportUtility.getOrgUnitGroup(stateCode);
            //System.err.println("orgUnitGroupList size is "+orgUnitGroupList.size());
        }
        acm.setStateListForReports(session);
        session.setAttribute("ssorgUnitGroupList", orgUnitGroupList);
        session.setAttribute("partnerList", partnerList);
        List assessmentNoList=new ArrayList();
        if(stateCode==null || stateCode.equalsIgnoreCase("All"))
        assessmentNoList=hvadao.getDistinctAssessmentNumber();
        else
        assessmentNoList=hvadao.getDistinctAssessmentNumber(stateCode);
        request.setAttribute("assessmentNoList", assessmentNoList);
        
        if(requiredAction==null)
        {
            stateList=util.getUserAssignedStates(session);
            session.setAttribute("hvisStateList",stateList);
            session.setAttribute("hvisLgaList",lgaList);
            session.setAttribute("hvisOrgList", cboList);
            session.setAttribute("hvawardList", wardList);
            cparform.reset(mapping, request);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equals("allLga"))
        {
            session.setAttribute("hvisLgaList", new ArrayList());
            session.setAttribute("hvisOrgList", new ArrayList());
            session.setAttribute("hvawardList", new ArrayList());
            if(orgUnitGroupId==null || orgUnitGroupId.equalsIgnoreCase("All"))
            generateLgaList(stateCode,request);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equals("lga"))
        {
            generateLgaList(stateCode,request);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equals("cbo"))
        {
            List orgList =new ArrayList();
            if(user !=null)
            orgList =acm.getListOfCBOsAssignedToUser(user,stateCode,lgaCode);
            session.setAttribute("hvisOrgList", orgList);
            session.setAttribute("hvawardList", wardList);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equals("wardList"))
        {
            if(user !=null)
            wardList =acm.getListOfCommunitiesAssignedToUserByCBO(user,stateCode,lgaCode,orgCode);
            session.setAttribute("hvawardList", wardList);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equals("cpaReport"))
        {
            if(orgUnitGroupId !=null && !orgUnitGroupId.equalsIgnoreCase("all"))
            {
                lgaCode="orgUnitGroup";
                orgCode=orgUnitGroupId;
            }
            String stateName=util.getStateName(stateCode);
            String lgaName=util.getLgaName(lgaCode);
            String cboName=util.getOrganizationName(orgCode);
            
            String wardName=util.getWardName(wardCode);
            String partnerCode=cparform.getPartnerCode();
            String partnerName=util.getPartnerName(partnerCode);
            String startDate=util.getStartDate(startMth, startYear);
            String endDate=util.getEndDate(endMth, endYear);
            String param[]={stateCode,lgaCode,orgCode,wardCode,lgaName,cboName,startMth+"",startYear+"",endMth+"",endYear+"",partnerCode,wardName,stateName,partnerName,assessmentNo+""};
            
            String queryCriteria=util.getHVIOrgUnitQuery(param);
            String periodQuery=util.getCareplanAchievementPeriodQuery(startDate,endDate);
            queryCriteria=queryCriteria+periodQuery;
            CareplanAchievementDao cpadao=new CareplanAchievementDaoImpl();
            List cpareportList=new ArrayList();
            if(pageName !=null)
            {
                cpareportList=util.getCareplanAchievementDaoInstance().getCareplanAchievementsNotGraduated(queryCriteria);
                REPORTPAGE="graduationListPage";
            }
            else
            cpareportList=cpadao.getCareplanAchievements(queryCriteria);
            
            session.setAttribute("cpareportList", cpareportList);
            session.setAttribute("cpaParam", param);
            
            cparform.reset(mapping, request);
            
            
            return mapping.findForward(REPORTPAGE);
        }
        else if(requiredAction.equals("graduate"))
        {
            DateManager dm=new DateManager();
            CareplanAchievementDao cpadao=new CareplanAchievementDaoImpl();
            String[] selectedHouseholds=cparform.getHouseholdsToGraduate();
            CareplanAchievement cpa=null;//cpadao.getCareplanAchievementsPerClient(pageId);
            String dateOfGraduation=dm.getCurrentDate();
            if(selectedHouseholds !=null)
            {
                List cpaList=null;
                for(int i=0; i<selectedHouseholds.length; i++)
                {
                    cpaList=cpadao.getCareplanAchievementsPerClient(selectedHouseholds[i]);
                    if(cpaList !=null && !cpaList.isEmpty())
                    {
                        //get the last assessment and use the date as date of graduation
                        cpa=(CareplanAchievement)cpaList.get(0);
                        dateOfGraduation=cpa.getDateOfAssessment();
                        util.getOvcWithdrawalDaoInstance().withdrawHousehold(selectedHouseholds[i], dateOfGraduation, NomisConstant.GRADUATED,NomisConstant.HOUSEHOLD_TYPE,1);
                        System.err.println("selectedHouseholds[i] is "+selectedHouseholds[i] );
                    }
                }
                cparform.reset(mapping, request);
                return mapping.findForward(PARAMPAGE);
            }
        }
        return mapping.findForward(PARAMPAGE);
    }
    private void generateLgaList(String stateCode,HttpServletRequest request)
    {
        AppUtility appUtil=new AppUtility();
        User user=appUtil.getUser(request.getSession());
        AccessManager acm=new AccessManager();
        HttpSession session=request.getSession();
        List emptyList=new ArrayList();
        try
        {
            List lgaList=new ArrayList();
            if(user !=null)
            lgaList =acm.getListOfLgasAssignedToUser(user,stateCode);
            session.setAttribute("hvisLgaList", lgaList);
            session.setAttribute("hvisOrgList", emptyList);
            session.setAttribute("hvawardList", emptyList);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
    }
}
