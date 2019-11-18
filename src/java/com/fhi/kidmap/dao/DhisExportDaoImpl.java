/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import ImportExport.DatabaseExport;
import com.fhi.kidmap.business.DhisDataExport;
import com.fhi.kidmap.business.DhisOrgUnitLink;
import com.fhi.kidmap.business.Indicators;
import com.fhi.kidmap.report.SummaryStatisticsBean;
import com.fhi.nomis.nomisutils.DatabaseUtilities;
import com.fhi.nomis.nomisutils.NomisConstant;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author smomoh
 */
public class DhisExportDaoImpl implements DhisExportDao
{

    DaoUtil util=new DaoUtil();
    final String maleGender="male";
    final String femaleGender="female";
    DhisOrgUnitLinkDao dhisdao=new DhisOrgUnitLinkDaoImpl();
    DhisOrgUnitLink dhisLink;
    String dhisInstanceId;
    String orgUnitLevelDhisInstance;
    int orgUnitLevel=0;
    String maleCriteria=util.getGenderCriteria(maleGender);
    String femaleCriteria=util.getGenderCriteria(femaleGender);
    String positiveStatus=util.getActiveHivStatusCriteria(NomisConstant.HIV_POSITIVE,NomisConstant.OVC_TYPE);
    String negativeStatus=util.getActiveHivStatusCriteria(NomisConstant.HIV_NEGATIVE,NomisConstant.OVC_TYPE);
    String unknownStatus=util.getActiveHivStatusCriteria(NomisConstant.HIV_UNKNOWN,NomisConstant.OVC_TYPE);

