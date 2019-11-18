/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.BirthRegistration;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.ChildStatusIndex;
import com.fhi.kidmap.business.FollowupSurvey;
import com.fhi.kidmap.business.HivStatusUpdate;
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.report.SummaryStatisticsBean;
import com.fhi.nomis.OperationsManagement.HivRecordsManager;
import com.fhi.nomis.nomisutils.DateManager;
import com.fhi.nomis.nomisutils.NomisConstant;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


/**
 *
 * @author HP USER
 */
public class FollowupDaoImpl implements FollowupDao 
{

    //private FollowupSurvey followup = null;
    //private FollowupSurvey[] followupObjects = null;
    Session session;
    Transaction tx;
    SessionFactory sessions;
    DaoUtil util=new DaoUtil();
    AppUtility appUtil=new AppUtility();
public List getDistinctOvcIdAndDateOfFollowup() throws Exception
{
    List list=null;
    try
     {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list= session.createQuery("select distinct fu.ovcId, fu.dateOfSurvey from FollowupSurvey fu order by fu.ovcId ").list();
        tx.commit();
        closeSession(session);
    }
    catch (Exception ex)
    {
        System.err.println(ex.getMessage());
        closeSession(session);
    }
   return list;
}
public FollowupSurvey getMostRecentFollowupRecord(String ovcId) throws Exception
{
    FollowupSurvey fu=null;
    List list = getFollowedupRecordsOrderedByDateDesc(ovcId);
    try
     {
         if(list !=null && !list.isEmpty())
         {
             fu=(FollowupSurvey)list.get(0);
         }
    }
    catch (Exception ex)
    {
        System.err.println(ex.getMessage());
    }
   return fu;
}
public List getFollowedupRecordsWithKnownHivStatusOrderedByDateDesc(String ovcId) throws Exception
{
    List list = new ArrayList();
    try
     {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list= session.createQuery("from FollowupSurvey fu where fu.ovcId=:id and fu.updatedHivStatus is not null and (fu.updatedHivStatus like '%positive%' or fu.updatedHivStatus like '%negative%') order by fu.dateOfSurvey desc").setString("id", ovcId).list();
        tx.commit();
        closeSession(session);
    }
    catch (Exception ex)
    {
        System.err.println(ex.getMessage());
        closeSession(session);
    }
   return list;
}
public List getFollowedupRecordsOrderedByDateDesc(String ovcId) throws Exception
{
    List list = new ArrayList();
    try
     {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list= session.createQuery("from FollowupSurvey fu where fu.ovcId=:id order by fu.dateOfSurvey desc").setString("id", ovcId).list();
        tx.commit();
        closeSession(session);
    }
    catch (Exception ex)
    {
        System.err.println(ex.getMessage());
        closeSession(session);
    }
   return list;
}
String hhOvcfollowupQueryPart=util.getHouseholdOvcFollowupQueryPart();//util.getHouseholdQueryPart()+" Ovc ovc,FollowupSurvey fu where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=fu.ovcId ";
public FollowupSurvey getLastFollowupSurveyForOvc(String ovcId) throws Exception
{
    FollowupSurvey fu=null;
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query="select distinct fu.dateOfSurvey from FollowupSurvey fu where fu.ovcId=:id order by fu.dateOfSurvey desc";
        System.err.println(query);
        List list = session.createQuery(query).setString("id", ovcId).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            String lastDateOfSurvey=(String)list.get(0);
            fu=getFollowup(ovcId,lastDateOfSurvey);
        }
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }   
    return fu;
}
public List getFollowedupRecordsWithSchoolDetails(String ovcId) throws Exception
{
    List list = new ArrayList();
    try
     {
       session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list= session.createQuery("from FollowupSurvey fu where fu.updatedSchoolStatus is not null and (UPPER(fu.updatedSchoolStatus) ='YES' or UPPER(fu.updatedSchoolStatus) ='NO') and fu.ovcId=:id order by fu.dateOfSurvey desc").setString("id", ovcId).list();
        tx.commit();
        closeSession(session);
    }
    catch (Exception ex)
    {
        System.err.println(ex.getMessage());
        closeSession(session);
    }

        return list;
}
public int getNumberOfFollowedupRecordsWithBirthRegistrationDetails(String additionalQueryCriteria) throws Exception
{
    int numberOfOvcWithBirthRegistrationDetails=0;
    List list = null;
    
         try
         {
           session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select count(distinct fu.ovcId)"+util.getHouseholdOvcFollowupQueryPart()+" and fu.updatedBirthCertStatus is not null and (UPPER(fu.updatedBirthCertStatus) ='YES' or UPPER(fu.updatedBirthCertStatus) ='NO') "+additionalQueryCriteria).list();
            tx.commit();
            closeSession(session);
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
            closeSession(session);
        }
        if(list !=null && !list.isEmpty())
        {
            numberOfOvcWithBirthRegistrationDetails=Integer.parseInt(list.get(0).toString());
        }
        return numberOfOvcWithBirthRegistrationDetails;
}
public List getFollowedupRecordsWithBirthRegistrationDetails(String additionalQuery) throws Exception
{
    List list = new ArrayList();
    List followupList=new ArrayList();
         try
         {
           session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list= session.createQuery(util.getHouseholdOvcFollowupBirthRegistrationQueryPart()+additionalQuery+" and fu.updatedBirthCertStatus is not null and (UPPER(fu.updatedBirthCertStatus) ='YES' or UPPER(fu.updatedBirthCertStatus) ='NO') and (br.birthRegistrationStatus ='No' and br.recordStatus='active' and br.pointOfUpdate !='followup') ").list();
            tx.commit();
            closeSession(session);
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
            closeSession(session);
        }
        if(list !=null && !list.isEmpty())
        {
            for(int i=0; i<list.size(); i++)
            {
                Object[] obj=(Object[])list.get(i);
                followupList.add(obj[2]);
            }
        }
        return followupList;
}
public int getNoOfOVCFollowedupPerCohort(String additionalQuery) throws Exception
{
    List list = new ArrayList();
    int numberOfOvc=0;
         try
         {
           session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list= session.createQuery("select count(distinct fu.ovcId) "+hhOvcfollowupQueryPart+additionalQuery).list();
            tx.commit();
            closeSession(session);
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
            closeSession(session);
        }
        if(list !=null && !list.isEmpty())
        numberOfOvc=Integer.parseInt(list.get(0).toString());
        return numberOfOvc;
}
public int getNoOfHivPositiveOVCFollowedupPerCohort(String additionalQuery) throws Exception
{
    List list = new ArrayList();
    int numberOfOvc=0;
       try
       {
           session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list= session.createQuery("select count(distinct fu.ovcId) "+hhOvcfollowupQueryPart+additionalQuery+" and UPPER(ovc.hivStatus) like '%POSITIVE%'").list();
            tx.commit();
            closeSession(session);
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
            closeSession(session);
        }
        if(list !=null && !list.isEmpty())
        {
            numberOfOvc=Integer.parseInt(list.get(0).toString());
        }
        return numberOfOvc;
}
public int getNoOfHivNegativeOVCFollowedupPerCohort(String additionalQuery) throws Exception
{
    List list = new ArrayList();
    int numberOfOvc=0;
       try
       {
           session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list= session.createQuery("select count(distinct fu.ovcId) "+hhOvcfollowupQueryPart+additionalQuery+" and UPPER(ovc.hivStatus) like '%NEGATIVE%'").list();
            tx.commit();
            closeSession(session);
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
            closeSession(session);
        }
        if(list !=null && !list.isEmpty())
        {
            numberOfOvc=Integer.parseInt(list.get(0).toString());
        }
        return numberOfOvc;
}
public int getNoOfHivUnknownOVCFollowedupPerCohort(String additionalQuery) throws Exception
{
    List list = new ArrayList();
    int numberOfOvc=0;
       try
       {
           session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list= session.createQuery("select count(distinct fu.ovcId) "+hhOvcfollowupQueryPart+additionalQuery+" and UPPER(ovc.hivStatus) like '%UNKNOWN%'").list();
            tx.commit();
            closeSession(session);
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
            closeSession(session);
        }
        if(list !=null && !list.isEmpty())
        {
            numberOfOvc=Integer.parseInt(list.get(0).toString());
        }
        return numberOfOvc;
}
public int getNoOfOVCNotFollowedupPerCohort(String additionalQuery) throws Exception
{
    OvcDao ovcdao=new OvcDaoImpl();
    SummaryStatisticsBean stb=new SummaryStatisticsBean();
    int numberOfOvcNotFollowedup=0;
    int totalNumberOfOvc=ovcdao.getNumberOfOvcEnrolledPerCohort(additionalQuery);
    int numberOfOvcFollowedup=getNoOfOVCFollowedupPerCohort(additionalQuery);
    numberOfOvcNotFollowedup=totalNumberOfOvc-numberOfOvcFollowedup;  
    if(numberOfOvcNotFollowedup <0)
    numberOfOvcNotFollowedup=0;
    return numberOfOvcNotFollowedup;
}
public List getListOfOVCFollowedupPerCohort(String additionalQuery) throws Exception
{
    List list = new ArrayList();
    List ovcList = new ArrayList();
         try
         {
           session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list= session.createQuery(hhOvcfollowupQueryPart+additionalQuery).list();
            tx.commit();
            closeSession(session);
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
            closeSession(session);
        }
        if(list !=null && !list.isEmpty())
        {
            List idList=new ArrayList();
            Ovc ovc=null;
            for(int i=0; i<list.size(); i++)
            {
                Object[] obj=(Object[])list.get(i);
                ovc=(Ovc)obj[1];
                if(idList.contains(ovc.getOvcId()))
                continue;
                ovcList.add(ovc);
                idList.add(ovc.getOvcId());
            }
        }
        return ovcList;
}
public List getListOfHivPositiveOVCFollowedupPerCohort(String additionalQuery) throws Exception
{
    List list = new ArrayList();
    List ovcList = new ArrayList();
       try
       {
           session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list= session.createQuery(hhOvcfollowupQueryPart+additionalQuery+" and UPPER(ovc.hivStatus) like '%POSITIVE%'").list();
            tx.commit();
            closeSession(session);
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
            closeSession(session);
        }
        if(list !=null && !list.isEmpty())
        {
            List idList=new ArrayList();
            Ovc ovc=null;
            for(int i=0; i<list.size(); i++)
            {
                Object[] obj=(Object[])list.get(i);
                ovc=(Ovc)obj[1];
                if(idList.contains(ovc.getOvcId()))
                continue;
                ovcList.add(ovc);
                idList.add(ovc.getOvcId());
            }
        }
        return ovcList;
}
public List getListOfHivNegativeOVCFollowedupPerCohort(String additionalQuery) throws Exception
{
    List list = new ArrayList();
    List ovcList = new ArrayList();
       try
       {
           session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list= session.createQuery(hhOvcfollowupQueryPart+additionalQuery+" and UPPER(ovc.hivStatus) like '%NEGATIVE%'").list();
            tx.commit();
            closeSession(session);
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
            closeSession(session);
        }
        if(list !=null && !list.isEmpty())
        {
            List idList=new ArrayList();
            Ovc ovc=null;
            for(int i=0; i<list.size(); i++)
            {
                Object[] obj=(Object[])list.get(i);
                ovc=(Ovc)obj[1];
                if(idList.contains(ovc.getOvcId()))
                continue;
                ovcList.add(ovc);
                idList.add(ovc.getOvcId());
            }
        }
        return ovcList;
}
public List getListOfHivUnknownOVCFollowedupPerCohort(String additionalQuery) throws Exception
{
    List list = new ArrayList();
    List ovcList = new ArrayList();
       try
       {
           session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list= session.createQuery(hhOvcfollowupQueryPart+additionalQuery+" and UPPER(ovc.hivStatus) like '%UNKNOWN%'").list();
            tx.commit();
            closeSession(session);
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
            closeSession(session);
        }
        if(list !=null && !list.isEmpty())
        {
            List idList=new ArrayList();
            Ovc ovc=null;
            for(int i=0; i<list.size(); i++)
            {
                Object[] obj=(Object[])list.get(i);
                ovc=(Ovc)obj[1];
                if(idList.contains(ovc.getOvcId()))
                continue;
                ovcList.add(ovc);
                idList.add(ovc.getOvcId());
            }
        }
        return ovcList;
}
public List getDistinctListOVCIdsFromFollowup(String additionalQuery) throws Exception
{
    List list = new ArrayList();
       try
       {
           session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list= session.createQuery("select distinct fu.ovcId "+hhOvcfollowupQueryPart+additionalQuery).list();
            tx.commit();
            closeSession(session);
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
            closeSession(session);
        }
        return list;
}
public List getListOfOVCNotFollowedupPerCohort(String additionalQuery) throws Exception
{
    OvcDao ovcdao=new OvcDaoImpl();
    List listOfOvcNotFollowedup=new ArrayList();
    List totalListOfOvc=ovcdao.getListOfOvcEnrolledPerCohort(additionalQuery);
    List listOfOvcFollowedupIds=getDistinctListOVCIdsFromFollowup(additionalQuery);
    for(int i=0; i<totalListOfOvc.size(); i++)
    {
        Ovc ovc=(Ovc)totalListOfOvc.get(i);
        if(listOfOvcFollowedupIds.contains(ovc.getOvcId()))
        continue;
        listOfOvcNotFollowedup.add(ovc);
    }
    return listOfOvcNotFollowedup;
}
public List getNoOfOVCWithUpdatedHIVStatusAtFollowup(String stateCode,String startDate,String endDate) throws Exception
{
    List list = new ArrayList();
    List mainList=new ArrayList();
    String additionalQuery=util.getFollowupDateQuery(startDate, endDate);
    mainList.add("Number of Ovc with updated HIV status at followup");
         try
         {
           session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list= session.createQuery("select hhe.stateCode,hhe.lgaCode, hhe.orgCode, hhe.communityCode,ovc.gender,ovc.currentAge, MONTH(fu.dateOfSurvey),YEAR(fu.dateOfSurvey),count(distinct fu.ovcId) "+hhOvcfollowupQueryPart+" and hhe.stateCode=:state and (UPPER(fu.updatedHivStatus) not like '%UNKNOWN%' and fu.updatedHivStatus is not null and fu.updatedHivStatus !='' and fu.updatedHivStatus !=' ' and fu.updatedHivStatus !='  ') and (UPPER(ovc.hivStatus) like '%UNKNOWN' or ovc.hivStatus='' or ovc.hivStatus=' ' or ovc.hivStatus='  ')"+additionalQuery+"  group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(fu.dateOfSurvey),YEAR(fu.dateOfSurvey)").setString("state", stateCode).list();
            tx.commit();
            closeSession(session);
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
            closeSession(session);
        }
        mainList.addAll(list);
        return mainList;
}
public List getNoOfHIVPositiveOVCIdentifiedAtFollowup(String stateCode,String startDate,String endDate) throws Exception
{
    List list = new ArrayList();
    List mainList=new ArrayList();
    String additionalQuery=util.getFollowupDateQuery(startDate, endDate);
    mainList.add("Number of HIV positive Ovc identified at followup");
         try
         {
           session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list= session.createQuery("select hhe.stateCode,hhe.lgaCode, hhe.orgCode, hhe.communityCode,ovc.gender,ovc.currentAge, MONTH(fu.dateOfSurvey),YEAR(fu.dateOfSurvey),count(distinct fu.ovcId) "+hhOvcfollowupQueryPart+" and hhe.stateCode=:state and (UPPER(fu.updatedHivStatus) like '%POSITIVE%') and (UPPER(ovc.hivStatus) not like '%POSITIVE%')"+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(fu.dateOfSurvey),YEAR(fu.dateOfSurvey)").setString("state", stateCode).list();
            tx.commit();
            closeSession(session);
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
            closeSession(session);
        }
        mainList.addAll(list);
        return mainList;
}
public List getNoOfOVCWithUpdatedBirthRegistrationAtFollowup(String state,String startDate,String endDate) throws Exception
{
    List list = new ArrayList();
    List mainList=new ArrayList();
    mainList.add("Number of Ovc provided birth registration at followup");
    String additionalQuery=util.getFollowupDateQuery(startDate, endDate);
         try
         {
           session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("select hhe.stateCode,hhe.lgaCode, hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(fu.dateOfSurvey),YEAR(fu.dateOfSurvey),count(distinct fu.ovcId) "+hhOvcfollowupQueryPart+" and UPPER(fu.updatedBirthCertStatus) ='YES' and (UPPER(ovc.birthCertificate)='NO' or ovc.birthCertificate='' or ovc.birthCertificate=' ' or ovc.birthCertificate='  ')"+additionalQuery+" group by hhe.stateCode,hhe.lgaCode, hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(fu.dateOfSurvey),YEAR(fu.dateOfSurvey)");
            list = query.list();
            tx.commit();
            closeSession(session);
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
            closeSession(session);
        }
        mainList.addAll(list);
        return mainList;
}
public List getNumberOfOVCEnrolledInSchoolAtFollowupPerMth(String indicatorName,String state,String startDate,String endDate) throws Exception
{
    List list = new ArrayList();
    List mainList=new ArrayList();
    mainList.add(indicatorName);
    String additionalQuery=util.getFollowupDateQuery(startDate, endDate);
         try
         {
           session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("select hhe.stateCode,hhe.lgaCode, hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(fu.dateOfSurvey),YEAR(fu.dateOfSurvey),count(distinct fu.ovcId) "+hhOvcfollowupQueryPart+" and UPPER(fu.updatedSchoolStatus) ='YES' and (UPPER(ovc.schoolStatus)='NO' or ovc.schoolStatus='' or ovc.schoolStatus=' ' or ovc.schoolStatus='  ')"+additionalQuery+" group by hhe.stateCode,hhe.lgaCode, hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(fu.dateOfSurvey),YEAR(fu.dateOfSurvey)");
            list = query.list();
            tx.commit();
            closeSession(session);
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
            closeSession(session);
        }
        mainList.addAll(list);
        return mainList;
}
public List getNumberOfActiveOVCEnrolledInSchoolAtFollowupPerMth(String indicatorName,String state,String startDate,String endDate) throws Exception
{
    List list = new ArrayList();
    List mainList=new ArrayList();
    mainList.add(indicatorName);
    String additionalQuery=util.getFollowupDateQuery(startDate, endDate);
         try
         {
           session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("select hhe.stateCode,hhe.lgaCode, hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(fu.dateOfSurvey),YEAR(fu.dateOfSurvey),count(distinct fu.ovcId) "+hhOvcfollowupQueryPart+" and UPPER(fu.updatedSchoolStatus) ='YES' and ovc.withdrawnFromProgram='No' and (UPPER(ovc.schoolStatus)='NO' or ovc.schoolStatus='' or ovc.schoolStatus=' ' or ovc.schoolStatus='  ')"+additionalQuery+" group by hhe.stateCode,hhe.lgaCode, hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(fu.dateOfSurvey),YEAR(fu.dateOfSurvey)");
            list = query.list();
            tx.commit();
            closeSession(session);
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
            closeSession(session);
        }
        mainList.addAll(list);
        return mainList;
}
public List getListOfActiveOVCEnrolledInSchoolAtFollowup(String additionalQuery) throws Exception
{
    List list = new ArrayList();
    //System.err.println(hhOvcfollowupQueryPart+" and UPPER(fu.updatedSchoolStatus) ='YES' and ovc.withdrawnFromProgram='No' and (UPPER(ovc.schoolStatus)='NO' or ovc.schoolStatus='' or ovc.schoolStatus=' ' or ovc.schoolStatus='  ')"+additionalQuery);
         try
         {
           session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            Query query = session.createQuery(hhOvcfollowupQueryPart+" and UPPER(fu.updatedSchoolStatus) ='YES' and ovc.withdrawnFromProgram='No' and (UPPER(ovc.schoolStatus)='NO' or ovc.schoolStatus='' or ovc.schoolStatus=' ' or ovc.schoolStatus='  ')"+additionalQuery);
            list = query.list();
            tx.commit();
            closeSession(session);
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
            closeSession(session);
        }
        return list;
}
public int getNumberOfActiveOVCEnrolledInSchoolAtFollowup(String additionalQuery) throws Exception
{
    List list = new ArrayList();
    //System.err.println("select count(distinct fu.ovcId) "+hhOvcfollowupQueryPart+" and UPPER(fu.updatedSchoolStatus) ='YES' and ovc.withdrawnFromProgram='No' and (UPPER(ovc.schoolStatus)='NO' or ovc.schoolStatus='' or ovc.schoolStatus=' ' or ovc.schoolStatus='  ')"+additionalQuery);
   // List mainList=new ArrayList();
    //mainList.add(indicatorName);
    int count=0;
    //String additionalQuery=util.getFollowupDateQuery(startDate, endDate);
         try
         {
           session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("select count(distinct fu.ovcId) "+hhOvcfollowupQueryPart+" and UPPER(fu.updatedSchoolStatus) ='YES' and ovc.withdrawnFromProgram='No' and (UPPER(ovc.schoolStatus)='NO' or ovc.schoolStatus='' or ovc.schoolStatus=' ' or ovc.schoolStatus='  ')"+additionalQuery);
            list = query.list();
            tx.commit();
            closeSession(session);
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
            closeSession(session);
        }
         if(list !=null && !list.isEmpty())
         {
             count=(Integer.parseInt(list.get(0).toString()));
         }
        
        return count;
}
public int getNumberOfActiveOVCNotInSchoolAtEnrollmentAndFollowup(String additionalQuery) throws Exception
{
    List list = new ArrayList();
    //System.err.println("select count(distinct fu.ovcId) "+hhOvcfollowupQueryPart+" and UPPER(fu.updatedSchoolStatus) ='No' and ovc.withdrawnFromProgram='No' and (UPPER(ovc.schoolStatus)='NO' or ovc.schoolStatus='' or ovc.schoolStatus=' ' or ovc.schoolStatus='  ')"+additionalQuery);
   // List mainList=new ArrayList();
    //mainList.add(indicatorName);
    int count=0;
    //String additionalQuery=util.getFollowupDateQuery(startDate, endDate);
         try
         {
           session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("select count(distinct fu.ovcId) "+hhOvcfollowupQueryPart+" and UPPER(fu.updatedSchoolStatus) ='NO' and ovc.withdrawnFromProgram='No' and (UPPER(ovc.schoolStatus)='NO' or ovc.schoolStatus='' or ovc.schoolStatus=' ' or ovc.schoolStatus='  ')"+additionalQuery);
            list = query.list();
            tx.commit();
            closeSession(session);
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
            closeSession(session);
        }
         if(list !=null && !list.isEmpty())
         {
             count=(Integer.parseInt(list.get(0).toString()));
         }
        
        return count;
}
public void normalizeFollowupAssessment() throws Exception
{
    HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
    ChildStatusIndexDao csidao=new ChildStatusIndexDaoImpl();

    List orgList=hhedao.getDistinctOrgCodes();
    if(orgList !=null && !orgList.isEmpty())
    {
        String orgCode=null;
        List fsList=null;
        FollowupSurvey fs=null;
        ChildStatusIndex csi=null;
        int count=0;
        for(int i=0; i<orgList.size(); i++)
        {
            orgCode=(String)orgList.get(i);
            fsList=getFollowupSurvey(" and hhe.orgCode='"+orgCode+"'");
            if(fsList !=null)
            {
                for(int j=0; j<fsList.size(); j++)
                {
                    fs=(FollowupSurvey)fsList.get(j);
                    csi=csidao.getChildStatusIndex(fs.getOvcId(), fs.getDateOfSurvey());
                    if(csi==null)
                    {
                        csi=new ChildStatusIndex();
                        csi.setCsiDate(fs.getDateOfSurvey());
                        csi.setOvcId(fs.getOvcId());
                        csi.setDateOfEntry(appUtil.getCurrentDate());
                        csi.setSurveyNumber(2);
                        csidao.saveChildStatusIndex(csi);
                        count++;
                        //System.err.println("csi with id "+csi.getOvcId()+" saved for Followup survey "+count);
                    }
                }
            }
        }
    }

}
public List getFollowupSurvey(String additionalQueryCriteria) throws Exception
{
         List list = new ArrayList();
         List fsList = new ArrayList();
         try
         {
           session = HibernateUtil.getSession();
            tx = session.beginTransaction();//hhOvcfollowupQueryPart
            Query query = session.createQuery(hhOvcfollowupQueryPart+additionalQueryCriteria);
            list = query.list();

            tx.commit();
            closeSession(session);
            if(list !=null)
            {
                for(int i=0; i<list.size(); i++)
                {
                    Object[] objArray=(Object[])list.get(i);
                    fsList.add((FollowupSurvey)objArray[2]);
                }
            }
        }
        catch (Exception ex)
        {
            ex.getMessage();
            closeSession(session);
        }
        return fsList;
}
public void changeOvcId(String oldId,String newId) throws Exception
{
    AppUtility appUtil=new AppUtility();
    List list=new ArrayList();
    try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("from FollowupSurvey fu where fu.ovcId = :id").setString("id", oldId).list();

        tx.commit();
        closeSession(session);
        } catch (HibernateException he) {
            closeSession(session);
            throw new Exception(he);
        }
    if(list !=null && !list.isEmpty())
    {
        FollowupSurvey fu=null;
        for(Object obj:list)
        {
            fu=(FollowupSurvey)obj;
            fu.setDateOfEntry(appUtil.getCurrentDate());
            /*Delete existing record and save a new one if a record with the new Id does not already exist*/
            deleteFollowup(fu);
            if(getFollowup(newId, fu.getDateOfSurvey())==null)
            {
                fu.setOvcId(newId);
                saveFollowup(fu,false,false);
            }
        }
    }
}
public FollowupSurvey getFollowup(String ovcId, String dateOfSurvey) throws Exception
{
        List list = new ArrayList();
        FollowupSurvey fu=null;
     try
     {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("from FollowupSurvey fu where fu.ovcId =:id and fu.dateOfSurvey =:dt")
         .setString("id", ovcId).setString("dt", dateOfSurvey).list();
        tx.commit();
        closeSession(session);
        if(list.size()>0)
         {
             fu = (FollowupSurvey)list.get(0);
         }
     } 
       catch (Exception ex)
       {
           ex.printStackTrace();
                closeSession(session);
       }
         
        return fu;
    }
