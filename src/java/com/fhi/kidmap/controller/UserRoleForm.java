/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author Siaka
 */
public class UserRoleForm extends org.apache.struts.action.ActionForm {
    
    private String actionName;
    private String roleId;
    private String roleName;
    private String[] assignedRights;
    private String userRoleList;

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String[] getAssignedRights() {
        return assignedRights;
    }

    public void setAssignedRights(String[] assignedRights) {
        this.assignedRights = assignedRights;
    }

    public String getUserRoleList() {
        return userRoleList;
    }

    public void setUserRoleList(String userRoleList) {
        this.userRoleList = userRoleList;
    }
        
    public UserRoleForm() {
        super();
        // TODO Auto-generated constructor stub
    }
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) 
    {
        actionName=null;
        roleId=null;
        roleName=null;
        assignedRights=null;
        userRoleList=null;
    }
    /**
     * This is the action called from the Struts framework.
     *
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        
        return errors;
    }
}
