/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.report.controller;

import com.fhi.kidmap.business.User;
import com.fhi.nomis.nomisutils.LoadUpInfo;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.PartnersDao;
import com.fhi.kidmap.dao.PartnersDaoImpl;
import com.fhi.kidmap.report.OvcRecords;
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
 * @author smomoh
 */
public class OvcFollowupSurveyReportAction extends org.apache.struts.action.Action {
    
    /* forward name="success" path="" */
    private static final String REPORT = "report";
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
        OvcFollowupSurveyReportForm reportForm=(OvcFollowupSurveyReportForm)form;
        //OvcRecords records=new OvcRecords();
        DaoUtil util=new DaoUtil();
        PartnersDao pdao=new PartnersDaoImpl();
        AppUtility appUtil=new AppUtility();
        HttpSession session=request.getSession();
        User user=appUtil.getUser(session);
        AccessManager acm=appUtil.getAccessManager();
        acm.setStateListForReports(session);
        List lgaList=new ArrayList();
        //List cboList=new ArrayList();
        List wardList=new ArrayList();
        String stateCode=reportForm.getState();
        String lgaCode=reportForm.getLga();
        String cboCode=reportForm.getCbo();
        String wardCode=reportForm.getWard();
        String partnerCode=reportForm.getPartnerCode();
        int startMth=reportForm.getStartMth();
        int startYear=reportForm.getStartYear();
        int endMth=reportForm.getEndMth();
        int endYear=reportForm.getEndYear();

       String requiredAction=reportForm.getActionName();
       String ovcId=request.getParameter("req");
       if(ovcId !=null)
       requiredAction="csireport";
       //System.err.println("requestAction is "+requestAction);
       session.removeAttribute("caregiverlist");
       List partnerList=pdao.getAllPartners();
       session.setAttribute("partnerList", partnerList);
       session.setAttribute("caregiverwardList", new ArrayList());
       List yearList=DateManager.generateYears();
       session.setAttribute("yearList", yearList);
       LoadUpInfo loadup=new LoadUpInfo();
        loadup.getAllStates(session);
        loadup.setParamAttributes(request);
        
        List stateList=acm.getStateListForReports(session);
        reportForm.setStateList(stateList);
        if(requiredAction==null)
        {
            
            reportForm.setLgaList(lgaList);
            reportForm.setCboList(new ArrayList());
            reportForm.setWardList(new ArrayList());
            reportForm.reset(mapping, request);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equals("lga"))
        {
            List emptyList=new ArrayList();
            if(user !=null)
            lgaList =acm.getListOfLgasAssignedToUser(user,stateCode);
            reportForm.setLgaList(lgaList);
            reportForm.setCboList(new ArrayList());
            reportForm.setWardList(new ArrayList());
            reportForm.reset(mapping, request);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equals("cbo"))
        {
            List orgList =new ArrayList();
            if(user !=null)
            orgList =acm.getListOfCBOsAssignedToUser(user,stateCode,lgaCode);
            reportForm.setCboList(orgList);
            reportForm.setWardList(new ArrayList());
            reportForm.reset(mapping, request);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equals("ward"))
        {
           if(user !=null)
            wardList =acm.getListOfCommunitiesAssignedToUserByCBO(user,stateCode,lgaCode,cboCode);
            reportForm.setWardList(wardList);
            reportForm.reset(mapping, request);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equals("report"))
        {
            String stateName=util.getStateName(stateCode);
            String lgaName=util.getLgaName(lgaCode);
            String cboName=util.getOrganizationName(cboCode);
            String wardName=util.getWardName(wardCode);
            String partnerName=util.getPartnerName(partnerCode);
            String param[]={stateCode,lgaCode,cboCode,wardCode,startMth+"",startYear+"",endMth+"",endYear+"","0","30",partnerCode,lgaName,cboName,partnerName,stateName,wardName};
            OvcRecords records=new OvcRecords();
            records.getFollowupRecords(param,request);
            reportForm.reset(mapping, request);
        }
        else if(requiredAction.equals("csireport"))
        {
            OvcRecords records=new OvcRecords();
            records.getFollowupCsi(request,ovcId);
            reportForm.reset(mapping, request);
            return mapping.findForward("csireport");
        }
        return mapping.findForward(REPORT);
    }
}
