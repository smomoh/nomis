/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.HouseholdEnrollmentDao;
import com.fhi.kidmap.dao.HouseholdEnrollmentDaoImpl;
import com.fhi.kidmap.dao.OvcServiceDao;
import com.fhi.kidmap.dao.OvcServiceDaoImpl;
import com.fhi.nomis.OperationsManagement.HivRecordsManager;
import com.fhi.nomis.OperationsManagement.SchoolAttendanceManager;
import com.fhi.nomis.nomisutils.TaskManager;
import com.nomis.databasemanagement.DatabaseCleanup;
import com.nomis.upgrade.NomisUpgrade;
import com.nomis.upgrade.UpgradeManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author smomoh
 */
public class DatabaseCleanupAction extends org.apache.struts.action.Action {
    
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
        DatabaseCleanupForm comform=(DatabaseCleanupForm)form;
        DatabaseCleanup dbc=new DatabaseCleanup();
        String requiredAction=comform.getActionName();
        String qparam=request.getParameter("qparam");
        if(qparam !=null)
        requiredAction=qparam;
        System.err.println("requiredAction in DatabaseCleanupAction is "+requiredAction);
        String msg=" ";
        request.setAttribute("dbUtilityBtnDisabled", "false");
        request.setAttribute("msg", msg);
        if(TaskManager.isDatabaseLocked())
        {
            msg="System busy. Kindly try again later";
            requiredAction=null;
            return mapping.findForward(SUCCESS);
        }
        if(requiredAction==null)
        {
            comform.reset(mapping, request);
            return mapping.findForward(SUCCESS);
        }
        /*else if(requiredAction.equalsIgnoreCase("updatecghivstausId"))
        {
            try
            {
               msg=dbc.resetClientIdForHouseholdHeadsInHivStatusUpdate();
            }
            catch(Exception ex)
            {
                msg=ex.getMessage();
            }
            TaskManager.setDatabaseLocked(false);
        }*/
        else if(requiredAction.equalsIgnoreCase("hivStatusInRiskAssessment"))
        {
            try
            {
               msg=dbc.updateHIVStatusInHivRiskAssessment();
            }
            catch(Exception ex)
            {
                msg=ex.getMessage();
            }
            TaskManager.setDatabaseLocked(false);
        }
        else if(requiredAction.equalsIgnoreCase("correctHhUniqueIdInHhs"))
        {
            try
            {
               msg=dbc.correctHhUniqueIdInHouseholdService() +" Household service records cleaned";
            }
            catch(Exception ex)
            {
                msg=ex.getMessage();
            }
            TaskManager.setDatabaseLocked(false);
        }
        else if(requiredAction.equalsIgnoreCase("createEnrollmentIds"))
        {
            try
            {
               //msg=SchoolAttendanceManager.populateOvcSchoolAttendanceRecords()+" OVC School attendance records reconfigured";
            }
            catch(Exception ex)
            {
                msg=ex.getMessage();
            }
            TaskManager.setDatabaseLocked(false);
        }
        else if(requiredAction.equalsIgnoreCase("reconfigureChildSchoolStatus"))
        {
            try
            {
               msg=SchoolAttendanceManager.populateChildSchoolStatusRecords()+" OVC School attendance records reconfigured";
            }
            catch(Exception ex)
            {
                msg=ex.getMessage();
            }
            TaskManager.setDatabaseLocked(false);
        }
        else if(requiredAction.equalsIgnoreCase("reconfigureSchAttendanceTracker"))
        {
            try
            {
               msg=SchoolAttendanceManager.moveSchoolAttendanceTrackerRecordsFromCaregiverExpenditureRecord()+" OVC records processed for School attendance tracker";
            }
            catch(Exception ex)
            {
                msg=ex.getMessage();
            }
            TaskManager.setDatabaseLocked(false);
        }
        else if(requiredAction.equalsIgnoreCase("markGraduatedOvcAsServed"))
        {
            try
            {
               //ServiceRecords srec=new ServiceRecords();
               //srec.recordGraduatedOvcAsServed();
               
            }
            catch(Exception ex)
            {
                msg=ex.getMessage();
            }
            TaskManager.setDatabaseLocked(false);
        }
        else if(requiredAction.equalsIgnoreCase("activateInactiveBeneficiaries"))
        {
            try
            {
                DaoUtil util=new DaoUtil();
                util.removeInactiveClientsFromWithdrawalList();
               
            }
            catch(Exception ex)
            {
                msg=ex.getMessage();
            }
            TaskManager.setDatabaseLocked(false);
        }
        else if(requiredAction.equalsIgnoreCase("updateOvcStatus"))
        {
            try
            {
                dbc.updateOvcWithLastServiceDate(true);
               /*OptionsManagerDao opmdao=new OptionsManagerDaoImpl(); 
               OptionsManager opm=opmdao.getOptionsManager("startdateId");
               if(opm !=null && opm.getValue() !=null && opm.getValue().indexOf("-") !=-1)
               {
                   String startDate=opm.getValue();
                   opm=opmdao.getOptionsManager("rpenddateId");
                   if(opm !=null && opm.getValue() !=null && opm.getValue().indexOf("-") !=-1)
                   {
                       
                       String endDate=opm.getValue();
                       System.err.println("startDate is "+startDate+" endDate is "+endDate);
                       DaoUtil util=new DaoUtil(); 
                       util.processExitedWithoutGraduation(startDate,endDate);
                       NomisUpgrade nu=new NomisUpgrade();
                       String values=nu.updateWithdrawalStatus(true);
                   }
               }*/
            }
            catch(Exception ex)
            {
                msg=ex.getMessage();
            }
            TaskManager.setDatabaseLocked(false);
        }
        else if(requiredAction.equalsIgnoreCase("updateTestNotIndicated"))
        {
            try
            {
               DaoUtil util=new DaoUtil(); 
               util.updateHivStatusOfOvcNotAtRisk();
            }
            catch(Exception ex)
            {
                msg=ex.getMessage();
            }
            TaskManager.setDatabaseLocked(false);
        }
        else if(requiredAction.equalsIgnoreCase("normalizeLgaCodes"))
        {
            try
            {
               //TaskManager.setDatabaseLocked(true); 
               NomisUpgrade nu=new NomisUpgrade();
               msg=nu.correctLgaCodesInHouseholdEnrollmentTable();
            }
            catch(Exception ex)
            {
                msg=ex.getMessage();
            }
            TaskManager.setDatabaseLocked(false);
        }
        else if(requiredAction.equalsIgnoreCase("updateWithdrawalStatus"))
        {
            try
            {
               //TaskManager.setDatabaseLocked(true); 
               dbc.setAppropriateWithdrawalType();
               NomisUpgrade nu=new NomisUpgrade();
               msg=nu.updateWithdrawalStatus(true);
               
               //nu.updateHouseholdWithdrawal();
                
            }
            catch(Exception ex)
            {
                msg=ex.getMessage();
            }
            TaskManager.setDatabaseLocked(false);
        }
        else if(requiredAction.equalsIgnoreCase("completeHouseholdWithdrawal"))
        {
            try
            {
                System.err.println("requiredAction is "+requiredAction);
                TaskManager.setDatabaseLocked(true);
                NomisUpgrade nu=new NomisUpgrade();
                nu.updateHouseholdWithdrawal();
                msg="Household member withdrawal completed";
            }
            catch(Exception ex)
            {
                msg=ex.getMessage();
            }
            TaskManager.setDatabaseLocked(false);
        }
        else if(requiredAction.equalsIgnoreCase("removeHouseholdsWithoutOvc"))
        {
            try
            {
                System.err.println("requiredAction is "+requiredAction);
                TaskManager.setDatabaseLocked(true);
                NomisUpgrade nu=new NomisUpgrade();
                nu.updateHouseholdWithdrawal();
                nu.withdrawHouseholdsWithoutOvc();
                msg="Household member withdrawal completed";
            }
            catch(Exception ex)
            {
                msg=ex.getMessage();
            }
            TaskManager.setDatabaseLocked(false);
        }
        else if(requiredAction.equalsIgnoreCase("updateBirthRegistrationStatus"))
        {
            try
            {
               TaskManager.setDatabaseLocked(true); 
               NomisUpgrade nu=new NomisUpgrade();
               nu.updateBirthRegistrationStatus(true);
               msg=" Task completed";
               //System.err.println("requiredAction.equalsIgnoreCase(updateBirthRegistrationStatus))");
            }
            catch(Exception ex)
            {
                msg=ex.getMessage();
            }
            TaskManager.setDatabaseLocked(false);
        }
        else if(requiredAction.equalsIgnoreCase("recreateHivStatusTable"))
        {
            try
            {
               NomisUpgrade nu=new NomisUpgrade();
               nu.updateHivStatusInFormsAndRecreateHivStatusTable();
               msg=" Task completed";
            }
            catch(Exception ex)
            {
                msg=ex.getMessage();
            }
            TaskManager.setDatabaseLocked(false);
        }
        else if(requiredAction.equalsIgnoreCase("setActiveHivStatus"))
        {
            try
            {
               //TaskManager.setDatabaseLocked(true); 
               NomisUpgrade nu=new NomisUpgrade();
               nu.setActiveHivStatus();
               msg=" Task completed";
            }
            catch(Exception ex)
            {
                msg=ex.getMessage();
            }
            TaskManager.setDatabaseLocked(false);
        }
        else if(requiredAction.equalsIgnoreCase("updateHivStatus"))
        {
            try
            {
               HivRecordsManager hrm=new HivRecordsManager();
               hrm.updateClientTypeInHivStatusUpdate();
               hrm.updateCaregiverHivStatusWithBaselineStatus();
               //hrm.setHivStatusRecordForAllOvcInHivStatusUpdate();
               hrm.setActiveHivStatusWithRecordsInHivStatusUpdate();
               hrm.updateOvcHivStatusWithBaselineStatus();
               hrm.updateOvcHivStatusWithFollowupStatus();
               
               //NomisUpgrade nu=new NomisUpgrade();
               //nu.updateHivStatus(true);
               msg=" Task completed";
            }
            catch(Exception ex)
            {
                msg=ex.getMessage();
            }
            TaskManager.setDatabaseLocked(false);
        }//
        else if(requiredAction.equals("hsuTrackingDate"))
        {
            msg=" ";
            HivRecordsManager hsum=new HivRecordsManager();
            hsum.updateLastHivTrackingDate();
            //System.err.println("contextPath is "+values);
        }
        else if(requiredAction.equals("updateReferralRecord"))
        {
            msg=" ";
            HivRecordsManager hsum=new HivRecordsManager();
            hsum.updateOvcReferralWithHTCReferralService();
            //System.err.println("contextPath is "+values);
        }
        else if(requiredAction.equalsIgnoreCase("setHouseholdHeadName"))
        {
            try
            {
               HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
               TaskManager.setDatabaseLocked(true); 
               int numberOfHouseholdsUpdated=hhedao.updateHouseholdHeadNameWithCaregiverName();
                msg=numberOfHouseholdsUpdated+" Household records updated";
                
            }
            catch(Exception ex)
            {
                msg=ex.getMessage();
            }
            TaskManager.setDatabaseLocked(false);
        }
        else if(requiredAction.equalsIgnoreCase("setNumberOfServices"))
        {
            try
            {
               OvcServiceDao serviceDao=new OvcServiceDaoImpl();
               TaskManager.setDatabaseLocked(true); 
               serviceDao.setNumberOfServicesPerServiceRecord();
                msg="Number of services recorded successfully";
                
            }
            catch(Exception ex)
            {
                msg=ex.getMessage();
            }
            TaskManager.setDatabaseLocked(false);
        }
        else if(requiredAction.equalsIgnoreCase("csiCleanup"))
        {
            try
            {
               TaskManager.setDatabaseLocked(true); 
               dbc.cleanFollowupRecords();
               //dbc.removeDuplicatesFromCSI();
                //dbc.cleanupCSI();
                msg="CSI records cleaned successfully";
                
            }
            catch(Exception ex)
            {
                msg=ex.getMessage();
            }
            TaskManager.setDatabaseLocked(false);
        }
        else if(requiredAction.equalsIgnoreCase("reorderSurveyNumber"))
        {
            try
            {
                TaskManager.setDatabaseLocked(true);
                dbc.reorderCSINumber();
                msg="CSI records reordered successfully";
                comform.reset(mapping, request);
            }
            catch(Exception ex)
            {
                msg=ex.getMessage();
            }
            TaskManager.setDatabaseLocked(false);
        }
        else if(requiredAction.equalsIgnoreCase("nutritionalAssessmentCleanup"))
        {
            try
            {
                TaskManager.setDatabaseLocked(true);
                dbc.cleanupNutritionalAssessmentRecords();
                dbc.cleanupBMIInNutritionalAssessmentRecords();
                msg="Nutritional assessment records cleaned successfully";
            }
            catch(Exception ex)
            {
                msg=ex.getMessage();
            }
            TaskManager.setDatabaseLocked(false);
        }
        else if(requiredAction.equalsIgnoreCase("reorderNutritionalAssessmentRecords"))
        {
            try
            {
                TaskManager.setDatabaseLocked(true);
                dbc.reorderNutritionalAssessmentRecords();
                msg="Nutritional assessment records reordered successfully";
            }
            catch(Exception ex)
            {
                msg=ex.getMessage();
            }
            TaskManager.setDatabaseLocked(false);
        }
        else if(requiredAction.equalsIgnoreCase("cleanupHouseholdAssessment"))
        {
            try
            {
                TaskManager.setDatabaseLocked(true);
                dbc.removeDuplicatesFromHHVA();
                dbc.repositionHouseholdEnrollmentAndFollowupAssessments();
                msg="Household assessment records cleaned successfully";
            }
            catch(Exception ex)
            {
                msg=ex.getMessage();
            }
            TaskManager.setDatabaseLocked(false);
        }
        else if(requiredAction.equalsIgnoreCase("reorderHouseholdAssessment"))
        {
            try
            {
                TaskManager.setDatabaseLocked(true);
                System.err.println("requiredAction is "+requiredAction);
                dbc.reorderHouseholdAssessmentNumber();
                msg="Household assessment records reordered";
            }
            catch(Exception ex)
            {
                msg=ex.getMessage();
            }
            TaskManager.setDatabaseLocked(false);
        }
        else if(requiredAction.equalsIgnoreCase("caregiverInformationCleanup"))
        {
            try
            {
                System.err.println("requiredAction is "+requiredAction);
                TaskManager.setDatabaseLocked(true);
                UpgradeManager um=new UpgradeManager();
                um.updateCaregiverInfoAndEnrollmentForCompatibility();
                int numberOfCaregiversCleaned=dbc.removeUnassignedCaregivers();
                msg=numberOfCaregiversCleaned+" cleaned";
            }
            catch(Exception ex)
            {
                msg=ex.getMessage();
            }
            TaskManager.setDatabaseLocked(false);
        }
        
        TaskManager.setDatabaseLocked(false);
        request.setAttribute("msg", msg);
        comform.reset(mapping, request);
        return mapping.findForward(SUCCESS);
    }
    //
}
