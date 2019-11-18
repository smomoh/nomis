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
public class DataElementsImportForm extends org.apache.struts.action.ActionForm {
    
    private String dataElementFileUpload;

    
    public DataElementsImportForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getDataElementFileUpload() {
        return dataElementFileUpload;
    }

    public void setDataElementFileUpload(String dataElementFileUpload) {
        this.dataElementFileUpload = dataElementFileUpload;
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
