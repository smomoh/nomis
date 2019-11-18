/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.report.controller;

import com.fhi.nomis.nomisutils.LoadUpInfo;
import com.fhi.kidmap.business.OvcWithdrawal;
import com.fhi.kidmap.business.User;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.OvcWithdrawalDao;
import com.fhi.kidmap.dao.OvcWithdrawalDaoImpl;
import com.fhi.kidmap.dao.PartnersDao;
import com.fhi.kidmap.dao.PartnersDaoImpl;
import com.fhi.kidmap.report.ReportUtility;
import com.fhi.kidmap.report.ServiceRecords;
import com.fhi.nomis.nomisutils.AccessManager;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.nomis.nomisutils.DateManager;
import com.nomis.databasemanagement.DatabaseCleanup;
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
 * @author COMPAQ USER
 */
public class OvcWithdrawalReportAction extends org.apache.struts.action.Action {
    
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
            throws Exception {

        OvcWithdrawalReportForm reportForm=(OvcWithdrawalReportForm)form;
        OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl();
        //AjaxProcessor aproc=new AjaxProcessor();
        ServiceRecords records=new ServiceRecords();
        PartnersDao pdao=new PartnersDaoImpl();
        AppUtility appUtil=new AppUtility();
        DaoUtil util=new DaoUtil();
        HttpSession session=request.getSession();
        List lgaList=new ArrayList();
        List cboList=new ArrayList();
        AccessManager acm=appUtil.getAccessManager();
        acm.setStateListForReports(session);
        User user=appUtil.getUser(session);
        //List wardList=new ArrayList();
        String partnerCode=reportForm.getPartnerCode();
        String type=reportForm.getType();
        List paramList=new ArrayList();
        String requiredAction;

        String stateCode=reportForm.getState();
        String lgaCode=reportForm.getLga();
        String orgUnitGroupId=reportForm.getOrgUnitGroupId();
        String cboCode=reportForm.getCbo();
        String wardName="All";
        String reasonWithdrawn=reportForm.getReasonWithdrawn();
        int month=reportForm.getServiceMonth();
        int year=reportForm.getServiceYear();
        int month2=reportForm.getServiceMonth2();
        int year2=reportForm.getServiceYear2();
        List yearList=DateManager.generateYears();
        session.setAttribute("yearList", yearList);

        requiredAction=reportForm.getActionName();

        List partnerList=pdao.getAllPartners();
        session.setAttribute("partnerList", partnerList);
        session.removeAttribute("withdrwalList");
        List withdrawalTypeList=wdao.getDistinctWithdrawalTypes();
        request.setAttribute("withdrawalTypeList", withdrawalTypeList);
        List withdrawalReasonList=wdao.getDistinctReasonForWithdrwal();
        request.setAttribute("withdrawalReasonList", withdrawalReasonList);
        List orgUnitGroupList=new ArrayList();
        if(lgaCode ==null || lgaCode.equalsIgnoreCase("all"))
        {
            orgUnitGroupList=ReportUtility.getOrgUnitGroup(stateCode);
        }
        session.setAttribute("ssorgUnitGroupList", orgUnitGroupList);
        String ovcId=request.getParameter("ovcId");
        String actionName=request.getParameter("an");
        if(actionName !=null && ovcId !=null)
        requiredAction=actionName;
        //System.err.println("ovcId in withdrawalAction is "+ovcId+" "+actionName);
        if(requiredAction==null)
        {
            reportForm.reset(mapping, request);
            DatabaseCleanup dbcleanup=new DatabaseCleanup();
            dbcleanup.setAppropriateWithdrawalType();
            LoadUpInfo loadup=new LoadUpInfo();
            //loadup.getAllStates(session);
            loadup.setParamAttributes(request);
        }
        else if(requiredAction.equals("allLga"))
        {
            session.setAttribute("lgaList",lgaList);
            session.setAttribute("orgList", cboList);
            //session.setAttribute("wardList", wardList);
            if(orgUnitGroupId==null || orgUnitGroupId.equalsIgnoreCase("All"))
            generateLgaList(stateCode,request);
            //return mapping.findForward("paramPage");
        }
        else if(requiredAction.equals("lga"))
        {
            generateLgaList(stateCode,request);
        }
        else if(requiredAction.equals("cbo"))
        {
            List orgList =new ArrayList();//util.getUserAssignedCBOs(stateCode,lgaCode,session);
            if(user !=null)
            orgList =acm.getListOfCBOsAssignedToUser(user,stateCode,lgaCode);
            
            session.setAttribute("orgList", orgList);
            session.setAttribute("wardList",new ArrayList());
        }
        else if(requiredAction.equals("withdrawalList"))
        {
            if(orgUnitGroupId !=null && !orgUnitGroupId.equalsIgnoreCase("all"))
            {
                lgaCode="orgUnitGroup";
                cboCode=orgUnitGroupId;
            }
            paramList.add(stateCode);
            paramList.add(lgaCode);
            paramList.add(cboCode);
            paramList.add(wardName);
            paramList.add(month);
            paramList.add(year);
            paramList.add(month2);
            paramList.add(year2);
            paramList.add(util.getPartnerName(partnerCode));
            paramList.add(partnerCode);
            paramList.add(type);
            paramList.add(reasonWithdrawn);
            session.setAttribute("withdrawalParamList", paramList);
            records.generateWithdrawalList(session,paramList);
            reportForm.reset(mapping, request);
        }
        else if(requiredAction.equalsIgnoreCase("remove"))
        {
            OvcWithdrawal withdrawal=new OvcWithdrawal();
            withdrawal.setOvcId(ovcId);
            wdao.deleteOvcWithdrawal(withdrawal);
        }
        return mapping.findForward(SUCCESS);
    }
    private void generateLgaList(String stateCode,HttpServletRequest request)
    {
        //DaoUtil util=new DaoUtil();
        HttpSession session=request.getSession();
        AppUtility appUtil=new AppUtility();
        User user=appUtil.getUser(request.getSession());
        AccessManager acm=new AccessManager();
        List lgaList =acm.getListOfLgasAssignedToUser(user,stateCode);//til.getUserAssignedLgas(stateCode,session);
        session.setAttribute("lgaList", lgaList);
        session.setAttribute("orgList", new ArrayList());
    }
}
