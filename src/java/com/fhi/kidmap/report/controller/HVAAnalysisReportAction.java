/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report.controller;

import com.fhi.kidmap.business.User;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.HouseholdVulnerabilityAssessmentDao;
import com.fhi.kidmap.dao.HouseholdVulnerabilityAssessmentDaoImpl;
import com.fhi.kidmap.dao.PartnersDao;
import com.fhi.kidmap.dao.PartnersDaoImpl;
import com.fhi.kidmap.report.HouseholdReports;
import com.fhi.kidmap.report.ReportUtility;
import com.fhi.nomis.nomisutils.AccessManager;
import com.fhi.nomis.nomisutils.AppUtility;
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
 * @author Siaka
 */
public class HVAAnalysisReportAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    /**
     * This is the action called from the Struts framework.
     *
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
        HVAAnalysisReportForm hvaForm=(HVAAnalysisReportForm)form;
        HouseholdReports hhr=new HouseholdReports();
        AppUtility appUtil=new AppUtility();
        DaoUtil util=new DaoUtil();
        AccessManager acm=appUtil.getAccessManager();
        String requiredAction=hvaForm.getActionName();
        String stateCode=hvaForm.getState();
        String lgaCode=hvaForm.getLga();
        String orgCode=hvaForm.getOrganization();
        String orgUnitGroupId=hvaForm.getOrgUnitGroupId();
        String wardCode=hvaForm.getWard();
        HttpSession session=request.getSession();
        String enrollmentStatus=hvaForm.getEnrollmentStatus();
        boolean currentlyEnrolled=false;
        if(enrollmentStatus !=null && enrollmentStatus.equalsIgnoreCase("currentlyEnrolled"))
        currentlyEnrolled=true;
        
        PartnersDao pdao=new PartnersDaoImpl();
        HouseholdVulnerabilityAssessmentDao hvadao=new HouseholdVulnerabilityAssessmentDaoImpl();
        
        List lgaList=new ArrayList();
        List cboList=new ArrayList();
        List wardList=new ArrayList();
        User user=appUtil.getUser(session);
        
        List stateList=new ArrayList();
        List partnerList=new ArrayList();
        partnerList=pdao.getAllPartners();
        int startMth=hvaForm.getStartMth();
        int endMth=hvaForm.getEndMth();
        int startYear=hvaForm.getStartYear();
        int endYear=hvaForm.getEndYear();
        int assessmentNo=hvaForm.getAssessmentNo();
        String thematicArea=request.getParameter("TA");
        List yearList=DateManager.generateYears();
       session.setAttribute("yearList", yearList);
        //String downloadInExcel=hvaForm.getDownloadInExcel();
        if(thematicArea !=null)
        requiredAction="hvaList";
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
            return mapping.findForward("paramPage");
        }
        else if(requiredAction.equals("allLga"))
        {
            session.setAttribute("hvisLgaList", new ArrayList());
            session.setAttribute("hvisOrgList", new ArrayList());
            session.setAttribute("hvawardList", new ArrayList());
            if(orgUnitGroupId==null || orgUnitGroupId.equalsIgnoreCase("All"))
            generateLgaList(stateCode,request);
            return mapping.findForward("paramPage");
        }
        else if(requiredAction.equals("lga"))
        {
            generateLgaList(stateCode,request);
            return mapping.findForward("paramPage");
        }
        else if(requiredAction.equals("cbo"))
        {
            List orgList =new ArrayList();
            if(user !=null)
            orgList =acm.getListOfCBOsAssignedToUser(user,stateCode,lgaCode);
            session.setAttribute("hvisOrgList", orgList);
            session.setAttribute("hvawardList", wardList);
            return mapping.findForward("paramPage");
        }
        else if(requiredAction.equals("wardList"))
        {
            if(user !=null)
            wardList =acm.getListOfCommunitiesAssignedToUserByCBO(user,stateCode,lgaCode,orgCode);
            session.setAttribute("hvawardList", wardList);
            return mapping.findForward("paramPage");
        }
        else if(requiredAction.equals("hvaList"))
        {
            String queryCriteria=(String)session.getAttribute("hvaAQueryCriteria");
            if(thematicArea.indexOf(":") !=-1)
            {
                String[] thematicAreaArray=thematicArea.split(":");
                String hhListTitle=null;
                if(thematicAreaArray[0].equalsIgnoreCase("vulnerabilityScore"))
                {
                    hhListTitle="List of Households that are "+thematicAreaArray[1];
                    List hhList=hhr.getListOfHouseholdsPerVulnerabilityStatus(Integer.parseInt(thematicAreaArray[2]), queryCriteria);
                    List hhListPerStatus=hhr.processHheList(hhList, session);
                    session.setAttribute("hhListPerScore", hhListPerStatus);
                    session.setAttribute("hhListTitle", hhListTitle);
                    return mapping.findForward("hhListPerScore");
                }
                else
                {
                    try
                    {
                        hhListTitle="List of Households that scored "+thematicAreaArray[2]+" in "+thematicAreaArray[0];
                        String property=thematicAreaArray[1];
                        int score=Integer.parseInt(thematicAreaArray[2]);
                        
                    
                        List hhListPerScore=hhr.getListOfHouseholdsPerThematicAreaScore(property, score, queryCriteria);
                        session.setAttribute("hhListPerScore", hhListPerScore);
                        session.setAttribute("hhListTitle", hhListTitle);
                        return mapping.findForward("hhListPerScore");
                    }
                    catch(Exception ex)
                    {
                        System.err.println("Could not load list "+ex.getMessage());
                    }
                }
            }
        }
        else if(requiredAction.equals("hvaReport"))
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
            String partnerCode=hvaForm.getPartnerCode();
            String partnerName=util.getPartnerName(partnerCode);
            String param[]={stateCode,lgaCode,orgCode,wardCode,lgaName,cboName,startMth+"",startYear+"",endMth+"",endYear+"",partnerCode,wardName,stateName,partnerName,assessmentNo+""};
            String queryCriteria=null;
            List analysisList=new ArrayList();
            List statusScoreList=new ArrayList();
            
                
                queryCriteria=util.getHVIReportCriteria(param);
                analysisList=hhr.getHVAThematicAreaAnalysis(queryCriteria);
                statusScoreList=hhr.getVulnerabilityScoreAndStatus(queryCriteria,currentlyEnrolled);
            //}
            session.setAttribute("analysisList", analysisList);
            session.setAttribute("statusScoreList", statusScoreList);
            session.setAttribute("hvaAParam", param);
            session.setAttribute("hvaAQueryCriteria", queryCriteria);
            
            return mapping.findForward("analysisList");
        }
        
        return mapping.findForward(SUCCESS);
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
