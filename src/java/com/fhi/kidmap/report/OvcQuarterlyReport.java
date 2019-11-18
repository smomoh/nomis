/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report;

import com.fhi.kidmap.business.Indicators;
import com.fhi.kidmap.business.OrganizationRecords;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.nomis.nomisutils.DateManager;
import com.fhi.nomis.nomisutils.NomisConstant;
import com.nomis.business.ReportTemplate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author smomoh
 */
public class OvcQuarterlyReport 
{
    public List getQuarterlyReport(List paramList,String indicatorCode)
    {
        ReportUtility rutil=new ReportUtility();
        
        List mainList=new ArrayList();
        String[] params=rutil.getQueryParam(paramList);
        String[] reportParams={params[0],params[1],params[2],params[3],"All","All","All",params[4],params[5],params[6],params[7],"All","All","All","All","0","17","All",params[3],params[3]};
        IndicatorDictionary ind=new IndicatorDictionary();
        String sex="Male";
        String currentlyEnrolledAndServedId=ind.getIndicatorForNumberOfOvcCurrentlyEnrolledAndServedInReportPeriod().getIndicatorId();
        String servedAndGraduated=ind.getIndicatorForNumberOfOvcGraduatedButServedInReportPeriod().getIndicatorId();
        String servedAndTransfered=ind.getIndicatorForNumberOfOvcTransferedOutButServedInReportPeriod().getIndicatorId();
        String exitedWithoutGraduationId=ind.getIndicatorForNumberOfOvcExitedWithoutGraduation().getIndicatorId();
        
        String newlyEnrolledAndServedId=ind.getIndicatorForNumberOfBeneficiariesNewlyEnrolledWithinTheReportPeriod().getIndicatorId();
        
        /*String currentlyEnrolledHivPositiveOvcServedId=ind.getIndicatorForNumberOfHivPositiveOvcCurrentlyEnrolledProvidedWithAtleastOneService().getIndicatorId();
        
        //String currentlyEnrolledOvcInCareServedId=ind.getIndicatorForNumberOfHIVPositiveOvcEverEnrolledInCare().getIndicatorId();
        String currentlyEnrolledHivNegativeOvcServedId=ind.getIndicatorForNumberOfHivNegativeOvcCurrentlyEnrolledProvidedWithAtleastOneService().getIndicatorId();
        String currentlyEnrolledHivUnknownOvcServedId=ind.getIndicatorForNumberOfHivUnknownOvcCurrentlyEnrolledProvidedWithAtleastOneService().getIndicatorId();*/
        String ovc_hivstatPositiveId=ind.getIndicatorForNumberOfOVC_HIVSTATPOSITIVE().getIndicatorId();
        String ovc_hivstatNegativeId=ind.getIndicatorForNumberOfOVC_HIVSTATNEGATIVE().getIndicatorId();
        String ovc_hivstatUnknownId=ind.getIndicatorForNumberOfOVC_HIVSTATUNKNOWN().getIndicatorId();
        
        /*String hivPositiveServedId=ind.getIndicatorForNumberOfHivPositiveOvcProvidedWithAtleastOneService().getIndicatorId();
        String hivNegativeServedId=ind.getIndicatorForNumberOfHivNegativeOvcProvidedWithAtleastOneService().getIndicatorId();
        String hivUnknownServedId=ind.getIndicatorForNumberOfHivUnknownOvcProvidedWithAtleastOneService().getIndicatorId();*/
        //enrolledOvcOnARTServedId,
        String enrolledOnARTId=ind.getIndicatorForNumberOfHIVPositiveOvcCurrentlyEnrolledOnART().getIndicatorId();
        //String enrolledOvcOnARTServedId=ind.getIndicatorForNumberOfHivPositiveOvcOnARTCurrentlyEnrolledProvidedWithAtleastOneService().getIndicatorId();
        String posIdentifiedAndServedId=ind.getIndicatorForNumberOfHivPositiveOvcIdentifiedAndServedWithinTheReportPeriod().getIndicatorId();
        String newlyTestedPosOnARTId=ind.getIndicatorForNumberOfOvcNewlyTestedPositiveAndLinkedToTreatment().getIndicatorId();
        String treatmentAdherenceId=ind.getIndicatorForNumberOfOvcSelfReportingAdherenceToTreatment().getIndicatorId();
        //String ovcAtRiskId=ind.getIndicatorForNumberOfOvcAssessedForHivRiskAndDeterminedToBeAtRisk().getIndicatorId();
        String ovcUnknownRiskAssessedId=ind.getNoOfHivUnknownOvcAssessedForHIVRiskAndServedWithinReportPeriod().getIndicatorId();
        String ovcNegRiskAssessedId=ind.getNoOfHivNegativeOvcAssessedForHIVRiskAndServedWithinReportPeriod().getIndicatorId();
        
        String birthCertId=ind.getNoOfOvcServedWithinTheReportPeriodThatHasBirthCert().getIndicatorId();
        String postViolenceServiceId=ind.getIndicatorForNumberOfOvcLinkedToGovtForPostViolenceServicesWithinReportPeriod().getIndicatorId();
        String referredAndTestedAndResultId=ind.getIndicatorForNumberOfOvcTestedAndReceivedResult().getIndicatorId();
        String referredForTestingOnlyId=ind.getIndicatorForNumberOfOvcTestedForHIV().getIndicatorId();
        
        String severelyMalnourishedId=ind.getIndicatorForNumberOfSeverelyMalnourishedOvcCurrently().getIndicatorId();
        String severelyMalnourishedAndServedId=ind.getIndicatorForNumberOfSeverelyMalnourishedOvcServedNutritonalServices().getIndicatorId();
        String ovcSchoolAttendanceId=ind.getIndicatorForNumberOfOvcRegularlyAttendingSchool().getIndicatorId();
        String ovcInSchoolId=ind.getIndicatorForNumberOfOvcCurrentlyInSchool().getIndicatorId();
        
        String hhOvcServedId=ind.getIndicatorNumberOfHouseholdsWhoseOvcWereServedWithinReportingPeriod().getIndicatorId();
        String hhgradOvcServedId=ind.getIndicatorNumberOfGraduatedHouseholdsWhoseOvcWereServedWithinReportingPeriod().getIndicatorId();
        String ovcEconsId=ind.getIndicatorNumberOfHouseholdsThatCanSolveEmergencyNeedsWithinReportingPeriod().getIndicatorId();
        String ovcHivPrevId=ind.getIndicatorForNumberOfAdolescentsProvidedHIVPreventionServices().getIndicatorId();
        
        //String[] selectedIndicators={currentlyEnrolledAndServedId,servedAndGraduated,servedAndTransfered,exitedWithoutGraduationId,newlyEnrolledAndServedId,ovc_hivstatNegativeId,ovc_hivstatPositiveId,ovc_hivstatUnknownId,ovcNegRiskAssessedId,ovcUnknownRiskAssessedId,referredAndTestedAndResultId,referredForTestingOnlyId,posIdentifiedAndServedId,enrolledOvcOnARTServedId,enrolledOnARTId,birthCertId,currentlyEnrolledAndServedId,hhgradOvcServedId,hhOvcServedId,ovcHivPrevId};
        
        String[] selectedIndicators={currentlyEnrolledAndServedId,servedAndGraduated,servedAndTransfered,exitedWithoutGraduationId,newlyEnrolledAndServedId,ovc_hivstatNegativeId,ovc_hivstatPositiveId,ovc_hivstatUnknownId,ovcNegRiskAssessedId,ovcUnknownRiskAssessedId,referredAndTestedAndResultId,referredForTestingOnlyId,posIdentifiedAndServedId,newlyTestedPosOnARTId,treatmentAdherenceId,enrolledOnARTId,severelyMalnourishedAndServedId,severelyMalnourishedId,postViolenceServiceId,birthCertId,ovcSchoolAttendanceId,ovcInSchoolId,ovcEconsId,hhgradOvcServedId,hhOvcServedId,ovcHivPrevId};
        //String[] selectedIndicators={hhgradOvcServedId,hhOvcServedId};
        mainList=getValuesForAllIndicator(selectedIndicators,reportParams);
               
        return mainList;
    }
    public List getCaregiverDataPerIndicator(String indicatorId,String[] reportParams)
    {
        SummaryStatisticsReportGenerator ssrg=new SummaryStatisticsReportGenerator();
        List mainList=new ArrayList();
        IndicatorDictionary ind=new IndicatorDictionary();
        String currentlyEnrolledAndServedId=ind.getIndicatorForNumberOfOvcCurrentlyEnrolledAndServedInReportPeriod().getIndicatorId();
        String servedAndGraduated=ind.getIndicatorForNumberOfOvcGraduatedButServedInReportPeriod().getIndicatorId();
        String servedAndTransfered=ind.getIndicatorForNumberOfOvcTransferedOutButServedInReportPeriod().getIndicatorId();
        String exitedWithoutGraduationId=ind.getIndicatorForNumberOfOvcExitedWithoutGraduation().getIndicatorId();
        try
        {
            if(indicatorId.equalsIgnoreCase(currentlyEnrolledAndServedId))
            {
                String[] selectedIndicatorArray={ind.getIndicatorForNumberOfActiveCaregiversServedWithinTheReportPeriod().getIndicatorId()};
                mainList=ssrg.getOvcEnrolledSummStatistics("",reportParams,selectedIndicatorArray);
            }
            else if(indicatorId.equalsIgnoreCase(servedAndGraduated))
            {
                String[] selectedIndicatorArray={ind.getIndicatorForNumberOfCaregiversServedAndGraduatedWithinTheReportPeriod().getIndicatorId()};
                mainList=ssrg.getOvcEnrolledSummStatistics("",reportParams,selectedIndicatorArray);
            }
            else if(indicatorId.equalsIgnoreCase(servedAndTransfered))
            {
                String[] selectedIndicatorArray={ind.getIndicatorForNumberOfCaregiversServedAndTransferedWithinTheReportPeriod().getIndicatorId()};
                mainList=ssrg.getOvcEnrolledSummStatistics("",reportParams,selectedIndicatorArray);
            }
            else if(indicatorId.equalsIgnoreCase(exitedWithoutGraduationId))
            {
                String[] selectedIndicatorArray={ind.getIndicatorForNumberOfCaregiversExitedWithoutGraduation().getIndicatorId()};
                mainList=ssrg.getOvcEnrolledSummStatistics("",reportParams,selectedIndicatorArray);
            }
            else
            {
                mainList.add(indicatorId);
                mainList.add(0);
                mainList.add(0);
                mainList.add(0);
                mainList.add(indicatorId);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return mainList;
    }
    public List getValuesForAllIndicator(String[] selectedIndicators,String[] reportParams)
    {
        List mainList=new ArrayList();
        String indicatorCode=null;
        List subList=null;
        for(int i=0; i<selectedIndicators.length; i++)
        {
            indicatorCode=selectedIndicators[i];
            subList=getValuesPerIndicator(indicatorCode,reportParams);
            if(subList !=null)
            CustomIndicatorsReportManager.processCustomIndicatorsReport(subList);
            //mainList.addAll(subList);
        }
        return mainList;
    }
    public List getValuesPerIndicator(String indicatorCode,String[] reportParams)
    {
        DaoUtil util=new DaoUtil();
        DateManager dm=new DateManager();
        List mainList=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        
        String stateName=util.getStateName(reportParams[0]);
        String lgaName=util.getLgaName(reportParams[1]);
        String cboName=" ";
        String period=dm.getMonthAsString(Integer.parseInt(reportParams[7]))+" "+reportParams[8]+"-"+dm.getMonthAsString(Integer.parseInt(reportParams[9]))+" "+reportParams[10];
        System.err.println("State: "+stateName+" Lga: "+lgaName);
        int[] ageDisaggregation={0,0,1,4,5,9,10,14,15,17};
        int[] ovc_servAgeDisaggregation={0,0,1,4,5,9,10,14,15,17,18,24,25,200};
        SummaryStatisticsReportGenerator ssrg=new SummaryStatisticsReportGenerator();
        try
        {
            IndicatorWarehouse indw=new IndicatorWarehouse();
            
            IndicatorDictionary indd=new IndicatorDictionary();
            //String hhgradOvcServedId=indd.getIndicatorNumberOfGraduatedHouseholdsWhoseOvcWereServedWithinReportingPeriod().getIndicatorId();
            //String ovcEconsId=indd.getIndicatorNumberOfHouseholdsThatCanSolveEmergencyNeedsWithinReportingPeriod().getIndicatorId();
            List cboList=util.getHouseholdEnrollmentDaoInstance().getDistinctCBOCodesByLga(reportParams[1]);
            if(cboList !=null)
            {
                for(int i=0; i<cboList.size(); i++)
                {
                    if(cboList.get(i) !=null)
                    {
                        OrganizationRecords or=util.getOrganizationRecords(cboList.get(i).toString());
                        if(or !=null)
                        {
                            cboName=or.getOrgName();
                            break;
                        }
                    }
                }
            }
            //for(int i=0; i<selectedIndicators.length; i++)
            //{
                maleList=new ArrayList();
                femaleList=new ArrayList();
                List valueList=null;
                Indicators ind=indw.getIndicatorById(indicatorCode);
                //if(selectedIndicators[i].equalsIgnoreCase(hhgradOvcServedId) || selectedIndicators[i].equalsIgnoreCase(ovcEconsId))
                //Check for household indicators, they only have one value as they do not return values based sex, but total
                if(ind !=null && ind.getIndicatorType().equalsIgnoreCase(NomisConstant.HOUSEHOLD_TYPE))
                {
                    List householdDataList=getHouseholdValuesPerIndicator(indicatorCode,reportParams);
                    if(householdDataList !=null)
                    {
                        maleList.add(householdDataList.get(0));
                        femaleList.add(householdDataList.get(1));
                    }
                }
                else
                {
                    if(ind.getMerCode() !=null && ind.getMerCode().equalsIgnoreCase("OVC_SERV"))
                    ageDisaggregation=ovc_servAgeDisaggregation;  
                    for(int j=0; j<ageDisaggregation.length; j+=2)
                    {
                        reportParams[15]=ageDisaggregation[j]+"";
                        reportParams[16]=ageDisaggregation[j+1]+"";
                        String[] selectedIndicatorArray={indicatorCode};
                        List list=null;
                        //list=ssrg.getOvcEnrolledSummStatistics("",reportParams,selectedIndicatorArray);
                            //valueList=(List)list.get(0);
                        if(ageDisaggregation[j]>24)
                        {
                            List cglist=getCaregiverDataPerIndicator(indicatorCode,reportParams);
                            if(cglist.size()==1)
                            valueList=(List)cglist.get(0);
                        }
                        else
                        {
                            list=ssrg.getOvcEnrolledSummStatistics("",reportParams,selectedIndicatorArray);
                            valueList=(List)list.get(0);  
                        }
                        if(valueList==null || valueList.size()<2)
                        {
                            maleList.add(0);
                            femaleList.add(0);
                        }
                        else
                        {
                            maleList.add(valueList.get(1));
                            if(valueList.size()>2)
                            femaleList.add(valueList.get(2));
                            else
                            femaleList.add(0);
                        }
                    }
                }
                mainList.add(getReportTemplate(stateName,lgaName,cboName,indicatorCode,maleList,femaleList,period));
            //}
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return mainList;
    }
    /*
     * public List getValuesPerIndicator(String[] selectedIndicators,String[] reportParams)
    {
        DaoUtil util=new DaoUtil();
        DateManager dm=new DateManager();
        List mainList=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        
        String stateName=util.getStateName(reportParams[0]);
        String lgaName=util.getLgaName(reportParams[1]);
        String cboName=" ";
        String period=dm.getMonthAsString(Integer.parseInt(reportParams[7]))+" "+reportParams[8]+"-"+dm.getMonthAsString(Integer.parseInt(reportParams[9]))+" "+reportParams[10];
        System.err.println("State: "+stateName+" Lga: "+lgaName);
        int[] ageDisaggregation={0,0,1,4,5,9,10,14,15,17};
        int[] ovc_servAgeDisaggregation={0,0,1,4,5,9,10,14,15,17,18,24,25,200};
        SummaryStatisticsReportGenerator ssrg=new SummaryStatisticsReportGenerator();
        try
        {
            IndicatorWarehouse indw=new IndicatorWarehouse();
            
            IndicatorDictionary indd=new IndicatorDictionary();
            //String hhgradOvcServedId=indd.getIndicatorNumberOfGraduatedHouseholdsWhoseOvcWereServedWithinReportingPeriod().getIndicatorId();
            //String ovcEconsId=indd.getIndicatorNumberOfHouseholdsThatCanSolveEmergencyNeedsWithinReportingPeriod().getIndicatorId();
            List cboList=util.getHouseholdEnrollmentDaoInstance().getDistinctCBOCodesByLga(reportParams[1]);
            if(cboList !=null)
            {
                for(int i=0; i<cboList.size(); i++)
                {
                    if(cboList.get(i) !=null)
                    {
                        OrganizationRecords or=util.getOrganizationRecords(cboList.get(i).toString());
                        if(or !=null)
                        {
                            cboName=or.getOrgName();
                            break;
                        }
                    }
                }
            }
            for(int i=0; i<selectedIndicators.length; i++)
            {
                maleList=new ArrayList();
                femaleList=new ArrayList();
                List valueList=null;
                Indicators ind=indw.getIndicatorById(selectedIndicators[i]);
                //if(selectedIndicators[i].equalsIgnoreCase(hhgradOvcServedId) || selectedIndicators[i].equalsIgnoreCase(ovcEconsId))
                //Check for household indicators, they only have one value as they do not return values based sex, but total
                if(ind !=null && ind.getIndicatorType().equalsIgnoreCase(NomisConstant.HOUSEHOLD_TYPE))
                {
                    List householdDataList=getHouseholdValuesPerIndicator(selectedIndicators[i],reportParams);
                    if(householdDataList !=null)
                    {
                        maleList.add(householdDataList.get(0));
                        femaleList.add(householdDataList.get(1));
                    }
                }
                else
                {
                    if(ind.getMerCode() !=null && ind.getMerCode().equalsIgnoreCase("OVC_SERV"))
                    ageDisaggregation=ovc_servAgeDisaggregation;  
                    for(int j=0; j<ageDisaggregation.length; j+=2)
                    {
                        reportParams[15]=ageDisaggregation[j]+"";
                        reportParams[16]=ageDisaggregation[j+1]+"";
                        String[] selectedIndicatorArray={selectedIndicators[i]};
                        List list=null;
                        //list=ssrg.getOvcEnrolledSummStatistics("",reportParams,selectedIndicatorArray);
                            //valueList=(List)list.get(0);
                        if(ageDisaggregation[j]>24)
                        {
                            List cglist=getCaregiverDataPerIndicator(selectedIndicators[i],reportParams);
                            if(cglist.size()==1)
                            valueList=(List)cglist.get(0);
                        }
                        else
                        {
                            list=ssrg.getOvcEnrolledSummStatistics("",reportParams,selectedIndicatorArray);
                            valueList=(List)list.get(0);  
                        }
                        if(valueList==null || valueList.size()<2)
                        {
                            maleList.add(0);
                            femaleList.add(0);
                        }
                        else
                        {
                            maleList.add(valueList.get(1));
                            if(valueList.size()>2)
                            femaleList.add(valueList.get(2));
                            else
                            femaleList.add(0);
                        }
                        
                        //System.err.println("Result is: "+valueList.get(0)+" "+valueList.get(1)+" "+valueList.get(2)+" "+valueList.get(3)+" mainList value: "+mainList.get(i).toString());
                        //mainList.add(maleList);
                        //mainList.add(femaleList);
                    }
                }
                mainList.add(getReportTemplate(stateName,lgaName,cboName,selectedIndicators[i],maleList,femaleList,period));
            }
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return mainList;
    }
     */
    public List getHouseholdValuesPerIndicator(String indicatorId,String[] reportParams)
    {
        List mainList=new ArrayList();
        SummaryStatisticsReportGenerator ssrg=new SummaryStatisticsReportGenerator();
        try
        {
            List valueList=null;
            reportParams[15]=0+"";
            reportParams[16]=200+"";
            String[] selectedIndicatorArray={indicatorId};
            List list=ssrg.getOvcEnrolledSummStatistics("",reportParams,selectedIndicatorArray);
            System.err.println();
            if(list !=null)
            {
                System.err.println("list.size() is "+list.size());
                valueList=(List)list.get(0);  
                if(valueList !=null && valueList.size()>1)
                {
                    System.err.println("valueList.get(0)) is "+valueList.get(0).toString()+" valueList.get(1)) is "+valueList.get(1).toString());
                    mainList.add(valueList.get(1));
                    mainList.add(0);
                }
            }  
         
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return mainList;
    }
    public ReportTemplate getReportTemplate(String stateCode,String lgaCode,String cboName,String indicatorId,List maleList,List femaleList,String period)
    {
        IndicatorWarehouse indw=new IndicatorWarehouse();
        Indicators ind=indw.getIndicatorById(indicatorId);
        
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
        
        if(ind !=null)
        {
            rt.setIndicatorName(ind.getAlternateName());
            rt.setMerCode(ind.getMerCode());
            rt.setOtherDisaggregation("other");
            rt.setUser("smomoh");
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
}
