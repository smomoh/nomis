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
 * @author smomoh
 */
public class HivRiskAssessmentReportForm extends org.apache.struts.action.ActionForm {
    
    private String actionName;
    private String stateCode;
    private String lgaCode;
    private String cboCode;
    private String orgUnitGroupId;
    private String wardCode;
    private String partnerCode;
    private String startMth;
    private String startYear;
    private String endMth;
    private String endYear;
    private String showOldFields;
    List stateList=new ArrayList();
    List lgaList=new ArrayList();
    List cboList=new ArrayList();
    List wardList=new ArrayList();
    List partnerList=new ArrayList();
    List orgUnitGroupList=new ArrayList();

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getCboCode() {
        return cboCode;
    }

    public void setCboCode(String cboCode) {
        this.cboCode = cboCode;
    }

    public String getEndMth() {
        return endMth;
    }

    public void setEndMth(String endMth) {
        this.endMth = endMth;
    }

    public String getEndYear() {
        return endYear;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }

    public String getLgaCode() {
        return lgaCode;
    }

    public void setLgaCode(String lgaCode) {
        this.lgaCode = lgaCode;
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

    public String getStartMth() {
        return startMth;
    }

    public void setStartMth(String startMth) {
        this.startMth = startMth;
    }

    public String getStartYear() {
        return startYear;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getWardCode() {
        return wardCode;
    }

    public void setWardCode(String wardCode) {
        this.wardCode = wardCode;
    }

    public List getCboList() {
        return cboList;
    }

    public void setCboList(List cboList) {
        this.cboList = cboList;
    }

    public List getLgaList() {
        return lgaList;
    }

    public void setLgaList(List lgaList) {
        this.lgaList = lgaList;
    }

    public List getPartnerList() {
        return partnerList;
    }

    public void setPartnerList(List partnerList) {
        this.partnerList = partnerList;
    }

    public List getStateList() {
        return stateList;
    }

    public void setStateList(List stateList) {
        this.stateList = stateList;
    }

    public List getWardList() {
        return wardList;
    }

    public void setWardList(List wardList) {
        this.wardList = wardList;
    }

    public List getOrgUnitGroupList() {
        return orgUnitGroupList;
    }

    public void setOrgUnitGroupList(List orgUnitGroupList) {
        this.orgUnitGroupList = orgUnitGroupList;
    }

    public String getShowOldFields() {
        return showOldFields;
    }

    public void setShowOldFields(String showOldFields) {
        this.showOldFields = showOldFields;
    }

    
    public HivRiskAssessmentReportForm() {
        super();
        // TODO Auto-generated constructor stub
    }
@Override
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
        
            // TODO: add 'error.name.required' key to your resources
        
        return errors;
    }
}
