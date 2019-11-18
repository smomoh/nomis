/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.report.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author COMPAQ USER
 */
public class SchoolAttendanceForm extends org.apache.struts.action.ActionForm {
    
    
    private String schAttend_state;
    private String actionName;
    private String schAttend_lga;
    private String school_type;
    private String cbo;
    private String ward;
    private String partnerCode;

    public SchoolAttendanceForm() {
        super();
        // TODO Auto-generated constructor stub
    }
    public String getSchAttend_state() {
        return schAttend_state;
    }

    public void setSchAttend_state(String schAttend_state) {
        this.schAttend_state = schAttend_state;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getSchAttend_lga() {
        return schAttend_lga;
    }

    public void setSchAttend_lga(String schAttend_lga) {
        this.schAttend_lga = schAttend_lga;
    }

    public String getSchool_type() {
        return school_type;
    }

    public void setSchool_type(String school_type) {
        this.school_type = school_type;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getCbo() {
        return cbo;
    }

    public void setCbo(String cbo) {
        this.cbo = cbo;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }
    
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
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
        
        return errors;
    }
}
