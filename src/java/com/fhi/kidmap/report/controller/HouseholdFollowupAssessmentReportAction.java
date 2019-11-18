/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report.controller;

import com.fhi.kidmap.business.User;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.PartnersDao;
import com.fhi.kidmap.dao.PartnersDaoImpl;
import com.fhi.kidmap.report.HouseholdReports;
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
public class HouseholdFollowupAssessmentReportAction extends org.apache.struts.action.Action {

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
            throws Exception {
        HouseholdFollowupAssessmentReportForm hviForm=(HouseholdFollowupAssessmentReportForm)form;

        String requiredAction=hviForm.getActionName();
        String stateCode=hviForm.getState();
        String lgaCode=hviForm.getLga();
        String orgCode=hviForm.getOrganization();
        String wardCode=hviForm.getWard();
        
        HttpSession session=request.getSession();
        AppUtility appUtil=new AppUtility();
        User user=appUtil.getUser(session);
        AccessManager acm=new AccessManager();
        acm.setStateListForReports(session);
        DaoUtil util=new DaoUtil();
        PartnersDao pdao=new PartnersDaoImpl();
        
        List lgaList=new ArrayList();
        List cboList=new ArrayList();
        List wardList=new ArrayList();
        List list=new ArrayList();
        List stateList=new ArrayList();
        List partnerList=new ArrayList();
        partnerList=pdao.getAllPartners();
        int startMth=hviForm.getStartMth();
        int endMth=hviForm.getEndMth();
        int startYear=hviForm.getStartYear();
        int endYear=hviForm.getEndYear();
        
        session.setAttribute("partnerList", partnerList);
        List yearList=DateManager.generateYears();
        session.setAttribute("yearList", yearList);

        if(requiredAction==null)
        {
            hviForm.reset(mapping, request);
            
            /*list=hviDao.getHVIStateList();
            for(Object obj:list)
            {
                States state=sDao.getStateByStateCode(obj.toString());
                if(state !=null)
                stateList.add(state);
            }*/
            stateList=util.getUserAssignedStates(session);
            session.setAttribute("hviStateList",stateList);
            session.setAttribute("hviLgaList",lgaList);
            session.setAttribute("hviOrgList", cboList);
            session.setAttribute("hviwardList", wardList);
            return mapping.findForward("paramPage");
        }

        else if(requiredAction.equals("lga"))
        {
            if(user !=null)
            lgaList =acm.getListOfLgasAssignedToUser(user,stateCode);
            session.setAttribute("hviLgaList", lgaList);
            session.setAttribute("hviOrgList", cboList);
            hviForm.reset(mapping, request);
            return mapping.findForward("paramPage");
        }
        else if(requiredAction.equals("cbo"))
        {
            List orgList =new ArrayList();
            if(user !=null)
            orgList =acm.getListOfCBOsAssignedToUser(user,stateCode,lgaCode);
            session.setAttribute("hviOrgList", orgList);
            hviForm.reset(mapping, request);
            return mapping.findForward("paramPage");
        }
        else if(requiredAction.equals("wardList"))
        {
            if(user !=null)
            wardList =acm.getListOfCommunitiesAssignedToUserByCBO(user,stateCode,lgaCode,orgCode);
            session.setAttribute("hviwardList", wardList);
            hviForm.reset(mapping, request);
            return mapping.findForward("paramPage");
        }
        else if(requiredAction.equals("hviReportList"))
        {
            String stateName=util.getStateName(stateCode);
            String lgaName=util.getLgaName(lgaCode);
            String cboName=util.getOrganizationName(orgCode);
            String wardName=util.getWardName(wardCode);
            String partnerCode=hviForm.getPartnerCode();
            String partnerName=util.getPartnerName(partnerCode);
            String[] param={stateCode,lgaCode,orgCode,wardCode,lgaName,cboName,startMth+"",startYear+"",endMth+"",endYear+"",partnerCode,wardName,stateName,partnerName};
            HouseholdReports hhr=new HouseholdReports();
            hhr.getHhFollowupAssessmentRecords(request,param);
            hviForm.reset(mapping, request);
        }
        hviForm.reset(mapping, request);
        return mapping.findForward(SUCCESS);
    }
}
