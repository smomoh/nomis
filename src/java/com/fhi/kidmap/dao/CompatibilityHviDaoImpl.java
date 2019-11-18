/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.nomis.upgrade.CompatibilityHvi;
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
public class CompatibilityHviDaoImpl implements CompatibilityHviDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    public int getNoOfHouseholdsEnrolled() throws Exception
    {
        List list = null;
        int count=0;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select count(distinct hvi.hhUniqueId) from CompatibilityHvi hvi").list();
        tx.commit();
        session.close();
        }
         catch (Exception ex)
         {
             session.close();
             ex.printStackTrace();
             return -1;
            //throw new Exception(he);
         }
        if(list !=null && !list.isEmpty())
        {
            count=(Integer.parseInt(list.get(0).toString()));
        }
        return count;
    }
    public List getHouseholdVulnerabilityIndexList(String additionalQuery) throws Exception
    {
        List list = new ArrayList();
        List hviList = new ArrayList();
         try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            System.err.println("from CompatibilityHvi hvi where hvi.orgCode is not null "+additionalQuery+" order by hvi.hhUniqueId");
        list = session.createQuery("from CompatibilityHvi hvi where hvi.orgCode is not null "+additionalQuery+" order by hvi.hhUniqueId").list();
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
            hviList.add((CompatibilityHvi)s);
        }
        return hviList;
    }
    public List getDistinctOrgCodes() throws Exception
    {
        List list = new ArrayList();
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("select distinct hvi.orgCode from CompatibilityHvi hvi").list();
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
    public CompatibilityHvi getCompatibleHouseholdVulnerabilityIndex(String uid) throws Exception
    {
        List list = new ArrayList();
        CompatibilityHvi chhvi=null;
         try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

        list = session.createQuery("from CompatibilityHvi chvi where chvi.hhUniqueId =:id ").setString("id", uid).list();

        tx.commit();
        session.close();
        }
        catch (HibernateException he)
        {
            throw new Exception(he);
        }
         if(list !=null && !list.isEmpty())
         {
             chhvi=(CompatibilityHvi)list.get(0);
         }
        return chhvi;
    }
}
