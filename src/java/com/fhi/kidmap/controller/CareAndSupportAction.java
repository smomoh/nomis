/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import com.fhi.kidmap.business.CareAndSupportChecklist;
import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.business.OvcWithdrawal;
import com.fhi.kidmap.business.States;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.HouseholdEnrollmentDao;
import com.fhi.kidmap.dao.HouseholdEnrollmentDaoImpl;
import com.fhi.kidmap.dao.OvcDao;
import com.fhi.kidmap.dao.OvcDaoImpl;
import com.fhi.kidmap.dao.OvcWithdrawalDao;
import com.fhi.kidmap.dao.OvcWithdrawalDaoImpl;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.nomis.nomisutils.DateManager;
import com.fhi.nomis.nomisutils.LoadUpInfo;
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
public class CareAndSupportAction extends org.apache.struts.action.Action {

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
        CareAndSupportForm candsform=(CareAndSupportForm)form;
        HttpSession session=request.getSession();
        DaoUtil util=new DaoUtil();
        
        String lgaCode=candsform.getLgaCode();
        String orgCode=candsform.getOrgCode();
        int hhSerialNo=candsform.getHhSerialNo();
        String hhUniqueId=candsform.getHhUniqueId();
        String ovcId=candsform.getOvcId();
        String communityCode=candsform.getCommunityCode();
        
