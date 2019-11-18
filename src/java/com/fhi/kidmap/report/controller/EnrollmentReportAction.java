/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.report.controller;

import com.fhi.kidmap.business.User;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.nomis.nomisutils.ExcelWriter;
import com.fhi.nomis.nomisutils.LoadUpInfo;
import com.fhi.kidmap.dao.PartnersDao;
import com.fhi.kidmap.dao.PartnersDaoImpl;
import com.fhi.kidmap.report.OvcRecords;
import com.fhi.nomis.nomisutils.AccessManager;
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
public class EnrollmentReportAction extends org.apache.struts.action.Action {
    
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
        EnrollmentReportForm reportForm=(EnrollmentReportForm)form;
        //AjaxProcessor aproc=new AjaxProcessor();
        OvcRecords records=new OvcRecords();
        //LgaDao dao = new LgaDaoImpl();
        PartnersDao pdao=new PartnersDaoImpl();

        //DaoUtil util=new DaoUtil();
        AppUtility appUtil=new AppUtility();
        HttpSession session=request.getSession();
        User user=appUtil.getUser(session);
        AccessManager acm=new AccessManager();
        acm.setStateListForReports(session);
        List lgaList=new ArrayList();
        //List cboList=new ArrayList();
        List wardList=new ArrayList();
        List paramList=new ArrayList();
        String requiredAction;

        String stateCode=reportForm.getState();
        String lgaCode=reportForm.getLga();
        String cboCode=reportForm.getCbo();
        String wardCode=reportForm.getWard();
        String partnerCode=reportForm.getPartnerCode();
        int fmonth=reportForm.getEnroll_month();
        int fyear=reportForm.getEnroll_year();
        int smonth=reportForm.getEnroll_month2();
        int syear=reportForm.getEnroll_year2();
        String startAge=reportForm.getEnroll_startAge();
        String endAge=reportForm.getEnroll_endAge();
        requiredAction=reportForm.getActionName();
        String id=request.getParameter("id");
        if(id !=null)
        requiredAction=id;
        //session.removeAttribute("ovcRecords");
        LoadUpInfo loadup=new LoadUpInfo();
        loadup.getAllStates(session);
        loadup.setParamAttributes(request);
        List emptyList=new ArrayList();
        List partnerList=pdao.getAllPartners();
        session.setAttribute("partnerList", partnerList);
        List yearList=DateManager.generateYears();
        session.setAttribute("yearList", yearList);
        //System.err.println("requiredAction in enrollmentreportaction is "+requiredAction);
        if(requiredAction==null)
        {
            session.setAttribute("lgaList", emptyList);
            session.setAttribute("orgList", emptyList);
            session.setAttribute("wardList", emptyList);
            reportForm.reset(mapping, request);
            return mapping.findForward("critriaPage");
        }
        if(requiredAction.equals("lga"))
        {
            if(user !=null)
            lgaList =acm.getListOfLgasAssignedToUser(user,stateCode);
            //lgaList =util.getOrganizationalUnits(requiredAction,stateCode,lgaCode,cboCode,wardCode);
            session.setAttribute("lgaList", lgaList);
            session.setAttribute("orgList", emptyList);
            session.setAttribute("wardList", emptyList);
            return mapping.findForward("critriaPage");
        }
        else if(requiredAction.equals("cbo"))
        {
            List orgList =new ArrayList();
            if(user !=null)
            orgList =acm.getListOfCBOsAssignedToUser(user,stateCode,lgaCode);
            session.setAttribute("orgList", orgList);
            session.setAttribute("wardList", emptyList);
            return mapping.findForward("critriaPage");
        }
        else if(requiredAction.equals("ward"))
        {
            if(user !=null)
            wardList =acm.getListOfCommunitiesAssignedToUserByCBO(user,stateCode,lgaCode,cboCode);
            session.setAttribute("wardList", wardList);
            return mapping.findForward("critriaPage");
        }
        else if(requiredAction.equals("exceldownload"))
        {
            List ovcList=(List)session.getAttribute("ovcRecords");
            String msg="Report is empty. You may need to log out and login again.";
            if(ovcList !=null)
            {
                //System.err.println("ovcList size is "+ovcList.size());
                ExcelWriter ew=new ExcelWriter();
                ew.writeEnrollmentRecordsToExcel(ovcList);
                msg="report written to "+appUtil.getReportDirectory();
            }
            request.setAttribute("msg", msg);
            reportForm.reset(mapping, request);
            return mapping.findForward("exceldownload");
        }
        else if(requiredAction.equals("ovcList"))
        {
            paramList.add(stateCode);
            paramList.add(lgaCode);
            paramList.add(cboCode);
            paramList.add(wardCode);
            paramList.add(fmonth);
            paramList.add(fyear);
            paramList.add(smonth);
            paramList.add(syear);
            paramList.add(startAge);
            paramList.add(endAge);
            paramList.add(partnerCode);
            records.getOvcRecords(session, paramList);
            reportForm.reset(mapping, request);
        }
        reportForm.reset(mapping, request);
        return mapping.findForward(SUCCESS);
    }
}
