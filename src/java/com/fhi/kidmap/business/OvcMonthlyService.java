/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.business;

import java.io.Serializable;

/**
 *
 * @author smomoh
 */
public class OvcMonthlyService implements Serializable
{

    private int month;
    private int year;
    private String dateOfService;
    private String dateOfEntry;
    private String ovcId;
    private String counsellingSupport;
    private String recreationalActivity;
    private String liveBuildingSkills;
    private String psychosocialOthers;
    private String nutitionEducation;
    private String foodAndNutrition;
    private String growthMonitoring;
    private String nutritionOthers;
    private String minorIlness;
    private String waterTreatment;
    private String healthEducation;
    private String provisionOfItns;
    private String vitaminSupplement;
    private String breastMilk;
    private String healthOthers;
    private String schoolFees;
    private String provisionOfMaterials;
    private String schoolOfMinerals;
    private String schoolVisit;
    private String schoolPerformance;
    private String educationOthers;
    private String legalService;
    private String birthRegistration;
    private String successionPlanning;
    private String childProtection;
    private String renovationOfAccmd;
    private String reintegration;
    private String clothingSupport;
    private String linkToShelter;
    private String shelterOthers;
    private String businessGrant;
    private String vocational;
    private String employment;
    private String supportForHeadOfHouse;
    private String economicOthers;
    private String isChildWithdrawn;
    private String knownDeath;
    private String ageAbove18;
    private String migrated;
    private String lostToFollowup;

    public OvcMonthlyService()
    {

    }

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getDateOfEntry() {
        return dateOfEntry;
    }

    public void setDateOfEntry(String dateOfEntry) {
        this.dateOfEntry = dateOfEntry;
    }

    public String getDateOfService() {
        return dateOfService;
    }

    public void setDateOfService(String dateOfService) {
        this.dateOfService = dateOfService;
    }

    public String getOvcId() {
        return ovcId;
    }

    public void setOvcId(String ovcId) {
        this.ovcId = ovcId;
    }

    public String getAgeAbove18() {
        return ageAbove18;
    }

    public void setAgeAbove18(String ageAbove18) {
        this.ageAbove18 = ageAbove18;
    }

    public String getBirthRegistration() {
        return birthRegistration;
    }

    public void setBirthRegistration(String birthRegistration) {
        this.birthRegistration = birthRegistration;
    }

    public String getBreastMilk() {
        return breastMilk;
    }

    public void setBreastMilk(String breastMilk) {
        this.breastMilk = breastMilk;
    }

    public String getBusinessGrant() {
        return businessGrant;
    }

    public void setBusinessGrant(String businessGrant) {
        this.businessGrant = businessGrant;
    }

    public String getChildProtection() {
        return childProtection;
    }

    public void setChildProtection(String childProtection) {
        this.childProtection = childProtection;
    }

    public String getClothingSupport() {
        return clothingSupport;
    }

    public void setClothingSupport(String clothingSupport) {
        this.clothingSupport = clothingSupport;
    }

    public String getCounsellingSupport() {
        return counsellingSupport;
    }

    public void setCounsellingSupport(String counsellingSupport) {
        this.counsellingSupport = counsellingSupport;
    }

    public String getEconomicOthers() {
        return economicOthers;
    }

    public void setEconomicOthers(String economicOthers) {
        this.economicOthers = economicOthers;
    }

    public String getEducationOthers() {
        return educationOthers;
    }

    public void setEducationOthers(String educationOthers) {
        this.educationOthers = educationOthers;
    }

    public String getEmployment() {
        return employment;
    }

    public void setEmployment(String employment) {
        this.employment = employment;
    }

    public String getFoodAndNutrition() {
        return foodAndNutrition;
    }

    public void setFoodAndNutrition(String foodAndNutrition) {
        this.foodAndNutrition = foodAndNutrition;
    }

    public String getGrowthMonitoring() {
        return growthMonitoring;
    }

    public void setGrowthMonitoring(String growthMonitoring) {
        this.growthMonitoring = growthMonitoring;
    }

    public String getHealthEducation() {
        return healthEducation;
    }

    public void setHealthEducation(String healthEducation) {
        this.healthEducation = healthEducation;
    }

    public String getHealthOthers() {
        return healthOthers;
    }

    public void setHealthOthers(String healthOthers) {
        this.healthOthers = healthOthers;
    }

    public String getIsChildWithdrawn() {
        return isChildWithdrawn;
    }

    public void setIsChildWithdrawn(String isChildWithdrawn) {
        this.isChildWithdrawn = isChildWithdrawn;
    }

