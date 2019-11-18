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
public class HouseholdChangeOperationForm extends org.apache.struts.action.ActionForm {
    
    private String actionName;
    private String stateCode;
    private String lgaCode;
    private String orgCode;
    private String curlgaCode;
    private String curorgCode;
    private String communityCode;
    private String curhhUniqueId;
    private String newHhUniqueId;
    private String ovcId;
    private String serialNo;
    private String ovcSerialNo;
    private String curhhSerialNo;
    
    /**
     * @return
     */
    
    public HouseholdChangeOperationForm() {
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

    public String getCurhhSerialNo() {
        return curhhSerialNo;
    }

    public void setCurhhSerialNo(String curhhSerialNo) {
        this.curhhSerialNo = curhhSerialNo;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getCurhhUniqueId() {
        return curhhUniqueId;
    }

    public void setCurhhUniqueId(String curhhUniqueId) {
        this.curhhUniqueId = curhhUniqueId;
    }
    
    
    public String getCurlgaCode() {
        return curlgaCode;
    }

    public void setCurlgaCode(String curlgaCode) {
        this.curlgaCode = curlgaCode;
    }

    public String getCurorgCode() {
        return curorgCode;
    }

    public void setCurorgCode(String curorgCode) {
        this.curorgCode = curorgCode;
    }

    public String getLgaCode() {
        return lgaCode;
    }

    public void setLgaCode(String lgaCode) {
        this.lgaCode = lgaCode;
    }

    public String getNewHhUniqueId() {
        return newHhUniqueId;
    }

    public void setNewHhUniqueId(String newHhUniqueId) {
        this.newHhUniqueId = newHhUniqueId;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOvcId() {
        return ovcId;
    }

    public void setOvcId(String ovcId) {
        this.ovcId = ovcId;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getOvcSerialNo() {
        return ovcSerialNo;
    }

    public void setOvcSerialNo(String ovcSerialNo) {
        this.ovcSerialNo = ovcSerialNo;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        actionName=null;
        //newHhUniqueId=null;
        //hhUniqueId=null;
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
