/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report;

import com.fhi.kidmap.business.Indicators;
import com.fhi.kidmap.business.OrganizationRecords;
import com.fhi.kidmap.dao.CustomIndicatorsReportDao;
import com.fhi.kidmap.dao.CustomIndicatorsReportDaoImpl;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.nomis.OperationsManagement.ReportPeriod;
import com.fhi.nomis.logs.NomisLogManager;
import com.fhi.nomis.nomisutils.DateManager;
import com.fhi.nomis.nomisutils.NomisConstant;
import com.fhi.nomis.nomisutils.ReportPeriodManager;
import com.nomis.business.DatimReportTemplate_Mer1718;
import com.nomis.business.ReportTemplate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author smomoh
 */
public class CustomIndicatorsReportManager 
{
    List parameterList=null;
    ReportPeriod fy=null;
    String mainReportPeriod="";
    IndicatorDictionary ind=new IndicatorDictionary();
    int[] ovcAgeDisaggregation={0,0,1,4,5,9,10,14,15,17};
    int[] ovcAndCaregiverAgeDisaggregation={0,0,1,4,5,9,10,14,15,17,18,24,25,200};
    String beneficiariesNewlyEnrolled=ind.getIndicatorForNumberOfBeneficiariesNewlyEnrolledWithinTheReportPeriod().getIndicatorId();
    String ovcActiveAndServeId=ind.getIndicatorForNumberOfOvcCurrentlyEnrolledAndServedInReportPeriod().getIndicatorId();
    String ovcGraduatedAndServeId=ind.getIndicatorForNumberOfOvcGraduatedButServedInReportPeriod().getIndicatorId();
    String ovcServeAndTransferedId=ind.getIndicatorForNumberOfOvcTransferedOutButServedInReportPeriod().getIndicatorId();
    String ovcExitedWithoutGraduationId=ind.getIndicatorForNumberOfOvcExitedWithoutGraduation().getIndicatorId();
    
