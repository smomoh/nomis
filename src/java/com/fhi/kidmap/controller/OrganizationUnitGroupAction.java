/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import com.fhi.kidmap.business.OrganizationUnitGroup;
import com.fhi.kidmap.dao.OrganizationUnitGroupDao;
import com.fhi.kidmap.dao.OrganizationUnitGroupDaoImpl;
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
public class OrganizationUnitGroupAction extends org.apache.struts.action.Action {
    
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
        OrganizationUnitGroupForm ougForm=(OrganizationUnitGroupForm)form;
        OrganizationUnitGroupDao ougdao=new OrganizationUnitGroupDaoImpl();
        OrganizationUnitGroup oug=new OrganizationUnitGroup();
        String orgUnitGroupId=ougForm.getOrgUnitGroupId();
        String orgUnitGroupName=ougForm.getOrgUnitGroupName();
        String hiddenId=ougForm.getHiddenId();
        String requiredAction=ougForm.getActionName();
        String description=ougForm.getDescription();
        oug.setOrgUnitGroupId(orgUnitGroupId);
        oug.setOrgUnitGroupName(orgUnitGroupName);
        oug.setDescription(description);
        
        HttpSession session=request.getSession();
        
        session.setAttribute("ougSavedisabled", "false");
        session.setAttribute("ougModifyDisabled", "true");
        ougForm.setOrgUnitGroupList(ougdao.getAllOrganizationUnitGroups());
        if(requiredAction==null)
        {
            ougForm.reset(mapping, request);
        }
        else if(requiredAction.equalsIgnoreCase("details"))
        {
            oug=ougdao.getOrganizationUnitGroup(orgUnitGroupId);
            if(oug !=null)
            {
                ougForm.reset(mapping, request);
                ougForm.setOrgUnitGroupName(oug.getOrgUnitGroupName());
                ougForm.setDescription(oug.getDescription());
                ougForm.setHiddenId(oug.getOrgUnitGroupId());
                session.setAttribute("ougSavedisabled", "true");
                session.setAttribute("ougModifyDisabled", "false");
                return mapping.findForward(SUCCESS);
            }
        }
        else if(requiredAction.equalsIgnoreCase("save"))
        {
            ougdao.saveOrganizationUnitGroup(oug);
        }
        else if(requiredAction.equalsIgnoreCase("modify"))
        {
            oug.setOrgUnitGroupId(hiddenId);
            ougdao.updateOrganizationUnitGroup(oug);
        }
        else if(requiredAction.equalsIgnoreCase("delete"))
        {
            oug.setOrgUnitGroupId(hiddenId);
            ougdao.deleteOrganizationUnitGroup(oug);
        }
        ougForm.reset(mapping, request);
        ougForm.setOrgUnitGroupList(ougdao.getAllOrganizationUnitGroups());
        return mapping.findForward(SUCCESS);
    }
}
