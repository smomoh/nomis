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
 * @author smomoh
 */
public class CategoryForm extends org.apache.struts.action.ActionForm
{
    private String actionName;
    private String categoryId;
    private String categoryName;
    private String hiddenId;
    private String[] categoryOptionId;

    public CategoryForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String[] getCategoryOptionId() {
        return categoryOptionId;
    }

    public void setCategoryOptionId(String[] categoryOptionId) {
        this.categoryOptionId = categoryOptionId;
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
        categoryId=null;
        categoryName=null;
        hiddenId=null;
        categoryOptionId=null;
    }
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {
        ActionErrors errors = new ActionErrors();
        if(getActionName()==null || getActionName().equalsIgnoreCase("details") || getActionName().equalsIgnoreCase("delete"))
        return errors;
        else if(getCategoryName()==null || getCategoryName().length()<4)
        errors.add("categoryName", new ActionMessage("categoryName.invalid"));
        else if(getCategoryOptionId()==null)
        errors.add("categoryOption", new ActionMessage("categoryOption.invalid"));
        return errors;
    }
}
