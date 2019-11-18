/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

/**
 *
 * @author HP USER
 */
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.Caregiver;
import com.fhi.kidmap.business.ChildStatusIndex;
import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.nomis.nomisutils.LoadUpInfo;
import com.fhi.kidmap.business.Ovc;
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
import com.fhi.kidmap.dao.OvcReferralDao;
import com.fhi.kidmap.dao.OvcReferralDaoImpl;
import com.fhi.kidmap.dao.OvcServiceDao;
import com.fhi.kidmap.dao.OvcServiceDaoImpl;
import com.fhi.nomis.OperationsManagement.HivRecordsManager;
import com.fhi.nomis.nomisutils.AccessManager;
import com.fhi.nomis.nomisutils.NomisConstant;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import java.util.ArrayList;
import java.util.List;
public class AddOvcAction extends Action {


String s = null;
String input=" ";

    @Override
public ActionForward execute(ActionMapping mapping,
ActionForm form,
HttpServletRequest request,
HttpServletResponse response)
throws Exception
{
// Default target to success
String target = "success";
OvcActionMapping ovcMapping =
(OvcActionMapping)mapping;
HttpSession session = request.getSession();
DaoUtil util=new DaoUtil();
OvcDao ovcDao=new OvcDaoImpl();
request.setAttribute("visible", "hidden");
OvcForm ovcregForm = (OvcForm) form;
String requiredAction=ovcregForm.getActionName();
//System.err.println("requiredAction is "+requiredAction);
// Does this action require the user to login
if ( ovcMapping.isLoginRequired() )
{
    if ( session.getAttribute("USER") == null ) 
    {
    // The user is not logged in
    target = "login";
    ActionErrors errors = new ActionErrors();
    errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("errors.login.required"));
// Report any errors we have discovered back
// to the original form
    if (!errors.isEmpty()) 
    {
        saveErrors(request, errors);
    }
    return (ovcMapping.findForward(target));
}
}
if ( isCancelled(request) )
{
// Cancel pressed back to employee list
    return (ovcMapping.findForward("success"));
}
String msg=" ";
/*if(TaskManager.isDatabaseLocked())
{
    msg="System busy. Kindly try again later";
    request.setAttribute("msg", msg);
    return mapping.findForward("success");
}*/
AppUtility appUtil=new AppUtility();
HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
String partnerCode=util.getPartnerCode(appUtil.getCurrentUser(session));
String partnerName=util.getPartnerName(partnerCode);
request.setAttribute("schoolDisabled", "true");
session.setAttribute("vcHhUniqueId", " ");
loadStartupInfo(session,partnerName);
AccessManager acm=new AccessManager();
boolean userInRole=acm.isUserInRole("001",appUtil.getCurrentUser(session));
if(!userInRole)
{
    session.setAttribute("enrollmentSaveDisable", "true");
    session.setAttribute("enrollmentModifyDisable", "true");
    return mapping.findForward("success");
}


String lgaCode=ovcregForm.getLga();
String orgCode=ovcregForm.getCompletedbyCbo();
String wardCode=ovcregForm.getWard();
String wardName=ovcregForm.getWardName();
session.setAttribute("enrollmentSaveDisable", "false");
session.setAttribute("enrollmentModifyDisable", "true");

LoadUpInfo loadup=new LoadUpInfo();
String hhUniqueIdMsg="";
String hhUniqueId=ovcregForm.getHhUniqueId();
List hivStatusList=HivRecordsManager.loadNewHivStatus(null);
session.setAttribute("enrhivStatusList", hivStatusList);
//ovcregForm.setHivStatusList(hivStatusList);

String stateCode=ovcregForm.getStateCode();
States state=(States)session.getAttribute("state");
if(state !=null)
stateCode=state.getState_code();
List facilityList=util.getReferralDirectoriesByState(stateCode);
ovcregForm.setReferralDirectoryList(facilityList);
List caregiverList=util.getCaregiverDaoInstance().getListOfCaregiversFromSameHousehold(hhUniqueId);
//ovcregForm.setCaregiverList(caregiverList);
session.setAttribute("ovcBaselineCaregiverList", caregiverList);
session.setAttribute("enrhivDisabled", "true");
session.setAttribute("enrOvcFachivDisabled", "true");
session.setAttribute("enrcghivDisabled", "true");
session.setAttribute("enrcgFachivDisabled", "true");

loadup.getCbosPerLgaFromSetup(session,ovcregForm.getLga());
//String user=(String)session.getAttribute("USER");
if(requiredAction ==null)
{
    try
    {
        
        updateOvcInformation();
        //NomisUpgrade nu=new NomisUpgrade();
        //nu.updateHivStatus(false);
        //ovcDao.setHHUniqueId();
    }
    catch(Exception ex)
    {
        ex.printStackTrace();
    }
    /*CompatibilityManager cm=new CompatibilityManager();
    cm.updateCaregiverInfoAndEnrollmentForCompatibility();*/
    
    ovcregForm.setPartnerCode(partnerCode);
    loadStartupInfo(session,partnerName);
    return mapping.findForward("success");
}
else if(requiredAction.equalsIgnoreCase("cboList"))
{
        loadup.getCbosPerLgaFromSetup(session,ovcregForm.getLga());
        ovcregForm.reset(mapping, request);
        ovcregForm.setLga(lgaCode);
        return mapping.findForward("success");
}
else if(requiredAction.equalsIgnoreCase("hhDetails"))
{
    //The user entered the household serial number, this section retrueves the details of the household if a household with the unique id already exists
    hhUniqueIdMsg="<span style=color:red>This household unique Id does not exist</span>";
    hhUniqueId=ovcregForm.getHhUniqueId();
    String hhSerialNo=ovcregForm.getHhSerialNo();
    
    ovcregForm.reset(mapping, request);
    session.setAttribute("vcHhUniqueId", hhUniqueIdMsg);
      try
      {
                HouseholdEnrollment hhe=hhedao.getHouseholdEnrollment(hhUniqueId);
                ovcregForm.setLga(lgaCode);
                loadup.getCbosPerLgaFromSetup(session,lgaCode);
                ovcregForm.setCompletedbyCbo(orgCode);
                ovcregForm.setWard(wardCode);
                ovcregForm.setHhSerialNo(hhSerialNo);
                if(hhe !=null)
                {
                    //If household already exists, set the details on the form
                    hhUniqueIdMsg="<div style='color:blue; border: 1px black solid'>Name: "+hhe.getHhSurname()+" "+hhe.getHhFirstname()+" Id: "+hhUniqueId+" Address: "+hhe.getAddress()+"</div>";
                    ovcregForm.setHhFirstname(hhe.getHhFirstname());
                    ovcregForm.setHhSurname(hhe.getHhSurname());
                    ovcregForm.setHhAge(hhe.getHhAge());
                    ovcregForm.setAddress(hhe.getAddress());
                    //ovcregForm.setCaregiverAddress(hhe.getAddress());
                    ovcregForm.setNumOfChildrenInHh(hhe.getNoOfChildrenInhh());
                    ovcregForm.setNumOfOVCInHh(hhe.getNoOfPeopleInhh());
                    ovcregForm.setHhUniqueId(hhUniqueId);
                    ovcregForm.setWard(hhe.getCommunityCode());
                    ovcregForm.setWardName(util.getWardName(hhe.getCommunityCode()));
                    ovcregForm.setHhGender(hhe.getHhGender());
                    
                    session.setAttribute("vcHhUniqueId", hhUniqueIdMsg);
                }
      }
      catch(Exception ex)
      {
          ex.printStackTrace();
      }
      return mapping.findForward("success");
}
else if(requiredAction.equalsIgnoreCase("ovcDetails"))
{
    String ovcId=ovcregForm.getOvcId();
    if(ovcId==null || ovcId.indexOf("/")==-1)
    return mapping.findForward("success");
    hhUniqueId=ovcId.substring(0,ovcId.lastIndexOf("/"));
    String hhSNo=hhUniqueId.substring(hhUniqueId.lastIndexOf("/")+1);
    OvcDao dao = new OvcDaoImpl();
    Ovc ovc=null;
    try
    {
        //Search for a child with similar uniqueId, 
        List list=dao.searchOvcByEnrollmentId(ovcId);
        if(list==null || list.isEmpty())
        list = dao.searchOvc(ovcId);
        if(!list.isEmpty())
        {
            util=new DaoUtil();
            //here the child exist, set the records on the form
            session.setAttribute("vcHhUniqueId", hhUniqueId);
            ovc = (Ovc)list.get(0);
            System.err.println("Current HIV status is "+ovc.getCurrentHivStatus().getHivStatus());
            ovcregForm.reset(mapping, request);
            ovcregForm.setHhSerialNo(hhSNo);
            ovcregForm.setHhUniqueId(hhUniqueId);
            ovcregForm.setOvcSno(ovcId.substring(ovcId.lastIndexOf("/")+1));
            ovcregForm.setLga(lgaCode);
            ovcregForm.setCompletedbyCbo(orgCode);
            ovcregForm.setWard(wardCode);
            ovcregForm.setWardName(wardName);
            ovcregForm.setOvcId(ovcId);
            ovcregForm.setAddress(ovc.getAddress());
            ovcregForm.setAge(ovc.getAge());
            ovcregForm.setAgeUnit(ovc.getAgeUnit());
            ovcregForm.setBirthCertificate(ovc.getBirthCertificate());
            ovcregForm.setHivStatus(ovc.getHivStatus());
            if(ovc.getHivStatus() !=null && ovc.getHivStatus().equalsIgnoreCase(NomisConstant.HIV_POSITIVE))
            {
                session.setAttribute("enrhivDisabled", "false");
                if((ovc.getEnrolledInCare() !=null && ovc.getEnrolledInCare().equalsIgnoreCase("Yes")) || (ovc.getEnrolledOnART() !=null && ovc.getEnrolledOnART().equalsIgnoreCase("Yes")))
                session.setAttribute("enrOvcFachivDisabled", "false");
            }
            
            ovcregForm.setCaregiverId(ovc.getCaregiverId());
            if(ovc.getCaregiverRelationships() !=null)
            {
                if(ovc.getCaregiverRelationships().indexOf(":") ==-1)
                {
                    ovcregForm.setCaregiverRelationships(ovc.getCaregiverRelationships());
                }
                else
                {
                    ovcregForm.setCaregiverRelationships2(ovc.getCaregiverRelationships().substring(ovc.getCaregiverRelationships().lastIndexOf(":")+1));
                }
            }
            
            //ovcregForm.setCompletedbyCbo(ovc.getCompletedbyCbo());
            ovcregForm.setCompletedbyDesignation(ovc.getCompletedbyDesignation());
            String[] completedByNameArray=appUtil.splitString(ovc.getCompletedbyName(), ",");
            if(completedByNameArray !=null && completedByNameArray.length>0)
            {
                ovcregForm.setCompletedbyName1(completedByNameArray[0]);
                if(completedByNameArray.length >1)
                ovcregForm.setCompletedbyName2(completedByNameArray[1]);
            }
            
            ovcregForm.setCurrentClass(ovc.getCurrentClass());
            ovcregForm.setDateEnrollment(appUtil.convertMysqlDateTomonthDayYear(ovc.getDateEnrollment()));
            String eligibility=ovc.getEligibility();
            String[] eligibilityArray=null;
            //if(eligibility !=null)
            eligibilityArray=appUtil.splitString(eligibility,",");
            ovcregForm.setEligibility(eligibilityArray);
            ovcregForm.setGender(ovc.getGender());
            ovcregForm.setHeight(ovc.getHeight());
            ovcregForm.setHhFirstname(ovc.getHhFirstname());
            ovcregForm.setHhSurname(ovc.getHhSurname());
            ovcregForm.setHivStatus(ovc.getCurrentHivStatus().getHivStatus());
            System.err.println("ovc.getCurrentHivStatus().getHivStatus() is "+ovc.getCurrentHivStatus().getHivStatus());
            ovcregForm.setChildEnrolledInCare(ovc.getCurrentHivStatus().getClientEnrolledInCare());
            ovcregForm.setChildEnrolledOnART(ovc.getCurrentHivStatus().getEnrolledOnART());
            ovcregForm.setOrganizationChildIsReferred(ovc.getCurrentHivStatus().getOrganizationClientIsReferred());
            
            
            String[] otherNamesArray=appUtil.splitString(ovc.getFirstName(), ",");
            if(otherNamesArray !=null && otherNamesArray.length>0)
            {
                ovcregForm.setOtherNames1(otherNamesArray[0]);
                if(otherNamesArray.length>1)
                ovcregForm.setOtherNames2(otherNamesArray[1]);
            }
            
            ovcregForm.setPhone(ovc.getPhone());
            ovcregForm.setSchoolStatus(ovc.getSchoolStatus());
            if(ovc.getSchoolStatus() !=null && ovc.getSchoolStatus().equalsIgnoreCase("Yes"))
            {
                loadup.getSchoolsPerState(session);
                request.setAttribute("schoolDisabled", "false");
            }
            ovcregForm.setSchoolName(ovc.getSchoolName());
            
            
            String sourceOfInfo=ovc.getSourceOfInfo();
            
            if(sourceOfInfo !=null)
            {
                if(sourceOfInfo.indexOf(":") !=-1)
                {
                    ovcregForm.setOtherSourceOfInfo(sourceOfInfo.substring(sourceOfInfo.lastIndexOf(":")+1));
                }
                else
                {
                    String[] sourceOfInfoArray=appUtil.splitString(ovc.getSourceOfInfo(), ",");
                    ovcregForm.setSourceOfInfo(sourceOfInfoArray);
                }
                
            }
            ovcregForm.setSurname(ovc.getLastName());
            ovcregForm.setWeight(ovc.getWeight());
            //ovcregForm.setCompletedbyCbo(ovc.getCompletedbyCbo());

            ChildStatusIndexDao csiDao = new ChildStatusIndexDaoImpl();

            ChildStatusIndex childStatusIndex = csiDao.getChildStatusIndex(ovcId, ovc.getDateEnrollment());
            if(childStatusIndex==null)
            childStatusIndex=new ChildStatusIndex();
//set the child status index component of the form
            ovcregForm.setChildIndexId(childStatusIndex.getId());
            ovcregForm.setEmotionalHealth(childStatusIndex.getCsiFactor1());
            ovcregForm.setSocialBehaviour(childStatusIndex.getCsiFactor2());
            ovcregForm.setFoodSecurity(childStatusIndex.getCsiFactor3());
            ovcregForm.setNutritionAndGrowth(childStatusIndex.getCsiFactor4());
            ovcregForm.setWellness(childStatusIndex.getCsiFactor5());
            ovcregForm.setHealthCareServices(childStatusIndex.getCsiFactor6());
            ovcregForm.setDevelopmentAndPerformance(childStatusIndex.getCsiFactor7());
            ovcregForm.setEducationAndWork(childStatusIndex.getCsiFactor8());
            ovcregForm.setAbuseAndExploitation(childStatusIndex.getCsiFactor9());
            ovcregForm.setLegalProtection(childStatusIndex.getCsiFactor10());
            ovcregForm.setShelter(childStatusIndex.getCsiFactor11());
            ovcregForm.setCare(childStatusIndex.getCsiFactor12());
            session.setAttribute("enrollmentSaveDisable", "true");
            session.setAttribute("enrollmentModifyDisable", "false");
        }
        else
        {
            ovcregForm.reset(mapping, request);
            ovcregForm.setHhSerialNo(hhSNo);
            ovcregForm.setHhUniqueId(hhUniqueId);
            ovcregForm.setOvcSno(ovcId.substring(ovcId.lastIndexOf("/")+1));
            ovcregForm.setLga(lgaCode);
            ovcregForm.setCompletedbyCbo(orgCode);
            ovcregForm.setWard(wardCode);
            ovcregForm.setWardName(wardName);
            ovcregForm.setOvcId(ovcId);
            loadup.getCbosPerLgaFromSetup(session, lgaCode);
            session.setAttribute("vcHhUniqueId", hhUniqueId);
        }
        
            HouseholdEnrollment hhe=hhedao.getHouseholdEnrollment(hhUniqueId);
            if(hhe !=null)
            {
                ovcregForm.setHhSurname(hhe.getHhSurname());
                ovcregForm.setHhFirstname(hhe.getHhFirstname());
                ovcregForm.setHhGender(hhe.getHhGender());
                ovcregForm.setHhAge(hhe.getHhAge());
                ovcregForm.setNumOfChildrenInHh(hhe.getNoOfChildrenInhh());
                ovcregForm.setNumOfOVCInHh(hhe.getNoOfPeopleInhh());
                ovcregForm.setAddress(hhe.getAddress());
                //ovcregForm.setCaregiverAddress(hhe.getAddress());
                hhUniqueIdMsg="<div style='color:blue; border: 1px black solid'>Name: "+hhe.getHhSurname()+" "+hhe.getHhFirstname()+" Id: "+hhUniqueId+" Address: "+hhe.getAddress()+"</div>";
                session.setAttribute("vcHhUniqueId", hhUniqueIdMsg);
            }
        session.setAttribute("vcHhUniqueId", hhUniqueId);
    }
    catch(Exception ex)
    {
        ex.printStackTrace();
    }
    loadup.getCbosPerLgaFromSetup(session,lgaCode);
    return mapping.findForward("success");
}
try
{
    //the insertUser method saves a new user or updates the records of an existing user
    insertUser(ovcregForm,session);
    form.reset(mapping, request);
    ovcregForm.setLga(lgaCode);
    ovcregForm.setCompletedbyCbo(orgCode);
    ovcregForm.setWard(wardCode);
}
catch (Exception e)
{
    System.err.println("Setting target to error");
    target = "error";
    //OvcDao ovcDao=new OvcDaoImpl();
    List ovcList=null;
    try
    {
        ovcList=ovcDao.searchOvc(ovcregForm.getOvcId());
    }
    catch(Exception ex)
    {
        System.err.println("Ovc id is "+ovcregForm.getOvcId());
    }
    ActionErrors errors = new ActionErrors();
    if(ovcList !=null && !ovcList.isEmpty())
    {
        errors.add(ActionErrors.GLOBAL_MESSAGE,
        new ActionMessage("ovc.already.exist", s));
    }
    else
    {
        errors.add(ActionErrors.GLOBAL_MESSAGE,
        new ActionMessage("errors.database.error", s));
    }
// Report any errors
    if (!errors.isEmpty())
    {
        saveErrors(request, errors);
    }
}
return (ovcMapping.findForward(target));
}
    private void loadStartupInfo(HttpSession session,String partnerName)
    {
        LoadUpInfo loadup=new LoadUpInfo();
        loadup.getStartupInfo(session);
        loadup.getSchoolsPerState(session);
        loadup.setEnrollmentAttributes(session);
        loadup.getAllStates(session);
        loadup.displayEligibilityCriteria(session);
        session.setAttribute("partnerName", partnerName);
    }
    
    private String getString(List list) 
    {
        String strValue = "";

        if (!list.isEmpty())
        {
            String name=null;
            for (int i=0; i<list.size(); i++)
            {
                name=(String)list.get(i);
                name=name.replaceAll(",", "");
                if(i<list.size()-1)
                strValue += name + ", ";
                else
                strValue += name;    
            }
        }

        if (strValue.endsWith(","))
        {
            return strValue.substring(0, strValue.lastIndexOf(","));
        } 
        else
        {
            return strValue;
        }
    }
      
    private String processDate(String date) {

        if (date != null)
        {
            if(date.contains("-"))
            {
                 return date;
             }
            String year = date.substring(date.lastIndexOf("/") + 1);
            String month = date.substring(0, date.indexOf("/"));
            String day = date.substring(date.indexOf("/") + 1, date.lastIndexOf("/"));
            date = year + "-" + month + "-" + day;
        }

        return date;

    }

