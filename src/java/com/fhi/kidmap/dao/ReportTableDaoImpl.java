/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.Service1;
import com.fhi.kidmap.report.CsiReport;
import com.fhi.kidmap.report.FollowupSurveyReport;
import com.fhi.kidmap.report.ReferralReport;
import com.fhi.kidmap.report.ServiceReport;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Siaka
 */
public class ReportTableDaoImpl implements ReportTableDao
{
    Session session;
    Transaction tx;
    DaoUtil util=new DaoUtil();
    public void saveCsiReport(CsiReport csiReport) throws Exception
    {
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.save(csiReport);
            tx.commit();
            session.close();
        } 
        catch (HibernateException he) 
        {
            session.close();
            throw new Exception(he);
        }
    }
    public void saveFollowupSurveyReport(FollowupSurveyReport fusReport) throws Exception
    {
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.save(fusReport);
            tx.commit();
            session.close();
        } 
        catch (HibernateException he) 
        {
            session.close();
            throw new Exception(he);
        }
    }
    public void saveServiceReport(ServiceReport serviceReport) throws Exception {
   try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        session.save(serviceReport);
        tx.commit();
        session.close();
        } catch (HibernateException he) 
        {
            session.close();
            throw new Exception(he);
        }
}
    public void saveService1(Service1 service1) throws Exception
    {
   try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        session.save(service1);
        tx.commit();
        session.close();
        }
         catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
}
    public void updateService1(Service1 service1) throws Exception
    {
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        session.update(service1);
        tx.commit();
        session.close();
        }
        catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
   }
    public void saveReferralReport(ReferralReport referralReport) throws Exception
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.save(referralReport);
            tx.commit();
            session.close();
        }
        catch(HibernateException hbe)
        {
            session.close();
            throw new Exception(hbe);
        }
    }
    public List getServicesFromService1()  throws Exception
    {
        List list=new ArrayList();
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("from Service1").list();

        tx.commit();
        session.close();
        }
        catch (HibernateException he) {
            session.close();
            throw new Exception(he);
        }
        return list;
    }
    public void dropReportTable(String tableName) throws Exception
    {
        util.execSqlQuery("drop table "+tableName);  
    }
}
