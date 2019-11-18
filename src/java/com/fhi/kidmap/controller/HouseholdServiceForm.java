/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

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
public class HouseholdServiceForm extends org.apache.struts.action.ActionForm {
    
    private String actionName;
    private int caregiverAge;
    private String careiverGender;
    private String caregiverName;
    private String serviceDate;
    private String[] economicStrengtheningServices;
    private String[] educationalServices;
    private String[] healthServices;
    private int id;
    private String lgaCode;
    private String[] nutritionalServices;
    private String orgCode;
    private String[] protectionServices;
    private String[] psychosocialSupportServices;
    private String reasonWithdrawal;
    private int serviceNo;
    private int serialNo;
    private String[] shelterAndCareServices;
    private String stateCode;
    private String hhUniqueId;
    private String volunteerDesignation;
    private String volunteerName;
    private String ward;
    private String currentHivStatus;
    private String newHivStatus;
    private String enrolledInCare;
    private String enrolledOnART;
    private String organizationClientIsReferred;
    private List hivStatusList=new ArrayList();
    private List referralDirectoryList=new ArrayList();

    public HouseholdServiceForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public int getCaregiverAge() {
        return caregiverAge;
    }

    public void setCaregiverAge(int caregiverAge) {
        this.caregiverAge = caregiverAge;
    }

    public String getCaregiverName() {
        return caregiverName;
    }

    public void setCaregiverName(String caregiverName) {
        this.caregiverName = caregiverName;
    }

    public String getCareiverGender() {
        return careiverGender;
    }

    public void setCareiverGender(String careiverGender) {
        this.careiverGender = careiverGender;
    }

    public String[] getEconomicStrengtheningServices() {
        return economicStrengtheningServices;
    }

    public void setEconomicStrengtheningServices(String[] economicStrengtheningServices) {
        this.economicStrengtheningServices = economicStrengtheningServices;
    }

    public String[] getEducationalServices() {
        return educationalServices;
    }

    public void setEducationalServices(String[] educationalServices) {
        this.educationalServices = educationalServices;
    }

    public String[] getHealthServices() {
        return healthServices;
    }

    public void setHealthServices(String[] healthServices) {
        this.healthServices = healthServices;
    }

    public String getHhUniqueId() {
        return hhUniqueId;
    }

