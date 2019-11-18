/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.Caregiver;
import com.fhi.kidmap.business.CaregiverTBHIVScreeningChecklist;
import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.nomis.nomisutils.LoadUpInfo;
import com.fhi.kidmap.dao.CaregiverDao;
import com.fhi.kidmap.dao.CaregiverDaoImpl;
import com.fhi.kidmap.dao.CaregiverTBHIVScreeningChecklistDao;
import com.fhi.kidmap.dao.CaregiverTBHIVScreeningChecklistDaoImpl;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.HouseholdEnrollmentDao;
import com.fhi.kidmap.dao.HouseholdEnrollmentDaoImpl;
import com.fhi.kidmap.dao.OvcWithdrawalDao;
import com.fhi.kidmap.dao.OvcWithdrawalDaoImpl;
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
public class CaregiverTBHIVScreeningAction extends org.apache.struts.action.Action {

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
            throws Exception {
       CaregiverTBHIVScreeningForm tbhivForm=(CaregiverTBHIVScreeningForm)form;
        CaregiverTBHIVScreeningChecklistDao tdhivscdao=new CaregiverTBHIVScreeningChecklistDaoImpl();
        DaoUtil util=new DaoUtil();
        AppUtility appUtil=new AppUtility();
        CaregiverDao cgiverdao=new CaregiverDaoImpl();
        OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl();
        //Ovc ovc=new Ovc();
        LoadUpInfo loadup=new LoadUpInfo();
        List caregiverList=new ArrayList();
        String requiredAction=tbhivForm.getActionName();
        HttpSession session=request.getSession();
        int hhSerialNo=tbhivForm.getHhSerialNo();
        String hhUniqueId=tbhivForm.getHhUniqueId();
        String lgaCode=tbhivForm.getLgaCode();
        String selectedCbo=tbhivForm.getOrgCode();
        String communityCode=tbhivForm.getCommunityCode();
         String caregiverId=tbhivForm.getCaregiverId();
         int id=tbhivForm.getId();
         int assessmentNo=tbhivForm.getAssessmentNo();
        CaregiverTBHIVScreeningChecklist tbHivScreening=new CaregiverTBHIVScreeningChecklist();
        tbHivScreening.setId(id);
        tbHivScreening.setCaregiverId(caregiverId);
        tbHivScreening.setAssessmentNo(assessmentNo);
        String assessmentDate=tbhivForm.getDateOfAssessment();
        String mySqlDateOfAssessment=appUtil.processMthDayYearToMysqlFormat(assessmentDate);
        tbHivScreening.setDateOfAssessment(mySqlDateOfAssessment);
        tbHivScreening.setBeneficiariesHasSoresOrBleeding(tbhivForm.getBeneficiariesHasSoresOrBleeding());
        tbHivScreening.setCaregiverEnrolledInPMTCT(tbhivForm.getCaregiverEnrolledInPMTCT());
        tbHivScreening.setCaregiverCoughing(tbhivForm.getCaregiverCoughing());
        tbHivScreening.setChildAboveSixWksAndStartedOnSeptrin(tbhivForm.getChildAboveSixWksAndStartedOnSeptrin());
        tbHivScreening.setCaregiverHavingFever(tbhivForm.getCaregiverHavingFever());
        tbHivScreening.setCaregiverHavingNightSweat(tbhivForm.getCaregiverHavingNightSweat());
        tbHivScreening.setCaregiverLossindWeight(tbhivForm.getCaregiverLossindWeight());
        tbHivScreening.setCaregiverOnSeptrin(tbhivForm.getCaregiverOnSeptrin());
        tbHivScreening.setCaregiverSleepInTreatedNet(tbhivForm.getCaregiverSleepInTreatedNet());
        tbHivScreening.setCaregiverSwellingOnNeck(tbhivForm.getCaregiverSwellingOnNeck());
        tbHivScreening.setDosesOfARVMissedByCaregiver(tbhivForm.getDosesOfARVMissedByCaregiver());
        tbHivScreening.setDosesOfSeptrinMissedByCaregiver(tbhivForm.getDosesOfSeptrinMissedByCaregiver());
        tbHivScreening.setEidTestingDoneForChild(tbhivForm.getEidTestingDoneForChild());
        tbHivScreening.setFamilyMemberHadTB(tbhivForm.getFamilyMemberHadTB());
        tbHivScreening.setHivStatusDisclosedToPartner(tbhivForm.getHivStatusDisclosedToPartner());
        tbHivScreening.setHivStatusOfPartnerKnown(tbhivForm.getHivStatusOfPartnerKnown());
        tbHivScreening.setNumberOfClubsAttndedInThreeMths(tbhivForm.getNumberOfClubsAttndedInThreeMths());
        tbHivScreening.setVolunteerName(tbhivForm.getVolunteerName());
        tbHivScreening.setDesignation(tbhivForm.getDesignation());
        tbHivScreening.setDateModified(appUtil.getCurrentDate());
         
        String hhUniqueIdMsg=" ";
        String ovcWithdrawn=" ";
        request.setAttribute("ovcWithdrawn", ovcWithdrawn);
        request.setAttribute("cgiverhivStatusAttribute", "false");
        if(requiredAction==null)
        {
            loadup.getStartupInfo(session);
            session.setAttribute("caregiverListInTbHivForm", caregiverList);
            session.setAttribute("vcHhUniqueId", hhUniqueIdMsg);
            session.setAttribute("vcUniqueId", "");
            setBtnState(session, "false", "true");
            return mapping.findForward("checklist");
        }//
        else if(requiredAction.equalsIgnoreCase("cboList"))
        {
            tbhivForm.reset(mapping, request);
            loadup.getCbosPerLgaFromSetup(session,lgaCode);
            tbhivForm.setLgaCode(lgaCode);
            return mapping.findForward("checklist");
        }
        else if(requiredAction.equalsIgnoreCase("refresh"))
        {
            form.reset(mapping, request);
            tbhivForm.setOrgCode(selectedCbo);
            tbhivForm.setLgaCode(lgaCode);
            setBtnState(session,"false","true");
            return mapping.findForward("checklist");
        }
        else if(requiredAction.equalsIgnoreCase("hhDetails"))
        {
            HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
            tbhivForm.reset(mapping, request);
              try
              {
                  System.err.println("hhUniqueId is "+hhUniqueId);
                        HouseholdEnrollment hhe=hhedao.getHouseholdEnrollment(hhUniqueId);
                        tbhivForm.setLgaCode(lgaCode);
                        tbhivForm.setOrgCode(selectedCbo);
                        tbhivForm.setHhSerialNo(hhSerialNo);

                        if(hhe !=null)
                        {
                            hhUniqueIdMsg=hhUniqueId;
                            tbhivForm.setHhUniqueId(hhUniqueId);
                            tbhivForm.setCommunityCode(util.getWardName(hhe.getCommunityCode()));
                            caregiverList=cgiverdao.getListOfCaregiversFromSameHousehold(hhUniqueId);
                            session.setAttribute("caregiverListInTbHivForm", caregiverList);
                        }
              }
              catch(Exception ex)
              {
                  ex.printStackTrace();
              }
              session.setAttribute("vcHhUniqueId", hhUniqueIdMsg);
              return mapping.findForward("checklist");
        }
        else if(requiredAction.equalsIgnoreCase("baselineDetails"))
        {
            ovcWithdrawn=" ";
            tbhivForm.setHhSerialNo(hhSerialNo);
            hhUniqueIdMsg=tbhivForm.getHhUniqueId();
            setBtnState(session, "false", "true");
            session.setAttribute("vcHhUniqueId", hhUniqueIdMsg);
            
            List list=wdao.getWithdrawnOvc(caregiverId);

            if(!list.isEmpty())
            {
                ovcWithdrawn="This VC has been withdrawn";
                request.setAttribute("ovcWithdrawn", ovcWithdrawn);
                return mapping.findForward("checklist");
            }
            Caregiver cgiver=cgiverdao.getCaregiverByCaregiverId(caregiverId);
            if(cgiver !=null)
            {
                form.reset(mapping, request);
                //setControlStatus(ovc, request);               
                setBaselineDetails(cgiver, tbhivForm,communityCode,hhSerialNo);
                if(cgiver.getActiveHivStatus().getHivStatus().equalsIgnoreCase("positive"))
                request.setAttribute("cgiverhivStatusAttribute", "true");
                int currentAge=util.getCurrentAge(cgiver.getDateOfEnrollment(), cgiver.getCaregiverAge(),"Year");
                tbhivForm.setAge(currentAge);
                tbhivForm.setActionName(null);
                tbhivForm.setLgaCode(lgaCode);
                tbhivForm.setOrgCode(selectedCbo);
                session.setAttribute("vcUniqueId", cgiver.getCaregiverId());
                return mapping.findForward("checklist");
            }
            tbhivForm.setLgaCode(lgaCode);
            tbhivForm.setOrgCode(selectedCbo);
            tbhivForm.setCommunityCode(communityCode);            
            //setBtnState(session,"false","true");
            session.setAttribute("ovcWithdrawn", "This Caregiver does not exist in the enrolment record");
            return mapping.findForward("checklist");
        }
        else if(requiredAction.equalsIgnoreCase("assessmentDetails"))
        {
            Caregiver cgiver=cgiverdao.getCaregiverByCaregiverId(caregiverId);
            setBtnState(session, "false", "true");
            form.reset(mapping, request);
            if(cgiver !=null)
            {
                tbHivScreening=tdhivscdao.getCaregiverTBHIVScreeningChecklist(caregiverId, mySqlDateOfAssessment);
                
                tbhivForm.setOrgCode(selectedCbo);
                tbhivForm.setCommunityCode(communityCode);
                tbhivForm.setAge(util.getCurrentAge(cgiver.getDateOfEnrollment(), cgiver.getCaregiverAge(),"Year"));
                tbhivForm.setGender(cgiver.getCaregiverGender());
                tbhivForm.setCaregiverId(caregiverId);
                tbhivForm.setHhSerialNo(hhSerialNo);
                tbhivForm.setDateOfAssessment(assessmentDate);
                if(cgiver.getActiveHivStatus().getHivStatus().equalsIgnoreCase("positive"))
                request.setAttribute("cgiverhivStatusAttribute", "true");
            }
            if(tbHivScreening !=null)
            {
                tbhivForm.setAssessmentNo(tbHivScreening.getAssessmentNo());
                tbhivForm.setId(tbHivScreening.getId());
                tbhivForm.setCaregiverId(tbHivScreening.getCaregiverId());
                tbhivForm.setBeneficiariesHasSoresOrBleeding(tbHivScreening.getBeneficiariesHasSoresOrBleeding());
                tbhivForm.setCaregiverEnrolledInPMTCT(tbHivScreening.getCaregiverEnrolledInPMTCT());
                tbhivForm.setChildAboveSixWksAndStartedOnSeptrin(tbHivScreening.getChildAboveSixWksAndStartedOnSeptrin());
                tbhivForm.setCaregiverCoughing(tbHivScreening.getCaregiverCoughing());
                tbhivForm.setCaregiverHavingFever(tbHivScreening.getCaregiverHavingFever());
                tbhivForm.setCaregiverHavingNightSweat(tbHivScreening.getCaregiverHavingNightSweat());
                tbhivForm.setCaregiverLossindWeight(tbHivScreening.getCaregiverLossindWeight());
                tbhivForm.setCaregiverOnSeptrin(tbHivScreening.getCaregiverOnSeptrin());
                tbhivForm.setCaregiverSleepInTreatedNet(tbHivScreening.getCaregiverSleepInTreatedNet());
                tbhivForm.setCaregiverSwellingOnNeck(tbHivScreening.getCaregiverSwellingOnNeck());
                tbhivForm.setDesignation(tbHivScreening.getDesignation());
                tbhivForm.setDosesOfARVMissedByCaregiver(tbHivScreening.getDosesOfARVMissedByCaregiver());
                tbhivForm.setDosesOfSeptrinMissedByCaregiver(tbHivScreening.getDosesOfSeptrinMissedByCaregiver());
                tbhivForm.setEidTestingDoneForChild(tbHivScreening.getEidTestingDoneForChild());
                tbhivForm.setFamilyMemberHadTB(tbHivScreening.getFamilyMemberHadTB());
                tbhivForm.setHivStatusDisclosedToPartner(tbHivScreening.getHivStatusDisclosedToPartner());
                tbhivForm.setHivStatusOfPartnerKnown(tbHivScreening.getHivStatusOfPartnerKnown());
                tbhivForm.setNumberOfClubsAttndedInThreeMths(tbHivScreening.getNumberOfClubsAttndedInThreeMths());
                tbhivForm.setVolunteerName(tbHivScreening.getVolunteerName());
                setBtnState(session,"true","false");
            }
            return mapping.findForward("checklist");
        }
        else if(requiredAction.equalsIgnoreCase("save"))
        {
            tdhivscdao.saveCaregiverTBHIVScreeningChecklist(tbHivScreening);
            tbhivForm.reset(mapping, request);
        }
        else if(requiredAction.equalsIgnoreCase("modify"))
        {
            tdhivscdao.updateCaregiverTBHIVScreeningChecklist(tbHivScreening);
            tbhivForm.reset(mapping, request);
        }
        else if(requiredAction.equalsIgnoreCase("delete"))
        {
            tdhivscdao.deleteCaregiverTBHIVScreeningChecklist(tbHivScreening);
            tbhivForm.reset(mapping, request);
        }
        return mapping.findForward("checklist");
    }
    
    private void setBtnState(HttpSession session, String saveState, String modifyState)
    {
        session.setAttribute("caregivertbhivSaveDisabled", saveState);
        session.setAttribute("caregivertbhivModifyDisabled", modifyState);
    }
    private void setBaselineDetails(Caregiver caregiver,CaregiverTBHIVScreeningForm tbhivForm,String communityCode, int hhSerialNo)
    {
        if(caregiver !=null)
        {
            tbhivForm.setCaregiverId(caregiver.getCaregiverId());
            tbhivForm.setAge(caregiver.getCurrentAge());
            tbhivForm.setGender(caregiver.getCaregiverGender());
            tbhivForm.setCommunityCode(communityCode);
            tbhivForm.setHhSerialNo(hhSerialNo);
        }
    }
}
