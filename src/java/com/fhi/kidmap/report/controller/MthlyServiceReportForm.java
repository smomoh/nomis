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
public class MthlyServiceReportForm extends org.apache.struts.action.ActionForm {
    
    private String state;
    private String lga;
    private String cbo;
    private String ward;
    private String partnerCode;
    private String serviceDate;
    private int serviceMonth;
    private int serviceYear;
    private int serviceMonth2;
    private int serviceYear2;
    private int startAge;
    private int endAge;
    private String actionName;
    private String reportFilter;
    private String chkReportType;
    private String[] servicesToChange;
    private List stateList=new ArrayList();
    private List lgaList=new ArrayList();
    private List cboList=new ArrayList();
    private List wardList=new ArrayList();
    

    public String getCbo() {
        return cbo;
    }

    public void setCbo(String cbo) {
        this.cbo = cbo;
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

    public int getServiceMonth() {
        return serviceMonth;
    }

    public void setServiceMonth(int serviceMonth) {
        this.serviceMonth = serviceMonth;
    }

    public int getServiceYear() {
        return serviceYear;
    }

    public void setServiceYear(int serviceYear) {
        this.serviceYear = serviceYear;
    }

    public int getServiceMonth2() {
        return serviceMonth2;
    }

    public void setServiceMonth2(int serviceMonth2) {
        this.serviceMonth2 = serviceMonth2;
    }

    public int getServiceYear2() {
        return serviceYear2;
    }

    public void setServiceYear2(int serviceYear2) {
        this.serviceYear2 = serviceYear2;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public List getCboList() {
        return cboList;
    }

    public void setCboList(List cboList) {
        this.cboList = cboList;
    }

    public String getReportFilter() {
        return reportFilter;
    }

    public void setReportFilter(String reportFilter) {
        this.reportFilter = reportFilter;
    }

    public List getLgaList() {
        return lgaList;
    }

    public void setLgaList(List lgaList) {
        this.lgaList = lgaList;
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
    
    
    public MthlyServiceReportForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String[] getServicesToChange() {
        return servicesToChange;
    }

    public void setServicesToChange(String[] servicesToChange) {
        this.servicesToChange = servicesToChange;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        serviceMonth=0;
        serviceYear=0;
        serviceMonth2=0;
        serviceYear2=0;
        actionName=null;
        startAge=0;
        endAge=24;
        chkReportType="off";
        servicesToChange=null;
        serviceDate=null;
    }
    
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        
        if(getServiceYear()>getServiceYear2())
        errors.add("serviceYear", new ActionMessage("serviceYear.greater.serviceYear2"));
        else if(getServiceYear()==getServiceYear2() && getServiceMonth() > getServiceMonth2())
        errors.add("serviceMonth", new ActionMessage("serviceMonth.greater.serviceMonth2"));
        else if(getStartAge() > getEndAge())
        errors.add("startAge", new ActionMessage("startAge.greater.endAge"));
        
        return errors;
    }

}