    String cgActiveAndServeId=ind.getIndicatorForNumberOfActiveCaregiversServedWithinTheReportPeriod().getIndicatorId();
    String cgServedAndTransferedId=ind.getIndicatorForNumberOfCaregiversServedAndTransferedWithinTheReportPeriod().getIndicatorId();
    String cgServedAndGraduatedId=ind.getIndicatorForNumberOfCaregiversServedAndGraduatedWithinTheReportPeriod().getIndicatorId();
    String cgExitedWithoutGraduationId=ind.getIndicatorForNumberOfCaregiversExitedWithoutGraduation().getIndicatorId();
    String userName="auto";
    public void processCustomIndicatorsByLga(List paramList,List selectedIndicators,String currentUser)
    {
        DateManager dm=new DateManager();
        if(selectedIndicators==null || selectedIndicators.isEmpty())
        selectedIndicators=CustomIndicatorsReportManager.getCustomIndicators();
        fy=ReportPeriodManager.getStartOfFinancialYear(Integer.parseInt(paramList.get(4).toString()), Integer.parseInt(paramList.get(5).toString()));
        parameterList=paramList;
        if(currentUser !=null)
        userName=currentUser;
        ReportUtility rutil=new ReportUtility();
        String partnerCode="All";
        if(paramList !=null && paramList.size()>8)
        partnerCode=(String)paramList.get(8);
        String[] params=rutil.getQueryParam(paramList);
        String[] reportParams={params[0],params[1],params[2],params[3],"All","All","All",params[4],params[5],params[6],params[7],"All","All","All","All","0","17","All",params[18],params[3]};
        String[] reportParamsForExited={params[0],params[1],params[2],params[3],"All","All","All",fy.getStartMonth()+"",fy.getStartYear()+"",params[6],params[7],"All","All","All","All","0","17","All",params[18],params[3]};
        mainReportPeriod=dm.getMonthAsString(Integer.parseInt(reportParams[7]))+" "+reportParams[8]+"-"+dm.getMonthAsString(Integer.parseInt(reportParams[9]))+" "+reportParams[10];
        
        IndicatorDictionary ind=new IndicatorDictionary();
        //Indicators ind=null;
        String indicatorId=null;
        for(int i=0; i<selectedIndicators.size(); i++)
        {
            indicatorId=selectedIndicators.get(i).toString();
            
            if(indicatorId.equalsIgnoreCase(ind.getOvc_ENROLLEDIndicator().getIndicatorId()))
            {//OVC_ENROLLED
                processsOVCNewEnrolledAndServed(reportParams,partnerCode);
            }
            else if(indicatorId.equalsIgnoreCase(ind.getOvc_SERVIndicator().getIndicatorId()))
            {//OVC_SERV
                processsOVCCurrentlyEnrolledAndServed(reportParams,partnerCode);      
                processsOVCGraduatedAndServed(reportParamsForExited,partnerCode);
                processsOVCTransferedAndServed(reportParamsForExited,partnerCode);
                processsOVCExitedWithoutGraduationAndServed(reportParamsForExited,partnerCode);
            }
            else if(indicatorId.equalsIgnoreCase(ind.getOvc_HIVSTATIndicator().getIndicatorId()))
            {//OVC_HIVSTAT
                processsOVC_HIVSTATPositive(reportParams,partnerCode);
                processsOVC_HIVSTATNegative(reportParams,partnerCode);
                processsOVC_HIVSTATUnknown(reportParams,partnerCode);
            }
            else if(indicatorId.equalsIgnoreCase(ind.getOvc_TXLINKIndicator().getIndicatorId()))
            {//OVC_TXLINK
                processsOVCIdentifiedPositiveAndServed(reportParams,partnerCode);
                processsOVCNewlyTestedPositiveEnrolledOnTreatmentAndServed(reportParams,partnerCode);
                //processsEnrolledOnTreatment(reportParams,partnerCode);
            }
            else if(indicatorId.equalsIgnoreCase(ind.getOvc_ARTSUPPIndicator().getIndicatorId()))
            {//OVC_ARTSUPP
                processsOVCIdentifiedPositiveAndServed(reportParams,partnerCode);
                processsOVCAdherenceToTreatment(reportParams,partnerCode);
            }
            else if(indicatorId.equalsIgnoreCase(ind.getOvc_HIVRISKASSIndicator().getIndicatorId()))
            {//OVC_HIVRISKASS
                processsOVCWithUnknownHivAssessedForHIVRisk(reportParams,partnerCode);
                processsOVCWithNegativeHivAssessedForHIVRisk(reportParams,partnerCode);
            }
            else if(indicatorId.equalsIgnoreCase(ind.getOvc_BIRTHCERTIndicator().getIndicatorId()))
            {//OVC_BIRTHCERT
                processsOVCWithBirthCertificateServed(reportParams,partnerCode);
            }
            else if(indicatorId.equalsIgnoreCase(ind.getOvc_PROTECTIndicator().getIndicatorId()))
            {//OVC_PROTECT
                processsOVCAbusedAndExploited(reportParams,partnerCode);
                processsOVCPostViolenceLinkedToGovt(reportParams,partnerCode);
            }
            else if(indicatorId.equalsIgnoreCase(ind.getOvc_HTSLINKIndicator().getIndicatorId()))
            {//OVC_HTSLINK
                processsHivUnknownOrNegativeOVCServedInReportPeriod(reportParams,partnerCode);
                //processsOVCReferredForTestingOnly(reportParams,partnerCode);
                processsOVCReferredForTestingAndTestedAndObtainedResult(reportParams,partnerCode);
            }
            else if(indicatorId.equalsIgnoreCase(ind.getOvc_NUTRITIONIndicator().getIndicatorId()))
            {//OVC_NUTRITION
                processsOVCSeverelyMalnourished(reportParams,partnerCode);
                processsOVCSeverelyMalnourishedAndServedNutrition(reportParams,partnerCode);
            }
            else if(indicatorId.equalsIgnoreCase(ind.getOvc_EDUIndicator().getIndicatorId()))
            {//OVC_EDU
                processsOVCCurrentlyInSchool(reportParams,partnerCode);
                processsOVCRegularlyAttendingSchool(reportParams,partnerCode);
            }
            else if(indicatorId.equalsIgnoreCase(ind.getOvc_HIVPREVIndicator().getIndicatorId()))
            {//OVC_HHGRAD
                processsNumberOfAdolescentsProvidedHIVPreventionServices(reportParams,partnerCode);
            }
            else if(indicatorId.equalsIgnoreCase(ind.getOvc_HHGRADIndicator().getIndicatorId()))
            {//OVC_HHGRAD
                processsNumberOfActiveHouseholdsWhoseOvcWereServed(reportParams,partnerCode);
                processsNumberOfGraduatedHouseholdsWhoseOvcWereServed(reportParamsForExited,partnerCode);
            }
            else if(indicatorId.equalsIgnoreCase(ind.getOvc_ECONSIndicator().getIndicatorId()))
            {//OVC_ECONS
                processsNumberOfHouseholdsThatResolveEmergencyNeeds(reportParams,partnerCode);
            }
        }
        /*processsCaregiversCurrentlyEnrolledAndServed(reportParams,partnerCode);
        processsCaregiversGraduatedAndServed(reportParams,partnerCode);
        processsCaregiversTransferedAndServed(reportParams,partnerCode);
        processsCaregiversExitedWithoutGraduationAndServed(reportParams,partnerCode);*/
    }
    public void processsOVCCurrentlyEnrolledAndServed(String[] reportParams,String partnerCode)
    {
        String indicatorCode=ind.getIndicatorForNumberOfOvcCurrentlyEnrolledAndServedInReportPeriod().getIndicatorId();
        processDataByCBO(reportParams,indicatorCode,partnerCode);
    }
    public void processsOVCGraduatedAndServed(String[] reportParams,String partnerCode)
    {
        String indicatorCode=ind.getIndicatorForNumberOfOvcGraduatedButServedInReportPeriod().getIndicatorId();
        processDataByCBO(reportParams,indicatorCode,partnerCode);
    }
    public void processsOVCTransferedAndServed(String[] reportParams,String partnerCode)
    {
        String indicatorCode=ind.getIndicatorForNumberOfOvcTransferedOutButServedInReportPeriod().getIndicatorId();
        processDataByCBO(reportParams,indicatorCode,partnerCode);
    }
    public void processsOVCExitedWithoutGraduationAndServed(String[] reportParams,String partnerCode)
    {
        String indicatorCode=ind.getIndicatorForNumberOfOvcExitedWithoutGraduation().getIndicatorId();
        processDataByCBO(reportParams,indicatorCode,partnerCode);
    }
    public void processsOVCNewEnrolledAndServed(String[] reportParams,String partnerCode)
    {
        String indicatorCode=ind.getIndicatorForNumberOfBeneficiariesNewlyEnrolledWithinTheReportPeriod().getIndicatorId();
        processDataByCBO(reportParams,indicatorCode,partnerCode);
    }
    
