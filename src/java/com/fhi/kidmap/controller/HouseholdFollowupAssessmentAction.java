/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.kidmap.business.HouseholdFollowupAssessment;
import com.fhi.kidmap.business.HouseholdVulnerabilityAssessment;
import com.fhi.nomis.nomisutils.LoadUpInfo;
import com.fhi.kidmap.business.OvcWithdrawal;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.HouseholdEnrollmentDao;
import com.fhi.kidmap.dao.HouseholdEnrollmentDaoImpl;
import com.fhi.kidmap.dao.HouseholdFollowupAssessmentDao;
import com.fhi.kidmap.dao.HouseholdFollowupAssessmentDaoImpl;
import com.fhi.kidmap.dao.HouseholdVulnerabilityAssessmentDao;
import com.fhi.kidmap.dao.HouseholdVulnerabilityAssessmentDaoImpl;
import com.fhi.kidmap.dao.OvcWithdrawalDao;
import com.fhi.kidmap.dao.OvcWithdrawalDaoImpl;
import com.fhi.nomis.nomisutils.DateManager;
import com.fhi.nomis.nomisutils.NomisConstant;
import com.fhi.nomis.nomisutils.ResourceManager;
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
public class HouseholdFollowupAssessmentAction extends org.apache.struts.action.Action {
    
    /* forward name="FORWARDPARAM" path="" */
    private static final String OLDFORM = "oldForm";
    private static final String REVISEDFORM = "revisedForm";
    private static String FORWARDPARAM = OLDFORM;
    
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
        HouseholdFollowupAssessmentForm hhForm=(HouseholdFollowupAssessmentForm)form;
        HouseholdFollowupAssessment hhfa=new HouseholdFollowupAssessment();
        HouseholdFollowupAssessmentDao hhfadao=new HouseholdFollowupAssessmentDaoImpl();
        HouseholdEnrollment hhe=new HouseholdEnrollment();
        
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        HouseholdVulnerabilityAssessment hhva=new HouseholdVulnerabilityAssessment();
        HouseholdVulnerabilityAssessmentDao hhvadao=new HouseholdVulnerabilityAssessmentDaoImpl();
        OvcWithdrawalDao withdrawaldao=new OvcWithdrawalDaoImpl();
        
        DaoUtil util=new DaoUtil();
        AppUtility appUtil=new AppUtility();
        HttpSession session=request.getSession();
        String currentUser=appUtil.getCurrentUser(session);
        String partnerCode=util.getPartnerCode(currentUser);
        String serialNo=hhForm.getSerialNo();
        String hhUniqueId=hhForm.getHhUniqueId();
        String address=hhForm.getAddress();
        String phone=hhForm.getPhone();
        String lgaCode=hhForm.getLgaCode();
        String occupation=hhForm.getOccupation();
        String initialDateOfAssessment=hhForm.getDateOfAssessment();
        String dateOfAssessment=appUtil.processMthDayYearToMysqlFormat(initialDateOfAssessment);
        String dateModified=appUtil.getCurrentDate();
        String recordId=hhForm.getRecordId();
        String orgCode=hhForm.getOrgCode();
        String communityCode=hhForm.getCommunityCode();
        int hhAge=hhForm.getHhAge();
        int assessmentId=hhForm.getAssessmentId();
        
        hhfa.setRecordId(recordId);
        hhfa.setHhUniqueId(hhUniqueId); 
        hhfa.setUpdatedAddress(address);
        hhfa.setDateModified(dateModified);
        hhfa.setDateOfAssessment(dateOfAssessment);
        hhfa.setHhAge(hhAge);
        hhfa.setNoOfChildrenInhh(hhForm.getNoOfChildrenInhh());
        hhfa.setNoOfPeopleInhh(hhForm.getNoOfPeopleInhh());
        hhfa.setOccupation(occupation);
        hhfa.setPhone(phone);
        hhfa.setHivStatus(hhForm.getHivStatus());
        hhfa.setEnrolledOnART(hhForm.getEnrolledOnART());
        hhfa.setFacilityId(hhForm.getFacilityId());
        hhfa.setPartnerCode(partnerCode);
        hhfa.setRecordedBy(currentUser);
        
        //hhfa.setDateOfEntry(dateModified);
        
