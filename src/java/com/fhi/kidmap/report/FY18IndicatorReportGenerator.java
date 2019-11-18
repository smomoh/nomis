/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report;

import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.nomis.nomisutils.NomisConstant;
import com.nomis.business.ReportTemplate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author smomoh
 */
public class FY18IndicatorReportGenerator 
{
    SummaryStatisticsReportGenerator ssrg=new SummaryStatisticsReportGenerator();
    ReportUtility rutil=new ReportUtility();
    DaoUtil util=new DaoUtil();
    public List getIndicatorValues(List paramList)
    {
        List mainlist=new ArrayList();
        String[] queryParam=rutil.getQueryParam(paramList);
        String[] hhsDateParams={queryParam[0],queryParam[1],queryParam[2],queryParam[3],"All","All",queryParam[4],queryParam[5],queryParam[6],queryParam[7],"All"};
        String[] dateParams={queryParam[4],queryParam[5],queryParam[6],queryParam[7]};
        String startDate=util.getStartDate(dateParams);
        String endDate=util.getEndDate(dateParams);
        String additionalQueryCriteria=util.getHVIOrgUnitQuery(queryParam);
        String enrollmentEndDateQuery=util.getEnrollmentEndDateQuery(dateParams);
        String enrollmentPeriodQuery=util.getEnrollmentDateQuery(util.getStartDate(dateParams),util.getEndDate(dateParams));
        String servicePeriodQuery=util.getAdditionalServiceQuery(queryParam[4],queryParam[5],queryParam[6],queryParam[7]);
        String hviServiceQueryCriteria=util.getHVIServiceReportCriteria(hhsDateParams);
        //String cgiverPeriodQuery=util.getCaregiverPeriodCriteria(util.getStartDate(dateParams),util.getEndDate(dateParams));
        mainlist.add(getNewlyEnrolledAndServedIndicatorValues(additionalQueryCriteria, enrollmentEndDateQuery,servicePeriodQuery,hviServiceQueryCriteria,startDate,endDate));
        mainlist.add(getOvcWithBirthCertificateAndServed(additionalQueryCriteria, servicePeriodQuery,hviServiceQueryCriteria));
        mainlist.add(getAssessedOnHIVRiskAndServedIndicatorValues(additionalQueryCriteria, enrollmentPeriodQuery,"All","All"));
        mainlist.add(getOvcTestedAndReceivedResultIndicatorValues(additionalQueryCriteria, enrollmentPeriodQuery,"All","All"));
        
        
        return mainlist;
    }
    public ReportTemplate getNewlyEnrolledAndServedIndicatorValues(String additionalQueryCriteria, String enrollmentPeriodQuery,String servicePeriodQuery,String cgiverPeriodQuery,String startDate,String endDate)
    {
        ReportTemplate reportTemplate=null;
        List valueList=new ArrayList();
        int ovc_served=0;
        int cgiver_served=0;
        int totalOvc_serv=0;
        try
        {
            String indicatorName="OVC_ENROLLED";
            int[] newAgeSegregation=rutil.getNewAgeSegregation();
            String ageQuery=" ";
            String lowerAgeLimit="";
            String upperAgeLimit="";
            int startAge=0;
            int endAge=0;
            for(int i=0; i<newAgeSegregation.length; i+=2)
            {
                startAge=newAgeSegregation[i];
                endAge=newAgeSegregation[i+1];
                lowerAgeLimit=startAge+"";
                upperAgeLimit=endAge+"";
                if(i==newAgeSegregation.length-1)
                upperAgeLimit=newAgeSegregation[i+1]+"+";
                ageQuery=util.getAgeCriteria(lowerAgeLimit, upperAgeLimit);
                //getNoOfOvcNewlyEnrolledAndServedWithinTheReportPeriod(String indicatorName,String additionalQueryCriteria,int startAge,int endAge,String startDate,String endDate,String indicatorCode)
                List list=ssrg.getNoOfOvcNewlyEnrolledAndServedWithinTheReportPeriod(indicatorName,additionalQueryCriteria, startAge, endAge,startDate,endDate, indicatorName);
                if(list !=null && !list.isEmpty())
                {
                    valueList.add(list.get(1));
                    valueList.add(list.get(2));
                }
            }
            List ovc_servResultList=getOVC_SERV(additionalQueryCriteria, servicePeriodQuery, cgiverPeriodQuery, "",false,false);
            
            if(ovc_servResultList.size()>2)
            totalOvc_serv=Integer.parseInt(ovc_servResultList.get(2).toString());
            
            reportTemplate=getPopulatedReportTemplate(valueList,totalOvc_serv,"OVC_SERV Total");
            
            reportTemplate.setOvc_serv(totalOvc_serv);
            reportTemplate.setIndicatorName(indicatorName);
            System.err.println(reportTemplate.getFemale10to14()+";"+reportTemplate.getFemale15to17()+"-"+reportTemplate.getMale10to14()+"-"+reportTemplate.getMale15to17()+"-valueList.size() is "+valueList.size());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return reportTemplate;
    }
    public ReportTemplate getOvcWithBirthCertificateAndServed(String additionalQueryCriteria, String enrollmentEndDateQuery,String cgiverPeriodQuery)
    {
        ReportTemplate reportTemplate=null;
        List valueList=new ArrayList();
        int totalOvc_serv=0;
        try
        {
            String indicatorName="OVC_BIRTHCERT";
            int[] newAgeSegregation=rutil.get0_17AgeSegregation();
            String ageQuery=" ";
            String lowerAgeLimit="";
            String upperAgeLimit="";
            
            for(int i=0; i<newAgeSegregation.length; i+=2)
            {
                lowerAgeLimit=newAgeSegregation[i]+"";
                upperAgeLimit=newAgeSegregation[i+1]+"";
                if(i==newAgeSegregation.length-1)
                upperAgeLimit=newAgeSegregation[i+1]+"";
                ageQuery=util.getAgeCriteria(lowerAgeLimit, upperAgeLimit);
                List list=ssrg.getNoOfOvcWithBirthCertificate(indicatorName,additionalQueryCriteria, enrollmentEndDateQuery,ageQuery, indicatorName,null,false);
                if(list !=null && !list.isEmpty())
                {
                    valueList.add(list.get(1));
                    valueList.add(list.get(2));
                }
            }
            ageQuery=util.getAgeCriteria(0+"", 17+"");
            List ovc_servResultList=ssrg.getNoOfOvcEverEnrolled(indicatorName,additionalQueryCriteria,enrollmentEndDateQuery,ageQuery,indicatorName);
            //List ovc_servResultList=getOVC_SERV(additionalQueryCriteria, servicePeriodQuery, cgiverPeriodQuery, ageQuery,false,false);
            //getNoOfOvcEverEnrolled(indicatorName,additionalQueryCriteria,enrollmentEndDateQuery,ageQuery,String indicatorCode)
            if(ovc_servResultList.size()>3)
            totalOvc_serv=Integer.parseInt(ovc_servResultList.get(3).toString());
            reportTemplate=getPopulatedReportTemplate(valueList,totalOvc_serv,"Ever enrolled (0-17)");
            reportTemplate.setIndicatorName(indicatorName);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return reportTemplate;
    }
    public ReportTemplate getAssessedOnHIVRiskAndServedIndicatorValues(String additionalQueryCriteria, String enrollmentPeriodQuery,String startDate,String endDate)
    {
        ReportTemplate reportTemplate=null;
        List valueList=new ArrayList();
        
        int totalNegUnkOvc=0;
        try
        {
            String indicatorName="OVC_HIVRISKASS";
            int[] ageDisaggregation=rutil.get0_17AgeSegregation();
            String ageQuery=" ";
            String lowerAgeLimit="";
            String upperAgeLimit="";
            //String startDate="All";
            //String endDate="All";
            for(int i=0; i<ageDisaggregation.length; i+=2)
            {
                lowerAgeLimit=ageDisaggregation[i]+"";
                upperAgeLimit=ageDisaggregation[i+1]+"";
                if(i==ageDisaggregation.length-1)
                upperAgeLimit=ageDisaggregation[i+1]+"";
                ageQuery=util.getAgeCriteria(lowerAgeLimit, upperAgeLimit);
                
                List list=ssrg.getNoOfOvcThatHasHIVRiskAssessmentDone(indicatorName,indicatorName,additionalQueryCriteria, startDate,endDate, ageQuery,true,false);
                if(list !=null && !list.isEmpty())
                {
                    valueList.add(list.get(1));
                    valueList.add(list.get(2));
                }
            }
            ageQuery=util.getAgeCriteria(0+"", 17+"");
            List hivNegList=ssrg.getNoOfOvcCurrentlyEnrolledByHIVStatus(indicatorName,additionalQueryCriteria,ageQuery,indicatorName,NomisConstant.HIV_NEGATIVE,false);
            List hivUnknownList=ssrg.getNoOfOvcCurrentlyEnrolledByHIVStatus(indicatorName,additionalQueryCriteria,ageQuery,indicatorName,NomisConstant.HIV_UNKNOWN,false);
            if(hivNegList.size()>3)
            totalNegUnkOvc+=Integer.parseInt(hivNegList.get(3).toString());
            if(hivUnknownList.size()>3)
            totalNegUnkOvc+=Integer.parseInt(hivUnknownList.get(3).toString());
            
            reportTemplate=getPopulatedReportTemplate(valueList,totalNegUnkOvc,"HIV unknown + HIV Negative OVC");
            reportTemplate.setOvc_serv(totalNegUnkOvc);
            reportTemplate.setIndicatorName(indicatorName);
            System.err.println(reportTemplate.getFemale10to14()+";"+reportTemplate.getFemale15to17()+"-"+reportTemplate.getMale10to14()+"-"+reportTemplate.getMale15to17()+"-valueList.size() is "+valueList.size());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return reportTemplate;
    }
    public ReportTemplate getOvcTestedAndReceivedResultIndicatorValues(String additionalQueryCriteria, String enrollmentPeriodQuery,String startDate,String endDate)
    {
        ReportTemplate reportTemplate=null;
        List valueList=new ArrayList();
        
        int totalTestedOvc=0;
        try
        {
            String indicatorName="OVC_HTSLINK";
            int[] ageDisaggregation=rutil.get0_17AgeSegregation();
            String ageQuery=" ";
            String lowerAgeLimit="";
            String upperAgeLimit="";
            //String startDate="All";
            //String endDate="All";
            for(int i=0; i<ageDisaggregation.length; i+=2)
            {
                lowerAgeLimit=ageDisaggregation[i]+"";
                upperAgeLimit=ageDisaggregation[i+1]+"";
                if(i==ageDisaggregation.length-1)
                upperAgeLimit=ageDisaggregation[i+1]+"";
                ageQuery=util.getAgeCriteria(lowerAgeLimit, upperAgeLimit);
                
                List list=ssrg.getNumberOfOvcTestedAndReceivedResult(indicatorName,additionalQueryCriteria,ageQuery, startDate,endDate,indicatorName, false);
                if(list !=null && !list.isEmpty())
                {
                    valueList.add(list.get(1));
                    valueList.add(list.get(2));
                }
            }
            ageQuery=util.getAgeCriteria(0+"", 17+"");
            List totalTestedForHivList=ssrg.getNoOfOvcTestedForHIV(indicatorName,additionalQueryCriteria,startDate,endDate,ageQuery,indicatorName);
            /*List hivNegList=ssrg.getNoOfOvcCurrentlyEnrolledByHIVStatus(indicatorName,additionalQueryCriteria,ageQuery,indicatorName,NomisConstant.HIV_NEGATIVE,false);
            List hivUnknownList=ssrg.getNoOfOvcCurrentlyEnrolledByHIVStatus(indicatorName,additionalQueryCriteria,ageQuery,indicatorName,NomisConstant.HIV_UNKNOWN,false);
            if(hivNegList.size()>3)
            totalNegUnkOvc+=Integer.parseInt(hivNegList.get(3).toString());
            if(hivUnknownList.size()>3)
            totalNegUnkOvc+=Integer.parseInt(hivUnknownList.get(3).toString());*/
            if(totalTestedForHivList !=null && totalTestedForHivList.size()>3)
            totalTestedOvc+=Integer.parseInt(totalTestedForHivList.get(3).toString());
            reportTemplate=getPopulatedReportTemplate(valueList,totalTestedOvc,"Total number tested for HIV");
            reportTemplate.setOvc_serv(totalTestedOvc);
            reportTemplate.setIndicatorName(indicatorName);
            System.err.println(reportTemplate.getFemale10to14()+";"+reportTemplate.getFemale15to17()+"-"+reportTemplate.getMale10to14()+"-"+reportTemplate.getMale15to17()+"-valueList.size() is "+valueList.size());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return reportTemplate;
    }
    public List getOVC_SERV(String additionalQueryCriteria, String ovcPeriodQuery, String caregiverPeriodQuery, String ovcAgeQuery,boolean newlyEnrolled,boolean currentlyEnrolled)
    {
        List resultList = ssrg.getOVC_SERV(additionalQueryCriteria, ovcPeriodQuery, caregiverPeriodQuery, ovcAgeQuery,newlyEnrolled,currentlyEnrolled);
        return resultList;
    }
    public ReportTemplate getPopulatedReportTemplate(List valueList,int ovc_serv,String ovc_servAgeCategory)
    {
        ReportTemplate reportTemplate=new ReportTemplate();
        int maleLessThan1=0;
        int femaleLessThan1=0;
        int male1to4=0;
        int female1to4=0;
        int male5to9=0;
        int female5to9=0;
        int male10to14=0;
        int female10to14=0;
        int male15to17=0;
        int female15to17=0;
        int male18to24=0;
        int female18to24=0;
        int male25AndAbove=0;
        int female25AndAbove=0;
        int maleTotal=0;
        int femaleTotal=0;
        int grandTotal=0;
        
        if(valueList !=null && !valueList.isEmpty())
        {
            
            maleLessThan1=Integer.parseInt(valueList.get(0).toString());
            
            if(valueList.size()>1)
            femaleLessThan1=Integer.parseInt(valueList.get(1).toString());
            if(valueList.size()>2)
            male1to4=Integer.parseInt(valueList.get(2).toString());
            if(valueList.size()>3)
            female1to4=Integer.parseInt(valueList.get(3).toString());
            if(valueList.size()>4)
            male5to9=Integer.parseInt(valueList.get(4).toString());
            if(valueList.size()>5)
            female5to9=Integer.parseInt(valueList.get(5).toString());
            if(valueList.size()>6)
            male10to14=Integer.parseInt(valueList.get(6).toString());
            if(valueList.size()>7)
            female10to14=Integer.parseInt(valueList.get(7).toString());
            if(valueList.size()>8)
            male15to17=Integer.parseInt(valueList.get(8).toString());
            if(valueList.size()>9)
            female15to17=Integer.parseInt(valueList.get(9).toString());
            if(valueList.size()>10)
            male18to24=Integer.parseInt(valueList.get(10).toString());
            if(valueList.size()>11)
            female18to24=Integer.parseInt(valueList.get(11).toString());
            if(valueList.size()>12)
            male25AndAbove=Integer.parseInt(valueList.get(12).toString());
            if(valueList.size()>13)
            female25AndAbove=Integer.parseInt(valueList.get(13).toString());
            
            maleTotal=maleLessThan1+male1to4+male5to9+male10to14+male15to17+male18to24+male25AndAbove;
            femaleTotal=femaleLessThan1+female1to4+female5to9+female10to14+female15to17+female18to24+female25AndAbove;
            grandTotal=maleTotal+femaleTotal;
            
            reportTemplate.setMaleLessThan1(maleLessThan1);
            reportTemplate.setFemaleLessThan1(femaleLessThan1);
            reportTemplate.setMale1to4(male1to4);
            reportTemplate.setFemale1to4(female1to4);      
            reportTemplate.setMale5to9(male5to9);  
            reportTemplate.setFemale5to9(female5to9);
            reportTemplate.setMale10to14(male10to14);
            reportTemplate.setFemale10to14(female10to14);
            reportTemplate.setMale15to17(male15to17);
            reportTemplate.setFemale15to17(female15to17);
            reportTemplate.setMale18to24(male18to24);
            reportTemplate.setFemale18to24(female18to24);
            reportTemplate.setMale25AndAbove(male25AndAbove);
            reportTemplate.setFemale25AndAbove(female25AndAbove);
            reportTemplate.setMaleTotal(maleTotal);
            reportTemplate.setFemaleTotal(femaleTotal);
            reportTemplate.setGrandTotal(grandTotal);
            reportTemplate.setOvc_serv(ovc_serv);
            reportTemplate.setOvc_servAgeCategory(ovc_servAgeCategory);
            
            System.err.println("grandTotal is "+grandTotal);
            System.err.println("ovc_serv is "+ovc_serv);
            if(ovc_serv>0)
            {
                double proportion=Math.round((grandTotal)/(double)(ovc_serv)*100);
                reportTemplate.setPercentage(proportion+"%");
                System.err.println("proportion is "+proportion);
            }
            
            
        }
        return reportTemplate;
    }
}
