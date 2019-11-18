/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.Caregiver;
import com.fhi.kidmap.business.HivStatus;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.HivStatusUpdate;
import com.fhi.kidmap.business.HouseholdService;
import com.fhi.kidmap.business.Ovc;
import com.fhi.nomis.nomisutils.DateManager;
import com.fhi.nomis.nomisutils.NomisConstant;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class HivStatusUpdateDaoImpl implements HivStatusUpdateDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    AppUtility appUtil=new AppUtility();
    DaoUtil util=new DaoUtil();
    public int updateCaregiverActiveHivStatusWithPreviousHivStatus() throws Exception
    {
        int count=0;
        try 
        {
            String query=util.getHouseholdQueryPart()+" Caregiver cgiver, HivStatusUpdate hsu where hhe.hhUniqueId=cgiver.hhUniqueId and hsu.clientId=cgiver.caregiverId and (hsu.hivStatus='unknown' and hsu.recordStatus='active')";
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(query).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                
                HivStatusUpdate hsu=null;
                HivStatusUpdate hsu2=null;
                List prevHivList=null;
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    hsu=(HivStatusUpdate)objArray[2];
                    prevHivList=getListOfKnownHivStatusRecordsByClientId(hsu.getClientId());
                    if(prevHivList !=null && !prevHivList.isEmpty())
                    {
                        hsu2=(HivStatusUpdate)prevHivList.get(0);
                        hsu.setHivStatus(hsu2.getHivStatus());
                        this.updateHivStatusOnly(hsu);
                        count++;
                    }
                }
            }
        }
         catch (Exception ex)
         {
             closeSession(session);
             ex.printStackTrace();
         }
         return count;
    }
    public List getListOfKnownHivStatusRecordsByClientId(String clientId) throws Exception
    {
        List list = new ArrayList();
        try 
        {
            String query="From HivStatusUpdate hsu where hsu.clientId=:id and (hsu.hivStatus='"+NomisConstant.HIV_NEGATIVE+"' or hsu.hivStatus='"+NomisConstant.HIV_POSITIVE+"')";
            System.err.println(query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(query).setString("id", clientId).list();
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
    public int updateClientTypeOnOvcHivStatusUpdate() throws Exception
    {
        int count=0;   
        try 
        {
            String query=util.getHouseholdQueryPart()+" Ovc ovc, HivStatusUpdate hsu where hhe.hhUniqueId=ovc.hhUniqueId and hsu.clientId=ovc.ovcId and (hsu.clientType !='"+NomisConstant.OVC_TYPE+"')";
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(query).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                HivStatusUpdate hsu=null;
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    hsu=(HivStatusUpdate)objArray[2];
                    hsu.setClientType(NomisConstant.OVC_TYPE);
                    updateHivStatusOnly(hsu);
                    count++;
                }
            }
        }
         catch (Exception ex)
         {
             closeSession(session);
             ex.printStackTrace();
         }
         return count;
    }
    public int updateClientTypeOnCaregiverHivStatusUpdate() throws Exception
    {
        int count=0;   
        try 
        {
            String query=util.getHouseholdQueryPart()+" Caregiver cgiver, HivStatusUpdate hsu where hhe.hhUniqueId=cgiver.hhUniqueId and hsu.clientId=cgiver.caregiverId and (hsu.clientType !='"+NomisConstant.Caregiver_TYPE+"')";
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(query).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                HivStatusUpdate hsu=null;
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    hsu=(HivStatusUpdate)objArray[2];
                    hsu.setClientType(NomisConstant.Caregiver_TYPE);
                    updateHivStatusOnly(hsu);
                    count++;
                }
            }
        }
         catch (Exception ex)
         {
             closeSession(session);
             ex.printStackTrace();
         }
         return count;
    }
    public int updateHivStatusWithCorrectLabel() throws Exception
    {
        int count=0;
               
        try 
        {
            String query="update HivStatusUpdate set hivStatus ='unknown' where (UPPER(hivStatus) not like '%UNKNOWN%' and UPPER(hivStatus) not like '%NEGATIVE%' and UPPER(hivStatus) not like '%POSITIVE%')";
            
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            count=session.createSQLQuery(query).executeUpdate();
            tx.commit();
            closeSession(session);
            
        }
         catch (Exception ex)
         {
             closeSession(session);
             ex.printStackTrace();
         }
         return count;
    }
    public int updateCaregiverHivStatusWithHivStatusAtService() throws Exception
    {
        int count=0;
               
        try 
        {
            String query=util.getHouseholdQueryPart()+" Caregiver cgiver, HivStatusUpdate hsu,HouseholdService hhs where hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=hhs.caregiverId and hsu.clientId=hhs.caregiverId and (hhs.currentHivStatus !='unknown' and hsu.hivStatus='unknown' and hsu.recordStatus='active')";
            
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(query).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                HouseholdService hhs=null;
                HivStatusUpdate hsu=null;
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    hhs=(HouseholdService)objArray[3];
                    hsu=(HivStatusUpdate)objArray[2];
                    hsu.setHivStatus(hhs.getCurrentHivStatus());
                    this.updateHivStatusOnly(hsu);
                    count++;
                    //listOfOvc.add(objArray[2]);
                }
            }
        }
         catch (Exception ex)
         {
             closeSession(session);
             ex.printStackTrace();
         }
         return count;
    }
    public int updateCaregiverHivStatusWithBaselineHivStatus() throws Exception
    {
        int count=0;
               
        try 
        {
            String query=util.getHouseholdQueryPart()+" Caregiver cgiver, HivStatusUpdate hsu where hhe.hhUniqueId=cgiver.hhUniqueId and hsu.clientId=cgiver.caregiverId and (cgiver.cgiverHivStatus !='unknown' and hsu.hivStatus='unknown' and hsu.recordStatus='active')";
            
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(query).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                Caregiver cgiver=null;
                HivStatusUpdate hsu=null;
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    cgiver=(Caregiver)objArray[1];
                    hsu=(HivStatusUpdate)objArray[2];
                    hsu.setHivStatus(cgiver.getCgiverHivStatus());
                    this.updateHivStatusOnly(hsu);
                    count++;
                    //listOfOvc.add(objArray[2]);
                }
            }
        }
         catch (Exception ex)
         {
             closeSession(session);
             ex.printStackTrace();
         }
         return count;
    }
    public int getNumberOfActiveHivPositiveOvcIdentifiedAndServedWithinTheReportPeriod(String additionalQueryCriteria,String startDate,String endDate,boolean currentlyEnrolled,boolean onTreatment) throws Exception
    {
        int count=0;
        //List list = new ArrayList();
        try 
        {
            String periodQuery=util.getHIVStatusDateQuery(startDate, endDate);
            String servicePeriodQuery=util.getOvcServiceDateQuery(startDate, endDate);
            String currentlyEnrolledQuery=" ";
            if(currentlyEnrolled)
            currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
            
            String enrolledOnARTQuery=util.getActiveHivStatusCriteria(NomisConstant.HIV_POSITIVE, NomisConstant.OVC_TYPE);
            if(onTreatment)
            enrolledOnARTQuery=util.getEnrolledOnARTQueryCriteria(NomisConstant.OVC_TYPE);
            String query="select count (distinct hsu.clientId) "+util.getHouseholdQueryPart()+" HivStatusUpdate hsu, Ovc ovc, OvcService service where hhe.hhUniqueId=ovc.hhUniqueId and hsu.clientId=ovc.ovcId and ovc.ovcId=service.ovcId "+additionalQueryCriteria+currentlyEnrolledQuery+servicePeriodQuery+enrolledOnARTQuery+periodQuery;
            
            System.err.println("Query in getNumberOfHivPositiveOvcIdentifiedAndServedWithinTheReportPeriod is "+query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(query).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                count=Integer.parseInt(list.get(0).toString());
            }
        }
         catch (Exception ex)
         {
             closeSession(session);
             ex.printStackTrace();
         }
         return count;
    }
    public int getNumberOfHivPositiveOvcIdentifiedServedAndWithdrawnWithinTheReportPeriod(String additionalQueryCriteria,String startDate,String endDate,boolean onTreatment) throws Exception
    {
        int count=0;
        //List list = new ArrayList();
        try 
        {
            String periodQuery=util.getHIVStatusDateQuery(startDate, endDate);
            String servicePeriodQuery=util.getOvcServiceDateQuery(startDate, endDate);
            String enrolledOnARTQuery=util.getActiveHivStatusCriteria(NomisConstant.HIV_POSITIVE, NomisConstant.OVC_TYPE);
            if(onTreatment)
            enrolledOnARTQuery=util.getEnrolledOnARTQueryCriteria(NomisConstant.OVC_TYPE);
            String query="select count (distinct hsu.clientId) "+util.getHouseholdQueryPart()+" HivStatusUpdate hsu, Ovc ovc, OvcService service,OvcWithdrawal withdrawal where hhe.hhUniqueId=ovc.hhUniqueId and hsu.clientId=ovc.ovcId and ovc.ovcId=service.ovcId and ovc.ovcId=withdrawal.ovcId"+additionalQueryCriteria+servicePeriodQuery+enrolledOnARTQuery+util.getWithdrawalServicePeriodCriteria(startDate)+periodQuery;
            System.err.println("Query in getNumberOfHivPositiveOvcIdentifiedServedAndWithdrawnWithinTheReportPeriod is "+query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(query).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                count=Integer.parseInt(list.get(0).toString());
            }
        }
         catch (Exception ex)
         {
             closeSession(session);
             ex.printStackTrace();
         }
         return count;
    }
    public List getListOfActiveHivPositiveOvcIdentifiedAndServedWithinTheReportPeriod(String additionalQueryCriteria,String startDate,String endDate,boolean currentlyEnrolled,boolean onTreatment) throws Exception
    {
        List mainList = new ArrayList();
        try 
        {
            String periodQuery=util.getHIVStatusDateQuery(startDate, endDate);
            String servicePeriodQuery=util.getOvcServiceDateQuery(startDate, endDate);
            String currentlyEnrolledQuery=" ";
            if(currentlyEnrolled)
            currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
            
            String enrolledOnARTQuery=util.getActiveHivStatusCriteria(NomisConstant.HIV_POSITIVE, NomisConstant.OVC_TYPE);
            if(onTreatment)
            enrolledOnARTQuery=util.getEnrolledOnARTQueryCriteria(NomisConstant.OVC_TYPE);
            String query=util.getHouseholdQueryPart()+" HivStatusUpdate hsu, Ovc ovc, OvcService service where hhe.hhUniqueId=ovc.hhUniqueId and hsu.clientId=ovc.ovcId and ovc.ovcId=service.ovcId "+additionalQueryCriteria+currentlyEnrolledQuery+servicePeriodQuery+enrolledOnARTQuery+periodQuery;
            
            System.err.println("Query in getNumberOfHivPositiveOvcIdentifiedAndServedWithinTheReportPeriod is "+query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(query).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                List uniqueIdList=new ArrayList();
                Ovc ovc=null;
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    ovc=(Ovc)objArray[2];
                    if(!uniqueIdList.contains(ovc.getOvcId()))
                    {
                        mainList.add(ovc);
                        uniqueIdList.add(ovc.getOvcId());
                    }
                }
            }
        }
         catch (Exception ex)
         {
             closeSession(session);
             ex.printStackTrace();
         }
         return mainList;
    }
    public List getListOfHivPositiveOvcIdentifiedServedAndWithdrawnWithinTheReportPeriod(String additionalQueryCriteria,String startDate,String endDate,boolean onTreatment) throws Exception
    {
        int count=0;
        List mainList = new ArrayList();
        try 
        {
            String periodQuery=util.getHIVStatusDateQuery(startDate, endDate);
            String servicePeriodQuery=util.getOvcServiceDateQuery(startDate, endDate);
            String enrolledOnARTQuery=util.getActiveHivStatusCriteria(NomisConstant.HIV_POSITIVE, NomisConstant.OVC_TYPE);
            if(onTreatment)
            enrolledOnARTQuery=util.getEnrolledOnARTQueryCriteria(NomisConstant.OVC_TYPE);
            String query=util.getHouseholdQueryPart()+" HivStatusUpdate hsu, Ovc ovc, OvcService service,OvcWithdrawal withdrawal where hhe.hhUniqueId=ovc.hhUniqueId and hsu.clientId=ovc.ovcId and ovc.ovcId=service.ovcId and ovc.ovcId=withdrawal.ovcId"+additionalQueryCriteria+servicePeriodQuery+enrolledOnARTQuery+util.getWithdrawalServicePeriodCriteria(startDate)+periodQuery;
            System.err.println("Query in getNumberOfHivPositiveOvcIdentifiedServedAndWithdrawnWithinTheReportPeriod is "+query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(query).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                List uniqueIdList=new ArrayList();
                Ovc ovc=null;
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    ovc=(Ovc)objArray[2];
                    if(!uniqueIdList.contains(ovc.getOvcId()))
                    {
                        mainList.add(ovc);
                        uniqueIdList.add(ovc.getOvcId());
                    }
                }
            }
        }
         catch (Exception ex)
         {
             closeSession(session);
             ex.printStackTrace();
         }
         return mainList;
    }
    public int getNumberOfHivPositiveOvcIdentifiedAndServedWithinTheReportPeriod(String additionalQueryCriteria,String startDate,String endDate,boolean currentlyEnrolled,boolean onTreatment) throws Exception
    {
        int activeCount=getNumberOfActiveHivPositiveOvcIdentifiedAndServedWithinTheReportPeriod(additionalQueryCriteria,startDate,endDate,true,onTreatment);
        int withdrawnCount=getNumberOfHivPositiveOvcIdentifiedServedAndWithdrawnWithinTheReportPeriod(additionalQueryCriteria,startDate,endDate,onTreatment);
        int count=activeCount+withdrawnCount;
        
        return count;
    }
    public List getListOfHivPositiveOvcIdentifiedAndServedWithinTheReportPeriod(String additionalQueryCriteria,String startDate,String endDate,boolean currentlyEnrolled,boolean onTreatment) throws Exception
    {
        List activeList=getListOfActiveHivPositiveOvcIdentifiedAndServedWithinTheReportPeriod(additionalQueryCriteria,startDate,endDate,true,onTreatment);
        List withdrawnList=getListOfHivPositiveOvcIdentifiedServedAndWithdrawnWithinTheReportPeriod(additionalQueryCriteria,startDate,endDate,onTreatment);
        List mainList=new ArrayList();
        
        if(activeList !=null)
        mainList.addAll(activeList);
        if(withdrawnList !=null)
        mainList.addAll(withdrawnList);
        return mainList;
    }
    public int getNumberOfHivPositiveOvcIdentifiedWithinTheReportPeriod(String additionalQueryCriteria,String startDate,String endDate,boolean currentlyEnrolled) throws Exception
    {
        int count=0;
        List list = new ArrayList();
        try 
        {
            String periodQuery=util.getHIVStatusDateQuery(startDate, endDate);
            String currentlyEnrolledQuery=" ";
            if(currentlyEnrolled)
            currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
            String query="select count (distinct hsu.clientId) "+util.getHouseholdQueryPart()+" HivStatusUpdate hsu, Ovc ovc where hhe.hhUniqueId=ovc.hhUniqueId and hsu.clientId=ovc.ovcId "+additionalQueryCriteria+currentlyEnrolledQuery+util.getActiveHivStatusCriteria(NomisConstant.HIV_POSITIVE, NomisConstant.OVC_TYPE)+periodQuery;
            
            //System.err.println(query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(query).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                count=Integer.parseInt(list.get(0).toString());
            }
        }
         catch (Exception ex)
         {
             closeSession(session);
             ex.printStackTrace();
         }
         return count;
    }
    public List getListOfHivPositiveOvcIdentifiedWithinTheReportPeriod(String additionalQueryCriteria,String startDate,String endDate,boolean currentlyEnrolled) throws Exception
    {
        List list = new ArrayList();
        List listOfOvc = new ArrayList();
        try 
        {
            String periodQuery=util.getHIVStatusDateQuery(startDate, endDate);
            String currentlyEnrolledQuery=" ";
            if(currentlyEnrolled)
            currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
            String query=util.getHouseholdQueryPart()+" HivStatusUpdate hsu, Ovc ovc where hhe.hhUniqueId=ovc.hhUniqueId and hsu.clientId=ovc.ovcId "+additionalQueryCriteria+currentlyEnrolledQuery+util.getActiveHivStatusCriteria(NomisConstant.HIV_POSITIVE, NomisConstant.OVC_TYPE)+periodQuery;
            
            //System.err.println(query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(query).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    listOfOvc.add(objArray[2]);
                }
            }
        }
         catch (Exception ex)
         {
             closeSession(session);
             ex.printStackTrace();
         }
         return listOfOvc;
    }
    public int getNumberOfOvcTestedAndReceivedResult(String additionalQueryCriteria,String startDate,String endDate,boolean currentlyEnrolled) throws Exception
    {
        int count=0;
        int activeTested=getNumberOfActiveOvcTestedAndReceivedResult(additionalQueryCriteria,startDate,endDate,currentlyEnrolled);
        int graduatedTested=getNumberOfExitedOvcTestedAndReceivedResult(additionalQueryCriteria,startDate,endDate,NomisConstant.GRADUATEDCODE);
        count=activeTested+graduatedTested;
        /*List list = new ArrayList();
        try 
        {
            String refPeriodQuery=util.getReferralPeriodQueryPart(startDate, endDate);
            String currentlyEnrolledQuery=" ";
            if(currentlyEnrolled)
            currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
            String query="select count (distinct hsu.clientId) "+util.getHouseholdQueryPart()+" HivStatusUpdate hsu, Ovc ovc,OvcReferral ovcRef where hhe.hhUniqueId=ovc.hhUniqueId and hsu.clientId=ovc.ovcId and ovc.ovcId=ovcRef.ovcId "+additionalQueryCriteria+currentlyEnrolledQuery+" and "+util.getActivePositiveOrNegativeHivStatusCriteria(NomisConstant.OVC_TYPE)+refPeriodQuery;
            
            System.err.println(query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(query).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                count=Integer.parseInt(list.get(0).toString());
            }
        }
         catch (Exception ex)
         {
             closeSession(session);
             ex.printStackTrace();
         }*/
         return count;
    }
    public int getNumberOfActiveOvcTestedAndReceivedResult(String additionalQueryCriteria,String startDate,String endDate,boolean currentlyEnrolled) throws Exception
    {
        int count=0;
        List list = new ArrayList();
        try 
        {
            String refPeriodQuery=util.getReferralPeriodQueryPart(startDate, endDate);
            String currentlyEnrolledQuery=" ";
            if(currentlyEnrolled)
            currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
            String query="select count (distinct hsu.clientId) "+util.getHouseholdQueryPart()+" HivStatusUpdate hsu, Ovc ovc,OvcReferral ovcRef where hhe.hhUniqueId=ovc.hhUniqueId and hsu.clientId=ovc.ovcId and ovc.ovcId=ovcRef.ovcId "+additionalQueryCriteria+currentlyEnrolledQuery+" and "+util.getActivePositiveOrNegativeHivStatusCriteria(NomisConstant.OVC_TYPE)+refPeriodQuery;
            
            System.err.println(query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(query).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                count=Integer.parseInt(list.get(0).toString());
            }
        }
         catch (Exception ex)
         {
             closeSession(session);
             ex.printStackTrace();
         }
         return count;
    }
    public int getNumberOfExitedOvcTestedAndReceivedResult(String additionalQueryCriteria,String startDate,String endDate,int exitStatus) throws Exception
    {
        int count=0;
        List list = new ArrayList();
        try 
        {
            String refPeriodQuery=util.getReferralPeriodQueryPart(startDate, endDate);
            String withdrawalStatusQuery=util.getWithdrawalStatusQuery(exitStatus);
            
            String query="select count (distinct hsu.clientId) "+util.getHouseholdQueryPart()+" HivStatusUpdate hsu, Ovc ovc,OvcReferral ovcRef, OvcWithdrawal withdrawal where hhe.hhUniqueId=ovc.hhUniqueId and hsu.clientId=ovc.ovcId and ovc.ovcId=ovcRef.ovcId and ovc.ovcId=withdrawal.ovcId "+additionalQueryCriteria+withdrawalStatusQuery+" and "+util.getActivePositiveOrNegativeHivStatusCriteria(NomisConstant.OVC_TYPE)+refPeriodQuery;
            
            System.err.println(query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(query).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                count=Integer.parseInt(list.get(0).toString());
            }
        }
         catch (Exception ex)
         {
             closeSession(session);
             ex.printStackTrace();
         }
         return count;
    }
    
    public List getListOfOvcTestedAndReceivedResult(String additionalQueryCriteria,String startDate,String endDate,boolean currentlyEnrolled) throws Exception
    {
        List ovcList = new ArrayList();
        try 
        {
            String periodQuery=util.getReferralPeriodQueryPart(startDate, endDate);
            String currentlyEnrolledQuery=" ";
            if(currentlyEnrolled)
            currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
            String query=util.getHouseholdQueryPart()+" HivStatusUpdate hsu, Ovc ovc,OvcReferral ovcRef where hhe.hhUniqueId=ovc.hhUniqueId and hsu.clientId=ovc.ovcId and ovc.ovcId=ovcRef.ovcId "+additionalQueryCriteria+currentlyEnrolledQuery+" and "+util.getActivePositiveOrNegativeHivStatusCriteria(NomisConstant.OVC_TYPE)+periodQuery;
            
            //System.err.println(query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(query).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                List idList=new ArrayList();
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    Ovc ovc=(Ovc)objArray[2];
                    if(!idList.contains(ovc.getOvcId()))
                    {
                        idList.add(ovc.getOvcId());
                        ovcList.add(ovc);
                    }
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
    public List getHivStatusUpdateWithDefaultLastHivTrackingDateByCommunity(String communityCode) throws Exception
    {
        List hsuList = new ArrayList();
        try 
        {
            String query=util.getHouseholdQueryPart()+" HivStatusUpdate hsu,Ovc ovc where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=hsu.clientId and hhe.communityCode=:ccode and hsu.lastHivTrackingDate='1900-01-01'";
            System.err.println(query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(query).setString("ccode", communityCode).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                HivStatusUpdate hsu=null;
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    hsu=(HivStatusUpdate)objArray[1];
                    hsuList.add(hsu);
                }
            }
        }
         catch (HibernateException he)
         {
             closeSession(session);
             throw new Exception(he);
         }
        return hsuList;
    }
    public List getHouseholdAndCaregiverHivStatusRecords(String hivStatus) throws Exception
    {
        List list = new ArrayList();
        String hivStatusQuery=""; //hsu.hivStatus='"+hivStatus+"' and
        if(hivStatus !=null)
        hivStatusQuery=" and hsu.hivStatus='"+hivStatus+"' and hsu.recordStatus='active'";
        try 
        {
            String query="From HivStatusUpdate hsu where hsu.clientType='"+NomisConstant.HOUSEHOLD_TYPE+"' or hsu.clientType='"+NomisConstant.Caregiver_TYPE+"'"+hivStatusQuery;
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
    public List getOvcHivStatusByStatusAndClientType(String hivStatus, String beneficiaryType) throws Exception
    {
        List list = new ArrayList();
        String hivStatusQuery=""; //hsu.hivStatus='"+hivStatus+"' and
        if(hivStatus !=null)
        hivStatusQuery=" and hsu.hivStatus='"+hivStatus+"' and hsu.recordStatus='active'";
        try 
        {
            String query="From HivStatusUpdate hsu where hsu.clientType='"+beneficiaryType+"'"+hivStatusQuery;
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
    public int getNumberOfHivPositiveOvcEnrolledOnART(String additionalQueryCriteria,String startDate,String endDate,boolean currentlyEnrolled) throws Exception
    {
        int count=0;
        List list = new ArrayList();
        try 
        {
            String periodQuery=util.getHIVStatusDateQuery(startDate, endDate);
            String currentlyEnrolledQuery=" ";
            if(currentlyEnrolled)
            currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
            String query="select count (distinct hsu.clientId) "+util.getHouseholdQueryPart()+" HivStatusUpdate hsu, Ovc ovc where hhe.hhUniqueId=ovc.hhUniqueId and hsu.clientId=ovc.ovcId "+additionalQueryCriteria+currentlyEnrolledQuery+util.getActiveHivStatusCriteria(NomisConstant.HIV_POSITIVE, NomisConstant.OVC_TYPE)+util.getHivPositiveEnrolledOnARTStatusCriteria("Yes")+periodQuery;
            //String query="select count (distinct hsu.clientId) "+util.getHouseholdQueryPart()+" HivStatusUpdate hsu, Ovc ovc where hhe.hhUniqueId=ovc.hhUniqueId and hsu.clientId=ovc.ovcId "+additionalQueryCriteria+currentlyEnrolledQuery+" and "+util.getActiveHivStatusCriteria(NomisConstant.HIV_POSITIVE, NomisConstant.OVC_TYPE) +" and "+util.getHivPositiveEnrolledOnARTStatusCriteria("Yes")+periodQuery;
            
            System.err.println(query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(query).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                count=Integer.parseInt(list.get(0).toString());
            }
        }
         catch (Exception ex)
         {
             closeSession(session);
             ex.printStackTrace();
         }
         return count;
    }
    public List getListOfHivPositiveOvcEnrolledOnART(String additionalQueryCriteria,String startDate,String endDate,boolean currentlyEnrolled) throws Exception
    {
        List list = new ArrayList();
        List listOfOvc = new ArrayList();
        try 
        {
            String periodQuery=util.getHIVStatusDateQuery(startDate, endDate);
            String currentlyEnrolledQuery=" ";
            if(currentlyEnrolled)
            currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
            String query=util.getHouseholdQueryPart()+" HivStatusUpdate hsu, Ovc ovc where hhe.hhUniqueId=ovc.hhUniqueId and hsu.clientId=ovc.ovcId "+additionalQueryCriteria+currentlyEnrolledQuery+util.getActiveHivStatusCriteria(NomisConstant.HIV_POSITIVE, NomisConstant.OVC_TYPE)+util.getHivPositiveEnrolledOnARTStatusCriteria("Yes")+periodQuery;
            
            System.err.println(query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(query).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    listOfOvc.add(objArray[2]);
                }
            }
        }
         catch (Exception ex)
         {
             closeSession(session);
             ex.printStackTrace();
         }
         return listOfOvc;
    }
    public List getDistinctClientIdByPointOfUpdate(String pointOfUpdate,boolean knownStatusOnly) throws Exception
    {
        String knowNstatusOnlyQuery="";
        if(knownStatusOnly)
        {
            knowNstatusOnlyQuery=" and hsu.hivStatus is not null and hsu.hivStatus !='"+NomisConstant.HIV_UNKNOWN+"'";
        }
        List list = new ArrayList();
        try 
        {
            String query="select distinct hsu.clientId from HivStatusUpdate hsu where hsu.pointOfUpdate=:pou"+knowNstatusOnlyQuery;
            
            System.err.println(query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(query).setString("pou", pointOfUpdate).list();
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
    public List getHivStatusRecordsFromOvcEnrollment(String communityCode) throws Exception
    {
        List list = new ArrayList();
        try 
        {
            String query=util.getHouseholdQueryPart()+" HivStatusUpdate hsu, Ovc ovc where hhe.hhUniqueId=ovc.hhUniqueId and hsu.clientId=ovc.ovcId and LOWER(hsu.hivStatus) like '%"+NomisConstant.HIV_POSITIVE+"%'";
            //String query=util.getHouseholdQueryPart()+" HivStatusUpdate hsu, Ovc ovc where hhe.hhUniqueId=ovc.hhUniqueId and hsu.clientId=ovc.ovcId and hhe.communityCode=:comcode and hsu.dateOfCurrentStatus=ovc.dateEnrollment and (hsu.clientEnrolledInCare is not null or hsu.enrolledOnART is not null) and (ovc.enrolledInCare is null or ovc.enrolledOnART is null) and LOWER(hsu.hivStatus) like '%"+NomisConstant.HIV_POSITIVE+"%'";
            //.setString("comcode", communityCode)
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
    public List getHivStatusRecordsFromOvcFollowup(String communityCode) throws Exception
    {
        List list = new ArrayList();
        try 
        {
            String query=util.getHouseholdQueryPart()+" HivStatusUpdate hsu, Ovc ovc, FollowupSurvey fu where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=fu.ovcId and hsu.clientId=fu.ovcId and LOWER(hsu.hivStatus) like '%"+NomisConstant.HIV_POSITIVE+"%'";
            //String query=util.getHouseholdQueryPart()+"Ovc ovc, HivStatusUpdate hsu, FollowupSurvey fu where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=fu.ovcId and hsu.clientId=fu.ovcId) and hhe.communityCode=:comcode and hsu.dateOfCurrentStatus=fu.dateOfSurvey and (hsu.clientEnrolledInCare is not null or hsu.enrolledOnART is not null) and (fu.enrolledInCare is null or fu.enrolledOnART is null) and LOWER(hsu.hivStatus) like '%"+NomisConstant.HIV_POSITIVE+"%'";
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
    public List getHivStatusRecordsFromOvcService(String communityCode) throws Exception
    {
        List list = new ArrayList();
        try 
        {
            String query=util.getHouseholdQueryPart()+" HivStatusUpdate hsu, Ovc ovc, FollowupSurvey fu where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=fu.ovcId and hsu.clientId=fu.ovcId and LOWER(hsu.hivStatus) like '%"+NomisConstant.HIV_POSITIVE+"%'";
            //String query=util.getHouseholdQueryPart()+"Ovc ovc, HivStatusUpdate hsu, OvcService service where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=service.ovcId and hsu.clientId=service.ovcId) and hhe.communityCode=:comcode and hsu.dateOfCurrentStatus=service.servicedate and service.currentHivStatus is null and hsu.hivStatus is not null";
            System.err.println(query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(query).setString("comcode", communityCode).list();
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
    public List getDistinctMonthAndYearFromHivStatusUpdate() throws Exception
    {
        List list = new ArrayList();
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct MONTH(hsu.dateOfCurrentStatus),YEAR(hsu.dateOfCurrentStatus) from HivStatusUpdate hsu").list();
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
    public List getHivStatusUpdatesByClientIdAndPointOfUpdate(String clientId,String pointOfUpdate) throws Exception
    {
        List list = new ArrayList();
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from HivStatusUpdate hsu where hsu.pointOfUpdate=:pou and hsu.clientId=:cid").setString("pou", pointOfUpdate).setString("cid", clientId).list();
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
    public List getHivStatusUpdatesByPointOfUpdateAndHivStatus(String pointOfUpdate,String hivStatus) throws Exception
    {
        List list = new ArrayList();
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from HivStatusUpdate hsu where hsu.pointOfUpdate=:pou and hsu.hivStatus=:hst").setString("pou", pointOfUpdate).setString("hst", hivStatus).list();
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
    public List getHivStatusUpdatesByPointOfUpdate(String pointOfUpdate) throws Exception
    {
        List mainList = new ArrayList();
        List list = new ArrayList();
        try 
        {
            List monthAndYearList=getDistinctMonthAndYearFromHivStatusUpdate();
            if(monthAndYearList !=null)
            {
                String query="";
                for(int i=0; i<monthAndYearList.size(); i++)
                {
                    Object[] objArray=(Object[])monthAndYearList.get(i);
                    query="from HivStatusUpdate hsu where hsu.pointOfUpdate=:pou and MONTH(hsu.dateOfCurrentStatus)="+objArray[0]+" and YEAR(hsu.dateOfCurrentStatus="+objArray[1]+")";
                    //System.err.println(query);
                    session = HibernateUtil.getSession();
                    tx = session.beginTransaction();
                    list = session.createQuery(query).setString("pou", pointOfUpdate).list();
                    tx.commit();
                    closeSession(session);
                    mainList.addAll(list);
                }
            }
        }
         catch (HibernateException he)
         {
             closeSession(session);
             throw new Exception(he);
         }
         return mainList;
    }
    
    public int getNumberOfHivStatusUpdatesByClientType(String clientType,String pointOfUpdate) throws Exception
    {
        List list = new ArrayList();
        int noOfRecordsInHivStatusUpdate=0;
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select count (distinct clientId) from HivStatusUpdate hsu where hsu.clientType=:type and hsu.pointOfUpdate=:pos").setString("type", clientType).setString("pos", pointOfUpdate).list();
            tx.commit();
            closeSession(session);
        }
         catch (HibernateException he)
         {
             closeSession(session);
             throw new Exception(he);
         }
        if(list !=null && !list.isEmpty())
        noOfRecordsInHivStatusUpdate=Integer.parseInt(list.get(0).toString());
         return noOfRecordsInHivStatusUpdate;
    }
    public HivStatusUpdate getHivStatusUpdate(String recordId) throws Exception
    {
        HivStatusUpdate hsu=null;
        List list = new ArrayList();
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from HivStatusUpdate hsu where hsu.recordId=:id").setString("id", recordId).list();
            tx.commit();
            closeSession(session);
        }
         catch (HibernateException he)
         {
             closeSession(session);
             throw new Exception(he);
         }
        if(list !=null && !list.isEmpty())
        {
            hsu=(HivStatusUpdate)list.get(0);
        }
         return hsu;
    }
    public HivStatusUpdate getHivStatusUpdateByClientIdDateOfStatusAndPointOfUpdate(String clientId,String dateOfCurrentStatus,String pointOfUpdate) throws Exception
    {
        HivStatusUpdate hsu=null;
        List list = new ArrayList();
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from HivStatusUpdate hsu where hsu.clientId=:id and hsu.dateOfCurrentStatus=:docs and hsu.pointOfUpdate=:pou").setString("id", clientId).setString("docs", dateOfCurrentStatus).setString("pou", pointOfUpdate).list();
            tx.commit();
            closeSession(session);
        }
         catch (HibernateException he)
         {
             closeSession(session);
             throw new Exception(he);
         }
        if(list !=null && !list.isEmpty())
        {
            hsu=(HivStatusUpdate)list.get(0);
        }
         return hsu;
    }
    public HivStatusUpdate getHivStatusUpdatesByClientIdAndDateOfStatus(String clientId,String dateOfCurrentStatus) throws Exception
    {
        HivStatusUpdate hsu=null;
        List list = new ArrayList();
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from HivStatusUpdate hsu where hsu.clientId=:id and hsu.dateOfCurrentStatus=:docs").setString("id", clientId).setString("docs", dateOfCurrentStatus).list();
            tx.commit();
            closeSession(session);
        }
         catch (HibernateException he)
         {
             closeSession(session);
             throw new Exception(he);
         }
        if(list !=null && !list.isEmpty())
        {
            hsu=(HivStatusUpdate)list.get(0);
        }
         return hsu;
    }
    public HivStatusUpdate getActiveHivStatusUpdatesByClientId(String clientId) throws Exception
    {
        HivStatusUpdate hsu=null;
        List list = new ArrayList();
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from HivStatusUpdate hsu where hsu.clientId=:id and hsu.recordStatus='active'").setString("id", clientId).list();
            tx.commit();
            closeSession(session);
        }
         catch (HibernateException he)
         {
             closeSession(session);
             throw new Exception(he);
         }
        if(list !=null && !list.isEmpty())
        {
            hsu=(HivStatusUpdate)list.get(0);
        }
         return hsu;
    }
    public List getBeneficiaryListPerHousehold(String beneficiaryType,String hhUniqueId) throws Exception
    {
        List beneficiaryList = new ArrayList();
        if(beneficiaryType.equalsIgnoreCase("ovc"))
        {
            OvcDao dao=new OvcDaoImpl();
            beneficiaryList=dao.getOvcPerHousehold(hhUniqueId);
        }
        else if(beneficiaryType.equalsIgnoreCase("caregiver"))
        {
            CaregiverDao cgiverdao=new CaregiverDaoImpl();
            beneficiaryList=cgiverdao.getListOfCaregiversFromSameHousehold(hhUniqueId);
        }
         return beneficiaryList;
    }
    public List getAllHivStatusUpdatesPerClient(String clientId) throws Exception
    {
        List list = new ArrayList();
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from HivStatusUpdate hsu where hsu.clientId=:id order by hsu.dateOfCurrentStatus desc").setString("id", clientId).list();
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
    public List getAllHivStatusUpdates() throws Exception
    {
        List list = new ArrayList();
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from HivStatusUpdate hsu ").list();
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
    public List getHivStatusUpdates(String additionalQueryCriteria) throws Exception
    {
        DaoUtil util=new DaoUtil();
        List list = new ArrayList();
        List ovcList =getOvcHivStatusUpdates(additionalQueryCriteria);
        List caregiverList =getCaregiverHivStatusUpdates(additionalQueryCriteria);
        if(ovcList !=null)
        list.addAll(ovcList);
        if(caregiverList !=null)
        list.addAll(caregiverList);
        //System.err.println("Hiv status list size is "+list.size());
         return list;
    }
    public List getOvcHivStatusUpdates(String additionalQueryCriteria) throws Exception
    {
        DaoUtil util=new DaoUtil();
        List list = new ArrayList();
        List ovcList = new ArrayList();
        try 
        {
            //System.err.println("additionalQueryCriteria in hsudao.getOvcHivStatusUpdates is "+additionalQueryCriteria);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(util.getHouseholdQueryPart()+"Ovc ovc, HivStatusUpdate hsu where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=hsu.clientId "+additionalQueryCriteria).list();
            tx.commit();
            closeSession(session);
        }
         catch (HibernateException he)
         {
             closeSession(session);
             throw new Exception(he);
         }
        if(list !=null)
        {
            for(Object obj:list)
            {
                Object[] objArray=(Object[])obj;
                ovcList.add((HivStatusUpdate)objArray[2]);
            }
        }
         return ovcList;
    }
    public List getCaregiverHivStatusUpdates(String additionalQueryCriteria) throws Exception
    {
        DaoUtil util=new DaoUtil();
        List list = new ArrayList();
        List caregiverList = new ArrayList();
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(util.getHouseholdQueryPart()+"Caregiver cgiver, HivStatusUpdate hsu where hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=hsu.clientId "+additionalQueryCriteria).list();
            tx.commit();
            closeSession(session);
        }
         catch (HibernateException he)
         {
             closeSession(session);
             throw new Exception(he);
         }
        if(list !=null)
        {
            for(Object obj:list)
            {
                Object[] objArray=(Object[])obj;
                caregiverList.add((HivStatusUpdate)objArray[2]);
            }
        }
         return caregiverList;
    }
    public String getUniqueRecordId()
    {
        String uniqueId=appUtil.generateUniqueId();
        try
        {
            if(getHivStatusUpdate(uniqueId) !=null)
            getUniqueRecordId();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return uniqueId;
    }
    public void updateHivTreatmentStatus(List hivOnTreatmentList) throws Exception
    {
        try
        {
            if(hivOnTreatmentList !=null && !hivOnTreatmentList.isEmpty())
            {
                int count=0;
                for(int i=0; i<hivOnTreatmentList.size(); i++)
                {
                    if(hivOnTreatmentList.get(i) !=null)
                    {
                        HivStatusUpdate hsu=(HivStatusUpdate)hivOnTreatmentList.get(i);
                        HivStatusUpdate activeHsu=getActiveHivStatusUpdatesByClientId(hsu.getClientId());
                        if(activeHsu !=null && activeHsu.getHivStatus().equalsIgnoreCase(NomisConstant.HIV_POSITIVE))
                        {
                            activeHsu.setClientEnrolledInCare(hsu.getClientEnrolledInCare());
                            activeHsu.setEnrolledOnART(hsu.getEnrolledOnART());
                            activeHsu.setOrganizationClientIsReferred(hsu.getOrganizationClientIsReferred());
                            updateHivStatusOnly(activeHsu);
                            count++;
                            //System.err.println(count+" clientId with id "+activeHsu.getClientId()+" updated on treatment");
                        }               
                    }
                }
            }
            System.err.println("hivOnTreatmentList.size() is "+hivOnTreatmentList.size());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void setActiveHivStatus(List clientIdList)
    {
        try
        {
            if(clientIdList !=null && !clientIdList.isEmpty())
            {
                String clientId=null;
                for(int i=0; i<clientIdList.size(); i++)
                {
                    if(clientIdList.get(i) !=null)
                    {
                        clientId=clientIdList.get(i).toString();
                        setActiveHivStatus(clientId);
                        //System.err.println("clientId in setActiveHivStatus(List clientIdList) is "+clientId);
                    }
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void setActiveHivStatus(String clientId)
    {
        try
        {
            List list=getAllHivStatusUpdatesPerClient(clientId);
            if(list !=null && !list.isEmpty())
            {
                HivStatusUpdate hsu=null;
                for(int i=0; i<list.size(); i++)
                {
                    hsu=(HivStatusUpdate)list.get(i);
                    if(i==0)
                    hsu.setRecordStatus("active");
                    else
                    hsu.setRecordStatus("passive");
                    updateHivStatusUpdateRecords(hsu);
                    //System.err.println("clientId in setActiveHivStatus(String clientId) is "+clientId);
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void saveHivStatusUpdate(HivStatusUpdate hsu) throws Exception
    {
        if(hsu !=null && hsu.getPointOfUpdate() !=null)
        {
            //System.err.println("HIV Status in service is "+hsu.getHivStatus()+" updated at "+hsu.getPointOfUpdate());
            if(hsu.getHivStatus() !=null && !hsu.getHivStatus().equalsIgnoreCase("select"))
            {
                //Check for the validity of the current date and last modified date
                //if(appUtil.isValidDate(hsu.getDateOfCurrentStatus()) && appUtil.isValidDate(hsu.getDateModified()))
                //{         
                    //Check if this is supposed to be an update
                    HivStatusUpdate duplicateHsu=getHivStatusUpdatesByClientIdAndDateOfStatus(hsu.getClientId(),hsu.getDateOfCurrentStatus());
                    if(duplicateHsu==null)
                    {
                        //if you get here, then it is a new hiv record, save it.
                        
                        if(isHivStatusSavable(hsu))
                        {
                            if(hsu.getPointOfUpdate() !=null)
                            {
                                HivStatusUpdate activehsu=null;
                                if(hsu.getPointOfUpdate().equalsIgnoreCase(NomisConstant.HHE_POINTOFUPDATE) || hsu.getPointOfUpdate().equalsIgnoreCase(NomisConstant.ENROLLMENT_POINTOFUPDATE) || hsu.getPointOfUpdate().equalsIgnoreCase(NomisConstant.CAREGIVER_ENROLL_POINTOFUPDATE))
                                {
                                    activehsu=getActiveHivStatusUpdatesByClientId(hsu.getClientId());
                                    if(activehsu !=null)
                                    {
                                        if(activehsu.getPointOfUpdate().equalsIgnoreCase(hsu.getPointOfUpdate()))
                                        updateHivStatusUpdate(hsu);
                                        else
                                        return;
                                    }
                                }
                                if(hsu.getRecordId()==null)
                                hsu.setRecordId(getUniqueRecordId());
                                hsu=processHivStatusInformation(hsu);
                                hsu=populateOtherHSUProperties(hsu);
                                session = HibernateUtil.getSession();
                                tx=session.beginTransaction();
                                session.save(hsu);
                                tx.commit();
                                closeSession(session); 
                                setActiveHivStatus(hsu.getClientId());
                                //System.err.println("HIV status saved "+hsu.getHivStatus());
                            }
                        }
                    }
                    else
                    {
                        //this is an update, send it to the update method
                        //System.err.println("duplicateHsu in save is null "+hsu.getHivStatus());
                        hsu.setRecordId(duplicateHsu.getRecordId());
                        updateHivStatusUpdate(hsu);
                    }
                //}
            }
        }
    }
    public void updateHivStatusOnly(HivStatusUpdate hsu) throws Exception
    {
        try
        {
            session = HibernateUtil.getSession();
            tx=session.beginTransaction();
            hsu=populateOtherHSUProperties(hsu);
            session.update(hsu);
            tx.commit();
            closeSession(session); 
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
    }
    /*public void updateActiveHivStatusUpdate(HivStatusUpdate hsu) throws Exception
    {
        if(hsu !=null)
        {
            if(hsu.getHivStatus() !=null && !hsu.getHivStatus().equalsIgnoreCase("select"))
            {
                setActiveHivStatus(hsu.getClientId());
                HivStatusUpdate duplicateHsu=getActiveHivStatusUpdatesByClientId(hsu.getClientId());//getHivStatusUpdatesByClientIdAndDateOfStatus(hsu.getClientId(),hsu.getDateOfCurrentStatus());
                if(duplicateHsu !=null)
                {
                    hsu=processHivStatusInformation(hsu);
                    hsu.setRecordId(duplicateHsu.getRecordId());
                    updateHivStatusOnly(hsu);
                }
                setActiveHivStatus(hsu.getClientId());
            }
        }
    }*/
    public void updateActiveHivStatusUpdate(HivStatusUpdate hsu) throws Exception
    {
        if(hsu !=null)
        {
            if(hsu.getHivStatus() !=null && !hsu.getHivStatus().equalsIgnoreCase("select"))
            {
                setActiveHivStatus(hsu.getClientId());
                HivStatusUpdate duplicateHsu=getActiveHivStatusUpdatesByClientId(hsu.getClientId());//getHivStatusUpdatesByClientIdAndDateOfStatus(hsu.getClientId(),hsu.getDateOfCurrentStatus());
                if(duplicateHsu !=null)
                {
                    if(hsu.getPointOfUpdate().equalsIgnoreCase(NomisConstant.HHE_POINTOFUPDATE) || hsu.getPointOfUpdate().equalsIgnoreCase(NomisConstant.ENROLLMENT_POINTOFUPDATE) || hsu.getPointOfUpdate().equalsIgnoreCase(NomisConstant.CAREGIVER_ENROLL_POINTOFUPDATE))
                    {
                        if(!duplicateHsu.getPointOfUpdate().equalsIgnoreCase(hsu.getPointOfUpdate()))
                        return;
                    }
                    hsu=processHivStatusInformation(hsu);
                    hsu.setRecordId(duplicateHsu.getRecordId());
                    updateHivStatusOnly(hsu);
                    setActiveHivStatus(hsu.getClientId());
                }
                
            }
        }
    }
    public void updateHivStatusUpdate(HivStatusUpdate hsu) throws Exception
    {
        if(hsu !=null)
        {
            if(hsu.getHivStatus() !=null && !hsu.getHivStatus().equalsIgnoreCase("select"))
            {
                //if(appUtil.isValidDate(hsu.getDateOfCurrentStatus()) && appUtil.isValidDate(hsu.getDateModified()))
                //{
                    HivStatusUpdate duplicateHsu=getHivStatusUpdatesByClientIdAndDateOfStatus(hsu.getClientId(),hsu.getDateOfCurrentStatus());
                    if(duplicateHsu !=null)
                    {
                        //System.err.println("hsu.getHivStatus() in update 1 is "+hsu.getHivStatus());
                        hsu=processHivStatusInformation(hsu);
                        hsu.setRecordId(duplicateHsu.getRecordId());
                        updateHivStatusOnly(hsu);
                        
                    }
                    setActiveHivStatus(hsu.getClientId());
                //}
            }
        }
    }
    private void updateHivStatusUpdateRecordsWithChangedId(HivStatusUpdate hsu) throws Exception
    {
        if(hsu !=null && (getHivStatusUpdate(hsu.getRecordId()) !=null))
        {
            if(hsu.getHivStatus() !=null && !hsu.getHivStatus().equalsIgnoreCase("select"))
            { 
                hsu=processHivStatusInformation(hsu);
                session = HibernateUtil.getSession();
                tx=session.beginTransaction();
                hsu=populateOtherHSUProperties(hsu);
                session.update(hsu);
                tx.commit();
                closeSession(session);           
            }
        }
    }
    private void updateHivStatusUpdateRecords(HivStatusUpdate hsu) throws Exception
    {
        //System.err.println("Inside updateHivStatusUpdateRecords(HivStatusUpdate hsu)");
        if(hsu !=null && (getHivStatusUpdate(hsu.getRecordId()) !=null))
        {
            if(hsu.getHivStatus() !=null && !hsu.getHivStatus().equalsIgnoreCase("select"))
            {
                HivStatusUpdate duplicateHsu=getHivStatusUpdatesByClientIdAndDateOfStatus(hsu.getClientId(),hsu.getDateOfCurrentStatus());
                if(duplicateHsu !=null)
                {
                    hsu.setRecordId(duplicateHsu.getRecordId());
                    hsu=processHivStatusInformation(hsu);
                    hsu=populateOtherHSUProperties(hsu);
                    session = HibernateUtil.getSession();
                    tx=session.beginTransaction();
                    session.update(hsu);
                    tx.commit();
                    closeSession(session);
                }
            }
        }
    }
    public void deleteHivStatusUpdate(HivStatusUpdate hsu) throws Exception
    {
        try
        {
            session = HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.delete(hsu);
            tx.commit();
            closeSession(session);
            util.saveDeletedRecord(hsu.getClientId(),null, "hivstatusupdate", hsu.getDateModified());
            setActiveHivStatus(hsu.getClientId());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void deleteAllHivStatusUpdatesPerClient(String clientId) throws Exception
    {
        List list=getAllHivStatusUpdatesPerClient(clientId);
        if(list !=null)
        {
            for(int i=0; i<list.size(); i++)
            {
                HivStatusUpdate hsu=(HivStatusUpdate)list.get(i);
                session = HibernateUtil.getSession();
                tx=session.beginTransaction();
                session.delete(hsu);
                tx.commit();
                closeSession(session);
            }
        }
    }
    public void changeClientId(String oldClientId,String newClientId) throws Exception
    {
        AppUtility appUtil=new AppUtility();
        List list=getAllHivStatusUpdatesPerClient(oldClientId);
        if(list !=null)
        {
            for(int i=0; i<list.size(); i++)
            {
                HivStatusUpdate hsu=(HivStatusUpdate)list.get(i);
                hsu.setClientId(newClientId);
                hsu.setDateModified(appUtil.getCurrentDate());
                updateHivStatusOnly(hsu);   
            }
        }
    }
    private HivStatusUpdate processHivStatusInformation(HivStatusUpdate hsu) throws Exception
    {
        if(hsu !=null)
        {
            if(hsu.getHivStatus().indexOf("positive") !=-1)
            hsu.setHivStatus(NomisConstant.HIV_POSITIVE);
            else if(hsu.getHivStatus().indexOf("negative") !=-1)
            hsu.setHivStatus(NomisConstant.HIV_NEGATIVE);
            else if(hsu.getHivStatus().indexOf("unknown") !=-1)
                    hsu.setHivStatus(NomisConstant.HIV_UNKNOWN);
            if(hsu.getHivStatus() !=null && hsu.getHivStatus().indexOf(NomisConstant.HIV_POSITIVE) ==-1)
            {
                hsu.setClientEnrolledInCare("No");
                hsu.setOrganizationClientIsReferred(null);
            }
            if(hsu.getDateEnrolledOnTreatment()==null)
            hsu.setDateEnrolledOnTreatment(DateManager.getDateInstance("1900-01-01"));
        }
        return hsu;
    }
    private boolean isHivStatusSavable(HivStatusUpdate hsu) throws Exception
    {
       boolean hivStatusSavable=false;
        if(hsu !=null)
        {
            HivStatusUpdate activeHsu=getActiveHivStatusUpdatesByClientId(hsu.getClientId());
            if(activeHsu ==null)
            hivStatusSavable=true;
            else if(hsu.getHivStatus() !=null && hsu.getHivStatus().equalsIgnoreCase(NomisConstant.HIV_POSITIVE))
            hivStatusSavable=true;    
            else
            {
                if(DateManager.compareDates(activeHsu.getDateOfCurrentStatus(), hsu.getDateOfCurrentStatus()))
                {
                    //This status was gotten after the last one, so check to be sure it is ascending ( i.e unknown -> negative -> positive)
                    if(HivStatus.getStatusWeight(hsu.getHivStatus()) >= HivStatus.getStatusWeight(activeHsu.getHivStatus()))
                    hivStatusSavable=true;
                    //This is likely a backlog data entry, so check to be sure it is descending ( i.e positive -> negative -> unknown)
                    else if(HivStatus.getStatusWeight(hsu.getHivStatus()) <= HivStatus.getStatusWeight(activeHsu.getHivStatus()))
                    hivStatusSavable=true;
                }
            }
        }
        return hivStatusSavable;
    }
    private boolean isHivStatusUpdatable(HivStatusUpdate hsu) throws Exception
    {
       boolean hivStatusUpdatable=false;
        if(hsu !=null)
        {
            HivStatusUpdate existingHsu=getHivStatusUpdatesByClientIdAndDateOfStatus(hsu.getClientId(),hsu.getDateOfCurrentStatus());
            if(existingHsu !=null)
            {
                    hivStatusUpdatable=true; 
            }
        }
        return hivStatusUpdatable;
    }
    public HivStatusUpdate populateOtherHSUProperties(HivStatusUpdate hsu)
    {
        if(hsu !=null)
        {
           if(hsu.getLastHivTrackingDate()==null || hsu.getLastHivTrackingDate().equalsIgnoreCase("1900-01-01"))
           hsu.setLastHivTrackingDate(hsu.getDateOfCurrentStatus());
           if(hsu.getRecordedBy()==null)
           hsu.setRecordedBy(NomisConstant.AUTO_ACTION); 
        }
        return hsu;
    }
    public void closeSession(Session session)
    {
        if(session !=null && session.isOpen())
        {
            session.close();
        }
    }
}
