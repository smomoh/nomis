/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.BirthRegistration;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.HivStatusUpdate;
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.business.OvcService;
import com.fhi.kidmap.report.IndicatorDictionary;
import com.fhi.kidmap.report.ReportUtility;
import com.fhi.nomis.OperationsManagement.HivRecordsManager;
import com.fhi.nomis.OperationsManagement.SchoolAttendanceManager;
import com.fhi.nomis.nomisutils.DateManager;
import com.fhi.nomis.nomisutils.NomisConstant;
import com.fhi.nomis.nomisutils.OvcServiceManager;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


/**
 *
 * @author HP USER
 */
public class OvcServiceDaoImpl implements OvcServiceDao {

    private OvcService ovcService = null;
    private OvcService[] ovcServiceObjects = null;
    Session session;
    Transaction tx;
    SessionFactory sessions;
    DaoUtil util=new DaoUtil();
    ReportUtility rutil=new ReportUtility();
public int getNumberOfOvcUnknownOrNegativeHivStatusServedWithinReportPeriod(String additionalQueryCriteria,String startDate,String endDate,int startAge,int endAge,boolean currentlyEnrolled) throws Exception
{
    List list=null;
    int activeOvc=getNumberOfActiveOvcWithUnknownOrNegativeHivStatusServedWithinReportPeriod(additionalQueryCriteria,startDate,endDate,startAge,endAge,currentlyEnrolled);
    int graduatedOvc=getNumberOfGraduatedOvcWithUnknownOrNegativeHivStatusServedWithinReportPeriod(additionalQueryCriteria,startDate,endDate,startAge,endAge,currentlyEnrolled);
    int numberOfOvc=activeOvc+graduatedOvc;
    /*//ReportUtility rutil=new ReportUtility();
    String currentlyEnrolledQuery="";//util.getOvcWithdrawnFromProgramQuery("No");
    String serviceDateQuery=util.getOvcServiceDateQuery(startDate, endDate);
    String additionalQuery=" and (hsu.hivStatus='negative' or hsu.hivStatus='unknown')";
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query="select count(distinct service.ovcId)"+util.getHouseholdOvcServiceHivReportQueryPart("OvcService")+serviceDateQuery+additionalQueryCriteria+additionalQuery;
        System.err.println("Query in getNumberOfOvcUnknownOrNegativeHivStatusServedWithinReportPeriod() is "+query);
        list = session.createQuery(query).list();
        tx.commit();
        session.close();
    }
    catch (HibernateException he)
    {
        session.close();
        throw new Exception(he);
    }
    if(list !=null && !list.isEmpty())
    {
        numberOfOvc=Integer.parseInt(list.get(0).toString());
    }*/
    return numberOfOvc;
}
public int getNumberOfActiveOvcWithUnknownOrNegativeHivStatusServedWithinReportPeriod(String additionalQueryCriteria,String startDate,String endDate,int startAge,int endAge,boolean currentlyEnrolled) throws Exception
{
    List list=null;
    int numberOfOvc=0;
    //ReportUtility rutil=new ReportUtility();
    String currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
    String serviceDateQuery=util.getOvcServiceDateQuery(startDate, endDate);
    String additionalQuery=" and (hsu.hivStatus='negative' or hsu.hivStatus='unknown')";
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query="select count(distinct service.ovcId)"+util.getHouseholdOvcServiceHivReportQueryPart("OvcService")+serviceDateQuery+additionalQueryCriteria+additionalQuery+currentlyEnrolledQuery;
        System.err.println("Query in getNumberOfOvcUnknownOrNegativeHivStatusServedWithinReportPeriod() is "+query);
        list = session.createQuery(query).list();
        tx.commit();
        session.close();
    }
    catch (HibernateException he)
    {
        session.close();
        throw new Exception(he);
    }
    if(list !=null && !list.isEmpty())
    {
        numberOfOvc=Integer.parseInt(list.get(0).toString());
    }
    return numberOfOvc;
}
public int getNumberOfGraduatedOvcWithUnknownOrNegativeHivStatusServedWithinReportPeriod(String additionalQueryCriteria,String startDate,String endDate,int startAge,int endAge,boolean currentlyEnrolled) throws Exception
{
    List list=null;
    int numberOfOvc=0;
    //ReportUtility rutil=new ReportUtility();
    String currentlyEnrolledQuery="and withdrawal.reasonWithdrawal='"+NomisConstant.GRADUATED+"'";
    String serviceDateQuery=util.getOvcServiceDateQuery(startDate, endDate);
    String additionalQuery=" and (hsu.hivStatus='negative' or hsu.hivStatus='unknown')";
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query="select count(distinct service.ovcId)"+util.getHouseholdOvcServiceHivStatusWithdrawalReportQueryPart("OvcService")+serviceDateQuery+additionalQueryCriteria+additionalQuery+currentlyEnrolledQuery;
        System.err.println("Query in getNumberOfOvcUnknownOrNegativeHivStatusServedWithinReportPeriod() is "+query);
        list = session.createQuery(query).list();
        tx.commit();
        session.close();
    }
    catch (HibernateException he)
    {
        session.close();
        throw new Exception(he);
    }
    if(list !=null && !list.isEmpty())
    {
        numberOfOvc=Integer.parseInt(list.get(0).toString());
    }
    return numberOfOvc;
}
public int getNoOfOvcNewlyEnrolledAndServedWithinTheReportPeriod(String additionalQuery,String sex,int startAge,int endAge,String startDate,String endDate) throws Exception
{
    int count=0;
     try 
     {
         String ageQuery=util.getAgeCriteria(startAge+"", endAge+"");
         String sexQuery="";
         if(sex !=null && (sex.equalsIgnoreCase("Male") || sex.equalsIgnoreCase("Female")))
         sexQuery=" and "+util.getGenderCriteria(sex);
        //select count(distinct service.ovcId) from OvcService service, Ovc ovc,HouseholdEnrollment hhe   where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=service.ovcId) and  UPPER(ovc.gender)='MALE' and ((ovc.currentAge >=0 and ovc.currentAge <=100) or UPPER(ovc.currentAgeUnit) like '%MONTH%')    and  and 
        String query="select count (distinct service.ovcId) "+util.getHouseholdOvcServiceReportQueryPart("OvcService")+additionalQuery+ageQuery+util.getEnrollmentDateQuery(startDate, endDate) +util.getOvcServiceDateQuery(startDate, endDate)+sexQuery;
        System.err.println(query);
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        List list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            count=Integer.parseInt(list.get(0).toString());
        }
    } 
    catch (Exception ex) 
    {
        session.close();
        ex.printStackTrace();
    } 
    return count;
}
public int getNumberOfAdolescentProvidedHivPreventionServices(String additionalQuery, String startDate,String endDate) throws Exception 
{
    int count=0;
     try 
     {
        String query="select count (distinct service.ovcId) "+util.getHouseholdOvcServiceReportQueryPart("OvcService")+util.getAdolescentHivPreventionQuery(NomisConstant.OVC_HIVPREV)+util.getOvcServiceDateQuery(startDate, endDate)+additionalQuery;
        System.err.println(query);
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        List list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            count=Integer.parseInt(list.get(0).toString());
        }
    } 
    catch (Exception ex) 
    {
        session.close();
        ex.printStackTrace();
    } 
    return count;
}
public List getListOfAdolescentProvidedHivPreventionServices(String additionalQuery, String startDate,String endDate) throws Exception 
{
    List ovcList=new ArrayList();
     
     try 
     {
        String query=util.getHouseholdOvcServiceReportQueryPart("OvcService")+util.getAdolescentHivPreventionQuery(NomisConstant.OVC_HIVPREV)+util.getOvcServiceDateQuery(startDate, endDate)+additionalQuery;
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        List list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            List idList=new ArrayList();
            for(Object obj:list)
            {
                Object[] objArray=(Object[])obj;
                Ovc ovc=(Ovc)objArray[1];
                if(!idList.contains(ovc.getOvcId()))
                {
                    ovcList.add(ovc);
                    idList.add(ovc.getOvcId());
                }
            }
        }
    } 
    catch (Exception ex) 
    {
        session.close();
        ex.printStackTrace();
    } 
    return ovcList;
}
public int getNumberOfOvcAbusedOrExploited(String additionalQuery, String startDate,String endDate) throws Exception 
{
    int count=0;
     
     try 
     {
         //+util.getOvcProtectionSupportQuery(NomisConstant.LINKED_TO_GOVT_POSTVIOLENCE) String periodQuery=util.getOvcServiceDateQuery(startDate, endDate);
        String query="select count (distinct service.ovcId) "+util.getHouseholdOvcServiceReportQueryPart("OvcService")+util.getChildAbusedQuery(NomisConstant.CHILDABUSED_NUM)+util.getOvcServiceDateQuery(startDate, endDate)+additionalQuery;
        System.err.println(query);
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        List list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            count=Integer.parseInt(list.get(0).toString());
        }
    } 
    catch (Exception ex) 
    {
        session.close();
        ex.printStackTrace();
    } 
    return count;
}
public List getListOfOvcAbusedOrExploited(String additionalQuery, String startDate,String endDate) throws Exception 
{
    List ovcList=new ArrayList();
     
     try 
     {
        String query=util.getHouseholdOvcServiceReportQueryPart("OvcService")+util.getChildAbusedQuery(NomisConstant.CHILDABUSED_NUM)+util.getOvcServiceDateQuery(startDate, endDate)+additionalQuery;
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        List list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            List idList=new ArrayList();
            for(Object obj:list)
            {
                Object[] objArray=(Object[])obj;
                Ovc ovc=(Ovc)objArray[1];
                if(!idList.contains(ovc.getOvcId()))
                {
                    ovcList.add(ovc);
                    idList.add(ovc.getOvcId());
                }
            }
        }
    } 
    catch (Exception ex) 
    {
        session.close();
        ex.printStackTrace();
    } 
    return ovcList;
}
public int getNumberOfOvcLinkedToGovtForPostViolenceServices(String additionalQuery, String startDate,String endDate) throws Exception 
{
    int count=0;
     
     try 
     {
         //String periodQuery=util.getOvcServiceDateQuery(startDate, endDate);
        //String query="select count (distinct service.ovcId) "+util.getHouseholdOvcServiceReportQueryPart("OvcService")+util.getOvcProtectionSupportQuery(NomisConstant.LINKED_TO_GOVT_POSTVIOLENCE)+util.getOvcServiceDateQuery(startDate, endDate)+additionalQuery;
         String query="select count (distinct service.ovcId) "+util.getHouseholdOvcServiceReportQueryPart("OvcService")+util.getChildAbusedAndLinkedToGovtQuery(NomisConstant.LINKEDTOGOVT_NUM)+util.getOvcServiceDateQuery(startDate, endDate)+additionalQuery;
        System.err.println(query);
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        List list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            count=Integer.parseInt(list.get(0).toString());
        }
    } 
    catch (Exception ex) 
    {
        session.close();
        ex.printStackTrace();
    } 
    return count;
}
public List getListOfOvcLinkedToGovtForPostViolenceServices(String additionalQuery, String startDate,String endDate) throws Exception 
{
    List ovcList=new ArrayList();
     
     try 
     {
        String query=util.getHouseholdOvcServiceReportQueryPart("OvcService")+util.getChildAbusedAndLinkedToGovtQuery(NomisConstant.LINKEDTOGOVT_NUM)+util.getOvcServiceDateQuery(startDate, endDate)+additionalQuery;
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        List list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            List idList=new ArrayList();
            for(Object obj:list)
            {
                Object[] objArray=(Object[])obj;
                Ovc ovc=(Ovc)objArray[1];
                if(!idList.contains(ovc.getOvcId()))
                {
                    ovcList.add(ovc);
                    idList.add(ovc.getOvcId());
                }
            }
        }
    } 
    catch (Exception ex) 
    {
        session.close();
        ex.printStackTrace();
    } 
    return ovcList;
}
public int getNumberOfGraduatedHouseholdsWhoseOvcWereServedWithinReportingPeriod(String additionalQuery,String startDate,String endDate,boolean currentlyEnrolled) throws Exception
{
        int count=0;
        List list = null;
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
        try
        {
            String query="select count(distinct hhe.hhUniqueId) "+util.getHouseholdQueryPart()+" Ovc ovc, OvcService service,OvcWithdrawal withdrawal where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=service.ovcId and hhe.hhUniqueId=withdrawal.ovcId and withdrawal.reasonWithdrawal like '%"+NomisConstant.GRADUATED+"%'"+util.getOvcServiceDateQuery(startDate, endDate)+util.getWithdrawalServicePeriodCriteria(startDate) +additionalQuery+currentlyEnrolledQuery;
            System.err.println(query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(query).list();
            tx.commit();
            closeSession(session);
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        if(list !=null && !list.isEmpty())
        {
            count=Integer.parseInt(list.get(0).toString());
        }
        return count;
}
public int getNumberOfHouseholdsWhoseOvcWereServedWithinReportingPeriod(String additionalQuery,String startDate,String endDate,boolean currentlyEnrolled) throws Exception
{
        int count=0;
        List list = null;
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
        try
        {
            String query="select count(distinct hhe.hhUniqueId) "+util.getHouseholdQueryPart()+" Ovc ovc, OvcService service where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=service.ovcId "+util.getOvcServiceDateQuery(startDate, endDate) +additionalQuery+currentlyEnrolledQuery;
            System.err.println(query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(query).list();
            tx.commit();
            closeSession(session);
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        if(list !=null && !list.isEmpty())
        {
            count=Integer.parseInt(list.get(0).toString());
        }
        return count;
}
public int getNoOfOvcServedWithinTheReportPeriodThatHasBirthCert(String additionalQuery,String startDate,String endDate,boolean currentlyEnrolled) throws Exception
{
        int count=0;
        List list = null;
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
        try
        {
            String query="select count(distinct br.clientId) "+util.getHouseholdQueryPart()+" Ovc ovc, BirthRegistration br,OvcService service where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=br.clientId and br.clientId=service.ovcId and br.recordStatus='active' and br.birthRegistrationStatus='Yes'"+util.getOvcServiceDateQuery(startDate, endDate) +additionalQuery+currentlyEnrolledQuery;
            System.err.println(query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(query).list();
            tx.commit();
            closeSession(session);
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        if(list !=null && !list.isEmpty())
        {
            count=Integer.parseInt(list.get(0).toString());
        }
        return count;
    }
    public List getListOfOvcServedWithinTheReportPeriodThatHasBirthCert(String additionalQuery,String startDate,String endDate,boolean currentlyEnrolled) throws Exception
    {
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
        List ovcList=new ArrayList();
        List list = null;
        try
        {
            String query=util.getHouseholdQueryPart()+" Ovc ovc, BirthRegistration br,OvcService service where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=br.clientId and br.clientId=service.ovcId and br.recordStatus='active' and br.birthRegistrationStatus='Yes'"+additionalQuery+currentlyEnrolledQuery+util.getOvcServiceDateQuery(startDate, endDate) ;
            System.err.println(query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(query).list();
            tx.commit();
            closeSession(session);
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        if(list !=null && !list.isEmpty())
        {
            List idList=new ArrayList();
            Ovc ovc=null;
            for(int i=0; i<list.size(); i++)
            {
                Object[] obj=(Object[])list.get(i);
                ovc=(Ovc)obj[1];
                if(!idList.contains(ovc.getOvcId()))
                {
                    ovcList.add(obj[1]);
                    idList.add(ovc.getOvcId());
                }
            }
        }
        return ovcList;
    }
public List getServiceRecordsWithKnownHivStatusById(String ovcId) throws Exception
{
    List list=null;
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query="from OvcService service where service.ovcId=:id and (service.currentHivStatus like '%"+NomisConstant.HIV_NEGATIVE+"%' or service.currentHivStatus like '%"+NomisConstant.HIV_NEGATIVE+"%')";
        System.err.println(query);
        list = session.createQuery(query).setString("id", ovcId).list();
        tx.commit();
        session.close();
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }
    return list;
}
public List getHTCReferralServiceRecordsFromOvcService() throws Exception
{
    List list=null;
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query="from OvcService service where service.ovcId is not null "+util.getHTCReferralQuery()+" order by service.servicedate desc";
        //System.err.println(query);
        list = session.createQuery(query).list();
        tx.commit();
        session.close();
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }
    return list;
}
public List getHTCReferralServiceRecordById(String ovcId) throws Exception
{
    List list=null;
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query="from OvcService service where service.ovcId=:id and (UPPER(service.serviceAccessed3) like '%HIV%' or UPPER(service.serviceAccessed3) like '%HTC%') order by service.servicedate desc";
        System.err.println(query);
        list = session.createQuery(query).setString("id", ovcId).list();
        tx.commit();
        session.close();
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }
    return list;
}
public int getNumberOfOvcWithdrawnButServedWithinTheReportPeriodByHivStatus(String additionalQuery,String hivStatus,boolean onART, String startDate,String endDate) throws Exception 
{
    int count=0;
     try 
     {
        String hivQuery="";
        if(hivStatus !=null)
        {
            hivQuery=util.getActiveHivStatusCriteria(hivStatus, NomisConstant.OVC_TYPE);
            if(hivStatus.equalsIgnoreCase(NomisConstant.HIV_POSITIVE))
            {
                if(onART)
                hivQuery=util.getEnrolledOnARTQueryCriteria(NomisConstant.OVC_TYPE);
            }
        }
        String query="select count (distinct service.ovcId) "+util.getHouseholdOvcServiceHivStatusWithdrawalReportQueryPart("OvcService")+hivQuery+util.getOvcServiceDateQuery(startDate, endDate)+additionalQuery;
        System.err.println(query);
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        List list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            count=Integer.parseInt(list.get(0).toString());
        }
    } 
    catch (Exception ex) 
    {
        session.close();
        ex.printStackTrace();
    } 
    return count;
}
public List getListOfOvcWithdrawnButServedWithinTheReportPeriodByHivStatus(String additionalQuery,String hivStatus,boolean onART, String startDate,String endDate) throws Exception 
{
     List OvcList=new ArrayList();
     try 
     {
        String hivQuery="";
        if(hivStatus !=null)
        {
            hivQuery=util.getActiveHivStatusCriteria(hivStatus, NomisConstant.OVC_TYPE);
            if(hivStatus.equalsIgnoreCase(NomisConstant.HIV_POSITIVE))
            {
                if(onART)
                hivQuery=util.getEnrolledOnARTQueryCriteria(NomisConstant.OVC_TYPE);
            }
        }
        String query=util.getHouseholdOvcServiceHivStatusWithdrawalReportQueryPart("OvcService")+hivQuery+util.getOvcServiceDateQuery(startDate, endDate)+additionalQuery;
        //System.err.println(query);
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        List list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            List idList=new ArrayList();
            for(Object obj:list)
            {
                Object[] objArray=(Object[])obj;
                Ovc ovc=(Ovc)objArray[1];
                if(!idList.contains(ovc.getOvcId()))
                {
                    OvcList.add(ovc);
                    idList.add(ovc.getOvcId());
                }
            }
        }
    } 
    catch (Exception ex) 
    {
        session.close();
        ex.printStackTrace();
    } 
    return OvcList;
}
public int getNumberOfOvcEnrolledInKidsClub(String additionalQuery, String startDate,String endDate) throws Exception 
{
    int count=0;
     
     try 
     {
         //String periodQuery=util.getOvcServiceDateQuery(startDate, endDate);
        String query="select count (distinct service.ovcId) "+util.getHouseholdOvcServiceReportQueryPart("OvcService")+util.getOvcPsychosocialSupportQuery(NomisConstant.RECREATION_ACTIVITIES)+util.getOvcServiceDateQuery(startDate, endDate)+additionalQuery;
        //System.err.println(query);
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        List list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            count=Integer.parseInt(list.get(0).toString());
        }
    } 
    catch (Exception ex) 
    {
        session.close();
        ex.printStackTrace();
    } 
    return count;
}
public List getListOfOvcEnrolledInKidsClub(String additionalQuery, String startDate,String endDate) throws Exception 
{
    List ovcList=new ArrayList();
     
     try 
     {
         //String periodQuery=util.getOvcServiceDateQuery(startDate, endDate);
        String query=util.getHouseholdOvcServiceReportQueryPart("OvcService")+util.getOvcPsychosocialSupportQuery(NomisConstant.RECREATION_ACTIVITIES)+util.getOvcServiceDateQuery(startDate, endDate)+additionalQuery;
        //System.err.println(query);
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        List list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            List idList=new ArrayList();
            for(Object obj:list)
            {
                Object[] objArray=(Object[])obj;
                Ovc ovc=(Ovc)objArray[1];
                if(!idList.contains(ovc.getOvcId()))
                {
                    ovcList.add(ovc);
                    idList.add(ovc.getOvcId());
                }
            }
        }
    } 
    catch (Exception ex) 
    {
        session.close();
        ex.printStackTrace();
    } 
    return ovcList;
}
public OvcService getHTCReferralServiceRecordById(String ovcId,String startDate) throws Exception
{
    OvcService service=null;
    List list=null;
    String dateQuery="";
    if(startDate !=null && !startDate.equalsIgnoreCase("All"))
    dateQuery=" and service.servicedate is not null and service.servicedate >='"+startDate+"'";
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query="from Ovc ovc, OvcService service where ovc.ovcId=service.ovcId and service.ovcId=:id and (UPPER(service.serviceAccessed3) like '%HIV%' or UPPER(service.serviceAccessed3) like '%HTC%')"+dateQuery;
        //System.err.println(query);
        list = session.createQuery(query).setString("id", ovcId).list();
        tx.commit();
        session.close();
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }
    if(list !=null && !list.isEmpty())
    {
        Object[] objarray=(Object[])list.get(0);
        service=(OvcService)objarray[1];    
    }
    return service;
}
public List getDistinctServiceDatesPerOvc(String ovcId) throws Exception 
{
        
    List list =null;
     try 
     {
         String query="select distinct service.servicedate from OvcService service where service.ovcId=:id order by service.servicedate desc";
        //System.err.println(query);
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery(query).setString("id", ovcId).list();
        tx.commit();
        session.close();
        
    } 
    catch (Exception ex) 
    {
        session.close();
        ex.printStackTrace();
    } 
    return list;
}
public List getOvcServedByHivStatusForReportDownload(String additionalQuery, String indicatorName,String startDate,String endDate,String hivStatus) throws Exception 
{
    List mainList=new ArrayList();
    mainList.add(indicatorName);
    String hivStatusQuery=util.getActiveHivStatusCriteria(hivStatus,NomisConstant.OVC_TYPE);//" and hsu.hivStatus='negative' and hsu.clientType='ovc'";
    String additionalServiceQuery=util.getOvcServiceDateQuery(startDate, endDate);
    List list =null;
     try 
     {
         String query="select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge, ovc.currentAgeUnit,count(distinct service.ovcId) "+util.getHouseholdOvcServiceHivReportQueryPart("OvcService")+hivStatusQuery+additionalQuery+additionalServiceQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,ovc.currentAgeUnit";
        System.err.println(query);
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            mainList.addAll(list);
        }
    } 
    catch (Exception ex) 
    {
        session.close();
        ex.printStackTrace();
    } 
    return mainList;
}
    public List getOvcServedByDomainAndHivStatusForReportDownload(String additionalQuery, String indicatorName,String startDate,String endDate,String domainName,String hivStatus) throws Exception 
{
    List mainList=new ArrayList();
    mainList.add(indicatorName);
    String hivStatusQuery=util.getActiveHivStatusCriteria(hivStatus,NomisConstant.OVC_TYPE);//" and hsu.hivStatus='negative' and hsu.clientType='ovc'";
    String additionalServiceQuery=util.getOvcServiceDateQuery(startDate, endDate);
    List list =null;
     try 
     {
         String query="select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge, ovc.currentAgeUnit,count(distinct service.ovcId) "+util.getHouseholdOvcServiceHivReportQueryPart("OvcService")+hivStatusQuery+additionalQuery+util.getDomainQuery(domainName,null)+additionalServiceQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,ovc.currentAgeUnit";
        System.err.println(query);
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            mainList.addAll(list);
        }
    } 
    catch (Exception ex) 
    {
        session.close();
        ex.printStackTrace();
    } 
    return mainList;
}
public List getDistinctOvcIdForHivRecordsUpdate(boolean knownStatusOnly) throws Exception
    {
        //select distinct s.ovc_id from APP.SERVICE s, APP.HIVSTATUSUPDATE hsu where s.OVC_ID=hsu.CLIENTID and s.SERVICEDATE=hsu.DATEOFCURRENTSTATUS and (s.hivstatus is null and hsu.HIVSTATUS !='unknown' and hsu.POINTOFUPDATE='service')
        String knownStatusOnlyQuery="";
        if(knownStatusOnly)
        {
            knownStatusOnlyQuery=" and hsu.hivStatus is not null and hsu.hivStatus !='"+NomisConstant.HIV_UNKNOWN+"'";
        }
        List list = new ArrayList();
        try 
        {
            String query="select distinct service.ovcId from OvcService service, HivStatusUpdate hsu where service.ovcId=hsu.clientId and service.servicedate=hsu.dateOfCurrentStatus and (service.currentHivStatus is null and hsu.pointOfUpdate='service'"+knownStatusOnlyQuery+")";
            
            System.err.println(query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(query).list();
            tx.commit();
            closeSession(session);
        }
         catch (HibernateException he)
         {
             closeSession(session);
             throw new Exception(he);
         }
         return list;
    }
public int getNumberOfServedByDomainAndHivStatus(String additionalQuery, String domainName) throws Exception 
{
    int count=0;
     
     try 
     {
        String query="select count (distinct service.ovcId) "+util.getHouseholdOvcServiceHivReportQueryPart("OvcService")+additionalQuery+util.getDomainQuery(domainName,null);
        //System.err.println(query);
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        List list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            count=Integer.parseInt(list.get(0).toString());
        }
    } 
    catch (Exception ex) 
    {
        session.close();
        ex.printStackTrace();
    } 
    return count;
}
public List getListOfOvcServedByDomainAndHivStatus(String additionalQuery, String domainName) throws Exception 
{
     List list = new ArrayList();
     try 
     {
         String query=util.getHouseholdOvcServiceHivReportQueryPart("OvcService")+additionalQuery+util.getDomainQuery(domainName,null);
         //System.err.println("query is "+query);
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery(query).list();
        tx.commit();
        session.close();
    } 
    catch (Exception ex) 
    {
        session.close();
        ex.printStackTrace();
    } 
    return list;
}
public OvcService getOvcServiceWithoutHivInformation(String ovcId, String serviceDate) throws Exception 
{
    OvcService service=null; 
    List list = new ArrayList();
     try 
     {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
    list = session.createQuery("from OvcService service where service.ovcId = :id and service.servicedate = :sd and service.currentHivStatus is null").setString("id", ovcId)
                       .setString("sd", serviceDate)
                       .list();
    tx.commit();
    session.close();
    } 
    catch (Exception ex) 
    {
        session.close();
        ex.printStackTrace();
    }
     if(list.size()>0)
     {
       service = (OvcService)list.get(0);
     }
     
    return service;
}
public List getServiceRecordsWithHivInformation() throws Exception
{
    List list=null;
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query="from OvcService service where service.currentHivStatus is not null";
        System.err.println(query);
        list = session.createQuery(query).list();
        tx.commit();
        session.close();
        
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }   
    return list;
}
public OvcService getLastServiceForOvc(String ovcId) throws Exception
{
    OvcService service=null;
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query="select distinct service.servicedate from OvcService service where service.ovcId=:id order by service.servicedate desc";
        System.err.println(query);
        List list = session.createQuery(query).setString("id", ovcId).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            String serviceDate=(String)list.get(0);
            service=getOvcServiceForTheMth(ovcId, serviceDate);
        }
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }   
    return service;
}
public List getListOfOvcWithoutServiceRecords(String additionalQuery,String startDate,String endDate) throws Exception
{
    List list = new ArrayList();
    List ovcNotServedList=new ArrayList();
    List uniqueIdList=new ArrayList();
          
    try
    {
        String query=util.getHouseholdOvcQueryPart()+additionalQuery+util.getEnrollmentEndDateQuery(endDate);
        //System.err.println("query in getListOfCaregiversWithoutServiceRecords is "+query);
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            System.err.println("list size is "+list.size());
            String ovcId=null;
            for(Object obj:list)
            {
                Object[] objArray=(Object[])obj;
                Ovc ovc=(Ovc)objArray[1];
                if(ovc !=null)
                {
                    ovcId=ovc.getOvcId();
                    if(uniqueIdList.contains(ovcId))
                    continue;
                    uniqueIdList.add(ovcId);
                    List ovcList=getOvcServicesByOvcIdAndPeriod(ovcId, startDate, endDate);
                    if(ovcList==null || ovcList.isEmpty())
                    {
                      ovcNotServedList.add(ovc);  
                    }
                }
            }
        }
    }
    catch(Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }
    return ovcNotServedList;
}
public int getNoOfOvcWithoutServiceRecords(String additionalQuery,String startDate,String endDate) throws Exception
{
    List list = new ArrayList();
    int numberOfOvc=0;
    try
    {
        String query="select distinct ovc.ovcId "+util.getHouseholdOvcQueryPart()+additionalQuery+util.getEnrollmentEndDateQuery(endDate);
        System.err.println("query in getNoOfOvcWithoutServiceRecords is "+query);
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            System.err.println("list size in getNoOfOvcWithoutServiceRecords is "+list.size());
            String ovcId=null;
            for(int i=0; i<list.size(); i++)
            {
                ovcId=(String)list.get(i);
                List ovcList=getOvcServicesByOvcIdAndPeriod(ovcId, startDate, endDate);
                if(ovcList==null || ovcList.isEmpty())
                {
                  numberOfOvc++;  
                }
            }
            //numberOfCaregivers=Integer.parseInt(list.get(0).toString());
        }
    }
    catch(Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }

    return numberOfOvc;
}
public List saveHivStatusOfOvcUnknownProvidedHTC() throws Exception
{
    List list=null;
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query="from OvcService service, HivStatusUpdate hsu where service.ovcId=hsu.clientId "+util.getActiveHivStatusCriteria("unknown",NomisConstant.OVC_TYPE)+" and (UPPER(service.serviceAccessed3) like '%HIV%' or UPPER(service.serviceAccessed3) like '%HTC%')";
        System.err.println(query);
        list = session.createQuery(query).list();
        tx.commit();
        session.close();
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
        //throw new Exception(he);
    }
    if(list !=null && !list.isEmpty())
    {
        List idList=new ArrayList();
        for(Object obj:list)
        {
            Object[] objarray=(Object[])obj;
            OvcService service=(OvcService)objarray[0];
            if(idList.contains(service.getOvcId()))
            continue;
            service.setCurrentHivStatus("negative");
            saveOvcHIVStatus(service);
            idList.add(service.getOvcId());
            //System.err.println("HIV status with Service Id "+service.getOvcId()+" saved");
        }
    }
    return list;
}
public int getNumberOfOvcProvidedBirthRegistrationServices(String additionalQueryCriteria) throws Exception
{
    List list=null; 
    int count=0;   
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("select count(distinct service.ovcId)"+util.getHouseholdOvcServiceReportQueryPart("OvcService")+" and (service.serviceAccessed5 is not null and UPPER(service.serviceAccessed5) like '%BIRTH%')"+additionalQueryCriteria).list();
        tx.commit();
        session.close();
    }
    catch (HibernateException he)
    {
        session.close();
        throw new Exception(he);
    }
    if(list !=null && !list.isEmpty())
    {
        count=Integer.parseInt(list.get(0).toString());
    }
    return count;
}
public List getServiceRecordsWithBirthRegistrationDetails(String additionalQueryCriteria) throws Exception
{
    List list=null; 
    List serviceList=new ArrayList();
        
    try
    {
        String query=util.getHouseholdOvcBirthRegistrationServiceReportQueryPart("OvcService")+additionalQueryCriteria+" and (br.birthRegistrationStatus ='No' and br.recordStatus='active' and br.pointOfUpdate='enrollment') and (service.serviceAccessed5 is not null and (UPPER(service.serviceAccessed5) like '%BIRTH%' or service.serviceAccessed5 like '%3pt%'))";
        System.err.println("query in getServiceRecordsWithBirthRegistrationDetails is "+query);
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery(query).list();
        tx.commit();
        session.close();
    }
    catch (HibernateException he)
    {
        session.close();
        throw new Exception(he);
    }
    if(list !=null && !list.isEmpty())
    {
        for(int i=0; i<list.size(); i++)
        {
            Object[] obj=(Object[])list.get(i);
            serviceList.add(obj[2]);
        }
    }
    return serviceList;
}
public int getNumberOfOvcReferredForHIVServices(String additionalQueryCriteria) throws Exception
{
    List list=null;
    int numberOfOvc=0;
    ReportUtility rutil=new ReportUtility();
    
    String additionalQuery=" and "+rutil.getHIVServicesReportQuery();
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query="select count(distinct service.ovcId)"+util.getHouseholdOvcServiceReportQueryPart("OvcService")+additionalQueryCriteria+additionalQuery;
        //System.err.println(query);
        list = session.createQuery(query).list();
        tx.commit();
        session.close();
    }
    catch (HibernateException he)
    {
        session.close();
        throw new Exception(he);
    }
    if(list !=null && !list.isEmpty())
    {
        numberOfOvc=Integer.parseInt(list.get(0).toString());
    }
    return numberOfOvc;
}
public int getNumberOfOvcTestedHivPerCohort(String additionalQueryCriteria,String startDate,String endDate) throws Exception
{
    List list=null;
    int numberOfOvc=0;
    //ReportUtility rutil=new ReportUtility();
    String currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
    String serviceDateQuery=util.getOvcServiceDateQuery(startDate, endDate);
    String additionalQuery=" and "+rutil.getHIVServicesReportQuery();
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query="select count(distinct service.ovcId)"+util.getHouseholdOvcServiceReportQueryPart("OvcService")+serviceDateQuery+additionalQueryCriteria+additionalQuery+currentlyEnrolledQuery;
        System.err.println("Query in getNumberOfOvcTestedHivPerCohort() is "+query);
        list = session.createQuery(query).list();
        tx.commit();
        session.close();
    }
    catch (HibernateException he)
    {
        session.close();
        throw new Exception(he);
    }
    if(list !=null && !list.isEmpty())
    {
        numberOfOvc=Integer.parseInt(list.get(0).toString());
    }
    return numberOfOvc+getNumberOfOvcWithdrawnFromProgramTestedHiv(additionalQueryCriteria,startDate,endDate);
}
public int getNumberOfOvcWithdrawnFromProgramTestedHiv(String additionalQueryCriteria,String startDate,String endDate) throws Exception
{
    List list=null;
    int numberOfOvc=0;
    //ReportUtility rutil=new ReportUtility();
    String serviceDateQuery=util.getOvcServiceDateQuery(startDate, endDate);
    String additionalQuery=" and "+rutil.getHIVServicesReportQuery();
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query="select count(distinct service.ovcId)"+util.getHouseholdOvcServiceWithdrawalReportQueryPart("OvcService")+additionalQueryCriteria+serviceDateQuery+util.getWithdrawalServicePeriodCriteria(startDate)+additionalQuery;
        System.err.println("Query in getNumberOfOvcWithdrawnFromProgramTestedHiv(--) is "+query);
        list = session.createQuery(query).list();
        tx.commit();
        session.close();
    }
    catch (HibernateException he)
    {
        session.close();
        throw new Exception(he);
    }
    if(list !=null && !list.isEmpty())
    {
        numberOfOvc=Integer.parseInt(list.get(0).toString());
    }
    return numberOfOvc;
}
public List getListOfOvcTestedHivPerCohort(String additionalQueryCriteria,String startDate,String endDate) throws Exception
{
    List mainList=new ArrayList();
    List list=null;
    String currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
    String serviceDateQuery=util.getOvcServiceDateQuery(startDate, endDate);
    String additionalQuery=" and "+rutil.getHIVServicesReportQuery();
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query=util.getHouseholdOvcServiceReportQueryPart("OvcService")+serviceDateQuery+additionalQueryCriteria+additionalQuery+currentlyEnrolledQuery;
        //System.err.println("Query in getNumberOfOvcTestedHivPerCohort() is "+query);
        list = session.createQuery(query).list();
        tx.commit();
        session.close();
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }
    if(list !=null && !list.isEmpty())
    {
        for(Object obj:list)
        {
            Object[] objArray=(Object[])obj;
            Ovc ovc=(Ovc)objArray[1];
            mainList.add(ovc);
        }
    }
    return mainList;
}
public List getListOfOvcWithdrawnFromProgramTestedHiv(String additionalQueryCriteria,String startDate,String endDate) throws Exception
{
    List mainList=new ArrayList();
    List list=null;
    String currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
    String serviceDateQuery=util.getOvcServiceDateQuery(startDate, endDate);
    String additionalQuery=" and "+rutil.getHIVServicesReportQuery();
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query=util.getHouseholdOvcServiceWithdrawalReportQueryPart("OvcService")+additionalQueryCriteria+serviceDateQuery+util.getWithdrawalServicePeriodCriteria(startDate)+additionalQuery;
        //System.err.println("Query in getNumberOfOvcTestedHivPerCohort() is "+query);
        list = session.createQuery(query).list();
        tx.commit();
        session.close();
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }
    if(list !=null && !list.isEmpty())
    {
        for(Object obj:list)
        {
            Object[] objArray=(Object[])obj;
            Ovc ovc=(Ovc)objArray[1];
            mainList.add(ovc);
        }
    }
    return mainList;
}
public List getNumberOfOvcSupportedToAccessHivServicesPerMonthByCBO(String indicatorName,String stateCode,String startDate,String endDate) throws Exception
{
    List list=null;
    List mainList=new ArrayList();
    ReportUtility rutil=new ReportUtility();
    String additionalQuery=util.getOvcServiceDateQuery(startDate, endDate)+" and "+rutil.getHIVServicesReportQuery();
    mainList.add(indicatorName);
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query="select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge, count(distinct service.ovcId) "+util.getHouseholdOvcServiceReportQueryPart("OvcService")+" and hhe.stateCode=:state"+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge";
        //System.err.println(query);
        list = session.createQuery(query).setString("state", stateCode).list();
        tx.commit();
        session.close();
    }
    catch (HibernateException he)
    {
        session.close();
        throw new Exception(he);
    }
    mainList.addAll(list);
    return mainList;
}
public List getNoOfOvcWithdrawnButServedWithinReportPeriodPerMonthByCBO(String indicatorName,String stateCode,String startDate,String endDate,String reasonWithdrawnFromProgram) throws Exception
{
    List list=null;
    List mainList=new ArrayList();
    String additionalQuery=util.getOvcServiceDateQuery(startDate, endDate);
    mainList.add(indicatorName);
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge, count(distinct service.ovcId) "+util.getHouseholdOvcServiceWithdrawalReportQueryPart("OvcService")+" and hhe.stateCode=:state"+additionalQuery+" and "+rutil.getOvcWithdrawnQuery(reasonWithdrawnFromProgram)+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge").setString("state", stateCode).list();
        tx.commit();
        session.close();
    }
    catch (HibernateException he)
    {
        session.close();
        throw new Exception(he);
    }
    mainList.addAll(list);
    return mainList;
}//withdrawalServiceCountQueryPart="from OvcService service, Ovc ovc,"+util.getHouseholdObjectName()+",OvcWithdrawal withdrawal  where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=service.ovcId and ovc.ovcId=withdrawal.ovcId) " ;
public List getNumberOfOvcServedPerMonthByCBO(String indicatorName,String stateCode,String startDate,String endDate,boolean currentlyEnrolled) throws Exception
{
    List list=null;
    List mainList=new ArrayList();
    String additionalQuery=util.getOvcServiceDateQuery(startDate, endDate);
    String currentlyEnrolledQuery="";
    if(currentlyEnrolled)
    currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
    mainList.add(indicatorName);
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge, count(distinct service.ovcId) "+util.getHouseholdOvcServiceReportQueryPart("OvcService")+" and hhe.stateCode=:state"+additionalQuery+currentlyEnrolledQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge").setString("state", stateCode).list();
        tx.commit();
        session.close();
    }
    catch (HibernateException he)
    {
        session.close();
        throw new Exception(he);
    }
    mainList.addAll(list);
    return mainList;
}
public List getListOfOvcProvidedPsychosocialSupportServices(String additionalQuery,String serviceType) throws Exception
{
    List mainList=new ArrayList();
    //String additionalDateQuery=util.getOvcServiceDateQuery(startDate, endDate);
    //additionalQuery=additionalQuery+additionalDateQuery;
    
    try
    {
        //util.getHouseholdOvcServiceReportQueryPart("OvcService")+util.getOvcPsychosocialSupportQuery(serviceType)+additionalQuery
        String query=util.getHouseholdOvcServiceReportQueryPart("OvcService")+util.getOvcPsychosocialSupportQuery(serviceType)+additionalQuery;
        System.err.println("query getListOfOvcProvidedPsychosocialSupportServices is "+query);
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        List list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            List uniqueIdList=new ArrayList();
            Ovc ovc=null;
            for(Object obj:list)
            {
                Object[] objArray=(Object[])obj;
                ovc=(Ovc)objArray[1];
                if(!uniqueIdList.contains(ovc.getOvcId()))
                {
                    mainList.add(ovc);
                    uniqueIdList.add(ovc.getOvcId());
                }
            }
        }
    }
    catch (HibernateException he)
    {
        session.close();
        throw new Exception(he);
    }
    return mainList;
}
public int getNumberOfOvcProvidedPsychosocialSupportServices(String additionalQuery,String serviceType) throws Exception
{
    List list=null;
    int count=0;
    //String additionalDateQuery=util.getOvcServiceDateQuery(startDate, endDate);
    //additionalQuery=additionalQuery+additionalDateQuery;
    try
    {
        String query="select count(distinct service.ovcId) "+util.getHouseholdOvcServiceReportQueryPart("OvcService")+util.getOvcPsychosocialSupportQuery(serviceType)+additionalQuery;
        System.err.println("query getNumberOfOvcProvidedPsychosocialSupportServices is "+query);
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            count=Integer.parseInt(list.get(0).toString());
        }
    }
    catch (HibernateException he)
    {
        session.close();
        throw new Exception(he);
    }
    return count;
}
public List getNumberOfOvcProvidedPsychosocialSupportServicesPerMonthByCBO(String stateCode,String startDate,String endDate) throws Exception
{
    List list=null;
    List mainList=new ArrayList();
    String additionalQuery=util.getOvcServiceDateQuery(startDate, endDate);
    mainList.add("Number of Ovc provided Psychosocial support services");
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge, count(distinct service.ovcId) "+util.getHouseholdOvcServiceReportQueryPart("OvcService")+" and hhe.stateCode=:state and (service.serviceAccessed1 is not null and service.serviceAccessed1 !='' and service.serviceAccessed1 !=' ' and service.serviceAccessed1 !='  ')"+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge").setString("state", stateCode).list();
        tx.commit();
        session.close();
    }
    catch (HibernateException he)
    {
        session.close();
        throw new Exception(he);
    }
    mainList.addAll(list);
    return mainList;
}
public List getNumberOfOvcProvidedBirthRegistrationServicesPerMonthByCBO(String stateCode,String startDate,String endDate) throws Exception
{
    IndicatorDictionary ind=new IndicatorDictionary();
    List list=null; 
    List mainList=new ArrayList();
    String additionalQuery=util.getOvcServiceDateQuery(startDate, endDate);
    mainList.add(ind.getIndicatorForNumberOfOvcProvidedBirthCertificateServicesAfterEnrollment().getIndicatorName());
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge, count(distinct service.ovcId) "+util.getHouseholdOvcServiceReportQueryPart("OvcService")+" and hhe.stateCode=:state and (service.serviceAccessed5 is not null and UPPER(service.serviceAccessed5) like '%BIRTH%')"+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge").setString("state", stateCode).list();
        tx.commit();
        session.close();
    }
    catch (HibernateException he)
    {
        session.close();
        throw new Exception(he);
    }
    mainList.addAll(list);
    return mainList;
}
public List getNumberOfOvcProvidedLegalServicesPerMonthByCBO(String stateCode,String startDate,String endDate) throws Exception
{
    IndicatorDictionary ind=new IndicatorDictionary();
    List list=null; 
    List mainList=new ArrayList();
    String additionalQuery=util.getOvcServiceDateQuery(startDate, endDate);
    mainList.add(ind.getIndicatorForNumberOfOvcProvidedLegalServicesAfterEnrollment().getIndicatorName());
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge, count(distinct service.ovcId) "+util.getHouseholdOvcServiceReportQueryPart("OvcService")+" and hhe.stateCode=:state and (service.serviceAccessed5 is not null and UPPER(service.serviceAccessed5) like '%LEGAL%')"+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge").setString("state", stateCode).list();
        tx.commit();
        session.close();
    }
    catch (HibernateException he)
    {
        session.close();
        throw new Exception(he);
    }
    mainList.addAll(list);
    return mainList;
}
public List getNumberOfOvcProvidedNutritionalServicesPerMonthByCBO(String stateCode,String startDate,String endDate) throws Exception
{
    List list=null;
    List mainList=new ArrayList();
    String additionalQuery=util.getOvcServiceDateQuery(startDate, endDate);
    mainList.add("Number of Ovc provided nutritional services");
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge, count(distinct service.ovcId) "+util.getHouseholdOvcServiceReportQueryPart("OvcService")+" and hhe.stateCode=:state and (service.serviceAccessed2 is not null and service.serviceAccessed2 !='' and service.serviceAccessed2 !=' ' and service.serviceAccessed2 !='  ')"+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge").setString("state", stateCode).list();
        tx.commit();
        session.close();
    }
    catch (HibernateException he)
    {
        session.close();
        throw new Exception(he);
    }
    mainList.addAll(list);
    return mainList;
}
public List getNumberOfOvcProvidedHealthServicesPerMonthByCBO(String stateCode,String startDate,String endDate) throws Exception
{
    List list=null;
    List mainList=new ArrayList();
    String additionalQuery=util.getOvcServiceDateQuery(startDate, endDate);
    mainList.add("Number of Ovc provided Health services");
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge, count(distinct service.ovcId) "+util.getHouseholdOvcServiceReportQueryPart("OvcService")+" and hhe.stateCode=:state and (service.serviceAccessed3 is not null and service.serviceAccessed3 !='' and service.serviceAccessed3 !=' ' and service.serviceAccessed3 !='  ')"+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge").setString("state", stateCode).list();
        tx.commit();
        session.close();
    }
    catch (HibernateException he)
    {
        session.close();
        throw new Exception(he);
    }
    mainList.addAll(list);
    return mainList;
}
public List getNumberOfOvcProvidedHIVServicesPerMonthByCBO(String stateCode,String startDate,String endDate) throws Exception
{
    List list=null;
    List mainList=new ArrayList();
    String additionalQuery=util.getOvcServiceDateQuery(startDate, endDate);
    mainList.add("Number of Ovc provided HIV care services");
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge, count(distinct service.ovcId) "+util.getHouseholdOvcServiceReportQueryPart("OvcService")+" and hhe.stateCode=:state and service.serviceAccessed3 like '%Access for HIV care%'"+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge").setString("state", stateCode).list();
        tx.commit();
        session.close();
    }
    catch (HibernateException he)
    {
        session.close();
        throw new Exception(he);
    }
    mainList.addAll(list);
    return mainList;
}
public List getNumberOfOvcProvidedEducationalServicesPerMonthByCBO(String stateCode,String startDate,String endDate) throws Exception
{
    List list=null;
    List mainList=new ArrayList();
    String additionalQuery=util.getOvcServiceDateQuery(startDate, endDate);
    mainList.add("Number of Ovc provided Educational services");
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge, count(distinct service.ovcId) "+util.getHouseholdOvcServiceReportQueryPart("OvcService")+" and hhe.stateCode=:state and (service.serviceAccessed4 is not null and service.serviceAccessed4 !='' and service.serviceAccessed4 !=' ' and service.serviceAccessed4 !='  ')"+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge").setString("state", stateCode).list();
        tx.commit();
        session.close();
    }
    catch (HibernateException he)
    {
        session.close();
        throw new Exception(he);
    }
    mainList.addAll(list);
    return mainList;
}
public List getNumberOfOvcProvidedProtectionServicesPerMonthByCBO(String stateCode,String startDate,String endDate) throws Exception
{
    List list=null;
    List mainList=new ArrayList();
    String additionalQuery=util.getOvcServiceDateQuery(startDate, endDate);
    mainList.add("Number of Ovc provided Protection services");
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge, count(distinct service.ovcId) "+util.getHouseholdOvcServiceReportQueryPart("OvcService")+" and hhe.stateCode=:state and (service.serviceAccessed5 is not null and service.serviceAccessed5 !='' and service.serviceAccessed5 !=' ' and service.serviceAccessed5 !='  ')"+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge").setString("state", stateCode).list();
        tx.commit();
        session.close();
    }
    catch (HibernateException he)
    {
        session.close();
        throw new Exception(he);
    }
    mainList.addAll(list);
    return mainList;
}
public List getNumberOfOvcProvidedShelterServicesPerMonthByCBO(String stateCode,String startDate,String endDate) throws Exception
{
    List list=null;
    List mainList=new ArrayList();
    String additionalQuery=util.getOvcServiceDateQuery(startDate, endDate);
    mainList.add("Number of Ovc provided Shelter services");
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge, count(distinct service.ovcId) "+util.getHouseholdOvcServiceReportQueryPart("OvcService")+" and hhe.stateCode=:state and (service.serviceAccessed6 is not null and service.serviceAccessed6 !='' and service.serviceAccessed6 !=' ' and service.serviceAccessed6 !='  ')"+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge").setString("state", stateCode).list();
        tx.commit();
        session.close();
    }
    catch (HibernateException he)
    {
        session.close();
        throw new Exception(he);
    }
    mainList.addAll(list);
    return mainList;
}
public List getNumberOfOvcProvidedEconomicStrengtheningServicesPerMonthByCBO(String stateCode,String startDate,String endDate) throws Exception
{
    List list=null;
    List mainList=new ArrayList();
    String additionalQuery=util.getOvcServiceDateQuery(startDate, endDate);
    mainList.add("Number of Ovc provided Economic strengthening services");
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge, count(distinct service.ovcId) "+util.getHouseholdOvcServiceReportQueryPart("OvcService")+" and hhe.stateCode=:state and (service.serviceAccessed7 is not null and service.serviceAccessed7 !='' and service.serviceAccessed7 !=' ' and service.serviceAccessed7 !='  ')"+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge").setString("state", stateCode).list();
        tx.commit();
        session.close();
    }
    catch (HibernateException he)
    {
        session.close();
        throw new Exception(he);
    }
    mainList.addAll(list);
    return mainList;
}
public List getNumberOfOvcReferredForServicesPerMonthByCBO(String stateCode,String startDate,String endDate) throws Exception
{
    List list=null;
    List mainList=new ArrayList();
    String additionalQuery=util.getOvcServiceDateQuery(startDate, endDate);
    mainList.add("Number of Ovc provided referred for services");
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge, count(distinct service.ovcId) "+util.getHouseholdOvcServiceReportQueryPart("OvcService")+" and hhe.stateCode=:state and (service.servicesReferred is not null and service.servicesReferred !='' and service.servicesReferred !=' ' and service.servicesReferred !='  ')"+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge").setString("state", stateCode).list();
        tx.commit();
        session.close();
    }
    catch (HibernateException he)
    {
        session.close();
        throw new Exception(he);
    }
    mainList.addAll(list);
    return mainList;
}
public List getNumberOfHIVPositiveOvcServedPerMonthByCBO(String stateCode,String startDate,String endDate) throws Exception
{
    List list=null;
    List mainList=new ArrayList();
    String additionalQuery=util.getOvcServiceDateQuery(startDate, endDate);
    mainList.add("Number of HIV positive Ovc provided atleast one service");
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge, count(distinct service.ovcId) "+util.getHouseholdOvcServiceReportQueryPart("OvcService")+" and hhe.stateCode=:state and UPPER(ovc.hivStatus) like '%POSITIVE%'"+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge").setString("state", stateCode).list();
        tx.commit();
        session.close();
    }
    catch (HibernateException he)
    {
        session.close();
        throw new Exception(he);
    }
    mainList.addAll(list);
    return mainList;
}
public List getNumberOfHIVNegativeOvcServedPerMonthByCBO(String stateCode,String startDate,String endDate) throws Exception
{
    List list=null;
    List mainList=new ArrayList();
    String additionalQuery=util.getOvcServiceDateQuery(startDate, endDate);
    mainList.add("Number of HIV negative Ovc provided atleast one service");
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge, count(distinct service.ovcId) "+util.getHouseholdOvcServiceReportQueryPart("OvcService")+" and hhe.stateCode=:state and UPPER(ovc.hivStatus) like '%NEGATIVE%'"+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge").setString("state", stateCode).list();
        tx.commit();
        session.close();
    }
    catch (HibernateException he)
    {
        session.close();
        throw new Exception(he);
    }
    mainList.addAll(list);
    return mainList;
}
public List getNumberOfHIVUnknownOvcServedPerMonthByCBO(String stateCode,String startDate,String endDate) throws Exception
{
    List list=null;
    List mainList=new ArrayList();
    String additionalQuery=util.getOvcServiceDateQuery(startDate, endDate);
    mainList.add("Number of HIV unknown Ovc provided atleast one service");
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge, count(distinct service.ovcId) "+util.getHouseholdOvcServiceReportQueryPart("OvcService")+" and hhe.stateCode=:state and UPPER(ovc.hivStatus) like '%UNKNOWN%'"+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge").setString("state", stateCode).list();
        tx.commit();
        session.close();
    }
    catch (HibernateException he)
    {
        session.close();
        throw new Exception(he);
    }
    mainList.addAll(list);
    return mainList;
}
public List getHivServicesByOrgUnitCodeAndPeriod(String orgUnitCode,String begDate,String endDate) throws Exception
{
    List list=new ArrayList();
    List serviceList=new ArrayList();
    //getHIVServicesReportQuery();
    try 
    {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(util.getHouseholdQueryPart()+"Ovc ovc,OvcService service where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=service.ovcId and hhe.communityCode=:cc and service.servicedate between '"+begDate+"' and '"+endDate+"' and "+rutil.getHIVServicesReportQuery()+" order by service.ovcId").setString("cc", orgUnitCode).list();
            tx.commit();
            session.close();
    } 
    catch (Exception ex) 
    {
            session.close();
            throw new Exception(ex);
    }
    if(list !=null && !list.isEmpty())
    {
        for(Object s:list)
        {
            Object[] obj=(Object[])s;
            serviceList.add((OvcService)obj[2]);
        }
    }
    return serviceList;
}
public List getServiceByCBOAndPeriod(String orgCode,String begDate,String endDate) throws Exception
{
    List list=new ArrayList();
    List serviceList=new ArrayList();
    try {
            String query=(util.getHouseholdQueryPart()+"Ovc ovc,OvcService os where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=os.ovcId and hhe.orgCode='"+orgCode+"' and os.servicedate between '"+begDate+"' and '"+endDate+"' order by os.servicedate");
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
           // System.err.println(query);
        list = session.createQuery(util.getHouseholdQueryPart()+"Ovc ovc,OvcService os where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=os.ovcId and hhe.orgCode=:oc and os.servicedate between '"+begDate+"' and '"+endDate+"' order by os.servicedate").setString("oc", orgCode).list();
            //list = session.createQuery(query).list();

        tx.commit();
        session.close();
        } catch (HibernateException he) {
            session.close();
            throw new Exception(he);
        }
    if(list !=null && !list.isEmpty())
    {
        for(Object s:list)
        {
            Object[] obj=(Object[])s;
            serviceList.add((OvcService)obj[2]);
        }
    }
    return serviceList;
}
public List getServiceByStateAndPeriod(String stateCode,String begDate,String endDate) throws Exception
{
    List list=new ArrayList();
    List serviceList=new ArrayList();
    try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
           // System.err.println("from Ovc ovc,OvcService os where ovc.ovcId=os.ovcId and hhe.stateCode=:st and os.servicedate between '"+begDate+"' and '"+endDate+"' order by os.ovcId");
        list = session.createQuery(util.getHouseholdQueryPart()+"Ovc ovc,OvcService os where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=os.ovcId and hhe.stateCode=:st and os.servicedate between '"+begDate+"' and '"+endDate+"' order by os.ovcId").setString("st", stateCode).list();
        
        tx.commit();
        session.close();
        } catch (HibernateException he) {
            session.close();
            throw new Exception(he);
        }
    if(list !=null && !list.isEmpty())
    {
        for(Object s:list)
        {
            Object[] obj=(Object[])s;
            serviceList.add((OvcService)obj[2]);
        }
    }
    return serviceList;
}
public List getServiceByPeriod(String begDate,String endDate) throws Exception
{
    List list=new ArrayList();
    try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery(util.getHouseholdQueryPart()+"Ovc ovc,OvcService os where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=os.ovcId and os.servicedate between '"+begDate+"' and '"+endDate+"' order by os.servicedate").list();
        
        tx.commit();
        session.close();
        } catch (HibernateException he) {
            session.close();
            throw new Exception(he);
        }
    return list;
}
public List getDistinctOvcIdsFromOvcService(String begDate,String endDate) throws Exception
{
    List list=new ArrayList();
    try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("select distinct (os.ovcId) from OvcService os where os.servicedate between '"+begDate+"' and '"+endDate+"' order by os.ovcId").list();
        //System.err.println("select distinct (os.ovcId) from OvcService os where os.servicedate between '"+begDate+"' and '"+endDate+"' order by os.ovcId");
        tx.commit();
        session.close();
        } catch (HibernateException he) {
            session.close();
            throw new Exception(he);
        }
    return list;
}
public List getOvcServicesByOvcIdAndEndDate(String ovcId,String endDate) throws Exception
{
    List list=new ArrayList();
    String periodQuery=" ";
    if(endDate !=null)
        periodQuery=" and service.servicedate <= '"+endDate+"'";
    try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from OvcService service where service.ovcId=:id "+periodQuery+" order by service.servicedate").setString("id", ovcId).list();

        tx.commit();
        session.close();
        } catch (HibernateException he) {
            session.close();
            throw new Exception(he);
        }
    return list;
}
public List getOvcServicesByOvcIdAndPeriod(String ovcId,String begDate,String endDate) throws Exception
{
    List list=new ArrayList();
    String periodQuery=" ";
    if(begDate !=null && endDate !=null)
        periodQuery=" and service.servicedate between '"+begDate+"' and '"+endDate+"'";
    try {
            String query="from OvcService service where service.ovcId='"+ovcId+"'"+periodQuery+" order by service.surveyNumber";
            //System.err.println(query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(query).list();
            //list = session.createQuery("from OvcService service where service.ovcId=:id "+periodQuery+" order by service.surveyNumber").setString("id", ovcId).list();

        tx.commit();
        session.close();
        } catch (HibernateException he) {
            session.close();
            throw new Exception(he);
        }
    return list;
}
public List getOvcServicesByOvcIdAndAdditionalServiceQuery(String ovcId,String additionalServiceQuery) throws Exception
{
    List list=new ArrayList();

    try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from OvcService service where service.ovcId=:id "+additionalServiceQuery+" order by service.surveyNumber").setString("id", ovcId).list();
            //System.err.println("from OvcService service where service.ovcId=:id "+additionalServiceQuery+" order by service.surveyNumber");
        tx.commit();
        session.close();
        } catch (HibernateException he) {
            session.close();
            throw new Exception(he);
        }
    return list;
}
public List getOvcServicesByOvcIdAndAdditionalServiceQueryByNumberOfServices(String ovcId,String additionalServiceQuery) throws Exception
{
    List list=new ArrayList();

    try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from OvcService service where service.ovcId=:id "+additionalServiceQuery+" order by service.numberOfServices desc").setString("id", ovcId).list();

        tx.commit();
        session.close();
        } catch (HibernateException he) {
            session.close();
            throw new Exception(he);
        }
    return list;
}
public int getNumberOfServicesPerServiceRecord(OvcService service) throws Exception
{
    int numberOfServices=0;
    if(service !=null)
    {
        if(service.getServiceAccessed1() !=null && !service.getServiceAccessed1().equals("") && !service.getServiceAccessed1().equals(" "))
        numberOfServices++;
        if(service.getServiceAccessed2() !=null && !service.getServiceAccessed2().equals("") && !service.getServiceAccessed2().equals(" "))
        numberOfServices++;
        if(service.getServiceAccessed3() !=null && !service.getServiceAccessed3().equals("") && !service.getServiceAccessed3().equals(" "))
        numberOfServices++;
        if(service.getServiceAccessed4() !=null && !service.getServiceAccessed4().equals("") && !service.getServiceAccessed4().equals(" "))
        numberOfServices++;
        if(service.getServiceAccessed5() !=null && !service.getServiceAccessed5().equals("") && !service.getServiceAccessed5().equals(" "))
        numberOfServices++;
        if(service.getServiceAccessed6() !=null && !service.getServiceAccessed6().equals("") && !service.getServiceAccessed6().equals(" "))
        numberOfServices++;
        if(service.getServiceAccessed7() !=null && !service.getServiceAccessed7().equals("") && !service.getServiceAccessed7().equals(" "))
        numberOfServices++;
        if(service.getServicesReferred() !=null && !service.getServicesReferred().equals("") && !service.getServicesReferred().equals(" "))
        numberOfServices++;
    }
    return numberOfServices;
}
public void setNumberOfServicesPerServiceRecord() throws Exception
{
    List list=new ArrayList();//OvcService os=new OvcService();os.getNumberOfServices();
    try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("from OvcService service where service.numberOfServices=0").list();

        tx.commit();
        session.close();
        } catch (HibernateException he) {
            session.close();
            throw new Exception(he);
        }
    if(list !=null && list.size()>0)
    {
        int count=0;
        int numberOfServices=0;
        OvcService service=null;
        for(Object s:list)
        {
            count++;
            service=(OvcService)s;
            numberOfServices=getNumberOfServicesPerServiceRecord(service);
            if(numberOfServices>0)
            {
                service.setNumberOfServices(numberOfServices);
                updateOvcService(service,false,false);
                //System.err.println("updating service "+count+" with number of services");
            }
            else
            {
                deleteService(service);
                //System.err.println("service "+service.getOvcId()+" at "+count+" deleted");  
            }
        }
    }
}
public boolean isNumberOfServicesGreaterThanZeroAndLessThanThree(String ovcId) throws Exception
{
    List list=new ArrayList();
    try 
    {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("from OvcService service where service.ovcId=:id and service.numberOfServices >0 and service.numberOfServices <3").setString("id", ovcId).list();

        tx.commit();
        session.close();
     }
      catch (HibernateException he) 
      {
            session.close();
            throw new Exception(he);
      }
    if(list !=null && list.size()>0)
    {
        return true;
    }
    return false;
}
public boolean isNumberOfServicesMoreThanTwo(String ovcId) throws Exception
{
    List list=new ArrayList();

    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("from OvcService service where service.ovcId=:id and service.numberOfServices >2").setString("id", ovcId).list();
        tx.commit();
        session.close();
    } 
    catch (HibernateException he)
    {
        session.close();
        throw new Exception(he);
    }
    if(list !=null && list.size()>0)
    {
        return true;
    }
    return false;
}

