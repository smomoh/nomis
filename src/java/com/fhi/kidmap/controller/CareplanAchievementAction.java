/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import com.fhi.kidmap.business.CareplanAchievement;
import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.kidmap.business.OvcWithdrawal;
import com.fhi.kidmap.dao.CareplanAchievementDao;
import com.fhi.kidmap.dao.CareplanAchievementDaoImpl;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.HouseholdEnrollmentDao;
import com.fhi.kidmap.dao.HouseholdEnrollmentDaoImpl;
import com.fhi.kidmap.dao.OvcWithdrawalDao;
import com.fhi.kidmap.dao.OvcWithdrawalDaoImpl;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.nomis.nomisutils.LoadUpInfo;
import com.fhi.nomis.nomisutils.DateManager;
import com.fhi.nomis.nomisutils.NomisConstant;
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
public class CareplanAchievementAction extends org.apache.struts.action.Action {

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
        CareplanAchievementForm cpaform=(CareplanAchievementForm)form;
        LoadUpInfo loadup=new LoadUpInfo();
            
        AppUtility appUtil=new AppUtility();
        DaoUtil util=new DaoUtil();
        HttpSession session=request.getSession();
        
        int hhSerialNo=cpaform.getHhSerialNo();
        String hhUniqueId=cpaform.getHhUniqueId();
        
        String lgaCode=cpaform.getLgaCode();
        String orgCode=cpaform.getOrgCode();
        String community=cpaform.getCommunity();
        
        String requiredAction=cpaform.getActionName();
        
