/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report;

import com.fhi.kidmap.business.OvcService;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.OvcServiceDao;
import com.fhi.kidmap.dao.OvcServiceDaoImpl;
import com.fhi.kidmap.dao.OvcWithdrawalDao;
import com.fhi.kidmap.dao.OvcWithdrawalDaoImpl;
import com.fhi.nomis.nomisutils.NomisConstant;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Siaka
 */
public class MonthlySummaryFormReport implements Serializable
{
    private final String maleGender="male";
    private final String femaleGender="female";
    public final String FINER_AGE_DISAGGREGATION="finerAgeDisaggregation";
    String hviMaleGender="Male";
    String hviFemaleGender="Female";
    String hhQueryPart=null;
    String hhOvcAndServiceQueryPart=null;
    String hhOvcAndServiceByOvcIdQueryPart=null;
    String countQueryPart=null;
    String serviceCountQueryPart=null;
    String serviceCountHivQueryPart=null;
    String hheCaregiverHhsQuery=null;
    String hheServiceCountQuery=null;
    String ageQuery;
    private int[] ageSegregation=null;
    int[] householdAgeSegregation={0,17,18,59,60,200};
    
    DaoUtil util=new DaoUtil();
    ReportUtility rutil=new ReportUtility();
    OvcServiceDao serviceDao=new OvcServiceDaoImpl();
    List maleGreaterThanThreeList=new ArrayList();
    List maleLessThanThreeList=new ArrayList();
    List femaleGreaterThanThreeList=new ArrayList();
    List femaleLessThanThreeList=new ArrayList();
    OvcWithdrawalDao withdrawalDao=new OvcWithdrawalDaoImpl();

    public MonthlySummaryFormReport()
    {
        hhQueryPart=rutil.getHhOvcQueryPart();//.getHouseholdQueryPart()+"Ovc ovc where hhe.hhUniqueId=ovc.hhUniqueId and ";
        countQueryPart=rutil.getOvcCountQueryPart();//"select count(distinct ovc.ovcId)"+util.getHouseholdQueryPart()+"Ovc ovc where hhe.hhUniqueId=ovc.hhUniqueId and ";
        serviceCountQueryPart=rutil.getServiceCountQueryPart();//"select count(distinct ovc.ovcId)"+util.getHouseholdQueryPart()+"Ovc ovc, OvcService service where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=service.ovcId and " ;
        hhOvcAndServiceQueryPart=rutil.getHhOvcAndServiceQueryPart();//util.getHouseholdQueryPart()+"Ovc ovc,OvcService service where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=service.ovcId) ";
        serviceCountHivQueryPart=util.getHouseholdHIVOvcServiceReportQueryPart("OvcService");
        hhOvcAndServiceByOvcIdQueryPart=rutil.getHhOvcAndServiceByOvcIdQueryPart();//"select distinct service.ovcId "+util.getHouseholdQueryPart()+"Ovc ovc,OvcService service where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=service.ovcId) ";
        hheCaregiverHhsQuery=rutil.getHheCaregiverHhsQuery();//"select count(distinct hhs.caregiverId) from HouseholdService hhs, HouseholdEnrollment hhe,Caregiver cgiver where (hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.hhUniqueId=hhs.hhUniqueId)";
        hheServiceCountQuery=rutil.getHheServiceCountQuery();//"select count(distinct hhs.hhUniqueId) from HouseholdService hhs, HouseholdEnrollment hhe where (hhe.hhUniqueId=hhs.hhUniqueId)";
    }
    public List execReportQuery(String sql)
    {
        List list=new ArrayList();
        list=util.execReportQuery(sql);
        return list;
    }
    public int[] getNewAgeSegregation()
    {
        return rutil.getNewAgeSegregation();
    }
    public int[] getNewFinerAgeSegregation()
    {
        return rutil.getNewFinerAgeSegregation();
        /*int[] ageSegregation={0,0,1,4,5,9,10,14,15,17,18,24,25,100};
        return ageSegregation;*/
    }
    public int[] getAgeSegregation()
    {
        return rutil.getAgeSegregation();
        /*int[] ageSegregation={0,5,6,12,13,17};
        return ageSegregation;*/
    }
    public int[] getHouseholdAgeSegregation()
    {
        return householdAgeSegregation;
    }
    public int[] setHouseholdAgeSegregation(int[] ageSegregation)
    {
        householdAgeSegregation=ageSegregation;
        return householdAgeSegregation;
    }
    public int[] getSecondHouseholdAgeSegregation()
    {
        return rutil.getSecondHouseholdAgeSegregation();
        /*int[] ageSegregation={0,17,18,24,25,200};
        return ageSegregation;*/
    }
    public void setAgeSegregation(int[] ageSegregation)
    {
        this.ageSegregation=ageSegregation;
    }
    