public void changeOvcId(String oldId,String newId) throws Exception
{
    AppUtility appUtil=new AppUtility();
    List list=new ArrayList();
    try 
    {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("from OvcService os where os.ovcId =:id").setString("id", oldId).list();

        tx.commit();
        session.close();
        
        if(list !=null && !list.isEmpty())
        {
            OvcService os=null;
            for(Object obj:list)
            {
                os=(OvcService)obj;
                os.setDateOfEntry(appUtil.getCurrentDate());
                deleteService(os);
                if(getOvcServiceForTheMth(newId, os.getServicedate())==null)
                {
                    os.setOvcId(newId);
                    saveOvcService(os,false,false);
                }
            }
        }
    } 
    catch (Exception ex)
    {
        ex.printStackTrace();
    }
}
public int getOvcServiceCount(String ovcId) throws Exception
{
    int serviceCount=0;
    List list=new ArrayList();
    try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("select count(os.ovcId) from OvcService os where os.ovcId = :id")
                           .setString("id", ovcId).list();

        tx.commit();
        session.close();
        } catch (HibernateException he) {
            session.close();
            throw new Exception(he);
        }
    if(list !=null && list.size()>0)
    serviceCount=Integer.parseInt(list.get(0).toString());
    return serviceCount;
}
public List getOvcIdsFromServices() throws Exception
{
    List list=new ArrayList();
    try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("select distinct os.ovcId from OvcService os").list();

        tx.commit();
        session.close();
        } catch (HibernateException he) {
            session.close();
            throw new Exception(he);
        }
    return list;
}
public List getOvcIdsFromServices(int surveyNo) throws Exception
{
    List list=new ArrayList();
    try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("select distinct os.ovcId from OvcService os where os.surveyNumber =:sNo")
                           .setInteger("sNo", surveyNo).list();

        tx.commit();
        session.close();
        } catch (HibernateException he) {
            session.close();
            throw new Exception(he);
        }
    return list;
}
public List getOvcServices(String ovcId) throws Exception
{
    List list=new ArrayList();
    try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("from OvcService as os where os.ovcId = :id order by os.servicedate").setString("id", ovcId).list();

        tx.commit();
        session.close();
        } catch (HibernateException he) {
            session.close();
            throw new Exception(he);
        }
    return list;
}
public OvcService getOvcServiceForTheMth(String ovcId, String serviceDate) throws Exception 
{
    OvcService service=null; 
    List list = new ArrayList();
     try 
     {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
    list = session.createQuery("from OvcService as os where os.ovcId = :id and os.servicedate = :sd")
                       .setString("id", ovcId)
                       .setString("sd", serviceDate)
                       .list();

    tx.commit();
    session.close();
    } 
     catch (HibernateException he) 
     {
        session.close();
        throw new Exception(he);
    }
    catch (Exception ex) 
    {
        session.close();
        ex.printStackTrace();
    }
     if(list.size()>0)
     {
       service = (OvcService)list.get(0);
     }
     else
     service=null;
    return service;
}
   
    public List getAllServiceRecords() throws Exception
    {
        List list = new ArrayList();
         try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("from OvcService os order by os.ovcId,os.servicedate").list();
        tx.commit();
        session.close();
        } catch (HibernateException he) {
            session.close();
            throw new Exception(he);
        }
         return list;
    }
    public List getAllServiceRecords(int surveyNo) throws Exception
    {
        List list = new ArrayList();
         try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("from OvcService os where os.surveyNumber="+surveyNo+" order by os.ovcId,os.servicedate").list();
        tx.commit();
        session.close();
        } catch (HibernateException he) {
            session.close();
            throw new Exception(he);
        }
         return list;
    }
    public List getServiceRecords(String startDate,String endDate,String additionalOrgQuery) throws Exception
    {
        String sql=util.getHouseholdQueryPart()+"OvcService service, Ovc ovc where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=service.ovcId) AND service.dateOfEntry between '"+ startDate+"' and '"+endDate+"' "+additionalOrgQuery+" order by service.ovcId";

        if(startDate.equalsIgnoreCase("All") || endDate.equalsIgnoreCase("All"))
        sql=util.getHouseholdQueryPart()+"OvcService service, Ovc ovc where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=service.ovcId) and service.ovcId is not null "+additionalOrgQuery+" order by service.ovcId";
        List list = new ArrayList();
        List serviceList = new ArrayList();
         try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery(sql).list();
        tx.commit();
        session.close();
        } catch (HibernateException he) {
            session.close();
            throw new Exception(he);
        }
        if(list !=null)
        {
            OvcService service=null;
            for(int i=0; i<list.size(); i++)
            {
                    Object[] obj=(Object[])list.get(i);
                    service=(OvcService)obj[1];
                    serviceList.add(service);
            }
        }
        return serviceList;
    }
    public List getListOfOvcServed(String additionalQuery) throws Exception 
    {
        List list = new ArrayList();
        List serviceList = new ArrayList();
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            String query=util.getHouseholdQueryPart()+"OvcService service,Ovc ovc where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=service.ovcId) "+additionalQuery+" order by service.ovcId";
            System.err.println("query in getListOfOvcServed(String additionalQuery) is "+query);
            list = session.createQuery(query).list();
            tx.commit();
            session.close();
        } 
        catch (Exception ex) 
        {
            session.close();
            ex.printStackTrace();
        }
        if(list !=null)
        {
            List ovcIdList=new ArrayList();
            OvcService service=null;
            for(int i=0; i<list.size(); i++)
            {
                    Object[] obj=(Object[])list.get(i);
                    service=(OvcService)obj[1];
                    if(!ovcIdList.contains(service.getOvcId()))
                    {
                        ovcIdList.add(service.getOvcId());
                        serviceList.add(service);
                    }
            }
        }
        return serviceList;
    }
    public List getOvcServicePerMth(List paramList) throws Exception {
        List list = new ArrayList();
        List serviceList = new ArrayList();
        String state=(String)paramList.get(0);
        String lga=(String)paramList.get(1);
        String cbo=(String)paramList.get(2);
        String ward=(String)paramList.get(3);
        int serviceMth=(Integer)paramList.get(4);
        int serviceYear=(Integer)paramList.get(5);
        int serviceMth2=(Integer)paramList.get(6);
        int serviceYear2=(Integer)paramList.get(7);
        int startAge=(Integer)paramList.get(8);
        int endAge=(Integer)paramList.get(9);
        String partnerName=(String)paramList.get(12);
        String partnerCode=(String)paramList.get(13);
        String reportFilter="All";
        if(paramList.size()>15)
        reportFilter=(String)paramList.get(15);
        String reportFilterQuery="";
        if(reportFilter !=null && !reportFilter.equalsIgnoreCase("All"))
        reportFilterQuery=" and os.providerName like '%"+reportFilter+"%'";
        
        //System.err.println("Report filter is "+reportFilter+" "+(String)paramList.get(14));
        List dateList=new ArrayList();
        dateList.add(serviceMth);
        dateList.add(serviceMth2);
        dateList.add(serviceYear);
        dateList.add(serviceYear2);
        String[] params={state,lga,cbo,ward,serviceMth+"",serviceYear+"",serviceMth2+"",serviceYear2+"",startAge+"",endAge+"",null,null,null,partnerName,partnerCode};
        String additionalQuery=util.getQueryCriteria(params);
        String dateQuery=util.getDatePeriodForServices(dateList);
         try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            //System.err.println(util.getHouseholdQueryPart()+"OvcService os,Ovc ovc where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=os.ovcId) and "+dateQuery+additionalQuery+reportFilterQuery+" order by os.ovcId ");
            list = session.createQuery(util.getHouseholdQueryPart()+"OvcService os,Ovc ovc where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=os.ovcId) and "+dateQuery+additionalQuery+reportFilterQuery+" order by os.ovcId ").list();
            //list = session.createQuery("from OvcService os where "+dateQuery+" and os.ovcId in (select ovc.ovcId from Ovc ovc where ovc.ovcId is not null "+additionalQuery +") order by os.ovcId ").list();
        tx.commit();
        session.close();
        } catch (HibernateException he) {
            session.close();
            throw new Exception(he);
        }
        if(list !=null)
        {
            OvcService service=null;
            for(int i=0; i<list.size(); i++)
            {
                    Object[] obj=(Object[])list.get(i);
                    service=(OvcService)obj[1];
                    serviceList.add(service);
            }
        }
        return serviceList;
    }

    public List getOvcMthlyService(List paramList) throws Exception 
    {
        List list = new ArrayList();
        List serviceList = new ArrayList();
        String state=(String)paramList.get(0);
        String lga=(String)paramList.get(1);
        String cbo=(String)paramList.get(2);
        String ward=(String)paramList.get(3);
        int serviceMth=(Integer)paramList.get(4);
        int serviceYear=(Integer)paramList.get(5);
        int serviceMth2=(Integer)paramList.get(6);
        int serviceYear2=(Integer)paramList.get(7);
        int startAge=(Integer)paramList.get(8);
        int endAge=(Integer)paramList.get(9);
        String partnerName=(String)paramList.get(12);
        String partnerCode=(String)paramList.get(13);
        List dateList=new ArrayList();
        dateList.add(serviceMth);
        dateList.add(serviceMth2);
        dateList.add(serviceYear);
        dateList.add(serviceYear2);
        String[] params={state,lga,cbo,ward,serviceMth+"",serviceYear+"",serviceMth2+"",serviceYear2+"",startAge+"",endAge+"",null,null,null,partnerName,partnerCode};
        String additionalQuery=util.getQueryCriteria(params);
        String dateQuery=util.getDatePeriodForServices(dateList);
         try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            //list = session.createQuery("from OvcService os,Ovc ovc where "+dateQuery+" and os.ovcId=ovc.ovcId "+additionalQuery +") order by os.ovcId ").list();
            list = session.createQuery(util.getHouseholdQueryPart()+"OvcService os,Ovc ovc where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=os.ovcId) and "+dateQuery+additionalQuery+" order by os.ovcId ").list();
            
        tx.commit();
        session.close();
        } catch (HibernateException he) {
            session.close();
            throw new Exception(he);
        }
        
        return list;
    }
    public OvcService[] getOvcService(String ovcId) throws Exception {

        return ovcServiceObjects;
    }

