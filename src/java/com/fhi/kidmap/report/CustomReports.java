/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report;

import com.fhi.nomis.nomisutils.ReportPeriodManager;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.DatimReportDao;
import com.fhi.kidmap.dao.DatimReportDaoImpl;
import com.fhi.nomis.OperationsManagement.ReportPeriod;
import com.fhi.nomis.nomisutils.DateManager;
import com.nomis.business.DatimReportTemplate_Mer1718;
import com.nomis.business.ReportHeaderTemplate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Siaka
 */
public class CustomReports implements Serializable
{
    int count=0;
    public List getMERReport(List paramList)
    {
        DaoUtil util=new DaoUtil();
        List mainList=new ArrayList();
        List list=new ArrayList();
        List list2=new ArrayList();
        List list3=new ArrayList();
        List list4=new ArrayList();
        List list5=new ArrayList();
        List beneficiaryList=new ArrayList();
        ReportUtility rutil=new ReportUtility();
        String[] queryParam=rutil.getQueryParam(paramList);
        int[] ageSegregation={0,17,18,24,25,200};
         
        String additionalQueryCriteria=util.getHVIOrgUnitQuery(queryParam);
        String[] dateParams={queryParam[4],queryParam[5],queryParam[6],queryParam[7]};
        String[] oneMonthDateParams={queryParam[6],queryParam[7],queryParam[6],queryParam[7]};
        
        String[] dateArray={util.getStartDate(dateParams),util.getEndDate(dateParams)};
        String[] oneMonthDateArray={util.getStartDate(oneMonthDateParams),util.getEndDate(oneMonthDateParams)};
        
        MonthlySummaryFormReport msr=new MonthlySummaryFormReport();
        msr.setAgeSegregation(msr.getNewAgeSegregation());
        msr.setHouseholdAgeSegregation(ageSegregation);
        try
        {
            List caregiverServedList=msr.getNoOfCaregiversAndHouseholdHeadsProvidedWithAtleastOneService("Number of caregivers served", additionalQueryCriteria, dateArray);
            List ovcServedList=msr.getNoOfOvcServedForMSF("Number of OVC served",additionalQueryCriteria,dateArray,"new");

            list.add(msr.getNoOfOvcNewlyEnrolled("Number of OVC newly enrolled",additionalQueryCriteria,oneMonthDateArray));
            list.add(msr.getNoOfOvcAccessingNutritional("Number of eligible adults and children who received food and/or other nutrition services", additionalQueryCriteria, dateArray));
            list.add(msr.getNoOfHIVPositivOvcProvidedClinicalServicees("Number of HIV positive OVC that received clinical services (including those on ART)", additionalQueryCriteria, dateArray));//
            //list.add(ovcServedList);
            list.addAll(msr.getOvcServedByNoOfServices("Number of OVC receiving primary direct support ( >=3 services)", "Number of OVC receiving supplemental direct support (< 3 services)", additionalQueryCriteria, dateParams));
            list2.add(msr.getNoOfActiveBeneficiariesServedForDatim2017("Number of active beneficiaries served by PEPFAR OVC programs for children and families affected by HIV/AIDS", ovcServedList, caregiverServedList));
            
            list3.add(msr.getNoOfOvcAccessingEducational("Number of OVC receiving educational and vocational support", additionalQueryCriteria, dateArray));
            list3.add(msr.getNoOfOvcAccessingShelter("Number of OVC provided with basic material needs of shelter/care", additionalQueryCriteria, dateArray));
            list3.add(msr.getNoOfOvcAccessingNutritional("Number of OVC provided with nutritional support", additionalQueryCriteria, dateArray));
            list3.add(msr.getNoOfOvcAccessingPsychoSupport("Number of OVC provided with psychosocial support", additionalQueryCriteria, dateArray));
            list3.add(msr.getNoOfOvcAccessingProtection("Number of OVC provided with legal assisstance and protective care services", additionalQueryCriteria, dateArray));
            list3.add(msr.getNoOfOvcAccessingEconStrengthening("Number of OVC provided with economic strengthening services", additionalQueryCriteria, dateArray,"new"));
            //list4.add(msr.getNoOfOvcSupportedOnHIVAIDSServicesForMSF("Number of active beneficiaries accompanied otherwise supported for transport to HIV testing, care and/or treatment services at least once every three months (subset of OVC served)",additionalQueryCriteria,dateArray));
            beneficiaryList.add(msr.getNoOfActiveBeneficiariesSupportedToAccessHIVCare("Number of active beneficiaries accompanied otherwise supported for transport to HIV testing, care and/or treatment services at least once every three months (subset of OVC served)",additionalQueryCriteria,dateArray));
            list4.add(msr.getNoOfOvcOutOfProgram("Number of OVC who graduated", additionalQueryCriteria, additionalQueryCriteria, "Age > 18"));
            list4.add(msr.getNoOfCaregiversTrained("Number of care givers trained to improve their ability in caring for OVC", additionalQueryCriteria, dateArray));
            //list4.add(ovcServedList);
            //list5.add(caregiverServedList);
            //withdrawal.reasonWithdrawal like '%Age > 18%'
            /*add 18-24 and 25+ values from list 5 (house hold served) to list3*/
            

            mainList.add(list);
            mainList.add(list2);
            mainList.add(list3);
            mainList.add(list4);
            mainList.add(list5);
            mainList.add(beneficiaryList);
            //getOvcServedDisaggregatedByActiveAndNotActive(queryParam);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return mainList;
    }/*public List getOvcServedDisaggregatedByActiveAndNotActive(List paramList)
    {
        ReportUtility rutil=new ReportUtility();
        String[] params=rutil.getQueryParam(paramList);
        String[] reportParams={params[0],params[1],params[2],params[3],"All","All","All",params[4],params[5],params[6],params[7],"All","All","All","All","All","All","All",params[3],params[3]};
        IndicatorDictionary ind=new IndicatorDictionary();
        String sex="Male";
        String currentlyEnrolledAndServedId=ind.getIndicatorForNumberOfOvcCurrentlyEnrolledAndServedInReportPeriod().getIndicatorId();
        String servedAndGraduated=ind.getIndicatorForNumberOfOvcGraduatedButServedInReportPeriod().getIndicatorId();
        String servedAndTransfered=ind.getIndicatorForNumberOfOvcTransferedOutButServedInReportPeriod().getIndicatorId();
        
        String[] selectedIndicators={currentlyEnrolledAndServedId,servedAndGraduated,servedAndTransfered};
        List mainList=new ArrayList();
        SummaryStatisticsReportGenerator ssrg=new SummaryStatisticsReportGenerator();
        try
        {
            for(int i=0; i<selectedIndicators.length; i++)
            {
                String[] selectedIndicatorArray={selectedIndicators[i]};
                List list=ssrg.getOvcEnrolledSummStatistics(sex,reportParams,selectedIndicatorArray);
                List valueList=(List)list.get(0);  
                mainList.add(valueList.get(3));
                //System.err.println("Result is: "+valueList.get(0)+" "+valueList.get(1)+" "+valueList.get(2)+" "+valueList.get(3));
            }
            mainList.add(getNoOfOVCDueToOtherLosses(reportParams));
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return mainList;
    }*/
    public List getOvcServedDisaggregatedByActiveAndNotActive(List paramList)
    {
        ReportUtility rutil=new ReportUtility();
        String[] params=rutil.getQueryParam(paramList);
        //System.err.println("params[6] is "+params[6]+" and params[7] is "+params[7]);
        ReportPeriod fy=ReportPeriodManager.getStartOfFinancialYear(Integer.parseInt(params[6]), Integer.parseInt(params[7]));
        //String[] reportParams={params[0],params[1],params[2],params[3],"All","All","All",params[4],params[5],params[6],params[7],"All","All","All","All","All","All","All",params[3],params[3]};
        String[] reportParamsForActive={params[0],params[1],params[2],params[3],"All","All","All",params[4],params[5],params[6],params[7],"All","All","All","All","0","17","All",params[3],params[3]};
        String[] reportParamsForExited={params[0],params[1],params[2],params[3],"All","All","All",fy.getStartMonth()+"",fy.getStartYear()+"",params[6],params[7],"All","All","All","All","0","17","All",params[3],params[3]};
        IndicatorDictionary ind=new IndicatorDictionary();
        String sex="Male";
        String currentlyEnrolledAndServedId=ind.getIndicatorForNumberOfOvcCurrentlyEnrolledAndServedInReportPeriod().getIndicatorId();
        String servedAndGraduated=ind.getIndicatorForNumberOfOvcGraduatedButServedInReportPeriod().getIndicatorId();
        String servedAndTransfered=ind.getIndicatorForNumberOfOvcTransferedOutButServedInReportPeriod().getIndicatorId();
        
        String[] selectedIndicators={currentlyEnrolledAndServedId,servedAndGraduated,servedAndTransfered};
        List mainList=new ArrayList();
        SummaryStatisticsReportGenerator ssrg=new SummaryStatisticsReportGenerator();
        try
        {
            List list=null;
            for(int i=0; i<selectedIndicators.length; i++)
            {
                String[] selectedIndicatorArray={selectedIndicators[i]};
                if(selectedIndicators[i].equalsIgnoreCase(currentlyEnrolledAndServedId))
                list=ssrg.getOvcEnrolledSummStatistics(sex,reportParamsForActive,selectedIndicatorArray);
                else
                list=ssrg.getOvcEnrolledSummStatistics(sex,reportParamsForExited,selectedIndicatorArray);
                
                List valueList=(List)list.get(0);  
                mainList.add(valueList.get(3));
                //System.err.println("Result is: "+valueList.get(0)+" "+valueList.get(1)+" "+valueList.get(2)+" "+valueList.get(3));
            }
            mainList.add(getNoOfOVCDueToOtherLosses(reportParamsForExited));
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return mainList;
    }
    public int getNoOfOVCDueToOtherLosses(String[] reportParams)
    {
        int losses=0;
        
        IndicatorDictionary ind=new IndicatorDictionary();
        String sex="Male";
        String servedAndLostToFollowup=ind.getIndicatorForNumberOfOvcLostToFollowupButServedInReportPeriod().getIndicatorId();
        //String voluntaryWithdrawalId=ind.getIndicatorForNumberOfOvcVoluntarilyWithdrawnButServedInReportPeriod().getIndicatorId();
        String servedAndMigratedId=ind.getIndicatorForNumberOfOvcMigratedButServedInReportPeriod().getIndicatorId();
        String servedAndAgedOutId=ind.getIndicatorForNumberOfOvcAgedoutButServedInReportPeriod().getIndicatorId();
        String servedAndDiedId=ind.getIndicatorForNumberOfOvcDiedButServedInReportPeriod().getIndicatorId();
        String servedButInactiveId=ind.getIndicatorForNumberOfOvcInactiveButServedInReportPeriod().getIndicatorId();
        //
        
        String[] selectedIndicators={servedAndLostToFollowup,servedAndMigratedId,servedAndAgedOutId,servedAndDiedId,servedButInactiveId};
        //List mainList=new ArrayList();
        SummaryStatisticsReportGenerator ssrg=new SummaryStatisticsReportGenerator();
        try
        {
            for(int i=0; i<selectedIndicators.length; i++)
            {
                String[] selectedIndicatorArray={selectedIndicators[i]};
                List list=ssrg.getOvcEnrolledSummStatistics(sex,reportParams,selectedIndicatorArray);
                List valueList=(List)list.get(0);  
                int value=Integer.parseInt(valueList.get(3).toString());
                losses+=value;
                //mainList.add(valueList.get(3));
                //System.err.println("Result is: "+valueList.get(0)+" "+valueList.get(1)+" "+valueList.get(2)+" "+valueList.get(3));
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        
        return losses;
    }
    public int getNoOfCaregiversServedAndExitedWithoutGraduation(String[] reportParams)
    {
        int losses=0;
        IndicatorDictionary ind=new IndicatorDictionary();
        String servedAndLostToFollowup=ind.getIndicatorForNumberOfCaregiversServedAndLostToFollowupWithinTheReportPeriod().getIndicatorId();
        String servedAndMigratedId=ind.getIndicatorForNumberOfCaregiversServedAndMigratedWithinTheReportPeriod().getIndicatorId();
        String servedAndDiedId=ind.getIndicatorForNumberOfCaregiversServedAndDiedWithinTheReportPeriod().getIndicatorId();
        String servedButInactiveId=ind.getIndicatorForNumberOfInactiveCaregiversServedInReportPeriod().getIndicatorId();
        String[] selectedIndicators={servedAndLostToFollowup,servedAndMigratedId,servedAndDiedId,servedButInactiveId};
        losses=getIndicatorValueFromSummaryStatGenerator(reportParams,selectedIndicators,3);
        
        return losses;
    }
    private int getIndicatorValueFromSummaryStatGenerator(String[] reportParams,String[] selectedIndicators,int valueIndex)
    {
        int totalValue=0;
        String sex="Male";
        SummaryStatisticsReportGenerator ssrg=new SummaryStatisticsReportGenerator();
        try
        {
            for(int i=0; i<selectedIndicators.length; i++)
            {
                String[] selectedIndicatorArray={selectedIndicators[i]};
                List list=ssrg.getOvcEnrolledSummStatistics(sex,reportParams,selectedIndicatorArray);
                List valueList=(List)list.get(0);  
                int value=Integer.parseInt(valueList.get(valueIndex).toString());
                totalValue+=value;
                //mainList.add(valueList.get(3));
                System.err.println("Result is: "+valueList.get(0)+" "+valueList.get(1)+" "+valueList.get(2)+" "+valueList.get(3));
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return totalValue;
    }
    public int getTotalNoOfMaleCaregiversActiveServedWithinTheReportPeriod(List paramList,int startAge,int endAge)
    {
        int totalValue=0;
        ReportUtility rutil=new ReportUtility();
        String[] params=rutil.getQueryParam(paramList);
        String[] reportParamsForActive={params[0],params[1],params[2],params[3],"All","All","All",params[4],params[5],params[6],params[7],"All","All","All","All",startAge+"",endAge+"","All",params[3],params[3]};
        IndicatorDictionary ind=new IndicatorDictionary();
        String caregiversServedAndActive=ind.getIndicatorForNumberOfActiveCaregiversServedWithinTheReportPeriod().getIndicatorId();
        String[] activeIndicatorId={caregiversServedAndActive};
        totalValue=getIndicatorValueFromSummaryStatGenerator(reportParamsForActive,activeIndicatorId,1);
        System.err.println("totalValue in getTotalNoOfMaleCaregiversActiveServedWithinTheReportPeriod "+startAge+" "+endAge+""+" is "+totalValue);
        return totalValue;
    }
    public int getTotalNoOfMaleCaregiversGraduatedAndServedWithinTheReportPeriod(List paramList,int startAge,int endAge)
    {
        int totalValue=0;
        ReportUtility rutil=new ReportUtility();
        String[] params=rutil.getQueryParam(paramList);
        ReportPeriod fy=ReportPeriodManager.getStartOfFinancialYear(Integer.parseInt(params[6]), Integer.parseInt(params[7]));
        String[] reportParamsForExited={params[0],params[1],params[2],params[3],"All","All","All",fy.getStartMonth()+"",fy.getStartYear()+"",params[6],params[7],"All","All","All","All",startAge+"",endAge+"","All",params[3],params[3]};
        IndicatorDictionary ind=new IndicatorDictionary();
        String caregiversServedAndGraduated=ind.getIndicatorForNumberOfCaregiversServedAndGraduatedWithinTheReportPeriod().getIndicatorId();
        
        String[] graduatedIndicatorId={caregiversServedAndGraduated};
        totalValue=getIndicatorValueFromSummaryStatGenerator(reportParamsForExited,graduatedIndicatorId,1);
        
        System.err.println("totalValue in getTotalNoOfMaleCaregiversGraduatedAndServedWithinTheReportPeriod "+startAge+" "+endAge+""+" is "+totalValue);
        return totalValue;
    }
    public int getTotalNoOfFemaleCaregiversActiveAndServedWithinTheReportPeriod(List paramList,int startAge, int endAge)
    {
        int totalValue=0;
        ReportUtility rutil=new ReportUtility();
        String[] params=rutil.getQueryParam(paramList);
        String[] reportParamsForActive={params[0],params[1],params[2],params[3],"All","All","All",params[4],params[5],params[6],params[7],"All","All","All","All",startAge+"",endAge+"","All",params[3],params[3]};
        IndicatorDictionary ind=new IndicatorDictionary();
        String caregiversServedAndActive=ind.getIndicatorForNumberOfActiveCaregiversServedWithinTheReportPeriod().getIndicatorId();
        String[] activeIndicatorId={caregiversServedAndActive};
        totalValue=getIndicatorValueFromSummaryStatGenerator(reportParamsForActive,activeIndicatorId,2);
        System.err.println("totalValue in getTotalNoOfFemaleCaregiversActiveAndServedWithinTheReportPeriod "+startAge+" "+endAge+""+" is "+totalValue);
        return totalValue;
    }
    public int getTotalNoOfFemaleCaregiversGraduatedAndServedWithinTheReportPeriod(List paramList,int startAge, int endAge)
    {
        int totalValue=0;
        ReportUtility rutil=new ReportUtility();
        String[] params=rutil.getQueryParam(paramList);
        ReportPeriod fy=ReportPeriodManager.getStartOfFinancialYear(Integer.parseInt(params[6]), Integer.parseInt(params[7]));
        String[] reportParamsForExited={params[0],params[1],params[2],params[3],"All","All","All",fy.getStartMonth()+"",fy.getStartYear()+"",params[6],params[7],"All","All","All","All",startAge+"",endAge+"","All",params[3],params[3]};
        IndicatorDictionary ind=new IndicatorDictionary();
        String caregiversServedAndGraduated=ind.getIndicatorForNumberOfCaregiversServedAndGraduatedWithinTheReportPeriod().getIndicatorId();
        String[] graduatedIndicatorId={caregiversServedAndGraduated};
        totalValue=getIndicatorValueFromSummaryStatGenerator(reportParamsForExited,graduatedIndicatorId,2);
        System.err.println("totalValue in getTotalNoOfFemaleCaregiversGraduatedAndServedWithinTheReportPeriod "+startAge+" "+endAge+""+" is "+totalValue);        
        return totalValue;
    }
    /*public int getTotalNoOfMaleCaregiversActiveAndGraduatedServedWithinTheReportPeriod(List paramList,int startAge,int endAge)
    {
        int totalValue=0;
        ReportUtility rutil=new ReportUtility();
        String[] params=rutil.getQueryParam(paramList);
        ReportPeriod fy=ReportPeriodManager.getStartOfFinancialYear(Integer.parseInt(params[6]), Integer.parseInt(params[7]));
        String[] reportParamsForActive={params[0],params[1],params[2],params[3],"All","All","All",params[4],params[5],params[6],params[7],"All","All","All","All",startAge+"",endAge+"","All",params[3],params[3]};
        String[] reportParamsForExited={params[0],params[1],params[2],params[3],"All","All","All",fy.getStartMonth()+"",fy.getStartYear()+"",params[6],params[7],"All","All","All","All",startAge+"",endAge+"","All",params[3],params[3]};
        IndicatorDictionary ind=new IndicatorDictionary();
        String caregiversServedAndActive=ind.getIndicatorForNumberOfActiveCaregiversServedWithinTheReportPeriod().getIndicatorId();
        String caregiversServedAndGraduated=ind.getIndicatorForNumberOfCaregiversServedAndGraduatedWithinTheReportPeriod().getIndicatorId();
        
        String[] activeIndicatorId={caregiversServedAndActive};
        String[] graduatedIndicatorId={caregiversServedAndGraduated};
        totalValue+=getIndicatorValueFromSummaryStatGenerator(reportParamsForActive,activeIndicatorId,1);
        System.err.println("totalValue in getTotalNoOfMaleCaregiversActiveAndGraduatedServedWithinTheReportPeriod is "+totalValue);
        totalValue+=getIndicatorValueFromSummaryStatGenerator(reportParamsForExited,graduatedIndicatorId,1);
        //String[] selectedIndicators={caregiversServedAndActive,caregiversServedAndGraduated};
        //totalValue=getIndicatorValueFromSummaryStatGenerator(reportParams,selectedIndicators,1);
        System.err.println("totalValue in getTotalNoOfMaleCaregiversActiveAndGraduatedServedWithinTheReportPeriod "+startAge+" "+endAge+""+" is "+totalValue);
        return totalValue;
    }
    public int getTotalNoOfFemaleCaregiversActiveAndGraduatedServedWithinTheReportPeriod(List paramList,int startAge, int endAge)
    {
        int totalValue=0;
        ReportUtility rutil=new ReportUtility();
        String[] params=rutil.getQueryParam(paramList);
        ReportPeriod fy=ReportPeriodManager.getStartOfFinancialYear(Integer.parseInt(params[6]), Integer.parseInt(params[7]));
        String[] reportParamsForActive={params[0],params[1],params[2],params[3],"All","All","All",params[4],params[5],params[6],params[7],"All","All","All","All",startAge+"",endAge+"","All",params[3],params[3]};
        String[] reportParamsForExited={params[0],params[1],params[2],params[3],"All","All","All",fy.getStartMonth()+"",fy.getStartYear()+"",params[6],params[7],"All","All","All","All",startAge+"",endAge+"","All",params[3],params[3]};
        IndicatorDictionary ind=new IndicatorDictionary();
        String caregiversServedAndActive=ind.getIndicatorForNumberOfActiveCaregiversServedWithinTheReportPeriod().getIndicatorId();
        String caregiversServedAndGraduated=ind.getIndicatorForNumberOfCaregiversServedAndGraduatedWithinTheReportPeriod().getIndicatorId();
        String[] activeIndicatorId={caregiversServedAndActive};
        String[] graduatedIndicatorId={caregiversServedAndGraduated};
        totalValue+=getIndicatorValueFromSummaryStatGenerator(reportParamsForActive,activeIndicatorId,2);
        //System.err.println("totalValue in getTotalNoOfFemaleCaregiversActiveAndGraduatedServedWithinTheReportPeriod with "+startAge+" "+endAge+""+" is "+totalValue);
        totalValue+=getIndicatorValueFromSummaryStatGenerator(reportParamsForExited,graduatedIndicatorId,2);
        
        //System.err.println("totalValue in getTotalNoOfFemaleCaregiversActiveAndGraduatedServedWithinTheReportPeriod is "+totalValue);
        //totalValue+=getIndicatorValueFromSummaryStatGenerator(reportParams,activeIndicatorId,2);
        //totalValue+=getIndicatorValueFromSummaryStatGenerator(reportParams,graduatedIndicatorId,2);
        return totalValue;
    }*/
    /*public int getTotalNoOfMaleCaregiversServedWithinTheReportPeriod(String[] reportParams)
    {
        int totalValue=0;
        
        IndicatorDictionary ind=new IndicatorDictionary();
        String caregiversServed=ind.getIndicatorForNumberOfCaregiversProvidedAtleastOneService().getIndicatorId();
        String[] selectedIndicators={caregiversServed};
        totalValue=getIndicatorValueFromSummaryStatGenerator(reportParams,selectedIndicators,1);
        return totalValue;
    }*/
    /*public int getTotalNoOfFemaleCaregiversServedWithinTheReportPeriod(String[] reportParams)
    {
        int totalValue=0;
        
        IndicatorDictionary ind=new IndicatorDictionary();
        String caregiversServed=ind.getIndicatorForNumberOfCaregiversProvidedAtleastOneService().getIndicatorId();
        String[] selectedIndicators={caregiversServed};
        totalValue=getIndicatorValueFromSummaryStatGenerator(reportParams,selectedIndicators,2);
        return totalValue;
    }*/
    public int getNoOfCaregiversServedAndActive(String[] reportParams)
    {
        int totalValue=0;
        
        IndicatorDictionary ind=new IndicatorDictionary();
        String sex="Male";
        String caregiversServedAndActive=ind.getIndicatorForNumberOfActiveCaregiversServedWithinTheReportPeriod().getIndicatorId();
        String[] selectedIndicators={caregiversServedAndActive};
        totalValue=getIndicatorValueFromSummaryStatGenerator(reportParams,selectedIndicators,3);
        return totalValue;
    }
    public int getNoOfCaregiversServedAndGraduated(String[] reportParams)
    {
        int totalValue=0;
        IndicatorDictionary ind=new IndicatorDictionary();
        String caregiversServedAndGraduated=ind.getIndicatorForNumberOfCaregiversServedAndGraduatedWithinTheReportPeriod().getIndicatorId();
        String[] selectedIndicators={caregiversServedAndGraduated};
        totalValue=getIndicatorValueFromSummaryStatGenerator(reportParams,selectedIndicators,3);
        return totalValue;
    }
    public int getNoOfCaregiversServedAndTransfered(String[] reportParams)
    {
        int totalValue=0;
        IndicatorDictionary ind=new IndicatorDictionary();
        String caregiversServedAndTransfered=ind.getIndicatorForNumberOfCaregiversServedAndTransferedWithinTheReportPeriod().getIndicatorId();
        String[] selectedIndicators={caregiversServedAndTransfered};
        totalValue=getIndicatorValueFromSummaryStatGenerator(reportParams,selectedIndicators,3);
        return totalValue;
    }
    /*public List getNoOfOvcByHivStatus(List paramList)
    {
        ReportUtility rutil=new ReportUtility();
        String[] params=rutil.getQueryParam(paramList);
        String[] reportParams={params[0],params[1],params[2],params[3],"All","All","All",params[4],params[5],params[6],params[7],"All","All","All","All","0","17","All",params[3],params[3]};
        IndicatorDictionary ind=new IndicatorDictionary();
        String sex="Male";
        String currentlyEnrolledHivPositiveOvcServedId=ind.getIndicatorForNumberOfHivPositiveOvcProvidedWithAtleastOneService().getIndicatorId();
        String enrolledOvcOnARTServedId=ind.getIndicatorForNumberOfHivPositiveOvcEnrolledOnARTProvidedWithAtleastOneService().getIndicatorId();
        //String currentlyEnrolledOvcInCareServedId=ind.getIndicatorForNumberOfHIVPositiveOvcEverEnrolledInCare().getIndicatorId();
        String currentlyEnrolledHivNegativeOvcServedId=ind.getIndicatorForNumberOfHivNegativeOvcProvidedWithAtleastOneService().getIndicatorId();
        String currentlyEnrolledHivUnknownOvcServedId=ind.getIndicatorForNumberOfHivUnknownOvcProvidedWithAtleastOneService().getIndicatorId();
        String currentlyEnrolledHivTestNotIndicatedOvcServedId=ind.getIndicatorForNumberOfHivUnknownOvcWithTestNotIndicatedServed().getIndicatorId();
        
        
        
        String[] selectedIndicators={currentlyEnrolledHivPositiveOvcServedId,enrolledOvcOnARTServedId,currentlyEnrolledHivNegativeOvcServedId,currentlyEnrolledHivUnknownOvcServedId,currentlyEnrolledHivTestNotIndicatedOvcServedId};
        List mainList=new ArrayList();
        SummaryStatisticsReportGenerator ssrg=new SummaryStatisticsReportGenerator();
        try
        {
            for(int i=0; i<selectedIndicators.length; i++)
            {
                String[] selectedIndicatorArray={selectedIndicators[i]};
                List list=ssrg.getOvcEnrolledSummStatistics(sex,reportParams,selectedIndicatorArray);
                List valueList=(List)list.get(0);  
                mainList.add(valueList.get(3));
                System.err.println("Result is: "+valueList.get(0)+" "+valueList.get(1)+" "+valueList.get(2)+" "+valueList.get(3)+" mainList value: "+mainList.get(i).toString());
            }
            mainList.add(getNoOfOVCDueToOtherLosses(reportParams));
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        return mainList;
    }*/
    public List getNoOfActiveAndGraduatedOvcServedByHivStatus(List paramList)
    {
        ReportUtility rutil=new ReportUtility();
        String[] params=rutil.getQueryParam(paramList);
        ReportPeriod fy=ReportPeriodManager.getStartOfFinancialYear(Integer.parseInt(params[6]), Integer.parseInt(params[7]));
        //String[] reportParams={params[0],params[1],params[2],params[3],"All","All","All",params[4],params[5],params[6],params[7],"All","All","All","All","All","All","All",params[3],params[3]};
        String[] reportParamsForActive={params[0],params[1],params[2],params[3],"All","All","All",params[4],params[5],params[6],params[7],"All","All","All","All","0","17","All",params[3],params[3]};
        String[] reportParamsForExited={params[0],params[1],params[2],params[3],"All","All","All",fy.getStartMonth()+"",fy.getStartYear()+"",params[6],params[7],"All","All","All","All","0","17","All",params[3],params[3]};
        //String[] reportParams={params[0],params[1],params[2],params[3],"All","All","All",params[4],params[5],params[6],params[7],"All","All","All","All","0","17","All",params[3],params[3]};
        IndicatorDictionary ind=new IndicatorDictionary();
        String sex="Male";
        String currentlyEnrolledHivPositiveOvcServedId=ind.getIndicatorForNumberOfHivPositiveOvcCurrentlyEnrolledProvidedWithAtleastOneService().getIndicatorId();
        String enrolledOvcOnARTServedId=ind.getIndicatorForNumberOfHivPositiveOvcOnARTCurrentlyEnrolledProvidedWithAtleastOneService().getIndicatorId();
        //String currentlyEnrolledOvcInCareServedId=ind.getIndicatorForNumberOfHIVPositiveOvcEverEnrolledInCare().getIndicatorId();
        String currentlyEnrolledHivNegativeOvcServedId=ind.getIndicatorForNumberOfHivNegativeOvcCurrentlyEnrolledProvidedWithAtleastOneService().getIndicatorId();
        String currentlyEnrolledHivUnknownOvcServedId=ind.getIndicatorForNumberOfHivUnknownOvcCurrentlyEnrolledProvidedWithAtleastOneService().getIndicatorId();
        String currentlyEnrolledHivTestNotIndicatedOvcServedId=ind.getIndicatorForNumberOfHivUnknownOvcTNICurrentlyEnrolledAndServedWithinTheReportPeriod().getIndicatorId();
        
        String graduatedHivTestNotIndicatedOvcServedId=ind.getIndicatorForNumberOfHivUnknownOvcTNIGraduatedAndServedWithinTheReportPeriod().getIndicatorId();
        
        String graduatedHivPositiveOvcServedId=ind.getIndicatorForNumberOfHivPositiveOvcServedButGraduatedWithinTheReportPeriod().getIndicatorId();
        String graduatedHivPositiveOnARTOvcServedId=ind.getIndicatorForNumberOfHivPositiveOvcOnARTServedButGraduatedWithinTheReportPeriod().getIndicatorId();
        String graduatedHivNegativeOvcServedId=ind.getIndicatorForNumberOfHivNegativeOvcServedButGraduatedWithinTheReportPeriod().getIndicatorId();
        String graduatedHivUnknownOvcServedId=ind.getIndicatorForNumberOfHivUnknownOvcServedButGraduatedWithinTheReportPeriod().getIndicatorId();
        
        String[] activeIndicatorIds={currentlyEnrolledHivPositiveOvcServedId,enrolledOvcOnARTServedId,currentlyEnrolledHivNegativeOvcServedId,currentlyEnrolledHivUnknownOvcServedId,currentlyEnrolledHivTestNotIndicatedOvcServedId};
        String[] graduateIndicatorIds={graduatedHivTestNotIndicatedOvcServedId,graduatedHivPositiveOvcServedId,graduatedHivPositiveOnARTOvcServedId,graduatedHivNegativeOvcServedId,graduatedHivUnknownOvcServedId};
        List mainList=new ArrayList();
        SummaryStatisticsReportGenerator ssrg=new SummaryStatisticsReportGenerator();
        try
        {
            for(int i=0; i<activeIndicatorIds.length; i++)
            {
                String[] selectedIndicatorArray={activeIndicatorIds[i]};
                List list=ssrg.getOvcEnrolledSummStatistics(sex,reportParamsForActive,selectedIndicatorArray);
                List valueList=(List)list.get(0);  
                mainList.add(valueList.get(3));
                System.err.println("Result is: "+valueList.get(0)+" "+valueList.get(1)+" "+valueList.get(2)+" "+valueList.get(3)+" mainList value: "+mainList.get(i).toString());
            }
            for(int i=0; i<graduateIndicatorIds.length; i++)
            {
                String[] selectedIndicatorArray={graduateIndicatorIds[i]};
                List list=ssrg.getOvcEnrolledSummStatistics(sex,reportParamsForExited,selectedIndicatorArray);
                List valueList=(List)list.get(0);  
                mainList.add(valueList.get(3));
                System.err.println("Result is: "+valueList.get(0)+" "+valueList.get(1)+" "+valueList.get(2)+" "+valueList.get(3)+" mainList value: "+mainList.get(i).toString());
            }
            mainList.add(getNoOfOVCDueToOtherLosses(reportParamsForExited));
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        return mainList;
    }
    public List getActiveAndGraduatedOvc_Serv(List paramList)
    {
        ReportUtility rutil=new ReportUtility();
        String[] params=rutil.getQueryParam(paramList);
        
        IndicatorDictionary ind=new IndicatorDictionary();
        String sex="Male";
        String currentlyEnrolledAndServedId=ind.getIndicatorForNumberOfOvcCurrentlyEnrolledAndServedInReportPeriod().getIndicatorId();
        String servedAndGraduated=ind.getIndicatorForNumberOfOvcGraduatedButServedInReportPeriod().getIndicatorId();
        //String servedAndTransfered=ind.getIndicatorForNumberOfOvcTransferedOutButServedInReportPeriod().getIndicatorId();
        
        String[] activeIndicatorId={currentlyEnrolledAndServedId};
        String[] graduatedIndicatorId={servedAndGraduated};
        List mainList=new ArrayList();
        SummaryStatisticsReportGenerator ssrg=new SummaryStatisticsReportGenerator();
        try
        {
            int[] ageDisaggregation={0,0,1,4,5,9,10,14,15,17};
            //int[] ageDisaggregation={0,0,1,4,5,9,10,14,15,17,18,24,25,1000};
            List maleList=new ArrayList();
            List femaleList=new ArrayList();
            ReportPeriod fy=ReportPeriodManager.getStartOfFinancialYear(Integer.parseInt(params[6]), Integer.parseInt(params[7]));
        //String[] reportParams={params[0],params[1],params[2],params[3],"All","All","All",params[4],params[5],params[6],params[7],"All","All","All","All","All","All","All",params[3],params[3]};
        //String[] reportParamsForActive={params[0],params[1],params[2],params[3],"All","All","All",params[4],params[5],params[6],params[7],"All","All","All","All","All","All","All",params[3],params[3]};
        
            for(int i=0; i<ageDisaggregation.length; i+=2)
            {
                String[] activeParams={params[0],params[1],params[2],params[3],"All","All","All",params[4],params[5],params[6],params[7],"All","All","All","All",ageDisaggregation[i]+"",ageDisaggregation[i+1]+"","All",params[3],params[3]};
                String[] graduatedParams={params[0],params[1],params[2],params[3],"All","All","All",fy.getStartMonth()+"",fy.getStartYear()+"",params[6],params[7],"All","All","All","All",ageDisaggregation[i]+"",ageDisaggregation[i+1]+"","All",params[3],params[3]};
                List activeMainList=ssrg.getOvcEnrolledSummStatistics(sex,activeParams,activeIndicatorId);
                List graduatedMainList=ssrg.getOvcEnrolledSummStatistics(sex,graduatedParams,graduatedIndicatorId);
                List activeList=(List)activeMainList.get(0);
                List graduatedList=(List)graduatedMainList.get(0);
                int maleActiveServed=Integer.parseInt(activeList.get(1).toString());
                int femaleActiveServed=Integer.parseInt(activeList.get(2).toString());
                int maleGraduatedAndServed=Integer.parseInt(graduatedList.get(1).toString());
                int femaleGraduatedAndServed=Integer.parseInt(graduatedList.get(2).toString());
                int maleOvcServed=maleActiveServed+maleGraduatedAndServed;
                int femaleOvcServed=femaleActiveServed+femaleGraduatedAndServed;
                maleList.add(maleOvcServed);
                femaleList.add(femaleOvcServed);
                
                System.err.println("Result is: maleOvcServed="+maleOvcServed+"; femaleOvcServed="+femaleOvcServed);
            }
            mainList.add(maleList);
            mainList.add(femaleList);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        return mainList;
    }
    public List getDatim2017Report(List paramList)
    {
       List mainList=new ArrayList();
       DaoUtil util=new DaoUtil();
        ReportUtility rutil=new ReportUtility();
        String[] queryParam=rutil.getQueryParam(paramList);
        String[] dateParams={queryParam[4],queryParam[5],queryParam[6],queryParam[7]};
        String[] oneMonthDateParams={queryParam[6],queryParam[7],queryParam[6],queryParam[7]};
        String[] dateArray={util.getStartDate(dateParams),util.getEndDate(dateParams)};
        //String[] oneMonthDateArray={util.getStartDate(oneMonthDateParams),util.getEndDate(oneMonthDateParams)};
        String additionalQueryCriteria=util.getHVIOrgUnitQuery(queryParam);
       //String additionalQueryCriteria=util.getOrgQueryCriteriaForUmbrellaOrganization(queryParam);
       //String currentlyEnrolledHivPositiveOvcId=ind.getIndicatorForNumberOfHIVPositiveOvcCurrentlyEnrolled().getIndicatorId();
       MonthlySummaryFormReport msr=new MonthlySummaryFormReport();
       //SummaryStatisticsReportGenerator ssrg=new SummaryStatisticsReportGenerator();
       try
       {
            msr.setAgeSegregation(msr.getNewFinerAgeSegregation());//
            mainList=msr.getOvc_ServActive("Number of OVC served",additionalQueryCriteria,dateArray,msr.FINER_AGE_DISAGGREGATION);
            
       }
       catch(Exception ex)
       {
           ex.printStackTrace();
       }
       return mainList;
    }
    public DatimReportTemplate getDatim2018Form(List paramList)
    {
        DatimReportTemplate dform=new DatimReportTemplate();
        DaoUtil util=new DaoUtil();
        ReportUtility rutil=new ReportUtility();
        String[] params=rutil.getQueryParam(paramList);
        ReportPeriod fy=ReportPeriodManager.getStartOfFinancialYear(Integer.parseInt(params[6]), Integer.parseInt(params[7]));
        String[] reportParamsForActive={params[0],params[1],params[2],params[3],"All","All","All",params[4],params[5],params[6],params[7],"All","All","All","All","All","All","All",params[3],params[3]};
        String[] reportParamsForExited={params[0],params[1],params[2],params[3],"All","All","All",fy.getStartMonth()+"",fy.getStartYear()+"",params[6],params[7],"All","All","All","All","All","All","All",params[3],params[3]};
        //String[] reportParams={params[0],params[1],params[2],params[3],"All","All","All",params[4],params[5],params[6],params[7],"All","All","All","All","All","All","All",params[3],params[3]};
        ReportParameterTemplate rt=getPopulatedReportParameterTemplate(paramList);
        DatimCaregiverReport dcr=new DatimCaregiverReport();
        
        
        String[] dateParam={paramList.get(4).toString(),paramList.get(5).toString(),paramList.get(6).toString(),paramList.get(7).toString()};
        String period=null;            
        if(util.validateDateParamenters(dateParam))
        period="01 "+util.getMonthAsString(Integer.parseInt(paramList.get(4).toString()))+" "+ paramList.get(5).toString()+" to   end of "+util.getMonthAsString(Integer.parseInt(paramList.get(6).toString()))+" "+paramList.get(7).toString();
        System.err.println("period in datim form is "+period);
        
        
        List datim2017HivList=getNoOfActiveAndGraduatedOvcServedByHivStatus(paramList);
        List mainList=getActiveAndGraduatedOvc_Serv(paramList);
        if(mainList !=null && !mainList.isEmpty())
        {
            List ovc_servActiveNotActiveList=getOvcServedDisaggregatedByActiveAndNotActive(paramList);
            List maleList=(List)mainList.get(0);
            List femaleList=(List)mainList.get(1);
            ReportHeaderTemplate rht=getReportHeaderTemplate(paramList);
            dform.setState(rht.getState());
            dform.setLga(rht.getLga());
            dform.setCbo(rht.getCbo());
            dform.setStartMth(rht.getStartMth());
            dform.setStartYr(rht.getStartYr());
            dform.setEndMth(rht.getEndMth());
            dform.setEndYr(rht.getEndYr());
            dform.setPeriod(period);
            dform.setPartnerCode("All");
            dform.setPartnerName(rht.getPartnerName());
            //OVC_HIVSTAT

            //Retrieve values for both OVC currently enrolled and served and OVC graduated and served by HIV status
            int positiveOvcServed=Integer.parseInt(datim2017HivList.get(0).toString());
            int enrolledOnART=Integer.parseInt(datim2017HivList.get(1).toString());
            int negativeOvcServed=Integer.parseInt(datim2017HivList.get(2).toString());
            int unknownOvcServed=Integer.parseInt(datim2017HivList.get(3).toString());
            int testNotIndicated=Integer.parseInt(datim2017HivList.get(4).toString());
            int graduatedHivTestNotIndicated=Integer.parseInt(datim2017HivList.get(5).toString());
            int positiveOvcGraduatedAndServed=Integer.parseInt(datim2017HivList.get(6).toString());
            int positiveOvcOnARTServedAndGraduated=Integer.parseInt(datim2017HivList.get(7).toString());
            int negativeOvcGraduatedAndServed=Integer.parseInt(datim2017HivList.get(8).toString());
            int unknownOvcGraduatedAndServed=Integer.parseInt(datim2017HivList.get(9).toString());
            
            //Add values for both currently enrolled and graduated OVC served by HIV status
            int totalPositive=positiveOvcServed+positiveOvcGraduatedAndServed;
            int totalEnrolledOnART=enrolledOnART+positiveOvcOnARTServedAndGraduated;
            int notEnrolledOnART=totalPositive-totalEnrolledOnART;
            int totalNegative=negativeOvcServed+negativeOvcGraduatedAndServed;
            int totalUnknown=unknownOvcServed+unknownOvcGraduatedAndServed;
            int totalHivTestNotIndicated=testNotIndicated+graduatedHivTestNotIndicated;
            //System.err.println("unknownOvcServed in getNoOfHivUnknownOvcServed is "+unknownOvcServed+" and unknownOvcGraduatedAndServed is "+unknownOvcGraduatedAndServed);
            
            int hiv_stat_numerator=totalPositive+totalNegative+totalUnknown;
            System.err.println("totalUnknown is "+totalUnknown+" and test not indicated is "+totalHivTestNotIndicated);
            //OVC_SERV
            
            int maleLessThan1=Integer.parseInt(maleList.get(0).toString());
            int femaleLessThan1=Integer.parseInt(femaleList.get(0).toString());
            int male1to4=Integer.parseInt(maleList.get(1).toString());
            int female1to4=Integer.parseInt(femaleList.get(1).toString());
            int male5to9=Integer.parseInt(maleList.get(2).toString());
            int female5to9=Integer.parseInt(femaleList.get(2).toString());
            int ovc_servLessThan1=maleLessThan1+femaleLessThan1;
            int ovc_serv1to9=male1to4+female1to4+male5to9+female5to9;
            int male10to14=Integer.parseInt(maleList.get(3).toString());
            int female10to14=Integer.parseInt(femaleList.get(3).toString());
            int male15to17=Integer.parseInt(maleList.get(4).toString());
            int female15to17=Integer.parseInt(femaleList.get(4).toString());
            //getTotalNoOfMaleCaregiversActiveServedWithinTheReportPeriod
            //dform18to24Active
            DatimReportTemplate dform18to24Active=dcr.getDatimCaregiverActiveAndServedReportFor18To24(rt);
            DatimReportTemplate dform18to24Graduated=dcr.getDatimCaregiverGraduatedAndServedReportFor18To24(rt);
            DatimReportTemplate dform25AndAboveActive=dcr.getDatimCaregiverActiveAndServedReportFor25AndAbove(rt);
            DatimReportTemplate dform25AndAboveGraduated=dcr.getDatimCaregiverGraduatedAndServedReportFor25AndAbove(rt);
        
            int maleCaregiversActiveServed=dform18to24Active.getOvc_servMale18To24()+dform25AndAboveActive.getOvc_servMale25AndAbove();
            int femaleCaregiversActiveServed=dform18to24Active.getOvc_servFemale18To24()+dform25AndAboveActive.getOvc_servFemale25AndAbove();
            int maleCaregiversGraduatedServed=dform18to24Graduated.getOvc_servMale18To24()+dform25AndAboveGraduated.getOvc_servMale25AndAbove();
            int femaleCaregiversGraduatedServed=dform18to24Graduated.getOvc_servFemale18To24()+dform25AndAboveGraduated.getOvc_servFemale25AndAbove();
            
            int male18to24=dform18to24Active.getOvc_servMale18To24()+dform18to24Graduated.getOvc_servMale18To24();
            int female18to24=dform18to24Active.getOvc_servFemale18To24()+dform18to24Graduated.getOvc_servFemale18To24();
            
            int male25AndAbove=dform25AndAboveActive.getOvc_servMale25AndAbove()+dform25AndAboveGraduated.getOvc_servMale25AndAbove();
            int female25AndAbove=dform25AndAboveActive.getOvc_servFemale25AndAbove()+dform25AndAboveGraduated.getOvc_servFemale25AndAbove();
            
            int ovc_servActive=Integer.parseInt(ovc_servActiveNotActiveList.get(0).toString())+maleCaregiversActiveServed+femaleCaregiversActiveServed;
            int ovc_servGraduated=Integer.parseInt(ovc_servActiveNotActiveList.get(1).toString())+maleCaregiversGraduatedServed+femaleCaregiversGraduatedServed;
            int ovc_servTransfered=Integer.parseInt(ovc_servActiveNotActiveList.get(2).toString())+getNoOfCaregiversServedAndTransfered(reportParamsForExited);
            int ovc_servExitedWithoutGraduation=Integer.parseInt(ovc_servActiveNotActiveList.get(3).toString())+getNoOfCaregiversServedAndExitedWithoutGraduation(reportParamsForExited);
            int ovc_serv_numerator=ovc_servActive+ovc_servGraduated;
            /*int ovc_servActive=Integer.parseInt(ovc_servActiveNotActiveList.get(0).toString())+getNoOfCaregiversServedAndActive(reportParamsForActive);
            int ovc_servGraduated=Integer.parseInt(ovc_servActiveNotActiveList.get(1).toString())+getNoOfCaregiversServedAndGraduated(reportParamsForExited);
            int ovc_servTransfered=Integer.parseInt(ovc_servActiveNotActiveList.get(2).toString())+getNoOfCaregiversServedAndTransfered(reportParamsForExited);
            int ovc_servExitedWithoutGraduation=Integer.parseInt(ovc_servActiveNotActiveList.get(3).toString())+getNoOfCaregiversServedAndExitedWithoutGraduation(reportParamsForExited);*/

            //int ovc_serv_numerator=ovc_servActive+ovc_servGraduated+ovc_servTransfered+ovc_servExitedWithoutGraduation;
            
            //int male18to24=getTotalNoOfMaleCaregiversActiveServedWithinTheReportPeriod(paramList,18,24)+getTotalNoOfMaleCaregiversGraduatedAndServedWithinTheReportPeriod(paramList,18,24);
            //int female18to24=getTotalNoOfFemaleCaregiversActiveAndServedWithinTheReportPeriod(paramList,18,24)+getTotalNoOfFemaleCaregiversGraduatedAndServedWithinTheReportPeriod(paramList,18,24);
            //int male25AndAbove=getTotalNoOfMaleCaregiversActiveServedWithinTheReportPeriod(paramList,25,100)+getTotalNoOfMaleCaregiversGraduatedAndServedWithinTheReportPeriod(paramList,25,100);
            //int female25AndAbove=getTotalNoOfFemaleCaregiversActiveAndServedWithinTheReportPeriod(paramList,25,100)+getTotalNoOfFemaleCaregiversGraduatedAndServedWithinTheReportPeriod(paramList,25,100);
            
            
            //int male18to24=Integer.parseInt(maleList.get(5).toString())+Integer.parseInt(maleList.get(6).toString());
            //int female18to24=Integer.parseInt(femaleList.get(5).toString())+Integer.parseInt(femaleList.get(6).toString());
            //get the total number of active and graduated male and female caregivers served within the report period
            
            //System.err.println("male18to24 is "+male18to24);
            //System.err.println("female18to24 is "+female18to24);
            //System.err.println("male25AndAbove is "+male25AndAbove);
            //System.err.println("female25AndAbove is "+female25AndAbove);
            dform.setMainDataElementName("Datim report");
            dform.setEnrolledOnART(totalEnrolledOnART);
            dform.setNotEnrolledOnART(notEnrolledOnART);
            dform.setHiv_statNumerator(hiv_stat_numerator);
            dform.setTotalPositive(totalPositive);
            dform.setTotalNegative(totalNegative);
            dform.setTotalUnknown(totalUnknown);
            dform.setTestNotIndicated(totalHivTestNotIndicated);
            dform.setOtherReasons(totalUnknown-totalHivTestNotIndicated);
            dform.setOvc_servNumerator(ovc_serv_numerator);
            dform.setOvc_servActive(ovc_servActive);
            dform.setOvc_servGraduated(ovc_servGraduated);
            dform.setOvc_servTransfered(ovc_servTransfered);
            dform.setOvc_servExitedWithoutGraduation(ovc_servExitedWithoutGraduation);
            dform.setOvc_servLessThan1(ovc_servLessThan1);
            dform.setOvc_serv1To9(ovc_serv1to9);
            dform.setOvc_servMale10To14(male10to14);
            dform.setOvc_servFemale10To14(female10to14);
            dform.setOvc_servMale15To17(male15to17);
            dform.setOvc_servFemale15To17(female15to17);
            dform.setOvc_servMale18To24(male18to24);
            dform.setOvc_servFemale18To24(female18to24);
            dform.setOvc_servMale25AndAbove(male25AndAbove);
            dform.setOvc_servFemale25AndAbove(female25AndAbove);
        }
        count++;
        System.err.println("Count is "+count);
        return dform;
    }
    /*public DatimReportTemplate_Mer1718 getDatim2017Form(List paramList)
    {
        DatimReportTemplate_Mer1718 dform=new DatimReportTemplate_Mer1718();
        DaoUtil util=new DaoUtil();
        ReportUtility rutil=new ReportUtility();
        String[] params=rutil.getQueryParam(paramList);
        String[] reportParams={params[0],params[1],params[2],params[3],"All","All","All",params[4],params[5],params[6],params[7],"All","All","All","All","All","All","All",params[3],params[3]};
        
        String[] dateParam={paramList.get(4).toString(),paramList.get(5).toString(),paramList.get(6).toString(),paramList.get(7).toString()};
        String period=null;            
        if(util.validateDateParamenters(dateParam))
        period="01 "+util.getMonthAsString(Integer.parseInt(paramList.get(4).toString()))+" "+ paramList.get(5).toString()+" to   end of "+util.getMonthAsString(Integer.parseInt(paramList.get(6).toString()))+" "+paramList.get(7).toString();
        System.err.println("period in datim form is "+period);
        
        
        List datim2017HivList=getNoOfActiveAndGraduatedOvcServedByHivStatus(paramList);
        List mainList=getDatim2017Report(paramList);
        if(mainList !=null && !mainList.isEmpty())
        {
            List ovc_servActiveNotActiveList=getOvcServedDisaggregatedByActiveAndNotActive(paramList);
            List maleList=(List)mainList.get(1);
            List femaleList=(List)mainList.get(2);
            ReportHeaderTemplate rht=getReportHeaderTemplate(paramList);
            dform.setState(rht.getState());
            dform.setLga(rht.getLga());
            dform.setCbo(rht.getCbo());
            dform.setStartMth(rht.getStartMth());
            dform.setStartYr(rht.getStartYr());
            dform.setEndMth(rht.getEndMth());
            dform.setEndYr(rht.getEndYr());
            dform.setPeriod(period);
            dform.setPartnerName(rht.getPartnerName());
            //OVC_HIVSTAT

            int totalPositive=Integer.parseInt(datim2017HivList.get(0).toString());
            int enrolledOnART=Integer.parseInt(datim2017HivList.get(1).toString());
            int notEnrolledOnART=totalPositive-enrolledOnART;
            int totalNegative=Integer.parseInt(datim2017HivList.get(2).toString());
            int totalUnknown=Integer.parseInt(datim2017HivList.get(3).toString());
            int testNotIndicated=Integer.parseInt(datim2017HivList.get(4).toString());
            int hiv_stat_numerator=totalPositive+totalNegative+totalUnknown;
            System.err.println("totalUnknown is "+totalUnknown+" and test not indicated is "+testNotIndicated);
            //OVC_SERV
            int ovc_servActive=Integer.parseInt(ovc_servActiveNotActiveList.get(0).toString())+getNoOfCaregiversServedAndActive(reportParams);
            int ovc_servGraduated=Integer.parseInt(ovc_servActiveNotActiveList.get(1).toString())+getNoOfCaregiversServedAndGraduated(reportParams);
            int ovc_servTransfered=Integer.parseInt(ovc_servActiveNotActiveList.get(2).toString())+getNoOfCaregiversServedAndTransfered(reportParams);
            int ovc_servExitedWithoutGraduation=Integer.parseInt(ovc_servActiveNotActiveList.get(3).toString())+getNoOfCaregiversServedAndExitedWithoutGraduation(reportParams);

            //int ovc_serv_numerator=ovc_servActive+ovc_servGraduated+ovc_servTransfered+ovc_servExitedWithoutGraduation;
            int ovc_serv_numerator=ovc_servActive+ovc_servGraduated;
            int maleLessThan1=Integer.parseInt(maleList.get(0).toString());
            int femaleLessThan1=Integer.parseInt(femaleList.get(0).toString());
            int male1to4=Integer.parseInt(maleList.get(1).toString());
            int female1to4=Integer.parseInt(femaleList.get(1).toString());
            int male5to9=Integer.parseInt(maleList.get(2).toString());
            int female5to9=Integer.parseInt(femaleList.get(2).toString());
            int ovc_servLessThan1=maleLessThan1+femaleLessThan1;
            int ovc_serv1to9=male1to4+female1to4+male5to9+female5to9;
            int male10to14=Integer.parseInt(maleList.get(3).toString());
            int female10to14=Integer.parseInt(femaleList.get(3).toString());
            int male15to17=Integer.parseInt(maleList.get(4).toString());
            int female15to17=Integer.parseInt(femaleList.get(4).toString());
            //int male18to24=getTotalNoOfMaleCaregiversServedWithinTheReportPeriod(reportParams,25,100);
            //int female18to24=getTotalNoOfFemaleCaregiversServedWithinTheReportPeriod(reportParams,25,100);
            int male18to24=Integer.parseInt(maleList.get(5).toString())+Integer.parseInt(maleList.get(6).toString());
            int female18to24=Integer.parseInt(femaleList.get(5).toString())+Integer.parseInt(femaleList.get(6).toString());
            int male25AndAbove=getTotalNoOfMaleCaregiversServedWithinTheReportPeriod(reportParams);
            int female25AndAbove=getTotalNoOfFemaleCaregiversServedWithinTheReportPeriod(reportParams);
            dform.setEnrolledOnART(enrolledOnART);
            dform.setNotEnrolledOnART(notEnrolledOnART);
            dform.setHiv_statNumerator(hiv_stat_numerator);
            dform.setTotalPositive(totalPositive);
            dform.setTotalNegative(totalNegative);
            dform.setTotalUnknown(totalUnknown);
            dform.setTestNotIndicated(testNotIndicated);
            dform.setOtherReasons(totalUnknown-testNotIndicated);
            dform.setOvc_servNumerator(ovc_serv_numerator);
            dform.setOvc_servActive(ovc_servActive);
            dform.setOvc_servGraduated(ovc_servGraduated);
            dform.setOvc_servTransfered(ovc_servTransfered);
            dform.setOvc_servExitedWithoutGraduation(ovc_servExitedWithoutGraduation);
            dform.setOvc_servLessThan1(ovc_servLessThan1);
            dform.setOvc_serv1To9(ovc_serv1to9);
            dform.setOvc_servMale10To14(male10to14);
            dform.setOvc_servFemale10To14(female10to14);
            dform.setOvc_servMale15To17(male15to17);
            dform.setOvc_servFemale15To17(female15to17);
            dform.setOvc_servMale18To24(male18to24);
            dform.setOvc_servFemale18To24(female18to24);
            dform.setOvc_servMale25AndAbove(male25AndAbove);
            dform.setOvc_servFemale25AndAbove(female25AndAbove);
        }
        return dform;
    }*/
    public SummaryStatisticsBean getPreparedSummaryStatisticsBean(DatimReportTemplate_Mer1718 datimForm,String indicatorCategory,String indicatorName,int value)
    {
        SummaryStatisticsBean stb=new SummaryStatisticsBean();
        stb.setState(datimForm.getState());
        stb.setLga(datimForm.getLga());
        stb.setCbo(datimForm.getCbo());
        stb.setCategoryOptionCombo(indicatorCategory);
        stb.setPeriod(datimForm.getPeriod());
        stb.setIndicatorName(indicatorName);
        stb.setTotNoOfOvc(value);
        return stb;
    }
    public List getDatim2017ForExcel(List datimFormList)
    {
        List stbList=new ArrayList();
        SummaryStatisticsBean stb=new SummaryStatisticsBean();
        if(datimFormList !=null)
        {
            String[] indicators={"HIV_Numerator","Reported HIV positive to IP (includes tested in the reporting period and known positive)","Of those positive: Currently receiving ART","Of those positive: Not currently receiving ART"};
            List stbSubList=null;
            DatimReportTemplate_Mer1718 datimForm=null;
            final String OVC_HIVSTAT="OVC_HIVSTAT";
            final String OVC_SERV="OVC_SERV";
            for(int i=0; i<datimFormList.size(); i++)
            {
                stbSubList=new ArrayList();
                datimForm=(DatimReportTemplate_Mer1718)datimFormList.get(i);
                stb=getPreparedSummaryStatisticsBean(datimForm,OVC_HIVSTAT,"Numerator",datimForm.getHiv_statNumerator());
                if(stb !=null)
                stbSubList.add(stb);
                
                stb=getPreparedSummaryStatisticsBean(datimForm,OVC_HIVSTAT,"Reported HIV positive to IP (includes tested in the reporting period and known positive)",datimForm.getTotalPositive());
                if(stb !=null)
                stbSubList.add(stb);
                stb=getPreparedSummaryStatisticsBean(datimForm,OVC_HIVSTAT,"Of those positive: Currently receiving ART",datimForm.getEnrolledOnART());
                if(stb !=null)
                stbSubList.add(stb);
                stb=getPreparedSummaryStatisticsBean(datimForm,OVC_HIVSTAT,"Of those positive: Not currently receiving ART",datimForm.getNotEnrolledOnART());
                if(stb !=null)
                stbSubList.add(stb);
                stb=getPreparedSummaryStatisticsBean(datimForm,OVC_HIVSTAT,"Reported HIV negative to IP",datimForm.getTotalNegative());
                if(stb !=null)
                stbSubList.add(stb);
                stb=getPreparedSummaryStatisticsBean(datimForm,OVC_HIVSTAT,"No HIV status reported to Implementing Partner",datimForm.getTotalUnknown());
                if(stb !=null)
                stbSubList.add(stb);
                stb=getPreparedSummaryStatisticsBean(datimForm,OVC_HIVSTAT,"Of those not reported: test not indicated",datimForm.getTestNotIndicated());
                if(stb !=null)
                stbSubList.add(stb);
                stb=getPreparedSummaryStatisticsBean(datimForm,OVC_HIVSTAT,"Of those not reported: Other reasons",(datimForm.getOtherReasons()));
                if(stb !=null)
                stbSubList.add(stb);
                stb=getPreparedSummaryStatisticsBean(datimForm,OVC_SERV,"Numerator_OVC_SERV",datimForm.getOvc_servNumerator());
                if(stb !=null)
                stbSubList.add(stb);
                stb=getPreparedSummaryStatisticsBean(datimForm,OVC_SERV,"Active",datimForm.getOvc_servActive());
                if(stb !=null)
                stbSubList.add(stb);
                stb=getPreparedSummaryStatisticsBean(datimForm,OVC_SERV,"Graduated",datimForm.getOvc_servGraduated());
                if(stb !=null)
                stbSubList.add(stb);
                stb=getPreparedSummaryStatisticsBean(datimForm,OVC_SERV,"Transfered",datimForm.getOvc_servTransfered());
                if(stb !=null)
                stbSubList.add(stb);
                stb=getPreparedSummaryStatisticsBean(datimForm,OVC_SERV,"Exited without graduation",datimForm.getOvc_servExitedWithoutGraduation());
                if(stb !=null)
                stbSubList.add(stb);
                
                stb=getPreparedSummaryStatisticsBean(datimForm,OVC_SERV,"<1",datimForm.getOvc_servLessThan1());
                if(stb !=null)
                stbSubList.add(stb);
                stb=getPreparedSummaryStatisticsBean(datimForm,OVC_SERV,"1-9",datimForm.getOvc_serv1To9());
                if(stb !=null)
                stbSubList.add(stb);
                stb=getPreparedSummaryStatisticsBean(datimForm,OVC_SERV,"(Female,10-14)",datimForm.getOvc_servFemale10To14());
                if(stb !=null)
                stbSubList.add(stb);
                stb=getPreparedSummaryStatisticsBean(datimForm,OVC_SERV,"(Female, 15-17)",datimForm.getOvc_servFemale15To17());
                if(stb !=null)
                stbSubList.add(stb);
                stb=getPreparedSummaryStatisticsBean(datimForm,OVC_SERV,"(Female,18-24)",datimForm.getOvc_servFemale18To24());
                if(stb !=null)
                stbSubList.add(stb);
                stb=getPreparedSummaryStatisticsBean(datimForm,OVC_SERV,"(Female, 25+)",datimForm.getOvc_servFemale25AndAbove());
                if(stb !=null)
                stbSubList.add(stb);
                
                stb=getPreparedSummaryStatisticsBean(datimForm,OVC_SERV,"(Male,10-14)",datimForm.getOvc_servMale10To14());
                if(stb !=null)
                stbSubList.add(stb);
                stb=getPreparedSummaryStatisticsBean(datimForm,OVC_SERV,"(Male, 15-17)",datimForm.getOvc_servMale15To17());
                if(stb !=null)
                stbSubList.add(stb);
                stb=getPreparedSummaryStatisticsBean(datimForm,OVC_SERV,"(Male,18-24)",datimForm.getOvc_servMale18To24());
                if(stb !=null)
                stbSubList.add(stb);
                stb=getPreparedSummaryStatisticsBean(datimForm,OVC_SERV,"(Male, 25+)",datimForm.getOvc_servMale25AndAbove());
                if(stb !=null)
                stbSubList.add(stb);
                stbList.add(stbSubList);
            }
        }
        return stbList;
    }
    public List getLgaList(String stateCode)
    {
            DaoUtil util=new DaoUtil();
            List lgaList=new ArrayList();
            if(stateCode !=null && !stateCode.equalsIgnoreCase("All"))
            {
                lgaList=util.getHheLgaList(stateCode);
                //System.err.println("lgaList size is "+lgaList.size());
            }
            else
            {
               List stateList=util.getHheStateList();
               if(stateList !=null)
               {
                   for(int i=0; i<stateList.size(); i++)
                   {
                       stateCode=(String)stateList.get(i);
                       lgaList.addAll(util.getHheLgaList(stateCode));
                   }
               }
            }
        return lgaList;
    }
    public List getDatimFormList(List paramList)
    {
        List datimFormList=new ArrayList();
        if(paramList !=null && paramList.size() > 0)
        {
            String stateCode=(String)paramList.get(0);
            List lgaList=getLgaList(stateCode);
            if(lgaList !=null)
            {
                String lgaCode=null;
                for(int i=0; i<lgaList.size(); i++)
                {
                    lgaCode=(String)lgaList.get(i);
                    paramList.set(1, lgaCode);
                    datimFormList.add(getDatim2018Form(paramList));
                }
            }
        }
        return datimFormList;
    }
    public List getDatimFormListFromGeneratedData(String stateName,String period)
    {
        DaoUtil util=new DaoUtil();
        List datimFormList=new ArrayList();
        try
        {
        List lgaList=util.getDatimReportDaoInstance().getDistinctLgas(stateName);
            if(lgaList !=null)
            {
                String lgaName=null;
                for(int i=0; i<lgaList.size(); i++)
                {
                    lgaName=(String)lgaList.get(i);
                    datimFormList.add(util.getDatimReportDaoInstance().getDatimReportTemplate(stateName, lgaName, period));
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return datimFormList;
    }
    public void writeDatimFormsToPDF(List paramList)
    {
        List datimFormList=getDatimFormList(paramList); 
    }
    public void writeDatim2017ToPDF(List paramList)
    {
        List datimFormList=getDatimFormList(paramList);
        //String stateCode=(String)paramList.get(0);
        DatimPDFForm dpdff=new DatimPDFForm();
        dpdff.writeDatim2017FormToPDF(datimFormList); 
    }
    public void saveDatimReportData(List datimReportTemplateList,String userName)
    {
        DatimReportDao drtdao=new DatimReportDaoImpl();
        try
        {
            if(datimReportTemplateList !=null)
            {
                for(Object obj:datimReportTemplateList)
                {
                    DatimReportTemplate dform=(DatimReportTemplate)obj;
                    dform.setDateCreated(DateManager.getNewDateInstance());
                    dform.setLastModifiedDate(DateManager.getNewDateInstance());
                    dform.setRecordedBy(userName);
                    DatimReportTemplate dupDrt=drtdao.getDatimReportTemplate(dform);
                    if(dupDrt==null)
                    drtdao.saveDatimReportTemplate(dform);
                    else
                    {
                        dform.setRecordId(dupDrt.getRecordId());
                        drtdao.updateDatimReportTemplate(dform);
                    }
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    /*public List getDatimReport(List paramList)
    {
        DaoUtil util=new DaoUtil();
        List mainList=new ArrayList();
        List list=new ArrayList();
        List list2=new ArrayList();
        List list3=new ArrayList();
        List list4=new ArrayList();
        List list5=new ArrayList();
        List beneficiaryList=new ArrayList();
        ReportUtility rutil=new ReportUtility();
        String[] queryParam=rutil.getQueryParam(paramList);
        int[] ageSegregation={0,17,18,24,25,200};

//util.getHVIOrgUnitQuery(queryParam);
        String additionalQueryCriteria=util.getHVIOrgUnitQuery(queryParam);
        //String additionalQueryCriteria=util.getOrgQueryCriteriaForUmbrellaOrganization(queryParam);
        String[] dateParams={queryParam[4],queryParam[5],queryParam[6],queryParam[7]};
        String[] oneMonthDateParams={queryParam[6],queryParam[7],queryParam[6],queryParam[7]};
        //String enrollmentEndDateQuery=util.getEnrollmentEndDateQuery(dateParams);

        String[] dateArray={util.getStartDate(dateParams),util.getEndDate(dateParams)};
        String[] oneMonthDateArray={util.getStartDate(oneMonthDateParams),util.getEndDate(oneMonthDateParams)};

        MonthlySummaryFormReport msr=new MonthlySummaryFormReport();
        msr.setAgeSegregation(msr.getNewAgeSegregation());
        msr.setHouseholdAgeSegregation(ageSegregation);
        try
        {
            List caregiverServedList=new ArrayList();
            if(caregiverServedList.isEmpty())
            {
                for(int i=0; i<15; i++)
                {
                    caregiverServedList.add(0);
                }
            }
            //msr.getNoOfCaregiversAndHouseholdHeadsProvidedWithAtleastOneService("Number of caregivers served", hhAdditionalQueryCriteria, dateArray);
            List ovcServedList=msr.getNoOfOvcServedForMSF("Number of OVC served",additionalQueryCriteria,dateArray,"new");

            beneficiaryList.add(msr.getNoOfActiveBeneficiariesSupportedToAccessHIVCare("Number of active beneficiaries receiving support from PEPFAR OVC programs to access HIV services",additionalQueryCriteria,dateArray));
            list.add(msr.getNoOfActiveBeneficiariesServed("Number of active beneficiaries served by PEPFAR programs for children and families affected by HIV/AIDS", ovcServedList, caregiverServedList));
            msr.setAgeSegregation(msr.getNewFinerAgeSegregation());
            list2.add(msr.getNoOfOvcAccessingEducational("Education support", additionalQueryCriteria, dateArray));
            list2.add(msr.getNoOfOvcAccessingProtection("Social protection (including conditional and unconditional cash transfer)", additionalQueryCriteria, dateArray));
            list2.add(msr.getNoOfOvcAccessingEconStrengthening("Economic strenghtening", additionalQueryCriteria, dateArray,"new"));

           
            mainList.add(beneficiaryList);
            mainList.add(list);
            mainList.add(list2);
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return mainList;
    }*/
    public ReportParameterTemplate getPopulatedReportParameterTemplate(List paramList)
    {
        ReportParameterManager rpm=new ReportParameterManager();
        ReportParameterTemplate rpt=rpm.getPopulatedReportParameterTemplate(paramList);
        /*new ReportParameterTemplate();
        if(paramList !=null)
        {
            rpt.setStateCode(paramList.get(0).toString());
            rpt.setLgaCode(paramList.get(1).toString());
            rpt.setCboCode(paramList.get(2).toString());
            rpt.setWardCode(paramList.get(3).toString());
            rpt.setStartMonth(Integer.parseInt(paramList.get(4).toString()));
            rpt.setStartYear(Integer.parseInt(paramList.get(5).toString()));
            rpt.setEndMonth(Integer.parseInt(paramList.get(6).toString()));
            rpt.setEndYear(Integer.parseInt(paramList.get(7).toString()));
            if(paramList.size()>7)
            rpt.setPartnerCode(paramList.get(8).toString());
        }*/
        return rpt;
    }
    public ReportHeaderTemplate getReportHeaderTemplate(List paramList)
    {
        ReportParameterManager rpm=new ReportParameterManager();
        ReportHeaderTemplate rht=rpm.getReportHeaderTemplate(paramList);
        
        /*new ReportHeaderTemplate();
        DaoUtil util=new DaoUtil();
        String stateName=util.getStateName(paramList.get(0).toString());
        String lgaName=util.getLgaByStateAndLgaCode(paramList.get(0).toString(),paramList.get(1).toString()).getLga_name();
        String cboName=util.getOrganizationName(paramList.get(2).toString());
        String startMth=paramList.get(4).toString();
        String startYr=paramList.get(5).toString();
        String endMth=paramList.get(6).toString();
        String endYr=paramList.get(7).toString();
        String partnerName=util.getPartnerName(paramList.get(8).toString());
        
        String[] dateParam={paramList.get(4).toString(),paramList.get(5).toString(),paramList.get(6).toString(),paramList.get(7).toString()};
        String period=null;            
        if(util.validateDateParamenters(dateParam))
        period="01 "+util.getMonthAsString(Integer.parseInt(paramList.get(4).toString()))+" "+ paramList.get(5).toString()+" to   end of "+util.getMonthAsString(Integer.parseInt(paramList.get(6).toString()))+" "+paramList.get(7).toString();
        
        rht.setCbo(cboName);
        rht.setEndMth(endMth);
        rht.setEndYr(endYr);
        rht.setLga(lgaName);
        rht.setPartnerName(partnerName);
        rht.setStartMth(startMth);
        rht.setStartYr(startYr);
        rht.setState(stateName);
        rht.setPeriod(period);*/
        
        return rht;
    }
}
