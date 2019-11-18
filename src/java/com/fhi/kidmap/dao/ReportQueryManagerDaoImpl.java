/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.nomis.nomisutils.AppUtility;
import com.nomis.business.ReportQueryManager;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author Siaka
 */
public class ReportQueryManagerDaoImpl implements ReportQueryManagerDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    AppUtility appUtil=new AppUtility();
    public void saveReportQueryManager(ReportQueryManager rqm) throws Exception
    {
        if(rqm.getUniqueId()==null)
        rqm.setUniqueId(generateUniqueId());
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        session.save(rqm);
        tx.commit();
        session.close();
    }
    public void updateReportQueryManager(ReportQueryManager rqm) throws Exception
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        session.update(rqm);
        tx.commit();
        session.close();
    }
    public void deleteReportQueryManager(ReportQueryManager rqm) throws Exception
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        session.delete(rqm);
        tx.commit();
        session.close();
    }
    public ReportQueryManager getReportQueryManager(String uniqueId) throws Exception
    {
        ReportQueryManager rqm=null;
        List list = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from ReportQueryManager rqm where rqm.uniqueId=:rqId").setString("rqId", uniqueId).list();
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
            rqm=(ReportQueryManager)list.get(0);
        }
        return rqm;
    }
    public List getAllReportQueryManagers() throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from ReportQueryManager rqm").list();
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
    public List getReportQueryManagers(String type) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from ReportQueryManager rqm where rqm.type=:rqmtype").setString("rqmtype", type).list();
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
    public String generateUniqueId() throws Exception
    {
        String uniqueId=appUtil.generateUniqueId();
        if(getReportQueryManager(uniqueId) !=null)
        generateUniqueId();
        return uniqueId;
    }
}