    public void processsOVC_HIVSTATPositive(String[] reportParams,String partnerCode)
    {
        String indicatorCode=ind.getIndicatorForNumberOfOVC_HIVSTATPOSITIVE().getIndicatorId();
        processDataByCBO(reportParams,indicatorCode,partnerCode);
    }
    public void processsOVC_HIVSTATNegative(String[] reportParams,String partnerCode)
    {
        String indicatorCode=ind.getIndicatorForNumberOfOVC_HIVSTATNEGATIVE().getIndicatorId();
        processDataByCBO(reportParams,indicatorCode,partnerCode);
    }
    public void processsOVC_HIVSTATUnknown(String[] reportParams,String partnerCode)
    {
        String indicatorCode=ind.getIndicatorForNumberOfOVC_HIVSTATUNKNOWN().getIndicatorId();
        processDataByCBO(reportParams,indicatorCode,partnerCode);
    }
    public void processsEnrolledOnTreatment(String[] reportParams,String partnerCode)
    {
        String indicatorCode=ind.getIndicatorForNumberOfHIVPositiveOvcCurrentlyEnrolledOnART().getIndicatorId();
        processDataByCBO(reportParams,indicatorCode,partnerCode);
    }
    public void processsOVCIdentifiedPositiveAndServed(String[] reportParams,String partnerCode)
    {
        String indicatorCode=ind.getIndicatorForNumberOfHivPositiveOvcIdentifiedAndServedWithinTheReportPeriod().getIndicatorId();
        processDataByCBO(reportParams,indicatorCode,partnerCode);
    }
    
