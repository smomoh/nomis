/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.OvcMonthlyService;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class OvcMthlyServiceDaoImpl implements OvcMthlyServiceDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;

    public void saveOvcService(OvcMonthlyService ovcService) throws Exception
    {
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.save(ovcService);
            tx.commit();
            session.close();
        }
    catch (HibernateException he) 
    {
        session.close();
            throw new Exception(he);
        }
    }
    public void updateOvcService(OvcMonthlyService ovcService) throws Exception
    {
        try
        {
        session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        session.save(ovcService);
        tx.commit();
        session.close();
        } catch (HibernateException he) 
        {
            session.close();
            throw new Exception(he);
        }
    }
    public void deleteOvcService(OvcMonthlyService ovcService) throws Exception
    {
        try
        {
        session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        session.save(ovcService);
        tx.commit();
        session.close();
        } catch (HibernateException he) 
        {
            session.close();
            throw new Exception(he);
        }
    }
}
