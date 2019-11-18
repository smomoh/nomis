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
import com.fhi.kidmap.report.ReportUtility;
import com.fhi.nomis.nomisutils.AccessManager;
import com.fhi.nomis.nomisutils.AppUtility;
import com.nomis.upgrade.UpgradeManager;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author smomoh
 */
public class OvcMthlySummaryAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
   private static final String SUCCESS = "success";
    private static final String FAILURE = "failure";

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
        OvcMthlySummaryForm reportForm=(OvcMthlySummaryForm)form;
        //AjaxProcessor aproc=new AjaxProcessor();
        OvcRecords records=new OvcRecords();
        //LgaDao dao = new LgaDaoImpl();
        PartnersDao pdao=new PartnersDaoImpl();
        DaoUtil util=new DaoUtil();
        HttpSession session=request.getSession();
        AppUtility appUtil=new AppUtility();
        User user=appUtil.getUser(session);
        AccessManager acm=new AccessManager();
        acm.setStateListForReports(session);
        List lgaList=new ArrayList();
        
        List paramList=new ArrayList();
        String requiredAction;
        String stateCode=reportForm.getState();
        String orgUnitGroupId=reportForm.getOrgUnitGroupId();
        String lgaCode=reportForm.getLga();
        String cboCode=reportForm.getCbo();
        String wardCode="All";
        int startMonth=reportForm.getEnroll_month();
        int startYear=reportForm.getEnroll_year();
        int endMonth=reportForm.getEnroll_month2();
        int endYear=reportForm.getEnroll_year2();
        String partnerCode=reportForm.getPartnerCode();
        String ageSegType=reportForm.getReportType();
        String periodSelected=reportForm.getPeriodChkBox();
        List orgUnitGroupList=new ArrayList();
        if(lgaCode ==null || lgaCode.equalsIgnoreCase("all"))
        {
            orgUnitGroupList=ReportUtility.getOrgUnitGroup(stateCode);
        }
        session.setAttribute("ssorgUnitGroupList", orgUnitGroupList);
        if(periodSelected==null)
        {
            endMonth=startMonth;
            endYear=startYear;
        }

        LoadUpInfo loadup=new LoadUpInfo();
        loadup.getAllStates(session);
        loadup.setParamAttributes(request);
        
        requiredAction=reportForm.getActionName();
        session.removeAttribute("mthSummarySessionObj");
        List partnerList=pdao.getAllPartners();
        session.setAttribute("partnerList", partnerList);
        if(requiredAction==null)
        {
            UpgradeManager cm=new UpgradeManager();
            cm.updateHouseholdServiceForCompatibility();
            reportForm.reset(mapping, request);
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equals("allLga"))
        {
            List emptyList=new ArrayList();
            session.setAttribute("lgaList", emptyList);
            session.setAttribute("MthlySummOrgList", emptyList);
            
            if(orgUnitGroupId==null || orgUnitGroupId.equalsIgnoreCase("All"))
            generateLgaList(stateCode,request);
        }
        else if(requiredAction.equals("lga"))
        {
            if(user !=null)
            lgaList =acm.getListOfLgasAssignedToUser(user,stateCode);
            session.setAttribute("lgaList", lgaList);
            session.setAttribute("MthlySummOrgList", new ArrayList());
            
        }
        else if(requiredAction.equals("cbo"))
        {
            List orgList =new ArrayList();
            if(user !=null)
            orgList =acm.getListOfCBOsAssignedToUser(user,stateCode,lgaCode);
            session.setAttribute("MthlySummOrgList", orgList);
        }
        else if(requiredAction.equals("monthlysummary"))
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
            paramList.add(startMonth);
            paramList.add(startYear);
            paramList.add(endMonth);
            paramList.add(endYear);
            paramList.add(partnerCode);
            records.getOvcMthlySummaryData(session, paramList,ageSegType);
            if(ageSegType.equalsIgnoreCase("new"))
            return mapping.findForward("newReport");    
        }
        reportForm.reset(mapping, request);
        return mapping.findForward(SUCCESS);
    }
    private void generateLgaList(String stateCode,HttpServletRequest request)
    {
        AppUtility appUtil=new AppUtility();
        AccessManager acm=new AccessManager();
        User user=appUtil.getUser(request.getSession());
        List emptyList=new ArrayList();
        HttpSession session=request.getSession();
        List lgaList=new ArrayList();
        if(user !=null)
        lgaList =acm.getListOfLgasAssignedToUser(user,stateCode);
        session.setAttribute("lgaList", lgaList);
        session.setAttribute("MthlySummOrgList", emptyList);
    }
}
