/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author smomoh
 */
public class GenderNormCohortAttendanceForm extends org.apache.struts.action.ActionForm {
    
    private String state;
    private String lga;
    private String ward;
    private String cboName;
    private String completedbyCbo;
    private String dateOfAttendance;
    private String dateEnrollment;
    private String actionName;
    private String clientId;
    private String ovcSno;
    private String hhSerialNo;
    private String hhUniqueId;
    private String surname;
    private String otherNames;
    private String gender;
    private int age;
    private String ageUnit;
    private String address;
    private String phone;
    private String caregiverName;
    private int recordId;
    private String targetGroup;
    private String pcvName;

    public GenderNormCohortAttendanceForm() {
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

    public String getCaregiverName() {
        return caregiverName;
    }

    public void setCaregiverName(String caregiverName) {
        this.caregiverName = caregiverName;
    }

    public String getCboName() {
        return cboName;
    }

    public void setCboName(String cboName) {
        this.cboName = cboName;
    }

    public String getCompletedbyCbo() {
        return completedbyCbo;
    }

    public void setCompletedbyCbo(String completedbyCbo) {
        this.completedbyCbo = completedbyCbo;
    }

    public String getDateEnrollment() {
        return dateEnrollment;
    }

    public void setDateEnrollment(String dateEnrollment) {
        this.dateEnrollment = dateEnrollment;
    }

    public String getDateOfAttendance() {
        return dateOfAttendance;
    }

    public void setDateOfAttendance(String dateOfAttendance) {
        this.dateOfAttendance = dateOfAttendance;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHhSerialNo() {
        return hhSerialNo;
    }

    public void setHhSerialNo(String hhSerialNo) {
        this.hhSerialNo = hhSerialNo;
    }

    public String getHhUniqueId() {
        return hhUniqueId;
    }

    public void setHhUniqueId(String hhUniqueId) {
        this.hhUniqueId = hhUniqueId;
    }

    public String getLga() {
        return lga;
    }

    public void setLga(String lga) {
        this.lga = lga;
    }

    public String getOtherNames() {
        return otherNames;
    }

    public void setOtherNames(String otherNames) {
        this.otherNames = otherNames;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getOvcSno() {
        return ovcSno;
    }

    public void setOvcSno(String ovcSno) {
        this.ovcSno = ovcSno;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getTargetGroup() {
        return targetGroup;
    }

    public void setTargetGroup(String targetGroup) {
        this.targetGroup = targetGroup;
    }

    public String getPcvName() {
        return pcvName;
    }

    public void setPcvName(String pcvName) {
        this.pcvName = pcvName;
    }
    
@Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        state=null;
        lga=null;
        ward=null;
        cboName=null;
        completedbyCbo=null;
        dateOfAttendance=null;
        dateEnrollment=null;
        actionName=null;
        clientId=null;
        ovcSno=null;
        hhSerialNo=null;
        hhUniqueId=null;
        surname=null;
        otherNames=null;
        gender=null;
        age=0;
        ageUnit=null;
        address=null;
        phone=null;
        recordId=0;
        caregiverName=null;  
        pcvName=null;
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
