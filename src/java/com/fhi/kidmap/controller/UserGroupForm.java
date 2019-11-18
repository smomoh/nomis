/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author smomoh
 */
public class UserGroupForm extends org.apache.struts.action.ActionForm {
    
    private String actionName;
    private String groupId;
    private String groupName;
    private String groupType;
    private String description;
    private String[] selectedOrganizationUnitGroups;
    private String selectedUserGroup;

   public UserGroupForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String[] getSelectedOrganizationUnitGroups() {
        return selectedOrganizationUnitGroups;
    }

    public void setSelectedOrganizationUnitGroups(String[] selectedOrganizationUnitGroups) {
        this.selectedOrganizationUnitGroups = selectedOrganizationUnitGroups;
    }

    public String getSelectedUserGroup() {
        return selectedUserGroup;
    }

    public void setSelectedUserGroup(String selectedUserGroup) {
        this.selectedUserGroup = selectedUserGroup;
    }
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) 
    {
        actionName=null;
        groupId=null;
        groupName=null;
        groupType=null;
        selectedOrganizationUnitGroups=null;
        description=null;
        selectedUserGroup=null;
    }
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) 
    {
        AppUtility appUtil=new AppUtility();
        String[] exceptions={"-","_"};
        ActionErrors errors = new ActionErrors();
        if(getActionName()==null || getActionName().equalsIgnoreCase("delete") || getActionName().equalsIgnoreCase("groupdetails") || getActionName().equalsIgnoreCase("partnerList"))
        return errors;
        else if(getGroupName()==null || !appUtil.isString(getGroupName()))
        errors.add("groupName", new ActionMessage("error.groupName.empty"));
        else if(appUtil.hasSpecialCharacters(getGroupName(), exceptions))
        errors.add("groupName", new ActionMessage("error.invalidGroupName"));
        else if(getSelectedOrganizationUnitGroups()==null || getSelectedOrganizationUnitGroups().length==0)
        errors.add("selectedOrganizationUnitGroups", new ActionMessage("error.organizationUnitGroups.empty"));
        return errors;
    }
}