    public void processsOVCNewlyTestedPositiveEnrolledOnTreatmentAndServed(String[] reportParams,String partnerCode)
    {
        String indicatorCode=ind.getIndicatorForNumberOfOvcNewlyTestedPositiveAndLinkedToTreatment().getIndicatorId();
        processDataByCBO(reportParams,indicatorCode,partnerCode);
    }
    public void processsOVCAdherenceToTreatment(String[] reportParams,String partnerCode)
    {
        String indicatorCode=ind.getIndicatorForNumberOfOvcSelfReportingAdherenceToTreatment().getIndicatorId();
        processDataByCBO(reportParams,indicatorCode,partnerCode);
    }
    public void processsOVCWithUnknownHivAssessedForHIVRisk(String[] reportParams,String partnerCode)
    {
        String indicatorCode=ind.getNoOfHivUnknownOvcAssessedForHIVRiskAndServedWithinReportPeriod().getIndicatorId();
        processDataByCBO(reportParams,indicatorCode,partnerCode);
    }
    public void processsOVCWithNegativeHivAssessedForHIVRisk(String[] reportParams,String partnerCode)
    {
        String indicatorCode=ind.getNoOfHivNegativeOvcAssessedForHIVRiskAndServedWithinReportPeriod().getIndicatorId();
        processDataByCBO(reportParams,indicatorCode,partnerCode);
    }
    public void processsOVCWithBirthCertificateServed(String[] reportParams,String partnerCode)
    {
        String indicatorCode=ind.getNoOfOvcServedWithinTheReportPeriodThatHasBirthCert().getIndicatorId();
        processDataByCBO(reportParams,indicatorCode,partnerCode);
    }
    //
    public void processsOVCAbusedAndExploited(String[] reportParams,String partnerCode)
    {
        String indicatorCode=ind.getIndicatorForNumberOfOvcAbusedWithinReportPeriod().getIndicatorId();
        processDataByCBO(reportParams,indicatorCode,partnerCode);
    }
    public void processsOVCPostViolenceLinkedToGovt(String[] reportParams,String partnerCode)
    {
        String indicatorCode=ind.getIndicatorForNumberOfOvcLinkedToGovtForPostViolenceServicesWithinReportPeriod().getIndicatorId();
        processDataByCBO(reportParams,indicatorCode,partnerCode);
    }
    public void processsHivUnknownOrNegativeOVCServedInReportPeriod(String[] reportParams,String partnerCode)
    {
        String indicatorCode=ind.getIndicatorForNumberOfHivUnknownOrNegativeOvcServedWithinTheReportPeriod().getIndicatorId();
        processDataByCBO(reportParams,indicatorCode,partnerCode);
    }
    public void processsOVCReferredForTestingOnly(String[] reportParams,String partnerCode)
    {
        String indicatorCode=ind.getIndicatorForNumberOfOvcTestedForHIV().getIndicatorId();
        processDataByCBO(reportParams,indicatorCode,partnerCode);
    }
    public void processsOVCReferredForTestingAndTestedAndObtainedResult(String[] reportParams,String partnerCode)
    {
        String indicatorCode=ind.getIndicatorForNumberOfOvcTestedAndReceivedResult().getIndicatorId();
        processDataByCBO(reportParams,indicatorCode,partnerCode);
    }
    
    public void processsOVCSeverelyMalnourished(String[] reportParams,String partnerCode)
    {
        String indicatorCode=ind.getIndicatorForNumberOfSeverelyMalnourishedOvcCurrently().getIndicatorId();
        processDataByCBO(reportParams,indicatorCode,partnerCode);
    }
    public void processsOVCSeverelyMalnourishedAndServedNutrition(String[] reportParams,String partnerCode)
    {
        String indicatorCode=ind.getIndicatorForNumberOfSeverelyMalnourishedOvcServedNutritonalServices().getIndicatorId();
        processDataByCBO(reportParams,indicatorCode,partnerCode);
    }
    public void processsOVCRegularlyAttendingSchool(String[] reportParams,String partnerCode)
    {
        String indicatorCode=ind.getIndicatorForNumberOfOvcRegularlyAttendingSchool().getIndicatorId();
        processDataByCBO(reportParams,indicatorCode,partnerCode);
    }
    public void processsOVCCurrentlyInSchool(String[] reportParams,String partnerCode)
    {
        String indicatorCode=ind.getIndicatorForNumberOfOvcCurrentlyInSchool().getIndicatorId();
        processDataByCBO(reportParams,indicatorCode,partnerCode);
    }
    public void processsNumberOfAdolescentsProvidedHIVPreventionServices(String[] reportParams,String partnerCode)
    {
        String indicatorCode=ind.getIndicatorForNumberOfAdolescentsProvidedHIVPreventionServices().getIndicatorId();
        processDataByCBO(reportParams,indicatorCode,partnerCode);
    }
    
    
    
