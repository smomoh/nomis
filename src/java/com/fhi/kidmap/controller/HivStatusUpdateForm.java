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
public class HivStatusUpdateForm extends org.apache.struts.action.ActionForm {
    
    private String actionName;
    private int hhSerialNo;
    private String beneficiaryId;
    private String hhUniqueId;
    private String ovcName;
    private String gender;
    private String beneficiaryType;
    private int age;
    private String ageUnit;
    private String phone;
    private String address;
    private String currentHivStatus;
    private String newHivStatus;
    private String enrolledInCare;
    private String enrolledOnART;
    private String organizationClientIsReferred;
    private String dateOfNewStatus;
    private String dateModified;
    private String stateCode;
    private String stateName;
    private String lgaCode;
    private String orgCode;
    private String ward;
    private String treatmentId;
    private String dateEnrolledOnTreatment;
    private List beneficiaryList=new ArrayList();
    private List lgaList=new ArrayList();
    private List cboList=new ArrayList();
    private List hivStatusList=new ArrayList();
    private List referralDirectoryList=new ArrayList();
    public HivStatusUpdateForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAgeUnit() {
        return ageUnit;
    }

    public void setAgeUnit(String ageUnit) {
        this.ageUnit = ageUnit;
    }

    public String getBeneficiaryType() {
        return beneficiaryType;
    }

    public void setBeneficiaryType(String beneficiaryType) {
        this.beneficiaryType = beneficiaryType;
    }

    public List getCboList() {
        return cboList;
    }

    public void setCboList(List cboList) {
        this.cboList = cboList;
    }

    public String getCurrentHivStatus() {
        return currentHivStatus;
    }

    public void setCurrentHivStatus(String currentHivStatus) {
        this.currentHivStatus = currentHivStatus;
    }
    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getDateOfNewStatus() {
        return dateOfNewStatus;
    }

    public void setDateOfNewStatus(String dateOfNewStatus) {
        this.dateOfNewStatus = dateOfNewStatus;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getHhSerialNo() {
        return hhSerialNo;
    }

    public void setHhSerialNo(int hhSerialNo) {
        this.hhSerialNo = hhSerialNo;
    }

    public String getHhUniqueId() {
        return hhUniqueId;
    }

    public void setHhUniqueId(String hhUniqueId) {
        this.hhUniqueId = hhUniqueId;
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

    public String getNewHivStatus() {
        return newHivStatus;
    }

    public void setNewHivStatus(String newHivStatus) {
        this.newHivStatus = newHivStatus;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getEnrolledInCare() {
        return enrolledInCare;
    }

    public void setEnrolledInCare(String enrolledInCare) {
        this.enrolledInCare = enrolledInCare;
    }

    public String getEnrolledOnART() {
        return enrolledOnART;
    }

    public void setEnrolledOnART(String enrolledOnART) {
        this.enrolledOnART = enrolledOnART;
    }

    public String getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(String beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public List getBeneficiaryList() {
        return beneficiaryList;
    }

    public void setBeneficiaryList(List beneficiaryList) {
        this.beneficiaryList = beneficiaryList;
    }

    public String getOvcName() {
        return ovcName;
    }

    public void setOvcName(String ovcName) {
        this.ovcName = ovcName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public List getHivStatusList() {
        return hivStatusList;
    }

    public void setHivStatusList(List hivStatusList) {
        this.hivStatusList = hivStatusList;
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

    public String getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(String treatmentId) {
        this.treatmentId = treatmentId;
    }

    public String getDateEnrolledOnTreatment() {
        return dateEnrolledOnTreatment;
    }

    public void setDateEnrolledOnTreatment(String dateEnrolledOnTreatment) {
        this.dateEnrolledOnTreatment = dateEnrolledOnTreatment;
    }
    
@Override
public void reset(ActionMapping mapping, HttpServletRequest request)
{
    actionName=null;
    gender=null;
    ovcName=null;
    address=null;
    hhSerialNo=0;
    hhUniqueId=null;
    age=0;
    ageUnit=null;
    phone=null;
    currentHivStatus=null;
    newHivStatus=null;
    enrolledInCare=null;
    dateOfNewStatus=null;
    treatmentId=null;
    dateEnrolledOnTreatment=null;
    beneficiaryList=new ArrayList();
}
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) 
    {
        ActionErrors errors = new ActionErrors();
        
            if(getActionName()==null || getActionName().equalsIgnoreCase("cboList") || getActionName().equalsIgnoreCase("beneficiaryList") || getActionName().equalsIgnoreCase("baselineDetails"))
            return errors;
            else if(getBeneficiaryId()==null || getBeneficiaryId().length() <2)
            errors.add("ovcId", new ActionMessage("erros.ovcId.required"));
            else if(getNewHivStatus().equalsIgnoreCase("none"))
            errors.add("newHivStatus", new ActionMessage("erros.newHivStatus.required"));
            else if(getDateOfNewStatus()==null || getDateOfNewStatus().indexOf("/")==-1)
            errors.add("dateOfNewStatus", new ActionMessage("erros.dateOfNewStatus.required"));
            // TODO: add 'error.name.required' key to your resources
       
        return errors;
    }
}
