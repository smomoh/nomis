/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report.controller;

import com.fhi.kidmap.business.OvcService;
import com.fhi.kidmap.business.OvcWithdrawal;
import com.fhi.kidmap.business.User;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.OvcServiceDao;
import com.fhi.kidmap.dao.OvcServiceDaoImpl;
import com.fhi.kidmap.dao.OvcWithdrawalDao;
import com.fhi.kidmap.dao.OvcWithdrawalDaoImpl;
import com.fhi.kidmap.dao.PartnersDao;
import com.fhi.kidmap.dao.PartnersDaoImpl;
import com.fhi.kidmap.report.ServiceRecords;
import com.fhi.nomis.nomisutils.AccessManager;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.nomis.nomisutils.DateManager;
import com.fhi.nomis.nomisutils.ServiceDomainManager;
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
public class MultipleReportAction extends org.apache.struts.action.Action 
{

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
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
        MultipleReportForm reportForm=(MultipleReportForm)form;
        ServiceRecords records=new ServiceRecords();
        PartnersDao pdao=new PartnersDaoImpl();
        DateManager dm=new DateManager();
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
        String requiredAction;

        String stateCode=reportForm.getState();
        String lgaCode=reportForm.getLga();
        String cboCode=reportForm.getCbo();
        String wardCode=reportForm.getWard();
        String partnerCode=reportForm.getPartnerCode();
        String criteria1=reportForm.getCriteria1();
        String criteria2=reportForm.getCriteria2();
        String criteria3=reportForm.getCriteria3();
        String criteria4=reportForm.getCriteria4();
        String[] selectedRecords=reportForm.getSelectedRecords();
        
        int month=reportForm.getServiceMonth();
        int year=reportForm.getServiceYear();
        int month2=reportForm.getServiceMonth2();
        int year2=reportForm.getServiceYear2();
        
        int cr2month=reportForm.getCr2serviceMonth();
        int cr2year=reportForm.getCr2serviceYear();
        int cr2month2=reportForm.getCr2serviceMonth2();
        int cr2year2=reportForm.getCr2serviceYear2();
        
        int startAge=reportForm.getStartAge();
        int endAge=reportForm.getEndAge();
        requiredAction=reportForm.getActionName();
        String reportType=reportForm.getChkReportType();

        session.removeAttribute("serviceList");
        List partnerList=pdao.getAllPartners();
        session.setAttribute("partnerList", partnerList);
        