    //Caregiver indicators starts here
    /*public void processsCaregiversCurrentlyEnrolledAndServed(String[] reportParams,String partnerCode)
    {
        //String indicatorCode=ind.getIndicatorForNumberOfActiveCaregiversServedWithinTheReportPeriod().getIndicatorId();
        processAdultDataByCBO(reportParams,cgActiveAndServeId,partnerCode);
    }
    public void processsCaregiversGraduatedAndServed(String[] reportParams,String partnerCode)
    {
        //String indicatorCode=ind.getIndicatorForNumberOfCaregiversServedAndGraduatedWithinTheReportPeriod().getIndicatorId();
        processAdultDataByCBO(reportParams,cgServedAndGraduatedId,partnerCode);
    }
    public void processsCaregiversTransferedAndServed(String[] reportParams,String partnerCode)
    {
        //String indicatorCode=ind.getIndicatorForNumberOfCaregiversServedAndTransferedWithinTheReportPeriod().getIndicatorId();
        processAdultDataByCBO(reportParams,cgServedAndTransferedId,partnerCode);
    }
    public void processsCaregiversExitedWithoutGraduationAndServed(String[] reportParams,String partnerCode)
    {
        //String indicatorCode=ind.getIndicatorForNumberOfCaregiversExitedWithoutGraduation().getIndicatorId();
        processAdultDataByCBO(reportParams,cgExitedWithoutGraduationId,partnerCode);
    }
    */
    //Household indicators starts here
    public void processsNumberOfActiveHouseholdsWhoseOvcWereServed(String[] reportParams,String partnerCode)
    {
        String indicatorCode=ind.getIndicatorNumberOfHouseholdsWhoseOvcWereServedWithinReportingPeriod().getIndicatorId();
        processDataByCBO(reportParams,indicatorCode,partnerCode);
    }
    public void processsNumberOfGraduatedHouseholdsWhoseOvcWereServed(String[] reportParams,String partnerCode)
    {
        String indicatorCode=ind.getIndicatorNumberOfGraduatedHouseholdsWhoseOvcWereServedWithinReportingPeriod().getIndicatorId();
        processDataByCBO(reportParams,indicatorCode,partnerCode);
    }
    public void processsNumberOfHouseholdsThatResolveEmergencyNeeds(String[] reportParams,String partnerCode)
    {
        String indicatorCode=ind.getIndicatorNumberOfHouseholdsThatCanSolveEmergencyNeedsWithinReportingPeriod().getIndicatorId();
        processDataByCBO(reportParams,indicatorCode,partnerCode);
    }
    
    
    private void processDataByCBO(String[] reportParams,String indicatorCode,String partnerCode)
    {
        //DateManager dm=new DateManager();
        DaoUtil util=new DaoUtil();
        SummaryStatisticsReportGenerator ssrg=new SummaryStatisticsReportGenerator();
        //List mainList=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        //String lgaCode=reportParams[1];
        String stateName=util.getStateName(reportParams[0]);
        String lgaName=util.getLgaName(reportParams[1]);
        String cboName=" ";
        int[] ageDisaggregation=ovcAgeDisaggregation;
        if(indicatorCode.equalsIgnoreCase(beneficiariesNewlyEnrolled))
        ageDisaggregation=ovcAndCaregiverAgeDisaggregation;
        //String period=dm.getMonthAsString(Integer.parseInt(reportParams[7]))+" "+reportParams[8]+"-"+dm.getMonthAsString(Integer.parseInt(reportParams[9]))+" "+reportParams[10];
        try
        {
            String[] indicatorArray={indicatorCode};
            List list=null;
            List valueList=null;
            String cboCode="All";
            reportParams[2]=cboCode;
            for(int j=0; j<ageDisaggregation.length; j+=2)
            {
                System.err.println("About to pull for "+ageDisaggregation[j]+" to "+ageDisaggregation[j+1]);
                //set the start age and end age
                reportParams[15]=ageDisaggregation[j]+"";
                reportParams[16]=ageDisaggregation[j+1]+"";
                list=ssrg.getOvcEnrolledSummStatistics("",reportParams,indicatorArray);
                valueList=(List)list.get(0);
                maleList.addAll(getMaleList(valueList));
                femaleList.addAll(getFemaleList(valueList));
                System.err.println("Pulled data for "+ageDisaggregation[j]+" to "+ageDisaggregation[j+1]);
            }//
            if(indicatorCode.equalsIgnoreCase(ovcActiveAndServeId) || indicatorCode.equalsIgnoreCase(ovcGraduatedAndServeId) || indicatorCode.equalsIgnoreCase(ovcServeAndTransferedId) || indicatorCode.equalsIgnoreCase(ovcExitedWithoutGraduationId))
            {
                List mainList=processDataFor18AndAboveByCBO(parameterList,maleList,femaleList,reportParams,indicatorCode);
                maleList=(List)mainList.get(0);
                femaleList=(List)mainList.get(1);
            }
           ReportTemplate rt=getReportTemplate(stateName,lgaName,cboName,partnerCode,indicatorCode,maleList,femaleList,mainReportPeriod);
           saveReportTemplate(rt);      
        }
        catch(Exception ex)
        {
            NomisLogManager.logStackTrace(ex);
        }
        //return mainList;
    }
    
