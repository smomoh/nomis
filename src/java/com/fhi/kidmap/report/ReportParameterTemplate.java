/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report;

import com.fhi.nomis.OperationsManagement.ReportPeriod;
import com.fhi.nomis.nomisutils.ReportPeriodManager;

/**
 *
 * @author smomoh
 */
public class ReportParameterTemplate 
{
    private String stateCode;
    private String lgaCode;
    private String cboCode;
    private String wardCode;
    private String partnerCode;
    private String stateName;
    private String lgaName;
    private String cboName;
    private String wardName;
    private String partnerName;
    
    private int startMonth;
    private int startYear;
    private int endMonth;
    private int endYear;
    private int startAge;
    private int endAge;
    private ReportPeriod financialYear;
    
    private ReportPeriod reportPeriod;

    public String getCboCode() {
        return cboCode;
    }

    public void setCboCode(String cboCode) {
        this.cboCode = cboCode;
    }

    public String getCboName() {
        return cboName;
    }

    public void setCboName(String cboName) {
        this.cboName = cboName;
    }

    public int getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(int endMonth) {
        this.endMonth = endMonth;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public String getLgaCode() {
        return lgaCode;
    }

    public void setLgaCode(String lgaCode) {
        this.lgaCode = lgaCode;
    }

    public String getLgaName() {
        return lgaName;
    }

    public void setLgaName(String lgaName) {
        this.lgaName = lgaName;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public ReportPeriod getReportPeriod() 
    {
        if(reportPeriod==null)
        reportPeriod=new ReportPeriod();
        return reportPeriod;
    }

    public void setReportPeriod(ReportPeriod reportPeriod) {
        this.reportPeriod = reportPeriod;
    }

    public int getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(int startMonth) {
        this.startMonth = startMonth;
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

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getWardCode() {
        return wardCode;
    }

    public void setWardCode(String wardCode) {
        this.wardCode = wardCode;
    }

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public int getEndAge() {
        return endAge;
    }

    public void setEndAge(int endAge) {
        this.endAge = endAge;
    }

    public int getStartAge() {
        return startAge;
    }

    public void setStartAge(int startAge) {
        this.startAge = startAge;
    }

    public ReportPeriod getFinancialYear() 
    {
        if(financialYear==null)
        financialYear=ReportPeriodManager.getStartOfFinancialYear(getStartMonth(), getStartYear());
        return financialYear;
    }

    public void setFinancialYear(ReportPeriod financialYear) {
        this.financialYear = financialYear;
    }
    
}
