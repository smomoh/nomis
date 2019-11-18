/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author Siaka
 */
public class MetadataMgtForm extends org.apache.struts.action.ActionForm {
    
    private String[] selectedStates;
    private String[] selectedLgas;
    private String[] selectedCBOs;
    private String[] selectedWards;
    private String[] selectedPartners;
    private String[] selectedVulnerabilityStatus;
    private String[] selectedSchools;
    private String[] selectedFacilities;
    private String actionName;
    private String[] metadata;
    private FormFile upload;
    

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
    public String[] getMetadata() {
        return metadata;
    }

    public void setMetadata(String[] metadata) {
        this.metadata = metadata;
    }

    public FormFile getUpload() {
        return upload;
    }

    public void setUpload(FormFile upload) {
        this.upload = upload;
    }

    public String[] getSelectedCBOs() {
        return selectedCBOs;
    }

    public void setSelectedCBOs(String[] selectedCBOs) {
        this.selectedCBOs = selectedCBOs;
    }

    public String[] getSelectedLgas() {
        return selectedLgas;
    }

    public void setSelectedLgas(String[] selectedLgas) {
        this.selectedLgas = selectedLgas;
    }

    public String[] getSelectedWards() {
        return selectedWards;
    }

    public void setSelectedWards(String[] selectedWards) {
        this.selectedWards = selectedWards;
    }

    public String[] getSelectedFacilities() {
        return selectedFacilities;
    }

    public void setSelectedFacilities(String[] selectedFacilities) {
        this.selectedFacilities = selectedFacilities;
    }

    public String[] getSelectedPartners() {
        return selectedPartners;
    }

    public void setSelectedPartners(String[] selectedPartners) {
        this.selectedPartners = selectedPartners;
    }

    public String[] getSelectedSchools() {
        return selectedSchools;
    }

    public void setSelectedSchools(String[] selectedSchools) {
        this.selectedSchools = selectedSchools;
    }

    public String[] getSelectedVulnerabilityStatus() {
        return selectedVulnerabilityStatus;
    }

    public void setSelectedVulnerabilityStatus(String[] selectedVulnerabilityStatus) {
        this.selectedVulnerabilityStatus = selectedVulnerabilityStatus;
    }

    public String[] getSelectedStates() {
        return selectedStates;
    }

    public void setSelectedStates(String[] selectedStates) {
        this.selectedStates = selectedStates;
    }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        actionName=null;
        metadata=null;
        upload=null;
        selectedStates=null;
        selectedLgas=null;
        selectedCBOs=null;
        selectedPartners=null;
        selectedVulnerabilityStatus=null;
        selectedSchools=null;
        selectedFacilities=null;
    }
       
    public MetadataMgtForm() {
        super();
        // TODO Auto-generated constructor stub
    }
private boolean hasExportParameters()
{//
    boolean exportParametersSelected=false;
    if((getSelectedStates()!=null && getSelectedStates().length>0)
    || (getSelectedCBOs()!=null && getSelectedCBOs().length>0)
    || (getSelectedFacilities() !=null && getSelectedFacilities().length>0)
    || (getSelectedLgas() !=null && getSelectedLgas().length>0)
    || (getSelectedPartners() !=null && getSelectedPartners().length>0)
    || (getSelectedSchools() !=null && getSelectedSchools().length>0)
    || (getSelectedVulnerabilityStatus() !=null && getSelectedVulnerabilityStatus().length>0)
    || (getSelectedWards() !=null && getSelectedWards().length>0))
     exportParametersSelected=true;
    return  exportParametersSelected;
}
    /**
     * This is the action called from the Struts framework.
     *
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if(this.getActionName()==null)
        return errors;
        else if(this.getActionName().equalsIgnoreCase("export"))
        {
            if(!hasExportParameters())
            errors.add("exportParameters", new ActionMessage("errors.exportParameters.required"));
        }
        return errors;
    }
}