    public String getKnownDeath() {
        return knownDeath;
    }

    public void setKnownDeath(String knownDeath) {
        this.knownDeath = knownDeath;
    }

    public String getLegalService() {
        return legalService;
    }

    public void setLegalService(String legalService) {
        this.legalService = legalService;
    }

    public String getLinkToShelter() {
        return linkToShelter;
    }

    public void setLinkToShelter(String linkToShelter) {
        this.linkToShelter = linkToShelter;
    }

    public String getLiveBuildingSkills() {
        return liveBuildingSkills;
    }

    public void setLiveBuildingSkills(String liveBuildingSkills) {
        this.liveBuildingSkills = liveBuildingSkills;
    }

    public String getLostToFollowup() {
        return lostToFollowup;
    }

    public void setLostToFollowup(String lostToFollowup) {
        this.lostToFollowup = lostToFollowup;
    }

    public String getMigrated() {
        return migrated;
    }

    public void setMigrated(String migrated) {
        this.migrated = migrated;
    }

    public String getMinorIlness() {
        return minorIlness;
    }

    public void setMinorIlness(String minorIlness) {
        this.minorIlness = minorIlness;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getNutitionEducation() {
        return nutitionEducation;
    }

    public void setNutitionEducation(String nutitionEducation) {
        this.nutitionEducation = nutitionEducation;
    }

    public String getNutritionOthers() {
        return nutritionOthers;
    }

    public void setNutritionOthers(String nutritionOthers) {
        this.nutritionOthers = nutritionOthers;
    }

    public String getProvisionOfItns() {
        return provisionOfItns;
    }

    public void setProvisionOfItns(String provisionOfItns) {
        this.provisionOfItns = provisionOfItns;
    }

    public String getProvisionOfMaterials() {
        return provisionOfMaterials;
    }

    public void setProvisionOfMaterials(String provisionOfMaterials) {
        this.provisionOfMaterials = provisionOfMaterials;
    }

    public String getPsychosocialOthers() {
        return psychosocialOthers;
    }

    public void setPsychosocialOthers(String psychosocialOthers) {
        this.psychosocialOthers = psychosocialOthers;
    }

    public String getRecreationalActivity() {
        return recreationalActivity;
    }

    public void setRecreationalActivity(String recreationalActivity) {
        this.recreationalActivity = recreationalActivity;
    }

    public String getReintegration() {
        return reintegration;
    }

    public void setReintegration(String reintegration) {
        this.reintegration = reintegration;
    }

    public String getRenovationOfAccmd() {
        return renovationOfAccmd;
    }

    public void setRenovationOfAccmd(String renovationOfAccmd) {
        this.renovationOfAccmd = renovationOfAccmd;
    }

    public String getSchoolFees() {
        return schoolFees;
    }

    public void setSchoolFees(String schoolFees) {
        this.schoolFees = schoolFees;
    }

    public String getSchoolOfMinerals() {
        return schoolOfMinerals;
    }

    public void setSchoolOfMinerals(String schoolOfMinerals) {
        this.schoolOfMinerals = schoolOfMinerals;
    }

    public String getSchoolPerformance() {
        return schoolPerformance;
    }

    public void setSchoolPerformance(String schoolPerformance) {
        this.schoolPerformance = schoolPerformance;
    }

    public String getSchoolVisit() {
        return schoolVisit;
    }

    public void setSchoolVisit(String schoolVisit) {
        this.schoolVisit = schoolVisit;
    }

    public String getShelterOthers() {
        return shelterOthers;
    }

    public void setShelterOthers(String shelterOthers) {
        this.shelterOthers = shelterOthers;
    }

    public String getSuccessionPlanning() {
        return successionPlanning;
    }

    public void setSuccessionPlanning(String successionPlanning) {
        this.successionPlanning = successionPlanning;
    }

    public String getSupportForHeadOfHouse() {
        return supportForHeadOfHouse;
    }

    public void setSupportForHeadOfHouse(String supportForHeadOfHouse) {
        this.supportForHeadOfHouse = supportForHeadOfHouse;
    }

    public String getVitaminSupplement() {
        return vitaminSupplement;
    }

    public void setVitaminSupplement(String vitaminSupplement) {
        this.vitaminSupplement = vitaminSupplement;
    }

    public String getVocational() {
        return vocational;
    }

    public void setVocational(String vocational) {
        this.vocational = vocational;
    }

    public String getWaterTreatment() {
        return waterTreatment;
    }

    public void setWaterTreatment(String waterTreatment) {
        this.waterTreatment = waterTreatment;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
