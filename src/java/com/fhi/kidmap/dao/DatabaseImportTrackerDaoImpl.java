/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.nomis.nomisutils.DatabaseImportTracker;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class DatabaseImportTrackerDaoImpl implements DatabaseImportTrackerDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    public void saveDatabaseImportTracker(DatabaseImportTracker dit) throws Exception
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.save(dit);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
    }
    public void updateDatabaseImportTracker(DatabaseImportTracker dit) throws Exception
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.update(dit);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
    }
    public void deleteDatabaseImportTracker(DatabaseImportTracker dit) throws Exception
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.delete(dit);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
    }
    public DatabaseImportTracker getDatabaseImportTracker(int recordId) throws Exception
    {
        List list=new ArrayList();
        DatabaseImportTracker dit=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DatabaseImportTracker dit where dit.recordId=:id").setInteger("id", recordId).list();
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
            dit=(DatabaseImportTracker)list.get(0);
        }
        return dit;
    }
    public List getAllDatabaseImportTracker() throws Exception
    {
        List list=new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DatabaseImportTracker dit").list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        return list;
    }
    public List getDistinctUserNamesFromDatabaseImportTracker() throws Exception
    {
        List list=new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct dit.userName from DatabaseImportTracker dit").list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        return list;
    }
    public List getDatabaseImportTracker(String userName) throws Exception
    {
        List list=new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DatabaseImportTracker dit where dit.userName=:name").setString("name", userName).list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        return list;
    }
    public List getDatabaseImportTracker(String userName,boolean responseSent) throws Exception
    {
        List list=new ArrayList();
        String responseSentQuery=" ";
        if(responseSent)
        responseSentQuery=" and dit.responseSent='Yes'";
        else
        responseSentQuery=" and dit.responseSent='No'";
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DatabaseImportTracker dit where dit.userName=:name and dit.fileName is not null "+responseSentQuery).setString("name", userName).list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        return list;
    }
    public List getDistinctImportDateFromDatabaseImportTracker() throws Exception
    {
        List list=new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct dit.dateOfImport from DatabaseImportTracker dit order by dit.dateOfImport").list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        return list;
    }
    public List getDatabaseImportTrackersByPeriod(String startDate,String endDate) throws Exception
    {
        List list=new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DatabaseImportTracker dit where dit.dateOfImport between '"+startDate+"' and '"+endDate+"'").list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        return list;
    }
    public List getDatabaseImportTrackersByUserNameAndPeriod(String userName,String startDate,String endDate) throws Exception
    {
        List list=new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DatabaseImportTracker dit where dit.userName=:name and dit.dateOfImport between '"+startDate+"' and '"+endDate+"'").setString("name", userName).list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        return list;
    }
}
