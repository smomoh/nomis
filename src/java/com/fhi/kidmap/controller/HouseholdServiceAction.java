/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.Caregiver;
import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.kidmap.business.HouseholdService;
import com.fhi.kidmap.business.States;
import com.fhi.nomis.nomisutils.LoadUpInfo;
import com.fhi.kidmap.dao.CaregiverDao;
import com.fhi.kidmap.dao.CaregiverDaoImpl;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.HouseholdEnrollmentDao;
import com.fhi.kidmap.dao.HouseholdEnrollmentDaoImpl;
import com.fhi.kidmap.dao.HouseholdServiceDao;
import com.fhi.kidmap.dao.HouseholdServiceDaoImpl;
import com.fhi.kidmap.dao.OvcWithdrawalDao;
import com.fhi.kidmap.dao.OvcWithdrawalDaoImpl;
import com.fhi.nomis.OperationsManagement.HivRecordsManager;
import com.fhi.nomis.nomisutils.AccessManager;
import com.fhi.nomis.nomisutils.OvcServiceManager;
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
 * @author Siaka
 */
public class HouseholdServiceAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    /**
     * This is the action called from the Struts framework.
     *
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
        HouseholdServiceForm hhsForm=(HouseholdServiceForm)form;
        HouseholdService hhs=new HouseholdService();
        Caregiver cgiver=null;
        HouseholdEnrollment hhe=new HouseholdEnrollment();
        HouseholdServiceDao hhsdao=new HouseholdServiceDaoImpl();
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl();
        CaregiverDao cgiverdao=new CaregiverDaoImpl();
        LoadUpInfo loadup=new LoadUpInfo();
        AppUtility appUtil=new AppUtility();
        DaoUtil util=new DaoUtil();
        HttpSession session=request.getSession();
        List caregiverList=new ArrayList();
        
        session.setAttribute("hhServiceUniqueId"," ");
        AccessManager acm=new AccessManager();
        boolean userInRole=acm.isUserInRole("001",appUtil.getCurrentUser(session));
        if(!userInRole)
        {
            loadStartupInfo(session);
            session.setAttribute("hhServiceSaveBtnDisabled", "true");
            session.setAttribute("hhServiceModifyBtnDisabled", "true");
            return mapping.findForward("success");
        }
        String uniqueId=hhsForm.getHhUniqueId();
        String realOrgCode=hhsForm.getOrgCode();
        String caregiverId=hhsForm.getCaregiverName();
        if(uniqueId !=null && caregiverId !=null)
        {
            if(uniqueId.equalsIgnoreCase(caregiverId))
            hhs.setServiceRecipientType("householdhead");
            else
            hhs.setServiceRecipientType("caregiver");
        }
        else
        {
            Caregiver cgiver2=cgiverdao.getCaregiverByCaregiverId(caregiverId);
            if(cgiver2 !=null)
            {
                if(cgiver2.getHouseholdhead()=="1")
                hhs.setServiceRecipientType("householdhead");
                else
                hhs.setServiceRecipientType("caregiver");
            }
        }
        String caregiverGender=hhsForm.getCareiverGender();
        String ward=hhsForm.getWard();
        int caregiverAge=hhsForm.getCaregiverAge();
        int SNo=hhsForm.getSerialNo();
        hhs.setHhUniqueId(uniqueId);
        hhs.setCaregiverId(caregiverId);
        hhs.setId(hhsForm.getId());
        hhs.setServiceNo(hhsForm.getServiceNo());
        hhs.setEconomicStrengtheningServices(appUtil.concatStr(hhsForm.getEconomicStrengtheningServices(),null));
        hhs.setEducationalServices(appUtil.concatStr(hhsForm.getEducationalServices(),null));
        hhs.setHealthServices(appUtil.concatStr(hhsForm.getHealthServices(),null));
        System.err.println("hhs.getHealthServices() in hhsservice action is "+hhs.getHealthServices());
        hhs.setNutritionalServices(appUtil.concatStr(hhsForm.getNutritionalServices(),null));
        hhs.setProtectionServices(appUtil.concatStr(hhsForm.getProtectionServices(),null));
        hhs.setPsychosocialSupportServices(appUtil.concatStr(hhsForm.getPsychosocialSupportServices(),null));
        hhs.setShelterAndCareServices(appUtil.concatStr(hhsForm.getShelterAndCareServices(),null));
        String serviceDate=hhsForm.getServiceDate();
        String mysqlServiceDate=appUtil.processMthDayYearToMysqlFormat(serviceDate);
        hhs.setServiceDate(mysqlServiceDate);
        hhs.setVolunteerName(hhsForm.getVolunteerName());
        hhs.setVolunteerDesignation(hhsForm.getVolunteerDesignation());
        hhs.setRecordedBy(appUtil.getCurrentUser(session));
        hhs.setDateOfEntry(appUtil.getCurrentDate());
        hhs.setNumberOfServices(hhsdao.getNumberOfServicesPerServiceRecord(hhs));
        hhs.setCurrentHivStatus(hhsForm.getNewHivStatus());
        hhs.setEnrolledInCare(hhsForm.getEnrolledInCare());
        hhs.setEnrolledOnART(hhsForm.getEnrolledOnART());
        hhs.setFacilityId(hhsForm.getOrganizationClientIsReferred());
        //System.err.println("hhs.getFacilityId() is "+hhs.getFacilityId());
        String stateCode=hhsForm.getStateCode();
        States state=(States)session.getAttribute("state");
        if(state !=null)
        stateCode=state.getState_code();
        List facilityList=util.getReferralDirectoriesByState(stateCode);
        hhsForm.setReferralDirectoryList(facilityList);
        cgiver=cgiverdao.getCaregiverByCaregiverId(caregiverId);
        if(cgiver==null)
        {
            cgiver=new Caregiver();
        }
        List hivStatusList=HivRecordsManager.loadNewHivStatus(cgiver.getActiveHivStatus().getHivStatus());
        hhsForm.setHivStatusList(hivStatusList);
        
        session.setAttribute("hhServiceSaveBtnDisabled", "false");
        session.setAttribute("hhServiceModifyBtnDisabled", "true");
        loadup.getCbosPerLgaFromSetup(session,hhsForm.getLgaCode());
        String requiredAction=hhsForm.getActionName();
        //System.err.println("requiredAction is "+requiredAction);
        if(requiredAction==null)
        {
            hhsForm.reset(mapping, request);
            loadup=new LoadUpInfo();
            loadup.getStartupInfo(session);
            loadup.setRequestParameters(request);
            loadup.setParamAttributes(request);
            String partnerCode=util.getPartnerCode(appUtil.getCurrentUser(session));
            String partnerName=util.getPartnerName(partnerCode);
            hhsdao.setCaregiverIdAndRecipientTypeInHhs();
            session.setAttribute("partnerName", partnerName);
            session.setAttribute("caregiverList", caregiverList);
        }
        else if(requiredAction.equalsIgnoreCase("cboList"))
        {
            loadup.getCbosPerLgaFromSetup(session,hhsForm.getLgaCode());
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("caregiverList"))
        {
            hhsForm.reset(mapping, request);
            hhsForm.setHhUniqueId(uniqueId);
            hhsForm.setSerialNo(SNo);
            hhsForm.setOrgCode(realOrgCode);
            String uniqueIdMsg="<span style='color:red'>This household unique id does not exist</span>";
            session.setAttribute("hhServiceSaveBtnDisabled", "true");
            session.setAttribute("hhServiceModifyBtnDisabled", "true");              
            hhe=hhedao.getHouseholdEnrollment(uniqueId);
            if(hhe !=null)
            {
               List caregiverList2=new ArrayList();
               cgiver=cgiverdao.getCaregiverByName(uniqueId, hhe.getHhFirstname(), hhe.getHhSurname());
               if(cgiver ==null)
               {
                   cgiver=new Caregiver();
                   cgiver.setCaregiverFirstname(hhe.getHhFirstname());
                   cgiver.setCaregiverLastName(hhe.getHhSurname()+"(HH)");
                   cgiver.setHouseholdhead("1");
                   cgiver.setHhUniqueId(uniqueId);
                   cgiver.setCaregiverId(uniqueId);
                   cgiver.setCaregiverAge(hhe.getHhAge());
                   cgiver.setCaregiverGender(hhe.getHhGender());
                   caregiverList.add(cgiver);
               }
               else
               {
                   cgiver=(Caregiver)cgiver;
                   cgiver.setCaregiverLastName(cgiver.getCaregiverLastName()+"(HH)");
                   caregiverList.add(cgiver);
               }
               
               caregiverList2=cgiverdao.getListOfCaregiversFromSameHousehold(uniqueId);
               if(caregiverList2 !=null & !caregiverList2.isEmpty())
               {
                   Caregiver cgiver2=null;
                   for(int i=0; i<caregiverList2.size(); i++)
                   {
                       cgiver2=(Caregiver)caregiverList2.get(i);
                       if(cgiver2 !=null && cgiver2.getHouseholdhead()=="1")
                       continue;
                       caregiverList.add(cgiver2);
                   }
               }
               hhsForm.setWard(util.getWardName(hhe.getCommunityCode()));
               uniqueIdMsg=uniqueId;

               session.setAttribute("hhServiceSaveBtnDisabled", "false");
               session.setAttribute("hhServiceModifyBtnDisabled", "true");  
            }
            session.setAttribute("caregiverList", caregiverList);
            session.setAttribute("hhServiceUniqueId",uniqueIdMsg);
           return mapping.findForward(SUCCESS); 
        }
        else if(requiredAction.equalsIgnoreCase("caregiverDetails"))
        {
            hhsForm.reset(mapping, request);
            hhsForm.setSerialNo(SNo);
            hhsForm.setCaregiverName(caregiverId);
            if(wdao.getWithdrawal(caregiverId)==null)
            {
                hhsForm.setHhUniqueId(uniqueId); 
                cgiver=cgiverdao.getCaregiverByCaregiverId(caregiverId);
                if(cgiver !=null)
                {
                    hhsForm.setCaregiverAge(cgiver.getCaregiverAge());
                    //hhsForm.setCaregiverName(caregiverId);
                    hhsForm.setCareiverGender(cgiver.getCaregiverGender());
                    
                    hivStatusList=HivRecordsManager.loadNewHivStatus(cgiver.getActiveHivStatus().getHivStatus());
                    hhsForm.setHivStatusList(hivStatusList);
                    hhsForm.setCurrentHivStatus(cgiver.getActiveHivStatus().getHivStatus());
                    hhsForm.setEnrolledInCare(cgiver.getEnrolledInCare());
                    hhsForm.setEnrolledOnART(cgiver.getEnrolledOnART());
                    hhsForm.setOrganizationClientIsReferred(cgiver.getFacilityId());
                    //System.err.println("cgiver.getCurrentHivStatus() in caregiverDetails is "+cgiver.getCurrentHivStatus());
                }
            }
            else
            {
                request.setAttribute("msg", "This caregiver has been withdrawn");
            }
            hhe=hhedao.getHouseholdEnrollment(uniqueId);
            if(hhe !=null)
            hhsForm.setWard(util.getWardName(hhe.getCommunityCode()));
            session.setAttribute("hhServiceUniqueId",uniqueId);
            return mapping.findForward(SUCCESS); 
        }
        else if(requiredAction.equalsIgnoreCase("hhserviceDetails"))
        {
            hhsForm.reset(mapping, request);
            //System.err.println("uniqueId in hhserviceaction is "+uniqueId+" and caregiverId is "+caregiverId);
            hhsForm.setCaregiverName(caregiverId);
            if(wdao.getWithdrawal(caregiverId) !=null)
            {
                request.setAttribute("msg", "This caregiver has been withdrawn");
                return mapping.findForward(SUCCESS);
            }
            hhsForm.setSerialNo(SNo);
            hhsForm.setCaregiverAge(caregiverAge); 
            //String caregiverId=hhsForm.getCaregiverName();
            //hhsForm.setCaregiverName(caregiverId);
            hhsForm.setCareiverGender(caregiverGender);
            hhsForm.setServiceDate(serviceDate);
            hhsForm.setHhUniqueId(uniqueId);
            hhe=hhedao.getHouseholdEnrollment(uniqueId);
            if(hhe !=null)
            hhsForm.setWard(util.getWardName(hhe.getCommunityCode()));
            hhs=hhsdao.getHouseholdService(caregiverId, appUtil.processMthDayYearToMysqlFormat(serviceDate));
            session.setAttribute("hhServiceUniqueId",uniqueId);
            if(hhs !=null)
            {
                String educationalServicesByName=OvcServiceManager.getConcatenatedServiceNames(hhs.getEducationalServices(),true);
                String economicStrengtheningServicesByName=OvcServiceManager.getConcatenatedServiceNames(hhs.getEconomicStrengtheningServices(),true);
                String healthServicesByName=OvcServiceManager.getConcatenatedServiceNames(hhs.getHealthServices(),true);
                String nutritionServicesByName=OvcServiceManager.getConcatenatedServiceNames(hhs.getNutritionalServices(),true);
                String protectionServicesByName=OvcServiceManager.getConcatenatedServiceNames(hhs.getProtectionServices(),true);
                String psychosocialServicesByName=OvcServiceManager.getConcatenatedServiceNames(hhs.getPsychosocialSupportServices(),true);
                String shelterServicesByName=OvcServiceManager.getConcatenatedServiceNames(hhs.getShelterAndCareServices(),true);
                hhsForm.setId(hhs.getId());
                hhsForm.setServiceNo(hhs.getServiceNo());
                hhsForm.setEconomicStrengtheningServices(appUtil.splitServices(economicStrengtheningServicesByName));
                hhsForm.setEducationalServices(appUtil.splitServices(educationalServicesByName));
                hhsForm.setHealthServices(appUtil.splitServices(healthServicesByName));
                //System.err.println("hhs.getHealthServices() is "+hhs.getHealthServices());
                hhsForm.setNutritionalServices(appUtil.splitServices(nutritionServicesByName));
                hhsForm.setProtectionServices(appUtil.splitServices(protectionServicesByName));
                hhsForm.setPsychosocialSupportServices(appUtil.splitServices(psychosocialServicesByName));
                hhsForm.setShelterAndCareServices(appUtil.splitServices(shelterServicesByName));
                hhsForm.setServiceDate(appUtil.convertMysqlDateTomonthDayYear(hhs.getServiceDate()));
                hhsForm.setCurrentHivStatus(hhs.getCurrentHivStatus());
                hhsForm.setEnrolledInCare(hhs.getEnrolledInCare());
                hhsForm.setEnrolledOnART(hhs.getEnrolledOnART());
                hhsForm.setOrganizationClientIsReferred(hhs.getFacilityId());
                hhsForm.setVolunteerName(hhs.getVolunteerName());
                hhsForm.setVolunteerDesignation(hhs.getVolunteerDesignation());
                hhsForm.setHivStatusList(HivRecordsManager.loadNewHivStatus("negpos"));
                session.setAttribute("hhServiceSaveBtnDisabled", "true");
                session.setAttribute("hhServiceModifyBtnDisabled", "false");  
            }
            else
            {
                hivStatusList=HivRecordsManager.loadNewHivStatus(cgiver.getActiveHivStatus().getHivStatus());
                hhsForm.setHivStatusList(hivStatusList);
            }
            
            hhsForm.setCurrentHivStatus(cgiver.getActiveHivStatus().getHivStatus());
            //System.err.println("cgiver.getCurrentHivStatus() in hhserviceDetails is "+cgiver.getCurrentHivStatus());
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("save"))
        {
            int serviceNo=1;
            List list=hhsdao.getAllServicesPerHousehold(uniqueId);
            if(list !=null && list.size()>0)
            {
                serviceNo=((HouseholdService)list.get(list.size()-1)).getServiceNo();
                serviceNo++;
            }
            hhs.setServiceNo(serviceNo);
            System.err.println("hhs.getHealthServices() 2 is "+hhs.getHealthServices());
            hhsdao.saveHouseholdService(hhs);
            if(hhsForm.getReasonWithdrawal() !=null)
            {
                withdrawClient(caregiverId,appUtil.processMthDayYearToMysqlFormat(serviceDate),hhsForm.getReasonWithdrawal(), serviceNo);
            }
        }
        else if(requiredAction.equalsIgnoreCase("modify"))
        {
            hhsdao.updateHouseholdService(hhs);
            if(hhsForm.getReasonWithdrawal() !=null)
            {
                int serviceNo=hhs.getServiceNo();
                withdrawClient(caregiverId,appUtil.processMthDayYearToMysqlFormat(serviceDate),hhsForm.getReasonWithdrawal(), serviceNo);
            }
        }
        else if(requiredAction.equalsIgnoreCase("delete"))
        {
            hhsdao.deleteHouseholdService(hhs);
        }
        hhsForm.reset(mapping, request);
        session.setAttribute("hhServiceSaveBtnDisabled", "false");
        session.setAttribute("hhServiceModifyBtnDisabled", "true");
        return mapping.findForward(SUCCESS);
    }
    private void withdrawClient(String uniqueId, String dateOfWithdrawal,String reasonWithdrawal, int serviceNo)
    {
        OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl();
        wdao.withdrawClient(uniqueId, dateOfWithdrawal,reasonWithdrawal, "caregiver",serviceNo);
    }
    private void loadStartupInfo(HttpSession session)
    {
        session.setAttribute("hhUniqueId", " ");
        LoadUpInfo loadup=new LoadUpInfo();
        loadup.getStartupInfo(session);
    }
}
