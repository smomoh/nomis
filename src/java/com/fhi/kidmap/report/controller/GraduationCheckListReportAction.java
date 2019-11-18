/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report.controller;

import com.fhi.kidmap.business.States;
import com.fhi.kidmap.business.User;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.GraduationCheckListDao;
import com.fhi.kidmap.dao.GraduationCheckListDaoImpl;
import com.fhi.kidmap.dao.PartnersDao;
import com.fhi.kidmap.dao.PartnersDaoImpl;
import com.fhi.kidmap.dao.StatesDao;
import com.fhi.kidmap.dao.StatesDaoImpl;
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
public class GraduationCheckListReportAction extends org.apache.struts.action.Action {

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
        GraduationCheckListReportForm gclrf=(GraduationCheckListReportForm)form;
        AppUtility appUtil=new AppUtility();
        String requiredAction=gclrf.getActionName();
        String stateCode=gclrf.getState();
        String lgaCode=gclrf.getLga();
        String orgUnitGroupId=gclrf.getOrgUnitGroupId();
        String orgCode=gclrf.getOrganization();
        String wardCode=gclrf.getWard();
        HttpSession session=request.getSession();
        AccessManager acm=appUtil.getAccessManager();
        acm.setStateListForReports(session);
        User user=appUtil.getUser(session);       
        
        DaoUtil util=new DaoUtil();
        PartnersDao pdao=new PartnersDaoImpl();
        
        List lgaList=new ArrayList();
        List cboList=new ArrayList();
        List wardList=new ArrayList();
        List list=new ArrayList();
        List stateList=new ArrayList();
        List partnerList=new ArrayList();
        partnerList=pdao.getAllPartners();
        int startMth=gclrf.getStartMth();
        int endMth=gclrf.getEndMth();
        int startYear=gclrf.getStartYear();
        int endYear=gclrf.getEndYear();
        String id=request.getParameter("id");
        if(id !=null)
        requiredAction=id;
        List orgUnitGroupList=new ArrayList();
        if(lgaCode ==null || lgaCode.equalsIgnoreCase("all"))
        {
            orgUnitGroupList=ReportUtility.getOrgUnitGroup(stateCode);
            //System.err.println("orgUnitGroupList size is "+orgUnitGroupList.size());
        }
        session.setAttribute("ssorgUnitGroupList", orgUnitGroupList);
        session.setAttribute("partnerList", partnerList);

        if(requiredAction==null)
        {
            gclrf.reset(mapping, request);
            
            session.setAttribute("gclLgaList",lgaList);
            session.setAttribute("gclOrgList", cboList);
            session.setAttribute("gclwardList", wardList);
            return mapping.findForward("paraPage");
        }
        else if(requiredAction.equals("allLga"))
        {
            session.setAttribute("gclLgaList",lgaList);
            session.setAttribute("gclOrgList", cboList);
            session.setAttribute("gclwardList", wardList);
            if(orgUnitGroupId==null || orgUnitGroupId.equalsIgnoreCase("All"))
            generateLgaList(stateCode,request);
            return mapping.findForward("paraPage");
        }
        else if(requiredAction.equals("lga"))
        {
            generateLgaList(stateCode,request);
            
            gclrf.reset(mapping, request);
            return mapping.findForward("paraPage");
        }
        else if(requiredAction.equals("cbo"))
        {
            List orgList =new ArrayList();
            if(user !=null)
            orgList =acm.getListOfCBOsAssignedToUser(user,stateCode,lgaCode);
            session.setAttribute("gclOrgList", orgList);
            gclrf.reset(mapping, request);
            return mapping.findForward("paraPage");
        }
        else if(requiredAction.equals("wardList"))
        {
             session.removeAttribute("summaryStatistics");
            if(user !=null)
            wardList =acm.getListOfCommunitiesAssignedToUserByCBO(user,stateCode,lgaCode,orgCode);
            session.setAttribute("gclwardList", wardList);
            gclrf.reset(mapping, request);
            return mapping.findForward("paraPage");
        }
        else if(requiredAction.equals("gclReportList"))
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
            String partnerCode=gclrf.getPartnerCode();
            String partnerName=util.getPartnerName(partnerCode);
            String param[]={stateCode,lgaCode,orgCode,wardCode,lgaName,cboName,startMth+"",startYear+"",endMth+"",endYear+"",partnerCode,wardName,stateName,partnerName};
            String additionalQueryCriteria=util.getHVIOrgUnitQuery(param);
            String periodQuery=util.getGraduationCheckListPeriodQuery(startMth, startYear, endMth, endYear);
            GraduationCheckListDao gcldao=new GraduationCheckListDaoImpl();
            List reportList=gcldao.getGraduationCheckLists(additionalQueryCriteria+periodQuery);
            String period=util.getMonthAsString(startMth)+" "+startYear+" to "+util.getMonthAsString(endMth)+" "+endYear;
            request.setAttribute("gclList", reportList);
            request.setAttribute("gclorgparams", param);
            request.setAttribute("gclperiod", period);
            gclrf.reset(mapping, request);
            return mapping.findForward("reportPage");
        }
        
        gclrf.reset(mapping, request);
        return mapping.findForward("paraPage");
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
            session.setAttribute("gclLgaList", lgaList);
            session.setAttribute("gclOrgList", new ArrayList());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