        List stateList=acm.getStateListForReports(session); 
        reportForm.setStateList(stateList);
        List monthList=dm.generateMonths();
        List yearList=appUtil.generateYears(2010,2017);
        request.setAttribute("mrmonthList", monthList);
        request.setAttribute("mryearList", yearList);
        System.err.println("requiredAction is "+requiredAction);
        if(requiredAction==null)
        {
            reportForm.setLgaList(lgaList);
            reportForm.setCboList(new ArrayList());
            reportForm.setWardList(new ArrayList());
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equals("lga"))
        {
            List emptyList=new ArrayList();
            if(user !=null)
            lgaList =acm.getListOfLgasAssignedToUser(user,stateCode);
            reportForm.setLgaList(lgaList);
            reportForm.setCboList(emptyList);
            reportForm.setWardList(emptyList);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equals("cbo"))
        {
            List orgList =new ArrayList();
            if(user !=null)
            orgList =acm.getListOfCBOsAssignedToUser(user,stateCode,lgaCode);
            reportForm.setCboList(orgList);
            reportForm.setWardList(new ArrayList());
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equals("ward"))
        {
            if(user !=null)
            wardList =acm.getListOfCommunitiesAssignedToUserByCBO(user,stateCode,lgaCode,cboCode);
            reportForm.setWardList(wardList);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equals("MthlyServiceList"))
        {
            ServiceDomainManager.setOvcServices(session);
            //DaoUtil util=new DaoUtil();
            String[] dateParams={month+"",year+"",month2+"",year2+""};
            String[] cr2dateParams={cr2month+"",cr2year+"",cr2month2+"",cr2year2+""};
            String cr1StartDate=util.getStartDate(dateParams);
            String cr1EndDate=util.getEndDate(dateParams);
            String cr2StartDate=util.getStartDate(cr2dateParams);
            String cr2EndDate=util.getEndDate(cr2dateParams);
            
            String criteria1Query=" ";
            String criteria2Query=" ";
            String criteria3Query=" ";
            String criteria4Query=" ";
            if(criteria1 !=null && !criteria1.equalsIgnoreCase("All")) 
            {
                if(criteria1.equalsIgnoreCase("served"))
                criteria1Query=" and service.ovcId in (select distinct service.ovcId from OvcService service where service.servicedate between'"+cr1StartDate+"' and '"+cr1EndDate+"')";
                else if(criteria1.equalsIgnoreCase("notserved"))
                criteria1Query=" and service.ovcId not in (select distinct service.ovcId from OvcService service where service.servicedate between'"+cr1StartDate+"' and '"+cr1EndDate+"')";
            }
            if(criteria2 !=null && !criteria2.equalsIgnoreCase("All")) 
            {
                if(criteria2.equalsIgnoreCase("served"))
                criteria2Query=" and service.ovcId in (select distinct service.ovcId from OvcService service where service.servicedate between'"+cr2StartDate+"' and '"+cr2EndDate+"')";
                else if(criteria2.equalsIgnoreCase("notserved"))
                criteria2Query=" and service.ovcId not in (select distinct service.ovcId from OvcService service where service.servicedate between'"+cr2StartDate+"' and '"+cr2EndDate+"')";
            }
            if(criteria3 !=null && !criteria3.equalsIgnoreCase("All")) 
            {
                if(criteria3.equalsIgnoreCase("curenr"))
                criteria3Query=util.getOvcWithdrawnFromProgramQuery("No");
                
            }
            if(criteria4 !=null && !criteria4.equalsIgnoreCase("All")) 
            {
                if(criteria4.equalsIgnoreCase("hivpos"))
                criteria4Query="";
                else if(criteria4.equalsIgnoreCase("hivneg"))
                criteria4Query=" ";
                else if(criteria4.equalsIgnoreCase("hivunk"))
                criteria4Query=" ";
            }
            String stateName=util.getStateName(stateCode);
            String lgaName=util.getLgaName(lgaCode);
            String partnerName=util.getPartnerName(partnerCode);
            String wardName=util.getWardName(wardCode);
            
            String[] params={stateCode,lgaCode,cboCode,wardCode,month+"",year+"",month2+"",year2+"",startAge+"",endAge+"",null,null,null,partnerName,partnerCode};
            String additionalQuery=util.getQueryCriteria(params);
            List ovcList=records.getListOfOvcServed(additionalQuery+criteria3Query+criteria1Query+criteria2Query);
            String cboName=util.getOrganizationName(cboCode);
            
            paramList.add(stateName);
            paramList.add(lgaName);
            paramList.add(cboName);
            paramList.add(wardName);
            
            String[] dateArray={util.getStartDate(dateParams),util.getEndDate(dateParams)};
            List dateLabels=util.getDateLabels(dateArray);
            String serviceAgePeriod=startAge+" and "+endAge;
            session.removeAttribute("serviceList");
            session.setAttribute("serviceAgePeriod", serviceAgePeriod);
            session.setAttribute("serviceDate", dateLabels);
            session.setAttribute("serviceOrgParam", paramList);
            session.setAttribute("serviceList", ovcList);
            reportForm.reset(mapping, request);
            setButtonAttribute(request);
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equals("processSelectedRecords"))
        {
            ServiceDomainManager.setOvcServices(session);
            session.setAttribute("selectedRecords", selectedRecords);
            String currentDate=appUtil.getCurrentDate();
            if(currentDate !=null)
            {
                String[] currentDateArray=currentDate.split("-");
                if(currentDateArray.length>2)
                {
                    int curYear=Integer.parseInt(currentDateArray[0]);
                    int curMth=Integer.parseInt(currentDateArray[1]);
                    int day=Integer.parseInt(currentDateArray[2]);
                    if((day>3 && day<16) && curMth==12 && curYear==2017)
                    {
                        request.setAttribute("rapidAttribute", "rapidAttribute");
                    }
                    else
                    request.removeAttribute("rapidAttribute");
                }
            }
            return mapping.findForward("rapidServicePage");
        }
        else if(requiredAction.equals("save"))
        {
            String[] selectedRecordIds=(String[])session.getAttribute("selectedRecords");
            if(selectedRecordIds !=null)
            {
                OvcServiceDao sdao = new OvcServiceDaoImpl();
                String serviceDate=reportForm.getServiceDate();
                String serviceProviderName=reportForm.getProviderName();
                //DateManager dm=new DateManager();
                if((serviceDate !=null && serviceDate.indexOf("/") !=-1) && serviceProviderName !=null)
                {
                    String mysqlDate=dm.processMthDayYearToMysqlFormat(serviceDate);
                    String ovcId=null;
                    OvcService service=null;
                    for(int i=0; i<selectedRecordIds.length; i++)
                    {
                        ovcId=selectedRecordIds[i];
                        OvcService dupservice=sdao.getOvcServiceForTheMth(ovcId, mysqlDate);
                        if(dupservice !=null)
                        continue;
                        service=getOvcService(reportForm,ovcId);
                        service.setOvcId(ovcId);
                        service.setProviderName(serviceProviderName+":auto");
                        service.setDateOfEntry(appUtil.getCurrentDate());
                        service.setServicedate(mysqlDate);
                        
                        
                        try
                        {
                            if(reportForm.getReasonWithdrawal() != null)
                            {
                                OvcWithdrawalDao dao = new OvcWithdrawalDaoImpl();
                                dao.withdrawClient(ovcId, mysqlDate,reportForm.getReasonWithdrawal(), "ovc",0);
                                
                            }
                            
                        }
                        catch(Exception ex)
                        {
                                ex.printStackTrace();
                        }
                        System.err.println("about to save service with unique id "+service.getOvcId());
                        
                        sdao.saveOvcService(service, false, true);
                    }
                }
            }
            return mapping.findForward(PARAMPAGE);
        }
        return mapping.findForward(SUCCESS);
    }
    private OvcService getOvcService(MultipleReportForm reportForm,String ovcId)
    {
        AppUtility appUtil=new AppUtility();
        OvcService service = new OvcService();
        service.setOvcId(ovcId);
               
        service.setserviceAccessed1(appUtil.concatStr(reportForm.getPsychosocialServices(), null));
        service.setserviceAccessed2(appUtil.concatStr(reportForm.getNutritionalServices(), null));
        service.setserviceAccessed3(appUtil.concatStr(reportForm.getHealthServices(), null));
        service.setserviceAccessed4(appUtil.concatStr(reportForm.getEducationServices(), null));
        service.setserviceAccessed5(appUtil.concatStr(reportForm.getProtectionServices(), null));
        service.setserviceAccessed6(appUtil.concatStr(reportForm.getShelterServices(), null));
        service.setserviceAccessed7(appUtil.concatStr(reportForm.getEconomicServices(), null));
        //service.setserviceAccessed1(appUtil.concatStr(app, null));
        
        return service;
    }
    private void setButtonAttribute(HttpServletRequest request)
    {
        AppUtility appUtil=new AppUtility();
        String currentDate=appUtil.getCurrentDate();
            System.err.println("currentDate is "+currentDate);
            if(currentDate !=null)
            {
                String[] currentDateArray=currentDate.split("-");
                if(currentDateArray.length>2)
                {
                    System.err.println("currentDateArray.length is "+currentDateArray.length);
                    int curYear=Integer.parseInt(currentDateArray[0]);
                    int curMth=Integer.parseInt(currentDateArray[1]);
                    int day=Integer.parseInt(currentDateArray[2]);
                    if(day>25 && curMth==10 && curYear==2017)
                    {
                        System.err.println("day is "+day+" "+curMth+" "+curYear);
                        request.setAttribute("rapidAttribute", "rapidAttribute");
                    }
                    else
                    request.removeAttribute("rapidAttribute");
                }
            }
    }
}
