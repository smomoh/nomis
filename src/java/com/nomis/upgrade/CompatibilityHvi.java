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
public class CompatibilityHvi implements Serializable
{
    private String hhUniqueId;
    private String hhSurname;
    private String hhFirstname;
    private String caregiverSurname;
    private String caregiverFirstname;
    private String address;
    private String phone;
    private int noOfChildrenInhh;
    private int noOfPeopleInhh;
    private String dateOfAssessment;
    private String caregiverGender;
    private int caregiverAge;
    private int currentAge;
    private String maritalStatus;
    private String occupation;
    
    private int hhHeadship;
    private int health;
    private int educationLevel;
    private int shelterAndHousing;
    private int foodSecurityAndNutrition;
    private int meansOfLivelihood;
    private int hhIncome;
    private String eligibleForEnrollment;
    private String nameOfAssessor;
    private String designation;
    private String stateCode;
    private String lgaCode;
    private String orgCode;
    private String communityCode;
    private String partnerCode;
    private String recordedBy;
    private String dateOfEntry;

    public String getHhUniqueId() {
        return hhUniqueId;
    }

    public void setHhUniqueId(String hhUniqueId) {
        this.hhUniqueId = hhUniqueId;
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

    public String getCaregiverSurname() {
        return caregiverSurname;
    }

    public void setCaregiverSurname(String caregiverSurname) {
        this.caregiverSurname = caregiverSurname;
    }

    public String getCaregiverFirstname() {
        return caregiverFirstname;
    }

    public void setCaregiverFirstname(String caregiverFirstname) {
        this.caregiverFirstname = caregiverFirstname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getNoOfChildrenInhh() {
        return noOfChildrenInhh;
    }

    public void setNoOfChildrenInhh(int noOfChildrenInhh) {
        this.noOfChildrenInhh = noOfChildrenInhh;
    }

    public int getNoOfPeopleInhh() {
        return noOfPeopleInhh;
    }

    public void setNoOfPeopleInhh(int noOfPeopleInhh) {
        this.noOfPeopleInhh = noOfPeopleInhh;
    }

    public String getDateOfAssessment() {
        return dateOfAssessment;
    }

    public void setDateOfAssessment(String dateOfAssessment) {
        this.dateOfAssessment = dateOfAssessment;
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

    public int getCurrentAge() {
        return currentAge;
    }

    public void setCurrentAge(int currentAge) {
        this.currentAge = currentAge;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public int getHhHeadship() {
        return hhHeadship;
    }

    public void setHhHeadship(int hhHeadship) {
        this.hhHeadship = hhHeadship;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(int educationLevel) {
        this.educationLevel = educationLevel;
    }

    public int getShelterAndHousing() {
        return shelterAndHousing;
    }

    public void setShelterAndHousing(int shelterAndHousing) {
        this.shelterAndHousing = shelterAndHousing;
    }

    public int getFoodSecurityAndNutrition() {
        return foodSecurityAndNutrition;
    }

    public void setFoodSecurityAndNutrition(int foodSecurityAndNutrition) {
        this.foodSecurityAndNutrition = foodSecurityAndNutrition;
    }

    public int getMeansOfLivelihood() {
        return meansOfLivelihood;
    }

    public void setMeansOfLivelihood(int meansOfLivelihood) {
        this.meansOfLivelihood = meansOfLivelihood;
    }

    public int getHhIncome() {
        return hhIncome;
    }

    public void setHhIncome(int hhIncome) {
        this.hhIncome = hhIncome;
    }

    public String getEligibleForEnrollment() {
        return eligibleForEnrollment;
    }

    public void setEligibleForEnrollment(String eligibleForEnrollment) {
        this.eligibleForEnrollment = eligibleForEnrollment;
    }

    public String getNameOfAssessor() {
        return nameOfAssessor;
    }

    public void setNameOfAssessor(String nameOfAssessor) {
        this.nameOfAssessor = nameOfAssessor;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getLgaCode() {
        return lgaCode;
    }

    public void setLgaCode(String lgaCode) {
        this.lgaCode = lgaCode;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getCommunityCode() {
        return communityCode;
    }

    public void setCommunityCode(String communityCode) {
        this.communityCode = communityCode;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
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
}
