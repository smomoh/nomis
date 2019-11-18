/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author smomoh
 */
public class SummaryStatisticsReportManager implements Serializable
{
    public List getSummaryStatisticsIndicators()
    {
        IndicatorDictionary ind=new IndicatorDictionary();
        List mainList=new ArrayList();
        List ovcList=new ArrayList();
        List birthRegList=new ArrayList();
        List nutritionalAssessmentList=new ArrayList();
        List ovcHhLinkageList=new ArrayList();
        List ovcServiceList=new ArrayList();
        List ovcServiceListByDomain=new ArrayList();
        List ovcServiceListByHiv=new ArrayList();
        List ovcInHhServedHESList=new ArrayList();
        
        List caregiverList=new ArrayList();
        List caregiverServiceList=new ArrayList();
        List caregiverHivList=new ArrayList();
        List householdList=new ArrayList();
        List householdListByBaselineAssessment=new ArrayList();
        List hivList=new ArrayList();
        List newlyEnrolledhivList=new ArrayList();
        List hivTestingAndLinkageList=new ArrayList();
        List hivRiskAssessmentList=new ArrayList();
        List newMERList=new ArrayList();
        
        ovcList.add(ind.getIndicatorForNumberOfNewOvcEnrolled());
        ovcList.add(ind.getIndicatorForNumberOfOvcCurrentlyEnrolled());
        ovcList.add(ind.getIndicatorForNumberOfOvcEverEnrolled());
        ovcList.add(ind.getIndicatorForNumberOfOvcGraduated());
        ovcList.add(ind.getIndicatorForNumberOfOvcWithdrawnDueToLostToFollowup());
        ovcList.add(ind.getIndicatorForNumberOfOvcWithdrawnDueToMigration());
        ovcList.add(ind.getIndicatorForNumberOfOvcWithdrawnDueToAgingOut());
        ovcList.add(ind.getIndicatorForNumberOfOvcWithdrawnDueToKnownDeath());
        ovcList.add(ind.getIndicatorForNumberOfOvcWithdrawnDueToTransfer());
        ovcList.add(ind.getIndicatorForNumberOfOvcDeclaredInactive());
        ovcList.add(ind.getIndicatorForNumberOfOvcCurrentlyInSchool());
        ovcList.add(ind.getIndicatorForNumberOfOvcOutOfSchool());
        mainList.add(ovcList);
        
        
        
        birthRegList.add(ind.getIndicatorForNumberOfOvcNewlyEnrolledWithBirthCertificateAtBaseline());
        birthRegList.add(ind.getIndicatorForNumberOfOvcCurrentlyEnrolledWithBirthCertAtBaseline());
        birthRegList.add(ind.getIndicatorForNumberOfOvcEverEnrolledWithBirthCertificateAtBaseline());
        birthRegList.add(ind.getIndicatorForNumberOfOvcCurrentlyEnrolledWithBirthCertificate());
        birthRegList.add(ind.getIndicatorForNumberOfOvcEverEnrolledWithBirthCertificate());
        birthRegList.add(ind.getIndicatorForNumberOfOvcProvidedBirthCertWithinReportPeriod());
        birthRegList.add(ind.getIndicatorForNumberOfOvcWithoutBirthCertificateCurrently());
        birthRegList.add(ind.getNoOfOvcServedWithinTheReportPeriodThatHasBirthCert());
        
        mainList.add(birthRegList);
        
        hivList.add(ind.getIndicatorForNumberOfHIVPositiveOvcCurrentlyEnrolled());
        hivList.add(ind.getIndicatorForNumberOfHIVNegativeOvcCurrentlyEnrolled());
        hivList.add(ind.getIndicatorForNumberOfHIVUnknownOvcCurrentlyEnrolled());
        hivList.add(ind.getIndicatorForNumberOfHIVPositiveOvcEverEnrolled());
        hivList.add(ind.getIndicatorForNumberOfHIVNegativeOvcEverEnrolled());
        hivList.add(ind.getIndicatorForNumberOfHIVUnknownOvcEverEnrolled());
        
        mainList.add(hivList);
        
        newlyEnrolledhivList.add(ind.getIndicatorForNumberOfHIVPositiveOvcNewlyEnrolled());
        newlyEnrolledhivList.add(ind.getIndicatorForNumberOfHIVNegativeOvcNewlyEnrolled());
        newlyEnrolledhivList.add(ind.getIndicatorForNumberOfHIVUnknownOvcNewlyEnrolled());
        newlyEnrolledhivList.add(ind.getIndicatorForNumberOfHIVPositiveOvcEnrolledAndAreStillInProgram());
        newlyEnrolledhivList.add(ind.getIndicatorForNumberOfHIVNegativeOvcEnrolledAndAreStillInProgram());
        newlyEnrolledhivList.add(ind.getIndicatorForNumberOfHIVUnknownOvcEnrolledAndAreStillInProgram());
        //newlyEnrolledhivList.add(ind.getIndicatorForNumberOfHIVPositiveOvcAtBaseline());
        //newlyEnrolledhivList.add(ind.getIndicatorForNumberOfHIVNegativeOvcAtBaseline());
        //newlyEnrolledhivList.add(ind.getIndicatorForNumberOfHIVUnknownOvcAtBaseline());
        mainList.add(newlyEnrolledhivList);
                
        hivTestingAndLinkageList.add(ind.getIndicatorForNumberOfHivPositiveOvc());
        hivTestingAndLinkageList.add(ind.getIndicatorForNumberOfHivPositiveOvcIdentifiedWithinTheReportPeriod());
        hivTestingAndLinkageList.add(ind.getIndicatorForNumberOfOvcTestedForHIV());
        //hivTestingAndLinkageList.add(ind.getIndicatorForNumberOfOvcReferredForHIVCare());
        hivTestingAndLinkageList.add(ind.getIndicatorForNumberOfHIVPositiveOvcNewlyEnrolledInCare());
        hivTestingAndLinkageList.add(ind.getIndicatorForNumberOfHIVPositiveOvcCurrentlyEnrolledInCare());
        hivTestingAndLinkageList.add(ind.getIndicatorForNumberOfHIVPositiveOvcEverEnrolledInCare());
        
        hivTestingAndLinkageList.add(ind.getIndicatorForNumberOfHIVPositiveOvcCurrentlyEnrolledOnART());
        hivTestingAndLinkageList.add(ind.getIndicatorForNumberOfHIVPositiveOvcEverEnrolledOnART());
        
        
        mainList.add(hivTestingAndLinkageList);
        
        
        
        hivRiskAssessmentList.add(ind.getIndicatorForNumberOfOvcCurrentlyEnrolledThatHaveBeenAssessedonHIVRisk());
        hivRiskAssessmentList.add(ind.getIndicatorForNumberOfOvcEverEnrolledThatHaveBeenAssessedonHIVRisk());
        hivRiskAssessmentList.add(ind.getIndicatorForNumberOfOvcCurrentlyEnrolledThatHasNoHIVRiskAssessmentRecord());
        hivRiskAssessmentList.add(ind.getIndicatorForNumberOfOvcNeverAssessedForHivRisk());
        hivRiskAssessmentList.add(ind.getIndicatorForNumberOfOvcWhoseCaregiversKnowTheirHivStatusWithinTheReportPeriod());
        hivRiskAssessmentList.add(ind.getIndicatorForNumberOfOvcWhoseCaregiversRefusedToDiscloseTheirHivStatusWithinTheReportPeriod());
        hivRiskAssessmentList.add(ind.getIndicatorForNumberOfOvcAssessedForHivRiskAndReferredForTesting());
        hivRiskAssessmentList.add(ind.getIndicatorForNumberOfOvcAssessedForHivRiskAndDeterminedToBeAtRisk());
        mainList.add(hivRiskAssessmentList);
        //hivList.add(ind.getIndicatorForNumberOfOvcAssessedForHivRiskWithinTheReportPeriod());
        
        //mainHivList.add("HIV indicators");
        //mainHivList.add(hivList);
        
        
        nutritionalAssessmentList.add(ind.getIndicatorForNumberOfSeverelyMalnourishedOvcAtBaseline());
        nutritionalAssessmentList.add(ind.getIndicatorForNumberOfModeratelyNourishedOvcAtBaseline());
        nutritionalAssessmentList.add(ind.getIndicatorForNumberOfWellNourishedOvcAtBaseline());
        nutritionalAssessmentList.add(ind.getIndicatorForNumberOfSeverelyMalnourishedOvcCurrently());
        nutritionalAssessmentList.add(ind.getIndicatorForNumberOfModeratelyNourishedOvcCurrently());
        nutritionalAssessmentList.add(ind.getIndicatorForNumberOfWellNourishedOvcCurrently());
        mainList.add(nutritionalAssessmentList);
        
        ovcHhLinkageList.add(ind.getIndicatorForNumberOfOvcEnrolledFromHouseholdsWithChronicallyIllMembers());    
        ovcHhLinkageList.add(ind.getIndicatorForNumberOfOvcCurrentlyEnrolledInHouseholdsWithBaselineAssessment());
        ovcHhLinkageList.add(ind.getIndicatorForNumberOfHivPosOvcCurrentlyEnrolledThatAreFromVulnerableHouseholdsAtBaseline());
        ovcHhLinkageList.add(ind.getIndicatorForNumberOfHivPosOvcCurrentlyEnrolledThatAreFromMoreVulnerableHouseholdsAtBaseline());
        ovcHhLinkageList.add(ind.getIndicatorForNumberOfHivPosOvcCurrentlyEnrolledThatAreFromMostVulnerableHouseholdsAtBaseline());
        ovcHhLinkageList.add(ind.getIndicatorForNumberOfOvcThatShowedAtleastOneScoreImprovement());  
        ovcHhLinkageList.add(ind.getIndicatorForNumberOfOvcScreenedForTB());
        
        
        mainList.add(ovcHhLinkageList);
     
        ovcServiceList.add(ind.getIndicatorForNumberOfOvcProvidedWithAtleastOneService());
        ovcServiceList.add(ind.getIndicatorForNumberOfNewOvcEnrolledAndServedWithinTheReportPeriod());
        ovcServiceList.add(ind.getIndicatorForNumberOfOvcCurrentlyEnrolledAndServedInReportPeriod());
        
        ovcServiceList.add(ind.getIndicatorForNumberOfOvcWithoutServiceRecords());
        ovcServiceList.add(ind.getIndicatorForNumberOfOvcGraduatedButServedInReportPeriod());
        ovcServiceList.add(ind.getIndicatorForNumberOfOvcLostToFollowupButServedInReportPeriod());
        ovcServiceList.add(ind.getIndicatorForNumberOfOvcMigratedButServedInReportPeriod());
        ovcServiceList.add(ind.getIndicatorForNumberOfOvcAgedoutButServedInReportPeriod());
        ovcServiceList.add(ind.getIndicatorForNumberOfOvcTransferedOutButServedInReportPeriod());
        ovcServiceList.add(ind.getIndicatorForNumberOfOvcDiedButServedInReportPeriod());
        ovcServiceList.add(ind.getIndicatorForNumberOfOvcVoluntarilyWithdrawnButServedInReportPeriod());
        ovcServiceList.add(ind.getIndicatorForNumberOfOvcInactiveButServedInReportPeriod());
        ovcServiceList.add(ind.getIndicatorForNumberOfOvcExitedWithoutGraduation());
        ovcServiceList.add(ind.getIndicatorForNumberOfOvcProvidedThreeOrMoreServices());
        ovcServiceList.add(ind.getIndicatorForNumberOfOvcProvidedLessThanThreeServices());
        ovcServiceList.add(ind.getIndicatorForNumberOfOvcThatParticipatedInRecreationalActivityWithinReportPeriod());
        
        mainList.add(ovcServiceList);
        
        ovcServiceListByDomain.add(ind.getIndicatorForNumberOfOvcProvidedPsychosocialSupport());
        ovcServiceListByDomain.add(ind.getIndicatorForNumberOfOvcProvidedNutritionalSupport());
        ovcServiceListByDomain.add(ind.getIndicatorForNumberOfOvcProvidedHealthService());
        ovcServiceListByDomain.add(ind.getIndicatorForNumberOfOvcProvidedEducationalSupport());
        ovcServiceListByDomain.add(ind.getIndicatorForNumberOfOvcProvidedProtectionServices());
        ovcServiceListByDomain.add(ind.getIndicatorForNumberOfOvcProvidedShelterAndCareServices());
        ovcServiceListByDomain.add(ind.getIndicatorForOVC_ACCPerMonthByCBO());
        ovcServiceListByDomain.add(ind.getIndicatorForNumberOfOvcEnrolledInKidClubWithinReportPeriod());
        mainList.add(ovcServiceListByDomain);
        
        ovcServiceListByHiv.add(ind.getIndicatorForNumberOfHivPositiveOvcProvidedWithAtleastOneService());
        ovcServiceListByHiv.add(ind.getIndicatorForNumberOfHivNegativeOvcProvidedWithAtleastOneService());
        ovcServiceListByHiv.add(ind.getIndicatorForNumberOfHivUnknownOvcProvidedWithAtleastOneService());
        
        ovcServiceListByHiv.add(ind.getIndicatorForNumberOfHivPositiveOvcCurrentlyEnrolledProvidedWithAtleastOneService());
        ovcServiceListByHiv.add(ind.getIndicatorForNumberOfHivPositiveOvcOnARTCurrentlyEnrolledProvidedWithAtleastOneService());
        ovcServiceListByHiv.add(ind.getIndicatorForNumberOfHivNegativeOvcCurrentlyEnrolledProvidedWithAtleastOneService());
        ovcServiceListByHiv.add(ind.getIndicatorForNumberOfHivUnknownOvcCurrentlyEnrolledProvidedWithAtleastOneService());
        
        ovcServiceListByHiv.add(ind.getIndicatorForNumberOfHivPositiveOvcServedButGraduatedWithinTheReportPeriod());
        ovcServiceListByHiv.add(ind.getIndicatorForNumberOfHivPositiveOvcOnARTServedButGraduatedWithinTheReportPeriod());
        ovcServiceListByHiv.add(ind.getIndicatorForNumberOfHivNegativeOvcServedButGraduatedWithinTheReportPeriod());
        ovcServiceListByHiv.add(ind.getIndicatorForNumberOfHivUnknownOvcServedButGraduatedWithinTheReportPeriod());
        
        ovcServiceListByHiv.add(ind.getIndicatorForNumberOfHivPosOvcProvidedNutritionalSupport());
        ovcServiceListByHiv.add(ind.getIndicatorForNumberOfHivNegOvcProvidedNutritionalSupport());
        ovcServiceListByHiv.add(ind.getIndicatorForNumberOfHivUnknOvcProvidedNutritionalSupport());
        ovcServiceListByHiv.add(ind.getIndicatorForNumberOfHivPosOvcProvidedHealthServices());
        ovcServiceListByHiv.add(ind.getIndicatorForNumberOfHivNegOvcProvidedHealthServices());
        ovcServiceListByHiv.add(ind.getIndicatorForNumberOfHivUnknOvcProvidedHealthServices());
        ovcServiceListByHiv.add(ind.getIndicatorForNumberOfHivPosOvcProvidedEducationalServices());
        ovcServiceListByHiv.add(ind.getIndicatorForNumberOfHivNegOvcProvidedEducationalServices());
        ovcServiceListByHiv.add(ind.getIndicatorForNumberOfHivUnknOvcProvidedEducationalServices());
        ovcServiceListByHiv.add(ind.getIndicatorForNumberOfHivPosOvcProvidedPsychosocialServices());
        ovcServiceListByHiv.add(ind.getIndicatorForNumberOfHivNegOvcProvidedPsychosocialServices());
        ovcServiceListByHiv.add(ind.getIndicatorForNumberOfHivUnknOvcProvidedPsychosocialServices());
        ovcServiceListByHiv.add(ind.getIndicatorForNumberOfHivPosOvcProvidedProtectionServices());
        ovcServiceListByHiv.add(ind.getIndicatorForNumberOfHivNegOvcProvidedProtectionServices());
        ovcServiceListByHiv.add(ind.getIndicatorForNumberOfHivUnknOvcProvidedProtectionServices());
        ovcServiceListByHiv.add(ind.getIndicatorForNumberOfHivPosOvcProvidedShelterAndCareServices());
        ovcServiceListByHiv.add(ind.getIndicatorForNumberOfHivNegOvcProvidedShelterAndCareServices());
        ovcServiceListByHiv.add(ind.getIndicatorForNumberOfHivUnknOvcProvidedShelterAndCareServices());
        ovcServiceListByHiv.add(ind.getIndicatorForNumberOfHivPosOvcProvidedEconomicStrengtheningServices());
        ovcServiceListByHiv.add(ind.getIndicatorForNumberOfHivNegOvcProvidedEconomicStrengtheningServices());
        ovcServiceListByHiv.add(ind.getIndicatorForNumberOfHivUnknOvcProvidedEconomicStrengtheningServices());
        
        
        mainList.add(ovcServiceListByHiv);
        
        ovcInHhServedHESList.add(ind.getIndicatorForNumberOfAdolescentsCurrentlyEnrolledWhoseHouseholdsProvidedEconomicStrengthening());
        ovcInHhServedHESList.add(ind.getIndicatorForNumberOfAdolescentsWhoseHouseholdsProvidedEconomicStrengthening());
        ovcInHhServedHESList.add(ind.getIndicatorForNumberOfOvc0to19CurrentlyEnrolledWhoseHouseholdsProvidedEconomicStrengthening());
        ovcInHhServedHESList.add(ind.getIndicatorForNumberOfOvc0to19WhoseHouseholdsProvidedEconomicStrengthening());
        mainList.add(ovcInHhServedHESList);
        
        
        caregiverList.add(ind.getIndicatorForNumberOfCaregiversNewlyEnrolled());
        caregiverList.add(ind.getIndicatorForNumberOfCaregiversCurrentlyEnrolled());
        caregiverList.add(ind.getIndicatorForNumberOfCaregiversEverEnrolled());
        caregiverList.add(ind.getIndicatorForNumberOfCaregiversGraduated());
        caregiverList.add(ind.getIndicatorForNumberOfCaregiversWithdrawnDueToMigration());
        caregiverList.add(ind.getIndicatorForNumberOfCaregiversWithdrawnDueToLostToFollowup());
        caregiverList.add(ind.getIndicatorForNumberOfCaregiversWithdrawnDueToKnownDeath());
        caregiverList.add(ind.getIndicatorForNumberOfCaregiversWithdrawnDueToTransfer());
        caregiverList.add(ind.getIndicatorForNumberOfCaregiversDeclaredInactive());
        caregiverList.add(ind.getIndicatorForNumberOfCaregiversScreenedForTB());
        mainList.add(caregiverList);
        
        caregiverHivList.add(ind.getIndicatorForNumberOfHIVPositiveCaregiversCurrentlyEnrolled());
        caregiverHivList.add(ind.getIndicatorForNumberOfHIVNegativeCaregiversCurrentlyEnrolled());
        caregiverHivList.add(ind.getIndicatorForNumberOfHIVUnknownCaregiversCurrentlyEnrolled());
        caregiverHivList.add(ind.getIndicatorForNumberOfHIVPositiveCaregiversEverEnrolled());
        caregiverHivList.add(ind.getIndicatorForNumberOfHIVNegativeCaregiversEverEnrolled());
        caregiverHivList.add(ind.getIndicatorForNumberOfHIVUnknownCaregiversEverEnrolled());
        caregiverHivList.add(ind.getIndicatorForNumberOfHIVPositiveCaregiversCurrentlyEnrolledInCare());
        caregiverHivList.add(ind.getIndicatorForNumberOfHIVPositiveCaregiversCurrentlyEnrolledOnART());
        caregiverHivList.add(ind.getIndicatorForNumberOfCaregiversSupportedToAccessHIVServices());
        caregiverHivList.add(ind.getIndicatorForNumberOfHivPosCaregiversCurrentlyEnrolledThatAreFromVulnerableHouseholdsAtBaseline());
        caregiverHivList.add(ind.getIndicatorForNumberOfHivPosCaregiversCurrentlyEnrolledThatAreFromMoreVulnerableHouseholdsAtBaseline());
        caregiverHivList.add(ind.getIndicatorForNumberOfHivPosCaregiversCurrentlyEnrolledThatAreFromMostVulnerableHouseholdsAtBaseline());
        caregiverHivList.add(ind.getIndicatorForNumberOfCaregiversTestedForHiv());
        mainList.add(caregiverHivList);
        caregiverServiceList.add(ind.getIndicatorForNumberOfCaregiversProvidedAtleastOneService());
        caregiverServiceList.add(ind.getIndicatorForNumberOfNewlyEnrolledCaregiversServedWithinTheReportPeriod());
        caregiverServiceList.add(ind.getIndicatorForNumberOfActiveCaregiversServedWithinTheReportPeriod());
        caregiverServiceList.add(ind.getIndicatorForNumberOfCaregiversWithoutServiceRecords());
        caregiverServiceList.add(ind.getIndicatorForNumberOfCaregiversServedAndGraduatedWithinTheReportPeriod());
        caregiverServiceList.add(ind.getIndicatorForNumberOfCaregiversServedAndLostToFollowupWithinTheReportPeriod());
        caregiverServiceList.add(ind.getIndicatorForNumberOfCaregiversServedAndDiedWithinTheReportPeriod());
        caregiverServiceList.add(ind.getIndicatorForNumberOfCaregiversServedAndMigratedWithinTheReportPeriod());
        caregiverServiceList.add(ind.getIndicatorForNumberOfCaregiversServedAndTransferedWithinTheReportPeriod());
        caregiverServiceList.add(ind.getIndicatorForNumberOfInactiveCaregiversServedInReportPeriod());
        caregiverServiceList.add(ind.getIndicatorForNumberOfCaregiversExitedWithoutGraduation());
        caregiverServiceList.add(ind.getIndicatorForNumberOfCaregiversProvidedHES());
        
        caregiverServiceList.add(ind.getIndicatorForNumberOfCaregiversEnrolledInSILCWithinReportPeriod());
        caregiverServiceList.add(ind.getIndicatorForNumberOfCaregiversEnrolledInCaregiverForumWithinReportPeriod());
        mainList.add(caregiverServiceList);
        
        
                        
        householdList.add(ind.getIndicatorForNumberOfHouseholdsNewlyEnrolled());
        householdList.add(ind.getIndicatorForNumberOfHouseholdsCurrentlyEnrolled());
        householdList.add(ind.getIndicatorForNumberOfHouseholdsEverEnrolled());
        householdList.add(ind.getIndicatorForNumberOfHouseholdsWithdrawnDueToGraduation());
        householdList.add(ind.getIndicatorForNumberOfHouseholdsWithdrawnDueToMigration());
        householdList.add(ind.getIndicatorForNumberOfHouseholdsWithdrawnDueToLostToFollowup());
        householdList.add(ind.getIndicatorForNumberOfHouseholdsWithdrawnDueToTransfer());
        householdList.add(ind.getIndicatorForNumberOfHouseholdsDeclaredInactive());
        householdList.add(ind.getIndicatorForNumberOfHouseholdsServed());
        householdList.add(ind.getIndicatorForNumberOfHouseholdsProvidedHES());
        mainList.add(householdList);
        
        householdListByBaselineAssessment.add(ind.getIndicatorForNumberOfHouseholdsCurrentlyEnrolledThatAreVulnerableAtBaseline());
        householdListByBaselineAssessment.add(ind.getIndicatorForNumberOfHouseholdsCurrentlyEnrolledThatAreMoreVulnerableAtBaseline());
        householdListByBaselineAssessment.add(ind.getIndicatorForNumberOfHouseholdsCurrentlyEnrolledThatAreMostVulnerableAtBaseline());
        householdListByBaselineAssessment.add(ind.getIndicatorForNumberOfHouseholdsCurrentlyEnrolledWithBaselineAssessment());
        householdListByBaselineAssessment.add(ind.getIndicatorForNumberOfHouseholdsEverEnrolledWithBaselineAssessment());
        householdListByBaselineAssessment.add(ind.getIndicatorForNumberOfHouseholdsWithBaselineAssessmentWithinTheReprtPeriod());
        householdListByBaselineAssessment.add(ind.getIndicatorForNumberOfHouseholdsCurrentlyEnrolledWithBaselineAssessmentWithinTheReprtPeriod());
        householdListByBaselineAssessment.add(ind.getIndicatorForNumberOfHouseholdsCurrentlyEnrolledWithFollowupAssessment());
        householdListByBaselineAssessment.add(ind.getIndicatorForNumberOfHouseholdsEverEnrolledWithFollowupAssessment());
        mainList.add(householdListByBaselineAssessment);
        
        newMERList.add(ind.getIndicatorForNumberOfBeneficiariesNewlyEnrolledWithinTheReportPeriod());
        newMERList.add(ind.getNoOfOvcAssessedForHIVRiskAndServedWithinReportPeriod());
        newMERList.add(ind.getNoOfHivNegativeOvcAssessedForHIVRiskAndServedWithinReportPeriod());
        newMERList.add(ind.getNoOfHivUnknownOvcAssessedForHIVRiskAndServedWithinReportPeriod());
        newMERList.add(ind.getIndicatorForNumberOfOvcTestedAndReceivedResult());
        newMERList.add(ind.getIndicatorForNumberOfOvcNewlyTestedPositiveAndLinkedToTreatment());
        newMERList.add(ind.getIndicatorForNumberOfOvcSelfReportingAdherenceToTreatment());
        newMERList.add(ind.getIndicatorForNumberOfOvcRegularlyAttendingSchool());
        newMERList.add(ind.getIndicatorNumberOfHouseholdsThatCanSolveEmergencyNeedsWithinReportingPeriod());
        newMERList.add(ind.getIndicatorForNumberOfOvcAssessedForHivRiskAndDeterminedToBeAtRisk());
        newMERList.add(ind.getIndicatorForNumberOfOvcLessThan18CurrentlyEnrolledWithBirthCertificate());
        newMERList.add(ind.getIndicatorForNumberOfSeverelyMalnourishedOvcServedNutritonalServices());
        newMERList.add(ind.getIndicatorNumberOfHouseholdsWhoseOvcWereServedWithinReportingPeriod());
        newMERList.add(ind.getIndicatorNumberOfGraduatedHouseholdsWhoseOvcWereServedWithinReportingPeriod());
        newMERList.add(ind.getIndicatorForNumberOfOVC_HIVSTATPOSITIVE());
        newMERList.add(ind.getIndicatorForNumberOfOVC_HIVSTATNEGATIVE());
        newMERList.add(ind.getIndicatorForNumberOfOVC_HIVSTATUNKNOWN());
        newMERList.add(ind.getIndicatorForNumberOfOvcLinkedToGovtForPostViolenceServicesWithinReportPeriod());
        newMERList.add(ind.getIndicatorForNumberOfAdolescentsProvidedHIVPreventionServices());
        mainList.add(newMERList);
        
        return mainList;
    }
}