        hhfa.setEducationLevel(hhForm.getEducationLevel());
        hhfa.setFoodSecurityAndNutrition(hhForm.getFoodSecurityAndNutrition());
        hhfa.setHealth(hhForm.getHealth());
        hhfa.setHhHeadship(hhForm.getHhHeadship());
        hhfa.setHhIncome(hhForm.getHhIncome());
        hhfa.setMeansOfLivelihood(hhForm.getMeansOfLivelihood());
        hhfa.setShelterAndHousing(hhForm.getShelterAndHousing());
        hhfa.setProtection(hhForm.getProtection());
        hhfa.setNameOfAssessor(hhForm.getNameOfAssessor());
        hhfa.setDesignation(hhForm.getDesignation());
        int vulnerabilityScore=hhvadao.getHouseholdVulnerabilityScore(hhva);
        hhfa.setVulnerabilityScore(vulnerabilityScore);
        
        hhva.setDateOfAssessment(dateOfAssessment);
        hhva.setDateOfEntry(dateModified);
        hhva.setHhUniqueId(hhUniqueId);
        hhva.setEducationLevel(hhForm.getEducationLevel());
        hhva.setFoodSecurityAndNutrition(hhForm.getFoodSecurityAndNutrition());
        hhva.setHealth(hhForm.getHealth());
        hhva.setHhHeadship(hhForm.getHhHeadship());
        hhva.setHhIncome(hhForm.getHhIncome());
        hhva.setMeansOfLivelihood(hhForm.getMeansOfLivelihood());
        hhva.setShelterAndHousing(hhForm.getShelterAndHousing());
        hhva.setNameOfAssessor(hhForm.getNameOfAssessor());
        hhva.setDesignation(hhForm.getDesignation());
        vulnerabilityScore=hhvadao.getHouseholdVulnerabilityScore(hhva);
        hhva.setVulnerabilityScore(vulnerabilityScore);
        hhva.setRecordedBy(appUtil.getCurrentUser(session));
        
        String requiredAction=hhForm.getActionName();
        String withdrawalMsg=" ";
        session.setAttribute("hhfuSaveBtnDisabled", "false");
        session.setAttribute("hhfuModifyBtnDisabled", "true");
        request.setAttribute("withdrawalMsg", withdrawalMsg);
        session.setAttribute("hhUniqueId", hhUniqueId);
        
