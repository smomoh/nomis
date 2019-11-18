/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report;

import com.fhi.kidmap.dao.BirthRegistrationDao;
import com.fhi.kidmap.dao.BirthRegistrationDaoImpl;
import com.fhi.kidmap.dao.ChildStatusIndexDao;
import com.fhi.kidmap.dao.ChildStatusIndexDaoImpl;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.FollowupDao;
import com.fhi.kidmap.dao.FollowupDaoImpl;
import com.fhi.kidmap.dao.HouseholdEnrollmentDao;
import com.fhi.kidmap.dao.HouseholdEnrollmentDaoImpl;
import com.fhi.kidmap.dao.HouseholdServiceDao;
import com.fhi.kidmap.dao.HouseholdServiceDaoImpl;
import com.fhi.kidmap.dao.HouseholdVulnerabilityAssessmentDao;
import com.fhi.kidmap.dao.HouseholdVulnerabilityAssessmentDaoImpl;
import com.fhi.kidmap.dao.NutritionAssessmentDao;
import com.fhi.kidmap.dao.NutritionAssessmentDaoImpl;
import com.fhi.kidmap.dao.OvcDao;
import com.fhi.kidmap.dao.OvcDaoImpl;
import com.fhi.kidmap.dao.OvcServiceDao;
import com.fhi.kidmap.dao.OvcServiceDaoImpl;
import com.fhi.kidmap.dao.SpecificServiceReportDao;
import com.fhi.kidmap.dao.SpecificServiceReportDaoImpl;
import com.fhi.nomis.nomisutils.NomisConstant;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author smomoh
 */
