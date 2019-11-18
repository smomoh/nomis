/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import com.fhi.kidmap.business.HivRiskAssessmentChecklist;
import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.business.OvcWithdrawal;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.HivRiskAssessmentChecklistDao;
import com.fhi.kidmap.dao.HivRiskAssessmentChecklistDaoImpl;
import com.fhi.kidmap.dao.HouseholdEnrollmentDao;
import com.fhi.kidmap.dao.HouseholdEnrollmentDaoImpl;
import com.fhi.kidmap.dao.OvcDao;
import com.fhi.kidmap.dao.OvcDaoImpl;
import com.fhi.kidmap.dao.OvcWithdrawalDao;
import com.fhi.kidmap.dao.OvcWithdrawalDaoImpl;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.nomis.nomisutils.LoadUpInfo;
import com.fhi.nomis.nomisutils.NomisConstant;
import com.fhi.nomis.nomisutils.ResourceManager;
import java.util.ArrayList;
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
public class HivRiskAssessmentChecklistAction extends org.apache.struts.action.Action 
{

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    //private static final String PARAMPAGE = "paramPage";
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
            throws Exception 
    {
        //This action manages hiv risk assessment
        final String olderThan2Years="Yes";
        final String notOlderThan2Years="No";
        HivRiskAssessmentForm hraform=(HivRiskAssessmentForm)form;
        
        //There are two versions of this checklist. The one currently selected by the user is loaded
        if(ResourceManager.isRevisedHivRiskAssessmentForm())
         FORWARDPARAM=REVISEDFORM;
         else
         FORWARDPARAM = OLDFORM;
        
        HttpSession session=request.getSession();
        OvcDao dao=new OvcDaoImpl();
        LoadUpInfo loadup=new LoadUpInfo();
        DaoUtil util=new DaoUtil();
        int hhSerialNumber=hraform.getHhSerialNo();
        String hhUniqueId=hraform.getHhUniqueId();
        String dateOfAssessment=hraform.getDateOfAssessment();
        String ovcId=hraform.getOvcId();
        String stateCode=loadup.getStateCodeFromSetup(session);
        hraform.setStateCode(stateCode);
        hraform.setStateName(util.getStateName(stateCode));
        String lgaCode=hraform.getLgaCode();
        String hivStatusQuestion=hraform.getHivStatusQuestion();
        String hivStatus=hraform.getHivStatus();
        hraform.setOvcList(dao.getOvcPerHousehold(hhUniqueId));
        
        String requiredAction=hraform.getActionName();
        
        String msg=" ";
        String hhUniqueIdMsg="";
        session.setAttribute("hraSaveBtnDisabled", "false");
        session.setAttribute("hraModifyBtnDisabled", "true");
        if(childLessThan3Years(ovcId))
        {
            request.setAttribute("ovcWithdrawn", "This child is less than 3 years old and not eligible for Risk Assessment");
            session.setAttribute("hraSaveBtnDisabled", "true");
        }
        //request.setAttribute("hramuacfielddisabled", "true");
        //System.err.println("requiredAction is "+requiredAction);
        if(requiredAction==null)
        {
            hraform.reset(mapping, request);
            loadStartupInfo(session);
            return mapping.findForward(FORWARDPARAM);
        }
        else if(requiredAction.equalsIgnoreCase("cboList"))
        {
            loadup.getCbosPerLgaFromSetup(session,hraform.getLgaCode());
            hraform.reset(mapping, request);
            hraform.setLgaCode(lgaCode);
            
            return mapping.findForward(FORWARDPARAM);
        }
        else if(requiredAction.equalsIgnoreCase("hhDetails"))
        {
            HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
            hraform.reset(mapping, request);
            hraform.setHhSerialNo(hhSerialNumber);
          try
          {
            //System.err.println("hhUniqueId is "+hhUniqueId);
            HouseholdEnrollment hhe=hhedao.getHouseholdEnrollment(hhUniqueId);
            if(hhe !=null)
            {
                OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl();
                OvcWithdrawal withdrawal=wdao.getWithdrawal(hhe.getHhUniqueId());
                if(withdrawal !=null)
                {
                    String ovcWithdrawn="This Household has been withdrawn";
                    request.setAttribute("ovcWithdrawn", ovcWithdrawn);
                    hraform.setOvcList(new ArrayList());
                    form.reset(mapping, request);
                    return (mapping.findForward(FORWARDPARAM));
                }
                hhUniqueIdMsg=hhUniqueId;
                hraform.setHhUniqueId(hhUniqueId);
                hraform.setCommunity(util.getWardName(hhe.getCommunityCode()));
                //System.err.println("hhe is not null");
            }
          }
          catch(Exception ex)
          {
              ex.printStackTrace();
          }
          session.setAttribute("vcHhUniqueId", hhUniqueIdMsg);
          
          return mapping.findForward(FORWARDPARAM);
         }
        else if(requiredAction.equalsIgnoreCase("childDetails"))
        {
            //OvcDao dao=new OvcDaoImpl();
            Ovc ovc=dao.getOvc(ovcId);
            session.setAttribute("vcUniqueId", ovcId);
            if(ovc !=null)
            {
                OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl();
                OvcWithdrawal withdrawal=wdao.getWithdrawal(ovcId);
                if(withdrawal !=null)
                {
                    String ovcWithdrawn="This VC has been withdrawn";
                    request.setAttribute("ovcWithdrawn", ovcWithdrawn);
                    form.reset(mapping, request);
                    return (mapping.findForward(FORWARDPARAM));
                }
                else if(ovc.getActiveHivStatus().getHivStatus().equalsIgnoreCase(NomisConstant.HIV_POSITIVE))
                {
                    request.setAttribute("ovcWithdrawn", "This child is HIV positive and not eligible for Risk Assessment");
                    form.reset(mapping, request);
                    return (mapping.findForward(FORWARDPARAM));
                }
                else if(ovc.getCurrentAgeUnit() !=null)
                {
                    if(ovc.getCurrentAge()<3 || ovc.getCurrentAgeUnit().equalsIgnoreCase("Month"))
                    {
                        request.setAttribute("ovcWithdrawn", "This is less than 3 years and not eligible for Risk Assessment");
                        form.reset(mapping, request);
                        return (mapping.findForward(FORWARDPARAM));
                    }
                }
                //System.err.println("Child still in the program");
                hraform=setBaselineInfo(hraform, ovc,request);
            }
           return mapping.findForward(FORWARDPARAM);
        }
        else if(requiredAction.equalsIgnoreCase("assessmentDetails"))
        {
            AppUtility appUtil=new AppUtility();
            HivRiskAssessmentChecklistDao hracdao=new HivRiskAssessmentChecklistDaoImpl();
            HivRiskAssessmentChecklist hrac=hracdao.getHivRiskAssessmentChecklist(ovcId, appUtil.processMthDayYearToMysqlFormat(hraform.getDateOfAssessment()));
            session.setAttribute("vcUniqueId", ovcId);
            
            hraform.reset(mapping, request);
            hraform.setHivStatusQuestion(hivStatusQuestion);
            hraform.setHivStatus(hivStatus);
            Ovc ovc=dao.getOvc(ovcId);
            if(ovc !=null)
            {
                hraform=setBaselineInfo(hraform, ovc,request);
                hraform.setHhSerialNo(hhSerialNumber);
                hraform.setDateOfAssessment(dateOfAssessment);
                if(hrac !=null)
                {
                    hraform.setChildAgeComment(hrac.getChildAgeComment());
                    hraform.setChildAgeQuestion(hrac.getChildAgeQuestion());
                    hraform.setChildAtRiskQuestion(hrac.getChildAtRiskQuestion());
                    hraform.setChildSickComment(hrac.getChildSickComment());
                    hraform.setChildSickQuestion(hrac.getChildSickQuestion());
                    hraform.setChildTestedComment(hrac.getChildTestedComment());
                    hraform.setChildTestedQuestion(hrac.getChildTestedQuestion());
                    hraform.setChronicallyIllComment(hrac.getChronicallyIllComment());
                    hraform.setChronicallyIllQuestion(hrac.getChronicallyIllQuestion());

                    hraform.setDesignation(hrac.getDesignation());
                    hraform.setGenitalDischargeComment(hrac.getGenitalDischargeComment());
                    hraform.setGenitalDischargeQuestion(hrac.getGenitalDischargeQuestion());
                    hraform.setHivParentComment(hrac.getHivParentComment());
                    hraform.setHivParentQuestion(hrac.getHivParentQuestion());
                    hraform.setParentDeceasedComment(hrac.getParentDeceasedComment());
                    hraform.setParentDeceasedQuestion(hrac.getParentDeceasedQuestion());
                    hraform.setRecordId(hrac.getRecordId());
                    hraform.setSchoolGradeComment(hrac.getSchoolGradeComment());
                    hraform.setSchoolGradeQuestion(hrac.getSchoolGradeQuestion());
                    hraform.setServiceProviderName(hrac.getServiceProviderName());
                    hraform.setServiceProviderPhone(hrac.getServiceProviderPhone());
                    hraform.setSexualAbuseComment(hrac.getSexualAbuseComment());
                    hraform.setSexualAbuseQuestion(hrac.getSexualAbuseQuestion());
                    hraform.setSexuallyActiveComment(hrac.getSexuallyActiveComment());
                    hraform.setSexuallyActiveQuestion(hrac.getSexuallyActiveQuestion());
                    hraform.setSkinProblemComment(hrac.getSkinProblemComment());
                    hraform.setSkinProblemQuestion(hrac.getSkinProblemQuestion());
                    hraform.setBloodTransfusionQuestion(hrac.getBloodTransfusionQuestion());
                    hraform.setBloodTransfusionComment(hrac.getBloodTransfusionComment());
                    hraform.setMuacbmiQuestion(hrac.getMuacbmiQuestion());
                    hraform.setMuacbmiComment(hrac.getMuacbmiComment());
                    hraform.setHivStatusQuestion(hrac.getHivStatusQuestion());
                    hraform.setHivStatus(hrac.getHivStatus());
                    
                    session.setAttribute("hraSaveBtnDisabled", "true");
                    session.setAttribute("hraModifyBtnDisabled", "false");
                }
            }
           return mapping.findForward(FORWARDPARAM);
        }
        else if(requiredAction.equalsIgnoreCase("save"))
        {
            HivRiskAssessmentChecklistDao hracdao=new HivRiskAssessmentChecklistDaoImpl();
            HivRiskAssessmentChecklist hrac=createNewHivRiskAssessmentChecklist(hraform);
            hracdao.saveHivRiskAssessmentChecklist(hrac);
            hraform.reset(mapping, request);
        }
        else if(requiredAction.equalsIgnoreCase("modify"))
        {
            HivRiskAssessmentChecklist hrac=createNewHivRiskAssessmentChecklist(hraform);
            HivRiskAssessmentChecklistDao hracdao=new HivRiskAssessmentChecklistDaoImpl();
            hracdao.updateHivRiskAssessmentChecklist(hrac);
            hraform.reset(mapping, request);
        }
        else if(requiredAction.equalsIgnoreCase("delete"))
        {
            HivRiskAssessmentChecklist hrac=createNewHivRiskAssessmentChecklist(hraform);
            HivRiskAssessmentChecklistDao hracdao=new HivRiskAssessmentChecklistDaoImpl();
            hracdao.deleteHivRiskAssessmentChecklist(hrac);
            hraform.reset(mapping, request);
        }
        hraform.reset(mapping, request);
        return mapping.findForward(FORWARDPARAM);
    }
    private HivRiskAssessmentChecklist createNewHivRiskAssessmentChecklist(HivRiskAssessmentForm hraform)
    {
        HivRiskAssessmentChecklist hrac=new HivRiskAssessmentChecklist();
        AppUtility appUtil=new AppUtility();
        if(hraform !=null)
        {
            String childAtRisk="No";
            
                hrac.setChildAgeQuestion("Yes");
                hrac.setRecordId(hraform.getRecordId());
                hrac.setHhUniqueId(hraform.getHhUniqueId());
                hrac.setOvcId(hraform.getOvcId());
                hrac.setDateOfAssessment(appUtil.processMthDayYearToMysqlFormat(hraform.getDateOfAssessment()));
                hrac.setChildAgeComment(hraform.getChildAgeComment());
                hrac.setChildTestedQuestion(hraform.getChildTestedQuestion());
                hrac.setChildTestedComment(hraform.getChildTestedComment());
                hrac.setHivParentQuestion(hraform.getHivParentQuestion());
                hrac.setHivParentComment(hraform.getHivParentComment());
                hrac.setChildSickQuestion(hraform.getChildSickQuestion());
                hrac.setChildSickComment(hraform.getChildSickComment());
                hrac.setSkinProblemQuestion("N/A");
                hrac.setSkinProblemComment(hraform.getSkinProblemComment());
                hrac.setParentDeceasedQuestion("N/A");
                hrac.setParentDeceasedComment(hraform.getParentDeceasedComment());
                hrac.setChronicallyIllQuestion(hraform.getChronicallyIllQuestion());
                hrac.setChronicallyIllComment(hraform.getChronicallyIllComment());
                hrac.setSchoolGradeQuestion("N/A");
                hrac.setSchoolGradeComment(hraform.getSchoolGradeComment());
                hrac.setSexualAbuseQuestion(hraform.getSexualAbuseQuestion());
                hrac.setSexualAbuseComment(hraform.getSexualAbuseComment());
                hrac.setSexuallyActiveQuestion(hraform.getSexuallyActiveQuestion());
                hrac.setSexuallyActiveComment(hraform.getSexuallyActiveComment());
                hrac.setGenitalDischargeQuestion(hraform.getGenitalDischargeQuestion());
                hrac.setGenitalDischargeComment(hraform.getGenitalDischargeComment());
                hrac.setDesignation(hraform.getDesignation());
                hrac.setServiceProviderName(hraform.getServiceProviderName());
                hrac.setServiceProviderPhone(hraform.getServiceProviderPhone());
                hrac.setBloodTransfusionQuestion(hraform.getBloodTransfusionQuestion());
                hrac.setBloodTransfusionComment(hraform.getBloodTransfusionComment());
                String muacBmi=hraform.getMuacbmiQuestion();
                if(muacBmi==null)
                muacBmi="N/A";
                hrac.setMuacbmiQuestion(muacBmi);
                hrac.setMuacbmiComment(hraform.getMuacbmiComment());
                hrac.setHivStatusQuestion(hraform.getHivStatusQuestion());
                hrac.setHivStatus(hraform.getHivStatus());
                hrac.setLastModifiedDate(appUtil.getCurrentDate());
                if(isChildAtRisk(hrac))
                childAtRisk="Yes";
                hrac.setChildAtRiskQuestion(childAtRisk);
        }
        return hrac;
    }
    private boolean isChildAtRisk(HivRiskAssessmentChecklist hrac)
    {
        boolean childAtRisk=false;
        if(hrac !=null)
        {
            if(hrac.getHivParentQuestion() !=null && hrac.getHivParentQuestion().trim().equalsIgnoreCase("Yes"))
            childAtRisk=true;
            else if(hrac.getSexualAbuseQuestion() !=null && hrac.getSexualAbuseQuestion().trim().equalsIgnoreCase("Yes"))
            childAtRisk=true;
            else if(hrac.getChronicallyIllQuestion() !=null && hrac.getChronicallyIllQuestion().trim().equalsIgnoreCase("Yes"))
            childAtRisk=true;
            else if(hrac.getSexuallyActiveQuestion() !=null && hrac.getSexuallyActiveQuestion().trim().equalsIgnoreCase("Yes"))
            childAtRisk=true;
            else if(hrac.getChildSickQuestion() !=null && hrac.getChildSickQuestion().trim().equalsIgnoreCase("Yes"))
            childAtRisk=true;
            else if(hrac.getBloodTransfusionQuestion() !=null && hrac.getBloodTransfusionQuestion().trim().equalsIgnoreCase("Yes"))
            childAtRisk=true;
            else if(hrac.getMuacbmiQuestion() !=null && hrac.getMuacbmiQuestion().trim().equalsIgnoreCase("Yes"))
            childAtRisk=true;
        }
        return childAtRisk;
    }
    private boolean childLessThan3Years(String ovcId)
    {
        System.err.println("ovcId in childLessThan3Years is "+ovcId);
        boolean ageBelow3Years=false;
        try
        {
        OvcDao dao=new OvcDaoImpl();
            Ovc ovc=dao.getOvc(ovcId);
            if(ovc !=null)
            {
                DaoUtil util=new DaoUtil();
                ageBelow3Years=true;
                int currentAge=ovc.getCurrentAge();
                String currentAgeUnit=ovc.getCurrentAgeUnit();
                if(currentAgeUnit ==null)
                currentAgeUnit=util.getCurrentAgeUnit(ovc);
                System.err.println("currentAge in childLessThan3Years is "+currentAge+" "+currentAgeUnit);
                if(currentAgeUnit.equalsIgnoreCase("Year"))
                {
                    if(currentAge >2)
                    {
                        ageBelow3Years=false;
                    }
                }

            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return ageBelow3Years;
    }
    private boolean childLessThan5Years(Ovc ovc)
    {
        //System.err.println("Inside childLessThan5Years ");
        boolean ageBelow5Years=false;
        if(ovc !=null)
        {
            DaoUtil util=new DaoUtil();
            ageBelow5Years=true;
            int currentAge=ovc.getCurrentAge();
            String currentAgeUnit=ovc.getCurrentAgeUnit();
            if(currentAgeUnit ==null)
            currentAgeUnit=util.getCurrentAgeUnit(ovc);
            if(currentAgeUnit.equalsIgnoreCase("Year"))
            {

                if(currentAge >4)
                {
                    ageBelow5Years=false;
                }
            }
            
        }
        
        return ageBelow5Years;
    }
    private HivRiskAssessmentForm setBaselineInfo(HivRiskAssessmentForm hraform, Ovc ovc,HttpServletRequest request)
    {
        if(ovc !=null)
        {
            DaoUtil util=new DaoUtil();
            hraform.setOvcId(ovc.getOvcId());
            hraform.setAddress(ovc.getAddress());
            hraform.setAge(ovc.getCurrentAge());
            hraform.setAgeUnit(util.getCurrentAgeUnit(ovc));
            hraform.setFirstname(ovc.getFirstName());
            hraform.setSurname(ovc.getLastName());
            hraform.setGender(ovc.getGender());
            hraform.setHhUniqueId(ovc.getHhUniqueId());
            hraform.setPhone(ovc.getPhone());
            if(!childLessThan5Years(ovc))
            request.getSession().setAttribute("hramuacfielddisabled", "true");
            else
            request.getSession().setAttribute("hramuacfielddisabled", "false");
        }
        return hraform;
    }
    private void loadStartupInfo(HttpSession session)
    {
        //session.setAttribute("hhUniqueId", " ");
        LoadUpInfo loadup=new LoadUpInfo();
        loadup.getStartupInfo(session);
    }
}
