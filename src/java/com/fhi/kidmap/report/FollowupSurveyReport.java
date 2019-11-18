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
public class FollowupSurveyReport implements Serializable
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
    private int currentAge;
    private String partner;
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
    
    private String dateOfSurvey;
    private String updatedAddress;
    private int updatedAge;
    private String updatedAgeUnit;
    private String updatedHivStatus;
    private String updatedBirthCertStatus;
    private String updatedSchoolStatus;
    private String updatedSchoolName;
    private String updatedCaregiverName;
    private String updatedCaregiverGender;
    private int updatedCaregiverAge;
    private String updatedCaregiverAddress;
    private String updatedCaregiverPhone;
    private String updatedCaregiverOccupation;
    private String updatedCaregiverRelationship;
    private String followupCompletedbyName;
    private String followupCompletedbyDesignation;
    private String followupDateOfEntry;
    private String followupRecordedBy;
    
    private int csiFactor1;
    private int csiFactor2;
    private int csiFactor3;
    private int csiFactor4;
    private int csiFactor5;
    private int csiFactor6;
    private int csiFactor7;
    private int csiFactor8;
    private int csiFactor9;
    private int csiFactor10;
    private int csiFactor11;
    private int csiFactor12;
    private int surveyNumber;

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

    public int getCurrentAge() {
        return currentAge;
    }

    public void setCurrentAge(int currentAge) {
        this.currentAge = currentAge;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
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

    public String getDateOfSurvey() {
        return dateOfSurvey;
    }

    public void setDateOfSurvey(String dateOfSurvey) {
        this.dateOfSurvey = dateOfSurvey;
    }

    public String getUpdatedAddress() {
        return updatedAddress;
    }

    public void setUpdatedAddress(String updatedAddress) {
        this.updatedAddress = updatedAddress;
    }

    public int getUpdatedAge() {
        return updatedAge;
    }

    public void setUpdatedAge(int updatedAge) {
        this.updatedAge = updatedAge;
    }

    public String getUpdatedAgeUnit() {
        return updatedAgeUnit;
    }

    public void setUpdatedAgeUnit(String updatedAgeUnit) {
        this.updatedAgeUnit = updatedAgeUnit;
    }

    public String getUpdatedHivStatus() {
        return updatedHivStatus;
    }

    public void setUpdatedHivStatus(String updatedHivStatus) {
        this.updatedHivStatus = updatedHivStatus;
    }

    public String getUpdatedBirthCertStatus() {
        return updatedBirthCertStatus;
    }

    public void setUpdatedBirthCertStatus(String updatedBirthCertStatus) {
        this.updatedBirthCertStatus = updatedBirthCertStatus;
    }

    public String getUpdatedSchoolStatus() {
        return updatedSchoolStatus;
    }

    public void setUpdatedSchoolStatus(String updatedSchoolStatus) {
        this.updatedSchoolStatus = updatedSchoolStatus;
    }

    public String getUpdatedSchoolName() {
        return updatedSchoolName;
    }

    public void setUpdatedSchoolName(String updatedSchoolName) {
        this.updatedSchoolName = updatedSchoolName;
    }

    public String getUpdatedCaregiverName() {
        return updatedCaregiverName;
    }

    public void setUpdatedCaregiverName(String updatedCaregiverName) {
        this.updatedCaregiverName = updatedCaregiverName;
    }

    public String getUpdatedCaregiverGender() {
        return updatedCaregiverGender;
    }

    public void setUpdatedCaregiverGender(String updatedCaregiverGender) {
        this.updatedCaregiverGender = updatedCaregiverGender;
    }

    public int getUpdatedCaregiverAge() {
        return updatedCaregiverAge;
    }

    public void setUpdatedCaregiverAge(int updatedCaregiverAge) {
        this.updatedCaregiverAge = updatedCaregiverAge;
    }

    public String getUpdatedCaregiverAddress() {
        return updatedCaregiverAddress;
    }

    public void setUpdatedCaregiverAddress(String updatedCaregiverAddress) {
        this.updatedCaregiverAddress = updatedCaregiverAddress;
    }

    public String getUpdatedCaregiverPhone() {
        return updatedCaregiverPhone;
    }

    public void setUpdatedCaregiverPhone(String updatedCaregiverPhone) {
        this.updatedCaregiverPhone = updatedCaregiverPhone;
    }

    public String getUpdatedCaregiverOccupation() {
        return updatedCaregiverOccupation;
    }

    public void setUpdatedCaregiverOccupation(String updatedCaregiverOccupation) {
        this.updatedCaregiverOccupation = updatedCaregiverOccupation;
    }

    public String getUpdatedCaregiverRelationship() {
        return updatedCaregiverRelationship;
    }

    public void setUpdatedCaregiverRelationship(String updatedCaregiverRelationship) {
        this.updatedCaregiverRelationship = updatedCaregiverRelationship;
    }

    public String getFollowupCompletedbyName() {
        return followupCompletedbyName;
    }

    public void setFollowupCompletedbyName(String followupCompletedbyName) {
        this.followupCompletedbyName = followupCompletedbyName;
    }

    public String getFollowupCompletedbyDesignation() {
        return followupCompletedbyDesignation;
    }

    public void setFollowupCompletedbyDesignation(String followupCompletedbyDesignation) {
        this.followupCompletedbyDesignation = followupCompletedbyDesignation;
    }

    public String getFollowupDateOfEntry() {
        return followupDateOfEntry;
    }

    public void setFollowupDateOfEntry(String followupDateOfEntry) {
        this.followupDateOfEntry = followupDateOfEntry;
    }

    public String getFollowupRecordedBy() {
        return followupRecordedBy;
    }

    public void setFollowupRecordedBy(String followupRecordedBy) {
        this.followupRecordedBy = followupRecordedBy;
    }

    public int getCsiFactor1() {
        return csiFactor1;
    }

    public void setCsiFactor1(int csiFactor1) {
        this.csiFactor1 = csiFactor1;
    }

    public int getCsiFactor2() {
        return csiFactor2;
    }

    public void setCsiFactor2(int csiFactor2) {
        this.csiFactor2 = csiFactor2;
    }

    public int getCsiFactor3() {
        return csiFactor3;
    }

    public void setCsiFactor3(int csiFactor3) {
        this.csiFactor3 = csiFactor3;
    }

    public int getCsiFactor4() {
        return csiFactor4;
    }

    public void setCsiFactor4(int csiFactor4) {
        this.csiFactor4 = csiFactor4;
    }

    public int getCsiFactor5() {
        return csiFactor5;
    }

    public void setCsiFactor5(int csiFactor5) {
        this.csiFactor5 = csiFactor5;
    }

    public int getCsiFactor6() {
        return csiFactor6;
    }

    public void setCsiFactor6(int csiFactor6) {
        this.csiFactor6 = csiFactor6;
    }

    public int getCsiFactor7() {
        return csiFactor7;
    }

    public void setCsiFactor7(int csiFactor7) {
        this.csiFactor7 = csiFactor7;
    }

    public int getCsiFactor8() {
        return csiFactor8;
    }

    public void setCsiFactor8(int csiFactor8) {
        this.csiFactor8 = csiFactor8;
    }

    public int getCsiFactor9() {
        return csiFactor9;
    }

    public void setCsiFactor9(int csiFactor9) {
        this.csiFactor9 = csiFactor9;
    }

    public int getCsiFactor10() {
        return csiFactor10;
    }

    public void setCsiFactor10(int csiFactor10) {
        this.csiFactor10 = csiFactor10;
    }

    public int getCsiFactor11() {
        return csiFactor11;
    }

    public void setCsiFactor11(int csiFactor11) {
        this.csiFactor11 = csiFactor11;
    }

    public int getCsiFactor12() {
        return csiFactor12;
    }

    public void setCsiFactor12(int csiFactor12) {
        this.csiFactor12 = csiFactor12;
    }

    public int getSurveyNumber() {
        return surveyNumber;
    }

    public void setSurveyNumber(int surveyNumber) {
        this.surveyNumber = surveyNumber;
    }
    
    
}
