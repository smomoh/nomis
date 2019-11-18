/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Siaka
 */
public class CustomReportForm extends org.apache.struts.action.ActionForm {
    
    private String state;
    private String cbo;
    private String orgUnitGroup;
    private String partnerCode;
    private String lgaCode;
    private int enroll_month;
    private int enroll_year;
    private int enroll_month2;
    private int enroll_year2;
    private String actionName;
    private String periodChkBox;
    private String reportType;
    private List stateList=new ArrayList();
    private List organizationList=new ArrayList();
    private List lgaList=new ArrayList();
    private List organizationUnitGroupList=new ArrayList();
    private List partnerList=new ArrayList();
    
    public CustomReportForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLgaCode() {
        return lgaCode;
    }

    public void setLgaCode(String lgaCode) {
        this.lgaCode = lgaCode;
    }

    public String getCbo() {
        return cbo;
    }

    public void setCbo(String cbo) {
        this.cbo = cbo;
    }

    public String getOrgUnitGroup() {
        return orgUnitGroup;
    }

    public void setOrgUnitGroup(String orgUnitGroup) {
        this.orgUnitGroup = orgUnitGroup;
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

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
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

    public List getStateList() {
        return stateList;
    }

    public void setStateList(List stateList) {
        this.stateList = stateList;
    }

    public List getLgaList() {
        return lgaList;
    }

    public void setLgaList(List lgaList) {
        this.lgaList = lgaList;
    }

    public List getOrganizationList() {
        return organizationList;
    }

    public void setOrganizationList(List organizationList) {
        this.organizationList = organizationList;
    }

    public List getOrganizationUnitGroupList() {
        return organizationUnitGroupList;
    }

    public void setOrganizationUnitGroupList(List organizationUnitGroupList) {
        this.organizationUnitGroupList = organizationUnitGroupList;
    }

    
    public List getPartnerList() {
        return partnerList;
    }

    public void setPartnerList(List partnerList) {
        this.partnerList = partnerList;
    }
    
    @Override
    public void reset(ActionMapping mapping,HttpServletRequest request)
    {
        actionName=null;
        periodChkBox=null;
    }
    /**
     * This is the action called from the Struts framework.
     *
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) 
    {
        ActionErrors errors = new ActionErrors();
        
        return errors;
    }
}
