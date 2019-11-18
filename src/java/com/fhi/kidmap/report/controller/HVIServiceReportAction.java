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
import com.nomis.upgrade.UpgradeManager;
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
public class HVIServiceReportAction extends org.apache.struts.action.Action {

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
        
        HVIServiceReportForm hviForm=(HVIServiceReportForm)form;
        
        String requiredAction=hviForm.getActionName();
        String stateCode=hviForm.getState();
        String lgaCode=hviForm.getLga();
        String orgCode=hviForm.getOrganization();
        String wardCode=hviForm.getWard();
        HttpSession session=request.getSession();
        AppUtility appUtil=new AppUtility();
        User user=appUtil.getUser(session);
        AccessManager acm=new AccessManager();
        
        DaoUtil util=new DaoUtil();
        PartnersDao pdao=new PartnersDaoImpl();
        
        List lgaList=new ArrayList();
        List cboList=new ArrayList();
        List wardList=new ArrayList();
        //List list=new ArrayList();
        //List stateList=new ArrayList();
        List partnerList=new ArrayList();
        partnerList=pdao.getAllPartners();
        int startMth=hviForm.getStartMth();
        int endMth=hviForm.getEndMth();
        int startYear=hviForm.getStartYear();
        int endYear=hviForm.getEndYear();
        String reportFilter=hviForm.getReportFilter();
        session.setAttribute("partnerList", partnerList);
        acm.setStateListForReports(session);
        
        System.err.println("reportFilter is "+reportFilter);
        if(requiredAction==null)
        {
            UpgradeManager cm=new UpgradeManager();
            cm.updateHouseholdServiceForCompatibility();
            
            session.setAttribute("hvisLgaList",lgaList);
            session.setAttribute("hvisOrgList", cboList);
            session.setAttribute("hhswardList", wardList);
            
            return mapping.findForward("paramPage");
        }

        else if(requiredAction.equals("lga"))
        {
            if(user !=null)
            lgaList =acm.getListOfLgasAssignedToUser(user,stateCode);
            session.setAttribute("hvisLgaList", lgaList);
            session.setAttribute("hvisOrgList", cboList);
                       
            return mapping.findForward("paramPage");
        }
        else if(requiredAction.equals("cbo"))
        {
            List orgList =new ArrayList();
            if(user !=null)
            orgList =acm.getListOfCBOsAssignedToUser(user,stateCode,lgaCode);
            session.setAttribute("hvisOrgList", orgList);
            session.setAttribute("hhswardList", wardList);
            return mapping.findForward("paramPage");
        }
        else if(requiredAction.equals("wardList"))
        {
            if(user !=null)
            wardList =acm.getListOfCommunitiesAssignedToUserByCBO(user,stateCode,lgaCode,orgCode);
            session.setAttribute("hhswardList", wardList);
            return mapping.findForward("paramPage");
        }
        else if(requiredAction.equals("hvisReportList"))
        {
            String stateName=util.getStateName(stateCode);
            String lgaName=util.getLgaName(lgaCode);
            String cboName=util.getOrganizationName(orgCode);
            String wardName=util.getWardName(wardCode);
            String partnerCode=hviForm.getPartnerCode();
            String partnerName=util.getPartnerName(partnerCode);
            String filter=reportFilter;
            if(reportFilter !=null && reportFilter.equalsIgnoreCase("specialService"))
            reportFilter="All";
            String param[]={stateCode,lgaCode,orgCode,wardCode,lgaName,cboName,startMth+"",startYear+"",endMth+"",endYear+"",partnerCode,wardName,stateName,partnerName,reportFilter};
            HouseholdReports hhr=new HouseholdReports();
            hhr.getHVIServiceRecords(request,param);
            if(filter !=null && filter.equals("specialService"))
            {
                return mapping.findForward("specialReport");
            }
        }
        return mapping.findForward(SUCCESS);
    }
}
