/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomis.chart;

import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.report.IndicatorWarehouse;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.nomis.nomisutils.DateManager;
import com.fhi.nomis.nomisutils.NomisConstant;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;

/**
 *
 * @author smomoh
 */
public class ChartContentGenerator  implements Serializable
{
    ChartValueGenerator cvg=new ChartValueGenerator();
    double largestValue=0;
    public List getSeriesFilterAndCategoryOptions()
    {
        List list=new ArrayList();
        list.add("Organization unit");
        list.add("Indicators");
        list.add("Period");
        return list;
    }
    public List getCategoryOptions(String selectedSeriesOption)
    {
        List list=getSeriesFilterAndCategoryOptions();
        if(selectedSeriesOption==null)
        list.remove(list.get(0));
        else
        list.remove(selectedSeriesOption);
        return list;
    }
    public List getFilterOptions(String selectedSeriesOption,String selectedCategoryOption)
    {
        System.err.println("selectedSeriesOption is "+selectedSeriesOption+" selectedFilterOption is "+selectedCategoryOption);
        List list=getCategoryOptions(selectedSeriesOption);
        if(selectedCategoryOption==null)
        list.remove(list.get(0));
        else
        list.remove(selectedCategoryOption);
        
        return list;
    }
    public List getProportionOfHivPosOvcCurrentlyEnrolled(String indicatorCode,String additionalQuery,String startDate,String endDate,boolean percentage)
    {
       double value=cvg.getProportionOfOvcCurrentlyEnrolledByHivStatus(indicatorCode,"","","","positive",true);
       List list=new ArrayList();
       list.add(value);
       return list;
    }
    public List getAppropriateChartValues(String indicatorCode,String indicatorName,String additionalQuery,String startDate,String endDate,boolean percentage)
    {
        List list=new ArrayList();
        if(indicatorCode !=null)
        {
            //System.err.println("indicatorCode is "+indicatorCode);
            DaoUtil util=new DaoUtil();
            String enrollmentEndDateQuery=util.getEnrollmentEndDateQuery(endDate);
            if(indicatorCode.equalsIgnoreCase("vchivpprpce"))
            list.add(cvg.getProportionOfOvcCurrentlyEnrolledByHivStatus(indicatorCode,indicatorName,additionalQuery,enrollmentEndDateQuery,NomisConstant.HIV_POSITIVE,true));
            else if(indicatorCode.equalsIgnoreCase("vchivnprpce"))
            list.add(cvg.getProportionOfOvcCurrentlyEnrolledByHivStatus(indicatorCode,indicatorName,additionalQuery,enrollmentEndDateQuery,NomisConstant.HIV_NEGATIVE,true));
            else if(indicatorCode.equalsIgnoreCase("vchivuprpce"))
            list.add(cvg.getProportionOfOvcCurrentlyEnrolledByHivStatus(indicatorCode,indicatorName,additionalQuery,enrollmentEndDateQuery,NomisConstant.HIV_UNKNOWN,true));
        }
        return list;
    }
    
    public List getChartDataset(Chart chart,HttpSession session)
    {
        List mainChartList=null;
        //System.err.println("chart is "+chart);
        if(chart !=null)
        {
            //System.err.println("chart.getCategoryOption() in getChartDataset "+chart.getCategoryOption());
            String categoryOption=chart.getCategoryOption();
            if(categoryOption !=null)
            {
                if(categoryOption.equalsIgnoreCase("Period"))
                mainChartList=getCategoryValuesByPeriod(chart,session);
                else if(categoryOption.equalsIgnoreCase("Organization unit"))
                mainChartList=getCategoryValuesByOrgUnit(chart,session);
                else if(categoryOption.equalsIgnoreCase("Indicators"))
                mainChartList=getCategoryValuesByIndicators(chart,session);
                //System.err.println("mainChartList in getChartDataset "+mainChartList);
            }
        }
        
        return mainChartList;
    }
    
