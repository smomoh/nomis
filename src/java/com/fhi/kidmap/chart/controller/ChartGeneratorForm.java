/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.chart.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author smomoh
 */
public class ChartGeneratorForm extends org.apache.struts.action.ActionForm {
    
    private String actionName;
    private String stateCode;
    private String lgaCode;
    private String orgCode;
    private String wardCode;
    private String partnerCode;
    private String chartTitle;
    private String[] indicatorIndexes;
    private String[] selectedOrganizationUnits;
    private int startMth;
    private int startYear;
    private int endMth;
    private int endYear;
    private int startAge;
    private int endAge;
    private List stateList=new ArrayList();
    private List lgaList=new ArrayList();
    private List orgList=new ArrayList();
    private List wardList=new ArrayList();
    private List partnerList=new ArrayList();
    
    public ChartGeneratorForm()
    {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getChartTitle() {
        return chartTitle;
    }

    public void setChartTitle(String chartTitle) {
        this.chartTitle = chartTitle;
    }

    public int getEndAge() {
        return endAge;
    }

    public void setEndAge(int endAge) {
        this.endAge = endAge;
    }

    public int getEndMth() {
        return endMth;
    }

    public void setEndMth(int endMth) {
        this.endMth = endMth;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public String[] getIndicatorIndexes() {
        return indicatorIndexes;
    }

    public void setIndicatorIndexes(String[] indicatorIndexes) {
        this.indicatorIndexes = indicatorIndexes;
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

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public List getOrgList() {
        return orgList;
    }

    public void setOrgList(List orgList) {
        this.orgList = orgList;
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

    public int getStartAge() {
        return startAge;
    }

    public void setStartAge(int startAge) {
        this.startAge = startAge;
    }

    public int getStartMth() {
        return startMth;
    }

    public void setStartMth(int startMth) {
        this.startMth = startMth;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public List getStateList() {
        return stateList;
    }

    public void setStateList(List stateList) {
        this.stateList = stateList;
    }

    public String getWardCode() {
        return wardCode;
    }

    public void setWardCode(String wardCode) {
        this.wardCode = wardCode;
    }

    public List getWardList() {
        return wardList;
    }

    public void setWardList(List wardList) {
        this.wardList = wardList;
    }

    public String[] getSelectedOrganizationUnits() {
        return selectedOrganizationUnits;
    }

    public void setSelectedOrganizationUnits(String[] selectedOrganizationUnits) {
        this.selectedOrganizationUnits = selectedOrganizationUnits;
    }

    
    
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        actionName=null;
        indicatorIndexes=null;
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
