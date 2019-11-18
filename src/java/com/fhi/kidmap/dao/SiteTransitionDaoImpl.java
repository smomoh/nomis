/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.SiteTransition;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class SiteTransitionDaoImpl implements SiteTransitionDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    
    public void saveSiteTransition(SiteTransition st) throws Exception
    {
        try 
        {
            if(st !=null && getSiteTransition(st.getSiteCode())==null)
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.save(st);
                tx.commit();
                session.close();
            }
        } 
        catch (Exception ex) 
        {
            session.close();
            ex.printStackTrace();
        }
    }
    public void updateSiteTransition(SiteTransition st) throws Exception
    {
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.update(st);
            tx.commit();
            session.close();
        } 
        catch (Exception ex) 
        {
            session.close();
            ex.printStackTrace();
        }
    }
    public void deleteSiteTransition(SiteTransition st) throws Exception
    {
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.delete(st);
            tx.commit();
            session.close();
        } 
        catch (Exception ex) 
        {
            session.close();
            ex.printStackTrace();
        }
    }
    public SiteTransition getSiteTransition(String siteCode) throws Exception
    {
        SiteTransition st=null;
       List list=new ArrayList();
       try 
       {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from SiteTransition st where st.siteCode=:sc").setString("sc", siteCode).list();

            tx.commit();
            session.close();
       } 
       catch (Exception ex) 
       {
            session.close();
            ex.printStackTrace();
       }
        if(list !=null && !list.isEmpty())
        {
            st=(SiteTransition)list.get(0);
        }
        return st;
    }
    public List getAllTransitionedSites() throws Exception
    {
        List list=new ArrayList();
       try 
       {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from SiteTransition st").list();

            tx.commit();
            session.close();
       } 
       catch (Exception ex) 
       {
            session.close();
            throw new Exception(ex);
       }
       return list;
    }
}
