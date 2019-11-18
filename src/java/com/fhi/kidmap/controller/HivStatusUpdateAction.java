/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.Caregiver;
import com.fhi.kidmap.business.FollowupSurvey;
import com.fhi.kidmap.business.HivStatus;
import com.fhi.kidmap.business.HivStatusUpdate;
import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.nomis.nomisutils.LoadUpInfo;
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.business.OvcService;
import com.fhi.kidmap.business.States;
import com.fhi.kidmap.dao.CaregiverDao;
import com.fhi.kidmap.dao.CaregiverDaoImpl;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.HivStatusUpdateDao;
import com.fhi.kidmap.dao.HivStatusUpdateDaoImpl;
import com.fhi.kidmap.dao.HouseholdEnrollmentDao;
import com.fhi.kidmap.dao.HouseholdEnrollmentDaoImpl;
import com.fhi.kidmap.dao.LgaDao;
import com.fhi.kidmap.dao.LgaDaoImpl;
import com.fhi.kidmap.dao.OvcDao;
import com.fhi.kidmap.dao.OvcDaoImpl;
import com.fhi.nomis.OperationsManagement.HivRecordsManager;
import com.fhi.nomis.nomisutils.DateManager;
import com.fhi.nomis.nomisutils.NomisConstant;
import java.util.ArrayList;
import java.util.Date;
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
public class HivStatusUpdateAction extends org.apache.struts.action.Action {

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
        HivStatusUpdateForm hsf=(HivStatusUpdateForm)form;
        HttpSession session=request.getSession();
        DaoUtil util=new DaoUtil();
        LgaDao lgadao=new LgaDaoImpl();
        AppUtility appUtil=new AppUtility();
        DateManager dm=new DateManager();
        LoadUpInfo loadup=new LoadUpInfo();
        loadup.getStartupInfo(session);
        //List lgaList=lgadao.getLgaList(appUtil.getCurrentState(session));
        int serialNo=hsf.getHhSerialNo();
        String lgaCode=hsf.getLgaCode();
        String hhUniqueId=hsf.getHhUniqueId();
        String beneficiaryId=hsf.getBeneficiaryId();
        String beneficiaryType=hsf.getBeneficiaryType();
        String clientEnrolledInCare=null;
        String enrolledOnART=null;
        String treatmentId=null;
        Date dateEnrolledOnTreatment=null;
        String facilityClientEnrolled=null;
        String dateOfNewStatus=hsf.getDateOfNewStatus();
        String currentHivStatus=hsf.getCurrentHivStatus();
        String newStatus=hsf.getNewHivStatus();
        
        if(hsf.getDateEnrolledOnTreatment() !=null && hsf.getDateEnrolledOnTreatment().indexOf("/") !=-1)
        dateEnrolledOnTreatment=DateManager.getDateInstance(DateManager.processMthDayYearToMysqlFormat(hsf.getDateEnrolledOnTreatment()));
        if(newStatus ==null || newStatus.equalsIgnoreCase("select"))
        {
            newStatus =currentHivStatus;
        }
        if(newStatus !=null && newStatus.equalsIgnoreCase("positive"))
        {
            clientEnrolledInCare=hsf.getEnrolledInCare();
            enrolledOnART=hsf.getEnrolledOnART();
            if(clientEnrolledInCare !=null && clientEnrolledInCare.equalsIgnoreCase("Yes"))
            {
                if(hsf.getOrganizationClientIsReferred() !=null && !hsf.getOrganizationClientIsReferred().equalsIgnoreCase("select"))
                facilityClientEnrolled=hsf.getOrganizationClientIsReferred();
                treatmentId=hsf.getTreatmentId();
                
            }
            if(enrolledOnART !=null && enrolledOnART.equalsIgnoreCase("Yes"))
            {
                if(hsf.getOrganizationClientIsReferred() !=null && !hsf.getOrganizationClientIsReferred().equalsIgnoreCase("select"))
                facilityClientEnrolled=hsf.getOrganizationClientIsReferred();
                treatmentId=hsf.getTreatmentId();
            }
        }
        List directoryList=new ArrayList();//hsf.getReferralDirectoryList();
        if(directoryList !=null)
        hsf.setReferralDirectoryList(directoryList);
        //if(lgaList !=null)
        //hsf.setLgaList(lgaList);
        String requiredAction=hsf.getActionName();
        String statusUpdate=request.getParameter("q");
        if(statusUpdate !=null)
        requiredAction=statusUpdate;
        //System.err.println("hsf.getCurrentHivStatus() is "+hsf.getCurrentHivStatus());
        List hivStatusList=HivRecordsManager.loadNewHivStatus(null);
        hsf.setHivStatusList(hivStatusList);
        String stateCode=hsf.getStateCode();
        States state=(States)session.getAttribute("state");
        if(state !=null)
        stateCode=state.getState_code();
        List facilityList=util.getReferralDirectoriesByState(stateCode);
        hsf.setReferralDirectoryList(facilityList);
        