        session.setAttribute("cpaModifyBtnDisabled", "true");
        session.setAttribute("cpaSaveBtnDisabled", "false");
        loadup.getCbosPerLgaFromSetup(session,cpaform.getLgaCode());
        String msg=" ";
        if(requiredAction==null)
        {
            cpaform.reset(mapping, request);
            loadStartupInfo(session);
            
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("cboList"))
        {
            loadup.getCbosPerLgaFromSetup(session,cpaform.getLgaCode());
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("hhDetails"))
        {
            cpaform.reset(mapping, request);
            OvcWithdrawalDao withdrawaldao=new OvcWithdrawalDaoImpl();
            OvcWithdrawal withdrawal=withdrawaldao.getWithdrawal(hhUniqueId);
            if(withdrawal !=null)
            {
                msg="This household has been withdrawn ("+withdrawal.getReasonWithdrawal()+")";
                request.setAttribute("msg", msg);
                return mapping.findForward(SUCCESS);
            }
            cpaform.setLgaCode(lgaCode);
            cpaform.setHhUniqueId(hhUniqueId);
            cpaform.setHhSerialNo(hhSerialNo);
            cpaform.setOrgCode(orgCode);
            cpaform.setCommunity(community);
            cpaform.setHhUniqueId(hhUniqueId);
            session.setAttribute("hhUniqueId", hhUniqueId);
            HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
            HouseholdEnrollment hhe=hhedao.getHouseholdEnrollment(hhUniqueId);
            if(hhe !=null)
            {
                hhe.setNoOfChildrenCurrentlyEnrolled(util.getOvcDaoInstance().getNumberOfOvcPerHousehold(hhUniqueId,true));
                hhe.setNoOfChildrenEverEnrolled(util.getOvcDaoInstance().getNumberOfOvcPerHousehold(hhUniqueId,false));
                cpaform.setAddress(hhe.getAddress());
                cpaform.setHhAge(hhe.getHhAge());
                cpaform.setDateOfEnrollment(appUtil.monthDayYear(hhe.getDateOfAssessment()));
                cpaform.setHhFirstname(hhe.getHhFirstname());
                cpaform.setHhSurname(hhe.getHhSurname());
                cpaform.setCommunity(util.getWardName(hhe.getCommunityCode()));
                //cpaform.setMaritalStatus(hhe.getMaritalStatus());
                cpaform.setOccupation(hhe.getOccupation());
                cpaform.setPhone(hhe.getPhone());
                cpaform.setHhGender(hhe.getHhGender());
                cpaform.setNoOfChildrenEnrolled(hhe.getNoOfChildrenEverEnrolled());
                cpaform=getCareplanAchievementForm(cpaform);               
             }
                session.setAttribute("hhUniqueId", hhUniqueId);
                return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("cpadetails"))
        {
            //cpaform.reset(mapping, request);
            OvcWithdrawalDao withdrawaldao=new OvcWithdrawalDaoImpl();
            OvcWithdrawal withdrawal=withdrawaldao.getWithdrawal(hhUniqueId);
            if(withdrawal !=null)
            {
                msg="This household has been withdrawn ("+withdrawal.getReasonWithdrawal()+")";
                request.setAttribute("msg", msg);
                return mapping.findForward(SUCCESS);
            }
            cpaform.setLgaCode(lgaCode);
            cpaform.setHhUniqueId(hhUniqueId);
            cpaform.setHhSerialNo(hhSerialNo);
            cpaform.setOrgCode(orgCode);
            cpaform.setCommunity(community);
            cpaform.setHhUniqueId(hhUniqueId);
            session.setAttribute("hhUniqueId", hhUniqueId);
            HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
            HouseholdEnrollment hhe=hhedao.getHouseholdEnrollment(hhUniqueId);
            if(hhe !=null)
            {
                hhe.setNoOfChildrenCurrentlyEnrolled(util.getOvcDaoInstance().getNumberOfOvcPerHousehold(hhUniqueId,true));
                hhe.setNoOfChildrenEverEnrolled(util.getOvcDaoInstance().getNumberOfOvcPerHousehold(hhUniqueId,false));
                cpaform.setAddress(hhe.getAddress());
                cpaform.setHhAge(hhe.getHhAge());
                cpaform.setDateOfEnrollment(appUtil.monthDayYear(hhe.getDateOfAssessment()));
                cpaform.setHhFirstname(hhe.getHhFirstname());
                cpaform.setHhSurname(hhe.getHhSurname());
                cpaform.setCommunity(util.getWardName(hhe.getCommunityCode()));
                //cpaform.setMaritalStatus(hhe.getMaritalStatus());
                cpaform.setOccupation(hhe.getOccupation());
                cpaform.setPhone(hhe.getPhone());
                cpaform.setHhGender(hhe.getHhGender());
                cpaform.setNoOfChildrenEnrolled(hhe.getNoOfChildrenEverEnrolled());
                cpaform=getCareplanAchievementForm(cpaform);
                if(cpaform.getId() >0)
                {
                    session.setAttribute("cpaModifyBtnDisabled", "false");
                    session.setAttribute("cpaSaveBtnDisabled", "true");
                }
                
             }
                session.setAttribute("hhUniqueId", hhUniqueId);
                return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("save"))
        {
            CareplanAchievement cpa=getCareplanAchievement(cpaform,request);
            CareplanAchievementDao cpadao=new CareplanAchievementDaoImpl();
            cpadao.saveCareplanAchievement(cpa);
            cpadao.getGraduationScore(cpa);
            withdrawHousehold(cpa);
            cpaform.reset(mapping, request);
        }
        else if(requiredAction.equalsIgnoreCase("modify"))
        {
            CareplanAchievementDao cpadao=new CareplanAchievementDaoImpl();
            CareplanAchievement cpa=getCareplanAchievement(cpaform,request);
            cpadao.updateCareplanAchievement(cpa);
            withdrawHousehold(cpa);
            cpaform.reset(mapping, request);
        }
        else if(requiredAction.equalsIgnoreCase("delete"))
        {
            CareplanAchievement cpa=getCareplanAchievement(cpaform,request);
            CareplanAchievementDao cpadao=new CareplanAchievementDaoImpl();
            cpadao.deleteCareplanAchievement(cpa);
            cpaform.reset(mapping, request);
        }
        return mapping.findForward(SUCCESS);
    }
    private void loadStartupInfo(HttpSession session)
    {
        session.setAttribute("hhUniqueId", " ");
        LoadUpInfo loadup=new LoadUpInfo();
        loadup.getStartupInfo(session);
    }
    private CareplanAchievement getCareplanAchievement(CareplanAchievementForm cpaform,HttpServletRequest request)
    {
        System.err.println("cpaform.getGraduated() is "+cpaform.getGraduated());
        HttpSession session=request.getSession();
        AppUtility appUtil=new AppUtility();
        CareplanAchievement cpa=new CareplanAchievement();
        
        cpa.setId(cpaform.getId());
        cpa.setClientId(cpaform.getHhUniqueId());
        cpa.setDateOfAssessment(cpaform.getDateOfAssessment());
        cpa.setFollowupRequired(cpaform.getFollowupRequired());
        cpa.setGraduated(cpaform.getGraduated());
        cpa.setLastModifiedDate(appUtil.getCurrentDate());
        cpa.setRecordedBy(appUtil.getCurrentUser(session));
        cpa.setVolunteerId(cpaform.getVolunteerId());
        cpa.setDesignation(cpaform.getDesignation());
        cpa.setServiceProviderPhone(cpaform.getServiceProviderPhone());
        cpa.setAssessmentNo(1);
        
        cpa.setHth_hivOnArt(cpaform.getHth_hivOnArt());
        cpa.setHth_hivOnArtComment(cpaform.getHth_hivOnArtComment());
        cpa.setHth_hivdisclosed(cpaform.getHth_hivdisclosed());
        cpa.setHth_hivdisclosedComment(cpaform.getHth_hivdisclosedComment());
        cpa.setHth_hivknowledge(cpaform.getHth_hivknowledge());
        cpa.setHth_hivknowledgeComment(cpaform.getHth_hivknowledgeComment());
        cpa.setHth_vcInneedOfHthServices(cpaform.getHth_vcInneedOfHthServices());
        cpa.setHth_vcInneedOfHthServicesComment(cpaform.getHth_vcInneedOfHthServicesComment());
        cpa.setHth_vchivrisk(cpaform.getHth_vchivrisk());
        cpa.setHth_vchivriskComment(cpaform.getHth_vchivriskComment());
        cpa.setHth_vcreftested(cpaform.getHth_vcreftested());
        cpa.setHth_vcreftestedComment(cpaform.getHth_vcreftestedComment());
        
        cpa.setStb_cgPartEconServ(cpaform.getStb_cgPartEconServ());
        cpa.setStb_cgPartEconServComment(cpaform.getStb_cgPartEconServComment());
        cpa.setStb_hungryNoFood(cpaform.getStb_hungryNoFood());
        cpa.setStb_hungryNoFoodComment(cpaform.getStb_hungryNoFoodComment());
        cpa.setStb_resiliency(cpaform.getStb_resiliency());
        cpa.setStb_resiliencyComment(cpaform.getStb_resiliencyComment());
        cpa.setStb_socEmotSupport(cpaform.getStb_socEmotSupport());
        cpa.setStb_socEmotSupportComment(cpaform.getStb_socEmotSupportComment());
        
        cpa.setSft_birthCert(cpaform.getSft_birthCert());
        cpa.setSft_birthCertComment(cpaform.getSft_birthCertComment());
        cpa.setSft_cgcompletedTwoModules(cpaform.getSft_cgcompletedTwoModules());
        cpa.setSft_cgcompletedTwoModulesComment(cpaform.getSft_cgcompletedTwoModulesComment());
        cpa.setSft_childHeadedLinkedToServices(cpaform.getSft_childHeadedLinkedToServices());
        cpa.setSft_childHeadedLinkedToServicesComment(cpaform.getSft_childHeadedLinkedToServicesComment());
        cpa.setSft_vcSafeFromAbuse(cpaform.getSft_vcSafeFromAbuse());
        cpa.setSft_vcSafeFromAbuseComment(cpaform.getSft_vcSafeFromAbuseComment());
        cpa.setSft_vcreferredForCps(cpaform.getSft_vcreferredForCps());
        cpa.setSft_vcreferredForCpsComment(cpaform.getSft_vcreferredForCpsComment());
        cpa.setSft_vcsad(cpaform.getSft_vcsad());
        cpa.setSft_vcsadComment(cpaform.getSft_vcsadComment());
        
        cpa.setSch_adolInVoctraining(cpaform.getSch_adolInVoctraining());
        cpa.setSch_adolInVoctrainingComment(cpaform.getSch_adolInVoctrainingComment());
        cpa.setSch_earlyChildCare(cpaform.getSch_earlyChildCare());
        cpa.setSch_earlyChildCareComment(cpaform.getSch_earlyChildCareComment());
        cpa.setSch_schAttendance(cpaform.getSch_schAttendance());
        cpa.setSch_schAttendanceComment(cpaform.getSch_schAttendanceComment());
        cpa.setSch_vcEnrolledInSecondarySch(cpaform.getSch_vcEnrolledInSecondarySch());
        cpa.setSch_vcEnrolledInSecondarySchComment(cpaform.getSch_vcEnrolledInSecondarySchComment());
        cpa.setComment(cpaform.getComment());
        
        return cpa;
    }
    private CareplanAchievementForm getCareplanAchievementForm(CareplanAchievementForm cpaform)
    {
        try
        {
            DateManager ndate=new DateManager();
            CareplanAchievementDao cpadao=new CareplanAchievementDaoImpl();
            CareplanAchievement cpa=cpadao.getCareplanAchievement(cpaform.getHhUniqueId(),ndate.processMthDayYearToMysqlFormat(cpaform.getDateOfAssessment()));
            if(cpa !=null)
            {
                cpaform.setId(cpa.getId());
                cpaform.setDateOfAssessment(ndate.convertMysqlDateTomonthDayYear(cpa.getDateOfAssessment()));
                cpaform.setGraduated(cpa.getGraduated());
                cpaform.setFollowupRequired(cpa.getFollowupRequired());
                cpaform.setHth_hivOnArt(cpa.getHth_hivOnArt());
                cpaform.setHth_hivOnArtComment(cpa.getHth_hivOnArtComment());
                cpaform.setHth_hivdisclosed(cpa.getHth_hivdisclosed());
                cpaform.setHth_hivdisclosedComment(cpa.getHth_hivdisclosedComment());
                cpaform.setHth_hivknowledge(cpa.getHth_hivknowledge());
                cpaform.setHth_hivknowledgeComment(cpa.getHth_hivknowledgeComment());
                cpaform.setHth_vcInneedOfHthServices(cpa.getHth_vcInneedOfHthServices());
                cpaform.setHth_vcInneedOfHthServicesComment(cpa.getHth_vcInneedOfHthServicesComment());
                cpaform.setHth_vchivrisk(cpa.getHth_vchivrisk());
                cpaform.setHth_vchivriskComment(cpa.getHth_vchivriskComment());
                cpaform.setHth_vcreftested(cpa.getHth_vcreftested());
                cpaform.setHth_vcreftestedComment(cpa.getHth_vcreftestedComment());

                cpaform.setStb_cgPartEconServ(cpa.getStb_cgPartEconServ());
                cpaform.setStb_cgPartEconServComment(cpa.getStb_cgPartEconServComment());
                cpaform.setStb_hungryNoFood(cpa.getStb_hungryNoFood());
                cpaform.setStb_hungryNoFoodComment(cpa.getStb_hungryNoFoodComment());
                cpaform.setStb_resiliency(cpa.getStb_resiliency());
                cpaform.setStb_resiliencyComment(cpa.getStb_resiliencyComment());
                cpaform.setStb_socEmotSupport(cpa.getStb_socEmotSupport());
                cpaform.setStb_socEmotSupportComment(cpa.getStb_socEmotSupportComment());

                cpaform.setSft_birthCert(cpa.getSft_birthCert());
                cpaform.setSft_birthCertComment(cpa.getSft_birthCertComment());
                cpaform.setSft_cgcompletedTwoModules(cpa.getSft_cgcompletedTwoModules());
                cpaform.setSft_cgcompletedTwoModulesComment(cpa.getSft_cgcompletedTwoModulesComment());
                cpaform.setSft_childHeadedLinkedToServices(cpa.getSft_childHeadedLinkedToServices());
                cpaform.setSft_childHeadedLinkedToServicesComment(cpa.getSft_childHeadedLinkedToServicesComment());
                cpaform.setSft_vcSafeFromAbuse(cpa.getSft_vcSafeFromAbuse());
                cpaform.setSft_vcSafeFromAbuseComment(cpa.getSft_vcSafeFromAbuseComment());
                cpaform.setSft_vcreferredForCps(cpa.getSft_vcreferredForCps());
                cpaform.setSft_vcreferredForCpsComment(cpa.getSft_vcreferredForCpsComment());
                cpaform.setSft_vcsad(cpa.getSft_vcsad());
                cpaform.setSft_vcsadComment(cpa.getSft_vcsadComment());

                cpaform.setSch_adolInVoctraining(cpa.getSch_adolInVoctraining());
                cpaform.setSch_adolInVoctrainingComment(cpa.getSch_adolInVoctrainingComment());
                cpaform.setSch_earlyChildCare(cpa.getSch_earlyChildCare());
                cpaform.setSch_earlyChildCareComment(cpa.getSch_earlyChildCareComment());
                cpaform.setSch_schAttendance(cpa.getSch_schAttendance());
                cpaform.setSch_schAttendanceComment(cpa.getSch_schAttendanceComment());
                cpaform.setSch_vcEnrolledInSecondarySch(cpa.getSch_vcEnrolledInSecondarySch());
                cpaform.setSch_vcEnrolledInSecondarySchComment(cpa.getSch_vcEnrolledInSecondarySchComment());
                cpaform.setGraduated(cpa.getGraduated());
                cpaform.setDesignation(cpa.getDesignation());
                cpaform.setGraduated(cpa.getServiceProviderPhone());
                cpaform.setVolunteerId(cpa.getVolunteerId());
                cpaform.setComment(cpa.getComment());
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return cpaform;
    }
    private void withdrawHousehold(CareplanAchievement cpa)
    {
        try
        {
        if(cpa.getFollowupRequired() !=null && cpa.getFollowupRequired().equalsIgnoreCase("No") && (cpa.getScore()>17))
        {
            CareplanAchievementDao cpadao=new CareplanAchievementDaoImpl();
            cpa.setScore(cpadao.getGraduationScore(cpa));
            OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl();
            wdao.withdrawHousehold(cpa.getClientId(), cpa.getDateOfAssessment(), NomisConstant.GRADUATED, NomisConstant.HOUSEHOLD_TYPE, 0);
            System.err.println("cpa.getClientId() "+cpa.getClientId()+" and type "+NomisConstant.HOUSEHOLD_TYPE+" withdrwawn");
        }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
