/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomis.dataexchange.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author smomoh
 */
public class DataExchangeOrganizationUnitForm extends org.apache.struts.action.ActionForm {
    
    private String actionName;
    private String metadataType;
    private String softwareInstance;
    private FormFile upload;
    
    public DataExchangeOrganizationUnitForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getMetadataType() {
        return metadataType;
    }

    public void setMetadataType(String metadataType) {
        this.metadataType = metadataType;
    }

    public String getSoftwareInstance() {
        return softwareInstance;
    }

    public void setSoftwareInstance(String softwareInstance) {
        this.softwareInstance = softwareInstance;
    }

    public FormFile getUpload() {
        return upload;
    }

    public void setUpload(FormFile upload) {
        this.upload = upload;
    }
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        actionName=null;
        metadataType=null;
        softwareInstance=null;
        upload=null;
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
