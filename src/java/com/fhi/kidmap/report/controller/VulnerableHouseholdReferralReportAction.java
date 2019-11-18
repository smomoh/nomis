/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report.controller;

import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.OvcReferralDao;
import com.fhi.kidmap.dao.OvcReferralDaoImpl;
import com.fhi.kidmap.dao.PartnersDao;
import com.fhi.kidmap.dao.PartnersDaoImpl;
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
public class VulnerableHouseholdReferralReportAction extends org.apache.struts.action.Action {

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
        VulnerableHouseholdReferralReportForm reportForm=(VulnerableHouseholdReferralReportForm)form;

        OvcReferralDao referralDao=new OvcReferralDaoImpl();
        DaoUtil util=new DaoUtil();
        PartnersDao pdao=new PartnersDaoImpl();
        HttpSession session=request.getSession();
        
        String requiredAction=reportForm.getActionName();
        String stateCode=reportForm.getState();
        String lgaCode=reportForm.getLga();
        String wardCode=reportForm.getWard();
        String orgCode=reportForm.getOrganization();
        String partnerCode=reportForm.getPartnerCode();
       
        List lgaList=new ArrayList();
        List cboList=new ArrayList();
        List wardList=new ArrayList();
        List partnerList=new ArrayList();
        partnerList=pdao.getAllPartners();
        int startMth=reportForm.getStartMth();
        int endMth=reportForm.getEndMth();
        int startYear=reportForm.getStartYear();
        int endYear=reportForm.getEndYear();
        session.setAttribute("partnerList", partnerList);
        List yearList=DateManager.generateYears();
        session.setAttribute("yearList", yearList);
        
        if(requiredAction==null)
        {
            List stateList=util.getUserAssignedStates(session);
            reportForm.setStateList(stateList);
            reportForm.setLgaList(lgaList);
            reportForm.setCboList(new ArrayList());
            reportForm.setWardList(new ArrayList());
            return mapping.findForward("paramPage");
        }

        else if(requiredAction.equals("lga"))
        {
            lgaList =util.getUserAssignedLgas(stateCode,session);
            reportForm.setLgaList(lgaList);
            reportForm.setCboList(new ArrayList());
            reportForm.setWardList(new ArrayList());
            return mapping.findForward("paramPage");
        }
        else if(requiredAction.equals("cbo"))
        {
            /*OrganizationRecords orgRecords=null;
            List orgList=new ArrayList();
            cboList =util.getOrganizationalUnits(requiredAction,stateCode,lgaCode,orgCode,null);
            for(Object org:cboList)
            {
                orgRecords=util.getOrganizationRecords(org.toString());
                orgList.add(orgRecords);
            }*/
            List orgList =util.getUserAssignedCBOs(stateCode,lgaCode,session);
            reportForm.setCboList(orgList);
            reportForm.setWardList(new ArrayList());
            return mapping.findForward("paramPage");
        }
        else if(requiredAction.equals("ward"))
        {
            wardList=util.getUserAssignedCommunities(stateCode,lgaCode,orgCode,session);
            /*Wards ward=null;
            List orgList=new ArrayList();
            for(Object org:wardList)
            {
                if(org==null || org.toString().indexOf("/")==-1)
                continue;
                ward=util.getWard(org.toString());
                orgList.add(ward);
            }*/
            reportForm.setWardList(wardList);
            return mapping.findForward("paramPage");
        }
        else if(requiredAction.equals("report"))
        {
            String stateName=util.getStateName(stateCode);
            String lgaName=util.getLgaName(lgaCode);
            String cboName=util.getOrganizationName(orgCode);
            String wardName=util.getWardName(wardCode);
            String partnerName=util.getPartnerName(partnerCode);
            
            String period=util.getMonthAsString(startMth)+" "+startYear+" to "+util.getMonthAsString(endMth)+" "+endYear;
            String[] param={stateCode,lgaCode,orgCode,wardCode,startMth+"",startYear+"",endMth+"",endYear+"",stateName,lgaName,cboName,partnerCode,partnerName,wardName};
            List referralList=referralDao.getVulnerableHouseholdReferralList(param);
            session.setAttribute("referralList", referralList);
            session.setAttribute("referralPeriod", period);
            session.setAttribute("referralParams", param);
        }
        
        return mapping.findForward(SUCCESS);
    }
}
