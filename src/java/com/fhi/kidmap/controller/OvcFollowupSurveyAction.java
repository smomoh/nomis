/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.Caregiver;
import com.fhi.kidmap.business.ChildStatusIndex;
import com.fhi.kidmap.business.FollowupSurvey;
import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.nomis.nomisutils.LoadUpInfo;
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.business.OvcWithdrawal;
import com.fhi.kidmap.business.States;
import com.fhi.kidmap.dao.CaregiverDao;
import com.fhi.kidmap.dao.CaregiverDaoImpl;
import com.fhi.kidmap.dao.ChildStatusIndexDao;
import com.fhi.kidmap.dao.ChildStatusIndexDaoImpl;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.FollowupDao;
import com.fhi.kidmap.dao.FollowupDaoImpl;
import com.fhi.kidmap.dao.HouseholdEnrollmentDao;
import com.fhi.kidmap.dao.HouseholdEnrollmentDaoImpl;
import com.fhi.kidmap.dao.OvcDao;
import com.fhi.kidmap.dao.OvcDaoImpl;
import com.fhi.kidmap.dao.OvcSchoolDao;
import com.fhi.kidmap.dao.OvcSchoolDaoImpl;
import com.fhi.kidmap.dao.OvcWithdrawalDao;
import com.fhi.kidmap.dao.OvcWithdrawalDaoImpl;
import com.fhi.nomis.OperationsManagement.HivRecordsManager;
import com.fhi.nomis.nomisutils.AccessManager;
import com.fhi.nomis.nomisutils.DateManager;
import com.fhi.nomis.nomisutils.NomisConstant;
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
public class OvcFollowupSurveyAction extends org.apache.struts.action.Action {
    
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
        
       String target = "success";
       
