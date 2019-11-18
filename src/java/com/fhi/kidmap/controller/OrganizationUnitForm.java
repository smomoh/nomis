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
public class OrganizationUnitForm extends org.apache.struts.action.ActionForm {
    
    private String actionName;
    private String orgunitId;
    private String orgunitName;
    private String orgunitShortCode;
    private String parentId;
    private int level;

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getOrgunitId() {
        return orgunitId;
    }

    public void setOrgunitId(String orgunitId) {
        this.orgunitId = orgunitId;
    }

    public String getOrgunitName() {
        return orgunitName;
    }

    public void setOrgunitName(String orgunitName) {
        this.orgunitName = orgunitName;
    }

    public String getOrgunitShortCode() {
        return orgunitShortCode;
    }

    public void setOrgunitShortCode(String orgunitShortCode) {
        this.orgunitShortCode = orgunitShortCode;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    
    
    /**
     *
     */
    public OrganizationUnitForm() {
        super();
        // TODO Auto-generated constructor stub
    }
@Override
public void reset(ActionMapping mapping, HttpServletRequest request)
{
    actionName=null;
    orgunitId=null;
    orgunitName=null;
    orgunitShortCode=null;
    parentId=null;
    level=1;
}
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        AppUtility appUtil=new AppUtility();
        ActionErrors errors = new ActionErrors();
        if(getActionName()==null || getActionName().equalsIgnoreCase("delete"))
        return errors;
        else if(this.getParentId()==null || !appUtil.isString(getParentId()))
        errors.add("parentId", new ActionMessage("errors.parent.required"));
        else if(getOrgunitName()==null || !appUtil.isString(getOrgunitName()))
        errors.add("orgunitName", new ActionMessage("errors.orgunitName.required"));
        else if(getOrgunitShortCode()==null || !appUtil.isString(getOrgunitShortCode()))
        errors.add("orgunitShortCode", new ActionMessage("errors.orgunitShortCode.required"));
        return errors;
    }
}
