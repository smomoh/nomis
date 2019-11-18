/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report;

import java.io.Serializable;
import com.fhi.nomis.nomisutils.ReportPeriodManager;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.DatimReportDao;
import com.fhi.kidmap.dao.DatimReportDaoImpl;
import com.fhi.nomis.OperationsManagement.ReportPeriod;
import com.fhi.nomis.nomisutils.DateManager;
//import com.nomis.business.DatimReportTemplate;
import com.nomis.business.ReportHeaderTemplate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author smomoh
 */
public class DatimReport implements Serializable
{
  int count=0;
    
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
    public List getNoOfActiveAndGraduatedOvcServedByHivStatus(List paramList)
    {
        ReportUtility rutil=new ReportUtility();
        String[] params=rutil.getQueryParam(paramList);
        ReportPeriod fy=ReportPeriodManager.getStartOfFinancialYear(Integer.parseInt(params[6]), Integer.parseInt(params[7]));
        String[] reportParamsForActive={params[0],params[1],params[2],params[3],"All","All","All",params[4],params[5],params[6],params[7],"All","All","All","All","0","17","All",params[3],params[3]};
        String[] reportParamsForExited={params[0],params[1],params[2],params[3],"All","All","All",fy.getStartMonth()+"",fy.getStartYear()+"",params[6],params[7],"All","All","All","All","0","17","All",params[3],params[3]};
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
    public List getActiveOvc_Serv(List paramList)
    {
        ReportUtility rutil=new ReportUtility();
        String[] params=rutil.getQueryParam(paramList);
        
        IndicatorDictionary ind=new IndicatorDictionary();
        String sex="Male";
        String currentlyEnrolledAndServedId=ind.getIndicatorForNumberOfOvcCurrentlyEnrolledAndServedInReportPeriod().getIndicatorId();
        String[] activeIndicatorId={currentlyEnrolledAndServedId};
        List mainList=new ArrayList();
        SummaryStatisticsReportGenerator ssrg=new SummaryStatisticsReportGenerator();
        try
        {
            int[] ageDisaggregation={0,0,1,4,5,9,10,14,15,17};
            List maleList=new ArrayList();
            List femaleList=new ArrayList();
            ReportPeriod fy=ReportPeriodManager.getStartOfFinancialYear(Integer.parseInt(params[6]), Integer.parseInt(params[7]));
               
            for(int i=0; i<ageDisaggregation.length; i+=2)
            {
                String[] activeParams={params[0],params[1],params[2],params[3],"All","All","All",params[4],params[5],params[6],params[7],"All","All","All","All",ageDisaggregation[i]+"",ageDisaggregation[i+1]+"","All",params[3],params[3]};
                List activeMainList=ssrg.getOvcEnrolledSummStatistics(sex,activeParams,activeIndicatorId);
                List activeList=(List)activeMainList.get(0);
                int maleActiveServed=Integer.parseInt(activeList.get(1).toString());
                int femaleActiveServed=Integer.parseInt(activeList.get(2).toString());
                int maleOvcServed=maleActiveServed;
                int femaleOvcServed=femaleActiveServed;
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
    public List getGraduatedOvc_Serv(List paramList)
    {
        ReportUtility rutil=new ReportUtility();
        String[] params=rutil.getQueryParam(paramList);
        IndicatorDictionary ind=new IndicatorDictionary();
        String sex="Male";
        String servedAndGraduated=ind.getIndicatorForNumberOfOvcGraduatedButServedInReportPeriod().getIndicatorId();
        String[] graduatedIndicatorId={servedAndGraduated};
        List mainList=new ArrayList();
        SummaryStatisticsReportGenerator ssrg=new SummaryStatisticsReportGenerator();
        try
        {
            int[] ageDisaggregation={0,0,1,4,5,9,10,14,15,17};
            List maleList=new ArrayList();
            List femaleList=new ArrayList();
            ReportPeriod fy=ReportPeriodManager.getStartOfFinancialYear(Integer.parseInt(params[6]), Integer.parseInt(params[7]));
               
            for(int i=0; i<ageDisaggregation.length; i+=2)
            {
                String[] graduatedParams={params[0],params[1],params[2],params[3],"All","All","All",fy.getStartMonth()+"",fy.getStartYear()+"",params[6],params[7],"All","All","All","All",ageDisaggregation[i]+"",ageDisaggregation[i+1]+"","All",params[3],params[3]};
                List graduatedMainList=ssrg.getOvcEnrolledSummStatistics(sex,graduatedParams,graduatedIndicatorId);
                List graduatedList=(List)graduatedMainList.get(0);
                int maleGraduatedAndServed=Integer.parseInt(graduatedList.get(1).toString());
                int femaleGraduatedAndServed=Integer.parseInt(graduatedList.get(2).toString());
                int maleOvcServed=maleGraduatedAndServed;
                int femaleOvcServed=femaleGraduatedAndServed;
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
        String additionalQueryCriteria=util.getHVIOrgUnitQuery(queryParam);
       
       MonthlySummaryFormReport msr=new MonthlySummaryFormReport();
       try
       {
            msr.setAgeSegregation(msr.getNewFinerAgeSegregation());
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
        ReportParameterTemplate rt=getPopulatedReportParameterTemplate(paramList);
        DatimCaregiverReport dcr=new DatimCaregiverReport();
               
        String[] dateParam={paramList.get(4).toString(),paramList.get(5).toString(),paramList.get(6).toString(),paramList.get(7).toString()};
        String period=null;            
        if(util.validateDateParamenters(dateParam))
        period="01 "+util.getMonthAsString(Integer.parseInt(paramList.get(4).toString()))+" "+ paramList.get(5).toString()+" to   end of "+util.getMonthAsString(Integer.parseInt(paramList.get(6).toString()))+" "+paramList.get(7).toString();
        System.err.println("period in datim form is "+period);
        
        List datim2017HivList=getNoOfActiveAndGraduatedOvcServedByHivStatus(paramList);
        List activeMainList=getActiveOvc_Serv(paramList);
        List graduatedMainList=getGraduatedOvc_Serv(paramList);
        if(activeMainList !=null && !activeMainList.isEmpty() && graduatedMainList !=null && !graduatedMainList.isEmpty())
        {
            List ovc_servActiveNotActiveList=getOvcServedDisaggregatedByActiveAndNotActive(paramList);
            List activeMaleList=(List)activeMainList.get(0);
            List activeFemaleList=(List)activeMainList.get(1);
            List graduatedMaleList=(List)graduatedMainList.get(0);
            List graduatedFemaleList=(List)graduatedMainList.get(1);
            ReportHeaderTemplate rht=getReportHeaderTemplate(paramList);
            dform.setState(rht.getState());
            dform.setLga(rht.getLga());
            dform.setCbo(rht.getCbo());
            dform.setStartMth(rht.getStartMth());
            dform.setStartYr(rht.getStartYr());
            dform.setEndMth(rht.getEndMth());
            dform.setEndYr(rht.getEndYr());
            dform.setPeriod(period);
            dform.setPartnerName("All");
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
            DatimReportTemplate dform18to24Active=dcr.getDatimCaregiverActiveAndServedReportFor18To24(rt);
            DatimReportTemplate dform25AndAboveActive=dcr.getDatimCaregiverActiveAndServedReportFor25AndAbove(rt);
            
            int activeMaleLessThan1=Integer.parseInt(activeMaleList.get(0).toString());
            int activeFemaleLessThan1=Integer.parseInt(activeFemaleList.get(0).toString());
            int activeMale1to4=Integer.parseInt(activeMaleList.get(1).toString());
            int activeFemale1to4=Integer.parseInt(activeFemaleList.get(1).toString());
            int activeMale5to9=Integer.parseInt(activeMaleList.get(2).toString());
            int activeFemale5to9=Integer.parseInt(activeFemaleList.get(2).toString());
            //int activeOvc_servLessThan1=activeMaleLessThan1+activeFemaleLessThan1;
            //int activeOvc_serv1to9=activeMale1to4+activeFemale1to4+activeMale5to9+activeFemale5to9;
            int activeMale10to14=Integer.parseInt(activeMaleList.get(3).toString());
            int activeFemale10to14=Integer.parseInt(activeFemaleList.get(3).toString());
            int activeMale15to17=Integer.parseInt(activeMaleList.get(4).toString());
            int activeFemale15to17=Integer.parseInt(activeFemaleList.get(4).toString());
            int maleCaregiversActiveServed=dform18to24Active.getOvc_servMale18To24()+dform25AndAboveActive.getOvc_servMale25AndAbove();
            int femaleCaregiversActiveServed=dform18to24Active.getOvc_servFemale18To24()+dform25AndAboveActive.getOvc_servFemale25AndAbove();
            
            
            DatimReportTemplate dform18to24Graduated=dcr.getDatimCaregiverGraduatedAndServedReportFor18To24(rt);
            DatimReportTemplate dform25AndAboveGraduated=dcr.getDatimCaregiverGraduatedAndServedReportFor25AndAbove(rt);
                   
            int graduatedMaleLessThan1=Integer.parseInt(graduatedMaleList.get(0).toString());
            int graduatedFemaleLessThan1=Integer.parseInt(graduatedFemaleList.get(0).toString());
            int graduatedMale1to4=Integer.parseInt(graduatedMaleList.get(1).toString());
            int graduatedFemale1to4=Integer.parseInt(graduatedFemaleList.get(1).toString());
            int graduatedMale5to9=Integer.parseInt(graduatedMaleList.get(2).toString());
            int graduatedFemale5to9=Integer.parseInt(graduatedFemaleList.get(2).toString());
            //int graduatedOvc_servLessThan1=graduatedMaleLessThan1+graduatedFemaleLessThan1;
            //int graduatedOvc_serv1to9=graduatedMale1to4+graduatedFemale1to4+graduatedMale5to9+graduatedFemale5to9;
            int graduatedMale10to14=Integer.parseInt(graduatedMaleList.get(3).toString());
            int graduatedFemale10to14=Integer.parseInt(graduatedFemaleList.get(3).toString());
            int graduatedMale15to17=Integer.parseInt(graduatedMaleList.get(4).toString());
            int graduatedFemale15to17=Integer.parseInt(graduatedFemaleList.get(4).toString());
            
            int maleCaregiversGraduatedServed=dform18to24Graduated.getOvc_servMale18To24()+dform25AndAboveGraduated.getOvc_servMale25AndAbove();
            int femaleCaregiversGraduatedServed=dform18to24Graduated.getOvc_servFemale18To24()+dform25AndAboveGraduated.getOvc_servFemale25AndAbove();
                                  
            int ovc_servActive=Integer.parseInt(ovc_servActiveNotActiveList.get(0).toString())+maleCaregiversActiveServed+femaleCaregiversActiveServed;
            int ovc_servGraduated=Integer.parseInt(ovc_servActiveNotActiveList.get(1).toString())+maleCaregiversGraduatedServed+femaleCaregiversGraduatedServed;
            int ovc_servTransfered=Integer.parseInt(ovc_servActiveNotActiveList.get(2).toString())+getNoOfCaregiversServedAndTransfered(reportParamsForExited);
            int ovc_servExitedWithoutGraduation=Integer.parseInt(ovc_servActiveNotActiveList.get(3).toString())+getNoOfCaregiversServedAndExitedWithoutGraduation(reportParamsForExited);
            int ovc_serv_numerator=ovc_servActive+ovc_servGraduated;
            
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
            
            dform.setOvc_servActiveLessThan1Female(activeFemaleLessThan1);
            dform.setOvc_servActiveLessThan1Male(activeMaleLessThan1);
            dform.setOvc_servActive1to4Female(activeFemale1to4);
            dform.setOvc_servActive1to4Male(activeMale1to4);
            dform.setOvc_servActive5to9Female(activeFemale5to9);
            dform.setOvc_servActive5to9Male(activeMale5to9);
            dform.setOvc_servActive10to14Female(activeFemale10to14);
            dform.setOvc_servActive10to14Male(activeMale10to14);
            dform.setOvc_servActive15to17Female(activeFemale15to17);
            dform.setOvc_servActive15to17Male(activeMale15to17);
            dform.setOvc_servActive18AndAboveFemale(femaleCaregiversActiveServed);
            dform.setOvc_servActive18AndAboveMale(maleCaregiversActiveServed);
            
            dform.setOvc_servGraduatedLessThan1Female(graduatedFemaleLessThan1);
            dform.setOvc_servGraduatedLessThan1Male(graduatedMaleLessThan1);
            dform.setOvc_servGraduated1to4Female(graduatedFemale1to4);
            dform.setOvc_servGraduated1to4Male(graduatedMale1to4);
            dform.setOvc_servGraduated5to9Female(graduatedFemale5to9);
            dform.setOvc_servGraduated5to9Male(graduatedMale5to9);
            dform.setOvc_servGraduated10to14Female(graduatedFemale10to14);
            dform.setOvc_servGraduated10to14Male(graduatedMale10to14);
            dform.setOvc_servGraduated15to17Female(graduatedFemale15to17);
            dform.setOvc_servGraduated15to17Male(graduatedMale15to17);
            dform.setOvc_servGraduated18AndAboveFemale(femaleCaregiversGraduatedServed);
            dform.setOvc_servGraduated18AndAboveMale(maleCaregiversGraduatedServed);
            
        }
        count++;
        System.err.println("Count is "+count);
        return dform;
    }
    
    public SummaryStatisticsBean getPreparedSummaryStatisticsBean(DatimReportTemplate datimForm,String indicatorCategory,String indicatorName,int value)
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
    public List getLgaList(String stateCode)
    {
            DaoUtil util=new DaoUtil();
            List lgaList=new ArrayList();
            if(stateCode !=null && !stateCode.equalsIgnoreCase("All"))
            {
                lgaList=util.getHheLgaList(stateCode);
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
    
    public ReportParameterTemplate getPopulatedReportParameterTemplate(List paramList)
    {
        ReportParameterManager rpm=new ReportParameterManager();
        ReportParameterTemplate rpt=rpm.getPopulatedReportParameterTemplate(paramList);
        return rpt;
    }
    public ReportHeaderTemplate getReportHeaderTemplate(List paramList)
    {
        ReportParameterManager rpm=new ReportParameterManager();
        ReportHeaderTemplate rht=rpm.getReportHeaderTemplate(paramList);
        return rht;
    }
}