    public List getNoOfCSOReportingOVCServicesForTheMth(String[] params) throws Exception
    {
        List maleList=new ArrayList();

        String additionalQueryCriteria=util.getOrgQueryCriteria(params);
        String[] dateParams={params[4],params[5],params[6],params[7]};
        String[] dateArray={util.getStartDate(dateParams),util.getEndDate(dateParams)};

        String malesql="select count(distinct hhe.orgCode) "+hhOvcAndServiceQueryPart+" and ovc.ovcId is not null "+additionalQueryCriteria+" and service.servicedate between '"+dateArray[0]+"' and '"+dateArray[1]+"'";
        maleList.add("No of <b>CSO</b> reporting VC services in the reporting period");
        List resultList=new ArrayList();
        resultList=execReportQuery(malesql);
        maleList.add(resultList.get(0));
        //System.err.println("No of CBO reporting is "+resultList.get(0));
        return maleList;
    }
    public List getNoOfLGAReportingOVCServicesForTheMth(String[] params) throws Exception
    {
        List maleList=new ArrayList();
        String additionalQueryCriteria=util.getOrgQueryCriteria(params);
        String[] dateParams={params[4],params[5],params[6],params[7]};
        String[] dateArray={util.getStartDate(dateParams),util.getEndDate(dateParams)};
        String malesql="select count(distinct hhe.lgaCode) "+hhOvcAndServiceQueryPart+" and ovc.ovcId is not null "+additionalQueryCriteria+" and service.servicedate between '"+dateArray[0]+"' and '"+dateArray[1]+"'";// or ovc.ovcId in (select ovc.ovcId from Ovc ovc where  ovc.dateEnrollment between '"+dateArray[0]+"' and '"+dateArray[1]+"' )";

        maleList.add("No of <b>LGAs</b> reporting VC services in the reporting period");
        List resultList=new ArrayList();
        resultList=execReportQuery(malesql);
        maleList.add(resultList.get(0));

        return maleList;
    }
    public List getNoOfStateReportingOVCServicesForTheMth(String[] params) throws Exception
    {
        List maleList=new ArrayList();
        String additionalQueryCriteria=util.getOrgQueryCriteria(params);
        String[] dateParams={params[4],params[5],params[6],params[7]};
        String[] dateArray={util.getStartDate(dateParams),util.getEndDate(dateParams)};
        String malesql="select count(distinct hhe.stateCode)"+hhOvcAndServiceQueryPart+" and ovc.ovcId is not null "+additionalQueryCriteria+" and   service.servicedate between '"+dateArray[0]+"' and '"+dateArray[1]+"'";// or ovc.ovcId in (select ovc.ovcId from Ovc ovc where  ovc.dateEnrollment between '"+dateArray[0]+"' and '"+dateArray[1]+"' )";
        //System.err.println("State count query is "+malesql);
        maleList.add("No of <b>States</b> reporting VC services in the reporting period");
        List resultList=new ArrayList();
        resultList=execReportQuery(malesql);
        maleList.add(resultList.get(0));
        //System.err.println("No of State reporting is "+resultList.get(0));
        return maleList;
    }
    private String getAdditionalEnrollmentQuery(String additionalEnrollmentQuery)
    {
        return util.getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
    }
    private String getAdditionalServiceQuery(String additionalServiceQuery)
    {
        return util.getAdditionalServiceQuery(additionalServiceQuery);
    }
    public List getOvcMthlySummaryData(String[] params,String ageSegType) throws Exception
    {
        List mainList=new ArrayList();
        List list=new ArrayList();
        List list2=new ArrayList();
        List list3=new ArrayList();
        String additionalQueryCriteria=util.getHVIOrgUnitQuery(params);//util.getOrgQueryCriteria(params);
        
        String[] dateParams={params[4],params[5],params[6],params[7]};
        String[] dateArray={util.getStartDate(dateParams),util.getEndDate(dateParams)};
        String enrollmentEndDateQuery=util.getEnrollmentEndDateQuery(dateParams);
        if(ageSegType.equalsIgnoreCase("new"))
        ageSegregation=getNewFinerAgeSegregation();//getNewAgeSegregation();
        else
        ageSegregation=getAgeSegregation();
        list.add(getNoOfOvcNewlyEnrolled("No of <b>new VC</b> enrolled",additionalQueryCriteria,dateArray));
        list.add(getNoOfOvcCurrentlyEnrolled("No of <b>VC currently</b> enrolled",additionalQueryCriteria,enrollmentEndDateQuery));
        list.add(getNoOfOvcServedForMSF("Number of OVC provided with atleast one service",additionalQueryCriteria,dateArray,ageSegType));
        list.add(getNoOfOvcSupportedOnHIVAIDSServicesForMSF("Number of active VC beneficiaries receiving support to access HIV services",additionalQueryCriteria,dateArray));
        list.add(getNoOfOvcWithdrawn("Number of <b>VC Withdrawn</b> from the program","All",additionalQueryCriteria," "," ",null));
        list.add(getNoOfOvcOutOfProgram("<b>No of <b>VC known to have died",additionalQueryCriteria," ", "Known death"));
        list.add(getNoOfOvcOutOfProgram("<b>No of <b>VC who have migrated</b>",additionalQueryCriteria," ", "Migrated"));
        list.add(getNoOfOvcOutOfProgram("<b>No of <b>VC lost to followup</b>",additionalQueryCriteria," ", "Loss to follow-up"));
        list2.add(getNoOfOvcAbove18YearsWhoGraduated("Number of <b>VC graduated</b> from the program (18 and above)","All",additionalQueryCriteria," "," ",null));
        list.addAll(getOvcServedByNoOfServices("No of VC that received three or more services","No of VC that received less than three services",additionalQueryCriteria,dateParams));

        list3.add(getNoOfOvcAccessingHealthCare("Number of <b>VC</b> accessing <b>Health care</b> services",additionalQueryCriteria,dateArray));
        list3.add(getNoOfOvcAccessingNutritional("Number of <b>VC</b> accessing <b>Nutritional</b> services",additionalQueryCriteria,dateArray));
        list3.add(getNoOfOvcAccessingShelter("Number of <b>VC</b> accessing <b>Shelter</b> services",additionalQueryCriteria,dateArray));
        list3.add(getNoOfOvcAccessingEducational("Number of <b>VC</b> accessing <b>Education</b> services",additionalQueryCriteria,dateArray));
        list3.add(getNoOfOvcAccessingPsychoSupport("Number of <b>VC</b> accessing <b>Psychosocial support</b> services",additionalQueryCriteria,dateArray));
        list3.add(getNoOfOvcAccessingProtection("Number of <b>VC</b> accessing <b>Protection</b> services",additionalQueryCriteria,dateArray));

        mainList.add(list);
        mainList.add(list2);
        mainList.add(list3);
        return mainList;
    }
    public List getOvcMthlyEconStrengtheningData(String[] params, String ageSegType) throws Exception
    {
        List list=new ArrayList();
        List labelList=new ArrayList();
        List mainList=new ArrayList();
        String additionalQueryCriteria=util.getHVIOrgUnitQuery(params);
        //String[] hviParams={params[0],params[1],params[2],params[3],null,null,params[4],params[5],params[6],params[7],params[15]};
        //String additionalQueryCriteria=util.getQueryCriteria(params);
        String[] dateParams={params[4],params[5],params[6],params[7]};
        String[] dateArray={util.getStartDate(dateParams),util.getEndDate(dateParams)};
        String[] hhAgeDisaggregationLabel={" ","0-17","18-59",">60","Total(M)","0-17","18-59",">60","Total(F)","Grand Total"};
        for(int i=0; i<hhAgeDisaggregationLabel.length; i++)
        {
            labelList.add("<b>"+hhAgeDisaggregationLabel[i]+"</b>");
        }
        list.addAll(getNoOfOvcAccessingEconStrengthening("Number of <b>VC</b> accessing <b>Economic Strengthening</b> services",additionalQueryCriteria,dateArray,ageSegType));
        //additionalQueryCriteria=util.getHVIOrgUnitQuery(hviParams);
        mainList.add(list);

        if(!ageSegType.equalsIgnoreCase("new"))
        {
            mainList.add(labelList);
            mainList.add(getNoOfCaregiversAndHouseholdHeadsProvidedWithAtleastOneService("Number of <b>caregivers/household heads</b> provided with at least one service",additionalQueryCriteria,dateArray));
            mainList.add(getNoOfHouseholdsSupportedToAccessHIVCare("Number of caregivers/household heads supported to access HIV services",additionalQueryCriteria,dateArray));
            mainList.add(getNoOfHouseholdHeadsReceivingEconomicStrenghtening("Number of caregivers/Household heads receiving Economic Strengthening",additionalQueryCriteria,dateArray));
        }
        //if(ageSegType.equalsIgnoreCase("new"))
        mainList.add(getNoOfHouseholdsReceivingEconomicStrenghtening("Number of Households provided Economic Strengthening",additionalQueryCriteria,dateArray,ageSegType));
        return mainList;
    }
    public String[] getHVIParams(String[] params)
    {
        String[] hviParams={params[0],params[1],params[2],null,null,null,params[7], params[8], params[9],params[10],params[18],params[19]};
        return hviParams;
    }
    public void setNoOfServices(String additionalQueryCriteria,String additionalServiceQuery,String ageQuery) throws Exception
    {
        maleLessThanThreeList.clear();
        maleGreaterThanThreeList.clear();
        femaleLessThanThreeList.clear();
        femaleGreaterThanThreeList.clear();
        System.err.println("setting number of services in Monthly summary form");
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        List uniqueOvcIdList=new ArrayList();
        int numberOfServices=0;
        if(!additionalServiceQuery.equals(" "))
            additionalServiceQuery=" and "+additionalServiceQuery;

        String malesql=hhOvcAndServiceByOvcIdQueryPart+" and "+util.getGenderCriteria(maleGender)+additionalQueryCriteria+additionalServiceQuery+ageQuery;
        String femalesql=hhOvcAndServiceByOvcIdQueryPart+" and "+util.getGenderCriteria(femaleGender)+additionalQueryCriteria+additionalServiceQuery+ageQuery;
        System.err.println("malesql in setNoOfServices is "+malesql);
        uniqueOvcIdList=execReportQuery(malesql);
        if(uniqueOvcIdList !=null && !uniqueOvcIdList.isEmpty())
        {
            String ovcId=null;
            OvcService service=null;

            for(Object s: uniqueOvcIdList)
            {
                ovcId=(String)s;
                maleList=serviceDao.getOvcServicesByOvcIdAndAdditionalServiceQueryByNumberOfServices(ovcId, additionalServiceQuery);
                if(maleList !=null && !maleList.isEmpty())
                {
                    service=(OvcService)maleList.get(0);
                    numberOfServices=serviceDao.getNumberOfServicesPerServiceRecord(service);
                    if(numberOfServices >2)
                    maleGreaterThanThreeList.add(service.getOvcId());
                    else if(numberOfServices >0 && numberOfServices <3)
                    maleLessThanThreeList.add(service.getOvcId());
                }
              }
        }
        uniqueOvcIdList=execReportQuery(femalesql);
        if(uniqueOvcIdList !=null && !uniqueOvcIdList.isEmpty())
        {
            String ovcId=null;
            OvcService service=null;
            for(Object s: uniqueOvcIdList)
            {
                ovcId=(String)s;
                femaleList=serviceDao.getOvcServicesByOvcIdAndAdditionalServiceQueryByNumberOfServices(ovcId, additionalServiceQuery);
                service=(OvcService)femaleList.get(0);
                numberOfServices=serviceDao.getNumberOfServicesPerServiceRecord(service);
                if(numberOfServices >2)
                femaleGreaterThanThreeList.add(service.getOvcId());
                else if(numberOfServices >0 && numberOfServices <3)
                femaleLessThanThreeList.add(service.getOvcId());
            }
        }
        System.err.println("Number of OVC Services generated");
    }
    public List getOvcServedByNoOfServices(String greaterThanThreeIndicator,String lessThanThreeIndicator,String additionalQueryCriteria,String[] dateParams) throws Exception
    {
        List mainlist=new ArrayList();
        List greaterThanThreeList=new ArrayList();
        List lessThanThreeList=new ArrayList();
        List maleMoreThanThreeServicesList=new ArrayList();
        List femaleMoreThanThreeServicesList=new ArrayList();
        List maleLessThanThreeServicesList=new ArrayList();
        List femaleLessThanThreeServicesList=new ArrayList();
        int maleLessThanThreeTotal=0;
        int femaleLessThanThreeTotal=0;
        int maleGreaterThanThreeTotal=0;
        int femaleGreaterThanThreeTotal=0;
        int count=0;
        String ageQuery=" ";
        for(int i=0; i<ageSegregation.length; i+=2)
        {
            ageQuery=util.getAgeCriteria(ageSegregation[i]+"", ageSegregation[i+1]+"");
            String additionalServiceQuery= util.getAdditionalServiceQuery(dateParams[0], dateParams[1], dateParams[2], dateParams[3]);
            setNoOfServices(additionalQueryCriteria,additionalServiceQuery,ageQuery);

            maleGreaterThanThreeTotal+=maleGreaterThanThreeList.size();
            femaleGreaterThanThreeTotal+=femaleGreaterThanThreeList.size();
            maleLessThanThreeTotal+=maleLessThanThreeList.size();
            femaleLessThanThreeTotal+=femaleLessThanThreeList.size();

            maleMoreThanThreeServicesList.add(maleGreaterThanThreeList.size());
            femaleMoreThanThreeServicesList.add(femaleGreaterThanThreeList.size());
            maleLessThanThreeServicesList.add(maleLessThanThreeList.size());
            femaleLessThanThreeServicesList.add(femaleLessThanThreeList.size());
        }
        maleMoreThanThreeServicesList.add(maleGreaterThanThreeTotal);
        femaleMoreThanThreeServicesList.add(femaleGreaterThanThreeTotal);
        maleLessThanThreeServicesList.add(maleLessThanThreeTotal);
        femaleLessThanThreeServicesList.add(femaleLessThanThreeTotal);
        greaterThanThreeList.add(greaterThanThreeIndicator);
        //greaterThanThreeList.add("No of VC that received three or more services");
        greaterThanThreeList.addAll(maleMoreThanThreeServicesList);
        greaterThanThreeList.addAll(femaleMoreThanThreeServicesList);
        greaterThanThreeList.add((maleGreaterThanThreeTotal+femaleGreaterThanThreeTotal));

        lessThanThreeList.add(lessThanThreeIndicator);
        lessThanThreeList.addAll(maleLessThanThreeServicesList);
        lessThanThreeList.addAll(femaleLessThanThreeServicesList);
        lessThanThreeList.add(maleLessThanThreeTotal+femaleLessThanThreeTotal);
        mainlist.add(greaterThanThreeList);
        mainlist.add(lessThanThreeList);
        return mainlist;
    }
    public List getNoOfOvcNewlyEnrolled(String indicator,String additionalQueryCriteria,String[] dateArray) throws Exception
    {
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        int maleTotal=0;
        int femaleTotal=0;
        int count=0;
        String ageQuery=" ";
        for(int i=0; i<ageSegregation.length; i+=2)
        {
            ageQuery=util.getAgeCriteria(ageSegregation[i]+"", ageSegregation[i+1]+"");
            String queryPart= additionalQueryCriteria+ageQuery+" and ovc.dateEnrollment between '"+dateArray[0]+"' and '"+dateArray[1]+"' ";
            String malesql=countQueryPart+util.getGenderCriteria(maleGender)+" "+queryPart;
            String femalesql=countQueryPart+util.getGenderCriteria(femaleGender)+" "+queryPart;
            count=(Integer.parseInt(execReportQuery(malesql).get(0).toString()));
            maleList.add(count);
            maleTotal+=count;
            count=(Integer.parseInt(execReportQuery(femalesql).get(0).toString()));
            femaleList.add(count);
            femaleTotal+=count;
            //System.err.println("malesql is "+malesql);
        }
        maleList.add(maleTotal);
        femaleList.add(femaleTotal);
        mainlist.add(indicator);
        //mainlist.add("No of <b>new VC</b> enrolled");
        mainlist.addAll(maleList);
        mainlist.addAll(femaleList);
        int grandTotal=maleTotal+femaleTotal;
        mainlist.add(grandTotal);
        return mainlist;
    }
    public List getNoOfOvcCurrentlyEnrolled(String indicator,String additionalQueryCriteria,String enrollmentEndDateQuery) throws Exception
    {
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        int maleCount=0;
        int femaleCount=0;
        int maleTotal=0;
        int femaleTotal=0;
        int count=0;
        int maleWithdrawn=0;
        int femaleWithdrawn=0;
        List maleWithdrawnList=null;

        List femaleWithdrawnList=null;

        /*String dateQuery="";
        if(dateArray !=null)
        dateQuery=" and ovc.dateEnrollment <= '"+dateArray[1]+"'";// and '"+dateArray[1]+"' ";*/
        for(int i=0; i<ageSegregation.length; i+=2)
        {
            ageQuery=util.getAgeCriteria(ageSegregation[i]+"", ageSegregation[i+1]+"");
            String queryPart= ageQuery+" and "+enrollmentEndDateQuery+additionalQueryCriteria;//+" and ovc.ovcId not in (select withdrawal.ovcId from OvcWithdrawal withdrawal)";
            String malesql=countQueryPart+util.getGenderCriteria(maleGender)+" "+queryPart;
            String femalesql=countQueryPart+util.getGenderCriteria(femaleGender)+" "+queryPart;
            count=(Integer.parseInt(execReportQuery(malesql).get(0).toString()));

            maleWithdrawnList=withdrawalDao.getListOfOvcWithdrawn(queryPart+" and "+util.getGenderCriteria(maleGender));
            if(maleWithdrawnList !=null)
            maleWithdrawn=maleWithdrawnList.size();
            maleCount=count-maleWithdrawn;
            
            maleList.add(maleCount);
            maleTotal+=maleCount;
            count=(Integer.parseInt(execReportQuery(femalesql).get(0).toString()));
            femaleWithdrawnList=withdrawalDao.getListOfOvcWithdrawn(queryPart+" and "+util.getGenderCriteria(femaleGender));
            if(femaleWithdrawnList !=null)
            femaleWithdrawn=femaleWithdrawnList.size();
            femaleCount=count-femaleWithdrawn;
            femaleList.add(femaleCount);
            femaleTotal+=femaleCount;
        }
        maleList.add(maleTotal);
        femaleList.add(femaleTotal);
        mainlist.add(indicator);
        //mainlist.add("No of <b>VC currently</b> enrolled");
        mainlist.addAll(maleList);
        mainlist.addAll(femaleList);
        int grandTotal=maleTotal+femaleTotal;
        mainlist.add(grandTotal);
        return mainlist;
    }

