/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomis.chart.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author smomoh
 */
public class NewChartForm extends org.apache.struts.action.ActionForm {
    
    private String actionName;
    private String state;
    private String lga;
    private String cboCode;
    private String communityCode;
    private String chartName;
    private String startDate;
    private String endDate;
    private String periodType;
    private String chartType;
    private String startDay;
    private String startMth;
    private String startYr;
    private String endDay;
    private String endMth;
    private String endYr;
    private String year;
    private String series;
    private String filter;
    private String category;
    private boolean labelVisible;
    private String singleOrgUnitId;
    private String singleIndicatorId;
    private String selectedSinglePeriod;
    private String[] indicatorIds;
    private String[] selectedMonths;
    private String[] selectedOrganizationUnit;
    
    public NewChartForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getChartType() {
        return chartType;
    }

    public void setChartType(String chartType) {
        this.chartType = chartType;
    }

    public String getCommunityCode() {
        return communityCode;
    }

    public void setCommunityCode(String communityCode) {
        this.communityCode = communityCode;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

    public String getEndMth() {
        return endMth;
    }

    public void setEndMth(String endMth) {
        this.endMth = endMth;
    }

    public String getEndYr() {
        return endYr;
    }

    public void setEndYr(String endYr) {
        this.endYr = endYr;
    }

    public String getCboCode() {
        return cboCode;
    }

    public void setCboCode(String cboCode) {
        this.cboCode = cboCode;
    }
    
    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getSingleOrgUnitId() {
        return singleOrgUnitId;
    }

    public void setSingleOrgUnitId(String singleOrgUnitId) {
        this.singleOrgUnitId = singleOrgUnitId;
    }

    public String getSingleIndicatorId() {
        return singleIndicatorId;
    }

    public void setSingleIndicatorId(String singleIndicatorId) {
        this.singleIndicatorId = singleIndicatorId;
    }
    
    public String[] getIndicatorIds() {
        return indicatorIds;
    }

    public void setIndicatorIds(String[] indicatorIds) {
        this.indicatorIds = indicatorIds;
    }

    public boolean isLabelVisible() {
        return labelVisible;
    }

    public void setLabelVisible(boolean labelVisible) {
        this.labelVisible = labelVisible;
    }

    public String getLga() {
        return lga;
    }

    public void setLga(String lga) {
        this.lga = lga;
    }

    public String getPeriodType() {
        return periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public String getSelectedSinglePeriod() {
        return selectedSinglePeriod;
    }

    public void setSelectedSinglePeriod(String selectedSinglePeriod) {
        this.selectedSinglePeriod = selectedSinglePeriod;
    }

    public String[] getSelectedMonths() {
        return selectedMonths;
    }

    public void setSelectedMonths(String[] selectedMonths) {
        this.selectedMonths = selectedMonths;
    }

    public String[] getSelectedOrganizationUnit() {
        return selectedOrganizationUnit;
    }

    public void setSelectedOrganizationUnit(String[] selectedOrganizationUnit) {
        this.selectedOrganizationUnit = selectedOrganizationUnit;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getStartMth() {
        return startMth;
    }

    public void setStartMth(String startMth) {
        this.startMth = startMth;
    }

    public String getStartYr() {
        return startYr;
    }

    public void setStartYr(String startYr) {
        this.startYr = startYr;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getChartName() {
        return chartName;
    }

    public void setChartName(String chartName) {
        this.chartName = chartName;
    }
    
@Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        actionName=null;
        chartName=null;
    }
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if(this.getActionName() !=null && this.getActionName().equalsIgnoreCase("saveChart"))
        {
            if(getChartName()==null || getChartName().length()<1)
            errors.add("chartName", new ActionMessage("error.chartName.required"));
        }
        
        return errors;
    }
}