    public List getCategoryValuesByOrgUnit(Chart chart,HttpSession session)
    {
        List mainChartList=new ArrayList();
        List chartValueList=new ArrayList();
        String filter=null;
        String series=null;
        String chartId=chart.getChartId();
        String[] indicatorIds=chart.getSelectedIndicators().split(",");
        String[] selectedOrganizationUnit=chart.getSelectedOrgUnits().split(",");
        String[] selectedPeriods=chart.getSelectedPeriods().split(",");
        String periodType=chart.getPeriodType();
        String selectedSeriesOption=chart.getSeries();
        String startDate=null;
        String endDate=null;
        String indicatorName=null;
              
            try
            {
                if(selectedOrganizationUnit !=null)
                {
                    IndicatorWarehouse indw=new IndicatorWarehouse();
                    if(selectedPeriods !=null && indicatorIds !=null)
                    {
                        String indicatorId=null;
                        if(selectedSeriesOption !=null && selectedSeriesOption.equalsIgnoreCase("Indicators"))
                        {
                            startDate=getStartDate(periodType,selectedPeriods[0]);
                            endDate=getEndDate(periodType,selectedPeriods[0]);
                            filter=selectedPeriods[0];
                            for(int j=0; j<indicatorIds.length; j++)
                            {
                                indicatorId=indicatorIds[j];
                                series=indw.getIndicatorById(indicatorId).getIndicatorName();
                                chartValueList=getChartValueListByOrgUnit(chart,indicatorId,indicatorName,series,filter,startDate,endDate);
                                //chartValueList=getChartValueListByOrgUnit(selectedOrganizationUnit,indicatorId,indicatorName,series,filter,startDate,endDate);
                                mainChartList.add(chartValueList);
                            }
                        }
                        else if(selectedSeriesOption !=null && selectedSeriesOption.equalsIgnoreCase("Period"))
                        {
                            indicatorId=indicatorIds[0];
                            filter=indw.getIndicatorById(indicatorId).getIndicatorName();
                            for(int j=0; j<selectedPeriods.length; j++)
                            {
                                startDate=getStartDate(periodType,selectedPeriods[j]);
                                endDate=getEndDate(periodType,selectedPeriods[j]);
                                series=selectedPeriods[j];
                                chartValueList=getChartValueListByOrgUnit(chart,indicatorId,indicatorName,series,filter,startDate,endDate);
                                //chartValueList=getChartValueListByOrgUnit(selectedOrganizationUnit,indicatorId,indicatorName,series,filter,startDate,endDate);
                                mainChartList.add(chartValueList);
                         }
                      }
                  }
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        String selectedYear=" ";
        session.setAttribute("selectedChartYear", selectedYear);
        session.setAttribute("chartValueList", mainChartList);
        session.setAttribute("largestValue", largestValue);
        session.setAttribute("labelVisible", true);
        return mainChartList;
    }
    public List getCategoryValuesByIndicators(Chart chart,HttpSession session)
    {
       List mainChartList=new ArrayList();
        List chartValueList=new ArrayList();
        String filter=null;
        String series=null;
        String chartId=chart.getChartId();
        String[] indicatorIds=chart.getSelectedIndicators().split(",");
        String[] selectedOrganizationUnit=chart.getSelectedOrgUnits().split(",");
        String[] selectedPeriods=chart.getSelectedPeriods().split(",");
        String periodType=chart.getPeriodType();
        String selectedSeriesOption=chart.getSeries();
        String startDate=null;
        String endDate=null;
        String additionalQuery=null;   
            try
            {
                if(indicatorIds !=null)
                {
                    if(selectedPeriods !=null && indicatorIds !=null)
                    {
                        if(selectedSeriesOption !=null && selectedSeriesOption.equalsIgnoreCase("Organization unit"))
                        {
                            startDate=getStartDate(periodType,selectedPeriods[0]);
                            endDate=getEndDate(periodType,selectedPeriods[0]);
                            filter=selectedPeriods[0];
                            for(int j=0; j<selectedOrganizationUnit.length; j++)
                            {
                                series=getOrganizationUnitName(chart.getOrgUnitLevel(),selectedOrganizationUnit[j]);
                                additionalQuery=getAdditionalQueryCriteria(chart,selectedOrganizationUnit[j]);
                                chartValueList=getChartValueListByIndicators(chart,additionalQuery,series,filter,startDate,endDate);
                                mainChartList.add(chartValueList);
                            }
                        }
                        else if(selectedSeriesOption !=null && selectedSeriesOption.equalsIgnoreCase("Period"))
                        {
                            filter=selectedOrganizationUnit[0];
                            for(int j=0; j<selectedPeriods.length; j++)
                            {
                                startDate=getStartDate(periodType,selectedPeriods[j]);
                                endDate=getEndDate(periodType,selectedPeriods[j]);
                                series=selectedPeriods[j];
                                additionalQuery=getAdditionalQueryCriteria(chart,selectedOrganizationUnit[j]);
                                chartValueList=getChartValueListByIndicators(chart,additionalQuery,series,filter,startDate,endDate);
                                
                                mainChartList.add(chartValueList);
                         }
                      }
                  }
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        String selectedYear=" ";
        session.setAttribute("selectedChartYear", selectedYear);
        session.setAttribute("chartValueList", mainChartList);
        session.setAttribute("largestValue", largestValue);
        session.setAttribute("labelVisible", true);
        return mainChartList;
    }
    public List getCategoryValuesByPeriod(Chart chart,HttpSession session)
    {
        List mainChartList=new ArrayList();
        List chartValueList=new ArrayList();
        String filter=null;
        String series=null;
        String chartId=chart.getChartId();
        String[] indicatorIds=chart.getSelectedIndicators().split(",");
        String[] selectedOrganizationUnit=chart.getSelectedOrgUnits().split(",");
        String selectedSeriesOption=chart.getSeries();
        String indicatorName=null;
        //Double largestValue=0.0;
        String additionalQuery=null;
            try
            {
                if(indicatorIds !=null)
                {
                    IndicatorWarehouse indw=new IndicatorWarehouse();
                    if(selectedOrganizationUnit !=null)
                    {
                        String indicatorId=null;
                        if(selectedSeriesOption !=null && selectedSeriesOption.equalsIgnoreCase("Organization unit"))
                        {
                            indicatorId=indicatorIds[0];
                            //System.err.println("indicatorId is "+indicatorId);
                            indicatorName=indw.getIndicatorById(indicatorId).getIndicatorName();
                            filter=indicatorName;
                            for(int j=0; j<selectedOrganizationUnit.length; j++)
                            {
                                series=getOrganizationUnitName(chart.getOrgUnitLevel(),selectedOrganizationUnit[j]);
                                additionalQuery=getAdditionalQueryCriteria(chart,selectedOrganizationUnit[j]);
                                chartValueList=getChartValueListByPeriod(chart,indicatorId,indicatorName,additionalQuery,series,filter);
                                mainChartList.add(chartValueList);
                            }
                        }
                        else if(selectedSeriesOption !=null && selectedSeriesOption.equalsIgnoreCase("Indicators"))
                        {
                            String orgUnitId=selectedOrganizationUnit[0];
                            
                            filter=getOrganizationUnitName(chart.getOrgUnitLevel(),orgUnitId);
                            additionalQuery=getAdditionalQueryCriteria(chart,orgUnitId);
                            for(int j=0; j<indicatorIds.length; j++)
                            {
                                indicatorId=indicatorIds[j];
                                indicatorName=indw.getIndicatorById(indicatorId).getIndicatorName();
                                series=indicatorName;
                                chartValueList=getChartValueListByPeriod(chart,indicatorId,indicatorName,additionalQuery,series,filter);
                                mainChartList.add(chartValueList);
                            }
                        }
                    }
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        String selectedYear=" ";
        session.setAttribute("selectedChartYear", selectedYear);
        session.setAttribute("chartValueList", mainChartList);
        session.setAttribute("largestValue", largestValue);
        session.setAttribute("labelVisible", true);
        return mainChartList;
    }
    /*public String getChartValueListByPeriod(Chart chart,String indicatorId,String indicatorName,String additionalQuery,String series,String filter)
    {
        String[] selectedPeriods=chart.getSelectedPeriods().split(",");
        String periodType=chart.getPeriodType();
        String valueString="";
        String startDate=null;
        String endDate=null;
        List subList=null;
        List chartValueList=new ArrayList();
        for(int i=0; i<selectedPeriods.length; i++)
        {
            startDate=getStartDate(periodType,selectedPeriods[i]);//selectedYear+"-"+selectedPeriods[i]+"-01";
            endDate=getEndDate(periodType,selectedPeriods[i]);
            subList=getAppropriateChartValues(indicatorId,indicatorName,additionalQuery, startDate, endDate,true);
            valueString+=subList.get(0)+";";
            valueString+=series+";";
            valueString+=selectedPeriods[i]+";";
            valueString+=filter+":";
            System.err.println("valueString is "+valueString);
            //chartValueList.add(subList.get(0));
            //chartValueList.add(series);
            //chartValueList.add(selectedPeriods[i]);
            //chartValueList.add(filter);
            if(i==0)
            largestValue=Double.parseDouble(subList.get(0).toString());
        }
        return valueString;
    }
    public String getChartValueListByOrgUnit(Chart chart,String indicatorId,String indicatorName,String series,String filter,String startDate,String endDate)
    {
        String[] selectedOrganizationUnit=chart.getSelectedOrgUnits().split(",");
        List subList=null;
        String additionalQuery=" ";
        List chartValueList=new ArrayList();
        String valueString="";
        for(int i=0; i<selectedOrganizationUnit.length; i++)
        {
            additionalQuery=getAdditionalQueryCriteria(chart,selectedOrganizationUnit[i]);
            subList=getAppropriateChartValues(indicatorId,indicatorName,additionalQuery, startDate, endDate,true);
            //System.err.println("subList is "+subList.size());
            valueString+=subList.get(0)+";";
            valueString+=series+";";
            valueString+=getOrganizationUnitName(chart.getOrgUnitLevel(),selectedOrganizationUnit[i])+";";
            valueString+=filter+":";
            System.err.println("valueString is "+valueString);
            //chartValueList.add(subList.get(0));
            //chartValueList.add(series);
            //chartValueList.add(getOrganizationUnitName(chart.getOrgUnitLevel(),selectedOrganizationUnit[i]));
            //chartValueList.add(filter);
            if(i==0)
            largestValue=Double.parseDouble(subList.get(0).toString());
        }
        return valueString;
    }
    public String getChartValueListByIndicators(Chart chart,String additionalQuery,String series,String filter,String startDate,String endDate)
    {
        List subList=null;
        List chartValueList=new ArrayList();
        IndicatorWarehouse indw=new IndicatorWarehouse();
        String indicatorId=null;
        String indicatorName=null;
        String valueString="";
        String[] indicatorIds=chart.getSelectedIndicators().split(",");
        for(int i=0; i<indicatorIds.length; i++)
        {
            indicatorId=indicatorIds[i];
            indicatorName=indw.getIndicatorById(indicatorId).getIndicatorName();;
            
            subList=getAppropriateChartValues(indicatorId,indicatorName,additionalQuery, startDate, endDate,true);
            valueString+=subList.get(0)+";";
            valueString+=series+";";
            valueString+=indicatorName+";";
            valueString+=filter+":";
            //chartValueList.add(subList.get(0));
            //chartValueList.add(series);
            //chartValueList.add(indicatorName);
            //chartValueList.add(filter);
            if(i==0)
            largestValue=Double.parseDouble(subList.get(0).toString());
        }
        return valueString;
    }*/
    
    public List getChartValueListByPeriod(Chart chart,String indicatorId,String indicatorName,String additionalQuery,String series,String filter)
    {
        String[] selectedPeriods=chart.getSelectedPeriods().split(",");
        String periodType=chart.getPeriodType();
        String valueString="";
        String startDate=null;
        String endDate=null;
        List subList=null;
        List chartValueList=new ArrayList();
        for(int i=0; i<selectedPeriods.length; i++)
        {
            startDate=getStartDate(periodType,selectedPeriods[i]);//selectedYear+"-"+selectedPeriods[i]+"-01";
            endDate=getEndDate(periodType,selectedPeriods[i]);
            subList=getAppropriateChartValues(indicatorId,indicatorName,additionalQuery, startDate, endDate,true);
            
            chartValueList.add(subList.get(0));
            chartValueList.add(series);
            chartValueList.add(selectedPeriods[i]);
            chartValueList.add(filter);
            if(i==0)
            largestValue=Double.parseDouble(subList.get(0).toString());
        }
        return chartValueList;
    }
    public List getChartValueListByOrgUnit(Chart chart,String indicatorId,String indicatorName,String series,String filter,String startDate,String endDate)
    {
        String[] selectedOrganizationUnit=chart.getSelectedOrgUnits().split(",");
        List subList=null;
        String additionalQuery=" ";
        List chartValueList=new ArrayList();
        //String valueString="";
        for(int i=0; i<selectedOrganizationUnit.length; i++)
        {
            additionalQuery=getAdditionalQueryCriteria(chart,selectedOrganizationUnit[i]);
            subList=getAppropriateChartValues(indicatorId,indicatorName,additionalQuery, startDate, endDate,true);
            //System.err.println("subList is "+subList.size());
            //valueString+=subList.get(0)+";";
            //valueString+=series+";";
            //valueString+=getOrganizationUnitName(chart.getOrgUnitLevel(),selectedOrganizationUnit[i])+";";
            //valueString+=filter+":";
            //System.err.println("valueString is "+valueString);
            chartValueList.add(subList.get(0));
            chartValueList.add(series);
            chartValueList.add(getOrganizationUnitName(chart.getOrgUnitLevel(),selectedOrganizationUnit[i]));
            chartValueList.add(filter);
            if(i==0)
            largestValue=Double.parseDouble(subList.get(0).toString());
        }
        return chartValueList;
    }
    public List getChartValueListByIndicators(Chart chart,String additionalQuery,String series,String filter,String startDate,String endDate)
    {
        List subList=null;
        List chartValueList=new ArrayList();
        IndicatorWarehouse indw=new IndicatorWarehouse();
        String indicatorId=null;
        String indicatorName=null;
        String[] indicatorIds=chart.getSelectedIndicators().split(",");
        for(int i=0; i<indicatorIds.length; i++)
        {
            indicatorId=indicatorIds[i];
            indicatorName=indw.getIndicatorById(indicatorId).getIndicatorName();;
            
            subList=getAppropriateChartValues(indicatorId,indicatorName,additionalQuery, startDate, endDate,true);
            chartValueList.add(subList.get(0));
            chartValueList.add(series);
            chartValueList.add(indicatorName);
            chartValueList.add(filter);
            if(i==0)
            largestValue=Double.parseDouble(subList.get(0).toString());
        }
        return chartValueList;
    }
        
    public String getAdditionalQueryCriteria(Chart chart,String selectedOrgUnit)
    {
        String stateCode="All";
        String lgaCode="All";
        String cboCode="All";
        String communityCode="All";
        int orgUnitLevel=chart.getOrgUnitLevel();
        DaoUtil util=new DaoUtil();
        if(orgUnitLevel==2)
        stateCode=selectedOrgUnit;
        else if(orgUnitLevel==3)
        {
           stateCode=chart.getStateCode();
           lgaCode=selectedOrgUnit;
        }
        else if(orgUnitLevel==4)
        {
           stateCode=chart.getStateCode();
           lgaCode=chart.getLgaCode();
           cboCode=selectedOrgUnit;
        }
        else if(orgUnitLevel==4)
        {
           stateCode=chart.getStateCode();
           lgaCode=chart.getLgaCode();
           cboCode=chart.getCboCode();
           communityCode=selectedOrgUnit;
        }
        String[] params={stateCode,lgaCode,cboCode,communityCode};
        String additionalQuery=util.getOrgQueryCriteria(params);
        return additionalQuery;
    }
    
    public String getOrganizationUnitName(int orgUnitLevel,String orgUnitCode)
    {
        String organizationUnitName=orgUnitCode;
        if(orgUnitCode !=null)
        {
            DaoUtil util=new DaoUtil();
            if(orgUnitLevel==2)
            organizationUnitName=util.getStateName(orgUnitCode);
            else if(orgUnitLevel==3)
            organizationUnitName=util.getLgaByLgaCode(orgUnitCode).getLga_name();
            else if(orgUnitLevel==4)
            organizationUnitName=util.getOrganizationName(orgUnitCode);
            else if(orgUnitLevel==5)
            organizationUnitName=util.getWardName(orgUnitCode);
        }
        return organizationUnitName;
    }
    
  public String setChartTitle(Chart chart)
    {
        String[] selectedOrganizationUnit=chart.getSelectedOrgUnits().split(",");
        String[] indicatorIds=chart.getSelectedIndicators().split(",");
        String selectedSeriesOption=chart.getSeries();
        String chartTitle=null;
        try
        {
            if(selectedSeriesOption !=null)
            {
                if(selectedSeriesOption.equalsIgnoreCase("Organization unit"))
                {

                    if(indicatorIds !=null && indicatorIds.length>0)
                    {
                        chartTitle=indicatorIds[0];
                        
                    }
                }
                else if(selectedSeriesOption.equalsIgnoreCase("Indicators"))
                {
                    if(selectedOrganizationUnit !=null && selectedOrganizationUnit.length>0)
                    chartTitle=getOrganizationUnitName(chart.getOrgUnitLevel(),selectedOrganizationUnit[0]);//selectedOrganizationUnit[0];
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return chartTitle;//session.setAttribute("chartTitle", chartTitle);
    }
    public String getStartDate(String periodType,String period)
    {
        String startDate=null;
        try
        {
            System.err.println("periodType is "+periodType);
            if(periodType !=null)
            {
                //DaoUtil util=new DaoUtil();util
                DateManager ndate=new DateManager();
                if(periodType.equalsIgnoreCase("monthly"))
                {
                    String[] periodArray=period.split("-");
                    int mth=Integer.parseInt(ndate.getMonthAsNumber(periodArray[0]));
                    int year=Integer.parseInt(periodArray[1]);
                    startDate=ndate.getStartDate(mth, year);
                    //periodArray[1]+"-"+ndate.getMonthAsNumber(periodArray[0])+"01";
                }
                else if(periodType.equalsIgnoreCase("quarterly") || periodType.equalsIgnoreCase("sixmonthly"))
                {
                    //the format of this date is start month- end month Year (Jan-Mar 2017), so it needs to be splitted
                    String[] periodArray=period.split("-");
                    String[] endMthAndYear=periodArray[1].split(" ");
                    if(endMthAndYear !=null && endMthAndYear.length>1)
                    {
                        int startMth=Integer.parseInt(ndate.getMonthAsNumber(periodArray[0]));
                        int year=Integer.parseInt(endMthAndYear[1]);
                        startDate=ndate.getStartDate(startMth, year);
                    }
                }
                else if(periodType.equalsIgnoreCase("annually"))
                {
                    //the format of this date is Year (2017), so the returned start date should be year-12-31 (e.g 2017-12-31)
                    startDate=ndate.getStartDate(01,Integer.parseInt(period));               
                }
            }
        }
        catch(NumberFormatException nex)
        {
            System.err.println("Unable to convert month or year to number");//nex.printStackTrace();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return startDate;
    }
    public String getEndDate(String periodType,String period)
    {
        String endDate=null;
        if(periodType !=null)
        {
            //DaoUtil util=new DaoUtil();
            DateManager ndate=new DateManager(); 
            if(periodType.equalsIgnoreCase("monthly"))
            {
                //the format of this date is month-Year (Jan-2017), so it needs to be splitted
                String[] periodArray=period.split("-");
                int mth=Integer.parseInt(ndate.getMonthAsNumber(periodArray[0]));
                int year=Integer.parseInt(periodArray[1]);
                endDate=ndate.getEndDate(mth, year);
            }
            else if(periodType.equalsIgnoreCase("quarterly") || periodType.equalsIgnoreCase("sixmonthly"))
            {
                //the format of this date is start month- end month Year (Jan-Mar 2017), so it needs to be splitted
                String[] periodArray=period.split("-");
                String[] endMthAndYear=periodArray[1].split(" ");
                if(endMthAndYear !=null && endMthAndYear.length>1)
                {
                    System.err.println("endMthAndYear[0] is "+endMthAndYear[0]+" and endMthAndYear[1] is "+endMthAndYear[1]);
                    int endMth=Integer.parseInt(ndate.getMonthAsNumber(endMthAndYear[0]));
                    int year=Integer.parseInt(endMthAndYear[1]);
                    endDate=ndate.getEndDate(endMth, year);
                }
            }
            else if(periodType.equalsIgnoreCase("annually"))
            {
                //the format of this date is Year (2017), so the returned end date should be year-12-31 (e.g 2017-12-31)
                endDate=ndate.getEndDate(12,Integer.parseInt(period));               
            }
        }
        return endDate;
    }  
    
    
    
}
