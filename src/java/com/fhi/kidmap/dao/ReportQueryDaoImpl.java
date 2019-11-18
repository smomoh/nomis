/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.ReportQuery;
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
public class ReportQueryDaoImpl implements ReportQueryDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    AppUtility appUtil=new AppUtility();
    public String generateUniqueId() throws Exception
    {
        String uniqueId=appUtil.generateUniqueId();
        ReportQuery rq=getReportQuery(uniqueId);
        if(rq !=null)
        generateUniqueId();
        return uniqueId;
    }
    public void saveReportQuery(ReportQuery rq) throws Exception
    {
        if(rq.getQueryId()==null)
        rq.setQueryId(generateUniqueId());
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        session.save(rq);
        tx.commit();
        session.close();
    }
    public void updateReportQuery(ReportQuery rq) throws Exception
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        session.update(rq);
        tx.commit();
        session.close();
    }
    public void deleteReportQuery(ReportQuery rq) throws Exception
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        session.delete(rq);
        tx.commit();
        session.close();
    }
    public ReportQuery getReportQuery(String queryId) throws Exception
    {
        ReportQuery rq=null;
        List list = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from ReportQuery rq where rq.queryId=:rqId").setString("rqId", queryId).list();
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
            rq=(ReportQuery)list.get(0);
        }
        return rq;
    }
    public List getReportQueries() throws Exception
    {
        List list = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from ReportQuery rq").list();
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
    public ReportQuery getReportQueriesByQueryName(String queryName) throws Exception
    {
        List list = new ArrayList();
        ReportQuery rq=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from ReportQuery rq where rq.queryName=:qname").setString("qname", queryName).list();
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
            rq=(ReportQuery)list.get(0);
        }
        return rq;
    }
}
