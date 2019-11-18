/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomis.dataexchange.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author smomoh
 */
public class DhisInstanceForm extends org.apache.struts.action.ActionForm {
    
    private String actionName;
    private String instanceName;
    private String availableInstance;
    private String hiddenId;
    public DhisInstanceForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getAvailableInstance() {
        return availableInstance;
    }

    public void setAvailableInstance(String availableInstance) {
        this.availableInstance = availableInstance;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getHiddenId() {
        return hiddenId;
    }

    public void setHiddenId(String hiddenId) {
        this.hiddenId = hiddenId;
    }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        actionName=null;
        instanceName=null;
        availableInstance=null;
        hiddenId=null;
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
        String[] exceptions={"_","-"};
        if(getActionName()==null || getActionName().equalsIgnoreCase("details") || getActionName().equalsIgnoreCase("delete"))
        return errors;
        else if(this.getInstanceName()==null || appUtil.hasSpecialCharacters(getInstanceName(), exceptions))
        errors.add("instanceName", new ActionMessage("error.instanceName.required"));
        else if(appUtil.hasSpecialCharacters(getInstanceName(), exceptions) || !appUtil.isString(getInstanceName()))
        errors.add("instanceName", new ActionMessage("error.instanceName.invalid"));
        else if(getInstanceName().length() >24)
        errors.add("instanceName", new ActionMessage("error.instanceName.toolong"));
        return errors;
    }
}
