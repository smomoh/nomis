/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.nomis.nomisutils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;

/**
 *
 * @author smomoh
 */
public class ServiceDomainManager implements Serializable
{
    private static List psychosocialServices;
    private static List nutritionalServices;
    private static List healthServices;
    private static List educationalServices;
    private static List protectionServices;
    private static List shelterAndCareServices;
    private static List economicStrengtheningServices;
    
    public static List getEconomicStrengtheningServices() 
    {
        economicStrengtheningServices=new ArrayList();
        economicStrengtheningServices.add(OvcServiceManager.getFinancialEducation());
        economicStrengtheningServices.add(OvcServiceManager.getSavingsAndLoans());
        economicStrengtheningServices.add(OvcServiceManager.getVocationalAndApprenticeshipTraining());
        economicStrengtheningServices.add(OvcServiceManager.getLivelihoodOpportunity());
        economicStrengtheningServices.add(OvcServiceManager.getBusinessGrant());
        return economicStrengtheningServices;
    }
    public static List getHouseholdEconomicStrengtheningServices() 
    {
        economicStrengtheningServices=new ArrayList();
        economicStrengtheningServices.add(OvcServiceManager.getFinancialEducation());
        economicStrengtheningServices.add(OvcServiceManager.getSavingsAndLoans());
        economicStrengtheningServices.add(OvcServiceManager.getVocationalAndApprenticeshipTraining());
        economicStrengtheningServices.add(OvcServiceManager.getLivelihoodOpportunity());
        economicStrengtheningServices.add(OvcServiceManager.getBusinessGrant());
        
        economicStrengtheningServices.add(OvcServiceManager.getLinkageToCashTransferScheme());
        economicStrengtheningServices.add(OvcServiceManager.getPrivateSectorLinkages());
        economicStrengtheningServices.add(OvcServiceManager.getSavingsAndInternalLendingCommunity());
        economicStrengtheningServices.add(OvcServiceManager.getEconomicStrengtheningReferral());
        return economicStrengtheningServices;
    }
    public static List getEducationalServices() 
    {
        educationalServices=new ArrayList();
        educationalServices.add(OvcServiceManager.getAdvocacyForSchoolEnrolmentService());
        educationalServices.add(OvcServiceManager.getWaiverOfSchoolFeeService());
        educationalServices.add(OvcServiceManager.getProvisionOfSchoolMaterialService());
        educationalServices.add(OvcServiceManager.getSchoolVisit());
        educationalServices.add(OvcServiceManager.getSchoolPerformanceAssessmentService());
        educationalServices.add(OvcServiceManager.getHolisticScholarshipService());
        educationalServices.add(OvcServiceManager.getEducationReferralService());
        return educationalServices;
    }

    public static List getHealthServices() 
    {
        healthServices=new ArrayList();
        healthServices.add(OvcServiceManager.getHealthEducationService());
        healthServices.add(OvcServiceManager.getTreatmentOfMinorIllnessesService());
        healthServices.add(OvcServiceManager.getDewormingService());
        healthServices.add(OvcServiceManager.getInsecticideTreatedBedNetService());
        healthServices.add(OvcServiceManager.getWaterTreatmentService());
        healthServices.add(OvcServiceManager.getWashService());
        healthServices.add(OvcServiceManager.getPmtctHctCommunityHIVService());
        healthServices.add(OvcServiceManager.getHctEidHIVReferralService());
        healthServices.add(OvcServiceManager.getAccessForHIVCareService());
        healthServices.add(OvcServiceManager.getCommunityTBSymptomScreeningService());
        healthServices.add(OvcServiceManager.getTBServicesReferralDiagnosisDOTS());
        healthServices.add(OvcServiceManager.getHealthReferralService());
        return healthServices;
    }

    public static List getNutritionalServices() 
    {
        nutritionalServices=new ArrayList();
        nutritionalServices.add(OvcServiceManager.getNutritionEducationAndCounsellingService());
        nutritionalServices.add(OvcServiceManager.getFoodAndNutritionalSupplements());
        nutritionalServices.add(OvcServiceManager.getVitaminAZincAndIronSuplement());
        nutritionalServices.add(OvcServiceManager.getNutritionAssessmentCounsellingAndSupport());
        nutritionalServices.add(OvcServiceManager.getGrowthMonitoring());
        nutritionalServices.add(OvcServiceManager.getNutritionReferral());
        return nutritionalServices;
    }

    public static List getProtectionServices() 
    {
        protectionServices=new ArrayList();
        protectionServices.add(OvcServiceManager.getLegalServices());
        protectionServices.add(OvcServiceManager.getSuccessionPlanning());
        protectionServices.add(OvcServiceManager.getBirthRegistrationService());
        protectionServices.add(OvcServiceManager.getReferralForProtectionServices());
        return protectionServices;
    }

    public static List getPsychosocialServices() 
    {
        psychosocialServices=new ArrayList();
        psychosocialServices.add(OvcServiceManager.getCounsellingSupportService());
        psychosocialServices.add(OvcServiceManager.getRecreationalService());
        psychosocialServices.add(OvcServiceManager.getLifeSkillSupportService());
        psychosocialServices.add(OvcServiceManager.getHomeVisitService());
        return psychosocialServices;
    }

    public static List getShelterAndCareServices() 
    {
        shelterAndCareServices=new ArrayList();
        shelterAndCareServices.add(OvcServiceManager.getReIntegrationIntoFamily());
        shelterAndCareServices.add(OvcServiceManager.getProvisionAndRepairOfAccommodation());
        shelterAndCareServices.add(OvcServiceManager.getFosterParenting());
        shelterAndCareServices.add(OvcServiceManager.getClothingSupport());
        shelterAndCareServices.add(OvcServiceManager.getReferralForShelterAndCareServices());
        return shelterAndCareServices;
    }
    public static void setOvcServices(HttpSession session)
    {
        session.setAttribute("healthServices", getHealthServices());
        session.setAttribute("nutritionalServices", getNutritionalServices());
        session.setAttribute("educationServices", getEducationalServices());
        session.setAttribute("protectionServices", getProtectionServices());
        session.setAttribute("psychosocialServices", getPsychosocialServices());
        session.setAttribute("shelterServices", getShelterAndCareServices());
        session.setAttribute("economicStrenghteningServices", getEconomicStrengtheningServices());
        session.setAttribute("householdEconomicServices", getHouseholdEconomicStrengtheningServices());
    }
    
}
