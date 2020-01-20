/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author smomoh
 */
public class SummaryStatisticsReportForm extends org.apache.struts.action.ActionForm {
    
    private String sumstatPerMth_state;
    private String sumstatPerMth_lga;
    private String[] lgas;
    private String sumstatPerMth_cbo;
    private String orgUnitGroupId;
    private String ward;
    private String enrollmentStartMth;
    private String enrollmentStartYear;
    private String enrollmentEndMth;
    private String enrollmentEndYear;
    private String serviceStartMth;
    private String serviceStartYear;
    private String serviceEndMth;
    private String serviceEndYear;
    private String ovcStartAge;
    private String ovcEndAge;
    private String actionName=null;
    private String chkReportType;
    private int sumstat_month;
    private int sumstat_year;
    private String reportType;
    private String partnerCode;
    private String[] indicatorIndexes;

    public SummaryStatisticsReportForm() {
        super();
        // TODO Auto-generated constructor stub
    }
    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getChkReportType() {
        return chkReportType;
    }

    public void setChkReportType(String chkReportType) {
        this.chkReportType = chkReportType;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getSumstatPerMth_cbo() {
        return sumstatPerMth_cbo;
    }

    public String getOrgUnitGroupId() {
        return orgUnitGroupId;
    }

    public void setOrgUnitGroupId(String orgUnitGroupId) {
        this.orgUnitGroupId = orgUnitGroupId;
    }

    public void setSumstatPerMth_cbo(String sumstatPerMth_cbo) {
        this.sumstatPerMth_cbo = sumstatPerMth_cbo;
    }

    public String getSumstatPerMth_lga() {
        return sumstatPerMth_lga;
    }

    public void setSumstatPerMth_lga(String sumstatPerMth_lga) {
        this.sumstatPerMth_lga = sumstatPerMth_lga;
    }

    public String getSumstatPerMth_state() {
        return sumstatPerMth_state;
    }

    public void setSumstatPerMth_state(String sumstatPerMth_state) {
        this.sumstatPerMth_state = sumstatPerMth_state;
    }

    public int getSumstat_month() {
        return sumstat_month;
    }

    public void setSumstat_month(int sumstat_month) {
        this.sumstat_month = sumstat_month;
    }

    public int getSumstat_year() {
        return sumstat_year;
    }

    public void setSumstat_year(int sumstat_year) {
        this.sumstat_year = sumstat_year;
    }

    public String getEnrollmentEndMth() {
        return enrollmentEndMth;
    }

    public void setEnrollmentEndMth(String enrollmentEndMth) {
        this.enrollmentEndMth = enrollmentEndMth;
    }

    public String getEnrollmentEndYear() {
        return enrollmentEndYear;
    }

    public void setEnrollmentEndYear(String enrollmentEndYear) {
        this.enrollmentEndYear = enrollmentEndYear;
    }

    public String getEnrollmentStartMth() {
        return enrollmentStartMth;
    }

    public void setEnrollmentStartMth(String enrollmentStartMth) {
        this.enrollmentStartMth = enrollmentStartMth;
    }

    public String getEnrollmentStartYear() {
        return enrollmentStartYear;
    }

    public void setEnrollmentStartYear(String enrollmentStartYear) {
        this.enrollmentStartYear = enrollmentStartYear;
    }

    public String getServiceEndMth() {
        return serviceEndMth;
    }

    public void setServiceEndMth(String serviceEndMth) {
        this.serviceEndMth = serviceEndMth;
    }

    public String getServiceEndYear() {
        return serviceEndYear;
    }

    public void setServiceEndYear(String serviceEndYear) {
        this.serviceEndYear = serviceEndYear;
    }

    public String getServiceStartMth() {
        return serviceStartMth;
    }

    public void setServiceStartMth(String serviceStartMth) {
        this.serviceStartMth = serviceStartMth;
    }

    public String getServiceStartYear() {
        return serviceStartYear;
    }

    public void setServiceStartYear(String serviceStartYear) {
        this.serviceStartYear = serviceStartYear;
    }
    public String getOvcStartAge() {
        return ovcStartAge;
    }

    public void setOvcStartAge(String ovcStartAge) {
        this.ovcStartAge = ovcStartAge;
    }

    public String getOvcEndAge() {
        return ovcEndAge;
    }

    public void setOvcEndAge(String ovcEndAge) {
        this.ovcEndAge = ovcEndAge;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String[] getIndicatorIndexes() {
        return indicatorIndexes;
    }

    public void setIndicatorIndexes(String[] indicatorIndexes) {
        this.indicatorIndexes = indicatorIndexes;
    }

    public String[] getLgas() {
        return lgas;
    }

    public void setLgas(String[] lgas) {
        this.lgas = lgas;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        sumstatPerMth_state="All";
        sumstatPerMth_lga="All";
        sumstatPerMth_cbo="All";
        orgUnitGroupId="All";
        ward="All";
        enrollmentStartMth="All";
        enrollmentStartYear="All";
        enrollmentEndMth="All";
        enrollmentEndYear="All";
        serviceStartMth="All";
        serviceStartYear="All";
        serviceEndMth="All";
        serviceEndYear="All";
        ovcStartAge="0";
        ovcEndAge="24+";
        actionName=null;
        chkReportType="All";
        sumstat_month=0;
        sumstat_year=0;
        reportType="All";
        indicatorIndexes=null;
        lgas=null;
    }
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {
        ActionErrors errors = new ActionErrors();//enrollmentEndDates
        if (getActionName() !=null && getActionName().equalsIgnoreCase("enrollmentEndDates"))
        {
            if((getEnrollmentStartMth()==null || getEnrollmentStartMth().equalsIgnoreCase("All")))
            return errors;
            else if((getEnrollmentStartYear() !=null && !getEnrollmentStartYear().equalsIgnoreCase("All")) && ((getEnrollmentStartMth()==null || getEnrollmentStartMth().equalsIgnoreCase("All"))))
            errors.add("enrollmentStartMth", new ActionMessage("error.summStatReportEnrollmentStartPeriod.required"));
        }//
        else if (getActionName() !=null && getActionName().equalsIgnoreCase("serviceEndDates"))
        {
            if((getServiceStartYear() !=null && !getServiceStartYear().equalsIgnoreCase("All")) && ((getServiceStartMth()==null || getServiceStartMth().equalsIgnoreCase("All"))))
            errors.add("serviceStartMth", new ActionMessage("error.summStatReportServiceStartPeriod.required"));
        }
        else if (getActionName() !=null && getActionName().equalsIgnoreCase("summstatreport"))
        {
            if((getEnrollmentStartYear() !=null && !getEnrollmentStartYear().equalsIgnoreCase("All")) && ((getEnrollmentEndMth()==null || getEnrollmentEndMth().equalsIgnoreCase("All")) || (getEnrollmentEndYear()==null || this.getEnrollmentEndYear().equalsIgnoreCase("All"))))
            errors.add("enrollmentStartYear", new ActionMessage("error.summStatReportEnrollmentEndPeriod.required"));
            else if((getEnrollmentStartYear() !=null && !getEnrollmentStartYear().equalsIgnoreCase("All")) && ((getEnrollmentEndYear()!=null && !getEnrollmentEndYear().equalsIgnoreCase("All"))) )
            {
                if((getEnrollmentStartMth()!=null && !getEnrollmentStartMth().equalsIgnoreCase("All")) && (getEnrollmentEndMth()!=null && !getEnrollmentEndMth().equalsIgnoreCase("All")))
                {

                    try
                    {
                        AppUtility appUtil=new AppUtility();
                        int startMth=appUtil.getMonthAsNumber(getEnrollmentStartMth());
                        int endMth=appUtil.getMonthAsNumber(getEnrollmentEndMth());
                        if(getEnrollmentStartYear().equalsIgnoreCase(getEnrollmentEndYear()) && startMth > endMth )
                        errors.add("enrollmentStartYear", new ActionMessage("error.summStatReportEnrollmentEndPeriod.required"));
                    }
                    catch(NumberFormatException nfe)
                    {
                        return errors;
                    }
                }
            }
            else if(((getServiceStartYear() !=null && !getServiceStartYear().equalsIgnoreCase("All"))) && ((getServiceEndMth()==null || getServiceEndMth().equalsIgnoreCase("All")) || ((getServiceEndYear()==null || getServiceEndYear().equalsIgnoreCase("All")))))
            errors.add("serviceStartYear", new ActionMessage("error.summStatReportServiceEndPeriod.required"));
        }
        return errors;
    }
    
}