    DaoUtil util=new DaoUtil();
    OvcFollowupSurveyForm ovcfollowupsurveyForm = (OvcFollowupSurveyForm) form;
    CaregiverDao cgiverDao=new CaregiverDaoImpl();
    String ovcWithdrawn=" ";

HttpSession session = request.getSession();
AppUtility appUtil=new AppUtility();
String reasonWithdrawn=ovcfollowupsurveyForm.getReasonWithdrawal();
LoadUpInfo loadup=new LoadUpInfo();
loadup.processCboSetup(session);
//loadup.getSchoolsPerState(session);

loadup.setParamAttributes(request);
session.setAttribute("vcHhUniqueId", " ");
request.setAttribute("ovcWithdrawn", ovcWithdrawn);
AccessManager acm=new AccessManager();
boolean userInRole=acm.isUserInRole("001",appUtil.getCurrentUser(session));
if(!userInRole)
{
    session.setAttribute("followupSaveDisabled", "true");
    session.setAttribute("followupModifyDisabled", "true");
    return mapping.findForward("success");
}

OvcDao ovcDao=new OvcDaoImpl();
OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl();
ChildStatusIndexDao csDao=new ChildStatusIndexDaoImpl();
ChildStatusIndex csi=new ChildStatusIndex();
HouseholdEnrollment hhe=new HouseholdEnrollment();
HouseholdEnrollmentDao hheDao=new HouseholdEnrollmentDaoImpl();
Ovc ovc=null;
List list=new ArrayList();
String currentDate=appUtil.getCurrentDate();
//HttpSession session=request.getSession();
String lgaCode=ovcfollowupsurveyForm.getLga();
String cboCode=ovcfollowupsurveyForm.getCompletedbyCbo();
String hhSerialNo=ovcfollowupsurveyForm.getHhSerialNo();
String hhUniqueId=ovcfollowupsurveyForm.getHhUniqueId();
String ovcId=ovcfollowupsurveyForm.getOvcId();
String selectedCbo=ovcfollowupsurveyForm.getCompletedbyCbo();
String ward=ovcfollowupsurveyForm.getWard();
String requiredAction=ovcfollowupsurveyForm.getActionName();
String surveyDate=ovcfollowupsurveyForm.getDateOfSurvey();
String ovcSno=ovcfollowupsurveyForm.getOvcSno();
String[] dateArray;
String dateOfSurvey="";
if(surveyDate !=null)
{
    dateArray=surveyDate.split("/");
    if(dateArray.length>2)
    dateOfSurvey=dateArray[2]+"-"+dateArray[0]+"-"+dateArray[1];
}

FollowupSurvey fs=new FollowupSurvey();
FollowupDao fdao=new FollowupDaoImpl();
ovc=ovcDao.getOvc(ovcId);
if(ovc==null)
{
    ovc=new Ovc();
}
fs.setDateOfSurvey(dateOfSurvey);
fs.setOvcId(ovcId);
fs.setUpdatedAddress(ovcfollowupsurveyForm.getUpdatedAddress());
if(ovcfollowupsurveyForm.getUpdatedAddress()==null)
fs.setUpdatedAddress(ovc.getAddress());
fs.setUpdatedAge(ovc.getCurrentAge());
fs.setUpdatedAgeUnit("Year");
fs.setUpdatedBirthCertStatus(ovcfollowupsurveyForm.getUpdatedBirthCertStatus());
if(ovcfollowupsurveyForm.getUpdatedBirthCertStatus()==null)
fs.setUpdatedBirthCertStatus(ovc.getBirthCertificate());
String hivStatus=ovc.getActiveHivStatus().getHivStatus();
//set last hiv status as the default hiv status
fs.setUpdatedHivStatus(hivStatus);
fs.setEnrolledInCare(ovcfollowupsurveyForm.getEnrolledInCare());
fs.setEnrolledOnART(ovcfollowupsurveyForm.getEnrolledOnArt());
fs.setFacilityId(ovcfollowupsurveyForm.getVcFacilityId());
fs.setCgiverEnrolledInCare(ovcfollowupsurveyForm.getCgiverEnrolledInCare());
fs.setCgiverEnrolledOnART(ovcfollowupsurveyForm.getCgiverEnrolledOnArt());
fs.setCgiverFacilityId(ovcfollowupsurveyForm.getCgiverFacilityId());

//if a new updated hiv status is selected, set it as the new updated hiv status
String childUpdatedHivStatus=ovcfollowupsurveyForm.getChildUpdatedHivStatus();
if(childUpdatedHivStatus !=null && !childUpdatedHivStatus.equalsIgnoreCase("select"))
fs.setUpdatedHivStatus(childUpdatedHivStatus);

fs.setUpdatedSchoolStatus(ovcfollowupsurveyForm.getUpdatedSchoolStatus());
if(ovcfollowupsurveyForm.getUpdatedSchoolStatus()==null)
fs.setUpdatedSchoolStatus(ovc.getSchoolStatus());
fs.setUpdatedSchoolName(ovcfollowupsurveyForm.getUpdatedSchoolName());
if(ovcfollowupsurveyForm.getUpdatedSchoolName()==null)
fs.setUpdatedSchoolName(ovc.getSchoolName());
fs.setUpdatedClass(ovcfollowupsurveyForm.getUpdatedClass());
if(ovcfollowupsurveyForm.getUpdatedClass()==null)
fs.setUpdatedClass(ovc.getCurrentClass());
fs.setCgiverHivStatus(ovcfollowupsurveyForm.getCgiverHivStatus());
String caregiverId=ovcfollowupsurveyForm.getCaregiverId();

fs.setCaregiverId(caregiverId);
fs.setUpdatedCaregiverRelationship(ovcfollowupsurveyForm.getUpdatedCaregiverRelationship());
fs.setCompletedbyName(ovcfollowupsurveyForm.getCompletedbyName());
fs.setCompletedbyDesignation(ovcfollowupsurveyForm.getCompletedbyDesignation());
fs.setDateOfEntry(appUtil.getCurrentDate());
fs.setRecordedBy((String)session.getAttribute("userRealNames"));

String caregiverUpdatedHivStatus=ovcfollowupsurveyForm.getCgiverHivStatus();
String cgiverEnrolledInCare=ovcfollowupsurveyForm.getCgiverEnrolledInCare();
Caregiver cgiver=null;
if(caregiverId !=null)
{
    cgiver=cgiverDao.getCaregiverByCaregiverId(caregiverId);
    if(cgiver !=null)
    {
        if(caregiverUpdatedHivStatus !=null && !caregiverUpdatedHivStatus.equalsIgnoreCase("select"))
        cgiver.setCgiverHivStatus(caregiverUpdatedHivStatus);
        cgiver.setCaregiverPhone(ovcfollowupsurveyForm.getCaregiverPhone());
        if(cgiverEnrolledInCare !=null && caregiverUpdatedHivStatus.equalsIgnoreCase("positive"))
        cgiver.setEnrolledInCare(cgiverEnrolledInCare);
        cgiver.setPointOfService("followup");
    }
}
csi.setCsiDate(dateOfSurvey);
csi.setOvcId(ovcId);
csi.setCsiFactor1(ovcfollowupsurveyForm.getCsiFactor1());
csi.setCsiFactor2(ovcfollowupsurveyForm.getCsiFactor2());
csi.setCsiFactor3(ovcfollowupsurveyForm.getCsiFactor3());
csi.setCsiFactor4(ovcfollowupsurveyForm.getCsiFactor4());
csi.setCsiFactor5(ovcfollowupsurveyForm.getCsiFactor5());
csi.setCsiFactor6(ovcfollowupsurveyForm.getCsiFactor6());
csi.setCsiFactor7(ovcfollowupsurveyForm.getCsiFactor7());
csi.setCsiFactor8(ovcfollowupsurveyForm.getCsiFactor8());
csi.setCsiFactor9(ovcfollowupsurveyForm.getCsiFactor9());
csi.setCsiFactor10(ovcfollowupsurveyForm.getCsiFactor10());
csi.setCsiFactor11(ovcfollowupsurveyForm.getCsiFactor11());
csi.setCsiFactor12(ovcfollowupsurveyForm.getCsiFactor12());
csi.setDateOfEntry(appUtil.getCurrentDate());

String stateCode=ovcfollowupsurveyForm.getState();
States state=(States)session.getAttribute("state");
if(state !=null)
stateCode=state.getState_code();
List facilityList=util.getReferralDirectoriesByState(stateCode);
ovcfollowupsurveyForm.setReferralDirectoryList(facilityList);
        
List schoolList=new ArrayList();
OvcSchoolDao sdao=new OvcSchoolDaoImpl();
schoolList=sdao.getSchoolList(appUtil.getCurrentState(session));
if(schoolList !=null)
ovcfollowupsurveyForm.setSchoolList(schoolList);
request.setAttribute("schoolDisabled", "true");
List hivStatusList=HivRecordsManager.loadNewHivStatus(hivStatus);
ovcfollowupsurveyForm.setHivStatusList(hivStatusList);
ovcfollowupsurveyForm.setChildCurrentHivStatus(hivStatus);
List ovcList=new ArrayList();
String hhUniqueIdMsg="<span style=color:red>This household unique Id does not exist</span>";
if(requiredAction==null)
{
    loadup.getStartupInfo(session);
    if(lgaCode !=null)
    {
        List cboList=loadup.getCbosPerLgaFromSetup(session,lgaCode);
        session.setAttribute("cboListInOrgRecords", cboList);
    }
    ovcfollowupsurveyForm.setCaregiverList(new ArrayList());
    
    session.setAttribute("ovcListInFollowup", ovcList);
    session.setAttribute("vcUniqueId", " ");
    session.setAttribute("followupSaveDisabled", "false");
    session.setAttribute("followupModifyDisabled", "true");
    return mapping.findForward("success");
}
else if(requiredAction.equalsIgnoreCase("cboList"))
{
        loadup.getCbosPerLgaFromSetup(session,lgaCode);
        ovcfollowupsurveyForm.reset(mapping, request);
        ovcfollowupsurveyForm.setLga(lgaCode);
        return mapping.findForward("success");
}
else if(requiredAction.equalsIgnoreCase("cbo"))
{
    form.reset(mapping, request);
    ovcfollowupsurveyForm.setCompletedbyCbo(selectedCbo);
    ovcfollowupsurveyForm.setActionName(null);
    ovcfollowupsurveyForm.setLga(lgaCode);
    session.setAttribute("followupSaveDisabled", "false");
    session.setAttribute("followupModifyDisabled", "true");
    target="success";
    return (mapping.findForward(target));
}
else if(requiredAction.equalsIgnoreCase("caregiverDetails"))
{
    cgiver=cgiverDao.getCaregiverByCaregiverId(caregiverId);
    if(cgiver !=null)
    {
        ovcfollowupsurveyForm.setCaregiverPhone(cgiver.getCaregiverPhone());
        ovcfollowupsurveyForm.setCaregiverId(caregiverId);
        ovcfollowupsurveyForm.setCgiverHivStatus(cgiver.getCgiverHivStatus());
        ovcfollowupsurveyForm.setUpdatedCaregiverAddress(cgiver.getCaregiverAddress());
        ovcfollowupsurveyForm.setUpdatedCaregiverAge(cgiver.getCaregiverAge());
        ovcfollowupsurveyForm.setUpdatedCaregiverGender(cgiver.getCaregiverGender());
        ovcfollowupsurveyForm.setUpdatedCaregiverOccupation(cgiver.getCaregiverOccupation());
        ovcfollowupsurveyForm.setCgiverCurrentHivStatus(cgiver.getActiveHivStatus().getHivStatus());
        List cgiverHivStatusList=HivRecordsManager.loadNewHivStatus(cgiver.getActiveHivStatus().getHivStatus());
        ovcfollowupsurveyForm.setCaregiverHivStatusList(cgiverHivStatusList);
    }
    return (mapping.findForward(target));
}


try
{
        if(requiredAction.equalsIgnoreCase("hhDetails"))
        {

            HouseholdEnrollmentDao hhvidao=new HouseholdEnrollmentDaoImpl();
            ovcfollowupsurveyForm.reset(mapping, request);
              try
              {
                        HouseholdEnrollment hhvi=hhvidao.getHouseholdEnrollment(hhUniqueId);
                        ovcfollowupsurveyForm.setLga(lgaCode);
                        ovcfollowupsurveyForm.setCompletedbyCbo(selectedCbo);
                        ovcfollowupsurveyForm.setHhSerialNo(hhSerialNo);
                        OvcWithdrawal withdrawal=wdao.getWithdrawal(hhUniqueId);
                        if(withdrawal !=null)
                        {
                            ovcWithdrawn="This household has been withdrawn";
                            request.setAttribute("ovcWithdrawn", ovcWithdrawn);
                            form.reset(mapping, request);
                            return (mapping.findForward(target));
                        }
                        if(hhvi !=null)
                        {
                            hhUniqueIdMsg=hhUniqueId;
                            ovcList=ovcDao.getOvcPerHousehold(hhUniqueId);
                            session.setAttribute("ovcListInFollowup", ovcList);
                            ovcfollowupsurveyForm.setWard(util.getWardName(hhvi.getCommunityCode()));
                            ovcfollowupsurveyForm.setHhUniqueId(hhUniqueId);
                            List cgiverList=cgiverDao.getListOfCaregiversFromSameHousehold(hhUniqueId);
                            cgiver=new Caregiver();
                            cgiver.setCaregiverId(hhvi.getHhUniqueId());
                            cgiver.setCaregiverFirstname(hhvi.getHhFirstname());
                            cgiver.setCaregiverLastName(hhvi.getHhSurname());
                            
                            if(cgiverList !=null)
                            {
                                cgiverList.add(cgiver);
                            }
                            else
                            {
                                cgiverList=new ArrayList();
                                cgiverList.add(cgiver);
                            }
                            ovcfollowupsurveyForm.setCaregiverList(cgiverList);
                        }
              }
              catch(Exception ex)
              {
                  ex.printStackTrace();
              }
          session.setAttribute("vcHhUniqueId", hhUniqueIdMsg);
          return mapping.findForward("success");
}
    else if(requiredAction.equalsIgnoreCase("baselineDetails") || requiredAction.equalsIgnoreCase("followupDetails"))
    {
        form.reset(mapping, request);
        hhUniqueIdMsg=hhUniqueId;
        session.setAttribute("vcHhUniqueId", hhUniqueIdMsg);
        ovcWithdrawn=" ";
        
        hhe=hheDao.getHouseholdEnrollment(hhUniqueId);
        ovcfollowupsurveyForm.setLga(lgaCode);
        ovcfollowupsurveyForm.setCompletedbyCbo(cboCode);
        ovcfollowupsurveyForm.setWard(util.getWardName(hhe.getCommunityCode()));
        ovcfollowupsurveyForm.setHhSerialNo(hhSerialNo);
        ovcfollowupsurveyForm.setHhUniqueId(hhUniqueId);
        
        session.setAttribute("vcUniqueId", ovcId);
        OvcWithdrawal withdrawal=wdao.getWithdrawal(ovcId);
        if(withdrawal !=null)
        {
            ovcWithdrawn="This VC has been withdrawn";
            request.setAttribute("ovcWithdrawn", ovcWithdrawn);
            form.reset(mapping, request);
            return (mapping.findForward(target));
        }
        
        ovc=ovcDao.getOvc(ovcId);
        if(ovc ==null)
        {
            session.setAttribute("ovcWithdrawn", "This VC does not exist in the enrolment record");
        }
        else
        {
            List fsList=fdao.getFollowupRecordsByOvcId(ovcId);
            if(fsList !=null && !fsList.isEmpty())
            {
                FollowupSurvey fs1=(FollowupSurvey)fsList.get(0);
                if(fs1.getCaregiverId() !=null && fs1.getCaregiverId().length()>1)
                ovc.setCaregiverId(fs1.getCaregiverId());
            }
            ovcfollowupsurveyForm.setOvcSno(ovcSno);
            //ovcfollowupsurveyForm.setWard(ward);
            setBaselineInfo(ovc,ovcfollowupsurveyForm);
            int currentAge=util.getCurrentAge(ovc);
            if(currentAge > 17)
            session.setAttribute("currentAge", "overaged;"+currentAge);
            else
            session.setAttribute("currentAge", "underaged;"+currentAge);
            ovcfollowupsurveyForm.setActionName(null);
            ovcfollowupsurveyForm.setCompletedbyCbo(selectedCbo);
            ovcfollowupsurveyForm.setAge(currentAge);
            ovcfollowupsurveyForm.setAgeUnit(ovc.getAgeUnit());
            //ovcfollowupsurveyForm.setEnrolledInCare(fs.getEnrolledInCare());
            //ovcfollowupsurveyForm.setEnrolledOnArt(fs.getEnrolledOnART());
            List fsList2=fdao.getFollowupRecordsByOvcId(ovcId);
            if(fsList2 !=null && !fsList2.isEmpty())
            {
                FollowupSurvey fs2=null;
                for(int i=0; i<fsList2.size(); i++)
                {
                    fs2=(FollowupSurvey)fsList2.get(i);
                    System.err.println(fs2.getDateOfSurvey());
                }
                
                ovcfollowupsurveyForm.setUpdatedAddress(fs2.getUpdatedAddress());
            }
            cgiver=cgiverDao.getCaregiverByCaregiverId(ovc.getCaregiverId());
            if(cgiver !=null)
            {
                ovcfollowupsurveyForm.setUpdatedCaregiverRelationship(ovc.getCaregiverRelationships());
                ovcfollowupsurveyForm.setBirthCertificateAtEnrollment(ovc.getBirthCertificate());
            }
            
            if(requiredAction.equalsIgnoreCase("followupDetails"))
            {
                hivStatusList=HivRecordsManager.loadNewHivStatus("negpos");
                boolean dateFlag=isFollowupDateBeforeEnrollmentDate(fs);
                if(!dateFlag)
                {
                    ActionErrors errors = new ActionErrors();
                    errors.add(ActionErrors.GLOBAL_MESSAGE,
                    new ActionMessage("error.followupdatebeforeenrollmentdate",
                    "Followup survey date earlier than enrollment date"));
                    if (!errors.isEmpty())
                    {
                        ovcfollowupsurveyForm.setDateOfSurvey(surveyDate);
                        saveErrors(request, errors);
                        return (mapping.findForward("success"));
                    }
                }
                fs=fdao.getFollowup(ovcId, dateOfSurvey);
                if(fs==null)
                {
                    form.reset(mapping, request);
                    ovcfollowupsurveyForm.setLga(lgaCode);
                    ovcfollowupsurveyForm.setCompletedbyCbo(cboCode);
                    ovcfollowupsurveyForm.setWard(util.getWardName(hhe.getCommunityCode()));
                    ovcfollowupsurveyForm.setHhSerialNo(hhSerialNo);
                    ovcfollowupsurveyForm.setHhUniqueId(hhUniqueId);
                    ovcfollowupsurveyForm.setOvcSno(ovcSno);
                    ovcfollowupsurveyForm.setUpdatedAddress(ovc.getAddress());
                    setBaselineInfo(ovc,ovcfollowupsurveyForm);
                    ovcfollowupsurveyForm.setDateOfSurvey(surveyDate);
                    return (mapping.findForward(target));
                }
                csi=csDao.getChildStatusIndex(ovcId, dateOfSurvey);
                ovcfollowupsurveyForm.setDateOfSurvey(surveyDate);
                ovcfollowupsurveyForm.setUpdatedAddress(fs.getUpdatedAddress());
                ovcfollowupsurveyForm.setUpdatedAge(fs.getUpdatedAge());
                ovcfollowupsurveyForm.setUpdatedAgeUnit(ovcfollowupsurveyForm.getUpdatedAgeUnit());
                caregiverId=fs.getCaregiverId();
                ovcfollowupsurveyForm.setCaregiverId(caregiverId);
                cgiver=cgiverDao.getCaregiverByCaregiverId(caregiverId);
                if(cgiver !=null)
                {
                    ovcfollowupsurveyForm.setUpdatedCaregiverName1(cgiver.getCaregiverLastName());
                    ovcfollowupsurveyForm.setUpdatedCaregiverName2(cgiver.getCaregiverFirstname());
                    ovcfollowupsurveyForm.setUpdatedCaregiverAddress(cgiver.getCaregiverAddress());
                    ovcfollowupsurveyForm.setUpdatedCaregiverAge(cgiver.getCaregiverAge());
                    ovcfollowupsurveyForm.setUpdatedCaregiverGender(cgiver.getCaregiverGender());
                    ovcfollowupsurveyForm.setUpdatedCaregiverOccupation(cgiver.getCaregiverOccupation());
                    ovcfollowupsurveyForm.setUpdatedCaregiverPhone(cgiver.getCaregiverPhone());
                    ovcfollowupsurveyForm.setCgiverHivStatus(cgiver.getCgiverHivStatus());
                    ovcfollowupsurveyForm.setCaregiverHivStatusList(hivStatusList);
                }
                
                ovcfollowupsurveyForm.setUpdatedBirthCertStatus(fs.getUpdatedBirthCertStatus());
                ovcfollowupsurveyForm.setUpdatedCaregiverRelationship(fs.getUpdatedCaregiverRelationship());
                ovcfollowupsurveyForm.setChildCurrentHivStatus(fs.getUpdatedHivStatus());
                ovcfollowupsurveyForm.setChildUpdatedHivStatus(fs.getUpdatedHivStatus());
                ovcfollowupsurveyForm.setEnrolledInCare(fs.getEnrolledInCare());
                ovcfollowupsurveyForm.setEnrolledOnArt(fs.getEnrolledOnART());
                ovcfollowupsurveyForm.setVcFacilityId(fs.getFacilityId());
                ovcfollowupsurveyForm.setCgiverEnrolledInCare(fs.getCgiverEnrolledInCare());
                ovcfollowupsurveyForm.setCgiverEnrolledOnArt(fs.getCgiverEnrolledOnART());
                ovcfollowupsurveyForm.setCgiverFacilityId(fs.getCgiverFacilityId());
                
                
                ovcfollowupsurveyForm.setUpdatedSchoolStatus(fs.getUpdatedSchoolStatus());
                if(fs.getUpdatedSchoolStatus() !=null && fs.getUpdatedSchoolStatus().equalsIgnoreCase("yes"))
                request.setAttribute("schoolDisabled", "false");
                ovcfollowupsurveyForm.setUpdatedSchoolName(fs.getUpdatedSchoolName());
                ovcfollowupsurveyForm.setFollowupId(fs.getId());
                ovcfollowupsurveyForm.setUpdatedClass(fs.getUpdatedClass());

                if(csi !=null)
                {
                    ovcfollowupsurveyForm.setCsiFactor1(csi.getCsiFactor1());
                    ovcfollowupsurveyForm.setCsiFactor2(csi.getCsiFactor2());
                    ovcfollowupsurveyForm.setCsiFactor3(csi.getCsiFactor3());
                    ovcfollowupsurveyForm.setCsiFactor4(csi.getCsiFactor4());
                    ovcfollowupsurveyForm.setCsiFactor5(csi.getCsiFactor5());
                    ovcfollowupsurveyForm.setCsiFactor6(csi.getCsiFactor6());
                    ovcfollowupsurveyForm.setCsiFactor7(csi.getCsiFactor7());
                    ovcfollowupsurveyForm.setCsiFactor8(csi.getCsiFactor8());
                    ovcfollowupsurveyForm.setCsiFactor9(csi.getCsiFactor9());
                    ovcfollowupsurveyForm.setCsiFactor10(csi.getCsiFactor10());
                    ovcfollowupsurveyForm.setCsiFactor11(csi.getCsiFactor11());
                    ovcfollowupsurveyForm.setCsiFactor12(csi.getCsiFactor12());
                    ovcfollowupsurveyForm.setFollowupCsiId(csi.getId());
                }

                ovcfollowupsurveyForm.setCompletedbyName(fs.getCompletedbyName());
                ovcfollowupsurveyForm.setCompletedbyDesignation(fs.getCompletedbyDesignation());
                ovcfollowupsurveyForm.setCompletedbyCbo(selectedCbo);
                session.setAttribute("followupSaveDisabled", "true");
                session.setAttribute("followupModifyDisabled", "false");
            }
            ovcfollowupsurveyForm.setLga(lgaCode);
            ovcfollowupsurveyForm.setCompletedbyCbo(cboCode);
            target="success";
            
            ovcfollowupsurveyForm.setHivStatusList(hivStatusList);
            ovcfollowupsurveyForm.setEnrolledInCare(ovc.getActiveHivStatus().getClientEnrolledInCare());
    }
        
    return (mapping.findForward(target));
}
else if(requiredAction.equalsIgnoreCase("save"))
{
    boolean dateFlag=isFollowupDateBeforeEnrollmentDate(fs);
    if(!dateFlag)
    {
         ActionErrors errors = new ActionErrors();
         errors.add(ActionErrors.GLOBAL_MESSAGE,
         new ActionMessage("error.followupdatebeforeenrollmentdate",
        "Followup survey date earlier than enrollment date"));
        if (!errors.isEmpty())
        {
            ovcfollowupsurveyForm.setDateOfSurvey(surveyDate);
            saveErrors(request, errors);
            return (mapping.findForward("success"));
        }
    }
    try
    {
        fdao.saveFollowup(fs,true,true);
        if(caregiverId !=null)
        {
            ovc=ovcDao.getOvc(ovcId);
            ovc.setCaregiverId(caregiverId);
            ovcDao.updateOvc(ovc,true,true);
        }
        List csiList=csDao.getCsiListOrderedByDateAsc(ovcId);
        int csiCount=csiList.size();
        csiCount++;
        csi.setSurveyNumber(csiCount);
        csDao.saveChildStatusIndexAndReorderAssessmentNumber(csi);

        cgiverDao.updateCaregiver(cgiver);
        OvcWithdrawal ovcWithdrawal;
        if(reasonWithdrawn !=null)
        {
            ovcWithdrawal=new OvcWithdrawal();
            ovcWithdrawal.setOvcId(ovcId);
            ovcWithdrawal.setDateOfWithdrawal(dateOfSurvey);
            ovcWithdrawal.setReasonWithdrawal(reasonWithdrawn);
            ovcWithdrawal.setType("ovc");
            ovcWithdrawal.setSurveyNumber(csiCount);
            wdao.saveOvcWithdrawal(ovcWithdrawal);
        }
    }
    catch(Exception ex)
    {
        ex.printStackTrace();
    }
    session.setAttribute("followupSaveDisabled", "false");
    session.setAttribute("followupModifyDisabled", "true");
}
else if(requiredAction.equalsIgnoreCase("modify"))
{
    try
    {
        fs.setId(ovcfollowupsurveyForm.getFollowupId());
        fdao.updateFollowup(fs,true,true);
        if(caregiverId !=null)
        {
            ovc=ovcDao.getOvc(ovcId);
            ovc.setCaregiverId(caregiverId);
            ovcDao.updateOvc(ovc,true,true);
        }
        List csiList=csDao.getCsiListOrderedByDateAsc(ovcId);
        int csiCount=csiList.size();
        csi.setSurveyNumber(csiCount);
        csi.setId(ovcfollowupsurveyForm.getFollowupCsiId());
        csDao.updateChildStatusIndexAndReorderAssessmentNumber(csi);
      /*Update caregiver information*/
        cgiverDao.updateCaregiver(cgiver);
        OvcWithdrawal ovcWithdrawal;

        /*If child is withdrawn, then withdraw the child*/
        if(reasonWithdrawn !=null)
        {
            ovcWithdrawal=new OvcWithdrawal();
            ovcWithdrawal.setOvcId(ovcId);
            ovcWithdrawal.setDateOfWithdrawal(dateOfSurvey);
            ovcWithdrawal.setReasonWithdrawal(reasonWithdrawn);
            ovcWithdrawal.setType(NomisConstant.OVC_TYPE);
            ovcWithdrawal.setSurveyNumber(csiCount);
            wdao.saveOvcWithdrawal(ovcWithdrawal);
        }
    }
    catch(Exception ex)
    {
        ex.printStackTrace();
    }
    session.setAttribute("followupSaveDisabled", "false");
    session.setAttribute("followupModifyDisabled", "true");
}
else if(requiredAction.equalsIgnoreCase("delete"))
{
    if(ovcfollowupsurveyForm.getFollowupId()>0)
    {
        fs.setId(ovcfollowupsurveyForm.getFollowupId());
        fdao.deleteFollowup(fs);
    }
    if(ovcfollowupsurveyForm.getFollowupCsiId()>0)
    {
        csi.setId(ovcfollowupsurveyForm.getFollowupCsiId());
        csDao.deleteChildStatusIndex(csi);
    }
    session.setAttribute("followupSaveDisabled", "false");
    session.setAttribute("followupModifyDisabled", "true");
}
        
}
catch (Exception e)
{
    //e.printStackTrace();
    target = "error";
    ActionErrors errors = new ActionErrors();
    errors.add(ActionErrors.GLOBAL_MESSAGE,
    new ActionMessage("errors.database.error",
    e.getMessage() ));
    // Report any errors
    if (!errors.isEmpty())
    {
        saveErrors(request, errors);
    }
}
    form.reset(mapping, request);
    ovcfollowupsurveyForm.setLga(lgaCode);
    ovcfollowupsurveyForm.setCompletedbyCbo(cboCode);
return (mapping.findForward(target));
}
private boolean isFollowupDateBeforeEnrollmentDate(FollowupSurvey fs)
{
    OvcDao odao=new OvcDaoImpl();
    Ovc ovc;
    boolean dateFlag=false;
    try
    {
        ovc=(Ovc)odao.getOvc(fs.getOvcId());
        String enrollmentDate=ovc.getDateEnrollment();
        String serviceDate=fs.getDateOfSurvey();
        dateFlag=DateManager.compareDates(enrollmentDate,serviceDate);
    }
    catch(Exception ex)
    {
        ex.printStackTrace();
    }

    return dateFlag;
}
private void setBaselineInfo(Ovc ovc, OvcFollowupSurveyForm ovcfollowupsurveyForm)
{
    DaoUtil util=new DaoUtil();
    AppUtility appUtil=new AppUtility();
    try
    {
        //ovcfollowupsurveyForm.setWard(util.getWardName(ovc.getWard()));
        ovcfollowupsurveyForm.setOvcId(ovc.getOvcId());
        ovcfollowupsurveyForm.setDateEnrollment(appUtil.getDisplayDate(ovc.getDateEnrollment()));
        ovcfollowupsurveyForm.setOtherNames1(ovc.getFirstName());
        ovcfollowupsurveyForm.setSurname(ovc.getLastName());
        ovcfollowupsurveyForm.setAge(util.getCurrentAge(ovc));
        ovcfollowupsurveyForm.setAgeUnit(ovc.getAgeUnit());
        ovcfollowupsurveyForm.setGender(ovc.getGender());
        ovcfollowupsurveyForm.setAddress(ovc.getAddress());
        ovcfollowupsurveyForm.setHeight(ovc.getHeight());
        ovcfollowupsurveyForm.setWeight(ovc.getWeight());
        ovcfollowupsurveyForm.setPhone(ovc.getPhone());
        //ovcfollowupsurveyForm.setHivStatusAtEnrollment(ovc.getHivStatus());
        ovcfollowupsurveyForm.setChildCurrentHivStatus(ovc.getActiveHivStatus().getHivStatus());
        ovcfollowupsurveyForm.setChildUpdatedHivStatus(ovc.getActiveHivStatus().getHivStatus());
        ovcfollowupsurveyForm.setEnrolledInCare(ovc.getActiveHivStatus().getClientEnrolledInCare());
        ovcfollowupsurveyForm.setEnrolledOnArt(ovc.getActiveHivStatus().getEnrolledOnART());
        ovcfollowupsurveyForm.setBirthCertificateAtEnrollment(ovc.getBirthCertificate());
        ovcfollowupsurveyForm.setSchoolStatusAtEnrollment(ovc.getSchoolStatus());
        ovcfollowupsurveyForm.setSchoolName(ovc.getSchoolName());
        ovcfollowupsurveyForm.setCombinedCaregiverName(ovc.getCaregiverName());
        ovcfollowupsurveyForm.setCaregiverPhone(ovc.getCaregiverPhone());
        ovcfollowupsurveyForm.setAddress(ovc.getAddress());
        ovcfollowupsurveyForm.setGenderAtEnrollemnt(ovc.getGender());
        ovcfollowupsurveyForm.setAgeAtEnrollemnt(ovc.getAge());
        ovcfollowupsurveyForm.setUpdatedAge(util.getCurrentAge(ovc));
        ovcfollowupsurveyForm.setUpdatedAgeUnit(ovc.getAgeUnit());
        ovcfollowupsurveyForm.setAgeUnitAtEnrollemnt(ovc.getAgeUnit());
        CaregiverDao cgiverDao=new CaregiverDaoImpl();
        Caregiver cgiver=cgiverDao.getCaregiverByCaregiverId(ovc.getCaregiverId());
        if(cgiver !=null)
        {
            ovcfollowupsurveyForm.setCaregiverId(cgiver.getCaregiverId());
            ovcfollowupsurveyForm.setCaregiverPhone(cgiver.getCaregiverPhone());
            ovcfollowupsurveyForm.setCgiverCurrentHivStatus(cgiver.getActiveHivStatus().getHivStatus());
            ovcfollowupsurveyForm.setCgiverHivStatus(cgiver.getActiveHivStatus().getHivStatus());
            ovcfollowupsurveyForm.setUpdatedCaregiverAddress(cgiver.getCaregiverAddress());
            ovcfollowupsurveyForm.setUpdatedCaregiverGender(cgiver.getCaregiverGender());
            ovcfollowupsurveyForm.setUpdatedCaregiverOccupation(cgiver.getCaregiverOccupation());
            ovcfollowupsurveyForm.setUpdatedCaregiverRelationship(ovc.getCaregiverRelationships());
            ovcfollowupsurveyForm.setCombinedCaregiverName(cgiver.getCaregiverFullName());
            List cgiverHivStatusList=HivRecordsManager.loadNewHivStatus(cgiver.getActiveHivStatus().getHivStatus());
            ovcfollowupsurveyForm.setCaregiverHivStatusList(cgiverHivStatusList);
        }
    }
    catch(Exception ex)
    {
        ex.printStackTrace();
    }
}
}
