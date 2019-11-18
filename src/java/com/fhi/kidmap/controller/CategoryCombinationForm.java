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
public class CategoryCombinationForm extends org.apache.struts.action.ActionForm {

    private String actionName;
    private String hiddenId;
    private String[] categoryId;
    private String categoryComboId;
    private String catComboName;
    private String description;
    private String query;
    private String programArea;
    private String type;
    

    public CategoryCombinationForm() {
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

    
    public String getCatComboName() {
        return catComboName;
    }

    public void setCatComboName(String catComboName) {
        this.catComboName = catComboName;
    }

    public String getCategoryComboId() {
        return categoryComboId;
    }

    public void setCategoryComboId(String categoryComboId) {
        this.categoryComboId = categoryComboId;
    }

    public String[] getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String[] categoryId) {
        this.categoryId = categoryId;
    }

    
    public String getHiddenId() {
        return hiddenId;
    }

    public void setHiddenId(String hiddenId) {
        this.hiddenId = hiddenId;
    }

    public String getProgramArea() {
        return programArea;
    }

    public void setProgramArea(String programArea) {
        this.programArea = programArea;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        actionName=null;
        hiddenId=null;
        categoryId=null;
        categoryComboId=null;
        catComboName=null;
        description=null;
        query=null;
        programArea=null;
        type=null;
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
        else if(getCatComboName()==null || getCatComboName().length() < 3)
        errors.add("catComboName", new ActionMessage("catComboName.invalid"));
        else if(getCategoryId()==null)
        errors.add("categoryId", new ActionMessage("categoryId.invalid"));
        return errors;
    }
}
