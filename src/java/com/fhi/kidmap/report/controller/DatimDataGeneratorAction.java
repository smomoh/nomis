/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report.controller;

import com.fhi.kidmap.business.User;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.DatimReportDao;
import com.fhi.kidmap.dao.DatimReportDaoImpl;
import com.fhi.kidmap.report.CustomReports;
import com.fhi.kidmap.report.ReportUtility;
import com.fhi.nomis.logs.NomisLogManager;
import com.fhi.nomis.nomisutils.AccessManager;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.nomis.nomisutils.DateManager;
import com.nomis.business.DatimReportTemplate_Mer1718;
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
public class DatimDataGeneratorAction extends org.apache.struts.action.Action {

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
        DatimDataGeneratorForm ddgf=(DatimDataGeneratorForm)form;
        AppUtility appUtil=new AppUtility();
        DaoUtil util=new DaoUtil();
        HttpSession session=request.getSession();
        AccessManager acm=new AccessManager();
        DateManager dm=new DateManager();      
        String userName=appUtil.getCurrentUser(session);
        
        if(!acm.isUserInRole("datr", userName))
        session.setAttribute("datimGenButtonDisabled", "true");
        else
        session.setAttribute("datimGenButtonDisabled", "false");
        acm.setStateListForReports(session);
        
        String requiredAction=ddgf.getActionName();
        
        int startMth=appUtil.getMonthAsNumber(ddgf.getStartMth());
        int startYear=ddgf.getStartYear();
        int endMth=appUtil.getMonthAsNumber(ddgf.getEndMth());
        int endYear=ddgf.getEndYear();
        
        String[] selectedStates=ddgf.getStates();
        
        List datimReportStateList=util.getStateListFromHhe();
        session.setAttribute("datimReportStateList", datimReportStateList);
        List listOfMonths=dm.generateMonths(1);
        List listOfYears=DateManager.generateYears();
        session.setAttribute("generatedYears", listOfYears);
        session.setAttribute("generatedMonths", listOfMonths);
        String currentUser=appUtil.getCurrentUser(session);
        if(currentUser==null)
        return mapping.findForward("login");
        User user=util.getUserDaoInstance().getUser(currentUser);
        
        if(requiredAction==null)
        {
            ddgf.reset(mapping, request);
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("deleteData"))
        {
            String stateCode=null;
            int count=0;
            String stateName=null;
            DatimReportDao drtdao=new DatimReportDaoImpl();
            for(int j=0; j<selectedStates.length; j++)
            {
               stateCode=selectedStates[j];
               stateName=util.getStateName(stateCode);
               count+=drtdao.deleteAllDatimReportTemplates(stateName);
            }
            ddgf.reset(mapping, request);
            System.err.println(count+" deleted");
        }
        else if(requiredAction.equalsIgnoreCase("generateData"))
        {
            try  
            {
                CustomReports customReport=new CustomReports();
                String stateCode=null;
                    
                List paramList=getParamList("All","All","All",startMth,startYear,endMth,endYear,"All");
                if(selectedStates !=null && selectedStates.length>0)
                {
                        List datimFormList=new ArrayList();
                    for(int j=0; j<selectedStates.length; j++)
                    {
                       stateCode=selectedStates[j];
                        //set the individual state code and get the datim forms
                        paramList.set(0, stateCode);
                        datimFormList=customReport.getDatimFormList(paramList);
                        customReport.saveDatimReportData(datimFormList,userName);
                    }
                }
             }
               catch(Exception ex)
                {
                    NomisLogManager.logStackTrace(ex);
                    //ex.printStackTrace();
                }
                ddgf.reset(mapping, request);
                return mapping.findForward(SUCCESS);
        }
        return mapping.findForward(SUCCESS);
    }
private List getParamList(String stateCode,String lgaCode,String cboCode,int startMth,int startYr,int endMth,int endYr,String partnerCode)
    {
        List paramList=new ArrayList();
        
        paramList.add(stateCode);
        paramList.add(lgaCode);
        paramList.add(cboCode);
        paramList.add("All");
        paramList.add(startMth);
        paramList.add(startYr);
        paramList.add(endMth);
        paramList.add(endYr);
        paramList.add(partnerCode);
        return paramList;
    }
}
