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
public class EligibilityCriteriaForm extends org.apache.struts.action.ActionForm {
    
    private String eligibilityName;
    private String eligibilityList;
    private String actionName;

    /**
     * @return
     */
    public String getEligibilityName() {
        return eligibilityName;
    }

    /**
     * @param string
     */
    public void setEligibilityName(String eligibilityCriteria) {
        eligibilityName = eligibilityCriteria;
    }

    /**
     *
     */
    public EligibilityCriteriaForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    
    public String getEligibilityList() {
        return eligibilityList;
    }

    public void setEligibilityList(String eligibilityList) {
        this.eligibilityList = eligibilityList;
    }


    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        eligibilityName=null;
        eligibilityList=null;
        actionName=null;
    }
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if(getActionName()==null || getActionName().equals("eligibilityDetails"))
            return errors;
        if (getEligibilityName() == null || getEligibilityName().length() < 1) {
            errors.add("eligibilityName", new ActionMessage("error.eligibilityName.required"));
            // TODO: add 'error.name.required' key to your resources
        }
        return errors;
    }
}
