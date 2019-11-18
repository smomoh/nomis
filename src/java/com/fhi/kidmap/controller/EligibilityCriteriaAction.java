/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.EligibilityCriteria;
import com.fhi.nomis.nomisutils.LoadUpInfo;
import com.fhi.kidmap.dao.EligibilityCriteriaDao;
import com.fhi.kidmap.dao.EligibilityCriteriaDaoImpl;
import com.fhi.nomis.nomisutils.AccessManager;
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
public class EligibilityCriteriaAction extends org.apache.struts.action.Action {
    
    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    private static final String FAILURE = "failure";
    
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

        HttpSession session=request.getSession();
        EligibilityCriteriaForm eligibilityForm=(EligibilityCriteriaForm)form;
        EligibilityCriteria eligibilityCritria=new EligibilityCriteria();
        EligibilityCriteriaDao dao=new EligibilityCriteriaDaoImpl();
        LoadUpInfo loadup=new LoadUpInfo();
        loadup.getAllEligibilityCriteria(session);
        AppUtility appUtil=new AppUtility();
        AccessManager acm=new AccessManager();
        boolean userInRole=acm.isUserInRole("009",appUtil.getCurrentUser(session));
        if(!userInRole)
        {
            request.setAttribute("eligibilitysavedisabled", "true");
            request.setAttribute("eligibilityModifyDisabled", "true");
            return mapping.findForward(SUCCESS);
            //request.setAttribute("errorMsg", appUtil.getAccessDeniedMessage());
            //return mapping.findForward("accessDenied");
        }
                
        String eligibilityName=eligibilityForm.getEligibilityName();
        if(eligibilityName !=null)
        eligibilityName=eligibilityName.trim();

        eligibilityCritria.setEligibilityName(eligibilityName);
        String requiredAction=eligibilityForm.getActionName();

        String eligibilityId=eligibilityForm.getEligibilityList();
        int id=0;
        if(eligibilityId !=null)
        id=Integer.parseInt(eligibilityId);
        if(requiredAction==null)
        {
            eligibilityForm.reset(mapping, request);
            request.setAttribute("eligibilitysavedisabled", "false");
            request.setAttribute("eligibilityModifyDisabled", "true");
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("eligibilityDetails"))
        {
            //if(appUtil.isUserInRole(session, "Administrator"))
            //{
                EligibilityCriteria eligibilityCriteria=dao.getEligibilityCriteria(id);
                eligibilityForm.setEligibilityName(eligibilityCriteria.getEligibilityName());
                request.setAttribute("eligibilitysavedisabled", "true");
                request.setAttribute("eligibilityModifyDisabled", "false");
            //}
            //else
            /*{
                request.setAttribute("eligibilitysavedisabled", "true");
                request.setAttribute("eligibilityModifyDisabled", "true");
            }*/
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("save"))
        {
            EligibilityCriteria criteria=dao.getEligibilityCriteria(eligibilityName);
            ActionErrors errors=checkEligibilityName(criteria,request);
            if(!errors.isEmpty())
            {
                request.setAttribute("eligibilitysavedisabled", "false");
                request.setAttribute("eligibilityModifyDisabled", "true");
                return mapping.findForward(FAILURE);
            }
            dao.saveEligibilityCriteria(eligibilityCritria);
        }
        
        else if(requiredAction.equalsIgnoreCase("modify"))
        {
            EligibilityCriteria criteria=dao.getEligibilityCriteria(eligibilityName);
            ActionErrors errors=checkEligibilityName(criteria,request);
            if(!errors.isEmpty())
            {
                request.setAttribute("eligibilitysavedisabled", "true");
                request.setAttribute("eligibilityModifyDisabled", "false");
                return mapping.findForward(FAILURE);
            }
            eligibilityCritria.setId(id);
            dao.updateEligibilityCriteria(eligibilityCritria);
        }
        else if(requiredAction.equalsIgnoreCase("delete"))
        {
            eligibilityCritria.setId(id);
            dao.deleteEligibilityCriteria(eligibilityCritria);
        }
        eligibilityForm.reset(mapping, request);
        loadup.getAllEligibilityCriteria(session);
        request.setAttribute("eligibilitysavedisabled", "false");
        request.setAttribute("eligibilityModifyDisabled", "true");
        return mapping.findForward(SUCCESS);
    }
    public ActionErrors checkEligibilityName(EligibilityCriteria criteria,HttpServletRequest request)
    {
        ActionErrors errors = new ActionErrors();
            if(criteria !=null)
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE,
                new ActionMessage("eligibility.name.exist", "This indicator already exist"));
                saveErrors(request, errors);
            }
        return errors;
    }
}