    public void setHhUniqueId(String hhUniqueId) {
        this.hhUniqueId = hhUniqueId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLgaCode() {
        return lgaCode;
    }

    public void setLgaCode(String lgaCode) {
        this.lgaCode = lgaCode;
    }

    public String[] getNutritionalServices() {
        return nutritionalServices;
    }

    public void setNutritionalServices(String[] nutritionalServices) {
        this.nutritionalServices = nutritionalServices;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String[] getProtectionServices() {
        return protectionServices;
    }

    public void setProtectionServices(String[] protectionServices) {
        this.protectionServices = protectionServices;
    }

    public String[] getPsychosocialSupportServices() {
        return psychosocialSupportServices;
    }

    public void setPsychosocialSupportServices(String[] psychosocialSupportServices) {
        this.psychosocialSupportServices = psychosocialSupportServices;
    }

    public String getReasonWithdrawal() {
        return reasonWithdrawal;
    }

    public void setReasonWithdrawal(String reasonWithdrawal) {
        this.reasonWithdrawal = reasonWithdrawal;
    }

    public int getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }

    public int getServiceNo() {
        return serviceNo;
    }

    public void setServiceNo(int serviceNo) {
        this.serviceNo = serviceNo;
    }

    public String[] getShelterAndCareServices() {
        return shelterAndCareServices;
    }

    public void setShelterAndCareServices(String[] shelterAndCareServices) {
        this.shelterAndCareServices = shelterAndCareServices;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getVolunteerDesignation() {
        return volunteerDesignation;
    }

    public void setVolunteerDesignation(String volunteerDesignation) {
        this.volunteerDesignation = volunteerDesignation;
    }

    public String getVolunteerName() {
        return volunteerName;
    }

    public void setVolunteerName(String volunteerName) {
        this.volunteerName = volunteerName;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getEnrolledInCare() {
        return enrolledInCare;
    }

    public void setEnrolledInCare(String enrolledInCare) {
        this.enrolledInCare = enrolledInCare;
    }

    public List getHivStatusList() {
        return hivStatusList;
    }

    public void setHivStatusList(List hivStatusList) {
        this.hivStatusList = hivStatusList;
    }

    public String getCurrentHivStatus() {
        return currentHivStatus;
    }

    public void setCurrentHivStatus(String currentHivStatus) {
        this.currentHivStatus = currentHivStatus;
    }

    public String getNewHivStatus() {
        return newHivStatus;
    }

    public void setNewHivStatus(String newHivStatus) {
        this.newHivStatus = newHivStatus;
    }

    public String getOrganizationClientIsReferred() {
        return organizationClientIsReferred;
    }

    public void setOrganizationClientIsReferred(String organizationClientIsReferred) {
        this.organizationClientIsReferred = organizationClientIsReferred;
    }

    public List getReferralDirectoryList() {
        return referralDirectoryList;
    }

    public void setReferralDirectoryList(List referralDirectoryList) {
        this.referralDirectoryList = referralDirectoryList;
    }

    public String getEnrolledOnART() {
        return enrolledOnART;
    }

    public void setEnrolledOnART(String enrolledOnART) {
        this.enrolledOnART = enrolledOnART;
    }

    private boolean serviceAccessed()
    {
        if(getEconomicStrengtheningServices()==null && getEducationalServices()==null
          && getHealthServices()==null && getNutritionalServices()==null && getProtectionServices()==null
          && getPsychosocialSupportServices()==null && getShelterAndCareServices()==null && getReasonWithdrawal()==null)
        return false;
        return true;
    }
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        actionName=null;
        caregiverAge=0;
        careiverGender=null;
        caregiverName=null;
        serviceDate=null;
        economicStrengtheningServices=null;
        educationalServices=null;
        healthServices=null;
        id=0;
        caregiverAge=0;
        careiverGender=null;
        nutritionalServices=null;
        //orgCode=null;
        protectionServices=null;
        psychosocialSupportServices=null;
        reasonWithdrawal=null;
        serviceNo=1;
        serialNo=0;
        shelterAndCareServices=null;
        enrolledInCare="No";
        enrolledOnART="No";
        organizationClientIsReferred=null;
        hhUniqueId=null;
        volunteerDesignation=null;
        volunteerName=null;
        ward=null;
        currentHivStatus=null;
        newHivStatus=null;
    }
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        //System.err.println("getActionName() in hhservice form bean is "+getActionName());
        if(getActionName()==null || getActionName().equalsIgnoreCase("delete") || getActionName().equalsIgnoreCase("caregiverList")|| getActionName().equalsIgnoreCase("caregiverDetails")|| getActionName().equalsIgnoreCase("cboList") || getActionName().equalsIgnoreCase("hhserviceDetails"))
        return errors;
        else if(getHhUniqueId() ==null)
        errors.add("hhUniqueId", new ActionMessage("errors.hhuniqueId.required"));
        else if(this.getCaregiverName() ==null)
        errors.add("caregiverName", new ActionMessage("errors.caregiverName.required"));
        else if( getServiceDate()==null ||  getServiceDate().indexOf("/")==-1)
        errors.add("serviceDate", new ActionMessage("errors.serviceDate.required"));
        else if( getServiceDate()!=null &&  getServiceDate().indexOf("/")!=-1)
        {
            String[] dateArray=getServiceDate().split("/");
            if(dateArray.length != 3)
            errors.add("serviceDate", new ActionMessage("errors.serviceDate.required"));
        }
        if(errors.isEmpty() && !serviceAccessed())
        errors.add("healthServices", new ActionMessage("errors.services.required"));
        return errors;
    }
}
