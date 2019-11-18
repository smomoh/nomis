/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import com.fhi.kidmap.dao.HouseholdVulnerabilityAssessmentDao;
import com.fhi.kidmap.dao.HouseholdVulnerabilityAssessmentDaoImpl;
import com.fhi.kidmap.dao.OvcDao;
import com.fhi.kidmap.dao.OvcDaoImpl;
import com.fhi.nomis.nomisutils.TaskManager;
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
public class CompatibilityAction extends org.apache.struts.action.Action {
    
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
        CompatibilityForm comform=(CompatibilityForm)form;
        UpgradeManager um=new UpgradeManager();
        OvcDao ovcDao=new OvcDaoImpl();
        String requiredAction=comform.getActionName();
        String msg=" ";
        request.setAttribute("upgradeMsg", msg);
        if(TaskManager.isDatabaseLocked())
        {
            msg="System busy. Kindly try again later";
            request.setAttribute("upgradeMsg", msg);
            return mapping.findForward(SUCCESS);
        }
        if(requiredAction==null)
        {
            comform.reset(mapping, request);
        }
        else if(requiredAction.equalsIgnoreCase("assignCaregiverToOVC"))
        {
            try
            {
                um.updateCaregiverInfoAndEnrollmentForCompatibility();
                msg="Task completed successfully";
            }
            catch(Exception ex)
            {
                msg=ex.getMessage();
                //msg="An error occured. This version may not be configured to use this functionality";
            }
        }
        else if(requiredAction.equalsIgnoreCase("assignHouseholdUniqueIdToOVC"))
        {
            try
            {
                ovcDao.setHHUniqueId();
                msg="Task completed successfully";
            }
            catch(Exception ex)
            {
                msg=ex.getMessage();
            }
        }
        else if(requiredAction.equalsIgnoreCase("upgrade"))
        {
            try
            {
                um.populateHouseholdEnrollmentAndAssessment();
                msg="Task completed successfully";
            }
            catch(Exception ex)
            {
                msg=ex.getMessage();
            }
        }
        else if(requiredAction.equalsIgnoreCase("updateHouseholdService"))
        {
            try
            {
                um.updateHouseholdServiceForCompatibility();
                msg="Task completed successfully";
            }
            catch(Exception ex)
            {
                msg=ex.getMessage();
            }
        }//
        else if(requiredAction.equalsIgnoreCase("updateVulnerabilityScore"))
        {
            try
            {
                HouseholdVulnerabilityAssessmentDao hvadao=new HouseholdVulnerabilityAssessmentDaoImpl();
                hvadao.UpdateVulnerabilityScore();
                msg="Task completed successfully";
            }
            catch(Exception ex)
            {
                msg=ex.getMessage();
            }
        }//
        comform.reset(mapping, request);
        request.setAttribute("upgradeMsg", msg);
        return mapping.findForward(SUCCESS);
    }
}
