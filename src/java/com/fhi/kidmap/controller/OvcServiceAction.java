/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.nomis.nomisutils.LoadUpInfo;
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.business.OvcService;
import com.fhi.kidmap.business.OvcWithdrawal;
import com.fhi.kidmap.business.States;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.HouseholdEnrollmentDao;
import com.fhi.kidmap.dao.HouseholdEnrollmentDaoImpl;
import com.fhi.kidmap.dao.OvcDao;
import com.fhi.kidmap.dao.OvcDaoImpl;
import com.fhi.kidmap.dao.OvcServiceDao;
import com.fhi.kidmap.dao.OvcServiceDaoImpl;
import com.fhi.kidmap.dao.OvcWithdrawalDao;
import com.fhi.kidmap.dao.OvcWithdrawalDaoImpl;
import com.fhi.nomis.OperationsManagement.BackgroundProcessManager;
import com.fhi.nomis.OperationsManagement.HivRecordsManager;
import com.fhi.nomis.OperationsManagement.OvcServiceUtilityManager;
import com.fhi.nomis.OperationsManagement.SchoolAttendanceManager;
import com.fhi.nomis.nomisutils.AccessManager;
import com.fhi.nomis.nomisutils.DateManager;
import com.fhi.nomis.nomisutils.NomisConstant;
import com.fhi.nomis.nomisutils.OvcServiceManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author HP USER
 */
public class OvcServiceAction extends Action {

    private OvcService ovcService = null;
    private OvcWithdrawal ovcWithdrawal = null;
    DaoUtil util=new DaoUtil();
    AppUtility appUtil=new AppUtility();

    String target = null;
    String ovcWithdrawn=" ";

    String s = "";

