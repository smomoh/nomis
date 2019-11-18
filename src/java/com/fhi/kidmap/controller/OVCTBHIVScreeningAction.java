/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.nomis.nomisutils.LoadUpInfo;
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.business.OvcTBHIVScreeningChecklist;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.HouseholdEnrollmentDao;
import com.fhi.kidmap.dao.HouseholdEnrollmentDaoImpl;
import com.fhi.kidmap.dao.OvcDao;
import com.fhi.kidmap.dao.OvcDaoImpl;
import com.fhi.kidmap.dao.OvcTBHIVScreeningChecklistDao;
import com.fhi.kidmap.dao.OvcTBHIVScreeningChecklistDaoImpl;
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
public class OVCTBHIVScreeningAction extends org.apache.struts.action.Action {

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
        OVCTBHIVScreeningForm tbhivForm=(OVCTBHIVScreeningForm)form;
        OvcTBHIVScreeningChecklistDao tdhivscdao=new OvcTBHIVScreeningChecklistDaoImpl();
        DaoUtil util=new DaoUtil();
        AppUtility appUtil=new AppUtility();
        OvcDao ovcDao=new OvcDaoImpl();
        OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl();
        Ovc ovc=new Ovc();
        LoadUpInfo loadup=new LoadUpInfo();
        List ovcList=new ArrayList();
        String requiredAction=tbhivForm.getActionName();
        HttpSession session=request.getSession();
        int hhSerialNo=tbhivForm.getHhSerialNo();
        String hhUniqueId=tbhivForm.getHhUniqueId();
        String lgaCode=tbhivForm.getLgaCode();
        String selectedCbo=tbhivForm.getOrgCode();
        String communityCode=tbhivForm.getCommunityCode();
         String ovcId=tbhivForm.getOvcId();
         int id=tbhivForm.getId();
         int assessmentNo=tbhivForm.getAssessmentNo();
        OvcTBHIVScreeningChecklist tbHivScreening=new OvcTBHIVScreeningChecklist();
        tbHivScreening.setId(id);
        tbHivScreening.setOvcId(ovcId);
        tbHivScreening.setAssessmentNo(assessmentNo);
        String assessmentDate=tbhivForm.getDateOfAssessment();
        String mySqlDateOfAssessment=appUtil.processMthDayYearToMysqlFormat(assessmentDate);
        tbHivScreening.setDateOfAssessment(mySqlDateOfAssessment);
        tbHivScreening.setBeneficiariesHasSoresOrBleeding(tbhivForm.getBeneficiariesHasSoresOrBleeding());
        tbHivScreening.setCaregiverEnrolledInPMTCT(tbhivForm.getCaregiverEnrolledInPMTCT());
        tbHivScreening.setChildCoughing(tbhivForm.getChildCoughing());
        tbHivScreening.setChildAboveSixWksAndStartedOnSeptrin(tbhivForm.getChildAboveSixWksAndStartedOnSeptrin());
        tbHivScreening.setChildHavingFever(tbhivForm.getChildHavingFever());
        tbHivScreening.setChildHavingNightSweat(tbhivForm.getChildHavingNightSweat());
        tbHivScreening.setChildLossindWeight(tbhivForm.getChildLossindWeight());
        tbHivScreening.setChildOnSeptrin(tbhivForm.getChildOnSeptrin());
        tbHivScreening.setChildSleepInTreatedNet(tbhivForm.getChildSleepInTreatedNet());
        tbHivScreening.setChildSwellingOnNeck(tbhivForm.getChildSwellingOnNeck());
        tbHivScreening.setDosesOfARVMissedByChild(tbhivForm.getDosesOfARVMissedByChild());
        tbHivScreening.setDosesOfSeptrinMissedByChild(tbhivForm.getDosesOfSeptrinMissedByChild());
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
        request.setAttribute("adolescentAttribute", "false");
        request.setAttribute("hivStatusAttribute", "false");
        if(requiredAction==null)
        {
            loadup.getStartupInfo(session);
            session.setAttribute("ovcListInTbHivForm", ovcList);
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
                  //System.err.println("hhUniqueId is "+hhUniqueId);
                        HouseholdEnrollment hhe=hhedao.getHouseholdEnrollment(hhUniqueId);
                        tbhivForm.setLgaCode(lgaCode);
                        tbhivForm.setOrgCode(selectedCbo);
                        tbhivForm.setHhSerialNo(hhSerialNo);

                        if(hhe !=null)
                        {
                            hhUniqueIdMsg=hhUniqueId;
                            tbhivForm.setHhUniqueId(hhUniqueId);
                            tbhivForm.setCommunityCode(util.getWardName(hhe.getCommunityCode()));
                            ovcList=ovcDao.getOvcPerHousehold(hhUniqueId);
                            session.setAttribute("ovcListInTbHivForm", ovcList);
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
            List list=wdao.getWithdrawnOvc(ovcId);

            if(!list.isEmpty())
            {
                ovcWithdrawn="This VC has been withdrawn";
                request.setAttribute("ovcWithdrawn", ovcWithdrawn);
                return mapping.findForward("checklist");
            }
            List ovcDetailsList=ovcDao.getOVC(ovcId);
            if(!ovcDetailsList.isEmpty())
            {
                ovc=(Ovc)ovcDetailsList.get(0);
                form.reset(mapping, request);
                //setControlStatus(ovc, request);               
                setBaselineDetails(ovc, tbhivForm,communityCode,hhSerialNo);
                int currentAge=util.getCurrentAge(ovc);
                if(currentAge > 17)
                request.setAttribute("currentAge", "overaged;"+currentAge);
                else
                request.setAttribute("currentAge", "underaged;"+currentAge);
                if(currentAge <10)
                request.setAttribute("adolescentAttribute", "true");
                if(ovc.getActiveHivStatus().getHivStatus().equalsIgnoreCase("positive"))
                request.setAttribute("hivStatusAttribute", "true");
                tbhivForm.setActionName(null);
                tbhivForm.setLgaCode(lgaCode);
                tbhivForm.setOrgCode(selectedCbo);
                session.setAttribute("vcUniqueId", ovc.getOvcId());
                return mapping.findForward("checklist");
            }
            tbhivForm.setLgaCode(lgaCode);
            tbhivForm.setOrgCode(selectedCbo);
            tbhivForm.setCommunityCode(communityCode);            
            //setBtnState(session,"false","true");
            session.setAttribute("ovcWithdrawn", "This VC does not exist in the enrolment record");
            return mapping.findForward("checklist");
        }
        else if(requiredAction.equalsIgnoreCase("assessmentDetails"))
        {
            ovc=ovcDao.getOvc(ovcId);
            setBtnState(session, "false", "true");
            form.reset(mapping, request);
            if(ovc !=null)
            {
                tbHivScreening=tdhivscdao.getOvcTBHIVScreeningChecklist(ovcId, mySqlDateOfAssessment);
                int currentAge=util.getCurrentAge(ovc);
                tbhivForm.setOrgCode(selectedCbo);
                tbhivForm.setCommunityCode(communityCode);
                tbhivForm.setOvcAge(currentAge);
                tbhivForm.setOvcGender(ovc.getGender());
                tbhivForm.setAgeUnit(ovc.getAgeUnit());
                tbhivForm.setOvcId(ovcId);
                tbhivForm.setHhSerialNo(hhSerialNo);
                tbhivForm.setDateOfAssessment(assessmentDate);
                if(currentAge <10)
                request.setAttribute("adolescentAttribute", "true");
                if(ovc.getActiveHivStatus().getHivStatus().equalsIgnoreCase("positive"))
                request.setAttribute("hivStatusAttribute", "true");
            }
            if(tbHivScreening !=null)
            {
                tbhivForm.setAssessmentNo(tbHivScreening.getAssessmentNo());
                tbhivForm.setId(tbHivScreening.getId());
                tbhivForm.setOvcId(tbHivScreening.getOvcId());
                tbhivForm.setBeneficiariesHasSoresOrBleeding(tbHivScreening.getBeneficiariesHasSoresOrBleeding());
                tbhivForm.setCaregiverEnrolledInPMTCT(tbHivScreening.getCaregiverEnrolledInPMTCT());
                tbhivForm.setChildAboveSixWksAndStartedOnSeptrin(tbHivScreening.getChildAboveSixWksAndStartedOnSeptrin());
                tbhivForm.setChildCoughing(tbHivScreening.getChildCoughing());
                tbhivForm.setChildHavingFever(tbHivScreening.getChildHavingFever());
                tbhivForm.setChildHavingNightSweat(tbHivScreening.getChildHavingNightSweat());
                tbhivForm.setChildLossindWeight(tbHivScreening.getChildLossindWeight());
                tbhivForm.setChildOnSeptrin(tbHivScreening.getChildOnSeptrin());
                tbhivForm.setChildSleepInTreatedNet(tbHivScreening.getChildSleepInTreatedNet());
                tbhivForm.setChildSwellingOnNeck(tbHivScreening.getChildSwellingOnNeck());
                tbhivForm.setDesignation(tbHivScreening.getDesignation());
                tbhivForm.setDosesOfARVMissedByChild(tbHivScreening.getDosesOfARVMissedByChild());
                tbhivForm.setDosesOfSeptrinMissedByChild(tbHivScreening.getDosesOfSeptrinMissedByChild());
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
            tdhivscdao.saveOvcTBHIVScreeningChecklist(tbHivScreening);
            tbhivForm.reset(mapping, request);
        }
        else if(requiredAction.equalsIgnoreCase("modify"))
        {
            tdhivscdao.updateOvcTBHIVScreeningChecklist(tbHivScreening);
            tbhivForm.reset(mapping, request);
        }
        else if(requiredAction.equalsIgnoreCase("delete"))
        {
            tdhivscdao.deleteOvcTBHIVScreeningChecklist(tbHivScreening);
            tbhivForm.reset(mapping, request);
        }
        return mapping.findForward("checklist");
    }
    
    private void setBtnState(HttpSession session, String saveState, String modifyState)
    {
        session.setAttribute("childtbhivSaveDisabled", saveState);
        session.setAttribute("childtbhivModifyDisabled", modifyState);
    }
    private void setBaselineDetails(Ovc ovc,OVCTBHIVScreeningForm tbhivForm,String communityCode, int hhSerialNo)
    {
        if(ovc !=null)
        {
            tbhivForm.setOvcId(ovc.getOvcId());
            tbhivForm.setOvcAge(ovc.getCurrentAge());
            tbhivForm.setAgeUnit(ovc.getAgeUnit());
            tbhivForm.setOvcGender(ovc.getGender());
            tbhivForm.setCommunityCode(communityCode);
            tbhivForm.setHhSerialNo(hhSerialNo);
        }
    }
}
