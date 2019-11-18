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
public class CaregiverForm extends org.apache.struts.action.ActionForm {
    
    private String actionName;
    private int serialNo;
    private String caregiverId;
    private String hhUniqueId;
    private String caregiverFirstname;
    private String caregiverLastName;
    private String caregiverGender;
    private int caregiverAge;
    private int currentAge;
    private String caregiverPhone;
    private String caregiverAddress;
    private String caregiverOccupation;
    private String caregiverMaritalStatus;
    private String currentHivStatus;
    private String newHivStatus;
    private String dateOfEnrollment;
    private String dateModified;
    private String householdhead;
    private String stateCode;
    private String lgaCode;
    private String orgCode;
    private String ward;
    private String formType;
    private String savebtn;
    private List caregiverList=new ArrayList();
    private List lgaList=new ArrayList();
    private List cboList=new ArrayList();
    private List hivStatusList=new ArrayList();
    private String enrolledInCare;
    private String enrolledOnART;
    private String organizationClientIsReferred;
    private List referralDirectoryList=new ArrayList();
    /**
     *
     */
    public CaregiverForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getCaregiverAddress() {
        return caregiverAddress;
    }

    public void setCaregiverAddress(String caregiverAddress) {
        this.caregiverAddress = caregiverAddress;
    }

    public int getCaregiverAge() {
        return caregiverAge;
    }

    public void setCaregiverAge(int caregiverAge) {
        this.caregiverAge = caregiverAge;
    }

    public String getCaregiverFirstname() {
        return caregiverFirstname;
    }

    public void setCaregiverFirstname(String caregiverFirstname) {
        this.caregiverFirstname = caregiverFirstname;
    }

    public String getCaregiverGender() {
        return caregiverGender;
    }

    public void setCaregiverGender(String caregiverGender) {
        this.caregiverGender = caregiverGender;
    }

    public String getCaregiverId() {
        return caregiverId;
    }

    public void setCaregiverId(String caregiverId) {
        this.caregiverId = caregiverId;
    }

    public String getCaregiverLastName() {
        return caregiverLastName;
    }

    public void setCaregiverLastName(String caregiverLastName)
    {
        this.caregiverLastName = caregiverLastName;
    }

    public List getCaregiverList() {
        return caregiverList;
    }

    public void setCaregiverList(List caregiverList) {
        this.caregiverList = caregiverList;
    }

    public String getCaregiverMaritalStatus() {
        return caregiverMaritalStatus;
    }

    public void setCaregiverMaritalStatus(String caregiverMaritalStatus) {
        this.caregiverMaritalStatus = caregiverMaritalStatus;
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
    
    public String getCaregiverOccupation() {
        return caregiverOccupation;
    }

    public void setCaregiverOccupation(String caregiverOccupation) {
        this.caregiverOccupation = caregiverOccupation;
    }

    public String getCaregiverPhone() {
        return caregiverPhone;
    }

    public void setCaregiverPhone(String caregiverPhone) {
        this.caregiverPhone = caregiverPhone;
    }

    public List getCboList() {
        return cboList;
    }

    public void setCboList(List cboList) {
        this.cboList = cboList;
    }

    public int getCurrentAge() {
        return currentAge;
    }

    public void setCurrentAge(int currentAge) {
        this.currentAge = currentAge;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getDateOfEnrollment() {
        return dateOfEnrollment;
    }

    public void setDateOfEnrollment(String dateOfEnrollment) {
        this.dateOfEnrollment = dateOfEnrollment;
    }

    public String getHhUniqueId() {
        return hhUniqueId;
    }

    public void setHhUniqueId(String hhUniqueId) {
        this.hhUniqueId = hhUniqueId;
    }

    public String getHouseholdhead() {
        return householdhead;
    }

    public void setHouseholdhead(String householdhead) {
        this.householdhead = householdhead;
    }

    /*public boolean isHouseholdhead() {
        return householdhead;
    }

    public void setHouseholdhead(boolean householdhead) {
        this.householdhead = householdhead;
    }*/

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

    public List getHivStatusList() {
        return hivStatusList;
    }

    public void setHivStatusList(List hivStatusList) {
        this.hivStatusList = hivStatusList;
    }

    public int getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public String getSavebtn() {
        return savebtn;
    }

    public void setSavebtn(String savebtn) {
        this.savebtn = savebtn;
    }

    public String getEnrolledInCare() {
        return enrolledInCare;
    }

    public void setEnrolledInCare(String enrolledInCare) {
        this.enrolledInCare = enrolledInCare;
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

    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        actionName=null;
        serialNo=0;
        caregiverFirstname=null;
        caregiverLastName=null;
        caregiverGender=null;
        caregiverAge=0;
        caregiverPhone=null;
        caregiverAddress=null;
        caregiverOccupation=null;
        caregiverMaritalStatus=null;
        currentHivStatus="HIV Unknown";
        newHivStatus=null;
        householdhead="0";
        enrolledInCare="No";
        enrolledOnART="No";
        formType=null;
        caregiverList=new ArrayList();
    }
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if(getActionName() ==null || (getActionName().equalsIgnoreCase("refresh")) || (getActionName().equalsIgnoreCase("cboList")) || (getActionName().equalsIgnoreCase("caregiverList")) || getActionName().equalsIgnoreCase("caregiverDetails") || (getActionName().equalsIgnoreCase("delete")))
        {
            return errors;
        }
        else if(getLgaCode()==null || getLgaCode().length() < 1)
        errors.add("lga", new ActionMessage("error.lga.required"));
        else if(getOrgCode()==null || getOrgCode().length() < 1)
        errors.add("cbo", new ActionMessage("error.cbo.required"));

        else if(getHhUniqueId()==null || getHhUniqueId().length() < 1)
        errors.add("hhUniqueId", new ActionMessage("error.hhUniqueId.required"));
        else if(getCaregiverFirstname()==null || getCaregiverFirstname().length() < 1)
        errors.add("caregiverFirstname", new ActionMessage("error.caregiverFirstname.required"));
        else if(getCaregiverLastName()==null || getCaregiverLastName().length() < 1)
        errors.add("caregiverLastName", new ActionMessage("error.caregiverLastName.required"));

        else if(getCaregiverGender()==null || getCaregiverGender().length() < 1)
        errors.add("caregiverGender", new ActionMessage("error.caregiverGender.required"));
        else if(getCaregiverAge()<10)
        errors.add("caregiverAAge", new ActionMessage("error.caregiverAAge.required"));
        else if(getCaregiverAddress()==null || getCaregiverAddress().length() < 1)
        errors.add("caregiverAddress", new ActionMessage("error.caregiverAddress.required"));
        else if(getCaregiverMaritalStatus()==null || getCaregiverMaritalStatus().length() < 1)
        errors.add("caregiverMaritalStatus", new ActionMessage("error.caregiverMaritalStatus.required"));
        
        return errors;
    }
}