protected void insertUser(OvcForm ovcregForm,HttpSession session)
throws Exception {

        Ovc ovc = new Ovc();
        DaoUtil util=new DaoUtil();
        AppUtility appUtil=new AppUtility();
        input=" ";
        
        String requiredAction=ovcregForm.getActionName();
        ovc.setState(ovcregForm.getStateCode());
        ovc.setLga(ovcregForm.getLga());
        String wardCode=ovcregForm.getWard();
        //String wardName=ovcregForm.getWardName();
        String hhUniqueId=ovcregForm.getHhUniqueId();
        String currentDate=appUtil.getCurrentDate();
        //HouseholdEnrollmentDao hheDao=new HouseholdEnrollmentDaoImpl();
        //HouseholdEnrollment hhe=null;
        if(wardCode !=null)
        wardCode=wardCode.trim().toUpperCase();

        ovc.setWard(wardCode);
        
        ovc.setDateEnrollment(processDate(ovcregForm.getDateEnrollment()));
        ovc.setHhUniqueId(hhUniqueId);
        ovc.setOvcId(ovcregForm.getOvcId());
        ovc.setLastName(ovcregForm.getSurname());

        List list = new ArrayList();
        list.add(ovcregForm.getOtherNames1());
        list.add(ovcregForm.getOtherNames2());

        ovc.setFirstName(getString(list));

        list.clear();

        ovc.setGender(ovcregForm.getGender());
        ovc.setAge(ovcregForm.getAge());
        ovc.setAgeUnit(ovcregForm.getAgeUnit());
        ovc.setAddress(ovcregForm.getAddress());
        ovc.setPhone(ovcregForm.getPhone());

        String[] eligibility=ovcregForm.getEligibility();
        if(eligibility !=null)
        {
            for(String s:eligibility)
            {
                list.add(s);
            }
        }
                
        ovc.setEligibility(getString(list));

        list.clear();
        System.err.println("ovcregForm.getHivStatus() is "+ovcregForm.getHivStatus());
        ovc.setHivStatus(ovcregForm.getHivStatus());
        ovc.setEnrolledInCare(ovcregForm.getChildEnrolledInCare());
        ovc.setEnrolledOnART(ovcregForm.getChildEnrolledOnART());
        ovc.setFacilityId(ovcregForm.getOrganizationChildIsReferred());
        ovc.setBirthCertificate(ovcregForm.getBirthCertificate());
        ovc.setSchoolStatus(ovcregForm.getSchoolStatus());
        ovc.setSchoolName(ovcregForm.getSchoolName());
        ovc.setCurrentClass(ovcregForm.getCurrentClass());
        ovc.setOrphanageName(ovcregForm.getOrphanageName());
        
        list.clear();
           
        ovc.setCaregiverId(ovcregForm.getCaregiverId());

        if(ovcregForm.getCaregiverRelationships() !=null && ovcregForm.getCaregiverRelationships().length()>1)
        ovc.setCaregiverRelationships(ovcregForm.getCaregiverRelationships());
        else
        ovc.setCaregiverRelationships("Efm:"+ovcregForm.getCaregiverRelationships2());
        
        list.clear();

        list.add(ovcregForm.getCompletedbyName1());
        list.add(ovcregForm.getCompletedbyName2());

        ovc.setCompletedbyName(getString(list));

        list.clear();

        ovc.setCompletedbyDesignation(ovcregForm.getCompletedbyDesignation());
        ovc.setCompletedbyCbo(ovcregForm.getCompletedbyCbo());
        ovc.setWeight(ovcregForm.getWeight());
        ovc.setHeight(ovcregForm.getHeight());
        ovc.setVerifiedBy(ovcregForm.getVerifiedBy());
        String[] sourceOfInfo=ovcregForm.getSourceOfInfo();
        String otherSourceOfInfo=ovcregForm.getOtherSourceOfInfo();
        if(sourceOfInfo !=null)
        {
            for(String s:sourceOfInfo)
            {
                list.add(s);
            }
        }
        else if(otherSourceOfInfo !=null)
        {
            otherSourceOfInfo="other:"+otherSourceOfInfo;
        }
        ovc.setSourceOfInfo(getString(list));
        ovc.setDateOfEntry(currentDate);

        ovc.setPartner(ovcregForm.getPartnerCode());
        //System.err.println("ovcregForm.getPartnerCode() in AddOvc is "+ovcregForm.getPartnerCode());
        ovc.setRecordedBy((String)session.getAttribute("userRealNames"));
        ovc=util.getOvcWithCurrentAge(ovc);
        //ovc.setCurrentAge(util.getCurrentAge(ovc));
         
        ChildStatusIndex childStatusIndex = new ChildStatusIndex();

        childStatusIndex.setId(ovcregForm.getChildIndexId());
        childStatusIndex.setOvcId(ovcregForm.getOvcId());
        childStatusIndex.setDateOfEntry(currentDate);
        childStatusIndex.setCsiDate(processDate(ovcregForm.getDateEnrollment()));
        childStatusIndex.setCsiFactor1(ovcregForm.getEmotionalHealth());
        childStatusIndex.setCsiFactor2(ovcregForm.getSocialBehaviour());
        childStatusIndex.setCsiFactor3(ovcregForm.getFoodSecurity());
        childStatusIndex.setCsiFactor4(ovcregForm.getNutritionAndGrowth());
        childStatusIndex.setCsiFactor5(ovcregForm.getWellness());
        childStatusIndex.setCsiFactor6(ovcregForm.getHealthCareServices());
        childStatusIndex.setCsiFactor7(ovcregForm.getDevelopmentAndPerformance());
        childStatusIndex.setCsiFactor8(ovcregForm.getEducationAndWork());
        childStatusIndex.setCsiFactor9(ovcregForm.getAbuseAndExploitation());
        childStatusIndex.setCsiFactor10(ovcregForm.getLegalProtection());
        childStatusIndex.setCsiFactor11(ovcregForm.getShelter());
        childStatusIndex.setCsiFactor12(ovcregForm.getCare());
        childStatusIndex.setSurveyNumber(1);




       String errorMsg="Could not save or modify data. Ensure that all required fields are entered and the OVC Id has not been used. Also ensure that the date format is mm/dd/yyyy ";
       
        try {
            OvcDao dao = new OvcDaoImpl();
            OvcServiceDao sdao=new OvcServiceDaoImpl();
            OvcReferralDao rdao=new OvcReferralDaoImpl();
            FollowupDao fdao=new FollowupDaoImpl();
            if(requiredAction==null)
            {
                
            }
            else if(requiredAction.equalsIgnoreCase("save"))
            {
                //ovc=util.getOvcWithCurrentAge(ovc);
                dao.saveOvc(ovc,true,true);
                //if(cgiver.getCaregiverId() !=null)
                //cgiverDao.saveCaregiver(cgiver);
            }
            else if(requiredAction.equalsIgnoreCase("modify"))
            {
                if(ovc.getWard()==null || ovc.getWard().length() < 1)
                ovc.setWard(ovcregForm.getWard());
                Ovc ovc2=dao.getOvc(ovc.getOvcId());
                if(ovc2 !=null)
                {
                    ovc.setWithdrawnFromProgram(ovc2.getWithdrawnFromProgram());
                    ovc=util.getOvcWithCurrentAge(ovc);
                    dao.updateOvc(ovc,true,true);
                }
                
                //cgiverDao.saveCaregiver(cgiver);
            }
            else if(requiredAction.equalsIgnoreCase("delete"))
            {
                dao.deleteOvc(ovc);
                sdao.deleteOvcServices(ovc.getOvcId());
                rdao.deleteOvcReferrals(ovc.getOvcId());
                fdao.deleteOvcFollowups(ovc.getOvcId());
            }
            session.removeAttribute("enableSave");
            session.removeAttribute("enableModify");
        } catch (Exception e) {
            
            s = errorMsg;
            

            throw new Exception(e);
        }
        
        try {
            ChildStatusIndexDao dao = new ChildStatusIndexDaoImpl();
            if(requiredAction.equals("save"))
            {
                dao.saveChildStatusIndexAndReorderAssessmentNumber(childStatusIndex);
            }
            else if(requiredAction.equals("modify"))
            {
                System.err.println("childStatusIndex.getOvcId() is "+childStatusIndex.getOvcId()+" and childStatusIndex.getCsiDate() is "+childStatusIndex.getCsiDate());
                ChildStatusIndex duplicateChildStatusIndex = dao.getChildStatusIndex(childStatusIndex.getOvcId(),childStatusIndex.getCsiDate());
                if(duplicateChildStatusIndex==null)
                {
                    dao.saveChildStatusIndexAndReorderAssessmentNumber(childStatusIndex);
                    //System.err.println("CSI saved");
                }
                else
                {
                    childStatusIndex.setId(duplicateChildStatusIndex.getId());
                    //System.err.println("childStatusIndex.getOvcId() is "+childStatusIndex.getOvcId());
                    dao.updateChildStatusIndexAndReorderAssessmentNumber(childStatusIndex);
                    //System.err.println("CSI updated ");
                }
            }
            else if(requiredAction.equals("delete"))
            {
                dao.deleteChildStatusIndex(childStatusIndex);
                
            }

        } catch (Exception e) {
            s =errorMsg;
            throw new Exception(e);
        }

}
    private void updateOvcInformation()
    {
        ChildStatusIndexDao csidao = new ChildStatusIndexDaoImpl();
        try
        {
            csidao.updateCsiDateOfEntry();
            //NomisUpgrade nu=new NomisUpgrade();
        //nu.updateWithdrawalStatusInEnrollment(true);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
    }
}

