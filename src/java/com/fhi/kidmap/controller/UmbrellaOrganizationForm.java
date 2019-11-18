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

/**
 *
 * @author smomoh
 */
public class UmbrellaOrganizationForm extends org.apache.struts.action.ActionForm {
    
    private String actionName;
    private String recordId;
    private String stateCode;
    private String umbrellaOrganizationCode;
    private String[] subOrganizationCodes;
    private List organizationList=new ArrayList();
    private List stateList=new ArrayList();

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }
    
    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public List getOrganizationList() {
        return organizationList;
    }

    public void setOrganizationList(List organizationList) {
        this.organizationList = organizationList;
    }
    
    public List getStateList() {
        return stateList;
    }

    public void setStateList(List stateList) {
        this.stateList = stateList;
    }

    public String[] getSubOrganizationCodes() {
        return subOrganizationCodes;
    }

    public void setSubOrganizationCodes(String[] subOrganizationCodes) {
        this.subOrganizationCodes = subOrganizationCodes;
    }

    public String getUmbrellaOrganizationCode() {
        return umbrellaOrganizationCode;
    }

    public void setUmbrellaOrganizationCode(String umbrellaOrganizationCode) {
        this.umbrellaOrganizationCode = umbrellaOrganizationCode;
    }
   @Override
   public void reset(ActionMapping mapping, HttpServletRequest request)
   {
       recordId=null;
        //umbrellaOrganizationCode=null;
        subOrganizationCodes=null;
        actionName=null;
   }
    /**
     *
     */
    public UmbrellaOrganizationForm() {
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
        
        return errors;
    }
}
