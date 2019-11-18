/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report;

/**
 *
 * @author smomoh
 */
public class HouseholdUniqueServiceReport 
{
    private String hhUniqueId;
    private String caregiverId;
    private String serviceRecipientType;
    private int caregiverAge;
    private String careiverGender;
    private String caregiverFirstname;
    private String caregiverSurname;
    private String serviceDate;
    private String economicStrengtheningServices;
    private String educationalServices;
    private String healthServices;
    private int id;
    private String nutritionalServices;
    private String orgCode;  
    private String protectionServices;
    private String psychosocialSupportServices;
    private int serviceNo;
    private int numberOfServices;
    private String shelterAndCareServices;
    private String volunteerDesignation;
    private String volunteerName;
    private String recordedBy;
    private String dateOfEntry;
    private String currentHivStatus;
    private String pointOfService="householdservice";
    private String enrolledInCare="No";
    private String organizationClientIsReferred;

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

    public String getCaregiverId() {
        return caregiverId;
    }

    public void setCaregiverId(String caregiverId) {
        this.caregiverId = caregiverId;
    }

    public String getCaregiverSurname() {
        return caregiverSurname;
    }

    public void setCaregiverSurname(String caregiverSurname) {
        this.caregiverSurname = caregiverSurname;
    }

    public String getCareiverGender() {
        return careiverGender;
    }

    public void setCareiverGender(String careiverGender) {
        this.careiverGender = careiverGender;
    }

    public String getCurrentHivStatus() {
        return currentHivStatus;
    }

    public void setCurrentHivStatus(String currentHivStatus) {
        this.currentHivStatus = currentHivStatus;
    }

    public String getDateOfEntry() {
        return dateOfEntry;
    }

    public void setDateOfEntry(String dateOfEntry) {
        this.dateOfEntry = dateOfEntry;
    }

    public String getEconomicStrengtheningServices() {
        return economicStrengtheningServices;
    }

    public void setEconomicStrengtheningServices(String economicStrengtheningServices) {
        this.economicStrengtheningServices = economicStrengtheningServices;
    }

    public String getEducationalServices() {
        return educationalServices;
    }

    public void setEducationalServices(String educationalServices) {
        this.educationalServices = educationalServices;
    }

    public String getEnrolledInCare() {
        return enrolledInCare;
    }

    public void setEnrolledInCare(String enrolledInCare) {
        this.enrolledInCare = enrolledInCare;
    }

    public String getHealthServices() {
        return healthServices;
    }

    public void setHealthServices(String healthServices) {
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

    public int getNumberOfServices() {
        return numberOfServices;
    }

    public void setNumberOfServices(int numberOfServices) {
        this.numberOfServices = numberOfServices;
    }

    public String getNutritionalServices() {
        return nutritionalServices;
    }

    public void setNutritionalServices(String nutritionalServices) {
        this.nutritionalServices = nutritionalServices;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrganizationClientIsReferred() {
        return organizationClientIsReferred;
    }

    public void setOrganizationClientIsReferred(String organizationClientIsReferred) {
        this.organizationClientIsReferred = organizationClientIsReferred;
    }

    public String getPointOfService() {
        return pointOfService;
    }

    public void setPointOfService(String pointOfService) {
        this.pointOfService = pointOfService;
    }

    public String getProtectionServices() {
        return protectionServices;
    }

    public void setProtectionServices(String protectionServices) {
        this.protectionServices = protectionServices;
    }

    public String getPsychosocialSupportServices() {
        return psychosocialSupportServices;
    }

    public void setPsychosocialSupportServices(String psychosocialSupportServices) {
        this.psychosocialSupportServices = psychosocialSupportServices;
    }

    public String getRecordedBy() {
        return recordedBy;
    }

    public void setRecordedBy(String recordedBy) {
        this.recordedBy = recordedBy;
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

    public String getServiceRecipientType() {
        return serviceRecipientType;
    }

    public void setServiceRecipientType(String serviceRecipientType) {
        this.serviceRecipientType = serviceRecipientType;
    }

    public String getShelterAndCareServices() {
        return shelterAndCareServices;
    }

    public void setShelterAndCareServices(String shelterAndCareServices) {
        this.shelterAndCareServices = shelterAndCareServices;
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
    
}
