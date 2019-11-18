/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report;

import java.io.Serializable;

/**
 *
 * @author Siaka
 */
public class ReferralReport  implements Serializable
{
    private int id;
    private String ovcId;
    private String dateEnrollment;
    private String state;
    private String lga;
    private String ward;
    private String gender;
    private int age;
    private String ageUnit;
    private String eligibility;
    private String hivStatus;
    private String birthCertificate;
    private String schoolStatus;
    private String cboCode;
    private String partner;
    private int currentAge;
    private String recordedBy;
    private String dateOfEntry;
    private String caregiverName;
    private String caregiverGender;
    private int caregiverAge;
    private String caregiverAddress;
    private String caregiverPhone;
    private String caregiverRelationships;
    private String caregiverOccupation;
    private String completedbyName;
    private String completedbyDesignation;
    
    private String referringOrganization;
    private String dateOfReferral;
    private String psychoServices;
    private String nutritionalServices;
    private String healthServices;
    private String educationalServices;
    private String protectionServices;
    private String shelterServices;
    private String economicServices;
    private String nameOfOrganizationChildIsreferred;
    private String referralCompleted;
    private String nameOfPersonReferring;
    private String designationOfReferrer;
    private String referrerPhoneNo;
    private String referrerEmail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOvcId() {
        return ovcId;
    }

    public void setOvcId(String ovcId) {
        this.ovcId = ovcId;
    }

    public String getDateEnrollment() {
        return dateEnrollment;
    }

    public void setDateEnrollment(String dateEnrollment) {
        this.dateEnrollment = dateEnrollment;
    }

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

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getEligibility() {
        return eligibility;
    }

    public void setEligibility(String eligibility) {
        this.eligibility = eligibility;
    }

    public String getHivStatus() {
        return hivStatus;
    }

    public void setHivStatus(String hivStatus) {
        this.hivStatus = hivStatus;
    }

    public String getBirthCertificate() {
        return birthCertificate;
    }

    public void setBirthCertificate(String birthCertificate) {
        this.birthCertificate = birthCertificate;
    }

    public String getSchoolStatus() {
        return schoolStatus;
    }

    public void setSchoolStatus(String schoolStatus) {
        this.schoolStatus = schoolStatus;
    }

    public String getCboCode() {
        return cboCode;
    }

    public void setCboCode(String cboCode) {
        this.cboCode = cboCode;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public int getCurrentAge() {
        return currentAge;
    }

    public void setCurrentAge(int currentAge) {
        this.currentAge = currentAge;
    }

    public String getRecordedBy() {
        return recordedBy;
    }

    public void setRecordedBy(String recordedBy) {
        this.recordedBy = recordedBy;
    }

    public String getDateOfEntry() {
        return dateOfEntry;
    }

    public void setDateOfEntry(String dateOfEntry) {
        this.dateOfEntry = dateOfEntry;
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

    public int getCaregiverAge() {
        return caregiverAge;
    }

    public void setCaregiverAge(int caregiverAge) {
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

    public String getCaregiverRelationships() {
        return caregiverRelationships;
    }

    public void setCaregiverRelationships(String caregiverRelationships) {
        this.caregiverRelationships = caregiverRelationships;
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

    public String getReferringOrganization() {
        return referringOrganization;
    }

    public void setReferringOrganization(String referringOrganization) {
        this.referringOrganization = referringOrganization;
    }

    public String getDateOfReferral() {
        return dateOfReferral;
    }

    public void setDateOfReferral(String dateOfReferral) {
        this.dateOfReferral = dateOfReferral;
    }

    public String getPsychoServices() {
        return psychoServices;
    }

    public void setPsychoServices(String psychoServices) {
        this.psychoServices = psychoServices;
    }

    public String getNutritionalServices() {
        return nutritionalServices;
    }

    public void setNutritionalServices(String nutritionalServices) {
        this.nutritionalServices = nutritionalServices;
    }

    public String getHealthServices() {
        return healthServices;
    }

    public void setHealthServices(String healthServices) {
        this.healthServices = healthServices;
    }

    public String getEducationalServices() {
        return educationalServices;
    }

    public void setEducationalServices(String educationalServices) {
        this.educationalServices = educationalServices;
    }

    public String getProtectionServices() {
        return protectionServices;
    }

    public void setProtectionServices(String protectionServices) {
        this.protectionServices = protectionServices;
    }

    public String getShelterServices() {
        return shelterServices;
    }

    public void setShelterServices(String shelterServices) {
        this.shelterServices = shelterServices;
    }

    public String getEconomicServices() {
        return economicServices;
    }

    public void setEconomicServices(String economicServices) {
        this.economicServices = economicServices;
    }

    public String getNameOfOrganizationChildIsreferred() {
        return nameOfOrganizationChildIsreferred;
    }

    public void setNameOfOrganizationChildIsreferred(String nameOfOrganizationChildIsreferred) {
        this.nameOfOrganizationChildIsreferred = nameOfOrganizationChildIsreferred;
    }

    public String getReferralCompleted() {
        return referralCompleted;
    }

    public void setReferralCompleted(String referralCompleted) {
        this.referralCompleted = referralCompleted;
    }

    public String getNameOfPersonReferring() {
        return nameOfPersonReferring;
    }

    public void setNameOfPersonReferring(String nameOfPersonReferring) {
        this.nameOfPersonReferring = nameOfPersonReferring;
    }

    public String getDesignationOfReferrer() {
        return designationOfReferrer;
    }

    public void setDesignationOfReferrer(String designationOfReferrer) {
        this.designationOfReferrer = designationOfReferrer;
    }

    public String getReferrerPhoneNo() {
        return referrerPhoneNo;
    }

    public void setReferrerPhoneNo(String referrerPhoneNo) {
        this.referrerPhoneNo = referrerPhoneNo;
    }

    public String getReferrerEmail() {
        return referrerEmail;
    }

    public void setReferrerEmail(String referrerEmail) {
        this.referrerEmail = referrerEmail;
    }
    
    
}
