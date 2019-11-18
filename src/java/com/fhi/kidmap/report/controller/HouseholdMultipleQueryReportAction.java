/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report.controller;

import com.fhi.kidmap.business.Caregiver;
import com.fhi.kidmap.business.HouseholdService;
import com.fhi.kidmap.business.OvcWithdrawal;
import com.fhi.kidmap.business.User;
import com.fhi.kidmap.dao.CaregiverDao;
import com.fhi.kidmap.dao.CaregiverDaoImpl;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.HouseholdEnrollmentDao;
import com.fhi.kidmap.dao.HouseholdEnrollmentDaoImpl;
import com.fhi.kidmap.dao.HouseholdServiceDao;
import com.fhi.kidmap.dao.HouseholdServiceDaoImpl;
import com.fhi.kidmap.dao.OvcWithdrawalDao;
import com.fhi.kidmap.dao.OvcWithdrawalDaoImpl;
import com.fhi.kidmap.dao.PartnersDao;
import com.fhi.kidmap.dao.PartnersDaoImpl;
import com.fhi.kidmap.report.HouseholdReports;
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
public class HouseholdMultipleQueryReportAction extends org.apache.struts.action.Action 
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
            throws Exception {
        HouseholdMultipleQueryReportForm reportForm=(HouseholdMultipleQueryReportForm)form;
        HouseholdReports hhr=new HouseholdReports();
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
        List yearList=DateManager.generateYears();//appUtil.generateYears(2010,2017);
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
            boolean currentlyEnrolled=false;
            String criteria1Query=" ";
            String criteria2Query=" ";
            String criteria3Query=" ";
            String criteria4Query=" ";
            if(criteria1 !=null && !criteria1.equalsIgnoreCase("All")) 
            {
                if(criteria1.equalsIgnoreCase("served"))
                criteria1Query=" and hhs.caregiverId in (select distinct hhs.caregiverId from HouseholdService service where hhs.serviceDate between'"+cr1StartDate+"' and '"+cr1EndDate+"')";
                else if(criteria1.equalsIgnoreCase("notserved"))
                criteria1Query=" and hhs.caregiverId not in (select distinct hhs.caregiverId from HouseholdService service where hhs.serviceDate between'"+cr1StartDate+"' and '"+cr1EndDate+"')";
            }
            if(criteria2 !=null && !criteria2.equalsIgnoreCase("All")) 
            {
                if(criteria2.equalsIgnoreCase("served"))
                criteria2Query=" and hhs.caregiverId in (select distinct hhs.caregiverId from HouseholdService service where hhs.serviceDate between'"+cr2StartDate+"' and '"+cr2EndDate+"')";
                else if(criteria2.equalsIgnoreCase("notserved"))
                criteria2Query=" and hhs.caregiverId not in (select distinct hhs.caregiverId from HouseholdService service where hhs.serviceDate between'"+cr2StartDate+"' and '"+cr2EndDate+"')";
            }
            if(criteria3 !=null && !criteria3.equalsIgnoreCase("All")) 
            {
                if(criteria3.equalsIgnoreCase("curenr"))
                currentlyEnrolled=true;
                
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
            //String param[]={stateCode,lgaCode,orgCode,wardCode,lgaName,cboName,startMth+"",startYear+"",endMth+"",endYear+"",partnerCode,wardName,stateName,partnerName};
            String[] params={stateCode,lgaCode,cboCode,wardCode,"All","All",month+"",year+"",month2+"",year2+"",partnerCode,wardName,null,partnerName};
            String additionalQuery=util.getHVIOrgUnitQuery(params);
            List hviList=hhr.getHouseholdServiceRecordsForMultiQuery(request,additionalQuery+criteria1Query+criteria2Query,currentlyEnrolled);
            String cboName=util.getOrganizationName(cboCode);
            
            paramList.add(stateName);
            paramList.add(lgaName);
            paramList.add(cboName);
            paramList.add(wardName);
            
            String[] dateArray={util.getStartDate(dateParams),util.getEndDate(dateParams)};
            List dateLabels=util.getDateLabels(dateArray);
            String partner="Partner: "+util.getPartnerName(params[10]);
            String hhsAgeParam="Served between start of "+appUtil.getMonthAsString(Integer.parseInt(params[6]))+" "+params[7]+" and end of "+appUtil.getMonthAsString(Integer.parseInt(params[8]))+" "+params[9];
            request.setAttribute("hhsRecords", hviList);
            request.setAttribute("hhsParam", params);
            request.setAttribute("hhsAgeParam", hhsAgeParam);
            request.setAttribute("hhsReportPartner", partner);
            reportForm.reset(mapping, request);
            setButtonAttribute(request);
        }
        else if(requiredAction.equals("processSelectedRecords"))
        {
            ServiceDomainManager.setOvcServices(session);
            session.setAttribute("selectedRecords", selectedRecords);
            
            return mapping.findForward("rapidServicePage");
        }
        else if(requiredAction.equals("save"))
        {
            String[] selectedRecordIds=(String[])session.getAttribute("selectedRecords");
            if(selectedRecordIds !=null)
            {
                HouseholdServiceDao hhsdao = new HouseholdServiceDaoImpl();
                String serviceDate=reportForm.getServiceDate();
                String serviceProviderName=reportForm.getProviderName();
                String volunteerDesignation=reportForm.getVolunteerDesignation();
                //DateManager dm=new DateManager();
                if((serviceDate !=null && serviceDate.indexOf("/") !=-1) && serviceProviderName !=null)
                {
                    String mysqlDate=dm.processMthDayYearToMysqlFormat(serviceDate);
                    String caregiverId=null;
                    String beneficiaryType="householdhead";
                    String withdrawalType="household";
                    HouseholdService hhs=null;
                    CaregiverDao cgiverdao=new CaregiverDaoImpl();
                    //HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
                    String hhUniqueId=null;
                    for(int i=0; i<selectedRecordIds.length; i++)
                    {
                        caregiverId=selectedRecordIds[i];
                        if(caregiverId==null)
                        continue;
                        HouseholdService dupservice=hhsdao.getHouseholdService(caregiverId, mysqlDate);
                        if(dupservice !=null)
                        continue;
                        String[] caregiverIdArray=caregiverId.split("/");
                        if(caregiverIdArray !=null && caregiverIdArray.length > 4)
                        {
                            Caregiver cgiver=cgiverdao.getCaregiverByCaregiverId(caregiverId);
                            if(cgiver ==null)
                            {
                                continue;
                            }
                            hhUniqueId=cgiver.getHhUniqueId();
                            beneficiaryType="caregiver";
                            withdrawalType=beneficiaryType;
                        }
                        else
                        hhUniqueId=caregiverId;
                        hhs=getHouseholdService(reportForm,caregiverId);
                        hhs.setHhUniqueId(hhUniqueId);
                        hhs.setCaregiverId(caregiverId);
                        hhs.setVolunteerName(serviceProviderName+":auto");
                        hhs.setVolunteerDesignation(volunteerDesignation);
                        hhs.setServiceRecipientType(beneficiaryType);
                        hhs.setDateOfEntry(appUtil.getCurrentDate());
                        hhs.setServiceDate(mysqlDate);
                        
                        System.err.println("about to save Household service with unique id "+hhs.getCaregiverId());
                        hhsdao.saveHouseholdService(hhs);
                        
                        try
                        {
                            if(reportForm.getReasonWithdrawal() != null)
                            {
                                 OvcWithdrawalDao dao = new OvcWithdrawalDaoImpl();
                                 dao.withdrawClient(caregiverId, mysqlDate,reportForm.getReasonWithdrawal(), withdrawalType,0);
                                /*OvcWithdrawal ovcWithdrawal = new OvcWithdrawal();
                                ovcWithdrawal.setOvcId(caregiverId);
                                ovcWithdrawal.setDateOfWithdrawal(mysqlDate);
                                ovcWithdrawal.setType("caregiver");
                                ovcWithdrawal.setReasonWithdrawal(reportForm.getReasonWithdrawal());
                               
                                dao.saveOrUpdateOvcWithdrawal(ovcWithdrawal);*/
                            }
                            
                        }
                        catch(Exception ex)
                        {
                                ex.printStackTrace();
                        }
                        
                        
                        
                    }
                }
            }
            return mapping.findForward(PARAMPAGE);
        }
        return mapping.findForward(SUCCESS);
    }
    private HouseholdService getHouseholdService(HouseholdMultipleQueryReportForm reportForm,String caregiverId)
    {
        AppUtility appUtil=new AppUtility();
        HouseholdService hhs = new HouseholdService();
        hhs.setCaregiverId(caregiverId);
               
        hhs.setPsychosocialSupportServices(appUtil.concatStr(reportForm.getPsychosocialServices(), null));
        hhs.setNutritionalServices(appUtil.concatStr(reportForm.getNutritionalServices(), null));
        hhs.setHealthServices(appUtil.concatStr(reportForm.getHealthServices(), null));
        hhs.setEducationalServices(appUtil.concatStr(reportForm.getEducationServices(), null));
        hhs.setProtectionServices(appUtil.concatStr(reportForm.getProtectionServices(), null));
        hhs.setShelterAndCareServices(appUtil.concatStr(reportForm.getShelterServices(), null));
        hhs.setEconomicStrengtheningServices(appUtil.concatStr(reportForm.getEconomicServices(), null));
        //hhs.setserviceAccessed1(appUtil.concatStr(app, null));
        
        return hhs;
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
