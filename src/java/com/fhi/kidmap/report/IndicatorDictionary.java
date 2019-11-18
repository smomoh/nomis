/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report;

import com.fhi.kidmap.business.Indicators;
import com.fhi.nomis.nomisutils.NomisConstant;
import java.io.Serializable;

/**
 *
 * @author smomoh
 */
public class IndicatorDictionary implements Serializable
{
    
    //Number of school aged children enrolled in the OVC program who are regularly (defined as not missing more than 5 days in a month of uninterrupted academic or vocational training session) attending school or vocational training
    public Indicators getIndicatorForNumberOfAdolescentsProvidedHIVPreventionServices()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vchivprevrp");
        indicator.setIndicatorName("Number of OVC (aged 10-17) that received adolescent HIV prevention and sexual reproductive health services");
        indicator.setAlternateName("Number of OVC (aged 10-17) that received adolescent HIV prevention and sexual reproductive health services");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_HIVPREV");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOVC_HIVSTATPOSITIVE()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vchivstapos");
        indicator.setIndicatorName("OVC_HIVSTAT POSITIVE");
        indicator.setAlternateName("OVC (<18) positive (including known +ve)");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_HIVSTAT");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOVC_HIVSTATNEGATIVE()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vchivstaneg");
        indicator.setIndicatorName("OVC_HIVSTAT NEGATIVE");
        indicator.setAlternateName("Reported HIV Negative");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_HIVSTAT");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOVC_HIVSTATUNKNOWN()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vchivstaunk");
        indicator.setIndicatorName("OVC_HIVSTAT UNKNOWN");
        indicator.setAlternateName("No Reported HIV Status");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_HIVSTAT");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcExitedWithoutGraduation()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcexitnogra");
        indicator.setIndicatorName("Number of OVC exited without graduation");
        indicator.setAlternateName("Exited without Graduation");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_SERV");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcRegularlyAttendingSchool()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcschattend");
        indicator.setIndicatorName("Number of school aged children enrolled in the OVC program who are regularly (defined as not missing more than 5 days in a month of uninterrupted academic or vocational training session) attending school or vocational training within the report period (OVC_EDU)");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setAlternateName("OVC school aged (5-17 years) regularly attending school/vocational training");
        indicator.setMerCode("OVC_EDU");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcSelfReportingAdherenceToTreatment()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcadherence");
        indicator.setIndicatorName("Number of HIV positive OVC on treatment self-reporting adherence to treatment within the report period (OVC_ART_SUPP)");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setAlternateName("OVC reporting adherence to treatment");
        indicator.setMerCode("OVC_ART_SUPP");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcNewlyTestedPositiveAndLinkedToTreatment()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcnewposart");
        indicator.setIndicatorName("Number of Newly tested HIV positive OVC successfully linked to treatment within the report period (OVC_TXLINK)");
        indicator.setAlternateName("OVC Newly tested Positive (<18) and linked to treatment in Report Period");
        indicator.setMerCode("OVC_TXLINK");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcTestedAndReceivedResult()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vctstresult");
        indicator.setIndicatorName("Number of OVC 0-17 years who got tested and received result within the report period (OVC_HTSLINK)");
        indicator.setAlternateName("Tested and received result within the report period");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_HTSLINK");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcThatShowedAtleastOneScoreImprovement()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vc1scorimpr");
        indicator.setIndicatorName("Number of OVC that showed at least one score improvement in any VC service area");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
   
    public Indicators getIndicatorForNumberOfOvcWithdrawn()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcwithdrawn");
        indicator.setIndicatorName("Number of <b>OVC Withdrawn</b> from the program");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    
    public Indicators getIndicatorForNumberOfOvcCurrentlyInSchool()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vccurrinsch");
        indicator.setIndicatorName("Number of OVC currently in the program who are in school");
        indicator.setAlternateName("OVC school aged (5-17 years) in the program");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_EDU");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcOutOfSchool()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcnotschool");
        indicator.setIndicatorName("Number of OVC currently in the program who are out of school");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_EDU");
        return indicator;
    }
    
    public Indicators getIndicatorForNumberOfOvcProvidedThreeOrMoreServices()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("gt3services");
        indicator.setIndicatorName("Number of OVC that received three or more services");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcProvidedLessThanThreeServices()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("lt3services");
        indicator.setIndicatorName("Number of OVC that received less than three services");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    
    public Indicators getIndicatorForNumberOfOvcFollowedup()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("noOfOvcFollowedUp");
        indicator.setIndicatorName("Number of OVC followed up");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    
    public Indicators getIndicatorForNumberOfOvcNotFollowedup()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("noOfOvcNotFollowedUp");
        indicator.setIndicatorName("Number of OVC not followed up");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivPositiveOvcAtFollowedup()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("noOfHivPositiveOvcFollowedUp");
        indicator.setIndicatorName("Number of HIV positive OVC followed up");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivNegativeOvcAtFollowedup()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("noOfHivNegativeOvcFollowedUp");
        indicator.setIndicatorName("Number of HIV negative OVC followed up");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivUnknownOvcAtFollowedup()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("noOfHivUnknownOvcFollowedUp");
        indicator.setIndicatorName("Number of HIV unknown OVC followed up");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcAssessedForHivRiskAndDeterminedToBeAtRisk()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vchivatrisk");
        indicator.setIndicatorName("Numbers of children assessed for HIV risk and determined to be at Risk of HIV (OVC_HIVRISKASS)");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_HIVRISKASS");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcAssessedForHivRiskAndReferredForTesting()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vchivrskref");
        indicator.setIndicatorName("Numbers of children assessed for HIV risk and referred for testing");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcNeverAssessedForHivRisk()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcnashivrsk");
        indicator.setIndicatorName("Numbers of children Never assessed for HIV risk");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    /*public Indicators getIndicatorForNumberOfOvcAssessedForHivRiskWithinTheReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcasshivrsk");
        indicator.setIndicatorName("Numbers of children assessed for HIV risk within the report period");
        return indicator;
    }*/
    public Indicators getIndicatorForNumberOfCaregiversExitedWithoutGraduation()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("cgexitnogra");
        indicator.setIndicatorName("Number of Caregivers exited without graduation");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_EDU");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfCaregiversExitedWithoutGraduationByAge()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("cgexnograge");
        indicator.setIndicatorName("Number of Caregivers exited without graduation (by age)");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_EDU");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcWhoseCaregiversRefusedToDiscloseTheirHivStatusWithinTheReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vccgrdclhiv");
        indicator.setIndicatorName("Number of OVC whose caregiver refused to Disclose children HIV status within the report period");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcWhoseCaregiversKnowTheirHivStatusWithinTheReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vccgknhivst");
        indicator.setIndicatorName("Number of OVC whose caregiver know their HIV status within the report period");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcThatParticipatedInRecreationalActivityWithinReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcrecactyrp");
        indicator.setIndicatorName("Number of OVC that participated in Recreational activity within the report period");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcEnrolledInKidClubWithinReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vckidsclbrp");
        indicator.setIndicatorName("Number of OVC enrolled in Kids club within the report period");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcAbusedWithinReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcabusedrpe");
        indicator.setIndicatorName("Number of OVC with a demonstrated and/or documented case of violence, exploitation or neglect within the report period");
        indicator.setMerCode("OVC_PROTECT");
        indicator.setAlternateName("Number of OVC <18 abused/neglected/exploited within the report period");
        //indicator.setIndicatorName("Number of OVC linked to Government for Post-Violence Services within the report period");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcLinkedToGovtForPostViolenceServicesWithinReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vclgovptvrp");
        indicator.setIndicatorName("Number of OVC with a demonstrated and/or documented case of violence, exploitation or neglect who have been successfully referred to Government of Nigeria Social Welfare and other post-violence and child protection services");
        indicator.setMerCode("OVC_PROTECT");
        indicator.setAlternateName("Number of OVC <18 abused/neglected/exploited successfully linked to a GON Social Welfare and other post-violence and child protection services");
        //indicator.setIndicatorName("Number of OVC linked to Government for Post-Violence Services within the report period");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcGraduated()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcgraduated");
        indicator.setIndicatorName("Number of OVC graduated from the program within the reporting period");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcWithdrawnDueToKnownDeath()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcknowndeat");
        indicator.setIndicatorName("Number of OVC known to have died within the reporting period");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcWithdrawnDueToAgingOut()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("ovcagingout");
        indicator.setIndicatorName("Number of OVC withdrawn from the program (Aging out) within the reporting period");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcWithdrawnDueToMigration()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("ovcmigrated");
        indicator.setIndicatorName("Number of OVC migrated within the reporting period");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcWithdrawnDueToLostToFollowup()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vclosttofup");
        indicator.setIndicatorName("Number of OVC lost to follow up within the reporting period");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    
    public Indicators getIndicatorForNumberOfOvcWithdrawnDueToTransfer()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vctransferd");
        indicator.setIndicatorName("Number of OVC transfered from the program within the reporting period");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcDeclaredInactive()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcinactivrp");
        indicator.setIndicatorName("Number of OVC declared inactive and removed from the program within the reporting period");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcVoluntarilyWithdrawnFromTheProgramReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcvolleftrp");
        indicator.setIndicatorName("Number of OVC who voluntarily withdrew from the program within the report period ");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setDescription("This indicator counts the number of OVC that voluntarily withdrew from the program within the report period");
        return indicator;
    }
    public Indicators getIndicatorForOVC_ACCPerMonthByCBO()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vchivaccess");
        indicator.setIndicatorName("Number of active OVC beneficiaries receiving support from PEPFAR OVC programs to access HIV services (OVC_ACC)");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForProportionOfHivPosOvcCurrentlyEnrolled()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vchivpprpce");
        indicator.setIndicatorName("Proportion of currently enrolled OVC who are HIV positive");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForProportionOfHivNegOvcCurrentlyEnrolled()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vchivnprpce");
        indicator.setIndicatorName("Proportion of currently enrolled OVC who are HIV negative");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForProportionOfHivUnkOvcCurrentlyEnrolled()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vchivuprpce");
        indicator.setIndicatorName("Proportion of currently enrolled OVC whose HIV status is unknown");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfCaregiversEnrolledInCaregiverForumWithinReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("cginforumrp");
        indicator.setIndicatorName("Number of Caregivers enrolled in Caregiver forum within the report period");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfCaregiversEnrolledInSILCWithinReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("cgenrsilcrp");
        indicator.setIndicatorName("Number of Caregivers enrolled in SILC within the report period");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHIVPositiveCaregiversCurrentlyEnrolledInCare()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("cghivcencar");
        indicator.setIndicatorName("Number of HIV positive caregivers currently enrolled in care");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHIVPositiveCaregiversCurrentlyEnrolledOnART()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("cghivcenart");
        indicator.setIndicatorName("Number of HIV positive caregivers currently enrolled on ART");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHIVUnknownCaregiversEverEnrolled()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("cghivunkeen");
        indicator.setIndicatorName("Number of HIV unknown caregivers ever enrolled");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHIVNegativeCaregiversEverEnrolled()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("cghivnegeen");
        indicator.setIndicatorName("Number of HIV negative caregivers ever enrolled");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHIVPositiveCaregiversEverEnrolled()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("cghivposeen");
        indicator.setIndicatorName("Number of HIV positive caregivers ever enrolled");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHIVUnknownCaregiversCurrentlyEnrolled()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("cghivunkcen");
        indicator.setIndicatorName("Number of HIV unknown caregivers currently enrolled");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHIVNegativeCaregiversCurrentlyEnrolled()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("cghivnegcen");
        indicator.setIndicatorName("Number of HIV negative caregivers currently enrolled");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHIVPositiveCaregiversCurrentlyEnrolled()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("cghivposcen");
        indicator.setIndicatorName("Number of HIV positive caregivers currently enrolled");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcCurrentlyEnrolledThatHaveBeenAssessedonHIVRisk()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vccehivrisk");
        indicator.setIndicatorName("Number of OVC currently enrolled that has HIV Risk assessment done");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcEverEnrolledThatHaveBeenAssessedonHIVRisk()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vceehivrisk");
        indicator.setIndicatorName("Number of OVC ever enrolled that has HIV Risk assessment done");
        indicator.setMerCode("OVC_HIVRISKASS");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getNoOfHivNegativeOvcAssessedForHIVRiskAndServedWithinReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcnegrskser");
        indicator.setIndicatorName("Number of HIV Negative OVC assessed for HIV and served within the report period");
        indicator.setAlternateName("Number of OVC <18 with negative HIV status  risk assessed");
        indicator.setMerCode("OVC_HIVRISKASS");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getNoOfHivUnknownOvcAssessedForHIVRiskAndServedWithinReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcunkrskser");
        indicator.setIndicatorName("Number of HIV unknown OVC assessed for HIV and served within the report period");
        indicator.setAlternateName("Number of OVC <18 with unknown HIV status  risk assessed");
        indicator.setMerCode("OVC_HIVRISKASS");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getNoOfOvcAssessedForHIVRiskAndServedWithinReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vchivrskser");
        indicator.setIndicatorName("Number of OVC assessed for HIV and served within the report period");
        indicator.setMerCode("OVC_HIVRISKASS");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
     
    public Indicators getIndicatorForNumberOfHivUnknownOvcEverEnrolledThatHaveBeenAssessedonHIVRisk()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vceeunkrass");
        indicator.setIndicatorName("Number of HIV Unknown OVC ever enrolled assessed for HIV risk");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_HIVRISKASS");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivNegativeOvcEverEnrolledThatHaveBeenAssessedonHIVRisk()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vceenegrass");
        indicator.setIndicatorName("Number of Hiv Negative OVC ever enrolled assessed for HIV risk");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_HIVRISKASS");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcCurrentlyEnrolledThatHasNoHIVRiskAssessmentRecord()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vccenohivra");
        indicator.setIndicatorName("Number of OVC currently enrolled that has no HIV Risk assessment record");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcCurrentlyEnrolledInHouseholdsWithBaselineAssessment()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vccehhblass");
        indicator.setIndicatorName("Number of Ovc enrolled within the report period and currently in the program whose Households has Baseline Assessment");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivPosOvcCurrentlyEnrolledThatAreFromVulnerableHouseholdsAtBaseline()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcposcevulb");
        indicator.setIndicatorName("Number of HIV positive Ovc currently enrolled whose households were vulnerable at baseline");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivPosOvcCurrentlyEnrolledThatAreFromMoreVulnerableHouseholdsAtBaseline()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcposcemrvb");
        indicator.setIndicatorName("Number of HIV positive OVC currently enrolled whose households were more vulnerable at baseline");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivPosOvcCurrentlyEnrolledThatAreFromMostVulnerableHouseholdsAtBaseline()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcposcemovb");
        indicator.setIndicatorName("Number of HIV Positive OVC currently enrolled whose households were most vulnerable at baseline");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivPosCaregiversCurrentlyEnrolledThatAreFromVulnerableHouseholdsAtBaseline()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("cgposcevulb");
        indicator.setIndicatorName("Number of HIV positive caregivers currently enrolled whose households were vulnerable at baseline");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivPosCaregiversCurrentlyEnrolledThatAreFromMoreVulnerableHouseholdsAtBaseline()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("cgposcemrvb");
        indicator.setIndicatorName("Number of HIV positive caregivers currently enrolled whose households were more vulnerable at baseline");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivPosCaregiversCurrentlyEnrolledThatAreFromMostVulnerableHouseholdsAtBaseline()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("cgposcemovb");
        indicator.setIndicatorName("Number of HIV positive caregivers currently enrolled whose households were most vulnerable at baseline");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHouseholdsCurrentlyEnrolledThatAreVulnerableAtBaseline()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("hhcevulbase");
        indicator.setIndicatorName("Number of households currently enrolled that are vulnerable at baseline");
        indicator.setIndicatorType(NomisConstant.HOUSEHOLD_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHouseholdsCurrentlyEnrolledThatAreMoreVulnerableAtBaseline()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("hhcemrvulba");
        indicator.setIndicatorName("Number of households currently enrolled that are more vulnerable at baseline");
        indicator.setIndicatorType(NomisConstant.HOUSEHOLD_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHouseholdsCurrentlyEnrolledThatAreMostVulnerableAtBaseline()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("hhcemtvulba");
        indicator.setIndicatorName("Number of households currently enrolled that are most vulnerable at baseline");
        indicator.setIndicatorType(NomisConstant.HOUSEHOLD_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHouseholdsProvidedHES()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("hhdserhesrp");
        indicator.setIndicatorName("Number of households provided economic strengthening services within the reporting period");
        indicator.setIndicatorType(NomisConstant.HOUSEHOLD_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHouseholdsServed()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("hhdservedrp");
        indicator.setIndicatorName("Number of households served within the reporting period");
        indicator.setIndicatorType(NomisConstant.HOUSEHOLD_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvc0to19CurrentlyEnrolledWhoseHouseholdsProvidedEconomicStrengthening()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcce019hhes");
        indicator.setIndicatorName("Number of OVC (0 - 19 years) currently enrolled in the Program whose families have been empowered with Household Economic Strengthening (HES)  Interventions (excludes those out of the program)");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvc0to19WhoseHouseholdsProvidedEconomicStrengthening()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcee019hhes");
        indicator.setIndicatorName("Number of OVC (0 - 19 years) enrolled whose families have been empowered with Household Economic Strengthening (HES)  Interventions (includes those out of the program)");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfAdolescentsWhoseHouseholdsProvidedEconomicStrengthening()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcadolshhes");
        indicator.setIndicatorName("Number of Adolescents (10 - 19 years) enrolled whose families have been empowered with Household Economic Strengthening (HES)  Interventions (includes those out of the program)");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfAdolescentsCurrentlyEnrolledWhoseHouseholdsProvidedEconomicStrengthening()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcceadohhes");
        indicator.setIndicatorName("Number of Adolescents (10 - 19 years) currently enrolled in the Program whose families have been empowered with Household Economic Strengthening (HES)  Interventions (excludes those out of the program)");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcEnrolledInCare()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vchivincare");
        indicator.setIndicatorName("Number of OVC enrolled in HIV care");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcReferredForHIVCare()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vchivreferr");
        indicator.setIndicatorName("Number of OVC referred for HIV care");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcEnrolledFromHouseholdsWithChronicallyIllMembers()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vchivillmem");
        indicator.setIndicatorName("Number of OVC enrolled from households with chronically ill or HIV positive members");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    /*public Indicators getIndicatorForNumberOfOvcReferredForHivTestAndReceivedResult()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcrefforhiv");
        indicator.setIndicatorName("Number of OVC (<18 years old) with unknown/undisclosed HIV status or previously documented HIV-negative status  referred for HIV testing services");
        indicator.setAlternateName("Number of OVC (<18 years old) with unknown/undisclosed HIV status or previously documented HIV-negative status  referred for HIV testing services");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_HTSLINK");
        return indicator;
    }*/
    public Indicators getIndicatorForNumberOfOvcTestedForHIV()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vctestedhiv");
        indicator.setIndicatorName("Number of OVC tested for HIV");
        indicator.setAlternateName("OVC Newly tested in Reporting Period");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_HTSLINK");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcScreenedForTB()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcscrnfortb");
        indicator.setIndicatorName("Number of OVC screened for TB");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    
    public Indicators getIndicatorForNumberOfOvcWhoAreOverweightAtBaseline()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcbmi25to29");
        indicator.setIndicatorName("Number of OVC that are over weight (BMI 25 - 29.9) at baseline");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcThatAreObesseAtBaseline()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcbmi30to40");
        indicator.setIndicatorName("Number of OVC that are obesse (BMI 30 - 40) at baseline");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcThatAreMorbidityObesseAtBaseline()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcwtbmigt40");
        indicator.setIndicatorName("Number of OVC that are morbidity obesse (BMI > 40) at baseline");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfSeverelyMalnourishedOvcAtBaseline()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcmuaclth11");
        indicator.setIndicatorName("Number of OVC that are severely malnourished (MUAC <11.5cm) at baseline");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfModeratelyNourishedOvcAtBaseline()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcmua11to12");
        indicator.setIndicatorName("Number of OVC that are moderately nourished (MUAC 11.5cm - 12.5cm) at baseline");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfWellNourishedOvcAtBaseline()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcmuacgth12");
        indicator.setIndicatorName("Number of OVC that are well nourished (MUAC > 12.5cm) at baseline");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    
    public Indicators getIndicatorForNumberOfSeverelyMalnourishedOvcCurrently()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vccmuaclt11");
        indicator.setIndicatorName("Number of OVC that are severely malnourished (MUAC <11.5cm) currently");
        indicator.setAlternateName("Malnourished OVC (<18)");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_NUTRITION");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfSeverelyMalnourishedOvcServedNutritonalServices()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcsmalnsern");
        indicator.setIndicatorName("Number of severely malnourished OVC provided nutritional services");
        indicator.setAlternateName("Malnourished OVC (<18) linked to Nutrition");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_NUTRITION");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfModeratelyNourishedOvcCurrently()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vccmuac1112");
        indicator.setIndicatorName("Number of OVC that are moderately nourished (MUAC 11.5cm - 12.5cm) currently");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfWellNourishedOvcCurrently()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vccmuacgt12");
        indicator.setIndicatorName("Number of OVC that are well nourished (MUAC > 12.5cm) currently");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    
    public Indicators getIndicatorForNumberOfOvcWhoAreMostVulnerableAtBaseline()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("mostVulnerableOvc");
        indicator.setIndicatorName("Number of OVC who are most vulnerable at baseline (total CSI below 13)");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcWhoAreMoreVulnerableAtBaseline()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("moreVulnerableOvc");
        indicator.setIndicatorName("Number of OVC who are more vulnerable at baseline  (total CSI between 13 and 24)");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcWhoAreVulnerableAtBaseline()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vulnerableOvc");
        indicator.setIndicatorName("Number of OVC who are vulnerable at baseline  (total CSI above 24)");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcVulnerableToHIV_AIDS()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcVulnerableToHiv");
        indicator.setIndicatorName("Number of OVC vulnerable to HIV/AIDS and other adversities");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcSickWithLimitedAccessToHealthCare()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcSickWithLtdAccessToHealthCare");
        indicator.setIndicatorName("Number of OVC frequently sick status and limited access to health care");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcSickWithNoAccessToHealthCare()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcSickWithNoAccessToHealthCare");
        indicator.setIndicatorName("Number of OVC frequently sick with no access to health care");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcInChildHeadedHouseholds()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcInChildHeadedHouseholds");
        indicator.setIndicatorName("Number of OVC who are in child-headed households");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    
    
    public Indicators getIndicatorForNumberOfOvcNewlyEnrolledWithBirthCertificateAtBaseline()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcnebslbcrt");
        indicator.setIndicatorName("Number of Ovc newly enrolled within the report period who had birth certificate at baseline(includes those out of the program)");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcCurrentlyEnrolledWithBirthCertAtBaseline()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vccebslbcrt");
        indicator.setIndicatorName("Total Number of Ovc currently enrolled in the program who had birth certificate at baseline");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcEverEnrolledWithBirthCertificateAtBaseline()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vceebslbcrt");
        indicator.setIndicatorName("Total number of Ovc ever enrolled in the program who had birth certificate at baseline");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcProvidedBirthCertWithinReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcserbtctrp");
        indicator.setIndicatorName("Number of Ovc provided birth certificate within the report period(includes those out of the program)");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getNoOfOvcServedWithinTheReportPeriodThatHasBirthCert()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcceserbcer");
        indicator.setIndicatorName("Number of Ovc currently enrolled served within the report period that has brith certificate");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_BIRTHCERT");
        //indicator.setAlternateName("Number of OVC with a birth certificate");
        indicator.setAlternateName("OVC (<18) with Birth Certificate");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcCurrentlyEnrolledWithBirthCertificate()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vccebthcert");
        indicator.setIndicatorName("Number of Ovc currently enrolled that has birth certificate");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcLessThan18CurrentlyEnrolledWithBirthCertificate()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vccelt18cer");
        indicator.setIndicatorName("Number of Ovc less than 18 years currently enrolled that has birth certificate (OVC_BIRTHCERT)");
        indicator.setMerCode("OVC_BIRTHCERT");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcEverEnrolledWithBirthCertificate()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vceebthcert");
        indicator.setIndicatorName("Number of Ovc ever enrolled that has birth certificate");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcProvidedBirthCertificateServicesAfterEnrollment()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcWithBirthCertAfterEnrollmentPerMth");
        indicator.setIndicatorName("Number of Ovc provided birth registration services after enrollment");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    
    public Indicators getIndicatorForNumberOfOvcWithoutBirthCertificateAtBaseline()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcwithnobir");
        indicator.setIndicatorName("Number of OVC without birth certificate at baseline");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    
    public Indicators getIndicatorForNumberOfOvcWithoutBirthCertificateCurrently()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcwithnobcc");
        indicator.setIndicatorName("Number of OVC without birth certificate that are currently enrolled in the program (excludes those out of the program)");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    
    
    public Indicators getIndicatorForNumberOfOvcProvidedLegalServicesAfterEnrollment()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcProvidedLegalServicesPerMth");
        indicator.setIndicatorName("Number of Ovc provided legal services after enrollment");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcWithKnownHIVStatusAtBaseline()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcWithKnownHivStatusAtEnrolledmentPerMth");
        indicator.setIndicatorName("Number of Ovc with known HIV status at enrollment");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivPositiveOvc()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vccurhivpos");
        indicator.setIndicatorName("Number of HIV positive OVC enrolled within the report period");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivPositiveOvcIdentifiedAndServedWithinTheReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vchivposser");
        //indicator.setIndicatorName("Number of HIV positive OVC identified and served within the report period");
        indicator.setIndicatorName("Number of OVC Newly tested Positive in Reporting Period");
        indicator.setAlternateName("OVC Newly tested Positive (<18) in Reporting Period");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_TXLINK");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcNewlyTestedPositiveAndEnrolledOnARTWithinTheReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vchivposart");
        //indicator.setIndicatorName("Number of HIV positive OVC identified and served within the report period");
        indicator.setIndicatorName("Number of OVC Newly tested Positive and enrolled on treatment in Reporting Period");
        indicator.setAlternateName("OVC Newly tested Positive (<18) and enrolled on treatment in Reporting Period");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_TXLINK");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivPositiveOvcIdentifiedWithinTheReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vchivposide");
        indicator.setIndicatorName("Number of HIV positive OVC identified within the report period");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_TXLINK");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivPositiveOvcAtBaseline()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vchivPosbas");
        indicator.setIndicatorName("Number of HIV positive OVC identified at enrollment");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivNegativeOvcAtBaseline()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vchivNegbas");
        indicator.setIndicatorName("Number of HIV negative OVC identified at enrollment");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivUnknownOvcAtBaseline()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vchivUnkbas");
        indicator.setIndicatorName("Number of HIV unknown OVC identified at enrollment");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcOutOfSchoolAtEnrollmentPerMonthByCBO()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("noOfOvcOutOfSchoolAtEnrollment");
        indicator.setIndicatorName("Number of Ovc out of school at baseline");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcEverEnrolledAndInSchoolAtEnrollmentPerMonthByCBO()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcinschaten");
        indicator.setIndicatorName("Number of Ovc in school at baseline");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcCurrentlyAndInSchoolAtEnrollmentPerMonthByCBO()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcceinscenr");
        indicator.setIndicatorName("Number of active Ovc in school at baseline");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcThatDroppedOutOfSchoolPerMonthByCBO()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("noOfOvcThatDroppedOutOfSchool");
        indicator.setIndicatorName("Number of Ovc who dropped out of school");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcServedPerMonthByCBO()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("noOfOvcServedPerMth");
        indicator.setIndicatorName("Number of Ovc provided atleast one service");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHIVNegativeOvcServedPerMonthByCBO()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("noOfHivNegOvcServedPerMth");
        indicator.setIndicatorName("Number of HIV negative Ovc provided atleast one service");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHIVPositiveOvcServedPerMonthByCBO()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("noOfHivPosOvcServedPerMth");
        indicator.setIndicatorName("Number of HIV positive Ovc provided atleast one service");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHIVUnknownOvcServedPerMonthByCBO()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("noOfHivUnknownOvcServedPerMth");
        indicator.setIndicatorName("Number of HIV unknown Ovc provided atleast one service");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    
    public Indicators getIndicatorForNumberOfOvcProvidedHIVServicesPerMonthByCBO()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("noOfOvcServedHivCarePerMth");
        indicator.setIndicatorName("Number of Ovc provided HIV care services");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcProvidedPsychosocialSupportServicesPerMonthByCBO()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("noOfOvcServedPsychoPerMth");
        indicator.setIndicatorName("Number of Ovc provided Psychosocial support services");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    /*public Indicators getIndicatorForNumberOfOvcProvidedNutritionalServicesPerMonthByCBO()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("noOfOvcServedNutritionPerMth");
        indicator.setIndicatorName("Number of Ovc provided nutritional services");
        return indicator;
    }*/
    public Indicators getIndicatorForNumberOfOvcProvidedHealthServicesPerMonthByCBO()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("noOfOvcServedHealthPerMth");
        indicator.setIndicatorName("Number of Ovc provided Health services");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcProvidedEducationalServicesPerMonthByCBO()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("noOfOvcServedEducPerMth");
        indicator.setIndicatorName("Number of Ovc provided Educational services");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcProvidedProtectionServicesPerMonthByCBO()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("noOfOvcServedProtPerMth");
        indicator.setIndicatorName("Number of Ovc provided Protection services");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcProvidedShelterServicesPerMonthByCBO()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("noOfOvcServedShelterPerMth");
        indicator.setIndicatorName("Number of Ovc provided Shelter services");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcProvidedEconomicStrengtheningServicesPerMonthByCBO()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("noOfOvcServedEconStrgthPerMth");
        indicator.setIndicatorName("Number of Ovc provided Economic strengthening services");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOVCWithUpdatedHIVStatusAtFollowup()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcWithUpdatedHivAtFollowup");
        indicator.setIndicatorName("Number of Ovc with updated HIV status at followup");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForHIVPositiveOVCIdentifiedAtAtFollowup()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("hivPosVcIdentifiedAtFollowup");
        indicator.setIndicatorName("Number of HIV positive Ovc identified at followup");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getNoOfOVCWithUpdatedBirthRegistrationAtFollowup()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcWithUpdatedBirthRegAtFollowup");
        indicator.setIndicatorName("Number of Ovc provided birth registeration at followup");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getNumberOfOVCEnrolledInSchoolAtFollowup()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcevrinscflp");
        indicator.setIndicatorName("Number of Ovc enrolled in school at followup since inception");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getNumberOfActiveOVCEnrolledInSchoolAtFollowup()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vccurinscflp");
        indicator.setIndicatorName("Number of active Ovc enrolled in school at followup");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcReferredForServicesPerMonthByCBO()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("noOfOvcReferredPerMth");
        indicator.setIndicatorName("Number of Ovc referred for services");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHouseholdsEverEnrolledWithBaselineAssessment()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("hhdbasasses");
        indicator.setIndicatorName("Number of households ever enrolled with baseline assessment");
        indicator.setIndicatorType(NomisConstant.HOUSEHOLD_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHouseholdsCurrentlyEnrolledWithBaselineAssessment()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("hhdcebasass");
        indicator.setIndicatorName("Number of households currently enrolled with baseline assessment");
        indicator.setIndicatorType(NomisConstant.HOUSEHOLD_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHouseholdsWithBaselineAssessmentWithinTheReprtPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("hhbassesper");
        indicator.setIndicatorName("Number of households with baseline assessment within the report period");
        indicator.setIndicatorType(NomisConstant.HOUSEHOLD_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHouseholdsCurrentlyEnrolledWithBaselineAssessmentWithinTheReprtPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("hhcebassper");
        indicator.setIndicatorName("Number of households currently enrolled with baseline assessment within the report period");
        indicator.setIndicatorType(NomisConstant.HOUSEHOLD_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHouseholdsCurrentlyEnrolledWithFollowupAssessment()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("hhcefupasse");
        indicator.setIndicatorName("Number of households currently enrolled with follow up assessment");
        indicator.setIndicatorType(NomisConstant.HOUSEHOLD_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHouseholdsEverEnrolledWithFollowupAssessment()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("hheefupasse");
        indicator.setIndicatorName("Number of households ever enrolled with follow up assessment");
        indicator.setIndicatorType(NomisConstant.HOUSEHOLD_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHouseholdsEnrolled()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("hhdEnrolled");
        indicator.setIndicatorName("Number of households enrolled");
        indicator.setIndicatorType(NomisConstant.HOUSEHOLD_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHouseholdsNewlyEnrolled()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("hhnenrolled");
        indicator.setIndicatorName("Number of Households newly enrolled");
        indicator.setIndicatorType(NomisConstant.HOUSEHOLD_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHouseholdsCurrentlyEnrolled()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("hhcenrolled");
        indicator.setIndicatorName("Number of households currently enrolled");
        indicator.setIndicatorType(NomisConstant.HOUSEHOLD_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHouseholdsEverEnrolled()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("hheenrolled");
        indicator.setIndicatorName("Number of Households ever enrolled");
        indicator.setIndicatorType(NomisConstant.HOUSEHOLD_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHouseholdsEnrolledPerCohort()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("noOfHouseholdsEnrolledPerCohort");
        indicator.setIndicatorName("Number of households enrolled in this cohort");
        indicator.setIndicatorType(NomisConstant.HOUSEHOLD_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHouseholdsFollowedupPerCohort()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("noOfHouseholdsFollowedUpPerCohort");
        indicator.setIndicatorName("Number of households followed up");
        indicator.setIndicatorType(NomisConstant.HOUSEHOLD_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHouseholdsNotFollowedupPerCohort()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("noOfHouseholdsNotFollowedUpPerCohort");
        indicator.setIndicatorName("Number of households not followed up");
        indicator.setIndicatorType(NomisConstant.HOUSEHOLD_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfCaregiversNewlyEnrolled()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("cgnenrolled");
        indicator.setIndicatorName("Number of Caregivers newly enrolled");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfCaregiversCurrentlyEnrolled()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("cgcenrolled");
        indicator.setIndicatorName("Number of Caregivers currently enrolled");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfCaregiversEverEnrolled()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("cgeenrolled");
        indicator.setIndicatorName("Number of Caregivers ever enrolled");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfCaregiversGraduated()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("cggraduated");
        indicator.setIndicatorName("Number of Caregivers graduated from the program");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfCaregiversWithdrawnDueToLostToFollowup()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("cglosttofup");
        indicator.setIndicatorName("Number of Caregivers lost to followup");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfCaregiversWithdrawnDueToMigration()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("cgmigrated1");
        indicator.setIndicatorName("Number of Caregivers migrated");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfCaregiversWithdrawnDueToTransfer()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("cgtransferd");
        indicator.setIndicatorName("Number of Caregivers transfered to other program");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfCaregiversWithdrawnDueToKnownDeath()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("cgknowndeat");
        indicator.setIndicatorName("Number of Caregivers known to have died");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfCaregiversEnrolledPerCohort()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("noOfCaregiverEnrolledPerCohort");
        indicator.setIndicatorName("Number of caregivers enrolled");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfCaregiversFollowedup()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("noOfCaregiversFollowedUp");
        indicator.setIndicatorName("Number of caregivers followed up");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivPositiveCaregiversAtFollowedup()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("noOfHivPositiveCaregiversFollowedUp");
        indicator.setIndicatorName("Number of HIV positive Caregivers followed up");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivNegativeCaregiversAtFollowedup()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("noOfHivNegativeCaregiversFollowedUp");
        indicator.setIndicatorName("Number of HIV negative Caregivers followed up");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivUnknownCaregiversAtFollowedup()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("noOfHivUnknownCaregiversFollowedUp");
        indicator.setIndicatorName("Number of HIV unknown Caregivers followed up");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfCaregiversScreenedForTB()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("cgscrnfortb");
        indicator.setIndicatorName("Number of Caregivers screened for TB");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfCaregiversTestedForHiv()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("cgtesforhiv");
        indicator.setIndicatorName("Number of Caregivers tested for HIV");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfCaregiversProvidedHES()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("cgservedhes");
        indicator.setIndicatorName("Number of Caregivers provided Economic strengthening services");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    /*public Indicators getIndicatorForNumberOfOvcEnrolledPerMonthByCBO()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcEnrolledPerMth");
        indicator.setIndicatorName("Number of OVC enrolled");
        return indicator;
    }*/
    public Indicators getIndicatorForNumberOfOvcEnrolled()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("ovcEnrolled");
        indicator.setIndicatorName("Number of OVC enrolled in the cohort");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfNewOvcEnrolled()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcnewlyEnro");
        indicator.setIndicatorName("Number of new OVC enrolled");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcCurrentlyEnrolled()
    {//currEnro031
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vccenrolled");
        indicator.setIndicatorName("Number of OVC currently enrolled");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcEverEnrolled()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcevenroled");
        indicator.setIndicatorName("Number of OVC ever enrolled");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHIVPositiveOvcAtBaseline()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("hivPosbasel");
        indicator.setIndicatorName("Number of OVC newly enrolled whose baseline HIV status is positive (includes those out of the program)");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHIVNegativeOvcAtBaseline()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("hivNegbasel");
        indicator.setIndicatorName("Number of OVC newly enrolled whose baseline HIV status is negative (includes those out of the program)");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHIVUnknownOvcAtBaseline()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("hivUnkbasel");
        indicator.setIndicatorName("Number of OVC newly enrolled whose baseline HIV status is unknown (includes those out of the program)");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    
    public Indicators getIndicatorForNumberOfHIVPositiveOvcIdentifiedWithinTheReportingPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vchivPosIdf");
        indicator.setIndicatorName("Number of HIV positive OVC identified within the reporting period");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHIVNegativeOvcIdentifiedWithinTheReportingPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vchivNegIdf");
        indicator.setIndicatorName("Number of HIV Negative OVC identified within the report period");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHIVUnknownOvcIdentifiedWithinTheReportingPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vchivunkIdf");
        indicator.setIndicatorName("Number of HIV unknown OVC identified within the report period");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHIVPositiveOvcCurrentlyEnrolled()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("hivPosEnrol");
        indicator.setIndicatorName("Number of HIV positive OVC currently enrolled");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHIVNegativeOvcCurrentlyEnrolled()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("hivNegEnrol");
        indicator.setIndicatorName("Number of HIV negative OVC currently enrolled");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHIVUnknownOvcCurrentlyEnrolled()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("hivUnkEnrol");
        indicator.setIndicatorName("Number of HIV unknown OVC currently enrolled");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHIVPositiveOvcEverEnrolled()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vchivposeen");
        indicator.setIndicatorName("Number of HIV positive OVC ever enrolled");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHIVNegativeOvcEverEnrolled()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vchivnegeen");
        indicator.setIndicatorName("Number of HIV negative OVC ever enrolled");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHIVUnknownOvcEverEnrolled()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vchivunkeen");
        indicator.setIndicatorName("Number of HIV unknown OVC ever enrolled");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    
    public Indicators getIndicatorForNumberOfHIVPositiveOvcNewlyEnrolledInCare()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcnewInCare");
        indicator.setIndicatorName("Number of HIV positive OVC newly enrolled in care");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHIVPositiveOvcCurrentlyEnrolledInCare()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vccurInCare");
        indicator.setIndicatorName("Number of HIV positive OVC currently enrolled in care");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_TXLINK");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHIVPositiveOvcEverEnrolledInCare()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vceveInCare");
        indicator.setIndicatorName("Number of HIV positive OVC ever enrolled that are enrolled in care");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_TXLINK");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHIVPositiveOvcCurrentlyEnrolledOnART()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vccureOnArt");
        indicator.setIndicatorName("Number of HIV positive OVC currently enrolled that are enrolled on ART within the report period");
        indicator.setAlternateName("Active OVC Currently receiving ART");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_HIVSTAT");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHIVPositiveOvcEverEnrolledOnART()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vceverOnArt");
        indicator.setIndicatorName("Number of HIV positive OVC ever enrolled that are enrolled on ART within the report period");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_TXLINK");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHIVPositiveOvcEnrolledAndAreStillInProgram()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vchivPosinp");
        indicator.setIndicatorName("Number of OVC enrolled within the report period whose current HIV status is positive and are still in the program (excludes those out of the program)");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHIVNegativeOvcEnrolledAndAreStillInProgram()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vchivNeginp");
        indicator.setIndicatorName("Number of OVC enrolled within the report period whose current HIV status is negative and are still in the program (excludes those out of the program)");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHIVUnknownOvcEnrolledAndAreStillInProgram()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vchivUnkinp");
        indicator.setIndicatorName("Number of OVC enrolled within the report period whose current HIV status is unknown and are still in the program (excludes those out of the program)");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    
    public Indicators getIndicatorForNumberOfHIVPositiveOvcNewlyEnrolled()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vchivPosnEn");
        indicator.setIndicatorName("Number of OVC newly enrolled whose current HIV status is positive (includes those out of the program)");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHIVNegativeOvcNewlyEnrolled()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vchivNegnEn");
        indicator.setIndicatorName("Number of OVC newly enrolled whose current HIV status is negative (includes those out of the program)");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHIVUnknownOvcNewlyEnrolled()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vchivUnknEn");
        indicator.setIndicatorName("Number of OVC newly enrolled whose current HIV status is unknown (includes those out of the program)");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    
    public Indicators getIndicatorForNumberOfOVCCurrentlyEnrolledAndServedHTCWhoseCurrentHIVStatusIsUnknown()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("nhivunkhtc");
        indicator.setIndicatorName("Number of OVC provided HTC services whose current HIV status is unknown");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcProvidedWithAtleastOneService()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcserved007");
        indicator.setIndicatorName("Number of OVC provided with at least one service within the report period");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setDescription("This indicator counts the number of OVC that are served within the report period (includes those graduated, lost to follow-up, migrated and other losses");
        indicator.setMerCode("OVC_SERV");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfBeneficiariesNewlyEnrolledWithinTheReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vccgnenserv");
        indicator.setIndicatorName("Number of beneficiaries newly enrolled and served within the report period (OVC_ENROLLED)");
        indicator.setAlternateName("New beneficiaries enrolled");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_ENROLLED");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfNewOvcEnrolledAndServedWithinTheReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcnenservrp");
        indicator.setIndicatorName("Number of OVC newly enrolled and served within the report period");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_SERV");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcCurrentlyEnrolledAndServedInReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vccurenserv");
        indicator.setIndicatorName("Number of OVC currently enrolled and served within the report period ");
        indicator.setAlternateName("Active");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setDescription("This indicator counts the number of OVC that are currently in the program and are served within the report period (excludes those graduated, lost to follow-up, migrated and other losses)");
        indicator.setMerCode("OVC_SERV");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcGraduatedButServedInReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcgradserve");
        indicator.setIndicatorName("Number of OVC graduated but served within the report period ");
        indicator.setAlternateName("Graduated");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_SERV");
        indicator.setDescription("This indicator counts the number of OVC that are graduated out of the program and but served within the report period");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcLostToFollowupButServedInReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcltfuserve");
        indicator.setIndicatorName("Number of OVC Lost to follow-up but served within the report period ");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_SERV");
        indicator.setDescription("This indicator counts the number of OVC that were declared lost to follow-up but served within the report period");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcMigratedButServedInReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcmigrserve");
        indicator.setIndicatorName("Number of OVC migrated but served within the report period ");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_SERV");
        indicator.setDescription("This indicator counts the number of OVC that are recorded to have migrated out of the program but served within the report period");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcAgedoutButServedInReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcageoserve");
        indicator.setIndicatorName("Number of OVC Aged out but served within the report period ");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_SERV");
        indicator.setDescription("This indicator counts the number of OVC that are withdrawn from the program due to age above 17 years but were served within the report period");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcTransferedOutButServedInReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vctranserve");
        indicator.setIndicatorName("Number of OVC transfered out but served within the report period ");
        indicator.setAlternateName("Transfered");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_SERV");
        indicator.setDescription("This indicator counts the number of OVC that are transfered out of the program but served within the report period");
        return indicator;
    }
    
    public Indicators getIndicatorForNumberOfOvcDiedButServedInReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcdeadserve");
        indicator.setIndicatorName("Number of OVC known to  have died but served within the report period ");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_SERV");
        indicator.setDescription("This indicator counts the number of OVC that are graduated out of the program and but served within the report period");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcInactiveButServedInReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcinactserv");
        indicator.setIndicatorName("Number of Inactive OVC served within the report period ");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_SERV");
        indicator.setDescription("This indicator counts the number of OVC that are declared inactive but served within the report period");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcVoluntarilyWithdrawnButServedInReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcvolwserve");
        indicator.setIndicatorName("Number of OVC who voluntarily withdrew from the program but served within the report period ");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_SERV");
        indicator.setDescription("This indicator counts the number of OVC that voluntarily withdrew from the program but served within the report period");
        return indicator;
    }
    
    public Indicators getIndicatorForNumberOfHivPositiveOvcProvidedWithAtleastOneService()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("posvcserved");
        indicator.setIndicatorName("Number of HIV positive OVC provided with at least one service");
        indicator.setMerCode("OVC_HIVSTAT");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivPositiveOvcEnrolledOnARTProvidedWithAtleastOneService()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcartserved");
        indicator.setIndicatorName("Number of OVC enrolled on ART and provided with at least one service within the report period");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_HIVSTAT");
        return indicator;
    }//
    public Indicators getIndicatorForNumberOfHivNegativeOvcProvidedWithAtleastOneService()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("negvcserved");
        indicator.setIndicatorName("Number of HIV negative OVC provided with at least one service");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_HIVSTAT");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivUnknownOvcProvidedWithAtleastOneService()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("unkvcserved");
        indicator.setIndicatorName("Number of HIV unknown OVC provided with at least one service");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_HIVSTAT");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivUnknownOvcWithTestNotIndicatedServed()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vctniserved");
        indicator.setIndicatorName("Number of HIV unknown OVC (Test not indicated) provided with at least one service");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_HIVSTAT");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivPositiveOvcCurrentlyEnrolledProvidedWithAtleastOneService()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcposceserv");
        indicator.setIndicatorName("Number of HIV positive OVC currently enrolled and served within the report period");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_HIVSTAT");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivNegativeOvcCurrentlyEnrolledProvidedWithAtleastOneService()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcnegceserv");
        indicator.setIndicatorName("Number of HIV negative OVC currently enrolled and served within the report period");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_HIVSTAT");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivUnknownOvcTNIGraduatedAndServedWithinTheReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vctnigrdser");
        indicator.setIndicatorName("Number of HIV unknown OVC (Test not indicated) graduated and served within the report period");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_HIVSTAT");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivUnknownOvcTNICurrentlyEnrolledAndServedWithinTheReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vctniceserv");
        indicator.setIndicatorName("Number of HIV unknown OVC (Test not indicated) currently enrolled and served within the report period");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_HIVSTAT");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivUnknownOvcCurrentlyEnrolledProvidedWithAtleastOneService()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcunkceserv");
        indicator.setIndicatorName("Number of HIV unknown OVC currently enrolled and served within the report period");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_HIVSTAT");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivPositiveOvcServedButGraduatedWithinTheReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcpossergrd");
        indicator.setIndicatorName("Number of HIV Positive OVC served and graduated within the report period");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_HIVSTAT");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivNegativeOvcServedButGraduatedWithinTheReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcnegsergrd");
        indicator.setIndicatorName("Number of HIV Negative OVC served and graduated within the report period");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_HIVSTAT");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivUnknownOvcServedButGraduatedWithinTheReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcunksergrd");
        indicator.setIndicatorName("Number of HIV unknown OVC served and graduated within the report period");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_HIVSTAT");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivUnknownOrNegativeOvcServedWithinTheReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcunknegser");
        indicator.setIndicatorName("Number of HIV unknown or HIV negative OVC served within the report period");
        indicator.setAlternateName("Number of HIV unknown or HIV negative OVC served within the report period");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_HIVSTAT");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivPositiveOvcOnARTCurrentlyEnrolledProvidedWithAtleastOneService()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcartceserv");
        indicator.setIndicatorName("Number of HIV positive OVC currently enrolled who are on treatment and served within the report period");
        indicator.setAlternateName("Active  HIV+ OVC currently receiving ART");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_HIVSTAT");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivPositiveOvcOnARTServedButGraduatedWithinTheReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcartsergrd");
        indicator.setIndicatorName("Number of HIV Positive OVC who are on Treatment but served and graduated within the report period");
        indicator.setAlternateName("Graduated but currently receiving ART");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_HIVSTAT");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcServedThatWereHivPositiveAtTheEndOfTheReportReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcposrpserv");
        indicator.setIndicatorName("Number of OVC provided with at least one service that were  HIV positive at the end of the report period");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_HIVSTAT");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcServedThatWereHivNegativeAtTheEndOfTheReportReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcnegrpserv");
        indicator.setIndicatorName("Number of OVC provided with at least one service that were  HIV negative at the end of the report period");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_HIVSTAT");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcServedThatWereHivUnknownAtTheEndOfTheReportReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcuknrpserv");
        indicator.setIndicatorName("Number of OVC provided with at least one service that were  HIV unknown at the end of the report period");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        indicator.setMerCode("OVC_HIVSTAT");
        return indicator;
    }
    
    public Indicators getIndicatorForNumberOfOvcProvidedPsychosocialSupport()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcservedpsy");
        indicator.setIndicatorName("Number of OVC provided Psychosocial services");
        indicator.setMerCode("OVC_SERV");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcProvidedNutritionalSupport()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcservednut");
        indicator.setIndicatorName("Number of OVC provided Nutritional services");
        indicator.setMerCode("OVC_SERV");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcProvidedHealthService()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcservedhlt");
        indicator.setIndicatorName("Number of OVC provided Health services");
        indicator.setMerCode("OVC_SERV");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcProvidedEducationalSupport()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcservededu");
        indicator.setIndicatorName("Number of OVC provided Educational services");
        indicator.setMerCode("OVC_SERV");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcProvidedProtectionServices()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcservedpro");
        indicator.setIndicatorName("Number of OVC provided Protection services");
        indicator.setMerCode("OVC_SERV");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfOvcProvidedShelterAndCareServices()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcservedshe");
        indicator.setIndicatorName("Number of OVC provided Shelter and care services");
        indicator.setMerCode("OVC_SERV");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    
    
    
    public Indicators getIndicatorForNumberOfHivPosOvcProvidedNutritionalSupport()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcpossernut");
        indicator.setIndicatorName("Number of HIV Positive OVC provided Nutritional services");
        indicator.setMerCode("OVC_HIVSTAT");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivNegOvcProvidedNutritionalSupport()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcnegsernut");
        indicator.setIndicatorName("Number of HIV Negative OVC provided Nutritional services");
        indicator.setMerCode("OVC_HIVSTAT");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivUnknOvcProvidedNutritionalSupport()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcunksernut");
        indicator.setIndicatorName("Number of HIV unknown OVC provided Nutritional services");
        indicator.setMerCode("OVC_HIVSTAT");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivPosOvcProvidedPsychosocialServices()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcposserpsy");
        indicator.setIndicatorName("Number of HIV Positive OVC provided Psychosocial services");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivNegOvcProvidedPsychosocialServices()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcnegserpsy");
        indicator.setIndicatorName("Number of HIV Negative OVC provided Psychosocial services");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivUnknOvcProvidedPsychosocialServices()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcunkserpsy");
        indicator.setIndicatorName("Number of HIV unknown OVC provided Psychosocial services");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivPosOvcProvidedHealthServices()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcposserhlt");
        indicator.setIndicatorName("Number of HIV Positive OVC provided Health services");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivNegOvcProvidedHealthServices()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcnegserhlt");
        indicator.setIndicatorName("Number of HIV Negative OVC provided Health services");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivUnknOvcProvidedHealthServices()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcunkserhlt");
        indicator.setIndicatorName("Number of HIV unknown OVC provided Health services");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    
    public Indicators getIndicatorForNumberOfHivPosOvcProvidedEducationalServices()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcposseredu");
        indicator.setIndicatorName("Number of HIV Positive OVC provided Educational services");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivNegOvcProvidedEducationalServices()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcnegseredu");
        indicator.setIndicatorName("Number of HIV Negative OVC provided Educational services");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivUnknOvcProvidedEducationalServices()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcunkseredu");
        indicator.setIndicatorName("Number of HIV unknown OVC provided Educational services");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    
       
    public Indicators getIndicatorForNumberOfHivPosOvcProvidedProtectionServices()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcposserpro");
        indicator.setIndicatorName("Number of HIV Positive OVC provided Protection services");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivNegOvcProvidedProtectionServices()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcnegserpro");
        indicator.setIndicatorName("Number of HIV Negative OVC provided Protection services");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivUnknOvcProvidedProtectionServices()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcunkserpro");
        indicator.setIndicatorName("Number of HIV unknown OVC provided Protection services");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    
    public Indicators getIndicatorForNumberOfHivPosOvcProvidedShelterAndCareServices()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcpossersht");
        indicator.setIndicatorName("Number of HIV Positive OVC provided Shelter and care services");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivNegOvcProvidedShelterAndCareServices()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcnegsersht");
        indicator.setIndicatorName("Number of HIV Negative OVC provided Shelter and care services");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivUnknOvcProvidedShelterAndCareServices()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcunksersht");
        indicator.setIndicatorName("Number of HIV unknown OVC provided Shelter and care services");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    
    public Indicators getIndicatorForNumberOfHivPosOvcProvidedEconomicStrengtheningServices()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcposserecs");
        indicator.setIndicatorName("Number of HIV Positive OVC provided Economic Strengthening services");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivNegOvcProvidedEconomicStrengtheningServices()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcnegserecs");
        indicator.setIndicatorName("Number of HIV Negative OVC provided Economic Strengthening services");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHivUnknOvcProvidedEconomicStrengtheningServices()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcunkserecs");
        indicator.setIndicatorName("Number of HIV unknown OVC provided Economic Strengthening services");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    
    
    
    public Indicators getIndicatorForNumberOfOvcWithoutServiceRecords()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("vcnotserved");
        indicator.setIndicatorName("Number of OVC currently enrolled without service records within the report period");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfNewOvcWithoutServiceRecords()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("newnotserve");
        indicator.setIndicatorName("Number of OVC newly enrolled without service records within the report period");
        indicator.setIndicatorType(NomisConstant.OVC_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfCaregiversWithoutServiceRecords()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("cgnotserved");
        indicator.setIndicatorName("Number of Caregivers currently enrolled without service records within the report period");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfCaregiversProvidedSILCSupport()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("cgservesilc");
        indicator.setIndicatorName("Number of Caregivers provided Savings and Internal Lending Community (SILC) support");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfCaregiversProvidedMicrofinanceSupport()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("cgservemfin");
        indicator.setIndicatorName("Number of Caregivers provided microfinace support");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfCaregiversProvidedEconomicStrenghteningServices()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("cgserveecon");
        indicator.setIndicatorName("Number of Caregivers provided economic strengthening services");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfCaregiversProvidedAtleastOneService()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("cgiverserve");
        indicator.setIndicatorName("Number of Caregivers/Household heads provided with at least one service within the report period");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }//cgnenrserrp
    public Indicators getIndicatorForNumberOfNewlyEnrolledCaregiversServedWithinTheReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("cgnenrserrp");
        indicator.setIndicatorName("Number of Caregivers newly enrolled and served within the report period");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfActiveCaregiversServedWithinTheReportPeriodByAge()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("cgactsrpage");
        indicator.setIndicatorName("Number of Caregivers currently enrolled and served within the report period");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfActiveCaregiversServedWithinTheReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("cgactiserrp");
        indicator.setIndicatorName("Number of Caregivers currently enrolled and served within the report period");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfCaregiversServedAndGraduatedWithinTheReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("cgsergradrp");
        indicator.setIndicatorName("Number of Caregivers served but graduated within the report period");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfCaregiversServedAndGraduatedWithinTheReportPeriodByAge()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("cgsgrdrpage");
        indicator.setIndicatorName("Number of Caregivers served but graduated within the report period");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfInactiveCaregiversServedInReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("cginactserv");
        indicator.setIndicatorName("Number of Inactive Caregivers served within the report period ");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        indicator.setDescription("This indicator counts the number of Caregivers that are declared inactive but served within the report period");
        return indicator;
    }
    public Indicators getIndicatorForNumberOfCaregiversServedAndLostToFollowupWithinTheReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("cgserltfurp");
        indicator.setIndicatorName("Number of Caregivers served but lost to follow up within the report period");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfCaregiversServedAndDiedWithinTheReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("cgserdiedrp");
        indicator.setIndicatorName("Number of Caregivers served but died within the report period");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfCaregiversServedAndMigratedWithinTheReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("cgsermigrrp");
        indicator.setIndicatorName("Number of Caregivers served but migrated within the report period");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfCaregiversServedAndTransferedWithinTheReportPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("cgsertranrp");
        indicator.setIndicatorName("Number of Caregivers served but transfered within the report period");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfCaregiversServedAndTransferedWithinTheReportPeriodByAge()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("cgstrarpage");
        indicator.setIndicatorName("Number of Caregivers served but transfered within the report period (by age)");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfCaregiversSupportedToAccessHIVServices()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("cgivehivacc");
        indicator.setIndicatorName("Number of caregivers/household heads supported to access HIV services");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfCaregiversDeclaredInactive()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("cginactivrp");
        indicator.setIndicatorName("Number of Caregivers declared inactive and removed from the program within the reporting period");
        indicator.setIndicatorType(NomisConstant.Caregiver_TYPE);
        return indicator;
    }
    public Indicators getIndicatorNumberOfGraduatedHouseholdsWhoseOvcWereServedWithinReportingPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("hhgdvcserrp");
        indicator.setIndicatorName("Number of Graduated Households whose OVC where served within the report period");
        indicator.setAlternateName("Number of Households Graduated");
        indicator.setIndicatorType(NomisConstant.HOUSEHOLD_TYPE);
        indicator.setMerCode("OVC_HHGRAD");
        return indicator;
    }
    public Indicators getIndicatorNumberOfActiveHouseholdsWhoseOvcWereServedWithinReportingPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("hhactvcserp");
        indicator.setIndicatorName("Number of active Households whose OVC where served within the report period");
        indicator.setAlternateName("Number of active Households served");
        indicator.setIndicatorType(NomisConstant.HOUSEHOLD_TYPE);
        indicator.setMerCode("OVC_HHGRAD");
        return indicator;
    }
    public Indicators getIndicatorNumberOfHouseholdsWhoseOvcWereServedWithinReportingPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("hhvcserverp");
        indicator.setIndicatorName("Number of Households whose OVC where served within the report period");
        indicator.setAlternateName("Number of Households served");
        indicator.setIndicatorType(NomisConstant.HOUSEHOLD_TYPE);
        indicator.setMerCode("OVC_HHGRAD");
        return indicator;
    }
    public Indicators getIndicatorNumberOfHouseholdsThatCanSolveEmergencyNeedsWithinReportingPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("hhemergneed");
        indicator.setIndicatorName("Number of active HHs that have access to money to pay for unexpected household expenses, school fees and other important expenses (OVC_ECON)");
        indicator.setAlternateName("Number of active HHs that have access to money to pay for unexpected household expenses, school fees and other important expenses");
        indicator.setMerCode("OVC_ECONS");
        indicator.setIndicatorType(NomisConstant.HOUSEHOLD_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForTotalNumberOfHouseholdsWithdrawnForTheReportingPeriod()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("hhwithdrawn");
        indicator.setIndicatorName("Number of households withdrawn from the program");
        indicator.setIndicatorType(NomisConstant.HOUSEHOLD_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHouseholdsWithdrawnDueToGraduation()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("hhgraduated");
        indicator.setIndicatorName("Number of households graduated from the program");
        indicator.setIndicatorType(NomisConstant.HOUSEHOLD_TYPE);
        return indicator;
    }
    
    public Indicators getIndicatorForNumberOfHouseholdsWithdrawnDueToMigration()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("hhmigrated1");
        indicator.setIndicatorName("Number of households migrated");
        indicator.setIndicatorType(NomisConstant.HOUSEHOLD_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHouseholdsWithdrawnDueToLostToFollowup()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("hhlosttofup");
        indicator.setIndicatorName("Number of households lost to follow up");
        indicator.setIndicatorType(NomisConstant.HOUSEHOLD_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHouseholdsWithdrawnDueToTransfer()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("hhtransferd");
        indicator.setIndicatorName("Number of households transfered from the program (transfer)");
        indicator.setIndicatorType(NomisConstant.HOUSEHOLD_TYPE);
        return indicator;
    }
    public Indicators getIndicatorForNumberOfHouseholdsDeclaredInactive()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("hhinactivrp");
        indicator.setIndicatorName("Number of Households declared inactive and removed from the program within the reporting period");
        indicator.setIndicatorType(NomisConstant.HOUSEHOLD_TYPE);
        return indicator;
    }
    
    public Indicators getOvc_ENROLLEDIndicator()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("merovcenrol");
        indicator.setIndicatorName("OVC_ENROLLED");
        indicator.setMerCode("OVC_ENROLLED");
        indicator.setIndicatorType(NomisConstant.MAIN_INDICATOR_TYPE);
        return indicator;
    }
    public Indicators getOvc_SERVIndicator()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("merovcservn");
        indicator.setIndicatorName("OVC_SERV");
        indicator.setMerCode("OVC_SERV");
        indicator.setIndicatorType(NomisConstant.MAIN_INDICATOR_TYPE);
        return indicator;
    }
    public Indicators getOvc_HIVSTATIndicator()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("merovchivst");
        indicator.setIndicatorName("OVC_HIVSTAT");
        indicator.setMerCode("OVC_HIVSTAT");
        indicator.setIndicatorType(NomisConstant.MAIN_INDICATOR_TYPE);
        return indicator;
    }
    public Indicators getOvc_BIRTHCERTIndicator()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("movcbircert");
        indicator.setIndicatorName("OVC_BIRTHCERT");
        indicator.setMerCode("OVC_BIRTHCERT");
        indicator.setIndicatorType(NomisConstant.MAIN_INDICATOR_TYPE);
        return indicator;
    }
    public Indicators getOvc_EDUIndicator()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("merovceduin");
        indicator.setIndicatorName("OVC_EDU");
        indicator.setMerCode("OVC_EDU");
        indicator.setIndicatorType(NomisConstant.MAIN_INDICATOR_TYPE);
        return indicator;
    }
    public Indicators getOvc_HIVRISKASSIndicator()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("movchivrisk");
        indicator.setIndicatorName("OVC_HIVRISKASS");
        indicator.setMerCode("OVC_HIVRISKASS");
        indicator.setIndicatorType(NomisConstant.MAIN_INDICATOR_TYPE);
        return indicator;
    }
    public Indicators getOvc_HTSLINKIndicator()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("movchtslink");
        indicator.setIndicatorName("OVC_HTSLINK");
        indicator.setMerCode("OVC_HTSLINK");
        indicator.setIndicatorType(NomisConstant.MAIN_INDICATOR_TYPE);
        return indicator;
    }
    public Indicators getOvc_TXLINKIndicator()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("merovctxlink");
        indicator.setIndicatorName("OVC_HTSLINK");
        indicator.setMerCode("OVC_HTSLINK");
        indicator.setIndicatorType(NomisConstant.MAIN_INDICATOR_TYPE);
        return indicator;
    }
    public Indicators getOvc_ARTSUPPIndicator()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("mrovcartsup");
        indicator.setIndicatorName("OVC_ARTSUPP");
        indicator.setMerCode("OVC_ARTSUPP");
        indicator.setIndicatorType(NomisConstant.MAIN_INDICATOR_TYPE);
        return indicator;
    }
    public Indicators getOvc_NUTRITIONIndicator()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("mrovnutritn");
        indicator.setIndicatorName("OVC_NUTRITION");
        indicator.setMerCode("OVC_NUTRITION");
        indicator.setIndicatorType(NomisConstant.MAIN_INDICATOR_TYPE);
        return indicator;
    }
    public Indicators getOvc_ECONSIndicator()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("merovcecons");
        indicator.setIndicatorName("OVC_ECONS");
        indicator.setMerCode("OVC_ECONS");
        indicator.setIndicatorType(NomisConstant.MAIN_INDICATOR_TYPE);
        return indicator;
    }
    public Indicators getOvc_PROTECTIndicator()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("movcprotect");
        indicator.setIndicatorName("OVC_PROTECT");
        indicator.setMerCode("OVC_PROTECT");
        indicator.setIndicatorType(NomisConstant.MAIN_INDICATOR_TYPE);
        return indicator;
    }
    public Indicators getOvc_HIVPREVIndicator()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("movchivprev");
        indicator.setIndicatorName("OVC_HIVPREV");
        indicator.setMerCode("OVC_HIVPREV");
        indicator.setIndicatorType(NomisConstant.MAIN_INDICATOR_TYPE);
        return indicator;
    }
    public Indicators getOvc_HHGRADIndicator()
    {
        Indicators indicator=Indicators.getInstance();
        indicator.setIndicatorId("mrovchhgrad");
        indicator.setIndicatorName("OVC_HHGRAD");
        indicator.setMerCode("OVC_HHGRAD");
        indicator.setIndicatorType(NomisConstant.MAIN_INDICATOR_TYPE);
        return indicator;
    }
}
