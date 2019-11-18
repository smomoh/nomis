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
public class EnrollmentReportForm extends org.apache.struts.action.ActionForm {
    
    private String state;
    private String lga;
    private String cbo;
    private String ward;
    private String partnerCode;
    private int enroll_month;
    private int enroll_year;
    private int enroll_month2;
    private int enroll_year2;
    private String enroll_startAge;
    private String enroll_endAge;
    private String actionName;

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

    public String getEnroll_endAge() {
        return enroll_endAge;
    }

    public void setEnroll_endAge(String enroll_endAge) {
        this.enroll_endAge = enroll_endAge;
    }

    public int getEnroll_month() {
        return enroll_month;
    }

    public void setEnroll_month(int enroll_month) {
        this.enroll_month = enroll_month;
    }

    public int getEnroll_month2() {
        return enroll_month2;
    }

    public void setEnroll_month2(int enroll_month2) {
        this.enroll_month2 = enroll_month2;
    }

    public String getEnroll_startAge() {
        return enroll_startAge;
    }

    public void setEnroll_startAge(String enroll_startAge) {
        this.enroll_startAge = enroll_startAge;
    }

    public int getEnroll_year() {
        return enroll_year;
    }

    public void setEnroll_year(int enroll_year) {
        this.enroll_year = enroll_year;
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

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    @Override
    public void reset(ActionMapping mapping,HttpServletRequest request)
    {
       /*name=null;
       state=null;
        lga=null;
        cbo=null;
        ward=null;
        enroll_month=1;
        enroll_year=2010;
        enroll_month2=1;
        enroll_year2=2010;*/
        enroll_startAge="0";
        enroll_endAge="18+";
        actionName=null;
    }
    
    /**
     *
     */
    public EnrollmentReportForm() {
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
