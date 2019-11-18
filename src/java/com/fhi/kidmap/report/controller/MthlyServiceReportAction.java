/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.report.controller;

import com.fhi.kidmap.business.User;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.PartnersDao;
import com.fhi.kidmap.dao.PartnersDaoImpl;
import com.fhi.kidmap.report.ServiceRecords;
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
public class MthlyServiceReportAction extends org.apache.struts.action.Action {
    
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
        MthlyServiceReportForm reportForm=(MthlyServiceReportForm)form;
        ServiceRecords records=new ServiceRecords();
        PartnersDao pdao=new PartnersDaoImpl();
        DaoUtil util=new DaoUtil();
        AppUtility appUtil=new AppUtility();
        HttpSession session=request.getSession();
        User user=appUtil.getUser(session);
        AccessManager acm=appUtil.getAccessManager();
        acm.setStateListForReports(session);
        
        
        List lgaList=new ArrayList();
        //List cboList=new ArrayList();
        List wardList=new ArrayList();
        List paramList=new ArrayList();
        

        String stateCode=reportForm.getState();
        String lgaCode=reportForm.getLga();
        String cboCode=reportForm.getCbo();
        String wardCode=reportForm.getWard();
        String partnerCode=reportForm.getPartnerCode();
        String reportFilter=reportForm.getReportFilter();
        
        
        int month=reportForm.getServiceMonth();
        int year=reportForm.getServiceYear();
        int month2=reportForm.getServiceMonth2();
        int year2=reportForm.getServiceYear2();
        
        int startAge=reportForm.getStartAge();
        int endAge=reportForm.getEndAge();
        String requiredAction=reportForm.getActionName();
        String reportType=reportForm.getChkReportType();

        session.removeAttribute("serviceList");
        List partnerList=pdao.getAllPartners();
        session.setAttribute("partnerList", partnerList);
        List yearList=DateManager.generateYears();
        session.setAttribute("yearList", yearList);
        
        List stateList=acm.getStateListForReports(session); 
        reportForm.setStateList(stateList);
        
        if(requiredAction==null)
        {
            
            reportForm.setLgaList(lgaList);
            reportForm.setCboList(new ArrayList());
            reportForm.setWardList(new ArrayList());
            return mapping.findForward(SUCCESS);
        }
        
        else if(requiredAction.equals("lga"))
        {
            List emptyList=new ArrayList();
            if(user !=null)
            lgaList =acm.getListOfLgasAssignedToUser(user,stateCode);
            reportForm.setLgaList(lgaList);
            reportForm.setCboList(emptyList);
            reportForm.setWardList(emptyList);
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equals("cbo"))
        {
            List orgList =new ArrayList();
            if(user !=null)
            orgList =acm.getListOfCBOsAssignedToUser(user,stateCode,lgaCode);
            reportForm.setCboList(orgList);
            reportForm.setWardList(new ArrayList());
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equals("ward"))
        {
            if(user !=null)
            wardList =acm.getListOfCommunitiesAssignedToUserByCBO(user,stateCode,lgaCode,cboCode);
            reportForm.setWardList(wardList);
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equals("MthlyServiceList"))
        {
            if(reportType.equalsIgnoreCase("on"))
            {
                month2=month;
                year2=year;
            }
            System.err.println("reportFilter is "+reportFilter);
            String stateName=util.getStateName(stateCode);
            String lgaName=util.getLgaName(lgaCode);
            String partnerName=util.getPartnerName(partnerCode);
            String wardName=util.getWardName(wardCode);
            paramList.add(stateCode);
            paramList.add(lgaCode);
            paramList.add(cboCode);
            paramList.add(wardCode);
            paramList.add(month);
            paramList.add(year);
            paramList.add(month2);
            paramList.add(year2);
            paramList.add(startAge);
            paramList.add(endAge);
            paramList.add(stateName);
            paramList.add(lgaName);
            paramList.add(partnerName);
            paramList.add(partnerCode);
            paramList.add(wardName);
            if(reportFilter !=null && reportFilter.equalsIgnoreCase("specialService"))
            paramList.add("All");
            else
            paramList.add(reportFilter);
            records.setMthlyServiceList(session, paramList);
            reportForm.reset(mapping, request);
            if(reportFilter !=null && reportFilter.equals("specialService"))
            {
                return mapping.findForward("specialReport");
            } 
        }
        /*else if(requiredAction.equals("save"))
        {
            String[] servicesToChange=reportForm.getServicesToChange();
            if(servicesToChange !=null &&(newServiceDate !=null && newServiceDate.indexOf("/") !=-1))
            {
                String idAndDate=null;
                String ovcId=null;
                String serviceDate=null;
                String mysqlServiceDate=null;
                OvcServiceDao sdao=new OvcServiceDaoImpl();
                for(int i=0; i<servicesToChange.length; i++)
                {
                    
                    idAndDate=servicesToChange[i];
                    if(idAndDate !=null && idAndDate.indexOf(";") !=-1)
                    {
                        String[] idAndDateArray=idAndDate.split(";");
                        ovcId=idAndDateArray[0];
                        serviceDate=idAndDateArray[1];
                        OvcService service=sdao.getOvcServiceForTheMth(ovcId, serviceDate);
                        if(service !=null)
                        {
                            mysqlServiceDate=DateManager.processMthDayYearToMysqlFormat(newServiceDate);
                            
                            if(sdao.getOvcServiceForTheMth(ovcId, mysqlServiceDate)==null)
                            {
                                sdao.deleteService(service);
                                System.err.println("service with "+service.getOvcId()+" "+service.getServicedate()+" deleted");
                                service.setServicedate(mysqlServiceDate);
                                sdao.saveOvcService(service, false, false);
                                System.err.println("service with "+service.getOvcId()+" "+service.getServicedate()+" saved");
                            }
                        }
                    }
                }
            }
            return mapping.findForward("specialReport");
        }*/
        return mapping.findForward(SUCCESS);
    }
}

