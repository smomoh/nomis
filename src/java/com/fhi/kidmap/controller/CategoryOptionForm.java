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
public class CategoryOptionForm extends org.apache.struts.action.ActionForm
{

    private String actionName;
    private String categoryOptionId;
    private String categoryOptionName;
    private String categoryOptionDescription;
    private String disaggregation;
    private String logic;
    private String ageUnit;
    private String hiddenId;
    private int startAge;
    private int endAge;

    public CategoryOptionForm()
    {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    
    public String getCategoryOptionDescription() {
        return categoryOptionDescription;
    }

    public void setCategoryOptionDescription(String categoryOptionDescription) {
        this.categoryOptionDescription = categoryOptionDescription;
    }

    public String getCategoryOptionId() {
        return categoryOptionId;
    }

    public void setCategoryOptionId(String categoryOptionId) {
        this.categoryOptionId = categoryOptionId;
    }

    public String getCategoryOptionName() {
        return categoryOptionName;
    }

    public void setCategoryOptionName(String categoryOptionName) {
        this.categoryOptionName = categoryOptionName;
    }

    public String getDisaggregation() {
        return disaggregation;
    }

    public void setDisaggregation(String disaggregation) {
        this.disaggregation = disaggregation;
    }

    public String getLogic() {
        return logic;
    }

    public void setLogic(String logic) {
        this.logic = logic;
    }

    public int getEndAge() {
        return endAge;
    }

    public void setEndAge(int endAge) {
        this.endAge = endAge;
    }

    public int getStartAge() {
        return startAge;
    }

    public void setStartAge(int startAge) {
        this.startAge = startAge;
    }

    public String getAgeUnit() {
        return ageUnit;
    }

    public void setAgeUnit(String ageUnit) {
        this.ageUnit = ageUnit;
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
        categoryOptionId=null;
        categoryOptionName=null;
        categoryOptionDescription=null;
        disaggregation=null;
        hiddenId=null;
        logic=null;
        startAge=0;
        endAge=0;
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
        else if(getCategoryOptionName()==null || getCategoryOptionName().length() <1)
        errors.add("categoryOptionName", new ActionMessage("categoryOptionName.invalid"));
        else if(getAgeUnit()==null)
        errors.add("ageUnit", new ActionMessage("ageUnit.invalid"));
        return errors;
    }
}
