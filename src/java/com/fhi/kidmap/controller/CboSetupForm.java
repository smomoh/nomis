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
public class CboSetupForm extends org.apache.struts.action.ActionForm {
    
    private String state_code;
    private String lga_code;
    private String userName;
    private String partner;
    private String actionName;


    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public CboSetupForm() {
        super();
        // TODO Auto-generated constructor stub
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLga_code() {
        return lga_code;
    }

    public void setLga_code(String lga_code) {
        this.lga_code = lga_code;
    }

    public String getState_code() {
        return state_code;
    }

    public void setState_code(String state_code) {
        this.state_code = state_code;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }
    



    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        lga_code =null;
        userName=null;
        state_code=null;
        partner=null;
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
        if(getActionName()==null || getActionName().equalsIgnoreCase("stateList"))
        return errors;
        else if (this.getState_code() == null ||  this.getState_code().length()< 1)
            errors.add("state_name", new ActionMessage("error.state_code.required"));
        //else if (this.getLga_code() == null ||  this.getLga_code().length()< 1)
            //errors.add("lga_name", new ActionMessage("error.lga_name.required"));
                
        return errors;
    }
}
