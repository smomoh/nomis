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
public class CbosActionForm extends org.apache.struts.action.ActionForm {
    
    
    private String state_code;
    private String lga_code;
    private String actionName;
    private String cbo_code;
    private String cboList;
    private String cbo_name;
    private String address;
    private String phone;
    private String email;
    private String website;
    private String fax;

    public CbosActionForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCbo_code() {
        return cbo_code;
    }

    public void setCbo_code(String cbo_code) {
        this.cbo_code = cbo_code;
    }

    public String getCbo_name() {
        return cbo_name;
    }

    public void setCbo_name(String cbo_name) {
        this.cbo_name = cbo_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getLga_code() {
        return lga_code;
    }

    public void setLga_code(String lga_code) {
        this.lga_code = lga_code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getState_code() {
        return state_code;
    }

    public void setState_code(String state_code) {
        this.state_code = state_code;
    }

    public String getCboList() {
        return cboList;
    }

    public void setCboList(String cboList) {
        this.cboList = cboList;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
         state_code=null;
        lga_code=null;
        cboList=null;
        actionName=null;
        cbo_code=null;
        cbo_name=null;
        address=null;
        phone=null;
        email=null;
        website=null;
        fax=null;
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
        if(requiredAction.equalsIgnoreCase("delete") || requiredAction.equalsIgnoreCase("cboList") || requiredAction.equalsIgnoreCase("cboDetails"))
        return errors;
        /*if (this.getState_code() == null || getState_code().length() < 1)
            errors.add("state_code", new ActionMessage("error.state_code.required"));*/
        else if (this.getLga_code() == null || getLga_code().length() < 1)
            errors.add("lga_code", new ActionMessage("error.lga.required"));
        else if (getCbo_code() == null || getCbo_code().length() < 1)
            errors.add("cbo_code", new ActionMessage("error.cbo_code.required"));
        else if (this.getCbo_code() != null && getCbo_code().length() > 6)
            errors.add("cbo_code_length", new ActionMessage("error.cbo_code.length"));
            
        else if (getCbo_name() == null || getCbo_name().length() < 1)
            errors.add("cbo_name", new ActionMessage("error.cbo_name.required"));
        else if (getCbo_name() != null && getCbo_name().length() > 100)
            errors.add("cbo_length", new ActionMessage("error.cbo_name.length"));
        else if (this.getPhone() != null && getPhone().length() > 15)
            errors.add("cbo_phone_length", new ActionMessage("error.cbo_phone.length"));
        else if(this.getAddress() != null && getAddress().length() > 100)
            errors.add("cbo_address_length", new ActionMessage("error.cbo_address.length"));

            // TODO: add 'error.name.required' key to your resources
        
        return errors;
    }
}
