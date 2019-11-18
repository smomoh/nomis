/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author smomoh
 */
public class OrganizationUnitGroupForm extends org.apache.struts.action.ActionForm {
    
    private String actionName;
    private String hiddenId;
    private String orgUnitGroupId;
    private String orgUnitGroupName;
    private String description;
    private List orgUnitGroupList=new ArrayList();

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getHiddenId() {
        return hiddenId;
    }

    public void setHiddenId(String hiddenId) {
        this.hiddenId = hiddenId;
    }

    public String getOrgUnitGroupId() {
        return orgUnitGroupId;
    }

    public void setOrgUnitGroupId(String orgUnitGroupId) {
        this.orgUnitGroupId = orgUnitGroupId;
    }

    public String getOrgUnitGroupName() {
        return orgUnitGroupName;
    }

    public void setOrgUnitGroupName(String orgUnitGroupName) {
        this.orgUnitGroupName = orgUnitGroupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List getOrgUnitGroupList() {
        return orgUnitGroupList;
    }

    public void setOrgUnitGroupList(List orgUnitGroupList) {
        this.orgUnitGroupList = orgUnitGroupList;
    }

    public OrganizationUnitGroupForm() {
        super();
        // TODO Auto-generated constructor stub
    }
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        actionName=null;
        hiddenId=null;
        orgUnitGroupId=null;
        orgUnitGroupName=null;
        description=null;
    }
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if(getActionName()==null || getActionName().equalsIgnoreCase("details") || getActionName().equalsIgnoreCase("delete"))
        return errors;
        else if(getActionName()==null || getActionName().length() <1)
        errors.add("orgUnitGroupName", new ActionMessage("errors.orgUnitGroupName.required"));
        return errors;
    }
}