public void saveOvcHIVStatus(OvcService service) throws Exception
{
    if(service==null || service.getOvcId()==null)
    return;
    AppUtility appUtil=new AppUtility();
    HivStatusUpdateDao hsudao=new HivStatusUpdateDaoImpl();
    HivStatusUpdate hsu=new HivStatusUpdate();
    hsu.setClientEnrolledInCare(service.getEnrolledInCare());
    hsu.setEnrolledOnART(service.getEnrolledOnART());
    hsu.setOrganizationClientIsReferred(service.getFacilityId());
    hsu.setClientId(service.getOvcId());
    hsu.setClientType("ovc");
    hsu.setDateModified(appUtil.getCurrentDate());
    hsu.setDateOfCurrentStatus(service.getServicedate());
    hsu.setHivStatus(HivRecordsManager.getHivStatusScode(service.getCurrentHivStatus()));
    hsu.setRecordStatus("active");
    hsu.setPointOfUpdate(service.getPointOfService());
    //System.err.println("HIV Status in service is "+service.getCurrentHivStatus());
    //hsudao.saveHivStatusUpdate(hsu);
}
public void saveOvcBirthRegistrationStatus(OvcService service) throws Exception
{
    if(service==null || service.getOvcId()==null)
    return;
    //check if the birth registration option is selected, then save the details
    
        AppUtility appUtil=new AppUtility();
        BirthRegistrationDao brdao=new BirthRegistrationDaoImpl();
        BirthRegistration br=new BirthRegistration();
        br.setClientId(service.getOvcId());
        br.setClientType("ovc");
        br.setDateModified(appUtil.getCurrentDate());
        br.setDateOfRegistration(service.getServicedate());
        if(service.getServiceAccessed5() !=null && service.getServiceAccessed5().indexOf("Birth") !=-1)
        br.setBirthRegistrationStatus("Yes");
        else
        br.setBirthRegistrationStatus(null);
        br.setRecordStatus("active");
        br.setPointOfUpdate(service.getPointOfService());
        brdao.saveBirthRegistration(br);
}
public void saveOvcService(OvcService ovcService,boolean saveHiv,boolean saveBirthRegistration) throws Exception 
{
   try {
            ovcService=getServicesByServiceCode(ovcService);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.save(ovcService);
            tx.commit();
            session.close();
            //if(saveHiv)
            //saveOvcHIVStatus(ovcService);
            if(saveBirthRegistration)
            saveOvcBirthRegistrationStatus(ovcService);
            SchoolAttendanceManager.saveOrUpdateSchoolAttendanceTracker(ovcService);
        } 
        catch (HibernateException he) 
        {
            session.close();
            throw new Exception(he);
        }
}
     public void updateOvcService(OvcService ovcService,boolean saveHiv,boolean saveBirthRegistration) throws Exception
     {
        try 
        {
            if(ovcService !=null)
            {
                OvcService ovcService2=this.getOvcServiceForTheMth(ovcService.getOvcId(), ovcService.getServicedate());
                if(ovcService2 !=null)
                {
                   if(DateManager.compareDates(ovcService2.getDateOfEntry(), ovcService.getDateOfEntry())) 
                   {
                       updateOvcServiceWithoutDateCheck(ovcService,saveHiv,saveBirthRegistration);
                        
                   }
                }
            }
        } 
        catch (HibernateException he) 
        {
            session.close();
            throw new Exception(he);
        }
}
public void updateOvcServiceWithoutDateCheck(OvcService ovcService,boolean saveHiv,boolean saveBirthRegistration) throws Exception
{
    try 
    {
        //System.err.println(" inside updateOvcServiceWithoutDateCheck");
        if(ovcService !=null)
        {
            //System.err.println(" ovcService id is "+ovcService.getOvcId()+" "+ovcService.getServicedate());
            OvcService ovcService2=getOvcServiceForTheMth(ovcService.getOvcId(), ovcService.getServicedate());
            if(ovcService2 !=null)
            {
                //System.err.println(" ovcService2 id is "+ovcService2.getOvcId()+" "+ovcService2.getServicedate());
                ovcService=getServicesByServiceCode(ovcService);
                ovcService.setId(ovcService2.getId());
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.update(ovcService);
                tx.commit();
                session.close();
                //if(saveHiv)
                //saveOvcHIVStatus(ovcService);
                if(saveBirthRegistration)
                saveOvcBirthRegistrationStatus(ovcService);
                SchoolAttendanceManager.saveOrUpdateSchoolAttendanceTracker(ovcService);
            }
        }
    } 
    catch (HibernateException he) 
    {
        session.close();
        throw new Exception(he);
    }
}
public void markedForDelete(OvcService ovcService) throws Exception
{
    try
    {
        ovcService.setMarkedForDelete(NomisConstant.MARKEDFORDELETE);
        updateOvcServiceWithoutDateCheck(ovcService,false,false);
    }
    catch(Exception ex)
    {
        ex.printStackTrace();
    }
}
public void deleteService(OvcService ovcService) throws Exception
{
    try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
           session.delete(ovcService);
            tx.commit();
            session.close();
            util.saveDeletedRecord(ovcService.getOvcId(),null, "ovcService", ovcService.getServicedate());
            HivRecordsManager.deleteHivStatusRecord(ovcService.getOvcId(), ovcService.getServicedate(),NomisConstant.SERVICE_POINTOFUPDATE);
        }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }
}


     public void deleteOvcService(String ovcId, String dateAccessed) throws Exception {
        List list = new ArrayList();
         try 
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from OvcService as os where os.ovcId = :id and os.servicedate = :dt")
            .setString("id", ovcId).setString("dt", dateAccessed).list();
            tx.commit();
            session.close();
            if(list != null) 
            {
               ovcService = (OvcService)list.get(0);
               deleteService(ovcService);
               //session.delete(ovcService);
            }
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
        }
    }
