/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.Caregiver;
import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.nomis.nomisutils.LoadUpInfo;
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.business.OvcReferral;
import com.fhi.kidmap.business.OvcWithdrawal;
import com.fhi.kidmap.dao.CaregiverDao;
import com.fhi.kidmap.dao.CaregiverDaoImpl;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.HouseholdEnrollmentDao;
import com.fhi.kidmap.dao.HouseholdEnrollmentDaoImpl;
import com.fhi.kidmap.dao.OvcDao;
import com.fhi.kidmap.dao.OvcDaoImpl;
import com.fhi.kidmap.dao.OvcReferralDao;
import com.fhi.kidmap.dao.OvcReferralDaoImpl;
import com.fhi.kidmap.dao.OvcWithdrawalDao;
import com.fhi.kidmap.dao.OvcWithdrawalDaoImpl;
import com.fhi.nomis.nomisutils.AccessManager;
import com.fhi.nomis.nomisutils.DateManager;
import java.util.ArrayList;
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
public class OvcReferralAction extends org.apache.struts.action.Action {
    
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

        OvcReferralServiceForm orsf=(OvcReferralServiceForm)form;
        OvcReferral ovcRef=new OvcReferral();
        AppUtility appUtil=new AppUtility();
        DaoUtil util=new DaoUtil();
        OvcReferralDao ovcRefDao=new OvcReferralDaoImpl();
        CaregiverDao cgiverDao=new CaregiverDaoImpl();
        Caregiver cgiver=null;
        OvcDao ovcDao=new OvcDaoImpl();
        HttpSession session=request.getSession();
        LoadUpInfo loadup=new LoadUpInfo();
        session.setAttribute("ovcIdFieldDisabled", "False");
        session.setAttribute("vcHhUniqueId", " ");
        AccessManager acm=new AccessManager();
        boolean userInRole=acm.isUserInRole("001",appUtil.getCurrentUser(session));
        if(!userInRole)
        {
            loadup.getStartupInfo(session);
            session.setAttribute("referralModifyDisabled", "true");
            session.setAttribute("referralSaveDisabled", "true");
            return mapping.findForward("success");
        }
        String origRefDate=orsf.getDateOfReferral();
        //String[] refDateArray=null;
        String mySqlDate=appUtil.processMthDayYearToMysqlFormat(origRefDate);
        ovcRef.setDateOfReferral(mySqlDate);

        String typeOfReferral=orsf.getType();
        String ovcId=orsf.getOvcId();
        if(ovcId !=null)
          ovcId.trim();
       ovcRef.setType(typeOfReferral);
       /*if(typeOfReferral !=null && typeOfReferral.equalsIgnoreCase("VC"))
       ovcRef.setOvcId(ovcId);
       else*/
       ovcRef.setOvcId(orsf.getOvcId());
       ovcRef.setRecordId(orsf.getRecordId());
       ovcRef.setNameOfOrganizationChildIsreferred(orsf.getNameOfOrganizationChildIsreferred());
       ovcRef.setReferralCompleted(orsf.getReferralCompleted());
       ovcRef.setReferringOrganization(orsf.getCompletedbyCbo());
       ovcRef.setDesignationOfReferrer(orsf.getDesignationOfReferrer());
       ovcRef.setReferrerEmail(orsf.getReferrerEmail());
       ovcRef.setReferrerPhoneNo(orsf.getReferrerPhoneNo());
       

       ovcRef.setEconomicServices(appUtil.concatStr(orsf.getEconomicServices(), orsf.getEconomicOther()));
       ovcRef.setEducationalServices(appUtil.concatStr(orsf.getEducationalServices(), orsf.getEducationOther()));
       ovcRef.setHealthServices(appUtil.concatStr(orsf.getHealthServices(), orsf.getHealthOther()));
       ovcRef.setNameOfPersonReferring(orsf.getNameOfReferrer());
       ovcRef.setNutritionalServices(appUtil.concatStr(orsf.getNutritionalServices(), orsf.getNutritionOther()));
       ovcRef.setProtectionServices(appUtil.concatStr(orsf.getProtectionServices(), orsf.getProtectionOther()));
       ovcRef.setPsychoServices(appUtil.concatStr(orsf.getPsychoServices(), orsf.getPsychosocialOther()));
       ovcRef.setShelterServices(appUtil.concatStr(orsf.getShelterServices(), orsf.getShelterOther()));

       String requiredAction=orsf.getActionName();
       
       String lgaCode=orsf.getLga();
       String cboCode=orsf.getCompletedbyCbo();
       String ward=orsf.getWard();
       String hhUniqueId=orsf.getHhUniqueId();
       String hhSerialNo=orsf.getHhSerialNo();
       List ovcList=new ArrayList();

