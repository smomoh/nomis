/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.Caregiver;
import com.fhi.kidmap.business.HivStatus;
import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.kidmap.business.States;
import com.fhi.nomis.nomisutils.LoadUpInfo;
import com.fhi.kidmap.dao.CaregiverDao;
import com.fhi.kidmap.dao.CaregiverDaoImpl;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.HouseholdEnrollmentDao;
import com.fhi.kidmap.dao.HouseholdEnrollmentDaoImpl;
import com.fhi.kidmap.dao.LgaDao;
import com.fhi.kidmap.dao.LgaDaoImpl;
import com.fhi.nomis.OperationsManagement.HivRecordsManager;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author smomoh
 */
public class CaregiverAction extends org.apache.struts.action.Action {
    
    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    private String target="success";
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
        //This action manages caregiver information
        CaregiverForm cgiverForm=(CaregiverForm)form;
        CaregiverDao cgiverDao=new CaregiverDaoImpl();
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        DaoUtil util=new DaoUtil();
        Caregiver cgiver=new Caregiver();
        //LgaDao lgadao=new LgaDaoImpl();
        AppUtility appUtil=new AppUtility();
        HttpSession session=request.getSession();
//get the household that this caregiver belongs to
        String hhUniqueId=cgiverForm.getHhUniqueId();
        HouseholdEnrollment hhe=hhedao.getHouseholdEnrollment(hhUniqueId);
        int serialNo=cgiverForm.getSerialNo();
        String lgaCode=cgiverForm.getLgaCode();
        String caregiverId=cgiverForm.getCaregiverId();
        cgiver.setHhUniqueId(hhUniqueId);
        cgiver.setCaregiverId(caregiverId);
        cgiver.setCaregiverAddress(cgiverForm.getCaregiverAddress());
        cgiver.setCaregiverAge(cgiverForm.getCaregiverAge());
        cgiver.setCaregiverFirstname(cgiverForm.getCaregiverFirstname());
        cgiver.setCaregiverLastName(cgiverForm.getCaregiverLastName());
        cgiver.setCaregiverGender(cgiverForm.getCaregiverGender());
        cgiver.setCaregiverMaritalStatus(cgiverForm.getCaregiverMaritalStatus());
        cgiver.setEnrolledInCare(cgiverForm.getEnrolledInCare());
        cgiver.setEnrolledOnART(cgiverForm.getEnrolledOnART());
        cgiver.setFacilityId(cgiverForm.getOrganizationClientIsReferred());
        cgiver.setCgiverHivStatus(cgiverForm.getNewHivStatus());
        cgiver.setCaregiverOccupation(cgiverForm.getCaregiverOccupation());
        cgiver.setCaregiverPhone(cgiverForm.getCaregiverPhone());
        if(hhe !=null)
        cgiver.setDateOfEnrollment(hhe.getDateOfAssessment());
        cgiver.setDateModified(appUtil.getCurrentDate());

