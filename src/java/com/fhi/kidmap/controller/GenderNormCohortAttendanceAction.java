/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import com.fhi.kidmap.business.Caregiver;
import com.fhi.kidmap.business.GenderNormCohortAttendance;
import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.dao.CaregiverDao;
import com.fhi.kidmap.dao.CaregiverDaoImpl;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.GenderNormCohortAttendanceDao;
import com.fhi.kidmap.dao.GenderNormCohortAttendanceDaoImpl;
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
public class GenderNormCohortAttendanceAction extends org.apache.struts.action.Action {

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
        GenderNormCohortAttendanceForm gncaf=(GenderNormCohortAttendanceForm)form;
        GenderNormCohortAttendanceDao gncadao=new GenderNormCohortAttendanceDaoImpl();
        DateManager dm=new DateManager();
        
        DaoUtil util=new DaoUtil();
        
        CaregiverDao cgiverDao=new CaregiverDaoImpl();
        Caregiver cgiver=null;
        OvcDao ovcDao=new OvcDaoImpl();
        HttpSession session=request.getSession();
        LoadUpInfo loadup=new LoadUpInfo();
        session.setAttribute("ovcIdFieldDisabled", "False");
        session.setAttribute("vcHhUniqueId", " ");
        
        String requiredAction=gncaf.getActionName();
       
       String lgaCode=gncaf.getLga();
       String cboCode=gncaf.getCompletedbyCbo();
       String ward=gncaf.getWard();
       String hhUniqueId=gncaf.getHhUniqueId();
       String hhSerialNo=gncaf.getHhSerialNo();
       String clientId=gncaf.getClientId();
       String targetGroup=gncaf.getTargetGroup();//gncaf..getOvcId();
       
       List ovcList=new ArrayList();

       int sessionNumber=0;
       int sessionCount=0;
       request.setAttribute("sessionNumber", sessionNumber);
       request.setAttribute("sessionCount", sessionCount);
       String hhUniqueIdMsg="<span style=color:red>This household unique Id does not exist</span>";
       enableDisableButtons(session, "true","true");
       
