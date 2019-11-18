/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.report.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author smomoh
 */
public class CaregiverListForm extends org.apache.struts.action.ActionForm {
    
    private String actionName;
    private String state;
    private String orgUnitGroupId;
    private String lga;
    private String cbo;
    private String ward;
    private String partnerCode;
    private String enroll_month;
    private String enroll_year;
    private String enroll_month2;
    private String enroll_year2;

   public CaregiverListForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }


    public String getCbo() {
        return cbo;
    }

    public void setCbo(String cbo) {
        this.cbo = cbo;
    }

    public String getLga() {
        return lga;
    }

    public void setLga(String lga) {
        this.lga = lga;
    }

    public String getState() {
        return state;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOrgUnitGroupId() {
        return orgUnitGroupId;
    }

    public void setOrgUnitGroupId(String orgUnitGroupId) {
        this.orgUnitGroupId = orgUnitGroupId;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getEnroll_month() {
        return enroll_month;
    }

    public void setEnroll_month(String enroll_month) {
        this.enroll_month = enroll_month;
    }

    public String getEnroll_month2() {
        return enroll_month2;
    }

    public void setEnroll_month2(String enroll_month2) {
        this.enroll_month2 = enroll_month2;
    }

    public String getEnroll_year() {
        return enroll_year;
    }

    public void setEnroll_year(String enroll_year) {
        this.enroll_year = enroll_year;
    }

    public String getEnroll_year2() {
        return enroll_year2;
    }

    public void setEnroll_year2(String enroll_year2) {
        this.enroll_year2 = enroll_year2;
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
