/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.Lgas;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


/**
 *
 * @author HP USER
 */
public class LgaDaoImpl implements LgaDao {

    private Lgas lga = null;
    Session session;
    Transaction tx;
    SessionFactory sessions;
    
    public Lgas getLgaByStateAndLgaCode(String stateCode,String lgaCode) throws Exception
    {
        Lgas lga=null;
         try 
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from Lgas lga where lga.state_code=:stcode and lga.lga_code =:lgcode").setString("stcode", stateCode).setString("lgcode",lgaCode).list();
            tx.commit();
            session.close();
            if(list !=null && list.size()>0)
            {
                lga=(Lgas)list.get(0);
            }
         } 
         catch (HibernateException he) 
         {
            throw new Exception(he);
         }
        return lga;
    }
    public List getLgaByLgaCode(String lgaCode) throws Exception
    {
        List list = new ArrayList();
         try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

        list = session.createQuery("from Lgas lga where lga.lga_code =:scode").setString("scode",lgaCode).list();

        tx.commit();
        session.close();
        } catch (HibernateException he) {
            throw new Exception(he);
        }
        return list;
    }
    public Lgas getLgaByCode(String lgaCode) throws Exception
    {
        Lgas lga=null;
        List list = new ArrayList();
        list=getLgaByLgaCode(lgaCode);
        if(list !=null && list.size()>0)
        {
            lga=(Lgas)list.get(0);
        }
        return lga;
    }
    public List getLgas() throws Exception {
        List list = new ArrayList();



         try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

        list = session.createQuery("from Lgas lga").list();

        tx.commit();
        session.close();
        } catch (HibernateException he) {
            throw new Exception(he);
        }
        return list;
    }
    public List getAllLgas() throws Exception {
        List list = new ArrayList();

         try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

        list = session.createQuery("from Lgas lg order by lg.lga_name asc").list();

        tx.commit();
        session.close();
        } catch (HibernateException he) {
            throw new Exception(he);
        }
        return list;
    }

    public List getLgaList(String stateId) throws Exception
    {
        List list = new ArrayList();
         try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("from Lgas lga where lga.state_code=:scode order by lga.lga_name asc").setString("scode",stateId).list();

        tx.commit();
        session.close();
        } catch (HibernateException he) {
            throw new Exception(he);
        }
        return list;
    }
   
    public Lgas getLga(String name) throws Exception {

        return lga;
    }

    public void saveLga(Lgas lga) throws Exception
    {
        try
        {
        session = HibernateUtil.getSession();
        tx=session.beginTransaction();
        session.save(lga);
        tx.commit();
        session.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void updateLga(Lgas lga) throws Exception
    {
        try
        {
        session = HibernateUtil.getSession();
        tx=session.beginTransaction();
        session.update(lga);
        tx.commit();
        session.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void deleteLga(Lgas lgas) throws Exception
    {
        try
        {
        session = HibernateUtil.getSession();
        tx=session.beginTransaction();
        session.delete(lgas);
        tx.commit();
        session.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

}
