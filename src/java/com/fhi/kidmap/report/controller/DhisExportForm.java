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
public class DhisExportForm extends org.apache.struts.action.ActionForm {

    private String month;
    private String year;
    private String month2;
    private String year2;
    private String state;
    private String lga;
    private String cbo;
    private String actionName;
    private String dhisInstanceId;
    private int orgUnitLevel;

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getCbo() {
        return cbo;
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

    public void setCbo(String cbo) {
        this.cbo = cbo;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth2() {
        return month2;
    }

    public void setMonth2(String month2) {
        this.month2 = month2;
    }

    public String getYear2() {
        return year2;
    }

    public void setYear2(String year2) {
        this.year2 = year2;
    }

    public String getDhisInstanceId() {
        return dhisInstanceId;
    }

    public void setDhisInstanceId(String dhisInstanceId) {
        this.dhisInstanceId = dhisInstanceId;
    }

    public int getOrgUnitLevel() {
        return orgUnitLevel;
    }

    public void setOrgUnitLevel(int orgUnitLevel) {
        this.orgUnitLevel = orgUnitLevel;
    }

    @Override
   public void reset(ActionMapping mapping, HttpServletRequest request)
   {
       actionName=null;
   }
    /**
     *
     */
    public DhisExportForm() {
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