public List getFollowupRecords(String ovcId, String dateOfSurvey) throws Exception
{
        List list = new ArrayList();
        
     try
     {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("from FollowupSurvey fu where fu.ovcId =:id and fu.dateOfSurvey =:dt")
         .setString("id", ovcId).setString("dt", dateOfSurvey).list();
        tx.commit();
        closeSession(session);
        
     } 
       catch (Exception ex)
       {
           ex.printStackTrace();
                closeSession(session);
       }
         
        return list;
    }
public List getDistinctFollowupIds(String additionalQueryCriteria) throws Exception
{

         List list = new ArrayList();
         try
         {
           session = HibernateUtil.getSession();
            tx = session.beginTransaction();//hhOvcfollowupQueryPart
            //Query query = session.createQuery("select distinct fu.ovcId from FollowupSurvey fu order by fu.ovcId");
            Query query = session.createQuery("select distinct fu.ovcId "+hhOvcfollowupQueryPart+additionalQueryCriteria);
            list = query.list();

            tx.commit();
            closeSession(session);
        }
        catch (Exception ex)
        {
            ex.getMessage();
            closeSession(session);
        }
        return list;
}
public List getDistinctFollowup(String additionalQueryCriteria) throws Exception
{

         List list = new ArrayList();
         List followupList = new ArrayList();
         List distinctFollowupIdList = getDistinctFollowupIds(additionalQueryCriteria);
         try
         {
           session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            if(distinctFollowupIdList !=null && distinctFollowupIdList.size()>0)
            {
                String ovcId=null;
                for(int i=0; i<distinctFollowupIdList.size(); i++)
                {
                    ovcId=distinctFollowupIdList.get(i).toString();
                    //Query query = session.createQuery("from FollowupSurvey fu where fu.ovcId =:ovc_id order by fu.ovcId").setString("ovc_id", distinctFollowupIdList.get(i).toString());
                    Query query = session.createQuery(hhOvcfollowupQueryPart+additionalQueryCriteria+" and fu.ovcId =:ovc_id order by fu.dateOfSurvey desc").setString("ovc_id", ovcId);
                    list = query.list();
                    followupList.add(list.get(0));
                }
            }
            tx.commit();
            closeSession(session);
        } 
         catch (Exception ex)
        {
            ex.getMessage();
            
            closeSession(session);
        }
        return followupList;
}
public List getFollowup() throws Exception
{

         List list = new ArrayList();
         try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

        Query query = session.createQuery("from FollowupSurvey fu order by fu.ovcId desc, fu.dateOfSurvey desc"); 
        list = query.list();

        tx.commit();
        closeSession(session);
        }
         catch (Exception ex)
        {
            ex.getMessage();
            closeSession(session);
        }
        return list;
}
public List getFollowupRecords(String orgQueryCriteria, String startDate,String endDate) throws Exception
{
        String sql="from FollowupSurvey followup, Ovc ovc where followup.ovcId=ovc.ovcId and followup.dateOfEntry between '"+startDate+"' and '"+endDate+"'"+orgQueryCriteria+" order by followup.ovcId desc, followup.dateOfSurvey desc";
        if(startDate.equalsIgnoreCase("All") || endDate.equalsIgnoreCase("All"))
        sql="from FollowupSurvey followup, Ovc ovc where followup.ovcId=ovc.ovcId "+orgQueryCriteria+" order by followup.ovcId desc, followup.dateOfSurvey desc";
         List list = new ArrayList();
         List followupList= new ArrayList();
         
         try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        Query query = session.createQuery(sql);

        list = query.list();

        tx.commit();
        closeSession(session);
        }
        catch (Exception ex)
        {
            ex.getMessage();
            closeSession(session);
        }
         if(list !=null)
        {
            FollowupSurvey followup=null;
            for(int i=0; i<list.size(); i++)
            {
                    Object[] obj=(Object[])list.get(i);
                    followup=(FollowupSurvey)obj[0];
                    followupList.add(followup);
            }
        }
        return followupList;
}
public List getFollowupRecordsWithCSIByOvcId(String ovcId) throws Exception
{

         List mainList = new ArrayList();
         
         try 
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from FollowupSurvey followup,ChildStatusIndex csi where followup.ovcId=csi.ovcId and followup.dateOfSurvey=csi.csiDate and followup.ovcId=:id").setString("id", ovcId).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    FollowupSurvey followup=(FollowupSurvey)objArray[0];
                    ChildStatusIndex csi=(ChildStatusIndex)objArray[1];
                    followup.setChildStatusIndex(csi);
                    mainList.add(followup);
                }
            }
        }
        catch (Exception ex)
        {
            ex.getMessage();
            closeSession(session);
        }
        return mainList;
}
public List getFollowupRecordsByOvcId(String ovcId) throws Exception
{

         List list = new ArrayList();
         
         try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
         list = session.createQuery("from FollowupSurvey followup where followup.ovcId=:id order by followup.dateOfSurvey desc").setString("id", ovcId).list();

        tx.commit();
        closeSession(session);
        }
        catch (Exception ex)
        {
            ex.getMessage();
            closeSession(session);
        }
        return list;
}
public List getFollowupRecordsByCaregiverId(String caregiverId) throws Exception
{
     List list = new ArrayList();
     try 
     {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("from FollowupSurvey followup where followup.caregiverId=:id order by followup.dateOfSurvey desc").setString("id", caregiverId).list();
        tx.commit();
        closeSession(session);
    }
    catch (Exception ex)
    {
        ex.getMessage();
        closeSession(session);
    }
    return list;
}
public String getOvcCaregiverId(String ovcId) throws Exception
{
    String caregiverId=null;
    OvcDao ovcdao=new OvcDaoImpl();
    Ovc ovc=ovcdao.getOvc(ovcId);
    if(ovc !=null)
    caregiverId=ovc.getCaregiverId();
    return caregiverId;
}
public void changeCaregiverId(String oldCaregiverId, String newCaregiverId) throws Exception
{
    List fsList=getFollowupRecordsByCaregiverId(oldCaregiverId);
    if(fsList !=null)
    {
        FollowupSurvey fs=null;
        for(int i=0; i<fsList.size(); i++)
        {
           fs=(FollowupSurvey)fsList.get(i);
           fs.setCaregiverId(newCaregiverId);
           updateFollowup(fs,false,false);
        }
    }
}
public void saveOvcHIVStatus(FollowupSurvey fu) throws Exception
{
    if(fu==null || fu.getOvcId()==null)
    return;
    try
    {
        HivStatusUpdateDao hsudao=new HivStatusUpdateDaoImpl();
        HivStatusUpdate hsu=new HivStatusUpdate();
        hsu.setClientEnrolledInCare(fu.getEnrolledInCare());
        hsu.setEnrolledOnART(fu.getEnrolledOnART());
        hsu.setOrganizationClientIsReferred(fu.getFacilityId());
        hsu.setClientId(fu.getOvcId());
        hsu.setClientType("ovc");
        hsu.setDateModified(DateManager.getCurrentDate());
        hsu.setDateOfCurrentStatus(fu.getDateOfSurvey());
        hsu.setHivStatus(HivRecordsManager.getHivStatusScode(fu.getUpdatedHivStatus()));
        hsu.setRecordStatus("active");
        hsu.setPointOfUpdate("followup");
        hsudao.saveHivStatusUpdate(hsu);
    }
    catch(Exception ex)
    {
        ex.printStackTrace();
    }
}
public void saveOvcBirthRegistrationStatus(FollowupSurvey fu) throws Exception
{
    if(fu==null || fu.getOvcId()==null)
    return;
    try
    {
        //if(fu.getUpdatedBirthCertStatus() !=null && (fu.getUpdatedBirthCertStatus().equalsIgnoreCase("Yes") || fu.getUpdatedBirthCertStatus().equalsIgnoreCase("No")))
       //{
            BirthRegistrationDao brdao=new BirthRegistrationDaoImpl();
            BirthRegistration br=new BirthRegistration();
            br.setClientId(fu.getOvcId());
            br.setClientType("ovc");
            br.setBirthRegistrationStatus(fu.getUpdatedBirthCertStatus());
            br.setDateModified(DateManager.getCurrentDate());
            br.setDateOfRegistration(fu.getDateOfSurvey());
            br.setRecordStatus("active");
            br.setPointOfUpdate(fu.getPointOfService());
            brdao.saveBirthRegistration(br);
        //}
    }
    catch(Exception ex)
    {
        ex.printStackTrace();
    }
}
public void saveFollowup(FollowupSurvey followup,boolean saveHiv,boolean saveBirthRegistration) throws Exception {
   try {
            if(followup.getCaregiverId()==null)
            followup.setCaregiverId(getOvcCaregiverId(followup.getOvcId()));
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.save(followup);
            tx.commit();
            closeSession(session);
            //System.err.println("followup with id "+followup.getOvcId()+" saved");
            if(saveHiv)
            saveOvcHIVStatus(followup);
            if(saveBirthRegistration)                
            saveOvcBirthRegistrationStatus(followup);
        }
   catch (Exception ex)
   {
       System.err.println("Exception at followup update "+ex.getMessage());
       ex.printStackTrace();
            //closeSession(session);
           //throw new Exception(he);
   }
}

    public void updateFollowup(FollowupSurvey followup,boolean saveHiv,boolean saveBirthRegistration) throws Exception
    {
       try
       {
            if(followup.getCaregiverId()==null)
            followup.setCaregiverId(getOvcCaregiverId(followup.getOvcId()));
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.update(followup);
            tx.commit();
            closeSession(session);
            if(saveHiv)
            saveOvcHIVStatus(followup);
            if(saveBirthRegistration)                
            saveOvcBirthRegistrationStatus(followup);
        }
        catch (Exception ex)
        {
            System.err.println("Exception at followup update "+ex.getMessage());
            ex.printStackTrace();
            closeSession(session);
        }
   }
public void markedForDelete(FollowupSurvey followup) throws Exception
    {
        try
        {
            followup.setMarkedForDelete(NomisConstant.MARKEDFORDELETE);
            updateFollowup(followup,false,false);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
public void deleteFollowup(FollowupSurvey followup) throws Exception {

       try 
       {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.delete(followup);
            tx.commit();
            closeSession(session);
            util.saveDeletedRecord(followup.getOvcId(), null, "followup", followup.getDateOfSurvey());
            HivRecordsManager.deleteHivStatusRecord(followup.getOvcId(), followup.getDateOfSurvey(),NomisConstant.FOLLOWUP_POINTOFUPDATE);
        }
        catch (Exception ex)
        {
            ex.getMessage();
            closeSession(session);
        }


}
public void deleteOvcFollowups(String ovcId) throws Exception
{
    List list=getFollowupRecordsByOvcId(ovcId);
    if(list !=null)
    {
        for(int i=0; i<list.size(); i++)
        {
            FollowupSurvey fs=(FollowupSurvey)list.get(i);
            deleteFollowup(fs);

        }
    }
}
public void closeSession(Session session)
{
    if(session !=null && session.isOpen())
    {
        session.close();
    }
}
}