       if(requiredAction ==null)
       {
           gncaf.reset(mapping, request);
           loadup.getStartupInfo(session);
           session.setAttribute("gncafOvcList", ovcList);
           session.setAttribute("gncafCaregiverList", ovcList);
           session.setAttribute("gncafName", "VC Name");
           session.setAttribute("gncafId", "Ovc Unique Id");
           session.setAttribute("gncafIdValue", " ");
           //session.setAttribute("gncafModifyDisabled", "true");
           //session.setAttribute("gncafSaveDisabled", "false");
           session.removeAttribute("ovcWithdrawn");
           return mapping.findForward(SUCCESS);
       }
       else if(requiredAction.equalsIgnoreCase("cboList"))
       {
            loadup.getCbosPerLgaFromSetup(session,gncaf.getLga());
            gncaf.reset(mapping, request);
            gncaf.setLga(lgaCode);
            return mapping.findForward("success");
       }
       else if(requiredAction.equalsIgnoreCase("cbo"))
       {
           form.reset(mapping, request);
           gncaf.setLga(lgaCode);
           gncaf.setCompletedbyCbo(cboCode);
           
           return mapping.findForward(SUCCESS);
       }
       else if(requiredAction.equalsIgnoreCase("hhDetails"))
       {  
        session.removeAttribute("ovcWithdrawn");
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        gncaf.reset(mapping, request);
          try
          {
                    HouseholdEnrollment hhe=hhedao.getHouseholdEnrollment(hhUniqueId);

                    gncaf.setLga(lgaCode);
                    gncaf.setCompletedbyCbo(cboCode);
                    gncaf.setHhSerialNo(hhSerialNo);
                    if(hhe !=null)
                    {
                        hhUniqueIdMsg=hhUniqueId;
                        gncaf.setHhUniqueId(hhUniqueId);
                        gncaf.setWard(util.getWardName(hhe.getCommunityCode()));
                        if(targetGroup.equalsIgnoreCase("Ovc"))
                        {
                            ovcList=ovcDao.getOvcPerHousehold(hhUniqueId);
                            session.setAttribute("gncafOvcList", ovcList);
                            session.setAttribute("gncafCaregiverList", new ArrayList());
                            session.setAttribute("gncafName", "VC Name");
                            session.setAttribute("gncafId", "Ovc Unique Id");
                            session.setAttribute("gncafIdValue", " ");
                        }
                        else if(targetGroup.equalsIgnoreCase("Caregiver"))
                        {
                            List caregiverList=cgiverDao.getListOfCaregiversFromSameHousehold(hhUniqueId);
                            session.setAttribute("gncafCaregiverList", caregiverList);
                            session.setAttribute("gncafOvcList", ovcList);
                            session.setAttribute("gncafName", "Caregiver Name");
                            session.setAttribute("gncafId", "Caregiver Id");//refIdValue
                            session.setAttribute("gncafIdValue", " ");
                        }
                        gncaf.setTargetGroup(targetGroup);

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
           session.removeAttribute("ovcWithdrawn");
           String sno=gncaf.getOvcSno();
           gncaf.reset(mapping, request);
           gncaf.setLga(lgaCode);
           gncaf.setCompletedbyCbo(cboCode);
           gncaf.setHhUniqueId(hhUniqueId);
           gncaf.setHhSerialNo(hhSerialNo);
           hhUniqueIdMsg=hhUniqueId;
           session.setAttribute("vcHhUniqueId", hhUniqueIdMsg);
           Ovc ovc=null;
           ovcDao=new OvcDaoImpl();
           OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl();
           List list=ovcDao.getOVC(clientId);
           String ovcWithdrawn=" ";
           list=wdao.getWithdrawnOvc(clientId);
           //List ovcDetailsList=new ArrayList();
           int age=0;
           String address=null;
           String ageUnit=null;
           String phone=null;
           String gender=null;
           String caregiverName=null;
           if(!list.isEmpty())
            {
                if(targetGroup.equalsIgnoreCase("Ovc"))
                ovcWithdrawn="This VC has been withdrawn";
                else if(targetGroup.equalsIgnoreCase("Caregiver"))
                ovcWithdrawn="This Caregiver has been withdrawn";
                session.setAttribute("ovcWithdrawn", ovcWithdrawn);
            }
           if(targetGroup.equalsIgnoreCase("Ovc"))
           {
                ovc=ovcDao.getOvc(clientId);
                if(ovc !=null)
                {
                    age=ovc.getAge();
                    address=ovc.getAddress();
                    ageUnit=ovc.getAgeUnit();
                    phone=ovc.getPhone();
                    gender=ovc.getGender();
                    session.setAttribute("gncafIdValue", ovc.getOvcId());
                    Caregiver cgiver2=cgiverDao.getCaregiverByCaregiverId(ovc.getCaregiverId());
                    if(cgiver2 !=null)
                    {
                        caregiverName=cgiver2.getCaregiverFirstname()+" "+cgiver2.getCaregiverLastName();
                    }
                }
           }
           else if(targetGroup.equalsIgnoreCase("Caregiver"))
           {
                cgiver=cgiverDao.getCaregiverByCaregiverId(clientId);
                if(cgiver !=null)
                {
                    age=cgiver.getCaregiverAge();
                    address=cgiver.getCaregiverAddress();
                    ageUnit="Years";
                    phone=cgiver.getCaregiverPhone();
                    gender=cgiver.getCaregiverGender();
                    session.setAttribute("gncafIdValue", cgiver.getCaregiverId());
                }
           }
            if(ovc !=null || cgiver !=null)
            {
                gncaf.setOvcSno(sno);
                gncaf.setClientId(clientId);
                gncaf.setLga(lgaCode);
                gncaf.setCompletedbyCbo(cboCode);
                gncaf.setWard(ward);
                
                gncaf.setAge(age);
                gncaf.setAgeUnit(ageUnit);
                gncaf.setAddress(address);
                gncaf.setPhone(phone);
                gncaf.setGender(gender);
                gncaf.setCaregiverName(caregiverName);
                gncaf.setTargetGroup(targetGroup);
                enableDisableButtons(session, "false","true");
                
            }
            else
            session.setAttribute("ovcWithdrawn", "This VC or caregiver does not exist");
            return mapping.findForward(SUCCESS);
       }
       else if(requiredAction.equalsIgnoreCase("gncadetails"))
       {
           GenderNormCohortAttendance gnca=gncadao.getGenderNormCohortAttendance(clientId,dm.processMthDayYearToMysqlFormat(gncaf.getDateOfAttendance()));
            if(gnca !=null)
            {
                gncaf.setRecordId(gnca.getRecordId());
                gncaf.setDateOfAttendance(dm.convertMysqlDateTomonthDayYear(gnca.getDateOfAttendance()));
                gncaf.setPcvName(gnca.getEndorsedBy());
                enableDisableButtons(session, "true","false");
            }
            else
            enableDisableButtons(session, "false","true");
            return mapping.findForward(SUCCESS);
       }
       else if(requiredAction.equalsIgnoreCase("save"))
       {
           GenderNormCohortAttendance gnca=getGenderNormCohortAttendance(session,gncaf);
           gncadao.saveGenderNormCohortAttendance(gnca);
           return mapping.findForward(SUCCESS);
       }
       else if(requiredAction.equalsIgnoreCase("modify"))
       {
           GenderNormCohortAttendance gnca=getGenderNormCohortAttendance(session,gncaf);
           gncadao.updateGenderNormCohortAttendance(gnca);
           return mapping.findForward(SUCCESS);
       }
       else if(requiredAction.equalsIgnoreCase("delete"))
       {
           GenderNormCohortAttendance gnca=getGenderNormCohortAttendance(session,gncaf);
           gncadao.deleteGenderNormCohortAttendance(gnca);
           return mapping.findForward(SUCCESS);
       }
        return mapping.findForward(SUCCESS);
    }
    private void enableDisableButtons(HttpSession session, String saveBtnDisabled,String modifyButtonDisabled)
    {
        session.setAttribute("gncafSaveDisabled", saveBtnDisabled);
        session.setAttribute("gncafModifyDisabled", modifyButtonDisabled);  
    }
    private GenderNormCohortAttendance getGenderNormCohortAttendance(HttpSession session,GenderNormCohortAttendanceForm gncaf)
    {
        DateManager dm=new DateManager();
        AppUtility appUtil=new AppUtility();
        GenderNormCohortAttendance gnca=new GenderNormCohortAttendance();
        gnca.setClientId(gncaf.getClientId());
        gnca.setDateOfAttendance(dm.processMthDayYearToMysqlFormat(gncaf.getDateOfAttendance()));
        gnca.setLastModifiedDate(appUtil.getCurrentDate());
        gnca.setEndorsedBy(gncaf.getPcvName());
        gnca.setLastModifiedBy(appUtil.getCurrentUser(session));
        gnca.setTargetGroup(gncaf.getTargetGroup());
        return gnca;
    }
}