    private List processDataFor18AndAboveByCBO(List paramList,List maleList,List femaleList,String[] reportParams,String indicatorCode)
    {
        List mainList=new ArrayList();
        ReportParameterManager rpm=new ReportParameterManager();
        ReportParameterTemplate rpt=rpm.getPopulatedReportParameterTemplate(paramList);
        DatimCaregiverReport dcr=new DatimCaregiverReport();
        
        //SummaryStatisticsReportGenerator ssrg=new SummaryStatisticsReportGenerator();
        //int[] caregiverAgeDisaggregation={18,24,25,200};
        //String cgiverIndicator=indicatorCode; 
        if(indicatorCode.equalsIgnoreCase(beneficiariesNewlyEnrolled)) 
        {
            DatimReportTemplate dform18to24=dcr.getDatimCaregiverNewlyEnrolledReport(rpt,18,24);
            DatimReportTemplate dform25AndAbove=dcr.getDatimCaregiverNewlyEnrolledReport(rpt,25,200);
            maleList.add(dform18to24.getOvc_servMale18To24());
            maleList.add(dform25AndAbove.getOvc_servMale25AndAbove());
            femaleList.add(dform18to24.getOvc_servFemale18To24());
            femaleList.add(dform25AndAbove.getOvc_servFemale25AndAbove());
            //cgiverIndicator=cgActiveAndServeId;
        }
        else if(indicatorCode.equalsIgnoreCase(ovcActiveAndServeId)) 
        {
            DatimReportTemplate dform18to24Active=dcr.getDatimCaregiverActiveAndServedReportFor18To24(rpt);
            DatimReportTemplate dform25AndAboveActive=dcr.getDatimCaregiverActiveAndServedReportFor25AndAbove(rpt);
            maleList.add(dform18to24Active.getOvc_servMale18To24());
            maleList.add(dform25AndAboveActive.getOvc_servMale25AndAbove());
            femaleList.add(dform18to24Active.getOvc_servFemale18To24());
            femaleList.add(dform25AndAboveActive.getOvc_servFemale25AndAbove());
            //cgiverIndicator=cgActiveAndServeId;
        }
        else if(indicatorCode.equalsIgnoreCase(ovcGraduatedAndServeId))
        {
            DatimReportTemplate dform18to24Graduated=dcr.getDatimCaregiverGraduatedAndServedReportFor18To24(rpt);
            DatimReportTemplate dform25AndAboveGraduated=dcr.getDatimCaregiverGraduatedAndServedReportFor25AndAbove(rpt);
            maleList.add(dform18to24Graduated.getOvc_servMale18To24());
            maleList.add(dform25AndAboveGraduated.getOvc_servMale25AndAbove());
            femaleList.add(dform18to24Graduated.getOvc_servFemale18To24());
            femaleList.add(dform25AndAboveGraduated.getOvc_servFemale25AndAbove());
            //cgiverIndicator=cgServedAndGraduatedId;
        }
        else if(indicatorCode.equalsIgnoreCase(ovcServeAndTransferedId))
        {
            DatimReportTemplate dform18to24Transfered=dcr.getDatimCaregiverTransferedAndServedReportFor18To24(rpt);
            DatimReportTemplate dform25AndAboveTransfered=dcr.getDatimCaregiverTransferedAndServedReportFor25AndAbove(rpt);
            maleList.add(dform18to24Transfered.getOvc_servMale18To24());
            maleList.add(dform25AndAboveTransfered.getOvc_servMale25AndAbove());
            femaleList.add(dform18to24Transfered.getOvc_servFemale18To24());
            femaleList.add(dform25AndAboveTransfered.getOvc_servFemale25AndAbove());
        }
        else if(indicatorCode.equalsIgnoreCase(ovcExitedWithoutGraduationId))
        {
            DatimReportTemplate dform18to24ExitedWithoutGraduation=dcr.getDatimCaregiverServedAndExitedWithoutGraduationReportFor18To24(rpt);
            DatimReportTemplate dform25AndAboveExitedWithoutGraduation=dcr.getDatimCaregiverServedAndExitedWithoutGraduationReportFor25AndAbove(rpt);
            maleList.add(dform18to24ExitedWithoutGraduation.getOvc_servMale18To24());
            maleList.add(dform25AndAboveExitedWithoutGraduation.getOvc_servMale25AndAbove());
            femaleList.add(dform18to24ExitedWithoutGraduation.getOvc_servFemale18To24());
            femaleList.add(dform25AndAboveExitedWithoutGraduation.getOvc_servFemale25AndAbove());
        }
        mainList.add(maleList);
        mainList.add(femaleList);
        
        return mainList;
    }
    