     @Override
public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException 
{
// Default target to success
String target = "success";
OvcActionMapping ovcMapping =
(OvcActionMapping)mapping;
HttpSession session = request.getSession();
AppUtility appUtil=new AppUtility();
// Does this action require the user to login
if ( ovcMapping.isLoginRequired() ) 
{

    if ( session.getAttribute("USER") == null ) 
    {
        // The user is not logged in
        target = "login";
        ActionErrors errors = new ActionErrors();
        errors.add(ActionErrors.GLOBAL_MESSAGE,
        new ActionMessage("errors.login.required"));
        // Report any errors we have discovered back
        // to the original form
        if (!errors.isEmpty()) 
        {
        saveErrors(request, errors);
        }
        return (ovcMapping.findForward(target));
    }
}
if ( isCancelled(request) ) {
// Cancel pressed back to employee list
return (ovcMapping.findForward("success"));
}
session.setAttribute("vcHhUniqueId", " ");
AccessManager acm=new AccessManager();
LoadUpInfo loadup=new LoadUpInfo();
    
boolean userInRole=acm.isUserInRole("001",appUtil.getCurrentUser(session));
if(!userInRole)
{
    loadup.getStartupInfo(session);
    setBtnState(session, "true", "true");
    return mapping.findForward("success");
}
try
{
OvcServiceForm ovcserviceForm = (OvcServiceForm) form;
OvcDao ovcDao=new OvcDaoImpl();
OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl();
Ovc ovc=ovcDao.getOvc(ovcserviceForm.getOvcId());
if(ovc==null)
{
    ovc=new Ovc();
}
List list=new ArrayList();
//HttpSession session=request.getSession();
String ovcId=ovcserviceForm.getOvcId();
String selectedCbo=ovcserviceForm.getCompletedbyCbo();
String lgaCode=ovcserviceForm.getLga();
String hhSerialNo=ovcserviceForm.getHhSerialNo();
String hhUniqueId=ovcserviceForm.getHhUniqueId();
String ward=ovcserviceForm.getWard();
List ovcList=new ArrayList();
List hivStatusList=HivRecordsManager.loadNewHivStatus(ovc.getActiveHivStatus().getHivStatus());
ovcserviceForm.setHivStatusList(hivStatusList);
//ovcserviceForm.setCurrentHivStatus(ovc.getCurrentHivStatus());
String stateCode=ovcserviceForm.getState();
States state=(States)session.getAttribute("state");
if(state !=null)
stateCode=state.getState_code();
List facilityList=util.getReferralDirectoriesByState(stateCode);
ovcserviceForm.setReferralDirectoryList(facilityList);

String hhUniqueIdMsg="<span style=color:red>This household unique Id does not exist</span>";
String requiredAction=ovcserviceForm.getActionName();
request.setAttribute("ovcWithdrawn", " ");
session.setAttribute("hivprevdisabled", "true");
request.setAttribute("servicedatedisabled", "false");
String userName=appUtil.getCurrentUser(session);
childInSchool(session,ovcId);
if(requiredAction==null)
{
    form.reset(mapping, request);
    loadup.getStartupInfo(session);
    ovcserviceForm.setLga(lgaCode);
    session.setAttribute("ovcListInService", ovcList);
    session.setAttribute("vcUniqueId", "");
    return mapping.findForward("success");
}
else if(requiredAction.equalsIgnoreCase("cboList"))
{
        loadup.getCbosPerLgaFromSetup(session,lgaCode);
        ovcserviceForm.reset(mapping, request);
        ovcserviceForm.setLga(lgaCode);
        return mapping.findForward("success");
}
else if(requiredAction.equalsIgnoreCase("cbo"))
{
    form.reset(mapping, request);
    ovcserviceForm.setCompletedbyCbo(selectedCbo);
    ovcserviceForm.setLga(lgaCode);
    setBtnState(session,"false","true");
    return mapping.findForward("success");
}

else if(requiredAction.equalsIgnoreCase("hhDetails"))
{
    HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
    ovcserviceForm.reset(mapping, request);
      try
      {
                HouseholdEnrollment hhe=hhedao.getHouseholdEnrollment(hhUniqueId);
                ovcserviceForm.setLga(lgaCode);
                ovcserviceForm.setCompletedbyCbo(selectedCbo); 
                ovcserviceForm.setHhSerialNo(hhSerialNo);
                
                if(hhe !=null)
                {
                    hhUniqueIdMsg=hhUniqueId;
                    ovcserviceForm.setHhUniqueId(hhUniqueId);
                    
                    OvcWithdrawal withdrawal=wdao.getWithdrawal(hhUniqueId);
                    if(withdrawal !=null)
                    {
                        ovcWithdrawn="This Household has been withdrawn ("+withdrawal.getReasonWithdrawal()+")";
                        request.setAttribute("ovcWithdrawn", ovcWithdrawn);
                        request.setAttribute("servicedatedisabled", "true");
                        session.setAttribute("ovcListInService", new ArrayList());
                        return mapping.findForward("success");
                    }
                    ovcserviceForm.setWard(util.getWardName(hhe.getCommunityCode()));
                    ovcList=ovcDao.getOvcPerHousehold(hhUniqueId);
                    session.setAttribute("ovcListInService", ovcList);//

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
    ovcWithdrawn=" ";
    ovcserviceForm.reset(mapping, request);
    ovcserviceForm.setLga(lgaCode);
    ovcserviceForm.setCompletedbyCbo(selectedCbo); 
    ovcserviceForm.setHhSerialNo(hhSerialNo);
    ovcserviceForm.setOvcId(ovcId);
    hhUniqueIdMsg=ovcserviceForm.getHhUniqueId();
    SchoolAttendanceManager satm=new SchoolAttendanceManager();
    int childInSchool=satm.childInSchool(ovcId);
    if(childInSchool==0)
    {
        
    }
    //hhUniqueIdMsg=hhUniqueId;
    session.setAttribute("vcHhUniqueId", hhUniqueIdMsg);
    list=wdao.getWithdrawnOvc(ovcId);
    OvcWithdrawal withdrawal=wdao.getWithdrawal(ovcId);
    if(withdrawal !=null)
    {
        //OvcWithdrawal withdrawal=(OvcWithdrawal)list.get(0);
        ovcWithdrawn="This VC has been withdrawn ("+withdrawal.getReasonWithdrawal()+")";
        request.setAttribute("ovcWithdrawn", ovcWithdrawn);
        request.setAttribute("servicedatedisabled", "true");
        //setBtnState(session,"true","true");
        return mapping.findForward("success");
    }
    ovc=ovcDao.getOvc(ovcId);
    if(ovc !=null)
    {
        //ovc=(Ovc)ovcDetailsList.get(0);
        //System.err.println("ovc.getWithdrawnFromProgram() is "+ovc.getWithdrawnFromProgram()+" and ovc.getReasonForWithdrawal() is "+ovc.getReasonForWithdrawal());
       /*OvcWithdrawal withdrawal=util.getBeneficiaryWithdrawn(ovcId);
        if(withdrawal !=null)
        {
            ovcWithdrawn="This VC has been withdrawn ("+withdrawal.getReasonWithdrawal()+")";
            request.setAttribute("ovcWithdrawn", ovcWithdrawn);
            request.setAttribute("servicedatedisabled", "true");
            return mapping.findForward("success");
        }*/
        enableControlForHIVPrevControl(session,ovc.getOvcId());
        
        form.reset(mapping, request);
        session.setAttribute("baselineInfo", ovc);
        ovcserviceForm.setWard(ward);
        setBaselineDetails(ovc, ovcserviceForm,selectedCbo,hhSerialNo);
        int currentAge=util.getCurrentAge(ovc);
        if(currentAge > 17)
        request.setAttribute("currentAge", "overaged;"+currentAge);
        else
        request.setAttribute("currentAge", "underaged;"+currentAge);
        ovcserviceForm.setActionName(null);
        ovcserviceForm.setLga(lgaCode);
        hivStatusList=HivRecordsManager.loadNewHivStatus(ovc.getActiveHivStatus().getHivStatus());
        ovcserviceForm.setHivStatusList(hivStatusList);
        ovcserviceForm.setCurrentHivStatus(ovc.getActiveHivStatus().getHivStatus());
        ovcserviceForm.setNewHivStatus(ovc.getActiveHivStatus().getHivStatus());
        ovcserviceForm.setEnrolledInCare(ovc.getActiveHivStatus().getClientEnrolledInCare());
        ovcserviceForm.setEnrolledOnART(ovc.getEnrolledOnART());
        session.setAttribute("vcUniqueId", ovc.getOvcId());
        target="success";
        return (ovcMapping.findForward(target));
    }
    ovcserviceForm.setLga(lgaCode);
    
    setBtnState(session,"false","true");
    session.setAttribute("ovcWithdrawn", "This VC does not exist in the enrolment record");
    return mapping.findForward("success");
}
else if(requiredAction.equalsIgnoreCase("serviceDetails"))
{
    enableControlForHIVPrevControl(session,ovc.getOvcId());
    hhUniqueIdMsg=ovcserviceForm.getHhUniqueId();
    OvcWithdrawal withdrawal=wdao.getWithdrawal(ovcId);
    /*if(withdrawal !=null)
    {
        if(DateManager.compareDates(withdrawal.getDateOfWithdrawal(), dateOfAssessment))
        {
            ovcWithdrawn="This household was withdrawn from the program on an earlier date";
            request.setAttribute("ovcWithdrawn", ovcWithdrawn);
            setBtnState(session,"true","true");
        }
        //return mapping.findForward(SUCCESS);
    }*/
    if(withdrawal !=null)
    {
        ovcWithdrawn="This VC has been withdrawn";
        request.setAttribute("ovcWithdrawn", ovcWithdrawn);
        //setBtnState(session,"true","true");
        return mapping.findForward("success");
    }
    OvcServiceDao osDao=new OvcServiceDaoImpl();
    String originalServiceDate=ovcserviceForm.getServiceDate();
    String serviceDate=null;
    if(originalServiceDate !=null && originalServiceDate.indexOf("/") !=-1)
    serviceDate=DateManager.processMthDayYearToMysqlFormat(originalServiceDate);
    OvcService os=osDao.getOvcServiceForTheMth(ovcId, serviceDate);
    
    String[] psychoServices=null;
    String[] nutritionalServices=null;
    String[] healthServices=null;
    String[] educationalServices=null;
    String[] protectionServices=null;
    String[] shelterServices=null;
    String[] economicServices=null;
    String[] referralServices=null;
    if(os !=null)
    {
        String serviceCodes=null;
        if(os.getServiceAccessed1() !=null)
        {
            serviceCodes=os.getServiceAccessed1();
            serviceCodes.replaceAll(";", ",");
            serviceCodes.replaceAll(",,", ",");
            boolean removeStartAndEndWildCharacters=true;
            /*if(os.getServiceAccessed1().indexOf(";") !=-1)
            {
                System.err.println("psychoServices: "+os.getServiceAccessed1());
                psychoServices=appUtil.splitString(os.getServiceAccessed1(),";");
                ovcserviceForm.setPsychosocialOther(psychoServices[1]);
                psychoServices=appUtil.splitServices(OvcServiceManager.getConcatenatedServiceNames(appUtil.splitServices(psychoServices[0]),removeStartAndEndWildCharacters));
                //appUtil.splitServices(psychoServices[0]);
            }*/
            //else
            psychoServices=appUtil.splitServices(OvcServiceManager.getConcatenatedServiceNames(appUtil.splitServices(serviceCodes),removeStartAndEndWildCharacters));
            //appUtil.splitServices(os.getServiceAccessed1());
        }
        if(os.getServiceAccessed2() !=null)
        {
            System.err.println("nutritionalServices: "+os.getServiceAccessed2());
            serviceCodes=os.getServiceAccessed2();
            serviceCodes.replaceAll(";", ",");
            serviceCodes.replaceAll(",,", ",");
            serviceCodes.replace("Nutrition referralNutrition assessment", "NACS");
            boolean removeStartAndEndWildCharacters=true;
            /*if(os.getServiceAccessed2().indexOf(";") !=-1)
            {
                nutritionalServices=appUtil.splitString(os.getServiceAccessed2(),";");
                ovcserviceForm.setNutritionOther(nutritionalServices[1]);
                nutritionalServices=appUtil.splitServices(OvcServiceManager.getConcatenatedServiceNames(appUtil.splitServices(nutritionalServices[0]),removeStartAndEndWildCharacters));
                //appUtil.splitServices(nutritionalServices[0]);
            }*/
            //else
            nutritionalServices=appUtil.splitServices(OvcServiceManager.getConcatenatedServiceNames(appUtil.splitServices(serviceCodes),removeStartAndEndWildCharacters));
            //appUtil.splitServices(os.getServiceAccessed2());
        }
        if(os.getServiceAccessed3() !=null)
        {
            System.err.println("healthServices: "+os.getServiceAccessed3());
            serviceCodes=os.getServiceAccessed3();
            serviceCodes.replaceAll(";", ",");
            serviceCodes.replaceAll(",,", ",");
            
            boolean removeStartAndEndWildCharacters=true;
            
            healthServices=appUtil.splitServices(OvcServiceManager.getConcatenatedServiceNames(appUtil.splitServices(serviceCodes),removeStartAndEndWildCharacters));
            //appUtil.splitServices(os.getServiceAccessed3());
        }
        if(os.getServiceAccessed4() !=null)
        {
            System.err.println("educationalServices: "+os.getServiceAccessed4());
            serviceCodes=os.getServiceAccessed4();
            serviceCodes.replaceAll(";", ",");
            serviceCodes.replaceAll(",,", ",");
            boolean removeStartAndEndWildCharacters=true;
            educationalServices=educationalServices=appUtil.splitServices(OvcServiceManager.getConcatenatedServiceNames(appUtil.splitServices(serviceCodes),removeStartAndEndWildCharacters));
            //appUtil.splitServices(os.getServiceAccessed4());
        }


        if(os.getServiceAccessed5() !=null)
        {
            System.err.println("protectionServices: "+os.getServiceAccessed5());
            serviceCodes=os.getServiceAccessed5();
            serviceCodes.replaceAll(";", ",");
            serviceCodes.replaceAll(",,", ",");
            boolean removeStartAndEndWildCharacters=true;
            protectionServices=appUtil.splitServices(OvcServiceManager.getConcatenatedServiceNames(appUtil.splitServices(serviceCodes),removeStartAndEndWildCharacters));
            //appUtil.splitServices(os.getServiceAccessed5());
        }
        if(os.getServiceAccessed6() !=null)
        {
            System.err.println("shelterServices: "+os.getServiceAccessed6());
            serviceCodes=os.getServiceAccessed6();
            serviceCodes.replaceAll(";", ",");
            serviceCodes.replaceAll(",,", ",");
            
            boolean removeStartAndEndWildCharacters=true;
            
            shelterServices=appUtil.splitServices(OvcServiceManager.getConcatenatedServiceNames(appUtil.splitServices(serviceCodes),removeStartAndEndWildCharacters));
            //appUtil.splitServices(os.getServiceAccessed6());
        }
        if(os.getServiceAccessed7() !=null)
        {
            System.err.println("economicServices: "+os.getServiceAccessed7());
            serviceCodes=os.getServiceAccessed7();
            serviceCodes.replaceAll(";", ",");
            serviceCodes.replaceAll(",,", ",");
            boolean removeStartAndEndWildCharacters=true;
            
            economicServices=appUtil.splitServices(OvcServiceManager.getConcatenatedServiceNames(appUtil.splitServices(serviceCodes),removeStartAndEndWildCharacters));
            //appUtil.splitServices(os.getServiceAccessed7());
        }
        if(os.getServicesReferred() !=null)
        {
            if(os.getServicesReferred().indexOf(";") !=-1)
            {
                referralServices=appUtil.splitString(os.getServicesReferred(),";");
                referralServices=appUtil.splitServices(referralServices[0]);
                //System.err.println("First service is "+referralServices[0]);
            }
            else
            {
                referralServices=appUtil.splitServices(os.getServicesReferred());
               
            }
        }
        ovcserviceForm.setPsychoServices(psychoServices);
        ovcserviceForm.setNutritionalServices(nutritionalServices);
        ovcserviceForm.setHealthServices(healthServices);
        ovcserviceForm.setEducationalServices(educationalServices);
        ovcserviceForm.setProtectionServices(protectionServices);
        ovcserviceForm.setShelterServices(shelterServices);
        ovcserviceForm.setEconomicServices(economicServices);
        ovcserviceForm.setReferralServices(referralServices);
        
        ovcserviceForm.setOvcServiceId(os.getId());
        ovcserviceForm.setCurrentHeight(os.getHeight());
        ovcserviceForm.setCurrentWeight(os.getWeight());
        ovcserviceForm.setProviderName(os.getProviderName());
        hivStatusList=HivRecordsManager.loadNewHivStatus("negpos");
        ovcserviceForm.setHivStatusList(hivStatusList);
        ovcserviceForm.setCurrentHivStatus(ovc.getActiveHivStatus().getHivStatus());
        ovcserviceForm.setNewHivStatus(ovc.getActiveHivStatus().getHivStatus());
        ovcserviceForm.setEnrolledInCare(ovc.getActiveHivStatus().getClientEnrolledInCare());
        ovcserviceForm.setEnrolledOnART(ovc.getEnrolledOnART());
        ovcserviceForm.setChildAbused(os.getChildAbused());
        ovcserviceForm.setChildMissedSchool(os.getChildMissedSchool());
        ovcserviceForm.setChildLinkedToGovt(os.getChildLinkedToGovt());
        if(ovcserviceForm.getCurrentHeight()>0)
        request.setAttribute("heightDisabled", "false");
        if(ovcserviceForm.getCurrentWeight()>0)
        request.setAttribute("weightDisabled", "false");
        setBtnState(session,"true","false");
        ovcserviceForm.setLga(lgaCode);
        target="success";
        //mapping.findForward("success");
        
    }
    else
    {

        form.reset(mapping, request);
        ovcserviceForm.setCurrentHivStatus(ovc.getActiveHivStatus().getHivStatus());
        ovc=(Ovc)session.getAttribute("baselineInfo");
        ovcserviceForm.setWard(ward);
        setBaselineDetails(ovc, ovcserviceForm,selectedCbo,hhSerialNo);
        ovcserviceForm.setServiceDate(originalServiceDate);
        ovcserviceForm.setLga(lgaCode);
        setBtnState(session,"false","true");
        target="success";
        //return (ovcMapping.findForward(target));
    }
    ovcserviceForm.setCurrentHivStatus(ovc.getActiveHivStatus().getHivStatus());
    session.setAttribute("vcHhUniqueId", hhUniqueIdMsg);
    target="success";
    return (ovcMapping.findForward(target));
}

fillServiceDetails(ovcserviceForm,userName);




if(requiredAction.equalsIgnoreCase("save"))
{
    OvcServiceUtilityManager osm=new OvcServiceUtilityManager();
    OvcDao odao=new OvcDaoImpl();
    Ovc ovc2=(Ovc)odao.getOvc(ovcService.getOvcId());
    String enrollmentDate=ovc2.getDateEnrollment();
    String serviceDate=ovcService.getServicedate();

    boolean dateFlag=compareDates(enrollmentDate,serviceDate);
    if(!dateFlag)
    {
        ActionErrors errors = new ActionErrors();
        errors.add(ActionErrors.GLOBAL_MESSAGE,
        new ActionMessage("error.servicedatebeforeenrollmentdate",
        "Service date earlier than enrollment date"));

        if (!errors.isEmpty())
        {
            saveErrors(request, errors);
            return (ovcMapping.findForward("success"));
        }
    }
    saveService(ovcService, ovcWithdrawal);
    osm.updateOvcWithLastServiceDate(ovcId, serviceDate);
    osm.setOvcBirthRegistrationStatus(ovcService);
    executeBackgroundProcesses(ovcService.getOvcId());
    setBtnState(session,"false","true");
}
else if(requiredAction.equalsIgnoreCase("modify")) 
{
    OvcServiceUtilityManager osm=new OvcServiceUtilityManager();
    updateService(ovcService, ovcWithdrawal, ovcserviceForm);
    osm.updateOvcWithLastServiceDate(ovcId, ovcService.getServicedate());
    osm.setOvcBirthRegistrationStatus(ovcService);
    executeBackgroundProcesses(ovcService.getOvcId());
    setBtnState(session,"false","true");
}
else if(requiredAction.equalsIgnoreCase("delete")) {
    deleteService(ovcService, ovcWithdrawal);
    setBtnState(session,"false","true");
}
    ovcserviceForm.reset(mapping, request);
    ovcserviceForm.setLga(lgaCode);
    ovcserviceForm.setCompletedbyCbo(selectedCbo); 
}
catch (Exception e)
{
    System.err.println("Setting target to error in ovcserviceaction");

    target = "error";
    ActionErrors errors = new ActionErrors();
    errors.add(ActionErrors.GLOBAL_MESSAGE,
    new ActionMessage("errors.database.error",
    e.getMessage() + s));
    // Report any errors
    if (!errors.isEmpty())
    {
        saveErrors(request, errors);
    }
}
//form.reset(mapping, request);

return (ovcMapping.findForward(target));
}
     private void executeBackgroundProcesses(String ovcId)
     {
         BackgroundProcessManager bpm=new BackgroundProcessManager();
         bpm.updateHivStatusAndSchoolStatus(ovcId, NomisConstant.OVC_TYPE);
     }
     private void childInSchool(HttpSession session,String ovcId)
     {
         if(ovcId !=null && ovcId.trim().length()>1 && ovcId.indexOf("/") !=-1)
         {
             SchoolAttendanceManager satm=new SchoolAttendanceManager();
            int childInSchool=satm.childInSchool(ovcId);
            if(childInSchool==0)
            {
                executeBackgroundProcesses(ovcId);
                childInSchool(session,ovcId);
            }
            else if(childInSchool==1)
            {
                session.setAttribute("childMissSchoolDisabled", "true");
            }
            else if(childInSchool==2)
            {
                session.setAttribute("childMissSchoolDisabled", "false");
            }
         }
     }

public void updateSurveyId()
{
    List list=new ArrayList();
    List idList=new ArrayList();
    OvcServiceDao sdao=new OvcServiceDaoImpl();
    try
    {
            idList=sdao.getOvcIdsFromServices(0);
            OvcService service=new OvcService();
            for(int i=0; i<idList.size(); i++)
            {
                int surveyNo=0;
                list=sdao.getOvcServices((String)idList.get(i));
                for(int j=0; j<list.size(); j++)
                {
                    surveyNo++;
                    service=(OvcService)list.get(j);
                    service.setSurveyNumber(surveyNo);
                    sdao.updateOvcService(service,false,false);                   
                }
            }
    }
    catch(Exception ex)
    {
        ex.printStackTrace();
    }
}


protected void fillServiceDetails(OvcServiceForm ovcserviceForm,String userName)
throws Exception
{

     ovcService = new OvcService();
     OvcServiceDao serviceDao=new OvcServiceDaoImpl();
    
    String[] psychoServices=ovcserviceForm.getPsychoServices();
    String[] nutritionServices=ovcserviceForm.getNutritionalServices();
    String[] healthServices=ovcserviceForm.getHealthServices();
    String[] educationalServices=ovcserviceForm.getEducationalServices();
    String[] protectionServices=ovcserviceForm.getProtectionServices();
    String[] shelterServices=ovcserviceForm.getShelterServices();
    String[] economicServices=ovcserviceForm.getEconomicServices();
    String[] referralServices=ovcserviceForm.getReferralServices();


    String psychosocialOther=ovcserviceForm.getPsychosocialOther();
    String nutritionOther=ovcserviceForm.getNutritionOther();
    String healthOther=ovcserviceForm.getHealthOther();
    String educationalOther=ovcserviceForm.getEducationOther();
    String protectionOther=ovcserviceForm.getProtectionOther();
    String shelterOther=ovcserviceForm.getShelterOther();
    String economicOther=ovcserviceForm.getEconomicOther();

    ovcService.setserviceAccessed1(appUtil.concatStr(psychoServices,psychosocialOther));
    ovcService.setserviceAccessed2(appUtil.concatStr(nutritionServices,nutritionOther));
    ovcService.setserviceAccessed3(appUtil.concatStr(healthServices,healthOther));
    ovcService.setserviceAccessed4(appUtil.concatStr(educationalServices,educationalOther));
    ovcService.setserviceAccessed5(appUtil.concatStr(protectionServices,protectionOther));
    ovcService.setserviceAccessed6(appUtil.concatStr(shelterServices,shelterOther));
    ovcService.setserviceAccessed7(appUtil.concatStr(economicServices,economicOther));
    ovcService.setServicesReferred(appUtil.concatStr(referralServices,null));

    ovcService.setOvcId(ovcserviceForm.getOvcId());
    String serviceDate=DateManager.processMthDayYearToMysqlFormat(ovcserviceForm.getServiceDate());
    ovcService.setServicedate(serviceDate);
    ovcService.setDateOfEntry(appUtil.getCurrentDate());
        
    ovcService.setWeight(ovcserviceForm.getCurrentWeight());
    ovcService.setHeight(ovcserviceForm.getCurrentHeight());
    ovcService.setSurveyNumber(0);
    ovcService.setProviderName(ovcserviceForm.getProviderName());
    ovcService.setNumberOfServices(serviceDao.getNumberOfServicesPerServiceRecord(ovcService));
    ovcService.setCurrentHivStatus(ovcserviceForm.getNewHivStatus());
    ovcService.setEnrolledInCare(ovcserviceForm.getEnrolledInCare());
    ovcService.setEnrolledOnART(ovcserviceForm.getEnrolledOnART());
    ovcService.setFacilityId(ovcserviceForm.getOrganizationClientIsReferred());
    ovcService.setChildMissedSchool(ovcserviceForm.getChildMissedSchool());
    if(ovcserviceForm.getChildAbused()==0 || ovcserviceForm.getChildAbused()==1)
    ovcserviceForm.setChildLinkedToGovt(1);
    ovcService.setChildAbused(ovcserviceForm.getChildAbused());
    ovcService.setChildLinkedToGovt(ovcserviceForm.getChildLinkedToGovt());
    ovcService.setRecordedBy(userName);

    String withdrawalDate=serviceDate;

    ovcWithdrawal = null;

    if(ovcserviceForm.getReasonWithdrawal() != null)
    {
        ovcWithdrawal = new OvcWithdrawal();
        ovcWithdrawal.setOvcId(ovcserviceForm.getOvcId());
        ovcWithdrawal.setDateOfWithdrawal(withdrawalDate);
        ovcWithdrawal.setType("ovc");
        ovcWithdrawal.setReasonWithdrawal(ovcserviceForm.getReasonWithdrawal());
    }
}



private boolean compareDates(String enrollmentDate, String serviceDate)
{
    String[] enrollmentDateArray;
    String[] serviceDateArray;
   //AppUtility appUtil=new AppUtility();
    boolean dateFlag=false;
    if(enrollmentDate !=null && serviceDate !=null)
    {
        if(enrollmentDate.equalsIgnoreCase(serviceDate))
        dateFlag=true;
        else
        {
         enrollmentDateArray=enrollmentDate.split("-");
         int year1=Integer.parseInt(enrollmentDateArray[0]);
         int mth1=Integer.parseInt(enrollmentDateArray[1]);
         int day1=Integer.parseInt(enrollmentDateArray[2]);

         serviceDateArray=serviceDate.split("-");
         int year2=Integer.parseInt(serviceDateArray[0]);
         int mth2=Integer.parseInt(serviceDateArray[1]);
         int day2=Integer.parseInt(serviceDateArray[2]);
         dateFlag=DateManager.compareDate(year1, mth1, day1,year2, mth2, day2);
        }
    }
    return dateFlag;
}
protected void saveService(OvcService ovcService, OvcWithdrawal ovcWithdrawal) throws Exception {

try{
        OvcServiceDao dao = new OvcServiceDaoImpl();
        int surveyNo=dao.getOvcServiceCount(ovcService.getOvcId());
        surveyNo++;
        ovcService.setSurveyNumber(surveyNo);
        if(ovcWithdrawal == null)
        {
            dao.saveOvcService(ovcService,true,true);
        }
        else
        {
            OvcWithdrawalDao wdao = new OvcWithdrawalDaoImpl();
            OvcDao ovcdao = new OvcDaoImpl();
            String reasonWithdrawal=ovcWithdrawal.getReasonWithdrawal();
            if(!reasonWithdrawal.equalsIgnoreCase(NomisConstant.DIED) && !reasonWithdrawal.equalsIgnoreCase(NomisConstant.MIGRATED) &&!reasonWithdrawal.equalsIgnoreCase(NomisConstant.LOST_TO_FOLLOWUP))
            {
                dao.saveOvcService(ovcService,true,true);
                wdao.saveOvcWithdrawal(ovcWithdrawal); 
            }
            else
            {
                wdao.saveOvcWithdrawal(ovcWithdrawal);
            }
        }

    }
    catch(Exception e)
    {
            target = new String("failure");
           s+="5 " + e.getMessage();
    }
}
protected void updateService(OvcService ovcService, OvcWithdrawal ovcWithdrawal, OvcServiceForm ovcserviceForm) throws Exception {
     try {
            ovcService.setId(ovcserviceForm.getOvcServiceId());
            OvcServiceDao dao = new OvcServiceDaoImpl();
            dao.updateOvcService(ovcService,true,true);

        } catch (Exception e) {
            //target = new String("failure");
            s += "2 " + e.getMessage();
            throw new Exception(e);
        }
    if(ovcWithdrawal != null) {

    try{

            OvcWithdrawalDao dao = new OvcWithdrawalDaoImpl();
            dao.saveOrUpdateOvcWithdrawal(ovcWithdrawal);
        }
    catch(Exception e)
    {
            target = new String("failure");
           s+= "4 " + e.getMessage();
    }
    }



}




protected void deleteService(OvcService ovcService, OvcWithdrawal ovcWithdrawal) throws Exception {



         try {
            OvcServiceDao dao = new OvcServiceDaoImpl();
            dao.deleteOvcService(ovcService.getOvcId(), ovcService.getServicedate());

        } catch (Exception e) {
            //target = new String("failure");
            s += "2 " + e.getMessage();
            throw new Exception(e);
        }



    if(ovcWithdrawal != null) {

    try{
            OvcWithdrawalDao dao = new OvcWithdrawalDaoImpl();
            dao.deleteOvcWithdrawal(ovcWithdrawal);
        }catch(Exception e) {
            target = new String("failure");
           s+= "4 " + e.getMessage();
        }
    }
}
private void setBaselineDetails(Ovc ovc,OvcServiceForm ovcserviceForm,String selectedCbo, String hhSerialNo)
{
    if(ovc !=null)
    {
        String otherName1=" ";
        String otherName2=" ";
        String[] ovcIdArray=ovc.getOvcId().split("/");
        ovcserviceForm.setOvcSno(ovcIdArray[ovcIdArray.length-1]);
        ovcserviceForm.setOvcId(ovc.getOvcId());
        ovcserviceForm.setDateEnrollment(appUtil.getDisplayDate(ovc.getDateEnrollment()));
        ovcserviceForm.setAge(ovc.getCurrentAge());
        ovcserviceForm.setAgeUnit(ovc.getCurrentAgeUnit());
        String otherNames=ovc.getFirstName();
        if(ovc.getFirstName() !=null && ovc.getFirstName().indexOf(",") !=-1)
        {
            String[] otherNamesArray=ovc.getFirstName().split(",");
            otherName1=otherNamesArray[0];
            if(otherNamesArray.length > 1)
            otherName2=otherNamesArray[1];
        }
        else
        otherName1=otherNames;
        ovcserviceForm.setOtherNames1(otherName1);
        ovcserviceForm.setOtherNames2(otherName2);
        ovcserviceForm.setSurname(ovc.getLastName());
        ovcserviceForm.setAddress(ovc.getAddress());
        ovcserviceForm.setGender(ovc.getGender());
        ovcserviceForm.setPhone(ovc.getPhone());
        ovcserviceForm.setWeight(ovc.getWeight());
        ovcserviceForm.setHeight(ovc.getHeight());
        //ovcserviceForm.setWard(util.getWardName(ovc.getWard()));
        ovcserviceForm.setCompletedbyCbo(selectedCbo);
        ovcserviceForm.setHhSerialNo(hhSerialNo);
    }
}
public void enableControlForHIVPrevControl(HttpSession session,String ovcId)
{
    try
    {
        DaoUtil util=new DaoUtil();
        Ovc ovc=util.getOvcDaoInstance().getOvc(ovcId);
        if((ovc.getCurrentAge()>9 && ovc.getCurrentAge()<18) && ovc.getCurrentAgeUnit() !=null && ovc.getCurrentAgeUnit().equalsIgnoreCase("Year"))
        session.setAttribute("hivprevdisabled", "false");
    }
    catch(Exception ex)
    {
        ex.printStackTrace();
    }
}
private void setBtnState(HttpSession session, String saveState, String modifyState)
{
    session.setAttribute("serviceSaveDisabled", saveState);
    session.setAttribute("serviceModifyDisabled", modifyState);
    
}
}
