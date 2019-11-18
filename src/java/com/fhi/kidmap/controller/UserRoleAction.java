/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.UserRole;
import com.fhi.kidmap.dao.UserRightDao;
import com.fhi.kidmap.dao.UserRightDaoImpl;
import com.fhi.kidmap.dao.UserRoleDao;
import com.fhi.kidmap.dao.UserRoleDaoImpl;
import com.fhi.nomis.OperationsManagement.UserRightsManager;
import com.fhi.nomis.nomisutils.AccessManager;
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
 * @author Siaka
 */
public class UserRoleAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    /**
     * This is the action called from the Struts framework.
     *
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
        UserRoleForm urForm=(UserRoleForm)form;
        UserRoleDao roleDao=new UserRoleDaoImpl();
        UserRightDao urDao=new UserRightDaoImpl();
        UserRole ur=new UserRole();
        AppUtility appUtil=new AppUtility();
        String roleId=urForm.getRoleId();
        String roleName=urForm.getRoleName();
        String[] assignedRightsArray=urForm.getAssignedRights();
        
        ur.setRoleId(roleId);
        ur.setRoleName(roleName);
        String assignedRights=appUtil.concatStr(assignedRightsArray, null);
        ur.setAssignedRights(assignedRights);
        String requiredAction=urForm.getActionName();
        HttpSession session=request.getSession();
        AccessManager acm=new AccessManager();
        boolean userInRole=acm.isUserInRole("002",appUtil.getCurrentUser(session));
        if(!userInRole)
        {
            request.setAttribute("errorMsg", appUtil.getAccessDeniedMessage());
            return mapping.findForward("accessDenied");
        }
        session.setAttribute("userRoleSaveDisabled", "false");
        session.setAttribute("userRoleModifyDisabled", "true");
        
        //System.err.println("Required action is "+requiredAction);
        if(requiredAction==null)
        {
           
        }
        else if(requiredAction.equalsIgnoreCase("roledetails"))
        {
             ur=roleDao.getUserRole(urForm.getUserRoleList());
             urForm.reset(mapping, request);
            if(ur !=null)
            {
               
                urForm.setRoleId(ur.getRoleId());
                urForm.setRoleName(ur.getRoleName());
                urForm.setAssignedRights(appUtil.splitString(ur.getAssignedRights(), ","));
                session.setAttribute("userRoleSaveDisabled", "true");
                session.setAttribute("userRoleModifyDisabled", "false");
            }
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("save"))
        {
            //String duplicateRoleName=null;
            UserRole duplicateRole=null;
            try
            {
                duplicateRole=roleDao.getUserRoleByRoleName(ur.getRoleName());
                if(duplicateRole !=null)
                {
                    ActionErrors errors=new ActionErrors();
                    errors.add("roleName", new ActionMessage("roleNameExist"));
                    saveErrors(request, errors);
                    return mapping.findForward(SUCCESS);
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
            int noOfRoles=roleDao.getNoOfUserRoles();
            noOfRoles++;
            roleId=getRoleId(roleDao,noOfRoles);
            ur.setRoleId(roleId);
            roleDao.saveUserRole(ur);
        }
        else if(requiredAction.equalsIgnoreCase("modify"))
        {
            roleDao.updateUserRole(ur);
        }
        else if(requiredAction.equalsIgnoreCase("delete"))
        {
            roleDao.deleteUserRole(ur);
        }
        List list=urDao.getAllUserRights();
        if(list==null)
         list=new ArrayList();
        list.addAll(UserRightsManager.getUserRights());
           List roleList=roleDao.getAllUserRoles();
           session.setAttribute("avalaibleRights", list);
           session.setAttribute("existingRoles", roleList);
        urForm.reset(mapping, request);
        return mapping.findForward(SUCCESS);
    }
    private String getRoleId(UserRoleDao urDao,int noOfRoles)
    {
        String roleId="roleId"+noOfRoles;
        try
        {
            UserRole ur=urDao.getUserRole(roleId);
            if(ur !=null)
            {
                //System.err.println("roleId in getRoleId is "+ur.getRoleId());
                noOfRoles++;
                roleId=getRoleId(urDao,noOfRoles);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        //System.err.println("roleId is "+roleId);
        return roleId;
    }
}
