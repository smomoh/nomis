/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomis.upgrade;

import java.io.Serializable;

/**
 *
 * @author Siaka
 */
public class CompatibilityOvc implements Serializable
{
    private String state;
    private String lga;
    private String cboCode;
    private String ward;
    private String dateEnrollment;
    private String ovcId;
    private String hhSurname;
    private String hhFirstname;
    private String hhGender;
    private Integer hhAge;
    private int numOfChildrenInHh;
    private int numOfOVCInHh;
    private String caregiverName;
    private String caregiverGender;
    private Integer caregiverAge;
    private String caregiverAddress;
    private String caregiverPhone;
    private String caregiverOccupation;
    private String completedbyName;
    private String completedbyDesignation;
    private String dateOfEntry;
    private String recordedBy;
    private String partner;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLga() {
        return lga;
    }

    public void setLga(String lga) {
        this.lga = lga;
    }

    public String getCboCode() {
        return cboCode;
    }

    public void setCboCode(String cboCode) {
        this.cboCode = cboCode;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getDateEnrollment() {
        return dateEnrollment;
    }

    public void setDateEnrollment(String dateEnrollment) {
        this.dateEnrollment = dateEnrollment;
    }

    public String getDateOfEntry() {
        return dateOfEntry;
    }

    public void setDateOfEntry(String dateOfEntry) {
        this.dateOfEntry = dateOfEntry;
    }

    public String getOvcId() {
        return ovcId;
    }

    public void setOvcId(String ovcId) {
        this.ovcId = ovcId;
    }

    public String getHhSurname() {
        return hhSurname;
    }

    public void setHhSurname(String hhSurname) {
        this.hhSurname = hhSurname;
    }

    public String getHhFirstname() {
        return hhFirstname;
    }

    public void setHhFirstname(String hhFirstname) {
        this.hhFirstname = hhFirstname;
    }

    public String getHhGender() {
        return hhGender;
    }

    public void setHhGender(String hhGender) {
        this.hhGender = hhGender;
    }

    public Integer getHhAge() {
        return hhAge;
    }

    public void setHhAge(Integer hhAge) {
        this.hhAge = hhAge;
    }

    public int getNumOfChildrenInHh() {
        return numOfChildrenInHh;
    }

    public void setNumOfChildrenInHh(int numOfChildrenInHh) {
        this.numOfChildrenInHh = numOfChildrenInHh;
    }

    public int getNumOfOVCInHh() {
        return numOfOVCInHh;
    }

    public void setNumOfOVCInHh(int numOfOVCInHh) {
        this.numOfOVCInHh = numOfOVCInHh;
    }

    public String getCaregiverName() {
        return caregiverName;
    }

    public void setCaregiverName(String caregiverName) {
        this.caregiverName = caregiverName;
    }

    public String getCaregiverGender() {
        return caregiverGender;
    }

    public void setCaregiverGender(String caregiverGender) {
        this.caregiverGender = caregiverGender;
    }

    public Integer getCaregiverAge() {
        return caregiverAge;
    }

    public void setCaregiverAge(Integer caregiverAge) {
        this.caregiverAge = caregiverAge;
    }

    public String getCaregiverAddress() {
        return caregiverAddress;
    }

    public void setCaregiverAddress(String caregiverAddress) {
        this.caregiverAddress = caregiverAddress;
    }

    public String getCaregiverPhone() {
        return caregiverPhone;
    }

    public void setCaregiverPhone(String caregiverPhone) {
        this.caregiverPhone = caregiverPhone;
    }

    public String getCaregiverOccupation() {
        return caregiverOccupation;
    }

    public void setCaregiverOccupation(String caregiverOccupation) {
        this.caregiverOccupation = caregiverOccupation;
    }

    public String getCompletedbyName() {
        return completedbyName;
    }

    public void setCompletedbyName(String completedbyName) {
        this.completedbyName = completedbyName;
    }

    public String getCompletedbyDesignation() {
        return completedbyDesignation;
    }

    public void setCompletedbyDesignation(String completedbyDesignation) {
        this.completedbyDesignation = completedbyDesignation;
    }

    public String getRecordedBy() {
        return recordedBy;
    }

    public void setRecordedBy(String recordedBy) {
        this.recordedBy = recordedBy;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }   
}