       String hhUniqueIdMsg="<span style=color:red>This household unique Id does not exist</span>";
       
       if(requiredAction ==null)
       {
           orsf.reset(mapping, request);
           loadup.getStartupInfo(session);
           session.setAttribute("referralOvcList", ovcList);
           session.setAttribute("referralCaregiverList", ovcList);
           session.setAttribute("refName", "VC Name");
           session.setAttribute("refId", "Ovc Unique Id");
           session.setAttribute("refIdValue", " ");
           session.setAttribute("referralModifyDisabled", "true");
           session.setAttribute("referralSaveDisabled", "false");
           return mapping.findForward(SUCCESS);
       }
       else if(requiredAction.equalsIgnoreCase("cboList"))
       {
            loadup.getCbosPerLgaFromSetup(session,orsf.getLga());
            orsf.reset(mapping, request);
            orsf.setLga(lgaCode);
            return mapping.findForward("success");
       }
       else if(requiredAction.equalsIgnoreCase("cbo"))
       {
           form.reset(mapping, request);
           orsf.setLga(lgaCode);
           orsf.setCompletedbyCbo(cboCode);
           session.setAttribute("referralModifyDisabled", "true");
           session.setAttribute("referralSaveDisabled", "false");
           return mapping.findForward(SUCCESS);
       }
       else if(requiredAction.equalsIgnoreCase("hhDetails"))
    {    
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        orsf.reset(mapping, request);
          try
          {
                    HouseholdEnrollment hhe=hhedao.getHouseholdEnrollment(hhUniqueId);

                    orsf.setLga(lgaCode);
                    orsf.setCompletedbyCbo(cboCode);
                    orsf.setHhSerialNo(hhSerialNo);
                    if(hhe !=null)
                    {
                        hhUniqueIdMsg=hhUniqueId;
                        orsf.setHhUniqueId(hhUniqueId);
                        orsf.setWard(util.getWardName(hhe.getCommunityCode()));
                        if(typeOfReferral.equalsIgnoreCase("VC"))
                        {
                            ovcList=ovcDao.getOvcPerHousehold(hhUniqueId);
                            session.setAttribute("referralOvcList", ovcList);
                            session.setAttribute("referralCaregiverList", new ArrayList());
                            session.setAttribute("refName", "VC Name");
                            session.setAttribute("refId", "Ovc Unique Id");
                            session.setAttribute("refIdValue", " ");
                        }
                        else if(typeOfReferral.equalsIgnoreCase("household"))
                        {
                            List caregiverList=cgiverDao.getListOfCaregiversFromSameHousehold(hhUniqueId);
                            session.setAttribute("referralCaregiverList", caregiverList);
                            session.setAttribute("referralOvcList", ovcList);
                            session.setAttribute("refName", "Caregiver Name");
                            session.setAttribute("refId", "Caregiver Id");//refIdValue
                            session.setAttribute("refIdValue", " ");
                        }
                        orsf.setType(typeOfReferral);

                    }
          }
          catch(Exception ex)
          {
              ex.printStackTrace();
          }
          session.setAttribute("vcHhUniqueId", hhUniqueIdMsg);
          return mapping.findForward("success");
       }
       else if(requiredAction.equalsIgnoreCase("baselineDetails"))
       {
           String sno=orsf.getOvcSno();
           orsf.reset(mapping, request);
           orsf.setLga(lgaCode);
           orsf.setCompletedbyCbo(cboCode);
           orsf.setHhUniqueId(hhUniqueId);
           orsf.setHhSerialNo(hhSerialNo);
           hhUniqueIdMsg=hhUniqueId;
           session.setAttribute("vcHhUniqueId", hhUniqueIdMsg);
           Ovc ovc=null;
           ovcDao=new OvcDaoImpl();
           OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl();
           List list=ovcDao.getOVC(ovcId);
           String ovcWithdrawn=" ";
           //list=wdao.getWithdrawnOvc(ovcId);
           //List ovcDetailsList=new ArrayList();
           int age=0;
           String address=null;
           String ageUnit=null;
           String phone=null;
           String gender=null;
           String caregiverName=null;
           OvcWithdrawal withdrawal=wdao.getWithdrawal(ovcId);
            if(withdrawal !=null)
            {
                ovcWithdrawn="This VC has been withdrawn";
                session.setAttribute("ovcWithdrawn", ovcWithdrawn);
                session.setAttribute("referralModifyDisabled", "true");
                session.setAttribute("referralSaveDisabled", "true");
                return mapping.findForward(SUCCESS);
            }
           else if(typeOfReferral.equalsIgnoreCase("VC"))
           {
                ovc=ovcDao.getOvc(ovcId);
                if(ovc !=null)
                {
                    age=ovc.getAge();
                    address=ovc.getAddress();
                    ageUnit=ovc.getAgeUnit();
                    phone=ovc.getPhone();
                    gender=ovc.getGender();
                    session.setAttribute("refIdValue", ovc.getOvcId());
                    Caregiver cgiver2=cgiverDao.getCaregiverByCaregiverId(ovc.getCaregiverId());
                    if(cgiver2 !=null)
                    {
                        caregiverName=cgiver2.getCaregiverFirstname()+" "+cgiver2.getCaregiverLastName();
                    }
                }
           }
           else if(typeOfReferral.equalsIgnoreCase("household"))
           {
                cgiver=cgiverDao.getCaregiverByCaregiverId(ovcId);
                if(cgiver !=null)
                {
                    age=cgiver.getCaregiverAge();
                    address=cgiver.getCaregiverAddress();
                    ageUnit="Years";
                    phone=cgiver.getCaregiverPhone();
                    gender=cgiver.getCaregiverGender();
                    session.setAttribute("refIdValue", cgiver.getCaregiverId());
                }
           }
            if(ovc !=null || cgiver !=null)
            {
                orsf.setOvcSno(sno);
                orsf.setOvcId(ovcId);
                orsf.setLga(lgaCode);
                orsf.setCompletedbyCbo(cboCode);
                orsf.setWard(ward);
                
                orsf.setAge(age);
                orsf.setAgeUnit(ageUnit);
                orsf.setAddress(address);
                orsf.setPhone(phone);
                orsf.setGender(gender);
                orsf.setCaregiverName(caregiverName);
                orsf.setType(typeOfReferral);
                session.setAttribute("referralModifyDisabled", "true");
                session.setAttribute("referralSaveDisabled", "false");
            }
            else
            {
                session.setAttribute("ovcWithdrawn", "This VC does not exist in the enrolment record");
                session.setAttribute("referralModifyDisabled", "true");
                session.setAttribute("referralSaveDisabled", "true");
            }
            return mapping.findForward(SUCCESS);
       }
       if(requiredAction.equalsIgnoreCase("referralDetails"))
       {
           hhUniqueIdMsg=hhUniqueId;
           session.setAttribute("vcHhUniqueId", hhUniqueIdMsg);
           orsf.setEconomicServices(null);
            orsf.setEducationalServices(null);
            orsf.setHealthServices(null);
            orsf.setNutritionalServices(null);
            orsf.setProtectionServices(null);
            orsf.setPsychoServices(null);
            orsf.setReferrerEmail(null);
            orsf.setReferrerPhoneNo(null);
            orsf.setNameOfReferrer(null);
            orsf.setDesignationOfReferrer(null);
            orsf.setDateOfReferral(origRefDate);
           boolean dateFlag=isReferralDateBeforeEnrollmentDate(ovcRef,typeOfReferral,hhUniqueId);
            if(!dateFlag)
            {
                ActionErrors errors = new ActionErrors();
                errors.add(ActionErrors.GLOBAL_MESSAGE,
                new ActionMessage("error.followupdatebeforeenrollmentdate",
                "Date of referral earlier than enrollment date"));
                if (!errors.isEmpty())
                {
                    orsf.setDateOfReferral(origRefDate);
                    saveErrors(request, errors);
                    return mapping.findForward("success");
                }
            }
            //if(typeOfReferral.equalsIgnoreCase("household"))
            ovcRef=ovcRefDao.getOvcReferral(orsf.getOvcId(),mySqlDate);
            //else
            //ovcRef=ovcRefDao.getOvcReferral(orsf.getOvcId(),mySqlDate);
            
            if(ovcRef !=null)
            {
                orsf.setRecordId(ovcRef.getRecordId());
                orsf.setEconomicServices(setOtherServiceParameters(orsf,appUtil.splitString(ovcRef.getEconomicServices(),","),"economicOther"));
                orsf.setEducationalServices(setOtherServiceParameters(orsf,appUtil.splitString(ovcRef.getEducationalServices(),","),"educationOther"));
                orsf.setHealthServices(setOtherServiceParameters(orsf,appUtil.splitString(ovcRef.getHealthServices(),","),"healthOther"));
                orsf.setNutritionalServices(setOtherServiceParameters(orsf,appUtil.splitString(ovcRef.getNutritionalServices(),","),"nutritionOther"));
                orsf.setProtectionServices(setOtherServiceParameters(orsf,appUtil.splitString(ovcRef.getProtectionServices(),","),"protectionOther"));
                orsf.setPsychoServices(setOtherServiceParameters(orsf,appUtil.splitString(ovcRef.getPsychoServices(),","),"psychosocialOther"));
                orsf.setShelterServices(setOtherServiceParameters(orsf,appUtil.splitString(ovcRef.getShelterServices(),","),"shelterOther"));
                String referralCompleted=ovcRef.getReferralCompleted();

                if(referralCompleted !=null)
                referralCompleted=referralCompleted.trim();
                
                orsf.setReferralCompleted(referralCompleted);
                orsf.setNameOfOrganizationChildIsreferred(ovcRef.getNameOfOrganizationChildIsreferred());
                orsf.setNameOfReferrer(ovcRef.getNameOfPersonReferring());
                orsf.setDesignationOfReferrer(ovcRef.getDesignationOfReferrer());
                orsf.setReferrerPhoneNo(ovcRef.getReferrerPhoneNo());
                orsf.setReferrerEmail(ovcRef.getReferrerEmail());

                session.setAttribute("referralModifyDisabled", "false");
                session.setAttribute("referralSaveDisabled", "true");
            }
            else
            {
                session.setAttribute("referralModifyDisabled", "true");
                session.setAttribute("referralSaveDisabled", "false");
            }
            orsf.setType(typeOfReferral);
            return mapping.findForward(SUCCESS);
       }

