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
public class CustomDataElementsForm extends org.apache.struts.action.ActionForm 
{
    private String actionName;
    private String hiddenId;
    private String categoryComboId;
    private String customDeId;
    private String customDeName;
    private String customDeDescription;
    private String stdDeId;
    private String stdDeDescription;
    
    
    public CustomDataElementsForm() {
        super();
        // TODO Auto-generated constructor stub
    }

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

    public String getCategoryComboId() {
        return categoryComboId;
    }

    public void setCategoryComboId(String categoryComboId) {
        this.categoryComboId = categoryComboId;
    }

    public String getCustomDeId() {
        return customDeId;
    }

    public void setCustomDeId(String customDeId) {
        this.customDeId = customDeId;
    }

    public String getCustomDeName() {
        return customDeName;
    }

    public void setCustomDeName(String customDeName) {
        this.customDeName = customDeName;
    }

    public String getCustomDeDescription() {
        return customDeDescription;
    }

    public void setCustomDeDescription(String customDeDescription) {
        this.customDeDescription = customDeDescription;
    }

    public String getStdDeId() {
        return stdDeId;
    }

    public void setStdDeId(String stdDeId) {
        this.stdDeId = stdDeId;
    }

    public String getStdDeDescription() {
        return stdDeDescription;
    }

    public void setStdDeDescription(String stdDeDescription) {
        this.stdDeDescription = stdDeDescription;
    }
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) 
    {
        actionName=null;
        hiddenId=null;
        categoryComboId=null;
        customDeId=null;
        customDeName=null;
        customDeDescription=null;
        stdDeId=null;
        stdDeDescription=null;
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
        if(getActionName()==null || getActionName().equalsIgnoreCase("details") || getActionName().equalsIgnoreCase("delete"))
        return errors;
        else if(getCustomDeName()==null || getCustomDeName().length()<4)
        errors.add("customDeName", new ActionMessage("customDeName.invalid"));
        else if(getStdDeId()==null)
        errors.add("stdDeId", new ActionMessage("stdDeId.invalid"));
        else if(getCategoryComboId()==null)
        errors.add("categoryComboId", new ActionMessage("categoryComboId.invalid"));
        return errors;
    }
}