        String requiredAction=candsform.getActionName();
        String stateCode=null;
        States state=(States)session.getAttribute("state");
        if(state !=null)
        stateCode=state.getState_code();
        List facilityList=util.getReferralDirectoriesByState(stateCode);
        candsform.setReferralDirectoryList(facilityList);
        LoadUpInfo loadup=new LoadUpInfo();
        String hhUniqueIdMsg="";
        String ovcWithdrawn=" ";
        List ovcList=new ArrayList();
        setBtnState(session, "false", "true");
        request.setAttribute("doadisabled", "false");
        if(requiredAction==null)
        {
           loadup.getStartupInfo(session);
           session.setAttribute("ovcListIncandsform", ovcList);
           return mapping.findForward("checklist");
        }
        else if(requiredAction.equalsIgnoreCase("cboList"))
        {
            candsform.reset(mapping, request);
            loadup.getCbosPerLgaFromSetup(session,lgaCode);
            candsform.setLgaCode(lgaCode);
            return mapping.findForward("checklist");
        }
        else if(requiredAction.equalsIgnoreCase("hhDetails"))
        {
            session.setAttribute("ovcListInCsc",new ArrayList());
            HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
            OvcDao ovcdao=new OvcDaoImpl();
            candsform.reset(mapping, request);
              try
              {
                  //System.err.println("hhUniqueId is "+hhUniqueId);
                        HouseholdEnrollment hhe=hhedao.getHouseholdEnrollment(hhUniqueId);
                        candsform.setLgaCode(lgaCode);
                        candsform.setOrgCode(orgCode);
                        candsform.setHhSerialNo(hhSerialNo);

                        if(hhe !=null)
                        {
                            OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl();
                            List list=wdao.getWithdrawnOvc(hhe.getHhUniqueId());

                            if(list !=null && !list.isEmpty())
                            {
                                OvcWithdrawal withdrawal=(OvcWithdrawal)list.get(0);
                                ovcWithdrawn="This household has been withdrawn("+withdrawal.getReasonWithdrawal()+")";
                                request.setAttribute("ovcWithdrawn", ovcWithdrawn);
                                setBtnState(session, "true", "true");
                                request.setAttribute("doadisabled", "true");
                                return mapping.findForward("checklist");
                            }
                            else
                            {
                                hhUniqueIdMsg=hhUniqueId;
                                candsform.setHhUniqueId(hhUniqueId);
                                candsform.setCommunityCode(util.getWardName(hhe.getCommunityCode()));
                                ovcList=ovcdao.getOvcPerHousehold(hhUniqueId);
                                session.setAttribute("ovcListInCsc", ovcList);
                            }
                        }
                        else
                        {
                            ovcWithdrawn="This household does not exist";
                            request.setAttribute("ovcWithdrawn", ovcWithdrawn);
                            setBtnState(session, "true", "true");
                            request.setAttribute("doadisabled", "true");
                            return mapping.findForward("checklist");
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
             OvcDao ovcdao=new OvcDaoImpl();
            candsform.setHhSerialNo(hhSerialNo);
            hhUniqueIdMsg=candsform.getHhUniqueId();
            setBtnState(session, "false", "true");
            session.setAttribute("vcHhUniqueId", hhUniqueIdMsg);
            OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl();
            List list=wdao.getWithdrawnOvc(ovcId);

            if(list !=null && !list.isEmpty())
            {
                OvcWithdrawal withdrawal=(OvcWithdrawal)list.get(0);
                ovcWithdrawn="This VC has been withdrawn("+withdrawal.getReasonWithdrawal()+")";
                request.setAttribute("ovcWithdrawn", ovcWithdrawn);
                setBtnState(session, "true", "true");
                request.setAttribute("doadisabled", "true");
                return mapping.findForward("checklist");
            }
            List ovcDetailsList=ovcdao.getOVC(ovcId);
            if(!ovcDetailsList.isEmpty())
            {
                Ovc ovc=(Ovc)ovcDetailsList.get(0);
                form.reset(mapping, request);
                //setControlStatus(ovc, request);               
                setBaselineDetails(ovc, candsform,communityCode,hhSerialNo);
                int currentAge=util.getCurrentAge(ovc);
                if(currentAge > 17)
                request.setAttribute("currentAge", "overaged;"+currentAge);
                else
                request.setAttribute("currentAge", "underaged;"+currentAge);
                if(currentAge <10)
                request.setAttribute("adolescentAttribute", "true");
                if(ovc.getActiveHivStatus().getHivStatus().equalsIgnoreCase("positive"))
                request.setAttribute("hivStatusAttribute", "true");
                candsform.setActionName(null);
                candsform.setLgaCode(lgaCode);
                candsform.setOrgCode(orgCode);
                session.setAttribute("vcUniqueId", ovc.getOvcId());
                return mapping.findForward("checklist");
            }
            candsform.setLgaCode(lgaCode);
            candsform.setOrgCode(orgCode);
            candsform.setCommunityCode(communityCode);            
            session.setAttribute("ovcWithdrawn", "This VC does not exist in the enrolment record");
            return mapping.findForward("checklist");
        }
        else if(requiredAction.equalsIgnoreCase("assessmentDetails"))
        {
            candsform.reset(mapping, request);
            CareAndSupportChecklist cAndS=util.getCareAndSupportDaoInstance().getCareAndSupportCheclist(candsform.getOvcId(), DateManager.processMthDayYearToMysqlFormat(candsform.getDateOfAssessment()));
            if(cAndS !=null)
            {
                candsform.setDateEnrolledOnART(DateManager.getMthDayYearFromMySqlDate(cAndS.getDateEnrolledOnART()));
                candsform.setDateOfHivTest(DateManager.getMthDayYearFromMySqlDate(cAndS.getDateOfHivTest()));
                candsform.setDateOfViralLoadTest(DateManager.getMthDayYearFromMySqlDate(cAndS.getDateOfViralLoadTest()));
                candsform.setDesignation(cAndS.getDesignation());
                candsform.setEnrolledOnTreatment(cAndS.getEnrolledOnTreatment());
                candsform.setHivStatus(cAndS.getHivStatus());
                candsform.setMedicationPickedUp(cAndS.getMedicationPickedUp());
                String reasonsPeopleSkipARV=cAndS.getReasonsPeopleSkipARV();
                if(reasonsPeopleSkipARV !=null)
                candsform.setReasonsPeopleSkipARV(reasonsPeopleSkipARV.split(","));
                else
                candsform.setReasonsPeopleSkipARV(null);
                candsform.setReceivedTestResult(cAndS.getReceivedTestResult());
                candsform.setSkippedARV(cAndS.getSkippedARV());
                candsform.setTransportationSupport(cAndS.getTransportationSupport());
                candsform.setTreatmentFacility(cAndS.getTreatmentFacility());
                candsform.setViralLoadTest(cAndS.getViralLoadTestDone());
                candsform.setVolunteerName(cAndS.getVolunteerName());
                candsform.setProviderPhone(cAndS.getProviderPhone());
                setBtnState(session, "true", "false");
            }
        }
        else if(requiredAction.equalsIgnoreCase("save"))
        {
            CareAndSupportChecklist cAndS=getPopulatedCareAndSupport(candsform);
            util.getCareAndSupportDaoInstance().saveCareAndSupportCheclist(cAndS);
            candsform.reset(mapping, request);
        }
        else if(requiredAction.equalsIgnoreCase("modify"))
        {
            CareAndSupportChecklist cAndS=getPopulatedCareAndSupport(candsform);
            util.getCareAndSupportDaoInstance().updateCareAndSupportCheclist(cAndS);
            candsform.reset(mapping, request);
        }
        else if(requiredAction.equalsIgnoreCase("delete"))
        {
            CareAndSupportChecklist cAndS=getPopulatedCareAndSupport(candsform);
            util.getCareAndSupportDaoInstance().deleteCareAndSupportCheclist(cAndS);
            candsform.reset(mapping, request);
        }
        return mapping.findForward("checklist");
    }
    private void setBaselineDetails(Ovc ovc,CareAndSupportForm candsform,String communityCode, int hhSerialNo)
    {
        if(ovc !=null)
        {
            candsform.setOvcId(ovc.getOvcId());
            candsform.setAge(ovc.getCurrentAge());
            candsform.setAgeUnit(ovc.getAgeUnit());
            candsform.setOvcGender(ovc.getGender());
            candsform.setCommunityCode(communityCode);
            candsform.setHhSerialNo(hhSerialNo);
        }
    }
    private CareAndSupportChecklist getPopulatedCareAndSupport(CareAndSupportForm candsform)
    {
        AppUtility appUtil=new AppUtility();
        CareAndSupportChecklist cAndS=new CareAndSupportChecklist();
        cAndS.setExperiencedSoresOrRash(candsform.getChildExperiencedSoresOrRash());
        cAndS.setDateEnrolledOnART(DateManager.processMthDayYearToMysqlFormat(candsform.getDateEnrolledOnART()));
        cAndS.setDateOfAssessment(DateManager.processMthDayYearToMysqlFormat(candsform.getDateOfAssessment()));
        cAndS.setDateOfHivTest(DateManager.processMthDayYearToMysqlFormat(candsform.getDateOfHivTest()));
        cAndS.setDateOfViralLoadTest(DateManager.processMthDayYearToMysqlFormat(candsform.getDateOfViralLoadTest()));
        cAndS.setEnrolledOnTreatment(candsform.getEnrolledOnTreatment());
        cAndS.setDesignation(candsform.getDesignation());
        cAndS.setHhUniqueId(candsform.getHhUniqueId());
        cAndS.setHivStatus(candsform.getHivStatus());
        cAndS.setLastModifiedDate(DateManager.getCurrentDate());
        cAndS.setMedicationPickedUp(candsform.getMedicationPickedUp());
        cAndS.setClientId(candsform.getOvcId());
        cAndS.setReasonsPeopleSkipARV(appUtil.concatStr(candsform.getReasonsPeopleSkipARV(),null));
        cAndS.setReceivedTestResult(candsform.getReceivedTestResult());
        cAndS.setSkippedARV(candsform.getSkippedARV());
        cAndS.setTransportationSupport(candsform.getTransportationSupport());
        cAndS.setTreatmentFacility(candsform.getTreatmentFacility());
        cAndS.setViralLoadTestDone(candsform.getViralLoadTest());
        cAndS.setVolunteerName(candsform.getVolunteerName());
        cAndS.setProviderPhone(candsform.getProviderPhone());
        cAndS.setRecordId(candsform.getRecordId());
        return cAndS;
    }
    private void setBtnState(HttpSession session, String saveState, String modifyState)
    {
        session.setAttribute("candsSaveDisabled", saveState);
        session.setAttribute("candsModifyDisabled", modifyState);
    }
}