       else if(requiredAction.equalsIgnoreCase("save"))
       {
            ovcRefDao.saveOvcReferral(ovcRef);
       }
       else if(requiredAction.equalsIgnoreCase("modify"))
       {
            ovcRefDao.updateOvcReferral(ovcRef);
       }
       else if(requiredAction.equalsIgnoreCase("delete"))
       {
            ovcRefDao.deleteOvcReferral(ovcRef);
       }
       orsf.reset(mapping, request);
       orsf.setLga(lgaCode);
       orsf.setCompletedbyCbo(cboCode);
       session.setAttribute("referralModifyDisabled", "true");
       session.setAttribute("referralSaveDisabled", "false");
       
       return mapping.findForward(SUCCESS);
    }
    private boolean isReferralDateBeforeEnrollmentDate(OvcReferral referral,String type,String hhUniqueId)
{
            OvcDao odao=new OvcDaoImpl();
            HouseholdEnrollmentDao hvidao=new HouseholdEnrollmentDaoImpl();
            
            Ovc ovc;
            boolean dateFlag=false;
            try
            {
                HouseholdEnrollment hvi=(HouseholdEnrollment)hvidao.getHouseholdEnrollment(hhUniqueId);
                String enrollmentDate=null;
                if(type==null)
                return false;
                if(type.equalsIgnoreCase("household"))
                enrollmentDate=hvi.getDateOfAssessment();
                else
                {
                    ovc=(Ovc)odao.getOvc(referral.getOvcId());
                    enrollmentDate=ovc.getDateEnrollment();
                }
                String serviceDate=referral.getDateOfReferral();
                dateFlag=DateManager.compareDates(enrollmentDate,serviceDate);
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
            return dateFlag;
}
    String otherService="";
    String[] checkBoxServices=null;
    private String[] setOtherServiceParameters(OvcReferralServiceForm orsf,String[] services,String otherServiceName)
    {
        
        if(services !=null)
        {
            checkBoxServices=new String[services.length];
            for(int i=0; i<services.length; i++)
            {
                if(services[i] !=null && services[i].indexOf(";") !=-1)
                {
                    checkBoxServices[i]=(services[i].split(";"))[0];
                    otherService=(services[i].split(";"))[1];
                    if(otherServiceName.equals("protectionOther"))
                    orsf.setProtectionOther(otherService);
                    else if(otherServiceName.equals("psychosocialOther"))
                    orsf.setPsychosocialOther(otherService);
                    else if(otherServiceName.equals("nutritionOther"))//nutritionOther
                    orsf.setNutritionOther(otherService);
                    else if(otherServiceName.equals("healthOther"))
                    orsf.setHealthOther(otherService);
                    else if(otherServiceName.equals("educationOther"))
                    orsf.setEducationOther(otherService);
                    else if(otherServiceName.equals("shelterOther"))
                    orsf.setShelterOther(otherService);
                    else if(otherServiceName.equals("economicOther"))
                    orsf.setEconomicOther(otherService);
                }
                else
                checkBoxServices[i]=services[i];
        }
        }

         return checkBoxServices;
    }
}
