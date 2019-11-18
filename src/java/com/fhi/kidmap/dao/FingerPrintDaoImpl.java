/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.nomis.business.FingerPrint;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class FingerPrintDaoImpl implements FingerPrintDao
{
    Session session;
    Transaction tx;

    public void saveFingerPrint(FingerPrint fingerPrint) throws Exception
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.save(fingerPrint);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            session.close();
        }
    }
    public void updateFingerPrint(FingerPrint fingerPrint) throws Exception
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.update(fingerPrint);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
        }
    }
    public void deleteFingerPrint(FingerPrint fingerPrint) throws Exception
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.delete(fingerPrint);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
        }
    }
    public FingerPrint getFingerPrint(String id) throws Exception
    {
        FingerPrint fingerPrint=null;
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            fingerPrint=(FingerPrint)session.get(FingerPrint.class, id);
        }
        catch (Exception ex)
         {
             session.close();
         }
        return fingerPrint;
    }
    public FingerPrint getFingerPrint(long uid) throws Exception
    {
        FingerPrint fingerPrint=null;
        List list = new ArrayList();
         try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("from FingerPrint fp where fp.uid = :id").setLong("id", uid).list();
        tx.commit();
        session.close();
        }
         catch (Exception ex)
         {
             session.close();
         }
         if(list.size()>0)
         {
           fingerPrint = (FingerPrint)list.get(0);
         }
        return fingerPrint;
    }
}
