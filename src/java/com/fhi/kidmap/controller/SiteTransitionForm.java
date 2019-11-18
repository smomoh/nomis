/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author smomoh
 */
public class SiteTransitionForm extends org.apache.struts.action.ActionForm 
{
    private String actionName;
    private String stateCode;
    private String lgaCode;
    private String orgCode;
    private String communityCode;
    private String partnerCode;
    private String dateOfTransition;
    private String organizationSiteTransitionedTo;
    public SiteTransitionForm() 
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

    public String getCommunityCode() {
        return communityCode;
    }

    public void setCommunityCode(String communityCode) {
        this.communityCode = communityCode;
    }

    public String getDateOfTransition() {
        return dateOfTransition;
    }

    public void setDateOfTransition(String dateOfTransition) {
        this.dateOfTransition = dateOfTransition;
    }

    public String getLgaCode() {
        return lgaCode;
    }

    public void setLgaCode(String lgaCode) {
        this.lgaCode = lgaCode;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getOrganizationSiteTransitionedTo() {
        return organizationSiteTransitionedTo;
    }

    public void setOrganizationSiteTransitionedTo(String organizationSiteTransitionedTo) {
        this.organizationSiteTransitionedTo = organizationSiteTransitionedTo;
    }

    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if(this.getActionName() !=null && this.getActionName().equalsIgnoreCase("transitionSite"))
        {
            AppUtility appUtil=new AppUtility();
            if (getCommunityCode() == null || getCommunityCode().length() < 1 || getCommunityCode().equalsIgnoreCase("All")) 
            errors.add("community", new ActionMessage("errors.community.required"));
            else if (this.getDateOfTransition() == null || this.getDateOfTransition().indexOf("/")==-1) 
            errors.add("dateOfTransition", new ActionMessage("error.dateOfTransition.required"));
            else if (getOrganizationSiteTransitionedTo() == null || !appUtil.isString(getOrganizationSiteTransitionedTo())) 
            errors.add("organizationSiteTransitionedTo", new ActionMessage("error.organizationSiteTransitionedTo.required"));
            // TODO: add 'error.name.required' key to your resources
        }
        
        return errors;
    }
}