    public List getNoOfOvcServedForMSF(String indicator,String additionalQueryCriteria,String[] dateArray,String ageSegType) throws Exception
    {
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        String serviceCriteria=" ";
        int maleTotal=0;
        int femaleTotal=0;
        int count=0;
        for(int i=0; i<ageSegregation.length; i+=2)
        {
            ageQuery=util.getAgeCriteria(ageSegregation[i]+"", ageSegregation[i+1]+"");
            serviceCriteria=" and service.servicedate between '"+dateArray[0]+"' and '"+dateArray[1]+"' )";
            String malesql=serviceCountQueryPart+util.getGenderCriteria(maleGender)+ageQuery+" "+additionalQueryCriteria+serviceCriteria;
            String femalesql=serviceCountQueryPart+util.getGenderCriteria(femaleGender)+ageQuery+" "+additionalQueryCriteria+serviceCriteria;
            count=(Integer.parseInt(execReportQuery(malesql).get(0).toString()));
            maleList.add(count);
            maleTotal+=count;
            count=(Integer.parseInt(execReportQuery(femalesql).get(0).toString()));
            femaleList.add(count);
            femaleTotal+=count;
        }
        maleList.add(maleTotal);
        femaleList.add(femaleTotal);
        mainlist.add(indicator);
        mainlist.addAll(maleList);
        mainlist.addAll(femaleList);
        int grandTotal=maleTotal+femaleTotal;
        mainlist.add(grandTotal);
        return mainlist;
    }
    public List getOvc_ServActive(String indicator,String additionalQueryCriteria,String[] dateArray,String ageSegType) throws Exception
    {
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        int[] newAgeSegregation=ageSegregation;
        if(ageSegType !=null && ageSegType.equalsIgnoreCase(FINER_AGE_DISAGGREGATION))
        newAgeSegregation=getNewFinerAgeSegregation();
        String serviceCriteria=" ";
        int maleTotal=0;
        int femaleTotal=0;
        int count=0;
        String currentlyQuery=util.getOvcWithdrawnFromProgramQuery("No");
        additionalQueryCriteria=additionalQueryCriteria+currentlyQuery;
        
        for(int i=0; i<newAgeSegregation.length; i+=2)
        {
            ageQuery=util.getAgeCriteria(newAgeSegregation[i]+"", newAgeSegregation[i+1]+"");
            serviceCriteria=" and service.servicedate between '"+dateArray[0]+"' and '"+dateArray[1]+"' )";
            String malesql=serviceCountQueryPart+util.getGenderCriteria(maleGender)+ageQuery+" "+additionalQueryCriteria+serviceCriteria;
            String femalesql=serviceCountQueryPart+util.getGenderCriteria(femaleGender)+ageQuery+" "+additionalQueryCriteria+serviceCriteria;
            count=(Integer.parseInt(execReportQuery(malesql).get(0).toString()));
            maleList.add(count);
            maleTotal+=count;
            count=(Integer.parseInt(execReportQuery(femalesql).get(0).toString()));
            femaleList.add(count);
            femaleTotal+=count;
            System.err.println("malesql in getOvc_ServActive is "+malesql);
        }
        maleList.add(maleTotal);
        femaleList.add(femaleTotal);
        mainlist.add(indicator);
        mainlist.add(maleList);
        mainlist.add(femaleList);
        int grandTotal=maleTotal+femaleTotal;
        mainlist.add(grandTotal);
        return mainlist;
    }
    public List getNoOfOvcServed(String indicator,String additionalQueryCriteria,String[] dateArray,String ageSegType) throws Exception
    {
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        int[] newAgeSegregation=ageSegregation;
        if(ageSegType !=null && ageSegType.equalsIgnoreCase(FINER_AGE_DISAGGREGATION))
        newAgeSegregation=getNewFinerAgeSegregation();
        String serviceCriteria=" ";
        int maleTotal=0;
        int femaleTotal=0;
        int count=0;
        for(int i=0; i<newAgeSegregation.length; i+=2)
        {
            ageQuery=util.getAgeCriteria(newAgeSegregation[i]+"", newAgeSegregation[i+1]+"");
            serviceCriteria=" and service.servicedate between '"+dateArray[0]+"' and '"+dateArray[1]+"' )";
            String malesql=serviceCountQueryPart+util.getGenderCriteria(maleGender)+ageQuery+" "+additionalQueryCriteria+serviceCriteria;
            String femalesql=serviceCountQueryPart+util.getGenderCriteria(femaleGender)+ageQuery+" "+additionalQueryCriteria+serviceCriteria;
            count=(Integer.parseInt(execReportQuery(malesql).get(0).toString()));
            maleList.add(count);
            maleTotal+=count;
            count=(Integer.parseInt(execReportQuery(femalesql).get(0).toString()));
            femaleList.add(count);
            femaleTotal+=count;
        }
        maleList.add(maleTotal);
        femaleList.add(femaleTotal);
        mainlist.add(indicator);
        mainlist.add(maleList);
        mainlist.add(femaleList);
        int grandTotal=maleTotal+femaleTotal;
        mainlist.add(grandTotal);
        return mainlist;
    }
    public List getNoOfActiveBeneficiariesServed(String indicator,List ovcServedList,List caregiverServedList) throws Exception
    {
        List mainlist=new ArrayList();
        int maleTotal=0;
        int femaleTotal=0;
        int grandTotal=0;
        int ovcServed=0;
        int caregiversServed=0;
        //int totalServed=0;
        mainlist.add(indicator);
        for(int i=1; i<8; i++)
        {
            if(i==5)
            {
               ovcServed=Integer.parseInt(ovcServedList.get(i).toString());
               caregiversServed=Integer.parseInt(caregiverServedList.get(1).toString());
               mainlist.add(ovcServed);
               maleTotal+=ovcServed;
               //maleTotal+=caregiversServed;
               continue;
            }
            else if(i==6)
            {
               ovcServed=Integer.parseInt(ovcServedList.get(i).toString());
               caregiversServed=Integer.parseInt(caregiverServedList.get(2).toString());
               mainlist.add(ovcServed+caregiversServed);
               maleTotal+=ovcServed;
               maleTotal+=caregiversServed;
               continue;
            }
            else if(i==7)
            {
              caregiversServed=Integer.parseInt(caregiverServedList.get(3).toString());
              mainlist.add(caregiversServed);
              maleTotal+=caregiversServed;
              //mainlist.add(maleTotal);
              continue;
            }
            ovcServed=Integer.parseInt(ovcServedList.get(i).toString());
            mainlist.add(ovcServed);
            maleTotal+=ovcServed;

        }
        for(int i=8; i<ovcServedList.size(); i++)
        {
            if(i==12)
            {
               ovcServed=Integer.parseInt(ovcServedList.get(i).toString());
               caregiversServed=Integer.parseInt(caregiverServedList.get(5).toString());
               mainlist.add(ovcServed);
               femaleTotal+=ovcServed;
               //femaleTotal+=caregiversServed;
               continue;
            }
            else if(i==13)
            {
               ovcServed=Integer.parseInt(ovcServedList.get(i).toString());
               caregiversServed=Integer.parseInt(caregiverServedList.get(6).toString());
               mainlist.add(ovcServed+caregiversServed);
               femaleTotal+=ovcServed;
               femaleTotal+=caregiversServed;
               continue;
            }
            else if(i==14)
            {
              caregiversServed=Integer.parseInt(caregiverServedList.get(7).toString());
              mainlist.add(caregiversServed);
              femaleTotal+=caregiversServed;
              //mainlist.add(femaleTotal);
              continue;
            }
            ovcServed=Integer.parseInt(ovcServedList.get(i).toString());
            mainlist.add(ovcServed);
            femaleTotal+=ovcServed;
        }
        grandTotal=maleTotal+femaleTotal;
        //mainlist.add(grandTotal);
        return mainlist;
    }
    public List getNoOfActiveBeneficiariesServedForDatim2017(String indicator,List ovcServedList,List caregiverServedList) throws Exception
    {
        List mainlist=new ArrayList();
        int maleTotal=0;
        int femaleTotal=0;
        int grandTotal=0;
        int ovcServed=0;
        int caregiversServed=0;
        //int totalServed=0;
        mainlist.add(indicator);
        for(int i=1; i<8; i++)
        {
            if(i==5)
            {
               ovcServed=Integer.parseInt(ovcServedList.get(i).toString());
               //caregiversServed=Integer.parseInt(caregiverServedList.get(1).toString());
               mainlist.add(ovcServed);
               maleTotal+=ovcServed;
               //maleTotal+=caregiversServed;
               continue;
            }
            else if(i==6)
            {
               ovcServed=Integer.parseInt(ovcServedList.get(i).toString());
               //caregiversServed=Integer.parseInt(caregiverServedList.get(2).toString());
               mainlist.add(ovcServed+caregiversServed);
               maleTotal+=ovcServed;
               maleTotal+=caregiversServed;
               continue;
            }
            else if(i==7)
            {
              //caregiversServed=Integer.parseInt(caregiverServedList.get(3).toString());
              mainlist.add(caregiversServed);
              maleTotal+=caregiversServed;
              //mainlist.add(maleTotal);
              continue;
            }
            ovcServed=Integer.parseInt(ovcServedList.get(i).toString());
            mainlist.add(ovcServed);
            maleTotal+=ovcServed;

        }
        for(int i=8; i<ovcServedList.size(); i++)
        {
            if(i==12)
            {
               ovcServed=Integer.parseInt(ovcServedList.get(i).toString());
               //caregiversServed=Integer.parseInt(caregiverServedList.get(5).toString());
               mainlist.add(ovcServed);
               femaleTotal+=ovcServed;
               //femaleTotal+=caregiversServed;
               continue;
            }
            else if(i==13)
            {
               ovcServed=Integer.parseInt(ovcServedList.get(i).toString());
               //caregiversServed=Integer.parseInt(caregiverServedList.get(6).toString());
               mainlist.add(ovcServed+caregiversServed);
               femaleTotal+=ovcServed;
               femaleTotal+=caregiversServed;
               continue;
            }
            else if(i==14)
            {
              //caregiversServed=Integer.parseInt(caregiverServedList.get(7).toString());
              mainlist.add(caregiversServed);
              femaleTotal+=caregiversServed;
              //mainlist.add(femaleTotal);
              continue;
            }
            ovcServed=Integer.parseInt(ovcServedList.get(i).toString());
            mainlist.add(ovcServed);
            femaleTotal+=ovcServed;
        }
        grandTotal=maleTotal+femaleTotal;
        //mainlist.add(grandTotal);
        return mainlist;
    }
        
