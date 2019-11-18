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
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author smomoh
 */
public class DatimReportForm extends org.apache.struts.action.ActionForm {
    
    private String state;
    private String cbo;
    private String orgUnitGroup;
    private String partnerCode;
    private String lgaCode;//exitedMonth1
    private int activeMonth1;
    private int activeYear1;
    private int activeMonth2;
    private int activeYear2;
    
    private int exitedMonth1;
    private int exitedYear1;
    private int exitedMonth2;
    private int exitedYear2;
    private String actionName;
    private String periodChkBox;
    private String reportType;
    private List stateList=new ArrayList();
    private List organizationList=new ArrayList();
    private List lgaList=new ArrayList();
    private List organizationUnitGroupList=new ArrayList();
    private List partnerList=new ArrayList();
    
    public DatimReportForm() {
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

    public int getActiveMonth1() {
        return activeMonth1;
    }

    public void setActiveMonth1(int activeMonth1) {
        this.activeMonth1 = activeMonth1;
    }

    public int getActiveMonth2() {
        return activeMonth2;
    }

    public void setActiveMonth2(int activeMonth2) {
        this.activeMonth2 = activeMonth2;
    }

    public int getActiveYear1() {
        return activeYear1;
    }

    public void setActiveYear1(int activeYear1) {
        this.activeYear1 = activeYear1;
    }

    public int getActiveYear2() {
        return activeYear2;
    }

    public void setActiveYear2(int activeYear2) {
        this.activeYear2 = activeYear2;
    }

    public int getExitedMonth1() {
        return exitedMonth1;
    }

    public void setExitedMonth1(int exitedMonth1) {
        this.exitedMonth1 = exitedMonth1;
    }

    public int getExitedMonth2() {
        return exitedMonth2;
    }

    public void setExitedMonth2(int exitedMonth2) {
        this.exitedMonth2 = exitedMonth2;
    }

    public int getExitedYear1() {
        return exitedYear1;
    }

    public void setExitedYear1(int exitedYear1) {
        this.exitedYear1 = exitedYear1;
    }

    public int getExitedYear2() {
        return exitedYear2;
    }

    public void setExitedYear2(int exitedYear2) {
        this.exitedYear2 = exitedYear2;
    }

    public String getLgaCode() {
        return lgaCode;
    }

    public void setLgaCode(String lgaCode) {
        this.lgaCode = lgaCode;
    }

    public List getLgaList() {
        return lgaList;
    }

    public void setLgaList(List lgaList) {
        this.lgaList = lgaList;
    }

    public String getOrgUnitGroup() {
        return orgUnitGroup;
    }

    public void setOrgUnitGroup(String orgUnitGroup) {
        this.orgUnitGroup = orgUnitGroup;
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

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public List getPartnerList() {
        return partnerList;
    }

    public void setPartnerList(List partnerList) {
        this.partnerList = partnerList;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List getStateList() {
        return stateList;
    }

    public void setStateList(List stateList) {
        this.stateList = stateList;
    }

@Override
public void reset(ActionMapping mapping,HttpServletRequest request)
{
    actionName=null;
    periodChkBox=null;
    //preparedDataSourceChkBox=null;
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
