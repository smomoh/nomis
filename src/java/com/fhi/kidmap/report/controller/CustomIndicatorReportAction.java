/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report.controller;

import com.fhi.kidmap.business.Partners;
import com.fhi.kidmap.business.User;
import com.fhi.kidmap.dao.CustomIndicatorsReportDao;
import com.fhi.kidmap.dao.CustomIndicatorsReportDaoImpl;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.nomis.nomisutils.AccessManager;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.nomis.nomisutils.DateManager;
import com.fhi.nomis.nomisutils.ExcelWriter;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jxl.write.WritableWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author smomoh
 */
public class CustomIndicatorReportAction extends org.apache.struts.action.Action {

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
        CustomIndicatorReportForm cirform=(CustomIndicatorReportForm)form;
        HttpSession session=request.getSession();
        CustomIndicatorsReportDao cirdao=new CustomIndicatorsReportDaoImpl();
        AppUtility appUtil=new AppUtility();
        DaoUtil util=new DaoUtil();
        AccessManager acm=new AccessManager();
        String currentUser=appUtil.getCurrentUser(session);
        if(currentUser==null)
        return mapping.findForward("login");
        User user=util.getUserDaoInstance().getUser(currentUser);
        setPartnerList(user,session);
        
        String requiredAction=cirform.getActionName();
        String period=cirform.getPeriod();
        String[] selectedStates=cirform.getStates();
        
        List periodList=cirdao.getDistinctPeriods();
        if(periodList==null)
        periodList=new ArrayList();
        session.setAttribute("cirperiodList", periodList);
        
        String partnerCode=cirform.getPartnerCode();
        List stateList=cirdao.getDistinctStatesByPartner(partnerCode);//getDistinctStates();
        if(period !=null && !period.equalsIgnoreCase("select"))
        stateList=cirdao.getDistinctStatesByPeriodAndPartner(partnerCode,period);
        if(stateList==null)
        stateList=new ArrayList();
        session.setAttribute("cirstateList",stateList);
        
        List yearList=DateManager.generateYears();
        session.setAttribute("yearList", yearList);
        
        /*String partnerCode=cirform.getPartnerCode();
        List selectedPartnerList=new ArrayList();
        if(partnerCode==null || partnerCode.equalsIgnoreCase("All"))
        selectedPartnerList=acm.getPartnerListForReports(session);
        else
        {
            Partners partner=util.getPartnerDaoInstance().getPartner(partnerCode);
            if(partner !=null)
            selectedPartnerList.add(partner);
        }
        acm.setStateListForReportsByPartnerCode(session,selectedPartnerList);*/
        /*AccessManager acm=appUtil.getAccessManager();
        List partnerList=acm.getPartnerListForReports(session);
        session.setAttribute("partnerList", partnerList);*/
        
        if(requiredAction==null)
        {
            return mapping.findForward("paramPage");
        }
        else if(requiredAction.equalsIgnoreCase("stateList"))
        {
            return mapping.findForward("paramPage");
        }
        else if(requiredAction.equalsIgnoreCase("downloadReport"))
        {
            try
                {
                    String fileName="Custom_Indicators_multiple"+DateManager.getCurrentDate();
                    
                    List mainList=new ArrayList();
                    if(selectedStates !=null && selectedStates.length>0)
                    {
                        String stateName=null;
                        List list=null;
                        if(selectedStates.length==1)
                        {
                            stateName=selectedStates[0];
                            stateName=stateName.replaceAll(" ", "_");
                            fileName="Custom_Indicators_"+stateName+DateManager.getCurrentDate();
                        }
                        
                        for(int j=0; j<selectedStates.length; j++)
                        {
                           stateName=selectedStates[j];
                           list=cirdao.getReportTemplatesByStatePartnerAndPeriod(stateName,partnerCode,period);
                           if(list !=null)
                           mainList.add(list);
                           System.err.println("Custom_Indicators size is "+mainList.size()); 
                        }
                        
                    }  
                    
                    response.setContentType("application/vnd.ms-excel");
                    response.setHeader("Content-Disposition", "attachment; filename="+fileName+".xls");
                    OutputStream os=response.getOutputStream();
                    ExcelWriter ew=new ExcelWriter();
                    WritableWorkbook wworkbook=ew.writeQuarterlyReportToExcel(os, mainList) ;
                    //WritableWorkbook wworkbook=ew.writeRevisedQuarterlyReportTemplateToExcel(os, mainList) ;
                    
                    if(wworkbook !=null)
                    {
                        wworkbook.write();
                        wworkbook.close();
                    }
                    os.close();
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            return mapping.findForward("paramPage");
        }
        return mapping.findForward("paramPage");
    }
    private void setPartnerList(User user,HttpSession session)
    {
        String partnerCodes=null;
        DaoUtil util=new DaoUtil();
        try
        {
            if(user !=null)
            {
                List partnerList=new ArrayList();
                if(user.isSuperUser())
                {
                   partnerList=util.getPartnerDaoInstance().getAllPartners(); 
                }
                else
                {
                    partnerCodes=user.getPartnerCodes();
                    if(partnerCodes !=null)
                    {
                        String[] partnerCodeArray=partnerCodes.split(",");
                        if(partnerCodeArray !=null)
                        {
                            Partners partner=null;
                            for(int i=0; i<partnerCodeArray.length; i++)
                            {
                                partner=util.getPartnerDaoInstance().getPartner(partnerCodeArray[i]);
                                partnerList.add(partner);
                            }
                        }
                    }
                }
                session.setAttribute("userAssignedPartners", partnerList);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