        HivStatusUpdate hsu=new HivStatusUpdate();
        hsu.setClientEnrolledInCare(clientEnrolledInCare);
        hsu.setEnrolledOnART(enrolledOnART);
        hsu.setTreatmentId(treatmentId);
        hsu.setDateEnrolledOnTreatment(dateEnrolledOnTreatment);
        hsu.setClientId(beneficiaryId);
        hsu.setClientType("ovc");
        hsu.setDateModified(appUtil.getCurrentDate());
        hsu.setDateOfCurrentStatus(appUtil.processMthDayYearToMysqlFormat(dateOfNewStatus));
        hsu.setHivStatus(newStatus);
        hsu.setOrganizationClientIsReferred(facilityClientEnrolled);
        hsu.setRecordStatus("active");
        hsu.setPointOfUpdate("statusUpdate");
        request.setAttribute("beneficiaryType", beneficiaryType);
        String msg=" ";
        request.setAttribute("hsuHhUniqueId", hhUniqueId);
        request.setAttribute("beneficiaryId", beneficiaryId);
        
        loadup.getCbosPerLgaFromSetup(session, lgaCode);
        
        
        if(requiredAction==null)
        {
            hsf.reset(mapping, request);
            hsf.setBeneficiaryList(new ArrayList());
            //hsf.setHivStatusList(hivStatusList);
            return mapping.findForward("hivInformationUpdatePage");
        }
        else if(requiredAction.equalsIgnoreCase("su"))
        {
            hsf.reset(mapping, request);
            hsf.setBeneficiaryList(new ArrayList());
            //hsf.setHivStatusList(hivStatusList);
            return mapping.findForward("inputPage");
        }
        
