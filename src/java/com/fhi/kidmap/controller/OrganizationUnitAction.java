/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import com.fhi.kidmap.business.OrganizationUnit;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author smomoh
 */
public class OrganizationUnitAction extends org.apache.struts.action.Action {

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
        OrganizationUnitForm ouform=(OrganizationUnitForm)form;
        OrganizationUnit ou=new OrganizationUnit();
        
        String requiredAction=ouform.getActionName();
        String orgunitId=ouform.getOrgunitId();
        String orgunitName=ouform.getOrgunitName();
        String orgunitShortCode=ouform.getOrgunitShortCode();
        String parentId=ouform.getParentId();
        int level=ouform.getLevel();
        
        ou.setOrgunitId(orgunitId);
        ou.setOrgunitName(orgunitName);
        ou.setOrgunitShortCode(orgunitShortCode);
        ou.setParentId(parentId);
        ou.setLevel(level);
        
        if(requiredAction==null)
        {
            ouform.reset(mapping, request);
            //ouform.setLevel(1);
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("save"))
        {
            
        }
        else if(requiredAction.equalsIgnoreCase("modify"))
        {
            
        }
        else if(requiredAction.equalsIgnoreCase("delete"))
        {
            
        }
        return mapping.findForward(SUCCESS);
    }
}