    public ReportTemplate getReportTemplate(String stateCode,String lgaCode,String cboName,String partnerCode,String indicatorId,List maleList,List femaleList,String period)
    {
        IndicatorWarehouse indw=new IndicatorWarehouse();
        Indicators ind=indw.getIndicatorById(indicatorId);
        if(ind.getAlternateName()==null)
        ind.setAlternateName(ind.getIndicatorName());
        //String hhgradOvcServedId=ind.getIndicatorId();
        //String ovcEconsId=ind.getIndicatorId();
        ReportTemplate rt=new ReportTemplate();
        rt.setState(stateCode);
        rt.setLga(lgaCode);
        rt.setCbo(cboName);
        rt.setIndicator(ind);
        rt.setIndicatorId(indicatorId);
        rt.setIndicatorName(indicatorId);
        rt.setPeriod(period);
        rt.setPartnerCode(partnerCode);
        //rt.setp
        
        if(ind !=null)
        {
            rt.setIndicatorName(ind.getAlternateName());
            rt.setMerCode(ind.getMerCode());
            rt.setOtherDisaggregation("other");
            rt.setUser(userName);
            rt.setDateCreated(DateManager.getDateInstance(DateManager.getCurrentDate()));
        }        
        
        int maleValue=0;
        int femaleValue=0;
        int maleTotal=0;
        int femaleTotal=0;
        if(maleList !=null && femaleList !=null)
        {
            if(ind !=null && ind.getIndicatorType().equalsIgnoreCase(NomisConstant.HOUSEHOLD_TYPE))
            {
                maleValue=Integer.parseInt(maleList.get(0).toString());
                rt.setGrandTotal(maleValue);
            }
            else
            {
                for(int i=0; i<maleList.size(); i++)
                {
                    maleValue=Integer.parseInt(maleList.get(i).toString());
                    femaleValue=Integer.parseInt(femaleList.get(i).toString());
                    maleTotal+=maleValue;
                    femaleTotal+=femaleValue;
                    if(i==0)
                    {
                        rt.setMaleLessThan1(maleValue);
                        rt.setFemaleLessThan1(femaleValue);
                    }
                    else if(i==1)
                    {
                        rt.setMale1to4(maleValue);
                        rt.setFemale1to4(femaleValue);
                    }
                    else if(i==2)
                    {
                        rt.setMale5to9(maleValue);
                        rt.setFemale5to9(femaleValue);
                    }
                    else if(i==3)
                    {
                        rt.setMale10to14(maleValue);
                        rt.setFemale10to14(femaleValue);
                    }
                    else if(i==4)
                    {
                        rt.setMale15to17(maleValue);
                        rt.setFemale15to17(femaleValue);
                    }
                    else if(i==5)
                    {
                        rt.setMale18to24(maleValue);
                        rt.setFemale18to24(femaleValue);
                    }
                    else if(i==6)
                    {
                        rt.setMale25AndAbove(maleValue);
                        rt.setFemale25AndAbove(femaleValue);
                    }
                }
                int grandTotal=maleTotal+femaleTotal;
                rt.setMaleTotal(maleTotal);
                rt.setFemaleTotal(femaleTotal);
                rt.setGrandTotal(grandTotal);
            }
            
        }
        return rt;
    }
    
