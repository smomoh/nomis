/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomis.chart;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author smomoh
 */
public class Chart implements Serializable
{
    private String stateCode;
    private String lgaCode;
    private String cboCode;
    private String communityCode;
    private String orgUnitCode;
    private int orgUnitLevel;
    private String chartId;
    private String chartName;
    private String chartType;
    private String series;
    private String filter;
    private String categoryOption;
    private String selectedOrgUnits;
    private String selectedIndicators;
    private String selectedPeriods;
    private String periodType;
    private String startDate;
    private String endDate;
    private String labelVisible;
    private String dateCreated;
    private String lastModifiedDate;
    private List chartValueList;

    public String getCboCode() {
        return cboCode;
    }

    public void setCboCode(String cboCode) {
        this.cboCode = cboCode;
    }

    public String getChartId() {
        return chartId;
    }

    public void setChartId(String chartId) {
        this.chartId = chartId;
    }

    public String getChartName() {
        return chartName;
    }

    public void setChartName(String chartName) {
        this.chartName = chartName;
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

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getLabelVisible() {
        return labelVisible;
    }

    public void setLabelVisible(String labelVisible) {
        this.labelVisible = labelVisible;
    }

    public String getLgaCode() {
        return lgaCode;
    }

    public void setLgaCode(String lgaCode) {
        this.lgaCode = lgaCode;
    }

    public String getPeriodType() {
        return periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
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

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getSelectedIndicators() {
        return selectedIndicators;
    }

    public void setSelectedIndicators(String selectedIndicators) {
        this.selectedIndicators = selectedIndicators;
    }

    public String getSelectedOrgUnits() {
        return selectedOrgUnits;
    }

    public void setSelectedOrgUnits(String selectedOrgUnits) {
        this.selectedOrgUnits = selectedOrgUnits;
    }

    public String getSelectedPeriods() {
        return selectedPeriods;
    }

    public void setSelectedPeriods(String selectedPeriods) {
        this.selectedPeriods = selectedPeriods;
    }

    public String getOrgUnitCode() {
        return orgUnitCode;
    }

    public void setOrgUnitCode(String orgUnitCode) {
        this.orgUnitCode = orgUnitCode;
    }

    public int getOrgUnitLevel() {
        return orgUnitLevel;
    }

    public void setOrgUnitLevel(int orgUnitLevel) {
        this.orgUnitLevel = orgUnitLevel;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getCategoryOption() {
        return categoryOption;
    }

    public void setCategoryOption(String categoryOption) {
        this.categoryOption = categoryOption;
    }

    public List getChartValueList() {
        return chartValueList;
    }

    public void setChartValueList(List chartValueList) {
        this.chartValueList = chartValueList;
    }
    
}
