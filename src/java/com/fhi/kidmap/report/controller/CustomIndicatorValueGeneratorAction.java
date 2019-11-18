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
import com.fhi.kidmap.report.CustomIndicatorsReportManager;
import com.fhi.nomis.logs.NomisLogManager;
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
public class CustomIndicatorValueGeneratorAction extends org.apache.struts.action.Action {

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
        CustomIndicatorValueGeneratorForm civgform=(CustomIndicatorValueGeneratorForm)form;
        AppUtility appUtil=new AppUtility();
        DaoUtil util=new DaoUtil();
        DateManager dm=new DateManager();
        HttpSession session=request.getSession();
        AccessManager acm=new AccessManager();
        
        String partnerCode=civgform.getPartnerCode();
        
        String userName=appUtil.getCurrentUser(session);
        
        if(!acm.isUserInRole("cirb", userName))
        session.setAttribute("cirbGenButtonDisabled", "true");
        else
        session.setAttribute("cirbGenButtonDisabled", "false");
        /*List selectedPartnerList=new ArrayList();
        if(partnerCode==null || partnerCode.trim().length()==0 || partnerCode.equalsIgnoreCase("All"))
        {
            selectedPartnerList=acm.getPartnerListForReports(session);
            acm.setStateListForReports(session);
        }
        else
        {
            Partners partner=util.getPartnerDaoInstance().getPartner(partnerCode);
            if(partner !=null)
            selectedPartnerList.add(partner);
            acm.setStateListForReportsByPartnerCode(session,selectedPartnerList);
        }*/
        
        acm.setStateListForReports(session);
        
        int startMth=appUtil.getMonthAsNumber(civgform.getStartMth());
        int startYear=civgform.getStartYear();
        int endMth=appUtil.getMonthAsNumber(civgform.getEndMth());
        int endYear=civgform.getEndYear();
        //String[] dateParams={startMth+"",startYear+"",endMth+"",endYear+""};
        //String startDate=util.getStartDate(dateParams);
        //String endDate=util.getEndDate(dateParams);
        String[] selectedStates=civgform.getStates();
        String[] selectedIndicators=civgform.getIndicators();
        
        String requiredAction=civgform.getActionName();
        List listOfMonths=dm.generateMonths(1);
        List listOfYears=DateManager.generateYears();
        session.setAttribute("generatedYears", listOfYears);
        session.setAttribute("generatedMonths", listOfMonths);
        String currentUser=appUtil.getCurrentUser(session);
        if(currentUser==null)
        return mapping.findForward("login");
        User user=util.getUserDaoInstance().getUser(currentUser);
        setPartnerList(user,session);
        List indicatorList=CustomIndicatorsReportManager.getCustomIndicators();
        session.setAttribute("customIndicatorList", indicatorList);
        /*List partnerList=acm.getPartnerListForReports(session);
        session.setAttribute("partnerList", partnerList);*/
        
        if(requiredAction==null)
        {
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("stateList"))
        {
            
        }
        else if(requiredAction.equalsIgnoreCase("deleteData"))
        {
            String stateCode=null;
            int count=0;
            String stateName=null;
            CustomIndicatorsReportDao cirbdao=new CustomIndicatorsReportDaoImpl();
            for(int j=0; j<selectedStates.length; j++)
            {
               stateCode=selectedStates[j];
               stateName=util.getStateName(stateCode);
               count+=cirbdao.deleteAllReportTemplates(stateName);
            }
            System.err.println(count+" deleted");
        }
        else if(requiredAction.equalsIgnoreCase("generateData"))
        {
            try
                {
                    CustomIndicatorsReportManager cirm=new CustomIndicatorsReportManager();
                    List mainList=new ArrayList();
                    String stateCode=null;
                    String lgaCode="All";
                    String cboCode="All";
                    List listFromArray=getListFromArray(selectedIndicators);
                    if(selectedStates !=null && selectedStates.length>0)
                    {
                        List paramList=getParamList(stateCode,lgaCode,cboCode,startMth,startYear,endMth,endYear,partnerCode);
                        //OvcQuarterlyReport quarterlyReport=new OvcQuarterlyReport();
                        List lgaList=null;
                        List partnerCodeList=null;
                        List list=null;
                        if(selectedStates.length==1)
                        {
                            stateCode=selectedStates[0];
                            paramList.set(0, stateCode);
                            String stateName=util.getStateName(stateCode);
                            if(stateName !=null)
                            stateName=stateName.replaceAll(" ", "_");
                            partnerCodeList=util.getHouseholdEnrollmentDaoInstance().getDistinctPartnerCodesByState(stateCode);
                            if(partnerCodeList !=null)
                            {
                                for(Object s:partnerCodeList)
                                {
                                    partnerCode=(String)s;
                                    lgaList=util.getHouseholdEnrollmentDaoInstance().getDistinctLgaCodesByStateAndPartnerCodes(stateCode,partnerCode);
                                    for(int i=0; i<lgaList.size(); i++)
                                    {
                                      lgaCode=(String)lgaList.get(i);
                                      paramList.set(1, lgaCode);
                                      paramList.set(8, partnerCode);
                                      cirm.processCustomIndicatorsByLga(paramList,listFromArray,userName);
                                      //mainList.add(quarterlyReport.getQuarterlyReport(paramList, stateCode)); 
                                    }
                                }
                            }
                        }
                        else
                        {
                            for(int j=0; j<selectedStates.length; j++)
                            {
                               stateCode=selectedStates[j];
                               partnerCodeList=util.getHouseholdEnrollmentDaoInstance().getDistinctPartnerCodesByState(stateCode);
                                if(partnerCodeList !=null)
                                {
                                    for(Object s:partnerCodeList)
                                    {
                                        partnerCode=(String)s;
                                        paramList.set(0, stateCode);
                                        paramList.set(8, partnerCode);
                                        lgaList=util.getHouseholdEnrollmentDaoInstance().getDistinctLgaCodesByStateAndPartnerCodes(stateCode,partnerCode);
                                        for(int i=0; i<lgaList.size(); i++)
                                        {
                                          lgaCode=(String)lgaList.get(i);
                                          paramList.set(1, lgaCode);
                                          cirm.processCustomIndicatorsByLga(paramList,listFromArray,userName);
                                          //mainList.add(quarterlyReport.getQuarterlyReport(paramList, stateCode)); 
                                        }
                                       System.err.println("Quaterly_Report size is "+mainList.size());
                                    }
                                }
                            }
                        }
                    }
                }
                catch(Exception ex)
                {
                    NomisLogManager.logStackTrace(ex);
                    //ex.printStackTrace();
                }
                civgform.reset(mapping, request);
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
    private List getListFromArray(String[] selectedIndicators)
    {
        List list=new ArrayList();
        if(selectedIndicators !=null)
        {
            for(int i=0; i<selectedIndicators.length; i++)
            {
                list.add(selectedIndicators[i]);
            }
        }
        return list;
    }
}
