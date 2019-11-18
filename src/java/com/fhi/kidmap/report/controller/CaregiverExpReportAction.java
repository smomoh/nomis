/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report.controller;

import com.fhi.kidmap.business.CaregiverExpenditureAndSchoolAttendance;
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.business.User;
import com.fhi.kidmap.dao.CaregiverExpenditureAndSchoolAttendanceDao;
import com.fhi.kidmap.dao.CaregiverExpenditureAndSchoolAttendanceDaoImpl;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.HouseholdVulnerabilityAssessmentDao;
import com.fhi.kidmap.dao.HouseholdVulnerabilityAssessmentDaoImpl;
import com.fhi.kidmap.dao.OvcDao;
import com.fhi.kidmap.dao.OvcDaoImpl;
import com.fhi.kidmap.dao.PartnersDao;
import com.fhi.kidmap.dao.PartnersDaoImpl;
import com.fhi.kidmap.report.ReportUtility;
import com.fhi.nomis.nomisutils.AccessManager;
import com.fhi.nomis.nomisutils.AppUtility;
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
public class CaregiverExpReportAction extends org.apache.struts.action.Action {

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
       CaregiverExpReportForm cparform=(CaregiverExpReportForm)form;
        
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
        else if(requiredAction.equals("report"))
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
            String param[]={stateCode,lgaCode,orgCode,wardCode,lgaName,cboName,startMth+"",startYear+"",endMth+"",endYear+"",partnerCode,wardName,stateName,partnerName,assessmentNo+""};
            
            String queryCriteria=util.getHVIOrgUnitQuery(param);
            CaregiverExpenditureAndSchoolAttendanceDao ceasadao=new CaregiverExpenditureAndSchoolAttendanceDaoImpl();
            List reportList=new ArrayList();
            List list=ceasadao.getCaregiverExpenditureAndSchoolAttendanceList(queryCriteria);
            
            if(list !=null)
            {
                String ovcId=null;
                String ovcNames="";
                OvcDao dao=new OvcDaoImpl();
                Ovc ovc=null;
                List idList=new ArrayList();
                for(Object obj:list)
                {
                    CaregiverExpenditureAndSchoolAttendance ceasa=(CaregiverExpenditureAndSchoolAttendance)obj;
                    if(ceasa.getOvcId() !=null)
                    {
                        ovcNames="";
                        String[] ovcIdArray=appUtil.splitString(ceasa.getOvcId(),",");
                        if(ovcIdArray !=null)
                        {
                            for(int i=0; i<ovcIdArray.length; i++)
                            {
                                ovcId=ovcIdArray[i];
                                ovc=dao.getOvc(ovcId);
                                if(ovc !=null)
                                {
                                    if(i>0 && i<ovcIdArray.length-1)
                                    ovcNames+=",";
                                    ovcNames+=ovc.getFullName();
                                }
                            }
                            if(!idList.contains(ceasa.getRecordId()))
                            {
                                ceasa.setOvcNames(ovcNames);
                                idList.add(ceasa.getRecordId());
                            }
                        }
                        reportList.add(ceasa);
                    }
                    else
                    reportList.add(ceasa);
                }
            }
            
            request.setAttribute("ceasareportList", reportList);
            request.setAttribute("ceasaParam", param);
            
            cparform.reset(mapping, request);
            
            
            return mapping.findForward(REPORTPAGE);
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
