
 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.BirthRegistration;
import com.fhi.kidmap.business.Caregiver;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.ChildStatusIndex;
import com.fhi.kidmap.business.HivStatusUpdate;
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.business.OvcWithdrawal;
import com.fhi.kidmap.report.IndicatorDictionary;
import com.fhi.kidmap.report.ReportUtility;
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
public class OvcDaoImpl implements OvcDao {

    private Ovc ovc = null;
    Session session;
    Transaction tx;
    SessionFactory sessions;
    DaoUtil util=new DaoUtil();
    AppUtility appUtil=new AppUtility();
public int getNumberOfOvcAge5To20InHousehold(String hhUniqueId) throws Exception
{
    int count=0;
    //List list =null;
    try
    {
        //String query="select count(distinct ovc.ovcId) "+util.getHouseholdOvcQueryPart()+"and ovc.hhUniqueId='"+hhUniqueId+"' and (ovc.currentAge>4 and ovc.currentAge<21) and (UPPER(ovc.currentAgeUnit) !='MONTH')";
        String query="select count(distinct ovc.ovcId) from Ovc ovc where ovc.hhUniqueId='"+hhUniqueId+"' and (ovc.currentAge>4 and ovc.currentAge<21) and (UPPER(ovc.currentAgeUnit) !='MONTH')";
        System.err.println("query in getListOfOvcAge5To20InHousehold() is "+query);
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
    catch(Exception ex)
    {
        closeSession(session);
        ex.printStackTrace();
    }
    return count;
}
public int getNumberOfRecordsWithIncorrectCurrentAgeUnit() throws Exception
{
    List list =null;
    int count=0;
    try
    {
        String query="select count(distinct ovc.ovcId) from Ovc ovc where (UPPER(ovc.currentAgeUnit) !='MONTH' and UPPER(ovc.currentAgeUnit) !='YEAR')";
        System.err.println("query in getRecordsWithIncorrectCurrentAgeUnit() is "+query);
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
    catch(Exception ex)
    {
        closeSession(session);
        ex.printStackTrace();
    }
    return count;
}
public int setCurrentAgeUnitByCommunity(String communityCode) throws Exception
{
    List list =null;
    int count=0;
    try
    {
        String query=util.getHouseholdOvcQueryPart()+" and hhe.communityCode=:cc and (UPPER(ovc.currentAgeUnit) !='MONTH' and UPPER(ovc.currentAgeUnit) !='YEAR')";
        System.err.println("query in getRecordsWithoutCurrentAgeUnitsByCommunity is "+query);
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery(query).setString("cc", communityCode).list();
        tx.commit();
        closeSession(session);
        if(list !=null && !list.isEmpty())
        {
            for(Object obj:list)
            {
                Object[] objArray=(Object[])obj;
                Ovc ovc=(Ovc)objArray[1];
                ovc=util.getOvcWithCurrentAge(ovc);
                updateOvc(ovc, false, false);
                count++;
            }
        }
    }
    catch(Exception ex)
    {
        closeSession(session);
        ex.printStackTrace();
    }
    return count;
}
public List getListOfOvcWithoutHivRecords() throws Exception
{
    List ovcList =new ArrayList();
    try
    {
        String query=util.getHouseholdOvcHIVQueryPart()+" and ovc.ovcId not in (select distinct hsu.clientId from HivStatusUpdate hsu )";
        System.err.println("query in getListOfOvcWithoutHivRecords() is "+query);
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        List list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            for(Object obj:list)
            {
                Object[] objArray=(Object[])obj;
                ovcList.add(objArray[1]);
            }
        }
    }
    catch(Exception ex)
    {
        closeSession(session);
        ex.printStackTrace();
    }
    return ovcList;
}
public List getListOfOvcWithoutHivRecordsByCommunity(String communityCode) throws Exception
{
    List ovcList =new ArrayList();
    try
    {
        String query=util.getHouseholdOvcHIVQueryPart()+" and hhe.communityCode=:cc and ovc.ovcId not in (select distinct hsu.clientId from HivStatusUpdate hsu )";
        System.err.println("query in getListOfOvcWithoutHivRecordsByCommunity is "+query);
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        List list = session.createQuery(query).setString("cc", communityCode).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            for(Object obj:list)
            {
                Object[] objArray=(Object[])obj;
                ovcList.add(objArray[1]);
            }
        }
    }
    catch(Exception ex)
    {
        closeSession(session);
        ex.printStackTrace();
    }
    return ovcList;
}
public List getListOfOvcWithoutLastServiceDateByCommunity(String communityCode) throws Exception
{
    List ovcList =new ArrayList();
    try
    {
        String query=util.getHouseholdOvcQueryPart()+" and hhe.communityCode=:cc and ovc.lastServiceDate='1900-01-01'";
        System.err.println("query in getListOfOvcByCommunity is "+query);
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        List list = session.createQuery(query).setString("cc", communityCode).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            for(Object obj:list)
            {
                Object[] objArray=(Object[])obj;
                ovcList.add(objArray[1]);
            }
        }
    }
    catch(Exception ex)
    {
        closeSession(session);
        ex.printStackTrace();
    }
    return ovcList;
}
public List getListOfOvcByCommunity(String communityCode) throws Exception
{
    List ovcList =new ArrayList();
    try
    {
        String query=util.getHouseholdOvcQueryPart()+" and hhe.communityCode=:cc";
        System.err.println("query in getListOfOvcByCommunity is "+query);
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        List list = session.createQuery(query).setString("cc", communityCode).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            for(Object obj:list)
            {
                Object[] objArray=(Object[])obj;
                ovcList.add(objArray[1]);
            }
        }
    }
    catch(Exception ex)
    {
        closeSession(session);
        ex.printStackTrace();
    }
    return ovcList;
}
public List getRecordsWithoutCurrentAgeUnitsByCommunity(String communityCode) throws Exception
{
    List ovcList =new ArrayList();
    try
    {
        String query=util.getHouseholdOvcQueryPart()+" and hhe.communityCode=:cc and ovc.currentAgeUnit is null";
        System.err.println("query in getRecordsWithoutCurrentAgeUnitsByCommunity is "+query);
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        List list = session.createQuery(query).setString("cc", communityCode).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            for(Object obj:list)
            {
                Object[] objArray=(Object[])obj;
                ovcList.add(objArray[1]);
            }
        }
    }
    catch(Exception ex)
    {
        closeSession(session);
        ex.printStackTrace();
    }
    return ovcList;
}
public List getDistinctCommunityCodesWithIncorrectCurrentAgeUnit() throws Exception
{
    List list =null;
    try
    {
        String query="select distinct hhe.communityCode "+util.getHouseholdOvcQueryPart()+" and (UPPER(ovc.currentAgeUnit) !='MONTH' and UPPER(ovc.currentAgeUnit) !='YEAR')";
        System.err.println("query in getDistinctCommunityCodesWithIncorrectCurrentAgeUnit() is "+query);
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery(query).list();
        tx.commit();
        session.close();
    }
    catch(Exception ex)
    {
        closeSession(session);
        ex.printStackTrace();
    }
    return list;
}
public List getDistinctOvcIdsByCommunity(String communityCode) throws Exception
{
    List list =null;
    try
    {
        String query="select distinct ovc.ovcId "+util.getHouseholdOvcQueryPart()+" and hhe.communityCode=:cc";
        System.err.println("query in getDistinctOvcIdsByCommunity is "+query);
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery(query).setString("cc", communityCode).list();
        tx.commit();
        session.close();
    }
    catch(Exception ex)
    {
        closeSession(session);
        ex.printStackTrace();
    }
    return list;
}
public List getListOfOvcNewlyEnrolled(String additionalQuery,String startDate,String endDate,boolean currentlyEnrolled) throws Exception
{
    String currentlyEnrolledQuery=" ";
    if(currentlyEnrolled)
    currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
    if(additionalQuery==null)
    additionalQuery=" ";
    List ovcList =new ArrayList();
    try
    {
        String query=util.getHouseholdOvcQueryPart()+additionalQuery+currentlyEnrolledQuery+util.getEnrollmentDateQuery(startDate,endDate);
        System.err.println("query in getListOfOvcNewlyEnrolled is "+query);
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        List list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            int count=0;
            System.err.println("list.size() in getListOfOvcEnrolledBeforeTheStartOfReportPeriod is "+list.size());
            for(Object obj:list)
            {
                Object[] objArray=(Object[])obj;
                Ovc ovc=(Ovc)objArray[1];
                ovcList.add(ovc);
                //System.err.println("ovc.getOvcId() at "+(++count)+" in getListOfOvcEnrolledBeforeTheStartOfReportPeriod is "+ovc.getOvcId());
            }
        }
    }
    catch(Exception ex)
    {
        closeSession(session);
        ex.printStackTrace();
    }
    return ovcList;
}
public List getListOfOvcEnrolledBeforeTheStartOfReportPeriod(String additionalQuery,String startDate,boolean currentlyEnrolled) throws Exception
{
    String currentlyEnrolledQuery=" ";
    if(currentlyEnrolled)
    currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
    if(additionalQuery==null)
    additionalQuery=" ";
    List ovcList =new ArrayList();
    try
    {
        String query=util.getHouseholdOvcQueryPart()+additionalQuery+currentlyEnrolledQuery+util.getEnrollmentEndDateQuery(startDate);
        System.err.println("query in getListOfOvcEnrolledBeforeTheStartOfReportPeriod is "+query);
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        List list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            int count=0;
            System.err.println("list.size() in getListOfOvcEnrolledBeforeTheStartOfReportPeriod is "+list.size());
            for(Object obj:list)
            {
                Object[] objArray=(Object[])obj;
                Ovc ovc=(Ovc)objArray[1];
                ovcList.add(ovc);
                //System.err.println("ovc.getOvcId() at "+(++count)+" in getListOfOvcEnrolledBeforeTheStartOfReportPeriod is "+ovc.getOvcId());
            }
        }
    }
    catch(Exception ex)
    {
        closeSession(session);
        ex.printStackTrace();
    }
    return ovcList;
}
public int getNumberOfOvcThatHasNoHIVRiskAssessmentRecord(String additionalQuery) throws Exception
{
    int count=0;
    try
    {
        String query="select count(distinct ovc.ovcId) "+util.getHouseholdOvcHIVQueryPart()+additionalQuery+" and hsu.hivStatus !='"+NomisConstant.HIV_POSITIVE+"' and ovc.ovcId not in (select distinct hrac.ovcId from HivRiskAssessmentChecklist hrac)";
        System.err.println("query in getNumberOfOvcThatHasNoHIVRiskAssessmentRecord is "+query);
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
    catch(Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }
    return count;
}
public List getListOfOvcThatHasNoHIVRiskAssessmentRecord(String additionalQuery) throws Exception
{
    List mainList = new ArrayList();
    try
    {
        String query=util.getHouseholdOvcHIVQueryPart()+additionalQuery+" and hsu.hivStatus !='"+NomisConstant.HIV_POSITIVE+"' and ovc.ovcId not in (select distinct hrac.ovcId from HivRiskAssessmentChecklist hrac)";
        System.err.println("query in getNumberOfOvcThatHasNoHIVRiskAssessmentRecord is "+query);
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        List list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            List ovcIdList=new ArrayList();
            for(Object obj:list)
            {
                Object[] objArray=(Object[])obj;
                Ovc ovc=(Ovc)objArray[1];
                if(!ovcIdList.contains(ovc.getOvcId()))
                {
                    mainList.add(ovc);
                    ovcIdList.add(ovc.getOvcId());
                }
            }
        }
    }
    catch(Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }
    return mainList;
}
public List getListOfOvcEnrolledWithinTheReportPeriod(String additionalQuery,String startDate,String endDate) throws Exception
{
    List mainList = new ArrayList();
    try
    {
        String query=util.getHouseholdOvcQueryPart()+additionalQuery+" and ovc.dateEnrollment between '"+startDate+"' and '"+endDate+"'";
        System.err.println("query in getListOfOvcEnrolledWithinTheReportPeriod is "+query);
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        List list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            for(Object obj:list)
            {
                Object[] objArray=(Object[])obj;
                Ovc ovc=(Ovc)objArray[1];
                mainList.add(ovc);
            }
        }
    }
    catch(Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }
    return mainList;
}
public int getNumberOfOvcCurrentlyEnrolledInHouseholdsWithBaselineAssessment(String additionalQuery) throws Exception
{
    List list = new ArrayList();
    int numberOfHouseholds=0;
        try
        {
            String query="select count(distinct ovc.ovcId)"+util.getHouseholdQueryPart()+"HouseholdVulnerabilityAssessment hva, Ovc ovc where hhe.hhUniqueId=hva.hhUniqueId and hhe.hhUniqueId=ovc.hhUniqueId "+additionalQuery+" and (hva.assessmentNo=1 and hva.vulnerabilityScore>"+NomisConstant.NOTVULNERABLE_ENDVALUE+")";
            //System.err.println("query in getNumberOfOvcCurrentlyEnrolledInHouseholdsWithBaselineAssessment is "+query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(query).list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        if(list !=null && !list.isEmpty())
        {
            numberOfHouseholds=Integer.parseInt(list.get(0).toString());
        }
        return numberOfHouseholds;
} 
public List getListOfOvcCurrentlyEnrolledInHouseholdsWithBaselineAssessment(String additionalQuery) throws Exception
{
    List mainList = new ArrayList();
    try
    {
        String query=util.getHouseholdQueryPart()+"HouseholdVulnerabilityAssessment hva, Ovc ovc where hhe.hhUniqueId=hva.hhUniqueId and hhe.hhUniqueId=ovc.hhUniqueId "+additionalQuery+" and (hva.assessmentNo=1 and hva.vulnerabilityScore>"+NomisConstant.NOTVULNERABLE_ENDVALUE+")";
        //System.err.println("query in getListOfOvcCurrentlyEnrolledInHouseholdsWithBaselineAssessment is "+query);
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        List list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            List ovcIdList = new ArrayList();
            for(Object obj:list)
            {
                Object[] objArray=(Object[])obj;
                Ovc ovc=(Ovc)objArray[2];
                if(!ovcIdList.contains(ovc.getOvcId()))
                {
                    mainList.add(objArray[2]);
                    ovcIdList.add(ovc.getOvcId());
                }
            }
        }
    }
    catch(Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }
    return mainList;
}
public List getListOfHivPosOvcCurrentlyEnrolledBasedOnVulnerability(String additionalQuery,int startValue,int endValue) throws Exception
{
    List list = new ArrayList();
    List mainList = new ArrayList();
    List ovcIdList = new ArrayList();
    try
    {
        String query=util.getHouseholdQueryPart()+"HouseholdVulnerabilityAssessment hva, Ovc ovc, HivStatusUpdate hsu where hhe.hhUniqueId=hva.hhUniqueId and hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=hsu.clientId "+additionalQuery+util.getActiveHivStatusCriteria(NomisConstant.HIV_POSITIVE, NomisConstant.OVC_TYPE)+" and (hva.assessmentNo=1 and hva.vulnerabilityScore between "+startValue+" and "+endValue+") ";
        System.err.println("query in getNumberOfHivPosOvcCurrentlyEnrolledBasedOnVulnerability is "+query);
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            System.err.println("list.size() is "+list.size());
            for(Object obj:list)
            {
                Object[] objArray=(Object[])obj;
                Ovc ovc=(Ovc)objArray[2];
                if(!ovcIdList.contains(ovc.getOvcId()))
                {
                    mainList.add(objArray[2]);
                    ovcIdList.add(ovc.getOvcId());
                }
            }
        }
    }
    catch(ClassCastException ex)
    {
        session.close();
        System.err.println("Class cast error occured: "+ex.getMessage());
    }
    catch(Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }
    return mainList;
}
public int getNumberOfHivPosOvcCurrentlyEnrolledBasedOnVulnerability(String additionalQuery,int startValue,int endValue) throws Exception
{
    List list = new ArrayList();
    int numberOfHouseholds=0;
        try
        {
            String query="select count(distinct ovc.ovcId)"+util.getHouseholdQueryPart()+"HouseholdVulnerabilityAssessment hva, Ovc ovc, HivStatusUpdate hsu where hhe.hhUniqueId=hva.hhUniqueId and hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=hsu.clientId "+additionalQuery+util.getActiveHivStatusCriteria(NomisConstant.HIV_POSITIVE, NomisConstant.OVC_TYPE)+" and (hva.assessmentNo=1 and hva.vulnerabilityScore between "+startValue+" and "+endValue+") ";
            //System.err.println("query in getNumberOfHivPosOvcCurrentlyEnrolledBasedOnVulnerability is "+query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(query).list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        if(list !=null && !list.isEmpty())
        {
            numberOfHouseholds=Integer.parseInt(list.get(0).toString());
        }
        return numberOfHouseholds;
} 
public List getListOfAdolescentsWhoseHouseholdsWereProvidedEconomicStrengthening(String additionalQuery,String startDate,String endDate,String currentlyEnrolledQuery) throws Exception
{
    List listOfAdolescents=new ArrayList();
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query=util.getHouseholdOvcHouseholdServiceQueryPart()+additionalQuery+util.getAgeCriteria(10+"",19+"")+currentlyEnrolledQuery+util.getHouseholdEconomicStrentheningServiceQueryPart()+util.getHouseholdServiceDateQuery(startDate,endDate);
        //System.err.println(query);
        List list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null)
        {
            List ovcIdList=new ArrayList();
            Ovc ovc=null;
            for(Object obj:list)
            {
                Object[] objArray=(Object[])obj;
                ovc=(Ovc)objArray[1];
                if(!ovcIdList.contains(ovc.getOvcId()))
                {
                    listOfAdolescents.add(ovc);
                    ovcIdList.add(ovc.getOvcId());
                }
            }
        }
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }   
    return listOfAdolescents;
}
public int getNumberOfAdolescentsWhoseHouseholdsWereProvidedEconomicStrengthening(String additionalQuery,String startDate,String endDate,String currentlyEnrolledQuery) throws Exception
{
    int numberOfAdolescents=0;
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query="select count (distinct ovc.ovcId)"+util.getHouseholdOvcHouseholdServiceQueryPart()+additionalQuery+util.getAgeCriteria(10+"",19+"")+currentlyEnrolledQuery+util.getHouseholdEconomicStrentheningServiceQueryPart()+util.getHouseholdServiceDateQuery(startDate,endDate);
        //System.err.println(query);
        List list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            numberOfAdolescents=Integer.parseInt(list.get(0).toString());
        }
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }   
    return numberOfAdolescents;
}
public int getNumberOfOvcWhoseHouseholdsWereProvidedEconomicStrengthening(String additionalQuery,String startDate,String endDate,String currentlyEnrolledQuery,int startAge,int endAge) throws Exception
{
    int numberOfAdolescents=0;
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query="select count (distinct ovc.ovcId)"+util.getHouseholdOvcHouseholdServiceQueryPart()+additionalQuery+util.getAgeCriteria(startAge+"",endAge+"")+currentlyEnrolledQuery+util.getHouseholdEconomicStrentheningServiceQueryPart()+util.getHouseholdServiceDateQuery(startDate,endDate);
        //System.err.println(query);
        List list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            numberOfAdolescents=Integer.parseInt(list.get(0).toString());
        }
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }   
    return numberOfAdolescents;
}
public List getListOfOvcWhoseHouseholdsWereProvidedEconomicStrengthening(String additionalQuery,String startDate,String endDate,String currentlyEnrolledQuery,int startAge,int endAge) throws Exception
{
    List listOfAdolescents=new ArrayList();
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query=util.getHouseholdOvcHouseholdServiceQueryPart()+additionalQuery+util.getAgeCriteria(startAge+"",endAge+"")+currentlyEnrolledQuery+util.getHouseholdEconomicStrentheningServiceQueryPart()+util.getHouseholdServiceDateQuery(startDate,endDate);
        //System.err.println(query);
        List list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null)
        {
            List ovcIdList=new ArrayList();
            Ovc ovc=null;
            for(Object obj:list)
            {
                Object[] objArray=(Object[])obj;
                ovc=(Ovc)objArray[1];
                if(!ovcIdList.contains(ovc.getOvcId()))
                {
                    listOfAdolescents.add(ovc);
                    ovcIdList.add(ovc.getOvcId());
                }
            }
        }
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }   
    return listOfAdolescents;
}
public List getOvcWithYesWithdrawalStatus() throws Exception
{
    List mainList=new ArrayList();
    List list=util.getHouseholdEnrollmentDaoInstance().getDistinctCommunityCodes();
    try
    {
        if(list !=null && !list.isEmpty())
        {
            String communityCode=null;
            List ovcList=null;
            for(Object obj:list)
            {
                if(obj !=null)
                communityCode=obj.toString();
                else
                communityCode=(String)obj;
                ovcList=getOvcWithYesWithdrawalStatus(communityCode);
                if(ovcList !=null && !ovcList.isEmpty())
                {
                    mainList.addAll(ovcList); 
                    System.err.println("Community code is "+communityCode+" and ovcList size is "+ovcList.size());
                }
            }
        }
        
    }
    catch (Exception ex)
    {
        ex.printStackTrace();
    }
   return mainList;
}
public List getOvcWithYesWithdrawalStatus(String communityCode) throws Exception
{
    List ovcList=new ArrayList();
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        List list = session.createQuery(util.getHouseholdOvcQueryPart()+" and hhe.communityCode=:cc and ovc.withdrawnFromProgram='Yes'").setString("cc", communityCode).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            Ovc ovc=null;
            for(Object obj:list)
            {
                Object[] ovcArray=(Object[])obj;
                ovc=(Ovc)ovcArray[1];
                ovcList.add(ovc);
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

public List getActiveOvcFromWithdrawalRecords() throws Exception
{
    
    List listOfOvc=new ArrayList();
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        List list = session.createQuery("from Ovc ovc, OvcWithdrawal withdrawal where ovc.ovcId=withdrawal.ovcId and ovc.withdrawnFromProgram='No'").list();
        tx.commit();
        session.close();
        if(list !=null)
        {
            for(Object s:list)
            {
                Object[] obj=(Object[])s;
                listOfOvc.add(obj[0]);
            }
        }
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }
    
    return listOfOvc;
}
public List getListOfOvcByOvcId(List listOfOvcIds) throws Exception
{
    List mainList=new ArrayList();
    Ovc ovc=null;
    try
    {
        if(listOfOvcIds !=null)
        {
            String ovcId=null;
            for(int i=0;i<listOfOvcIds.size(); i++)
            {
                if(listOfOvcIds.get(i) !=null)
                ovcId=listOfOvcIds.get(i).toString();
                //System.err.println("ovcId in getListOfOvcByOvcId is "+ovcId);
                ovc=getOvc(ovcId);
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
public int getNumberOfOvcEnrolledFromHouseholdsWithChronicallyIllMembers(String additionalQueryCriteria) throws Exception
{
    List list=null;
    int numberOfOvc=0;
    
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query="select count(distinct ovc.ovcId)"+util.getHouseholdQueryPart()+"Ovc ovc, HouseholdVulnerabilityAssessment hva where hhe.hhUniqueId=hva.hhUniqueId and hhe.hhUniqueId=ovc.hhUniqueId and (hva.health =4)"+additionalQueryCriteria;
        System.err.println(query);
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
public int getNoOfOvcThatAreHIVPositive(String additionalQueryCriteria) throws Exception
{
    List list=null;
    int numberOfOvc=0;
    
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query="select count(distinct ovc.ovcId)"+util.getHouseholdQueryPart()+"Ovc ovc, HivStatusUpdate hsu where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=hsu.clientId "+util.getActiveHivStatusCriteria(NomisConstant.HIV_POSITIVE, NomisConstant.OVC_TYPE)+additionalQueryCriteria;
        System.err.println(query);
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
public List getListOfOvcThatAreHIVPositive(String additionalQueryCriteria) throws Exception
{
    List list=null;
    List ovcList=new ArrayList();
    
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query=util.getHouseholdQueryPart()+"Ovc ovc, HivStatusUpdate hsu where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=hsu.clientId and "+util.getActiveHivStatusCriteria(NomisConstant.HIV_POSITIVE, NomisConstant.OVC_TYPE)+additionalQueryCriteria;
        System.err.println(query);
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
        for(Object obj:list)
        {
            Object[] objArray=(Object[])obj;
            ovcList.add(objArray[1]);
        }
    }
    return ovcList;
}
    public List getOvcListPerCaregiver(String caregiverId) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from Ovc ovc where ovc.caregiverId=:cgId").setString("cgId", caregiverId).list();
            tx.commit();
            session.close();
        }
        catch (Exception ex)
        {
            session.close();
            ex.printStackTrace();
            //throw new Exception(he);
        }
        return list;
    }
    public int getNumberOfOvcPerHousehold(String hhUniqueId, boolean currentlyEnrolled) throws Exception
    {
        int count=0;
        try
        {
            String currentlyEnrolledQuery="";
            if(currentlyEnrolled)
            currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
            String query="select count (distinct ovc.ovcId) "+util.getHouseholdQueryPart()+" Ovc ovc where hhe.hhUniqueId=ovc.hhUniqueId and ovc.hhUniqueId=:hhId"+currentlyEnrolledQuery;
            System.err.println("Query in getNumberOfOvcPerHousehold is "+query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list= session.createQuery(query).setString("hhId", hhUniqueId).list();
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
    public List getOvcListPerHousehold(String hhUniqueId) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from Ovc ovc where ovc.hhUniqueId=:hhId").setString("hhId", hhUniqueId).list();
            tx.commit();
            session.close();
        }
        catch (Exception ex)
        {
            session.close();
            ex.printStackTrace();
            //throw new Exception(he);
        }
        return list;
    }
    public int getNumberOfOvcEnrolledPerCohort(String additionalQuery) throws Exception
    {
        List list=null;
        int numberOfOvc=0;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select count(distinct ovc.ovcId)"+util.getHouseholdOvcQueryPart()+additionalQuery).list();
            tx.commit();
            session.close();
        }
        catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
        if(list !=null && !list.isEmpty())
        numberOfOvc=Integer.parseInt(list.get(0).toString());
        return numberOfOvc;
    }
    public List getListOfOvcEnrolledPerCohort(String additionalQuery) throws Exception
    {
        List list=null;
        List ovcList=new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(util.getHouseholdOvcQueryPart()+additionalQuery).list();
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
                ovcList.add(obj[1]);
            }
        }
        return ovcList;
    }
    public List getNumberOfOvcThatDroppedOutOfSchoolPerMonthByCBO(String stateCode,String startDate,String endDate) throws Exception
    {
        List list=null;
        List mainList=new ArrayList();
        String additionalQuery=util.getEnrollmentDateQuery(startDate, endDate);
        IndicatorDictionary ind =new IndicatorDictionary();
        mainList.add(ind.getIndicatorForNumberOfOvcThatDroppedOutOfSchoolPerMonthByCBO().getIndicatorName());
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge, count(distinct ovc.ovcId)"+util.getHouseholdQueryPart()+"Ovc ovc,FollowupSurvey fs where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=fs.ovcId and (ovc.schoolStatus='Yes' and fs.updatedSchoolStatus='No') and hhe.stateCode=:state "+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge").setString("state", stateCode).list();
            //list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment), count(distinct ovc.ovcId)"+util.getHouseholdQueryPart()+"Ovc ovc,FollowupSurvey fs where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=fs.ovcId and (ovc.schoolStatus='Yes' and fs.updatedSchoolStatus='No') and hhe.stateCode=:state "+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment)").setString("state", stateCode).list();
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
    public List getNumberOfOvcVulnerableToHIVAtEnrollmentPerMonthByCBO(String stateCode,String startDate,String endDate) throws Exception
    {
        List list=null;
        List mainList=new ArrayList();
        String additionalQuery=util.getEnrollmentDateQuery(startDate, endDate);
        IndicatorDictionary ind =new IndicatorDictionary();
        mainList.add(ind.getIndicatorForNumberOfOvcVulnerableToHIV_AIDS().getIndicatorName());
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge, count(distinct ovc.ovcId)"+util.getHouseholdQueryPart()+"HouseholdVulnerabilityAssessment hva,Ovc ovc where hhe.hhUniqueId=hva.hhUniqueId and hhe.hhUniqueId=ovc.hhUniqueId and hhe.stateCode=:state and hva.health=4 "+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge").setString("state", stateCode).list();
            //list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment), count(distinct ovc.ovcId)"+util.getHouseholdQueryPart()+"HouseholdVulnerabilityAssessment hva,Ovc ovc where hhe.hhUniqueId=hva.hhUniqueId and hhe.hhUniqueId=ovc.hhUniqueId and hhe.stateCode=:state and hva.health=4 "+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment)").setString("state", stateCode).list();
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
    public List getNumberOfOvcInChildHeadedHouseholdsPerMonthByCBO(String stateCode,String startDate,String endDate) throws Exception
    {
        List list=null;
        List mainList=new ArrayList();
        IndicatorDictionary ind =new IndicatorDictionary();
        String additionalQuery=util.getEnrollmentDateQuery(startDate, endDate);
        mainList.add(ind.getIndicatorForNumberOfOvcInChildHeadedHouseholds().getIndicatorName());
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge, count(distinct ovc.ovcId)"+util.getHouseholdQueryPart()+"HouseholdVulnerabilityAssessment hva,Ovc ovc where hhe.hhUniqueId=hva.hhUniqueId and hhe.hhUniqueId=ovc.hhUniqueId and hhe.stateCode=:state and hva.hhHeadship=4 "+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge").setString("state", stateCode).list();
            //list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment), count(distinct ovc.ovcId)"+util.getHouseholdQueryPart()+"HouseholdVulnerabilityAssessment hva,Ovc ovc where hhe.hhUniqueId=hva.hhUniqueId and hhe.hhUniqueId=ovc.hhUniqueId and hhe.stateCode=:state and hva.hhHeadship=4 "+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment)").setString("state", stateCode).list();
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
    public List getNumberOfOvcNewlyEnrolledPerMonthByCBO(String indicatorName,String stateCode,String startDate,String endDate) throws Exception
    {
        List list=null;
        List mainList=new ArrayList();
        String additionalQuery=util.getEnrollmentDateQuery(startDate, endDate);
        mainList.add(indicatorName);
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            //System.err.println("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge, count(distinct ovc.ovcId)"+util.getHouseholdOvcQueryPart()+" and hhe.stateCode=:state"+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge");
            list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge, count(distinct ovc.ovcId)"+util.getHouseholdOvcQueryPart()+" and hhe.stateCode=:state"+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge").setString("state", stateCode).list();
            //list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment), count(distinct ovc.ovcId)"+util.getHouseholdOvcQueryPart()+" and hhe.stateCode=:state"+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment)").setString("state", stateCode).list();
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
    public List getNumberOfOvcEnrolledPerMonthByCBO(String indicatorName,String stateCode, boolean currentlyEnrolled) throws Exception
    {
        List list=null;
        List mainList=new ArrayList();
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
        //String additionalQuery=util.getEnrollmentDateQuery(startDate, endDate);
        mainList.add(indicatorName);
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge, count(distinct ovc.ovcId)"+util.getHouseholdOvcQueryPart()+currentlyEnrolledQuery+" and hhe.stateCode=:state group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge").setString("state", stateCode).list();
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
    public List getNumberOfHivPositiveOvcEnrolledPerMonthByCBO(String indicatorName,String stateCode,String startDate,String endDate) throws Exception
    {
        List list=null;
        List mainList=new ArrayList();
        String additionalQuery=util.getEnrollmentDateQuery(startDate, endDate);
        mainList.add(indicatorName);//mainList.add("Number of HIV positive OVC enrolled");
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge, count(distinct ovc.ovcId)"+util.getHouseholdOvcQueryPart()+" and hhe.stateCode=:state and UPPER(ovc.hivStatus) like '%POSITIVE%' "+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge").setString("state", stateCode).list();
            //list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment), count(distinct ovc.ovcId)"+util.getHouseholdOvcQueryPart()+" and hhe.stateCode=:state and UPPER(ovc.hivStatus) like '%POSITIVE%' "+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment)").setString("state", stateCode).list();
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
    public int getNumberOfOvcCurrentlyEnrolled(String additionalQuery) throws Exception
    {
        int count=0;
        List list=null;
        
        try
        {
            String query="select count(distinct ovc.ovcId)"+util.getHouseholdOvcQueryPart()+additionalQuery+" and ovc.withdrawnFromProgram='No'";
            System.err.println("query in getNumberOfOvcCurrentlyEnrolled is "+query);
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
        catch (Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        return count;
    }
    public List getListOfOvcCurrentlyEnrolled(String additionalQuery) throws Exception
    {
        List list=null;
        List ovcList=new ArrayList();
        try
        {
            String query=util.getHouseholdOvcQueryPart()+additionalQuery+" and ovc.withdrawnFromProgram='No'";
            System.err.println("getListOfOvcCurrentlyEnrolled is "+query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            
            list = session.createQuery(query).list();
            tx.commit();
            session.close();
            if(list !=null && !list.isEmpty())
            {
                Ovc ovc=null;
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    ovc=(Ovc)objArray[1];
                    ovcList.add(ovc);
                }
            }
        }
        catch (Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
        return ovcList;
    }
    public int getNumberOfOvcCurrentlyEnrolledByHivStatus(String additionalQuery,String hivStatus) throws Exception
    {
        int count=0;
        List list=null;
        
        try
        {
            String query="select count(distinct ovc.ovcId)"+util.getHouseholdOvcHIVQueryPart()+additionalQuery+" and ovc.withdrawnFromProgram='No' "+util.getActiveHivStatusCriteria(hivStatus,NomisConstant.OVC_TYPE);
            //System.err.println("query in getNumberOfOvcCurrentlyEnrolled is "+query);
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
        catch (Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        return count;
    }
    public List getNumberOfHivPositiveOvcCurrentlyEnrolledPerMonthByCBO(String indicatorName,String stateCode,String startDate,String endDate,boolean currentlyEnrolled) throws Exception
    {
        List list=null;
        List mainList=new ArrayList();
        String additionalQuery=util.getEnrollmentDateQuery(startDate, endDate);
        String currentlyEnrolledQuery=" ";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
        mainList.add(indicatorName);//("Number of HIV positive OVC Currently enrolled");
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            //list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment), count(distinct ovc.ovcId)"+util.getHouseholdOvcHIVQueryPart()+" and hhe.stateCode=:state and ovc.withdrawnFromProgram='No' and "+util.getActiveHivStatusCriteria("positive")+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment)").setString("state", stateCode).list();
            list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge, count(distinct ovc.ovcId)"+util.getHouseholdOvcHIVQueryPart()+" and hhe.stateCode=:state"+currentlyEnrolledQuery+util.getActiveHivStatusCriteria(NomisConstant.HIV_POSITIVE,NomisConstant.OVC_TYPE)+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge").setString("state", stateCode).list();
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
    public List getNumberOfHivNegativeOvcCurrentlyEnrolledPerMonthByCBO(String indicatorName,String stateCode,String startDate,String endDate,boolean currentlyEnrolled) throws Exception
    {
        List list=null;
        List mainList=new ArrayList();
        String additionalQuery=util.getEnrollmentDateQuery(startDate, endDate);
        String currentlyEnrolledQuery=" ";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
        mainList.add(indicatorName);//mainList.add("Number of HIV Negative OVC Currently enrolled");
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            //list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment), count(distinct ovc.ovcId)"+util.getHouseholdOvcHIVQueryPart()+" and hhe.stateCode=:state and ovc.withdrawnFromProgram='No' and "+util.getActiveHivStatusCriteria("negative")+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment)").setString("state", stateCode).list();
            list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge, count(distinct ovc.ovcId)"+util.getHouseholdOvcHIVQueryPart()+" and hhe.stateCode=:state "+currentlyEnrolledQuery+" and "+util.getActiveHivStatusCriteria(NomisConstant.HIV_NEGATIVE,NomisConstant.OVC_TYPE)+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge").setString("state", stateCode).list();
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
    public List getNumberOfHivUnknownOvcCurrentlyEnrolledPerMonthByCBO(String indicatorName,String stateCode,String startDate,String endDate,boolean currentlyEnrolled) throws Exception
    {
        List list=null;
        List mainList=new ArrayList();
        String additionalQuery=util.getEnrollmentDateQuery(startDate, endDate);
        String currentlyEnrolledQuery=" ";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
        mainList.add(indicatorName);//mainList.add("Number of HIV unknown OVC Currently enrolled");
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            //list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment), count(distinct ovc.ovcId)"+util.getHouseholdOvcHIVQueryPart()+" and hhe.stateCode=:state and ovc.withdrawnFromProgram='No' and "+util.getActiveHivStatusCriteria("negative")+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment)").setString("state", stateCode).list();
            list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge, count(distinct ovc.ovcId)"+util.getHouseholdOvcHIVQueryPart()+" and hhe.stateCode=:state"+currentlyEnrolledQuery+util.getActiveHivStatusCriteria(NomisConstant.HIV_UNKNOWN,NomisConstant.OVC_TYPE)+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge").setString("state", stateCode).list();
            tx.commit();
            session.close();
        }
        catch (Exception ex)
        {
            session.close();
            System.err.println(ex.getMessage());
            
        }
        mainList.addAll(list);
        return mainList;
    }
    public List getNoOfOvcWithdrawnFromProgramWithinReportPeriodPerMonthByCBO(String indicatorName,String stateCode,String startDate,String endDate,String reasonWithdrawnFromProgram) throws Exception
    {
        ReportUtility rutil=new ReportUtility();
        List list=null;
        List mainList=new ArrayList();
        String additionalQuery=util.getDateQueryForWithdrawalWithinReportPeriod(startDate, endDate);
        mainList.add(indicatorName);
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge, count(distinct ovc.ovcId) "+util.getHouseholdOvcWithdrawalReportQueryPart()+" and hhe.stateCode=:state"+additionalQuery+" and "+rutil.getOvcWithdrawnQuery(reasonWithdrawnFromProgram)+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge").setString("state", stateCode).list();
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
    public List getNumberOfHIVPositiveOvcIdentifiedWithinTheReportingPeriodPerCBO(String indicatorName,String stateCode,String startDate,String endDate) throws Exception
    {
        List list=null;
        List mainList=new ArrayList();
        String additionalQuery=util.getHIVStatusDateQuery(startDate, endDate);
        mainList.add(indicatorName);
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();//and ovc.withdrawnFromProgram='No'
            //String query="select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(hsu.dateOfCurrentStatus),YEAR(hsu.dateOfCurrentStatus), count(distinct ovc.ovcId)"+util.getHouseholdOvcHIVQueryPart()+" and hhe.stateCode=:state and (hsu.hivStatus='positive' and hsu.recordStatus='active')"+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(hsu.dateOfCurrentStatus),YEAR(hsu.dateOfCurrentStatus)";
            String query="select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge, count(distinct ovc.ovcId)"+util.getHouseholdOvcHIVQueryPart()+" and hhe.stateCode=:state and (hsu.hivStatus='positive' and hsu.recordStatus='active')"+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge";
            System.err.println(query);
            list = session.createQuery(query).setString("state", stateCode).list();
            tx.commit();
            session.close();
        }
        catch (Exception ex)
        {
            session.close();
            System.err.println(ex.getMessage());
            
        }
        mainList.addAll(list);
        return mainList;
    }
    public List getNumberOfHIVNegativeOvcIdentifiedWithinTheReportingPeriodPerCBO(String indicatorName,String stateCode,String startDate,String endDate) throws Exception
    {
        List list=null;
        List mainList=new ArrayList();
        String additionalQuery=util.getHIVStatusDateQuery(startDate, endDate);
        mainList.add(indicatorName);
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();//and ovc.withdrawnFromProgram='No'
            //String query="select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(hsu.dateOfCurrentStatus),YEAR(hsu.dateOfCurrentStatus), count(distinct ovc.ovcId)"+util.getHouseholdOvcHIVQueryPart()+" and hhe.stateCode=:state and (hsu.hivStatus='negative' and hsu.recordStatus='active')"+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(hsu.dateOfCurrentStatus),YEAR(hsu.dateOfCurrentStatus)";
            String query="select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge, count(distinct ovc.ovcId)"+util.getHouseholdOvcHIVQueryPart()+" and hhe.stateCode=:state and (hsu.hivStatus='negative' and hsu.recordStatus='active')"+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge";
            System.err.println(query);
            list = session.createQuery(query).setString("state", stateCode).list();
            tx.commit();
            session.close();
        }
        catch (Exception ex)
        {
            session.close();
            System.err.println(ex.getMessage());
            
        }
        mainList.addAll(list);
        return mainList;
    }
    public List getNumberOfHIVUnknownOvcIdentifiedWithinTheReportingPeriodPerCBO(String indicatorName,String stateCode,String startDate,String endDate) throws Exception
    {
        List list=null;
        List mainList=new ArrayList();
        String additionalQuery=util.getHIVStatusDateQuery(startDate, endDate);
        mainList.add(indicatorName);
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();//and ovc.withdrawnFromProgram='No'
            //String query="select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(hsu.dateOfCurrentStatus),YEAR(hsu.dateOfCurrentStatus), count(distinct ovc.ovcId)"+util.getHouseholdOvcHIVQueryPart()+" and hhe.stateCode=:state and (hsu.hivStatus='unknown' and hsu.recordStatus='active')"+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(hsu.dateOfCurrentStatus),YEAR(hsu.dateOfCurrentStatus)";
            String query="select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge, count(distinct ovc.ovcId)"+util.getHouseholdOvcHIVQueryPart()+" and hhe.stateCode=:state and (hsu.hivStatus='unknown' and hsu.recordStatus='active')"+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge";
            System.err.println(query);
            list = session.createQuery(query).setString("state", stateCode).list();
            tx.commit();
            session.close();
        }
        catch (Exception ex)
        {
            session.close();
            System.err.println(ex.getMessage());
            
        }
        mainList.addAll(list);
        return mainList;
    }
    public List getNumberOfHivUnknownOvcCurrentlyEnrolledAndProvidedHTCPerMonthByCBO(String indicatorName,String stateCode,String startDate,String endDate) throws Exception
    {
        List list=null;
        List mainList=new ArrayList();
        ReportUtility rutil=new ReportUtility();
        String additionalQuery=util.getEnrollmentDateQuery(startDate, endDate);
        mainList.add(indicatorName);
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            //String sql="select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment), count(distinct ovc.ovcId)"+util.getHouseholdHIVOvcServiceReportQueryPart("OvcService")+" and hhe.stateCode=:state and ovc.withdrawnFromProgram='No' and (hsu.hivStatus='unknown' and hsu.recordStatus='active')"+additionalQuery+" and "+rutil.getHIVServicesReportQuery()+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment)";
            String sql="select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge, count(distinct ovc.ovcId)"+util.getHouseholdHIVOvcServiceReportQueryPart("OvcService")+" and hhe.stateCode=:state and ovc.withdrawnFromProgram='No' and (hsu.hivStatus='unknown' and hsu.recordStatus='active')"+additionalQuery+" and "+rutil.getHIVServicesReportQuery()+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge";
            //System.err.println(sql);
            list = session.createQuery(sql).setString("state", stateCode).list();
            tx.commit();
            session.close();
        }
        catch (Exception ex)
        {
            session.close();
            System.err.println(ex.getMessage());
            
        }
        mainList.addAll(list);
        return mainList;
    }
    public List getNumberOfHivNegativeOvcEnrolledPerMonthByCBO(String stateCode,String startDate,String endDate) throws Exception
    {
        List list=null;
        List mainList=new ArrayList();
        String additionalQuery=util.getEnrollmentDateQuery(startDate, endDate);
        mainList.add("Number of HIV negative OVC enrolled");
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            //list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment), count(distinct ovc.ovcId)"+util.getHouseholdOvcQueryPart()+" and hhe.stateCode=:state and UPPER(ovc.hivStatus) like '%NEGATIVE%' "+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment)").setString("state", stateCode).list();
            list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge, count(distinct ovc.ovcId)"+util.getHouseholdOvcQueryPart()+" and hhe.stateCode=:state and UPPER(ovc.hivStatus) like '%NEGATIVE%' "+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge").setString("state", stateCode).list();
            //list = session.createQuery("select ovc.state,ovc.lga,ovc.completedbyCbo,ovc.ward,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment), count(distinct ovc.ovcId) from Ovc ovc  where UPPER(ovc.hivStatus) like '%NEGATIVE%' and ovc.state=:state"+additionalQuery+" group by ovc.state,ovc.lga,ovc.completedbyCbo,ovc.ward,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment)").setString("state", stateCode).list();
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
    public List getNumberOfHivUnknownOvcEnrolledPerMonthByCBO(String stateCode,String startDate,String endDate) throws Exception
    {
        List list=null;
        List mainList=new ArrayList();
        String additionalQuery=util.getEnrollmentDateQuery(startDate, endDate);
        mainList.add("Number of HIV unknown OVC enrolled");
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge, count(distinct ovc.ovcId)"+util.getHouseholdOvcQueryPart()+" and hhe.stateCode=:state and UPPER(ovc.hivStatus) like '%UNKNOWN%' "+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge").setString("state", stateCode).list();
            //list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment), count(distinct ovc.ovcId)"+util.getHouseholdOvcQueryPart()+" and hhe.stateCode=:state and UPPER(ovc.hivStatus) like '%UNKNOWN%' "+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment)").setString("state", stateCode).list();
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
    public List getNumberOfOvcInSchoolAtEnrollmentPerMonthByCBO(String indicatorName,String stateCode,String startDate,String endDate) throws Exception
    {
        List list=null;
        List mainList=new ArrayList();
        String additionalQuery=util.getEnrollmentDateQuery(startDate, endDate);
        mainList.add(indicatorName);
        //System.err.println("additionalQuery is "+additionalQuery);
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            //list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment), count(distinct ovc.ovcId) "+util.getHouseholdOvcQueryPart()+" and hhe.stateCode=:state and ovc.schoolStatus='Yes'"+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment)").setString("state", stateCode).list();
            list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge, count(distinct ovc.ovcId) "+util.getHouseholdOvcQueryPart()+" and hhe.stateCode=:state and ovc.schoolStatus='Yes'"+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge").setString("state", stateCode).list();
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
    public int getNumberOfActiveOvcThatWereInSchoolAtEnrollment(String additionalQuery) throws Exception
    {
        List list=null;
        int count=0;
        try
        {
            System.err.println("select count(distinct ovc.ovcId) "+util.getHouseholdOvcQueryPart()+" and ovc.schoolStatus='Yes' and ovc.withdrawnFromProgram='No'"+additionalQuery);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select count(distinct ovc.ovcId) "+util.getHouseholdOvcQueryPart()+" and ovc.schoolStatus='Yes' and ovc.withdrawnFromProgram='No'"+additionalQuery).list();
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
    public int getNumberOfActiveOvcThatAreCurrentlyOutOfSchool(String additionalQuery) throws Exception
    {
        List list=null;
        int count=0;
        try
        {
            System.err.println("select count(distinct ovc.ovcId) "+util.getHouseholdOvcQueryPart()+" and ovc.schoolStatus='No' and ovc.withdrawnFromProgram='No'"+additionalQuery);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select count(distinct ovc.ovcId) "+util.getHouseholdOvcQueryPart()+" and ovc.schoolStatus !='Yes' and ovc.withdrawnFromProgram='No' and ovc.ovcId not in (select fu.ovcId from FollowupSurvey fu where UPPER(fu.updatedSchoolStatus) ='YES')"+additionalQuery).list();
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
    public List getListOfActiveOvcOutOfSchool(String additionalQuery) throws Exception
    {
        List list=null;
        try
        {
            System.err.println(util.getHouseholdOvcQueryPart()+" and ovc.schoolStatus='Yes' and ovc.withdrawnFromProgram='No'"+additionalQuery);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(util.getHouseholdOvcQueryPart()+" and ovc.schoolStatus !='Yes' and ovc.withdrawnFromProgram='No' and ovc.ovcId not in (select fs.ovcId from FollowupSurvey fs where UPPER(fs.updatedSchoolStatus) ='YES')"+additionalQuery).list();
            tx.commit();
            session.close();
        }
        catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
        return list;
    }
    public List getListOfActiveOvcThatWereInSchoolAtEnrollment(String additionalQuery) throws Exception
    {
        List list=null;
        try
        {
            System.err.println(util.getHouseholdOvcQueryPart()+" and ovc.schoolStatus='Yes' and ovc.withdrawnFromProgram='No'"+additionalQuery);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(util.getHouseholdOvcQueryPart()+" and ovc.schoolStatus='Yes' and ovc.withdrawnFromProgram='No'"+additionalQuery).list();
            tx.commit();
            session.close();
        }
        catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
        return list;
    }
    public List getNumberOfOvcCurrentlyEnrolledAndInSchoolAtEnrollmentPerMonthByCBO(String indicatorName,String stateCode,String startDate,String endDate) throws Exception
    {
        List list=null;
        List mainList=new ArrayList();
        String additionalQuery=util.getEnrollmentDateQuery(startDate, endDate);
        mainList.add(indicatorName);
        //System.err.println("additionalQuery is "+additionalQuery);
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender, count(distinct ovc.ovcId) "+util.getHouseholdOvcQueryPart()+" and hhe.stateCode=:state and ovc.schoolStatus='Yes' and ovc.withdrawnFromProgram='No'"+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge").setString("state", stateCode).list();
            //list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment), count(distinct ovc.ovcId) "+util.getHouseholdOvcQueryPart()+" and hhe.stateCode=:state and ovc.schoolStatus='Yes' and ovc.withdrawnFromProgram='No'"+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment)").setString("state", stateCode).list();
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
    public List getNumberOfOvcOutOfSchoolAtEnrollmentPerMonthByCBO(String stateCode,String startDate,String endDate) throws Exception
    {
        List list=null;
        List mainList=new ArrayList();
        String additionalQuery=util.getEnrollmentDateQuery(startDate, endDate);
        mainList.add("Number of Ovc out of school at enrollment ");
        System.err.println("additionalQuery is "+additionalQuery);
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge, count(distinct ovc.ovcId) "+util.getHouseholdOvcQueryPart()+" and hhe.stateCode=:state and (ovc.schoolStatus='No' or ovc.schoolStatus is null or ovc.schoolStatus='' or ovc.schoolStatus=' ' or ovc.schoolStatus='  ' or ovc.schoolStatus='   ')"+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge").setString("state", stateCode).list();
            //list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment), count(distinct ovc.ovcId) "+util.getHouseholdOvcQueryPart()+" and hhe.stateCode=:state and (ovc.schoolStatus='No' or ovc.schoolStatus is null or ovc.schoolStatus='' or ovc.schoolStatus=' ' or ovc.schoolStatus='  ' or ovc.schoolStatus='   ')"+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment)").setString("state", stateCode).list();
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
    public List getCurrentAgeFromEnrollment() throws Exception
    {
        List list = new ArrayList();

        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct ovc.currentAge from Ovc ovc order by ovc.currentAge").list();
            tx.commit();
            session.close();
        }
        catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
        return list;
    }
    public List getNumberOfOvcWithBirthCertificateAtBaseline(String stateCode,String startDate,String endDate) throws Exception
    {
        List list = new ArrayList();
        List mainList=new ArrayList();
        String additionalQuery=util.getEnrollmentDateQuery(startDate, endDate);
        mainList.add("Number of Ovc with birth registration at enrollment");
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge, count(distinct ovc.ovcId) "+util.getHouseholdOvcQueryPart()+" and hhe.stateCode=:state and ovc.birthCertificate='Yes' "+additionalQuery+" group by hhe.stateCode,hhe.lgaCode, hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge ").setString("state", stateCode).list();
            //list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment), count(distinct ovc.ovcId) "+util.getHouseholdOvcQueryPart()+" and hhe.stateCode=:state and ovc.birthCertificate='Yes' "+additionalQuery+" group by hhe.stateCode,hhe.lgaCode, hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment) ").setString("state", stateCode).list();
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
    public List getListOfOvcWithBirthCertificateAtBaseline() throws Exception
    {
        List list = new ArrayList();
        List mainList=new ArrayList();
        
        
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from HouseholdEnrollment hhe, Ovc ovc,BirthRegistration br where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=br.clientId and ovc.birthCertificate='Yes' and br.birthRegistrationStatus='No' and br.recordStatus='active'").list();
            //list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment), count(distinct ovc.ovcId) "+util.getHouseholdOvcQueryPart()+" and hhe.stateCode=:state and ovc.birthCertificate='Yes' "+additionalQuery+" group by hhe.stateCode,hhe.lgaCode, hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment) ").setString("state", stateCode).list();
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
    public List getOfOvcWithKnownHIVStatusAtBaseline(String stateCode,String startDate,String endDate) throws Exception
    {
        List list = new ArrayList();
        List mainList=new ArrayList();
        String additionalQuery=util.getEnrollmentDateQuery(startDate, endDate);
        mainList.add("Number of Ovc with known HIV status at enrollment");
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,count(distinct ovc.ovcId)"+util.getHouseholdOvcQueryPart()+" and hhe.stateCode=:state and (ovc.hivStatus not like '%unknown%' and ovc.hivStatus not like '%Unknown%') "+additionalQuery+" group by hhe.stateCode,hhe.lgaCode, hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge").setString("state", stateCode).list();
            //list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment),count(distinct ovc.ovcId)"+util.getHouseholdOvcQueryPart()+" and hhe.stateCode=:state and (ovc.hivStatus not like '%unknown%' and ovc.hivStatus not like '%Unknown%') "+additionalQuery+" group by hhe.stateCode,hhe.lgaCode, hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment)").setString("state", stateCode).list();
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
    public List getNumberOfHivPositiveOvcAtBaseline(String stateCode,String startDate,String endDate) throws Exception
    {
        List list = new ArrayList();
        List mainList=new ArrayList();
        String additionalQuery=util.getEnrollmentDateQuery(startDate, endDate);
        mainList.add("Number of HIV positive OVC identified at enrollment");
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,count(distinct ovc.ovcId) "+util.getHouseholdOvcQueryPart()+" and hhe.stateCode=:state and (ovc.hivStatus like '%positive%') "+additionalQuery+" group by hhe.stateCode,hhe.lgaCode, hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge").setString("state", stateCode).list();
            //list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment),count(distinct ovc.ovcId) "+util.getHouseholdOvcQueryPart()+" and hhe.stateCode=:state and (ovc.hivStatus like '%positive%') "+additionalQuery+" group by hhe.stateCode,hhe.lgaCode, hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment)").setString("state", stateCode).list();
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
    public int getNumberOfOvcOutOfSchoolPerCBO(String stateCode,String lgaCode,String cboCode) throws Exception
    {
        List list=null;
        int noOfOvc=0;

        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select count(distinct ovc.ovcId) "+util.getHouseholdOvcQueryPart()+" and hhe.stateCode=:state and hhe.lgaCode=:lga and hhe.orgCode=:cbo  and ovc.schoolStatus='No'").setString("state", stateCode).setString("lga", lgaCode).setString("cbo", cboCode).list();
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
            noOfOvc=(Integer.parseInt(list.get(0).toString()));
        }
        return noOfOvc;
    }
    public int getNumberOfOvcOutOfSchoolByAgeGroupAndPerCBO(String stateCode,String lgaCode,String cboCode,String logic,int currentAge) throws Exception
    {
        List list=null;
        int noOfOvc=0;

        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select count(distinct ovc.ovcId) "+util.getHouseholdOvcQueryPart()+" and hhe.stateCode=:state and hhe.lgaCode=:lga and hhe.orgCode=:cbo  and ovc.schoolStatus='No' and ovc.currentAge"+logic+currentAge+"").setString("state", stateCode).setString("lga", lgaCode).setString("cbo", cboCode).list();
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
            noOfOvc=(Integer.parseInt(list.get(0).toString()));
        }
        return noOfOvc;
    }
    public int getNumberOfOvcEnrolledPerCBO(String stateCode,String lgaCode,String cboCode) throws Exception
    {
        List list=null;
        int noOfOvc=0;

        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select count(distinct ovc.ovcId) "+util.getHouseholdOvcQueryPart()+" and hhe.stateCode=:state and hhe.lgaCode=:lga and hhe.orgCode=:cbo").setString("state", stateCode).setString("lga", lgaCode).setString("cbo", cboCode).list();
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
            noOfOvc=(Integer.parseInt(list.get(0).toString()));
        }
        return noOfOvc;
    }
    public int getNumberOfOvcEnrolledByOrgUnit(String orgUnitCode) throws Exception
    {
        int numberOfOvc=0;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            //System.err.println("select count(distinct ovc.ovcId)"+util.getHouseholdQueryPart()+"Ovc ovc where hhe.hhUniqueId=ovc.hhUniqueId and (hhe.stateCode='"+orgUnitCode+"' or hhe.lgaCode='"+orgUnitCode+"' or hhe.orgCode='"+orgUnitCode+"' or hhe.communityCode='"+orgUnitCode+"')");
            list = session.createQuery("select count(distinct ovc.ovcId)"+util.getHouseholdQueryPart()+"Ovc ovc where hhe.hhUniqueId=ovc.hhUniqueId and (hhe.stateCode='"+orgUnitCode+"' or hhe.lgaCode='"+orgUnitCode+"' or hhe.orgCode='"+orgUnitCode+"' or hhe.communityCode='"+orgUnitCode+"')").list();
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
           Long lvalue=(Long)list.get(0);
           numberOfOvc=lvalue.intValue();
        }
        return numberOfOvc;
    }
    public List getNumberOfOvcEnrolledPerMonthByCBO() throws Exception
    {
        List list=null;
        List mainList=new ArrayList();
        mainList.add("Number of Ovc enrolled");
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            //list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment), count(distinct ovc.ovcId) "+util.getHouseholdQueryPart()+"Ovc ovc where (hhe.hhUniqueId=ovc.hhUniqueId) group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment)").list();
            list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge, count(distinct ovc.ovcId) "+util.getHouseholdQueryPart()+"Ovc ovc where (hhe.hhUniqueId=ovc.hhUniqueId) group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge").list();
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
    public void normalizeBaselineAssessment() throws Exception
    {
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        ChildStatusIndexDao csidao=new ChildStatusIndexDaoImpl();

        List orgList=hhedao.getDistinctOrgCodes();
        if(orgList !=null && !orgList.isEmpty())
        {
            String orgCode=null;
            List ovcList=null;
            Ovc ovc=null;
            ChildStatusIndex csi=null;
            int count=0;
            for(int i=0; i<orgList.size(); i++)
            {
                orgCode=(String)orgList.get(i);
                ovcList=getOvcList(" and hhe.orgCode='"+orgCode+"'");
                if(ovcList !=null)
                {
                    for(int j=0; j<ovcList.size(); j++)
                    {
                        ovc=(Ovc)ovcList.get(j);
                        if(ovc.getOvcId().indexOf("/")==-1 && ovc.getOvcId().indexOf("0")==-1)
                        continue;
                        csi=csidao.getChildStatusIndex(ovc.getOvcId(), ovc.getDateEnrollment());
                        if(csi==null)
                        {
                            csi=new ChildStatusIndex();
                            csi.setCsiDate(ovc.getDateEnrollment());
                            csi.setOvcId(ovc.getOvcId());
                            csi.setDateOfEntry(appUtil.getCurrentDate());
                            csi.setSurveyNumber(1);
                            csidao.saveChildStatusIndex(csi);
                            count++;
                            System.err.println("csi with id "+csi.getOvcId()+" saved "+count);
                        }
                    }
                }
            }
        }

    }
    public List getDistinctStateCodesFromEnrollment() throws Exception
    {
        List list = new ArrayList();

        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct hhe.stateCode from Ovc ovc").list();
            tx.commit();
            session.close();
        }
        catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
        return list;
    }
    public List getDistinctLgaCodesFromEnrollment() throws Exception
    {
        List list = new ArrayList();

        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct hhe.lgaCode from Ovc ovc").list();
            tx.commit();
            session.close();
        }
        catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
        return list;
    }
    public String updateOvcCurrentAge(List stateList) throws Exception
    {
        String msg=" ";
        List list=new ArrayList();
        try
        {
            if(stateList !=null)
            {
                String stateCode=null;
                for(int i=0; i<stateList.size(); i++)
                {
                    stateCode=(String)stateList.get(i);
                list=getOvcs(stateCode);
                Ovc ovc=null;
                if(list !=null && !list.isEmpty())
                {
                    int size=list.size();
                    int count=0;
                    for(Object obj:list)
                    {
                        Object[] objArr=(Object[])obj;
                        ovc=(Ovc)objArr[1];
                        ovc.setCurrentAge(util.getCurrentAge(ovc));
                        updateOvc(ovc,false,false);
                        count++;
                        System.err.println("Ovc current age for record  "+count+" of "+size+" updated");
                    }
                    msg="OVC age updated successfully";
                }
                else
                {
                    msg="Could not update ovc current age, No record found";
                }
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return msg;
    }
    public String updateOvcCurrentAge(List stateList,int startAge,int endAge) throws Exception
    {
        String msg=" ";
        List list=new ArrayList();
        try
        {
            if(stateList !=null)
            {
                String stateCode=null;
                for(int i=0; i<stateList.size(); i++)
                {
                    stateCode=(String)stateList.get(i);
                    list=getOvcs(stateCode,startAge, endAge);
                    Ovc ovc=null;
                    if(list !=null && !list.isEmpty())
                    {
                        int size=list.size();
                        int count=0;
                        for(Object obj:list)
                        {
                            Object[] objArr=(Object[])obj;
                            ovc=(Ovc)objArr[1];
                            ovc.setCurrentAge(util.getCurrentAge(ovc));
                            updateOvc(ovc,false,false);
                            count++;
                            System.err.println("Ovc current age for record  "+count+" of "+size+" updated in updateOvcCurrentAge(List stateList,int startAge,int endAge) throws Exception");
                        }
                        msg="OVC age updated successfully in updateOvcCurrentAge(List stateList,int startAge,int endAge) throws Exception";
                    }
                    else
                    {
                        msg="Could not update ovc current age, No record found";
                    }
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return msg;
    }
    public List getUniqueOvcId() throws Exception
     {
         List list = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("select distinct ovc.ovcId from Ovc as ovc").list();
        tx.commit();
        session.close();
        } catch (HibernateException he)
        {
            session.close();
            //throw new Exception(he);
        }
        catch(Exception ex)
        {
            session.close();
        }
         return list;
     }
    public List getOvcWithNoCaregiverId() throws Exception
    {
        List list = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from Ovc ovc where (ovc.caregiverId is null or ovc.caregiverId ='caregiverId' or ovc.caregiverId ='' or ovc.caregiverId=' ' or ovc.caregiverId='  ' or ovc.caregiverId='   ')").list();
            tx.commit();
            session.close();
        }
         catch (HibernateException he)
         {
             session.close();
            throw new Exception(he);
         }
         return list;

    }
    public List getOvcPerHousehold(String hhUniqueId) throws Exception
    {
        List list = new ArrayList();
        List ovcList = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from Ovc ovc where ovc.hhUniqueId=:hhId order by ovc.firstName").setString("hhId", hhUniqueId).list();
            tx.commit();
            session.close();
        }
         catch (HibernateException he)
         {
             session.close();
            throw new Exception(he);
         }
        cleanOvcNames(ovc);
        for(Object s:list)
        {
            Ovc ovc=(Ovc)s;
            ovcList.add(cleanOvcNames(ovc));
        }
         return ovcList;
    }
    public void setHHUniqueId() throws Exception
    {
        int numberOfOvc=getNoOfOvcWithoutHHUniqueId();
        int start=0;
        int end=0;
        int counter=0;
        double count=0;
       
            Ovc ovc=null;
            String ovcId=null;
            String hhUniqueId=null;
            count=Math.ceil(numberOfOvc/5000d);
            System.err.println("count is "+count+" numberOfOvc "+numberOfOvc);
            
                List list=getOvcWithoutHHUniqueId();
                if(list !=null && !list.isEmpty())
                {
                    for(int j=0; j<list.size(); j++)
                    {
                        ovc=(Ovc)list.get(j);
                        ovcId=ovc.getOvcId();
                        if(ovcId.indexOf("/") !=-1)
                        {
                            counter++;
                            hhUniqueId=ovcId.substring(0,ovcId.lastIndexOf("/"));
                            ovc.setHhUniqueId(hhUniqueId);
                            updateOvc(ovc,false,false);
                            //System.err.println("Processing "+counter+" of "+numberOfOvc+" hhUniqueId is "+hhUniqueId+" count is "+count+" start "+start+" end "+end+" i is "+i);
                        }
                    }
                }
    }
    public int getNoOfOvcWithoutHHUniqueId() throws Exception
    {
        List list = new ArrayList();
        List ovcList = new ArrayList();
        int numberOfOvc=0;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createQuery("select count (distinct ovc.ovcId) from Ovc ovc where ovc.hhUniqueId is null").list();
            
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
            numberOfOvc=((Long)list.get(0)).intValue();
        }
        return numberOfOvc;
    }
    public List getOvcWithoutHHUniqueId() throws Exception
    {
        List list = new ArrayList();
        List ovcList = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            Query query=session.createQuery("from Ovc ovc where ovc.hhUniqueId is null");
            //query.setFirstResult(start);
            //query.setMaxResults(end);
            list = query.list();
            tx.commit();
            session.close();
        }
         catch (HibernateException he)
         {
             session.close();
            throw new Exception(he);
         }
        //cleanOvcNames(ovc);
        for(Object s:list)
        {
            Ovc ovc=(Ovc)s;
            ovcList.add(cleanOvcNames(ovc));
        }
         return ovcList;
    }
    private Ovc cleanOvcNames(Ovc ovc)
    {
        if(ovc !=null)
        {
            String firstName=ovc.getFirstName();
            if(firstName !=null && firstName.indexOf(",") !=-1)
            firstName=firstName.replace(",", " ");
            ovc.setFirstName(firstName);
            String lastName=ovc.getLastName();
            if(lastName !=null && lastName.indexOf(",") !=-1)
            lastName=lastName.replace(",", " ");
            ovc.setLastName(lastName);
        }
        return ovc;
    }
    /*public void changeOvcId(String oldId,String newId) throws Exception
    {
        List list = new ArrayList();
        if(newId==null|| newId.indexOf("/")==-1)
        return;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from Ovc ovc where ovc.ovcId=:id").setString("id", oldId).list();
            tx.commit();
            session.close();
        
            if(list !=null && !list.isEmpty())
            {
                Ovc ovc=(Ovc)list.get(0);
                
                if(!oldId.equalsIgnoreCase(newId))
                newId=generateNewUniqueId(newId);
                System.err.println("Old OVC Id is "+oldId);
                System.err.println("New OVC id is "+newId);
                
                ovc.setVcEnrollmentId(newId);
                
                ovc.setDateOfEntry(appUtil.getCurrentDate());
                if(getOvc(ovc.getOvcId())!=null)
                updateOvc(ovc,false,false);
                
                
            }
        }
        catch (Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
      
    }*/
    public void changeOvcId(String oldId,String newId) throws Exception
    {
        List list = new ArrayList();
        if(newId==null|| newId.indexOf("/")==-1)
        return;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from Ovc ovc where ovc.ovcId=:id").setString("id", oldId).list();
            tx.commit();
            session.close();
        
            if(list !=null && !list.isEmpty())
            {
                Ovc ovc=(Ovc)list.get(0);
                //Ovc duplicateOvc=null;
                ChildStatusIndexDao csidao=new ChildStatusIndexDaoImpl();
                OvcServiceDao serviceDao=new OvcServiceDaoImpl();
                OvcReferralDao refDao=new OvcReferralDaoImpl();
                FollowupDao fuDao=new FollowupDaoImpl();
                NutritionAssessmentDao nadao=new NutritionAssessmentDaoImpl();
                OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl();
                HivStatusUpdateDao hsudao=new HivStatusUpdateDaoImpl();
                HivRiskAssessmentChecklistDao hracdao=new HivRiskAssessmentChecklistDaoImpl();
                ChildSchoolStatusDao cssdao=new ChildSchoolStatusDaoImpl();
                if(!oldId.equalsIgnoreCase(newId))
                newId=generateNewUniqueId(newId);
                System.err.println("Old OVC Id is "+oldId);
                System.err.println("New OVC id is "+newId);
                String oldHhUniqueId=oldId.substring(0,oldId.lastIndexOf("/"));
                String newHhUniqueId=newId.substring(0,newId.lastIndexOf("/"));
                String caregiverId=ovc.getCaregiverId();
                if(caregiverId !=null)
                {
                    if(caregiverId.indexOf(oldHhUniqueId) !=-1)
                    caregiverId=caregiverId.replace(oldHhUniqueId, newHhUniqueId);
                }
                else
                {
                    CaregiverDao cgiverdao=new CaregiverDaoImpl();
                    List cgiverList=cgiverdao.getListOfCaregiversFromSameHousehold(newHhUniqueId);
                    if(cgiverList !=null && !cgiverList.isEmpty())
                    caregiverId=((Caregiver)cgiverList.get(0)).getCaregiverId();
                }
                ovc.setOvcId(oldId);
                ovc.setNewOvcId(newId);
                deleteOvcFromEnrollmentRecordOnly(ovc);
                
                ovc.setOvcId(newId);
                ovc.setHhUniqueId(newHhUniqueId);
                ovc.setCaregiverId(caregiverId);
                ovc.setDateOfEntry(appUtil.getCurrentDate());
                if(getOvc(ovc.getOvcId())==null)
                saveOvc(ovc,false,false);
                
                //Change other records only if the ovc unique id has changed
                if(!oldId.equalsIgnoreCase(newId))
                {
                    csidao.changeOvcId(oldId, newId);
                    //System.err.println("csi with ovcid "+ovc.getOvcId()+" saved");
                    serviceDao.changeOvcId(oldId, newId);
                    //System.err.println("serviced with ovcid "+ovc.getOvcId()+" saved");
                    refDao.changeOvcId(oldId, newId);
                    //System.err.println("referral with ovcid "+ovc.getOvcId()+" saved");
                    fuDao.changeOvcId(oldId, newId);
                    //System.err.println("followup with ovcid "+ovc.getOvcId()+" saved");
                    nadao.changeOvcId(oldId, newId);
                    //System.err.println("nutritional with newId "+ovc.getOvcId()+" saved");
                    wdao.changeOvcId(oldId, newId);
                    //System.err.println("withdrawal with ovcid "+ovc.getOvcId()+" saved");
                    hsudao.changeClientId(oldId, newId);  
                    //System.err.println("hsu with ovcid "+ovc.getOvcId()+" saved");
                    hracdao.changeOvcId(oldId, newId);
                    //Change the id for OvcSchool attendance records
                    cssdao.changeOvcId(oldId, newId);
                }
            }
        }
        catch (Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
      
    }
    public String generateNewUniqueId(String existingUniqueId) throws Exception
    {
        if(existingUniqueId !=null)
        existingUniqueId=existingUniqueId.trim();
        String newId=existingUniqueId;
        System.err.println("newId is "+newId+"-");
        String paddedSnumber=null;
        try
        {
            Ovc ovc=getOvc(existingUniqueId);
            if(ovc==null)
            return existingUniqueId;
            else
            {
                String hhIdParts=existingUniqueId.substring(0,existingUniqueId.lastIndexOf("/"));
                String serialNumber=existingUniqueId.substring(existingUniqueId.lastIndexOf("/")+1);
                if(serialNumber !=null)
                {
                    serialNumber=serialNumber.trim();
                    int snumber=Integer.parseInt(serialNumber);
                    snumber++;
                    paddedSnumber=snumber+"";
                    paddedSnumber=paddedSnumber.trim();
                    while(paddedSnumber.length() <5)
                    {
                        paddedSnumber=padSnumber(paddedSnumber);
                    }
                    newId=hhIdParts+"/"+paddedSnumber;
                    ovc=getOvc(newId);
                    if(ovc !=null)
                    newId=generateNewUniqueId(newId); 
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return newId;
    }
    public String padSnumber(String snumber)
    {
        if(snumber==null || snumber.length()==5)
        return snumber;
        snumber=snumber.trim();
        if(snumber.length()==1)
        snumber="0000"+snumber;
        else if(snumber.length()==2)
        snumber="000"+snumber;
        else if(snumber.length()==3)
        snumber="00"+snumber;
        else if(snumber.length()==4)
        snumber="0"+snumber;
        return snumber;
    }
    public List getListOfCompatibilityOvc(String cboCode) throws Exception
    {
        List list = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from CompatibilityOvc covc where covc.cboCode =:cbo").setString("cbo", cboCode).list();
            tx.commit();
        session.close();
        }
        catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
        return list;
    }
    public List getDistinctCboCode() throws Exception
    {
        List list = new ArrayList();

        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct hhe.orgCode from HouseholdEnrollment hhe").list();
            tx.commit();
        session.close();
        }
        catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
        return list;
    }
    public List getDistinctCboCodeFromCompatibilityOvc() throws Exception
    {
        List list = new ArrayList();

        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct covc.cboCode from CompatibilityOvc covc").list();
            tx.commit();
        session.close();
        }
        catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
        return list;
    }
    public String updateOvcCurrentAge() throws Exception
    {
        String msg=" ";
        List list=new ArrayList();
        try
        {
                list=getOvcs();
                Ovc ovc=null;
                if(list !=null && !list.isEmpty())
                {
                    for(Object obj:list)
                    {
                        ovc=(Ovc)obj;
                        ovc.setCurrentAge(util.getCurrentAge(ovc));
                        //System.err.println("ovc.geturrentAge is "+ovc.getCurrentAge());
                        updateOvc(ovc,false,false);
                    }
                    msg="OVC age updated successfully";
                }
                else
                {
                    msg="Could not update ovc current age, No record found";
                }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return msg;
    }
    public int getNoOfOvcsPerCaregiver(String additionalQuery,String caregiverUniqueId) throws Exception
    {
        List list = new ArrayList();
        int noOfVC=0;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select count(distinct ovc.ovcId) from Ovc ovc where ovc.caregiverId =:cgiverId ").setString("cgiverId", caregiverUniqueId).list();
            tx.commit();
        session.close();
        }
        catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
        if(!list.isEmpty())
        {
            noOfVC=Integer.parseInt(((Long)list.get(0)).toString());
        }
        return noOfVC;
    }
    public List getListOfOvcsPerCaregiver(String additionalQuery,String caregiverUniqueId) throws Exception
    {
        List list = new ArrayList();
        List ovcList = new ArrayList();
        Ovc ovc=null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            //System.err.println(util.getHouseholdQueryPart()+"Ovc ovc where hhe.hhUniqueId=ovc.hhUniqueId and ovc.caregiverId =:cgiverId "+additionalQuery);
            list = session.createQuery(util.getHouseholdQueryPart()+"Ovc ovc where hhe.hhUniqueId=ovc.hhUniqueId and ovc.caregiverId =:cgiverId "+additionalQuery).setString("cgiverId", caregiverUniqueId).list();
            tx.commit();
        session.close();
        }
        catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
        for(Object s:list)
        {
            Object[] obj=(Object[])s;
            ovc=(Ovc)obj[1];
            ovcList.add(ovc);
        }
        return ovcList;
    }

public List getOVCByPartOfNames(String partOfName) throws Exception
{
    List list=null;
    partOfName=partOfName.toUpperCase();

    try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from Ovc as ovc where (UPPER(ovc.lastName) like '"+partOfName+"%' or UPPER(ovc.firstName) like '"+partOfName+"%') order by ovc.firstName").list();
        tx.commit();
        session.close();
        } catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
    return list;
}
public List getOvcByNameAndAge(String sname,String fname,int age,String ageunit) throws Exception
{
    List list = new ArrayList();
    if(sname !=null)
    sname=sname.trim().toUpperCase();
    if(fname !=null)
    fname=fname.trim().toUpperCase();
    if(ageunit !=null)
    ageunit=ageunit.trim();
    System.err.println("sname is "+sname+"-- fname:"+fname+"-- Age:"+age+"-- ageunit:"+ageunit+"--");
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from Ovc as ovc where UPPER(TRIM(ovc.lastName)) = :ln and (UPPER(TRIM(ovc.firstName)) = :fn or UPPER(TRIM(ovc.firstName)) = '"+fname+",') and ovc.age = :ag and TRIM(ovc.ageUnit) = :au")
            .setString("ln", sname).setString("fn", fname).setInteger("ag", age).setString("au", ageunit).list();
        tx.commit();
        session.close();
        } catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
        return list;
}
public List getOvcByNameAndCaregiver(String sname,String fname,String caregiverId) throws Exception
{
    List list = new ArrayList();
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from Ovc as ovc where UPPER(ovc.lastName) = :ln and UPPER(ovc.firstName) = :fn and ovc.caregiverId = :cgId")
            .setString("ln", sname.toUpperCase()).setString("fn", fname.toUpperCase()).setString("cgId", caregiverId).list();
        tx.commit();
        session.close();
        } catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
        return list;
}
    public Ovc getOvc(String ovcId) throws Exception
    {
        Ovc ovc=null;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createQuery("from Ovc ovc where ovc.ovcId=:id").setString("id", ovcId).list();
        //ovc = (Ovc) session.load(Ovc.class, ovcId, LockMode.UPGRADE);
        tx.commit();
        session.close();
        } 
        catch (Exception ex)
        {
            session.close();
            ex.printStackTrace();
            throw new Exception(ex);
        }
        if(list !=null && !list.isEmpty())
        {
            ovc=(Ovc)list.get(0);
        }
        return ovc;
    }
    public List getOVC(String ovcId) throws Exception
    {
        List list = new ArrayList();
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from Ovc as ovc where ovc.ovcId = :id and ovc.ovcId not in (select withdrawal.ovcId from OvcWithdrawal withdrawal)")
            .setString("id", ovcId).list();
        tx.commit();
        session.close();
        } catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
        return list;
    }
    public void saveOvcHIVStatus(Ovc ovc) throws Exception
    {
        HivStatusUpdateDao hsudao=new HivStatusUpdateDaoImpl();
        HivStatusUpdate hsu=new HivStatusUpdate();
        String dateOfCurrentHivStatus=ovc.getDateOfCurrentHivStatus();
        if(dateOfCurrentHivStatus==null)
        dateOfCurrentHivStatus=ovc.getDateEnrollment();
        hsu.setClientEnrolledInCare(ovc.getEnrolledInCare());
        hsu.setEnrolledOnART(ovc.getEnrolledOnART());
        hsu.setClientId(ovc.getOvcId());
        hsu.setClientType(NomisConstant.OVC_TYPE);
        hsu.setDateModified(DateManager.getCurrentDate());
        hsu.setDateOfCurrentStatus(dateOfCurrentHivStatus);
        hsu.setHivStatus(HivRecordsManager.getHivStatusScode(ovc.getHivStatus()));
        hsu.setRecordStatus("active");
        hsu.setPointOfUpdate(ovc.getPointOfService());
        hsu.setOrganizationClientIsReferred(ovc.getFacilityId());
        hsudao.saveHivStatusUpdate(hsu);
    }
    public void saveOvcBirthRegistrationStatus(Ovc ovc) throws Exception
    {
        BirthRegistrationDao brdao=new BirthRegistrationDaoImpl();
        BirthRegistration br=new BirthRegistration();
        br.setClientId(ovc.getOvcId());
        br.setClientType("ovc");
        br.setDateModified(appUtil.getCurrentDate());
        br.setDateOfRegistration(ovc.getDateEnrollment());
        br.setRecordStatus("active");
        br.setPointOfUpdate(ovc.getPointOfService());
        br.setBirthRegistrationStatus(ovc.getBirthCertificate());
        br.setRecordId(ovc.getBirthRegistration().getRecordId());
        //System.err.println("ovc.getBirthRegistration().getRecordId() in saveOvcBirthRegistrationStatus is "+ovc.getBirthRegistration().getRecordId());
        brdao.saveBirthRegistration(br);
    }
    /*public boolean saveOvcOnly(Ovc ovc) throws Exception 
    {
        boolean saved=false;
       try 
       {
           if(ovc.getOvcId() !=null && ovc.getOvcId().indexOf("/")!=-1)
            {
                AppUtility appUtil=new AppUtility();
                String serialNumber=ovc.getOvcId().substring(ovc.getOvcId().lastIndexOf("/")+1, ovc.getOvcId().length());
                if(appUtil.isNumberGreaterThanZero(serialNumber))
                {
                    session = HibernateUtil.getSession();
                    tx = session.beginTransaction();
                    session.save(ovc);
                    tx.commit();
                    session.close();
                    saved=true;
                }
            } 
       }
        catch (Exception ex)
        {
            session.close();
            ex.printStackTrace();
            //throw new Exception(he);
        }
       return saved;
}*/
/*public boolean updateOvcOnly(Ovc ovc) throws Exception 
{
    boolean updated=false;
    try 
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        session.update(ovc);
        tx.commit();
        session.close();
        updated=true;

    } 
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
        //throw new Exception(he);
    }
    return updated;
}*/
    public void saveOvc(Ovc ovc,boolean saveHiv,boolean saveBirthRegistration) throws Exception 
    {
       try 
        {
            if(ovc.getOvcId() !=null && ovc.getOvcId().indexOf("/")!=-1)
            {
                //System.err.println("ovc.getOvcId() in saveOvc is "+ovc.getOvcId());
                AppUtility appUtil=new AppUtility();
                String serialNumber=ovc.getOvcId().substring(ovc.getOvcId().lastIndexOf("/")+1, ovc.getOvcId().length());
                if(appUtil.isNumberGreaterThanZero(serialNumber))
                {
                    if(ovc.getCurrentAgeUnit()==null)
                    ovc=util.getOvcWithCurrentAge(ovc);
                    if(ovc.getVcEnrollmentId()==null)
                    ovc.setVcEnrollmentId(ovc.getOvcId());
                    ovc=getOvcWithCleanedFirstName(ovc);
                    ovc=getOvcWithUpdatedWithdrawalStatus(ovc);
                    session = HibernateUtil.getSession();
                    tx = session.beginTransaction();
                    session.save(ovc);
                    //System.err.println("ovc.getOvcId() in saveOvc is "+ovc.getOvcId());
                    tx.commit();
                    session.close();
                    if(saveHiv)
                    saveOvcHIVStatus(ovc);
                    if(saveBirthRegistration)
                    saveOvcBirthRegistrationStatus(ovc);
                }
            }
              
        }
        catch (Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
}

   public void updateOvc(Ovc ovc,boolean saveHiv,boolean saveBirthRegistration) throws Exception 
   {
        try 
        {
            if(ovc !=null)
            {
                Ovc ovc2=getOvc(ovc.getOvcId());
                if(ovc2 !=null)
                {
                    //AppUtility appUtil=new AppUtility();
                   if(DateManager.compareDates(ovc2.getDateOfEntry(), ovc.getDateOfEntry())) 
                   {
                       if(ovc.getVcEnrollmentId()==null)
                       {
                           if(ovc2.getVcEnrollmentId() !=null)
                           ovc.setVcEnrollmentId(ovc2.getVcEnrollmentId());
                           else
                           ovc.setVcEnrollmentId(ovc.getOvcId());
                       }
                       if(ovc.getCurrentAgeUnit()==null)
                       ovc=util.getOvcWithCurrentAge(ovc);
                       ovc=getOvcWithCleanedFirstName(ovc);
                       ovc=getOvcWithUpdatedWithdrawalStatus(ovc);
                       //System.err.println("ovc.getFirstName() is "+ovc.getFirstName());
                        session = HibernateUtil.getSession();
                        tx = session.beginTransaction();
                        session.update(ovc);
                        tx.commit();
                        session.close();
                        if(saveHiv)
                        saveOvcHIVStatus(ovc);
                        if(saveBirthRegistration)
                        saveOvcBirthRegistrationStatus(ovc);
                   }
                }
            }
        } 
        catch (Exception ex)
        {
            session.close();
            ex.printStackTrace();
            //throw new Exception(he);
        }
   }
   private Ovc getOvcWithCleanedFirstName(Ovc ovc)
   {
       if(ovc !=null)
       {
           String firstName=ovc.getFirstName();
           if(firstName !=null && firstName.trim().endsWith(","))
           {
               firstName=firstName.substring(0, firstName.lastIndexOf(","));
                ovc.setFirstName(firstName);
           }
       }
       return ovc;
   }
   public void markedForDelete(Ovc ovc) throws Exception
    {
        try
        {
            ovc.setMarkedForDelete(NomisConstant.MARKEDFORDELETE);
            updateOvc(ovc,false,false);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
   public void deleteOvcFromEnrollmentRecordOnly(Ovc ovc) throws Exception
   {
       try 
       {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.delete(ovc);
            tx.commit();
            session.close();
            util.saveDeletedRecord(ovc.getOvcId(),ovc.getNewOvcId(), "ovc", ovc.getDateEnrollment());
            //HivRecordsManager.deleteHivStatusRecord(ovc.getOvcId(),NomisConstant.ENROLLMENT_POINTOFUPDATE, ovc.getDateEnrollment());
            //deleteAllOvcRecords(ovc.getOvcId());
        } 
        catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
   }
   public void deleteOvc(Ovc ovc) throws Exception
   {
       try 
       {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.delete(ovc);
            tx.commit();
            session.close();
            util.saveDeletedRecord(ovc.getOvcId(),ovc.getNewOvcId(), "ovc", ovc.getDateEnrollment());
            deleteAllOvcRecords(ovc.getOvcId());
            HivRecordsManager.deleteAllClientHivStatusRecord(ovc.getOvcId());
        } 
        catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
   }
   public void deleteOvc(String ovcId) throws Exception {

       List list = new ArrayList();
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from Ovc as ovc where ovc.ovcId = :id").setString("id", ovcId).list();
            tx.commit();
            session.close();
            if(list != null && !list.isEmpty()) 
            {
               ovc = (Ovc)list.get(0);
               deleteOvc(ovc);
               //session.delete(ovc);
            }
        } 
        catch (HibernateException he)
        {
            if(session !=null && session.isOpen())
            session.close();
            throw new Exception(he);
        }
   }
   public void deleteAllOvcInHousehold(String hhUniqueId) throws Exception
   {
       List ovcList=getOvcListPerHousehold(hhUniqueId);
        if(ovcList !=null)
        {
            for(int i=0; i<ovcList.size(); i++)
            {
                Ovc ovc=(Ovc)ovcList.get(i);
                deleteAllOvcRecords(ovc.getOvcId());
            }
        }
   }
   public void deleteAllOvcRecords(String ovcId) throws Exception
   {
       ChildStatusIndexDao csidao=new ChildStatusIndexDaoImpl();
       csidao.deleteAllCsiRecordsPerOvc(ovcId);
       OvcServiceDao sdao=new OvcServiceDaoImpl();
       sdao.deleteOvcServices(ovcId);
       OvcReferralDao rdao=new OvcReferralDaoImpl();
       rdao.deleteOvcReferrals(ovcId);
       FollowupDao fdao=new FollowupDaoImpl();
       fdao.deleteOvcFollowups(ovcId);
       OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl();
       wdao.deleteOvcWithdrawal(ovcId);
       HivStatusUpdateDao hsudao=new HivStatusUpdateDaoImpl();
       hsudao.deleteAllHivStatusUpdatesPerClient(ovcId);
       //deleteOvc(ovcId);
   }
   public List getCountOfOvcsByCurrentAge(String logic,int currentAge) throws Exception
   {
        List list = new ArrayList();
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,count(distinct ovc.ovcId) "+util.getHouseholdOvcQueryPart()+" and ovc.currentAge"+logic+currentAge+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode").list();
            tx.commit();
            session.close();
        }
        catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
        return list;
    }
   public List getListOfOvcsByCurrentAge(String stateCode,String logic,int currentAge) throws Exception
   {
        List list = new ArrayList();
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(util.getHouseholdOvcQueryPart()+" and hhe.stateCode=:code and ovc.currentAge"+logic+currentAge).setString("code", stateCode).list();
            tx.commit();
            session.close();
        }
        catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
        return list;
    }
   public List getOvcs(String stateCode) throws Exception {
        List list = new ArrayList();
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            Query query = session.createQuery(util.getHouseholdOvcQueryPart()+" and hhe.stateCode='"+stateCode+"' order by ovc.dateEnrollment desc, ovc.ovcId desc");
            list = query.list();
            tx.commit();
            session.close();
        }
        catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
        return list;
    }
   public List getOvcs(String stateCode,int startAge, int endAge) throws Exception {
        List list = new ArrayList();
         try
         {
            String sql=util.getHouseholdOvcQueryPart()+" and hhe.stateCode='"+stateCode+"' and ovc.currentAge between "+startAge+" and "+endAge+" order by ovc.dateEnrollment desc, ovc.ovcId desc";
            System.err.println(sql);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            Query query = session.createQuery(sql);
            list = query.list();
            tx.commit();
            session.close();
        }
        catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
        return list;
    }
   public List getOvcs() throws Exception {
        List list = new ArrayList();
         try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

        Query query = session.createQuery("from Ovc ovc order by ovc.dateEnrollment desc, ovc.ovcId desc");
        list = query.list();

        tx.commit();
        session.close();
        } catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }

        return list;
    }
    public List getOvcList() throws Exception {
        List list = new ArrayList();

         try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        Query query = session.createQuery("from Ovc as ovc order by ovc.ovcId asc");

        list = query.list();
        tx.commit();
        session.close();
        } catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
        return list;
    }
    public List getOvcCount(String startDate,String endDate) throws Exception {
        List list = new ArrayList();

         try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        Query query = session.createQuery("select count(ovc.ovcId) from Ovc as ovc where ovc.dateOfEntry between '"+ startDate+"' and '"+endDate+"'");

        list = query.list();
        tx.commit();
        session.close();
        } catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
        return list;
    }
    public List getOvcList(String additionalOrgQuery) throws Exception {
        List list = new ArrayList();
        List ovcList = new ArrayList();
        String householdQueryPart=util.getHouseholdQueryPart();
        String sql=householdQueryPart+"Ovc ovc where hhe.hhUniqueId=ovc.hhUniqueId "+additionalOrgQuery+" order by ovc.ovcId asc";
         try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        Query query = session.createQuery(sql);

        list = query.list();
        tx.commit();
        session.close();
        } catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
        for(int i=0; i<list.size(); i++)
        {
            Object[] obj=(Object[])list.get(i);
            ovcList.add((Ovc)obj[1]);
        }
        return ovcList;
    }
    public List getOvcList(String startDate,String endDate,String additionalOrgQuery) throws Exception {
        List list = new ArrayList();
        List ovcList = new ArrayList();
        String householdQueryPart=util.getHouseholdQueryPart();
        String sql=householdQueryPart+"Ovc as ovc where hhe.hhUniqueId=ovc.hhUniqueId and ovc.dateOfEntry between '"+ startDate+"' and '"+endDate+"' "+additionalOrgQuery+" order by ovc.ovcId asc";
        if(startDate.equalsIgnoreCase("All") || endDate.equalsIgnoreCase("All"))
        sql=householdQueryPart+"Ovc ovc where hhe.hhUniqueId=ovc.hhUniqueId "+additionalOrgQuery+" order by ovc.ovcId asc";
         try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        Query query = session.createQuery(sql);

        list = query.list();
        tx.commit();
        session.close();
        } catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
        for(int i=0; i<list.size(); i++)
        {
            Object[] obj=(Object[])list.get(i);
            ovcList.add((Ovc)obj[1]);
        }
        return ovcList;
    }
    public List searchOvc(String ovcId) throws Exception {
        List list = new ArrayList();
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

        list = session.createQuery("from Ovc ovc where ovc.ovcId = :id")
                .setString("id", ovcId)
                .list();
        tx.commit();
        session.close();
        } catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
        return list;
    }
    public List searchOvcByEnrollmentId(String ovcId) throws Exception {
        List list = new ArrayList();
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from Ovc ovc where ovc.vcEnrollmentId = :id").setString("id", ovcId).list();
            tx.commit();
            session.close();
        } 
        catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
        return list;
    }
    public List getOvcRecords(String[] params) throws Exception
    {
        DaoUtil util=new DaoUtil();
        String[] dateParts={params[4],params[5],params[6],params[7]};
        String householdQueryPart=util.getHouseholdQueryPart();
        String queryPart=" and ovc.dateEnrollment ";
        String dateQueryPart=util.getQueryPeriod(dateParts, queryPart);
        String additionalQuery=util.getQueryCriteria(params);
        List list = new ArrayList();
        List ovcList = new ArrayList();
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            String sql=householdQueryPart+"Ovc ovc where hhe.hhUniqueId=ovc.hhUniqueId "+dateQueryPart+" "+additionalQuery+" order by ovc.ovcId";
            //System.err.println("sql in OvcDaoImpl is "+sql);
            list = session.createQuery(sql).list();
        tx.commit();
        session.close();
        } catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
        //System.err.println("list size in OvcDaoImpl is "+list.size());
        for(int i=0; i<list.size(); i++)
        {
            Object[] obj=(Object[])list.get(i);
            ovcList.add((Ovc)obj[1]);
        }
        return ovcList;
    }
    public void changeCaregiverId(String oldCaregiverId, String newCaregiverId) throws Exception
    {
        List ovcList=getListOfOvcsPerCaregiver(" ", oldCaregiverId);
        if(ovcList !=null)
        {
            Ovc ovc=null;
            CaregiverDao cgiverdao=new CaregiverDaoImpl();
            for(int i=0; i<ovcList.size(); i++)
            {
               ovc=(Ovc)ovcList.get(i);
               if(newCaregiverId==null)
               ovc.setCaregiverId(null);
               else
               {
                   //Assign Caregiver only if Caregiver and OVC are from the same households
                   if(cgiverdao.getCaregiverByHhUniqueIdAndCaregiverId(ovc.getHhUniqueId(), newCaregiverId) !=null)
                   ovc.setCaregiverId(newCaregiverId);
                   else
                   {
                       //De-assign old Caregiver if it no longer exist.
                       if(cgiverdao.getCaregiverByHhUniqueIdAndCaregiverId(ovc.getHhUniqueId(), oldCaregiverId) ==null)
                       ovc.setCaregiverId(null);
                   }    
               }
               updateOvc(ovc,false,false);
            }
        }
    }
    public void saveOvcBirthRegistrationDetails()
    {
        
    }
    public void saveBirthRegistrationDetails(Ovc ovc) throws Exception
    {
        if(ovc !=null)
        {
            BirthRegistration br=new BirthRegistration();
            BirthRegistrationDao brdao=new BirthRegistrationDaoImpl();
            br.setBirthRegistrationStatus(ovc.getBirthCertificate());
            br.setClientId(ovc.getOvcId());
            br.setClientType("ovc");
            if(ovc.getDateOfEntry()==null)
            br.setDateModified(appUtil.getCurrentDate());
            br.setDateOfRegistration(ovc.getDateEnrollment());
            //br.setRecordId(appUtil.generateUniqueId(11));
            br.setPointOfUpdate(ovc.getPointOfService());
            brdao.saveBirthRegistration(br);
        }
    }
    public Ovc getOvcWithUpdatedWithdrawalStatus(Ovc ovc) throws Exception
    {
       try
       {
             if(ovc !=null)
             {
                 OvcWithdrawal withdrawal=util.getOvcWithdrawalDaoInstance().getOvcWithdrawal(ovc.getOvcId());
                 if(withdrawal !=null)
                 ovc.setWithdrawnFromProgram("Yes");
                 else
                 ovc.setWithdrawnFromProgram("No");                 
             } 
       }
       catch(Exception ex)
       {
           ex.printStackTrace();
       }
       return ovc;
    }
    private void closeSession(Session session)
    {
        if(session !=null && session.isOpen())
        session.close();
    }
}

