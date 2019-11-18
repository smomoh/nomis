/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import com.fhi.kidmap.business.Caregiver;
import com.fhi.kidmap.business.CaregiverExpenditureAndSchoolAttendance;
import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.kidmap.dao.CaregiverDao;
import com.fhi.kidmap.dao.CaregiverDaoImpl;
import com.fhi.kidmap.dao.CaregiverExpenditureAndSchoolAttendanceDao;
import com.fhi.kidmap.dao.CaregiverExpenditureAndSchoolAttendanceDaoImpl;
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
public class CaregiverExpenditureAndSchoolAttendanceAction extends org.apache.struts.action.Action {

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
        CaregiverExpenditureAndSchoolAttendanceForm expform=(CaregiverExpenditureAndSchoolAttendanceForm)form;
        HttpSession session=request.getSession();
        DaoUtil util=new DaoUtil();
        HouseholdEnrollment hhe=null;
        Caregiver cgiver=null;
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        CaregiverDao cgiverdao=new CaregiverDaoImpl();
        
        String requiredAction=expform.getActionName();
        List emptyList=new ArrayList();
        List ovcListInEcons=new ArrayList();
        String hhUniqueId=expform.getHhUniqueId();
        int hhSerialNo=expform.getHhSerialNo();
        String cboCode=expform.getOrgCode();
        String ward=expform.getWard();
        String uniqueId=expform.getCaregiverId();
        String dateOfAssessment=DateManager.processMthDayYearToMysqlFormat(expform.getDateOfAssessment());
        LoadUpInfo loadup=new LoadUpInfo();
        loadup.getCbosPerLgaFromSetup(session,expform.getLgaCode());
        session.setAttribute("hhUniqueIdInEcons", hhUniqueId);
        session.setAttribute("ovcEconsSaveBtnDisabled", "false");
        session.setAttribute("ovcEconsModifyBtnDisabled", "true");
        
        
        System.err.println("requiredAction is "+requiredAction);
        if(requiredAction==null)
        {
            loadStartupInfo(session);
            session.setAttribute("caregiverListInEcons", emptyList);
            session.setAttribute("ovcListInEcons",ovcListInEcons);
           return mapping.findForward(SUCCESS); 
        }
        else if(requiredAction.equalsIgnoreCase("cboList"))
        {
            loadup=new LoadUpInfo();
            loadup.getCbosPerLgaFromSetup(session,expform.getLgaCode());
            session.setAttribute("caregiverListInEcons", emptyList);
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("caregiverList"))
        {
            System.err.println("uniqueId is "+uniqueId);
            List caregiverList=new ArrayList();
            expform.reset(mapping, request);
            expform.setHhUniqueId(hhUniqueId);
            expform.setHhSerialNo(hhSerialNo);
            expform.setOrgCode(cboCode);
            expform.setWard(ward);
            String uniqueIdMsg="<span style='color:red'>This household unique id does not exist</span>";
            session.setAttribute("ovcEconsSaveBtnDisabled", "true");
            session.setAttribute("ovcEconsModifyBtnDisabled", "true");              
            hhe=hhedao.getHouseholdEnrollment(hhUniqueId);
            if(hhe !=null)
            {
               List caregiverList2=new ArrayList();
               /*cgiver=cgiverdao.getCaregiverByName(hhe.getHhUniqueId(), hhe.getHhFirstname(), hhe.getHhSurname());
               if(cgiver ==null)
               {
                   cgiver=new Caregiver();
                   cgiver.setCaregiverFirstname(hhe.getHhFirstname());
                   cgiver.setCaregiverLastName(hhe.getHhSurname()+"(HH)");
                   cgiver.setHouseholdhead(true);
                   cgiver.setHhUniqueId(hhUniqueId);
                   cgiver.setCaregiverId(hhUniqueId);
                   cgiver.setCaregiverAge(hhe.getHhAge());
                   cgiver.setCaregiverGender(hhe.getHhGender());
                   caregiverList.add(cgiver);
               }
               else
               {
                   cgiver=(Caregiver)cgiver;
                   cgiver.setCaregiverLastName(cgiver.getCaregiverLastName()+"(HH)");
                   caregiverList.add(cgiver);
               }*/
               
               caregiverList2=cgiverdao.getListOfCaregiversFromSameHousehold(hhUniqueId);
               if(caregiverList2 !=null & !caregiverList2.isEmpty())
               {
                   Caregiver cgiver2=null;
                   for(int i=0; i<caregiverList2.size(); i++)
                   {
                       cgiver2=(Caregiver)caregiverList2.get(i);
                       //if(cgiver2 !=null && cgiver2.isHouseholdhead())
                       //continue;
                       caregiverList.add(cgiver2);
                   }
               }
               expform.setWard(util.getWardName(hhe.getCommunityCode()));
               uniqueIdMsg=hhe.getHhUniqueId();

                session.setAttribute("ovcEconsSaveBtnDisabled", "false");
                session.setAttribute("ovcEconsModifyBtnDisabled", "true");  
            }
            
            session.setAttribute("caregiverListInEcons", caregiverList);
            session.setAttribute("hhUniqueIdInEcons",uniqueIdMsg);
           return mapping.findForward(SUCCESS); 
        }
        else if(requiredAction.equalsIgnoreCase("caregiverDetails"))
        {
            System.err.println("uniqueId is "+uniqueId);
             hhe=hhedao.getHouseholdEnrollment(hhUniqueId);
            expform.reset(mapping, request);
            expform.setHhSerialNo(hhSerialNo);
            expform.setCaregiverId(uniqueId);
            if(hhe !=null)
            {
                expform.setWard(util.getWardName(hhe.getCommunityCode()));
            }
            expform.setHhUniqueId(hhUniqueId);
            //System.err.println("expform.getHhUniqueId is "+expform.getHhUniqueId());
            OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl();
            if(wdao.getWithdrawal(uniqueId)==null)
            {
                String ovcCaregiverId=null;
                cgiver=cgiverdao.getCaregiverByCaregiverId(uniqueId);
                if(cgiver !=null)
                {
                    ovcCaregiverId=uniqueId;
                    expform.setCaregiverAge(cgiver.getCaregiverAge());
                    expform.setCareiverGender(cgiver.getCaregiverGender());
                }
                else
                {
                    if(hhe !=null)
                    {
                        ovcCaregiverId=hhe.getHhUniqueId();
                       expform.setCaregiverAge(hhe.getHhAge());
                       expform.setCareiverGender(hhe.getHhGender()); 
                    }
                }
                if(ovcCaregiverId !=null)
                {
                    OvcDao dao=new OvcDaoImpl();
                    List ovcList=dao.getListOfOvcsPerCaregiver(" ", ovcCaregiverId);
                    if(ovcList !=null)
                    {
                        session.setAttribute("ovcListInEcons",ovcList);
                    }
                }
            }
            else
            {
                request.setAttribute("msg", "This caregiver has been withdrawn");
                session.setAttribute("ovcEconsSaveBtnDisabled", "true");
                session.setAttribute("ovcEconsModifyBtnDisabled", "true");
            }
            
            session.setAttribute("hhUniqueIdInEcons",hhUniqueId);
            return mapping.findForward(SUCCESS); 
        }
        else if(requiredAction.equalsIgnoreCase("assessmentDetails"))
        {
            OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl();
            if(wdao.getWithdrawal(uniqueId)!=null)
            {
                request.setAttribute("msg", "This caregiver has been withdrawn");
                session.setAttribute("ovcEconsSaveBtnDisabled", "true");
                session.setAttribute("ovcEconsModifyBtnDisabled", "true");
                return mapping.findForward(SUCCESS);
            }
            else if(dateOfAssessment !=null && dateOfAssessment.indexOf("-") !=-1)
            {
                //System.err.println("expform.getCaregiverId() is "+expform.getCaregiverId());
                 hhe=hhedao.getHouseholdEnrollment(hhUniqueId);
                 expform.setHhSerialNo(hhSerialNo);
                expform.setCaregiverId(uniqueId);
                if(hhe !=null)
                expform.setWard(util.getWardName(hhe.getCommunityCode()));
                CaregiverExpenditureAndSchoolAttendanceDao ceasadao=new CaregiverExpenditureAndSchoolAttendanceDaoImpl();
                CaregiverExpenditureAndSchoolAttendance ceasa=ceasadao.getCaregiverExpenditureAndSchoolAttendance(expform.getCaregiverId(), dateOfAssessment);
                if(ceasa !=null)
                {
                    expform=getCaregiverExpenditureAndSchoolAttendanceForm(expform,ceasa);
                    session.setAttribute("ovcEconsSaveBtnDisabled", "true");
                    session.setAttribute("ovcEconsModifyBtnDisabled", "false"); 
                }
                return mapping.findForward(SUCCESS);
            }
        }
        else if(requiredAction.equalsIgnoreCase("save"))
        {
            CaregiverExpenditureAndSchoolAttendanceDao ceasadao=new CaregiverExpenditureAndSchoolAttendanceDaoImpl();
            ceasadao.saveCaregiverExpenditureAndSchoolAttendance(getCaregiverExpenditureAndSchoolAttendance(expform,session));
        }
        else if(requiredAction.equalsIgnoreCase("modify"))
        {
            CaregiverExpenditureAndSchoolAttendanceDao ceasadao=new CaregiverExpenditureAndSchoolAttendanceDaoImpl();
            ceasadao.updateCaregiverExpenditureAndSchoolAttendance(getCaregiverExpenditureAndSchoolAttendance(expform,session));
        }
        else if(requiredAction.equalsIgnoreCase("delete"))
        {
            CaregiverExpenditureAndSchoolAttendanceDao ceasadao=new CaregiverExpenditureAndSchoolAttendanceDaoImpl();
            ceasadao.deleteCaregiverExpenditureAndSchoolAttendance(getCaregiverExpenditureAndSchoolAttendance(expform,session));
        }
        expform.reset(mapping, request);
        return mapping.findForward(SUCCESS);
    }
    public CaregiverExpenditureAndSchoolAttendance getCaregiverExpenditureAndSchoolAttendance(CaregiverExpenditureAndSchoolAttendanceForm form,HttpSession session)
    {
        System.err.println("form.getCaregiverId() is "+form.getCaregiverId());
        AppUtility appUtil=new AppUtility();
        CaregiverExpenditureAndSchoolAttendance ceasa=new CaregiverExpenditureAndSchoolAttendance();
        ceasa.setRecordId(form.getId());
        ceasa.setAccessMoney(form.getAccessMoney());
        ceasa.setCaregiverId(form.getCaregiverId());
        ceasa.setDateOfAssessment(DateManager.processMthDayYearToMysqlFormat(form.getDateOfAssessment()));
        ceasa.setOvcId(appUtil.concatStr(form.getOvcId(),null));
        ceasa.setReasonsForMissingSch(form.getReasonsForMissingSch());
        ceasa.setSchAttendance(form.getSchAttendance());
        ceasa.setSourceOfMoney(appUtil.concatStr(form.getSourceOfMoney(),null));
        ceasa.setUnexpectedExpenditure(form.getUnexpectedExpenditure());
        ceasa.setUrgentHhNeeds(appUtil.concatStr(form.getUrgentHhNeeds(),null));
        ceasa.setVolunteerName(form.getVolunteerName());
        ceasa.setVolunteerPhone(form.getVolunteerPhone());
        ceasa.setLastModifiedDate(appUtil.getCurrentDate());
        ceasa.setRecordedBy(appUtil.getCurrentUser(session));
        return ceasa;
    }
    public CaregiverExpenditureAndSchoolAttendanceForm getCaregiverExpenditureAndSchoolAttendanceForm(CaregiverExpenditureAndSchoolAttendanceForm form, CaregiverExpenditureAndSchoolAttendance ceasa)
    {
        AppUtility appUtil=new AppUtility();
        
        if(ceasa !=null && form !=null)
        {
            form.setAccessMoney(ceasa.getAccessMoney());
            form.setCaregiverId(ceasa.getCaregiverId());
            form.setDateOfAssessment(DateManager.getMthDayYearFromMySqlDate(ceasa.getDateOfAssessment()));
            String ovcIds=ceasa.getOvcId();
            if(ovcIds !=null)
            {
                form.setOvcId(appUtil.splitString(ovcIds, ","));
            }
            form.setReasonsForMissingSch(ceasa.getReasonsForMissingSch());
            form.setSchAttendance(ceasa.getSchAttendance());
            form.setUnexpectedExpenditure(ceasa.getUnexpectedExpenditure());
            form.setId(ceasa.getRecordId());
            String sourceOfMoney=ceasa.getSourceOfMoney();
            if(sourceOfMoney !=null)
            {
                String[] sourceOfMoneyArray=appUtil.splitString(ceasa.getSourceOfMoney(), ",");
                form.setSourceOfMoney(sourceOfMoneyArray);
            }
            String urgentNeeds=ceasa.getUrgentHhNeeds();
            if(urgentNeeds !=null)
            {
                String[] urgentNeedsArray=appUtil.splitString(urgentNeeds, ",");
                form.setUrgentHhNeeds(urgentNeedsArray);
            }
            form.setVolunteerName(ceasa.getVolunteerName());
            form.setVolunteerPhone(ceasa.getVolunteerPhone());
        }
                
        return form;
    }
    private void loadStartupInfo(HttpSession session)
    {
        session.setAttribute("hhUniqueId", " ");
        LoadUpInfo loadup=new LoadUpInfo();
        loadup.getStartupInfo(session);
        session.setAttribute("caregiverListInEcons", new ArrayList());
    }
}
