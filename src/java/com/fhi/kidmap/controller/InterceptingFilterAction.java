/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import com.fhi.kidmap.business.User;
import com.fhi.nomis.nomisutils.AccessManager;
import com.fhi.nomis.nomisutils.AppUtility;
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
public class InterceptingFilterAction extends org.apache.struts.action.Action {

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
        InterceptingFilterForm ifform=(InterceptingFilterForm)form;
        AppUtility appUtil=new AppUtility();
        String requiredAction=ifform.getActionName();
        String req=request.getParameter("q");
        HttpSession session=request.getSession();
        
        AccessManager acm=new AccessManager();
        boolean userInRole=acm.isUserInRole("001fb",appUtil.getCurrentUser(session));
        if(userInRole)
        {
            request.setAttribute("enableBeneficiaryDisable", "true");
        }
        else
        {
            request.setAttribute("emptyLink", "true");
            request.setAttribute("accessdenied", "Sorry. You do not have access to this page");
        }
        if(req !=null)
        requiredAction=req;
        System.err.println("requiredAction in intercepting filter is "+requiredAction);
        if(requiredAction==null)
        {
            return mapping.findForward("beneficiaryRecords");
        }
        else if(requiredAction.equalsIgnoreCase("brm"))
        {
            return mapping.findForward("beneficiaryRecords");
        }
        else if(requiredAction.equalsIgnoreCase("hhva"))
        {
            return mapping.findForward("hhva");
        }
        else if(requiredAction.equalsIgnoreCase("csc"))
        {
            return mapping.findForward("csc");
        }
        else if(requiredAction.equalsIgnoreCase("ceasa"))
        {
            return mapping.findForward("ceasa");
        }
        //cgexpandschattend
        //householdVulnerabilityIndexAction.do
        return mapping.findForward(SUCCESS);
    }
}