        LoadUpInfo loadup=new LoadUpInfo();
        //HttpSession session=request.getSession();
        String requiredAction=cgiverForm.getActionName();
        //List lgaList=lgadao.getLgaList(appUtil.getCurrentState(session));//loadup.getLgasPerStateFromOrgRecords(appUtil.getCurrentState(session));
        //if(lgaList !=null)
        //cgiverForm.setLgaList(lgaList);
        request.setAttribute("hhServiceUniqueId", hhUniqueId);
        session.setAttribute("caregiverSaveBtnDisabled", "false");
        session.setAttribute("caregiverModifyBtnDisabled", "true");
        loadup.getStartupInfo(session);
        String formType=request.getParameter("formType");
        request.setAttribute("visible", "visible");
        if(formType==null)
        target="success";
        else if(formType.equalsIgnoreCase("span"))
        target="caregiverSpan";
        List hivStatusList=HivRecordsManager.loadNewHivStatus(cgiverForm.getCurrentHivStatus());
        String stateCode=cgiverForm.getStateCode();
        States state=(States)session.getAttribute("state");
        if(state !=null)
        stateCode=state.getState_code();
        List facilityList=util.getReferralDirectoriesByState(stateCode);
        cgiverForm.setReferralDirectoryList(facilityList);
        String msg=" ";
        loadup.getCbosPerLgaFromSetup(session, lgaCode);
        /*if(TaskManager.isDatabaseLocked())
        {
            msg="System busy. Kindly try again later";
            request.setAttribute("msg", msg);
            return mapping.findForward(target);
        }*/
        if(requiredAction==null)
        {
            cgiverForm.reset(mapping, request);
            cgiverForm.setHivStatusList(hivStatusList);
            return mapping.findForward(target);
        }
        else if(requiredAction.equalsIgnoreCase("refresh"))
        {
            cgiverForm.reset(mapping, request);
            return mapping.findForward(target);
        }
        else if(requiredAction.equalsIgnoreCase("cboList"))
        {
            List cboList=loadup.getCbosPerLgaFromSetup(session, lgaCode);
            //if(cboList !=null)
            //cgiverForm.setCboList(cboList);
            cgiverForm.reset(mapping, request);
            cgiverForm.setSerialNo(serialNo);
            cgiverForm.setLgaCode(lgaCode);
            return mapping.findForward(target);
        }
        else if(requiredAction.equalsIgnoreCase("caregiverList"))
        {
            if(hhe !=null)
            {
                cgiverForm.reset(mapping, request);
                cgiverForm.setWard(util.getWardName(hhe.getCommunityCode()));
                cgiverForm.setCaregiverList(cgiverDao.getListOfCaregiversFromSameHousehold(hhUniqueId));
                cgiverForm.setSerialNo(serialNo);
            }
           return mapping.findForward(target);
        }
        else if(requiredAction.equalsIgnoreCase("caregiverDetails"))
        {
            cgiver=cgiverDao.getCaregiverByCaregiverId(caregiverId);
            cgiverForm.reset(mapping, request);
            cgiverForm.setSerialNo(serialNo);
            cgiverForm.setCaregiverList(cgiverDao.getListOfCaregiversFromSameHousehold(hhUniqueId));
            if(cgiver !=null)
            {
                cgiverForm.setCaregiverAddress(cgiver.getCaregiverAddress());
                cgiverForm.setCaregiverAge(cgiver.getCaregiverAge());
                cgiverForm.setCaregiverFirstname(cgiver.getCaregiverFirstname());
                cgiverForm.setCaregiverGender(cgiver.getCaregiverGender());
                cgiverForm.setCaregiverLastName(cgiver.getCaregiverLastName());
                cgiverForm.setCaregiverMaritalStatus(cgiver.getCaregiverMaritalStatus());
                cgiverForm.setCaregiverOccupation(cgiver.getCaregiverOccupation());
                cgiverForm.setCaregiverPhone(cgiver.getCaregiverPhone());
                cgiverForm.setHhUniqueId(cgiver.getHhUniqueId());
                cgiverForm.setHouseholdhead(cgiver.getHouseholdhead());
                 /*String baselineHivStatusName=cgiver.getCgiverHivStatus();
                HivStatus baselineHivstatus=appUtil.getHivStatus(cgiver.getCurrentHivStatus().getHivStatus());
                if(baselineHivstatus !=null)
                baselineHivStatusName=baselineHivstatus.getHivStatusName();*/
                String activeHivStatusName=cgiver.getCgiverHivStatus();
                HivStatus activeHivstatus=HivRecordsManager.getHivStatus(cgiver.getActiveHivStatus().getHivStatus());
                if(activeHivstatus !=null)
                activeHivStatusName=activeHivstatus.getHivStatusName();
                cgiverForm.setCurrentHivStatus(activeHivStatusName);
                cgiverForm.setEnrolledInCare(cgiver.getEnrolledInCare());
                cgiverForm.setEnrolledOnART(cgiver.getEnrolledOnART());
                hivStatusList=HivRecordsManager.loadNewHivStatus(cgiver.getCurrentHivStatus().getHivStatus());
                cgiverForm.setHivStatusList(hivStatusList);
                session.setAttribute("caregiverSaveBtnDisabled", "true");
                session.setAttribute("caregiverModifyBtnDisabled", "false");
            }
            return mapping.findForward(target);
        }
        else if(requiredAction.equalsIgnoreCase("save"))
        {
            if(hhe !=null)
            {
                Caregiver duplicateCgiger=cgiverDao.getCaregiverByName(hhUniqueId, cgiverForm.getCaregiverFirstname(), cgiverForm.getCaregiverLastName());
                if(duplicateCgiger==null)
                {
                    cgiverDao.saveCaregiver(cgiver);
                }
                else
                {
                    ActionErrors errors = new ActionErrors();
                    errors.add(ActionErrors.GLOBAL_MESSAGE,
                    new ActionMessage("caregiver.already.exist", "Caregiver with same name already exist for this household"));
                    saveErrors(request, errors);
                    return mapping.findForward(target);
                }
            }
        }
        else if(requiredAction.equalsIgnoreCase("modify"))
        {
            if(hhe !=null)
            {
                Caregiver duplicateCgiger=cgiverDao.getCaregiverByName(hhUniqueId, cgiverForm.getCaregiverFirstname(), cgiverForm.getCaregiverLastName());
                if(duplicateCgiger!=null)
                {
                   if(duplicateCgiger.getCaregiverId().equalsIgnoreCase(cgiver.getCaregiverId()))
                   {
                       cgiverDao.updateCaregiver(cgiver); 
                   }
                   else
                   {
                        ActionErrors errors = new ActionErrors();
                        errors.add(ActionErrors.GLOBAL_MESSAGE,
                        new ActionMessage("caregiver.already.exist", "Caregiver with same name already exist for this household"));
                        saveErrors(request, errors);
                        return mapping.findForward(target);
                   }
                }
                else
                cgiverDao.updateCaregiver(cgiver);
            }
        }
        else if(requiredAction.equalsIgnoreCase("delete"))
        {
            if(hhe !=null)
            cgiverDao.deleteCaregiver(cgiver);
        }
        cgiverForm.reset(mapping, request);
        return mapping.findForward(target);
    }
}