    public List exportToDhis(String[] params) throws Exception
    {
        String additionalQueryCriteria1=util.getSummStatQueryCriteria(params);
        String servicePeriodQuery=" and "+util.getAdditionalServiceQuery(params[3],params[4],params[5],params[6]);
        String enrollmentPeriodQuery1=" and "+util.getAdditionalEnrollmentQuery(params[3],params[4],params[5],params[6]);
        
        dhisInstanceId=params[7];
        if(dhisInstanceId !=null && dhisInstanceId.equalsIgnoreCase("report"))
        orgUnitLevelDhisInstance="FHI";
        else
        orgUnitLevelDhisInstance=dhisInstanceId;
        orgUnitLevel=Integer.parseInt(params[8]);

        Indicators indicator=null;
        IndicatorsDao idao=new IndicatorsDaoImpl();
        List exportList=new ArrayList();
        String query=null;
        List sqlList=new ArrayList();

        List<Indicators> indicatorList=idao.getIndicatorsByDhisInstance(dhisInstanceId);


        for(int i=0; i<indicatorList.size(); i++)
        {
            indicator=indicatorList.get(i);
            query=indicator.getQuery();
            if(query !=null && !query.equalsIgnoreCase("") && !query.equalsIgnoreCase(" ") && query.length()>1)
            {
                query=query.replace("additionalQueryCriteria", additionalQueryCriteria1);
                query=query.replace("enrollmentPeriodQuery", enrollmentPeriodQuery1);
                query=query.replace("femalesql", femaleCriteria);
                query=query.replace("malesql", maleCriteria);
                query=query.replace("servicePeriodQuery", servicePeriodQuery);
                query=query.replace("positivehivstatus", positiveStatus);
                query=query.replace("negativehivstatus", negativeStatus);
                query=query.replace("unknownhivStatus", unknownStatus);
                //System.err.println("query is "+query);
                sqlList=util.execReportQuery(query);
                //exportList.addAll(populateBean(sqlList,indicator));
            }
        }

        return exportList;
    }
    public List exportToExcel(String[] params) throws Exception
    {
        List exportList=generateOVCDataByCBO(params);
        return exportList;
    }
    private String cleanQuery(String query,String additionalQueryCriteria1,String enrollmentPeriodQuery1,String servicePeriodQuery)
    {
            if(query !=null && !query.equalsIgnoreCase("") && !query.equalsIgnoreCase(" ") && query.length()>1)
            {
                query=query.replace("additionalQueryCriteria", additionalQueryCriteria1);
                query=query.replace("enrollmentPeriodQuery", enrollmentPeriodQuery1);
                query=query.replace("femalesql", femaleCriteria);
                query=query.replace("malesql", maleCriteria);
                query=query.replace("servicePeriodQuery", servicePeriodQuery);
                query=query.replace("positivehivstatus", positiveStatus);
                query=query.replace("negativehivstatus", negativeStatus);
                query=query.replace("unknownhivStatus", unknownStatus);
            }
        return query;
    }
    /*private List populateBean(List sqlList,Indicators indicator)
    {
        List beanList=new ArrayList();
        SummaryStatisticsBean stb=null;
        DhisOrgUnitAggregationLevel doal=null;
        DhisOrgUnitAggregationLevelDao dhisDao=new DhisOrgUnitAggregationLevelDaoImpl();
        DhisOrgUnit dou=null;
        DhisOrgUnitDao douDao=new DhisOrgUnitDaoImpl();
        
        for(Object obj:sqlList)
        {
            Object[] record=(Object[])obj;
            stb=new SummaryStatisticsBean();
            try
            {
               doal=(DhisOrgUnitAggregationLevel)dhisDao.getDhisOrgUnitAggregationLevel(orgUnitLevel, (String)record[0], orgUnitLevelDhisInstance);
               //state=(States)sdao.getStateByStateCode((String)record[0]);
               if(doal==null)
                continue;
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
            String dhisId=doal.getDhisId();
            String cboName=" ",lgaName=" ",stateName=" ";
            if(dhisId==null || dhisId.length()<1)
            continue;
            try
            {
                dou=(DhisOrgUnit)douDao.getDhisOrgUnit(dhisId);
                if(dou !=null)
                {
                    cboName=dou.getOrgUnitName();
                    dou=(DhisOrgUnit)douDao.getDhisOrgUnit(dou.getParentId(),dou.getDhisInstance(),(dou.getOrgUnitLevel()-1));
                    if(dou !=null)
                    {
                        lgaName=dou.getOrgUnitName();
                        dou=(DhisOrgUnit)douDao.getDhisOrgUnit(dou.getParentId(),dou.getDhisInstance(),(dou.getOrgUnitLevel()-1));
                        {
                            if(dou !=null)
                            stateName=dou.getOrgUnitName();
                        }
                    }
                }               
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
            stb.setCboCode(dhisId);
            stb.setCbo(cboName);
            stb.setLga(lgaName);
            stb.setState(stateName);
            stb.setIndicatorId(indicator.getIndicatorId());
            stb.setIndicatorName(indicator.getIndicatorName());
            stb.setCategoryOptionCombo(indicator.getCategoryOptionCombo());
            stb.setMonth((Integer)record[1]+"");
            stb.setYear((Integer)record[2]+"");
            stb.setDataset(indicator.getIndicatorType());
            stb.setNoOfOvc(((Long)record[3]).intValue());
            beanList.add(stb);
            
        }
        return beanList;
    }*/
    private List populateBeanForExcelOutput(List sqlList,Indicators indicator)
    {
        List beanList=new ArrayList();
        SummaryStatisticsBean stb=null;
        DhisOrgUnitLinkDao doulDao=new DhisOrgUnitLinkDaoImpl();
        
        DhisOrgUnitLink doul=new DhisOrgUnitLink();
        String dhisId=null;
        try
        {
            for(Object obj:sqlList)
            {
                Object[] record=(Object[])obj;
                stb=new SummaryStatisticsBean();
                String cboName=" ",lgaName=" ",stateName=" ";
                stateName=util.getStateName((String)record[0]);
                lgaName=util.getLgaName((String)record[1]);
                doul=doulDao.getDhisOrgUnitLink((String)record[2]);
                if(doul !=null)
                dhisId=doul.getDhisId();
                cboName=util.getOrganizationName((String)record[2]);
                stb.setCboCode(dhisId);
                stb.setCbo(cboName);
                stb.setLga(lgaName);
                stb.setState(stateName);
                stb.setIndicatorId(indicator.getIndicatorId());
                stb.setIndicatorName(indicator.getIndicatorName());
                stb.setCategoryOptionCombo(indicator.getCategoryOptionCombo());
                stb.setMonth((Integer)record[3]+"");
                stb.setYear((Integer)record[4]+"");
                stb.setDataset(indicator.getIndicatorType());
                stb.setNoOfOvc(((Long)record[5]).intValue());
                beanList.add(stb);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        return beanList;
    }
    public List generateOVCDataByCBO(String[] params) throws Exception
    {
        String additionalQueryCriteria1=util.getSummStatQueryCriteria(params);
        String servicePeriodQuery=" and "+util.getAdditionalServiceQuery(params[3],params[4],params[5],params[6]);
        String enrollmentPeriodQuery1=" and "+util.getAdditionalEnrollmentQuery(params[3],params[4],params[5],params[6]);
        dhisInstanceId="FHI";
        //orgUnitLevel=Integer.parseInt(params[8]);
        Indicators indicator=null;
        IndicatorsDao idao=new IndicatorsDaoImpl();
        List exportList=new ArrayList();
        String query=null;
        List sqlList=new ArrayList();

        List<Indicators> indicatorList=idao.getIndicatorsByDhisInstance(dhisInstanceId);
        //System.err.println("indicatorList size is "+indicatorList.size());
        for(int i=0; i<indicatorList.size(); i++)
        {
            indicator=indicatorList.get(i);
            query=indicator.getQuery();
            String queryPart="ovc.state,ovc.lga,ovc.completedbyCbo,";

            if(query !=null && !query.equalsIgnoreCase("") && !query.equalsIgnoreCase(" ") && query.length()>1)
            {
                query=cleanQuery(query,additionalQueryCriteria1,enrollmentPeriodQuery1,servicePeriodQuery);
                query=query.replace("ovc.state,", queryPart);
                //System.err.println("query is "+query);
                sqlList=util.execReportQuery(query);
                //System.err.println("sqlList size is "+sqlList.size());
                exportList.addAll(populateBeanForExcelOutput(sqlList,indicator));
            }
        }
        return exportList;
    }
    public void createDhisExportFile(String dhisInstance) throws Exception
    {
        DhisDataExportDao ddeDao=new DhisDataExportDaoImpl();
        List mainList=new ArrayList();
        List subList=new ArrayList();
        DatabaseExport dbe=new DatabaseExport();
        SummaryStatisticsBean stb=null;
        String startDate=null;
        String[] startDateArray=null;
        String exportFileName=dhisInstance+"Dhisexport";
        Object[] objArray=null;
        String user="sidhas";
        String fileName=exportFileName;
        String orgUnitId=null;
        int start=0;
        int end=0;
        int count=0;
        int exportSize=10000;
        
        try
        {
            List orgUnitIdList=ddeDao.getDistinctOrgUnitIdsFromDhisDataExport(dhisInstance);
            //System.err.println("orgUnitIdList size is "+orgUnitIdList.size());
            for(int i=0; i<orgUnitIdList.size(); i++)
            {
                orgUnitId=(String)orgUnitIdList.get(i); //objArray[0].toString();
                List dhisDataExportList=ddeDao.getNonDuplicateRecordsFromDhisDataExportForOVC(orgUnitId, dhisInstance);
                for(int j=0; j<dhisDataExportList.size(); j++)
                {
                    objArray=(Object[])dhisDataExportList.get(j);
                    System.err.println("record number is "+count);
                    stb=new SummaryStatisticsBean();
                    startDate=objArray[3].toString();
                    if(startDate ==null || startDate.indexOf("-") ==-1 )
                    continue;
                    startDateArray=startDate.split("-");
                    stb.setIndicatorId(objArray[1].toString());
                    stb.setMonth(startDateArray[1]);
                    stb.setYear(startDateArray[0]);
                    stb.setCboCode(objArray[0].toString());
                    stb.setCategoryOptionCombo(objArray[2].toString());
                    stb.setNoOfOvc(Integer.parseInt(objArray[4].toString()));
                    subList.add(stb);
                    if(subList.size()==exportSize)
                    {
                        mainList.add(subList);
                        subList=new ArrayList();
                    }
                    else if(i==orgUnitIdList.size()-1 && j==dhisDataExportList.size()-1)
                    {
                        mainList.add(subList);
                    }
                    count++;
                 }
            }
            List list=null;

            for(int k=0; k<mainList.size(); k++)
            {
               start=(k*exportSize)+1;
               list=(List)mainList.get(k);
               end=(list.size()+(k*exportSize));
               exportFileName=fileName+start+"-"+end;
               dbe.exportToDhisInXml(list, user, exportFileName);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void saveOvcDataInDde(String[] params,String dhisInstanceId)
    {

        DhisDataExport dde=null;
        SummaryStatisticsBean stb=null;
        DhisDataExportDao ddeDao=new DhisDataExportDaoImpl();
        DatabaseUtilities dbUtils=new DatabaseUtilities();
            try
            {
                dbUtils.dropDhisDataExportTable();
                dbUtils.createDhisDataExportTable(false);
                List list=generateOVCDataByCBO(params);
                //System.err.println("list in generateOVCDataByCBO(params) is "+list.size());
                for(Object obj:list)
                {
                    stb=(SummaryStatisticsBean)obj;
                    dde=new DhisDataExport();
                    dde.setCategoryOptionComboId(stb.getCategoryOptionCombo());
                    dde.setDataElementId(stb.getIndicatorId());
                    dde.setDhisInstance(dhisInstanceId);
                    dde.setOrgUnitId(stb.getCboCode());
                    dde.setDataElementName(stb.getIndicatorName());
                    dde.setStartDate(stb.getYear()+"-"+stb.getMonth()+"-01");
                    dde.setOrgUnitName(stb.getCbo());
                    dde.setLga(stb.getLga());
                    dde.setState(stb.getState());
                    dde.setValue(stb.getNoOfOvc());
                    ddeDao.saveDhisDataExport(dde);
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
    }
    public List getOvcDataInDde()
    {
        DhisDataExportDao ddeDao=new DhisDataExportDaoImpl();
        List list=null;
            try
            {
                list=ddeDao.getDhisDataExports();
                //System.err.println("list in generateOVCDataByCBO(params) is "+list.size());  
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        return list;
    }
}