        else if(requiredAction.equalsIgnoreCase("cboList"))
        {
            List cboList=loadup.getCbosPerLgaFromSetup(session, lgaCode);
            if(cboList !=null)
            hsf.setCboList(cboList);
            hsf.reset(mapping, request);
            hsf.setLgaCode(lgaCode);
            //hsf.setHhSerialNo(serialNo);
            return mapping.findForward("inputPage");
        }
        else if(requiredAction.equalsIgnoreCase("beneficiaryList"))
        { 
            request.setAttribute("beneficiaryId", " ");
            HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
            HouseholdEnrollment hhe=hhedao.getHouseholdEnrollment(hhUniqueId);
            HivStatusUpdateDao hsudao=new HivStatusUpdateDaoImpl();
            List beneficiaryList=new ArrayList();
            if(beneficiaryType!=null)
            {
                beneficiaryList=hsudao.getBeneficiaryListPerHousehold(beneficiaryType, hhUniqueId);
                setBeneficiaryInformation(request,beneficiaryType,beneficiaryList);
                hsf.reset(mapping, request);
                hsf.setHhSerialNo(serialNo);
                hsf.setHhUniqueId(hhUniqueId);
                if(hhe !=null)
                {
                    hsf.setBeneficiaryList(beneficiaryList);
                    hsf.setWard(util.getWardName(hhe.getCommunityCode()));   
                }
            }
           return mapping.findForward("inputPage");
        }
        else if(requiredAction.equalsIgnoreCase("baselineDetails"))
        {
            HivStatusUpdateDao hsudao=new HivStatusUpdateDaoImpl();
            HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
            HouseholdEnrollment hhe=hhedao.getHouseholdEnrollment(hhUniqueId);
            hsf.reset(mapping, request);
            hsf.setHhSerialNo(serialNo);
            hsf.setLgaCode(lgaCode);
            hsf.setHhUniqueId(hhUniqueId);
            hsf.setBeneficiaryId(beneficiaryId);
            currentHivStatus=hsf.getCurrentHivStatus();
            System.err.println("requiredAction is "+requiredAction);
            
            if(hhe !=null)
            {
                hsf.setWard(util.getWardName(hhe.getCommunityCode()));  
            }
            if(beneficiaryType!=null)
            {  
                List beneficiaryList=hsudao.getBeneficiaryListPerHousehold(beneficiaryType, hhUniqueId);
                setBeneficiaryInformation(request,beneficiaryType,beneficiaryList);
                hsf.setBeneficiaryList(beneficiaryList);
                
                if(beneficiaryType.equalsIgnoreCase("ovc"))
                {
                    OvcDao dao=new OvcDaoImpl();
                    hsf.setBeneficiaryList(dao.getOvcPerHousehold(hhUniqueId));
                    hsf.setBeneficiaryId(beneficiaryId);
                    Ovc ovc=dao.getOvc(beneficiaryId);  
                    if(ovc !=null)
                    {
                        hsf.setAddress(ovc.getAddress());
                        hsf.setAge(ovc.getCurrentAge());
                        hsf.setAgeUnit(ovc.getCurrentAgeUnit());
                        String hivStatusName=ovc.getHivStatus();
                        String dateOfCurrentStatus=null;
                        HivStatusUpdate activeHsu=ovc.getActiveHivStatus();
                        if(activeHsu !=null)
                        {
                            currentHivStatus=activeHsu.getHivStatus();
                            dateOfCurrentStatus=activeHsu.getDateOfCurrentStatus();
                            hsf.setEnrolledInCare(activeHsu.getClientEnrolledInCare());
                            hsf.setEnrolledOnART(activeHsu.getEnrolledOnART());
                        }
                        //currentHivStatus=ovc.getCurrentHivStatus().getHivStatus();
                        System.err.println("currentHivStatus 1 in type OVC is "+currentHivStatus);
                        HivStatus hivstatus=HivRecordsManager.getHivStatus(currentHivStatus);
                        if(hivstatus !=null)
                        hivStatusName=hivstatus.getHivStatusName();
                        hsf.setNewHivStatus(currentHivStatus);
                        hsf.setCurrentHivStatus(hivStatusName);
                        hsf.setDateOfNewStatus(dm.convertMysqlDateTomonthDayYear(dateOfCurrentStatus));
                        hsf.setGender(ovc.getGender());
                        hsf.setPhone(ovc.getPhone());
                        if(currentHivStatus==null)
                        currentHivStatus=hivStatusName;
                        System.err.println("currentHivStatus in type OVC is "+currentHivStatus);
                    }
                }
                else if(beneficiaryType.equalsIgnoreCase(NomisConstant.Caregiver_TYPE))
                {
                    CaregiverDao cgiverdao=new CaregiverDaoImpl();
                    Caregiver cgiver=cgiverdao.getCaregiverByCaregiverId(beneficiaryId);
                    hsf.setAddress(cgiver.getCaregiverAddress());
                    hsf.setAge(cgiver.getCurrentAge());
                    hsf.setAgeUnit("Year");
                    String dateOfCurrentStatus=null;
                    HivStatusUpdate activeHsu=cgiver.getActiveHivStatus();
                        if(activeHsu !=null)
                        {
                            currentHivStatus=activeHsu.getHivStatus();
                            dateOfCurrentStatus=activeHsu.getDateOfCurrentStatus();
                            hsf.setEnrolledInCare(activeHsu.getClientEnrolledInCare());
                            hsf.setEnrolledOnART(activeHsu.getEnrolledOnART());
                        }
                    //currentHivStatus=cgiver.getCurrentHivStatus().getHivStatus();
                    hsf.setCurrentHivStatus(currentHivStatus);
                    hsf.setNewHivStatus(currentHivStatus);
                    String hivStatusName=cgiver.getCurrentHivStatus().getHivStatus();
                    
                    HivStatus hivstatus=HivRecordsManager.getHivStatus(currentHivStatus);
                    if(hivstatus !=null)
                    hivStatusName=hivstatus.getHivStatusName();
                    hsf.setDateOfNewStatus(dm.convertMysqlDateTomonthDayYear(dateOfCurrentStatus));
                    hsf.setCurrentHivStatus(hivStatusName);
                    hsf.setGender(cgiver.getCaregiverGender());
                    hsf.setPhone(cgiver.getCaregiverPhone());
                    
                    System.err.println("currentHivStatus in type caregiver is "+currentHivStatus);
                }
            }
            hivStatusList=HivRecordsManager.loadNewHivStatus(null);
            hsf.setHivStatusList(hivStatusList);
            return mapping.findForward("inputPage");
        }
        else if(requiredAction.equalsIgnoreCase("save"))
        {
            HivStatusUpdateDao hsudao=new HivStatusUpdateDaoImpl();
            hsu.setTreatmentId(treatmentId);
            hsu.setDateEnrolledOnTreatment(dateEnrolledOnTreatment);
            hsudao.saveHivStatusUpdate(hsu);
            hsf.reset(mapping, request);
        }
        else if(requiredAction.equalsIgnoreCase("modify"))
        {
            HivStatusUpdateDao hsudao=new HivStatusUpdateDaoImpl();
            HivStatusUpdate currentHsu=hsudao.getActiveHivStatusUpdatesByClientId(hsu.getClientId());
            if(currentHsu !=null)
            {
                hsu.setRecordId(currentHsu.getRecordId());
                //hsu.setDateOfCurrentStatus(currentHsu.getDateOfCurrentStatus());
                if(hsu.getHivStatus()==null || hsu.getHivStatus().equalsIgnoreCase("select"))
                {
                    hsu.setHivStatus(currentHsu.getHivStatus());
                }
                hsu.setPointOfUpdate(currentHsu.getPointOfUpdate());
                hsu.setRecordStatus(currentHsu.getRecordStatus());
                hsu.setTreatmentId(treatmentId);
                hsu.setDateEnrolledOnTreatment(dateEnrolledOnTreatment);
                hsudao.updateActiveHivStatusUpdate(hsu);
                updateSourceHivInformation(hsu);
            }
            hsf.reset(mapping, request);
        }
        else if(requiredAction.equalsIgnoreCase("delete"))
        {
            HivStatusUpdateDao hsudao=new HivStatusUpdateDaoImpl();
            hsudao.deleteHivStatusUpdate(hsu);
            hsf.reset(mapping, request);
        }
        return mapping.findForward("inputPage");
    }
    private void setBeneficiaryInformation(HttpServletRequest request,String beneficiaryType,List beneficiaryList)
    {
        if(beneficiaryType.equalsIgnoreCase("ovc"))
        {
           request.setAttribute("ovcBeneficiaryList", beneficiaryList);
        }
        else if(beneficiaryType.equalsIgnoreCase("caregiver"))
        {
            request.setAttribute("caregiverBeneficiaryList", beneficiaryList);
        }          
    }
    private void updateSourceHivInformation(HivStatusUpdate hsu)
    {
        try
        {
            String pointOfUpdate=hsu.getPointOfUpdate();
            if(pointOfUpdate !=null)
            {
                DaoUtil util=new DaoUtil();
                if(pointOfUpdate.equalsIgnoreCase(NomisConstant.ENROLLMENT_POINTOFUPDATE))
                {
                    Ovc ovc=util.getOvcDaoInstance().getOvc(hsu.getClientId());
                    if(ovc !=null)
                    {
                        ovc.setHivStatus(hsu.getHivStatus());
                        ovc.setEnrolledInCare(hsu.getClientEnrolledInCare());
                        ovc.setEnrolledOnART(hsu.getEnrolledOnART());
                        ovc.setFacilityId(hsu.getOrganizationClientIsReferred());
                        ovc.setDateOfEntry(hsu.getDateModified());
                        util.getOvcDaoInstance().updateOvc(ovc, true, false);
                    }
                }
                else if(pointOfUpdate.equalsIgnoreCase(NomisConstant.SERVICE_POINTOFUPDATE))
                {
                    OvcService service=util.getOvcServiceDaoInstance().getOvcServiceForTheMth(hsu.getClientId(), hsu.getDateOfCurrentStatus());
                    if(service !=null)
                    {
                        service.setCurrentHivStatus(hsu.getHivStatus());
                        service.setEnrolledInCare(hsu.getClientEnrolledInCare());
                        service.setEnrolledOnART(hsu.getEnrolledOnART());
                        service.setFacilityId(hsu.getOrganizationClientIsReferred());
                        service.setDateOfEntry(hsu.getDateModified());
                        util.getOvcServiceDaoInstance().updateOvcService(service, true, false); 
                    }
                }
                else if(pointOfUpdate.equalsIgnoreCase(NomisConstant.FOLLOWUP_POINTOFUPDATE))
                {
                    FollowupSurvey fu=util.getFollowupDaoInstance().getFollowup(hsu.getClientId(), hsu.getDateOfCurrentStatus());
                    if(fu !=null)
                    {
                        fu.setUpdatedHivStatus(hsu.getHivStatus());
                        fu.setEnrolledInCare(hsu.getClientEnrolledInCare());
                        fu.setEnrolledOnART(hsu.getEnrolledOnART());
                        fu.setFacilityId(hsu.getOrganizationClientIsReferred());
                        fu.setDateOfEntry(hsu.getDateModified());
                        util.getFollowupDaoInstance().updateFollowup(fu, true, false); 
                    }
                }
                else if(pointOfUpdate.equalsIgnoreCase(NomisConstant.HHE_POINTOFUPDATE))
                {

                }
                else if(pointOfUpdate.equalsIgnoreCase(NomisConstant.HHS_POINTOFUPDATE))
                {

                }
                else if(pointOfUpdate.equalsIgnoreCase(NomisConstant.HHF_POINTOFUPDATE))
                {

                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
