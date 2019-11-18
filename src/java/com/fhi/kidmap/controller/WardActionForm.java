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
public class WardActionForm extends org.apache.struts.action.ActionForm {
    
    private String state_code;
    private String lga_code;
    private String cbo_code;
    private String ward_code;
    private String hiddenWardCode;
    private String wardList;
    private String ward_name;
    private String actionName;

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getCbo_code() {
        return cbo_code;
    }

    public void setCbo_code(String cbo_code) {
        this.cbo_code = cbo_code;
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

    public String getWard_code() {
        return ward_code;
    }

    public void setWard_code(String ward_code) {
        this.ward_code = ward_code;
    }

    public String getWard_name() {
        return ward_name;
    }

    public void setWard_name(String ward_name) {
        this.ward_name = ward_name;
    }

    public String getWardList() {
        return wardList;
    }

    public void setWardList(String wardList) {
        this.wardList = wardList;
    }

    public String getHiddenWardCode() {
        return hiddenWardCode;
    }

    public void setHiddenWardCode(String hiddenWardCode) {
        this.hiddenWardCode = hiddenWardCode;
    }

    
    public WardActionForm() {
        super();
        // TODO Auto-generated constructor stub
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
        String requiredAction=getActionName();
        if(requiredAction==null || requiredAction.equalsIgnoreCase("refresh") || requiredAction.equalsIgnoreCase("delete") || requiredAction.equalsIgnoreCase("cboList") || requiredAction.equalsIgnoreCase("wardList") || requiredAction.equalsIgnoreCase("wardDetails"))
        return errors;
        
        /*if (getState_code() == null || getState_code().length() < 1)
        errors.add("state_code", new ActionMessage("error.state.required"));*/
        else if (getLga_code() == null || getLga_code().length() < 1)
        errors.add("lga_code", new ActionMessage("error.lga.required"));
        else if (getCbo_code() == null || getCbo_code().length() < 1)
        errors.add("cbo_code", new ActionMessage("error.cbo.required"));
        else if (getWard_code() == null || getWard_code().length() < 1)
        errors.add("ward_code", new ActionMessage("error.ward_code.required"));
        else if (getWard_code() == null || getWard_code().trim().length() != 3)
        errors.add("ward_code", new ActionMessage("error.ward_code.length"));
        else if (getWard_name() == null || getWard_name().trim().length() < 1)
        errors.add("ward_name", new ActionMessage("error.ward_name.required"));
        else if (getWard_name() != null && getWard_name().length() > 25)
        errors.add("ward_name_length", new ActionMessage("error.ward_name.length"));
        
        //System.err.println("getWard_code() is "+getWard_code());
            // TODO: add 'error.name.required' key to your resources
        
        return errors;
    }
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        ward_code =null;
        ward_name=null;
        wardList =null;
        lga_code =null;
        cbo_code=null;
        state_code=null;
        actionName=null;
    }
}