    public List getNoOfActiveBeneficiariesSupportedToAccessHIVCare(String indicator,String additionalQueryCriteria,String[] dateArray) throws Exception
    {
        List mainlist=new ArrayList();
        List ovcList=getNoOfOvcSupportedOnHIVAIDSServicesForMSF(indicator,additionalQueryCriteria,dateArray);
        List cgiverList=getNoOfHouseholdsSupportedToAccessHIVCare(indicator,additionalQueryCriteria,dateArray);
        int count=0;
        int maleOvcTotal=0;
        int femaleOvcTotal=0;
        int maleTotal=0;
        int femaleTotal=0;
        int cgiverMale0To17=Integer.parseInt(cgiverList.get(1).toString());
        int cgiverMale18To24=Integer.parseInt(cgiverList.get(2).toString());
        int cgiverMale25AndAbove=Integer.parseInt(cgiverList.get(3).toString());
        int cgiverMaleTotal=Integer.parseInt(cgiverList.get(4).toString());
        int cgiverFemale0To17=Integer.parseInt(cgiverList.get(5).toString());
        int cgiverFemale18To24=Integer.parseInt(cgiverList.get(6).toString());
        int cgiverFemale25AndAbove=Integer.parseInt(cgiverList.get(7).toString());
        int cgiverFemaleTotal=Integer.parseInt(cgiverList.get(8).toString());
        for(int i=0; i<ovcList.size(); i++)
        {
            if(i==6)
            {
                //System.err.println("cgiverMale18To24 is "+cgiverMale18To24);
                //System.err.println("cgiverMale25AndAbove is "+cgiverMale25AndAbove);
                count=Integer.parseInt(ovcList.get(i).toString());
                mainlist.add(count+cgiverMale18To24);
                mainlist.add(cgiverMale25AndAbove);
                maleTotal=count+cgiverMale18To24+cgiverMale25AndAbove;
                //mainlist.add(maleTotal);
                
            }
            else if(i==7)
            {
               maleOvcTotal=Integer.parseInt(ovcList.get(i).toString());
               //cgiverMaleTotal=Integer.parseInt(cgiverList.get(4).toString());
               maleTotal=maleOvcTotal+cgiverMaleTotal;
               mainlist.add(maleTotal);
            }
            else if(i==13)
            {
                //System.err.println("cgiverFemale18To24 is "+cgiverFemale18To24);
                //System.err.println("cgiverFemale25AndAbove is "+cgiverFemale25AndAbove);
                count=Integer.parseInt(ovcList.get(i).toString());
                mainlist.add(count+cgiverFemale18To24);
                mainlist.add(cgiverFemale25AndAbove);
                femaleTotal=count+cgiverFemale18To24+cgiverFemale25AndAbove;
            }
            else if(i==14)
            {
               femaleOvcTotal=Integer.parseInt(ovcList.get(i).toString());
               femaleTotal=femaleOvcTotal+cgiverFemaleTotal;
               mainlist.add(femaleTotal);
            }
            else if(i==15)
            {
                mainlist.add(maleTotal+femaleTotal);
            }
            else
            {
                mainlist.add(ovcList.get(i));
            }
        }

        return mainlist;
    }
    public List getNoOfOvcSupportedOnHIVAIDSServicesForMSF(String indicator,String additionalQueryCriteria,String[] dateArray) throws Exception
    {
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        String serviceCriteria=" ";
        int maleTotal=0;
        int femaleTotal=0;
        int count=0;
        
        for(int i=0; i<ageSegregation.length; i+=2)
        {
            ageQuery=util.getAgeCriteria(ageSegregation[i]+"", ageSegregation[i+1]+"");
            if(dateArray !=null)
            serviceCriteria=" and service.servicedate between '"+dateArray[0]+"' and '"+dateArray[1]+"' )";
            String malesql=serviceCountQueryPart+rutil.getHIVServicesReportQuery()+" and "+util.getGenderCriteria(maleGender)+ageQuery+" "+additionalQueryCriteria+serviceCriteria;
            String femalesql=serviceCountQueryPart+rutil.getHIVServicesReportQuery()+" and "+util.getGenderCriteria(femaleGender)+ageQuery+" "+additionalQueryCriteria+serviceCriteria;
            count=(Integer.parseInt(execReportQuery(malesql).get(0).toString()));
            maleList.add(count);
            maleTotal+=count;
            count=(Integer.parseInt(execReportQuery(femalesql).get(0).toString()));
            femaleList.add(count);
            femaleTotal+=count;
            System.err.println("malesql in getNoOfOvcSupportedOnHIVAIDSServicesForMSF is "+malesql+" Male: "+maleTotal+" Female: "+femaleTotal);
        }
        maleList.add(maleTotal);
        femaleList.add(femaleTotal);
        mainlist.add(indicator);
        //mainlist.add("Number of active VC beneficiaries receiving support to access HIV services");
        mainlist.addAll(maleList);
        mainlist.addAll(femaleList);
        int grandTotal=maleTotal+femaleTotal;
        mainlist.add(grandTotal);
        return mainlist;
    }
    public List getNoOfOvcWithdrawn(String indicator,String sex,String additionalQueryCriteria,String additionalWithdrawalQuery,String additionalServiceQuery,String ageQuery) throws Exception
    {
        int maleTotal=0;
        int femaleTotal=0;
        List mainlist=new ArrayList();
        if(!additionalWithdrawalQuery.equalsIgnoreCase(" "))
           additionalWithdrawalQuery=" and "+additionalWithdrawalQuery;
        additionalServiceQuery=getAdditionalServiceQuery(additionalServiceQuery);
        //String dataElement="Number of <b>VC Withdrawn</b> from the program";
        String subquery="select count(distinct withdrawal.ovcId)"+util.getHouseholdQueryPart()+"Ovc ovc, OvcWithdrawal withdrawal where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=withdrawal.ovcId "+additionalWithdrawalQuery;
        String maleSubSql=subquery+" and "+util.getGenderCriteria(maleGender);
        String femaleSubSql=subquery+" and "+util.getGenderCriteria(femaleGender);
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        int count=0;
        if(ageQuery==null)
        {
            for(int i=0; i<ageSegregation.length; i+=2)
            {
                ageQuery=util.getAgeCriteria(ageSegregation[i]+"", ageSegregation[i+1]+"");
                String malesql=maleSubSql+" "+additionalQueryCriteria+ageQuery;
                String femalesql=femaleSubSql+" "+additionalQueryCriteria+ageQuery;
                count=(Integer.parseInt(execReportQuery(malesql).get(0).toString()));
                maleList.add(count);
                maleTotal+=count;
                count=(Integer.parseInt(execReportQuery(femalesql).get(0).toString()));
                femaleList.add(count);
                femaleTotal+=count;
            }
            mainlist.add(indicator);
            maleList.add(maleTotal);
            femaleList.add(femaleTotal);
            mainlist.addAll(maleList);
            mainlist.addAll(femaleList);
        }
        else
        {
            mainlist.add(indicator);
            String malesql=maleSubSql+" "+additionalQueryCriteria+ageQuery;
            String femalesql=femaleSubSql+" "+additionalQueryCriteria+ageQuery;

            count=(Integer.parseInt(execReportQuery(malesql).get(0).toString()));
            maleList.add(count);
            maleTotal+=count;
            count=(Integer.parseInt(execReportQuery(femalesql).get(0).toString()));
            femaleList.add(count);
            femaleTotal+=count;
            maleList.add(maleTotal);
            femaleList.add(femaleTotal);
            mainlist.addAll(maleList);
            mainlist.addAll(femaleList);
        }
        System.err.println("Inside getNoOfOvcWithdrawn");
        int grandTotal=maleTotal+femaleTotal;
        mainlist.add(grandTotal);
        return mainlist;
    }
    public List getNoOfOvcOutOfProgram(String indicator,String additionalQueryCriteria,String additionalServiceQuery,String reasonForWithdrawal) throws Exception
    {
        List mainlist=new ArrayList();
        additionalServiceQuery=getAdditionalServiceQuery(additionalServiceQuery);
        //String maleSubSql=util.getHouseholdQueryPart()+"Ovc ovc where hhe.hhUniqueId=ovc.hhUniqueId and "+util.getGenderCriteria(maleGender);
        //String femaleSubSql=util.getHouseholdQueryPart()+"Ovc ovc where hhe.hhUniqueId=ovc.hhUniqueId and "+util.getGenderCriteria(femaleGender);
        //String serviceCriteria=" and ovc.ovcId in (select distinct withdrawal.ovcId from OvcWithdrawal withdrawal where withdrawal.reasonWithdrawal like '%"+reasonForWithdrawal+"%')";
        String ageQuery=" ";
        String queryPart=null;
        String withdrawalQuery="";
        if(reasonForWithdrawal !=null)
        withdrawalQuery=" and withdrawal.reasonWithdrawal like '%"+reasonForWithdrawal+"%'";
        int maleWithdrawn=0;
        int femaleWithdrawn=0;
        List maleWithdrawnList=null;
        List femaleWithdrawnList=null;
        int maleCount=0;
        int femaleCount=0;
        int maleTotal=0;
        int femaleTotal=0;
        int count=0;
            List maleList=new ArrayList();
            List femaleList=new ArrayList();
            for(int i=0; i<ageSegregation.length; i+=2)
            {
                ageQuery=util.getAgeCriteria(ageSegregation[i]+"", ageSegregation[i+1]+"");
                queryPart=additionalQueryCriteria+ageQuery;
                String malesql=queryPart+" and "+util.getGenderCriteria(maleGender)+withdrawalQuery;
                String femalesql=queryPart+" and "+util.getGenderCriteria(femaleGender)+withdrawalQuery;
                //count=execReportQuery(malesql).size();
                
                maleWithdrawnList=withdrawalDao.getListOfOvcWithdrawn(malesql);
                if(maleWithdrawnList !=null)
                maleWithdrawn=maleWithdrawnList.size();
                maleCount=maleWithdrawn;
                maleList.add(maleCount);
                maleTotal+=maleCount;
                //count=execReportQuery(femalesql).size();
                femaleWithdrawnList=withdrawalDao.getListOfOvcWithdrawn(femalesql);
                if(femaleWithdrawnList !=null)
                femaleWithdrawn=femaleWithdrawnList.size();
                femaleCount=femaleWithdrawn;
                femaleList.add(femaleCount);
                femaleTotal+=femaleCount;
            }
            maleList.add(maleTotal);
            femaleList.add(femaleTotal);
            mainlist.add(indicator);
            mainlist.addAll(maleList);
            mainlist.addAll(femaleList);
            int grandTotal=maleTotal+femaleTotal;
            mainlist.add(grandTotal);
        System.err.println("Inside getNoOfOvcOutOfProgram");
        return mainlist;
    }
    public List getNoOfOvcAbove18YearsWhoGraduated(String indicator,String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        additionalServiceQuery=getAdditionalServiceQuery(additionalServiceQuery);
        //String dataElement="Number of <b>VC graduated</b> from the program ( > 18)";
        String maleSubSql=util.getHouseholdQueryPart()+"Ovc ovc,OvcWithdrawal withdrawal where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=withdrawal.ovcId and "+util.getGenderCriteria(maleGender);
        String femaleSubSql=util.getHouseholdQueryPart()+"Ovc ovc, OvcWithdrawal withdrawal where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=withdrawal.ovcId and "+util.getGenderCriteria(femaleGender);
        String serviceCriteria=" and withdrawal.reasonWithdrawal like '%ageabove17%'";
        String queryPart=additionalQueryCriteria+serviceCriteria;
        //int maleWithdrawn=0;
        //int femaleWithdrawn=0;
        /*List maleWithdrawnList=withdrawalDao.getListOfOvcWithdrawn(queryPart+" and "+util.getGenderCriteria(maleGender));
        if(maleWithdrawnList !=null)
        maleWithdrawn=maleWithdrawnList.size();
        /List femaleWithdrawnList=withdrawalDao.getListOfOvcWithdrawn(queryPart+" and "+util.getGenderCriteria(femaleGender));
        if(femaleWithdrawnList !=null)
        femaleWithdrawn=femaleWithdrawnList.size();*/
        int maleCount=0;
        int femaleCount=0;
        int maleTotal=0;
        int femaleTotal=0;
        int mcount=0;
        int fcount=0;
        if(ageQuery==null)
        {
            List maleList=new ArrayList();
            List femaleList=new ArrayList();

            String malesql=maleSubSql+" "+queryPart;
            String femalesql=femaleSubSql+" "+queryPart;
            mcount=(execReportQuery(malesql).size());
            maleCount=mcount;//-maleWithdrawn;
            
            maleList.add(maleCount);
            maleTotal+=maleCount;
            maleList.add(maleTotal);
            fcount=(execReportQuery(femalesql).size());
            femaleCount=fcount;//-femaleWithdrawn;
            femaleList.add(femaleCount);
            femaleTotal+=femaleCount;
            
            femaleList.add(femaleTotal);
            mainlist.add(indicator);
            mainlist.addAll(maleList);
            mainlist.addAll(femaleList);
            /*System.err.println("maleTotal in getNoOfOvcAbove18Years is "+maleTotal);
            System.err.println("femaleTotal in getNoOfOvcAbove18Years is "+femaleCount);
            System.err.println("malesql in getNoOfOvcAbove18YearsWhoGraduated is "+malesql);
            System.err.println("femalesql in getNoOfOvcAbove18YearsWhoGraduated is "+femalesql);*/
        }
        int grandTotal=maleTotal+femaleTotal;
        
        mainlist.add(grandTotal);
        System.err.println("grandTotal in getNoOfOvcAbove18Years is "+grandTotal);
        //System.err.println("Finished executing getNoOfOvcAbove18Years");
        
        return mainlist;
    }
    public List getNoOfOvcAccessingPsychoSupport(String indicator,String additionalQueryCriteria,String[] dateArray) throws Exception
    {
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        int j=0;
        int score=0;
        int maleTotal=0;
        int femaleTotal=0;
        int count=0;
        for(int i=0; i<ageSegregation.length; i+=2)
        {
            ageQuery=util.getAgeCriteria(ageSegregation[i]+"", ageSegregation[i+1]+"");
            String queryPart=serviceCountQueryPart+" (service.serviceAccessed1 is not null and  service.serviceAccessed1 !='' and service.serviceAccessed1 !=' ') and service.servicedate between '"+dateArray[0]+"' and '"+dateArray[1]+"'";
            String malesql=queryPart+" and "+util.getGenderCriteria(maleGender)+ageQuery+" "+additionalQueryCriteria;
            String femalesql=queryPart+" and "+util.getGenderCriteria(femaleGender)+ageQuery+" "+additionalQueryCriteria;
            count=(Integer.parseInt(execReportQuery(malesql).get(0).toString()));
            maleList.add(count);
            maleTotal+=count;
            count=(Integer.parseInt(execReportQuery(femalesql).get(0).toString()));
            femaleList.add(count);
            femaleTotal+=count;
        }
        maleList.add(maleTotal);
        femaleList.add(femaleTotal);
        mainlist.add(indicator);
        //mainlist.add("Number of <b>VC</b> accessing <b>Psychosocial support</b> services");
        mainlist.addAll(maleList);
        mainlist.addAll(femaleList);
        int grandTotal=maleTotal+femaleTotal;
        mainlist.add(grandTotal);
        return mainlist;
    }
    public List getNoOfOvcAccessingHealthCare(String indicator,String additionalQueryCriteria,String[] dateArray) throws Exception
    {
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        int maleTotal=0;
        int femaleTotal=0;
        int count=0;
        int j=0;
        for(int i=0; i<ageSegregation.length; i+=2)
        {
            ageQuery=util.getAgeCriteria(ageSegregation[i]+"", ageSegregation[i+1]+"");
            String queryPart=serviceCountQueryPart+" (service.serviceAccessed3 is not null and  service.serviceAccessed3 !='' and service.serviceAccessed3 !=' ') and service.servicedate between '"+dateArray[0]+"' and '"+dateArray[1]+"'";
            String malesql=queryPart+" and "+util.getGenderCriteria(maleGender)+ageQuery+" "+additionalQueryCriteria;
            String femalesql=queryPart+" and "+util.getGenderCriteria(femaleGender)+ageQuery+" "+additionalQueryCriteria;
            count=(Integer.parseInt(execReportQuery(malesql).get(0).toString()));
            maleList.add(count);
            maleTotal+=count;
            count=(Integer.parseInt(execReportQuery(femalesql).get(0).toString()));
            femaleList.add(count);
            femaleTotal+=count;
        }
        maleList.add(maleTotal);
        femaleList.add(femaleTotal);
        mainlist.add(indicator);
        //mainlist.add("Number of <b>VC</b> accessing <b>Health care</b> services");
        mainlist.addAll(maleList);
        mainlist.addAll(femaleList);
        int grandTotal=maleTotal+femaleTotal;
        mainlist.add(grandTotal);
        return mainlist;
    }
    public List getNoOfHIVPositivOvcProvidedClinicalServicees(String indicator,String additionalQueryCriteria,String[] dateArray) throws Exception
    {
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        additionalQueryCriteria=additionalQueryCriteria+util.getActiveHivStatusCriteria(NomisConstant.HIV_POSITIVE,NomisConstant.OVC_TYPE);
        int maleTotal=0;
        int femaleTotal=0;
        int count=0;
        int j=0; //   
        for(int i=0; i<ageSegregation.length; i+=2)
        {
            ageQuery=util.getAgeCriteria(ageSegregation[i]+"", ageSegregation[i+1]+"");
            String queryPart=serviceCountQueryPart+" (service.serviceAccessed3 like '%Access for HIV care%' or service.serviceAccessed3 like '%HIV services referral–HTC/PMTCT%' or service.serviceAccessed3 like '%Community HIV services – HTC/PMTCT%' or service.serviceAccessed3 like '%De-worming%' or service.serviceAccessed3 like '%Health referral%') and service.servicedate between '"+dateArray[0]+"' and '"+dateArray[1]+"'";
            String malesql=queryPart+" and "+util.getGenderCriteria(maleGender)+ageQuery+" "+additionalQueryCriteria;
            String femalesql=queryPart+" and "+util.getGenderCriteria(femaleGender)+ageQuery+" "+additionalQueryCriteria;
            count=(Integer.parseInt(execReportQuery(malesql).get(0).toString()));
            maleList.add(count);
            maleTotal+=count;
            count=(Integer.parseInt(execReportQuery(femalesql).get(0).toString()));
            femaleList.add(count);
            femaleTotal+=count;
        }
        maleList.add(maleTotal);
        femaleList.add(femaleTotal);
        mainlist.add(indicator);
        //mainlist.add("Number of <b>VC</b> accessing <b>Health care</b> services");
        mainlist.addAll(maleList);
        mainlist.addAll(femaleList);
        int grandTotal=maleTotal+femaleTotal;
        mainlist.add(grandTotal);
        return mainlist;
    }
    public List getNoOfOvcAccessingNutritional(String indicator,String additionalQueryCriteria,String[] dateArray) throws Exception
    {
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        int maleTotal=0;
        int femaleTotal=0;
        int count=0;
        int j=0;
        for(int i=0; i<ageSegregation.length; i+=2)
        {
            ageQuery=util.getAgeCriteria(ageSegregation[i]+"", ageSegregation[i+1]+"");
            String queryPart=serviceCountQueryPart+"(service.serviceAccessed2 is not null and  service.serviceAccessed2 !='' and service.serviceAccessed2 !=' ') and service.servicedate between '"+dateArray[0]+"' and '"+dateArray[1]+"'";
            String malesql=queryPart+" and "+util.getGenderCriteria(maleGender)+ageQuery+" "+additionalQueryCriteria;
            String femalesql=queryPart+" and "+util.getGenderCriteria(femaleGender)+ageQuery+" "+additionalQueryCriteria;
            count=(Integer.parseInt(execReportQuery(malesql).get(0).toString()));
            maleList.add(count);
            maleTotal+=count;
            count=(Integer.parseInt(execReportQuery(femalesql).get(0).toString()));
            femaleList.add(count);
            femaleTotal+=count;
        }
        maleList.add(maleTotal);
        femaleList.add(femaleTotal);
        mainlist.add(indicator);
        //mainlist.add("Number of <b>VC</b> accessing <b>Nutritional</b> services");
        mainlist.addAll(maleList);
        mainlist.addAll(femaleList);
        int grandTotal=maleTotal+femaleTotal;
        mainlist.add(grandTotal);
        return mainlist;
    }
    public List getNoOfOvcAccessingEducational(String indicator,String additionalQueryCriteria,String[] dateArray) throws Exception
    {
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        int maleTotal=0;
        int femaleTotal=0;
        int count=0;
        for(int i=0; i<ageSegregation.length; i+=2)
        {
            ageQuery=util.getAgeCriteria(ageSegregation[i]+"", ageSegregation[i+1]+"");
            String queryPart=serviceCountQueryPart+"(service.serviceAccessed4 is not null and  service.serviceAccessed4 !='' and service.serviceAccessed4 !=' ') and service.servicedate between '"+dateArray[0]+"' and '"+dateArray[1]+"'";
            String malesql=queryPart+" and "+util.getGenderCriteria(maleGender)+ageQuery+" "+additionalQueryCriteria;
            String femalesql=queryPart+" and "+util.getGenderCriteria(femaleGender)+ageQuery+" "+additionalQueryCriteria;
            count=(Integer.parseInt(execReportQuery(malesql).get(0).toString()));
            maleList.add(count);
            maleTotal+=count;
            count=(Integer.parseInt(execReportQuery(femalesql).get(0).toString()));
            femaleList.add(count);
            femaleTotal+=count;
        }
        maleList.add(maleTotal);
        femaleList.add(femaleTotal);
        mainlist.add(indicator);
        //mainlist.add("Number of <b>VC</b> accessing <b>Education</b> services");
        mainlist.addAll(maleList);
        mainlist.addAll(femaleList);
        int grandTotal=maleTotal+femaleTotal;
        mainlist.add(grandTotal);
        return mainlist;
    }
    public List getNoOfOvcAccessingShelter(String indicator,String additionalQueryCriteria,String[] dateArray) throws Exception
    {
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        int maleTotal=0;
        int femaleTotal=0;
        int count=0;
        for(int i=0; i<ageSegregation.length; i+=2)
        {
            ageQuery=util.getAgeCriteria(ageSegregation[i]+"", ageSegregation[i+1]+"");
            String queryPart=serviceCountQueryPart+" (service.serviceAccessed6 is not null and  service.serviceAccessed6 !='' and service.serviceAccessed6 !=' ') and service.servicedate between '"+dateArray[0]+"' and '"+dateArray[1]+"'";
            String malesql=queryPart+" and "+util.getGenderCriteria(maleGender)+ageQuery+" "+additionalQueryCriteria;
            String femalesql=queryPart+" and "+util.getGenderCriteria(femaleGender)+ageQuery+" "+additionalQueryCriteria;
            count=(Integer.parseInt(execReportQuery(malesql).get(0).toString()));
            maleList.add(count);
            maleTotal+=count;
            count=(Integer.parseInt(execReportQuery(femalesql).get(0).toString()));
            femaleList.add(count);
            femaleTotal+=count;
        }
        maleList.add(maleTotal);
        femaleList.add(femaleTotal);
        mainlist.add(indicator);
        //mainlist.add("Number of <b>VC</b> accessing <b>Shelter</b> services");
        mainlist.addAll(maleList);
        mainlist.addAll(femaleList);
        int grandTotal=maleTotal+femaleTotal;
        mainlist.add(grandTotal);
        return mainlist;
    }
    public List getNoOfOvcAccessingProtection(String indicator,String additionalQueryCriteria,String[] dateArray) throws Exception
    {
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        int maleTotal=0;
        int femaleTotal=0;
        int count=0;
        for(int i=0; i<ageSegregation.length; i+=2)
        {
            ageQuery=util.getAgeCriteria(ageSegregation[i]+"", ageSegregation[i+1]+"");
            String queryPart=serviceCountQueryPart+" (service.serviceAccessed5 is not null and  service.serviceAccessed5 !='' and service.serviceAccessed5 !=' ') and service.servicedate between '"+dateArray[0]+"' and '"+dateArray[1]+"'";
            String malesql=queryPart+" and "+util.getGenderCriteria(maleGender)+ageQuery+" "+additionalQueryCriteria;
            String femalesql=queryPart+" and "+util.getGenderCriteria(femaleGender)+ageQuery+" "+additionalQueryCriteria;
            count=(Integer.parseInt(execReportQuery(malesql).get(0).toString()));
            maleList.add(count);
            maleTotal+=count;
            count=(Integer.parseInt(execReportQuery(femalesql).get(0).toString()));
            femaleList.add(count);
            femaleTotal+=count;
        }
        maleList.add(maleTotal);
        femaleList.add(femaleTotal);
        mainlist.add(indicator);
        //mainlist.add("Number of <b>VC</b> accessing <b>Protection</b> services");
        mainlist.addAll(maleList);
        mainlist.addAll(femaleList);
        int grandTotal=maleTotal+femaleTotal;
        mainlist.add(grandTotal);
        return mainlist;
    }
    public List getNoOfOvcAccessingEconStrengthening(String indicator,String additionalQueryCriteria,String[] dateArray,String ageSegType) throws Exception
    {
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        int maleTotal=0,femaleTotal=0,grandTotal=0,maleCount=0,femaleCount=0;
        int k=4;
        String blankStyle="<label style='background-color:grey; width:50px'>&nbsp;</label>";
        if(ageSegType.equalsIgnoreCase("new"))
        {
            ageSegregation=getNewAgeSegregation();
            k=0;
            for(int i=k; i<ageSegregation.length; i+=2)
            {
                ageQuery=util.getAgeCriteria(ageSegregation[i]+"", ageSegregation[i+1]+"");
                String queryPart=serviceCountQueryPart+"(service.serviceAccessed7 is not null and  service.serviceAccessed7 !='' and service.serviceAccessed7 !=' ') and service.servicedate between '"+dateArray[0]+"' and '"+dateArray[1]+"'";
                String malesql=queryPart+" and "+util.getGenderCriteria(maleGender)+" and ovc.age between "+ ageSegregation[i]+" and "+ageSegregation[i+1]+" "+additionalQueryCriteria;
                String femalesql=queryPart+" and "+util.getGenderCriteria(femaleGender)+" and ovc.age between "+ ageSegregation[i]+" and "+ageSegregation[i+1]+" "+additionalQueryCriteria;
                maleCount=((Long)execReportQuery(malesql).get(0)).intValue();
                femaleCount=((Long)execReportQuery(femalesql).get(0)).intValue();
                maleList.add(maleCount);
                femaleList.add(femaleCount);
                maleTotal+=maleCount;
                femaleTotal+=femaleCount;
            }
            maleList.add(maleTotal);
            femaleList.add(femaleTotal);
        }
        else
        {
            for(int i=k; i<ageSegregation.length; i+=2)
            {
                ageQuery=util.getAgeCriteria(ageSegregation[i]+"", ageSegregation[i+1]+"");
                String queryPart=serviceCountQueryPart+"(service.serviceAccessed7 is not null and  service.serviceAccessed7 !='' and service.serviceAccessed7 !=' ') and service.servicedate between '"+dateArray[0]+"' and '"+dateArray[1]+"'";
                String malesql=queryPart+" and "+util.getGenderCriteria(maleGender)+" and ovc.age between "+ ageSegregation[i]+" and "+ageSegregation[i+1]+" "+additionalQueryCriteria;
                String femalesql=queryPart+" and "+util.getGenderCriteria(femaleGender)+" and ovc.age between "+ ageSegregation[i]+" and "+ageSegregation[i+1]+" "+additionalQueryCriteria;
                maleCount=((Long)execReportQuery(malesql).get(0)).intValue();
                femaleCount=((Long)execReportQuery(femalesql).get(0)).intValue();
                maleList.add(blankStyle);
                maleList.add(blankStyle);
                femaleList.add(blankStyle);
                femaleList.add(blankStyle);
                maleList.add(maleCount);
                femaleList.add(femaleCount);
                maleTotal+=maleCount;
                femaleTotal+=femaleCount;
            }
            maleList.add(maleTotal);
            femaleList.add(femaleTotal);
        }
        mainlist.add(indicator);
        //mainlist.add("Number of <b>VC</b> accessing <b>Economic Strengthening</b> services");
        //System.err.println("maleList size is "+maleList.size());
        //System.err.println("femaleList size is "+femaleList.size());
        mainlist.addAll(maleList);
        mainlist.addAll(femaleList);
        grandTotal=maleTotal+femaleTotal;
        mainlist.add(grandTotal);
        System.err.println("mainList size is "+mainlist.size());
        return mainlist;
    }
    public List getNoOfCaregiversTrained(String indicator,String additionalQueryCriteria,String[] dateArray) throws Exception
    {
        //int[] ageSegregation=getHouseholdAgeSegregation();
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        List mlist=new ArrayList();
        List flist=new ArrayList();

        int maleTotal=0,femaleTotal=0,grandTotal=0,maleCount=0,femaleCount=0;
        for(int i=0; i<ageSegregation.length; i+=2)
        {
            String queryPart=hheCaregiverHhsQuery+" and hhs.economicStrengtheningServices like '%Vocational/apprenticeship training%' and hhs.serviceDate between '"+dateArray[0]+"' and '"+dateArray[1]+"' and ";
            String malesql=queryPart+util.getCaregiverGenderCriteria(hviMaleGender)+" and "+util.getCaregiverAgeCriteria(ageSegregation[i],ageSegregation[i+1])+" "+additionalQueryCriteria;
            String femalesql=queryPart+util.getCaregiverGenderCriteria(hviFemaleGender)+" and "+util.getCaregiverAgeCriteria(ageSegregation[i],ageSegregation[i+1])+" "+additionalQueryCriteria;
            //System.err.println("malesql in getNoOfHouseholdsProvidedWithAtleastOneService is "+malesql);
            //System.err.println("femalesql in getNoOfHouseholdsProvidedWithAtleastOneService is "+femalesql);
            mlist=execReportQuery(malesql);
            if(mlist !=null && !mlist.isEmpty())
            {
                maleCount=((Long)mlist.get(0)).intValue();
            }
            flist=execReportQuery(femalesql);
            if(flist !=null && !flist.isEmpty())
            {
                femaleCount=((Long)flist.get(0)).intValue();
            }
            maleList.add(maleCount);
            femaleList.add(femaleCount);
            maleTotal+=maleCount;
            femaleTotal+=femaleCount;
        }
        mainlist.add(indicator);
        //mainlist.add("Number of <b>caregivers/household heads</b> provided with at least one service");
        mainlist.addAll(maleList);
        mainlist.add(maleTotal);
        mainlist.addAll(femaleList);
        mainlist.add(femaleTotal);
        grandTotal=maleTotal+femaleTotal;
        mainlist.add(grandTotal);
        return mainlist;
    }
    public List getNoOfCaregiversAndHouseholdHeadsProvidedWithAtleastOneService(String indicator,String additionalQueryCriteria,String[] dateArray) throws Exception
    {
        int[] ageSegregation=getHouseholdAgeSegregation();
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        List mlist=new ArrayList();
        List flist=new ArrayList();

        int maleTotal=0,femaleTotal=0,grandTotal=0,maleCount=0,femaleCount=0;
        for(int i=0; i<ageSegregation.length; i+=2)
        {
            String queryPart=hheCaregiverHhsQuery+" and hhs.serviceDate between '"+dateArray[0]+"' and '"+dateArray[1]+"' and ";
            String malesql=queryPart+util.getCaregiverGenderCriteria(hviMaleGender)+" and "+util.getCaregiverAgeCriteria(ageSegregation[i],ageSegregation[i+1])+" "+additionalQueryCriteria;
            String femalesql=queryPart+util.getCaregiverGenderCriteria(hviFemaleGender)+" and "+util.getCaregiverAgeCriteria(ageSegregation[i],ageSegregation[i+1])+" "+additionalQueryCriteria;
            //System.err.println("malesql in getNoOfHouseholdsProvidedWithAtleastOneService is "+malesql);
            //System.err.println("femalesql in getNoOfHouseholdsProvidedWithAtleastOneService is "+femalesql);
            mlist=execReportQuery(malesql);
            if(mlist !=null && !mlist.isEmpty())
            {
                maleCount=((Long)mlist.get(0)).intValue();
            }
            flist=execReportQuery(femalesql);
            if(flist !=null && !flist.isEmpty())
            {
                femaleCount=((Long)flist.get(0)).intValue();
            }
            maleList.add(maleCount);
            femaleList.add(femaleCount);
            maleTotal+=maleCount;
            femaleTotal+=femaleCount;
        }
        mainlist.add(indicator);
        //mainlist.add("Number of <b>caregivers/household heads</b> provided with at least one service");
        mainlist.addAll(maleList);
        mainlist.add(maleTotal);
        mainlist.addAll(femaleList);
        mainlist.add(femaleTotal);
        grandTotal=maleTotal+femaleTotal;
        mainlist.add(grandTotal);
        return mainlist;
    }
    public List getNoOfHouseholdsSupportedToAccessHIVCare(String indicator,String additionalQueryCriteria,String[] dateArray) throws Exception
    {
        int[] ageSegregation=getHouseholdAgeSegregation();
        String periodQuery=" ";
        if(dateArray !=null && dateArray.length==2)
        periodQuery="and hhs.serviceDate between '"+dateArray[0]+"' and '"+dateArray[1]+"'";
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        int maleTotal=0,femaleTotal=0,grandTotal=0,maleCount=0,femaleCount=0;

        for(int i=0; i<ageSegregation.length; i+=2)
        {
            String queryPart=hheCaregiverHhsQuery+" and (hhs.healthServices like '%HIV services%' or hhs.healthServices like '%HIV care and support%')"+periodQuery+" and ";
            String malesql=queryPart+util.getCaregiverGenderCriteria(hviMaleGender)+" and "+util.getCaregiverAgeCriteria(ageSegregation[i],ageSegregation[i+1])+" "+additionalQueryCriteria;
            String femalesql=queryPart+util.getCaregiverGenderCriteria(hviFemaleGender)+" and "+util.getCaregiverAgeCriteria(ageSegregation[i],ageSegregation[i+1])+" "+additionalQueryCriteria;
            maleCount=((Long)execReportQuery(malesql).get(0)).intValue();
            femaleCount=((Long)execReportQuery(femalesql).get(0)).intValue();
            maleList.add(maleCount);
            femaleList.add(femaleCount);
            maleTotal+=maleCount;
            femaleTotal+=femaleCount;
        }
        mainlist.add(indicator);
        //mainlist.add("Number of caregivers/household heads supported to access HIV services");
        mainlist.addAll(maleList);
        //System.err.println("maleTotal in getNoOfHouseholdsSupportedToAccessHIVCare is "+maleTotal);
        mainlist.add(maleTotal);
        mainlist.addAll(femaleList);
        mainlist.add(femaleTotal);
        //System.err.println("femaleTotal in getNoOfHouseholdsSupportedToAccessHIVCare is "+femaleTotal);
        grandTotal=maleTotal+femaleTotal;
        mainlist.add(grandTotal);
        return mainlist;
    }
    public List getNoOfHouseholdHeadsReceivingEconomicStrenghtening(String indicator,String additionalQueryCriteria,String[] dateArray) throws Exception
    {
        int[] ageSegregation=getHouseholdAgeSegregation();
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();

        int maleTotal=0,femaleTotal=0,grandTotal=0,maleCount=0,femaleCount=0;
        for(int i=0; i<ageSegregation.length; i+=2)
        {
            String queryPart=hheCaregiverHhsQuery+" and (hhs.economicStrengtheningServices is not null and hhs.economicStrengtheningServices !='' and hhs.economicStrengtheningServices !=' ' and hhs.economicStrengtheningServices !='  ' ) and hhs.serviceDate between '"+dateArray[0]+"' and '"+dateArray[1]+"' and ";
            String malesql=queryPart+util.getCaregiverGenderCriteria(hviMaleGender)+" and "+util.getCaregiverAgeCriteria(ageSegregation[i],ageSegregation[i+1])+" "+additionalQueryCriteria;
            String femalesql=queryPart+util.getCaregiverGenderCriteria(hviFemaleGender)+" and "+util.getCaregiverAgeCriteria(ageSegregation[i],ageSegregation[i+1])+" "+additionalQueryCriteria;
            maleCount=((Long)execReportQuery(malesql).get(0)).intValue();
            femaleCount=((Long)execReportQuery(femalesql).get(0)).intValue();
            maleList.add(maleCount);
            femaleList.add(femaleCount);
            maleTotal+=maleCount;
            femaleTotal+=femaleCount;
        }
        mainlist.add(indicator);
        //mainlist.add("Number of caregivers/Household heads receiving Economic Strengthening");
        mainlist.addAll(maleList);
        mainlist.add(maleTotal);
        mainlist.addAll(femaleList);
        mainlist.add(femaleTotal);
        grandTotal=maleTotal+femaleTotal;
        mainlist.add(grandTotal);
        return mainlist;
    }
    public List getNoOfHouseholdsReceivingEconomicStrenghtening(String indicator,String additionalQueryCriteria,String[] dateArray,String ageSegType) throws Exception
    {
        int[] ageSegregation=getHouseholdAgeSegregation();
        if(ageSegType.equalsIgnoreCase("new"))
        ageSegregation=getNewAgeSegregation();
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();

        int maleTotal=0,femaleTotal=0,grandTotal=0,maleCount=0,femaleCount=0;
        for(int i=0; i<ageSegregation.length; i+=2)
        {
            maleList.add(maleCount);
            femaleList.add(femaleCount);
        }
        maleList.add(" ");
        femaleList.add(" ");
        String queryPart=hheServiceCountQuery+" and (hhs.economicStrengtheningServices is not null and hhs.economicStrengtheningServices !='' and hhs.economicStrengtheningServices !=' ' and hhs.economicStrengtheningServices !='  ' ) and hhs.serviceDate between '"+dateArray[0]+"' and '"+dateArray[1]+"'";
        String sql=queryPart+" "+additionalQueryCriteria;
        //System.err.println("sql in getNoOfHouseholdsReceivingEconomicStrenghtening is "+sql);
        grandTotal=((Long)execReportQuery(sql).get(0)).intValue();
        mainlist.add(indicator);
        //mainlist.add("Number of Households provided Economic Strengthening");
        mainlist.addAll(maleList);
        mainlist.addAll(femaleList);
        mainlist.add(grandTotal);
        return mainlist;
    }
    /*public List getReportQuery()
    {
        String reportQuery=" and ";
        String query=null;
        ReportQueryManagerDao rqmdao=new ReportQueryManagerDaoImpl();
        List reportQueryList=new ArrayList();
        try
        {
            List list=rqmdao.getAllReportQueryManagers();
            if(list !=null)
            {
                ReportQueryManager rqm=null;
                IndicatorDao idao=new IndicatorDaoImpl();
                CategoryCombinationDao ccdao=new CategoryCombinationDaoImpl();
                CategoryOptionDao codao=new CategoryOptionDaoImpl();
                Indicator indicator=null;
                CategoryCombination cc=null;
                CategoryOption coption=null;
                String indicatorId=null;
                String categoryComboId=null;
                String indicatorQuery=null;
                String categoryOptionId=null;
                String stdCategoryOptionId=null;
                String categoryDefinition=null;
                String[] categoryOptionIdArray=null;
                List stdcategoryOptionIdList=new ArrayList();
                for(int i=0; i<list.size(); i++)
                {
                    reportQuery=" ";
                    stdcategoryOptionIdList=new ArrayList();
                    rqm=(ReportQueryManager)list.get(i);
                    indicatorId=rqm.getIndicatorId();
                    categoryComboId=rqm.getCategoryComboId();
                    indicator=idao.getIndicator(indicatorId);
                    cc=ccdao.getCategoryCombination(categoryComboId);
                    categoryOptionId=cc.getCategoryId();
                    if(categoryOptionId !=null)
                    {
                        categoryOptionIdArray=categoryOptionId.split(",");
                        for(int j=0; j<categoryOptionIdArray.length; j++)
                        {
                            if(categoryOptionIdArray[j].indexOf("_std") !=-1)
                            {
                                stdCategoryOptionId=categoryOptionIdArray[j].substring(0, categoryOptionIdArray[j].indexOf("_std"));
                                stdcategoryOptionIdList.add(stdCategoryOptionId);
                                //System.err.println("stdCategoryOptionId is "+stdCategoryOptionId);
                            }
                            else
                            {
                                coption=codao.getCategoryOption(categoryOptionIdArray[j]);
                                if(coption !=null)
                                {
                                    categoryDefinition=coption.getCategoryDefinition();
                                    if(!reportQuery.endsWith(" and "))
                                    reportQuery+=" and ";
                                    reportQuery+=rutil.getCategoryOptionAgeQuery(categoryDefinition);
                                }
                            }
                        }
                        if(!reportQuery.endsWith(" and "))
                        reportQuery+=" and ";
                        reportQuery+=rutil.getQueryForStandardCategoryOption(stdcategoryOptionIdList);
                    }
                    indicatorQuery=indicator.getQuery(); 
                    query=indicatorQuery.replace("queryCriteria", reportQuery);
                    //reportQuery=indicatorQuery+reportQuery;
                    System.err.println("query is "+query);
                    rqm.setQuery(query);
                    reportQueryList.add(rqm);
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return reportQueryList;
    }
    public String generateDataValue()
    {
        List reportQueryList=getReportQuery();
        List list=null;
        String query=null;
        String startDate=null;
        String month=null;
        String year=null;
        Datavalue dv=null;
        Datavalue duplicateDv=null;
        Object[] objArray=null;
        DatavalueDao dvdao=new DatavalueDaoImpl();
        ReportQueryManager rqm=null;
        String msg=" ";
        try
        {
            for(int i=0; i<reportQueryList.size(); i++)
            {
                rqm=(ReportQueryManager)reportQueryList.get(i);
                if(rqm !=null)
                {
                    query=rqm.getQuery();
                    //System.err.println("query is "+query);
                    list=util.execReportQuery(query);
                    
                    for(int j=0; j<list.size(); j++)
                    {
                        objArray=(Object[])list.get(j);
                        dv=new Datavalue();
                        dv.setOrgunitid(objArray[0].toString());
                        dv.setIndicatorid(rqm.getIndicatorId());
                        dv.setCategoryComboId(rqm.getCategoryComboId());
                        month=objArray[1].toString();
                        year=objArray[2].toString();
                        startDate=year+"-"+month+"-01";
                        dv.setStartdate(startDate);
                        dv.setValue(Integer.parseInt(objArray[3].toString()));
                        duplicateDv=dvdao.getDatavalue(dv.getOrgunitid(), dv.getIndicatorid(),dv.getCategoryComboId(), dv.getStartdate());
                        if(duplicateDv ==null)
                        dvdao.saveDatavalue(dv);
                        else
                        {
                            dv.setRecordId(duplicateDv.getRecordId());
                            dvdao.updateDatavalue(dv);
                        }
                    }
                }
               msg="Records generated";
            }
        }
        catch(Exception ex)
        {
            msg="Error: "+ex.getMessage();
            //ex.printStackTrace();
        }
        return msg;
    }*/
        
}