        if(ResourceManager.isRevisedHheForm())
         FORWARDPARAM=REVISEDFORM;
         else
         FORWARDPARAM = OLDFORM;
        if(requiredAction==null)
        {
            LoadUpInfo loadup=new LoadUpInfo();
            loadup.getStartupInfo(session);
            session.setAttribute("hhUniqueId", " ");
            return mapping.findForward(FORWARDPARAM);
        }
        else if(requiredAction.equalsIgnoreCase("cboList"))
        {
            LoadUpInfo loadup=new LoadUpInfo();
            loadup.getCbosPerLgaFromSetup(session,lgaCode);
            hhForm.reset(mapping, request);
            hhForm.setLgaCode(lgaCode);
            return mapping.findForward(FORWARDPARAM);
        }
        else if(requiredAction.equalsIgnoreCase("hhdetails"))
        {
            /*OvcWithdrawal withdrawal=withdrawaldao.getWithdrawal(hhUniqueId);
            if(withdrawal !=null)
            {
                withdrawalMsg="This household has been withdrawn";
                request.setAttribute("withdrawalMsg", withdrawalMsg);
                return mapping.findForward(FORWARDPARAM);
            }*/
            hhe=hhedao.getHouseholdEnrollment(hhUniqueId);
            hhForm.reset(mapping, request);
            hhForm.setSerialNo(serialNo);
            hhForm.setHhUniqueId(hhUniqueId);
            hhForm.setLgaCode(lgaCode);
            hhForm.setOrgCode(orgCode);
            if(hhe==null)
            {
                request.setAttribute("withdrawalMsg", "Household with this unique Id does not exist");
                session.setAttribute("hhfuSaveBtnDisabled", "true");
                session.setAttribute("hhfuModifyBtnDisabled", "true");
                return mapping.findForward(FORWARDPARAM);
            }
            else
            {
                if(hhe.getBaselineAssessmentScore()<7)
                {
                    HouseholdVulnerabilityAssessment hva=util.getHouseholdVulnerabilityAssessmentDaoInstance().getHouseholdVulnerabilityAssessment(hhUniqueId, hhe.getDateOfAssessment());
                    if( hva==null || util.getHouseholdVulnerabilityAssessmentDaoInstance().getHouseholdVulnerabilityScore(hva) <NomisConstant.VULNERABLE_STARTVALUE)
                    {
                        request.setAttribute("withdrawalMsg", "Household has no baseline assessment, so cannot be followed up");
                        session.setAttribute("hhfuSaveBtnDisabled", "true");
                        session.setAttribute("hhfuModifyBtnDisabled", "true");
                        return mapping.findForward(FORWARDPARAM);
                    }
                }
                    
                    
                List list=hhfadao.getListOfAssessmentsPerHousehold(hhUniqueId);
                if(list !=null && !list.isEmpty())
                {
                    hhfa=(HouseholdFollowupAssessment)list.get(0);
                    hhForm.setAddress(hhfa.getUpdatedAddress());
                    hhForm.setOccupation(hhfa.getOccupation());
                    hhForm.setPhone(hhfa.getPhone());
                    hhForm.setNoOfChildrenInhh(hhfa.getNoOfChildrenInhh());
                    hhForm.setNoOfPeopleInhh(hhfa.getNoOfPeopleInhh());
                    hhForm.setHivStatus(hhfa.getHivStatus());
                    hhForm.setEnrolledOnART(hhfa.getEnrolledOnART());
                    hhForm.setFacilityId(hhfa.getFacilityId());
                }
                else
                {
                    hhForm.setAddress(hhe.getAddress());
                    hhForm.setOccupation(hhe.getOccupation());
                    hhForm.setPhone(hhe.getPhone());
                    hhForm.setNoOfChildrenInhh(hhe.getNoOfChildrenInhh());
                    hhForm.setNoOfPeopleInhh(hhe.getNoOfPeopleInhh());
                    hhForm.setHivStatus(hhe.getBaselineHivStatus());
                    hhForm.setEnrolledOnART(hhe.getEnrolledOnART());
                    hhForm.setFacilityId(hhe.getFacilityId());
                }
                hhForm.setHhAge(util.getCurrentAge(hhe.getDateOfAssessment(), hhe.getHhAge(),"Year"));
                hhForm.setHhFirstname(hhe.getHhFirstname());
                hhForm.setHhSurname(hhe.getHhSurname());
                hhForm.setHhGender(hhe.getHhGender());
                hhForm.setOrgCode(hhe.getOrgCode());
                hhForm.setCommunityCode(util.getWardName(hhe.getCommunityCode()));
            }
            return mapping.findForward(FORWARDPARAM);
        }
        else if(requiredAction.equalsIgnoreCase("hhFollowupDetails"))
        {
            OvcWithdrawal withdrawal=withdrawaldao.getWithdrawal(hhUniqueId);
            if(withdrawal !=null)
            {
                if(DateManager.compareDates(withdrawal.getDateOfWithdrawal(), dateOfAssessment))
                {
                    withdrawalMsg="This household was withdrawn from the program on an earlier date";
                    request.setAttribute("withdrawalMsg", withdrawalMsg);
                    session.setAttribute("hhfuSaveBtnDisabled", "true");
                }
                //return mapping.findForward(FORWARDPARAM);
            }
            String firstName=hhForm.getHhFirstname();
            String surName=hhForm.getHhSurname();
            String gender=hhForm.getHhGender();
            hhForm.reset(mapping, request);
            hhForm.setSerialNo(serialNo);
            hhForm.setHhUniqueId(hhUniqueId);
            hhForm.setHhFirstname(firstName);
            hhForm.setHhSurname(surName);
            hhForm.setAddress(address);
            hhForm.setPhone(phone);
            hhForm.setOccupation(occupation);
            hhForm.setHhGender(gender);
            hhForm.setHhAge(hhAge);
            hhForm.setOrgCode(orgCode);
            hhForm.setCommunityCode(communityCode);
            hhForm.setLgaCode(lgaCode);
            hhForm.setDateOfAssessment(initialDateOfAssessment);
            
            
            if(dateOfAssessment==null || dateOfAssessment.indexOf("-")==-1)
            return mapping.findForward(FORWARDPARAM);
            hhfa=hhfadao.getHouseholdFollowupAssessmentByIdAndDate(hhUniqueId, dateOfAssessment);
            if(hhfa !=null)
            {
                hhForm.setRecordId(hhfa.getRecordId());
                hhForm.setAddress(hhfa.getUpdatedAddress());
                hhForm.setOccupation(hhfa.getOccupation());
                hhForm.setPhone(hhfa.getPhone());
                hhForm.setNoOfChildrenInhh(hhfa.getNoOfChildrenInhh());
                hhForm.setNoOfPeopleInhh(hhfa.getNoOfPeopleInhh());
                hhForm.setHivStatus(hhfa.getHivStatus());
                hhForm.setEnrolledOnART(hhfa.getEnrolledOnART());
                hhForm.setFacilityId(hhfa.getFacilityId());
                if(hhfa.getVulnerabilityScore()>0)
                {
                    hhForm.setNameOfAssessor(hhfa.getNameOfAssessor());
                    hhForm.setDesignation(hhfa.getDesignation());
                    hhForm.setEducationLevel(hhfa.getEducationLevel());
                    hhForm.setFoodSecurityAndNutrition(hhfa.getFoodSecurityAndNutrition());
                    hhForm.setHealth(hhfa.getHealth());
                    hhForm.setHhHeadship(hhfa.getHhHeadship());
                    hhForm.setHhIncome(hhfa.getHhIncome());
                    hhForm.setMeansOfLivelihood(hhfa.getMeansOfLivelihood());
                    hhForm.setShelterAndHousing(hhfa.getShelterAndHousing());
                    hhForm.setProtection(hhfa.getProtection());
                    //hhForm.setAssessmentId(hhfa.getId());
                    hhForm.setNameOfAssessor(hhfa.getNameOfAssessor());
                    hhForm.setDesignation(hhfa.getDesignation());
                }
                else
                {
                    hhva=hhvadao.getHouseholdVulnerabilityAssessment(hhUniqueId, dateOfAssessment);
                    if(hhva !=null)
                    {
                        hhForm.setNameOfAssessor(hhva.getNameOfAssessor());
                        hhForm.setDesignation(hhva.getDesignation());
                        hhForm.setEducationLevel(hhva.getEducationLevel());
                        hhForm.setFoodSecurityAndNutrition(hhva.getFoodSecurityAndNutrition());
                        hhForm.setHealth(hhva.getHealth());
                        hhForm.setHhHeadship(hhva.getHhHeadship());
                        hhForm.setHhIncome(hhva.getHhIncome());
                        hhForm.setMeansOfLivelihood(hhva.getMeansOfLivelihood());
                        hhForm.setShelterAndHousing(hhva.getShelterAndHousing());
                        hhForm.setAssessmentId(hhva.getId());
                        hhForm.setNameOfAssessor(hhva.getNameOfAssessor());
                        hhForm.setDesignation(hhva.getDesignation());
                    }
                }
                session.setAttribute("hhfuSaveBtnDisabled", "true");
                session.setAttribute("hhfuModifyBtnDisabled", "false");
                return mapping.findForward(FORWARDPARAM);
            }
        }
        else if(requiredAction.equalsIgnoreCase("save"))
        {
            hhfadao.saveHouseholdFollowupAssessment(hhfa);
            hhvadao.saveHouseholdVulnerabilityAssessment(hhva);
            if(hhForm.getReasonWithdrawal() !=null)
            {
                saveWithdrawalInformation(hhfa,hhForm.getReasonWithdrawal(),hhvadao.getNextAssessmentNumber(hhfa.getHhUniqueId()));
            }
            hhForm.reset(mapping, request);
        }
        else if(requiredAction.equalsIgnoreCase("modify"))
        {
            hhfadao.updateHouseholdFollowupAssessment(hhfa);
            hhva.setId(assessmentId);
            System.err.println("hhfa.getRecordId() is "+hhfa.getRecordId());
            System.err.println("hhva id is "+hhva.getId());
            hhvadao.updateHouseholdVulnerabilityAssessmentById(hhva);
            if(hhForm.getReasonWithdrawal() !=null)
            {
                saveWithdrawalInformation(hhfa,hhForm.getReasonWithdrawal(),hhvadao.getNextAssessmentNumber(hhfa.getHhUniqueId()));
            }
            hhForm.reset(mapping, request);
        }
        else if(requiredAction.equalsIgnoreCase("delete"))
        {
            hhfadao.deleteHouseholdFollowupAssessment(hhfa);
            hhvadao.deleteHouseholdVulnerabilityAssessment(hhva); 
            hhForm.reset(mapping, request);
        }
        return mapping.findForward(FORWARDPARAM);
    }
    private void saveWithdrawalInformation(HouseholdFollowupAssessment hhfa,String reasonWithdrawal,int assessmentNumber)
    {
        try
        {
            OvcWithdrawalDao withdrawaldao=new OvcWithdrawalDaoImpl();
            withdrawaldao.withdrawHousehold(hhfa.getHhUniqueId(), hhfa.getDateOfAssessment(), reasonWithdrawal, "household", assessmentNumber);
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
