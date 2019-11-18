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
public class PartnersForm extends org.apache.struts.action.ActionForm {
    
    private String partnerName;
    private String actionName;
    private String partnerCode;
    private String partnerList;
    
    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getPartnerList() {
        return partnerList;
    }

    public void setPartnerList(String partnerList) {
        this.partnerList = partnerList;
    }

     /**
     *
     */
    public PartnersForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if(getActionName()==null || getActionName().equalsIgnoreCase("delete") || getActionName().equalsIgnoreCase("partnerDetails"))
        return errors;

        if (this.getPartnerCode() == null || getPartnerCode().length() < 1)
            errors.add("partnerCode", new ActionMessage("error.partnerCode.required"));
        else if (getPartnerCode().length() >3)
            errors.add("partnerCode", new ActionMessage("error.partnerCode.length"));
        else if (this.getPartnerName() == null || getPartnerName().length() < 1)
            errors.add("partnerName", new ActionMessage("error.partnerName.required"));
        else if (getPartnerName().length() > 100)
            errors.add("partnerName", new ActionMessage("error.partnerName.length"));
        
        return errors;
    }
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        partnerName=null;
        actionName=null;
        partnerCode=null;
        partnerList=null;
    }
}
