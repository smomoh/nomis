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
public class CurrentAgeUpdateForm extends org.apache.struts.action.ActionForm {

    private String actionName;

    private String[] selectedStates;
    private String ageGroup;

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String[] getSelectedStates() {
        return selectedStates;
    }

    public void setSelectedStates(String[] selectedStates) {
        this.selectedStates = selectedStates;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }


    /**
     *
     */
    public CurrentAgeUpdateForm() {
        super();
        // TODO Auto-generated constructor stub
    }
public void reset(ActionMapping mapping, HttpServletRequest request)
{
    actionName=null;
    selectedStates=null;
}
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();

        return errors;
    }
}
