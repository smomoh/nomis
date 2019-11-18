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
public class OvcMthlySummaryForm extends org.apache.struts.action.ActionForm {

    private String state;
    private String orgUnitGroupId;
    private String lga;
    private String cbo;
    private String partnerCode;
    private int enroll_month;
    private int enroll_year;
    private int enroll_month2;
    private int enroll_year2;
    private String actionName;
    private String periodChkBox;
    private String reportType;

    public OvcMthlySummaryForm() {
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
    
    public int getEnroll_month() {
        return enroll_month;
    }

    public void setEnroll_month(int enroll_month) {
        this.enroll_month = enroll_month;
    }

    public int getEnroll_year() {
        return enroll_year;
    }

    public void setEnroll_year(int enroll_year) {
        this.enroll_year = enroll_year;
    }

    public int getEnroll_month2() {
        return enroll_month2;
    }

    public void setEnroll_month2(int enroll_month2) {
        this.enroll_month2 = enroll_month2;
    }

    public int getEnroll_year2() {
        return enroll_year2;
    }

    public void setEnroll_year2(int enroll_year2) {
        this.enroll_year2 = enroll_year2;
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

    public void setState(String state) {
        this.state = state;
    }

    public String getPeriodChkBox() {
        return periodChkBox;
    }

    public void setPeriodChkBox(String periodChkBox) {
        this.periodChkBox = periodChkBox;
    }
    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }



    @Override
    public void reset(ActionMapping mapping,
HttpServletRequest request)
    {
        actionName=null;
        periodChkBox=null;
    }
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();

            // TODO: add 'error.name.required' key to your resources

        return errors;
    }
}
