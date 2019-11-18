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
 * @author smomoh
 */
public class OVCServiceSpecialOperationsForm extends org.apache.struts.action.ActionForm {
    
    private String actionName;
    private String serviceDate;
    private String[] servicesToChange;
    
    public OVCServiceSpecialOperationsForm() 
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

    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String[] getServicesToChange() {
        return servicesToChange;
    }

    public void setServicesToChange(String[] servicesToChange) {
        this.servicesToChange = servicesToChange;
    }
    
@Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        
        actionName=null;
        servicesToChange=null;
        serviceDate=null;
    }
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if(this.getActionName() ==null)
        {
            return errors;
        }
        else if(this.getActionName().equalsIgnoreCase("save"))
        {
            if(this.getServicesToChange()==null || this.getServicesToChange().length<1)
            {
                errors.add("servicesToChange", new ActionMessage("error.servicesToChange.required"));
                
            }
            else if(this.getServiceDate()==null || this.getServiceDate().indexOf("/")==-1)
            {
                errors.add("serviceDate", new ActionMessage("error.serviceDate.required"));
                return errors;
            }
        }
        return errors;
    }
}
