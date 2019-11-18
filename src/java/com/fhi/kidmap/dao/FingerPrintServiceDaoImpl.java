/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.nomis.business.FingerPrintService;

/**
 *
 * @author smomoh
 */
public class FingerPrintServiceDaoImpl implements FingerPrintServiceDao
{
    Session session;
    Transaction tx;
    public void saveFingerPrintService(FingerPrintService fingerPrintService) throws Exception
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.save(fingerPrintService);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            session.close();
        }
    }
    public void updateFingerPrintService(FingerPrintService fingerPrintService) throws Exception
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.update(fingerPrintService);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            session.close();
        }
    }
    public void deleteFingerPrintService(FingerPrintService fingerPrintService) throws Exception
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.delete(fingerPrintService);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            session.close();
        }
    }
    public FingerPrintService getFingerPrintService(String machineId,String serviceDate) throws Exception
    {
        FingerPrintService fingerPrintService=null;

        List list = new ArrayList();
         try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("from FingerPrintService fps where fps.machineId = :mid and fps.servicedate=:d").setString("mid", machineId).setString("d", serviceDate).list();
        tx.commit();
        session.close();
        }
         catch (Exception ex)
         {
             session.close();
         }
         if(list.size()>0)
         {
           fingerPrintService = (FingerPrintService)list.get(0);
         }
        return fingerPrintService;
    }
    public FingerPrintService getFingerPrintService(long uid) throws Exception
    {
        FingerPrintService fingerPrintService=null;
        return fingerPrintService;
    }
}