public class ReportDownloadManager implements Serializable
{
   public List getReportDownloadIndicators()
    {
        IndicatorDictionary ind=new IndicatorDictionary();
        List list=new ArrayList();
        list.add(ind.getIndicatorForNumberOfNewOvcEnrolled());
        list.add(ind.getIndicatorForNumberOfOvcCurrentlyEnrolled());
        list.add(ind.getIndicatorForNumberOfOvcEverEnrolled());
        
        list.add(ind.getIndicatorForNumberOfHIVPositiveOvcCurrentlyEnrolled());
        list.add(ind.getIndicatorForNumberOfHIVNegativeOvcCurrentlyEnrolled());
        list.add(ind.getIndicatorForNumberOfHIVUnknownOvcCurrentlyEnrolled());
        
        list.add(ind.getIndicatorForNumberOfHIVPositiveOvcEverEnrolled());
        list.add(ind.getIndicatorForNumberOfHIVNegativeOvcEverEnrolled());
        list.add(ind.getIndicatorForNumberOfHIVUnknownOvcEverEnrolled());
        
        
        list.add(ind.getIndicatorForNumberOfOvcGraduated());
        list.add(ind.getIndicatorForNumberOfOvcWithdrawnDueToKnownDeath());
        list.add(ind.getIndicatorForNumberOfOvcWithdrawnDueToAgingOut());
        list.add(ind.getIndicatorForNumberOfOvcWithdrawnDueToMigration());
        list.add(ind.getIndicatorForNumberOfOvcWithdrawnDueToLostToFollowup());
        list.add(ind.getIndicatorForNumberOfOvcWithdrawnDueToTransfer());
        list.add(ind.getIndicatorForNumberOfOvcVoluntarilyWithdrawnFromTheProgramReportPeriod());
        
        list.add(ind.getIndicatorForNumberOfHIVPositiveOvcIdentifiedWithinTheReportingPeriod());
        list.add(ind.getIndicatorForNumberOfHIVNegativeOvcIdentifiedWithinTheReportingPeriod());
        list.add(ind.getIndicatorForNumberOfHIVUnknownOvcIdentifiedWithinTheReportingPeriod());
        
        list.add(ind.getIndicatorForNumberOfOVCCurrentlyEnrolledAndServedHTCWhoseCurrentHIVStatusIsUnknown());
        list.add(ind.getIndicatorForOVC_ACCPerMonthByCBO());
        list.add(ind.getIndicatorForNumberOfOvcServedPerMonthByCBO());
        //list.add(ind.getIndicatorForNumberOfCaregiversProvidedAtleastOneService());
        list.add(ind.getIndicatorForNumberOfHIVNegativeOvcServedPerMonthByCBO());
        list.add(ind.getIndicatorForNumberOfHIVPositiveOvcServedPerMonthByCBO());
        list.add(ind.getIndicatorForNumberOfHIVUnknownOvcServedPerMonthByCBO());
        list.add(ind.getIndicatorForNumberOfHivPositiveOvcAtBaseline());
        list.add(ind.getIndicatorForNumberOfHivNegativeOvcAtBaseline());
        list.add(ind.getIndicatorForNumberOfHivUnknownOvcAtBaseline());
        list.add(ind.getIndicatorForNumberOfOVCWithUpdatedHIVStatusAtFollowup());
        list.add(ind.getIndicatorForHIVPositiveOVCIdentifiedAtAtFollowup());
        list.add(ind.getIndicatorForNumberOfOvcOutOfSchoolAtEnrollmentPerMonthByCBO());
        list.add(ind.getIndicatorForNumberOfOvcEverEnrolledAndInSchoolAtEnrollmentPerMonthByCBO());
        list.add(ind.getIndicatorForNumberOfOvcCurrentlyAndInSchoolAtEnrollmentPerMonthByCBO());
        list.add(ind.getIndicatorForNumberOfOvcCurrentlyAndInSchoolAtEnrollmentPerMonthByCBO());
        list.add(ind.getNumberOfActiveOVCEnrolledInSchoolAtFollowup());
        list.add(ind.getIndicatorForNumberOfOvcProvidedEconomicStrengtheningServicesPerMonthByCBO());
        list.add(ind.getIndicatorForNumberOfOvcProvidedEducationalServicesPerMonthByCBO());
        list.add(ind.getIndicatorForNumberOfOvcProvidedHIVServicesPerMonthByCBO());
        list.add(ind.getIndicatorForNumberOfOvcProvidedHealthServicesPerMonthByCBO());
        list.add(ind.getIndicatorForNumberOfOvcProvidedNutritionalSupport());
        //list.add(ind.getIndicatorForNumberOfOvcProvidedNutritionalServicesPerMonthByCBO());
        list.add(ind.getIndicatorForNumberOfOvcProvidedProtectionServicesPerMonthByCBO());
        list.add(ind.getIndicatorForNumberOfOvcProvidedPsychosocialSupportServicesPerMonthByCBO());
        list.add(ind.getIndicatorForNumberOfOvcProvidedShelterServicesPerMonthByCBO());
        list.add(ind.getIndicatorForNumberOfOvcReferredForServicesPerMonthByCBO());
        list.add(ind.getIndicatorForNumberOfOvcNewlyEnrolledWithBirthCertificateAtBaseline());
        list.add(ind.getIndicatorForNumberOfOvcProvidedBirthCertificateServicesAfterEnrollment());
        list.add(ind.getIndicatorForNumberOfOvcCurrentlyEnrolledWithBirthCertificate());
        list.add(ind.getIndicatorForNumberOfOvcProvidedLegalServicesAfterEnrollment());
        list.add(ind.getIndicatorForNumberOfOvcSickWithLimitedAccessToHealthCare());
        list.add(ind.getIndicatorForNumberOfOvcSickWithNoAccessToHealthCare());
        list.add(ind.getIndicatorForNumberOfOvcVulnerableToHIV_AIDS());
        list.add(ind.getIndicatorForNumberOfOvcInChildHeadedHouseholds());
        list.add(ind.getIndicatorForNumberOfOvcWhoAreVulnerableAtBaseline());
        list.add(ind.getIndicatorForNumberOfOvcWhoAreMoreVulnerableAtBaseline());
        list.add(ind.getIndicatorForNumberOfOvcWhoAreMostVulnerableAtBaseline());
        list.add(ind.getIndicatorForNumberOfOvcThatDroppedOutOfSchoolPerMonthByCBO());
        list.add(ind.getIndicatorForNumberOfOvcCurrentlyEnrolledAndServedInReportPeriod());
        list.add(ind.getIndicatorForNumberOfOvcGraduatedButServedInReportPeriod());
        
        list.add(ind.getIndicatorForNumberOfOvcLostToFollowupButServedInReportPeriod());
        list.add(ind.getIndicatorForNumberOfOvcMigratedButServedInReportPeriod());
        list.add(ind.getIndicatorForNumberOfOvcAgedoutButServedInReportPeriod());
        list.add(ind.getIndicatorForNumberOfOvcTransferedOutButServedInReportPeriod());
        list.add(ind.getIndicatorForNumberOfOvcDiedButServedInReportPeriod());
        list.add(ind.getIndicatorForNumberOfOvcVoluntarilyWithdrawnButServedInReportPeriod());
        
        
        list.add(ind.getIndicatorForNumberOfHivPositiveOvcProvidedWithAtleastOneService());
        list.add(ind.getIndicatorForNumberOfHivNegativeOvcProvidedWithAtleastOneService());
        list.add(ind.getIndicatorForNumberOfHivUnknownOvcProvidedWithAtleastOneService());
        list.add(ind.getIndicatorForNumberOfHivPositiveOvcCurrentlyEnrolledProvidedWithAtleastOneService());
        list.add(ind.getIndicatorForNumberOfHivNegativeOvcCurrentlyEnrolledProvidedWithAtleastOneService());
        list.add(ind.getIndicatorForNumberOfHivUnknownOvcCurrentlyEnrolledProvidedWithAtleastOneService());
        
        
        list.add(ind.getIndicatorForNumberOfHivPosOvcProvidedPsychosocialServices());
        list.add(ind.getIndicatorForNumberOfHivNegOvcProvidedPsychosocialServices());
        list.add(ind.getIndicatorForNumberOfHivUnknOvcProvidedPsychosocialServices());
        list.add(ind.getIndicatorForNumberOfHivPosOvcProvidedNutritionalSupport());
        list.add(ind.getIndicatorForNumberOfHivNegOvcProvidedNutritionalSupport());
        list.add(ind.getIndicatorForNumberOfHivUnknOvcProvidedNutritionalSupport());
        list.add(ind.getIndicatorForNumberOfHivPosOvcProvidedHealthServices());
        list.add(ind.getIndicatorForNumberOfHivNegOvcProvidedHealthServices());
        list.add(ind.getIndicatorForNumberOfHivUnknOvcProvidedHealthServices());
        list.add(ind.getIndicatorForNumberOfHivPosOvcProvidedEducationalServices());
        list.add(ind.getIndicatorForNumberOfHivNegOvcProvidedEducationalServices());
        list.add(ind.getIndicatorForNumberOfHivUnknOvcProvidedEducationalServices());
        list.add(ind.getIndicatorForNumberOfHivPosOvcProvidedProtectionServices());
        list.add(ind.getIndicatorForNumberOfHivNegOvcProvidedProtectionServices());
        list.add(ind.getIndicatorForNumberOfHivUnknOvcProvidedProtectionServices());
        list.add(ind.getIndicatorForNumberOfHivPosOvcProvidedShelterAndCareServices());
        list.add(ind.getIndicatorForNumberOfHivNegOvcProvidedShelterAndCareServices());
        list.add(ind.getIndicatorForNumberOfHivUnknOvcProvidedShelterAndCareServices());
        list.add(ind.getIndicatorForNumberOfHivPosOvcProvidedEconomicStrengtheningServices());
        list.add(ind.getIndicatorForNumberOfHivNegOvcProvidedEconomicStrengtheningServices());
        list.add(ind.getIndicatorForNumberOfHivUnknOvcProvidedEconomicStrengtheningServices());
        
        return list;
    }
    public List getReportDownloadIndicatorsForOVCNutritionalAssessment()
    {
        IndicatorDictionary ind=new IndicatorDictionary();
        List list=new ArrayList();
        list.add(ind.getIndicatorForNumberOfHouseholdsNewlyEnrolled());
        list.add(ind.getIndicatorForNumberOfHouseholdsCurrentlyEnrolled());
        list.add(ind.getIndicatorForNumberOfHouseholdsEverEnrolled());
        list.add(ind.getIndicatorForNumberOfHouseholdsCurrentlyEnrolledWithBaselineAssessment());
        list.add(ind.getIndicatorForNumberOfHouseholdsEverEnrolledWithBaselineAssessment());
        list.add(ind.getIndicatorForNumberOfHouseholdsWithdrawnDueToGraduation());
                
        return list;
    }
    public List getReportDownloadIndicatorsForCaregivers()
    {
        IndicatorDictionary ind=new IndicatorDictionary();
        List list=new ArrayList();
        list.add(ind.getIndicatorForNumberOfCaregiversProvidedAtleastOneService());
        list.add(ind.getIndicatorForNumberOfCaregiversProvidedEconomicStrenghteningServices());
        list.add(ind.getIndicatorForNumberOfCaregiversProvidedMicrofinanceSupport());
        list.add(ind.getIndicatorForNumberOfCaregiversProvidedSILCSupport());
        /*list.add(ind.getIndicatorForNumberOfCaregiversNewlyEnrolled());
        list.add(ind.getIndicatorForNumberOfCaregiversCurrentlyEnrolled());
        list.add(ind.getIndicatorForNumberOfCaregiversEverEnrolled());
        list.add(ind.getIndicatorForNumberOfCaregiversProvidedAtleastOneService());
        list.add(ind.getIndicatorForNumberOfCaregiversSupportedToAccessHIVServices());
        list.add(ind.getIndicatorForNumberOfCaregiversGraduated());
        list.add(ind.getIndicatorForNumberOfCaregiversWithdrawnDueToKnownDeath());
        list.add(ind.getIndicatorForNumberOfCaregiversWithdrawnDueToLostToFollowup());
        list.add(ind.getIndicatorForNumberOfCaregiversWithdrawnDueToMigration());
        list.add(ind.getIndicatorForNumberOfCaregiversWithdrawnDueToTransfer());
        list.add(ind.getIndicatorForNumberOfHouseholdsNewlyEnrolled());
        
        list.add(ind.getIndicatorForNumberOfHouseholdsWithBaselineAssessmentWithinTheReprtPeriod());
        list.add(ind.getIndicatorForNumberOfHouseholdsWithFollowupAssessment());
        
        list.add(ind.getIndicatorForNumberOfHouseholdsWithdrawnDueToLostToFollowup());
        list.add(ind.getIndicatorForNumberOfHouseholdsWithdrawnDueToMigration());
        list.add(ind.getIndicatorForNumberOfHouseholdsWithdrawnDueToTransfer());*/
        
        
        
        return list;
    }
    public List getReportDownloadIndicatorsForHouseholds()
    {
        IndicatorDictionary ind=new IndicatorDictionary();
        List list=new ArrayList();
        list.add(ind.getIndicatorForNumberOfHouseholdsNewlyEnrolled());
        list.add(ind.getIndicatorForNumberOfHouseholdsCurrentlyEnrolled());
        list.add(ind.getIndicatorForNumberOfHouseholdsEverEnrolled());
        list.add(ind.getIndicatorForNumberOfHouseholdsCurrentlyEnrolledWithBaselineAssessment());
        list.add(ind.getIndicatorForNumberOfHouseholdsEverEnrolledWithBaselineAssessment());
        list.add(ind.getIndicatorForNumberOfHouseholdsWithdrawnDueToGraduation());
                
        return list;
    }
   public List getReportDownloadData(String indicatorId,String stateCode,String startDate,String endDate)
    {
        ReportUtility rutil=new ReportUtility();
        List list=new ArrayList();
        IndicatorDictionary ind=new IndicatorDictionary();
        if(indicatorId !=null)
        {
            OvcDao dao=new OvcDaoImpl();
            OvcServiceDao servicedao=new OvcServiceDaoImpl();
            FollowupDao fudao=new FollowupDaoImpl();
            ChildStatusIndexDao csidao=new ChildStatusIndexDaoImpl();
            BirthRegistrationDao brdao=new BirthRegistrationDaoImpl();
            HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
            HouseholdVulnerabilityAssessmentDao hvadao=new HouseholdVulnerabilityAssessmentDaoImpl();
            HouseholdServiceDao hhsdao=new HouseholdServiceDaoImpl();
            IndicatorWarehouse iwh=new IndicatorWarehouse();
            String indicatorName=iwh.getIndicatorById(indicatorId).getIndicatorName();
            try
            {
                String additionalQuery=" and hhe.stateCode='"+stateCode+"'";
                if(indicatorId.equalsIgnoreCase("vcnewlyEnro"))
                {
                    //list.addAll(dao.getCummulativeNumberOfOvcEnrolledByCBO(stateCode,startDate,endDate));
                    list.addAll(dao.getNumberOfOvcNewlyEnrolledPerMonthByCBO(ind.getIndicatorForNumberOfNewOvcEnrolled().getIndicatorName(),stateCode,startDate,endDate));
                }//hivPosEnrol
                if(indicatorId.equalsIgnoreCase("vccenrolled"))
                {
                    //list.addAll(dao.getCummulativeNumberOfOvcEnrolledByCBO(stateCode,startDate,endDate));
                    list.addAll(dao.getNumberOfOvcEnrolledPerMonthByCBO(ind.getIndicatorForNumberOfOvcCurrentlyEnrolled().getIndicatorName(),stateCode,true));
                }
                if(indicatorId.equalsIgnoreCase("vcevenroled"))
                {
                    list.addAll(dao.getNumberOfOvcEnrolledPerMonthByCBO(ind.getIndicatorForNumberOfOvcEverEnrolled().getIndicatorName(),stateCode,false));
                }//
                if(indicatorId.equalsIgnoreCase("hivPosEnrol"))
                {
                    list.addAll(dao.getNumberOfHivPositiveOvcCurrentlyEnrolledPerMonthByCBO(ind.getIndicatorForNumberOfHIVPositiveOvcCurrentlyEnrolled().getIndicatorName(),stateCode,startDate,endDate,true));
                }
                if(indicatorId.equalsIgnoreCase("hivNegEnrol"))
                {
                    list.addAll(dao.getNumberOfHivNegativeOvcCurrentlyEnrolledPerMonthByCBO(ind.getIndicatorForNumberOfHIVNegativeOvcCurrentlyEnrolled().getIndicatorName(),stateCode,startDate,endDate,true));
                }
                if(indicatorId.equalsIgnoreCase("hivUnkEnrol"))
                {
                    list.addAll(dao.getNumberOfHivUnknownOvcCurrentlyEnrolledPerMonthByCBO(ind.getIndicatorForNumberOfHIVUnknownOvcCurrentlyEnrolled().getIndicatorName(),stateCode,startDate,endDate,true));
                }
                
                if(indicatorId.equalsIgnoreCase("vchivposeen"))
                {
                    list.addAll(dao.getNumberOfHivPositiveOvcCurrentlyEnrolledPerMonthByCBO(ind.getIndicatorForNumberOfHIVPositiveOvcCurrentlyEnrolled().getIndicatorName(),stateCode,startDate,endDate,false));
                }
                if(indicatorId.equalsIgnoreCase("vchivnegeen"))
                {
                    list.addAll(dao.getNumberOfHivNegativeOvcCurrentlyEnrolledPerMonthByCBO(ind.getIndicatorForNumberOfHIVNegativeOvcCurrentlyEnrolled().getIndicatorName(),stateCode,startDate,endDate,false));
                }
                if(indicatorId.equalsIgnoreCase("vchivunkeen"))
                {
                    list.addAll(dao.getNumberOfHivUnknownOvcCurrentlyEnrolledPerMonthByCBO(ind.getIndicatorForNumberOfHIVUnknownOvcCurrentlyEnrolled().getIndicatorName(),stateCode,startDate,endDate,false));
                }
                
                
                if(indicatorId.equalsIgnoreCase("vchivPosIdf"))
                {
                    list.addAll(dao.getNumberOfHIVPositiveOvcIdentifiedWithinTheReportingPeriodPerCBO(ind.getIndicatorForNumberOfHIVPositiveOvcIdentifiedWithinTheReportingPeriod().getIndicatorName(),stateCode,startDate,endDate));
                }
                if(indicatorId.equalsIgnoreCase("vchivNegIdf"))
                {
                    list.addAll(dao.getNumberOfHIVNegativeOvcIdentifiedWithinTheReportingPeriodPerCBO(ind.getIndicatorForNumberOfHIVNegativeOvcIdentifiedWithinTheReportingPeriod().getIndicatorName(),stateCode,startDate,endDate));
                }
                if(indicatorId.equalsIgnoreCase("vchivunkIdf"))
                {
                    list.addAll(dao.getNumberOfHIVUnknownOvcIdentifiedWithinTheReportingPeriodPerCBO(ind.getIndicatorForNumberOfHIVUnknownOvcIdentifiedWithinTheReportingPeriod().getIndicatorName(),stateCode,startDate,endDate));
                }
                if(indicatorId.equalsIgnoreCase("nhivunkhtc"))
                {
                    indicatorName=ind.getIndicatorForNumberOfOVCCurrentlyEnrolledAndServedHTCWhoseCurrentHIVStatusIsUnknown().getIndicatorName();
                    list.addAll(dao.getNumberOfHivUnknownOvcCurrentlyEnrolledAndProvidedHTCPerMonthByCBO(indicatorName,stateCode,startDate,endDate));
                }
                else if(indicatorId.equalsIgnoreCase("vcbslbthreg"))
                {
                    list.addAll(dao.getNumberOfOvcWithBirthCertificateAtBaseline(stateCode,startDate,endDate));
                }
                else if(indicatorId.equalsIgnoreCase("vcWithBirthCertAfterEnrollmentPerMth"))
                {
                    list.addAll(servicedao.getNumberOfOvcProvidedBirthRegistrationServicesPerMonthByCBO(stateCode,startDate,endDate));
                }
                else if(indicatorId.equalsIgnoreCase("vcProvidedLegalServicesPerMth"))
                {
                    list.addAll(servicedao.getNumberOfOvcProvidedLegalServicesPerMonthByCBO(stateCode,startDate,endDate));
                }
                if(indicatorId.equalsIgnoreCase("vchivPosbas"))
                {
                    list.addAll(dao.getNumberOfHivPositiveOvcEnrolledPerMonthByCBO(ind.getIndicatorForNumberOfHivPositiveOvcAtBaseline().getIndicatorName(),stateCode, startDate, endDate));
                }
                else if(indicatorId.equalsIgnoreCase("vchivNegbas"))
                {
                    list.addAll(dao.getNumberOfHivNegativeOvcEnrolledPerMonthByCBO(stateCode, startDate, endDate));
                }
                else if(indicatorId.equalsIgnoreCase("vchivUknbas"))
                {
                    list.addAll(dao.getNumberOfHivUnknownOvcEnrolledPerMonthByCBO(stateCode, startDate, endDate));
                }
                else if(indicatorId.equalsIgnoreCase("noOfOvcOutOfSchoolAtEnrollment"))
                {
                    list.addAll(dao.getNumberOfOvcOutOfSchoolAtEnrollmentPerMonthByCBO(stateCode, startDate, endDate));
                }
                else if(indicatorId.equalsIgnoreCase("vcinschaten"))
                {
                    list.addAll(dao.getNumberOfOvcInSchoolAtEnrollmentPerMonthByCBO(ind.getIndicatorForNumberOfOvcEverEnrolledAndInSchoolAtEnrollmentPerMonthByCBO().getIndicatorName(),stateCode, startDate, endDate));
                }
                else if(indicatorId.equalsIgnoreCase("vcceinscenr"))
                {
                    list.addAll(dao.getNumberOfOvcCurrentlyEnrolledAndInSchoolAtEnrollmentPerMonthByCBO(ind.getIndicatorForNumberOfOvcCurrentlyAndInSchoolAtEnrollmentPerMonthByCBO().getIndicatorName(),stateCode, startDate, endDate));
                }
                else if(indicatorId.equalsIgnoreCase("vchivaccess"))
                {
                    SpecificServiceReportDao sservicedao=new SpecificServiceReportDaoImpl();
                    list.addAll(sservicedao.getNumberOfOvcSupportedToAccessHivServicesPerMonthByCBO(ind.getIndicatorForOVC_ACCPerMonthByCBO().getIndicatorName(),stateCode, startDate, endDate));
                }//
                else if(indicatorId.equalsIgnoreCase("noOfOvcServedPerMth"))
                {
                    //list.addAll(servicedao.getCummulativeNumberOfOvcServedByCBO(stateCode, startDate, endDate));
                    list.addAll(servicedao.getNumberOfOvcServedPerMonthByCBO(ind.getIndicatorForNumberOfOvcServedPerMonthByCBO().getIndicatorName(),stateCode, startDate, endDate,false));
                }
                else if(indicatorId.equalsIgnoreCase("vcgraduated"))
                {
                    list.addAll(dao.getNoOfOvcWithdrawnFromProgramWithinReportPeriodPerMonthByCBO(ind.getIndicatorForNumberOfOvcGraduated().getIndicatorName(),stateCode, startDate, endDate,NomisConstant.GRADUATED));
                }
                else if(indicatorId.equalsIgnoreCase("vclosttofup"))
                {
                    list.addAll(dao.getNoOfOvcWithdrawnFromProgramWithinReportPeriodPerMonthByCBO(ind.getIndicatorForNumberOfOvcWithdrawnDueToLostToFollowup().getIndicatorName(),stateCode, startDate, endDate,ReportUtility.LOST_TO_FOLLOWUP));
                }
                else if(indicatorId.equalsIgnoreCase("ovcmigrated"))
                {
                    list.addAll(dao.getNoOfOvcWithdrawnFromProgramWithinReportPeriodPerMonthByCBO(ind.getIndicatorForNumberOfOvcWithdrawnDueToMigration().getIndicatorName(),stateCode, startDate, endDate,ReportUtility.MIGRATED));
                }
                else if(indicatorId.equalsIgnoreCase("ovcagingout"))
                {
                    list.addAll(dao.getNoOfOvcWithdrawnFromProgramWithinReportPeriodPerMonthByCBO(ind.getIndicatorForNumberOfOvcWithdrawnDueToAgingOut().getIndicatorName(),stateCode, startDate, endDate,ReportUtility.AGED_OUT));
                }
                else if(indicatorId.equalsIgnoreCase("vcknowndeat"))
                {
                    list.addAll(dao.getNoOfOvcWithdrawnFromProgramWithinReportPeriodPerMonthByCBO(ind.getIndicatorForNumberOfOvcWithdrawnDueToKnownDeath().getIndicatorName(),stateCode, startDate, endDate,ReportUtility.DIED));
                }
                else if(indicatorId.equalsIgnoreCase("vctransferd"))
                {
                    list.addAll(dao.getNoOfOvcWithdrawnFromProgramWithinReportPeriodPerMonthByCBO(ind.getIndicatorForNumberOfOvcWithdrawnDueToTransfer().getIndicatorName(),stateCode, startDate, endDate,ReportUtility.TRANSFERED));
                }
                else if(indicatorId.equalsIgnoreCase("vcvolleftrp"))
                {
                    list.addAll(dao.getNoOfOvcWithdrawnFromProgramWithinReportPeriodPerMonthByCBO(ind.getIndicatorForNumberOfOvcVoluntarilyWithdrawnFromTheProgramReportPeriod().getIndicatorName(),stateCode, startDate, endDate,ReportUtility.VOLUNTARILY_WITHDRAWN));
                }
                
                
                else if(indicatorId.equalsIgnoreCase("vcgradserve"))
                {
                    list.addAll(servicedao.getNoOfOvcWithdrawnButServedWithinReportPeriodPerMonthByCBO(ind.getIndicatorForNumberOfOvcGraduatedButServedInReportPeriod().getIndicatorName(),stateCode, startDate, endDate,NomisConstant.GRADUATED));
                }
                else if(indicatorId.equalsIgnoreCase("vcltfuserve"))
                {
                    list.addAll(servicedao.getNoOfOvcWithdrawnButServedWithinReportPeriodPerMonthByCBO(ind.getIndicatorForNumberOfOvcLostToFollowupButServedInReportPeriod().getIndicatorName(),stateCode, startDate, endDate,NomisConstant.LOST_TO_FOLLOWUP));
                }
                else if(indicatorId.equalsIgnoreCase("vcmigrserve"))
                {
                    list.addAll(servicedao.getNoOfOvcWithdrawnButServedWithinReportPeriodPerMonthByCBO(ind.getIndicatorForNumberOfOvcMigratedButServedInReportPeriod().getIndicatorName(),stateCode, startDate, endDate,NomisConstant.MIGRATED));
                }
                else if(indicatorId.equalsIgnoreCase("vcageoserve"))
                {
                    list.addAll(servicedao.getNoOfOvcWithdrawnButServedWithinReportPeriodPerMonthByCBO(ind.getIndicatorForNumberOfOvcAgedoutButServedInReportPeriod().getIndicatorName(),stateCode, startDate, endDate,NomisConstant.AGED_OUT));
                }
                else if(indicatorId.equalsIgnoreCase("vctranserve"))
                {
                    list.addAll(servicedao.getNoOfOvcWithdrawnButServedWithinReportPeriodPerMonthByCBO(ind.getIndicatorForNumberOfOvcTransferedOutButServedInReportPeriod().getIndicatorName(),stateCode, startDate, endDate,NomisConstant.TRANSFERED));
                }
                else if(indicatorId.equalsIgnoreCase("vcdeadserve"))
                {
                    list.addAll(servicedao.getNoOfOvcWithdrawnButServedWithinReportPeriodPerMonthByCBO(ind.getIndicatorForNumberOfOvcDiedButServedInReportPeriod().getIndicatorName(),stateCode, startDate, endDate,NomisConstant.DIED));
                }
                else if(indicatorId.equalsIgnoreCase("vcvolwserve"))
                {
                    list.addAll(servicedao.getNoOfOvcWithdrawnButServedWithinReportPeriodPerMonthByCBO(ind.getIndicatorForNumberOfOvcVoluntarilyWithdrawnButServedInReportPeriod().getIndicatorName(),stateCode, startDate, endDate,NomisConstant.VOLUNTARILY_WITHDRAWN));
                }
                
                else if(indicatorId.equalsIgnoreCase("vccurenserv"))
                {
                    list.addAll(servicedao.getNumberOfOvcServedPerMonthByCBO(ind.getIndicatorForNumberOfOvcCurrentlyEnrolledAndServedInReportPeriod().getIndicatorName(),stateCode, startDate, endDate,true));
                }
                else if(indicatorId.equalsIgnoreCase("cgiverserve"))
                {
                    //list.addAll(servicedao.getCummulativeNumberOfOvcServedByCBO(stateCode, startDate, endDate));
                    list.addAll(hhsdao.getNumberOfCaregiversServedPerMonthByCBO(ind.getIndicatorForNumberOfCaregiversProvidedAtleastOneService().getIndicatorName(),stateCode, startDate, endDate));
                }
                else if(indicatorId.equalsIgnoreCase("cgservesilc"))
                {
                    list.addAll(hhsdao.getNumberOfCaregiversServedHESPerMonthByCBO(ind.getIndicatorForNumberOfCaregiversProvidedSILCSupport().getIndicatorName(),stateCode, startDate, endDate,"silc"));
                }
                else if(indicatorId.equalsIgnoreCase("cgservemfin"))
                {
                    list.addAll(hhsdao.getNumberOfCaregiversServedHESPerMonthByCBO(ind.getIndicatorForNumberOfCaregiversProvidedMicrofinanceSupport().getIndicatorName(),stateCode, startDate, endDate,"microfinance"));
                }
                else if(indicatorId.equalsIgnoreCase("cgserveecon"))
                {
                    list.addAll(hhsdao.getNumberOfCaregiversServedHESPerMonthByCBO(ind.getIndicatorForNumberOfCaregiversProvidedEconomicStrenghteningServices().getIndicatorName(),stateCode, startDate, endDate,"hes"));
                }
                else if(indicatorId.equalsIgnoreCase("noOfHivNegOvcServedPerMth"))
                {
                    list.addAll(servicedao.getNumberOfHIVNegativeOvcServedPerMonthByCBO(stateCode, startDate, endDate));
                }
                else if(indicatorId.equalsIgnoreCase("noOfHivPosOvcServedPerMth"))
                {
                    list.addAll(servicedao.getNumberOfHIVPositiveOvcServedPerMonthByCBO(stateCode, startDate, endDate));
                }
                else if(indicatorId.equalsIgnoreCase("noOfHivUnknownOvcServedPerMth"))
                {
                    list.addAll(servicedao.getNumberOfHIVUnknownOvcServedPerMonthByCBO(stateCode, startDate, endDate));
                }
                else if(indicatorId.equalsIgnoreCase("noOfOvcServedHivCarePerMth"))
                {
                    list.addAll(servicedao.getNumberOfOvcProvidedHIVServicesPerMonthByCBO(stateCode, startDate, endDate));
                }
                else if(indicatorId.equalsIgnoreCase("noOfOvcServedPsychoPerMth"))
                {
                    list.addAll(servicedao.getNumberOfOvcProvidedPsychosocialSupportServicesPerMonthByCBO(stateCode, startDate, endDate));
                }
                else if(indicatorId.equalsIgnoreCase("vcservednut"))
                {
                    list.addAll(servicedao.getNumberOfOvcProvidedNutritionalServicesPerMonthByCBO(stateCode, startDate, endDate));
                }
                else if(indicatorId.equalsIgnoreCase("noOfOvcServedHealthPerMth"))
                {
                    list.addAll(servicedao.getNumberOfOvcProvidedHealthServicesPerMonthByCBO(stateCode, startDate, endDate));
                }
                else if(indicatorId.equalsIgnoreCase("noOfOvcServedEducPerMth"))
                {
                    list.addAll(servicedao.getNumberOfOvcProvidedEducationalServicesPerMonthByCBO(stateCode, startDate, endDate));
                }
                else if(indicatorId.equalsIgnoreCase("noOfOvcServedProtPerMth"))
                {
                    list.addAll(servicedao.getNumberOfOvcProvidedProtectionServicesPerMonthByCBO(stateCode, startDate, endDate));
                }
                else if(indicatorId.equalsIgnoreCase("noOfOvcServedShelterPerMth"))
                {
                    list.addAll(servicedao.getNumberOfOvcProvidedShelterServicesPerMonthByCBO(stateCode, startDate, endDate));
                }
                else if(indicatorId.equalsIgnoreCase("noOfOvcServedEconStrgthPerMth"))
                {
                    list.addAll(servicedao.getNumberOfOvcProvidedEconomicStrengtheningServicesPerMonthByCBO(stateCode, startDate, endDate));
                }
                else if(indicatorId.equalsIgnoreCase("noOfOvcReferredPerMth"))
                {
                    list.addAll(servicedao.getNumberOfOvcReferredForServicesPerMonthByCBO(stateCode, startDate, endDate));
                }
                else if(indicatorId.equalsIgnoreCase("vcWithUpdatedHivAtFollowup"))
                {
                    list.addAll(fudao.getNoOfOVCWithUpdatedHIVStatusAtFollowup(stateCode, startDate, endDate));
                }
                else if(indicatorId.equalsIgnoreCase("hivPosVcIdentifiedAtFollowup"))
                {
                    list.addAll(fudao.getNoOfHIVPositiveOVCIdentifiedAtFollowup(stateCode, startDate, endDate));
                }
                else if(indicatorId.equalsIgnoreCase("vcWithUpdatedBirthRegAtFollowup"))
                {
                    list.addAll(fudao.getNoOfOVCWithUpdatedBirthRegistrationAtFollowup(stateCode, startDate, endDate));
                }
                else if(indicatorId.equalsIgnoreCase("vcevrinscflp"))
                {
                    list.addAll(fudao.getNumberOfOVCEnrolledInSchoolAtFollowupPerMth(ind.getNumberOfOVCEnrolledInSchoolAtFollowup().getIndicatorName(),stateCode, startDate, endDate));
                }
                else if(indicatorId.equalsIgnoreCase("vccurinscflp"))
                {
                    list.addAll(fudao.getNumberOfActiveOVCEnrolledInSchoolAtFollowupPerMth(ind.getNumberOfActiveOVCEnrolledInSchoolAtFollowup().getIndicatorName(),stateCode, startDate, endDate));
                }
                else if(indicatorId.equalsIgnoreCase("vcVulnerableToHiv"))
                {
                    list.addAll(dao.getNumberOfOvcVulnerableToHIVAtEnrollmentPerMonthByCBO(stateCode, startDate, endDate));
                }
                else if(indicatorId.equalsIgnoreCase("vcInChildHeadedHouseholds"))
                {
                    list.addAll(dao.getNumberOfOvcInChildHeadedHouseholdsPerMonthByCBO(stateCode, startDate, endDate));
                }
                else if(indicatorId.equalsIgnoreCase("vcSickWithLtdAccessToHealthCare"))
                {
                    list.addAll(csidao.getNumberOfOvcSickWithLimitedAccessToHealthCareAtBaseline(stateCode, startDate, endDate));
                }
                else if(indicatorId.equalsIgnoreCase("vcSickWithNoAccessToHealthCare"))
                {
                    list.addAll(csidao.getNumberOfOvcSickWithNoAccessToHealthCareAtBaseline(stateCode, startDate, endDate));
                }
                else if(indicatorId.equalsIgnoreCase("vulnerableOvc"))
                {
                    list.addAll(csidao.getNumberOfOvcWhoAreVulnerable(null,stateCode, startDate, endDate,1));
                }
                else if(indicatorId.equalsIgnoreCase("moreVulnerableOvc"))
                {
                    list.addAll(csidao.getNumberOfOvcWhoAreMoreVulnerable(null,stateCode, startDate, endDate,1));
                }
                else if(indicatorId.equalsIgnoreCase("mostVulnerableOvc"))
                {
                    list.addAll(csidao.getNumberOfOvcWhoAreMostVulnerable(null,stateCode, startDate, endDate,1));
                }
                else if(indicatorId.equalsIgnoreCase("noOfOvcThatDroppedOutOfSchool"))
                {
                    list.addAll(dao.getNumberOfOvcThatDroppedOutOfSchoolPerMonthByCBO(stateCode, startDate, endDate));
                }
                else if(indicatorId.equalsIgnoreCase("hhnenrolled"))
                {
                    list.addAll(hhedao.getNumberOfHouseholdsNewlyEnrolledPerMonthByCBO(ind.getIndicatorForNumberOfHouseholdsNewlyEnrolled().getIndicatorName(),stateCode,startDate, endDate,false));
                }
                else if(indicatorId.equalsIgnoreCase("hhcenrolled"))
                {
                    list.addAll(hhedao.getNumberOfHouseholdsEnrolledPerMonthByCBO(ind.getIndicatorForNumberOfHouseholdsCurrentlyEnrolled().getIndicatorName(),stateCode,true));
                }
                else if(indicatorId.equalsIgnoreCase("hheenrolled"))
                {
                    list.addAll(hhedao.getNumberOfHouseholdsEnrolledPerMonthByCBO(ind.getIndicatorForNumberOfHouseholdsEverEnrolled().getIndicatorName(),stateCode,false));
                }//
                else if(indicatorId.equalsIgnoreCase("hhdcebasass"))
                {
                    list.addAll(hvadao.getBaselineAssessementStatusOfHouseholdsCurrentlyEnrolledPerMth(ind.getIndicatorForNumberOfHouseholdsCurrentlyEnrolledWithBaselineAssessment().getIndicatorName(),"",stateCode));
                }
                else if(indicatorId.equalsIgnoreCase("hhdbasasses"))
                {
                    list.addAll(hvadao.getBaselineAssessementStatusOfHouseholdsEverEnrolledPerMth(ind.getIndicatorForNumberOfHouseholdsEverEnrolledWithBaselineAssessment().getIndicatorName(),"",stateCode));
                }
                else if(indicatorId.equalsIgnoreCase("hhgraduated"))
                {
                    String withdrawalQuery=" and "+rutil.getHhWithdrawnQuery(NomisConstant.GRADUATED);
                    list.addAll(hvadao.getBaselineAssessementStatusOfHouseholdsGraduatedPerMth(ind.getIndicatorForNumberOfHouseholdsWithdrawnDueToGraduation().getIndicatorName(),withdrawalQuery,stateCode));
                }
                else if(indicatorId.equalsIgnoreCase("vccebthcert"))
                {
                   list.addAll(brdao.getNumberOfOvcCurrentlyEnrolledWithBirthRegistration(ind.getIndicatorForNumberOfOvcCurrentlyEnrolledWithBirthCertificate().getIndicatorName(),stateCode));
                }//
                else if(indicatorId.equalsIgnoreCase("vcnassesmnt"))
                {
                   NutritionAssessmentDao nadao=new NutritionAssessmentDaoImpl();
                   list.addAll(nadao.getListofOvcWithNutrionalAssessmentByCohort(additionalQuery, startDate, endDate));
                }
                
                
                
                else if(indicatorId.equalsIgnoreCase("vcposserpsy"))
                {
                    list.addAll(getOvcServedByDomainAndHivStatusForReportDownload(additionalQuery, indicatorName,startDate,endDate,NomisConstant.DOMAIN_PSYCHOSOCIAL,NomisConstant.HIV_POSITIVE));
                }
                else if(indicatorId.equalsIgnoreCase("vcnegserpsy"))
                {
                    list.addAll(getOvcServedByDomainAndHivStatusForReportDownload(additionalQuery, indicatorName,startDate,endDate,NomisConstant.DOMAIN_PSYCHOSOCIAL,NomisConstant.HIV_NEGATIVE));
                }
                else if(indicatorId.equalsIgnoreCase("vcunkserpsy"))
                {
                    list.addAll(getOvcServedByDomainAndHivStatusForReportDownload(additionalQuery, indicatorName,startDate,endDate,NomisConstant.DOMAIN_PSYCHOSOCIAL,NomisConstant.HIV_UNKNOWN));
                }
                
                else if(indicatorId.equalsIgnoreCase("vcpossernut"))
                {
                    list.addAll(getOvcServedByDomainAndHivStatusForReportDownload(additionalQuery, indicatorName,startDate,endDate,NomisConstant.DOMAIN_NUTRITION,NomisConstant.HIV_POSITIVE));
                }
                else if(indicatorId.equalsIgnoreCase("vcnegsernut"))
                {
                    list.addAll(getOvcServedByDomainAndHivStatusForReportDownload(additionalQuery, indicatorName,startDate,endDate,NomisConstant.DOMAIN_NUTRITION,NomisConstant.HIV_NEGATIVE));
                }
                else if(indicatorId.equalsIgnoreCase("vcunksernut"))
                {
                    list.addAll(getOvcServedByDomainAndHivStatusForReportDownload(additionalQuery, indicatorName,startDate,endDate,NomisConstant.DOMAIN_NUTRITION,NomisConstant.HIV_UNKNOWN));
                }
                
                else if(indicatorId.equalsIgnoreCase("vcposserhlt"))
                {
                    list.addAll(getOvcServedByDomainAndHivStatusForReportDownload(additionalQuery, indicatorName,startDate,endDate,NomisConstant.DOMAIN_HEALTH,NomisConstant.HIV_POSITIVE));
                }
                else if(indicatorId.equalsIgnoreCase("vcnegserhlt"))
                {
                    list.addAll(getOvcServedByDomainAndHivStatusForReportDownload(additionalQuery, indicatorName,startDate,endDate,NomisConstant.DOMAIN_HEALTH,NomisConstant.HIV_NEGATIVE));
                }
                else if(indicatorId.equalsIgnoreCase("vcunkserhlt"))
                {
                    list.addAll(getOvcServedByDomainAndHivStatusForReportDownload(additionalQuery, indicatorName,startDate,endDate,NomisConstant.DOMAIN_HEALTH,NomisConstant.HIV_UNKNOWN));
                }
                
                else if(indicatorId.equalsIgnoreCase("vcposseredu"))
                {
                    list.addAll(getOvcServedByDomainAndHivStatusForReportDownload(additionalQuery, indicatorName,startDate,endDate,NomisConstant.DOMAIN_EDUCATION,NomisConstant.HIV_POSITIVE));
                }
                else if(indicatorId.equalsIgnoreCase("vcnegseredu"))
                {
                    list.addAll(getOvcServedByDomainAndHivStatusForReportDownload(additionalQuery, indicatorName,startDate,endDate,NomisConstant.DOMAIN_EDUCATION,NomisConstant.HIV_NEGATIVE));
                }
                else if(indicatorId.equalsIgnoreCase("vcunkseredu"))
                {
                    list.addAll(getOvcServedByDomainAndHivStatusForReportDownload(additionalQuery, indicatorName,startDate,endDate,NomisConstant.DOMAIN_EDUCATION,NomisConstant.HIV_UNKNOWN));
                }
                
                else if(indicatorId.equalsIgnoreCase("vcposserpro"))
                {
                    list.addAll(getOvcServedByDomainAndHivStatusForReportDownload(additionalQuery, indicatorName,startDate,endDate,NomisConstant.DOMAIN_PROTECTION,NomisConstant.HIV_POSITIVE));
                }
                else if(indicatorId.equalsIgnoreCase("vcnegserpro"))
                {
                    list.addAll(getOvcServedByDomainAndHivStatusForReportDownload(additionalQuery, indicatorName,startDate,endDate,NomisConstant.DOMAIN_PROTECTION,NomisConstant.HIV_NEGATIVE));
                }
                else if(indicatorId.equalsIgnoreCase("vcunkserpro"))
                {
                    list.addAll(getOvcServedByDomainAndHivStatusForReportDownload(additionalQuery, indicatorName,startDate,endDate,NomisConstant.DOMAIN_PROTECTION,NomisConstant.HIV_UNKNOWN));
                }
                
                else if(indicatorId.equalsIgnoreCase("vcpossersht"))
                {
                    list.addAll(getOvcServedByDomainAndHivStatusForReportDownload(additionalQuery, indicatorName,startDate,endDate,NomisConstant.DOMAIN_SHELTER,NomisConstant.HIV_POSITIVE));
                }
                else if(indicatorId.equalsIgnoreCase("vcnegsersht"))
                {
                    list.addAll(getOvcServedByDomainAndHivStatusForReportDownload(additionalQuery, indicatorName,startDate,endDate,NomisConstant.DOMAIN_SHELTER,NomisConstant.HIV_NEGATIVE));
                }
                else if(indicatorId.equalsIgnoreCase("vcunksersht"))
                {
                    list.addAll(getOvcServedByDomainAndHivStatusForReportDownload(additionalQuery, indicatorName,startDate,endDate,NomisConstant.DOMAIN_SHELTER,NomisConstant.HIV_UNKNOWN));
                }
                
                else if(indicatorId.equalsIgnoreCase("vcposserecs"))
                {
                    list.addAll(getOvcServedByDomainAndHivStatusForReportDownload(additionalQuery, indicatorName,startDate,endDate,NomisConstant.DOMAIN_ECONOMICSTRENTHENINIG,NomisConstant.HIV_POSITIVE));
                }
                else if(indicatorId.equalsIgnoreCase("vcnegserecs"))
                {
                    list.addAll(getOvcServedByDomainAndHivStatusForReportDownload(additionalQuery, indicatorName,startDate,endDate,NomisConstant.DOMAIN_ECONOMICSTRENTHENINIG,NomisConstant.HIV_NEGATIVE));
                }
                else if(indicatorId.equalsIgnoreCase("vcunkserecs"))
                {
                    list.addAll(getOvcServedByDomainAndHivStatusForReportDownload(additionalQuery, indicatorName,startDate,endDate,NomisConstant.DOMAIN_ECONOMICSTRENTHENINIG,NomisConstant.HIV_UNKNOWN));
                }
                
                
                
                else if(indicatorId.equalsIgnoreCase("posvcserved"))
                {
                    list.addAll(getOvcServedByHivStatusForReportDownload(additionalQuery, indicatorName,startDate,endDate,NomisConstant.HIV_POSITIVE,false));
                }
                else if(indicatorId.equalsIgnoreCase("negvcserved"))
                {
                    list.addAll(getOvcServedByHivStatusForReportDownload(additionalQuery, indicatorName,startDate,endDate,NomisConstant.HIV_NEGATIVE,false));
                }
                else if(indicatorId.equalsIgnoreCase("unkvcserved"))
                {
                    list.addAll(getOvcServedByHivStatusForReportDownload(additionalQuery, indicatorName,startDate,endDate,NomisConstant.HIV_UNKNOWN,false));
                }
                else if(indicatorId.equalsIgnoreCase("vcposceserv"))
                {
                    list.addAll(getOvcServedByHivStatusForReportDownload(additionalQuery, indicatorName,startDate,endDate,NomisConstant.HIV_POSITIVE,true));
                }
                else if(indicatorId.equalsIgnoreCase("vcnegceserv"))
                {
                    list.addAll(getOvcServedByHivStatusForReportDownload(additionalQuery, indicatorName,startDate,endDate,NomisConstant.HIV_NEGATIVE,true));
                }
                else if(indicatorId.equalsIgnoreCase("vcunkceserv"))
                {
                    list.addAll(getOvcServedByHivStatusForReportDownload(additionalQuery, indicatorName,startDate,endDate,NomisConstant.HIV_UNKNOWN,true));
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        
        return list;
    }
    public List getOvcServedByDomainAndHivStatusForReportDownload(String additionalQuery, String indicatorName,String startDate,String endDate,String domainName,String hivStatus)
    {
        DaoUtil util=new DaoUtil();
        String lastEnrollmentDateQuery=" and ovc.dateEnrollment <'"+startDate+"'";
        String lastHivStatusDateQuery=util.getLastHIVStatusDateQuery(startDate,"<");
        OvcServiceDao servicedao=new OvcServiceDaoImpl(); 
        List list=new ArrayList();
        try
        {
            list.addAll(servicedao.getOvcServedByDomainAndHivStatusForReportDownload(additionalQuery+lastEnrollmentDateQuery, indicatorName,startDate,endDate,domainName,hivStatus));
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return list;
    }
    public List getOvcServedByHivStatusForReportDownload(String additionalQuery, String indicatorName,String startDate,String endDate,String hivStatus,boolean currentlyEnrolled)
    {
        DaoUtil util=new DaoUtil();
        OvcServiceDao servicedao=new OvcServiceDaoImpl(); 
        String lastEnrollmentDateQuery=" and ovc.dateEnrollment <'"+startDate+"'";
        String currentlyEnrolledQuery=" ";
        if(currentlyEnrolled)
        {
            currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No")+lastEnrollmentDateQuery;
        }
        List list=new ArrayList();
        try
        {
            list.addAll(servicedao.getOvcServedByHivStatusForReportDownload(additionalQuery+lastEnrollmentDateQuery+currentlyEnrolledQuery, indicatorName,startDate,endDate,hivStatus));
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return list;
    }
    
}
