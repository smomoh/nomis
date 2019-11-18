/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report;

import com.fhi.kidmap.business.Caregiver;
import com.fhi.kidmap.business.ChildStatusIndex;
import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.kidmap.business.OrganizationalCapacityAssessment;
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.business.OvcService;
import com.fhi.kidmap.dao.BirthRegistrationDao;
import com.fhi.kidmap.dao.BirthRegistrationDaoImpl;
import com.fhi.kidmap.dao.CareAndSupportDao;
import com.fhi.kidmap.dao.CareAndSupportDaoImpl;
import com.fhi.kidmap.dao.CaregiverDao;
import com.fhi.kidmap.dao.CaregiverDaoImpl;
import com.fhi.kidmap.dao.CaregiverExpenditureAndSchoolAttendanceDao;
import com.fhi.kidmap.dao.CaregiverExpenditureAndSchoolAttendanceDaoImpl;
import com.fhi.kidmap.dao.CaregiverTBHIVScreeningChecklistDao;
import com.fhi.kidmap.dao.CaregiverTBHIVScreeningChecklistDaoImpl;
import com.fhi.kidmap.dao.ChildSchoolStatusDao;
import com.fhi.kidmap.dao.ChildSchoolStatusDaoImpl;
import com.fhi.kidmap.dao.ChildStatusIndexDao;
import com.fhi.kidmap.dao.ChildStatusIndexDaoImpl;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.FollowupDao;
import com.fhi.kidmap.dao.FollowupDaoImpl;
import com.fhi.kidmap.dao.HibernateUtil;
import com.fhi.kidmap.dao.HivRiskAssessmentChecklistDao;
import com.fhi.kidmap.dao.HivRiskAssessmentChecklistDaoImpl;
import com.fhi.kidmap.dao.HivStatusUpdateDao;
import com.fhi.kidmap.dao.HivStatusUpdateDaoImpl;
import com.fhi.kidmap.dao.HouseholdEnrollmentDao;
import com.fhi.kidmap.dao.HouseholdEnrollmentDaoImpl;
import com.fhi.kidmap.dao.HouseholdFollowupAssessmentDao;
import com.fhi.kidmap.dao.HouseholdFollowupAssessmentDaoImpl;
import com.fhi.kidmap.dao.HouseholdServiceDao;
import com.fhi.kidmap.dao.HouseholdServiceDaoImpl;
import com.fhi.kidmap.dao.HouseholdVulnerabilityAssessmentDao;
import com.fhi.kidmap.dao.HouseholdVulnerabilityAssessmentDaoImpl;
import com.fhi.kidmap.dao.NutritionAssessmentDao;
import com.fhi.kidmap.dao.NutritionAssessmentDaoImpl;
import com.fhi.kidmap.dao.OrganizationalCapacityAssessmentDao;
import com.fhi.kidmap.dao.OrganizationalCapacityAssessmentDaoImpl;
import com.fhi.kidmap.dao.OvcDao;
import com.fhi.kidmap.dao.OvcDaoImpl;
import com.fhi.kidmap.dao.OvcSchoolAttendanceDao;
import com.fhi.kidmap.dao.OvcSchoolAttendanceDaoImpl;
import com.fhi.kidmap.dao.OvcServiceDao;
import com.fhi.kidmap.dao.OvcServiceDaoImpl;
import com.fhi.kidmap.dao.OvcTBHIVScreeningChecklistDao;
import com.fhi.kidmap.dao.OvcTBHIVScreeningChecklistDaoImpl;
import com.fhi.kidmap.dao.OvcWithdrawalDao;
import com.fhi.kidmap.dao.OvcWithdrawalDaoImpl;
import com.fhi.kidmap.dao.SchoolAttendanceDao;
import com.fhi.kidmap.dao.SchoolAttendanceDaoImpl;
import com.fhi.nomis.logs.NomisLogManager;
import com.fhi.nomis.nomisutils.NomisConstant;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Siaka
 */
public class SummaryStatisticsReportGenerator implements Serializable
{
    Session session;
    Transaction tx;
    DaoUtil util=new DaoUtil();
    OvcServiceDao serviceDao=new OvcServiceDaoImpl();
    OvcDao ovcdao=new OvcDaoImpl();
    OvcRecords records=new OvcRecords();
    ReportUtility rutil=new ReportUtility();
    private final String maleGender="male";
    private final String femaleGender="female";
    String hviMaleGender="Male";
    String hviFemaleGender="Female";
    String hhQueryPart=null;
    String hhOvcAndServiceQueryPart=null;
    String hhOvcAndServiceByOvcIdQueryPart=null;
    String countQueryPart=null;
    String serviceQueryPart=null;
    String serviceCountQueryPart=null;
    String withdrawalServiceCountQueryPart=null;
    //String withdrawalServiceCountQueryPartByHiv=null;
    String hhOvcServicefollowupQueryPart=null;
    String hhOvcfollowupQueryPart=null;
    String hheCaregiverHhsQuery=null;
    String hheCaregiverHhsListQuery=null;
    String withdrawalQueryWithinReportPeriod="";
    String withdrawalServicePeriodQuery="";
    String hhHivQueryPart=null;
    String hhOvcAndServiceHivQueryPart=null;
    String hhOvcAndServiceByOvcIdHivQueryPart=null;
    String countHivQueryPart=null;
    String serviceCountHivQueryPart=null;
    String withdrawalQuery="";
    String birthCertQuery=null;
    String currEnrolledQuery=null;
    
    String ageQuery;
    String lessThan18ageQuery=util.getAgeCriteria(0+"", 17+"");;
    List maleLessThanThreeList=new ArrayList();
    List femaleLessThanThreeList=new ArrayList();
    List maleGreaterThanThreeList=new ArrayList();
    List femaleGreaterThanThreeList=new ArrayList();
    OvcWithdrawalDao withdrawalDao=new OvcWithdrawalDaoImpl();
    String lastDateOfHivStatus=null;
    
    public SummaryStatisticsReportGenerator()
    {
        util=new DaoUtil();
        hhQueryPart=util.getHouseholdQueryPart()+"Ovc ovc where hhe.hhUniqueId=ovc.hhUniqueId and ";
        countQueryPart="select count(distinct ovc.ovcId) from Ovc ovc,"+util.getHouseholdObjectName()+" where hhe.hhUniqueId=ovc.hhUniqueId and ";
        serviceQueryPart=" from OvcService service, Ovc ovc,"+util.getHouseholdObjectName()+"  where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=service.ovcId) and " ;
        serviceCountQueryPart="select count(distinct service.ovcId) from OvcService service, Ovc ovc,"+util.getHouseholdObjectName()+"  where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=service.ovcId) and " ;
        withdrawalServiceCountQueryPart="from OvcService service, Ovc ovc,"+util.getHouseholdObjectName()+",OvcWithdrawal withdrawal  where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=service.ovcId and ovc.ovcId=withdrawal.ovcId) " ;
        //withdrawalServiceCountQueryPartByHiv="from OvcService service, Ovc ovc,"+util.getHouseholdObjectName()+",OvcWithdrawal withdrawal  where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=service.ovcId and ovc.ovcId=withdrawal.ovcId) " ;
        hhOvcfollowupQueryPart=util.getHouseholdQueryPart()+" Ovc ovc,FollowupSurvey fu where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=fu.ovcId ";
        hhOvcServicefollowupQueryPart=util.getHouseholdQueryPart()+" Ovc ovc,OvcService service,FollowupSurvey fu where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=fu.ovcId and fu.ovcId=service.ovcId";
        hhOvcAndServiceQueryPart=util.getHouseholdQueryPart()+"Ovc ovc,OvcService service where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=service.ovcId) ";
        hhOvcAndServiceByOvcIdQueryPart="select distinct service.ovcId "+util.getHouseholdQueryPart()+"Ovc ovc,OvcService service where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=service.ovcId) ";
        hheCaregiverHhsQuery="select count(distinct hhs.caregiverId) from HouseholdService hhs, HouseholdEnrollment hhe,Caregiver cgiver where (hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.hhUniqueId=hhs.hhUniqueId)";
        hheCaregiverHhsListQuery="from HouseholdEnrollment hhe,Caregiver cgiver,HouseholdService hhs where (hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.hhUniqueId=hhs.hhUniqueId)";
        
        hhHivQueryPart=util.getHouseholdQueryPart()+"Ovc ovc,"+util.getHivStatusUpdateQueryPart()+" where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=hsu.clientId ";
        countHivQueryPart="select count(distinct ovc.ovcId) from Ovc ovc,"+util.getHouseholdObjectName()+","+util.getHivStatusUpdateQueryPart()+" where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=hsu.clientId ";
        serviceCountHivQueryPart="select count(distinct service.ovcId) from OvcService service, Ovc ovc,"+util.getHouseholdObjectName()+","+util.getHivStatusUpdateQueryPart()+"  where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=service.ovcId and ovc.ovcId=hsu.clientId) and " ;
        hhOvcAndServiceHivQueryPart=util.getHouseholdQueryPart()+"Ovc ovc,OvcService service,"+util.getHivStatusUpdateQueryPart()+" where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=service.ovcId and ovc.ovcId=hsu.clientId ) ";
        hhOvcAndServiceByOvcIdHivQueryPart="select distinct service.ovcId "+util.getHouseholdQueryPart()+"Ovc ovc,OvcService service,"+util.getHivStatusUpdateQueryPart()+" where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=service.ovcId and ovc.ovcId=hsu.clientId) "; 
        //birthCertQuery="(UPPER(ovc.birthCertificate)='NO' or ovc.birthCertificate='' or ovc.birthCertificate=' ' or ovc.birthCertificate='  ')";
        birthCertQuery="(UPPER(ovc.birthCertificate) !='YES')";
        //withdrawalQuery=" and ovc.ovcId not in (select distinct withdrawal.ovcId from OvcWithdrawal withdrawal)";
    }
    public List execReportQuery(String sql)
    {
        List list=new ArrayList();
        list=util.execReportQuery(sql);
        return list;
    }
    private List getListOfOvcByOvcId(List listOfOvcIds)
    {
        List mainList=new ArrayList();
        OvcDao ovcdao=new OvcDaoImpl();
        try
        {
            mainList=ovcdao.getListOfOvcByOvcId(listOfOvcIds);
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
    private List getListOfDistinctOvc(List list)
    {
        List mainlist=new ArrayList();
        List distinctOvcIdList=new ArrayList();
        String ovcId=null;
        if(list !=null && !list.isEmpty())
        {
            Ovc ovc=null;
            for(Object s:list)
            {
                //Object[] obj=(Object[])s;
                ovc=(Ovc)s;
                ovcId=ovc.getOvcId();
                if(!distinctOvcIdList.contains(ovcId))
                {
                    distinctOvcIdList.add(ovcId);
                    mainlist.add(ovc);
                }
            }
        }
        return mainlist;
    }
    private List getListOfDistinctOvcFromQueryObject(List list)
    {
        List mainlist=new ArrayList();
        List distinctOvcIdList=new ArrayList();
        String ovcId=null;
        if(list !=null && !list.isEmpty())
        {
            Ovc ovc=null;
            for(Object s:list)
            {
                Object[] obj=(Object[])s;
                ovc=(Ovc)obj[1];
                ovcId=ovc.getOvcId();
                if(!distinctOvcIdList.contains(ovcId))
                {
                    distinctOvcIdList.add(ovcId);
                    mainlist.add(ovc);
                }
            }
        }
        return mainlist;
    }
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
            else if(sex.equalsIgnoreCase("Both gender") || sex.equalsIgnoreCase("Both"))
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
    /*private String getQueryCriteria(String[] params)
    {
        String additionalQueryCriteria=util.getQueryCriteria(params);
        return additionalQueryCriteria;
    }*/
    private String getAdditionalEnrollmentQuery(String additionalEnrollmentQuery)
    {
        return util.getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
    }
    private String getAdditionalServiceQuery(String additionalServiceQuery)
    {
        return util.getAdditionalServiceQuery(additionalServiceQuery);
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
        String startDate=util.getStartDate(dateParams);
        String endDate=util.getEndDate(dateParams);
        withdrawalQueryWithinReportPeriod=util.getDateQueryForWithdrawalWithinReportPeriod(startDate, endDate);
        withdrawalServicePeriodQuery=util.getWithdrawalServicePeriodCriteria(startDate);
        lastDateOfHivStatus=startDate;
        String enrollmentEndDateQuery=util.getEnrollmentEndDateQuery(dateParams);
        String hivStatusPeriodQuery=util.getHIVStatusDateQuery(util.getStartDate(dateParams), util.getEndDate(dateParams));
        ageQuery=util.getAgeCriteria(params[15], params[16]);
        String sex=reqparams[2];
        String indicatorCode=reqparams[1];
        //int index=Integer.parseInt(reqparams[1]);
        String additionalQueryCriteria=util.getSummStatQueryCriteria(params);
        List list=new ArrayList();
        String[] periodParams={params[7], params[8], params[9],params[10]};
        String[] hviParams=getHVIParams(params);//{params[0],params[1],params[2],null,null,null,params[7], params[8], params[9],params[10],params[18],params[19]};
        String hviQueryCriteria=util.getHVIReportCriteria(hviParams);
        String hviQueryCriteriaWithoutPeriod=util.getHVIOrgUnitQuery(hviParams);
        String hviServiceQueryCriteria=util.getHVIServiceReportCriteria(hviParams);
        String hviPeriodQuery=" ";
        String cgiverPeriodQuery=" ";
        String withdrawalPeriodQuery=" ";
        if(!params[7].equalsIgnoreCase("all") && !params[8].equalsIgnoreCase("all") && !params[9].equalsIgnoreCase("all") && !params[10].equalsIgnoreCase("all"))
        {
            hviPeriodQuery=util.getHhePeriodCriteria(util.getStartDate(periodParams),util.getEndDate(periodParams));
            cgiverPeriodQuery=util.getCaregiverPeriodCriteria(util.getStartDate(periodParams),util.getEndDate(periodParams));
            withdrawalPeriodQuery=util.getWithdrawalPeriodCriteria(util.getStartDate(periodParams),util.getEndDate(periodParams));
        }
        
        String[] dateArray=null;
        if(!params[7].equalsIgnoreCase("all") && !params[8].equalsIgnoreCase("all") && !params[9].equalsIgnoreCase("all") && !params[10].equalsIgnoreCase("all"))
        {
            String[] dateArray2={util.getStartDate(periodParams),util.getEndDate(periodParams)};
            dateArray=dateArray2;
        }
        if(indicatorCode.equalsIgnoreCase("vcschattend"))
        list=getListOfOvcRegularlyAttendingSchool(sex,additionalQueryCriteria,ageQuery,startDate,endDate,true);
        else if(indicatorCode.equalsIgnoreCase("hhemergneed"))
        list=getListOfHouseholdsThatAreAbleToPayForUnexpectedExpenses(sex,additionalQueryCriteria,ageQuery,startDate,endDate,false);
        else if(indicatorCode.equalsIgnoreCase("vcadherence"))
        list=getListOfHIVPositiveOvcWhoReportedAdherenceToTreatmentWithinTheReportPeriod(sex,additionalQueryCriteria,ageQuery,startDate,endDate,false,false);
        else if(indicatorCode.equalsIgnoreCase("vcnewposart"))
        list=getListOfHIVPositiveOvcIdentifiedAndServedWithinTheReportPeriod(sex,additionalQueryCriteria,ageQuery,startDate,endDate,false,true);
        else if(indicatorCode.equalsIgnoreCase("vctstresult"))
        list=getListOfOvcTestedAndReceivedResult(sex,additionalQueryCriteria,ageQuery,startDate,endDate,false);
        else if(indicatorCode.equalsIgnoreCase("vchivprevrp"))
        list=getListOfAdolescentsProvidedWithHIVPreventionServicesWithinTheReportPeriod(sex,additionalQueryCriteria+ageQuery,startDate,endDate,false);
        else if(indicatorCode.equalsIgnoreCase("vcabusedrpe"))
        list=getListOfOvcAbusedAndExploitedWithinTheReportPeriod(sex,additionalQueryCriteria+ageQuery,startDate,endDate,false);
        else if(indicatorCode.equalsIgnoreCase("vclgovptvrp"))
        list=getListOfOvcLinkedToGovtForPostViolenceServicesWithinTheReportPeriod(sex,additionalQueryCriteria+ageQuery,startDate,endDate,false);
        else if(indicatorCode.equalsIgnoreCase("vckidsclbrp"))
        list=getListOfOvcEnrolledInKidsClubWithinTheReportPeriod(sex,additionalQueryCriteria+ageQuery,startDate,endDate,false);
        else if(indicatorCode.equalsIgnoreCase("vctestedhiv"))
        list=getListOfOvcTestedForHIV(sex,additionalQueryCriteria,ageQuery,startDate,endDate,false);
        else if(indicatorCode.equalsIgnoreCase("vcrefforhiv"))
        list=getListOfOvcTestedForHIV(sex,additionalQueryCriteria,ageQuery,startDate,endDate,false);
        else if(indicatorCode.equalsIgnoreCase("vccurhivpos"))
        list=getListOfOvcThatAreHIVPositive(sex,additionalQueryCriteria,ageQuery,startDate,endDate,false);
        else if(indicatorCode.equalsIgnoreCase("vchivposide"))
        list=getListOfHIVPositiveOvcIdentifiedWithinTheReport(sex,additionalQueryCriteria,ageQuery,startDate,endDate,false);
        else if(indicatorCode.equalsIgnoreCase("vccmuacgt12"))
        list=getListOfWellNourishedOvcAtBaseline(sex,additionalQueryCriteria,startDate,endDate,true,false,false);
        else if(indicatorCode.equalsIgnoreCase("vccmuac1112"))
        list=getListOfModeratelyNourishedOvcAtBaseline(sex,additionalQueryCriteria,startDate,endDate,true,false,false);
        else if(indicatorCode.equalsIgnoreCase("vccmuaclt11"))
        list=getListOfSeverelyMalnourishedOvcAtBaseline(sex,additionalQueryCriteria,startDate,endDate,true,false,false);
        
       else if(indicatorCode.equalsIgnoreCase("vcmuacgth12"))
        list=getListOfWellNourishedOvcAtBaseline(sex,additionalQueryCriteria,startDate,endDate,false,true,false);
        else if(indicatorCode.equalsIgnoreCase("vcmua11to12"))
        list=getListOfModeratelyNourishedOvcAtBaseline(sex,additionalQueryCriteria,startDate,endDate,false,true,false);
        else if(indicatorCode.equalsIgnoreCase("vcmuaclth11"))
        list=getListOfSeverelyMalnourishedOvcAtBaseline(sex,additionalQueryCriteria,startDate,endDate,false,true,false);
        else if(indicatorCode.equalsIgnoreCase("vccehhblass"))
        list=getListOfOvcCurrentlyEnrolledInHouseholdsWithBaselineAssessment(sex,additionalQueryCriteria+ageQuery,startDate,endDate,true);
        else if(indicatorCode.equalsIgnoreCase("vccenohivra"))
        list=getListOfOvcThatHasNoHIVRiskAssessmentRecord(sex,additionalQueryCriteria, startDate,endDate,true);
        
        else if(indicatorCode.equalsIgnoreCase("vchivrskser"))
        list=getListOfOvcAssessedForHIVRiskAndServed(sex,additionalQueryCriteria, startDate,endDate,ageQuery,NomisConstant.HIV_NEGATIVE,false,false);
        
        else if(indicatorCode.equalsIgnoreCase("vcnegrskser"))
        list=getListOfOvcAssessedForHIVRiskAndServed(sex,additionalQueryCriteria, startDate,endDate,ageQuery,NomisConstant.HIV_UNKNOWN,false,false);
        else if(indicatorCode.equalsIgnoreCase("vcunkrskser"))
        list=getListOfOvcAssessedForHIVRiskAndServed(sex,additionalQueryCriteria, startDate,endDate,ageQuery,"All",false,false);
        
        else if(indicatorCode.equalsIgnoreCase("vchivatrisk"))
        list=getListOfOvcThatHasHIVRiskAssessmentDone(sex,additionalQueryCriteria, startDate,endDate,ageQuery,true,true);
        else if(indicatorCode.equalsIgnoreCase("vccehivrisk"))
        list=getListOfOvcThatHasHIVRiskAssessmentDone(sex,additionalQueryCriteria, startDate,endDate,ageQuery,false,true);
        else if(indicatorCode.equalsIgnoreCase("vceehivrisk"))
        list=getListOfOvcThatHasHIVRiskAssessmentDone(sex,additionalQueryCriteria, startDate,endDate,ageQuery,false,false);
        else if(indicatorCode.equalsIgnoreCase("vcnashivrsk"))
        list=getListOfOvcNeverAssessedForHivRiskAssessment(sex,additionalQueryCriteria,startDate,endDate,false);
        if(indicatorCode.equalsIgnoreCase("vccgrdclhiv"))
        list=getListOfOvcByHivStatusFromHivRiskAssessment(sex,additionalQueryCriteria,startDate,endDate,NomisConstant.HIV_RESULT_NOT_DISCLOSED,false);
        if(indicatorCode.equalsIgnoreCase("vccgknhivst"))
        list=getListOfOvcWhoseCaregiversKnowTheirHivStatus(sex,additionalQueryCriteria,startDate,endDate,"Yes",false);
        if(indicatorCode.equalsIgnoreCase("vchivrskref"))
        list=getListOfOvcAssessedForHivRiskAndReferredForTesting(sex,additionalQueryCriteria,startDate,endDate,"Yes",false);
        
        if(indicatorCode.equalsIgnoreCase("vcnewlyEnro"))
        list=getListOfOvcEnrolled(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery);
        else if(indicatorCode.equalsIgnoreCase("vccenrolled"))
        list=getListOfOvcCurrentlyEnrolled(sex,additionalQueryCriteria,enrollmentEndDateQuery,ageQuery);
        else if(indicatorCode.equalsIgnoreCase("vcevenroled"))
        list=getListOfOvcEverEnrolled(sex,additionalQueryCriteria,enrollmentEndDateQuery,ageQuery);
        else if(indicatorCode.equalsIgnoreCase("vcserved007"))
        list=getListOfOvcServed(sex,additionalQueryCriteria,additionalServiceQuery,ageQuery);
        
        else if(indicatorCode.equalsIgnoreCase("vcnenservrp"))
        list=getListOfOvcNewlyEnrolledAndServedAndServedWithinTheReportPeriod(sex,additionalQueryCriteria,additionalServiceQuery,ageQuery);
        else if(indicatorCode.equalsIgnoreCase("vccurenserv"))
        list=getListOfOvcCurrentlyEnrolledAndServed(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery);
        
        else if(indicatorCode.equalsIgnoreCase("vcgradserve"))
        list=getListOfOvcWithdrawnButServedWithinTheReportPeriod(sex,additionalQueryCriteria,additionalServiceQuery,ageQuery,ReportUtility.GRADUATED);
        
        else if(indicatorCode.equalsIgnoreCase("vcltfuserve"))
        list=getListOfOvcWithdrawnButServedWithinTheReportPeriod(sex,additionalQueryCriteria,additionalServiceQuery,ageQuery,ReportUtility.LOST_TO_FOLLOWUP);
        else if(indicatorCode.equalsIgnoreCase("vcmigrserve"))
        list=getListOfOvcWithdrawnButServedWithinTheReportPeriod(sex,additionalQueryCriteria,additionalServiceQuery,ageQuery,ReportUtility.MIGRATED);
        else if(indicatorCode.equalsIgnoreCase("vcageoserve"))
        list=getListOfOvcWithdrawnButServedWithinTheReportPeriod(sex,additionalQueryCriteria,additionalServiceQuery,ageQuery,ReportUtility.AGED_OUT);
        else if(indicatorCode.equalsIgnoreCase("vctranserve"))
        list=getListOfOvcWithdrawnButServedWithinTheReportPeriod(sex,additionalQueryCriteria,additionalServiceQuery,ageQuery,ReportUtility.TRANSFERED);
        else if(indicatorCode.equalsIgnoreCase("vcdeadserve"))
        list=getListOfOvcWithdrawnButServedWithinTheReportPeriod(sex,additionalQueryCriteria,additionalServiceQuery,ageQuery,ReportUtility.DIED);
        else if(indicatorCode.equalsIgnoreCase("vcinactserv"))
        list=getListOfOvcWithdrawnButServedWithinTheReportPeriod(sex,additionalQueryCriteria,additionalServiceQuery,ageQuery,NomisConstant.INACTIVE);
        
        
        else if(indicatorCode.equalsIgnoreCase("vchivPosnEn"))
        list=getListOfHivPosOvc(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery);
        else if(indicatorCode.equalsIgnoreCase("vchivNegnEn"))
        list=getListHivNegOvc(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery);
        else if(indicatorCode.equalsIgnoreCase("vchivUnknEn"))
        list=getListOfHivUnknownOvc(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery);
        
        else if(indicatorCode.equalsIgnoreCase("hivPosEnrol"))
        list=getListOfOvcCurrentlyEnrolledByHIVStatus(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,NomisConstant.HIV_POSITIVE,true);
        else if(indicatorCode.equalsIgnoreCase("hivNegEnrol"))
        list=getListOfOvcCurrentlyEnrolledByHIVStatus(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,NomisConstant.HIV_NEGATIVE,true);
        else if(indicatorCode.equalsIgnoreCase("hivUnkEnrol"))
        list=getListOfOvcCurrentlyEnrolledByHIVStatus(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,NomisConstant.HIV_UNKNOWN,true);
        
        else if(indicatorCode.equalsIgnoreCase("vchivposeen"))
        list=getListOfOvcCurrentlyEnrolledByHIVStatus(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,NomisConstant.HIV_POSITIVE,false);
        else if(indicatorCode.equalsIgnoreCase("vchivnegeen"))
        list=getListOfOvcCurrentlyEnrolledByHIVStatus(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,NomisConstant.HIV_NEGATIVE,false);
        else if(indicatorCode.equalsIgnoreCase("vchivunkeen"))
        list=getListOfOvcCurrentlyEnrolledByHIVStatus(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,NomisConstant.HIV_UNKNOWN,false);
        
        else if(indicatorCode.equalsIgnoreCase("vcnewInCare"))
        list=getListOfOvcEnrolledOrNotEnrolledInCare(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,true,true,hivStatusPeriodQuery);
        else if(indicatorCode.equalsIgnoreCase("vccurInCare"))
        list=getListOfOvcEnrolledOrNotEnrolledInCare(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,true,true,null);
        else if(indicatorCode.equalsIgnoreCase("vceveInCare"))
        list=getListOfOvcEnrolledOrNotEnrolledInCare(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,false,true,null);
        else if(indicatorCode.equalsIgnoreCase("vccureOnArt"))
        list=getListOfHIVPositiveOvcEnrolledOnART(sex,additionalQueryCriteria,ageQuery,indicatorCode,startDate,endDate,true);
        else if(indicatorCode.equalsIgnoreCase("vceverOnArt"))
        list=getListOfHIVPositiveOvcEnrolledOnART(sex,additionalQueryCriteria,ageQuery,indicatorCode,startDate,endDate,false);
        
        else if(indicatorCode.equalsIgnoreCase("vcpossergrd"))
        list=getListOfOvcWithdrawnButServedWithinTheReportPeriodByHivStatus(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,NomisConstant.GRADUATED,NomisConstant.HIV_POSITIVE,false,startDate,endDate);
        else if(indicatorCode.equalsIgnoreCase("vcartsergrd"))
        list=getListOfOvcWithdrawnButServedWithinTheReportPeriodByHivStatus(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,NomisConstant.GRADUATED,NomisConstant.HIV_POSITIVE,true,startDate,endDate);
        else if(indicatorCode.equalsIgnoreCase("vcnegsergrd"))
        list=getListOfOvcWithdrawnButServedWithinTheReportPeriodByHivStatus(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,NomisConstant.GRADUATED,NomisConstant.HIV_NEGATIVE,false,startDate,endDate);
        else if(indicatorCode.equalsIgnoreCase("vcunksergrd"))
        list=getListOfOvcWithdrawnButServedWithinTheReportPeriodByHivStatus(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,NomisConstant.GRADUATED,NomisConstant.HIV_UNKNOWN,false,startDate,endDate);
        
        
        else if(indicatorCode.equalsIgnoreCase("vchivPosinp"))
        list=getListOfOvcCurrentlyEnrolledByHIVStatusAndReportPeriod(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,"positive");
        else if(indicatorCode.equalsIgnoreCase("vchivNeginp"))
        list=getListOfOvcCurrentlyEnrolledByHIVStatusAndReportPeriod(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,"negative");
        else if(indicatorCode.equalsIgnoreCase("vchivUnkinp"))
        list=getListOfOvcCurrentlyEnrolledByHIVStatusAndReportPeriod(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,"unknown");
        
        else if(indicatorCode.equalsIgnoreCase("posvcserved"))
        list=getListOfHivPosOvcServed(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery,false,false);
        else if(indicatorCode.equalsIgnoreCase("vcartserved"))
        list=getListOfHivPosOvcServed(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery,true,false);
        else if(indicatorCode.equalsIgnoreCase("negvcserved"))
        list=getListOfHivNegOvcServed(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery,false);
        else if(indicatorCode.equalsIgnoreCase("unkvcserved"))
        list=getListOfHivUnknownOvcServed(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery,false);
        
             
        else if(indicatorCode.equalsIgnoreCase("vcposceserv"))
        list=getListOfHivPosOvcServed(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery,false,true);
        else if(indicatorCode.equalsIgnoreCase("vcartceserv"))
        list=getListOfHivPosOvcServed(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery,true,true);
        
        else if(indicatorCode.equalsIgnoreCase("vcnegceserv"))
        list=getListOfHivNegOvcServed(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery,true);
        else if(indicatorCode.equalsIgnoreCase("vcunkceserv"))
        list=getListOfHivUnknownOvcServed(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery,true);
        
        
        else if(indicatorCode.equalsIgnoreCase("vcnotserved"))
        list=getListOfOvcWithoutServiceRecords(sex,additionalQueryCriteria+ageQuery,startDate,endDate,true);
        else if(indicatorCode.equalsIgnoreCase("vcrecactyrp"))
        list=getListOfOvcProvidedPsychosocialSupportServices(sex,additionalQueryCriteria,additionalServiceQuery,ageQuery,"recreationalActivities");
        else if(indicatorCode.equalsIgnoreCase("vcservedpsy"))
        list=getListOfOvcProvidedPsychosocialServices(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery);
        else if(indicatorCode.equalsIgnoreCase("vcservednut"))
        list=getListOfOvcProvidedNutritionalServices(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery);
        else if(indicatorCode.equalsIgnoreCase("vcservedhlt"))
        list=getListOfOvcProvidedHealthServices(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery);
        else if(indicatorCode.equalsIgnoreCase("vcservededu"))
        list=getListOfOvcProvidedEducationalServices(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery);
        else if(indicatorCode.equalsIgnoreCase("vcservedpro"))
        list=getListOfOvcProvidedProtectionServices(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery);
        else if(indicatorCode.equalsIgnoreCase("vcservedshe"))
        list=getListOfOvcProvidedShelterAndCareServices(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery);
        
        
        else if(indicatorCode.equalsIgnoreCase("vcpossernut"))
        list=getListOfOvcServedByDomainAndHivStatus(sex,indicatorCode,additionalQueryCriteria,additionalServiceQuery,NomisConstant.DOMAIN_NUTRITION,NomisConstant.HIV_POSITIVE);
        else if(indicatorCode.equalsIgnoreCase("vcnegsernut"))
        list=getListOfOvcServedByDomainAndHivStatus(sex,indicatorCode,additionalQueryCriteria,additionalServiceQuery,NomisConstant.DOMAIN_NUTRITION,NomisConstant.HIV_NEGATIVE);
        else if(indicatorCode.equalsIgnoreCase("vcunksernut"))
        list=getListOfOvcServedByDomainAndHivStatus(sex,indicatorCode,additionalQueryCriteria,additionalServiceQuery,NomisConstant.DOMAIN_NUTRITION,NomisConstant.HIV_UNKNOWN);
        
        else if(indicatorCode.equalsIgnoreCase("vcposserpsy"))
        list=getListOfOvcServedByDomainAndHivStatus(sex,indicatorCode,additionalQueryCriteria,additionalServiceQuery,NomisConstant.DOMAIN_PSYCHOSOCIAL,NomisConstant.HIV_POSITIVE);
        else if(indicatorCode.equalsIgnoreCase("vcnegserpsy"))
        list=getListOfOvcServedByDomainAndHivStatus(sex,indicatorCode,additionalQueryCriteria,additionalServiceQuery,NomisConstant.DOMAIN_PSYCHOSOCIAL,NomisConstant.HIV_NEGATIVE);
        else if(indicatorCode.equalsIgnoreCase("vcunkserpsy"))
        list=getListOfOvcServedByDomainAndHivStatus(sex,indicatorCode,additionalQueryCriteria,additionalServiceQuery,NomisConstant.DOMAIN_PSYCHOSOCIAL,NomisConstant.HIV_UNKNOWN);

        
        else if(indicatorCode.equalsIgnoreCase("vcposserhlt"))
        list=getListOfOvcServedByDomainAndHivStatus(sex,indicatorCode,additionalQueryCriteria,additionalServiceQuery,NomisConstant.DOMAIN_HEALTH,NomisConstant.HIV_POSITIVE);
        else if(indicatorCode.equalsIgnoreCase("vcnegserhlt"))
        list=getListOfOvcServedByDomainAndHivStatus(sex,indicatorCode,additionalQueryCriteria,additionalServiceQuery,NomisConstant.DOMAIN_HEALTH,NomisConstant.HIV_NEGATIVE);
        else if(indicatorCode.equalsIgnoreCase("vcunkserhlt"))
        list=getListOfOvcServedByDomainAndHivStatus(sex,indicatorCode,additionalQueryCriteria,additionalServiceQuery,NomisConstant.DOMAIN_HEALTH,NomisConstant.HIV_UNKNOWN);

        else if(indicatorCode.equalsIgnoreCase("vcposseredu"))
        list=getListOfOvcServedByDomainAndHivStatus(sex,indicatorCode,additionalQueryCriteria,additionalServiceQuery,NomisConstant.DOMAIN_EDUCATION,NomisConstant.HIV_POSITIVE);
        else if(indicatorCode.equalsIgnoreCase("vcnegseredu"))
        list=getListOfOvcServedByDomainAndHivStatus(sex,indicatorCode,additionalQueryCriteria,additionalServiceQuery,NomisConstant.DOMAIN_EDUCATION,NomisConstant.HIV_NEGATIVE);
        else if(indicatorCode.equalsIgnoreCase("vcunkseredu"))
        list=getListOfOvcServedByDomainAndHivStatus(sex,indicatorCode,additionalQueryCriteria,additionalServiceQuery,NomisConstant.DOMAIN_EDUCATION,NomisConstant.HIV_UNKNOWN);

        else if(indicatorCode.equalsIgnoreCase("vcposserpro"))
        list=getListOfOvcServedByDomainAndHivStatus(sex,indicatorCode,additionalQueryCriteria,additionalServiceQuery,NomisConstant.DOMAIN_PROTECTION,NomisConstant.HIV_POSITIVE);
        else if(indicatorCode.equalsIgnoreCase("vcnegserpro"))
        list=getListOfOvcServedByDomainAndHivStatus(sex,indicatorCode,additionalQueryCriteria,additionalServiceQuery,NomisConstant.DOMAIN_PROTECTION,NomisConstant.HIV_NEGATIVE);
        else if(indicatorCode.equalsIgnoreCase("vcunkserpro"))
        list=getListOfOvcServedByDomainAndHivStatus(sex,indicatorCode,additionalQueryCriteria,additionalServiceQuery,NomisConstant.DOMAIN_PSYCHOSOCIAL,NomisConstant.HIV_UNKNOWN);

        else if(indicatorCode.equalsIgnoreCase("vcpossersht"))
        list=getListOfOvcServedByDomainAndHivStatus(sex,indicatorCode,additionalQueryCriteria,additionalServiceQuery,NomisConstant.DOMAIN_SHELTER,NomisConstant.HIV_POSITIVE);
        else if(indicatorCode.equalsIgnoreCase("vcnegsersht"))
        list=getListOfOvcServedByDomainAndHivStatus(sex,indicatorCode,additionalQueryCriteria,additionalServiceQuery,NomisConstant.DOMAIN_SHELTER,NomisConstant.HIV_NEGATIVE);
        else if(indicatorCode.equalsIgnoreCase("vcunksersht"))
        list=getListOfOvcServedByDomainAndHivStatus(sex,indicatorCode,additionalQueryCriteria,additionalServiceQuery,NomisConstant.DOMAIN_SHELTER,NomisConstant.HIV_UNKNOWN);

        else if(indicatorCode.equalsIgnoreCase("vcposserecs"))
        list=getListOfOvcServedByDomainAndHivStatus(sex,indicatorCode,additionalQueryCriteria,additionalServiceQuery,NomisConstant.DOMAIN_ECONOMICSTRENTHENINIG,NomisConstant.HIV_POSITIVE);
        else if(indicatorCode.equalsIgnoreCase("vcnegserecs"))
        list=getListOfOvcServedByDomainAndHivStatus(sex,indicatorCode,additionalQueryCriteria,additionalServiceQuery,NomisConstant.DOMAIN_ECONOMICSTRENTHENINIG,NomisConstant.HIV_NEGATIVE);
        else if(indicatorCode.equalsIgnoreCase("vcunkserecs"))
        list=getListOfOvcServedByDomainAndHivStatus(sex,indicatorCode,additionalQueryCriteria,additionalServiceQuery,NomisConstant.DOMAIN_ECONOMICSTRENTHENINIG,NomisConstant.HIV_UNKNOWN);
                
        else if(indicatorCode.equalsIgnoreCase("vcwithdrawn"))
        list=getListOfOvcWithdrawn(sex,additionalQueryCriteria,withdrawalPeriodQuery,null);
        
        else if(indicatorCode.equalsIgnoreCase("vcgraduated"))
        list=(getListOfOvcWithdrawn(sex,additionalQueryCriteria,withdrawalPeriodQuery,NomisConstant.GRADUATED));
        else if(indicatorCode.equalsIgnoreCase("vclosttofup"))
        list=(getListOfOvcWithdrawn(sex,additionalQueryCriteria,withdrawalPeriodQuery,NomisConstant.LOST_TO_FOLLOWUP));
        else if(indicatorCode.equalsIgnoreCase("ovcmigrated"))
        list=(getListOfOvcWithdrawn(sex,additionalQueryCriteria,withdrawalPeriodQuery,NomisConstant.MIGRATED));
        else if(indicatorCode.equalsIgnoreCase("ovcagingout"))
        list=(getListOfOvcWithdrawn(sex,additionalQueryCriteria,withdrawalPeriodQuery,NomisConstant.AGED_OUT));
        else if(indicatorCode.equalsIgnoreCase("vcknowndeat"))
        list=(getListOfOvcWithdrawn(sex,additionalQueryCriteria,withdrawalPeriodQuery,NomisConstant.DIED));
        else if(indicatorCode.equalsIgnoreCase("vctransferd"))
        list=(getListOfOvcWithdrawn(sex,additionalQueryCriteria,withdrawalPeriodQuery,NomisConstant.TRANSFERED));
        else if(indicatorCode.equalsIgnoreCase("vcinactivrp"))
        list=(getListOfOvcWithdrawn(sex,additionalQueryCriteria,withdrawalPeriodQuery,NomisConstant.INACTIVE));
        
        
        else if(indicatorCode.equalsIgnoreCase("vcnotschool"))
        list=getListOfOvcCurrentlyOutOfSchoolSchool(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,true);
        else if(indicatorCode.equalsIgnoreCase("vccurrinsch"))
        list=getListOfOvcCurrentlyInSchool(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,true);
        else if(indicatorCode.equalsIgnoreCase("vcwithnobir"))
        list=getListOvcWithoutBirthCertAtBaseline(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery);
        else if(indicatorCode.equalsIgnoreCase("gt3services"))
        {
            setNoOfServices(sex,additionalQueryCriteria,additionalServiceQuery,ageQuery);
            list=getListOfOvcThatReceivedThreeOrMoreServices(sex,additionalQueryCriteria,additionalServiceQuery,ageQuery);
        }
        else if(indicatorCode.equalsIgnoreCase("lt3services"))
        {
            setNoOfServices(sex,additionalQueryCriteria,additionalServiceQuery,ageQuery);
            list=getListOfOvcThatReceivedLessThanThreeServices(sex,additionalQueryCriteria,additionalServiceQuery,ageQuery); 
        }
        else if(indicatorCode.equalsIgnoreCase("newnotserve"))
        list=getListOfNewlyEnrolledOvcWithoutServeRecords(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery);
        else if(indicatorCode.equalsIgnoreCase("vchivaccess"))
        list=getListOfOvcSupportedOnHIVAIDSServicesForMSF(sex,additionalQueryCriteria,dateArray);
        else if(indicatorCode.equalsIgnoreCase("vcwithnobcc"))
        list=getListOfOvcWithoutBirthCertCurrently(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery);
        
        else if(indicatorCode.equalsIgnoreCase("vcposcevulb"))
        list=getListOfHivPosOvcCurrentlyEnrolledBasedOnVulnerability(sex,additionalQueryCriteria,NomisConstant.VULNERABLE_STARTVALUE,NomisConstant.VULNERABLE_ENDVALUE,true);
        else if(indicatorCode.equalsIgnoreCase("vcposcemrvb"))
        list=getListOfHivPosOvcCurrentlyEnrolledBasedOnVulnerability(sex,additionalQueryCriteria,NomisConstant.MOREVULNERABLE_STARTVALUE,NomisConstant.MOREVULNERABLE_ENDVALUE,true);
        else if(indicatorCode.equalsIgnoreCase("vcposcemovb"))
        list=getListOfHivPosOvcCurrentlyEnrolledBasedOnVulnerability(sex,additionalQueryCriteria,NomisConstant.MOSTVULNERABLE_STARTVALUE,NomisConstant.MOSTVULNERABLE_ENDVALUE,true);
        else if(indicatorCode.equalsIgnoreCase("vcce019hhes"))
        list=getListOfOvcWhoseHouseholdProvidedEconomicStrentheningServices(sex,additionalQueryCriteria, startDate,endDate,0,19,true);
        else if(indicatorCode.equalsIgnoreCase("vcee019hhes"))
        list=getListOfOvcWhoseHouseholdProvidedEconomicStrentheningServices(sex,additionalQueryCriteria, startDate,endDate,0,19,false);
        else if(indicatorCode.equalsIgnoreCase("vcceadohhes"))
        list=getListOfAdolescentWhoseHouseholdProvidedEconomicStrentheningServices(sex,additionalQueryCriteria,startDate,endDate,true);
        else if(indicatorCode.equalsIgnoreCase("vcadolshhes"))
        list=getListOfAdolescentWhoseHouseholdProvidedEconomicStrentheningServices(sex,additionalQueryCriteria,startDate,endDate,false);
        
        else if(indicatorCode.equalsIgnoreCase("cgnotserved"))
        list=getListOfCaregiversWithoutServiceRecords(sex,additionalQueryCriteria,startDate,endDate,true);
        else if(indicatorCode.equalsIgnoreCase("cgscrnfortb"))
        list=getListOfCaregiversScreenedForTB(sex,additionalQueryCriteria,dateArray);
        
        else if(indicatorCode.equalsIgnoreCase("cgenrsilcrp"))
        list.add(getListOfCaregiversProvidedHES(sex,additionalQueryCriteria,startDate,endDate,"SILC",false));
        else if(indicatorCode.equalsIgnoreCase("cginforumrp"))
        list.add(getListOfCaregiversProvidedHES(sex,additionalQueryCriteria,startDate,endDate,"cgforum",false));
        
        else if(indicatorCode.equalsIgnoreCase("cgservedhes"))
        list.add(getListOfCaregiversProvidedHES(sex,additionalQueryCriteria,startDate,endDate,"hes",false));
        else if(indicatorCode.equalsIgnoreCase("cgtesforhiv"))
        list.add(getListOfCaregiversTestedForHIV(sex,additionalQueryCriteria,startDate,endDate,false));
        else if(indicatorCode.equalsIgnoreCase("cghivcencar"))
        list.add(getListOfCaregiversEnrolledInCareOrOnART(sex,additionalQueryCriteria,"Care",true));
        else if(indicatorCode.equalsIgnoreCase("cghivcenart"))
        list.add(getListOfCaregiversEnrolledInCareOrOnART(sex,additionalQueryCriteria,"ART",true));
        else if(indicatorCode.equalsIgnoreCase("cghivposeen"))
        list.add(getListOfCaregiversEnrolledByHIVStatus(sex,additionalQueryCriteria,NomisConstant.HIV_POSITIVE,false));
        else if(indicatorCode.equalsIgnoreCase("cghivnegeen"))
        list.add(getListOfCaregiversEnrolledByHIVStatus(sex,additionalQueryCriteria,NomisConstant.HIV_NEGATIVE,false));
        else if(indicatorCode.equalsIgnoreCase("cghivunkeen"))
        list.add(getListOfCaregiversEnrolledByHIVStatus(sex,additionalQueryCriteria,NomisConstant.HIV_UNKNOWN,false));
        
        else if(indicatorCode.equalsIgnoreCase("cghivposcen"))
        list.add(getListOfCaregiversEnrolledByHIVStatus(sex,additionalQueryCriteria,NomisConstant.HIV_POSITIVE,true));
        else if(indicatorCode.equalsIgnoreCase("cghivnegcen"))
        list.add(getListOfCaregiversEnrolledByHIVStatus(sex,additionalQueryCriteria,NomisConstant.HIV_NEGATIVE,true));
        else if(indicatorCode.equalsIgnoreCase("cghivunkcen"))
        list.add(getListOfCaregiversEnrolledByHIVStatus(sex,additionalQueryCriteria,NomisConstant.HIV_UNKNOWN,true));
        
        else if(indicatorCode.equalsIgnoreCase("cgposcevulb"))
        list.add(getListOfHivPosCaregiverCurrentlyEnrolledBasedOnVulnerability(sex,additionalQueryCriteria,NomisConstant.VULNERABLE_STARTVALUE,NomisConstant.VULNERABLE_ENDVALUE,true));
        else if(indicatorCode.equalsIgnoreCase("cgposcemrvb"))
        list.add(getListOfHivPosCaregiverCurrentlyEnrolledBasedOnVulnerability(sex,additionalQueryCriteria,NomisConstant.MOREVULNERABLE_STARTVALUE,NomisConstant.MOREVULNERABLE_ENDVALUE,true));
        else if(indicatorCode.equalsIgnoreCase("cgposcemovb"))
        list.add(getListOfHivPosCaregiverCurrentlyEnrolledBasedOnVulnerability(sex,additionalQueryCriteria,NomisConstant.MOSTVULNERABLE_STARTVALUE,NomisConstant.MOSTVULNERABLE_ENDVALUE,true));
        //hviQueryCriteria,hviQueryCriteria,
        else if(indicatorCode.equalsIgnoreCase("cgiverserve"))
        list.add(getListOfCaregiversServed(sex,hviServiceQueryCriteria,ageQuery,false,false));
        else if(indicatorCode.equalsIgnoreCase("cgnenrserrp"))
        list.add(getListOfCaregiversServed(sex,hviServiceQueryCriteria,ageQuery,true,false));
        else if(indicatorCode.equalsIgnoreCase("cgactiserrp"))
        list.add(getListOfCaregiversServed(sex,hviServiceQueryCriteria,ageQuery,false,true));
        
        else if(indicatorCode.equalsIgnoreCase("cgsergradrp"))
        list.add(getListOfCaregiversServedButWithdrawn(sex,hviQueryCriteria,cgiverPeriodQuery,hviServiceQueryCriteria,ageQuery,ReportUtility.GRADUATED));
        else if(indicatorCode.equalsIgnoreCase("cgserltfurp"))
        list.add(getListOfCaregiversServedButWithdrawn(sex,hviQueryCriteria,cgiverPeriodQuery,hviServiceQueryCriteria,ageQuery,ReportUtility.LOST_TO_FOLLOWUP));
        else if(indicatorCode.equalsIgnoreCase("cgserdiedrp"))
        list.add(getListOfCaregiversServedButWithdrawn(sex,hviQueryCriteria,cgiverPeriodQuery,hviServiceQueryCriteria,ageQuery,ReportUtility.DIED));
        else if(indicatorCode.equalsIgnoreCase("cgsermigrrp"))
        list.add(getListOfCaregiversServedButWithdrawn(sex,hviQueryCriteria,cgiverPeriodQuery,hviServiceQueryCriteria,ageQuery,ReportUtility.MIGRATED));
        else if(indicatorCode.equalsIgnoreCase("cgsertranrp"))
        list.add(getListOfCaregiversServedButWithdrawn(sex,hviQueryCriteria,cgiverPeriodQuery,hviServiceQueryCriteria,ageQuery,NomisConstant.TRANSFERED));
        else if(indicatorCode.equalsIgnoreCase("cginactserv"))
        list.add(getListOfCaregiversServedButWithdrawn(sex,hviQueryCriteria,cgiverPeriodQuery,hviServiceQueryCriteria,ageQuery,NomisConstant.INACTIVE));

        
        else if(indicatorCode.equalsIgnoreCase("cgnenrolled"))
        list.add(getListOfCaregiversNewlyEnrolled(sex,additionalQueryCriteria,cgiverPeriodQuery));
        else if(indicatorCode.equalsIgnoreCase("cgcenrolled"))
        list.add(getListOfCaregiversCurrentlyEnrolled(sex,additionalQueryCriteria,cgiverPeriodQuery));
        else if(indicatorCode.equalsIgnoreCase("cgeenrolled"))
        list.add(getListOfCaregiversEverEnrolled(sex,additionalQueryCriteria,cgiverPeriodQuery));
        
        else if(indicatorCode.equalsIgnoreCase("cggraduated"))
        list.add(getListOfCaregiversWithdrawn(sex,additionalQueryCriteria,withdrawalPeriodQuery,NomisConstant.GRADUATED));
        else if(indicatorCode.equalsIgnoreCase("cgmigrated1"))
        list.add(getListOfCaregiversWithdrawn(sex,additionalQueryCriteria,withdrawalPeriodQuery,NomisConstant.MIGRATED));
        else if(indicatorCode.equalsIgnoreCase("cglosttofup"))
        list.add(getListOfCaregiversWithdrawn(sex,additionalQueryCriteria,withdrawalPeriodQuery,"Loss to follow-up"));
        else if(indicatorCode.equalsIgnoreCase("cgtransferd"))
        list.add(getListOfCaregiversWithdrawn(sex,additionalQueryCriteria,withdrawalPeriodQuery,"transfer"));
        else if(indicatorCode.equalsIgnoreCase("cgknowndeat"))
        list.add(getListOfCaregiversWithdrawn(sex,additionalQueryCriteria,withdrawalPeriodQuery,"Known death"));
        else if(indicatorCode.equalsIgnoreCase("cginactivrp"))
        list.add(getListOfCaregiversWithdrawn(sex,additionalQueryCriteria,withdrawalPeriodQuery,NomisConstant.INACTIVE));
        
        else if(indicatorCode.equalsIgnoreCase("hhdEnrolled"))
        list.add(getListOfHouseholdsEnrolled(sex,hviQueryCriteria,hviPeriodQuery,ageQuery));
        else if(indicatorCode.equalsIgnoreCase("hhnenrolled"))
        list.add(getListOfHouseholdsNewlyEnrolled(sex,additionalQueryCriteria,hviPeriodQuery,ageQuery));
        else if(indicatorCode.equalsIgnoreCase("hhcenrolled"))
        list.add(getListOfHouseholdsCurrentlyEnrolled(sex,additionalQueryCriteria,hviPeriodQuery,ageQuery));
        else if(indicatorCode.equalsIgnoreCase("hheenrolled"))
        list.add(getListOfHouseholdsEverEnrolled(sex,additionalQueryCriteria,hviPeriodQuery,ageQuery));
        
        if(indicatorCode.equalsIgnoreCase("hhdbasasses"))
        list.add(getListOfHouseholdsWithBaselineAssessment(hviQueryCriteriaWithoutPeriod,hviPeriodQuery,false));
        else if(indicatorCode.equalsIgnoreCase("hhdcebasass"))
        list.add(getListOfHouseholdsWithBaselineAssessment(hviQueryCriteriaWithoutPeriod,hviPeriodQuery,true));
        
        else if(indicatorCode.equalsIgnoreCase("hhbassesper"))
        list.add(getListOfHouseholdsWithBaselineAssessment(hviQueryCriteria,hviPeriodQuery,false));
        else if(indicatorCode.equalsIgnoreCase("hhcebassper"))
        list.add(getListOfHouseholdsWithBaselineAssessment(hviQueryCriteria,hviPeriodQuery,true));
        else if(indicatorCode.equalsIgnoreCase("hhcefupasse"))
        list.add(getListOfHouseholdsWithFollowupAssessment(hviQueryCriteria,hviPeriodQuery,true));
        else if(indicatorCode.equalsIgnoreCase("hheefupasse"))
        list.add(getListOfHouseholdsWithFollowupAssessment(hviQueryCriteria,hviPeriodQuery,false));
        
                        
        else if(indicatorCode.equalsIgnoreCase("hhwithdrawn"))
        list.add(getListOfHouseholdsWithdrawn(sex,additionalQueryCriteria,hviPeriodQuery,null));
        else if(indicatorCode.equalsIgnoreCase("hhgraduated"))
        list.add(getListOfHouseholdsWithdrawn(sex,additionalQueryCriteria,withdrawalPeriodQuery,NomisConstant.GRADUATED));
        else if(indicatorCode.equalsIgnoreCase("hhmigrated1"))
        list.add(getListOfHouseholdsWithdrawn(sex,additionalQueryCriteria,withdrawalPeriodQuery,NomisConstant.MIGRATED));
        else if(indicatorCode.equalsIgnoreCase("hhlosttofup"))
        list.add(getListOfHouseholdsWithdrawn(sex,additionalQueryCriteria,withdrawalPeriodQuery,NomisConstant.LOST_TO_FOLLOWUP));
        else if(indicatorCode.equalsIgnoreCase("hhtransferd"))
        list.add(getListOfHouseholdsWithdrawn(sex,additionalQueryCriteria,withdrawalPeriodQuery,NomisConstant.TRANSFERED));
        else if(indicatorCode.equalsIgnoreCase("hhinactivrp"))
        list.add(getListOfHouseholdsWithdrawn(sex,additionalQueryCriteria,withdrawalPeriodQuery,NomisConstant.INACTIVE));
        else if(indicatorCode.equalsIgnoreCase("hhdservedrp"))
        list.add(getListOfHouseholdsServed(additionalQueryCriteria,hviServiceQueryCriteria,ageQuery,false));
        else if(indicatorCode.equalsIgnoreCase("hhdserhesrp"))
        list.add(getListOfHouseholdsProvidedHES(additionalQueryCriteria,startDate,endDate,false));
        //getListOfHouseholdsProvidedHES(String additionalQueryCriteria,String startDate,String endDate,boolean currentlyEnrolled)
              
        if(indicatorCode.equalsIgnoreCase("cgivehivacc"))
        list.add(getListOfHouseholdsSupportedToAccessHIVCare(sex,additionalQueryCriteria,dateArray));
        else if(indicatorCode.equalsIgnoreCase("vcscrnfortb"))
        list=getListOfOvcScreenedForTB(sex,additionalQueryCriteria,dateArray);
        
        else if(indicatorCode.equalsIgnoreCase("vcsmalnsern"))
        list=getListofSeverelyMalnourishedOvcIdentifiedAndServedWithinTheReportPeriod(sex,additionalQueryCriteria,startDate,endDate,ageQuery,false);
        else if(indicatorCode.equalsIgnoreCase("vcnebslbcrt"))
        list=getListOfOvcWithBirthCertAtBaseline(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,false);
        else if(indicatorCode.equalsIgnoreCase("vccebslbcrt"))
        //set additionalEnrollmentQuery="" because period is not required currently enrolled and ever enrolled
        list=getListOfOvcWithBirthCertAtBaseline(sex,additionalQueryCriteria,"",ageQuery,true);
        else if(indicatorCode.equalsIgnoreCase("vceebslbcrt"))
        list=getListOfOvcWithBirthCertAtBaseline(sex,additionalQueryCriteria,"",ageQuery,false);
        else if(indicatorCode.equalsIgnoreCase("vcserbtctrp"))
        list=getListOfOvcProvidedBirthCertService(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,NomisConstant.SERVICE_POINTOFUPDATE,false);
        else if(indicatorCode.equalsIgnoreCase("vcceserbcer"))
        list=getListOfOvcServedWithinTheReportPeriodThatHasBirthCert(sex,additionalQueryCriteria,startDate,endDate,ageQuery,true);
        else if(indicatorCode.equalsIgnoreCase("vccelt18cer"))
        list=getListOfOvcWithBirthCertificate(sex,additionalQueryCriteria,additionalEnrollmentQuery,lessThan18ageQuery,null,true);
        else if(indicatorCode.equalsIgnoreCase("vccebthcert"))
        list=getListOfOvcWithBirthCertificate(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,null,true);
         else if(indicatorCode.equalsIgnoreCase("vceebthcert"))
        list=getListOfOvcWithBirthCertificate(sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,null,false);
        
        return list;
    }
    private ReportParameterTemplate getReportParameterTemplate(String[] params)
    {
        ReportParameterTemplate rpt=new ReportParameterTemplate();
        int startMth=Integer.parseInt(params[7]);
        int startYear=Integer.parseInt(params[8]);
        int endMth=Integer.parseInt(params[9]);
        int endYear=Integer.parseInt(params[10]);
        
        rpt.setStartMonth(startMth);
        rpt.setStartYear(startYear);
        rpt.setEndMonth(endMth);
        rpt.setEndYear(endYear);
        return rpt;
    }
    public List getOvcEnrolledSummStatistics(String sex,String[] params,String[] selectedIndicators) throws Exception
    {
        IndicatorDictionary ind=new IndicatorDictionary();
        IndicatorWarehouse indwh=new IndicatorWarehouse();
        /*int startMth=Integer.parseInt(params[7]);
        int startYear=Integer.parseInt(params[8]);
        int endMth=Integer.parseInt(params[9]);
        int endYear=Integer.parseInt(params[10]);*/
        
        //Create a new ReportParameterTemplate
        //ReportParameterTemplate rpt=new ReportParameterTemplate();
        //set the start moth and start year to be able to get the financial year
        //rpt.setStartMonth(startMth);
        //rpt.setStartYear(startYear);
        //rpt.setEndMonth(endMth);
        //rpt.setEndYear(endYear);
        //create a seperate request parameters for exited beneficiaries after receiving services for that financial year
        //String[] reportParamsForExited=params;
        //set the start month and start year to the start of the financial year for donor reporting
        //reportParamsForExited[7]=rpt.getFinancialYear().getStartMonth()+"";
        //reportParamsForExited[8]=rpt.getFinancialYear().getStartYear()+"";
        String additionalQueryCriteria=util.getSummStatQueryCriteria(params);
        //create query criteria for exited beneficiaries
        //String exitedQueryCriteria=util.getSummStatQueryCriteria(reportParamsForExited);
        System.err.println("additionalQueryCriteria is "+additionalQueryCriteria);
        String[] hviParams=getHVIParams(params);//{params[0],params[1],params[2],null,null,null,null,null,null,null,params[18],params[19]};
        String[] periodParams={params[7], params[8], params[9],params[10]};
        
        String startDate=util.getStartDate(periodParams);
        String endDate=util.getEndDate(periodParams);
        String fyStartDate=startDate;
        String fyEndDate=endDate;
        if((params[7] !=null && !params[7].equalsIgnoreCase("All")) && (params[8] !=null && !params[8].equalsIgnoreCase("All")) && (params[9] !=null && !params[9].equalsIgnoreCase("All")) && (params[10] !=null && !params[10].equalsIgnoreCase("All")))
        {
            ReportParameterTemplate rpt=getReportParameterTemplate(params);
            String[] fyPeriodParams={rpt.getFinancialYear().getStartMonth()+"", rpt.getFinancialYear().getStartYear()+"", params[9],params[10]};
            fyStartDate=util.getStartDate(fyPeriodParams);
            fyEndDate=util.getEndDate(fyPeriodParams);
        }
        
        withdrawalQueryWithinReportPeriod=util.getDateQueryForWithdrawalWithinReportPeriod(startDate, endDate);
        withdrawalServicePeriodQuery=util.getWithdrawalServicePeriodCriteria(startDate);
        String enrollmentEndDateQuery=util.getEnrollmentEndDateQuery(periodParams);//getHIVStatusDateQuery(
        String hivStatusPeriodQuery=util.getHIVStatusDateQuery(util.getStartDate(periodParams), util.getEndDate(periodParams));
        String hviQueryCriteria=util.getHVIReportCriteria(hviParams);
        
        String hviServiceQueryCriteria=" "; //util.getHVIServiceReportCriteria(params);
        hviServiceQueryCriteria=util.getHVIServiceReportCriteria(hviParams);
        String hviPeriodQuery=" ";
        String cgiverPeriodQuery=" ";
        String withdrawalPeriodQuery=" ";
        if(!params[7].equalsIgnoreCase("all") && !params[8].equalsIgnoreCase("all") && !params[9].equalsIgnoreCase("all") && !params[10].equalsIgnoreCase("all"))
        {
            hviPeriodQuery=util.getHhePeriodCriteria(util.getStartDate(periodParams),util.getEndDate(periodParams));
            cgiverPeriodQuery=util.getCaregiverPeriodCriteria(util.getStartDate(periodParams),util.getEndDate(periodParams));
            withdrawalPeriodQuery=util.getWithdrawalPeriodCriteria(util.getStartDate(periodParams),util.getEndDate(periodParams));
        }
        String additionalEnrollmentQuery=util.getAdditionalEnrollmentQuery(params[7], params[8], params[9], params[10]);
        String additionalServiceQuery=util.getAdditionalServiceQuery(params[7], params[8], params[9], params[10]);
        //String additionalWithdrawalQuery=util.getWithdrawalPeriodQuery(params[7], params[8], params[9], params[10]);
        //String additionalServiceQuery=util.getAdditionalServiceQuery(params[11], params[12], params[13], params[14]);
        lessThan18ageQuery=util.getAgeCriteria(0+"", 17+"");
        int startAge=0;
        int endAge=0;
        String caregiverageQuery="";
        if((params[15] !=null && !params[15].equalsIgnoreCase("All")) && (params[16] !=null && !params[16].equalsIgnoreCase("All")))
        {
            if(params[16].indexOf("+") !=-1)
            params[16]="100";
            startAge=Integer.parseInt(params[15]);
            endAge=Integer.parseInt(params[16]);
            ageQuery=util.getAgeCriteria(params[15], params[16]);
            caregiverageQuery=util.getCaregiverAgeCriteria(startAge, endAge);
        }
        String[] dateArray=null;
        if(!params[7].equalsIgnoreCase("all") && !params[8].equalsIgnoreCase("all") && !params[9].equalsIgnoreCase("all") && !params[10].equalsIgnoreCase("all"))
        {
            String[] dateArray2={util.getStartDate(periodParams),util.getEndDate(periodParams)};
            dateArray=dateArray2;
        }
        List list=new ArrayList();
        if(selectedIndicators !=null && selectedIndicators.length>0)
        {
            String indicatorCode=null;
            String indicatorName=" ";
            boolean numberOfServicesSet=false;
            //int index=0;
            for(int i=0; i<selectedIndicators.length; i++)
            {
                indicatorCode=selectedIndicators[i];
                //getOVC_HIVSTATPOSITIVE(indicatorCode,indicatorName,additionalQueryCriteria, additionalEnrollmentQuery, additionalServiceQuery,ageQuery,startDate,endDate,false)
                indicatorName=indwh.getIndicatorById(indicatorCode).getIndicatorName();
                System.err.println("indicatorName is "+indicatorName);
                if(indicatorCode.equalsIgnoreCase("vchivstapos"))
                list.add(getOVC_HIVSTATPOSITIVE(indicatorCode,indicatorName,additionalQueryCriteria, additionalEnrollmentQuery, additionalServiceQuery,ageQuery,startDate,endDate,fyStartDate,fyEndDate,false));
                else if(indicatorCode.equalsIgnoreCase("vchivstaneg"))
                list.add(getOVC_HIVSTATNEGATIVE(indicatorCode,indicatorName,additionalQueryCriteria, additionalEnrollmentQuery, additionalServiceQuery,ageQuery,startDate,endDate,fyStartDate,fyEndDate,false));
                if(indicatorCode.equalsIgnoreCase("vchivstaunk"))
                list.add(getOVC_HIVSTATUNKNOWN(indicatorCode,indicatorName,additionalQueryCriteria, additionalEnrollmentQuery, additionalServiceQuery,ageQuery,startDate,endDate,fyStartDate,fyEndDate,false));
                else if(indicatorCode.equalsIgnoreCase("hhvcserverp"))
                list.add(getNumberOfHouseholdsWhoseOvcWereServedWithinReportingPeriod(indicatorName,additionalQueryCriteria,ageQuery,startDate,endDate,fyStartDate,fyEndDate,indicatorCode,false));
                else if(indicatorCode.equalsIgnoreCase("hhactvcserp"))
                list.add(getNumberOfActiveHouseholdsWhoseOvcWereServedWithinReportingPeriod(indicatorName,additionalQueryCriteria,ageQuery,startDate,endDate,indicatorCode,false));
                else if(indicatorCode.equalsIgnoreCase("hhgdvcserrp"))
                list.add(getNumberOfGraduatedHouseholdsWhoseOvcWereServedWithinReportingPeriod(indicatorName,additionalQueryCriteria,ageQuery,startDate,endDate,indicatorCode,false));
                else if(indicatorCode.equalsIgnoreCase("vcexitnogra"))
                list.add(getNumberOfOvcExitedWithoutGraduation(indicatorName,additionalQueryCriteria,additionalServiceQuery,ageQuery,indicatorCode));
                else if(indicatorCode.equalsIgnoreCase("vcschattend"))
                list.add(getNumberOfOvcRegularlyAttendingSchool(indicatorCode,indicatorName,additionalQueryCriteria,ageQuery,startDate,endDate,true));
                else if(indicatorCode.equalsIgnoreCase("hhemergneed"))
                list.add(getNumberOfHouseholdsThatAreAbleToPayForUnexpectedExpenses(indicatorName,additionalQueryCriteria,ageQuery,startDate,endDate,indicatorCode,false));
                else if(indicatorCode.equalsIgnoreCase("vcadherence"))
                list.add(getNumberOfHIVPositiveOvcWhoReportedAdherenceToTreatmentWithinTheReportPeriod(indicatorName,additionalQueryCriteria,ageQuery,startDate,endDate,indicatorCode,false,false));
                else if(indicatorCode.equalsIgnoreCase("vcnewposart"))
                list.add(getNoOfHIVPositiveOvcIdentifiedAndServedWithinTheReportPeriod(indicatorCode,indicatorName,additionalQueryCriteria,ageQuery,startDate,endDate,false,true));
                /*else if(indicatorCode.equalsIgnoreCase("vcnewposart"))
                list.add(getNumberOfNewlyTestedHIVPositiveOvcLinkedToTrestmentWithinTheReportPeriod(indicatorName,additionalQueryCriteria,ageQuery,startDate,endDate,indicatorCode,false));*/
                else if(indicatorCode.equalsIgnoreCase("vctstresult"))
                list.add(getNumberOfOvcTestedAndReceivedResult(indicatorName,additionalQueryCriteria,ageQuery,startDate,endDate,indicatorCode,false));
                else if(indicatorCode.equalsIgnoreCase("vcunknegser"))
                list.add(getNumberOfOvcUnknownOrNegativeHivStatusServedWithinReportPeriod(indicatorName,indicatorCode,additionalQueryCriteria,startDate,endDate,startAge,endAge,false));
                //
                else if(indicatorCode.equalsIgnoreCase("vchivprevrp"))
                list.add(getNumberOfAdolescentsProvidedWithHIVPreventionServicesWithinTheReportPeriod(indicatorName,additionalQueryCriteria+ageQuery,startDate,endDate,indicatorCode,false));
                else if(indicatorCode.equalsIgnoreCase("vcabusedrpe"))
                list.add(getNumberOfOvcAbusedOrExploitedWithinTheReportPeriod(indicatorName,additionalQueryCriteria+ageQuery,startDate,endDate,indicatorCode,false));
                else if(indicatorCode.equalsIgnoreCase("vclgovptvrp"))
                list.add(getNumberOfOvcLinkedToGovtForPostViolenceServicesWithinTheReportPeriod(indicatorName,additionalQueryCriteria+ageQuery,startDate,endDate,indicatorCode,false));
                else if(indicatorCode.equalsIgnoreCase("vckidsclbrp"))
                list.add(getNumberOfOvcEnrolledInKidsClubWithinTheReportPeriod(indicatorName,additionalQueryCriteria+ageQuery,startDate,endDate,indicatorCode,false));
                else if(indicatorCode.equalsIgnoreCase("vccehhblass"))
                list.add(getNumberOfOvcCurrentlyEnrolledInHouseholdsWithBaselineAssessment(ind.getIndicatorForNumberOfOvcCurrentlyEnrolledInHouseholdsWithBaselineAssessment().getIndicatorName(),additionalQueryCriteria+ageQuery,startDate,endDate,indicatorCode,true));
                else if(indicatorCode.equalsIgnoreCase("vccenohivra"))
                list.add(getNoOfOvcThatHasNoHIVRiskAssessmentRecord(ind.getIndicatorForNumberOfOvcCurrentlyEnrolledThatHasNoHIVRiskAssessmentRecord().getIndicatorName(),additionalQueryCriteria,startDate,endDate,indicatorCode,true));
                
                else if(indicatorCode.equalsIgnoreCase("vceeunkrass"))
                list.add(getNoOfOvcAssessedForHivRiskByHivStatus(indicatorName,indicatorCode,additionalQueryCriteria,startDate,endDate,ageQuery,NomisConstant.HIV_UNKNOWN,false));
                else if(indicatorCode.equalsIgnoreCase("vceenegrass"))
                list.add(getNoOfOvcAssessedForHivRiskByHivStatus(indicatorName,indicatorCode,additionalQueryCriteria,startDate,endDate,ageQuery,NomisConstant.HIV_NEGATIVE,false));
                else if(indicatorCode.equalsIgnoreCase("vchivatrisk"))
                list.add(getNoOfOvcThatHasHIVRiskAssessmentDone(indicatorName,indicatorCode,additionalQueryCriteria,startDate,endDate,ageQuery,true,true));
                else if(indicatorCode.equalsIgnoreCase("vccehivrisk"))
                list.add(getNoOfOvcThatHasHIVRiskAssessmentDone(indicatorName,indicatorCode,additionalQueryCriteria,startDate,endDate,ageQuery,false,true));
                else if(indicatorCode.equalsIgnoreCase("vceehivrisk"))
                list.add(getNoOfOvcThatHasHIVRiskAssessmentDone(indicatorName,indicatorCode,additionalQueryCriteria,startDate,endDate,ageQuery,false,false));
                else if(indicatorCode.equalsIgnoreCase("vchivrskser"))
                list.add(getNoOfOvcAssessedForHIVRiskAndServed(indicatorName,additionalQueryCriteria,startDate,endDate,indicatorCode,ageQuery,"All",false,false));
                
                else if(indicatorCode.equalsIgnoreCase("vcnegrskser"))
                list.add(getNoOfHivNegativeOvcAssessedForHIVRiskAndServed(indicatorName,additionalQueryCriteria,startDate,endDate,indicatorCode,ageQuery,false,false));
                
                else if(indicatorCode.equalsIgnoreCase("vcunkrskser"))
                list.add(getNoOfHivUnknownOvcAssessedForHIVRiskAndServed(indicatorName,additionalQueryCriteria,startDate,endDate,indicatorCode,ageQuery,false,false));
                
                else if(indicatorCode.equalsIgnoreCase("vcnashivrsk"))
                list.add(getNumberOfOvcNeverAssessedForHivRiskAssessment(indicatorName,additionalQueryCriteria,startDate,endDate,indicatorCode,false));
                else if(indicatorCode.equalsIgnoreCase("vccgrdclhiv"))
                list.add(getNumberOfOvcByHivStatusFromHivRiskAssessment(indicatorName,additionalQueryCriteria,startDate,endDate,indicatorCode,NomisConstant.HIV_RESULT_NOT_DISCLOSED,false));
                else if(indicatorCode.equalsIgnoreCase("vccgknhivst"))
                list.add(getNumberOfOvcWhoseCaregiversKnowTheirHivStatus(indicatorName,additionalQueryCriteria,startDate,endDate,indicatorCode,"Yes",false));
                else if(indicatorCode.equalsIgnoreCase("vchivrskref"))
                list.add(getNumberOfOvcAssessedForHivRiskAndReferredForTesting(indicatorName,additionalQueryCriteria,startDate,endDate,indicatorCode,"Yes",false));
                
                
                else if(indicatorCode.equalsIgnoreCase("vcce019hhes"))
                list.add(getNoOfOvcWhoseHouseholdProvidedEconomicStrentheningServices(ind.getIndicatorForNumberOfOvc0to19CurrentlyEnrolledWhoseHouseholdsProvidedEconomicStrengthening().getIndicatorName(),additionalQueryCriteria,startDate,endDate,indicatorCode,0,19,true));
                else if(indicatorCode.equalsIgnoreCase("vcee019hhes"))
                list.add(getNoOfOvcWhoseHouseholdProvidedEconomicStrentheningServices(ind.getIndicatorForNumberOfOvc0to19WhoseHouseholdsProvidedEconomicStrengthening().getIndicatorName(),additionalQueryCriteria,startDate,endDate,indicatorCode,0,19,false));
                else if(indicatorCode.equalsIgnoreCase("vcceadohhes"))
                list.add(getNoOfAdolescentWhoseHouseholdProvidedEconomicStrentheningServices(ind.getIndicatorForNumberOfAdolescentsCurrentlyEnrolledWhoseHouseholdsProvidedEconomicStrengthening().getIndicatorName(),additionalQueryCriteria,startDate,endDate,indicatorCode,true));
                else if(indicatorCode.equalsIgnoreCase("vcadolshhes"))
                list.add(getNoOfAdolescentWhoseHouseholdProvidedEconomicStrentheningServices(ind.getIndicatorForNumberOfAdolescentsWhoseHouseholdsProvidedEconomicStrengthening().getIndicatorName(),additionalQueryCriteria,startDate,endDate,indicatorCode,false));
                else if(indicatorCode.equalsIgnoreCase("vcnewlyEnro"))
                list.add(getNoOfOvcEnrolled(indicatorName,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,indicatorCode));
                else if(indicatorCode.equalsIgnoreCase("vccenrolled"))
                list.add(getNoOfOvcCurrentlyEnrolled(additionalQueryCriteria,enrollmentEndDateQuery,ageQuery,indicatorCode));
                else if(indicatorCode.equalsIgnoreCase("vcevenroled"))
                list.add(getNoOfOvcEverEnrolled(ind.getIndicatorForNumberOfOvcEverEnrolled().getIndicatorName(),additionalQueryCriteria,enrollmentEndDateQuery,ageQuery,indicatorCode));
                else if(indicatorCode.equalsIgnoreCase("vcserved007"))
                list.add(getNoOfOvcServed(ind.getIndicatorForNumberOfOvcProvidedWithAtleastOneService().getIndicatorName(),additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery,indicatorCode));
                
                else if(indicatorCode.equalsIgnoreCase("vcnenservrp"))
                list.add(getNoOfOvcNewlyEnrolledAndServedWithinTheReportPeriod(indicatorName,additionalQueryCriteria,startAge,endAge,startDate,endDate,indicatorCode));
                //list.add(getNoOfOvcNewlyEnrolledAndServedWithinTheReportPeriod(ind.getIndicatorForNumberOfNewOvcEnrolledAndServedWithinTheReportPeriod().getIndicatorName(),additionalQueryCriteria,additionalServiceQuery,ageQuery,indicatorCode));
                
                else if(indicatorCode.equalsIgnoreCase("vccurenserv"))
                list.add(getNoOfOvcCurrentlyEnrolledAndServed(ind.getIndicatorForNumberOfOvcCurrentlyEnrolledAndServedInReportPeriod().getIndicatorName(),sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery,indicatorCode));
                else if(indicatorCode.equalsIgnoreCase("vcgradserve"))
                list.add(getNoOfOvcWithdrawnButServedWithinReportPeriod(ind.getIndicatorForNumberOfOvcGraduatedButServedInReportPeriod().getIndicatorName(),additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery,indicatorCode,ReportUtility.GRADUATED));
                else if(indicatorCode.equalsIgnoreCase("vcltfuserve"))
                list.add(getNoOfOvcWithdrawnButServedWithinReportPeriod(ind.getIndicatorForNumberOfOvcLostToFollowupButServedInReportPeriod().getIndicatorName(),additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery,indicatorCode,ReportUtility.LOST_TO_FOLLOWUP));
                else if(indicatorCode.equalsIgnoreCase("vcmigrserve"))
                list.add(getNoOfOvcWithdrawnButServedWithinReportPeriod(ind.getIndicatorForNumberOfOvcMigratedButServedInReportPeriod().getIndicatorName(),additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery,indicatorCode,ReportUtility.MIGRATED));
                else if(indicatorCode.equalsIgnoreCase("vcageoserve"))
                list.add(getNoOfOvcWithdrawnButServedWithinReportPeriod(ind.getIndicatorForNumberOfOvcAgedoutButServedInReportPeriod().getIndicatorName(),additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery,indicatorCode,ReportUtility.AGED_OUT));
                else if(indicatorCode.equalsIgnoreCase("vctranserve"))
                list.add(getNoOfOvcWithdrawnButServedWithinReportPeriod(ind.getIndicatorForNumberOfOvcTransferedOutButServedInReportPeriod().getIndicatorName(),additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery,indicatorCode,ReportUtility.TRANSFERED));
                else if(indicatorCode.equalsIgnoreCase("vcdeadserve"))
                list.add(getNoOfOvcWithdrawnButServedWithinReportPeriod(ind.getIndicatorForNumberOfOvcDiedButServedInReportPeriod().getIndicatorName(),additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery,indicatorCode,ReportUtility.DIED));
                else if(indicatorCode.equalsIgnoreCase("vcinactserv"))
                list.add(getNoOfOvcWithdrawnButServedWithinReportPeriod(ind.getIndicatorForNumberOfOvcInactiveButServedInReportPeriod().getIndicatorName(),additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery,indicatorCode,NomisConstant.INACTIVE));
                
                else if(indicatorCode.equalsIgnoreCase("vcpossergrd"))
                list.add(getNoOfOvcWithdrawnButServedWithinReportPeriodByHivStatus(indicatorName,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,indicatorCode,NomisConstant.GRADUATED,NomisConstant.HIV_POSITIVE,false,startDate,endDate));
                else if(indicatorCode.equalsIgnoreCase("vcartsergrd"))
                list.add(getNoOfOvcWithdrawnButServedWithinReportPeriodByHivStatus(indicatorName,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,indicatorCode,NomisConstant.GRADUATED,NomisConstant.HIV_POSITIVE,true,startDate,endDate));
                else if(indicatorCode.equalsIgnoreCase("vcnegsergrd"))
                list.add(getNoOfOvcWithdrawnButServedWithinReportPeriodByHivStatus(indicatorName,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,indicatorCode,NomisConstant.GRADUATED,NomisConstant.HIV_NEGATIVE,false,startDate,endDate));
                else if(indicatorCode.equalsIgnoreCase("vcunksergrd"))
                list.add(getNoOfOvcWithdrawnButServedWithinReportPeriodByHivStatus(indicatorName,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,indicatorCode,NomisConstant.GRADUATED,NomisConstant.HIV_UNKNOWN,false,startDate,endDate));
                else if(indicatorCode.equalsIgnoreCase("vctnigrdser"))
                list.add(getNoOfOvcWithdrawnButServedWithinReportPeriodByHivStatus(indicatorName,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,indicatorCode,NomisConstant.GRADUATED,NomisConstant.HIV_TEST_NOT_INDICATED,false,startDate,endDate));
                
                //getNoOfOvcWithdrawnButServedWithinReportPeriodByHivStatus(String indicatorName,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery,String indicatorCode,String reasonWithrawal,String hivStatus,String startDate,String endDate)
                /*else if(indicatorCode.equalsIgnoreCase("hivPosbasel"))
                list.add(getNoHivPosOvcAtBaseline(ind.getIndicatorForNumberOfHIVPositiveOvcAtBaseline().getIndicatorName(),additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,indicatorCode));
                else if(indicatorCode.equalsIgnoreCase("hivNegbasel"))
                list.add(getNoHivNegOvcAtBaseline(ind.getIndicatorForNumberOfHIVNegativeOvcAtBaseline().getIndicatorName(),additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,indicatorCode));
                else if(indicatorCode.equalsIgnoreCase("hivUnkbasel"))
                list.add(getNoHivUnknownOvcAtBaseline(ind.getIndicatorForNumberOfHIVUnknownOvcAtBaseline().getIndicatorName(),additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,indicatorCode));
                  */             
                else if(indicatorCode.equalsIgnoreCase("vchivPosnEn"))
                list.add(getNoHivPosOvc(ind.getIndicatorForNumberOfHIVPositiveOvcNewlyEnrolled().getIndicatorName(),additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,indicatorCode));
                else if(indicatorCode.equalsIgnoreCase("vchivNegnEn"))
                list.add(getNoHivNegOvc(ind.getIndicatorForNumberOfHIVNegativeOvcNewlyEnrolled().getIndicatorName(),additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,indicatorCode));
                else if(indicatorCode.equalsIgnoreCase("vchivUnknEn"))
                list.add(getNoHivUnknownOvc(ind.getIndicatorForNumberOfHIVUnknownOvcNewlyEnrolled().getIndicatorName(),additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,indicatorCode));
                
                else if(indicatorCode.equalsIgnoreCase("hivPosEnrol"))
                list.add(getNoOfOvcCurrentlyEnrolledByHIVStatus(ind.getIndicatorForNumberOfHIVPositiveOvcCurrentlyEnrolled().getIndicatorName(),additionalQueryCriteria,ageQuery,indicatorCode,NomisConstant.HIV_POSITIVE,true));
                else if(indicatorCode.equalsIgnoreCase("hivNegEnrol"))
                list.add(getNoOfOvcCurrentlyEnrolledByHIVStatus(ind.getIndicatorForNumberOfHIVNegativeOvcCurrentlyEnrolled().getIndicatorName(),additionalQueryCriteria,ageQuery,indicatorCode,NomisConstant.HIV_NEGATIVE,true));
                else if(indicatorCode.equalsIgnoreCase("hivUnkEnrol"))
                list.add(getNoOfOvcCurrentlyEnrolledByHIVStatus(ind.getIndicatorForNumberOfHIVUnknownOvcCurrentlyEnrolled().getIndicatorName(),additionalQueryCriteria,ageQuery,indicatorCode,NomisConstant.HIV_UNKNOWN,true));
                
                else if(indicatorCode.equalsIgnoreCase("vchivposeen"))
                list.add(getNoOfOvcCurrentlyEnrolledByHIVStatus(ind.getIndicatorForNumberOfHIVPositiveOvcEverEnrolled().getIndicatorName(),additionalQueryCriteria,ageQuery,indicatorCode,NomisConstant.HIV_POSITIVE,false));
                else if(indicatorCode.equalsIgnoreCase("vchivnegeen"))
                list.add(getNoOfOvcCurrentlyEnrolledByHIVStatus(ind.getIndicatorForNumberOfHIVNegativeOvcEverEnrolled().getIndicatorName(),additionalQueryCriteria,ageQuery,indicatorCode,NomisConstant.HIV_NEGATIVE,false));
                else if(indicatorCode.equalsIgnoreCase("vchivunkeen"))
                list.add(getNoOfOvcCurrentlyEnrolledByHIVStatus(ind.getIndicatorForNumberOfHIVUnknownOvcEverEnrolled().getIndicatorName(),additionalQueryCriteria,ageQuery,indicatorCode,NomisConstant.HIV_UNKNOWN,false));
                
                else if(indicatorCode.equalsIgnoreCase("vcnewInCare"))
                list.add(getNoOfHIVPositiveOvcNewlyEnrolledInCare(ind.getIndicatorForNumberOfHIVPositiveOvcCurrentlyEnrolledInCare().getIndicatorName(),additionalQueryCriteria,ageQuery,indicatorCode,true,hivStatusPeriodQuery));
                else if(indicatorCode.equalsIgnoreCase("vccurInCare"))
                list.add(getNoOfHIVPositiveOvcEnrolledInCare(ind.getIndicatorForNumberOfHIVPositiveOvcCurrentlyEnrolledInCare().getIndicatorName(),additionalQueryCriteria,ageQuery,indicatorCode,true));
                else if(indicatorCode.equalsIgnoreCase("vceveInCare"))
                list.add(getNoOfHIVPositiveOvcEnrolledInCare(ind.getIndicatorForNumberOfHIVPositiveOvcEverEnrolledInCare().getIndicatorName(),additionalQueryCriteria,ageQuery,indicatorCode,false));
                else if(indicatorCode.equalsIgnoreCase("vccureOnArt"))
                list.add(getNoOfHIVPositiveOvcEnrolledOnART(ind.getIndicatorForNumberOfHIVPositiveOvcCurrentlyEnrolledOnART().getIndicatorName(),additionalQueryCriteria,ageQuery,indicatorCode,startDate,endDate,true));
                else if(indicatorCode.equalsIgnoreCase("vceverOnArt"))
                list.add(getNoOfHIVPositiveOvcEnrolledOnART(ind.getIndicatorForNumberOfHIVPositiveOvcEverEnrolledOnART().getIndicatorName(),additionalQueryCriteria,ageQuery,indicatorCode,startDate,endDate,false));
                
                else if(indicatorCode.equalsIgnoreCase("vchivPosinp"))
                list.add(getNoOfOvcCurrentlyEnrolledByHIVStatusAndReportPeriod(ind.getIndicatorForNumberOfHIVPositiveOvcEnrolledAndAreStillInProgram().getIndicatorName(),additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,indicatorCode,"positive"));
                else if(indicatorCode.equalsIgnoreCase("vchivNeginp"))
                list.add(getNoOfOvcCurrentlyEnrolledByHIVStatusAndReportPeriod(ind.getIndicatorForNumberOfHIVNegativeOvcEnrolledAndAreStillInProgram().getIndicatorName(),additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,indicatorCode,"negative"));
                else if(indicatorCode.equalsIgnoreCase("vchivUnkinp"))
                list.add(getNoOfOvcCurrentlyEnrolledByHIVStatusAndReportPeriod(ind.getIndicatorForNumberOfHIVUnknownOvcEnrolledAndAreStillInProgram().getIndicatorName(),additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,indicatorCode,"unknown"));
                
                else if(indicatorCode.equalsIgnoreCase("posvcserved"))
                list.add(getNoOfHivPositiveOvcServed(ind.getIndicatorForNumberOfHivPositiveOvcProvidedWithAtleastOneService().getIndicatorName(),additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery,indicatorCode,false,false));
                
                else if(indicatorCode.equalsIgnoreCase("vcartserved"))
                list.add(getNoOfHivPositiveOvcServed(ind.getIndicatorForNumberOfHivPositiveOvcProvidedWithAtleastOneService().getIndicatorName(),additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery,indicatorCode,true,false));
                
                else if(indicatorCode.equalsIgnoreCase("negvcserved"))
                list.add(getNoOfHivNegativeOvcServed(ind.getIndicatorForNumberOfHivNegativeOvcProvidedWithAtleastOneService().getIndicatorName(),additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery,indicatorCode,false));
                else if(indicatorCode.equalsIgnoreCase("unkvcserved"))
                list.add(getNoOfHivUnknownOvcServed(ind.getIndicatorForNumberOfHivUnknownOvcProvidedWithAtleastOneService().getIndicatorName(),additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery,indicatorCode,NomisConstant.HIV_UNKNOWN,false));
                
                else if(indicatorCode.equalsIgnoreCase("vctniserved"))
                list.add(getNoOfHivUnknownOvcServed(ind.getIndicatorForNumberOfHivUnknownOvcWithTestNotIndicatedServed().getIndicatorName(),additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery,indicatorCode,NomisConstant.HIV_TEST_NOT_INDICATED,false));
                
                else if(indicatorCode.equalsIgnoreCase("vcposceserv"))
                list.add(getNoOfHivPositiveOvcServed(ind.getIndicatorForNumberOfHivPositiveOvcCurrentlyEnrolledProvidedWithAtleastOneService().getIndicatorName(),additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery,indicatorCode,false,true));
                else if(indicatorCode.equalsIgnoreCase("vcartceserv"))
                list.add(getNoOfHivPositiveOvcServed(indicatorName,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery,indicatorCode,true,true));
                else if(indicatorCode.equalsIgnoreCase("vcnegceserv"))
                list.add(getNoOfHivNegativeOvcServed(ind.getIndicatorForNumberOfHivNegativeOvcCurrentlyEnrolledProvidedWithAtleastOneService().getIndicatorName(),additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery,indicatorCode,true));
                else if(indicatorCode.equalsIgnoreCase("vcunkceserv"))
                list.add(getNoOfHivUnknownOvcServed(ind.getIndicatorForNumberOfHivUnknownOvcCurrentlyEnrolledProvidedWithAtleastOneService().getIndicatorName(),additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery,indicatorCode,NomisConstant.HIV_UNKNOWN,true));
                else if(indicatorCode.equalsIgnoreCase("vctniceserv"))
                list.add(getNoOfHivUnknownOvcServed(ind.getIndicatorForNumberOfHivUnknownOvcCurrentlyEnrolledProvidedWithAtleastOneService().getIndicatorName(),additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery,indicatorCode,NomisConstant.HIV_TEST_NOT_INDICATED,true));
                
                else if(indicatorCode.equalsIgnoreCase("vcnotserved"))
                list.add(getNumberOfOvcWithoutServiceRecords(indicatorCode,ind.getIndicatorForNumberOfOvcWithoutServiceRecords().getIndicatorName(),additionalQueryCriteria+ageQuery,startDate,endDate,true));
                //getNumberOfOvcProvidedPsychosocialServices(String indicatorCode,String indicatorName,String additionalQueryCriteria,String additionalServiceQuery,String ageQuery,String serviceType)
                else if(indicatorCode.equalsIgnoreCase("vcrecactyrp"))
                list.add(getNumberOfOvcProvidedPsychosocialServices(indicatorCode,indwh.getIndicatorById(indicatorCode).getIndicatorName(), additionalQueryCriteria,additionalServiceQuery,ageQuery,"recreationalActivities"));
                else if(indicatorCode.equalsIgnoreCase("vcservedpsy"))
                list.add(getNoOfOvcProvidedPsychosocialServices(indicatorName,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery,indicatorCode));
                else if(indicatorCode.equalsIgnoreCase("vcservednut"))
                list.add(getNoOfOvcProvidedNutritionalServices(indicatorName,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery,indicatorCode));
                else if(indicatorCode.equalsIgnoreCase("vcservedhlt"))
                list.add(getNoOfOvcProvidedHealthServices(indicatorName,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery,indicatorCode));
                else if(indicatorCode.equalsIgnoreCase("vcservededu"))
                list.add(getNoOfOvcProvidedEducationalServices(indicatorName,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery,indicatorCode));
                else if(indicatorCode.equalsIgnoreCase("vcservedpro"))
                list.add(getNoOfOvcProvidedProtectionServices(indicatorName,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery,indicatorCode));
                else if(indicatorCode.equalsIgnoreCase("vcservedshe"))
                list.add(getNoOfOvcProvidedShelterAndCareServices(indicatorName,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery,indicatorCode));
                
                else if(indicatorCode.equalsIgnoreCase("vcposserpsy"))
                list.add(getNumberOfServedByDomainAndHivStatus(indicatorCode,indicatorName,additionalQueryCriteria,additionalServiceQuery,NomisConstant.DOMAIN_PSYCHOSOCIAL,NomisConstant.HIV_POSITIVE));
                else if(indicatorCode.equalsIgnoreCase("vcnegserpsy"))
                list.add(getNumberOfServedByDomainAndHivStatus(indicatorCode,indicatorName,additionalQueryCriteria,additionalServiceQuery,NomisConstant.DOMAIN_PSYCHOSOCIAL,NomisConstant.HIV_NEGATIVE));
                else if(indicatorCode.equalsIgnoreCase("vcunkserpsy"))
                list.add(getNumberOfServedByDomainAndHivStatus(indicatorCode,indicatorName,additionalQueryCriteria,additionalServiceQuery,NomisConstant.DOMAIN_PSYCHOSOCIAL,NomisConstant.HIV_UNKNOWN));
                
                /*else if(indicatorCode.equalsIgnoreCase("posvcsernut"))
                list.add(getNoOfHIVPositiveOvcProvidedNutritionalServices(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery,indicatorCode));
                else if(indicatorCode.equalsIgnoreCase("negvcsernut"))
                list.add(getNoOfHIVNegativeOvcProvidedNutritionalServices(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery,indicatorCode));
                else if(indicatorCode.equalsIgnoreCase("unkvcsernut"))
                list.add(getNoOfHIVUnknownOvcProvidedNutritionalServices(sex,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery,indicatorCode));*/
                else if(indicatorCode.equalsIgnoreCase("vcpossernut"))
                list.add(getNumberOfServedByDomainAndHivStatus(indicatorCode,indicatorName,additionalQueryCriteria,additionalServiceQuery,NomisConstant.DOMAIN_NUTRITION,NomisConstant.HIV_POSITIVE));
                else if(indicatorCode.equalsIgnoreCase("vcnegsernut"))
                list.add(getNumberOfServedByDomainAndHivStatus(indicatorCode,indicatorName,additionalQueryCriteria,additionalServiceQuery,NomisConstant.DOMAIN_NUTRITION,NomisConstant.HIV_NEGATIVE));
                else if(indicatorCode.equalsIgnoreCase("vcunksernut"))
                list.add(getNumberOfServedByDomainAndHivStatus(indicatorCode,indicatorName,additionalQueryCriteria,additionalServiceQuery,NomisConstant.DOMAIN_NUTRITION,NomisConstant.HIV_UNKNOWN));
                
                else if(indicatorCode.equalsIgnoreCase("vcposserhlt"))
                list.add(getNumberOfServedByDomainAndHivStatus(indicatorCode,indicatorName,additionalQueryCriteria,additionalServiceQuery,NomisConstant.DOMAIN_HEALTH,NomisConstant.HIV_POSITIVE));
                else if(indicatorCode.equalsIgnoreCase("vcnegserhlt"))
                list.add(getNumberOfServedByDomainAndHivStatus(indicatorCode,indicatorName,additionalQueryCriteria,additionalServiceQuery,NomisConstant.DOMAIN_HEALTH,NomisConstant.HIV_NEGATIVE));
                else if(indicatorCode.equalsIgnoreCase("vcunkserhlt"))
                list.add(getNumberOfServedByDomainAndHivStatus(indicatorCode,indicatorName,additionalQueryCriteria,additionalServiceQuery,NomisConstant.DOMAIN_HEALTH,NomisConstant.HIV_UNKNOWN));
                
                else if(indicatorCode.equalsIgnoreCase("vcposseredu"))
                list.add(getNumberOfServedByDomainAndHivStatus(indicatorCode,indicatorName,additionalQueryCriteria,additionalServiceQuery,NomisConstant.DOMAIN_EDUCATION,NomisConstant.HIV_POSITIVE));
                else if(indicatorCode.equalsIgnoreCase("vcnegseredu"))
                list.add(getNumberOfServedByDomainAndHivStatus(indicatorCode,indicatorName,additionalQueryCriteria,additionalServiceQuery,NomisConstant.DOMAIN_EDUCATION,NomisConstant.HIV_NEGATIVE));
                else if(indicatorCode.equalsIgnoreCase("vcunkseredu"))
                list.add(getNumberOfServedByDomainAndHivStatus(indicatorCode,indicatorName,additionalQueryCriteria,additionalServiceQuery,NomisConstant.DOMAIN_EDUCATION,NomisConstant.HIV_UNKNOWN));
                
                else if(indicatorCode.equalsIgnoreCase("vcposserpro"))
                list.add(getNumberOfServedByDomainAndHivStatus(indicatorCode,indicatorName,additionalQueryCriteria,additionalServiceQuery,NomisConstant.DOMAIN_PROTECTION,NomisConstant.HIV_POSITIVE));
                else if(indicatorCode.equalsIgnoreCase("vcnegserpro"))
                list.add(getNumberOfServedByDomainAndHivStatus(indicatorCode,indicatorName,additionalQueryCriteria,additionalServiceQuery,NomisConstant.DOMAIN_PROTECTION,NomisConstant.HIV_NEGATIVE));
                else if(indicatorCode.equalsIgnoreCase("vcunkserpro"))
                list.add(getNumberOfServedByDomainAndHivStatus(indicatorCode,indicatorName,additionalQueryCriteria,additionalServiceQuery,NomisConstant.DOMAIN_PSYCHOSOCIAL,NomisConstant.HIV_UNKNOWN));
                
                else if(indicatorCode.equalsIgnoreCase("vcpossersht"))
                list.add(getNumberOfServedByDomainAndHivStatus(indicatorCode,indicatorName,additionalQueryCriteria,additionalServiceQuery,NomisConstant.DOMAIN_SHELTER,NomisConstant.HIV_POSITIVE));
                else if(indicatorCode.equalsIgnoreCase("vcnegsersht"))
                list.add(getNumberOfServedByDomainAndHivStatus(indicatorCode,indicatorName,additionalQueryCriteria,additionalServiceQuery,NomisConstant.DOMAIN_SHELTER,NomisConstant.HIV_NEGATIVE));
                else if(indicatorCode.equalsIgnoreCase("vcunksersht"))
                list.add(getNumberOfServedByDomainAndHivStatus(indicatorCode,indicatorName,additionalQueryCriteria,additionalServiceQuery,NomisConstant.DOMAIN_SHELTER,NomisConstant.HIV_UNKNOWN));
                
                else if(indicatorCode.equalsIgnoreCase("vcposserecs"))
                list.add(getNumberOfServedByDomainAndHivStatus(indicatorCode,indicatorName,additionalQueryCriteria,additionalServiceQuery,NomisConstant.DOMAIN_ECONOMICSTRENTHENINIG,NomisConstant.HIV_POSITIVE));
                else if(indicatorCode.equalsIgnoreCase("vcnegserecs"))
                list.add(getNumberOfServedByDomainAndHivStatus(indicatorCode,indicatorName,additionalQueryCriteria,additionalServiceQuery,NomisConstant.DOMAIN_ECONOMICSTRENTHENINIG,NomisConstant.HIV_NEGATIVE));
                else if(indicatorCode.equalsIgnoreCase("vcunkserecs"))
                list.add(getNumberOfServedByDomainAndHivStatus(indicatorCode,indicatorName,additionalQueryCriteria,additionalServiceQuery,NomisConstant.DOMAIN_ECONOMICSTRENTHENINIG,NomisConstant.HIV_UNKNOWN));
                
                else if(indicatorCode.equalsIgnoreCase("vcwithdrawn"))
                list.add(getNoOfOvcWithdrawn(sex,additionalQueryCriteria,withdrawalPeriodQuery,additionalServiceQuery,ageQuery,indicatorCode));
                else if(indicatorCode.equalsIgnoreCase("vcgraduated"))
                list.add(getNumberOfOvcWithdrawn(ind.getIndicatorForNumberOfOvcGraduated().getIndicatorName(),additionalQueryCriteria,withdrawalPeriodQuery,indicatorCode,ReportUtility.GRADUATED));
                else if(indicatorCode.equalsIgnoreCase("ovcagingout"))
                list.add(getNumberOfOvcWithdrawn(ind.getIndicatorForNumberOfOvcWithdrawnDueToAgingOut().getIndicatorName(),additionalQueryCriteria,withdrawalPeriodQuery,indicatorCode,ReportUtility.AGED_OUT));
                else if(indicatorCode.equalsIgnoreCase("vcknowndeat"))
                list.add(getNumberOfOvcWithdrawn(ind.getIndicatorForNumberOfOvcWithdrawnDueToKnownDeath().getIndicatorName(),additionalQueryCriteria,withdrawalPeriodQuery,indicatorCode,ReportUtility.DIED));
                else if(indicatorCode.equalsIgnoreCase("ovcmigrated"))
                list.add(getNumberOfOvcWithdrawn(ind.getIndicatorForNumberOfOvcWithdrawnDueToMigration().getIndicatorName(),additionalQueryCriteria,withdrawalPeriodQuery,indicatorCode,ReportUtility.MIGRATED));
                else if(indicatorCode.equalsIgnoreCase("vclosttofup"))
                list.add(getNumberOfOvcWithdrawn(ind.getIndicatorForNumberOfOvcWithdrawnDueToLostToFollowup().getIndicatorName(),additionalQueryCriteria,withdrawalPeriodQuery,indicatorCode,ReportUtility.LOST_TO_FOLLOWUP));
                else if(indicatorCode.equalsIgnoreCase("vctransferd"))
                list.add(getNumberOfOvcWithdrawn(ind.getIndicatorForNumberOfOvcWithdrawnDueToTransfer().getIndicatorName(),additionalQueryCriteria,withdrawalPeriodQuery,indicatorCode,ReportUtility.TRANSFERED));
                else if(indicatorCode.equalsIgnoreCase("vcinactivrp"))
                list.add(getNumberOfOvcWithdrawn(ind.getIndicatorForNumberOfOvcDeclaredInactive().getIndicatorName(),additionalQueryCriteria,withdrawalPeriodQuery,indicatorCode,NomisConstant.INACTIVE));
                
                else if(indicatorCode.equalsIgnoreCase("vcposcevulb"))
                list.add(getNumberOfHivPosOvcCurrentlyEnrolledBasedOnVulnerability(ind.getIndicatorForNumberOfHivPosOvcCurrentlyEnrolledThatAreFromVulnerableHouseholdsAtBaseline().getIndicatorId(),ind.getIndicatorForNumberOfHivPosOvcCurrentlyEnrolledThatAreFromVulnerableHouseholdsAtBaseline().getIndicatorName(),additionalQueryCriteria,NomisConstant.VULNERABLE_STARTVALUE,NomisConstant.VULNERABLE_ENDVALUE,true));
                else if(indicatorCode.equalsIgnoreCase("vcposcemrvb"))
                list.add(getNumberOfHivPosOvcCurrentlyEnrolledBasedOnVulnerability(ind.getIndicatorForNumberOfHivPosOvcCurrentlyEnrolledThatAreFromMoreVulnerableHouseholdsAtBaseline().getIndicatorId(),ind.getIndicatorForNumberOfHivPosOvcCurrentlyEnrolledThatAreFromMoreVulnerableHouseholdsAtBaseline().getIndicatorName(),additionalQueryCriteria,NomisConstant.MOREVULNERABLE_STARTVALUE,NomisConstant.MOREVULNERABLE_ENDVALUE,true));
                else if(indicatorCode.equalsIgnoreCase("vcposcemovb"))
                list.add(getNumberOfHivPosOvcCurrentlyEnrolledBasedOnVulnerability(ind.getIndicatorForNumberOfHivPosOvcCurrentlyEnrolledThatAreFromMostVulnerableHouseholdsAtBaseline().getIndicatorId(),ind.getIndicatorForNumberOfHivPosOvcCurrentlyEnrolledThatAreFromMostVulnerableHouseholdsAtBaseline().getIndicatorName(),additionalQueryCriteria,NomisConstant.MOSTVULNERABLE_STARTVALUE,NomisConstant.MOSTVULNERABLE_ENDVALUE,true));
                

                else if(indicatorCode.equalsIgnoreCase("vcnotschool"))
                list.add(getNoOfOvcOutOfSchool(ind.getIndicatorForNumberOfOvcOutOfSchool().getIndicatorName(),sex,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,indicatorCode,true));
                else if(indicatorCode.equalsIgnoreCase("vccurrinsch"))
                list.add(getNoOfOvcCurrentlyInSchool(ind.getIndicatorForNumberOfOvcCurrentlyInSchool().getIndicatorName(),additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,indicatorCode,true));
                //
                //else if(indicatorCode.equalsIgnoreCase("vcwbthcert"))
                //list.add(getTotalNoOfOvcWithBirthCert(ind.getIndicatorForTotalNumberOfOvcProvidedBirthCertWithinReportPeriod().getIndicatorName(),additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,indicatorCode));
                else if(indicatorCode.equalsIgnoreCase("vcsmalnsern"))
                list.add(getNumberofSeverelyMalnourishedOvcIdentifiedAndServedWithinTheReportPeriod(indicatorCode,indicatorName,additionalQueryCriteria,startDate,endDate,false));//(ind.getIndicatorForNumberOfOvcNewlyEnrolledWithBirthCertificateAtBaseline().getIndicatorName(),additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,indicatorCode,false));
                else if(indicatorCode.equalsIgnoreCase("vcnebslbcrt"))
                list.add(getNoOfOvcWithBirthCertAtBaseline(ind.getIndicatorForNumberOfOvcNewlyEnrolledWithBirthCertificateAtBaseline().getIndicatorName(),additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,indicatorCode,false));
                else if(indicatorCode.equalsIgnoreCase("vccebslbcrt"))
                list.add(getNoOfOvcWithBirthCertAtBaseline(ind.getIndicatorForNumberOfOvcCurrentlyEnrolledWithBirthCertAtBaseline().getIndicatorName(),additionalQueryCriteria,"",ageQuery,indicatorCode,true));
                else if(indicatorCode.equalsIgnoreCase("vceebslbcrt"))
                list.add(getNoOfOvcWithBirthCertAtBaseline(ind.getIndicatorForNumberOfOvcEverEnrolledWithBirthCertificateAtBaseline().getIndicatorName(),additionalQueryCriteria,"",ageQuery,indicatorCode,false));
                else if(indicatorCode.equalsIgnoreCase("vcserbtctrp"))
                list.add(getNoOfOvcProvidedBirthCertService(ind.getIndicatorForNumberOfOvcProvidedBirthCertWithinReportPeriod().getIndicatorName(),additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,indicatorCode,NomisConstant.SERVICE_POINTOFUPDATE,false));
                //
                else if(indicatorCode.equalsIgnoreCase("vcceserbcer"))
                list.add(getNoOfOvcServedWithinTheReportPeriodThatHasBirthCert(indicatorCode,indicatorName,additionalQueryCriteria,ageQuery,startDate,endDate, true));
                else if(indicatorCode.equalsIgnoreCase("vccelt18cer"))
                list.add(getNoOfOvcWithBirthCertificate(indicatorName,additionalQueryCriteria,additionalEnrollmentQuery,lessThan18ageQuery,indicatorCode,null,true));
                else if(indicatorCode.equalsIgnoreCase("vccebthcert"))
                list.add(getNoOfOvcWithBirthCertificate(ind.getIndicatorForNumberOfOvcCurrentlyEnrolledWithBirthCertificate().getIndicatorName(),additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,indicatorCode,null,true));
                else if(indicatorCode.equalsIgnoreCase("vceebthcert"))
                list.add(getNoOfOvcWithBirthCertificate(ind.getIndicatorForNumberOfOvcEverEnrolledWithBirthCertificate().getIndicatorName(),additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,indicatorCode,null,false));
               
                else if(indicatorCode.equalsIgnoreCase("vcwithnobir"))
                list.add(getNoOvcWithoutBirthCertAtBaseline(ind.getIndicatorForNumberOfOvcWithoutBirthCertificateAtBaseline().getIndicatorName(),additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,indicatorCode));
                else if(indicatorCode.equalsIgnoreCase("vcwithnobcc"))
                list.add(getNoOvcWithoutBirthCertCurrently(ind.getIndicatorForNumberOfOvcWithoutBirthCertificateCurrently().getIndicatorName(),additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,indicatorCode));
                
                else if(indicatorCode.equalsIgnoreCase("gt3services"))
                {
                    if(!numberOfServicesSet)
                    {
                        setNoOfServices(sex,additionalQueryCriteria,additionalServiceQuery,ageQuery);
                        numberOfServicesSet=true;
                    }
                    list.add(getNoOfOvcThatReceivedThreeOrMoreServices(indicatorCode));
                }
                else if(indicatorCode.equalsIgnoreCase("lt3services"))
                {
                    if(!numberOfServicesSet)
                    {
                        setNoOfServices(sex,additionalQueryCriteria,additionalServiceQuery,ageQuery);
                        numberOfServicesSet=true;
                    }
                    list.add(getNoOfOvcThatReceivedLessThanThreeServices(indicatorCode));
                }
                else if(indicatorCode.equalsIgnoreCase("newnotserve"))
                list.add(getNoOfOvcNewlyEnrolledWithoutServiceRecords(ind.getIndicatorForNumberOfNewOvcWithoutServiceRecords().getIndicatorName(),additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery,indicatorCode));
                else if(indicatorCode.equalsIgnoreCase("vchivaccess"))
                {
                    MonthlySummaryFormReport msr=new MonthlySummaryFormReport();
                    msr.setAgeSegregation(msr.getNewAgeSegregation());              
                    List mainList=getNoOfOvcSupportedOnHIVAIDSServicesForMSF("Number of OVC accompanied or otherwise supported for transport to HIV testing, care and/or treatment services at least once every three months (OVC_ACC)",additionalQueryCriteria,dateArray,indicatorCode);
                    list.add(mainList);
                }//Caregivers forum
                else if(indicatorCode.equalsIgnoreCase("cgenrsilcrp"))
                list.add(getNumberOfCaregiversProvidedHES(indicatorCode,indwh.getIndicatorById(indicatorCode).getIndicatorName(),additionalQueryCriteria,startDate,endDate,"SILC",false));
                else if(indicatorCode.equalsIgnoreCase("cginforumrp"))
                list.add(getNumberOfCaregiversProvidedHES(indicatorCode,indwh.getIndicatorById(indicatorCode).getIndicatorName(),additionalQueryCriteria,startDate,endDate,"cgforum",false));
                
                else if(indicatorCode.equalsIgnoreCase("cgnotserved"))
                list.add(getNumberOfCaregiversWithoutServiceRecords(indicatorCode,ind.getIndicatorForNumberOfCaregiversWithoutServiceRecords().getIndicatorName(),additionalQueryCriteria,startDate,endDate,true));
                else if(indicatorCode.equalsIgnoreCase("cgservedhes"))
                list.add(getNumberOfCaregiversProvidedHES(indicatorCode,ind.getIndicatorForNumberOfCaregiversProvidedHES().getIndicatorName(),additionalQueryCriteria,startDate,endDate,"hes",false));
                else if(indicatorCode.equalsIgnoreCase("cgtesforhiv"))
                list.add(getNumberOfCaregiversTestedForHIV(indicatorCode,ind.getIndicatorForNumberOfCaregiversTestedForHiv().getIndicatorName(),additionalQueryCriteria,startDate,endDate,false));
                else if(indicatorCode.equalsIgnoreCase("cgscrnfortb"))
                list.add(getNumberOfCaregiversScreenedForTB(ind.getIndicatorForNumberOfCaregiversScreenedForTB().getIndicatorName(),additionalQueryCriteria,dateArray,indicatorCode));
                //getNumberOfBeneficiariesNewlyEnrolled(indicatorName,additionalQueryCriteria,hviQueryCriteria,additionalServiceQuery, cgiverPeriodQuery,hviServiceQueryCriteria, indicatorCode,currentlyEnrolled)
                else if(indicatorCode.equalsIgnoreCase("vccgnenserv"))
                list.add(getNumberOfBeneficiariesNewlyEnrolled(indicatorName,additionalQueryCriteria,startAge,endAge,startDate,endDate,hviQueryCriteria,additionalServiceQuery, cgiverPeriodQuery,hviServiceQueryCriteria, indicatorCode,false));
                //list.add(getNumberOfBeneficiariesNewlyEnrolled(indicatorName,additionalQueryCriteria,hviQueryCriteria,additionalServiceQuery, cgiverPeriodQuery,hviServiceQueryCriteria, indicatorCode,false));
                
                else if(indicatorCode.equalsIgnoreCase("cgiverserve"))
                list.add(getNoOfCaregiversServed(ind.getIndicatorForNumberOfCaregiversProvidedAtleastOneService().getIndicatorId(),ind.getIndicatorForNumberOfCaregiversProvidedAtleastOneService().getIndicatorName(),hviQueryCriteria,cgiverPeriodQuery,hviServiceQueryCriteria,caregiverageQuery,false,false));
                else if(indicatorCode.equalsIgnoreCase("cgnenrserrp"))
                list.add(getNoOfCaregiversNewlyEnrolledAndServed(indicatorCode,indicatorName,additionalQueryCriteria,startDate,endDate,false));
                //list.add(getNoOfCaregiversServed(indicatorCode,indicatorName,hviQueryCriteria,cgiverPeriodQuery,hviServiceQueryCriteria,ageQuery,true,false));
                //
                else if(indicatorCode.equalsIgnoreCase("cgactiserrp"))
                list.add(getNoOfCaregiversServed(ind.getIndicatorForNumberOfActiveCaregiversServedWithinTheReportPeriod().getIndicatorId(),ind.getIndicatorForNumberOfActiveCaregiversServedWithinTheReportPeriod().getIndicatorName(),hviQueryCriteria,cgiverPeriodQuery,hviServiceQueryCriteria,caregiverageQuery,false,true));
                else if(indicatorCode.equalsIgnoreCase("cgactsrpage"))
                list.add(getNoOfCaregiversServedByAge(ind.getIndicatorForNumberOfActiveCaregiversServedWithinTheReportPeriod().getIndicatorId(),ind.getIndicatorForNumberOfActiveCaregiversServedWithinTheReportPeriod().getIndicatorName(),hviQueryCriteria,cgiverPeriodQuery,hviServiceQueryCriteria,caregiverageQuery,false,true));
                else if(indicatorCode.equalsIgnoreCase("cgsergradrp"))
                list.add(getNoOfActiveCaregiversServedAndWithdrawnFromProgram(ind.getIndicatorForNumberOfCaregiversServedAndGraduatedWithinTheReportPeriod().getIndicatorId(),ind.getIndicatorForNumberOfCaregiversServedAndGraduatedWithinTheReportPeriod().getIndicatorName(),hviQueryCriteria,cgiverPeriodQuery,hviServiceQueryCriteria,caregiverageQuery,ReportUtility.GRADUATED));
                else if(indicatorCode.equalsIgnoreCase("cgserltfurp"))
                list.add(getNoOfActiveCaregiversServedAndWithdrawnFromProgram(ind.getIndicatorForNumberOfCaregiversServedAndLostToFollowupWithinTheReportPeriod().getIndicatorId(),ind.getIndicatorForNumberOfCaregiversServedAndLostToFollowupWithinTheReportPeriod().getIndicatorName(),hviQueryCriteria,cgiverPeriodQuery,hviServiceQueryCriteria,caregiverageQuery,ReportUtility.LOST_TO_FOLLOWUP));
                else if(indicatorCode.equalsIgnoreCase("cgserdiedrp"))
                list.add(getNoOfActiveCaregiversServedAndWithdrawnFromProgram(ind.getIndicatorForNumberOfCaregiversServedAndDiedWithinTheReportPeriod().getIndicatorId(),ind.getIndicatorForNumberOfCaregiversServedAndDiedWithinTheReportPeriod().getIndicatorName(),hviQueryCriteria,cgiverPeriodQuery,hviServiceQueryCriteria,caregiverageQuery,ReportUtility.DIED));
                else if(indicatorCode.equalsIgnoreCase("cgsermigrrp"))
                list.add(getNoOfActiveCaregiversServedAndWithdrawnFromProgram(ind.getIndicatorForNumberOfCaregiversServedAndMigratedWithinTheReportPeriod().getIndicatorId(),ind.getIndicatorForNumberOfCaregiversServedAndMigratedWithinTheReportPeriod().getIndicatorName(),hviQueryCriteria,cgiverPeriodQuery,hviServiceQueryCriteria,caregiverageQuery,ReportUtility.MIGRATED));
                else if(indicatorCode.equalsIgnoreCase("cgsertranrp"))
                list.add(getNoOfActiveCaregiversServedAndWithdrawnFromProgram(ind.getIndicatorForNumberOfCaregiversServedAndTransferedWithinTheReportPeriod().getIndicatorId(),ind.getIndicatorForNumberOfCaregiversServedAndTransferedWithinTheReportPeriod().getIndicatorName(),hviQueryCriteria,cgiverPeriodQuery,hviServiceQueryCriteria,caregiverageQuery,ReportUtility.TRANSFERED));
                else if(indicatorCode.equalsIgnoreCase("cgsgrdrpage"))
                list.add(getNoOfActiveCaregiversServedAndWithdrawnFromProgramByAge(ind.getIndicatorForNumberOfCaregiversServedAndGraduatedWithinTheReportPeriod().getIndicatorId(),ind.getIndicatorForNumberOfCaregiversServedAndGraduatedWithinTheReportPeriod().getIndicatorName(),hviQueryCriteria,cgiverPeriodQuery,hviServiceQueryCriteria,caregiverageQuery,ReportUtility.GRADUATED));
                else if(indicatorCode.equalsIgnoreCase("cgstrarpage"))
                list.add(getNoOfActiveCaregiversServedAndWithdrawnFromProgramByAge(ind.getIndicatorForNumberOfCaregiversServedAndTransferedWithinTheReportPeriod().getIndicatorId(),ind.getIndicatorForNumberOfCaregiversServedAndTransferedWithinTheReportPeriod().getIndicatorName(),hviQueryCriteria,cgiverPeriodQuery,hviServiceQueryCriteria,caregiverageQuery,ReportUtility.TRANSFERED));
                else if(indicatorCode.equalsIgnoreCase("cginactserv"))
                list.add(getNoOfActiveCaregiversServedAndWithdrawnFromProgram(ind.getIndicatorForNumberOfInactiveCaregiversServedInReportPeriod().getIndicatorId(),ind.getIndicatorForNumberOfInactiveCaregiversServedInReportPeriod().getIndicatorName(),hviQueryCriteria,cgiverPeriodQuery,hviServiceQueryCriteria,caregiverageQuery,NomisConstant.INACTIVE));
                else if(indicatorCode.equalsIgnoreCase("cgexitnogra"))
                list.add(getNumberOfCaregiversExitedWithoutGraduation(indicatorCode,indicatorName,hviQueryCriteria,cgiverPeriodQuery,hviServiceQueryCriteria,caregiverageQuery));
                else if(indicatorCode.equalsIgnoreCase("cgexnograge"))
                list.add(getNumberOfCaregiversExitedWithoutGraduationByAge(indicatorCode,indicatorName,hviQueryCriteria,cgiverPeriodQuery,hviServiceQueryCriteria,caregiverageQuery));
                else if(indicatorCode.equalsIgnoreCase("cgnenrolled"))
                list.add(getNoOfCaregiversNewlyEnrolled(ind,additionalQueryCriteria,cgiverPeriodQuery));
                else if(indicatorCode.equalsIgnoreCase("cgcenrolled"))
                list.add(getNoOfCaregiversCurrentlyEnrolled(ind,additionalQueryCriteria,cgiverPeriodQuery));
                else if(indicatorCode.equalsIgnoreCase("cgeenrolled"))
                list.add(getNoOfCaregiversEverEnrolled(ind,additionalQueryCriteria,cgiverPeriodQuery));
                
                else if(indicatorCode.equalsIgnoreCase("cgivehivacc"))
                {
                    List mainList=getNoOfHouseholdsSupportedToAccessHIVCare("Number of caregivers/household heads supported to access HIV services",additionalQueryCriteria,dateArray,indicatorCode);
                    //mainList.add(29);
                    list.add(mainList);
                    
                }
                else if(indicatorCode.equalsIgnoreCase("cggraduated"))
                list.add(getNumberOfCaregiversWithdrawn(ind.getIndicatorForNumberOfCaregiversGraduated().getIndicatorName(),additionalQueryCriteria,withdrawalPeriodQuery,indicatorCode,ReportUtility.GRADUATED));
                else if(indicatorCode.equalsIgnoreCase("cgknowndeat"))
                list.add(getNumberOfCaregiversWithdrawn(ind.getIndicatorForNumberOfCaregiversWithdrawnDueToKnownDeath().getIndicatorName(),additionalQueryCriteria,withdrawalPeriodQuery,indicatorCode,"Known death"));
                else if(indicatorCode.equalsIgnoreCase("cgmigrated1"))
                list.add(getNumberOfCaregiversWithdrawn(ind.getIndicatorForNumberOfCaregiversWithdrawnDueToMigration().getIndicatorName(),additionalQueryCriteria,withdrawalPeriodQuery,indicatorCode,"migrated"));
                else if(indicatorCode.equalsIgnoreCase("cglosttofup"))
                list.add(getNumberOfCaregiversWithdrawn(ind.getIndicatorForNumberOfCaregiversWithdrawnDueToLostToFollowup().getIndicatorName(),additionalQueryCriteria,withdrawalPeriodQuery,indicatorCode,"Loss to follow-up"));
                else if(indicatorCode.equalsIgnoreCase("cgtransferd"))
                list.add(getNumberOfCaregiversWithdrawn(ind.getIndicatorForNumberOfCaregiversWithdrawnDueToTransfer().getIndicatorName(),additionalQueryCriteria,withdrawalPeriodQuery,indicatorCode,"transfer"));
                else if(indicatorCode.equalsIgnoreCase("cginactivrp"))
                list.add(getNumberOfCaregiversWithdrawn(ind.getIndicatorForNumberOfCaregiversDeclaredInactive().getIndicatorName(),additionalQueryCriteria,withdrawalPeriodQuery,indicatorCode,NomisConstant.INACTIVE));
                
                else if(indicatorCode.equalsIgnoreCase("cgposcevulb"))
                list.add(getNumberOfHivPosCaregiverCurrentlyEnrolledBasedOnVulnerability(ind.getIndicatorForNumberOfHivPosCaregiversCurrentlyEnrolledThatAreFromVulnerableHouseholdsAtBaseline().getIndicatorId(),ind.getIndicatorForNumberOfHivPosCaregiversCurrentlyEnrolledThatAreFromVulnerableHouseholdsAtBaseline().getIndicatorName(),additionalQueryCriteria,NomisConstant.VULNERABLE_STARTVALUE,NomisConstant.VULNERABLE_ENDVALUE,true));
                else if(indicatorCode.equalsIgnoreCase("cgposcemrvb"))
                list.add(getNumberOfHivPosCaregiverCurrentlyEnrolledBasedOnVulnerability(ind.getIndicatorForNumberOfHivPosCaregiversCurrentlyEnrolledThatAreFromMoreVulnerableHouseholdsAtBaseline().getIndicatorId(),ind.getIndicatorForNumberOfHivPosCaregiversCurrentlyEnrolledThatAreFromMoreVulnerableHouseholdsAtBaseline().getIndicatorName(),additionalQueryCriteria,NomisConstant.MOREVULNERABLE_STARTVALUE,NomisConstant.MOREVULNERABLE_ENDVALUE,true));
                else if(indicatorCode.equalsIgnoreCase("cgposcemovb"))
                list.add(getNumberOfHivPosCaregiverCurrentlyEnrolledBasedOnVulnerability(ind.getIndicatorForNumberOfHivPosCaregiversCurrentlyEnrolledThatAreFromMostVulnerableHouseholdsAtBaseline().getIndicatorId(),ind.getIndicatorForNumberOfHivPosCaregiversCurrentlyEnrolledThatAreFromMostVulnerableHouseholdsAtBaseline().getIndicatorName(),additionalQueryCriteria,NomisConstant.MOSTVULNERABLE_STARTVALUE,NomisConstant.MOSTVULNERABLE_ENDVALUE,true));
                
                else if(indicatorCode.equalsIgnoreCase("cghivcencar"))
                list.add(getNoOfCaregiversEnrolledInCareOrOnART(ind.getIndicatorForNumberOfHIVPositiveCaregiversCurrentlyEnrolledInCare().getIndicatorName(),additionalQueryCriteria,indicatorCode,"Care",true));
                else if(indicatorCode.equalsIgnoreCase("cghivcenart"))
                list.add(getNoOfCaregiversEnrolledInCareOrOnART(ind.getIndicatorForNumberOfHIVPositiveCaregiversCurrentlyEnrolledOnART().getIndicatorName(),additionalQueryCriteria,indicatorCode,"ART",true));
                else if(indicatorCode.equalsIgnoreCase("cghivposeen"))
                list.add(getNoOfCaregiversEnrolledByHIVStatus(ind.getIndicatorForNumberOfHIVPositiveCaregiversEverEnrolled().getIndicatorName(),additionalQueryCriteria,indicatorCode,NomisConstant.HIV_POSITIVE,false));
                else if(indicatorCode.equalsIgnoreCase("cghivnegeen"))
                list.add(getNoOfCaregiversEnrolledByHIVStatus(ind.getIndicatorForNumberOfHIVNegativeCaregiversEverEnrolled().getIndicatorName(),additionalQueryCriteria,indicatorCode,NomisConstant.HIV_NEGATIVE,false));
                else if(indicatorCode.equalsIgnoreCase("cghivunkeen"))
                list.add(getNoOfCaregiversEnrolledByHIVStatus(ind.getIndicatorForNumberOfHIVUnknownCaregiversEverEnrolled().getIndicatorName(),additionalQueryCriteria,indicatorCode,NomisConstant.HIV_UNKNOWN,false));
                
                else if(indicatorCode.equalsIgnoreCase("cghivposcen"))
                list.add(getNoOfCaregiversEnrolledByHIVStatus(ind.getIndicatorForNumberOfHIVPositiveCaregiversCurrentlyEnrolled().getIndicatorName(),additionalQueryCriteria,indicatorCode,NomisConstant.HIV_POSITIVE,true));
                else if(indicatorCode.equalsIgnoreCase("cghivnegcen"))
                list.add(getNoOfCaregiversEnrolledByHIVStatus(ind.getIndicatorForNumberOfHIVNegativeCaregiversCurrentlyEnrolled().getIndicatorName(),additionalQueryCriteria,indicatorCode,NomisConstant.HIV_NEGATIVE,true));
                else if(indicatorCode.equalsIgnoreCase("cghivunkcen"))
                list.add(getNoOfCaregiversEnrolledByHIVStatus(ind.getIndicatorForNumberOfHIVUnknownCaregiversCurrentlyEnrolled().getIndicatorName(),additionalQueryCriteria,indicatorCode,NomisConstant.HIV_UNKNOWN,true));
                
                else if(indicatorCode.equalsIgnoreCase("vccmuaclt11"))
                list.add(getNumberOfSeverelyMalnourishedOvc(ind.getIndicatorForNumberOfSeverelyMalnourishedOvcCurrently().getIndicatorName(),additionalQueryCriteria,startDate,endDate,indicatorCode,true,false,false));
                else if(indicatorCode.equalsIgnoreCase("vccmuac1112"))
                list.add(getNumberOfModeratelyNourishedOvc(ind.getIndicatorForNumberOfModeratelyNourishedOvcCurrently().getIndicatorName(),additionalQueryCriteria,startDate,endDate,indicatorCode,true,false,false));
                else if(indicatorCode.equalsIgnoreCase("vccmuacgt12"))
                list.add(getNumberOfWellNourishedOvc(ind.getIndicatorForNumberOfWellNourishedOvcCurrently().getIndicatorName(),additionalQueryCriteria,startDate,endDate,indicatorCode,true,false,false));
                
                else if(indicatorCode.equalsIgnoreCase("vcmuaclth11"))
                list.add(getNumberOfSeverelyMalnourishedOvc(ind.getIndicatorForNumberOfSeverelyMalnourishedOvcAtBaseline().getIndicatorName(),additionalQueryCriteria,startDate,endDate,indicatorCode,false,true,false));
                else if(indicatorCode.equalsIgnoreCase("vcmua11to12"))
                list.add(getNumberOfModeratelyNourishedOvc(ind.getIndicatorForNumberOfModeratelyNourishedOvcAtBaseline().getIndicatorName(),additionalQueryCriteria,startDate,endDate,indicatorCode,false,true,false));
                else if(indicatorCode.equalsIgnoreCase("vcmuacgth12"))
                list.add(getNumberOfWellNourishedOvc(ind.getIndicatorForNumberOfWellNourishedOvcAtBaseline().getIndicatorName(),additionalQueryCriteria,startDate,endDate,indicatorCode,false,true,false));
                
                else if(indicatorCode.equalsIgnoreCase("vcbmi25to29"))
                list.add(getNumberOfOvcWhoAreOverweightAtBaseline(ind.getIndicatorForNumberOfSeverelyMalnourishedOvcAtBaseline().getIndicatorName(),additionalQueryCriteria,startDate,endDate,indicatorCode));
                else if(indicatorCode.equalsIgnoreCase("vcbmi30to40"))
                list.add(getNumberOfOvcThatAreObesseAtBaseline(ind.getIndicatorForNumberOfModeratelyNourishedOvcAtBaseline().getIndicatorName(),additionalQueryCriteria,startDate,endDate,indicatorCode));
                else if(indicatorCode.equalsIgnoreCase("vcwtbmigt40"))
                list.add(getNumberOfOvcThatAreMorbidityObesseAtBaseline(ind.getIndicatorForNumberOfWellNourishedOvcAtBaseline().getIndicatorName(),additionalQueryCriteria,startDate,endDate,indicatorCode));
                else if(indicatorCode.equalsIgnoreCase("vcscrnfortb"))
                list.add(getNumberOfOvcScreenedForTB(ind.getIndicatorForNumberOfOvcScreenedForTB().getIndicatorName(),additionalQueryCriteria,dateArray,indicatorCode));
                
                
                else if(indicatorCode.equalsIgnoreCase("vc1scorimpr"))
                list.add(getTotalNoOfOvcThatShowedAtleastOneScoreImprovement(ind.getIndicatorForNumberOfOvcThatShowedAtleastOneScoreImprovement().getIndicatorName(),additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,indicatorCode));
                else if(indicatorCode.equalsIgnoreCase("vctestedhiv"))
                list.add(getNoOfOvcTestedForHIV(ind.getIndicatorForNumberOfOvcTestedForHIV().getIndicatorName(),additionalQueryCriteria,startDate,endDate,ageQuery,indicatorCode));
                else if(indicatorCode.equalsIgnoreCase("vcrefforhiv"))
                list.add(getNoOfOvcTestedForHIV(ind.getIndicatorForNumberOfOvcTestedForHIV().getIndicatorName(),additionalQueryCriteria,startDate,endDate,ageQuery,indicatorCode));
                //This is newly tested positive within the report period, whether enrolled on treatment or not
                else if(indicatorCode.equalsIgnoreCase("vchivposser"))
                list.add(getNoOfHIVPositiveOvcIdentifiedAndServedWithinTheReportPeriod(indicatorCode,indicatorName,additionalQueryCriteria,ageQuery,startDate,endDate,false,false));
                //This is newly tested positive and enrolled on treatment. Last variable is true
                /*else if(indicatorCode.equalsIgnoreCase("vchivposart"))
                list.add(getNoOfHIVPositiveOvcIdentifiedAndServedWithinTheReportPeriod(indicatorCode,indicatorName,additionalQueryCriteria,ageQuery,startDate,endDate,false,true));*/
                
                else if(indicatorCode.equalsIgnoreCase("vchivposide"))
                list.add(getNoOfOvcHIVPositiveOvcIdentifiedWithinTheReportPeriod(indicatorCode,indicatorName,additionalQueryCriteria,ageQuery,startDate,endDate,false));
                else if(indicatorCode.equalsIgnoreCase("vccurhivpos"))
                list.add(getNoOfOvcThatAreHIVPositive(ind.getIndicatorForNumberOfHivPositiveOvc().getIndicatorName(),additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,indicatorCode));
                else if(indicatorCode.equalsIgnoreCase("vchivillmem"))
                list.add(getNumberOfOvcEnrolledFromHouseholdsWithChronicallyIllMembers(ind.getIndicatorForNumberOfOvcEnrolledFromHouseholdsWithChronicallyIllMembers().getIndicatorName(),additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,indicatorCode));
                else if(indicatorCode.equalsIgnoreCase("vchivreferr"))
                list.add(getNumberOfOvcReferredForHIVCare(ind.getIndicatorForNumberOfOvcReferredForHIVCare().getIndicatorName(),additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,indicatorCode));
                
            }
        }     
        return list;
    }
    
    public List getSummaryStaticsCountWithoutGenderDisaggregation(String[] params,String[] selectedIndicators)
    {
        IndicatorDictionary ind=new IndicatorDictionary();
        List list=new ArrayList();
        String additionalQueryCriteria=util.getSummStatQueryCriteria(params);
        String[] hviParams=getHVIParams(params);
        String[] periodParams={params[7], params[8], params[9],params[10]};
        String startDate=util.getStartDate(periodParams);
        String endDate=util.getEndDate(periodParams);
        withdrawalQueryWithinReportPeriod=util.getDateQueryForWithdrawalWithinReportPeriod(startDate, endDate);
        withdrawalServicePeriodQuery=util.getWithdrawalServicePeriodCriteria(startDate);
        String hviQueryCriteria=util.getHVIReportCriteria(hviParams);
        String hviQueryCriteriaWithoutPeriod=util.getHVIOrgUnitQuery(hviParams);
        String hviServiceQueryCriteria=util.getHVIServiceReportCriteria(hviParams);

        String hviPeriodQuery=" ";
        String withdrawalPeriodQuery=" ";
        
        if(!params[7].equalsIgnoreCase("all") && !params[8].equalsIgnoreCase("all") && !params[9].equalsIgnoreCase("all") && !params[10].equalsIgnoreCase("all"))
        {
            hviPeriodQuery=util.getHhePeriodCriteria(util.getStartDate(periodParams),util.getEndDate(periodParams));
            withdrawalPeriodQuery=util.getWithdrawalPeriodCriteria(util.getStartDate(periodParams),util.getEndDate(periodParams));
            
        }
        //String additionalEnrollmentQuery=util.getAdditionalEnrollmentQuery(params[7], params[8], params[9], params[10]);
        try
        {
            String indicatorCode=null;
            if(selectedIndicators !=null)
            {
                for(int i=0; i<selectedIndicators.length; i++)
                {
                    indicatorCode=selectedIndicators[i];
                    if(indicatorCode.equalsIgnoreCase("hhdEnrolled"))
                    list.add(getNoOfHouseholdsEnrolled(hviQueryCriteria,hviPeriodQuery,indicatorCode));

                    else if(indicatorCode.equalsIgnoreCase("hhdbasasses"))
                    list.add(getNoOfHouseholdsWithBaselineAssessment(ind.getIndicatorForNumberOfHouseholdsEverEnrolledWithBaselineAssessment().getIndicatorName(),hviQueryCriteriaWithoutPeriod,indicatorCode,false));
                    else if(indicatorCode.equalsIgnoreCase("hhdcebasass"))
                    list.add(getNoOfHouseholdsWithBaselineAssessment(ind.getIndicatorForNumberOfHouseholdsCurrentlyEnrolledWithBaselineAssessment().getIndicatorName(),hviQueryCriteriaWithoutPeriod,indicatorCode,true));
                    //list.add(getNoOfHouseholdsCurrentlyEnrolledWithBaselineAssessment(ind.getIndicatorForNumberOfHouseholdsCurrentlyEnrolledWithBaselineAssessment().getIndicatorName(),hviQueryCriteriaWithoutPeriod,indicatorCode));

                    else if(indicatorCode.equalsIgnoreCase("hhbassesper"))
                    list.add(getNoOfHouseholdsWithBaselineAssessment(ind.getIndicatorForNumberOfHouseholdsWithBaselineAssessmentWithinTheReprtPeriod().getIndicatorName(),hviQueryCriteria,indicatorCode,false));
                    else if(indicatorCode.equalsIgnoreCase("hhcebassper"))
                    list.add(getNoOfHouseholdsWithBaselineAssessment(ind.getIndicatorForNumberOfHouseholdsCurrentlyEnrolledWithBaselineAssessmentWithinTheReprtPeriod().getIndicatorName(),hviQueryCriteria,indicatorCode,true));
                    else if(indicatorCode.equalsIgnoreCase("hhcefupasse"))
                    list.add(getNoOfHouseholdsWithFollowupAssessment(ind.getIndicatorForNumberOfHouseholdsCurrentlyEnrolledWithFollowupAssessment().getIndicatorName(),hviQueryCriteria,indicatorCode,true));
                    else if(indicatorCode.equalsIgnoreCase("hheefupasse"))
                    list.add(getNoOfHouseholdsWithFollowupAssessment(ind.getIndicatorForNumberOfHouseholdsEverEnrolledWithFollowupAssessment().getIndicatorName(),hviQueryCriteria,indicatorCode,false));

                    else if(indicatorCode.equalsIgnoreCase("hhnenrolled"))
                    list.add(getNoOfHouseholdsNewlyEnrolled(ind.getIndicatorForNumberOfHouseholdsNewlyEnrolled().getIndicatorName(),additionalQueryCriteria,hviPeriodQuery,indicatorCode));
                    else if(indicatorCode.equalsIgnoreCase("hhcenrolled"))
                    list.add(getNoOfHouseholdsCurrentlyEnrolled(ind.getIndicatorForNumberOfHouseholdsCurrentlyEnrolled().getIndicatorName(),additionalQueryCriteria,hviPeriodQuery,indicatorCode));
                    else if(indicatorCode.equalsIgnoreCase("hheenrolled"))
                    list.add(getNoOfHouseholdsEverEnrolled(ind.getIndicatorForNumberOfHouseholdsEverEnrolled().getIndicatorName(),additionalQueryCriteria,hviPeriodQuery,indicatorCode));
                    else if(indicatorCode.equalsIgnoreCase("hhwithdrawn"))
                    list.add(getNumberOfHouseholdsWithdrawn(ind.getIndicatorForTotalNumberOfHouseholdsWithdrawnForTheReportingPeriod().getIndicatorName(),additionalQueryCriteria,withdrawalPeriodQuery,indicatorCode,null));
                    else if(indicatorCode.equalsIgnoreCase("hhgraduated"))
                    list.add(getNumberOfHouseholdsWithdrawn(ind.getIndicatorForNumberOfHouseholdsWithdrawnDueToGraduation().getIndicatorName(),additionalQueryCriteria,withdrawalPeriodQuery,indicatorCode,ReportUtility.GRADUATED));
                    else if(indicatorCode.equalsIgnoreCase("hhmigrated1"))
                    list.add(getNumberOfHouseholdsWithdrawn(ind.getIndicatorForNumberOfHouseholdsWithdrawnDueToMigration().getIndicatorName(),additionalQueryCriteria,withdrawalPeriodQuery,indicatorCode,NomisConstant.MIGRATED));
                    else if(indicatorCode.equalsIgnoreCase("hhlosttofup"))
                    list.add(getNumberOfHouseholdsWithdrawn(ind.getIndicatorForNumberOfHouseholdsWithdrawnDueToLostToFollowup().getIndicatorName(),additionalQueryCriteria,withdrawalPeriodQuery,indicatorCode,NomisConstant.LOST_TO_FOLLOWUP));
                    else if(indicatorCode.equalsIgnoreCase("hhtransferd"))
                    list.add(getNumberOfHouseholdsWithdrawn(ind.getIndicatorForNumberOfHouseholdsWithdrawnDueToTransfer().getIndicatorName(),additionalQueryCriteria,withdrawalPeriodQuery,indicatorCode,NomisConstant.TRANSFERED));
                    else if(indicatorCode.equalsIgnoreCase("hhinactivrp"))
                    list.add(getNumberOfHouseholdsWithdrawn(ind.getIndicatorForNumberOfHouseholdsDeclaredInactive().getIndicatorName(),additionalQueryCriteria,withdrawalPeriodQuery,indicatorCode,NomisConstant.INACTIVE));
                    else if(indicatorCode.equalsIgnoreCase("hhdservedrp"))
                    list.add(getNoOfHouseholdsServed(ind.getIndicatorForNumberOfHouseholdsServed().getIndicatorId(),ind.getIndicatorForNumberOfHouseholdsServed().getIndicatorName(),additionalQueryCriteria,hviServiceQueryCriteria,ageQuery,false));
                    else if(indicatorCode.equalsIgnoreCase("hhdserhesrp"))
                    list.add(getNoOfHouseholdsProvidedHES(ind.getIndicatorForNumberOfHouseholdsProvidedHES().getIndicatorId(),ind.getIndicatorForNumberOfHouseholdsProvidedHES().getIndicatorName(),additionalQueryCriteria,startDate,endDate,false));
                    else if(indicatorCode.equalsIgnoreCase("hhcevulbase"))
                    list.add(getNumberOfHouseholdsCurrentlyEnrolledBasedOnVulnerability(ind.getIndicatorForNumberOfHouseholdsCurrentlyEnrolledThatAreVulnerableAtBaseline().getIndicatorId(),ind.getIndicatorForNumberOfHouseholdsCurrentlyEnrolledThatAreVulnerableAtBaseline().getIndicatorName(),additionalQueryCriteria,NomisConstant.VULNERABLE_STARTVALUE,NomisConstant.VULNERABLE_ENDVALUE,true));
                    else if(indicatorCode.equalsIgnoreCase("hhcemrvulba"))
                    list.add(getNumberOfHouseholdsCurrentlyEnrolledBasedOnVulnerability(ind.getIndicatorForNumberOfHouseholdsCurrentlyEnrolledThatAreMoreVulnerableAtBaseline().getIndicatorId(),ind.getIndicatorForNumberOfHouseholdsCurrentlyEnrolledThatAreMoreVulnerableAtBaseline().getIndicatorName(),additionalQueryCriteria,NomisConstant.MOREVULNERABLE_STARTVALUE,NomisConstant.MOREVULNERABLE_ENDVALUE,true));
                    else if(indicatorCode.equalsIgnoreCase("hhcemtvulba"))
                    list.add(getNumberOfHouseholdsCurrentlyEnrolledBasedOnVulnerability(ind.getIndicatorForNumberOfHouseholdsCurrentlyEnrolledThatAreMostVulnerableAtBaseline().getIndicatorId(),ind.getIndicatorForNumberOfHouseholdsCurrentlyEnrolledThatAreMostVulnerableAtBaseline().getIndicatorName(),additionalQueryCriteria,NomisConstant.MOSTVULNERABLE_STARTVALUE,NomisConstant.MOSTVULNERABLE_ENDVALUE,true));
                }
            }
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
    public List getListOfOvcCurrentlyEnrolled(String sex,String additionalQueryCriteria,String enrollmentEndDateQuery,String ageQuery) throws Exception
    {
        OvcDao dao=new OvcDaoImpl();
        List mainlist=new ArrayList();
        if(enrollmentEndDateQuery !=null && !enrollmentEndDateQuery.equalsIgnoreCase(" ")) 
        enrollmentEndDateQuery=" and "+enrollmentEndDateQuery;
        additionalQueryCriteria=additionalQueryCriteria+ageQuery;
        //+enrollmentEndDateQuery
        //String queryPart= ageQuery+additionalQueryCriteria;//+" and ovc.ovcId not in (select withdrawal.ovcId from OvcWithdrawal withdrawal)";
        //String malesql=hhQueryPart+util.getGenderCriteria(maleGender)+" "+queryPart;
        //String femalesql=hhQueryPart+util.getGenderCriteria(femaleGender)+" "+queryPart;
        //String bothGendersql=hhQueryPart+" ovc.ovcId is not null "+queryPart;
        if(sex !=null)
        {
            if(sex.equalsIgnoreCase(maleGender))
            additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
            if(sex.equalsIgnoreCase(femaleGender))
            additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);   
        }
        //System.err.println("malesql is "+bothGendersql);
        List list=dao.getListOfOvcCurrentlyEnrolled(additionalQueryCriteria);
        //execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql);
        
        mainlist=getListOfDistinctOvc(list);
        
        System.err.println("getNoOfOvcCurrentlyEnrolled executed");
        return mainlist;
    }
    public List getListOfOvcEverEnrolled(String sex,String additionalQueryCriteria,String enrollmentEndDateQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        if(enrollmentEndDateQuery !=null && !enrollmentEndDateQuery.equalsIgnoreCase(" ")) 
        enrollmentEndDateQuery=" and "+enrollmentEndDateQuery;
        String queryPart= ageQuery+enrollmentEndDateQuery+additionalQueryCriteria;
        String malesql=hhQueryPart+util.getGenderCriteria(maleGender)+" "+queryPart;
        String femalesql=hhQueryPart+util.getGenderCriteria(femaleGender)+" "+queryPart;
        String bothGendersql=hhQueryPart+" ovc.ovcId is not null "+queryPart;
        System.err.println("malesql is "+bothGendersql);
        List list=execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql);
        
        mainlist=getListOfOvc(list);
        
        System.err.println("getListOfOvcEverEnrolled executed");
        return mainlist;
    }
    public List getListOfOvcServed(String sex,String additionalQueryCriteria,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();//hhOvcAndServiceQueryPart
        if(!additionalServiceQuery.equals(" ") && !additionalServiceQuery.trim().startsWith("and"))
            additionalServiceQuery=" and "+additionalServiceQuery;
        //String serviceCriteria=" and ovc.ovcId in (select distinct service.ovcId from OvcService service "+additionalServiceQuery+" )";
        String malesql="select distinct service.ovcId "+hhOvcAndServiceQueryPart+additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+ageQuery+additionalServiceQuery;
        String femalesql="select distinct service.ovcId "+hhOvcAndServiceQueryPart+additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender)+ageQuery+additionalServiceQuery;
        String bothGendersql="select distinct service.ovcId "+hhOvcAndServiceQueryPart+" and ovc.ovcId is not null "+additionalQueryCriteria+ageQuery+additionalServiceQuery;
        
        //System.err.println("bothGendersql in getListOfOvcServed is "+bothGendersql);
        List list=execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql);
        //System.err.println("list size in getListOfOvcServed is "+list.size());
        mainlist=getListOfOvcByOvcId(list);
           
        return mainlist;
    }
    private List getListOfOvcCurrentlyEnrolledAndServed(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();//hhOvcAndServiceQueryPart
        if(!additionalServiceQuery.equals(" "))
            additionalServiceQuery=" and "+additionalServiceQuery;
        additionalQueryCriteria=additionalQueryCriteria+" and "+rutil.getOvcCurrentlyEnrolledQuery();
        //String serviceCriteria=" and ovc.ovcId in (select distinct service.ovcId from OvcService service "+additionalServiceQuery+" )";
        String malesql="select distinct service.ovcId "+hhOvcAndServiceQueryPart+additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+ageQuery+additionalServiceQuery;
        String femalesql="select distinct service.ovcId "+hhOvcAndServiceQueryPart+additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender)+ageQuery+additionalServiceQuery;
        String bothGendersql="select distinct service.ovcId "+hhOvcAndServiceQueryPart+" and ovc.ovcId is not null "+additionalQueryCriteria+ageQuery+additionalServiceQuery;
        
        List list=execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql);
        mainlist=getListOfOvcByOvcId(list);
           
        return mainlist;
    }
    private List getListOfOvcWithdrawnButServedWithinTheReportPeriod(String sex,String additionalQueryCriteria,String additionalServiceQuery,String ageQuery,String reasonWithdrawal) throws Exception
    {
        List mainlist=new ArrayList();
        if(!additionalServiceQuery.equals(" "))
        additionalServiceQuery=" and "+additionalServiceQuery;
        
        additionalQueryCriteria=additionalQueryCriteria+" and "+rutil.getOvcWithdrawnQuery(reasonWithdrawal)+withdrawalServicePeriodQuery;
        String selectQuery="select distinct service.ovcId ";
        String malesql=selectQuery+withdrawalServiceCountQueryPart+additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+ageQuery+additionalServiceQuery;
        String femalesql=selectQuery+withdrawalServiceCountQueryPart+additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender)+ageQuery+additionalServiceQuery;
        String bothGendersql=selectQuery+withdrawalServiceCountQueryPart+" and ovc.ovcId is not null "+additionalQueryCriteria+ageQuery+additionalServiceQuery;
        System.err.println("malesql in getListOfOvcWithdrawnButServedWithinTheReportPeriod is "+malesql);
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
    public List getListOfHouseholdsWithBaselineAssessment(String additionalQueryCriteria, String hviPeriodQuery,boolean currentlyEnrolled) throws Exception
    {
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        List mainlist=new ArrayList();
        //getListOfHouseholdsWithBaselineAssessement(String additionalQuery,boolean currentlyEnrolled)
        mainlist.addAll(hhedao.getListOfHouseholdsWithBaselineAssessement(additionalQueryCriteria,currentlyEnrolled));
        return mainlist;
    }
    /*public List getListOfHouseholdsCurrentlyEnrolledWithBaselineAssessment(String additionalQueryCriteria, String hviPeriodQuery) throws Exception
    {
        HouseholdVulnerabilityAssessmentDao hvadao=new HouseholdVulnerabilityAssessmentDaoImpl();
        List mainlist=new ArrayList();
        mainlist.addAll(hvadao.getListOfHouseholdsCurrentlyEnrolledWithBaselineAssessement(additionalQueryCriteria));
        return mainlist;
    }*/
    public List getListOfHouseholdsWithFollowupAssessment(String additionalQueryCriteria, String hviPeriodQuery,boolean currentlyEnrolled) throws Exception
    {
        HouseholdFollowupAssessmentDao hhfadao=new HouseholdFollowupAssessmentDaoImpl();
        //System.err.println("lnslde getListOfHouseholdsWithFollowupAssessment "+additionalQueryCriteria);
        //HouseholdVulnerabilityAssessmentDao hvadao=new HouseholdVulnerabilityAssessmentDaoImpl();
        List mainlist=new ArrayList();
        mainlist.addAll(hhfadao.getListOfHouseholdsWithFollowupAssessement(additionalQueryCriteria,currentlyEnrolled));
        return mainlist;
    }
    private List getListOfHivPosOvcServed(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery,boolean onART,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        String status="positive";
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        if(!additionalServiceQuery.equals(" "))
            additionalServiceQuery=" and "+additionalServiceQuery;
        String currentlyEnrolledQuery=" ";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
        String ovcHivQuery=util.getActiveHivStatusCriteria(status,NomisConstant.OVC_TYPE);
        if(onART)
        ovcHivQuery=util.getEnrolledOnARTQueryCriteria(NomisConstant.OVC_TYPE);
        additionalQueryCriteria=additionalQueryCriteria+currentlyEnrolledQuery;
        //String ovcHivQuery=util.getActiveHivStatusCriteria(status,NomisConstant.OVC_TYPE)+enrolledOnARTQuery;
        //String serviceCriteria=" and ovc.ovcId in (select distinct service.ovcId from OvcService service "+additionalServiceQuery+" )";
        String malesql="select distinct service.ovcId "+hhOvcAndServiceHivQueryPart+additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+ovcHivQuery+ageQuery+additionalServiceQuery;
        String femalesql="select distinct service.ovcId "+hhOvcAndServiceHivQueryPart+additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender)+ovcHivQuery+" "+ageQuery+additionalServiceQuery;
        String bothGendersql="select distinct service.ovcId "+hhOvcAndServiceHivQueryPart+additionalQueryCriteria+ovcHivQuery+ageQuery+additionalServiceQuery;
        //mainlist.addAll(execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql));
        //System.err.println("malesql is "+malesql);
        mainlist=getListOfOvcByOvcId(execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql));
        return mainlist;
    }
    private List getListOfHivNegOvcServed(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        String status="negative";
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        if(!additionalServiceQuery.equals(" "))
            additionalServiceQuery=" and "+additionalServiceQuery;
        String currentlyEnrolledQuery=" ";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
        additionalQueryCriteria=additionalQueryCriteria+currentlyEnrolledQuery;
        String ovcHivQuery=util.getActiveHivStatusCriteria(status,NomisConstant.OVC_TYPE);
        //String serviceCriteria=" and ovc.ovcId in (select distinct service.ovcId from OvcService service "+additionalServiceQuery+" )";
        String malesql="select distinct service.ovcId "+hhOvcAndServiceHivQueryPart+additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+ovcHivQuery+ageQuery+additionalServiceQuery;
        String femalesql="select distinct service.ovcId "+hhOvcAndServiceHivQueryPart+additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender)+ovcHivQuery+ageQuery+additionalServiceQuery;
        String bothGendersql="select distinct service.ovcId "+hhOvcAndServiceHivQueryPart+additionalQueryCriteria+ovcHivQuery+ageQuery+additionalServiceQuery;
        //System.err.println("malesql is "+malesql);
        mainlist=getListOfOvcByOvcId(execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql));
        return mainlist;
    }
    private List getListOfHivUnknownOvcServed(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        String status="unknown";
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        if(!additionalServiceQuery.equals(" "))
            additionalServiceQuery=" and "+additionalServiceQuery;
        String currentlyEnrolledQuery=" ";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
        additionalQueryCriteria=additionalQueryCriteria+currentlyEnrolledQuery;
        String ovcHivQuery=util.getActiveHivStatusCriteria(status,NomisConstant.OVC_TYPE);
        //String serviceCriteria=" and ovc.ovcId in (select distinct service.ovcId from OvcService service "+additionalServiceQuery+" )";
        String malesql="select distinct service.ovcId "+hhOvcAndServiceHivQueryPart+additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+ovcHivQuery+ageQuery+additionalServiceQuery;
        String femalesql="select distinct service.ovcId "+hhOvcAndServiceHivQueryPart+additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender)+ovcHivQuery+ageQuery+additionalServiceQuery;
        String bothGendersql="select distinct service.ovcId "+hhOvcAndServiceHivQueryPart+additionalQueryCriteria+ovcHivQuery+ageQuery+additionalServiceQuery;
        mainlist=getListOfOvcByOvcId(execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql));
        return mainlist;
    }
    
    
    private List getListOfNewlyEnrolledOvcWithoutServeRecords(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        List ovcList=new ArrayList();
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery)+util.getOvcWithdrawnFromProgramQuery("No");;
        if(!additionalServiceQuery.equals(" "))
        additionalServiceQuery=" and "+additionalServiceQuery;
        String malesql=hhQueryPart+util.getGenderCriteria(maleGender)+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery+withdrawalQuery;
        String femalesql=hhQueryPart+util.getGenderCriteria(femaleGender)+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery+withdrawalQuery;
        
        String bothGendersql=hhQueryPart+" ovc.ovcId is not null "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery+withdrawalQuery;
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
        //System.err.println("malesql in getListOfNewlyEnrolledOvcWithoutServeRecords is "+malesql);
        return ovcList;
    }
    
    public List getListOfHivPosOvc(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        String status="positive";
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        String ovcHivQuery=util.getActiveHivStatusCriteria(status,NomisConstant.OVC_TYPE);
        String malesql=hhHivQueryPart+ovcHivQuery+" and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String femalesql=hhHivQueryPart+ovcHivQuery+" and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String bothGendersql=hhHivQueryPart+ovcHivQuery+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        List list=execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql);
        mainlist=getListOfOvc(list);
        return mainlist;
    }
    public List getListHivNegOvc(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        String status="negative";
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        String ovcHivQuery=util.getActiveHivStatusCriteria(status,NomisConstant.OVC_TYPE);
        String malesql=hhHivQueryPart+ovcHivQuery+" and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String femalesql=hhHivQueryPart+ovcHivQuery+" and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String bothGendersql=hhHivQueryPart+ovcHivQuery+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        List list=execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql);
        mainlist=getListOfOvc(list);
        return mainlist;
    }
    public List getListOfHivUnknownOvc(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        String status="unknown";
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        String ovcHivQuery=util.getActiveHivStatusCriteria(status,NomisConstant.OVC_TYPE);
        String malesql=hhHivQueryPart+ovcHivQuery+" and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String femalesql=hhHivQueryPart+ovcHivQuery+" and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String bothGendersql=hhHivQueryPart+ovcHivQuery+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        List list=execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql);
        mainlist=getListOfOvc(list);
        return mainlist;
    }
    
    public List getListOfOvcCurrentlyEnrolledByHIVStatus(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery,String hivStatus,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        String currentlyEnrolledQuery=" ";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
        additionalQueryCriteria=additionalQueryCriteria+currentlyEnrolledQuery;
        //additionalQueryCriteria=additionalQueryCriteria+" and "+rutil.getOvcCurrentlyEnrolledQuery();
        //additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        String ovcHivQuery=util.getActiveHivStatusCriteria(hivStatus,NomisConstant.OVC_TYPE);
        String malesql=hhHivQueryPart+ovcHivQuery+" and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery;
        String femalesql=hhHivQueryPart+ovcHivQuery+" and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery;
        String bothGendersql=hhHivQueryPart+ovcHivQuery+" "+additionalQueryCriteria+ageQuery;
        List list=execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql);
        mainlist=getListOfOvc(list);
        return mainlist;
    }
    public List getListOfOvcEnrolledOrNotEnrolledInCare(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery,boolean currentlyEnrolled,boolean enrolledInCare,String periodEnrolledInCareQuery) throws Exception
    {
        List mainlist=new ArrayList();
        String enrolledInCareQuery=" and"+util.getHivPositiveEnrolledInCareStatusCriteria("No");
        if(enrolledInCare)
        {
            enrolledInCareQuery=" and"+util.getHivPositiveEnrolledInCareStatusCriteria("Yes");
            if(periodEnrolledInCareQuery !=null)
            enrolledInCareQuery=" and"+util.getHivPositiveEnrolledInCareStatusCriteria("Yes")+periodEnrolledInCareQuery;
        }
        String hivStatus="positive";
        if(currentlyEnrolled)
        additionalQueryCriteria=additionalQueryCriteria+" and "+rutil.getOvcCurrentlyEnrolledQuery();
        //additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        String ovcHivQuery=util.getActiveHivStatusCriteria(hivStatus,NomisConstant.OVC_TYPE);
        String malesql=hhHivQueryPart+ovcHivQuery+enrolledInCareQuery+" and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery;
        String femalesql=hhHivQueryPart+ovcHivQuery+enrolledInCareQuery+" and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery;
        String bothGendersql=hhHivQueryPart+ovcHivQuery+enrolledInCareQuery+" "+additionalQueryCriteria+ageQuery;
        List list=execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql);
        mainlist=getListOfOvc(list);
        return mainlist;
    }
    public List getListOfOvcCurrentlyEnrolledByHIVStatusAndReportPeriod(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery,String hivStatus) throws Exception
    {
        List mainlist=new ArrayList();
        additionalQueryCriteria=additionalQueryCriteria+" and "+rutil.getOvcCurrentlyEnrolledQuery();
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        String ovcHivQuery=util.getActiveHivStatusCriteria(hivStatus,NomisConstant.OVC_TYPE);
        String malesql=hhHivQueryPart+ovcHivQuery+" and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String femalesql=hhHivQueryPart+ovcHivQuery+" and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String bothGendersql=hhHivQueryPart+ovcHivQuery+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        List list=execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql);
        mainlist=getListOfOvc(list);
        return mainlist;
    }
    /*public List getListOfOvcOutOfSchool(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        String malesql=hhQueryPart+" ovc.schoolStatus='No' and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String femalesql=hhQueryPart+" ovc.schoolStatus='No' and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String bothGendersql=hhQueryPart+" ovc.schoolStatus='No' "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        List list=execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql);
        mainlist=getListOfOvc(list);
        return mainlist;
    }*/
    public List getListOfOvcCurrentlyInSchool(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        List list=new ArrayList();
        ChildSchoolStatusDao dao=new ChildSchoolStatusDaoImpl();
        //FollowupDao fudao=new FollowupDaoImpl();
        additionalQueryCriteria=additionalQueryCriteria+ageQuery;
        
        if(sex !=null)
        {
            if(sex.equalsIgnoreCase(maleGender))
            additionalQueryCriteria=additionalQueryCriteria+ageQuery+" and "+util.getGenderCriteria(maleGender);
            if(sex.equalsIgnoreCase(femaleGender))
            additionalQueryCriteria=additionalQueryCriteria+ageQuery+" and "+util.getGenderCriteria(femaleGender);   
        }
        mainlist=dao.getListOfOvcCurrentlyInSchool(additionalQueryCriteria,currentlyEnrolled);
        //mainlist=dao.getListOfOvcCurrentlyInSchool(additionalQueryCriteria,currentlyEnrolled);
        //list.addAll(dao.getListOfOvcCurrentlyInSchool(additionalQueryCriteria,currentlyEnrolled));
        //list.addAll(fudao.getListOfActiveOVCEnrolledInSchoolAtFollowup(additionalQueryCriteria));
        //mainlist=getListOfDistinctOvcFromQueryObject(list);
        return mainlist;
    }
    public List getListOfOvcCurrentlyOutOfSchoolSchool(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        //List list=new ArrayList();
        //OvcSchoolAttendanceDao dao=new OvcSchoolAttendanceDaoImpl();
        ChildSchoolStatusDao dao=new ChildSchoolStatusDaoImpl();
        additionalQueryCriteria=additionalQueryCriteria+ageQuery;
        
        if(sex !=null)
        {
            if(sex.equalsIgnoreCase(maleGender))
            additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
            if(sex.equalsIgnoreCase(femaleGender))
            additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);   
        }
        List list=dao.getListOfOvcCurrentlyOutOfSchool(additionalQueryCriteria,currentlyEnrolled);
        mainlist=getListOfDistinctOvc(list);
        return mainlist;
    }
    public List getListOvcWithoutBirthCertAtBaseline(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery) throws Exception
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
    public List getListOfOvcWithBirthCertAtBaseline(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        
        BirthRegistrationDao brdao=new BirthRegistrationDaoImpl();
        additionalQueryCriteria=additionalQueryCriteria+getAdditionalEnrollmentQuery(additionalEnrollmentQuery)+ageQuery;
        if(sex.equalsIgnoreCase("male"))
        mainlist=brdao.getListOfOvcWithBirthRegistrationAtBaseline(additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender),currentlyEnrolled);
        else if(sex.equalsIgnoreCase("female"))
        mainlist=brdao.getListOfOvcWithBirthRegistrationAtBaseline(additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender),currentlyEnrolled);
        else
        mainlist=brdao.getListOfOvcWithBirthRegistrationAtBaseline(additionalQueryCriteria,currentlyEnrolled);
        return mainlist;
    }
    public List getListOfOvcProvidedBirthCertService(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery,String pointOfUpdate,boolean currentlyEnrolled) throws Exception
    {
        BirthRegistrationDao brdao=new BirthRegistrationDaoImpl();
        List mainlist=new ArrayList();
        List ovcIdList=new ArrayList();
        String serviceDateQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        serviceDateQuery=serviceDateQuery.replace("ovc.dateEnrollment", "br.dateOfRegistration");
        additionalQueryCriteria=" "+additionalQueryCriteria+ageQuery+serviceDateQuery;
        String malesql=" and "+util.getGenderCriteria(maleGender)+additionalQueryCriteria;
        String femalesql=" and "+util.getGenderCriteria(femaleGender)+additionalQueryCriteria;
        if(sex.equalsIgnoreCase(maleGender))
        additionalQueryCriteria=malesql;
        else if(sex.equalsIgnoreCase(femaleGender))
        additionalQueryCriteria=femalesql;
        else
        additionalQueryCriteria=additionalQueryCriteria;
        ovcIdList=brdao.getListOfOvcWithBirthRegistrationByPointOfUpdate(additionalQueryCriteria, pointOfUpdate, currentlyEnrolled);
        mainlist.addAll(getListOfDistinctOvc(ovcIdList));
                
        System.err.println("getListOfOvcProvidedBirthCertService executed");
        return mainlist;
    }
    public List getListOfOvcWithBirthCertificate(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery,String pointOfUpdate,boolean currentlyEnrolled) throws Exception
    {
        BirthRegistrationDao brdao=new BirthRegistrationDaoImpl();
        List mainlist=new ArrayList();
        List ovcIdList=new ArrayList();
        
        additionalQueryCriteria=" "+additionalQueryCriteria+ageQuery;
        String malesql=" and "+util.getGenderCriteria(maleGender)+additionalQueryCriteria;
        String femalesql=" and "+util.getGenderCriteria(femaleGender)+additionalQueryCriteria;
        if(sex.equalsIgnoreCase(maleGender))
        additionalQueryCriteria=malesql;
        else if(sex.equalsIgnoreCase(femaleGender))
        additionalQueryCriteria=femalesql;
        else
        additionalQueryCriteria=additionalQueryCriteria;
        ovcIdList=brdao.getListOfOvcWithBirthRegistrationByPointOfUpdate(additionalQueryCriteria, pointOfUpdate, currentlyEnrolled);
        mainlist.addAll(getListOfDistinctOvc(ovcIdList));
                
        System.err.println("getListOfOvcProvidedBirthCertService executed");
        return mainlist;
    }
    public List getListOfOvcWithBirthCertAtFollowup(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        List ovcIdList=new ArrayList();
        String serviceBirthRegQuery=" and (service.serviceAccessed5 is null or UPPER(service.serviceAccessed5) not like '%BIRTH%')";
        String birthregQuery=" and UPPER(fu.updatedBirthCertStatus) ='YES' and "+birthCertQuery+serviceBirthRegQuery;
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        additionalEnrollmentQuery=additionalEnrollmentQuery.replace("ovc.dateEnrollment", "fu.dateOfSurvey");
        String malesql="select distinct fu.ovcId "+hhOvcServicefollowupQueryPart+" and ovc.birthCertificate='No' and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery+birthregQuery;
        String femalesql="select distinct fu.ovcId "+hhOvcServicefollowupQueryPart+" and ovc.birthCertificate='No' and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery+birthregQuery;
        String bothsql="select distinct fu.ovcId "+hhOvcServicefollowupQueryPart+" and ovc.birthCertificate='No' and "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery+birthregQuery;
        //System.err.println("malesql in getNoOfOvcWithBirthCertAtFollowup is "+malesql);
        //System.err.println("femalesql in getNoOfOvcWithBirthCertAtFollowup is "+femalesql);
       ovcIdList=execSummStatisticsQuery(sex,malesql,femalesql,bothsql);
       mainlist.addAll(getListOfOvcByOvcId(ovcIdList));
        System.err.println("getListOfOvcWithBirthCertAtFollowup executed");
        return mainlist;
    }
    public List getNumberOfCaregiversExitedWithoutGraduation(String indicatorCode,String indicatorName,String hviQueryCriteria,String cgiverPeriodQuery,String hviServiceQueryCriteria,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        List list=new ArrayList();
        int totalMale=0;
        int totalFemale=0;
        
        list=getNoOfActiveCaregiversServedAndWithdrawnFromProgram(indicatorCode,indicatorName,hviQueryCriteria,cgiverPeriodQuery,hviServiceQueryCriteria,ageQuery,ReportUtility.LOST_TO_FOLLOWUP);
        totalMale+=Integer.parseInt(list.get(1).toString());
        totalFemale+=Integer.parseInt(list.get(2).toString());
        list=getNoOfActiveCaregiversServedAndWithdrawnFromProgram(indicatorCode,indicatorName,hviQueryCriteria,cgiverPeriodQuery,hviServiceQueryCriteria,ageQuery,ReportUtility.MIGRATED);
        totalMale+=Integer.parseInt(list.get(1).toString());
        totalFemale+=Integer.parseInt(list.get(2).toString());
        list=getNoOfActiveCaregiversServedAndWithdrawnFromProgram(indicatorCode,indicatorName,hviQueryCriteria,cgiverPeriodQuery,hviServiceQueryCriteria,ageQuery,ReportUtility.AGED_OUT);
        totalMale+=Integer.parseInt(list.get(1).toString());
        totalFemale+=Integer.parseInt(list.get(2).toString());
        list=getNoOfActiveCaregiversServedAndWithdrawnFromProgram(indicatorCode,indicatorName,hviQueryCriteria,cgiverPeriodQuery,hviServiceQueryCriteria,ageQuery,ReportUtility.TRANSFERED);
        totalMale+=Integer.parseInt(list.get(1).toString());
        totalFemale+=Integer.parseInt(list.get(2).toString());
        list=getNoOfActiveCaregiversServedAndWithdrawnFromProgram(indicatorCode,indicatorName,hviQueryCriteria,cgiverPeriodQuery,hviServiceQueryCriteria,ageQuery,ReportUtility.DIED);
        totalMale+=Integer.parseInt(list.get(1).toString());
        totalFemale+=Integer.parseInt(list.get(2).toString());
        list=getNoOfActiveCaregiversServedAndWithdrawnFromProgram(indicatorCode,indicatorName,hviQueryCriteria,cgiverPeriodQuery,hviServiceQueryCriteria,ageQuery,NomisConstant.INACTIVE);
        totalMale+=Integer.parseInt(list.get(1).toString());
        totalFemale+=Integer.parseInt(list.get(2).toString());
        mainlist.add(indicatorName);
        mainlist.add(totalMale);
        mainlist.add(totalFemale);
        mainlist.add(totalMale+totalFemale);
        mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getNumberOfCaregiversExitedWithoutGraduationByAge(String indicatorCode,String indicatorName,String hviQueryCriteria,String cgiverPeriodQuery,String hviServiceQueryCriteria,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        List list=new ArrayList();
        int totalMale=0;
        int totalFemale=0;
        
        list=getNoOfActiveCaregiversServedAndWithdrawnFromProgramByAge(indicatorCode,indicatorName,hviQueryCriteria,cgiverPeriodQuery,hviServiceQueryCriteria,ageQuery,ReportUtility.LOST_TO_FOLLOWUP);
        totalMale+=Integer.parseInt(list.get(1).toString());
        totalFemale+=Integer.parseInt(list.get(2).toString());
        list=getNoOfActiveCaregiversServedAndWithdrawnFromProgramByAge(indicatorCode,indicatorName,hviQueryCriteria,cgiverPeriodQuery,hviServiceQueryCriteria,ageQuery,ReportUtility.MIGRATED);
        totalMale+=Integer.parseInt(list.get(1).toString());
        totalFemale+=Integer.parseInt(list.get(2).toString());
        list=getNoOfActiveCaregiversServedAndWithdrawnFromProgramByAge(indicatorCode,indicatorName,hviQueryCriteria,cgiverPeriodQuery,hviServiceQueryCriteria,ageQuery,ReportUtility.AGED_OUT);
        totalMale+=Integer.parseInt(list.get(1).toString());
        totalFemale+=Integer.parseInt(list.get(2).toString());
        list=getNoOfActiveCaregiversServedAndWithdrawnFromProgramByAge(indicatorCode,indicatorName,hviQueryCriteria,cgiverPeriodQuery,hviServiceQueryCriteria,ageQuery,ReportUtility.TRANSFERED);
        totalMale+=Integer.parseInt(list.get(1).toString());
        totalFemale+=Integer.parseInt(list.get(2).toString());
        list=getNoOfActiveCaregiversServedAndWithdrawnFromProgramByAge(indicatorCode,indicatorName,hviQueryCriteria,cgiverPeriodQuery,hviServiceQueryCriteria,ageQuery,ReportUtility.DIED);
        totalMale+=Integer.parseInt(list.get(1).toString());
        totalFemale+=Integer.parseInt(list.get(2).toString());
        list=getNoOfActiveCaregiversServedAndWithdrawnFromProgramByAge(indicatorCode,indicatorName,hviQueryCriteria,cgiverPeriodQuery,hviServiceQueryCriteria,ageQuery,NomisConstant.INACTIVE);
        totalMale+=Integer.parseInt(list.get(1).toString());
        totalFemale+=Integer.parseInt(list.get(2).toString());
        mainlist.add(indicatorName);
        mainlist.add(totalMale);
        mainlist.add(totalFemale);
        mainlist.add(totalMale+totalFemale);
        mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getNumberOfOvcExitedWithoutGraduation(String indicatorName,String additionalQueryCriteria,String additionalServiceQuery,String ageQuery,String indicatorCode) throws Exception
    {
        List mainlist=new ArrayList();
        List list=new ArrayList();
        int totalMale=0;
        int totalFemale=0;
       
        list=getNoOfOvcWithdrawnButServedWithinReportPeriod(indicatorName,additionalQueryCriteria,additionalQueryCriteria,additionalServiceQuery,ageQuery,indicatorCode,ReportUtility.LOST_TO_FOLLOWUP);
        totalMale+=Integer.parseInt(list.get(1).toString());
        totalFemale+=Integer.parseInt(list.get(2).toString());
        list=getNoOfOvcWithdrawnButServedWithinReportPeriod(indicatorName,additionalQueryCriteria,additionalQueryCriteria,additionalServiceQuery,ageQuery,indicatorCode,ReportUtility.MIGRATED);
        totalMale+=Integer.parseInt(list.get(1).toString());
        totalFemale+=Integer.parseInt(list.get(2).toString());
        list=getNoOfOvcWithdrawnButServedWithinReportPeriod(indicatorName,additionalQueryCriteria,additionalQueryCriteria,additionalServiceQuery,ageQuery,indicatorCode,ReportUtility.AGED_OUT);
        totalMale+=Integer.parseInt(list.get(1).toString());
        totalFemale+=Integer.parseInt(list.get(2).toString());
        list=getNoOfOvcWithdrawnButServedWithinReportPeriod(indicatorName,additionalQueryCriteria,additionalQueryCriteria,additionalServiceQuery,ageQuery,indicatorCode,ReportUtility.TRANSFERED);
        totalMale+=Integer.parseInt(list.get(1).toString());
        totalFemale+=Integer.parseInt(list.get(2).toString());
        list=getNoOfOvcWithdrawnButServedWithinReportPeriod(indicatorName,additionalQueryCriteria,additionalQueryCriteria,additionalServiceQuery,ageQuery,indicatorCode,ReportUtility.DIED);
        totalMale+=Integer.parseInt(list.get(1).toString());
        totalFemale+=Integer.parseInt(list.get(2).toString());
        list=getNoOfOvcWithdrawnButServedWithinReportPeriod(indicatorName,additionalQueryCriteria,additionalQueryCriteria,additionalServiceQuery,ageQuery,indicatorCode,NomisConstant.INACTIVE);
        totalMale+=Integer.parseInt(list.get(1).toString());
        totalFemale+=Integer.parseInt(list.get(2).toString());
        mainlist.add(indicatorName);
        mainlist.add(totalMale);
        mainlist.add(totalFemale);
        mainlist.add(totalMale+totalFemale);
        mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getNumberOfGraduatedHouseholdsWhoseOvcWereServedWithinReportingPeriod(String indicatorName,String additionalQueryCriteria,String ageQuery, String startDate,String endDate, String indicatorCode,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        additionalQueryCriteria=additionalQueryCriteria;
        OvcServiceDao dao=util.getOvcServiceDaoInstance();
        mainlist.add(indicatorName);
        String maleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
        String femaleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);
        int maleCount=dao.getNumberOfGraduatedHouseholdsWhoseOvcWereServedWithinReportingPeriod(maleAdditionalQuery, startDate, endDate,currentlyEnrolled);
        int femaleCount=dao.getNumberOfGraduatedHouseholdsWhoseOvcWereServedWithinReportingPeriod(femaleAdditionalQuery, startDate, endDate,currentlyEnrolled);
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getNumberOfActiveHouseholdsWhoseOvcWereServedWithinReportingPeriod(String indicatorName,String additionalQueryCriteria,String ageQuery, String startDate,String endDate, String indicatorCode,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        //additionalQueryCriteria=additionalQueryCriteria;
        OvcServiceDao dao=util.getOvcServiceDaoInstance();
        mainlist.add(indicatorName);
        mainlist.add(dao.getNumberOfHouseholdsWhoseOvcWereServedWithinReportingPeriod(additionalQueryCriteria+ageQuery, startDate, endDate,currentlyEnrolled));
        mainlist.add(indicatorCode);
        /*String maleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
        String femaleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);
        int maleCount=dao.getNumberOfHouseholdsWhoseOvcWereServedWithinReportingPeriod(maleAdditionalQuery, startDate, endDate,currentlyEnrolled);
        int femaleCount=dao.getNumberOfHouseholdsWhoseOvcWereServedWithinReportingPeriod(femaleAdditionalQuery, startDate, endDate,currentlyEnrolled);
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);*/
        
        return mainlist;
    }
    public List getNumberOfHouseholdsWhoseOvcWereServedWithinReportingPeriod(String indicatorName,String additionalQueryCriteria,String ageQuery, String startDate,String endDate,String fyStartDate,String fyEndDate, String indicatorCode,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        
        List activeList=getNumberOfActiveHouseholdsWhoseOvcWereServedWithinReportingPeriod(indicatorName,additionalQueryCriteria,ageQuery, startDate,endDate, indicatorCode,true);
        List graduatedList=getNumberOfGraduatedHouseholdsWhoseOvcWereServedWithinReportingPeriod(indicatorName,additionalQueryCriteria,ageQuery, fyStartDate,fyEndDate, indicatorCode,false);
        
        int activeTotal=Integer.parseInt(activeList.get(1).toString());
        int graduatedTotal=Integer.parseInt(graduatedList.get(3).toString());
        int total=activeTotal+graduatedTotal;
        mainlist.add(indicatorName);
        mainlist.add(total);
        mainlist.add(0);
        mainlist.add(total);
        System.err.println("total in getNumberOfHouseholdsWhoseOvcWereServedWithinReportingPeriod is "+total);
        return mainlist;
    }
    /*public List getNoOfHouseholdsCurrentlyEnrolled(String indicatorName,String additionalQueryCriteria, String hhePeriodQuery,String indicatorCode) throws Exception
    {
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl(); 
        List mainlist=new ArrayList();
        mainlist.add(indicatorName);
        mainlist.add(hhedao.getNumberOfHouseholdsCurrentlyEnrolled(additionalQueryCriteria));
        mainlist.add(indicatorCode);
        //System.err.println("Element at 2 is "+mainlist.get(2).toString());
        return mainlist;
    }*/
    public List getNumberOfHouseholdsThatAreAbleToPayForUnexpectedExpenses(String indicatorName,String additionalQueryCriteria,String ageQuery, String startDate,String endDate, String indicatorCode,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        additionalQueryCriteria=additionalQueryCriteria;//+ageQuery;
        CaregiverExpenditureAndSchoolAttendanceDao dao=new CaregiverExpenditureAndSchoolAttendanceDaoImpl();
        mainlist.add(indicatorName);
        mainlist.add(dao.getNumberOfHouseholdsThatAreAbleToPayForUnexpectedExpenses(additionalQueryCriteria, startDate, endDate,currentlyEnrolled));
        mainlist.add(indicatorCode);
        /*String maleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
        String femaleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);
        int maleCount=dao.getNumberOfHouseholdsThatAreAbleToPayForUnexpectedExpenses(maleAdditionalQuery, startDate, endDate,currentlyEnrolled);
        int femaleCount=dao.getNumberOfHouseholdsThatAreAbleToPayForUnexpectedExpenses(femaleAdditionalQuery, startDate, endDate,currentlyEnrolled);
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);*/
        
        return mainlist;
    }
    public List getListOfHouseholdsThatAreAbleToPayForUnexpectedExpenses(String sex,String additionalQueryCriteria, String ageQuery, String startDate,String endDate,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        additionalQueryCriteria=additionalQueryCriteria+ageQuery;
        CaregiverExpenditureAndSchoolAttendanceDao dao=new CaregiverExpenditureAndSchoolAttendanceDaoImpl();
        if(sex.equalsIgnoreCase(maleGender))
        additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
        else if(sex.equalsIgnoreCase(femaleGender))
        additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);
        else
        additionalQueryCriteria=additionalQueryCriteria;
        
        mainlist=dao.getListOfHouseholdsThatAreAbleToPayForUnexpectedExpenses(additionalQueryCriteria, startDate,endDate,currentlyEnrolled);
        
        return mainlist;
    }
    public List getNumberOfOvcRegularlyAttendingSchool(String indicatorCode,String indicatorName,String additionalQueryCriteria,String ageQuery, String startDate,String endDate,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        System.err.println("About to execute getNumberOfOvcRegularlyAttendingSchool");
        additionalQueryCriteria=additionalQueryCriteria+ageQuery;
        OvcSchoolAttendanceDao dao=new OvcSchoolAttendanceDaoImpl();
        mainlist.add(indicatorName);
        //System.err.println("additionalQueryCriteria in getNumberOfOvcRegularlyAttendingSchool is "+additionalQueryCriteria);
        String maleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
        String femaleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);
        int maleCount=dao.getNumberOfOvcRegularlyAttendingSchool(maleAdditionalQuery, startDate, endDate,currentlyEnrolled);
        int femaleCount=dao.getNumberOfOvcRegularlyAttendingSchool(femaleAdditionalQuery, startDate, endDate,currentlyEnrolled);
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        System.err.println("getNumberOfOvcRegularlyAttendingSchool executed");
        return mainlist;
    }
    public List getListOfOvcRegularlyAttendingSchool(String sex,String additionalQueryCriteria, String ageQuery, String startDate,String endDate,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        additionalQueryCriteria=additionalQueryCriteria+ageQuery;
        SchoolAttendanceDao dao=new SchoolAttendanceDaoImpl();
        if(sex.equalsIgnoreCase(maleGender))
        additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
        else if(sex.equalsIgnoreCase(femaleGender))
        additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);
        else
        additionalQueryCriteria=additionalQueryCriteria;
        
        mainlist=dao.getListOfOvcRegularlyAttendingSchool(additionalQueryCriteria, startDate,endDate,currentlyEnrolled);
        
        return mainlist;
    }
    public List getNumberOfHIVPositiveOvcWhoReportedAdherenceToTreatmentWithinTheReportPeriod(String indicatorName,String additionalQueryCriteria,String ageQuery, String startDate,String endDate, String indicatorCode,boolean skippedARV,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        additionalQueryCriteria=additionalQueryCriteria+ageQuery;
        CareAndSupportDao dao=new CareAndSupportDaoImpl();
        mainlist.add(indicatorName);
        String maleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
        String femaleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);
        int maleCount=dao.getNumberOfHIVPositiveOvcWhoReportedAdherenceToTreatmentWithinTheReportPeriod(maleAdditionalQuery, startDate, endDate,skippedARV,currentlyEnrolled);
        int femaleCount=dao.getNumberOfHIVPositiveOvcWhoReportedAdherenceToTreatmentWithinTheReportPeriod(femaleAdditionalQuery, startDate, endDate,skippedARV,currentlyEnrolled);
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getListOfHIVPositiveOvcWhoReportedAdherenceToTreatmentWithinTheReportPeriod(String sex,String additionalQueryCriteria, String ageQuery, String startDate,String endDate,boolean skippedARV,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        additionalQueryCriteria=additionalQueryCriteria+ageQuery;
        CareAndSupportDao dao=new CareAndSupportDaoImpl();
        if(sex.equalsIgnoreCase(maleGender))
        additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
        else if(sex.equalsIgnoreCase(femaleGender))
        additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);
        else
        additionalQueryCriteria=additionalQueryCriteria;
        
        mainlist=dao.getListOfHIVPositiveOvcWhoReportedAdherenceToTreatmentWithinTheReportPeriod(additionalQueryCriteria, startDate,endDate,skippedARV,currentlyEnrolled);
        
        return mainlist;
    }
    public List getNumberOfBeneficiariesNewlyEnrolled(String indicatorName,String additionalQueryCriteria,int startAge,int endAge,String startDate,String endDate,String hviQueryCriteria,String additionalServiceQuery, String cgiverPeriodQuery,String hviServiceQueryCriteria, String indicatorCode,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        List ovcNewlyEnrolledList=getNoOfOvcNewlyEnrolledAndServedWithinTheReportPeriod(indicatorName,additionalQueryCriteria,startAge,endAge,startDate,endDate,indicatorCode);
        
        List cgiversNewlyEnrolledList=new ArrayList();
        cgiversNewlyEnrolledList.add(indicatorCode);
        cgiversNewlyEnrolledList.add(0);
        cgiversNewlyEnrolledList.add(0);
        cgiversNewlyEnrolledList.add(0);//getNoOfCaregiversNewlyEnrolledAndServedByAge(indicatorCode,indicatorName,additionalQueryCriteria,startDate,endDate,startAge,endAge,false);
        if(startAge>17)
        {
            cgiversNewlyEnrolledList=getNoOfCaregiversNewlyEnrolledAndServedByAge(indicatorCode,indicatorName,additionalQueryCriteria,startDate,endDate,startAge,endAge,false);
        }
        //getNoOfCaregiversServed(indicatorCode,indicatorName,hviQueryCriteria,cgiverPeriodQuery,hviServiceQueryCriteria,ageQuery,true,false);
        //additionalQueryCriteria=additionalQueryCriteria+ageQuery;
        //CareAndSupportDao dao=new CareAndSupportDaoImpl();
        mainlist.add(indicatorName);
        int maleOvcCount=Integer.parseInt(ovcNewlyEnrolledList.get(1).toString());
        int femaleOvcCount=Integer.parseInt(ovcNewlyEnrolledList.get(2).toString());;
        int totalOvcCount=maleOvcCount+femaleOvcCount;
        int maleCgiverCount=Integer.parseInt(cgiversNewlyEnrolledList.get(1).toString());;
        int femaleCgiverCount=Integer.parseInt(cgiversNewlyEnrolledList.get(2).toString());;
        int totalCgiverCount=maleCgiverCount+femaleCgiverCount;
        int total=totalOvcCount+totalCgiverCount;
        mainlist.add(maleOvcCount+maleCgiverCount);
        mainlist.add(femaleOvcCount+femaleCgiverCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        return mainlist;
    }
    /*public List getNumberOfBeneficiariesNewlyEnrolled(String indicatorName,String additionalQueryCriteria,String hviQueryCriteria,String additionalServiceQuery, String cgiverPeriodQuery,String hviServiceQueryCriteria, String indicatorCode,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        getNoOfOvcNewlyEnrolledAndServedWithinTheReportPeriod(indicatorName,additionalQueryCriteria,startAge,endAge,startDate,endDate,indicatorCode));
        List ovcNewlyEnrolledList=getNoOfOvcNewlyEnrolledAndServedWithinTheReportPeriod(indicatorName,additionalQueryCriteria,additionalServiceQuery,ageQuery,indicatorCode);
        List cgiversNewlyEnrolledList=getNoOfCaregiversServed(indicatorCode,indicatorName,hviQueryCriteria,cgiverPeriodQuery,hviServiceQueryCriteria,ageQuery,true,false);
        additionalQueryCriteria=additionalQueryCriteria+ageQuery;
        CareAndSupportDao dao=new CareAndSupportDaoImpl();
        mainlist.add(indicatorName);
        int maleOvcCount=Integer.parseInt(ovcNewlyEnrolledList.get(1).toString());
        int femaleOvcCount=Integer.parseInt(ovcNewlyEnrolledList.get(2).toString());;
        int totalOvcCount=maleOvcCount+femaleOvcCount;
        int maleCgiverCount=Integer.parseInt(cgiversNewlyEnrolledList.get(1).toString());;
        int femaleCgiverCount=Integer.parseInt(cgiversNewlyEnrolledList.get(2).toString());;
        int totalCgiverCount=maleCgiverCount+femaleCgiverCount;
        int total=totalOvcCount+totalCgiverCount;
        mainlist.add(maleOvcCount+maleCgiverCount);
        mainlist.add(femaleOvcCount+femaleCgiverCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        return mainlist;
    }*/
    public List getNumberOfNewlyTestedHIVPositiveOvcLinkedToTreatmentWithinTheReportPeriod(String indicatorName,String additionalQueryCriteria,String ageQuery, String startDate,String endDate, String indicatorCode,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        additionalQueryCriteria=additionalQueryCriteria+ageQuery;
        CareAndSupportDao dao=new CareAndSupportDaoImpl();
        mainlist.add(indicatorName);
        String maleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
        String femaleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);
        int maleCount=dao.getNumberOfNewlyTestedHIVPositiveOvcLinkedToTrestmentWithinTheReportPeriod(maleAdditionalQuery, startDate, endDate,currentlyEnrolled);
        int femaleCount=dao.getNumberOfNewlyTestedHIVPositiveOvcLinkedToTrestmentWithinTheReportPeriod(femaleAdditionalQuery, startDate, endDate,currentlyEnrolled);
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getListOfHIVPositiveOvcIdentifiedAndServedWithinTheReportPeriod(String sex,String additionalQueryCriteria,String ageQuery,String startDate,String endDate,boolean currentlyEnrolled,boolean onTreatment) throws Exception
    {
        HivStatusUpdateDao hsudao=new HivStatusUpdateDaoImpl();
        List mainList=new ArrayList();
        
        if(sex.equalsIgnoreCase(maleGender))
        additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
        else if(sex.equalsIgnoreCase(femaleGender))
        additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);
        else
        additionalQueryCriteria=additionalQueryCriteria;
        
        mainList=hsudao.getListOfHivPositiveOvcIdentifiedAndServedWithinTheReportPeriod(additionalQueryCriteria,startDate,endDate,currentlyEnrolled,onTreatment);
        
       return mainList;
    }
    public List getListOfNewlyTestedHIVPositiveOvcLinkedToTreatmentWithinTheReportPeriod(String sex,String additionalQueryCriteria, String ageQuery, String startDate,String endDate,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        additionalQueryCriteria=additionalQueryCriteria+ageQuery;
        CareAndSupportDao dao=new CareAndSupportDaoImpl();
        if(sex.equalsIgnoreCase(maleGender))
        additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
        else if(sex.equalsIgnoreCase(femaleGender))
        additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);
        else
        additionalQueryCriteria=additionalQueryCriteria;
        
        mainlist=dao.getListOfNewlyTestedHIVPositiveOvcLinkedToTrestmentWithinTheReportPeriod(additionalQueryCriteria, startDate,endDate,currentlyEnrolled);
        
        return mainlist;
    }
    public List getNumberOfOvcUnknownOrNegativeHivStatusServedWithinReportPeriod(String indicatorCode,String indicatorName,String additionalQueryCriteria, String startDate,String endDate, int startAge,int endAge,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        additionalQueryCriteria=additionalQueryCriteria+ageQuery;
        OvcServiceDao sdao=util.getOvcServiceDaoInstance();
        
        mainlist.add(indicatorName);
        String maleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
        String femaleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);
        int maleCount=sdao.getNumberOfOvcUnknownOrNegativeHivStatusServedWithinReportPeriod(maleAdditionalQuery,startDate,endDate,startAge,endAge,currentlyEnrolled);
        int femaleCount=sdao.getNumberOfOvcUnknownOrNegativeHivStatusServedWithinReportPeriod(femaleAdditionalQuery,startDate,endDate,startAge,endAge,currentlyEnrolled);
        
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getNumberOfOvcTestedAndReceivedResult(String indicatorName,String additionalQueryCriteria,String ageQuery, String startDate,String endDate, String indicatorCode,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        additionalQueryCriteria=additionalQueryCriteria+ageQuery;
        //CareAndSupportDao dao=new CareAndSupportDaoImpl();
        HivStatusUpdateDao hsudao=new HivStatusUpdateDaoImpl();
        mainlist.add(indicatorName);
        String maleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
        String femaleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);
        int maleCount=hsudao.getNumberOfOvcTestedAndReceivedResult(maleAdditionalQuery,startDate,endDate,currentlyEnrolled);
        int femaleCount=hsudao.getNumberOfOvcTestedAndReceivedResult(femaleAdditionalQuery,startDate,endDate,currentlyEnrolled);
        
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getListOfOvcTestedAndReceivedResult(String sex,String additionalQueryCriteria, String ageQuery, String startDate,String endDate,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        additionalQueryCriteria=additionalQueryCriteria+ageQuery;
        HivStatusUpdateDao hsudao=new HivStatusUpdateDaoImpl();
        //CareAndSupportDao dao=new CareAndSupportDaoImpl();
        if(sex.equalsIgnoreCase(maleGender))
        additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
        else if(sex.equalsIgnoreCase(femaleGender))
        additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);
        else
        additionalQueryCriteria=additionalQueryCriteria;
        
        mainlist=hsudao.getListOfOvcTestedAndReceivedResult(additionalQueryCriteria, startDate,endDate,currentlyEnrolled);
        
        return mainlist;
    }
    public List getNumberOfAdolescentsProvidedWithHIVPreventionServicesWithinTheReportPeriod(String indicatorName,String additionalQueryCriteria, String startDate,String endDate, String indicatorCode,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        OvcServiceDao dao=new OvcServiceDaoImpl();
        mainlist.add(indicatorName);
        String maleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
        String femaleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=" and ovc.withdrawnFromProgram='No'";
        int maleCount=dao.getNumberOfAdolescentProvidedHivPreventionServices(maleAdditionalQuery+currentlyEnrolledQuery, startDate,endDate);
        int femaleCount=dao.getNumberOfAdolescentProvidedHivPreventionServices(femaleAdditionalQuery+currentlyEnrolledQuery, startDate,endDate);
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getListOfAdolescentsProvidedWithHIVPreventionServicesWithinTheReportPeriod(String sex,String additionalQueryCriteria, String startDate,String endDate,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        OvcServiceDao dao=new OvcServiceDaoImpl();
        
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=" and ovc.withdrawnFromProgram='No'";
        if(sex.equalsIgnoreCase(maleGender))
        additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
        else if(sex.equalsIgnoreCase(femaleGender))
        additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);
        else
        additionalQueryCriteria=additionalQueryCriteria;
        
        mainlist=dao.getListOfAdolescentProvidedHivPreventionServices(additionalQueryCriteria+currentlyEnrolledQuery, startDate,endDate);
        
        return mainlist;
    }
    public List getNumberOfOvcAbusedOrExploitedWithinTheReportPeriod(String indicatorName,String additionalQueryCriteria, String startDate,String endDate, String indicatorCode,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        OvcServiceDao dao=new OvcServiceDaoImpl();
        mainlist.add(indicatorName);
        String maleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
        String femaleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=" and ovc.withdrawnFromProgram='No'";
        int maleCount=dao.getNumberOfOvcAbusedOrExploited(maleAdditionalQuery+currentlyEnrolledQuery, startDate,endDate);
        int femaleCount=dao.getNumberOfOvcAbusedOrExploited(femaleAdditionalQuery+currentlyEnrolledQuery, startDate,endDate);
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getNumberOfOvcLinkedToGovtForPostViolenceServicesWithinTheReportPeriod(String indicatorName,String additionalQueryCriteria, String startDate,String endDate, String indicatorCode,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        OvcServiceDao dao=new OvcServiceDaoImpl();
        mainlist.add(indicatorName);
        String maleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
        String femaleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=" and ovc.withdrawnFromProgram='No'";
        int maleCount=dao.getNumberOfOvcLinkedToGovtForPostViolenceServices(maleAdditionalQuery+currentlyEnrolledQuery, startDate,endDate);
        int femaleCount=dao.getNumberOfOvcLinkedToGovtForPostViolenceServices(femaleAdditionalQuery+currentlyEnrolledQuery, startDate,endDate);
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getListOfOvcAbusedAndExploitedWithinTheReportPeriod(String sex,String additionalQueryCriteria, String startDate,String endDate,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        OvcServiceDao dao=new OvcServiceDaoImpl();
        
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=" and ovc.withdrawnFromProgram='No'";
        if(sex.equalsIgnoreCase(maleGender))
        additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
        else if(sex.equalsIgnoreCase(femaleGender))
        additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);
        else
        additionalQueryCriteria=additionalQueryCriteria;
        
        mainlist=dao.getListOfOvcAbusedOrExploited(additionalQueryCriteria+currentlyEnrolledQuery, startDate,endDate);
        
        return mainlist;
    }
    public List getListOfOvcLinkedToGovtForPostViolenceServicesWithinTheReportPeriod(String sex,String additionalQueryCriteria, String startDate,String endDate,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        OvcServiceDao dao=new OvcServiceDaoImpl();
        
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=" and ovc.withdrawnFromProgram='No'";
        if(sex.equalsIgnoreCase(maleGender))
        additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
        else if(sex.equalsIgnoreCase(femaleGender))
        additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);
        else
        additionalQueryCriteria=additionalQueryCriteria;
        
        mainlist=dao.getListOfOvcLinkedToGovtForPostViolenceServices(additionalQueryCriteria+currentlyEnrolledQuery, startDate,endDate);
        
        return mainlist;
    }
    public List getNumberOfOvcEnrolledInKidsClubWithinTheReportPeriod(String indicatorName,String additionalQueryCriteria, String startDate,String endDate, String indicatorCode,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        //String enrollmentPeriodQuery=util.getEnrollmentDateQuery(startDate, endDate);
        OvcServiceDao dao=new OvcServiceDaoImpl();
        mainlist.add(indicatorName);
        String maleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
        String femaleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=" and ovc.withdrawnFromProgram='No'";
        int maleCount=dao.getNumberOfOvcEnrolledInKidsClub(maleAdditionalQuery+currentlyEnrolledQuery, startDate,endDate);
        int femaleCount=dao.getNumberOfOvcEnrolledInKidsClub(femaleAdditionalQuery+currentlyEnrolledQuery, startDate,endDate);
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getListOfOvcEnrolledInKidsClubWithinTheReportPeriod(String sex,String additionalQueryCriteria, String startDate,String endDate,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        OvcServiceDao dao=new OvcServiceDaoImpl();
        
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=" and ovc.withdrawnFromProgram='No'";
        if(sex.equalsIgnoreCase(maleGender))
        additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
        else if(sex.equalsIgnoreCase(femaleGender))
        additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);
        else
        additionalQueryCriteria=additionalQueryCriteria;
        
        mainlist=dao.getListOfOvcEnrolledInKidsClub(additionalQueryCriteria+currentlyEnrolledQuery, startDate,endDate);
        
        return mainlist;
    }
    public List getNumberOfOvcCurrentlyEnrolledInHouseholdsWithBaselineAssessment(String indicatorName,String additionalQueryCriteria, String startDate,String endDate, String indicatorCode,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        String enrollmentPeriodQuery=util.getEnrollmentDateQuery(startDate, endDate);
        OvcDao dao=new OvcDaoImpl();
        mainlist.add(indicatorName);
        String maleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+enrollmentPeriodQuery;
        String femaleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender)+enrollmentPeriodQuery;
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=" and ovc.withdrawnFromProgram='No'";
        int maleCount=dao.getNumberOfOvcCurrentlyEnrolledInHouseholdsWithBaselineAssessment(maleAdditionalQuery+currentlyEnrolledQuery);
        int femaleCount=dao.getNumberOfOvcCurrentlyEnrolledInHouseholdsWithBaselineAssessment(femaleAdditionalQuery+currentlyEnrolledQuery);
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getListOfOvcCurrentlyEnrolledInHouseholdsWithBaselineAssessment(String sex,String additionalQueryCriteria, String startDate,String endDate,boolean currentlyEnrolled) throws Exception
    {
        String enrollmentPeriodQuery=util.getEnrollmentDateQuery(startDate, endDate);
        OvcDao dao=new OvcDaoImpl();
        List mainlist=new ArrayList();
        String currentlyEnrolledQuery="";
        
        if(currentlyEnrolled)
        currentlyEnrolledQuery=" and ovc.withdrawnFromProgram='No'";
        additionalQueryCriteria=additionalQueryCriteria+enrollmentPeriodQuery+currentlyEnrolledQuery;
        if(sex.equalsIgnoreCase(maleGender))
        additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
        else if(sex.equalsIgnoreCase(femaleGender))
        additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);
        else
        additionalQueryCriteria=additionalQueryCriteria;
        
        mainlist=dao.getListOfOvcCurrentlyEnrolledInHouseholdsWithBaselineAssessment(additionalQueryCriteria);
        //mainlist.add(indicatorCode);
        return mainlist;
    }
    
    public List getNoOfOvcThatHasNoHIVRiskAssessmentRecord(String indicatorName,String additionalQueryCriteria, String startDate,String endDate, String indicatorCode,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        
        OvcDao dao=new OvcDaoImpl();
        mainlist.add(indicatorName);
        String maleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
        String femaleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=" and ovc.withdrawnFromProgram='No'";
        int maleCount=dao.getNumberOfOvcThatHasNoHIVRiskAssessmentRecord(maleAdditionalQuery+currentlyEnrolledQuery);
        int femaleCount=dao.getNumberOfOvcThatHasNoHIVRiskAssessmentRecord(femaleAdditionalQuery+currentlyEnrolledQuery);
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getListOfOvcThatHasNoHIVRiskAssessmentRecord(String sex,String additionalQueryCriteria, String startDate,String endDate,boolean currentlyEnrolled) throws Exception
    {
        OvcDao dao=new OvcDaoImpl();
        List mainlist=new ArrayList();
        String currentlyEnrolledQuery="";
        
        if(currentlyEnrolled)
        currentlyEnrolledQuery=" and ovc.withdrawnFromProgram='No'";
        if(sex.equalsIgnoreCase(maleGender))
        additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
        else if(sex.equalsIgnoreCase(femaleGender))
        additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);
        else
        additionalQueryCriteria=additionalQueryCriteria;
        
        mainlist=dao.getListOfOvcThatHasNoHIVRiskAssessmentRecord(additionalQueryCriteria+currentlyEnrolledQuery);
        //mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getNoOfOvcAssessedForHivRiskByHivStatus(String indicatorName,String indicatorCode,String additionalQueryCriteria, String startDate,String endDate, String ageQuery,String hivStatus,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        
        HivRiskAssessmentChecklistDao dao=new HivRiskAssessmentChecklistDaoImpl();
        mainlist.add(indicatorName);
        String maleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+ageQuery;
        String femaleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender)+ageQuery;
        int maleCount=dao.getNoOfOvcAssessedForHivRiskByHivStatus(maleAdditionalQuery, startDate, endDate,hivStatus,currentlyEnrolled);
        int femaleCount=dao.getNoOfOvcAssessedForHivRiskByHivStatus(femaleAdditionalQuery, startDate, endDate,hivStatus,currentlyEnrolled);
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getNoOfOvcThatHasHIVRiskAssessmentDone(String indicatorName,String indicatorCode,String additionalQueryCriteria, String startDate,String endDate, String ageQuery,boolean atRisk,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        
        HivRiskAssessmentChecklistDao dao=new HivRiskAssessmentChecklistDaoImpl();
        mainlist.add(indicatorName);
        String maleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+ageQuery;
        String femaleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender)+ageQuery;
        int maleCount=dao.getNumberOfOvcThatHasBeenAssessedOnHivRiskAssessment(maleAdditionalQuery, startDate, endDate,atRisk,currentlyEnrolled);
        int femaleCount=dao.getNumberOfOvcThatHasBeenAssessedOnHivRiskAssessment(femaleAdditionalQuery, startDate, endDate,atRisk,currentlyEnrolled);
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getListOfOvcThatHasHIVRiskAssessmentDone(String sex,String additionalQueryCriteria, String startDate,String endDate,String ageQuery,boolean atRisk,boolean currentlyEnrolled) throws Exception
    {
        HivRiskAssessmentChecklistDao dao=new HivRiskAssessmentChecklistDaoImpl();
        List mainlist=new ArrayList();
        if(sex.equalsIgnoreCase(maleGender))
        additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+ageQuery;
        else if(sex.equalsIgnoreCase(femaleGender))
        additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender)+ageQuery;
        else
        additionalQueryCriteria=additionalQueryCriteria;
        mainlist=dao.getListOfOvcThatHasBeenAssessedOnHivRiskAssessment(additionalQueryCriteria, startDate, endDate,atRisk,currentlyEnrolled);
        //mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getNoOfOvcAssessedForHIVRiskAndServed(String indicatorName,String additionalQueryCriteria, String startDate,String endDate, String indicatorCode,String ageQuery,String hivStatus,boolean atRisk,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        
        HivRiskAssessmentChecklistDao dao=new HivRiskAssessmentChecklistDaoImpl();
        mainlist.add(indicatorName);
        String maleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+ageQuery;
        String femaleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender)+ageQuery;
        int maleCount=dao.getNoOfOvcAssessedForHIVRiskAndServed(maleAdditionalQuery, startDate, endDate,hivStatus,atRisk,currentlyEnrolled);
        int femaleCount=dao.getNoOfOvcAssessedForHIVRiskAndServed(femaleAdditionalQuery, startDate, endDate,hivStatus,atRisk,currentlyEnrolled);
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getListOfOvcAssessedForHIVRiskAndServed(String sex,String additionalQueryCriteria, String startDate,String endDate,String ageQuery,String hivStatus,boolean atRisk,boolean currentlyEnrolled) throws Exception
    {
        HivRiskAssessmentChecklistDao dao=new HivRiskAssessmentChecklistDaoImpl();
        List mainlist=new ArrayList();
        if(sex.equalsIgnoreCase(maleGender))
        additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+ageQuery;
        else if(sex.equalsIgnoreCase(femaleGender))
        additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender)+ageQuery;
        else
        additionalQueryCriteria=additionalQueryCriteria;
        mainlist=dao.getListOfOvcAssessedForHIVRiskAndServed(additionalQueryCriteria, startDate, endDate,hivStatus,atRisk,currentlyEnrolled);
        //mainlist.add(indicatorCode);
        return mainlist;
    }
    
    public List getNoOfHivUnknownOvcAssessedForHIVRiskAndServed(String indicatorName,String additionalQueryCriteria, String startDate,String endDate, String indicatorCode,String ageQuery,boolean atRisk,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        
        HivRiskAssessmentChecklistDao dao=new HivRiskAssessmentChecklistDaoImpl();
        mainlist.add(indicatorName);
        String maleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+ageQuery;
        String femaleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender)+ageQuery;
        int maleCount=dao.getNoOfHivUnknownOvcAssessedForHIVRiskAndServed(maleAdditionalQuery, startDate, endDate,atRisk,currentlyEnrolled);
        int femaleCount=dao.getNoOfHivUnknownOvcAssessedForHIVRiskAndServed(femaleAdditionalQuery, startDate, endDate,atRisk,currentlyEnrolled);
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        return mainlist;
    }
    /*public List getListOfHivUnknownOvcAssessedForHIVRiskAndServed(String sex,String additionalQueryCriteria, String startDate,String endDate,String ageQuery,String hivStatus,boolean atRisk,boolean currentlyEnrolled) throws Exception
    {
        HivRiskAssessmentChecklistDao dao=new HivRiskAssessmentChecklistDaoImpl();
        List mainlist=new ArrayList();
        if(sex.equalsIgnoreCase(maleGender))
        additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+ageQuery;
        else if(sex.equalsIgnoreCase(femaleGender))
        additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender)+ageQuery;
        else
        additionalQueryCriteria=additionalQueryCriteria;
        mainlist=dao.getListOfOvcAssessedForHIVRiskAndServed(additionalQueryCriteria, startDate, endDate,hivStatus,atRisk,currentlyEnrolled);
        //mainlist.add(indicatorCode);
        return mainlist;
    }*/
    public List getNoOfHivNegativeOvcAssessedForHIVRiskAndServed(String indicatorName,String additionalQueryCriteria, String startDate,String endDate, String indicatorCode,String ageQuery,boolean atRisk,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        
        HivRiskAssessmentChecklistDao dao=new HivRiskAssessmentChecklistDaoImpl();
        mainlist.add(indicatorName);
        String maleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+ageQuery;
        String femaleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender)+ageQuery;
        int maleCount=dao.getNoOfHivNegativeOvcAssessedForHIVRiskAndServed(maleAdditionalQuery, startDate, endDate,atRisk,currentlyEnrolled);
        int femaleCount=dao.getNoOfHivNegativeOvcAssessedForHIVRiskAndServed(femaleAdditionalQuery, startDate, endDate,atRisk,currentlyEnrolled);
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        return mainlist;
    }
    /*public List getListOfHivNegativeOvcAssessedForHIVRiskAndServed(String sex,String additionalQueryCriteria, String startDate,String endDate,String ageQuery,String hivStatus,boolean atRisk,boolean currentlyEnrolled) throws Exception
    {
        HivRiskAssessmentChecklistDao dao=new HivRiskAssessmentChecklistDaoImpl();
        List mainlist=new ArrayList();
        if(sex.equalsIgnoreCase(maleGender))
        additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+ageQuery;
        else if(sex.equalsIgnoreCase(femaleGender))
        additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender)+ageQuery;
        else
        additionalQueryCriteria=additionalQueryCriteria;
        mainlist=dao.getListOfOvcAssessedForHIVRiskAndServed(additionalQueryCriteria, startDate, endDate,hivStatus,atRisk,currentlyEnrolled);
        //mainlist.add(indicatorCode);
        return mainlist;
    }*/
    
    //getListOfOvcAssessedForHIVRiskAndServed(String additionalQuery,String startDate,String endDate,boolean atRisk,boolean currentlyEnrolled)
    public List getNumberOfOvcWhoseCaregiversKnowTheirHivStatus(String indicatorName,String additionalQueryCriteria, String startDate,String endDate, String indicatorCode,String hivStatusAnswer,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        
        HivRiskAssessmentChecklistDao dao=new HivRiskAssessmentChecklistDaoImpl();
        mainlist.add(indicatorName);
        String maleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
        String femaleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=" and ovc.withdrawnFromProgram='No'";
        int maleCount=dao.getNumberOfOvcWhoseCaregiversKnowTheirHivStatus(maleAdditionalQuery, startDate, endDate,currentlyEnrolledQuery,hivStatusAnswer);
        int femaleCount=dao.getNumberOfOvcWhoseCaregiversKnowTheirHivStatus(femaleAdditionalQuery, startDate, endDate,currentlyEnrolledQuery,hivStatusAnswer);
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getListOfOvcWhoseCaregiversKnowTheirHivStatus(String sex,String additionalQueryCriteria, String startDate,String endDate,String hivStatusAnswer,boolean currentlyEnrolled) throws Exception
    {
        HivRiskAssessmentChecklistDao dao=new HivRiskAssessmentChecklistDaoImpl();
        List mainlist=new ArrayList();
        String currentlyEnrolledQuery="";
        
        if(currentlyEnrolled)
        currentlyEnrolledQuery=" and ovc.withdrawnFromProgram='No'";
        if(sex.equalsIgnoreCase(maleGender))
        additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
        else if(sex.equalsIgnoreCase(femaleGender))
        additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);
                
        mainlist=dao.getListOfOvcWhoseCaregiversKnowTheirHivStatus(additionalQueryCriteria, startDate, endDate,currentlyEnrolledQuery,hivStatusAnswer);
        return mainlist;
    }
    public List getNumberOfOvcAssessedForHivRiskAndReferredForTesting(String indicatorName,String additionalQueryCriteria, String startDate,String endDate, String indicatorCode,String hivStatusAnswer,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        
        HivRiskAssessmentChecklistDao dao=new HivRiskAssessmentChecklistDaoImpl();
        mainlist.add(indicatorName);
        String maleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
        String femaleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);
        
        int maleCount=dao.getNumberOfOvcAssessedForHivRiskAndReferredForTesting(maleAdditionalQuery, startDate, endDate,currentlyEnrolled);
        int femaleCount=dao.getNumberOfOvcAssessedForHivRiskAndReferredForTesting(femaleAdditionalQuery, startDate, endDate,currentlyEnrolled);
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getListOfOvcAssessedForHivRiskAndReferredForTesting(String sex,String additionalQueryCriteria, String startDate,String endDate,String hivStatusAnswer,boolean currentlyEnrolled) throws Exception
    {
        HivRiskAssessmentChecklistDao dao=new HivRiskAssessmentChecklistDaoImpl();
        List mainlist=new ArrayList();
        if(sex.equalsIgnoreCase(maleGender))
        additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
        else if(sex.equalsIgnoreCase(femaleGender))
        additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);     
        mainlist=dao.getListOfOvcAssessedForHivRiskAndReferredForTesting(additionalQueryCriteria, startDate, endDate,currentlyEnrolled);
        return mainlist;
    }
    
    public List getNumberOfOvcNeverAssessedForHivRiskAssessment(String indicatorName,String additionalQueryCriteria, String startDate,String endDate, String indicatorCode,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        
        HivRiskAssessmentChecklistDao dao=new HivRiskAssessmentChecklistDaoImpl();
        mainlist.add(indicatorName);
        String maleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
        String femaleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=" and ovc.withdrawnFromProgram='No'";
        int maleCount=dao.getNumberOfOvcNeverAssessedForHivRiskAssessment(maleAdditionalQuery, startDate, endDate,currentlyEnrolledQuery);
        int femaleCount=dao.getNumberOfOvcNeverAssessedForHivRiskAssessment(femaleAdditionalQuery, startDate, endDate,currentlyEnrolledQuery);
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getListOfOvcNeverAssessedForHivRiskAssessment(String sex,String additionalQueryCriteria, String startDate,String endDate,boolean currentlyEnrolled) throws Exception
    {
        HivRiskAssessmentChecklistDao dao=new HivRiskAssessmentChecklistDaoImpl();
        List mainlist=new ArrayList();
        String currentlyEnrolledQuery="";
        
        if(currentlyEnrolled)
        currentlyEnrolledQuery=" and ovc.withdrawnFromProgram='No'";
        if(sex.equalsIgnoreCase(maleGender))
        additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
        else if(sex.equalsIgnoreCase(femaleGender))
        additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);
                
        mainlist=dao.getListOfOvcNeverAssessedForHivRiskAssessment(additionalQueryCriteria, startDate, endDate,currentlyEnrolledQuery);
        return mainlist;
    }
    public List getNumberOfOvcByHivStatusFromHivRiskAssessment(String indicatorName,String additionalQueryCriteria, String startDate,String endDate, String indicatorCode,String hivStatus,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        
        HivRiskAssessmentChecklistDao dao=new HivRiskAssessmentChecklistDaoImpl();
        mainlist.add(indicatorName);
        String maleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
        String femaleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=" and ovc.withdrawnFromProgram='No'";
        int maleCount=dao.getNumberOfOvcByHivStatusFromHivRiskAssessment(maleAdditionalQuery, startDate, endDate,currentlyEnrolledQuery,hivStatus);
        int femaleCount=dao.getNumberOfOvcByHivStatusFromHivRiskAssessment(femaleAdditionalQuery, startDate, endDate,currentlyEnrolledQuery,hivStatus);
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getListOfOvcByHivStatusFromHivRiskAssessment(String sex,String additionalQueryCriteria, String startDate,String endDate,String hivStatus,boolean currentlyEnrolled) throws Exception
    {
        HivRiskAssessmentChecklistDao dao=new HivRiskAssessmentChecklistDaoImpl();
        List mainlist=new ArrayList();
        String currentlyEnrolledQuery="";
        
        if(currentlyEnrolled)
        currentlyEnrolledQuery=" and ovc.withdrawnFromProgram='No'";
        if(sex.equalsIgnoreCase(maleGender))
        additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
        else if(sex.equalsIgnoreCase(femaleGender))
        additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);
                
        mainlist=dao.getListOfOvcByHivStatusFromHivRiskAssessment(additionalQueryCriteria, startDate, endDate,currentlyEnrolledQuery,hivStatus);
        return mainlist;
    }
    public List getNoOfOvcWhoseHouseholdProvidedEconomicStrentheningServices(String indicatorName,String additionalQueryCriteria, String startDate,String endDate, String indicatorCode,int startAge, int endAge,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        
        OvcDao dao=new OvcDaoImpl();
        mainlist.add(indicatorName);
        String maleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);//+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String femaleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);//+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=" and ovc.withdrawnFromProgram='No'";
        int maleCount=dao.getNumberOfOvcWhoseHouseholdsWereProvidedEconomicStrengthening(maleAdditionalQuery, startDate, endDate,currentlyEnrolledQuery,startAge,endAge);
        int femaleCount=dao.getNumberOfOvcWhoseHouseholdsWereProvidedEconomicStrengthening(femaleAdditionalQuery, startDate, endDate,currentlyEnrolledQuery,startAge,endAge);
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getListOfOvcWhoseHouseholdProvidedEconomicStrentheningServices(String sex,String additionalQueryCriteria, String startDate,String endDate,int startAge,int endAge,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=" and ovc.withdrawnFromProgram='No'";
        OvcDao dao=new OvcDaoImpl();
        if(sex.equalsIgnoreCase(maleGender))
        additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
        else if(sex.equalsIgnoreCase(femaleGender))
        additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);
        else
        additionalQueryCriteria=additionalQueryCriteria;
        
        mainlist=dao.getListOfOvcWhoseHouseholdsWereProvidedEconomicStrengthening(additionalQueryCriteria, startDate, endDate,currentlyEnrolledQuery,startAge,endAge);
        //mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getListOfAdolescentWhoseHouseholdProvidedEconomicStrentheningServices(String sex,String additionalQueryCriteria, String startDate,String endDate,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=getListOfOvcWhoseHouseholdProvidedEconomicStrentheningServices(sex,additionalQueryCriteria, startDate,endDate,10,19,currentlyEnrolled);//new ArrayList();
        /*String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=" and ovc.withdrawnFromProgram='No'";
        OvcDao dao=new OvcDaoImpl();
        if(sex.equalsIgnoreCase(maleGender))
        additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
        else if(sex.equalsIgnoreCase(femaleGender))
        additionalQueryCriteria=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);
        else
        additionalQueryCriteria=additionalQueryCriteria;
        
        mainlist=dao.getListOfAdolescentsWhoseHouseholdsWereProvidedEconomicStrengthening(additionalQueryCriteria, startDate, endDate,currentlyEnrolledQuery);
        //mainlist.add(indicatorCode);*/
        return mainlist;
    }
    public List getNoOfAdolescentWhoseHouseholdProvidedEconomicStrentheningServices(String indicatorName,String additionalQueryCriteria, String startDate,String endDate, String indicatorCode,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        
        OvcDao dao=new OvcDaoImpl();
        mainlist.add(indicatorName);
        String maleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);//+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String femaleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);//+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=" and ovc.withdrawnFromProgram='No'";
        int maleCount=dao.getNumberOfAdolescentsWhoseHouseholdsWereProvidedEconomicStrengthening(maleAdditionalQuery, startDate, endDate,currentlyEnrolledQuery);
        int femaleCount=dao.getNumberOfAdolescentsWhoseHouseholdsWereProvidedEconomicStrengthening(femaleAdditionalQuery, startDate, endDate,currentlyEnrolledQuery);
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getNoOfOvcEnrolled(String indicatorName,String additionalQueryCriteria, String additionalEnrollmentQuery,String ageQuery, String indicatorCode) throws Exception
    {
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        mainlist.add(indicatorName);
        //mainlist.add("No of new VC enrolled");
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        if(additionalEnrollmentQuery !=null)
        additionalEnrollmentQuery=additionalEnrollmentQuery.replace("and  and", "and");
        //System.err.println("additionalEnrollmentQuery is "+additionalEnrollmentQuery);
        String malesql=countQueryPart+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String femalesql=countQueryPart+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        System.err.println(malesql);
        maleList=execReportQuery(malesql);
        femaleList=execReportQuery(femalesql);
        int maleCount=Integer.parseInt(maleList.get(0).toString());
        int femaleCount=Integer.parseInt(femaleList.get(0).toString());
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getNoOfOvcCurrentlyEnrolled(String additionalQueryCriteria,String enrollmentEndDateQuery,String ageQuery,String indicatorCode)
    {
        OvcDao dao=new OvcDaoImpl();
        System.err.println("Executing getNoOfOvcCurrentlyEnrolled -----");
        List mainlist=new ArrayList();
        mainlist.add("No of <b>VC currently</b> enrolled");
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        int count=0;
        if(enrollmentEndDateQuery !=null && !enrollmentEndDateQuery.equalsIgnoreCase(" ") && !enrollmentEndDateQuery.trim().startsWith("and")) 
        enrollmentEndDateQuery=" and "+enrollmentEndDateQuery;
        String queryPart= ageQuery+additionalQueryCriteria;
        //String queryPart= ageQuery+enrollmentEndDateQuery+additionalQueryCriteria;//+" and ovc.ovcId not in (select withdrawal.ovcId from OvcWithdrawal withdrawal)";
        //String malesql=countQueryPart+util.getGenderCriteria(maleGender)+" "+queryPart;
        //String femalesql=countQueryPart+util.getGenderCriteria(femaleGender)+" "+queryPart;
        String malesql=" and "+util.getGenderCriteria(maleGender)+" "+queryPart;
        String femalesql=" and "+util.getGenderCriteria(femaleGender)+" "+queryPart;
        

        try
        {
            System.err.println("malesql is "+malesql);
            //maleList=execReportQuery(malesql);
            //femaleList=execReportQuery(femalesql);
            /*int maleWithdrawn=0;
            int femaleWithdrawn=0;
            List maleWithdrawnList=withdrawalDao.getListOfOvcWithdrawn(queryPart+" and "+util.getGenderCriteria(maleGender));
            if(maleWithdrawnList !=null)
            maleWithdrawn=maleWithdrawnList.size();
            List femaleWithdrawnList=withdrawalDao.getListOfOvcWithdrawn(queryPart+" and "+util.getGenderCriteria(femaleGender));
            if(femaleWithdrawnList !=null)
            femaleWithdrawn=femaleWithdrawnList.size();*/
            int maleCount=dao.getNumberOfOvcCurrentlyEnrolled(malesql);//Integer.parseInt(maleList.get(0).toString());
            int femaleCount=dao.getNumberOfOvcCurrentlyEnrolled(femalesql);//Integer.parseInt(femaleList.get(0).toString());
            //maleCount=maleCount-maleWithdrawn;
            //femaleCount=femaleCount-femaleWithdrawn;
            int total=maleCount+femaleCount;
            mainlist.add(maleCount);
            mainlist.add(femaleCount);
            mainlist.add(total);
            mainlist.add(indicatorCode);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
                    
        System.err.println("getNoOfOvcCurrentlyEnrolled executed");
        return mainlist;
    }
    public List getNoOfOvcEverEnrolled(String indicatorName,String additionalQueryCriteria,String enrollmentEndDateQuery,String ageQuery,String indicatorCode) throws Exception
    {
        System.err.println("Executing getNoOfOvcEverEnrolled -----");
        List mainlist=new ArrayList();
        mainlist.add(indicatorName);
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        if(enrollmentEndDateQuery !=null && !enrollmentEndDateQuery.equalsIgnoreCase(" ")) 
        enrollmentEndDateQuery=" and "+enrollmentEndDateQuery;
        //enrollmentEndDateQuery+
        String queryPart= ageQuery+additionalQueryCriteria;//+" and ovc.ovcId not in (select withdrawal.ovcId from OvcWithdrawal withdrawal)";
        String malesql=countQueryPart+util.getGenderCriteria(maleGender)+" "+queryPart;
        String femalesql=countQueryPart+util.getGenderCriteria(femaleGender)+" "+queryPart;

        maleList=execReportQuery(malesql);
        femaleList=execReportQuery(femalesql);
        
        int maleCount=Integer.parseInt(maleList.get(0).toString());
        int femaleCount=Integer.parseInt(femaleList.get(0).toString());
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
                    
        System.err.println("getNoOfOvcEverEnrolled executed");
        return mainlist;
    }
    public List getNoOfOvcServed(String indicatorName,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery,String indicatorCode) throws Exception
    {
        List mainlist=new ArrayList();
        System.err.println("executing No of VC provided with at least one service");
        if(!additionalServiceQuery.equals(" "))
            additionalServiceQuery=" and "+additionalServiceQuery;
       
       List maleList=new ArrayList();
        List femaleList=new ArrayList();
        mainlist.add(indicatorName);
        //mainlist.add("No of VC provided with at least one service");
        String malesql=serviceCountQueryPart+util.getGenderCriteria(maleGender)+ageQuery+" "+additionalQueryCriteria+additionalServiceQuery;
        String femalesql=serviceCountQueryPart+util.getGenderCriteria(femaleGender)+ageQuery+" "+additionalQueryCriteria+additionalServiceQuery;

        System.err.println("malesql in getNoOfOvcServed is "+malesql);
        maleList=execReportQuery(malesql);
        femaleList=execReportQuery(femalesql);
        int maleCount=Integer.parseInt(maleList.get(0).toString());
        int femaleCount=Integer.parseInt(femaleList.get(0).toString());
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getNoOfOvcNewlyEnrolledAndServedWithinTheReportPeriod(String indicatorName,String additionalQueryCriteria,int startAge,int endAge,String startDate,String endDate,String indicatorCode) throws Exception
    {
        List mainlist=new ArrayList();
        OvcServiceDao sdao=new OvcServiceDaoImpl();
        mainlist.add(indicatorName);
        int maleCount=sdao.getNoOfOvcNewlyEnrolledAndServedWithinTheReportPeriod(additionalQueryCriteria,"Male",startAge,endAge,startDate,endDate);
        int femaleCount=sdao.getNoOfOvcNewlyEnrolledAndServedWithinTheReportPeriod(additionalQueryCriteria,"Female",startAge,endAge,startDate,endDate);
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        return mainlist;
    }
    /*public List getNoOfOvcNewlyEnrolledAndServedWithinTheReportPeriod(String indicatorName,String additionalQueryCriteria,String additionalServiceQuery,String ageQuery,String indicatorCode) throws Exception
    {
        List mainlist=new ArrayList();
        OvcServiceDao sdao=new OvcServiceDaoImpl();
        getNoOfOvcNewlyEnrolledAndServedWithinTheReportPeriod(String additionalQuery,String sex,int startAge,int endAge,String startDate,String endDate);
        if(additionalServiceQuery !=null)
        {
            additionalServiceQuery=additionalServiceQuery.trim();
            if(!additionalServiceQuery.equals(" ") && !additionalServiceQuery.startsWith("and"))
                additionalServiceQuery=" and "+additionalServiceQuery;
            additionalQueryCriteria=additionalQueryCriteria;
        }
       String additionalEnrollmentQuery=additionalServiceQuery.replace("service.servicedate", "ovc.dateEnrollment");
       List maleList=new ArrayList();
        List femaleList=new ArrayList();
        mainlist.add(indicatorName);
        String malesql=serviceCountQueryPart+util.getGenderCriteria(maleGender)+ageQuery+" "+additionalQueryCriteria+additionalEnrollmentQuery+additionalServiceQuery;
        String femalesql=serviceCountQueryPart+util.getGenderCriteria(femaleGender)+ageQuery+" "+additionalQueryCriteria+additionalEnrollmentQuery+additionalServiceQuery;

        System.err.println("malesql in getNoOfOvcNewlyEnrolledAndServedWithinTheReportPeriod is "+malesql);
        maleList=execReportQuery(malesql);
        femaleList=execReportQuery(femalesql);
        int maleCount=Integer.parseInt(maleList.get(0).toString());
        int femaleCount=Integer.parseInt(femaleList.get(0).toString());
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        return mainlist;
    }*/
    private List getListOfOvcNewlyEnrolledAndServedAndServedWithinTheReportPeriod(String sex,String additionalQueryCriteria,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();//hhOvcAndServiceQueryPart
        if(!additionalServiceQuery.equals(" "))
            additionalServiceQuery=" and "+additionalServiceQuery;
        additionalQueryCriteria=additionalQueryCriteria;
        String additionalEnrollmentQuery=additionalServiceQuery.replace("service.servicedate", "ovc.dateEnrollment");
        //String serviceCriteria=" and ovc.ovcId in (select distinct service.ovcId from OvcService service "+additionalServiceQuery+" )";
        String malesql="select distinct service.ovcId "+hhOvcAndServiceQueryPart+additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+ageQuery+additionalEnrollmentQuery+additionalServiceQuery;
        String femalesql="select distinct service.ovcId "+hhOvcAndServiceQueryPart+additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender)+ageQuery+additionalEnrollmentQuery+additionalServiceQuery;
        String bothGendersql="select distinct service.ovcId "+hhOvcAndServiceQueryPart+" and ovc.ovcId is not null "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery+additionalServiceQuery;
        
        List list=execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql);
        mainlist=getListOfOvcByOvcId(list);
           
        return mainlist;
    }
    public List getNoOfOvcCurrentlyEnrolledAndServed(String indicatorName,String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery,String indicatorCode) throws Exception
    {
        List mainlist=new ArrayList();
        try
        {
        
            if(!additionalServiceQuery.equals(" "))
                additionalServiceQuery=" and "+additionalServiceQuery;
            additionalQueryCriteria=additionalQueryCriteria+" and "+rutil.getOvcCurrentlyEnrolledQuery();

            List maleList=new ArrayList();
            List femaleList=new ArrayList();
            mainlist.add(indicatorName);//+ageQuery ageQuery+
            String malesql=serviceCountQueryPart+util.getGenderCriteria(maleGender)+ageQuery+" "+additionalQueryCriteria+additionalServiceQuery;
            String femalesql=serviceCountQueryPart+util.getGenderCriteria(femaleGender)+ageQuery+" "+additionalQueryCriteria+additionalServiceQuery;

            System.err.println("malesql in getNoOfOvcCurrentlyEnrolledAndServed is "+malesql);
            System.err.println("additionalServiceQuery in getNoOfOvcCurrentlyEnrolledAndServed is "+additionalServiceQuery);
            maleList=execReportQuery(malesql);
            femaleList=execReportQuery(femalesql);
            int maleCount=Integer.parseInt(maleList.get(0).toString());
            int femaleCount=Integer.parseInt(femaleList.get(0).toString());
            int total=maleCount+femaleCount;
            mainlist.add(maleCount);
            mainlist.add(femaleCount);
            mainlist.add(total);
            mainlist.add(indicatorCode);
            System.err.println("getNoOfOvcCurrentlyEnrolledAndServed executed ");
        }
        catch(Exception ex)
        {
            NomisLogManager.logStackTrace(ex);
            ex.printStackTrace();
        }
        return mainlist;
    }
    public List getNoOfOvcWithdrawnButServedWithinReportPeriodByHivStatus(String indicatorName,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery,String indicatorCode,String reasonWithrawal,String hivStatus,boolean onART,String startDate,String endDate) throws Exception
    {
        OvcServiceDao sdao=new OvcServiceDaoImpl();
        List mainlist=new ArrayList();
        //getReportParameterTemplate(params);
        String withdrawalPeriodQuery=util.getWithdrawalServicePeriodCriteria(startDate);
        additionalQueryCriteria=additionalQueryCriteria+" and "+rutil.getOvcWithdrawnQuery(reasonWithrawal)+withdrawalPeriodQuery;
        mainlist.add(indicatorName);
        String malesql=" and "+util.getGenderCriteria(maleGender)+ageQuery+" "+additionalQueryCriteria;
        String femalesql=" and "+util.getGenderCriteria(femaleGender)+ageQuery+" "+additionalQueryCriteria;

        System.err.println("malesql in getNoOfOvcWithdrawnButServedWithinReportPeriodByHivStatus is "+malesql);
        
        int maleCount=sdao.getNumberOfOvcWithdrawnButServedWithinTheReportPeriodByHivStatus(malesql, hivStatus,onART, startDate, endDate);
        int femaleCount=sdao.getNumberOfOvcWithdrawnButServedWithinTheReportPeriodByHivStatus(femalesql, hivStatus,onART, startDate, endDate);
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        return mainlist;
    }
    private List getListOfOvcWithdrawnButServedWithinTheReportPeriodByHivStatus(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery,String reasonWithrawal,String hivStatus,boolean onART,String startDate,String endDate) throws Exception
    {
        List mainlist=new ArrayList();
        OvcServiceDao sdao=new OvcServiceDaoImpl();
        String query=" ";
        additionalQueryCriteria=additionalQueryCriteria+" and "+rutil.getOvcWithdrawnQuery(reasonWithrawal)+withdrawalServicePeriodQuery;
        if(sex.equalsIgnoreCase(maleGender))
        query=" and "+util.getGenderCriteria(maleGender)+ageQuery+" "+additionalQueryCriteria;
        else if(sex.equalsIgnoreCase(femaleGender))
        query=" and "+util.getGenderCriteria(femaleGender)+ageQuery+" "+additionalQueryCriteria;
        else
        query=ageQuery+" "+additionalQueryCriteria;
        System.err.println("query in getListOfOvcWithdrawnButServedWithinTheReportPeriodByHivStatus is "+query);
        
        mainlist=sdao.getListOfOvcWithdrawnButServedWithinTheReportPeriodByHivStatus(query, hivStatus,onART, startDate, endDate);
        //mainlist=getListOfOvcByOvcId(list);
           
        return mainlist;
    }
    public List getNoOfOvcWithdrawnButServedWithinReportPeriod(String indicatorName,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery,String indicatorCode,String reasonWithrawal) throws Exception
    {
        List mainlist=new ArrayList();
        if(!additionalServiceQuery.equals(" "))
            additionalServiceQuery=" and "+additionalServiceQuery;
        System.err.println("withdrawalServicePeriodQuery is "+withdrawalServicePeriodQuery);
        additionalQueryCriteria=additionalQueryCriteria+" and "+rutil.getOvcWithdrawnQuery(reasonWithrawal)+withdrawalServicePeriodQuery;
       String selectQuery="select count(distinct service.ovcId) ";
       List maleList=new ArrayList();
        List femaleList=new ArrayList();
        mainlist.add(indicatorName);
        String malesql=selectQuery+withdrawalServiceCountQueryPart+" and "+util.getGenderCriteria(maleGender)+ageQuery+" "+additionalQueryCriteria+additionalServiceQuery;
        String femalesql=selectQuery+withdrawalServiceCountQueryPart+" and "+util.getGenderCriteria(femaleGender)+ageQuery+" "+additionalQueryCriteria+additionalServiceQuery;

        System.err.println("malesql in getNoOfOvcWithdrawnButServedWithinReportPeriod is "+malesql);
        maleList=execReportQuery(malesql);
        femaleList=execReportQuery(femalesql);
        int maleCount=Integer.parseInt(maleList.get(0).toString());
        int femaleCount=Integer.parseInt(femaleList.get(0).toString());
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getNoOfHouseholdsWithBaselineAssessmentWithinTheReportPeriod(String indicatorName,String additionalQueryCriteria, String indicatorCode) throws Exception
    {
        HouseholdVulnerabilityAssessmentDao hvadao=new HouseholdVulnerabilityAssessmentDaoImpl();
        List mainlist=new ArrayList();
        mainlist.add(indicatorName);
        mainlist.add(hvadao.getNumberOfHouseholdsWithBaselineAssessement(additionalQueryCriteria));
        mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getNoOfHouseholdsCurrentlyEnrolledWithBaselineAssessmentWithinTheReportPeriod(String indicatorName,String additionalQueryCriteria, String indicatorCode) throws Exception
    {
        HouseholdVulnerabilityAssessmentDao hvadao=new HouseholdVulnerabilityAssessmentDaoImpl();
        List mainlist=new ArrayList();
        mainlist.add(indicatorName);
        mainlist.add(hvadao.getNumberOfHouseholdsCurrentlyEnrolledWithBaselineAssessement(additionalQueryCriteria));
        mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getNoOfHouseholdsWithBaselineAssessment(String indicatorName,String additionalQueryCriteria, String indicatorCode,boolean currentlyEnrolled) throws Exception
    {
        //HouseholdVulnerabilityAssessmentDao hvadao=new HouseholdVulnerabilityAssessmentDaoImpl();
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        List mainlist=new ArrayList();
        mainlist.add(indicatorName);
        mainlist.add(hhedao.getNumberOfHouseholdsWithBaselineAssessement(additionalQueryCriteria,currentlyEnrolled));
        //mainlist.add(hvadao.getNumberOfHouseholdsWithBaselineAssessement(additionalQueryCriteria));
        mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getNoOfHouseholdsCurrentlyEnrolledWithBaselineAssessment(String indicatorName,String additionalQueryCriteria, String indicatorCode) throws Exception
    {
        HouseholdVulnerabilityAssessmentDao hvadao=new HouseholdVulnerabilityAssessmentDaoImpl();
        List mainlist=new ArrayList();
        mainlist.add(indicatorName);
        mainlist.add(hvadao.getNumberOfHouseholdsCurrentlyEnrolledWithBaselineAssessement(additionalQueryCriteria));
        mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getNoOfHouseholdsWithFollowupAssessment(String indicatorName,String additionalQueryCriteria, String indicatorCode,boolean currentlyEnrolled) throws Exception
    {
        HouseholdFollowupAssessmentDao hhfadao=new HouseholdFollowupAssessmentDaoImpl();
        //HouseholdVulnerabilityAssessmentDao hvadao=new HouseholdVulnerabilityAssessmentDaoImpl();
        List mainlist=new ArrayList();
        mainlist.add(indicatorName);
        mainlist.add(hhfadao.getNumberOfHouseholdsWithFollowupAssessement(additionalQueryCriteria,currentlyEnrolled));
        mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getNoOfHouseholdsEnrolled(String additionalQueryCriteria, String hhePeriodQuery,String indicatorCode) throws Exception
    {
        //System.err.println("indicatorCode is "+indicatorCode);
        List mainlist=new ArrayList();
        mainlist.add("No of Households enrolled");
        String bothGendersql="select count(distinct hhe.hhUniqueId)from HouseholdEnrollment hhe where hhe.hhUniqueId is not null"+additionalQueryCriteria+hhePeriodQuery;
        mainlist.add(execReportQuery(bothGendersql).get(0));
        mainlist.add(indicatorCode);
        //System.err.println("Element at 2 is "+mainlist.get(2).toString());
        return mainlist;
    }
    public List getNoOfHouseholdsNewlyEnrolled(String indicatorName,String additionalQueryCriteria, String hhePeriodQuery,String indicatorCode) throws Exception
    {
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl(); 
        List mainlist=new ArrayList();
        additionalQueryCriteria=additionalQueryCriteria+hhePeriodQuery;
        mainlist.add(indicatorName);
        mainlist.add(hhedao.getNumberOfHouseholdsEnrolled(additionalQueryCriteria));
        mainlist.add(indicatorCode);
        //System.err.println("Element at 2 is "+mainlist.get(2).toString());
        return mainlist;
    }
    public List getNoOfHouseholdsCurrentlyEnrolled(String indicatorName,String additionalQueryCriteria, String hhePeriodQuery,String indicatorCode) throws Exception
    {
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl(); 
        List mainlist=new ArrayList();
        mainlist.add(indicatorName);
        mainlist.add(hhedao.getNumberOfHouseholdsCurrentlyEnrolled(additionalQueryCriteria));
        mainlist.add(indicatorCode);
        //System.err.println("Element at 2 is "+mainlist.get(2).toString());
        return mainlist;
    }
    public List getNoOfHouseholdsEverEnrolled(String indicatorName,String additionalQueryCriteria, String hhePeriodQuery,String indicatorCode) throws Exception
    {
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl(); 
        List mainlist=new ArrayList();
        mainlist.add(indicatorName);
        mainlist.add(hhedao.getNumberOfHouseholdsEnrolled(additionalQueryCriteria));
        mainlist.add(indicatorCode);
        System.err.println("Element at 1 is "+mainlist.get(1).toString());
        System.err.println("Element at 2 is "+mainlist.get(2).toString());
        return mainlist;
    }
    public List getNumberOfHouseholdsWithdrawn(String indicatorName,String additionalQueryCriteria, String hhePeriodQuery,String indicatorCode,String reasonWithdrawal) throws Exception
    {
        OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl(); 
        additionalQueryCriteria=additionalQueryCriteria+hhePeriodQuery;
        List mainlist=new ArrayList();
        mainlist.add(indicatorName);
        mainlist.add(wdao.getNumberOfHouseholdsWithdrawnPerCohort(additionalQueryCriteria,reasonWithdrawal));
        mainlist.add(indicatorCode);
        //System.err.println("Element at 2 is "+mainlist.get(2).toString());
        return mainlist;
    }
    public List getNumberOfOvcWithdrawn(String indicatorName,String additionalQueryCriteria, String ovcPeriodQuery,String indicatorCode,String reasonWithdrawal) throws Exception
    {
        OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl(); 
        additionalQueryCriteria=additionalQueryCriteria+ovcPeriodQuery;
        List mainlist=new ArrayList();
        mainlist.add(indicatorName);
        String malesql=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
        String femalesql=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);
        int maleCount=wdao.getNumberOfOvcWithdrawnPerCohort(malesql,reasonWithdrawal);
        int femaleCount=wdao.getNumberOfOvcWithdrawnPerCohort(femalesql,reasonWithdrawal);
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        
        //System.err.println("Element at 2 is "+mainlist.get(2).toString());
        return mainlist;
    }
    public List getListOfOvcWithdrawn(String sex,String additionalQueryCriteria,String withdrawalPeriodQuery,String reasonWithdrawal) throws Exception
    {
        OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl(); 
        additionalQueryCriteria=additionalQueryCriteria+withdrawalPeriodQuery;
        List mainlist=new ArrayList();
        String malesql=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
        String femalesql=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);
        if(sex.equalsIgnoreCase("male"))
        mainlist.addAll(wdao.getListOfOvcWithdrawnPerCohort(malesql,reasonWithdrawal));
        else if(sex.equalsIgnoreCase("female"))
        mainlist.addAll(wdao.getListOfOvcWithdrawnPerCohort(femalesql,reasonWithdrawal));
        else
        mainlist.addAll(wdao.getListOfOvcWithdrawnPerCohort(additionalQueryCriteria,reasonWithdrawal));
        return mainlist;
    }
    public List getListOfHouseholdsNewlyEnrolled(String indicatorName,String additionalQueryCriteria, String hhePeriodQuery,String indicatorCode) throws Exception
    {
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl(); 
        List mainlist=new ArrayList();
        additionalQueryCriteria=additionalQueryCriteria+hhePeriodQuery;
        mainlist.addAll(hhedao.getListOfHouseholdEnrollment(additionalQueryCriteria,null));
        
        //System.err.println("Element at 2 is "+mainlist.get(2).toString());
        return mainlist;
    }
    public List getListOfHouseholdsCurrentlyEnrolled(String indicatorName,String additionalQueryCriteria, String hhePeriodQuery,String indicatorCode) throws Exception
    {
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl(); 
        List mainlist=new ArrayList();
        additionalQueryCriteria=additionalQueryCriteria;//+hhePeriodQuery;
        mainlist.addAll(hhedao.getListOfHouseholdIdsCurrentlyEnrolled(additionalQueryCriteria));
        System.err.println("mainlist in getListOfHouseholdsCurrentlyEnrolled is "+mainlist.size());
        return mainlist;
    }
    public List getListOfHouseholdsEverEnrolled(String indicatorName,String additionalQueryCriteria, String hhePeriodQuery,String indicatorCode) throws Exception
    {
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl(); 
        List mainlist=new ArrayList();
        mainlist.addAll(hhedao.getListOfHouseholdEnrollment(additionalQueryCriteria,null));
        return mainlist;
    }
    public List getListOfHouseholdsWithdrawn(String sex,String additionalQueryCriteria,String hviPeriodQuery,String reasonWithdrawal) throws Exception
    {
        OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl(); 
        additionalQueryCriteria=additionalQueryCriteria+hviPeriodQuery;
        List mainlist=new ArrayList();
        mainlist.addAll(wdao.getListOfHouseholdsWithdrawnPerCohort(additionalQueryCriteria,reasonWithdrawal));
        return mainlist;
    }
    public List getNoOfCaregiversNewlyEnrolled(IndicatorDictionary ind,String additionalQueryCriteria,String cgiverPeriodQuery) throws Exception
    {
        List mainlist=new ArrayList();
        additionalQueryCriteria=additionalQueryCriteria+cgiverPeriodQuery;
        CaregiverDao cgiverdao=new CaregiverDaoImpl();
        mainlist.add(ind.getIndicatorForNumberOfCaregiversNewlyEnrolled().getIndicatorName());
        String malesql=additionalQueryCriteria+" and "+util.getCaregiverGenderCriteria(maleGender);
        String femalesql=additionalQueryCriteria+" and "+util.getCaregiverGenderCriteria(femaleGender);
        int maleCount=cgiverdao.getNoOfCaregiversPerCohort(malesql);
        int femaleCount=cgiverdao.getNoOfCaregiversPerCohort(femalesql);
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(ind.getIndicatorForNumberOfCaregiversNewlyEnrolled().getIndicatorId());
        return mainlist;
    }
    public List getNoOfCaregiversCurrentlyEnrolled(IndicatorDictionary ind,String additionalQueryCriteria,String cgiverPeriodQuery) throws Exception
    {
        List mainlist=new ArrayList();
        //additionalQueryCriteria=additionalQueryCriteria;
        CaregiverDao cgiverdao=new CaregiverDaoImpl();
        mainlist.add(ind.getIndicatorForNumberOfCaregiversCurrentlyEnrolled().getIndicatorName());
        String malesql=additionalQueryCriteria+" and "+util.getCaregiverGenderCriteria(maleGender);
        String femalesql=additionalQueryCriteria+" and "+util.getCaregiverGenderCriteria(femaleGender);
        int maleCount=cgiverdao.getNoOfCaregiversCurrentlyEnrolled(malesql);
        int femaleCount=cgiverdao.getNoOfCaregiversCurrentlyEnrolled(femalesql);
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(ind.getIndicatorForNumberOfCaregiversCurrentlyEnrolled().getIndicatorId());
        return mainlist;
    }
    public List getNoOfCaregiversEverEnrolled(IndicatorDictionary ind,String additionalQueryCriteria,String cgiverPeriodQuery) throws Exception
    {
        List mainlist=new ArrayList();
        CaregiverDao cgiverdao=new CaregiverDaoImpl();
        mainlist.add(ind.getIndicatorForNumberOfCaregiversEverEnrolled().getIndicatorName());
        String malesql=additionalQueryCriteria+" and "+util.getCaregiverGenderCriteria(maleGender);
        String femalesql=additionalQueryCriteria+" and "+util.getCaregiverGenderCriteria(femaleGender);
        int maleCount=cgiverdao.getNoOfCaregiversPerCohort(malesql);
        int femaleCount=cgiverdao.getNoOfCaregiversPerCohort(femalesql);
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(ind.getIndicatorForNumberOfCaregiversEverEnrolled().getIndicatorId());
        return mainlist;
    }
    public List getNoOfCaregiversEnrolledInCareOrOnART(String indicatorName,String additionalQueryCriteria,String indicatorCode,String careOrART,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        String currentlyEnrolledQuery=" ";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=" and "+rutil.getCgiverCurrentlyEnrolledQuery();
        mainlist.add(indicatorName);
        CaregiverDao cgiverdao=new CaregiverDaoImpl();
        additionalQueryCriteria=additionalQueryCriteria+currentlyEnrolledQuery;
        
        String malesql=" and "+util.getCaregiverGenderCriteria(maleGender)+" "+additionalQueryCriteria;
        String femalesql=" and "+util.getCaregiverGenderCriteria(femaleGender)+" "+additionalQueryCriteria;
        
        
        int maleCount=cgiverdao.getNoOfCaregiversCurrentlyEnrolledInCare(malesql);
        int femaleCount=cgiverdao.getNoOfCaregiversCurrentlyEnrolledInCare(femalesql);
        if(careOrART.equalsIgnoreCase("ART"))
        {
            maleCount=cgiverdao.getNoOfCaregiversCurrentlyEnrolledOnART(malesql);
            femaleCount=cgiverdao.getNoOfCaregiversCurrentlyEnrolledOnART(femalesql);
        }
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getNoOfCaregiversEnrolledByHIVStatus(String indicatorName,String additionalQueryCriteria,String indicatorCode,String hivStatus,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        String currentlyEnrolledQuery=" ";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=" and "+rutil.getCgiverCurrentlyEnrolledQuery();
        mainlist.add(indicatorName);
        CaregiverDao cgiverdao=new CaregiverDaoImpl();
        additionalQueryCriteria=additionalQueryCriteria+currentlyEnrolledQuery;
        
        String malesql=" and "+util.getCaregiverGenderCriteria(maleGender)+" "+additionalQueryCriteria;
        String femalesql=" and "+util.getCaregiverGenderCriteria(femaleGender)+" "+additionalQueryCriteria;
        
        
        int maleCount=cgiverdao.getNoOfCaregiversCurrentlyEnrolledBasedOnHivStatus(malesql, hivStatus);
        int femaleCount=cgiverdao.getNoOfCaregiversCurrentlyEnrolledBasedOnHivStatus(femalesql, hivStatus);
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        return mainlist;
    }//
    public List getListOfCaregiversEnrolledInCareOrOnART(String sex,String additionalQueryCriteria,String careStatus,boolean currentlyEnrolled) throws Exception
    {
        CaregiverDao dao=new CaregiverDaoImpl();
        List mainlist=new ArrayList();
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=" and "+rutil.getCgiverCurrentlyEnrolledQuery();
        additionalQueryCriteria=additionalQueryCriteria+currentlyEnrolledQuery;
        
        String sql=additionalQueryCriteria;
        
        if(sex.equalsIgnoreCase("male"))
        sql=additionalQueryCriteria+" and "+util.getCaregiverGenderCriteria(maleGender);
        else if(sex.equalsIgnoreCase("female"))
        sql=additionalQueryCriteria+" and "+util.getCaregiverGenderCriteria(femaleGender);
        mainlist= dao.getListOfCaregiversCurrentlyEnrolledInCareOrOnART(sql, careStatus);
        return mainlist;
    }
    public List getListOfCaregiversEnrolledByHIVStatus(String sex,String additionalQueryCriteria,String hivStatus,boolean currentlyEnrolled) throws Exception
    {
        CaregiverDao dao=new CaregiverDaoImpl();
        List mainlist=new ArrayList();
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=" and "+rutil.getCgiverCurrentlyEnrolledQuery();
        additionalQueryCriteria=additionalQueryCriteria+currentlyEnrolledQuery;
        
        String sql=additionalQueryCriteria;
        
        if(sex.equalsIgnoreCase("male"))
        sql=additionalQueryCriteria+" and "+util.getCaregiverGenderCriteria(maleGender);
        else if(sex.equalsIgnoreCase("female"))
        sql=additionalQueryCriteria+" and "+util.getCaregiverGenderCriteria(femaleGender);
        mainlist= dao.getListOfCaregiversCurrentlyEnrolledBasedOnHivStatus(sql, hivStatus);
        return mainlist;
    }
    public List getNoOfCaregiversNewlyEnrolledAndServedByAge(String indicatorId,String indicatorName,String additionalQueryCriteria,String startDate,String endDate,int startAge, int endAge,boolean active) throws Exception
    {
        System.err.println("About to execute getNoOfCaregiversNewlyEnrolledAndServed");
        HouseholdServiceDao hhsdao=new HouseholdServiceDaoImpl();
        List mainlist=new ArrayList();
        String withdrawnFromProgramQuery="";
        String ageCriteria="";
        if(endAge>0)
        ageCriteria=" and "+util.getCaregiverAgeCriteria(startAge, endAge);
        if(active)
        withdrawnFromProgramQuery=util.getCaregiverWithdrawnFromProgramQuery("No");
        mainlist.add(indicatorName);
        
        int maleCount=hhsdao.getNoOfCaregiversNewlyEnrolledAndServedWithinTheReportPeriod(additionalQueryCriteria+ageCriteria+withdrawnFromProgramQuery,"Male",startDate,endDate);
        int femaleCount=hhsdao.getNoOfCaregiversNewlyEnrolledAndServedWithinTheReportPeriod(additionalQueryCriteria+ageCriteria+withdrawnFromProgramQuery,"Female",startDate,endDate);
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorId);
        System.err.println("getNoOfCaregiversNewlyEnrolledAndServed executed");
        return mainlist;
        
    }
    public List getNoOfCaregiversNewlyEnrolledAndServed(String indicatorId,String indicatorName,String additionalQueryCriteria,String startDate,String endDate,boolean active) throws Exception
    {
        System.err.println("About to execute getNoOfCaregiversNewlyEnrolledAndServed");
        HouseholdServiceDao hhsdao=new HouseholdServiceDaoImpl();
        List mainlist=new ArrayList();
        String withdrawnFromProgramQuery="";
        if(active)
        withdrawnFromProgramQuery=util.getCaregiverWithdrawnFromProgramQuery("No");
        mainlist.add(indicatorName);
        
        int maleCount=hhsdao.getNoOfCaregiversNewlyEnrolledAndServedWithinTheReportPeriod(additionalQueryCriteria+withdrawnFromProgramQuery,"Male",startDate,endDate);
        int femaleCount=hhsdao.getNoOfCaregiversNewlyEnrolledAndServedWithinTheReportPeriod(additionalQueryCriteria+withdrawnFromProgramQuery,"Female",startDate,endDate);
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorId);
        System.err.println("getNoOfCaregiversNewlyEnrolledAndServed executed");
        return mainlist;
        
    }
    public List getNoOfCaregiversServed(String indicatorId,String indicatorName,String additionalQueryCriteria,String cgiverPeriodQuery,String additionalServiceQuery,String ageQuery,boolean newlyEnrolled,boolean active) throws Exception
    {
        
        List mainlist=new ArrayList();
        String withdrawnFromProgramQuery="";
        String enrollmentQuery="";
        if(newlyEnrolled)
        enrollmentQuery=additionalServiceQuery.replace("hhs.serviceDate", "cgiver.dateOfEnrollment");
        if(active)
        withdrawnFromProgramQuery=util.getCaregiverWithdrawnFromProgramQuery("No");
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        mainlist.add(indicatorName);
        //mainlist.add("No of Caregivers provided with at least one service");
        String malesql="select count(distinct hhs.caregiverId) from HouseholdEnrollment hhe, Caregiver cgiver, HouseholdService hhs where hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=hhs.caregiverId and "+util.getCaregiverGenderCriteria(maleGender)+enrollmentQuery+withdrawnFromProgramQuery+" "+additionalServiceQuery;
        String femalesql="select count(distinct hhs.caregiverId) from HouseholdEnrollment hhe, Caregiver cgiver, HouseholdService hhs where hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=hhs.caregiverId and "+util.getCaregiverGenderCriteria(femaleGender)+enrollmentQuery+withdrawnFromProgramQuery+" "+additionalServiceQuery;
        System.err.println(malesql);
        maleList=execReportQuery(malesql);
        femaleList=execReportQuery(femalesql);
        int maleCount=Integer.parseInt(maleList.get(0).toString());
        int femaleCount=Integer.parseInt(femaleList.get(0).toString());
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorId);
        return mainlist;
    }
    public List getNoOfCaregiversServedByAge(String indicatorId,String indicatorName,String additionalQueryCriteria,String cgiverPeriodQuery,String additionalServiceQuery,String ageQuery,boolean newlyEnrolled,boolean active) throws Exception
    {
        List mainlist=new ArrayList();
        String withdrawnFromProgramQuery="";
        String enrollmentQuery="";
        if(newlyEnrolled)
        enrollmentQuery=additionalServiceQuery.replace("hhs.serviceDate", "cgiver.dateOfEnrollment");
        if(active)
        withdrawnFromProgramQuery=util.getCaregiverWithdrawnFromProgramQuery("No");
        
        if(ageQuery !=null && ageQuery.indexOf("caregiverAge") !=-1)
        ageQuery=" and "+ageQuery;
        
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        mainlist.add(indicatorName);
        //mainlist.add("No of Caregivers provided with at least one service");
        String malesql="select count(distinct hhs.caregiverId) from HouseholdEnrollment hhe, Caregiver cgiver, HouseholdService hhs where hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=hhs.caregiverId and "+util.getCaregiverGenderCriteria(maleGender)+ageQuery+enrollmentQuery+withdrawnFromProgramQuery+" "+additionalServiceQuery;
        String femalesql="select count(distinct hhs.caregiverId) from HouseholdEnrollment hhe, Caregiver cgiver, HouseholdService hhs where hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=hhs.caregiverId and "+util.getCaregiverGenderCriteria(femaleGender)+ageQuery+enrollmentQuery+withdrawnFromProgramQuery+" "+additionalServiceQuery;
        System.err.println("Query in getNoOfCaregiversServedByAge is "+malesql);
        maleList=execReportQuery(malesql);
        femaleList=execReportQuery(femalesql);
        int maleCount=Integer.parseInt(maleList.get(0).toString());
        int femaleCount=Integer.parseInt(femaleList.get(0).toString());
        int total=maleCount+femaleCount;
        
        //System.err.println("ageQuery in getNoOfCaregiversServedByAge is "+ageQuery);
        System.err.println("maleCount in getNoOfCaregiversServedByAge is "+maleCount);
        System.err.println("femaleCount in getNoOfCaregiversServedByAge is "+femaleCount);
        System.err.println("total in getNoOfCaregiversServedByAge is "+total);
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorId);
        return mainlist;
    }
    public List getOVC_HIVSTATPOSITIVE(String indicatorCode,String indicatorName,String additionalQueryCriteria,String additionalEnrollmentQuery, String additionalServiceQuery, String ageQuery,String startDate,String endDate,String fyStartDate,String fyEndDate,boolean currentlyEnrolled)
    {
        List resultList = new ArrayList();
        int maleOvcCount=0;
        int femaleOvcCount=0;
        int maleGraduatedCount=0;
        int femaleGraduatedCount=0;
        int totalMaleCount=0;
        int totalFemaleCount=0;
        int grandTotal=0;
        try
        {
            List activePositiveServedList=getNoOfHivPositiveOvcServed(indicatorName,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery,indicatorCode,false,true);
            List positiveGradServedList=getNoOfOvcWithdrawnButServedWithinReportPeriodByHivStatus(indicatorName,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,indicatorCode,NomisConstant.GRADUATED,NomisConstant.HIV_POSITIVE,false,fyStartDate,fyEndDate);
            
            if(activePositiveServedList !=null && activePositiveServedList.size()>3)
            {
                maleOvcCount=Integer.parseInt(activePositiveServedList.get(1).toString());
                femaleOvcCount=Integer.parseInt(activePositiveServedList.get(2).toString());
                totalMaleCount+=maleOvcCount;
                totalFemaleCount+=femaleOvcCount;
                System.err.println("positiveServedList maleOvcCount: "+maleOvcCount+" positiveServedList femaleOvcCount: "+femaleOvcCount);
            }
             
            if(positiveGradServedList !=null && positiveGradServedList.size()>3)
            {
                maleGraduatedCount=Integer.parseInt(positiveGradServedList.get(1).toString());
                femaleGraduatedCount=Integer.parseInt(positiveGradServedList.get(2).toString());
                totalMaleCount+=maleGraduatedCount;
                totalFemaleCount+=femaleGraduatedCount;
                System.err.println("positiveGradServedList maleGraduatedCount: "+maleGraduatedCount+" positiveGradServedList femaleGraduatedCount: "+femaleGraduatedCount);
            }
            grandTotal=totalMaleCount+totalFemaleCount;
            resultList.add(indicatorName);
            resultList.add(totalMaleCount);
            resultList.add(totalFemaleCount);
            resultList.add(grandTotal);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
            return resultList;
    }
    public List getOVC_HIVSTATNEGATIVE(String indicatorCode,String indicatorName,String additionalQueryCriteria, String additionalEnrollmentQuery, String additionalServiceQuery, String ageQuery,String startDate,String endDate,String fyStartDate,String fyEndDate,boolean currentlyEnrolled)
    {
        //String indicatorName="OVC_HIVSTAT";
        
        List resultList = new ArrayList();
        int maleOvcCount=0;
        int femaleOvcCount=0;
        int maleGraduatedCount=0;
        int femaleGraduatedCount=0;
        int totalMaleCount=0;
        int totalFemaleCount=0;
        int grandTotal=0;
        try
        {
            List activeNegativeServedList=getNoOfHivNegativeOvcServed(indicatorName,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery,indicatorCode,true);
            List negativeGradServedList=getNoOfOvcWithdrawnButServedWithinReportPeriodByHivStatus(indicatorName,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,indicatorCode,NomisConstant.GRADUATED,NomisConstant.HIV_NEGATIVE,false,fyStartDate,fyEndDate);
            System.err.println("additionalQueryCriteria is "+additionalQueryCriteria);
            //System.err.println("exitedQueryCriteria is "+exitedQueryCriteria);
            if(activeNegativeServedList !=null && activeNegativeServedList.size()>3)
            {
                maleOvcCount=Integer.parseInt(activeNegativeServedList.get(1).toString());
                femaleOvcCount=Integer.parseInt(activeNegativeServedList.get(2).toString());
                totalMaleCount+=maleOvcCount;
                totalFemaleCount+=femaleOvcCount;
                //System.err.println("maleOvcCount: "+maleOvcCount+" femaleOvcCount: "+femaleOvcCount);
            }
             
            if(negativeGradServedList !=null && negativeGradServedList.size()>3)
            {
                maleGraduatedCount=Integer.parseInt(negativeGradServedList.get(1).toString());
                femaleGraduatedCount=Integer.parseInt(negativeGradServedList.get(2).toString());
                totalMaleCount+=maleGraduatedCount;
                totalFemaleCount+=femaleGraduatedCount;
                System.err.println("male HIV Negative served "+maleGraduatedCount+" female HIV Negative served: "+femaleGraduatedCount);
            }
            grandTotal=totalMaleCount+totalFemaleCount;
            resultList.add(indicatorName);
            resultList.add(totalMaleCount);
            resultList.add(totalFemaleCount);
            resultList.add(grandTotal);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
            return resultList;
    }
    public List getOVC_HIVSTATUNKNOWN(String indicatorCode,String indicatorName,String additionalQueryCriteria, String additionalEnrollmentQuery, String additionalServiceQuery, String ageQuery,String startDate,String endDate,String fyStartDate,String fyEndDate,boolean currentlyEnrolled)
    {
        //String indicatorName="OVC_HIVSTAT";
        
        List resultList = new ArrayList();
        int maleOvcCount=0;
        int femaleOvcCount=0;
        int maleTniCount=0;
        int femaleTniCount=0;
        int maleGraduatedCount=0;
        int femaleGraduatedCount=0;
        int totalMaleCount=0;
        int totalFemaleCount=0;
        int grandTotal=0;
        try
        {
            List activeUnknownServedList=getNoOfHivUnknownOvcServed(indicatorName,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery,indicatorCode,NomisConstant.HIV_UNKNOWN,true);
            //List tniServedList=getNoOfOvcWithdrawnButServedWithinReportPeriodByHivStatus(indicatorName,additionalQueryCriteria,additionalEnrollmentQuery,additionalServiceQuery,ageQuery,indicatorCode,NomisConstant.HIV_UNKNOWN,currentlyEnrolled);
            List unknownGradServedList=getNoOfOvcWithdrawnButServedWithinReportPeriodByHivStatus(indicatorName,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,indicatorCode,NomisConstant.GRADUATED,NomisConstant.HIV_UNKNOWN,false,fyStartDate,fyEndDate);
            
            if(activeUnknownServedList !=null && activeUnknownServedList.size()>3)
            {
                maleOvcCount=Integer.parseInt(activeUnknownServedList.get(1).toString());
                femaleOvcCount=Integer.parseInt(activeUnknownServedList.get(2).toString());
                totalMaleCount+=maleOvcCount;
                totalFemaleCount+=femaleOvcCount;
                System.err.println("maleOvcCount: "+maleOvcCount+" femaleOvcCount: "+femaleOvcCount);
            }
            /*if(tniServedList !=null && tniServedList.size()>3)
            {
                maleTniCount=Integer.parseInt(tniServedList.get(1).toString());
                femaleTniCount=Integer.parseInt(tniServedList.get(2).toString());
                totalMaleCount+=maleTniCount;
                totalFemaleCount+=femaleTniCount;
                //System.err.println("femaleTniCount "+maleTniCount+" femaleCaregiverCount: "+femaleTniCount);
            }*/
            if(unknownGradServedList !=null && unknownGradServedList.size()>3)
            {
                maleGraduatedCount=Integer.parseInt(unknownGradServedList.get(1).toString());
                femaleGraduatedCount=Integer.parseInt(unknownGradServedList.get(2).toString());
                totalMaleCount+=maleGraduatedCount;
                totalFemaleCount+=femaleGraduatedCount;
                //System.err.println("maleCaregiverCount "+maleGraduatedCount+" femaleCaregiverCount: "+femaleGraduatedCount);
            }
            grandTotal=totalMaleCount+totalFemaleCount;
            resultList.add(indicatorName);
            resultList.add(totalMaleCount);
            resultList.add(totalFemaleCount);
            resultList.add(grandTotal);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
            return resultList;
    }
    public List getOVC_HIVSTAT(String orgUnitQueryCriteria, String ovcPeriodQuery, String caregiverPeriodQuery, String ovcAgeQuery,boolean newlyEnrolled,boolean currentlyEnrolled)
    {
        String indicatorName="OVC_HIVSTAT";
        List resultList = new ArrayList();
        int maleOvcCount=0;
        int femaleOvcCount=0;
        int maleCaregiverCount=0;
        int femaleCaregiverCount=0;
        int totalMaleCount=0;
        int totalFemaleCount=0;
        int grandTotal=0;
        try
        {
            List ovc_ServList=getNoOfOvcServed(indicatorName, orgUnitQueryCriteria, ovcPeriodQuery, ovcPeriodQuery, ovcAgeQuery, indicatorName);
            List totalCaregiverServedList=getNoOfCaregiversServed(indicatorName,indicatorName,orgUnitQueryCriteria,"",caregiverPeriodQuery,"",newlyEnrolled,currentlyEnrolled);
            
            if(ovc_ServList !=null && ovc_ServList.size()>3)
            {
                maleOvcCount=Integer.parseInt(ovc_ServList.get(1).toString());
                femaleOvcCount=Integer.parseInt(ovc_ServList.get(2).toString());
                totalMaleCount+=maleOvcCount;
                totalFemaleCount+=femaleOvcCount;
                System.err.println("maleOvcCount: "+maleOvcCount+" femaleOvcCount: "+femaleOvcCount);
            }
             
            if(totalCaregiverServedList !=null && totalCaregiverServedList.size()>3)
            {
                maleCaregiverCount=Integer.parseInt(totalCaregiverServedList.get(1).toString());
                femaleCaregiverCount=Integer.parseInt(totalCaregiverServedList.get(2).toString());
                totalMaleCount+=maleCaregiverCount;
                totalFemaleCount+=femaleCaregiverCount;
                System.err.println("maleCaregiverCount "+maleCaregiverCount+" femaleCaregiverCount: "+femaleCaregiverCount);
            }
            grandTotal=totalMaleCount+totalFemaleCount;
            
            resultList.add(totalMaleCount);
            resultList.add(totalFemaleCount);
            resultList.add(grandTotal);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
            return resultList;
    }
    public List getOVC_SERV(String orgUnitQueryCriteria, String ovcPeriodQuery, String caregiverPeriodQuery, String ovcAgeQuery,boolean newlyEnrolled,boolean currentlyEnrolled)
    {
        String indicatorName="OVC_SERV";
        List resultList = new ArrayList();
        int maleOvcCount=0;
        int femaleOvcCount=0;
        int maleCaregiverCount=0;
        int femaleCaregiverCount=0;
        int totalMaleCount=0;
        int totalFemaleCount=0;
        int grandTotal=0;
        try
        {
            List ovc_ServList=getNoOfOvcServed(indicatorName, orgUnitQueryCriteria, ovcPeriodQuery, ovcPeriodQuery, ovcAgeQuery, indicatorName);
            List totalCaregiverServedList=getNoOfCaregiversServed(indicatorName,indicatorName,orgUnitQueryCriteria,"",caregiverPeriodQuery,"",newlyEnrolled,currentlyEnrolled);
            
            if(ovc_ServList !=null && ovc_ServList.size()>3)
            {
                maleOvcCount=Integer.parseInt(ovc_ServList.get(1).toString());
                femaleOvcCount=Integer.parseInt(ovc_ServList.get(2).toString());
                totalMaleCount+=maleOvcCount;
                totalFemaleCount+=femaleOvcCount;
                System.err.println("maleOvcCount: "+maleOvcCount+" femaleOvcCount: "+femaleOvcCount);
            }
             
            if(totalCaregiverServedList !=null && totalCaregiverServedList.size()>3)
            {
                maleCaregiverCount=Integer.parseInt(totalCaregiverServedList.get(1).toString());
                femaleCaregiverCount=Integer.parseInt(totalCaregiverServedList.get(2).toString());
                totalMaleCount+=maleCaregiverCount;
                totalFemaleCount+=femaleCaregiverCount;
                System.err.println("maleCaregiverCount "+maleCaregiverCount+" femaleCaregiverCount: "+femaleCaregiverCount);
            }
            grandTotal=totalMaleCount+totalFemaleCount;
            
            resultList.add(totalMaleCount);
            resultList.add(totalFemaleCount);
            resultList.add(grandTotal);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
            return resultList;
    }
    public List getNoOfActiveCaregiversServedAndWithdrawnFromProgram(String indicatorId,String indicatorName,String additionalQueryCriteria,String cgiverPeriodQuery,String additionalServiceQuery,String ageQuery, String reasonWithdrawal) throws Exception
    {
        List mainlist=new ArrayList();
        
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        String withdrawalQuery=rutil.getCgiverWithdrawnQuery(reasonWithdrawal);
        mainlist.add(indicatorName);
        //mainlist.add("No of Caregivers provided with at least one service");
        String malesql="select count(distinct hhs.caregiverId) from HouseholdEnrollment hhe, Caregiver cgiver, HouseholdService hhs, OvcWithdrawal withdrawal where hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=hhs.caregiverId and cgiver.caregiverId=withdrawal.ovcId and "+util.getCaregiverGenderCriteria(maleGender)+" and "+withdrawalQuery+" "+additionalServiceQuery;
        String femalesql="select count(distinct hhs.caregiverId) from HouseholdEnrollment hhe, Caregiver cgiver, HouseholdService hhs, OvcWithdrawal withdrawal where hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=hhs.caregiverId and cgiver.caregiverId=withdrawal.ovcId and "+util.getCaregiverGenderCriteria(femaleGender)+" and "+withdrawalQuery+" "+additionalServiceQuery;
        System.err.println("malesql in getNoOfActiveCaregiversServedAndWithdrawnFromProgram is "+malesql);
        maleList=execReportQuery(malesql);
        femaleList=execReportQuery(femalesql);
        int maleCount=Integer.parseInt(maleList.get(0).toString());
        int femaleCount=Integer.parseInt(femaleList.get(0).toString());
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorId);
        return mainlist;
    }
    public List getNoOfActiveCaregiversServedAndWithdrawnFromProgramByAge(String indicatorId,String indicatorName,String additionalQueryCriteria,String cgiverPeriodQuery,String additionalServiceQuery,String ageQuery, String reasonWithdrawal) throws Exception
    {
        List mainlist=new ArrayList();
        
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        String withdrawalQuery=rutil.getCgiverWithdrawnQuery(reasonWithdrawal);
        mainlist.add(indicatorName);
        
        if(ageQuery !=null && ageQuery.indexOf("caregiverAge") !=-1)
        ageQuery=" and "+ageQuery;
        //mainlist.add("No of Caregivers provided with at least one service");
        String malesql="select count(distinct hhs.caregiverId) from HouseholdEnrollment hhe, Caregiver cgiver, HouseholdService hhs, OvcWithdrawal withdrawal where hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=hhs.caregiverId and cgiver.caregiverId=withdrawal.ovcId and "+util.getCaregiverGenderCriteria(maleGender)+ageQuery+" and "+withdrawalQuery+" "+additionalServiceQuery;
        String femalesql="select count(distinct hhs.caregiverId) from HouseholdEnrollment hhe, Caregiver cgiver, HouseholdService hhs, OvcWithdrawal withdrawal where hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=hhs.caregiverId and cgiver.caregiverId=withdrawal.ovcId and "+util.getCaregiverGenderCriteria(femaleGender)+ageQuery+" and "+withdrawalQuery+" "+additionalServiceQuery;
        System.err.println("malesql in getNoOfActiveCaregiversServedAndWithdrawnFromProgram is "+malesql);
        maleList=execReportQuery(malesql);
        femaleList=execReportQuery(femalesql);
        int maleCount=Integer.parseInt(maleList.get(0).toString());
        int femaleCount=Integer.parseInt(femaleList.get(0).toString());
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorId);
        return mainlist;
    }
    public List getNumberOfCaregiversWithdrawn(String indicatorName,String additionalQueryCriteria, String ovcPeriodQuery,String indicatorCode,String reasonWithdrawal) throws Exception
    {
        OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl(); 
        additionalQueryCriteria=additionalQueryCriteria+ovcPeriodQuery;
        List mainlist=new ArrayList();
        mainlist.add(indicatorName);
        String malesql=additionalQueryCriteria+" and "+util.getCaregiverGenderCriteria(maleGender);
        String femalesql=additionalQueryCriteria+" and "+util.getCaregiverGenderCriteria(femaleGender);
        int maleCount=wdao.getNumberOfCaregiversWithdrawnPerCohort(malesql,reasonWithdrawal);
        int femaleCount=wdao.getNumberOfCaregiversWithdrawnPerCohort(femalesql,reasonWithdrawal);
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        
        //System.err.println("Element at 2 is "+mainlist.get(2).toString());
        return mainlist;
    }
    public List getNumberOfHivPosOvcCurrentlyEnrolledBasedOnVulnerability(String indicatorId,String indicatorName,String additionalQueryCriteria,int startValue,int endValue,boolean currentlyEnrolled) throws Exception
    {
        OvcDao dao=new OvcDaoImpl();
        List mainlist=new ArrayList();
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");//" and hhe.withdrawnFromProgram='No'";
        additionalQueryCriteria=additionalQueryCriteria+currentlyEnrolledQuery;
        mainlist.add(indicatorName);
        String malesql=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
        String femalesql=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);
        int maleCount=dao.getNumberOfHivPosOvcCurrentlyEnrolledBasedOnVulnerability(malesql, startValue, endValue);
        int femaleCount=dao.getNumberOfHivPosOvcCurrentlyEnrolledBasedOnVulnerability(femalesql, startValue, endValue);
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorId);
        return mainlist;
    }
    public List getListOfHivPosOvcCurrentlyEnrolledBasedOnVulnerability(String sex,String additionalQueryCriteria,int startValue,int endValue,boolean currentlyEnrolled) throws Exception
    {
        OvcDao dao=new OvcDaoImpl();
        List mainlist=new ArrayList();
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");//" and hhe.withdrawnFromProgram='No'";
        additionalQueryCriteria=additionalQueryCriteria+currentlyEnrolledQuery;
        
        String sql=additionalQueryCriteria;
        
        if(sex.equalsIgnoreCase("male"))
        sql=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
        else if(sex.equalsIgnoreCase("female"))
        sql=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);
        mainlist= dao.getListOfHivPosOvcCurrentlyEnrolledBasedOnVulnerability(sql, startValue, endValue);
        return mainlist;
    }
    public List getListOfHivPosCaregiverCurrentlyEnrolledBasedOnVulnerability(String sex, String additionalQueryCriteria,int startValue,int endValue,boolean currentlyEnrolled) throws Exception
    {
        CaregiverDao cgiverdao=new CaregiverDaoImpl();
        List mainlist=new ArrayList();
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getCaregiverWithdrawnFromProgramQuery("No");
        additionalQueryCriteria=additionalQueryCriteria+currentlyEnrolledQuery;
        String sql=additionalQueryCriteria;
        
        if(sex.equalsIgnoreCase("male"))
        sql=additionalQueryCriteria+" and "+util.getCaregiverGenderCriteria(maleGender);
        else if(sex.equalsIgnoreCase("female"))
        sql=additionalQueryCriteria+" and "+util.getCaregiverGenderCriteria(femaleGender);
        mainlist= cgiverdao.getListOfHivPosCaregiverCurrentlyEnrolledBasedOnVulnerability(sql, startValue, endValue);
        return mainlist;
    }
    public List getNumberOfHivPosCaregiverCurrentlyEnrolledBasedOnVulnerability(String indicatorId,String indicatorName,String additionalQueryCriteria,int startValue,int endValue,boolean currentlyEnrolled) throws Exception
    {
        CaregiverDao cgiverdao=new CaregiverDaoImpl();
        List mainlist=new ArrayList();
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getCaregiverWithdrawnFromProgramQuery("No");//" and hhe.withdrawnFromProgram='No'";
        additionalQueryCriteria=additionalQueryCriteria+currentlyEnrolledQuery;
        mainlist.add(indicatorName);
        String malesql=additionalQueryCriteria+" and "+util.getCaregiverGenderCriteria(maleGender);
        String femalesql=additionalQueryCriteria+" and "+util.getCaregiverGenderCriteria(femaleGender);
        int maleCount=cgiverdao.getNumberOfHivPosCaregiverCurrentlyEnrolledBasedOnVulnerability(malesql, startValue, endValue);
        int femaleCount=cgiverdao.getNumberOfHivPosCaregiverCurrentlyEnrolledBasedOnVulnerability(femalesql, startValue, endValue);
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorId);
        return mainlist;
    }
    public List getNumberOfHouseholdsCurrentlyEnrolledBasedOnVulnerability(String indicatorId,String indicatorName,String additionalQueryCriteria,int startValue,int endValue,boolean currentlyEnrolled) throws Exception
    {
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        List mainlist=new ArrayList();
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getHouseholdWithdrawnFromProgramQuery("No");//" and hhe.withdrawnFromProgram='No'";
        additionalQueryCriteria=additionalQueryCriteria+currentlyEnrolledQuery;
        mainlist.add(indicatorName);   
        int totalCount=hhedao.getNumberOfHouseholdsByBaselineVulnerabilityStatus(additionalQueryCriteria,currentlyEnrolled, startValue, endValue);
        mainlist.add(totalCount);
        mainlist.add(indicatorId);
        return mainlist;
    }
    public List getNoOfHouseholdsProvidedHES(String indicatorId,String indicatorName,String additionalQueryCriteria,String startDate,String endDate,boolean currentlyEnrolled) throws Exception
    {
        //getNumberOfHouseholdsProvidedHES(String additionalQuery,String currentlyEnrolledQuery,String startDate,String endDate)
        HouseholdServiceDao hhsdao=new HouseholdServiceDaoImpl();
        List mainlist=new ArrayList();
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");//" and ovc.withdrawnFromProgram='No'";
        mainlist.add(indicatorName);   
        int totalCount=hhsdao.getNumberOfHouseholdsProvidedHES(additionalQueryCriteria, currentlyEnrolledQuery, startDate, endDate);
        mainlist.add(totalCount);
        mainlist.add(indicatorId);
        return mainlist;
    }
    public List getListOfHouseholdsProvidedHES(String additionalQueryCriteria,String startDate,String endDate,boolean currentlyEnrolled)
    {
        List mainlist=new ArrayList();
        try
        {
            HouseholdServiceDao hhsdao=new HouseholdServiceDaoImpl();
            String currentlyEnrolledQuery="";
            if(currentlyEnrolled)
            currentlyEnrolledQuery=" and ovc.withdrawnFromProgram='No'";
            mainlist=hhsdao.getListOfHouseholdsProvidedHES(additionalQueryCriteria, currentlyEnrolledQuery, startDate, endDate);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return mainlist;
    }
    public List getNoOfHouseholdsServed(String indicatorId,String indicatorName,String additionalQueryCriteria,String additionalServiceQuery,String ageQuery,boolean active) throws Exception
    {
        //getNumberOfHouseholdsProvidedHES(String additionalQuery,String currentlyEnrolledQuery,String startDate,String endDate)
        List mainlist=new ArrayList();
        String withdrawnFromProgramQuery="";
        if(active)
        withdrawnFromProgramQuery=util.getCaregiverWithdrawnFromProgramQuery("No");
        List maleList=new ArrayList();
        mainlist.add(indicatorName);
        String malesql="select count(distinct hhs.hhUniqueId) from HouseholdEnrollment hhe, HouseholdService hhs where hhe.hhUniqueId=hhs.hhUniqueId "+withdrawnFromProgramQuery+" "+additionalServiceQuery;
        
        System.err.println("malesql in getNoOfHouseholdsServed is"+malesql);
        maleList=execReportQuery(malesql);
        int maleCount=Integer.parseInt(maleList.get(0).toString());
        int total=maleCount;
        mainlist.add(total);
        mainlist.add(indicatorId);
        return mainlist;
    }
    public List getListOfHouseholdsServed(String additionalQueryCriteria,String additionalServiceQuery,String ageQuery,boolean active) throws Exception
    {
        List mainlist=new ArrayList();
        List hhIdList=new ArrayList();
        String withdrawnFromProgramQuery="";
        if(active)
        withdrawnFromProgramQuery=util.getCaregiverWithdrawnFromProgramQuery("No");
        List list=new ArrayList();
        String malesql="from HouseholdEnrollment hhe, HouseholdService hhs where hhe.hhUniqueId=hhs.hhUniqueId "+withdrawnFromProgramQuery+" "+additionalServiceQuery;
        System.err.println("malesql in getListOfHouseholdsServed is"+malesql);
        list=execReportQuery(malesql);
        if(list !=null)
        {
            for(Object obj:list)
            {
                Object[] objArray=(Object[])obj;
                HouseholdEnrollment hhe=(HouseholdEnrollment)objArray[0];
                if(hhe !=null && !hhIdList.contains(hhe.getHhUniqueId()))
                {
                    mainlist.add(hhe);
                    hhIdList.add(hhe.getHhUniqueId());
                }
            }
        }
        return mainlist;
    }
    public List getListOfCaregiversWithdrawn(String sex,String additionalQueryCriteria,String withdrawalPeriodQuery,String reasonWithdrawal) throws Exception
    {
        OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl(); 
        additionalQueryCriteria=additionalQueryCriteria+withdrawalPeriodQuery;
        List mainlist=new ArrayList();
        String malesql=additionalQueryCriteria+" and "+util.getCaregiverGenderCriteria(maleGender);
        String femalesql=additionalQueryCriteria+" and "+util.getCaregiverGenderCriteria(femaleGender);
        if(sex.equalsIgnoreCase("male"))
        mainlist.addAll(wdao.getListOfCaregiversWithdrawnPerCohort(malesql,reasonWithdrawal));
        else if(sex.equalsIgnoreCase("female"))
        mainlist.addAll(wdao.getListOfCaregiversWithdrawnPerCohort(femalesql,reasonWithdrawal));
        else
        mainlist.addAll(wdao.getListOfCaregiversWithdrawnPerCohort(additionalQueryCriteria,reasonWithdrawal));
        return mainlist;
    }
    public List getNoOfHivPositiveOvcServed(String indicatorName,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery,String indicatorCode,boolean onART,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        String status=NomisConstant.HIV_POSITIVE;
        //additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        if(!additionalServiceQuery.equals(" "))
            additionalServiceQuery=" and "+additionalServiceQuery;
        String currentlyEnrolledQuery=" ";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
        //String enrolledOnARTQuery=" ";
        
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        mainlist.add(indicatorName);
        //mainlist.add("No of HIV positive VC provided with at least one service");
        String ovcHivQuery=util.getActiveHivStatusCriteria(status,NomisConstant.OVC_TYPE);
        if(onART)
        ovcHivQuery=util.getEnrolledOnARTQueryCriteria(NomisConstant.OVC_TYPE);
        String malesql=serviceCountHivQueryPart+util.getGenderCriteria(maleGender)+currentlyEnrolledQuery+ovcHivQuery+" "+additionalQueryCriteria+ageQuery+additionalServiceQuery;
        String femalesql=serviceCountHivQueryPart+util.getGenderCriteria(femaleGender)+currentlyEnrolledQuery+ovcHivQuery+" "+additionalQueryCriteria+ageQuery+additionalServiceQuery;
        maleList=execReportQuery(malesql);
        femaleList=execReportQuery(femalesql);
        int maleCount=Integer.parseInt(maleList.get(0).toString());
        int femaleCount=Integer.parseInt(femaleList.get(0).toString());
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getNoOfHivNegativeOvcServed(String indicatorName,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery,String indicatorCode,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        String status="negative";
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        if(!additionalServiceQuery.equals(" "))
            additionalServiceQuery=" and "+additionalServiceQuery;
        String currentlyEnrolledQuery=" ";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        mainlist.add(indicatorName);
        String ovcHivQuery=util.getActiveHivStatusCriteria(status,NomisConstant.OVC_TYPE);
        String malesql=serviceCountHivQueryPart+util.getGenderCriteria(maleGender)+currentlyEnrolledQuery+ovcHivQuery+" "+additionalQueryCriteria+ageQuery+additionalServiceQuery;
        String femalesql=serviceCountHivQueryPart+util.getGenderCriteria(femaleGender)+currentlyEnrolledQuery+ovcHivQuery+" "+additionalQueryCriteria+ageQuery+additionalServiceQuery;
        System.err.println("femalesql in getNoOfHivNegativeOvcServed is "+femalesql);
        maleList=execReportQuery(malesql);
        femaleList=execReportQuery(femalesql);
        int maleCount=Integer.parseInt(maleList.get(0).toString());
        int femaleCount=Integer.parseInt(femaleList.get(0).toString());
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        mainlist.addAll(femaleList);
        return mainlist;
    }
    public List getNoOfHivUnknownOvcServed(String indicatorName,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery,String indicatorCode,String status,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        //String status=NomisConstant.HIV_UNKNOWN;
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        if(!additionalServiceQuery.equals(" "))
            additionalServiceQuery=" and "+additionalServiceQuery;
        String currentlyEnrolledQuery=" ";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        mainlist.add(indicatorName);
        String ovcHivQuery=util.getActiveHivStatusCriteria(status,NomisConstant.OVC_TYPE);
        String malesql=serviceCountHivQueryPart+util.getGenderCriteria(maleGender)+currentlyEnrolledQuery+ovcHivQuery+" "+additionalQueryCriteria+ageQuery+additionalServiceQuery;
        String femalesql=serviceCountHivQueryPart+util.getGenderCriteria(femaleGender)+currentlyEnrolledQuery+ovcHivQuery+" "+additionalQueryCriteria+ageQuery+additionalServiceQuery;
        System.err.println("malesql in getNoOfHivUnknownOvcServed is "+malesql);
        maleList=execReportQuery(malesql);
        femaleList=execReportQuery(femalesql);
        int maleCount=Integer.parseInt(maleList.get(0).toString());
        int femaleCount=Integer.parseInt(femaleList.get(0).toString());
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        System.err.println("total in getNoOfHivUnknownOvcServed is "+total);
        return mainlist;
    }
    public List getNumberOfOvcWithoutServiceRecords(String indicatorCode,String indicatorName,String additionalQueryCriteria,String startDate, String endDate,boolean currentlyEnrolled) throws Exception
    {
        OvcServiceDao servicedao=new OvcServiceDaoImpl();
        System.err.println("Inside getNumberOfOvcWithoutServiceRecords");
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
        String maleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+currentlyEnrolledQuery;
        String femaleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender)+currentlyEnrolledQuery;
        int maleCount=servicedao.getNoOfOvcWithoutServiceRecords(maleAdditionalQuery, startDate, endDate);
        int femaleCount=servicedao.getNoOfOvcWithoutServiceRecords(femaleAdditionalQuery,startDate,endDate);
        int totalCount=maleCount+femaleCount;
        List mainList=new ArrayList();
        
        mainList.add(indicatorName);
        mainList.add(maleCount);
        mainList.add(femaleCount);
        mainList.add(totalCount);
        mainList.add(indicatorCode);
        return mainList;
    }
    public List getListOfOvcWithoutServiceRecords(String sex,String additionalQueryCriteria,String startDate, String endDate,boolean currentlyEnrolled) throws Exception
    {
        OvcServiceDao servicedao=new OvcServiceDaoImpl();
        System.err.println("Inside getListOfOvcWithoutServiceRecords");
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
        additionalQueryCriteria=additionalQueryCriteria+currentlyEnrolledQuery;
        String additionalQuery=additionalQueryCriteria;
        if(sex.equalsIgnoreCase("Male"))
        {
            additionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
        }
        else if(sex.equalsIgnoreCase("Female"))
        {
            additionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);
        }
        List mainList=servicedao.getListOfOvcWithoutServiceRecords(additionalQuery,startDate,endDate);       
        return mainList;
    }
    
    public List getNoOfOvcNewlyEnrolledWithoutServiceRecords(String indicatorName,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery,String indicatorCode) throws Exception
    {
        List mainlist=new ArrayList();
        
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery)+util.getOvcWithdrawnFromProgramQuery("No");
        if(!additionalServiceQuery.equals(" "))
            additionalServiceQuery=" and "+additionalServiceQuery;
        //String serviceCriteria=" and ovc.ovcId not in (select distinct service.ovcId from OvcService service "+additionalServiceQuery+" )";
        //mainlist.add("No of VC newly enrolled without service records");
        mainlist.add(indicatorName);
        String malesql=countQueryPart+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery+withdrawalQuery;
        String femalesql=countQueryPart+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery+withdrawalQuery;
        String maleServicesql=serviceCountQueryPart+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery+additionalServiceQuery+withdrawalQuery;
        String femaleServicesql=serviceCountQueryPart+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery+additionalServiceQuery+withdrawalQuery;

        System.err.println("malesql in getNoOfOvcNewlyEnrolledWithoutServiceRecords() is "+malesql);
        System.err.println("maleServicesql in getNoOfOvcNewlyEnrolledWithoutServiceRecords() is "+maleServicesql);

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
        int total=maleOvcWithoutService+femaleOvcWithoutService;
        mainlist.add(maleOvcWithoutService);
        mainlist.add(femaleOvcWithoutService);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        
        return mainlist;
    }
    public void setNoOfServices(String sex,String additionalQueryCriteria,String additionalServiceQuery,String ageQuery) throws Exception
    {
        System.err.println("setting number of services");
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
    public List getNoOfOvcThatReceivedThreeOrMoreServices(String indicatorCode) throws Exception
    {
        List mainlist=new ArrayList();
        mainlist.add("No of VC that received three or more services");
        int maleCount=maleGreaterThanThreeList.size();
        int femaleCount=femaleGreaterThanThreeList.size();
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getNoOfOvcThatReceivedLessThanThreeServices(String indicatorCode) throws Exception
    {
        List mainlist=new ArrayList();
        mainlist.add("No of VC that received less than three services");
        int maleCount=maleLessThanThreeList.size();
        int femaleCount=femaleLessThanThreeList.size();
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        
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
    public List getNoHivPosOvcAtBaseline(String indicatorName,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery,String indicatorCode) throws Exception
    {
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        String status="positive";
        mainlist.add(indicatorName);
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        String malesql=countHivQueryPart+util.getBaselineHivStatusCriteria(status)+" and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String femalesql=countHivQueryPart+util.getBaselineHivStatusCriteria(status)+" and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        System.err.println("malesql is "+malesql);
        maleList=execReportQuery(malesql);
        femaleList=execReportQuery(femalesql);
        int maleCount=Integer.parseInt(maleList.get(0).toString());
        int femaleCount=Integer.parseInt(femaleList.get(0).toString());
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getNoHivNegOvcAtBaseline(String indicatorName,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery,String indicatorCode) throws Exception
    {
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        String status="negative";
        mainlist.add(indicatorName);
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        String malesql=countHivQueryPart+util.getBaselineHivStatusCriteria(status)+" and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String femalesql=countHivQueryPart+util.getBaselineHivStatusCriteria(status)+" and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        
        maleList=execReportQuery(malesql);
        femaleList=execReportQuery(femalesql);
        int maleCount=Integer.parseInt(maleList.get(0).toString());
        int femaleCount=Integer.parseInt(femaleList.get(0).toString());
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        
        return mainlist;
    }
    public List getNoHivUnknownOvcAtBaseline(String indicatorName,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery,String indicatorCode) throws Exception
    {
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        String status="unknown";
        mainlist.add(indicatorName);
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        String malesql=countHivQueryPart+util.getBaselineHivStatusCriteria(status)+" and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String femalesql=countHivQueryPart+util.getBaselineHivStatusCriteria(status)+" and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
       
        maleList=execReportQuery(malesql);
        femaleList=execReportQuery(femalesql);
        int maleCount=Integer.parseInt(maleList.get(0).toString());
        int femaleCount=Integer.parseInt(femaleList.get(0).toString());
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getNoHivPosOvc(String indicatorName,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery,String indicatorCode) throws Exception
    {
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        String status="positive";
        mainlist.add(indicatorName);
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        String ovcHivQuery=util.getActiveHivStatusCriteria(status,NomisConstant.OVC_TYPE);
        String malesql=countHivQueryPart+ovcHivQuery+" and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String femalesql=countHivQueryPart+ovcHivQuery+" and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        System.err.println("malesql is "+malesql);
        maleList=execReportQuery(malesql);
        femaleList=execReportQuery(femalesql);
        int maleCount=Integer.parseInt(maleList.get(0).toString());
        int femaleCount=Integer.parseInt(femaleList.get(0).toString());
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getNoHivNegOvc(String indicatorName,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery,String indicatorCode) throws Exception
    {
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        String status="negative";
        mainlist.add(indicatorName);
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        String ovcHivQuery=util.getActiveHivStatusCriteria(status,NomisConstant.OVC_TYPE);
        String malesql=countHivQueryPart+ovcHivQuery+" and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String femalesql=countHivQueryPart+ovcHivQuery+" and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        
        maleList=execReportQuery(malesql);
        femaleList=execReportQuery(femalesql);
        int maleCount=Integer.parseInt(maleList.get(0).toString());
        int femaleCount=Integer.parseInt(femaleList.get(0).toString());
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        
        return mainlist;
    }
    public List getNoHivUnknownOvc(String indicatorName,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery,String indicatorCode) throws Exception
    {
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        String status="unknown";
        mainlist.add(indicatorName);
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        String ovcHivQuery=util.getActiveHivStatusCriteria(status,NomisConstant.OVC_TYPE);
        String malesql=countHivQueryPart+ovcHivQuery+" and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String femalesql=countHivQueryPart+ovcHivQuery+" and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
       
        maleList=execReportQuery(malesql);
        femaleList=execReportQuery(femalesql);
        int maleCount=Integer.parseInt(maleList.get(0).toString());
        int femaleCount=Integer.parseInt(femaleList.get(0).toString());
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getNoOfOvcCurrentlyEnrolledByHIVStatus(String indicatorName,String additionalQueryCriteria,String ageQuery,String indicatorCode,String hivStatus,boolean currentlyEnrolled)
    {
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        //String status="positive";
        mainlist.add(indicatorName);
        String currentlyEnrolledQuery=" ";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
        additionalQueryCriteria=additionalQueryCriteria+currentlyEnrolledQuery;//rutil.getOvcCurrentlyEnrolledQuery();
        //additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        String ovcHivQuery=util.getActiveHivStatusCriteria(hivStatus,NomisConstant.OVC_TYPE);
        String malesql=countHivQueryPart+ovcHivQuery+" and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery;
        String femalesql=countHivQueryPart+ovcHivQuery+" and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery;
        //System.err.println("malesql in getNoHivPosOvcCurrentlyEnrolled is "+malesql);
        try
        {
            maleList=execReportQuery(malesql);
            femaleList=execReportQuery(femalesql);
            int maleCount=Integer.parseInt(maleList.get(0).toString());
            int femaleCount=Integer.parseInt(femaleList.get(0).toString());
            int total=maleCount+femaleCount;
            mainlist.add(maleCount);
            mainlist.add(femaleCount);
            mainlist.add(total);
            mainlist.add(indicatorCode);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return mainlist;
    }
    public List getNoOfHIVPositiveOvcNewlyEnrolledInCare(String indicatorName,String additionalQueryCriteria,String ageQuery,String indicatorCode,boolean currentlyEnrolled,String periodEnrolledInCareQuery) throws Exception
    {
        List mainlist=getNoOfHIVPositiveOvcEnrolledOrNotEnrolledInCare(indicatorName,additionalQueryCriteria,ageQuery,indicatorCode,currentlyEnrolled,true,periodEnrolledInCareQuery);
        return mainlist;
    }
    public List getNoOfHIVPositiveOvcEnrolledInCare(String indicatorName,String additionalQueryCriteria,String ageQuery,String indicatorCode,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=getNoOfHIVPositiveOvcEnrolledOrNotEnrolledInCare(indicatorName,additionalQueryCriteria,ageQuery,indicatorCode,currentlyEnrolled,true,null);
        return mainlist;
    }
    public List getNoOfHIVPositiveOvcNotEnrolledInCare(String indicatorName,String additionalQueryCriteria,String ageQuery,String indicatorCode,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=getNoOfHIVPositiveOvcEnrolledOrNotEnrolledInCare(indicatorName,additionalQueryCriteria,ageQuery,indicatorCode,currentlyEnrolled,false,null);
        return mainlist;
    }
    public List getNoOfHIVPositiveOvcEnrolledOrNotEnrolledInCare(String indicatorName,String additionalQueryCriteria,String ageQuery,String indicatorCode,boolean currentlyEnrolled,boolean enrolledInCare,String periodEnrolledInCareQuery) throws Exception
    {
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        String hivStatus="positive";
        String enrolledInCareQuery=" and"+util.getHivPositiveEnrolledInCareStatusCriteria("No");
        if(enrolledInCare)
        {
            enrolledInCareQuery=" and"+util.getHivPositiveEnrolledInCareStatusCriteria("Yes");
            if(periodEnrolledInCareQuery !=null)//periodEnrolledInCareQuery
            enrolledInCareQuery=" and"+util.getHivPositiveEnrolledInCareStatusCriteria("Yes")+periodEnrolledInCareQuery;
        }
        mainlist.add(indicatorName);
        if(currentlyEnrolled)
        additionalQueryCriteria=additionalQueryCriteria+" and "+rutil.getOvcCurrentlyEnrolledQuery();
        String ovcHivQuery=util.getActiveHivStatusCriteria(hivStatus,NomisConstant.OVC_TYPE);
        String malesql=countHivQueryPart+ovcHivQuery+enrolledInCareQuery+" and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery;
        String femalesql=countHivQueryPart+ovcHivQuery+enrolledInCareQuery+" and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery;
        System.err.println("malesql in getNoOfPositiveOvcEnrolledInCare is "+malesql);
        maleList=execReportQuery(malesql);
        femaleList=execReportQuery(femalesql);
        int maleCount=Integer.parseInt(maleList.get(0).toString());
        int femaleCount=Integer.parseInt(femaleList.get(0).toString());
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getNoOfHIVPositiveOvcEnrolledOnART(String indicatorName,String additionalQueryCriteria,String ageQuery,String indicatorCode,String startDate,String endDate,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        mainlist.add(indicatorName);
        
        String malesql=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+" "+ageQuery;
        String femalesql=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender)+" "+ageQuery;
                
        int maleCount=util.getHivStatusUpdateDaoInstance().getNumberOfHivPositiveOvcEnrolledOnART(malesql,startDate,endDate,currentlyEnrolled);
        int femaleCount=util.getHivStatusUpdateDaoInstance().getNumberOfHivPositiveOvcEnrolledOnART(femalesql,startDate,endDate,currentlyEnrolled);
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        System.err.println("malesql in  getNoOfHIVPositiveOvcEnrolledOnART is "+malesql);
        return mainlist;
    }
    public List getListOfHIVPositiveOvcEnrolledOnART(String sex,String additionalQueryCriteria,String ageQuery,String indicatorCode,String startDate,String endDate,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        
        String malesql=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+" "+ageQuery;
        String femalesql=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender)+" "+ageQuery;
        String bothGendersql=additionalQueryCriteria+ageQuery;
        if(sex.equalsIgnoreCase("male"))
        mainlist=util.getHivStatusUpdateDaoInstance().getListOfHivPositiveOvcEnrolledOnART(malesql,startDate,endDate,currentlyEnrolled);
        else if(sex.equalsIgnoreCase("female"))
        mainlist=util.getHivStatusUpdateDaoInstance().getListOfHivPositiveOvcEnrolledOnART(femalesql,startDate,endDate,currentlyEnrolled);
        else
        mainlist=util.getHivStatusUpdateDaoInstance().getListOfHivPositiveOvcEnrolledOnART(bothGendersql,startDate,endDate,currentlyEnrolled);
        System.err.println("malesql in  getListOfHIVPositiveOvcEnrolledOnART is "+malesql);
        return mainlist;
    }
    public List getNoOfOvcCurrentlyEnrolledByHIVStatusAndReportPeriod(String indicatorName,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery,String indicatorCode,String hivStatus) throws Exception
    {
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        mainlist.add(indicatorName);
        additionalQueryCriteria=additionalQueryCriteria+" and "+rutil.getOvcCurrentlyEnrolledQuery();
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        String ovcHivQuery=util.getActiveHivStatusCriteria(hivStatus,NomisConstant.OVC_TYPE);
        String malesql=countHivQueryPart+ovcHivQuery+" and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String femalesql=countHivQueryPart+ovcHivQuery+" and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        System.err.println("malesql is "+malesql);
        maleList=execReportQuery(malesql);
        femaleList=execReportQuery(femalesql);
        int maleCount=Integer.parseInt(maleList.get(0).toString());
        int femaleCount=Integer.parseInt(femaleList.get(0).toString());
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getNoOfOvcOutOfSchool(String indicatorName,String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery,String indicatorCode,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        mainlist.add(indicatorName);
        //mainlist.add("No of VC currently enrolled that are out of school");
        ChildSchoolStatusDao dao=new ChildSchoolStatusDaoImpl();
        String maleQueryCriteria=additionalQueryCriteria+ageQuery+" and "+util.getGenderCriteria(maleGender);
        String femaleQueryCriteria=additionalQueryCriteria+ageQuery+" and "+util.getGenderCriteria(femaleGender);
        int maleOvcCount=dao.getNumberOfOvcCurrentlyOutOfSchool(maleQueryCriteria,currentlyEnrolled);
        int femaleOvcCount=dao.getNumberOfOvcCurrentlyOutOfSchool(femaleQueryCriteria,currentlyEnrolled);
        //int maleFuCount=fudao.getNumberOfActiveOVCNotInSchoolAtEnrollmentAndFollowup(maleQueryCriteria);
        //int femaleFuCount=fudao.getNumberOfActiveOVCNotInSchoolAtEnrollmentAndFollowup(femaleQueryCriteria);
        int maleCount=maleOvcCount;//+maleFuCount;
        int femaleCount=femaleOvcCount;//+femaleFuCount;
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        
        return mainlist;
    }
    public List getNoOfOvcCurrentlyInSchool(String indicatorName,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery,String indicatorCode,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        mainlist.add(indicatorName);
        ChildSchoolStatusDao dao=new ChildSchoolStatusDaoImpl();
        
        String maleQueryCriteria=additionalQueryCriteria+ageQuery+" and "+util.getGenderCriteria(maleGender);
        String femaleQueryCriteria=additionalQueryCriteria+ageQuery+" and "+util.getGenderCriteria(femaleGender);
        int maleOvcCount=dao.getNumberOfOvcCurrentlyInSchool(maleQueryCriteria,currentlyEnrolled);
        int femaleOvcCount=dao.getNumberOfOvcCurrentlyInSchool(femaleQueryCriteria,currentlyEnrolled);
        //int maleFuCount=fudao.getNumberOfActiveOVCEnrolledInSchoolAtFollowup(maleQueryCriteria);
        //int femaleFuCount=fudao.getNumberOfActiveOVCEnrolledInSchoolAtFollowup(femaleQueryCriteria);
        int maleCount=maleOvcCount;
        int femaleCount=femaleOvcCount;
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        
        System.err.println("getNoOfOvcCurrentlyInSchool executed");
        return mainlist;
    }
    public List getNoOfOvcWithBirthCertAtBaseline(String indicatorName,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery,String indicatorCode,boolean currentlyEnrolled) throws Exception
    {
        List mainlist=new ArrayList();
        mainlist.add(indicatorName);
        
        BirthRegistrationDao brdao=new BirthRegistrationDaoImpl();
        additionalQueryCriteria=additionalQueryCriteria+getAdditionalEnrollmentQuery(additionalEnrollmentQuery)+ageQuery;
        
        int maleCount=brdao.getNumberOfOvcWithBirthRegistrationAtBaseline(additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender),currentlyEnrolled);
        int femaleCount=brdao.getNumberOfOvcWithBirthRegistrationAtBaseline(additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender),currentlyEnrolled);
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        System.err.println("getTotalNoOfOvcWithBirthCert at baseline executed");
        return mainlist;
    }
    public List getNumberofSeverelyMalnourishedOvcIdentifiedAndServedWithinTheReportPeriod(String indicatorCode,String indicatorName,String additionalQueryCriteria,String startDate,String endDate,boolean curretlyEnrolled)
    {
        NutritionAssessmentDao nadao=new NutritionAssessmentDaoImpl();
        List mainlist=new ArrayList();
        mainlist.add(indicatorName);
        try
        {
            String malesql=" and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery;
            String femalesql=" and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery;

            int maleCount=nadao.getNumberofSeverelyMalnourishedOvcIdentifiedAndServedWithinTheReportPeriod(malesql, startDate, endDate, curretlyEnrolled);
            int femaleCount=nadao.getNumberofSeverelyMalnourishedOvcIdentifiedAndServedWithinTheReportPeriod(femalesql, startDate, endDate, curretlyEnrolled);
            mainlist.add(maleCount);
            mainlist.add(femaleCount);
            mainlist.add(maleCount+femaleCount);
            mainlist.add(indicatorCode);
            //System.err.println("getNoOfOvcServedWithinTheReportPeriodThatHasBirthCert executed");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return mainlist;
    }
    public List getListofSeverelyMalnourishedOvcIdentifiedAndServedWithinTheReportPeriod(String sex,String additionalQueryCriteria,String startDate,String endDate,String ageQuery,boolean currentlyEnrolled) throws Exception
    {
        NutritionAssessmentDao nadao=new NutritionAssessmentDaoImpl();
        List mainlist=new ArrayList();
        
        additionalQueryCriteria=" "+additionalQueryCriteria+ageQuery;
        String malesql=" and "+util.getGenderCriteria(maleGender)+additionalQueryCriteria;
        String femalesql=" and "+util.getGenderCriteria(femaleGender)+additionalQueryCriteria;
        if(sex.equalsIgnoreCase(maleGender))
        additionalQueryCriteria=malesql;
        else if(sex.equalsIgnoreCase(femaleGender))
        additionalQueryCriteria=femalesql;
        else
        additionalQueryCriteria=additionalQueryCriteria;
        mainlist=nadao.getListofSeverelyMalnourishedOvcIdentifiedAndServedWithinTheReportPeriod(additionalQueryCriteria,startDate,endDate,currentlyEnrolled);
        
        System.err.println("getListofSeverelyMalnourishedOvcIdentifiedAndReferredForServicesWithinTheReportPeriod executed");
        return mainlist;
    }
    public List getNoOfOvcServedWithinTheReportPeriodThatHasBirthCert(String indicatorCode,String indicatorName,String additionalQueryCriteria,String ageQuery,String startDate,String endDate,boolean currentlyEnrolled) throws Exception
    {
        OvcServiceDao servicedao=new OvcServiceDaoImpl();
        List mainlist=new ArrayList();
        mainlist.add(indicatorName);
        
        String malesql=" and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery;
        String femalesql=" and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery;
        
        int maleCount=servicedao.getNoOfOvcServedWithinTheReportPeriodThatHasBirthCert(malesql,startDate,endDate,currentlyEnrolled);
        int femaleCount=servicedao.getNoOfOvcServedWithinTheReportPeriodThatHasBirthCert(femalesql,startDate,endDate,currentlyEnrolled);
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(maleCount+femaleCount);
        mainlist.add(indicatorCode);
        System.err.println("getNoOfOvcServedWithinTheReportPeriodThatHasBirthCert executed");
        return mainlist;
    }
    public List getListOfOvcServedWithinTheReportPeriodThatHasBirthCert(String sex,String additionalQueryCriteria,String startDate,String endDate,String ageQuery,boolean currentlyEnrolled) throws Exception
    {
        OvcServiceDao servicedao=new OvcServiceDaoImpl();
        List mainlist=new ArrayList();
        
        additionalQueryCriteria=" "+additionalQueryCriteria+ageQuery;
        String malesql=" and "+util.getGenderCriteria(maleGender)+additionalQueryCriteria;
        String femalesql=" and "+util.getGenderCriteria(femaleGender)+additionalQueryCriteria;
        if(sex.equalsIgnoreCase(maleGender))
        additionalQueryCriteria=malesql;
        else if(sex.equalsIgnoreCase(femaleGender))
        additionalQueryCriteria=femalesql;
        else
        additionalQueryCriteria=additionalQueryCriteria;
        mainlist=servicedao.getListOfOvcServedWithinTheReportPeriodThatHasBirthCert(additionalQueryCriteria,startDate,endDate,currentlyEnrolled);
        
        System.err.println("getListOfOvcServedWithinTheReportPeriodThatHasBirthCert executed");
        return mainlist;
    }
    public List getNoOfOvcProvidedBirthCertService(String indicatorName,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery,String indicatorCode,String pointOfUpdate,boolean currentlyEnrolled) throws Exception
    {
        BirthRegistrationDao brdao=new BirthRegistrationDaoImpl();
        List mainlist=new ArrayList();
        mainlist.add(indicatorName);
        
        String serviceDateQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        serviceDateQuery=serviceDateQuery.replace("ovc.dateEnrollment", "br.dateOfRegistration");
        String malesql=" and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+serviceDateQuery;
        String femalesql=" and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+serviceDateQuery;
        
        int maleCount=brdao.getNumberOfOvcWithBirthRegistrationByPointOfUpdate(malesql,pointOfUpdate,currentlyEnrolled);
        int femaleCount=brdao.getNumberOfOvcWithBirthRegistrationByPointOfUpdate(femalesql,pointOfUpdate,currentlyEnrolled);
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(maleCount+femaleCount);
        mainlist.add(indicatorCode);
        System.err.println("getNoOfOvcProvidedBirthCertService executed");
        return mainlist;
    }
    public List getNoOfOvcWithBirthCertificate(String indicatorName,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery,String indicatorCode,String pointOfUpdate,boolean currentlyEnrolled) throws Exception
    {
        BirthRegistrationDao brdao=new BirthRegistrationDaoImpl();
        List mainlist=new ArrayList();
        mainlist.add(indicatorName);
        
        String malesql=" and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery;
        String femalesql=" and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery;
        
        int maleCount=brdao.getNumberOfOvcWithBirthRegistrationByPointOfUpdate(malesql,pointOfUpdate,currentlyEnrolled);
        int femaleCount=brdao.getNumberOfOvcWithBirthRegistrationByPointOfUpdate(femalesql,pointOfUpdate,currentlyEnrolled);
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(maleCount+femaleCount);
        mainlist.add(indicatorCode);
        System.err.println("getNoOfOvcEnrolledWithBirthCertService executed");
        return mainlist;
    }
    public List getNoOfOvcWithBirthCertAtFollowup(String indicatorName,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery,String indicatorCode) throws Exception
    {
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        mainlist.add(indicatorName);
        
        //String serviceBirthRegQuery=" and (service.serviceAccessed5 is null or UPPER(service.serviceAccessed5) not like '%BIRTH%')";
        String birthregQuery=" and UPPER(fu.updatedBirthCertStatus) ='YES' and "+birthCertQuery;//+serviceBirthRegQuery;
        String followupDateQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        followupDateQuery=followupDateQuery.replace("ovc.dateEnrollment", "fu.dateOfSurvey");
        String malesql="select distinct fu.ovcId "+util.getHouseholdOvcFollowupQueryPart()+" and ovc.birthCertificate='No' and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+followupDateQuery+birthregQuery;
        String femalesql="select distinct fu.ovcId "+util.getHouseholdOvcFollowupQueryPart()+" and ovc.birthCertificate='No' and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+followupDateQuery+birthregQuery;
        
        //System.err.println("malesql in getNoOfOvcWithBirthCertAtFollowup is "+malesql);
        //System.err.println("femalesql in getNoOfOvcWithBirthCertAtFollowup is "+femalesql);
        maleList=execReportQuery(malesql);
        femaleList=execReportQuery(femalesql);
        List serviceResult=getNoOfOvcProvidedBirthCertService(indicatorName,additionalQueryCriteria,additionalEnrollmentQuery,ageQuery,indicatorCode,NomisConstant.FOLLOWUP_POINTOFUPDATE,false);
        List maleServiceResult=(List)serviceResult.get(1);
        List femaleServiceResult=(List)serviceResult.get(2);
        String ovcId=null;
        for(int i=0; i<maleList.size(); i++)
        {
            ovcId=maleList.get(i).toString();
            if(maleServiceResult.contains(ovcId))
            maleList.remove(ovcId);
        }
        for(int i=0; i<femaleList.size(); i++)
        {
            ovcId=femaleList.get(i).toString();
            if(femaleServiceResult.contains(ovcId))
            femaleList.remove(ovcId);
        }
        mainlist.add(maleList);
        mainlist.add(femaleList);
        mainlist.add(indicatorCode);
        System.err.println("getNoOfOvcProvidedBirthCertService executed");
        return mainlist;
    }
    public List getTotalNoOfOvcWithBirthCert(String indicatorName,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery,String indicatorCode) throws Exception
    {
        List mainlist=new ArrayList();
        mainlist.add(indicatorName);
        BirthRegistrationDao brdao=new BirthRegistrationDaoImpl();
        additionalQueryCriteria=additionalQueryCriteria+getAdditionalEnrollmentQuery(additionalEnrollmentQuery)+ageQuery;
        
        int maleCount=brdao.getNumberOfOvcWithBirthRegistration(additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender));
        int femaleCount=brdao.getNumberOfOvcWithBirthRegistration(additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender));
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        System.err.println("getTotalNoOfOvcWithBirthCert executed");
        return mainlist;
    }
    public List getNoOvcWithoutBirthCertAtBaseline(String indicatorName,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery,String indicatorCode) throws Exception
    {
        List mainlist=new ArrayList();
        List maleList=new ArrayList();
        List femaleList=new ArrayList();
        mainlist.add(indicatorName);
        
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        String malesql=countQueryPart+birthCertQuery+" and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        String femalesql=countQueryPart+birthCertQuery+" and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+additionalEnrollmentQuery;
        maleList=execReportQuery(malesql);
        femaleList=execReportQuery(femalesql);
        int maleCount=Integer.parseInt(maleList.get(0).toString());
        int femaleCount=Integer.parseInt(femaleList.get(0).toString());
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        System.err.println("getNoOvcWithoutBirthCert executed");
        return mainlist;
    }
    public List getNoOvcWithoutBirthCertCurrently(String indicatorName,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery,String indicatorCode) throws Exception
    {
        List mainlist=new ArrayList();
        mainlist.add(indicatorName);
        BirthRegistrationDao brdao=new BirthRegistrationDaoImpl();
        additionalQueryCriteria=additionalQueryCriteria+getAdditionalEnrollmentQuery(additionalEnrollmentQuery)+ageQuery+util.getOvcWithdrawnFromProgramQuery("No");
        
        int maleCount=brdao.getNumberOfOvcWithoutBirthRegistration(additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender));
        int femaleCount=brdao.getNumberOfOvcWithoutBirthRegistration(additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender));
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        System.err.println("getNoOvcWithoutBirthCertCurrently executed");
        return mainlist;
        //this.getListOfDistinctOvc(mainlist);
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
    public List getNumberOfOvcProvidedPsychosocialServices(String indicatorCode,String indicatorName,String additionalQueryCriteria,String additionalServiceQuery,String ageQuery,String serviceType) throws Exception
    {
        OvcServiceDao sdao=new OvcServiceDaoImpl();
        List mainlist=new ArrayList();
        additionalServiceQuery=getAdditionalServiceQuery(additionalServiceQuery);
        mainlist.add(indicatorName);
        additionalQueryCriteria=additionalQueryCriteria+ageQuery+additionalServiceQuery;
        String malesql=" and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria;
        String femalesql=" and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria;
        int maleCount=sdao.getNumberOfOvcProvidedPsychosocialSupportServices(malesql,serviceType);
        int femaleCount=sdao.getNumberOfOvcProvidedPsychosocialSupportServices(femalesql,serviceType);
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(maleCount+femaleCount);
        mainlist.add(indicatorCode);
        System.err.println("Inside getNoOfOvcProvidedPsychosocialServices");
        return mainlist;
    }
    public List getNoOfOvcProvidedPsychosocialServices(String indicatorName,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery,String indicatorCode) throws Exception
    {
        List mainlist=new ArrayList();
        additionalServiceQuery=getAdditionalServiceQuery(additionalServiceQuery);
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        String serviceCriteria=" and (service.serviceAccessed1 is not null and service.serviceAccessed1 !='' and service.serviceAccessed1 !=' ' and service.serviceAccessed1 !='  '"+additionalServiceQuery+" )";
        //mainlist.add("No of <b>VC</b> provided <b>Psychosocial</b> services");
        mainlist.add(indicatorName);
        String malesql=serviceCountQueryPart+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+serviceCriteria;
        String femalesql=serviceCountQueryPart+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+serviceCriteria;
        int maleCount=Integer.parseInt(execReportQuery(malesql).get(0).toString());
        int femaleCount=Integer.parseInt(execReportQuery(femalesql).get(0).toString());
        int totalCount=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(totalCount);
        mainlist.add(indicatorCode);
        System.err.println("Inside getNoOfOvcProvidedPsychosocialServices");
        return mainlist;
    }
    public List getNoOfOvcProvidedNutritionalServices(String indicatorName,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery,String indicatorCode) throws Exception
    {
        //OvcServiceDao sdao=new OvcServiceDaoImpl();
        int maleCount=0;
        int femaleCount=0;
        int totalCount=0;
        List mainlist=new ArrayList();
        mainlist.add(indicatorName);
        additionalServiceQuery=getAdditionalServiceQuery(additionalServiceQuery);
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        //String serviceCriteria=" and ovc.ovcId in (select distinct service.ovcId from OvcService service where service.serviceAccessed2 is not null and service.serviceAccessed2 !='' and service.serviceAccessed2 !=' ' and service.serviceAccessed2 !='  '"+additionalServiceQuery+" )";
        String serviceCriteria=" and (service.serviceAccessed2 is not null and service.serviceAccessed2 !='' and service.serviceAccessed2 !=' ' and service.serviceAccessed2 !='  '"+additionalServiceQuery+" )";
        //mainlist.add("No of <b>VC</b> provided <b>Nutritional</b> services");
        String malesql=serviceCountHivQueryPart+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+serviceCriteria;
        String femalesql=serviceCountHivQueryPart+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+serviceCriteria;
        //String bothGendersql="select count(distinct ovc.ovcId) From Ovc ovc, OvcService service where ovc.ovcId=service.ovcId and "+additionalQueryCriteria+ageQuery+serviceCriteria;
        maleCount=Integer.parseInt(execReportQuery(malesql).get(0).toString());
        femaleCount=Integer.parseInt(execReportQuery(femalesql).get(0).toString());
        totalCount=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(totalCount);
        mainlist.add(indicatorCode);
    
    
        System.err.println("Inside getNoOfOvcProvidedNutritionalServices");
        return mainlist;
        
    }
    public List getNumberOfServedByDomainAndHivStatus(String indicatorCode,String indicatorName,String additionalQueryCriteria,String additionalServiceQuery,String domainName,String hivStatus)
    {
        List mainlist=new ArrayList();
        try
        {
            //String lastHivStatusDateQuery=util.getLastHIVStatusDateQuery(lastDateOfHivStatus,"<");
            additionalServiceQuery=getAdditionalServiceQuery(additionalServiceQuery);
            OvcServiceDao sdao=new OvcServiceDaoImpl();
            mainlist.add(indicatorName);
            String ovcHivQuery=util.getActiveHivStatusCriteria(hivStatus,NomisConstant.OVC_TYPE);
            String malesql=ovcHivQuery+" and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+additionalServiceQuery;
            String femalesql=ovcHivQuery+" and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+additionalServiceQuery;

            int maleCount=sdao.getNumberOfServedByDomainAndHivStatus(malesql, domainName);
            int femaleCount=sdao.getNumberOfServedByDomainAndHivStatus(femalesql, domainName);
            int total=maleCount+femaleCount;
            mainlist.add(maleCount);
            mainlist.add(femaleCount);
            mainlist.add(total);
            mainlist.add(indicatorCode);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return mainlist;
    }
    public List getListOfOvcServedByDomainAndHivStatus(String sex,String indicatorCode,String additionalQueryCriteria,String additionalServiceQuery,String domainName,String hivStatus)
    {
        List mainlist=new ArrayList();
        try
        {
            System.err.println("sex and code is "+sex+" "+indicatorCode);
            OvcServiceDao sdao=new OvcServiceDaoImpl();
            additionalServiceQuery=getAdditionalServiceQuery(additionalServiceQuery);
            additionalQueryCriteria=additionalQueryCriteria+ageQuery+additionalServiceQuery;
            String ovcHivQuery=util.getActiveHivStatusCriteria(hivStatus,NomisConstant.OVC_TYPE);
            String malesql=ovcHivQuery+" and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria;
            String femalesql=ovcHivQuery+" and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria;
            String bothGendersql=additionalQueryCriteria+" and "+ovcHivQuery;
            if(sex.equalsIgnoreCase("male"))
            mainlist=sdao.getListOfOvcServedByDomainAndHivStatus(malesql,domainName);
            else if(sex.equalsIgnoreCase("female"))
            mainlist=sdao.getListOfOvcServedByDomainAndHivStatus(femalesql,domainName);
            else
            mainlist=sdao.getListOfOvcServedByDomainAndHivStatus(bothGendersql,domainName);
            mainlist=getListOfDistinctOvcFromQueryObject(mainlist);
            System.err.println("mainlist is "+mainlist.size());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return mainlist;
    }
    public List getNoOfHIVPositiveOvcProvidedNutritionalServices(String indicatorName,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery,String indicatorCode) throws Exception
    {
        List mainlist=new ArrayList();
        String status="positive";
        additionalServiceQuery=getAdditionalServiceQuery(additionalServiceQuery);
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        mainlist.add(indicatorName);
        //String serviceCriteria=" and ovc.ovcId in (select distinct service.ovcId from OvcService service where service.serviceAccessed2 is not null and service.serviceAccessed2 !='' and service.serviceAccessed2 !=' ' and service.serviceAccessed2 !='  '"+additionalServiceQuery+" )";
        String serviceCriteria=" and (service.serviceAccessed2 is not null and service.serviceAccessed2 !='' and service.serviceAccessed2 !=' ' and service.serviceAccessed2 !='  '"+additionalServiceQuery+" )";
        //mainlist.add("No of <b>HIV Positive VC</b> provided <b>Nutritional</b> services");
        String ovcHivQuery=util.getActiveHivStatusCriteria(status,NomisConstant.OVC_TYPE);
        String malesql=serviceCountHivQueryPart+ovcHivQuery+" and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+serviceCriteria;
        String femalesql=serviceCountHivQueryPart+ovcHivQuery+" and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+serviceCriteria;
        System.err.println("Inside getNoOfHIVPositiveOvcProvidedNutritionalServices");
        List maleList=execReportQuery(malesql);
        List femaleList=execReportQuery(femalesql);
        int maleCount=Integer.parseInt(maleList.get(0).toString());
        int femaleCount=Integer.parseInt(femaleList.get(0).toString());
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getNoOfHIVNegativeOvcProvidedNutritionalServices(String indicatorName,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery,String indicatorCode) throws Exception
    {
        List mainlist=new ArrayList();
        String status="negative";
        additionalServiceQuery=getAdditionalServiceQuery(additionalServiceQuery);
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        mainlist.add(indicatorName);
        //String serviceCriteria=" and ovc.ovcId in (select distinct service.ovcId from OvcService service where service.serviceAccessed2 is not null and service.serviceAccessed2 !='' and service.serviceAccessed2 !=' ' and service.serviceAccessed2 !='  '"+additionalServiceQuery+" )";
        String serviceCriteria=" and (service.serviceAccessed2 is not null and service.serviceAccessed2 !='' and service.serviceAccessed2 !=' ' and service.serviceAccessed2 !='  '"+additionalServiceQuery+" )";
        //mainlist.add("No of <b>HIV Negative VC</b> provided <b>Nutritional</b> services");
        String ovcHivQuery=util.getActiveHivStatusCriteria(status,NomisConstant.OVC_TYPE);
        String malesql=serviceCountHivQueryPart+ovcHivQuery+" and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+serviceCriteria;
        String femalesql=serviceCountHivQueryPart+ovcHivQuery+" and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+serviceCriteria;
        //String bothGendersql="select count(distinct ovc.ovcId) From Ovc ovc, OvcService service where ovc.ovcId=service.ovcId and "+util.getHivStatusCriteria(status)+additionalQueryCriteria+ageQuery+serviceCriteria;
        
        List maleList=execReportQuery(malesql);
        List femaleList=execReportQuery(femalesql);
        int maleCount=Integer.parseInt(maleList.get(0).toString());
        int femaleCount=Integer.parseInt(femaleList.get(0).toString());
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        return mainlist;
    }
    
    public List getNoOfHIVUnknownOvcProvidedNutritionalServices(String indicatorName,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery,String indicatorCode) throws Exception
    {
        List mainlist=new ArrayList();
        String status="unknown";
        additionalServiceQuery=getAdditionalServiceQuery(additionalServiceQuery);
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        mainlist.add(indicatorName);
        //String serviceCriteria=" and ovc.ovcId in (select distinct service.ovcId from OvcService service where service.serviceAccessed2 is not null and service.serviceAccessed2 !='' and service.serviceAccessed2 !=' ' and service.serviceAccessed2 !='  '"+additionalServiceQuery+" )";
        String serviceCriteria=" and (service.serviceAccessed2 is not null and service.serviceAccessed2 !='' and service.serviceAccessed2 !=' ' and service.serviceAccessed2 !='  '"+additionalServiceQuery+" )";
        //mainlist.add("No of <b>HIV Unknown VC</b> provided <b>Nutritional</b> services");
        String ovcHivQuery=util.getActiveHivStatusCriteria(status,NomisConstant.OVC_TYPE);
        String malesql=serviceCountHivQueryPart+ovcHivQuery+" and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+serviceCriteria;
        String femalesql=serviceCountHivQueryPart+ovcHivQuery+" and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+serviceCriteria;
        //String bothGendersql="select count(distinct ovc.ovcId) From Ovc ovc, OvcService service where ovc.ovcId=service.ovcId and "+util.getHivStatusCriteria(status)+additionalQueryCriteria+ageQuery+serviceCriteria;
        
        List maleList=execReportQuery(malesql);
        List femaleList=execReportQuery(femalesql);
        int maleCount=Integer.parseInt(maleList.get(0).toString());
        int femaleCount=Integer.parseInt(femaleList.get(0).toString());
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        return mainlist;
    }

    public List getNoOfOvcProvidedHealthServices(String indicatorName,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery,String indicatorCode) throws Exception
    {
        List mainlist=new ArrayList();
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        additionalServiceQuery=getAdditionalServiceQuery(additionalServiceQuery);
        mainlist.add(indicatorName);
        //String serviceCriteria=" and ovc.ovcId in (select distinct service.ovcId from OvcService service where service.serviceAccessed3 is not null and service.serviceAccessed3 !='' and service.serviceAccessed3 !=' ' and service.serviceAccessed3 !='  '"+additionalServiceQuery+" )";
        String serviceCriteria=" and (service.serviceAccessed3 is not null and service.serviceAccessed3 !='' and service.serviceAccessed3 !=' ' and service.serviceAccessed3 !='  '"+additionalServiceQuery+" )";
        //mainlist.add("No of <b>VC</b> provided <b>Health</b> services");
        String malesql=serviceCountHivQueryPart+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+serviceCriteria;
        String femalesql=serviceCountHivQueryPart+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+serviceCriteria;
        List maleList=execReportQuery(malesql);
        List femaleList=execReportQuery(femalesql);
        int maleCount=Integer.parseInt(maleList.get(0).toString());
        int femaleCount=Integer.parseInt(femaleList.get(0).toString());
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        return mainlist;
    }
    public List getNoOfOvcProvidedEducationalServices(String indicatorName,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery,String indicatorCode) throws Exception
    {
        List mainlist=new ArrayList();
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        additionalServiceQuery=getAdditionalServiceQuery(additionalServiceQuery);
        mainlist.add(indicatorName);
        //mainlist.add("No of <b>VC</b> provided <b>Educational</b> services");
        String serviceCriteria=" and (service.serviceAccessed4 is not null and service.serviceAccessed4 !='' and service.serviceAccessed4 !=' ' and service.serviceAccessed4 !='  '"+additionalServiceQuery+" )";
        String malesql=serviceCountQueryPart+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+serviceCriteria;
        String femalesql=serviceCountQueryPart+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+serviceCriteria;
        List maleList=execReportQuery(malesql);
        List femaleList=execReportQuery(femalesql);
        int maleCount=Integer.parseInt(maleList.get(0).toString());
        int femaleCount=Integer.parseInt(femaleList.get(0).toString());
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        
        return mainlist;
    }
    public List getNoOfOvcProvidedProtectionServices(String indicatorName,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery,String indicatorCode) throws Exception
    {
        List mainlist=new ArrayList();
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        additionalServiceQuery=getAdditionalServiceQuery(additionalServiceQuery);
        mainlist.add(indicatorName);
        //mainlist.add("No of <b>VC</b> provided <b>Protection</b> services");
        String serviceCriteria=" and (service.serviceAccessed5 is not null and service.serviceAccessed5 !='' and service.serviceAccessed5 !=' ' and service.serviceAccessed5 !='  '"+additionalServiceQuery+" )";
        String malesql=serviceCountQueryPart+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+serviceCriteria;
        String femalesql=serviceCountQueryPart+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+serviceCriteria;
        List maleList=execReportQuery(malesql);
        List femaleList=execReportQuery(femalesql);
        int maleCount=Integer.parseInt(maleList.get(0).toString());
        int femaleCount=Integer.parseInt(femaleList.get(0).toString());
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        
        return mainlist;
    }
    public List getNoOfOvcProvidedShelterAndCareServices(String indicatorName,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery,String indicatorCode) throws Exception
    {
        List mainlist=new ArrayList();
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        additionalServiceQuery=getAdditionalServiceQuery(additionalServiceQuery);
        mainlist.add(indicatorName);
        //String serviceCriteria=" and ovc.ovcId in (select distinct service.ovcId from OvcService service where service.serviceAccessed6 is not null and service.serviceAccessed6 !='' and service.serviceAccessed6 !=' ' and service.serviceAccessed6 !='  '"+additionalServiceQuery+" )";
        //mainlist.add("No of <b>VC</b> provided <b>Shelter and care</b> services");
        String serviceCriteria=" and (service.serviceAccessed6 is not null and service.serviceAccessed6 !='' and service.serviceAccessed6 !=' ' and service.serviceAccessed6 !='  '"+additionalServiceQuery+" )";
        String malesql=serviceCountQueryPart+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+ageQuery+serviceCriteria;
        String femalesql=serviceCountQueryPart+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+ageQuery+serviceCriteria;
        List maleList=execReportQuery(malesql);
        List femaleList=execReportQuery(femalesql);
        int maleCount=Integer.parseInt(maleList.get(0).toString());
        int femaleCount=Integer.parseInt(femaleList.get(0).toString());
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        
        return mainlist;
    }
    public List getNoOfOvcWithdrawn(String sex,String additionalQueryCriteria,String additionalWithdrawalQuery,String additionalServiceQuery,String ageQuery,String indicatorCode) throws Exception
    {
        List mainlist=new ArrayList();
        if(!additionalWithdrawalQuery.equalsIgnoreCase(" "))
            additionalWithdrawalQuery=" and "+additionalWithdrawalQuery;
        //additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        additionalServiceQuery=getAdditionalServiceQuery(additionalServiceQuery);
        String dataElement="No of <b>VC Withdrawn</b> from the program";
        String subquery="select count(distinct withdrawal.ovcId)"+util.getHouseholdQueryPart()+"Ovc ovc, OvcWithdrawal withdrawal where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=withdrawal.ovcId and withdrawal.type='"+NomisConstant.OVC_TYPE+"' "+additionalWithdrawalQuery;
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
                maleList=execReportQuery(malesql);
                femaleList=execReportQuery(femalesql);
            }
            mainlist.add(dataElement);
            
            int maleCount=Integer.parseInt(maleList.get(0).toString());
            int femaleCount=Integer.parseInt(femaleList.get(0).toString());
            int total=maleCount+femaleCount;
            mainlist.add(maleCount);
            mainlist.add(femaleCount);
            mainlist.add(total);
            mainlist.add(indicatorCode);
        }
        else
        {
            mainlist.add(dataElement);
            String malesql=maleSubSql+" "+additionalQueryCriteria+ageQuery;
            String femalesql=femaleSubSql+" "+additionalQueryCriteria+ageQuery;
            
            maleList=execReportQuery(malesql);
            femaleList=execReportQuery(femalesql);
            int maleCount=Integer.parseInt(maleList.get(0).toString());
            int femaleCount=Integer.parseInt(femaleList.get(0).toString());
            int total=maleCount+femaleCount;
            mainlist.add(maleCount);
            mainlist.add(femaleCount);
            mainlist.add(total);
            mainlist.add(indicatorCode);
        }
        System.err.println("Inside getNoOfOvcWithdrawn");
        return mainlist;
    }
    public List getNoOfOvcAbove18YearsWhoGraduated(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery,String indicatorCode) throws Exception
    {
        List mainlist=new ArrayList();
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        additionalServiceQuery=getAdditionalServiceQuery(additionalServiceQuery);
        String dataElement="No of <b>VC graduated</b> from the program ( > 18)";
        mainlist.add(dataElement);
        String maleSubSql=util.getHouseholdQueryPart()+"Ovc ovc where hhe.hhUniqueId=ovc.hhUniqueId and "+util.getGenderCriteria(maleGender);
        String femaleSubSql=util.getHouseholdQueryPart()+"Ovc ovc where hhe.hhUniqueId=ovc.hhUniqueId and "+util.getGenderCriteria(femaleGender);
        String serviceCriteria=" and ovc.ovcId in (select distinct withdrawal.ovcId from OvcWithdrawal withdrawal where withdrawal.reasonWithdrawal like '%Age > 18%' )";


        if(ageQuery==null)
        {
            List maleList=new ArrayList();
            List femaleList=new ArrayList();
                      
                String malesql=maleSubSql+" "+additionalQueryCriteria+serviceCriteria;
                String femalesql=femaleSubSql+" "+additionalQueryCriteria+serviceCriteria;
                maleList=execReportQuery(malesql);
                femaleList=execReportQuery(femalesql);
                int maleCount=Integer.parseInt(maleList.get(0).toString());
                int femaleCount=Integer.parseInt(femaleList.get(0).toString());
                int total=maleCount+femaleCount;
                mainlist.add(maleCount);
                mainlist.add(femaleCount);
                mainlist.add(total);
                mainlist.add(indicatorCode);
            
        }
        System.err.println("Inside getNoOfOvcAbove18Years");
        return mainlist;
    }
    public List getNoOfOvcSupportedOnHIVAIDSServicesForMSF(String indicator,String additionalQueryCriteria,String[] dateArray,String indicatorCode) throws Exception
    {
        List mainList=new ArrayList();
        MonthlySummaryFormReport msr=new MonthlySummaryFormReport();
        msr.setAgeSegregation(msr.getNewAgeSegregation());                
        List list=msr.getNoOfOvcSupportedOnHIVAIDSServicesForMSF(indicator,additionalQueryCriteria,dateArray);
        if(list !=null && !list.isEmpty())
        {
            mainList.add(list.get(0));
            mainList.add(list.get(7));
            mainList.add(list.get(14));
            mainList.add(list.get(15));
            mainList.add(indicatorCode);
        }
        return mainList;
    }
    public List getNumberOfOvcReferredForHIVCare(String indicatorName,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery,String indicatorCode) throws Exception
    {
        List mainList=new ArrayList();
        
        mainList.add(indicatorName);
        additionalQueryCriteria=additionalQueryCriteria+getAdditionalEnrollmentQuery(additionalEnrollmentQuery)+ageQuery;
                
        String genderQueryCriteria=" and UPPER(ovc.gender)='MALE'";
       int maleCount=serviceDao.getNumberOfOvcReferredForHIVServices(additionalQueryCriteria+genderQueryCriteria);
       genderQueryCriteria=" and UPPER(ovc.gender)='FEMALE'";
       int femaleCount=serviceDao.getNumberOfOvcReferredForHIVServices(additionalQueryCriteria+genderQueryCriteria);
       mainList.add(maleCount);
       mainList.add(femaleCount);
       mainList.add(maleCount+femaleCount);
       mainList.add(indicatorCode);
       return mainList;
    }
    public List getNoOfOvcTestedForHIV(String indicatorName,String additionalQueryCriteria,String startDate,String endDate,String ageQuery,String indicatorCode) throws Exception
    {
        List mainList=new ArrayList();
        //getNumberOfOvcTestedHivPerCohort
        mainList.add(indicatorName);
        //additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        //String servicePeriodQuery=additionalEnrollmentQuery.replace("ovc.dateEnrollment", "service.servicedate");
        additionalQueryCriteria=additionalQueryCriteria+ageQuery;
                
        String genderQueryCriteria=" and UPPER(ovc.gender)='MALE'";
       int maleCount=serviceDao.getNumberOfOvcTestedHivPerCohort(additionalQueryCriteria+genderQueryCriteria,startDate,endDate);
       genderQueryCriteria=" and UPPER(ovc.gender)='FEMALE'";
       int femaleCount=serviceDao.getNumberOfOvcTestedHivPerCohort(additionalQueryCriteria+genderQueryCriteria,startDate,endDate);
       
       //System.err.println("Excuted getNoOfOvcTestedForHIV "+additionalQueryCriteria+genderQueryCriteria);
       mainList.add(maleCount);
       mainList.add(femaleCount);
       mainList.add(maleCount+femaleCount);
       mainList.add(indicatorCode);
       return mainList;
    }
    public List getListOfOvcTestedForHIV(String sex,String additionalQueryCriteria,String ageQuery,String startDate,String endDate,boolean currentlyEnrolled) throws Exception
    {
        String servicePeriodQuery=util.getOvcServiceDateQuery(startDate, endDate);
        additionalQueryCriteria=additionalQueryCriteria+servicePeriodQuery+ageQuery;
        System.err.println("Inside getListOfOvcTestedForHIV");
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
        additionalQueryCriteria=additionalQueryCriteria+currentlyEnrolledQuery;
        String additionalQuery=additionalQueryCriteria;
        
        if(sex.equalsIgnoreCase("Male"))
        {
            additionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
        }
        else if(sex.equalsIgnoreCase("Female"))
        {
            additionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);
        }
        else
        additionalQuery=additionalQueryCriteria;
        
        List mainList=getListOfDistinctOvc(serviceDao.getListOfOvcTestedHivPerCohort(additionalQuery,startDate, endDate));
        return mainList;
    }
    public List getListOfOvcThatAreHIVPositive(String sex,String additionalQueryCriteria,String ageQuery,String startDate,String endDate,boolean currentlyEnrolled) throws Exception
    {
        OvcDao dao=new OvcDaoImpl();
        String servicePeriodQuery=util.getOvcServiceDateQuery(startDate, endDate);
        additionalQueryCriteria=additionalQueryCriteria+servicePeriodQuery+ageQuery;
        System.err.println("Inside getListOfOvcThatAreHIVPositive");
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
        additionalQueryCriteria=additionalQueryCriteria+currentlyEnrolledQuery;
        String additionalQuery=additionalQueryCriteria;
        
        if(sex.equalsIgnoreCase("Male"))
        {
            additionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
        }
        else if(sex.equalsIgnoreCase("Female"))
        {
            additionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);
        }
        else
        additionalQuery=additionalQueryCriteria;
        
        List mainList=getListOfDistinctOvc(dao.getListOfOvcThatAreHIVPositive(additionalQuery));
        return mainList;
    }
    public List getNoOfHIVPositiveOvcIdentifiedAndServedWithinTheReportPeriod(String indicatorCode,String indicatorName,String additionalQueryCriteria,String ageQuery,String startDate,String endDate,boolean currentlyEnrolled,boolean onTreatment) throws Exception
    {
        HivStatusUpdateDao hsudao=new HivStatusUpdateDaoImpl();
        List mainList=new ArrayList();
        
        mainList.add(indicatorName);
        additionalQueryCriteria=additionalQueryCriteria+ageQuery;
        
       String genderQueryCriteria=" and "+util.getGenderCriteria(maleGender);
       int maleCount=hsudao.getNumberOfHivPositiveOvcIdentifiedAndServedWithinTheReportPeriod(additionalQueryCriteria+genderQueryCriteria,startDate,endDate,currentlyEnrolled,onTreatment);
       genderQueryCriteria=" and "+util.getGenderCriteria(femaleGender);;
       int femaleCount=hsudao.getNumberOfHivPositiveOvcIdentifiedAndServedWithinTheReportPeriod(additionalQueryCriteria+genderQueryCriteria,startDate,endDate,currentlyEnrolled,onTreatment);
       mainList.add(maleCount);
       mainList.add(femaleCount);
       mainList.add(maleCount+femaleCount);
       mainList.add(indicatorCode);
       return mainList;
    }
    
    public List getNoOfOvcHIVPositiveOvcIdentifiedWithinTheReportPeriod(String indicatorCode,String indicatorName,String additionalQueryCriteria,String ageQuery,String startDate,String endDate,boolean currentlyEnrolled) throws Exception
    {
        HivStatusUpdateDao hsudao=new HivStatusUpdateDaoImpl();
        List mainList=new ArrayList();
        
        mainList.add(indicatorName);
        additionalQueryCriteria=additionalQueryCriteria+ageQuery;
        
       String genderQueryCriteria=" and "+util.getGenderCriteria(maleGender);
       int maleCount=hsudao.getNumberOfHivPositiveOvcIdentifiedWithinTheReportPeriod(additionalQueryCriteria+genderQueryCriteria,startDate,endDate,currentlyEnrolled);
       genderQueryCriteria=" and "+util.getGenderCriteria(femaleGender);;
       int femaleCount=hsudao.getNumberOfHivPositiveOvcIdentifiedWithinTheReportPeriod(additionalQueryCriteria+genderQueryCriteria,startDate,endDate,currentlyEnrolled);
       mainList.add(maleCount);
       mainList.add(femaleCount);
       mainList.add(maleCount+femaleCount);
       mainList.add(indicatorCode);
       return mainList;
    }
    public List getListOfHIVPositiveOvcIdentifiedWithinTheReport(String sex,String additionalQueryCriteria,String ageQuery,String startDate,String endDate,boolean currentlyEnrolled) throws Exception
    {
        HivStatusUpdateDao hsudao=new HivStatusUpdateDaoImpl();
        
        additionalQueryCriteria=additionalQueryCriteria+ageQuery;
        System.err.println("Inside getListOfHIVPositiveOvcIdentifiedWithinTheReport");
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
        additionalQueryCriteria=additionalQueryCriteria+currentlyEnrolledQuery;
        String additionalQuery=additionalQueryCriteria;
        
        if(sex.equalsIgnoreCase("Male"))
        {
            additionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
        }
        else if(sex.equalsIgnoreCase("Female"))
        {
            additionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);
        }
        else
        additionalQuery=additionalQueryCriteria;
        List mainList=hsudao.getListOfHivPositiveOvcIdentifiedWithinTheReportPeriod(additionalQuery, startDate, endDate, currentlyEnrolled);
        return mainList;
    }
    public List getNoOfOvcThatAreHIVPositive(String indicatorName,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery,String indicatorCode) throws Exception
    {
        //String periodQuery=" and ovc.dateEnrollment between '"+dateArray[0]+"' and '"+dateArray[1]+"'";
        List mainList=new ArrayList();
        
        mainList.add(indicatorName);
        additionalQueryCriteria=additionalQueryCriteria+getAdditionalEnrollmentQuery(additionalEnrollmentQuery)+ageQuery;
        //additionalQueryCriteria=additionalQueryCriteria+additionalEnrollmentQuery+ageQuery;
        
        String genderQueryCriteria=" and UPPER(ovc.gender)='MALE'";
       int maleCount=ovcdao.getNoOfOvcThatAreHIVPositive(additionalQueryCriteria+genderQueryCriteria);
       genderQueryCriteria=" and UPPER(ovc.gender)='FEMALE'";
       int femaleCount=ovcdao.getNoOfOvcThatAreHIVPositive(additionalQueryCriteria+genderQueryCriteria);
       mainList.add(maleCount);
       mainList.add(femaleCount);
       mainList.add(maleCount+femaleCount);
       mainList.add(indicatorCode);
       return mainList;
    }
    public List getNumberOfOvcEnrolledFromHouseholdsWithChronicallyIllMembers(String indicatorName,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery,String indicatorCode) throws Exception
    {
        List mainList=new ArrayList();
        mainList.add(indicatorName);
        additionalQueryCriteria=additionalQueryCriteria+getAdditionalEnrollmentQuery(additionalEnrollmentQuery)+ageQuery;
              
        String genderQueryCriteria=" and UPPER(ovc.gender)='MALE'";
       int maleCount=ovcdao.getNumberOfOvcEnrolledFromHouseholdsWithChronicallyIllMembers(additionalQueryCriteria+genderQueryCriteria);
       genderQueryCriteria=" and UPPER(ovc.gender)='FEMALE'";
       int femaleCount=ovcdao.getNumberOfOvcEnrolledFromHouseholdsWithChronicallyIllMembers(additionalQueryCriteria+genderQueryCriteria);
       mainList.add(maleCount);
       mainList.add(femaleCount);
       mainList.add(maleCount+femaleCount);
       mainList.add(indicatorCode);
       return mainList;
    }
    /*public List getNoOfActiveBeneficiariesServed(String indicator,String additionalQueryCriteria,String[] dateArray,String indicatorCode) throws Exception
    {
        List mainList=new ArrayList();
        MonthlySummaryFormReport msr=new MonthlySummaryFormReport();
        msr.setAgeSegregation(msr.getNewAgeSegregation());                
        List list=msr.getNoOfActiveBeneficiariesServed(indicator,additionalQueryCriteria,dateArray);
        if(list !=null && !list.isEmpty())
        {
            mainList.add(list.get(0));
            mainList.add(list.get(7));
            mainList.add(list.get(14));
            mainList.add(list.get(15));
            mainList.add(indicatorCode);
        }
        return mainList;
    }*/
    public List getNoOfHouseholdsSupportedToAccessHIVCare(String indicator,String additionalQueryCriteria,String[] dateArray,String indicatorCode) throws Exception
    {
        List mainList=new ArrayList();
        MonthlySummaryFormReport msr=new MonthlySummaryFormReport();
        List list=msr.getNoOfHouseholdsSupportedToAccessHIVCare(indicator,additionalQueryCriteria,dateArray);
        if(list !=null && !list.isEmpty())
        {
            mainList.add(list.get(0));
            mainList.add(list.get(4));
            mainList.add(list.get(8));
            mainList.add(list.get(9));
            mainList.add(indicatorCode);
        }       
        return mainList;
    }
    public List getNumberOfOvcWhoAreOverweightAtBaseline(String indicator,String additionalQueryCriteria,String startDate,String endDate,String indicatorCode) throws Exception
    {
        System.err.println("Inside getNumberOfOvcWhoAreOverweightAtBaseline");
        String dateOfAssessment=util.getNutritionalAssessmentDateQuery(startDate, endDate);
        NutritionAssessmentDao nadao=new NutritionAssessmentDaoImpl();
        String maleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+dateOfAssessment+" and na.assessmentNo=1";
        String femaleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender)+dateOfAssessment+" and na.assessmentNo=1";
        /*Baseline assessment has assessmentNo of 1. Do this for male and female OVC*/
        int maleCount=nadao.getNumberOfOvcWhoAreOverweight(maleAdditionalQuery);
        int femaleCount=nadao.getNumberOfOvcWhoAreOverweight(femaleAdditionalQuery);
        int totalCount=maleCount+femaleCount;
        List mainList=new ArrayList();
        
        mainList.add(indicator);
        mainList.add(maleCount);
        mainList.add(femaleCount);
        mainList.add(totalCount);
        mainList.add(indicatorCode);
        return mainList;
    }
    public List getNumberOfOvcThatAreObesseAtBaseline(String indicator,String additionalQueryCriteria,String startDate,String endDate,String indicatorCode) throws Exception
    {
        System.err.println("Inside getNumberOfOvcWhoAreOverweightAtBaseline");
        String dateOfAssessment=util.getNutritionalAssessmentDateQuery(startDate, endDate);
        NutritionAssessmentDao nadao=new NutritionAssessmentDaoImpl();
        String maleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+dateOfAssessment+" and na.assessmentNo=1";
        String femaleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender)+dateOfAssessment+" and na.assessmentNo=1";
        /*Baseline assessment has assessmentNo of 1. Do this for male and female OVC*/
        int maleCount=nadao.getNumberOfOvcThatAreObesse(maleAdditionalQuery);
        int femaleCount=nadao.getNumberOfOvcThatAreObesse(femaleAdditionalQuery);
        int totalCount=maleCount+femaleCount;
        List mainList=new ArrayList();
        
        mainList.add(indicator);
        mainList.add(maleCount);
        mainList.add(femaleCount);
        mainList.add(totalCount);
        mainList.add(indicatorCode);
        return mainList;
    }
    public List getNumberOfOvcThatAreMorbidityObesseAtBaseline(String indicator,String additionalQueryCriteria,String startDate,String endDate,String indicatorCode) throws Exception
    {
        System.err.println("Inside getNumberOfOvcWhoAreOverweightAtBaseline");
        String dateOfAssessment=util.getNutritionalAssessmentDateQuery(startDate, endDate);
        NutritionAssessmentDao nadao=new NutritionAssessmentDaoImpl();
        String maleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+dateOfAssessment+" and na.assessmentNo=1";
        String femaleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender)+dateOfAssessment+" and na.assessmentNo=1";
        /*Baseline assessment has assessmentNo of 1. Do this for male and female OVC*/
        int maleCount=nadao.getNumberOfOvcThatAreMorbidityObesse(maleAdditionalQuery);
        int femaleCount=nadao.getNumberOfOvcThatAreMorbidityObesse(femaleAdditionalQuery);
        int totalCount=maleCount+femaleCount;
        List mainList=new ArrayList();
        
        mainList.add(indicator);
        mainList.add(maleCount);
        mainList.add(femaleCount);
        mainList.add(totalCount);
        mainList.add(indicatorCode);
        return mainList;
    }
    public List getListOfWellNourishedOvcAtBaseline(String sex,String additionalQueryCriteria,String startDate,String endDate,boolean currentRecord,boolean baselineOnly,boolean currentlyEnrolled) throws Exception
    {
        System.err.println("Inside getListOfWellNourishedOvcAtBaseline");
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
        String baselineOnlyQuery="";
        if(baselineOnly)
        baselineOnlyQuery=" and na.assessmentNo=1";
        String currentRecordQuery="";
        if(currentRecord)
        currentRecordQuery=" and na.naRecordStatus=2";
        additionalQueryCriteria=additionalQueryCriteria+currentlyEnrolledQuery+baselineOnlyQuery+currentRecordQuery;
        String additionalQuery=additionalQueryCriteria;
        String dateOfAssessment=util.getNutritionalAssessmentDateQuery(startDate, endDate);
        NutritionAssessmentDao nadao=new NutritionAssessmentDaoImpl();
        if(sex.equalsIgnoreCase("Male"))
        {
            additionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+dateOfAssessment;
        }
        else if(sex.equalsIgnoreCase("Female"))
        {
            additionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender)+dateOfAssessment;
        }
        else
        additionalQuery=additionalQueryCriteria+dateOfAssessment;
        /*Baseline assessment has assessmentNo of 1. Do this for male and female OVC*/
        List mainList=nadao.getListofWellNourishedOvc(additionalQuery);
        return mainList;
    }
    public List getListOfModeratelyNourishedOvcAtBaseline(String sex,String additionalQueryCriteria,String startDate,String endDate,boolean currentRecord,boolean baselineOnly,boolean currentlyEnrolled) throws Exception
    {
        System.err.println("Inside getNumberOfWellNourishedOvcAtBaseline");
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
        String baselineOnlyQuery="";
        /*Baseline assessment has assessmentNo of 1. Do this for male and female OVC*/
        if(baselineOnly)
        baselineOnlyQuery=" and na.assessmentNo=1";
        String currentRecordQuery="";
        if(currentRecord)
        currentRecordQuery=" and na.naRecordStatus=2";
        additionalQueryCriteria=additionalQueryCriteria+currentlyEnrolledQuery+baselineOnlyQuery+currentRecordQuery;
        String additionalQuery=additionalQueryCriteria;
        String dateOfAssessment=util.getNutritionalAssessmentDateQuery(startDate, endDate);
        NutritionAssessmentDao nadao=new NutritionAssessmentDaoImpl();
        if(sex.equalsIgnoreCase("Male"))
        {
            additionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+dateOfAssessment;
        }
        else if(sex.equalsIgnoreCase("Female"))
        {
            additionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender)+dateOfAssessment;
        }
        else
        additionalQuery=additionalQueryCriteria+dateOfAssessment;
        List mainList=nadao.getListofModeratelyMalnourishedOvc(additionalQuery);
        return mainList;
    }
    public List getListOfSeverelyMalnourishedOvcAtBaseline(String sex,String additionalQueryCriteria,String startDate,String endDate,boolean currentRecord,boolean baselineOnly,boolean currentlyEnrolled) throws Exception
    {
        System.err.println("Inside getNumberOfWellNourishedOvcAtBaseline");
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
        String baselineOnlyQuery="";
        if(baselineOnly)
        baselineOnlyQuery=" and na.assessmentNo=1";
        String currentRecordQuery="";
        if(currentRecord)
        currentRecordQuery=" and na.naRecordStatus=2";
        additionalQueryCriteria=additionalQueryCriteria+currentlyEnrolledQuery+baselineOnlyQuery+currentRecordQuery;
        String additionalQuery=additionalQueryCriteria;
        String dateOfAssessment=util.getNutritionalAssessmentDateQuery(startDate, endDate);
        NutritionAssessmentDao nadao=new NutritionAssessmentDaoImpl();
        if(sex.equalsIgnoreCase("Male"))
        {
            additionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+dateOfAssessment;
        }
        else if(sex.equalsIgnoreCase("Female"))
        {
            additionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender)+dateOfAssessment;
        }
        else
        additionalQuery=additionalQueryCriteria+dateOfAssessment;
        /*Baseline assessment has assessmentNo of 1. Do this for male and female OVC*/
        List mainList=nadao.getListofSeverelyMalnourishedOvc(additionalQuery);
        return mainList;
    }
    public List getNumberOfSeverelyMalnourishedOvc(String indicator,String additionalQueryCriteria,String startDate,String endDate,String indicatorCode,boolean currentRecord,boolean baselineOnly,boolean currentlyEnrolled) throws Exception
    {
        System.err.println("Inside getNumberOfSeverelyMalnourishedOvcAtBaseline");
        
        String dateOfAssessment=util.getNutritionalAssessmentDateQuery(startDate, endDate);
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
        String baselineOnlyQuery="";
        if(baselineOnly)
        baselineOnlyQuery=" and na.assessmentNo=1";
        String currentRecordQuery="";
        if(currentRecord)
        currentRecordQuery=" and na.naRecordStatus=2";
        additionalQueryCriteria=additionalQueryCriteria+currentlyEnrolledQuery+baselineOnlyQuery+currentRecordQuery;
        NutritionAssessmentDao nadao=new NutritionAssessmentDaoImpl();
        String maleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+dateOfAssessment;
        String femaleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender)+dateOfAssessment;
        /*Baseline assessment has assessmentNo of 1. Do this for male and female OVC*/
        int maleCount=nadao.getNumberofSeverelyMalnourishedOvc(maleAdditionalQuery);
        int femaleCount=nadao.getNumberofSeverelyMalnourishedOvc(femaleAdditionalQuery);
        int totalCount=maleCount+femaleCount;
        List mainList=new ArrayList();
        
        mainList.add(indicator);
        mainList.add(maleCount);
        mainList.add(femaleCount);
        mainList.add(totalCount);
        mainList.add(indicatorCode);
        return mainList;
    }
    public List getNumberOfModeratelyNourishedOvc(String indicator,String additionalQueryCriteria,String startDate,String endDate,String indicatorCode,boolean currentRecord,boolean baselineOnly,boolean currentlyEnrolled) throws Exception
    {
        System.err.println("Inside getNumberOfModeratelyNourishedOvcAtBaseline");
        String dateOfAssessment=util.getNutritionalAssessmentDateQuery(startDate, endDate);
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
        String baselineOnlyQuery="";
        if(baselineOnly)
        baselineOnlyQuery=" and na.assessmentNo=1";
        String currentRecordQuery="";
        if(currentRecord)
        currentRecordQuery=" and na.naRecordStatus=2";
        additionalQueryCriteria=additionalQueryCriteria+currentlyEnrolledQuery+baselineOnlyQuery+currentRecordQuery;
        NutritionAssessmentDao nadao=new NutritionAssessmentDaoImpl();
        String maleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+dateOfAssessment;
        String femaleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender)+dateOfAssessment;
        /*Baseline assessment has assessmentNo of 1. Do this for male and female OVC*/
        int maleCount=nadao.getNumberofModeratelyMalnourishedOvc(maleAdditionalQuery);
        int femaleCount=nadao.getNumberofModeratelyMalnourishedOvc(femaleAdditionalQuery);
        int totalCount=maleCount+femaleCount;
        List mainList=new ArrayList();
        
        mainList.add(indicator);
        mainList.add(maleCount);
        mainList.add(femaleCount);
        mainList.add(totalCount);
        mainList.add(indicatorCode);
        return mainList;
    }
    public List getNumberOfWellNourishedOvc(String indicator,String additionalQueryCriteria,String startDate,String endDate,String indicatorCode,boolean currentRecord,boolean baselineOnly,boolean currentlyEnrolled) throws Exception
    {
        System.err.println("Inside getNumberOfWellNourishedOvcAtBaseline");
        String dateOfAssessment=util.getNutritionalAssessmentDateQuery(startDate, endDate);
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
        String baselineOnlyQuery="";
        if(baselineOnly)
        baselineOnlyQuery=" and na.assessmentNo=1";
        String currentRecordQuery="";
        if(currentRecord)
        currentRecordQuery=" and na.naRecordStatus=2";
        additionalQueryCriteria=additionalQueryCriteria+currentlyEnrolledQuery+baselineOnlyQuery+currentRecordQuery;
        //additionalQueryCriteria=additionalQueryCriteria+currentlyEnrolledQuery;
        NutritionAssessmentDao nadao=new NutritionAssessmentDaoImpl();
        String maleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+dateOfAssessment;
        String femaleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender)+dateOfAssessment;
        /*Baseline assessment has assessmentNo of 1. Do this for male and female OVC*/
        int maleCount=nadao.getNumberofWellNourishedOvc(maleAdditionalQuery);
        int femaleCount=nadao.getNumberofWellNourishedOvc(femaleAdditionalQuery);
        int totalCount=maleCount+femaleCount;
        List mainList=new ArrayList();
        
        mainList.add(indicator);
        mainList.add(maleCount);
        mainList.add(femaleCount);
        mainList.add(totalCount);
        mainList.add(indicatorCode);
        return mainList;
    }
    public List getNumberOfOvcScreenedForTB(String indicator,String additionalQueryCriteria,String[] dateArray,String indicatorCode) throws Exception
    {
        OvcTBHIVScreeningChecklistDao cgtbdao=new OvcTBHIVScreeningChecklistDaoImpl();
        System.err.println("Inside getNumberOfOvcScreenedForTB");
        String dateQuery="";
        if(dateArray !=null)
        dateQuery=" and tbhivChecklist.dateOfAssessment between '"+dateArray[0]+"' and '"+dateArray[1]+"'";
        String maleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+dateQuery;
        String femaleAdditionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender)+dateQuery;
        //System.err.println(maleAdditionalQuery);
        //System.err.println(femaleAdditionalQuery);
        int maleCount=cgtbdao.getNumberOfOvcTBHIVScreeningChecklist(maleAdditionalQuery);
        int femaleCount=cgtbdao.getNumberOfOvcTBHIVScreeningChecklist(femaleAdditionalQuery);
        int totalCount=maleCount+femaleCount;
        List mainList=new ArrayList();
        
        mainList.add(indicator);
        mainList.add(maleCount);
        mainList.add(femaleCount);
        mainList.add(totalCount);
        mainList.add(indicatorCode);
        return mainList;
    }
    public List getNumberOfCaregiversWithoutServiceRecords(String indicatorCode,String indicatorName,String additionalQueryCriteria,String startDate, String endDate,boolean currentlyEnrolled) throws Exception
    {
        CaregiverDao cgiverdao=new CaregiverDaoImpl();
        System.err.println("Inside getNumberOfCaregiversWithoutServiceRecords");
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getCaregiverWithdrawnFromProgramQuery("No");
        String maleAdditionalQuery=additionalQueryCriteria+" and "+util.getCaregiverGenderCriteria(maleGender)+currentlyEnrolledQuery;
        String femaleAdditionalQuery=additionalQueryCriteria+" and "+util.getCaregiverGenderCriteria(femaleGender)+currentlyEnrolledQuery;
        int maleCount=cgiverdao.getNoOfCaregiversWithoutServiceRecords(maleAdditionalQuery, startDate, endDate);
        int femaleCount=cgiverdao.getNoOfCaregiversWithoutServiceRecords(femaleAdditionalQuery,startDate,endDate);
        int totalCount=maleCount+femaleCount;
        List mainList=new ArrayList();
        
        mainList.add(indicatorName);
        mainList.add(maleCount);
        mainList.add(femaleCount);
        mainList.add(totalCount);
        mainList.add(indicatorCode);
        return mainList;
    }
    public List getListOfCaregiversWithoutServiceRecords(String sex,String additionalQueryCriteria,String startDate, String endDate,boolean currentlyEnrolled) throws Exception
    {
        CaregiverDao cgiverdao=new CaregiverDaoImpl();
        System.err.println("Inside getListOfCaregiversProvidedHES");
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getCaregiverWithdrawnFromProgramQuery("No");
        additionalQueryCriteria=additionalQueryCriteria+currentlyEnrolledQuery;
        String additionalQuery=additionalQueryCriteria;
        if(sex.equalsIgnoreCase("Male"))
        {
            additionalQuery=additionalQueryCriteria+" and "+util.getCaregiverGenderCriteria(maleGender);
        }
        else if(sex.equalsIgnoreCase("Female"))
        {
            additionalQuery=additionalQueryCriteria+" and "+util.getCaregiverGenderCriteria(femaleGender);
        }
        List mainList=cgiverdao.getListOfCaregiversWithoutServiceRecords(additionalQuery,startDate,endDate);       
        return mainList;
    }
    public List getNumberOfCaregiversProvidedHES(String indicatorCode,String indicatorName,String additionalQueryCriteria,String startDate, String endDate,String hesType,boolean currentlyEnrolled) throws Exception
    {
        HouseholdServiceDao hhsdao=new HouseholdServiceDaoImpl();
        System.err.println("Inside getNumberOfCaregiversProvidedHES");
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getCaregiverWithdrawnFromProgramQuery("No");
        String maleAdditionalQuery=additionalQueryCriteria+" and "+util.getCaregiverGenderCriteria(maleGender)+currentlyEnrolledQuery;
        String femaleAdditionalQuery=additionalQueryCriteria+" and "+util.getCaregiverGenderCriteria(femaleGender)+currentlyEnrolledQuery;
        int maleCount=hhsdao.getNumberOfCaregiversProvidedHES(maleAdditionalQuery,startDate,endDate,hesType);
        int femaleCount=hhsdao.getNumberOfCaregiversProvidedHES(femaleAdditionalQuery,startDate,endDate,hesType);
        int totalCount=maleCount+femaleCount;
        List mainList=new ArrayList();
        
        mainList.add(indicatorName);
        mainList.add(maleCount);
        mainList.add(femaleCount);
        mainList.add(totalCount);
        mainList.add(indicatorCode);
        return mainList;
    }
    public List getListOfCaregiversProvidedHES(String sex,String additionalQueryCriteria,String startDate, String endDate,String hesType,boolean currentlyEnrolled) throws Exception
    {
        HouseholdServiceDao hhsdao=new HouseholdServiceDaoImpl();
        System.err.println("Inside getListOfCaregiversProvidedHES");
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getCaregiverWithdrawnFromProgramQuery("No");
        additionalQueryCriteria=additionalQueryCriteria+currentlyEnrolledQuery;
        String additionalQuery=additionalQueryCriteria;
        if(sex.equalsIgnoreCase("Male"))
        {
            additionalQuery=additionalQueryCriteria+" and "+util.getCaregiverGenderCriteria(maleGender);
        }
        else if(sex.equalsIgnoreCase("Female"))
        {
            additionalQuery=additionalQueryCriteria+" and "+util.getCaregiverGenderCriteria(femaleGender);
        }
        List mainList=hhsdao.getListOfCaregiversProvidedHES(additionalQuery,startDate,endDate,hesType);       
        return mainList;
    }
    public List getNumberOfCaregiversTestedForHIV(String indicatorCode,String indicatorName,String additionalQueryCriteria,String startDate, String endDate,boolean currentlyEnrolled) throws Exception
    {
        HouseholdServiceDao hhsdao=new HouseholdServiceDaoImpl();
        System.err.println("Inside getNumberOfCaregiversTestedForHIV");
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getCaregiverWithdrawnFromProgramQuery("No");
        String maleAdditionalQuery=additionalQueryCriteria+" and "+util.getCaregiverGenderCriteria(maleGender)+currentlyEnrolledQuery;
        String femaleAdditionalQuery=additionalQueryCriteria+" and "+util.getCaregiverGenderCriteria(femaleGender)+currentlyEnrolledQuery;
        int maleCount=hhsdao.getNumberOfCaregiversTestedForHiv(maleAdditionalQuery,startDate,endDate);
        int femaleCount=hhsdao.getNumberOfCaregiversTestedForHiv(femaleAdditionalQuery,startDate,endDate);
        int totalCount=maleCount+femaleCount;
        List mainList=new ArrayList();
        
        mainList.add(indicatorName);
        mainList.add(maleCount);
        mainList.add(femaleCount);
        mainList.add(totalCount);
        mainList.add(indicatorCode);
        return mainList;
    }
    public List getListOfCaregiversTestedForHIV(String sex,String additionalQueryCriteria,String startDate, String endDate,boolean currentlyEnrolled) throws Exception
    {
        HouseholdServiceDao hhsdao=new HouseholdServiceDaoImpl();
        System.err.println("Inside getListOfCaregiversTestedForHIV");
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getCaregiverWithdrawnFromProgramQuery("No");
        additionalQueryCriteria=additionalQueryCriteria+currentlyEnrolledQuery;
        String additionalQuery=additionalQueryCriteria;
        if(sex.equalsIgnoreCase("Male"))
        {
            additionalQuery=additionalQueryCriteria+" and "+util.getCaregiverGenderCriteria(maleGender)+currentlyEnrolledQuery;
        }
        else if(sex.equalsIgnoreCase("Female"))
        {
            additionalQuery=additionalQueryCriteria+" and "+util.getCaregiverGenderCriteria(femaleGender)+currentlyEnrolledQuery;
        }
        List mainList=hhsdao.getListOfCaregiversTestedForHiv(additionalQuery,startDate,endDate);       
        return mainList;
    }
    public List getNumberOfCaregiversScreenedForTB(String indicator,String additionalQueryCriteria,String[] dateArray,String indicatorCode) throws Exception
    {
        CaregiverTBHIVScreeningChecklistDao cgtbdao=new CaregiverTBHIVScreeningChecklistDaoImpl();
        System.err.println("Inside getNumberOfCaregiversScreenedForTB");
        String dateQuery="";
        if(dateArray !=null)
        dateQuery=" and tbhivChecklist.dateOfAssessment between '"+dateArray[0]+"' and '"+dateArray[1]+"'";
        
        String maleAdditionalQuery=additionalQueryCriteria+" and "+util.getCaregiverGenderCriteria(maleGender)+dateQuery;
        String femaleAdditionalQuery=additionalQueryCriteria+" and "+util.getCaregiverGenderCriteria(femaleGender)+dateQuery;
        //System.err.println(maleAdditionalQuery);
        //System.err.println(femaleAdditionalQuery);
        int maleCount=cgtbdao.getNumberOfCaregiverTBHIVScreeningChecklist(maleAdditionalQuery);
        int femaleCount=cgtbdao.getNumberOfCaregiverTBHIVScreeningChecklist(femaleAdditionalQuery);
        int totalCount=maleCount+femaleCount;
        List mainList=new ArrayList();
        
        mainList.add(indicator);
        mainList.add(maleCount);
        mainList.add(femaleCount);
        mainList.add(totalCount);
        mainList.add(indicatorCode);
        return mainList;
    }
    public List getListOfOvcScreenedForTB(String sex,String additionalQueryCriteria,String[] dateArray) throws Exception
    {
        System.err.println("Inside getListOfOvcScreenedForTB");
        OvcTBHIVScreeningChecklistDao tbdao=new OvcTBHIVScreeningChecklistDaoImpl();
        String dateQuery="";
        if(dateArray !=null)
        dateQuery=" and tbhivChecklist.dateOfAssessment between '"+dateArray[0]+"' and '"+dateArray[1]+"'";
        String additionalQuery=additionalQueryCriteria+dateQuery;
        if(sex.equalsIgnoreCase("Male"))
        {
            additionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender);
        }
        else if(sex.equalsIgnoreCase("Female"))
        {
            additionalQuery=additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender);
        }
   
        List mainList=tbdao.getListOfOvcScreenedForTBHIV(additionalQuery);       
        return mainList;
    }
    public List getListOfCaregiversScreenedForTB(String sex,String additionalQueryCriteria,String[] dateArray) throws Exception
    {
        CaregiverTBHIVScreeningChecklistDao tbdao=new CaregiverTBHIVScreeningChecklistDaoImpl();
        String dateQuery="";
        if(dateArray !=null)
        dateQuery=" and tbhivChecklist.dateOfAssessment between '"+dateArray[0]+"' and '"+dateArray[1]+"'";
        String additionalQuery=additionalQueryCriteria+dateQuery;
        if(sex.equalsIgnoreCase("Male"))
        {
            additionalQuery=additionalQueryCriteria+" and "+util.getCaregiverGenderCriteria(maleGender);
        }
        else if(sex.equalsIgnoreCase("Female"))
        {
            additionalQuery=additionalQueryCriteria+" and "+util.getCaregiverGenderCriteria(femaleGender);
        }
   
        List mainList=tbdao.getListOfCaregiversScreenedForTBHIV(additionalQuery);       
        return mainList;
    }
    public List getListOfOvcSupportedOnHIVAIDSServicesForMSF(String sex,String additionalQueryCriteria,String[] dateArray) throws Exception
    {
        List mainlist=new ArrayList();
        String serviceCriteria=" ";
        if(dateArray !=null)
        serviceCriteria=" and service.servicedate between '"+dateArray[0]+"' and '"+dateArray[1]+"'";
        String malesql=hhOvcAndServiceQueryPart+" and "+rutil.getHIVServicesReportQuery()+" and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+serviceCriteria;
        String femalesql=hhOvcAndServiceQueryPart+" and "+rutil.getHIVServicesReportQuery()+" and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+serviceCriteria;
        String bothGendersql=hhOvcAndServiceQueryPart+" and "+rutil.getHIVServicesReportQuery()+" "+additionalQueryCriteria+serviceCriteria;
        mainlist= getListOfDistinctOvcFromQueryObject(execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql));
        return mainlist;
    }
    public List getListOfHouseholdsSupportedToAccessHIVCare(String sex,String additionalQueryCriteria,String[] dateArray) throws Exception
    {
        String periodQuery=" ";
        if(dateArray !=null && dateArray.length==2)
        periodQuery="and hhs.serviceDate between '"+dateArray[0]+"' and '"+dateArray[1]+"'";
        List mainlist=new ArrayList();
        String queryPart=hheCaregiverHhsListQuery+" and (hhs.healthServices like '%HIV services%' or hhs.healthServices like '%HIV care and support%')"+periodQuery;
        String malesql=queryPart+" and "+util.getCaregiverGenderCriteria(hviMaleGender)+" "+additionalQueryCriteria;
        String femalesql=queryPart+" and "+util.getCaregiverGenderCriteria(hviFemaleGender)+" "+additionalQueryCriteria;
        String bothGendersql=queryPart+" "+additionalQueryCriteria;
        //System.err.println("malesql in getNoOfHouseholdsSupportedToAccessHIVCare is "+malesql);
        //System.err.println("femalesql in getNoOfHouseholdsSupportedToAccessHIVCare is "+femalesql);
        mainlist=execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql);//getCaregiverList();
        
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
    /*public List getNumberOfOvcProvidedPsychosocialServices(String indicatorCode,String indicatorName,String additionalQueryCriteria,String additionalServiceQuery,String ageQuery,String serviceType) throws Exception
    {
        OvcServiceDao sdao=new OvcServiceDaoImpl();
        List mainlist=new ArrayList();
        additionalServiceQuery=getAdditionalServiceQuery(additionalServiceQuery);
        mainlist.add(indicatorName);
        additionalQueryCriteria=additionalQueryCriteria+ageQuery+additionalServiceQuery;
        String malesql=util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria;
        String femalesql=util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria;
        mainlist.add(sdao.getNumberOfOvcProvidedPsychosocialSupportServices(malesql,serviceType));
        mainlist.add(sdao.getNumberOfOvcProvidedPsychosocialSupportServices(femalesql,serviceType));
        mainlist.add(indicatorCode);
        System.err.println("Inside getNoOfOvcProvidedPsychosocialServices");
        return mainlist;
    }*/
    public List getListOfOvcProvidedPsychosocialSupportServices(String sex,String additionalQueryCriteria,String additionalServiceQuery,String ageQuery,String serviceType) throws Exception
    {
        OvcServiceDao sdao=new OvcServiceDaoImpl();
        List mainlist=new ArrayList();
        additionalServiceQuery=getAdditionalServiceQuery(additionalServiceQuery);
        additionalQueryCriteria=additionalQueryCriteria+ageQuery+additionalServiceQuery;
        String malesql=" and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria;
        String femalesql=" and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria;
        String bothGendersql=additionalQueryCriteria;
        if(sex.equalsIgnoreCase("male"))
        mainlist=sdao.getListOfOvcProvidedPsychosocialSupportServices(malesql,serviceType);
        else if(sex.equalsIgnoreCase("female"))
        mainlist=sdao.getListOfOvcProvidedPsychosocialSupportServices(femalesql,serviceType);
        else
        mainlist=sdao.getListOfOvcProvidedPsychosocialSupportServices(bothGendersql,serviceType);
        mainlist=getListOfDistinctOvc(mainlist);
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
    /*public List getListOfOvcHIVPositiveProvidedNutritionalServices(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        additionalServiceQuery=getAdditionalServiceQuery(additionalServiceQuery);
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        String serviceCriteria=" and service.serviceAccessed2 is not null and service.serviceAccessed2 !='' and service.serviceAccessed2 !=' ' and service.serviceAccessed2 !='  '"+additionalServiceQuery;
        String malesql="select distinct service.ovcId "+hhOvcAndServiceHivQueryPart+additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+" and "+util.getActiveHivStatusCriteria("positive")+ageQuery+serviceCriteria;
        String femalesql="select distinct service.ovcId "+hhOvcAndServiceHivQueryPart+additionalQueryCriteria+" and "+util.getActiveHivStatusCriteria("positive")+ageQuery+serviceCriteria;
        String bothGendersql="select distinct service.ovcId "+hhOvcAndServiceHivQueryPart+additionalQueryCriteria+" and "+util.getActiveHivStatusCriteria("positive")+ageQuery+serviceCriteria;
        mainlist=getListOfOvcByOvcId(execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql));
        return mainlist;
    }
    public List getListOfOvcHIVNegativeProvidedNutritionalServices(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        additionalServiceQuery=getAdditionalServiceQuery(additionalServiceQuery);
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        String serviceCriteria=" and service.serviceAccessed2 is not null and service.serviceAccessed2 !='' and service.serviceAccessed2 !=' ' and service.serviceAccessed2 !='  '"+additionalServiceQuery;
        String malesql="select distinct service.ovcId "+hhOvcAndServiceHivQueryPart+additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+" and "+util.getActiveHivStatusCriteria("negative")+ageQuery+serviceCriteria;
        String femalesql="select distinct service.ovcId "+hhOvcAndServiceHivQueryPart+additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender)+" and "+util.getActiveHivStatusCriteria("negative")+ageQuery+serviceCriteria;
        String bothGendersql="select distinct service.ovcId "+hhOvcAndServiceHivQueryPart+additionalQueryCriteria+" and "+util.getActiveHivStatusCriteria("negative")+ageQuery+serviceCriteria;
        mainlist=getListOfOvcByOvcId(execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql));
        return mainlist;
    }
    public List getListOfOvcHIVUnknownProvidedNutritionalServices(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        additionalServiceQuery=getAdditionalServiceQuery(additionalServiceQuery);
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        String serviceCriteria=" and service.serviceAccessed2 is not null and service.serviceAccessed2 !='' and service.serviceAccessed2 !=' ' and service.serviceAccessed2 !='  '"+additionalServiceQuery;
        String malesql="select distinct service.ovcId "+hhOvcAndServiceHivQueryPart+additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender)+" and "+util.getActiveHivStatusCriteria("unknown")+ageQuery+serviceCriteria;
        String femalesql="select distinct service.ovcId "+hhOvcAndServiceHivQueryPart+additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender)+" and "+util.getActiveHivStatusCriteria("unknown")+ageQuery+serviceCriteria;
        String bothGendersql="select distinct service.ovcId "+hhOvcAndServiceHivQueryPart+additionalQueryCriteria+" and "+util.getActiveHivStatusCriteria("unknown")+ageQuery+serviceCriteria;
        mainlist=getListOfOvcByOvcId(execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql));
        return mainlist;
    }*/
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
    /*public List getListOfOvcWithdrawn(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String additionalServiceQuery,String ageQuery) throws Exception
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
    }*/
    public List getListOfCaregiversNewlyEnrolled(String sex,String additionalQueryCriteria,String cgiverPeriodQuery) throws Exception
    {
        CaregiverDao cgiverdao=new CaregiverDaoImpl();
        List mainlist=new ArrayList();
        additionalQueryCriteria=additionalQueryCriteria+cgiverPeriodQuery;
        String malesql=additionalQueryCriteria+" and "+util.getCaregiverGenderCriteria(maleGender);
        String femalesql=additionalQueryCriteria+" and "+util.getCaregiverGenderCriteria(femaleGender);
        String bothGendersql=additionalQueryCriteria;
        if(sex.equalsIgnoreCase("male"))
        mainlist=cgiverdao.getListOfCaregivers(malesql);
        else if(sex.equalsIgnoreCase("female"))
        mainlist=cgiverdao.getListOfCaregivers(femalesql);
        else
        mainlist=cgiverdao.getListOfCaregivers(bothGendersql);
        //mainlist=execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql);
        //System.err.println("mainlist size is "+mainlist.size());
        return mainlist;
    }
    public List getListOfCaregiversCurrentlyEnrolled(String sex,String additionalQueryCriteria,String cgiverPeriodQuery) throws Exception
    {
        //System.err.println("additionalQueryCriteria in getListOfCaregiversCurrentlyEnrolled is "+additionalQueryCriteria);
        CaregiverDao cgiverdao=new CaregiverDaoImpl();
        List mainlist=new ArrayList();
        String malesql=additionalQueryCriteria+" and "+util.getCaregiverGenderCriteria(maleGender);
        String femalesql=additionalQueryCriteria+" and "+util.getCaregiverGenderCriteria(femaleGender);
        String bothGendersql=additionalQueryCriteria;
        if(sex.equalsIgnoreCase("male"))
        mainlist=cgiverdao.getListOfCaregiversCurrentlyEnrolled(malesql);
        else if(sex.equalsIgnoreCase("female"))
        mainlist=cgiverdao.getListOfCaregiversCurrentlyEnrolled(femalesql);
        else
        mainlist=cgiverdao.getListOfCaregiversCurrentlyEnrolled(bothGendersql);
        
        return mainlist;
    }
    public List getListOfCaregiversEverEnrolled(String sex,String additionalQueryCriteria,String cgiverPeriodQuery) throws Exception
    {
        CaregiverDao cgiverdao=new CaregiverDaoImpl();
        List mainlist=new ArrayList();
        String malesql=additionalQueryCriteria+" and "+util.getCaregiverGenderCriteria(maleGender);
        String femalesql=additionalQueryCriteria+" and "+util.getCaregiverGenderCriteria(femaleGender);
        String bothGendersql=additionalQueryCriteria;
        if(sex.equalsIgnoreCase("male"))
        mainlist=cgiverdao.getListOfCaregivers(malesql);
        else if(sex.equalsIgnoreCase("female"))
        mainlist=cgiverdao.getListOfCaregivers(femalesql);
        else
        mainlist=cgiverdao.getListOfCaregivers(bothGendersql);
        //mainlist=execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql);
        //System.err.println("mainlist size is "+mainlist.size());
        return mainlist;
    }
    public List getListOfCaregiversServed(String sex,String additionalServiceQuery,String ageQuery,boolean newlyEnrolled,boolean active) throws Exception
    {
        String enrollmentQuery="";
        if(newlyEnrolled)
        enrollmentQuery=additionalServiceQuery.replace("hhs.serviceDate", "cgiver.dateOfEnrollment");
        String withdrawnFromProgramQuery="";
        if(active)
        withdrawnFromProgramQuery=util.getCaregiverWithdrawnFromProgramQuery("No");
        List mainlist=new ArrayList();
        String orderByQuery=" order by hhs.numberOfServices desc";
        String malesql="from HouseholdEnrollment hhe, Caregiver cgiver, HouseholdService hhs where hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=hhs.caregiverId and "+util.getCaregiverGenderCriteria(maleGender)+enrollmentQuery+withdrawnFromProgramQuery+" "+additionalServiceQuery+orderByQuery;
        String femalesql="from HouseholdEnrollment hhe, Caregiver cgiver, HouseholdService hhs where hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=hhs.caregiverId and "+util.getCaregiverGenderCriteria(femaleGender)+enrollmentQuery+withdrawnFromProgramQuery+" "+additionalServiceQuery+orderByQuery;
        String bothGendersql="from HouseholdEnrollment hhe, Caregiver cgiver, HouseholdService hhs where hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=hhs.caregiverId "+enrollmentQuery+withdrawnFromProgramQuery+additionalServiceQuery+orderByQuery;
       System.err.println(malesql);
       
        mainlist=execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql);
        //System.err.println("mainlist size is "+mainlist.size());
        return mainlist;
    }
    public List getListOfCaregiversServedButWithdrawn(String sex,String additionalQueryCriteria,String hviPeriodQuery,String additionalServiceQuery,String ageQuery,String reasonWithdrawal) throws Exception
    {
        
        String withdrawnFromProgramQuery=rutil.getCgiverWithdrawnQuery(reasonWithdrawal);
        List mainlist=new ArrayList();
        String orderByQuery=" order by hhs.numberOfServices desc";
        String malesql="from HouseholdEnrollment hhe, Caregiver cgiver, HouseholdService hhs, OvcWithdrawal withdrawal where hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=hhs.caregiverId and cgiver.caregiverId=withdrawal.ovcId and "+util.getCaregiverGenderCriteria(maleGender)+" and "+withdrawnFromProgramQuery+" "+additionalServiceQuery+orderByQuery;
        String femalesql="from HouseholdEnrollment hhe, Caregiver cgiver, HouseholdService hhs, OvcWithdrawal withdrawal where hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=hhs.caregiverId and cgiver.caregiverId=withdrawal.ovcId  and "+util.getCaregiverGenderCriteria(femaleGender)+" and "+withdrawnFromProgramQuery+" "+additionalServiceQuery+orderByQuery;
        String bothGendersql="from HouseholdEnrollment hhe, Caregiver cgiver, HouseholdService hhs, OvcWithdrawal withdrawal where hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=hhs.caregiverId and cgiver.caregiverId=withdrawal.ovcId and "+withdrawnFromProgramQuery+additionalServiceQuery+orderByQuery;
       
        System.err.println(malesql+" additionalQueryCriteria is "+additionalQueryCriteria);
       
        mainlist=execSummStatisticsQuery(sex,malesql,femalesql,bothGendersql);
        //System.err.println("mainlist size is "+mainlist.size());
        return mainlist;
    }
    public List getCaregiverList(List hhObjList)
    {
        List list=new ArrayList();
        if(hhObjList !=null)
        {
            Caregiver cgiver=null;
            for(Object obj:hhObjList)
            {
                Object[] objArray=(Object[])obj;
                cgiver=(Caregiver)objArray[1];
                list.add(cgiver);
            }
        }
        return list;
    }
    public List getTotalNoOfOvcThatShowedAtleastOneScoreImprovement(String indicatorName,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery,String indicatorCode) throws Exception
    {
        List mainlist=new ArrayList();
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        additionalEnrollmentQuery=additionalEnrollmentQuery.replace("ovc.dateEnrollment", "csi.csiDate");
        mainlist.add(indicatorName);
        String malesql="select distinct csi.ovcId, max(csi.totalCsiScore) "+util.getHouseholdQueryPart()+"Ovc ovc,ChildStatusIndex csi where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=csi.ovcId) and csi.surveyNumber > 1 and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+additionalEnrollmentQuery+ageQuery+" group by csi.ovcId";
        String femalesql="select distinct csi.ovcId, max(csi.totalCsiScore) "+util.getHouseholdQueryPart()+"Ovc ovc,ChildStatusIndex csi where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=csi.ovcId) and csi.surveyNumber >1 and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+additionalEnrollmentQuery+ageQuery+" group by csi.ovcId";
        
        int maleCount=NoOfOvcWithImprovedCSIScore(malesql);
        int femaleCount=NoOfOvcWithImprovedCSIScore(femalesql);
        int total=maleCount+femaleCount;
        mainlist.add(maleCount);
        mainlist.add(femaleCount);
        mainlist.add(total);
        mainlist.add(indicatorCode);
        
        System.err.println("Inside getTotalNoOfOvcThatShowedAtleastOneScoreImprovement");
        return mainlist;
    }
    private int NoOfOvcWithImprovedCSIScore(String query)
    {
        System.err.println("query in NoOfOvcWithImprovedCSIScore is "+query);
        List list=new ArrayList();
        List csiList=new ArrayList();
        ChildStatusIndex csi;
        String ovcId;
        int score=0;
        int numberOfOvcWithImprovedScore=0;
        list=execReportQuery(query);
                
        for(int i=0; i<list.size(); i++)
        {
            Object[] obj=(Object[])list.get(i);
            ovcId=obj[0].toString();
            score=Integer.parseInt(obj[1].toString());
            csiList=execReportQuery("From ChildStatusIndex csi where csi.ovcId='"+ovcId+"' and csi.surveyNumber < 2");
            try
            {
                if(csiList.size()>0)
                {
                    
                    for(int j=0; j<csiList.size(); j++)
                    {
                        csi=(ChildStatusIndex)csiList.get(j);
                        if(csi.getTotalCsiScore()<score)
                        {
                            numberOfOvcWithImprovedScore++;
                            break;
                        }
                    }
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        System.err.println("Inside NoOfOvcWithImprovedCSIScore");
        return numberOfOvcWithImprovedScore;
    }
    public List getListOfOvcThatShowedAtleastOneScoreImprovement(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        additionalEnrollmentQuery=getAdditionalEnrollmentQuery(additionalEnrollmentQuery);
        additionalEnrollmentQuery=additionalEnrollmentQuery.replace("ovc.dateEnrollment", "csi.csiDate");
        String sql=" ";
        if(sex.equalsIgnoreCase("male"))
        sql="select distinct csi.ovcId, max(csi.totalCsiScore) "+util.getHouseholdQueryPart()+"Ovc ovc,ChildStatusIndex csi where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=csi.ovcId) and csi.surveyNumber > 1 and "+util.getGenderCriteria(maleGender)+" "+additionalQueryCriteria+additionalEnrollmentQuery+ageQuery+" group by csi.ovcId";
        else if(sex.equalsIgnoreCase("female"))
        sql="select distinct csi.ovcId, max(csi.totalCsiScore) "+util.getHouseholdQueryPart()+"Ovc ovc,ChildStatusIndex csi where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=csi.ovcId) and csi.surveyNumber >1 and "+util.getGenderCriteria(femaleGender)+" "+additionalQueryCriteria+additionalEnrollmentQuery+ageQuery+" group by csi.ovcId";
        else
        sql="select distinct csi.ovcId, max(csi.totalCsiScore) "+util.getHouseholdQueryPart()+"Ovc ovc,ChildStatusIndex csi where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=csi.ovcId) and csi.surveyNumber >1 "+additionalQueryCriteria+additionalEnrollmentQuery+ageQuery+" group by csi.ovcId";
        mainlist=getListOfOvcWithImprovedCSIScore(sql);       
        System.err.println("Inside getListOfOvcThatShowedAtleastOneScoreImprovement");
        return mainlist;
    }
    private List getListOfOvcWithImprovedCSIScore(String query)
    {
        System.err.println("query in getListOfOvcWithImprovedCSIScore is "+query);
        List list=new ArrayList();
        List csiList=new ArrayList();
        List ovcList=new ArrayList();
        OvcDao dao=new OvcDaoImpl();
        ChildStatusIndex csi;
        ChildStatusIndexDao csidao=new ChildStatusIndexDaoImpl();
        String ovcId;
        int score=0;
        list=execReportQuery(query);
                
        for(int i=0; i<list.size(); i++)
        {
            Object[] obj=(Object[])list.get(i);
            ovcId=obj[0].toString();
            score=Integer.parseInt(obj[1].toString());
            csiList=execReportQuery("From ChildStatusIndex csi where csi.ovcId='"+ovcId+"' and csi.surveyNumber < 2");
            try
            {
                if(csiList.size()>0)
                {
                    
                    Ovc ovc=null;
                    for(int j=0; j<csiList.size(); j++)
                    {
                        csi=(ChildStatusIndex)csiList.get(j);
                        if(csi.getTotalCsiScore()<score)
                        {
                            ovc=dao.getOvc(csi.getOvcId());
                            if(ovc !=null)
                            ovcList.add(ovc);
                            else
                            csidao.deleteChildStatusIndex(csi);
                            break;
                        }
                    }
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        return ovcList;
    }
    public List getListOfOvcWithBirthCertCurrently(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        
        BirthRegistrationDao brdao=new BirthRegistrationDaoImpl();
        additionalQueryCriteria=additionalQueryCriteria+getAdditionalEnrollmentQuery(additionalEnrollmentQuery)+ageQuery;
        if(sex.equalsIgnoreCase("male"))
        mainlist=brdao.getListOfOvcWithBirthRegistration(additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender));
        else if(sex.equalsIgnoreCase("female"))
        mainlist=brdao.getListOfOvcWithBirthRegistration(additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender));
        else
        mainlist=brdao.getListOfOvcWithBirthRegistration(additionalQueryCriteria);
        
        System.err.println("getListOfOvcWithBirthCertCurrently executed");
        return mainlist;
        //this.getListOfDistinctOvc(mainlist);
    }
    public List getListOfOvcWithoutBirthCertCurrently(String sex,String additionalQueryCriteria,String additionalEnrollmentQuery,String ageQuery) throws Exception
    {
        List mainlist=new ArrayList();
        
        BirthRegistrationDao brdao=new BirthRegistrationDaoImpl();
        additionalQueryCriteria=additionalQueryCriteria+getAdditionalEnrollmentQuery(additionalEnrollmentQuery)+ageQuery;
        if(sex.equalsIgnoreCase("male"))
        mainlist=brdao.getListOfOvcWithoutBirthRegistration(additionalQueryCriteria+" and "+util.getGenderCriteria(maleGender));
        else if(sex.equalsIgnoreCase("female"))
        mainlist=brdao.getListOfOvcWithoutBirthRegistration(additionalQueryCriteria+" and "+util.getGenderCriteria(femaleGender));
        else
        mainlist=brdao.getListOfOvcWithoutBirthRegistration(additionalQueryCriteria);
        
        System.err.println("getListOfOvcWithoutBirthCertCurrently executed");
        return mainlist;
        //this.getListOfDistinctOvc(mainlist);
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
}
