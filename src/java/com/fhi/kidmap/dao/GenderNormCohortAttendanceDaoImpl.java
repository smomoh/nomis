/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.GenderNormCohortAttendance;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.nomis.nomisutils.NomisConstant;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class GenderNormCohortAttendanceDaoImpl implements GenderNormCohortAttendanceDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    
    AppUtility appUtil=new AppUtility();
    
    public GenderNormCohortAttendance getGenderNormCohortAttendance(int recordId) throws Exception
    {
        GenderNormCohortAttendance gnca=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            String query="from GenderNormCohortAttendance gnca where gnca.recordId=:id";
            System.err.println(query);
            List list = session.createQuery(query).setInteger("id", recordId).list();
            tx.commit();
            session.close();
            if(list !=null && !list.isEmpty())
            {
                gnca=(GenderNormCohortAttendance)list.get(0);
            }
        }
        catch (Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        return gnca;
    }
    public GenderNormCohortAttendance getGenderNormCohortAttendance(String clientId,String dateOfAttendance) throws Exception
    {
        GenderNormCohortAttendance gnca=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            String query="from GenderNormCohortAttendance gnca where gnca.clientId=:cid and gnca.dateOfAttendance=:doa";
            System.err.println(query);
            List list = session.createQuery(query).setString("cid", clientId).setString("doa", dateOfAttendance).list();
            tx.commit();
            session.close();
            if(list !=null && !list.isEmpty())
            {
                gnca=(GenderNormCohortAttendance)list.get(0);
            }
        }
        catch (Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        return gnca;
    }
    public List getListOfGenderNormCohortAttendanceByClientId(String clientId,String reportOrder) throws Exception
    {
        List list=null;
        String orderQuery="order by gnca.dateOfAttendance desc";
        if(reportOrder !=null && reportOrder.equalsIgnoreCase("asc"))
        orderQuery="order by gnca.dateOfAttendance";
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            String query="from GenderNormCohortAttendance gnca where gnca.clientId=:cid "+orderQuery;
            System.err.println(query);
            list = session.createQuery(query).setString("cid", clientId).list();
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
    public List getListOfGenderNormCohortAttendance(String additionalQuery,String startDate,String endDate,String reportOrder) throws Exception
    {
        List list=new ArrayList();
        try
        {
            List ovcList=getListOfOvcGenderNormCohortAttendance(additionalQuery,startDate,endDate,reportOrder);
            List cgiverList=getListOfCaregiverGenderNormCohortAttendance(additionalQuery,startDate,endDate,reportOrder);
            if(ovcList !=null)
            list.addAll(ovcList);
            if(cgiverList !=null)
            list.addAll(cgiverList);
        }
        catch (Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        return list;
    }
    public List getListOfCaregiverGenderNormCohortAttendance(String additionalQuery,String startDate,String endDate,String reportOrder) throws Exception
    {
        DaoUtil util=new DaoUtil();
        String orderQuery=", gnca.dateOfAttendance desc";
        if(reportOrder !=null && reportOrder.equalsIgnoreCase("asc"))
        orderQuery=" ,gnca.dateOfAttendance";
        List mainList=new ArrayList();
        String periodQuery=util.getGenderNormAttendancePeriodCriteria(startDate,endDate);
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            String query=util.getHouseholdCaregiverGenderNormQueryCriteria()+additionalQuery+periodQuery+" order by gnca.clientId"+orderQuery;
            //System.err.println(query);
            List list = session.createQuery(query).list();
            tx.commit();
            session.close();
            if(list !=null && !list.isEmpty())
            {
                for(int i=0; i<list.size(); i++)
                {
                    Object[] obj=(Object[])list.get(i);
                    mainList.add(obj[2]);
                }
            }
        }
        catch (Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        return mainList;
    }
    public List getListOfOvcGenderNormCohortAttendance(String additionalQuery,String startDate,String endDate,String reportOrder) throws Exception
    {
        DaoUtil util=new DaoUtil();
        List mainList=new ArrayList();
        String orderQuery=", gnca.dateOfAttendance desc";
        if(reportOrder !=null && reportOrder.equalsIgnoreCase("asc"))
        orderQuery=" ,gnca.dateOfAttendance";
        String periodQuery=util.getGenderNormAttendancePeriodCriteria(startDate,endDate);
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            String query=util.getHouseholdOvcGenderNormQueryCriteria()+additionalQuery+periodQuery+" order by gnca.clientId"+orderQuery;
            //System.err.println(query);
            List list = session.createQuery(query).list();
            tx.commit();
            session.close();
            if(list !=null && !list.isEmpty())
            {
                for(int i=0; i<list.size(); i++)
                {
                    Object[] obj=(Object[])list.get(i);
                    mainList.add(obj[2]);
                }
            }
        }
        catch (Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        return mainList;
    }
    public List getAllGenderNormCohortAttendance() throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            String query="from GenderNormCohortAttendance gnca order by gnca.clientId";
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
    public void saveGenderNormCohortAttendance(GenderNormCohortAttendance gnca) throws Exception
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        session.save(gnca);
        tx.commit();
        closeSession(session);
        updateSessionScores(gnca.getClientId());
    }
    public void updateGenderNormCohortAttendanceOnly(GenderNormCohortAttendance gnca) throws Exception
    {
        if(gnca !=null)
        {
            GenderNormCohortAttendance existingGnca=this.getGenderNormCohortAttendance(gnca.getClientId(), gnca.getDateOfAttendance());
            if(existingGnca !=null)
            {
                gnca.setRecordId(existingGnca.getRecordId());
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.update(gnca);
                tx.commit();
                closeSession(session);
            }
        }
    }
    public void updateGenderNormCohortAttendance(GenderNormCohortAttendance gnca) throws Exception
    {
        if(gnca !=null)
        {
            GenderNormCohortAttendance existingGnca=this.getGenderNormCohortAttendance(gnca.getClientId(), gnca.getDateOfAttendance());
            if(existingGnca !=null)
            {
                gnca.setRecordId(existingGnca.getRecordId());
                updateGenderNormCohortAttendanceOnly(gnca);
                updateSessionScores(gnca.getClientId());
            }
        }
    }
    public void markedForDelete(GenderNormCohortAttendance gnca) throws Exception
    {
        try
        {
            gnca.setMarkedForDelete(NomisConstant.MARKEDFORDELETE);
            updateGenderNormCohortAttendance(gnca);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void deleteGenderNormCohortAttendance(GenderNormCohortAttendance gnca) throws Exception
    {
        if(gnca !=null)
        {
            GenderNormCohortAttendance existingGnca=this.getGenderNormCohortAttendance(gnca.getClientId(), gnca.getDateOfAttendance());
            if(existingGnca !=null)
            {
                gnca.setRecordId(existingGnca.getRecordId());
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.delete(gnca);
                tx.commit();
                closeSession(session);
                updateSessionScores(gnca.getClientId());
            }
        }
    }
    public void updateSessionScores(String clientId)
    {
        try
        {
            List clientList=getListOfGenderNormCohortAttendanceByClientId(clientId,"asc");
            if(clientList !=null)
            {
                int sessionNumber=0;
                int numberOfSessions=clientList.size();
                GenderNormCohortAttendance gnca=null;
                for(Object obj:clientList)
                {
                   sessionNumber++;
                   gnca=(GenderNormCohortAttendance)obj; 
                   gnca.setSessionNumber(sessionNumber);
                   gnca.setNumberOfSessions(numberOfSessions);
                   updateGenderNormCohortAttendanceOnly(gnca);
                }
                
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
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