public void deleteOvcServices(String ovcId) throws Exception
{
    List list=getOvcServices(ovcId);
    if(list !=null)
    {
        for(int i=0; i<list.size(); i++)
        {
            OvcService ovcService=(OvcService)list.get(i);
            deleteService(ovcService);

        }
    }
}
public List getNumberOfOvcServedPerMonthByCBO() throws Exception
{
    List list=null;
    List mainList=new ArrayList();
    mainList.add("Number of Ovc provided atleast one service");
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge, count(distinct service.ovcId) "+util.getHouseholdQueryPart()+"ServiceReport service,Ovc ovc where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=service.ovcId) group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge").list();
        tx.commit();
        session.close();
    } 
    catch (HibernateException he)
    {
        session.close();
        throw new Exception(he);
    }
    mainList.addAll(list);
    return mainList;
}
public OvcService getServicesByServiceCode(OvcService service)
{
    
    if(service !=null)
    {
        boolean removeStartAndEndWildCharacters=false;
        if(service.getServiceAccessed1() !=null)
        service.setserviceAccessed1(OvcServiceManager.getConcatenatedServiceCodes(service.getServiceAccessed1().split(","),removeStartAndEndWildCharacters));
        if(service.getServiceAccessed2() !=null)
        service.setserviceAccessed2(OvcServiceManager.getConcatenatedServiceCodes(service.getServiceAccessed2().split(","),removeStartAndEndWildCharacters));
        if(service.getServiceAccessed3() !=null)
        service.setserviceAccessed3(OvcServiceManager.getConcatenatedServiceCodes(service.getServiceAccessed3().split(","),removeStartAndEndWildCharacters));
        if(service.getServiceAccessed4() !=null)
        service.setserviceAccessed4(OvcServiceManager.getConcatenatedServiceCodes(service.getServiceAccessed4().split(","),removeStartAndEndWildCharacters));
        if(service.getServiceAccessed5() !=null)
        service.setserviceAccessed5(OvcServiceManager.getConcatenatedServiceCodes(service.getServiceAccessed5().split(","),removeStartAndEndWildCharacters));
        if(service.getServiceAccessed6() !=null)
        service.setserviceAccessed6(OvcServiceManager.getConcatenatedServiceCodes(service.getServiceAccessed6().split(","),removeStartAndEndWildCharacters));
        if(service.getServiceAccessed7() !=null)
        service.setserviceAccessed7(OvcServiceManager.getConcatenatedServiceCodes(service.getServiceAccessed7().split(","),removeStartAndEndWildCharacters));
    }
    return service;
}
public void closeSession(Session session)
{
    if(session !=null && session.isOpen())
    {
        session.close();
    }
}

}


