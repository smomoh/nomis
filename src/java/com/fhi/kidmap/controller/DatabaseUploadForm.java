/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author smomoh
 */
public class DatabaseUploadForm extends org.apache.struts.action.ActionForm {

    private String actionName;
    private String importHivOnly;
    private String syncRecord;
    private String hivBirthRegUpdate;
    private String partnerCode;
    private FormFile uploadedFile;
    private String[] tableNames;
    private int loadFromImport;

    public DatabaseUploadForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getHivBirthRegUpdate() {
        return hivBirthRegUpdate;
    }

    public void setHivBirthRegUpdate(String hivBirthRegUpdate) {
        this.hivBirthRegUpdate = hivBirthRegUpdate;
    }

    public String getSyncRecord() {
        return syncRecord;
    }

    public void setSyncRecord(String syncRecord) {
        this.syncRecord = syncRecord;
    }
    
    public FormFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(FormFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public String getImportHivOnly() {
        return importHivOnly;
    }

    public void setImportHivOnly(String importHivOnly) {
        this.importHivOnly = importHivOnly;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String[] getTableNames() {
        return tableNames;
    }

    public void setTableNames(String[] tableNames) {
        this.tableNames = tableNames;
    }

    public int getLoadFromImport() {
        return loadFromImport;
    }

    public void setLoadFromImport(int loadFromImport) {
        this.loadFromImport = loadFromImport;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        actionName=null;
        uploadedFile=null;
        importHivOnly="off";
        partnerCode=null;
        tableNames=null;
        loadFromImport=0;
    }
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if(this.getActionName()==null)
        return errors;
        else if(getPartnerCode()==null || getPartnerCode().equalsIgnoreCase("select"))
        errors.add("partnerCode", new ActionMessage("errors.partnerCode.requiredForImport"));
        /*else if(this.getTableNames()==null || getTableNames().length==0)
        errors.add("tableNames", new ActionMessage("errors.tableNames.required"));*/
        return errors;
        
    }
}
