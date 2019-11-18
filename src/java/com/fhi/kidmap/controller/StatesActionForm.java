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
 * @author COMPAQ USER
 */
public class StatesActionForm extends org.apache.struts.action.ActionForm {
    
    private String name;
    private String state_code;
    private String stateList;
    String actionName;

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public StatesActionForm()
    {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getState_code() {
        return state_code;
    }

    public void setState_code(String state_code) {
        this.state_code = state_code;
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @param string
     */
    public void setName(String string) {
        name = string;
    }

    public String getStateList() {
        return stateList;
    }

    public void setStateList(String stateList) {
        this.stateList = stateList;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
         name=null;
         state_code=null;
         stateList=null;
         actionName=null;
    }
    

    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

        ActionErrors errors = new ActionErrors();
        if(getActionName()==null || this.getActionName().equalsIgnoreCase("delete") || getActionName().equalsIgnoreCase("stateDetails"))
        return errors;
        if(this.getState_code()==null || getState_code().length()<1)
        errors.add("state_code", new ActionMessage("error.stateCode.required"));
        else if (this.getState_code() != null && getState_code().length() >3)
            errors.add("codeLength", new ActionMessage("error.state_code.length"));
        else if (getName() == null || getName().length() < 1)
            errors.add("state_name", new ActionMessage("error.state_name.required"));
        else if (getName() != null && getName().length() >50)
            errors.add("nameLength", new ActionMessage("error.state_name.length"));
                
        return errors;
    }
}
