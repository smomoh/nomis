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
public class MultipleReportForm extends org.apache.struts.action.ActionForm {
    
    private String state;
    private String lga;
    private String cbo;
    private String ward;
    private String partnerCode;
    private int serviceMonth;
    private int serviceYear;
    private int serviceMonth2;
    private int serviceYear2;
    private int cr2serviceMonth;
    private int cr2serviceYear;
    private int cr2serviceMonth2;
    private int cr2serviceYear2;
    private int startAge;
    private int endAge;
    private String actionName;
    private String chkReportType;
    private List stateList=new ArrayList();
    private List lgaList=new ArrayList();
    private List cboList=new ArrayList();
    private List wardList=new ArrayList();
    private String criteria1;
    private String criteria2;
    private String criteria3;
    private String criteria4;
    private String[] selectedRecords;
    private String[] healthServices;
    private String[] economicServices;
    private String[] nutritionalServices;
    private String[] educationServices;
    private String[] protectionServices;
    private String[] psychosocialServices;
    private String[] shelterServices;
    private String serviceDate;
    private String providerName;
    private String reasonWithdrawal;
    public MultipleReportForm() {
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

    public List getCboList() {
        return cboList;
    }

    public void setCboList(List cboList) {
        this.cboList = cboList;
    }

    public String getChkReportType() {
        return chkReportType;
    }

    public void setChkReportType(String chkReportType) {
        this.chkReportType = chkReportType;
    }

    public int getEndAge() {
        return endAge;
    }

    public void setEndAge(int endAge) {
        this.endAge = endAge;
    }

    public String getLga() {
        return lga;
    }

    public void setLga(String lga) {
        this.lga = lga;
    }

    public List getLgaList() {
        return lgaList;
    }

    public void setLgaList(List lgaList) {
        this.lgaList = lgaList;
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

    public int getServiceMonth2() {
        return serviceMonth2;
    }

    public void setServiceMonth2(int serviceMonth2) {
        this.serviceMonth2 = serviceMonth2;
    }

    public int getServiceYear() {
        return serviceYear;
    }

    public void setServiceYear(int serviceYear) {
        this.serviceYear = serviceYear;
    }

    public int getServiceYear2() {
        return serviceYear2;
    }

    public void setServiceYear2(int serviceYear2) {
        this.serviceYear2 = serviceYear2;
    }

    public int getCr2serviceMonth() {
        return cr2serviceMonth;
    }

    public void setCr2serviceMonth(int cr2serviceMonth) {
        this.cr2serviceMonth = cr2serviceMonth;
    }

    public int getCr2serviceMonth2() {
        return cr2serviceMonth2;
    }

    public void setCr2serviceMonth2(int cr2serviceMonth2) {
        this.cr2serviceMonth2 = cr2serviceMonth2;
    }

    public int getCr2serviceYear() {
        return cr2serviceYear;
    }

    public void setCr2serviceYear(int cr2serviceYear) {
        this.cr2serviceYear = cr2serviceYear;
    }

    public int getCr2serviceYear2() {
        return cr2serviceYear2;
    }

    public void setCr2serviceYear2(int cr2serviceYear2) {
        this.cr2serviceYear2 = cr2serviceYear2;
    }

    public String getCriteria1() {
        return criteria1;
    }

    public void setCriteria1(String criteria1) {
        this.criteria1 = criteria1;
    }

    public String getCriteria2() {
        return criteria2;
    }

    public void setCriteria2(String criteria2) {
        this.criteria2 = criteria2;
    }

    public String getCriteria3() {
        return criteria3;
    }

    public void setCriteria3(String criteria3) {
        this.criteria3 = criteria3;
    }

    public String getCriteria4() {
        return criteria4;
    }

    public void setCriteria4(String criteria4) {
        this.criteria4 = criteria4;
    }

    public int getStartAge() {
        return startAge;
    }

    public void setStartAge(int startAge) {
        this.startAge = startAge;
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

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public List getWardList() {
        return wardList;
    }

    public void setWardList(List wardList) {
        this.wardList = wardList;
    }

    public String[] getSelectedRecords() {
        return selectedRecords;
    }

    public void setSelectedRecords(String[] selectedRecords) {
        this.selectedRecords = selectedRecords;
    }

    public String[] getHealthServices() {
        return healthServices;
    }

    public void setHealthServices(String[] healthServices) {
        this.healthServices = healthServices;
    }

    public String[] getEconomicServices() {
        return economicServices;
    }

    public void setEconomicServices(String[] economicServices) {
        this.economicServices = economicServices;
    }

    public String[] getEducationServices() {
        return educationServices;
    }

    public void setEducationServices(String[] educationServices) {
        this.educationServices = educationServices;
    }

    public String[] getNutritionalServices() {
        return nutritionalServices;
    }

    public void setNutritionalServices(String[] nutritionalServices) {
        this.nutritionalServices = nutritionalServices;
    }

    public String[] getProtectionServices() {
        return protectionServices;
    }

    public void setProtectionServices(String[] protectionServices) {
        this.protectionServices = protectionServices;
    }

    public String[] getPsychosocialServices() {
        return psychosocialServices;
    }

    public void setPsychosocialServices(String[] psychosocialServices) {
        this.psychosocialServices = psychosocialServices;
    }

    public String[] getShelterServices() {
        return shelterServices;
    }

    public void setShelterServices(String[] shelterServices) {
        this.shelterServices = shelterServices;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getReasonWithdrawal() {
        return reasonWithdrawal;
    }

    public void setReasonWithdrawal(String reasonWithdrawal) {
        this.reasonWithdrawal = reasonWithdrawal;
    }
    
public void reset(ActionMapping mapping, HttpServletRequest request)
{
    actionName=null;
    selectedRecords=null;
    providerName=null;
    serviceDate=null;
    healthServices=null;
    economicServices=null;
    nutritionalServices=null;
    educationServices=null;
    protectionServices=null;
    psychosocialServices=null;
    shelterServices=null;
    endAge=24;
    reasonWithdrawal=null;
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
