/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;
import com.fhi.kidmap.business.Caregiver;
import com.fhi.kidmap.business.ChildStatusIndex;
import com.fhi.kidmap.business.OrganizationalCapacityAssessment;
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.business.OvcService;
import com.fhi.kidmap.report.OvcRecords;
import com.fhi.nomis.nomisutils.NomisConstant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author COMPAQ USER
 */
public class EnrollmentReportDaoImpl implements EnrollmentReportDao {

    Session session;
    Transaction tx;
    DaoUtil util;
    OvcServiceDao serviceDao=new OvcServiceDaoImpl();
    OvcRecords records=new OvcRecords();
    private final String maleGender="male";
    private final String femaleGender="female";
    String hviMaleGender="Male";
    String hviFemaleGender="Female";
    String hhQueryPart=null;
    String hhOvcHivQueryPart=null;
    String hhOvcAndServiceQueryPart=null;
    String hhOvcServiceAndHivQueryPart=null;//util.getHouseholdHIVOvcServiceReportQueryPart("OvcService");
    String hhOvcAndServiceByOvcIdQueryPart=null;
    String countQueryPart=null;
    String countHivQueryPart=null;
    String serviceCountQueryPart=null;
    String serviceCountHivQueryPart=null;
    String hheCaregiverHhsQuery=null;
    String ageQuery;
    List maleLessThanThreeList=new ArrayList();
    List femaleLessThanThreeList=new ArrayList();
    List maleGreaterThanThreeList=new ArrayList();
    List femaleGreaterThanThreeList=new ArrayList();
    public EnrollmentReportDaoImpl()
    {
        util=new DaoUtil();
        hhQueryPart=util.getHouseholdQueryPart()+"Ovc ovc where hhe.hhUniqueId=ovc.hhUniqueId and ";
        hhOvcHivQueryPart=util.getHouseholdOvcHIVQueryPart();
        countQueryPart="select count(distinct ovc.ovcId)"+util.getHouseholdQueryPart()+"Ovc ovc where hhe.hhUniqueId=ovc.hhUniqueId and ";
        countHivQueryPart="select count(distinct ovc.ovcId)"+util.getHouseholdOvcHIVQueryPart();
        serviceCountQueryPart="select count(distinct service.ovcId)"+util.getHouseholdQueryPart()+"Ovc ovc, OvcService service where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=service.ovcId) and " ;
        serviceCountHivQueryPart="select count(distinct service.ovcId)"+util.getHouseholdHIVOvcServiceReportQueryPart("OvcService");
        hhOvcAndServiceQueryPart=util.getHouseholdQueryPart()+"Ovc ovc,OvcService service where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=service.ovcId) ";
        hhOvcServiceAndHivQueryPart=util.getHouseholdHIVOvcServiceReportQueryPart("OvcService");
        hhOvcAndServiceByOvcIdQueryPart="select distinct service.ovcId "+util.getHouseholdQueryPart()+"Ovc ovc,OvcService service where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=service.ovcId) ";
        hheCaregiverHhsQuery="select count(distinct hhs.caregiverId) from HouseholdService hhs, HouseholdEnrollment hhe,Caregiver cgiver where (hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.hhUniqueId=hhs.hhUniqueId)";
    }
    public List execReportQuery(String sql)
    {
        List list=new ArrayList();
        list=util.execReportQuery(sql);
        return list;
    }
    private List getListOfOvcByOvcId(List list)
    {
        List mainList=new ArrayList();
        OvcDao ovcDao=new OvcDaoImpl();
        Ovc ovc=null;
        try
        {
            if(list !=null)
            {
                for(int i=0;i<list.size(); i++)
                {
                    ovc=ovcDao.getOvc((String)list.get(i));
                    mainList.add(ovc);
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return mainList;
    }
    private List getListOfOvc(List list)
    {
        List mainlist=new ArrayList();
        if(list !=null && !list.isEmpty())
        {
            Ovc ovc=null;
            for(Object s:list)
            {
                Object[] obj=(Object[])s;
                ovc=(Ovc)obj[1];
                mainlist.add(ovc);
            }
        }
        return mainlist;
    }
    /*private List execSummStatisticsQueryPerMonth(String sex,String malesql, String femalesql,String bothGendersql)
    {
        List list=new ArrayList();
        List mainlist=new ArrayList();
        if(!sex.equalsIgnoreCase("All"))
        {
            if(sex.equalsIgnoreCase("Male"))
            {
                list=execReportQuery(malesql);
                return list;
            }
            else if(sex.equalsIgnoreCase("Female"))
            {
                list=execReportQuery(femalesql);
                return list;
            }
            else if(sex.equalsIgnoreCase("Both gender"))
            {
                list=execReportQuery(bothGendersql);
                return list;
            }
        }
        list=execReportQuery(malesql);
        mainlist.add(list.size());
        list=execReportQuery(femalesql);
        mainlist.add(list.size());
        return mainlist;
    }*/
    private List execSummStatisticsQuery(String sex,String malesql, String femalesql,String bothGendersql)
    {
        List list=new ArrayList();
        List mainlist=new ArrayList();
        if(!sex.equalsIgnoreCase("All"))
        {
            if(sex.equalsIgnoreCase("Male"))
            {
                list=execReportQuery(malesql);
                return list;
            }
            else if(sex.equalsIgnoreCase("Female"))
            {
                list=execReportQuery(femalesql);
                return list;
            }
            else if(sex.equalsIgnoreCase("Both gender"))
            {
                list=execReportQuery(bothGendersql);
                return list;
            }
        }
        list=execReportQuery(malesql);
        mainlist.add(list.size());
        list=execReportQuery(femalesql);
        mainlist.add(list.size());
        return mainlist;
    }
    private String getQueryCriteria(String[] params)
    {
        String additionalQueryCriteria=util.getQueryCriteria(params);
        return additionalQueryCriteria;
    }
    private String getAdditionalEnrollmentQuery(String additionalEnrollmentQuery)
    {
        return util.getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
    }
    private String getAdditionalServiceQuery(String additionalServiceQuery)
    {
        return util.getAdditionalServiceQuery(additionalServiceQuery);
    }
    public List getOvcMthlySummaryData(String[] params) throws Exception
    {
        List mainList=new ArrayList();
        List list=new ArrayList();
        List list2=new ArrayList();
        List list3=new ArrayList();
        String additionalQueryCriteria=util.getOrgQueryCriteria(params);
        String[] dateParams={params[3],params[4],params[5],params[6]};
        String[] dateArray={util.getStartDate(dateParams),util.getEndDate(dateParams)};

        list.add(getNoOfOvcNewlyEnrolled(additionalQueryCriteria,dateArray));
        list.add(getNoOfOvcCurrentlyEnrolled(additionalQueryCriteria,dateArray));
        list.add(getNoOfOvcServedForMSF(additionalQueryCriteria,dateArray));
        list.add(getNoOfOvcSupportedOnHIVAIDSServicesForMSF(additionalQueryCriteria,dateArray));
        list.add(getNoOfOvcWithdrawn("All",additionalQueryCriteria," "," ",null));
        list.add(getNoOfOvcOutOfProgram(additionalQueryCriteria,"<b>No of <b>VC known to have died"," ", "Known death"));
        list.add(getNoOfOvcOutOfProgram(additionalQueryCriteria,"<b>No of <b>VC who have migrated</b>"," ", "Migrated"));
        list.add(getNoOfOvcOutOfProgram(additionalQueryCriteria,"<b>No of <b>VC lost to followup</b>"," ", "Loss to follow-up"));
        
        list2.add(getNoOfOvcAbove18YearsWhoGraduated("All",additionalQueryCriteria," "," ",null));

        list3.add(getNoOfOvcAccessingHealthCare(additionalQueryCriteria,dateArray));
        list3.add(getNoOfOvcAccessingNutritional(additionalQueryCriteria,dateArray));
        list3.add(getNoOfOvcAccessingShelter(additionalQueryCriteria,dateArray));
        list3.add(getNoOfOvcAccessingEducational(additionalQueryCriteria,dateArray));
        list3.add(getNoOfOvcAccessingPsychoSupport(additionalQueryCriteria,dateArray));
        list3.add(getNoOfOvcAccessingProtection(additionalQueryCriteria,dateArray));

        mainList.add(list);
        mainList.add(list2);
        mainList.add(list3);
        return mainList;
        //list.add(getNoOfOvcAccessingEconStrengthening(additionalQueryCriteria,dateArray));
        //return list;
    }
    public List getOvcMthlyEconStrengtheningData(String[] params) throws Exception
    {
        List list=new ArrayList();
        List labelList=new ArrayList();
        List mainList=new ArrayList();
        String[] hviParams={params[0],params[1],params[2],null,null,null,params[3],params[4],params[5],params[6],params[7]};
        String additionalQueryCriteria=util.getQueryCriteria(params);
        String[] dateParams={params[3],params[4],params[5],params[6]};
        String[] dateArray={util.getStartDate(dateParams),util.getEndDate(dateParams)};
        String[] hhAgeDisaggregationLabel={" ","0-17","18-59",">60","Total(M)","0-17","18-59",">60","Total(F)","Grand Total"};
        for(int i=0; i<hhAgeDisaggregationLabel.length; i++)
        {
            labelList.add("<b>"+hhAgeDisaggregationLabel[i]+"</b>");
        }
        list.addAll(getNoOfOvcAccessingEconStrengthening(additionalQueryCriteria,dateArray));
        additionalQueryCriteria=util.getHVIOrgUnitQuery(hviParams);
        mainList.add(list);
        mainList.add(labelList);
        mainList.add(getNoOfHouseholdsProvidedWithAtleastOneService(additionalQueryCriteria,dateArray));
        mainList.add(getNoOfHouseholdsSupportedToAccessHIVCare(additionalQueryCriteria,dateArray));
        mainList.add(getNoOfHouseholdHeadsReceivingEconomicStrenghtening(additionalQueryCriteria,dateArray));
        
        return mainList;
    }
    public String[] getHVIParams(String[] params)
    {
        String[] hviParams={params[0],params[1],params[2],null,null,null,params[7], params[8], params[9],params[10],params[18],params[19]};
        return hviParams;
    }
    public List getSummaryStatisticList(String[] params, String[] reqparams) throws Exception
    {
        String additionalEnrollmentQuery=util.getAdditionalEnrollmentQuery(params[7], params[8], params[9], params[10]);
        String additionalServiceQuery=util.getAdditionalServiceQuery(params[7], params[8], params[9], params[10]);
        String[] dateParams={params[7], params[8], params[9], params[10]};
        String enrollmentEndDateQuery=util.getEnrollmentEndDateQuery(dateParams);
        ageQuery=util.getAgeCriteria(params[15], params[16]);
        String sex=reqparams[2];
        int index=Integer.parseInt(reqparams[1]);
        String additionalQueryCriteria=util.getSummStatQueryCriteria(params);
        List list=new ArrayList();
        String[] periodParams={params[7], params[8], params[9],params[10]};
        String[] hviParams=getHVIParams(params);//{params[0],params[1],params[2],null,null,null,params[7], params[8], params[9],params[10],params[18],params[19]};
        String hviQueryCriteria=util.getHVIReportCriteria(hviParams);
        String hviServiceQueryCriteria=util.getHVIServiceReportCriteria(hviParams);
        String hviPeriodQuery=" ";
        if(!params[7].equalsIgnoreCase("all") && !params[8].equalsIgnoreCase("all") && !params[9].equalsIgnoreCase("all") && !params[10].equalsIgnoreCase("all"))
        hviPeriodQuery=util.getHhePeriodCriteria(util.getStartDate(periodParams),util.getEndDate(periodParams));

        if(index==1)
        list=getListOfOvcEnrolled(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery);
        else if(index==2)
        list=getListOfOvcServed(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery);

        if(index==3)
        list=getListOfHivPosOvc(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery);

        else if(index==4)
        list=getListHivNegOvc(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery);
        else if(index==5)
        list=getListOfHivUnknownOvc(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery);


        else if(index==6)
        list=getListOfHivPosOvcServed(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery);
        else if(index==7)
        list=getListOfHivNegOvcServed(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery);
        else if(index==8)
        list=getListOfHivUnknownOvcServed(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery);
        else if(index==9)
        list=getListOfOvcWithoutServeRecords(sex,additionalQueryCriteria,enrollmentEndDateQuery,additionalServiceQuery,ageQuery);
        //list=getListOfOvcReceivingThreeServices(sex,additionalQueryCriteria);
        else if(index==10)
        list=getListOfOvcProvidedPsychosocialServices(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery);
        else if(index==11)
        list=getListOfOvcProvidedNutritionalServices(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery);
        else if(index==12)
        list=getListOfOvcHIVPositiveProvidedNutritionalServices(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery);
        else if(index==13)
        list=getListOfOvcHIVNegativeProvidedNutritionalServices(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery);
        else if(index==14)
        list=getListOfOvcHIVUnknownProvidedNutritionalServices(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery);
        else if(index==15)
        list=getListOfOvcProvidedHealthServices(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery);
        else if(index==16)
        list=getListOfOvcProvidedEducationalServices(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery);
        else if(index==17)
        list=getListOfOvcProvidedProtectionServices(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery);
        else if(index==18)
        list=getListOfOvcProvidedShelterAndCareServices(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery);
        else if(index==19)
        list=getListOfOvcWithdrawn(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery);
        
        
        /*if(index==18)
        list=getListOfHivPosOvc(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery);
        
        else if(index==19)
        list=getListHivNegOvc(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery);
        else if(index==20)
        list=getListOfHivUnknownOvc(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery);*/
        //else if(index==14)
        //list=getListOfOvcInOphanage(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery);
        else if(index==20)
        list=getListOfOvcOutOfSchool(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery);
        else if(index==21)
        list=getListOfOvcCurrentlyInSchool(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery);
        else if(index==22)
        list=getListOvcWithoutBirthCert(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery);
        else if(index==23)
        {
            setNoOfServices(sex,additionalQueryCriteria,additionalServiceQuery,ageQuery);
            list=getListOfOvcThatReceivedThreeOrMoreServices(sex,additionalQueryCriteria,additionalServiceQuery,ageQuery);
        }
        else if(index==24)
        {
            setNoOfServices(sex,additionalQueryCriteria,additionalServiceQuery,ageQuery);
            list=getListOfOvcThatReceivedLessThanThreeServices(sex,additionalQueryCriteria,additionalServiceQuery,ageQuery); 
        }
        else if(index>25 && index <29)
        {
            if(index==26)
            list.add(getListOfCaregiversServed(sex,hviQueryCriteria,hviPeriodQuery,hviServiceQueryCriteria,ageQuery));
            else if(index==27)
            list.add(getListOfHouseholdsEnrolled(sex,hviQueryCriteria,hviPeriodQuery,ageQuery));
            //else if(index==27)
            //list.add(getListOfHouseholdsServed(sex,hviQueryCriteria,hviPeriodQuery,hviServiceQueryCriteria,ageQuery));
            //else if(index==28)
            //list.add(getListOfHousholdProvidedBasicExternalSupport(sex,hviQueryCriteria,hviPeriodQuery,hviServiceQueryCriteria,additionalServiceQuery));
            //return util.getOvcWithCaregiverInfo(list);
        }
        return list;
        //return util.getOvcWithCaregiverInfo(list);
    }
    
    /*public List getOvcEnrolledSummStatisticsPerMth(String sex,String[] params) throws Exception
    {
        String additionalQueryCriteria=util.getSummStatQueryCriteria(params);
        String[] dateParams=getEnrollmentDates(params);
        List list=new ArrayList();
        list.add(getNoOfOvcEnrolledPerMth(sex,additionalQueryCriteria,dateParams));
        list.add(getNoOfOvcServedPerMth(sex,additionalQueryCriteria,dateParams));
        list.add(getNoReceivingThreeServicesPerMth(sex,additionalQueryCriteria,dateParams));
        list.add(getNoHivPosOvcPerMth(sex,additionalQueryCriteria,dateParams));
        list.add(getNoHivPosReceivingThreeServicesPerMth(sex,additionalQueryCriteria,dateParams));
        list.add(getNoHivNegOvcPerMth(sex,additionalQueryCriteria,dateParams));
        list.add(getNoHivUnknownOvcPerMth(sex,additionalQueryCriteria,dateParams));
        list.add(getNoOvcInOphanagePerMth(sex,additionalQueryCriteria,dateParams));
        list.add(getNoOfOvcOutOfSchoolPerMth(sex,additionalQueryCriteria,dateParams));
        list.add(getNoOfOvcCurrentlyInSchoolPerMth(sex,additionalQueryCriteria,dateParams));
        list.add(getNoOvcWithoutBirthCertPerMth(sex,additionalQueryCriteria,dateParams));

        return list;
    }*/
    public List getOvcEnrolledSummStatistics(String sex,String[] params) throws Exception
    {
        String additionalQueryCriteria=util.getSummStatQueryCriteria(params);
        String[] hviParams=getHVIParams(params);//{params[0],params[1],params[2],null,null,null,null,null,null,null,params[18],params[19]};
        String[] periodParams={params[7], params[8], params[9],params[10]};
        String enrollmentEndDateQuery=util.getEnrollmentEndDateQuery(periodParams);
        String hviQueryCriteria=util.getHVIReportCriteria(hviParams);
        String hviServiceQueryCriteria=" "; //util.getHVIServiceReportCriteria(params);
        String hviPeriodQuery=" ";
        if(!params[7].equalsIgnoreCase("all") && !params[8].equalsIgnoreCase("all") && !params[9].equalsIgnoreCase("all") && !params[10].equalsIgnoreCase("all"))
        hviPeriodQuery=util.getHhePeriodCriteria(util.getStartDate(periodParams),util.getEndDate(periodParams));
        String additionalEnrollmentQuery=util.getAdditionalEnrollmentQuery(params[7], params[8], params[9], params[10]);
        String additionalServiceQuery=util.getAdditionalServiceQuery(params[7], params[8], params[9], params[10]);
        String additionalWithdrawalQuery=util.getWithdrawalPeriodQuery(params[7], params[8], params[9], params[10]);
        //String additionalServiceQuery=util.getAdditionalServiceQuery(params[11], params[12], params[13], params[14]);
        ageQuery=util.getAgeCriteria(params[15], params[16]);
        List list=new ArrayList();
        list.add(getNoOfOvcEnrolled(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery));
        list.add(getNoOfOvcServed(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery));
        list.add(getNoHivPosOvc(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery));
        list.add(getNoHivNegOvc(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery));
        list.add(getNoHivUnknownOvc(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery));


        list.add(getNoOfHivPositiveOvcServed(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery));
        list.add(getNoOfHivNegativeOvcServed(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery));
        list.add(getNoOfHivUnknownOvcServed(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery));

        list.add(getNoOfOvcWithoutServiceRecords(sex,additionalQueryCriteria,enrollmentEndDateQuery,additionalServiceQuery,ageQuery));
        list.add(getNoOfOvcProvidedPsychosocialServices(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery));
        list.add(getNoOfOvcProvidedNutritionalServices(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery));
        list.add(getNoOfHIVPositiveOvcProvidedNutritionalServices(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery));
        list.add(getNoOfHIVNegativeOvcProvidedNutritionalServices(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery));
        list.add(getNoOfHIVUnknownOvcProvidedNutritionalServices(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery));
        list.add(getNoOfOvcProvidedHealthServices(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery));
        list.add(getNoOfOvcProvidedEducationalServices(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery));
        list.add(getNoOfOvcProvidedProtectionServices(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery));
        list.add(getNoOfOvcProvidedShelterAndCareServices(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery));
        list.add(getNoOfOvcWithdrawn(sex,additionalQueryCriteria,additionalWithdrawalQuery,additionalServiceQuery,ageQuery));
        

        /*list.add(getNoHivPosOvc(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery));
        list.add(getNoHivNegOvc(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery));
        list.add(getNoHivUnknownOvc(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery));*/
        //list.add(getNoOvcInOphanage(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery));
        list.add(getNoOfOvcOutOfSchool(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery));
        list.add(getNoOfOvcCurrentlyInSchool(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery));
        list.add(getNoOvcWithoutBirthCert(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery));
        setNoOfServices(sex,additionalQueryCriteria,additionalServiceQuery,ageQuery);
        list.add(getNoOfOvcThatReceivedThreeOrMoreServices());
        list.add(getNoOfOvcThatReceivedLessThanThreeServices());
        list.add(getTotalNoOfOvcThatShowedAtleastOneScoreImprovement(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery));

        //list.add(getNoOfHouseholdsEnrolled(sex,hviQueryCriteria,hviPeriodQuery,ageQuery));
        list.add(getNoOfCaregiversServed(sex,hviQueryCriteria,hviPeriodQuery,hviServiceQueryCriteria,ageQuery));
        //list.add(getNoOfHousholdProvidedBasicExternalSupport(sex,hviQueryCriteria,additionalServiceQuery,ageQuery));
        //list.add(getNoOfOrganizationsWithAtleastOneScoreImprovement(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery));
        
        return list;
    }
    public List getSummaryStaticsCountWithoutGenderDisaggregation(String[] params)
    {
        List list=new ArrayList();
        String additionalQueryCriteria=util.getSummStatQueryCriteria(params);
        String[] hviParams=getHVIParams(params);
        String[] periodParams={params[7], params[8], params[9],params[10]};
        String hviQueryCriteria=util.getHVIReportCriteria(hviParams);

        String hviPeriodQuery=" ";
        if(!params[7].equalsIgnoreCase("all") && !params[8].equalsIgnoreCase("all") && !params[9].equalsIgnoreCase("all") && !params[10].equalsIgnoreCase("all"))
        hviPeriodQuery=util.getHhePeriodCriteria(util.getStartDate(periodParams),util.getEndDate(periodParams));
        String additionalEnrollmentQuery=util.getAdditionalEnrollmentQuery(params[7], params[8], params[9], params[10]);
        try
        {
            list.add(getNoOfHouseholdsEnrolled(hviQueryCriteria,hviPeriodQuery));
            list.add(getNoOfOrganizationsWithAtleastOneScoreImprovement(additionalQueryCriteria,additionalEnrollmentQuery));
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return list;
    }
    public String[] getDatePeriod(String[] params)
    {
        String dateFormat="yyyy-MM-dd";
        java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat(dateFormat);
        Calendar cal=Calendar.getInstance();
        String[] dateArray=new String[12];
        int j=0;
        for(int i=0; i<params.length-1; i++)
        {
            String[] dateParts=params[i].split("-");
            int year=Integer.parseInt(dateParts[0]);
            int month=Integer.parseInt(dateParts[1])-1;
            int day=Integer.parseInt(dateParts[2]);
            cal.set(year, month, day);
            dateArray[j]=sdf.format(cal.getTime());
            cal.add(Calendar.MONTH, 1);
            cal.add(Calendar.DATE, -1);
            dateArray[++j]=sdf.format(cal.getTime());
            j++;
        }
        return dateArray;
    }
    public String[] getEnrollmentDates(String[] params)
    {
        String dateFormat="yyyy-MM-dd";
        int selectedMonth=0;
        int selectedYear=0;
        int period=0;
        try
        {
            selectedMonth=Integer.parseInt(params[3])-1;
            selectedYear=Integer.parseInt(params[4]);
            period=6;
        }
        catch(NumberFormatException nfe)
        {
            nfe.printStackTrace();
        }
        java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat(dateFormat);
        Calendar cal=Calendar.getInstance();
        String[] dateArray=new String[7];
        cal.set(selectedYear, selectedMonth, 01);
        dateArray[0]=sdf.format(cal.getTime());
        for(int i=1; i<7; i++)
        {
            cal.add(Calendar.MONTH, 1);
            dateArray[i]=sdf.format(cal.getTime());
        }
        return dateArray;
    }

   /* public List getNoOfOvcEnrolledPerMth(String sex,String additionalQueryCriteria,String[] dateArray) throws Exception
    {
        List mainlist=new ArrayList();
        mainlist.add("No of OVC enrolled");
        String[] periods=getDatePeriod(dateArray);
        for(int i=0; i<periods.length-1; i+=2)
        {
            String malesql="From Ovc ovc where "+util.getGenderCriteria(maleGender)+" and ovc.dateEnrollment between '"+periods[i]+"' and '"+periods[i+1]+"' "+additionalQueryCriteria;
            String femalesql="From Ovc ovc where "+util.getGenderCriteria(femaleGender)+" and ovc.dateEnrollment between '"+periods[i]+"' and '"+periods[i+1]+"' "+additionalQueryCriteria;
            String bothGendersql="From Ovc ovc where ovc.ovcId is not null and ovc.dateEnrollment between '"+periods[i]+"' and '"+periods[i+1]+"' "+additionalQueryCriteria;
            mainlist.addAll(execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql));      
        }
        return mainlist;
    }
    public List getNoOfOvcServedPerMth(String sex,String additionalQueryCriteria,String[] dateArray) throws Exception
    {
        List mainlist=new ArrayList();
        String[] periods=getDatePeriod(dateArray);
        String serviceCriteria="";
        String malesql="";
        String femalesql="";
        String bothGendersql="";
        mainlist.add("No of OVC Served");
        
        for(int i=0; i<periods.length-1; i+=2)
        {
            serviceCriteria=" and ovc.ovcId in (select service.ovcId from OvcService service where service.servicedate between '"+periods[i]+"' and '"+periods[i+1]+"') ";
            malesql="From Ovc ovc where "+util.getGenderCriteria(maleGender)+" "+serviceCriteria+additionalQueryCriteria;
            femalesql="From Ovc ovc where "+util.getGenderCriteria(femaleGender)+" "+serviceCriteria+additionalQueryCriteria;
            bothGendersql="From Ovc ovc where ovc.ovcId is not null "+serviceCriteria+additionalQueryCriteria;
            mainlist.addAll(execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql));
        }
        
        return mainlist;
    }
    public List getNoReceivingThreeServicesPerMth(String sex,String additionalQueryCriteria,String[] dateArray) throws Exception
    {
        List mainlist=new ArrayList();
        mainlist.add("No of OVC that received three services at enrollment");
        String[] periods=getDatePeriod(dateArray);
        for(int i=0; i<periods.length-1; i+=2)
        {
            String malesql="From Ovc ovc where "+util.getGenderCriteria(maleGender)+" and ovc.serviceEnrollment like '%Psychosocial support%' and ovc.serviceEnrollment like '%Nutrition counseling%' and ovc.serviceEnrollment like '%Health referral%' and ovc.dateEnrollment between '"+periods[i]+"' and '"+periods[i+1]+"' "+additionalQueryCriteria;
            String femalesql="From Ovc ovc where "+util.getGenderCriteria(femaleGender)+" and ovc.serviceEnrollment like '%Psychosocial support%' and ovc.serviceEnrollment like '%Nutrition counseling%' and ovc.serviceEnrollment like '%Health referral%' and ovc.dateEnrollment between '"+periods[i]+"' and '"+periods[i+1]+"' "+additionalQueryCriteria;
            String bothGendersql="From Ovc ovc where ovc.ovcId is not null and ovc.serviceEnrollment like '%Psychosocial support%' and ovc.serviceEnrollment like '%Nutrition counseling%' and ovc.serviceEnrollment like '%Health referral%' and ovc.dateEnrollment between '"+periods[i]+"' and '"+periods[i+1]+"' "+additionalQueryCriteria;
            mainlist.addAll(execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql));
        }
        return mainlist;
    }
    public List getNoHivPosReceivingThreeServicesPerMth(String sex,String additionalQueryCriteria,String[] dateArray) throws Exception
    {
        List mainlist=new ArrayList();
        String status=NomisConstant.HIV_POSITIVE;
        mainlist.add("No of HIV positive OVC that received three services at enrollment");
        String[] periods=getDatePeriod(dateArray);
        for(int i=0; i<periods.length-1; i+=2)
        {
            String malesql="From Ovc ovc where "+util.getGenderCriteria(maleGender)+" and "+util.getHivStatusCriteria(status)+" and ovc.serviceEnrollment like '%Psychosocial support%' and ovc.serviceEnrollment like '%Nutrition counseling%' and ovc.serviceEnrollment like '%Health referral%' and ovc.dateEnrollment between '"+periods[i]+"' and '"+periods[i+1]+"' "+additionalQueryCriteria;
            String femalesql="From Ovc ovc where "+util.getGenderCriteria(femaleGender)+" and "+util.getHivStatusCriteria(status)+" and ovc.serviceEnrollment like '%Psychosocial support%' and ovc.serviceEnrollment like '%Nutrition counseling%' and ovc.serviceEnrollment like '%Health referral%' and ovc.dateEnrollment between '"+periods[i]+"' and '"+periods[i+1]+"' "+additionalQueryCriteria;
            String bothGendersql="From Ovc ovc where "+util.getHivStatusCriteria(status)+" and ovc.serviceEnrollment like '%Psychosocial support%' and ovc.serviceEnrollment like '%Nutrition counseling%' and ovc.serviceEnrollment like '%Health referral%' and ovc.dateEnrollment between '"+periods[i]+"' and '"+periods[i+1]+"' "+additionalQueryCriteria;
            mainlist.addAll(execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql));
        }
        return mainlist;
    }
    public List getNoHivPosOvcPerMth(String sex,String additionalQueryCriteria,String[] dateArray) throws Exception
    {
        List mainlist=new ArrayList();
        mainlist.add("No of HIV positive OVC enrolled");
        String status=NomisConstant.HIV_POSITIVE;
        String[] periods=getDatePeriod(dateArray);
        for(int i=0; i<periods.length-1; i+=2)
        {
        String malesql="From Ovc ovc where "+util.getHivStatusCriteria(status)+" and "+util.getGenderCriteria(maleGender)+" and ovc.dateEnrollment between '"+periods[i]+"' and '"+periods[i+1]+"' "+additionalQueryCriteria;
        String femalesql="From Ovc ovc where "+util.getHivStatusCriteria(status)+" and "+util.getGenderCriteria(femaleGender)+" and ovc.dateEnrollment between '"+periods[i]+"' and '"+periods[i+1]+"' "+additionalQueryCriteria;
        String bothGendersql="From Ovc ovc where "+util.getHivStatusCriteria(status)+" and ovc.dateEnrollment between '"+periods[i]+"' and '"+periods[i+1]+"' "+additionalQueryCriteria;
        mainlist.addAll(execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql));
        }
        return mainlist;
    }
    public List getNoHivNegOvcPerMth(String sex,String additionalQueryCriteria,String[] dateArray) throws Exception
    {
        List mainlist=new ArrayList();
        mainlist.add("No of HIV negative OVC enrolled");
        String status=NomisConstant.HIV_NEGATIVE;
        String[] periods=getDatePeriod(dateArray);
        for(int i=0; i<periods.length-1; i+=2)
        {
        String malesql="From Ovc ovc where "+util.getHivStatusCriteria(status)+" and "+util.getGenderCriteria(maleGender)+" and ovc.dateEnrollment between '"+periods[i]+"' and '"+periods[i+1]+"' "+additionalQueryCriteria;
        String femalesql="From Ovc ovc where "+util.getHivStatusCriteria(status)+" and "+util.getGenderCriteria(femaleGender)+" and ovc.dateEnrollment between '"+periods[i]+"' and '"+periods[i+1]+"' "+additionalQueryCriteria;
        String bothGendersql="From Ovc ovc where "+util.getHivStatusCriteria(status)+" and ovc.dateEnrollment between '"+periods[i]+"' and '"+periods[i+1]+"' "+additionalQueryCriteria;
        mainlist.addAll(execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql));
        }
        return mainlist;
    }
    public List getNoHivUnknownOvcPerMth(String sex,String additionalQueryCriteria,String[] dateArray) throws Exception
    {
        List mainlist=new ArrayList();
        mainlist.add("No of OVC with HIV status unknown");
        String status=NomisConstant.HIV_UNKNOWN;
        String[] periods=getDatePeriod(dateArray);
        for(int i=0; i<periods.length-1; i+=2)
        {
        String malesql="From Ovc ovc where "+util.getHivStatusCriteria(status)+" and "+util.getGenderCriteria(maleGender)+" and ovc.dateEnrollment between '"+periods[i]+"' and '"+periods[i+1]+"' "+additionalQueryCriteria;
        String femalesql="From Ovc ovc where "+util.getHivStatusCriteria(status)+" and "+util.getGenderCriteria(femaleGender)+" and ovc.dateEnrollment between '"+periods[i]+"' and '"+periods[i+1]+"' "+additionalQueryCriteria;
        String bothGendersql="From Ovc ovc where "+util.getHivStatusCriteria(status)+" and ovc.dateEnrollment between '"+periods[i]+"' and '"+periods[i+1]+"' "+additionalQueryCriteria;
        mainlist.addAll(execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql));
        }
        return mainlist;
    }
    public List getNoOvcInOphanagePerMth(String sex,String additionalQueryCriteria,String[] dateArray) throws Exception
    {
        List mainlist=new ArrayList();
        mainlist.add("No of OVC in orphanage homes");
        String[] periods=getDatePeriod(dateArray);
        for(int i=0; i<periods.length-1; i+=2)
        {
        String malesql="From Ovc ovc where ovc.orphanage='Yes' and "+util.getGenderCriteria(maleGender)+" and ovc.dateEnrollment between '"+periods[i]+"' and '"+periods[i+1]+"' "+additionalQueryCriteria;
        String femalesql="From Ovc ovc where ovc.orphanage='Yes' and "+util.getGenderCriteria(femaleGender)+" and ovc.dateEnrollment between '"+periods[i]+"' and '"+periods[i+1]+"' "+additionalQueryCriteria;
        String bothGendersql="From Ovc ovc where ovc.orphanage='Yes' and ovc.dateEnrollment between '"+periods[i]+"' and '"+periods[i+1]+"' "+additionalQueryCriteria;
        mainlist.addAll(execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql));
        }
        return mainlist;
    }
    public List getNoOfOvcOutOfSchoolPerMth(String sex,String additionalQueryCriteria,String[] dateArray) throws Exception
    {
        List mainlist=new ArrayList();
        mainlist.add("No of OVC out of school");
        String[] periods=getDatePeriod(dateArray);
        for(int i=0; i<periods.length-1; i+=2)
        {
        String malesql="From Ovc ovc where ovc.schoolStatus='No' and "+util.getGenderCriteria(maleGender)+" and ovc.dateEnrollment between '"+periods[i]+"' and '"+periods[i+1]+"' "+additionalQueryCriteria;
        String femalesql="From Ovc ovc where ovc.schoolStatus='No' and "+util.getGenderCriteria(femaleGender)+" and ovc.dateEnrollment between '"+periods[i]+"' and '"+periods[i+1]+"' "+additionalQueryCriteria;
        String bothGendersql="From Ovc ovc where ovc.schoolStatus='No' and ovc.dateEnrollment between '"+periods[i]+"' and '"+periods[i+1]+"' "+additionalQueryCriteria;
        mainlist.addAll(execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql));
        }
        return mainlist;
    }
    public List getNoOfOvcCurrentlyInSchoolPerMth(String sex,String additionalQueryCriteria,String[] dateArray) throws Exception
    {
        List mainlist=new ArrayList();
        mainlist.add("No of OVC currently in school");
        String[] periods=getDatePeriod(dateArray);
        for(int i=0; i<periods.length-1; i+=2)
        {
        String malesql="From Ovc ovc where ovc.schoolStatus='Yes' and "+util.getGenderCriteria(maleGender)+" and ovc.dateEnrollment between '"+periods[i]+"' and '"+periods[i+1]+"' "+additionalQueryCriteria;
        String femalesql="From Ovc ovc where ovc.schoolStatus='Yes' and "+util.getGenderCriteria(femaleGender)+" and ovc.dateEnrollment between '"+periods[i]+"' and '"+periods[i+1]+"' "+additionalQueryCriteria;
        String bothGendersql="From Ovc ovc where ovc.schoolStatus='Yes' and ovc.dateEnrollment between '"+periods[i]+"' and '"+periods[i+1]+"' "+additionalQueryCriteria;
        mainlist.addAll(execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql));
        }
        return mainlist;
    }
    public List getNoOvcWithoutBirthCertPerMth(String sex,String additionalQueryCriteria,String[] dateArray) throws Exception
    {
        List mainlist=new ArrayList();
        mainlist.add("No of OVC without birth certificate");
        String[] periods=getDatePeriod(dateArray);
        for(int i=0; i<periods.length-1; i+=2)
        {
        String malesql="From Ovc ovc where ovc.birthCertificate='No' and "+util.getGenderCriteria(maleGender)+" and ovc.dateEnrollment between '"+periods[i]+"' and '"+periods[i+1]+"' "+additionalQueryCriteria;
        String femalesql="From Ovc ovc where ovc.birthCertificate='No' and "+util.getGenderCriteria(femaleGender)+" and ovc.dateEnrollment between '"+periods[i]+"' and '"+periods[i+1]+"' "+additionalQueryCriteria;
        String bothGendersql="From Ovc ovc where ovc.birthCertificate='No' and ovc.dateEnrollment between '"+periods[i]+"' and '"+periods[i+1]+"' "+additionalQueryCriteria;
        mainlist.addAll(execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql));
        }
        return mainlist;
    }*/     
    private List getListOfOvcEnrolled(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        String malesql=hhQueryPart+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String femalesql=hhQueryPart+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;;
        String bothGendersql=hhQueryPart+"ovc.ovcId is not null "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;;
        List list=execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql);
        mainlist=getListOfOvc(list);
        
        return mainlist;
    }

    private List getListOfOvcServed(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();//hhOvcAndServiceQueryPart
        if(!additionalServiceQuery.equals(" "))
            additionalServiceQuery=" and "+additionalServiceQuery;
        //String serviceCriteria=" and ovc.ovcId in (select distinct service.ovcId from OvcService service "+additionalServiceQuery+" )";
        String malesql="select distinct service.ovcId "+hhOvcAndServiceQueryPart+additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+ageQuery+additionalServiceQuery;
        String femalesql="select distinct service.ovcId "+hhOvcAndServiceQueryPart+additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender)+ageQuery+additionalServiceQuery;
        String bothGendersql="select distinct service.ovcId "+hhOvcAndServiceQueryPart+" and ovc.ovcId is not null "+additionalQueryCriteria+ageQuery+additionalServiceQuery;
        
        List list=execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql);
        mainlist=getListOfOvcByOvcId(list);
           
        return mainlist;
    }
    public List getListOfHouseholdsEnrolled(String sex,String additionalQueryCriteria, String hviPeriodQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        String malesql="From HouseholdEnrollment hhe where "+util.getHhGenderCriteria(maleGender)+" "+additionalQueryCriteria+hviPeriodQuery;
        String femalesql="From HouseholdEnrollment hhe where "+util.getHhGenderCriteria(femaleGender)+" "+additionalQueryCriteria+hviPeriodQuery;
        String bothGendersql="From HouseholdEnrollment hhe where hhe.hhUniqueId is not null "+additionalQueryCriteria+hviPeriodQuery;
        mainlist.addAll(execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql));
        //System.err.println(malesql);
        return mainlist;
    }
    /*public List getListOfHouseholdsServed(String sex,String additionalQueryCriteria,String hviPeriodQuery,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        List list=new ArrayList();
        if(!additionalServiceQuery.equals(" "))
        additionalServiceQuery=additionalServiceQuery;
        String malesql="select distinct hhs.hhUniqueId from HouseholdService hhs, HouseholdEnrollment hhe where hhe.hhUniqueId=hhs.hhUniqueId and "+util.getHhGenderCriteria(maleGender)+" "+additionalQueryCriteria+additionalServiceQuery;
        String femalesql="select distinct hhs.hhUniqueId from HouseholdService hhs, HouseholdEnrollment hhe where hhe.hhUniqueId=hhs.hhUniqueId and "+util.getHhGenderCriteria(femaleGender)+" "+additionalQueryCriteria+additionalServiceQuery;
        String bothGendersql="select distinct hhs.hhUniqueId from HouseholdService hhs, HouseholdEnrollment hhe where hhe.hhUniqueId=hhs.hhUniqueId "+additionalQueryCriteria+additionalServiceQuery;
        System.err.println("bothGendersql in getListOfHouseholdsServed is "+bothGendersql);
        
        list.addAll(execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql));
        Object[] record=null;
        HouseholdVulnerabilityIndex hvi=null;
        HouseholdVulnerabilityIndexDao hviDao=new HouseholdVulnerabilityIndexDaoImpl();
        int count=0;
        for(int i=0; i< list.size(); i++)
        {
            String id=(String)list.get(i);
            hvi=(HouseholdVulnerabilityIndex)hviDao.getHouseholdVulnerabilityIndex(id);
            if(hvi !=null)
            mainlist.add(hvi);
            System.err.println("id is "+id+" at "+count);
        }
        
        return mainlist;
    }*/
    private List getListOfHivPosOvcServed(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        String status=NomisConstant.HIV_POSITIVE;
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        if(!additionalServiceQuery.equals(" "))
            additionalServiceQuery=" and "+additionalServiceQuery;
        String ovcHivQuery=util.getActiveHivStatusCriteria(status,NomisConstant.OVC_TYPE);
        //String serviceCriteria=" and ovc.ovcId in (select distinct service.ovcId from OvcService service "+additionalServiceQuery+" )";
        String malesql="select distinct service.ovcId "+hhOvcServiceAndHivQueryPart+additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+ovcHivQuery+ageQuery+additionalServiceQuery;
        String femalesql="select distinct service.ovcId "+hhOvcServiceAndHivQueryPart+additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender)+ovcHivQuery+" "+ageQuery+additionalServiceQuery;
        String bothGendersql="select distinct service.ovcId "+hhOvcServiceAndHivQueryPart+additionalQueryCriteria+ovcHivQuery+ageQuery+additionalServiceQuery;
        //mainlist.addAll(execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql));
        //System.err.println("malesql is "+malesql);
        mainlist=getListOfOvcByOvcId(execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql));
        return mainlist;
    }
    private List getListOfHivNegOvcServed(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        String status=NomisConstant.HIV_NEGATIVE;
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        if(!additionalServiceQuery.equals(" "))
            additionalServiceQuery=" and "+additionalServiceQuery;
        String ovcHivQuery=util.getActiveHivStatusCriteria(status,NomisConstant.OVC_TYPE);
        //String serviceCriteria=" and ovc.ovcId in (select distinct service.ovcId from OvcService service "+additionalServiceQuery+" )";
        String malesql="select distinct service.ovcId "+hhOvcServiceAndHivQueryPart+additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+ovcHivQuery+ageQuery+additionalServiceQuery;
        String femalesql="select distinct service.ovcId "+hhOvcServiceAndHivQueryPart+additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender)+ovcHivQuery+ageQuery+additionalServiceQuery;
        String bothGendersql="select distinct service.ovcId "+hhOvcServiceAndHivQueryPart+additionalQueryCriteria+ovcHivQuery+ageQuery+additionalServiceQuery;
        //System.err.println("malesql is "+malesql);
        mainlist=getListOfOvcByOvcId(execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql));
        return mainlist;
    }
    private List getListOfHivUnknownOvcServed(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        String status=NomisConstant.HIV_UNKNOWN;
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        if(!additionalServiceQuery.equals(" "))
            additionalServiceQuery=" and "+additionalServiceQuery;
        String ovcHivQuery=util.getActiveHivStatusCriteria(status,NomisConstant.OVC_TYPE);
        //String serviceCriteria=" and ovc.ovcId in (select distinct service.ovcId from OvcService service "+additionalServiceQuery+" )";
        String malesql="select distinct service.ovcId "+hhOvcServiceAndHivQueryPart+additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+ovcHivQuery+ageQuery+additionalServiceQuery;
        String femalesql="select distinct service.ovcId "+hhOvcServiceAndHivQueryPart+additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender)+ovcHivQuery+ageQuery+additionalServiceQuery;
        String bothGendersql="select distinct service.ovcId "+hhOvcServiceAndHivQueryPart+additionalQueryCriteria+ovcHivQuery+ageQuery+additionalServiceQuery;
        mainlist=getListOfOvcByOvcId(execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql));
        return mainlist;
    }
    private List getListOfOvcWithoutServeRecords(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        List ovcList=new ArrayList();
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        if(!additionalServiceQuery.equals(" "))
        additionalServiceQuery=" and "+additionalServiceQuery;
        String malesql=hhQueryPart+util.getGenderCriteria(maleGender)+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String femalesql=hhQueryPart+util.getGenderCriteria(femaleGender)+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        
        String bothGendersql=hhQueryPart+" ovc.ovcId is not null "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        mainlist=execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql);
        if(mainlist !=null && !mainlist.isEmpty())
        {
            Ovc ovc=null;
            List list=null;
            for(Object s:mainlist)
            {
                Object[] obj=(Object[])s;
                ovc=(Ovc)obj[1];
                list=serviceDao.getOvcServicesByOvcIdAndAdditionalServiceQuery(ovc.getOvcId(), additionalServiceQuery);
                if(list !=null && !list.isEmpty())
                continue;
                ovcList.add(ovc);
            }
        }
        //System.err.println("malesql in getListOfOvcWithoutServeRecords is "+malesql);
        return ovcList;
    }
    public List getListOfHivPosOvc(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        String status=NomisConstant.HIV_POSITIVE;
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        String ovcHivQuery=util.getActiveHivStatusCriteria(status,NomisConstant.OVC_TYPE);
        String malesql=hhOvcHivQueryPart+ovcHivQuery+" and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String femalesql=hhOvcHivQueryPart+ovcHivQuery+" and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String bothGendersql=hhOvcHivQueryPart+ovcHivQuery+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        List list=execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql);
        mainlist=getListOfOvc(list);
        return mainlist;
    }
    public List getListHivNegOvc(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        String status=NomisConstant.HIV_NEGATIVE;
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        String ovcHivQuery=util.getActiveHivStatusCriteria(status,NomisConstant.OVC_TYPE);
        String malesql=hhOvcHivQueryPart+ovcHivQuery+" and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String femalesql=hhOvcHivQueryPart+ovcHivQuery+" and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String bothGendersql=hhOvcHivQueryPart+ovcHivQuery+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        List list=execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql);
        mainlist=getListOfOvc(list);
        return mainlist;
    }
    public List getListOfHivUnknownOvc(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        String status=NomisConstant.HIV_UNKNOWN;
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        String ovcHivQuery=util.getActiveHivStatusCriteria(status,NomisConstant.OVC_TYPE);
        String malesql=hhOvcHivQueryPart+ovcHivQuery+" and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String femalesql=hhOvcHivQueryPart+ovcHivQuery+" and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String bothGendersql=hhOvcHivQueryPart+ovcHivQuery+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        List list=execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql);
        mainlist=getListOfOvc(list);
        return mainlist;
    }
    /*public List getListOfOvcInOphanage(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        String malesql="From Ovc ovc where ovc.orphanage='Yes' and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String femalesql="From Ovc ovc where ovc.orphanage='Yes' and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String bothGendersql="From Ovc ovc where ovc.orphanage='Yes' "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        mainlist.addAll(execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql));
        return mainlist;
    }*/
    public List getListOfOvcOutOfSchool(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        String malesql=hhQueryPart+" ovc.schoolStatus='No' and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String femalesql=hhQueryPart+" ovc.schoolStatus='No' and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String bothGendersql=hhQueryPart+" ovc.schoolStatus='No' "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        List list=execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql);
        mainlist=getListOfOvc(list);
        return mainlist;
    }
    public List getListOfOvcCurrentlyInSchool(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        String malesql=hhQueryPart+" ovc.schoolStatus='Yes' and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String femalesql=hhQueryPart+" ovc.schoolStatus='Yes' and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String bothGendersql=hhQueryPart+" ovc.schoolStatus='Yes' "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        List list=execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql);
        mainlist=getListOfOvc(list);
        return mainlist;
    }
    public List getListOvcWithoutBirthCert(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        String malesql=hhQueryPart+" ovc.birthCertificate='No' and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String femalesql=hhQueryPart+" ovc.birthCertificate='No' and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String bothGendersql=hhQueryPart+" ovc.birthCertificate='No' "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        List list=execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql);
        mainlist=getListOfOvc(list);
        return mainlist;
    }
    public List getNoOfOvcEnrolled(String sex,String additionalQueryCriteria, String additionalEnrollmentQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        mainlist.add("No of new VC enrolled");
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        String malesql=countQueryPart+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String femalesql=countQueryPart+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        //System.err.println(malesql);
        maleList.add(execReportQuery(malesql).get(0));
        femaleList.add(execReportQuery(femalesql).get(0));
        mainlist.addAll(maleList);
        mainlist.addAll(femaleList);
        
        return mainlist;
    }
    public List getNoOfOvcServed(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        
        if(!additionalServiceQuery.equals(" "))
            additionalServiceQuery=" and "+additionalServiceQuery;
       
       List maleList=new ArrayList();
        List femaleList=new ArrayList();
        mainlist.add("No of VC provided with at least one service");
        String malesql=serviceCountQueryPart+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+additionalServiceQuery;
        String femalesql=serviceCountQueryPart+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+additionalServiceQuery;

        maleList.add(execReportQuery(malesql).get(0));
        femaleList.add(execReportQuery(femalesql).get(0));
        mainlist.addAll(maleList);
        mainlist.addAll(femaleList);
        return mainlist;
    }
    public List getNoOfHouseholdsEnrolled(String additionalQueryCriteria, String hhePeriodQuery) throws Exception
    {
        List mainlist=new ArrayList();
        mainlist.add("No of Households enrolled");
        String bothGendersql="select count(distinct hhe.hhUniqueId)from HouseholdEnrollment hhe where hhe.hhUniqueId is not null"+additionalQueryCriteria+hhePeriodQuery;
        mainlist.add(execReportQuery(bothGendersql).get(0));
        return mainlist;
    }
    public List getNoOfCaregiversServed(String sex,String additionalQueryCriteria,String hviPeriodQuery,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        if(!additionalServiceQuery.equals(" "))
            additionalServiceQuery=" and "+additionalServiceQuery;

        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        mainlist.add("No of Caregivers provided with at least one service");
        String malesql="select count(distinct hhs.hhUniqueId) from HouseholdEnrollment hhe, Caregiver cgiver, HouseholdService hhs where hhe.hhUniqueId=hhs.hhUniqueId and hhs.hhUniqueId=cgiver.hhUniqueId and "+util.getCaregiverGenderCriteria(maleGender)+" "+additionalQueryCriteria+additionalServiceQuery;
        String femalesql="select count(distinct hhs.hhUniqueId) from HouseholdEnrollment hhe, Caregiver cgiver, HouseholdService hhs where hhe.hhUniqueId=hhs.hhUniqueId and hhs.hhUniqueId=cgiver.hhUniqueId and "+util.getCaregiverGenderCriteria(femaleGender)+" "+additionalQueryCriteria+additionalServiceQuery;
        System.err.println(malesql);
       System.err.println(femalesql);
        maleList.add(execReportQuery(malesql).get(0));
        femaleList.add(execReportQuery(femalesql).get(0));
        mainlist.addAll(maleList);
        mainlist.addAll(femaleList);
        return mainlist;
    }
    public List getNoOfHivPositiveOvcServed(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        String status=NomisConstant.HIV_POSITIVE;
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        if(!additionalServiceQuery.equals(" "))
            additionalServiceQuery=" and "+additionalServiceQuery;
       
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        mainlist.add("No of HIV positive VC provided with at least one service");
        String ovcHivQuery=util.getActiveHivStatusCriteria(status,NomisConstant.OVC_TYPE);
        String malesql=serviceCountHivQueryPart+util.getGenderCriteria(maleGender)+ovcHivQuery+" "+additionalQueryCriteria+ageQuery+additionalServiceQuery;
        String femalesql=serviceCountHivQueryPart+util.getGenderCriteria(femaleGender)+ovcHivQuery+" "+additionalQueryCriteria+ageQuery+additionalServiceQuery;
        maleList.add(execReportQuery(malesql).get(0));
        femaleList.add(execReportQuery(femalesql).get(0));
        mainlist.addAll(maleList);
        mainlist.addAll(femaleList);
        return mainlist;
    }
    public List getNoOfHivNegativeOvcServed(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        String status=NomisConstant.HIV_NEGATIVE;
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        if(!additionalServiceQuery.equals(" "))
            additionalServiceQuery=" and "+additionalServiceQuery;
        
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        mainlist.add("No of HIV negative VC provided with at least one service");
        String ovcHivQuery=util.getActiveHivStatusCriteria(status,NomisConstant.OVC_TYPE);
        String malesql=serviceCountHivQueryPart+util.getGenderCriteria(maleGender)+ovcHivQuery+" "+additionalQueryCriteria+ageQuery+additionalServiceQuery;
        String femalesql=serviceCountHivQueryPart+util.getGenderCriteria(femaleGender)+ovcHivQuery+" "+additionalQueryCriteria+ageQuery+additionalServiceQuery;
        maleList.add(execReportQuery(malesql).get(0));
        femaleList.add(execReportQuery(femalesql).get(0));
        mainlist.addAll(maleList);
        mainlist.addAll(femaleList);
        return mainlist;
    }
    public List getNoOfHivUnknownOvcServed(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        String status=NomisConstant.HIV_UNKNOWN;
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        if(!additionalServiceQuery.equals(" "))
            additionalServiceQuery=" and "+additionalServiceQuery;
        
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        mainlist.add("No of HIV unknown VC provided with at least one service");
        String ovcHivQuery=util.getActiveHivStatusCriteria(status,NomisConstant.OVC_TYPE);
        String malesql=serviceCountHivQueryPart+util.getGenderCriteria(maleGender)+ovcHivQuery+" "+additionalQueryCriteria+ageQuery+additionalServiceQuery;
        String femalesql=serviceCountHivQueryPart+util.getGenderCriteria(femaleGender)+ovcHivQuery+" "+additionalQueryCriteria+ageQuery+additionalServiceQuery;
        maleList.add(execReportQuery(malesql).get(0));
        femaleList.add(execReportQuery(femalesql).get(0));
        mainlist.addAll(maleList);
        mainlist.addAll(femaleList);
        return mainlist;
    }
    public List getNoOfOvcWithoutServiceRecords(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        if(!additionalServiceQuery.equals(" "))
            additionalServiceQuery=" and "+additionalServiceQuery;
        //String serviceCriteria=" and ovc.ovcId not in (select distinct service.ovcId from OvcService service "+additionalServiceQuery+" )";
        mainlist.add("No of VC without service records");
        
        String malesql=countQueryPart+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String femalesql=countQueryPart+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String maleServicesql=serviceCountQueryPart+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+additionalServiceQuery;
        String femaleServicesql=serviceCountQueryPart+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+additionalServiceQuery;

        String malesqlResult=(String)(execReportQuery(malesql).get(0)).toString();
        String femalesqlResult=(String)(execReportQuery(femalesql).get(0)).toString();
        String maleServicesqlResult=(String)(execReportQuery(maleServicesql).get(0)).toString();
        String femaleServicesqlResult=(String)(execReportQuery(femaleServicesql).get(0)).toString();
       
        int maleOvcCount=Integer.parseInt(malesqlResult);
        int femaleOvcCount=Integer.parseInt(femalesqlResult);
        int maleServiceCount=Integer.parseInt(maleServicesqlResult);
        int femaleServiceCount=Integer.parseInt(femaleServicesqlResult);

        int maleOvcWithoutService=maleOvcCount-maleServiceCount;
        int femaleOvcWithoutService=femaleOvcCount-femaleServiceCount;
        mainlist.add(maleOvcWithoutService);
        mainlist.add(femaleOvcWithoutService);
        //System.err.println("malesql in getNoOfOvcWithoutServiceRecords() is "+malesql);
        //System.err.println("femalesql in getNoOfOvcWithoutServiceRecords() is "+femalesql);
        return mainlist;
    }
    public void setNoOfServices(String sex,String additionalQueryCriteria,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        List uniqueOvcIdList=new ArrayList();
        int numberOfServices=0;
        if(!additionalServiceQuery.equals(" "))
            additionalServiceQuery=" and "+additionalServiceQuery;

        String malesql=hhOvcAndServiceByOvcIdQueryPart+" and "+util.getGenderCriteria(maleGender)+additionalQueryCriteria+additionalServiceQuery+ageQuery;
        String femalesql=hhOvcAndServiceByOvcIdQueryPart+" and "+util.getGenderCriteria(femaleGender)+additionalQueryCriteria+additionalServiceQuery+ageQuery;
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
    public List getNoOfOvcThatReceivedThreeOrMoreServices() throws Exception
    {
        List mainlist=new ArrayList();
        mainlist.add("No of VC that received three or more services");
        mainlist.add(maleGreaterThanThreeList.size());
        mainlist.add(femaleGreaterThanThreeList.size());
        return mainlist;
    }
    public List getNoOfOvcThatReceivedLessThanThreeServices() throws Exception
    {
        List mainlist=new ArrayList();
        mainlist.add("No of VC that received less than three services");
        mainlist.add(maleLessThanThreeList.size());
        mainlist.add(femaleLessThanThreeList.size());
        return mainlist;
    }
    
    public int getNoOfServicesProvided(OvcService service)
    {             
            int j=0;
            if(service.getServiceAccessed1() !=null && service.getServiceAccessed1().length()>0 && !service.getServiceAccessed1().equals("") && !service.getServiceAccessed1().equals(" ") && !service.getServiceAccessed1().equals("  "))
            j++;    
            if(service.getServiceAccessed2() !=null && service.getServiceAccessed2().length()>0 && !service.getServiceAccessed2().equals("") && !service.getServiceAccessed2().equals(" ") && !service.getServiceAccessed2().equals("  "))
             j++;
            if(service.getServiceAccessed3() !=null && service.getServiceAccessed3().length()>0 && !service.getServiceAccessed3().equals("") && !service.getServiceAccessed3().equals(" ") && !service.getServiceAccessed3().equals("  "))
               j++;
            if(service.getServiceAccessed4() !=null && service.getServiceAccessed4().length()>0 && !service.getServiceAccessed4().equals("") && !service.getServiceAccessed4().equals(" ") && !service.getServiceAccessed4().equals("  "))
            j++;
            if(service.getServiceAccessed5() !=null && service.getServiceAccessed5().length()>0 && !service.getServiceAccessed5().equals("") && !service.getServiceAccessed5().equals(" ") && !service.getServiceAccessed5().equals("  "))
              j++;
            if(service.getServiceAccessed6() !=null && service.getServiceAccessed6().length()>0 && !service.getServiceAccessed6().equals("") && !service.getServiceAccessed6().equals(" ") && !service.getServiceAccessed6().equals("  "))
            j++;
            if(service.getServiceAccessed7() !=null && service.getServiceAccessed7().length()>0 && !service.getServiceAccessed7().equals("") && !service.getServiceAccessed7().equals(" ") && !service.getServiceAccessed7().equals("  "))
              j++;
                               
        return j;
    }
    /*public List getNoReceivingThreeServices(String sex,String additionalQueryCriteria,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        mainlist.add("No of VC that received three services at enrollment");
        String malesql="From Ovc ovc where "+util.getGenderCriteria(maleGender)+" and ovc.serviceEnrollment like '%Psychosocial support%' and ovc.serviceEnrollment like '%Nutrition counseling%' and ovc.serviceEnrollment like '%Health referral%' "+additionalQueryCriteria;
        String femalesql="From Ovc ovc where "+util.getGenderCriteria(femaleGender)+" and ovc.serviceEnrollment like '%Psychosocial support%' and ovc.serviceEnrollment like '%Nutrition counseling%' and ovc.serviceEnrollment like '%Health referral%' "+additionalQueryCriteria;
        String bothGendersql="From Ovc ovc where ovc.ovcId is not null and ovc.serviceEnrollment like '%Psychosocial support%' and ovc.serviceEnrollment like '%Nutrition counseling%' and ovc.serviceEnrollment like '%Health referral%' "+additionalQueryCriteria;
        mainlist.addAll(execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql));
        return mainlist;
    }*/
    /*public List getNoHivPosReceivingThreeServices(String sex,String additionalQueryCriteria,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        String status=NomisConstant.HIV_POSITIVE;
        mainlist.add("No of HIV positive VC that received three services at enrollment");
        String malesql="From Ovc ovc where "+util.getGenderCriteria(maleGender)+" and "+util.getHivStatusCriteria(status)+" and ovc.serviceEnrollment like '%Psychosocial support%' and ovc.serviceEnrollment like '%Nutrition counseling%' and ovc.serviceEnrollment like '%Health referral%' "+additionalQueryCriteria;
        String femalesql="From Ovc ovc where "+util.getGenderCriteria(femaleGender)+" and "+util.getHivStatusCriteria(status)+" and ovc.serviceEnrollment like '%Psychosocial support%' and ovc.serviceEnrollment like '%Nutrition counseling%' and ovc.serviceEnrollment like '%Health referral%' "+additionalQueryCriteria;
        String bothGendersql="From Ovc ovc where "+util.getHivStatusCriteria(status)+" and ovc.serviceEnrollment like '%Psychosocial support%' and ovc.serviceEnrollment like '%Nutrition counseling%' and ovc.serviceEnrollment like '%Health referral%' "+additionalQueryCriteria;
        mainlist.addAll(execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql));
        return mainlist;
    }*/
    public List getNoHivPosOvc(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        String status=NomisConstant.HIV_POSITIVE;
        mainlist.add("No of HIV positive VC enrolled");
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        String ovcHivQuery=util.getActiveHivStatusCriteria(status,NomisConstant.OVC_TYPE);
        String malesql=countHivQueryPart+ovcHivQuery+" and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String femalesql=countHivQueryPart+ovcHivQuery+" and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        
        maleList.add(execReportQuery(malesql).get(0));
        femaleList.add(execReportQuery(femalesql).get(0));
        mainlist.addAll(maleList);
        mainlist.addAll(femaleList);
        return mainlist;
    }
    public List getNoHivNegOvc(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        String status=NomisConstant.HIV_NEGATIVE;
        mainlist.add("No of HIV negative VC enrolled");
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        String ovcHivQuery=util.getActiveHivStatusCriteria(status,NomisConstant.OVC_TYPE);
        String malesql=countHivQueryPart+ovcHivQuery+" and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String femalesql=countHivQueryPart+ovcHivQuery+" and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        
        maleList.add(execReportQuery(malesql).get(0));
        femaleList.add(execReportQuery(femalesql).get(0));
        mainlist.addAll(maleList);
        mainlist.addAll(femaleList);
        
        return mainlist;
    }
    public List getNoHivUnknownOvc(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        String status=NomisConstant.HIV_UNKNOWN;
        mainlist.add("No of HIV unknown VC enrolled");
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        String ovcHivQuery=util.getActiveHivStatusCriteria(status,NomisConstant.OVC_TYPE);
        String malesql=countHivQueryPart+ovcHivQuery+" and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String femalesql=countHivQueryPart+ovcHivQuery+" and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
       
        maleList.add(execReportQuery(malesql).get(0));
        femaleList.add(execReportQuery(femalesql).get(0));
        mainlist.addAll(maleList);
        mainlist.addAll(femaleList);
        return mainlist;
    }
    /*public List getNoOvcInOphanage(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        mainlist.add("No of VC in orphanage home");
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        String malesql="From Ovc ovc where ovc.orphanage='Yes' and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String femalesql="From Ovc ovc where ovc.orphanage='Yes' and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String bothGendersql="From Ovc ovc where ovc.orphanage='Yes' "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        mainlist.addAll(execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql));
        return mainlist;
    }*/
    public List getNoOfOvcOutOfSchool(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        mainlist.add("No of VC out of school");
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        String malesql=countQueryPart+"ovc.schoolStatus='No' and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String femalesql=countQueryPart+"ovc.schoolStatus='No' and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        maleList.add(execReportQuery(malesql).get(0));
        femaleList.add(execReportQuery(femalesql).get(0));
        mainlist.addAll(maleList);
        mainlist.addAll(femaleList);
        
        return mainlist;
    }
    public List getNoOfOvcCurrentlyInSchool(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        mainlist.add("No of VC currently in school");
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        String malesql=countQueryPart+"ovc.schoolStatus='Yes' and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String femalesql=countQueryPart+"ovc.schoolStatus='Yes' and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        maleList.add(execReportQuery(malesql).get(0));
        femaleList.add(execReportQuery(femalesql).get(0));
        mainlist.addAll(maleList);
        mainlist.addAll(femaleList);
        
        return mainlist;
    }
    public List getNoOvcWithoutBirthCert(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        mainlist.add("No of VC without birth certificate");
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        String malesql=countQueryPart+"ovc.birthCertificate='No' and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String femalesql=countQueryPart+"ovc.birthCertificate='No' and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        maleList.add(execReportQuery(malesql).get(0));
        femaleList.add(execReportQuery(femalesql).get(0));
        mainlist.addAll(maleList);
        mainlist.addAll(femaleList);
        
        return mainlist;
    }
    public int[] getNewAgeSegregation()
    {
        int[] ageSegregation={0,1,1,4,5,9,10,14,15,17,18,30};
        return ageSegregation;
    }
    public int[] getAgeSegregation()
    {
        int[] ageSegregation={0,5,6,12,13,17};
        return ageSegregation;
    }
    public int[] getHouseholdAgeSegregation()
    {
        int[] ageSegregation={0,17,18,59,60,200};
        return ageSegregation;
    }

    public List getNoOfOvcNewlyEnrolled(String additionalQueryCriteria,String[] dateArray) throws Exception
    {
        int[] ageSegregation=getAgeSegregation();
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        String ageQuery=" ";
        for(int i=0; i<ageSegregation.length; i+=2)
        {
            ageQuery=util.getAgeCriteria(ageSegregation[i]+"", ageSegregation[i+1]+"");
            String queryPart= additionalQueryCriteria+ageQuery+" and ovc.dateEnrollment between '"+dateArray[0]+"' and '"+dateArray[1]+"' ";
            String malesql=countQueryPart+util.getGenderCriteria(maleGender)+" "+queryPart;
            String femalesql=countQueryPart+util.getGenderCriteria(femaleGender)+" "+queryPart;
            maleList.add(execReportQuery(malesql).get(0));
            femaleList.add(execReportQuery(femalesql).get(0));
            //System.err.println(malesql);
        }
        mainlist.add("No of <b>new VC</b> enrolled");
        mainlist.addAll(maleList);
        mainlist.addAll(femaleList);
        return mainlist;
    }
    public List getNoOfOvcCurrentlyEnrolled(String additionalQueryCriteria,String[] dateArray) throws Exception
    {
        int[] ageSegregation=getAgeSegregation();
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        //String dateQuery=" and ovc.dateEnrollment between '"+dateArray[0]+"' and '"+dateArray[1]+"' ";
        String dateQuery=" and ovc.dateEnrollment <= '"+dateArray[1]+"'";// and '"+dateArray[1]+"' ";
        for(int i=0; i<ageSegregation.length; i+=2)
        {
            ageQuery=util.getAgeCriteria(ageSegregation[i]+"", ageSegregation[i+1]+"");
            String queryPart= ageQuery+dateQuery+additionalQueryCriteria+" and ovc.ovcId not in (select withdrawal.ovcId from OvcWithdrawal withdrawal)";
            String malesql=countQueryPart+util.getGenderCriteria(maleGender)+" "+queryPart;
            String femalesql=countQueryPart+util.getGenderCriteria(femaleGender)+" "+queryPart;
            maleList.add(execReportQuery(malesql).get(0));
            femaleList.add(execReportQuery(femalesql).get(0));
        }
        mainlist.add("No of <b>VC currently</b> enrolled");
        mainlist.addAll(maleList);
        mainlist.addAll(femaleList);
        return mainlist;
    }

    public List getNoOfOvcServedForMSF(String additionalQueryCriteria,String[] dateArray) throws Exception
    {
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        int[] ageSegregation=getAgeSegregation();                   
        String serviceCriteria=" ";
        
        for(int i=0; i<ageSegregation.length; i+=2)
        {
            ageQuery=util.getAgeCriteria(ageSegregation[i]+"", ageSegregation[i+1]+"");
            serviceCriteria=" and service.servicedate between '"+dateArray[0]+"' and '"+dateArray[1]+"' )";
            String malesql=serviceCountQueryPart+util.getGenderCriteria(maleGender)+ageQuery+" "+additionalQueryCriteria+serviceCriteria;
            String femalesql=serviceCountQueryPart+util.getGenderCriteria(femaleGender)+ageQuery+" "+additionalQueryCriteria+serviceCriteria;
                        
            maleList.add(execReportQuery(malesql).get(0));
            femaleList.add(execReportQuery(femalesql).get(0));
        }
        mainlist.add("No of VC provided with at least one service");
        mainlist.addAll(maleList);
        mainlist.addAll(femaleList);
        //System.err.println("OVC Served malelist is "+maleList.toString());
        //System.err.println("OVC Served femalelist is "+femaleList.toString());
        return mainlist;
    }
    public List getNoOfOvcSupportedOnHIVAIDSServicesForMSF(String additionalQueryCriteria,String[] dateArray) throws Exception
    {
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        int[] ageSegregation=getAgeSegregation();                   
        String serviceCriteria=" ";
        
        for(int i=0; i<ageSegregation.length; i+=2)
        {
            ageQuery=util.getAgeCriteria(ageSegregation[i]+"", ageSegregation[i+1]+"");
            serviceCriteria=" and service.servicedate between '"+dateArray[0]+"' and '"+dateArray[1]+"' )";
            String malesql=serviceCountQueryPart+"service.serviceAccessed3 like '%Access for HIV care%' and "+util.getGenderCriteria(maleGender)+ageQuery+" "+additionalQueryCriteria+serviceCriteria;
            String femalesql=serviceCountQueryPart+" service.serviceAccessed3 like '%Access for HIV care%' and "+util.getGenderCriteria(femaleGender)+ageQuery+" "+additionalQueryCriteria+serviceCriteria;
            maleList.add(execReportQuery(malesql).get(0));
            femaleList.add(execReportQuery(femalesql).get(0));
        }
        mainlist.add("Total number of VC supported to access HIV/AIDS services");
        mainlist.addAll(maleList);
        mainlist.addAll(femaleList);
        
        //System.err.println("OVC Served malelist is "+maleList.toString());
        
        return mainlist;
    }
    public List getNoOfOvcAccessingPsychoSupport(String additionalQueryCriteria,String[] dateArray) throws Exception
    {
        int[] ageSegregation=getAgeSegregation();
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        int j=0;
        int score=0;

        for(int i=0; i<ageSegregation.length; i+=2)
        {
            ageQuery=util.getAgeCriteria(ageSegregation[i]+"", ageSegregation[i+1]+"");
            String queryPart=serviceCountQueryPart+" (service.serviceAccessed1 is not null and  service.serviceAccessed1 !='' and service.serviceAccessed1 !=' ') and service.servicedate between '"+dateArray[0]+"' and '"+dateArray[1]+"'";
            String malesql=queryPart+" and "+util.getGenderCriteria(maleGender)+ageQuery+" "+additionalQueryCriteria;
            String femalesql=queryPart+" and "+util.getGenderCriteria(femaleGender)+ageQuery+" "+additionalQueryCriteria;
            //String malesql=queryPart+" and "+util.getGenderCriteria(maleGender)+" and ovc.age between "+ ageSegregation[i]+" and "+ageSegregation[i+1]+" "+additionalQueryCriteria;
            //String femalesql=queryPart+" and "+util.getGenderCriteria(femaleGender)+" and ovc.age between "+ ageSegregation[i]+" and "+ageSegregation[i+1]+" "+additionalQueryCriteria;
            
            maleList.add(execReportQuery(malesql).get(0));
            femaleList.add(execReportQuery(femalesql).get(0));
        }
        mainlist.add("No of <b>VC</b> accessing <b>Psychosocial support</b> services");
        mainlist.addAll(maleList);
        mainlist.addAll(femaleList);
        return mainlist;
    }
    
    public List getNoOfOvcAccessingHealthCare(String additionalQueryCriteria,String[] dateArray) throws Exception
    {
        int[] ageSegregation=getAgeSegregation();
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        
        int j=0;
        for(int i=0; i<ageSegregation.length; i+=2)
        {
            ageQuery=util.getAgeCriteria(ageSegregation[i]+"", ageSegregation[i+1]+"");
            String queryPart=serviceCountQueryPart+" (service.serviceAccessed3 is not null and  service.serviceAccessed3 !='' and service.serviceAccessed3 !=' ') and service.servicedate between '"+dateArray[0]+"' and '"+dateArray[1]+"'";
            String malesql=queryPart+" and "+util.getGenderCriteria(maleGender)+ageQuery+" "+additionalQueryCriteria;
            String femalesql=queryPart+" and "+util.getGenderCriteria(femaleGender)+ageQuery+" "+additionalQueryCriteria;
            
            maleList.add(execReportQuery(malesql).get(0));
            femaleList.add(execReportQuery(femalesql).get(0));
        }
        mainlist.add("No of <b>VC</b> accessing <b>Health care</b> services");
        mainlist.addAll(maleList);
        mainlist.addAll(femaleList);
        return mainlist;
    }
    public List getNoOfOvcAccessingNutritional(String additionalQueryCriteria,String[] dateArray) throws Exception
    {
        int[] ageSegregation=getAgeSegregation();
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        
        int j=0;
        for(int i=0; i<ageSegregation.length; i+=2)
        {
            ageQuery=util.getAgeCriteria(ageSegregation[i]+"", ageSegregation[i+1]+"");
            String queryPart=serviceCountQueryPart+"(service.serviceAccessed2 is not null and  service.serviceAccessed2 !='' and service.serviceAccessed2 !=' ') and service.servicedate between '"+dateArray[0]+"' and '"+dateArray[1]+"'";
            String malesql=queryPart+" and "+util.getGenderCriteria(maleGender)+ageQuery+" "+additionalQueryCriteria;
            String femalesql=queryPart+" and "+util.getGenderCriteria(femaleGender)+ageQuery+" "+additionalQueryCriteria;
            
            maleList.add(execReportQuery(malesql).get(0));
            femaleList.add(execReportQuery(femalesql).get(0));
        }
        mainlist.add("No of <b>VC</b> accessing <b>Nutritional</b> services");
        mainlist.addAll(maleList);
        mainlist.addAll(femaleList);
        return mainlist;
    }
    public List getNoOfOvcAccessingEducational(String additionalQueryCriteria,String[] dateArray) throws Exception
    {
        int[] ageSegregation=getAgeSegregation();
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        for(int i=0; i<ageSegregation.length; i+=2)
        {
            ageQuery=util.getAgeCriteria(ageSegregation[i]+"", ageSegregation[i+1]+"");
            String queryPart=serviceCountQueryPart+"(service.serviceAccessed4 is not null and  service.serviceAccessed4 !='' and service.serviceAccessed4 !=' ') and service.servicedate between '"+dateArray[0]+"' and '"+dateArray[1]+"'";
            String malesql=queryPart+" and "+util.getGenderCriteria(maleGender)+ageQuery+" "+additionalQueryCriteria;
            String femalesql=queryPart+" and "+util.getGenderCriteria(femaleGender)+ageQuery+" "+additionalQueryCriteria;
            
            maleList.add(execReportQuery(malesql).get(0));
            femaleList.add(execReportQuery(femalesql).get(0));
        }
        mainlist.add("No of <b>VC</b> accessing <b>Education</b> services");
        mainlist.addAll(maleList);
        mainlist.addAll(femaleList);
        return mainlist;
    }
    public List getNoOfOvcAccessingShelter(String additionalQueryCriteria,String[] dateArray) throws Exception
    {
        int[] ageSegregation=getAgeSegregation();
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        for(int i=0; i<ageSegregation.length; i+=2)
        {
            ageQuery=util.getAgeCriteria(ageSegregation[i]+"", ageSegregation[i+1]+"");
            String queryPart=serviceCountQueryPart+" (service.serviceAccessed6 is not null and  service.serviceAccessed6 !='' and service.serviceAccessed6 !=' ') and service.servicedate between '"+dateArray[0]+"' and '"+dateArray[1]+"'";
            String malesql=queryPart+" and "+util.getGenderCriteria(maleGender)+ageQuery+" "+additionalQueryCriteria;
            String femalesql=queryPart+" and "+util.getGenderCriteria(femaleGender)+ageQuery+" "+additionalQueryCriteria;
            
            maleList.add(execReportQuery(malesql).get(0));
            femaleList.add(execReportQuery(femalesql).get(0));
        }
        mainlist.add("No of <b>VC</b> accessing <b>Shelter</b> services");
        mainlist.addAll(maleList);
        mainlist.addAll(femaleList);
        return mainlist;
    }
    public List getNoOfOvcAccessingProtection(String additionalQueryCriteria,String[] dateArray) throws Exception
    {
        int[] ageSegregation=getAgeSegregation();
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        for(int i=0; i<ageSegregation.length; i+=2)
        {
            ageQuery=util.getAgeCriteria(ageSegregation[i]+"", ageSegregation[i+1]+"");
            String queryPart=serviceCountQueryPart+" (service.serviceAccessed5 is not null and  service.serviceAccessed5 !='' and service.serviceAccessed5 !=' ') and service.servicedate between '"+dateArray[0]+"' and '"+dateArray[1]+"'";
            String malesql=queryPart+" and "+util.getGenderCriteria(maleGender)+ageQuery+" "+additionalQueryCriteria;
            String femalesql=queryPart+" and "+util.getGenderCriteria(femaleGender)+ageQuery+" "+additionalQueryCriteria;
            
            maleList.add(execReportQuery(malesql).get(0));
            femaleList.add(execReportQuery(femalesql).get(0));
        }
        mainlist.add("No of <b>VC</b> accessing <b>Protection</b> services");
        mainlist.addAll(maleList);
        mainlist.addAll(femaleList);
        return mainlist;
    }
    public List getNoOfOvcAccessingEconStrengthening(String additionalQueryCriteria,String[] dateArray) throws Exception
    {
        int[] ageSegregation=getAgeSegregation();
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        int maleTotal=0,femaleTotal=0,grandTotal=0,maleCount=0,femaleCount=0;
        String blankStyle="<label style='background-color:grey; width:50px'>--------</label>";
        for(int i=4; i<ageSegregation.length; i+=2)
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
        mainlist.add("No of <b>VC</b> accessing <b>Economic Strengthening</b> services");
        mainlist.addAll(maleList);
        mainlist.add(maleTotal);
        mainlist.addAll(femaleList);
        mainlist.add(femaleTotal);
        grandTotal=maleTotal+femaleTotal;
        mainlist.add(grandTotal);
        
        //System.err.println("OVC Served Economic Strenghtening femalelist is "+femaleList.toString());
        return mainlist;
    }
    public List getNoOfHouseholdsProvidedWithAtleastOneService(String additionalQueryCriteria,String[] dateArray) throws Exception
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
        mainlist.add("Number of <b>caregivers/household heads</b> provided with at least one service");
        mainlist.addAll(maleList);
        mainlist.add(maleTotal);
        mainlist.addAll(femaleList);
        mainlist.add(femaleTotal);
        grandTotal=maleTotal+femaleTotal;
        mainlist.add(grandTotal);
        return mainlist;
    }
    public List getNoOfHouseholdsSupportedToAccessHIVCare(String additionalQueryCriteria,String[] dateArray) throws Exception
    {
        int[] ageSegregation=getHouseholdAgeSegregation();
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        int maleTotal=0,femaleTotal=0,grandTotal=0,maleCount=0,femaleCount=0;
        
        for(int i=0; i<ageSegregation.length; i+=2)
        {
            String queryPart=hheCaregiverHhsQuery+" and (hhs.healthServices like '%HIV services%' or hhs.healthServices like '%HIV care and support%') and hhs.serviceDate between '"+dateArray[0]+"' and '"+dateArray[1]+"' and ";
            String malesql=queryPart+util.getCaregiverGenderCriteria(hviMaleGender)+" and "+util.getCaregiverAgeCriteria(ageSegregation[i],ageSegregation[i+1])+" "+additionalQueryCriteria;
            String femalesql=queryPart+util.getCaregiverGenderCriteria(hviFemaleGender)+" and "+util.getCaregiverAgeCriteria(ageSegregation[i],ageSegregation[i+1])+" "+additionalQueryCriteria;
            System.err.println("malesql in getNoOfHouseholdsSupportedToAccessHIVCare is "+malesql);
            System.err.println("femalesql in getNoOfHouseholdsSupportedToAccessHIVCare is "+femalesql);
            maleCount=((Long)execReportQuery(malesql).get(0)).intValue();
            femaleCount=((Long)execReportQuery(femalesql).get(0)).intValue();
            maleList.add(maleCount);
            femaleList.add(femaleCount);
            maleTotal+=maleCount;
            femaleTotal+=femaleCount;
        }
        mainlist.add("Number of caregivers/household heads supported to access HIV services");
        mainlist.addAll(maleList);
        mainlist.add(maleTotal);
        mainlist.addAll(femaleList);
        mainlist.add(femaleTotal);
        grandTotal=maleTotal+femaleTotal;
        mainlist.add(grandTotal);
        return mainlist;
    }
    public List getNoOfHouseholdHeadsReceivingEconomicStrenghtening(String additionalQueryCriteria,String[] dateArray) throws Exception
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
        mainlist.add("Number of caregivers/Household heads receiving Economic Strengthening");
        mainlist.addAll(maleList);
        mainlist.add(maleTotal);
        mainlist.addAll(femaleList);
        mainlist.add(femaleTotal);
        grandTotal=maleTotal+femaleTotal;
        mainlist.add(grandTotal);
        return mainlist;
    } 
    public List getNoOfCSOReportingOVCServicesForTheMth(String[] params) throws Exception
    {
        List maleList=new ArrayList();

        String additionalQueryCriteria=util.getOrgQueryCriteria(params);
        String[] dateParams={params[3],params[4],params[5],params[6]};
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
        String[] dateParams={params[3],params[4],params[5],params[6]};
        String[] dateArray={util.getStartDate(dateParams),util.getEndDate(dateParams)};
        //String malesql="select count(distinct ovc.lga) from Ovc ovc where ovc.ovcId is not null "+additionalQueryCriteria+" and ovc.ovcId in (select service.ovcId from OvcService service where  service.servicedate between '"+dateArray[0]+"' and '"+dateArray[1]+"' )";// or ovc.ovcId in (select ovc.ovcId from Ovc ovc where  ovc.dateEnrollment between '"+dateArray[0]+"' and '"+dateArray[1]+"' )";
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
        String[] dateParams={params[3],params[4],params[5],params[6]};
        String[] dateArray={util.getStartDate(dateParams),util.getEndDate(dateParams)};
        //String malesql="select count(distinct ovc.state) from Ovc ovc where ovc.ovcId is not null "+additionalQueryCriteria+" and ovc.ovcId in (select service.ovcId from OvcService service where  service.servicedate between '"+dateArray[0]+"' and '"+dateArray[1]+"' )";// or ovc.ovcId in (select ovc.ovcId from Ovc ovc where  ovc.dateEnrollment between '"+dateArray[0]+"' and '"+dateArray[1]+"' )";
        String malesql="select count(distinct hhe.stateCode)"+hhOvcAndServiceQueryPart+" and ovc.ovcId is not null "+additionalQueryCriteria+" and   service.servicedate between '"+dateArray[0]+"' and '"+dateArray[1]+"'";// or ovc.ovcId in (select ovc.ovcId from Ovc ovc where  ovc.dateEnrollment between '"+dateArray[0]+"' and '"+dateArray[1]+"' )";
        //System.err.println("State count query is "+malesql);
        maleList.add("No of <b>States</b> reporting VC services in the reporting period");
        List resultList=new ArrayList();
        resultList=execReportQuery(malesql);
        maleList.add(resultList.get(0));
        //System.err.println("No of State reporting is "+resultList.get(0));
        return maleList;
    }
    protected List tokenizeStr(String str, String delim)
    {
        List tokenList = new ArrayList();
        StringTokenizer st = new StringTokenizer(str, delim);
        while (st.hasMoreTokens())
        {
            tokenList.add(st.nextToken());
        }
        return tokenList;
    }

    public List getOvcCount(String month, String year) throws Exception
    {
        String date = year + "-" + month + "-" + "01";
        List list = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from Ovc o where o.dateEnrollment < :dt").setString("dt", date).list();
            tx.commit();
            session.close();
        } catch (HibernateException he) {
           System.err.println("This is getOvcCount error: " + he.getMessage());
            throw new Exception(he);
        }

        return list;
    }
    public List getLgas(String dateFrom, String dateTo) throws Exception
    {
        List list = new ArrayList();
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            
            list = session.createQuery("select distinct hhe.lgaCode from HouseholdEnrollment hhe where hhe.dateOfAssessment between '"+dateFrom+"' and '"+dateTo+"'").list();
            tx.commit();
            session.close();
        } catch (HibernateException he) {
            System.err.println("getLgas has thrown exception"+he.getMessage());
            throw new Exception(he);
        }
        return list;
    }

    public List fetchOvcCount(String lgaName, String dateFrom, String dateTo) throws Exception {

         List list = new ArrayList();
         List ovcList = new ArrayList();

        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from HouseholdEnrollment hhe, Ovc ovc where hhe.hhUniqueId=ovc.hhUniqueId  and hhe.lgaCode = :lga and ovc.dateEnrollment between '"+dateFrom+"' and '"+dateTo+"'").setString("lga", lgaName).list();
            tx.commit();
            session.close();
            if(list !=null && !list.isEmpty())
            {
                for(Object s:list)
                {
                    Object[] obj=(Object[])s;
                    ovcList.add((Ovc)obj[1]);
                }
            }
        } catch (HibernateException he) {
            throw new Exception(he);
        }

        return ovcList;
    }

    public List getCsiAtEnrolment(String ovcId) throws Exception {

        List list = new ArrayList();
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            list = session.createQuery("from ChildStatusIndex csi where csi.ovcId = :ovcId and csi.surveyNumber = 1)")
                    .setString("ovcId", ovcId).list();
            tx.commit();
            session.close();
        } catch (HibernateException he) {
            throw new Exception(he);
        }
        return list;
    }
    public List getNoOfOvcProvidedPsychosocialServices(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        additionalServiceQuery=getAdditionalServiceQuery(additionalServiceQuery);
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        String serviceCriteria=" and (service.serviceAccessed1 is not null and service.serviceAccessed1 !='' and service.serviceAccessed1 !=' ' and service.serviceAccessed1 !='  '"+additionalServiceQuery+" )";
        mainlist.add("No of <b>VC</b> provided <b>Psychosocial</b> services");
        String malesql=serviceCountQueryPart+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+serviceCriteria;
        String femalesql=serviceCountQueryPart+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+serviceCriteria;
        mainlist.add(execReportQuery(malesql).get(0));
        mainlist.add(execReportQuery(femalesql).get(0));
        System.err.println("Inside getNoOfOvcProvidedPsychosocialServices");
        return mainlist;

    }
    public List getNoOfOvcProvidedNutritionalServices(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        additionalServiceQuery=getAdditionalServiceQuery(additionalServiceQuery);
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        //String serviceCriteria=" and ovc.ovcId in (select distinct service.ovcId from OvcService service where service.serviceAccessed2 is not null and service.serviceAccessed2 !='' and service.serviceAccessed2 !=' ' and service.serviceAccessed2 !='  '"+additionalServiceQuery+" )";
        String serviceCriteria=" and (service.serviceAccessed2 is not null and service.serviceAccessed2 !='' and service.serviceAccessed2 !=' ' and service.serviceAccessed2 !='  '"+additionalServiceQuery+" )";
        mainlist.add("No of <b>VC</b> provided <b>Nutritional</b> services");
        String malesql=serviceCountQueryPart+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+serviceCriteria;
        String femalesql=serviceCountQueryPart+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+serviceCriteria;
        //String bothGendersql="select count(distinct ovc.ovcId) From Ovc ovc, OvcService service where ovc.ovcId=service.ovcId and "+additionalQueryCriteria+ageQuery+serviceCriteria;
        mainlist.add(execReportQuery(malesql).get(0));
        mainlist.add(execReportQuery(femalesql).get(0));
        System.err.println("Inside getNoOfOvcProvidedNutritionalServices");
        return mainlist;
        
    }
    public List getNoOfHIVPositiveOvcProvidedNutritionalServices(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        String status=NomisConstant.HIV_POSITIVE;
        additionalServiceQuery=getAdditionalServiceQuery(additionalServiceQuery);
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        //String serviceCriteria=" and ovc.ovcId in (select distinct service.ovcId from OvcService service where service.serviceAccessed2 is not null and service.serviceAccessed2 !='' and service.serviceAccessed2 !=' ' and service.serviceAccessed2 !='  '"+additionalServiceQuery+" )";
        String serviceCriteria=" and (service.serviceAccessed2 is not null and service.serviceAccessed2 !='' and service.serviceAccessed2 !=' ' and service.serviceAccessed2 !='  '"+additionalServiceQuery+" )";
        mainlist.add("No of <b>HIV Positive VC</b> provided <b>Nutritional</b> services");
        String ovcHivQuery=util.getActiveHivStatusCriteria(status,NomisConstant.OVC_TYPE);
        String malesql=serviceCountHivQueryPart+ovcHivQuery+" and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+serviceCriteria;
        String femalesql=serviceCountHivQueryPart+ovcHivQuery+" and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+serviceCriteria;
        //String bothGendersql="select count(distinct ovc.ovcId) From Ovc ovc, OvcService service where ovc.ovcId=service.ovcId and "+util.getHivStatusCriteria(status)+additionalQueryCriteria+ageQuery+serviceCriteria;
        System.err.println("Inside getNoOfHIVPositiveOvcProvidedNutritionalServices");
        mainlist.add(execReportQuery(malesql).get(0));
        mainlist.add(execReportQuery(femalesql).get(0));
        return mainlist;
    }
    public List getNoOfHIVNegativeOvcProvidedNutritionalServices(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        String status=NomisConstant.HIV_NEGATIVE;
        additionalServiceQuery=getAdditionalServiceQuery(additionalServiceQuery);
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        //String serviceCriteria=" and ovc.ovcId in (select distinct service.ovcId from OvcService service where service.serviceAccessed2 is not null and service.serviceAccessed2 !='' and service.serviceAccessed2 !=' ' and service.serviceAccessed2 !='  '"+additionalServiceQuery+" )";
        String serviceCriteria=" and (service.serviceAccessed2 is not null and service.serviceAccessed2 !='' and service.serviceAccessed2 !=' ' and service.serviceAccessed2 !='  '"+additionalServiceQuery+" )";
        mainlist.add("No of <b>HIV Negative VC</b> provided <b>Nutritional</b> services");
        String ovcHivQuery=util.getActiveHivStatusCriteria(status,NomisConstant.OVC_TYPE);
        String malesql=serviceCountHivQueryPart+ovcHivQuery+" and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+serviceCriteria;
        String femalesql=serviceCountHivQueryPart+ovcHivQuery+" and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+serviceCriteria;
        //String bothGendersql="select count(distinct ovc.ovcId) From Ovc ovc, OvcService service where ovc.ovcId=service.ovcId and "+util.getHivStatusCriteria(status)+additionalQueryCriteria+ageQuery+serviceCriteria;
        
        mainlist.add(execReportQuery(malesql).get(0));
        mainlist.add(execReportQuery(femalesql).get(0));
        return mainlist;
    }
    public List getNoOfHIVUnknownOvcProvidedNutritionalServices(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        String status=NomisConstant.HIV_UNKNOWN;
        additionalServiceQuery=getAdditionalServiceQuery(additionalServiceQuery);
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        //String serviceCriteria=" and ovc.ovcId in (select distinct service.ovcId from OvcService service where service.serviceAccessed2 is not null and service.serviceAccessed2 !='' and service.serviceAccessed2 !=' ' and service.serviceAccessed2 !='  '"+additionalServiceQuery+" )";
        String serviceCriteria=" and (service.serviceAccessed2 is not null and service.serviceAccessed2 !='' and service.serviceAccessed2 !=' ' and service.serviceAccessed2 !='  '"+additionalServiceQuery+" )";
        mainlist.add("No of <b>HIV Unknown VC</b> provided <b>Nutritional</b> services");
        String ovcHivQuery=util.getActiveHivStatusCriteria(status,NomisConstant.OVC_TYPE);
        String malesql=serviceCountHivQueryPart+ovcHivQuery+" and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+serviceCriteria;
        String femalesql=serviceCountHivQueryPart+ovcHivQuery+" and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+serviceCriteria;
        //String bothGendersql="select count(distinct ovc.ovcId) From Ovc ovc, OvcService service where ovc.ovcId=service.ovcId and "+util.getHivStatusCriteria(status)+additionalQueryCriteria+ageQuery+serviceCriteria;
        
        mainlist.add(execReportQuery(malesql).get(0));
        mainlist.add(execReportQuery(femalesql).get(0));
        return mainlist;
    }

    public List getNoOfOvcProvidedHealthServices(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        additionalServiceQuery=getAdditionalServiceQuery(additionalServiceQuery);
        //String serviceCriteria=" and ovc.ovcId in (select distinct service.ovcId from OvcService service where service.serviceAccessed3 is not null and service.serviceAccessed3 !='' and service.serviceAccessed3 !=' ' and service.serviceAccessed3 !='  '"+additionalServiceQuery+" )";
        String serviceCriteria=" and (service.serviceAccessed3 is not null and service.serviceAccessed3 !='' and service.serviceAccessed3 !=' ' and service.serviceAccessed3 !='  '"+additionalServiceQuery+" )";
        mainlist.add("No of <b>VC</b> provided <b>Health</b> services");
        String malesql=serviceCountQueryPart+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+serviceCriteria;
        String femalesql=serviceCountQueryPart+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+serviceCriteria;
        //String bothGendersql="select count(distinct ovc.ovcId) From Ovc ovc, OvcService service where ovc.ovcId=service.ovcId and "+additionalQueryCriteria+ageQuery+serviceCriteria;
        mainlist.add(execReportQuery(malesql).get(0));
        mainlist.add(execReportQuery(femalesql).get(0));
        return mainlist;
    }
    public List getNoOfOvcProvidedEducationalServices(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        additionalServiceQuery=getAdditionalServiceQuery(additionalServiceQuery);
        mainlist.add("No of <b>VC</b> provided <b>Educational</b> services");
        String serviceCriteria=" and (service.serviceAccessed4 is not null and service.serviceAccessed4 !='' and service.serviceAccessed4 !=' ' and service.serviceAccessed4 !='  '"+additionalServiceQuery+" )";
        String malesql=serviceCountQueryPart+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+serviceCriteria;
        String femalesql=serviceCountQueryPart+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+serviceCriteria;
        //String bothGendersql="select count(distinct ovc.ovcId) From Ovc ovc, OvcService service where ovc.ovcId=service.ovcId and "+additionalQueryCriteria+ageQuery+serviceCriteria;
        mainlist.add(execReportQuery(malesql).get(0));
        mainlist.add(execReportQuery(femalesql).get(0));
        
        return mainlist;
    }
    public List getNoOfOvcProvidedProtectionServices(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        additionalServiceQuery=getAdditionalServiceQuery(additionalServiceQuery);
        //String serviceCriteria=" and ovc.ovcId in (select distinct service.ovcId from OvcService service where service.serviceAccessed5 is not null and service.serviceAccessed5 !='' and service.serviceAccessed5 !=' ' and service.serviceAccessed5 !='  '"+additionalServiceQuery+" )";
        mainlist.add("No of <b>VC</b> provided <b>Protection</b> services");
        String serviceCriteria=" and (service.serviceAccessed5 is not null and service.serviceAccessed5 !='' and service.serviceAccessed5 !=' ' and service.serviceAccessed5 !='  '"+additionalServiceQuery+" )";
        String malesql=serviceCountQueryPart+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+serviceCriteria;
        String femalesql=serviceCountQueryPart+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+serviceCriteria;
        //String bothGendersql="select count(distinct ovc.ovcId) From Ovc ovc, OvcService service where ovc.ovcId=service.ovcId and "+additionalQueryCriteria+ageQuery+serviceCriteria;
        mainlist.add(execReportQuery(malesql).get(0));
        mainlist.add(execReportQuery(femalesql).get(0));
        
        return mainlist;
    }
    public List getNoOfOvcProvidedShelterAndCareServices(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        additionalServiceQuery=getAdditionalServiceQuery(additionalServiceQuery);
        //String serviceCriteria=" and ovc.ovcId in (select distinct service.ovcId from OvcService service where service.serviceAccessed6 is not null and service.serviceAccessed6 !='' and service.serviceAccessed6 !=' ' and service.serviceAccessed6 !='  '"+additionalServiceQuery+" )";
        mainlist.add("No of <b>VC</b> provided <b>Shelter and care</b> services");
        String serviceCriteria=" and (service.serviceAccessed6 is not null and service.serviceAccessed6 !='' and service.serviceAccessed6 !=' ' and service.serviceAccessed6 !='  '"+additionalServiceQuery+" )";
        String malesql=serviceCountQueryPart+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+serviceCriteria;
        String femalesql=serviceCountQueryPart+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+serviceCriteria;
        //String bothGendersql="select count(distinct ovc.ovcId) From Ovc ovc, OvcService service where ovc.ovcId=service.ovcId and "+additionalQueryCriteria+ageQuery+serviceCriteria;
        mainlist.add(execReportQuery(malesql).get(0));
        mainlist.add(execReportQuery(femalesql).get(0));
        
        return mainlist;
    }
    public List getNoOfOvcWithdrawn(String sex,String additionalQueryCriteria,String additionalWithdrawalQuery,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        if(!additionalWithdrawalQuery.equalsIgnoreCase(" "))
            additionalWithdrawalQuery=" and "+additionalWithdrawalQuery;
        //additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        additionalServiceQuery=getAdditionalServiceQuery(additionalServiceQuery);
        String dataElement="No of <b>VC Withdrawn</b> from the program";
        String subquery="select count(distinct withdrawal.ovcId)"+util.getHouseholdQueryPart()+"Ovc ovc, OvcWithdrawal withdrawal where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=withdrawal.ovcId "+additionalWithdrawalQuery;
        String maleSubSql=subquery+" and "+util.getGenderCriteria(maleGender);
        String femaleSubSql=subquery+" and "+util.getGenderCriteria(femaleGender);
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
                
        if(ageQuery==null)
        {
            int[] ageSegregation=getAgeSegregation();
            
            for(int i=0; i<ageSegregation.length; i+=2)
            {
                ageQuery=util.getAgeCriteria(ageSegregation[i]+"", ageSegregation[i+1]+"");
                String malesql=maleSubSql+" "+additionalQueryCriteria+ageQuery;
                String femalesql=femaleSubSql+" "+additionalQueryCriteria+ageQuery;
                //System.err.println("femalesql is "+femalesql);
                maleList.add(execReportQuery(malesql).get(0));
                femaleList.add(execReportQuery(femalesql).get(0));
            }
            mainlist.add(dataElement);
            mainlist.addAll(maleList);
            mainlist.addAll(femaleList);
        }
        else
        {
            mainlist.add(dataElement);
            String malesql=maleSubSql+" "+additionalQueryCriteria+ageQuery;
            String femalesql=femaleSubSql+" "+additionalQueryCriteria+ageQuery;
            
            maleList.add(execReportQuery(malesql).get(0));
            femaleList.add(execReportQuery(femalesql).get(0));
            mainlist.addAll(maleList);
            mainlist.addAll(femaleList);
        }
        System.err.println("Inside getNoOfOvcWithdrawn");
        return mainlist;
    }
    public List getNoOfOvcOutOfProgram(String additionalQueryCriteria,String dataElement,String additionalServiceQuery,String reasonForWithdrawal) throws Exception
    {
        List mainlist=new ArrayList();
        additionalServiceQuery=getAdditionalServiceQuery(additionalServiceQuery);
        String maleSubSql=util.getHouseholdQueryPart()+"Ovc ovc where hhe.hhUniqueId=ovc.hhUniqueId and "+util.getGenderCriteria(maleGender);
        String femaleSubSql=util.getHouseholdQueryPart()+"Ovc ovc where hhe.hhUniqueId=ovc.hhUniqueId and "+util.getGenderCriteria(femaleGender);
        String serviceCriteria=" and ovc.ovcId in (select distinct withdrawal.ovcId from OvcWithdrawal withdrawal where withdrawal.reasonWithdrawal like '%"+reasonForWithdrawal+"%')";
        String ageQuery=" ";
            int[] ageSegregation=getAgeSegregation();
            List maleList=new ArrayList();
            List femaleList=new ArrayList();
            for(int i=0; i<ageSegregation.length; i+=2)
            {
                ageQuery=util.getAgeCriteria(ageSegregation[i]+"", ageSegregation[i+1]+"");
                String malesql=maleSubSql+" "+additionalQueryCriteria+ageQuery+serviceCriteria;
                String femalesql=femaleSubSql+" "+additionalQueryCriteria+ageQuery+serviceCriteria;
                maleList.add(execReportQuery(malesql).size());
                femaleList.add(execReportQuery(femalesql).size());
            }
            mainlist.add(dataElement);
            mainlist.addAll(maleList);
            mainlist.addAll(femaleList);
        System.err.println("Inside getNoOfOvcOutOfProgram");
        return mainlist;
    }
    public List getNoOfOvcAbove18YearsWhoGraduated(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        additionalServiceQuery=getAdditionalServiceQuery(additionalServiceQuery);
        String dataElement="No of <b>VC graduated</b> from the program ( > 18)";
        String maleSubSql=util.getHouseholdQueryPart()+"Ovc ovc where hhe.hhUniqueId=ovc.hhUniqueId and "+util.getGenderCriteria(maleGender);
        String femaleSubSql=util.getHouseholdQueryPart()+"Ovc ovc where hhe.hhUniqueId=ovc.hhUniqueId and "+util.getGenderCriteria(femaleGender);
        String serviceCriteria=" and ovc.ovcId in (select distinct withdrawal.ovcId from OvcWithdrawal withdrawal where withdrawal.reasonWithdrawal like '%Age > 18%' )";


        if(ageQuery==null)
        {
            List maleList=new ArrayList();
            List femaleList=new ArrayList();
                      
                String malesql=maleSubSql+" "+additionalQueryCriteria+serviceCriteria;
                String femalesql=femaleSubSql+" "+additionalQueryCriteria+serviceCriteria;
                maleList.add(execReportQuery(malesql).size());
                femaleList.add(execReportQuery(femalesql).size());
            
            mainlist.add(dataElement);
            mainlist.addAll(maleList);
            mainlist.addAll(femaleList);
        }
        System.err.println("Inside getNoOfOvcAbove18Years");
        return mainlist;
    }
    public List getListOfOvcThatReceivedThreeOrMoreServices(String sex,String additionalQueryCriteria,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        List list=new ArrayList();
        if(sex.equalsIgnoreCase("male"))
        list=maleGreaterThanThreeList;
        else if(sex.equalsIgnoreCase("female"))
        list=femaleGreaterThanThreeList;
        else if (sex.equalsIgnoreCase("Both gender"))
        {
            list.addAll(maleGreaterThanThreeList);
            list.addAll(femaleGreaterThanThreeList);
        }

        mainlist=getListOfOvcByOvcId(list);
        //System.err.println("malesql in getListOfOvcThatReceivedThreeOrMoreServices() is "+malesql);
        return mainlist;
    }
    public List getListOfOvcThatReceivedLessThanThreeServices(String sex,String additionalQueryCriteria,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        List list=new ArrayList();
        if(sex.equalsIgnoreCase("male"))
        list=maleLessThanThreeList;
        else if(sex.equalsIgnoreCase("female"))
        list=femaleLessThanThreeList;
        else if (sex.equalsIgnoreCase("Both gender"))
        {
            list.addAll(maleLessThanThreeList);
            list.addAll(femaleLessThanThreeList);
        }
        mainlist=getListOfOvcByOvcId(list);
        return mainlist;
    }
    public List getListOfOvcProvidedPsychosocialServices(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        additionalServiceQuery=getAdditionalServiceQuery(additionalServiceQuery);
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        String serviceCriteria=" and service.serviceAccessed1 is not null and service.serviceAccessed1 !='' and service.serviceAccessed1 !=' ' and service.serviceAccessed1 !='  '"+additionalServiceQuery;
        //String serviceCriteria=" and ovc.ovcId in (select distinct service.ovcId from OvcService service where service.serviceAccessed1 is not null and service.serviceAccessed1 !='' and service.serviceAccessed1 !=' ' and service.serviceAccessed1 !='  '"+additionalServiceQuery+" )";
        String malesql="select distinct service.ovcId "+hhOvcAndServiceQueryPart+additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+ageQuery+serviceCriteria;
        String femalesql="select distinct service.ovcId "+hhOvcAndServiceQueryPart+additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender)+ageQuery+serviceCriteria;
        String bothGendersql="select distinct service.ovcId "+hhOvcAndServiceQueryPart+additionalQueryCriteria+serviceCriteria;
        mainlist=getListOfOvcByOvcId(execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql));
        //System.err.println("Inside ");
        return mainlist;

    }
    public List getListOfOvcProvidedNutritionalServices(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        additionalServiceQuery=getAdditionalServiceQuery(additionalServiceQuery);
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        String serviceCriteria=" and service.serviceAccessed2 is not null and service.serviceAccessed2 !='' and service.serviceAccessed2 !=' ' and service.serviceAccessed2 !='  '"+additionalServiceQuery;
        String malesql="select distinct service.ovcId "+hhOvcAndServiceQueryPart+additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+serviceCriteria;
        String femalesql="select distinct service.ovcId "+hhOvcAndServiceQueryPart+additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+serviceCriteria;
        String bothGendersql="select distinct service.ovcId "+hhOvcAndServiceQueryPart+additionalQueryCriteria+ageQuery+serviceCriteria;
        mainlist=getListOfOvcByOvcId(execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql));
        //System.err.println("Inside ");
        return mainlist;

    }
    public List getListOfOvcHIVPositiveProvidedNutritionalServices(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        additionalServiceQuery=getAdditionalServiceQuery(additionalServiceQuery);
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        String serviceCriteria=" and service.serviceAccessed2 is not null and service.serviceAccessed2 !='' and service.serviceAccessed2 !=' ' and service.serviceAccessed2 !='  '"+additionalServiceQuery;
        String ovcHivQuery=util.getActiveHivStatusCriteria(NomisConstant.HIV_POSITIVE,NomisConstant.OVC_TYPE);
        String malesql="select distinct service.ovcId "+hhOvcServiceAndHivQueryPart+additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+ovcHivQuery+ageQuery+serviceCriteria;
        String femalesql="select distinct service.ovcId "+hhOvcServiceAndHivQueryPart+additionalQueryCriteria+ovcHivQuery+ageQuery+serviceCriteria;
        String bothGendersql="select distinct service.ovcId "+hhOvcServiceAndHivQueryPart+additionalQueryCriteria+ovcHivQuery+ageQuery+serviceCriteria;
        mainlist=getListOfOvcByOvcId(execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql));
        return mainlist;
    }
    public List getListOfOvcHIVNegativeProvidedNutritionalServices(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        additionalServiceQuery=getAdditionalServiceQuery(additionalServiceQuery);
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        String serviceCriteria=" and service.serviceAccessed2 is not null and service.serviceAccessed2 !='' and service.serviceAccessed2 !=' ' and service.serviceAccessed2 !='  '"+additionalServiceQuery;
        String ovcHivQuery=util.getActiveHivStatusCriteria(NomisConstant.HIV_NEGATIVE,NomisConstant.OVC_TYPE);
        String malesql="select distinct service.ovcId "+hhOvcServiceAndHivQueryPart+additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+ovcHivQuery+ageQuery+serviceCriteria;
        String femalesql="select distinct service.ovcId "+hhOvcServiceAndHivQueryPart+additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender)+ovcHivQuery+ageQuery+serviceCriteria;
        String bothGendersql="select distinct service.ovcId "+hhOvcServiceAndHivQueryPart+additionalQueryCriteria+ovcHivQuery+ageQuery+serviceCriteria;
        mainlist=getListOfOvcByOvcId(execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql));
        return mainlist;
    }
    public List getListOfOvcHIVUnknownProvidedNutritionalServices(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        additionalServiceQuery=getAdditionalServiceQuery(additionalServiceQuery);
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        String serviceCriteria=" and service.serviceAccessed2 is not null and service.serviceAccessed2 !='' and service.serviceAccessed2 !=' ' and service.serviceAccessed2 !='  '"+additionalServiceQuery;
        String ovcHivQuery=util.getActiveHivStatusCriteria(NomisConstant.HIV_UNKNOWN,NomisConstant.OVC_TYPE);
        String malesql="select distinct service.ovcId "+hhOvcServiceAndHivQueryPart+additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+ovcHivQuery+ageQuery+serviceCriteria;
        String femalesql="select distinct service.ovcId "+hhOvcServiceAndHivQueryPart+additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender)+ovcHivQuery+ageQuery+serviceCriteria;
        String bothGendersql="select distinct service.ovcId "+hhOvcServiceAndHivQueryPart+additionalQueryCriteria+ovcHivQuery+ageQuery+serviceCriteria;
        mainlist=getListOfOvcByOvcId(execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql));
        return mainlist;
    }
    public List getListOfOvcProvidedHealthServices(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        additionalServiceQuery=getAdditionalServiceQuery(additionalServiceQuery);
        String serviceCriteria=" and service.serviceAccessed3 is not null and service.serviceAccessed3 !='' and service.serviceAccessed3 !=' ' and service.serviceAccessed3 !='  '"+additionalServiceQuery;
        String malesql="select distinct service.ovcId "+hhOvcAndServiceQueryPart+additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+ageQuery+serviceCriteria;
        String femalesql="select distinct service.ovcId "+hhOvcAndServiceQueryPart+additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender)+ageQuery+serviceCriteria;
        String bothGendersql="select distinct service.ovcId "+hhOvcAndServiceQueryPart+additionalQueryCriteria+ageQuery+serviceCriteria;
        mainlist=getListOfOvcByOvcId(execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql));
        return mainlist;
    }
    public List getListOfOvcProvidedEducationalServices(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        additionalServiceQuery=getAdditionalServiceQuery(additionalServiceQuery);
        String serviceCriteria=" and service.serviceAccessed4 is not null and service.serviceAccessed4 !='' and service.serviceAccessed4 !=' ' and service.serviceAccessed4 !='  '"+additionalServiceQuery;
        String malesql="select distinct service.ovcId "+hhOvcAndServiceQueryPart+additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+ageQuery+serviceCriteria;
        String femalesql="select distinct service.ovcId "+hhOvcAndServiceQueryPart+additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender)+ageQuery+serviceCriteria;
        String bothGendersql="select distinct service.ovcId "+hhOvcAndServiceQueryPart+additionalQueryCriteria+ageQuery+serviceCriteria;
        mainlist=getListOfOvcByOvcId(execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql));
        return mainlist;
    }
    public List getListOfOvcProvidedProtectionServices(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        additionalServiceQuery=getAdditionalServiceQuery(additionalServiceQuery);
        String serviceCriteria=" and service.serviceAccessed5 is not null and service.serviceAccessed5 !='' and service.serviceAccessed5 !=' ' and service.serviceAccessed5 !='  '"+additionalServiceQuery;
        String malesql="select distinct service.ovcId "+hhOvcAndServiceQueryPart+additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+ageQuery+serviceCriteria;
        String femalesql="select distinct service.ovcId "+hhOvcAndServiceQueryPart+additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender)+ageQuery+serviceCriteria;
        String bothGendersql="select distinct service.ovcId "+hhOvcAndServiceQueryPart+additionalQueryCriteria+ageQuery+serviceCriteria;
        mainlist=getListOfOvcByOvcId(execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql));
        return mainlist;
    }
    public List getListOfOvcProvidedShelterAndCareServices(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        additionalServiceQuery=getAdditionalServiceQuery(additionalServiceQuery);
        String serviceCriteria=" and service.serviceAccessed6 is not null and service.serviceAccessed6 !='' and service.serviceAccessed6 !=' ' and service.serviceAccessed6 !='  '"+additionalServiceQuery;
        String malesql="select distinct service.ovcId "+hhOvcAndServiceQueryPart+additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+ageQuery+serviceCriteria;
        String femalesql="select distinct service.ovcId "+hhOvcAndServiceQueryPart+additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender)+ageQuery+serviceCriteria;
        String bothGendersql="select distinct service.ovcId "+hhOvcAndServiceQueryPart+additionalQueryCriteria+ageQuery+serviceCriteria;
        mainlist=getListOfOvcByOvcId(execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql));
        return mainlist;
    }
    public List getListOfOvcWithdrawn(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        additionalServiceQuery=getAdditionalServiceQuery(additionalServiceQuery);
        String serviceCriteria=" and ovc.ovcId in (select distinct withdrawal.ovcId from OvcWithdrawal withdrawal )";
        String malesql=hhQueryPart+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery+serviceCriteria;
        String femalesql=hhQueryPart+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery+serviceCriteria;
        String bothGendersql=hhQueryPart+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery+serviceCriteria;
        mainlist=getListOfOvc(execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql));
        return mainlist;
    }
    public List getListOfCaregiversServed(String sex,String additionalQueryCriteria,String hviPeriodQuery,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        String orderByQuery=" order by hhs.numberOfServices desc";
        String malesql="from HouseholdEnrollment hhe, Caregiver cgiver, HouseholdService hhs where hhe.hhUniqueId=hhs.hhUniqueId and hhs.hhUniqueId=cgiver.hhUniqueId and "+util.getCaregiverGenderCriteria(maleGender)+" "+additionalServiceQuery+orderByQuery;
        String femalesql="from HouseholdEnrollment hhe, Caregiver cgiver, HouseholdService hhs where hhe.hhUniqueId=hhs.hhUniqueId and hhs.hhUniqueId=cgiver.hhUniqueId and "+util.getCaregiverGenderCriteria(femaleGender)+" "+additionalServiceQuery+orderByQuery;
        String bothGendersql="from HouseholdEnrollment hhe, Caregiver cgiver, HouseholdService hhs where hhe.hhUniqueId=hhs.hhUniqueId and hhs.hhUniqueId=cgiver.hhUniqueId "+additionalServiceQuery+orderByQuery;
       //System.err.println(malesql);
       //System.err.println(bothGendersql);
        mainlist=execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql);
        return mainlist;
    }
    public List getTotalNoOfOvcThatShowedAtleastOneScoreImprovement(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        mainlist.add("Total number of VC that showed at least one score improvement in any VC service area");
        String malesql="select distinct csi.ovcId "+util.getHouseholdQueryPart()+"Ovc ovc,ChildStatusIndex csi where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=csi.ovcId) and csi.surveyNumber > 1 and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+additionalEnrollmentQuery+ageQuery+")";
        String femalesql="select distinct csi.ovcId "+util.getHouseholdQueryPart()+"Ovc ovc,ChildStatusIndex csi where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=csi.ovcId) and csi.surveyNumber >1 and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+additionalEnrollmentQuery+ageQuery+")";
        /*String malesql="select distinct fs.ovcId from FollowupSurvey fs where fs.ovcId in (select ovc.ovcId From Ovc ovc where "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+additionalEnrollmentQuery+ageQuery+")";
        String femalesql="select distinct fs.ovcId from FollowupSurvey fs where fs.ovcId in (select ovc.ovcId From Ovc ovc where "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+additionalEnrollmentQuery+ageQuery+")";*/
        
        mainlist.add(NoOfOvcWithImprovedCSIScore(malesql));
        mainlist.add(NoOfOvcWithImprovedCSIScore(femalesql));
        System.err.println("Inside getTotalNoOfOvcThatShowedAtleastOneScoreImprovement");
        return mainlist;
    }
    private int NoOfOvcWithImprovedCSIScore(String query)
    {
        List list=new ArrayList();
        List csiList=new ArrayList();
        List csiTotalList=new ArrayList();
        ChildStatusIndex csi;
        ChildStatusIndexDao csiDao=new ChildStatusIndexDaoImpl();
        String ovcId;
        int score=0;
        list=execReportQuery(query);
                
        for(int i=0; i<list.size(); i++)
        {
            csiTotalList.clear();
            ovcId=list.get(i).toString();
            csiList=execReportQuery("From ChildStatusIndex csi where csi.ovcId='"+ovcId+"'");
            try
            {
                if(csiList.size()>1)
                {
                    //System.err.println("csiList size is "+csiList.size());
                    for(int j=0; j<csiList.size(); j++)
                    {
                        csi=(ChildStatusIndex)csiList.get(j);
                        csiTotalList.add(csiDao.getTotalCsiScore(csi));
                    }
                }
                if(csiTotalList.size()>1)
                {
                    score+=util.compareTotalScore(csiTotalList);
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        System.err.println("Inside NoOfOvcWithImprovedCSIScore");
        return score;
    }
    public List getNoOfOrganizationsWithAtleastOneScoreImprovement(String additionalQueryCriteria,String additionalEnrollmentQuery) throws Exception
    {
        List mainlist=new ArrayList();
        List list=new ArrayList();
        List orgList=new ArrayList();
        List totalScoreList=new ArrayList();
        int score=0;
        OrganizationalCapacityAssessment oCapAssessment=null;
        OrganizationalCapacityAssessmentDao ocaDao=new OrganizationalCapacityAssessmentDaoImpl();
        String sql=null;
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);

        mainlist.add("No. of organizations and agencies demonstrating at least one score improvement in at least one area of capacity building");
        mainlist.add(0);
        String query="select oca.orgCode From OrganizationalCapacityAssessment oca ";
        
        list=execReportQuery(query);

        for(int i=0; i<list.size(); i++)
        {
            totalScoreList.clear();
            sql=("From OrganizationalCapacityAssessment oca where oca.orgCode = '"+list.get(i).toString()+"'");
            orgList=execReportQuery(sql);
            if(orgList.size() > 1)
            {
                for(int j=0; j<orgList.size(); j++)
                {
                    oCapAssessment=(OrganizationalCapacityAssessment)orgList.get(j);
                    totalScoreList.add(ocaDao.getTotalScore(oCapAssessment));
                }
                if(totalScoreList.size()>1)
                {
                    score+=util.compareTotalScore(totalScoreList);
                }
            }
        }
        mainlist.add(score);
        System.err.println("Inside getNoOfOrganizationsWithAtleastOneScoreImprovement");
        return mainlist;
    }
    public void execSqlQuery(String sql)
    {
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
    }
    /*public List getListOfHousholdProvidedBasicExternalSupport(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ovcServiceQuery) throws Exception
    {
        List mainlist=new ArrayList();
        List<String> list=new ArrayList();
        if(!additionalServiceQuery.equals(" "))
            additionalServiceQuery=" where "+additionalServiceQuery;
        if(ovcServiceQuery !=null && !ovcServiceQuery.equalsIgnoreCase(" "))
           ovcServiceQuery=" and "+ovcServiceQuery;
        String malesql="select distinct hhe.hhUniqueId From HouseholdEnrollment hhe, HouseholdService hhs where hhe.hhUniqueId=hhs.hhUniqueId and "+util.getHhGenderCriteria(maleGender)+" "+additionalQueryCriteria;
        String femalesql="select distinct hhe.hhUniqueId From HouseholdEnrollment hhe, HouseholdService hhs where hhe.hhUniqueId=hhs.hhUniqueId and "+util.getHhGenderCriteria(femaleGender)+" "+additionalQueryCriteria;
        String bothGendersql="select distinct hhe.hhUniqueId From HouseholdVulnerabilityIndex hvi, HouseholdService hhs where hhe.hhUniqueId=hhs.hhUniqueId "+additionalQueryCriteria+additionalEnrollmentQuery;
        String ovcsql="select distinct service.ovcId From OvcService service where service.ovcId is not null "+ovcServiceQuery;
        list.addAll(execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql));
        System.err.println("ovcsql in getListOfHousholdProvidedBasicExternalSupport is "+ovcsql);
        HouseholdVulnerabilityIndex hvi=null;
        HouseholdVulnerabilityIndexDao hviDao=new HouseholdVulnerabilityIndexDaoImpl();
        
        //List<String> serviceIdList=new ArrayList<String>();
        List<String> serviceIdList=execReportQuery(ovcsql);
        String hhUniqueId=null;
        String serviceId=null;
        for(int i=0; i<serviceIdList.size(); i++)
        {
            serviceId=serviceIdList.get(i);
            hhUniqueId=serviceId.substring(0,serviceId.lastIndexOf("/"));
            if(!list.contains(hhUniqueId))
            {
                hvi=hviDao.getHouseholdVulnerabilityIndex(hhUniqueId);
                if(hvi !=null)
                {
                    list.add(hvi.getHhUniqueId());
                }
            }

        }
        for(int j=0; j<list.size(); j++)
        {
             hhUniqueId=list.get(j);
             hvi=hviDao.getHouseholdVulnerabilityIndex(hhUniqueId);
             if(hvi !=null)
             mainlist.add(hvi);
        }
        
        
        return mainlist;
    }*/
    /*private List getCaregiverAddressAndNoOfOVC(String additionalQuery,String caregiverName) throws Exception
    {
        List list = new ArrayList();
        List mainList = new ArrayList();
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct ovc.caregiverAddress from Ovc ovc where ovc.caregiverName = :caregiver_name "+additionalQuery)
            .setString("caregiver_name", caregiverName).list();
            mainList.add(list.get(0));
            tx.commit();
            session.close();
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select count(ovc.ovcId) from Ovc ovc where ovc.caregiverName = :caregiver_name "+additionalQuery+" group by ovc.caregiverName")
            .setString("caregiver_name", caregiverName).list();
            mainList.add(list.get(0));
            tx.commit();
        session.close();
        }
        catch (HibernateException he)
        {
            throw new Exception(he);
        }
        return mainList;
    }*/
    
}