    private List getMaleList(List valueList)
    {
        List maleList=new ArrayList();
        if(valueList==null || valueList.size()<2)
        {
            maleList.add(0);
        }
        else
        {
            maleList.add(valueList.get(1));
        }
        return maleList;
    }
    private List getFemaleList(List valueList)
    {
        List femaleList=new ArrayList();
        if(valueList==null || valueList.size()<3)
        {
            femaleList.add(0);
        }
        else
        {
            femaleList.add(valueList.get(2));
        }
        return femaleList;
    }
    public static void processCustomIndicatorsReport(List list)
    {
        try
        {
        //CustomIndicatorsReportDao cirbdao=new CustomIndicatorsReportDaoImpl();
        if(list !=null)
        {
            ReportTemplate rt=null;
            for(Object obj:list)
            {
                rt = (ReportTemplate)obj; 
                saveReportTemplate(rt);
                //cirbdao.saveOrUpdateReportTemplate(rt);
            }
        }
        }
        catch(Exception ex)
        {
            NomisLogManager.logStackTrace(ex);
        }
    }
    public static void saveReportTemplate(ReportTemplate rt)
    {
        try
        {
            if(rt !=null)
            {
                CustomIndicatorsReportDao cirbdao=new CustomIndicatorsReportDaoImpl();
                ReportTemplate dupRt=cirbdao.getReportTemplate(rt);//.getReportTemplate(rt.getLga(), rt.getCbo(), rt.getPartnerCode(), rt.getIndicatorName(), rt.getMerCode(), rt.getOtherDisaggregation(), rt.getPeriod());
                if(dupRt !=null)
                {
                    rt.setRecordId(dupRt.getRecordId());
                    cirbdao.updateReportTemplate(rt);
                    System.err.println("Record with "+rt.getIndicatorName()+" updated");
                }
                else
                {
                    cirbdao.saveOrUpdateReportTemplate(rt);
                    System.err.println("Record with "+rt.getIndicatorName()+" saved");
                }
            }        
        }
        catch(Exception ex)
        {
            NomisLogManager.logStackTrace(ex);
        }
    }
    public List getListOfCBOs(String lgaCode)
    {
        List cboList=new ArrayList();
        try
        {
            DaoUtil util=new DaoUtil();
            List cboCodeList=util.getHouseholdEnrollmentDaoInstance().getDistinctCBOCodesByLga(lgaCode);
            if(cboCodeList !=null)
            {
                
                for(int i=0; i<cboCodeList.size(); i++)
                {
                    if(cboCodeList.get(i) !=null)
                    {
                        OrganizationRecords or=util.getOrganizationRecords(cboCodeList.get(i).toString());
                        if(or==null)
                        {
                            or=new OrganizationRecords();
                            or.setOrgCode(cboCodeList.get(i).toString());
                            or.setOrgName(cboCodeList.get(i).toString());
                            or.setLga(lgaCode);
                        }
                        cboList.add(or);
                    }
                }
            }
        }
        catch(Exception ex)
        {
            NomisLogManager.logStackTrace(ex);
        }
        return cboList;
    }
    public static List getCustomIndicators()
    {
        List list=new ArrayList();
        IndicatorDictionary ind=new IndicatorDictionary();
        list.add(ind.getOvc_ARTSUPPIndicator());
        list.add(ind.getOvc_BIRTHCERTIndicator());
        list.add(ind.getOvc_ECONSIndicator());
        list.add(ind.getOvc_ENROLLEDIndicator());
        list.add(ind.getOvc_EDUIndicator());
        list.add(ind.getOvc_HHGRADIndicator());
        list.add(ind.getOvc_HIVPREVIndicator());
        list.add(ind.getOvc_HIVRISKASSIndicator());
        list.add(ind.getOvc_HIVSTATIndicator());
        list.add(ind.getOvc_HTSLINKIndicator());
        list.add(ind.getOvc_PROTECTIndicator());
        list.add(ind.getOvc_SERVIndicator());
        list.add(ind.getOvc_TXLINKIndicator());
        list.add(ind.getOvc_NUTRITIONIndicator());
        return list;
    }
}
