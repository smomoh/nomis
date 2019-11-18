/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.Cbo;
import com.fhi.kidmap.business.Cbos;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


/**
 *
 * @author HP USER
 */
public class CboDaoImpl implements CboDao {

    private Cbo cbo = null;
    Session session;
    Transaction tx;
    SessionFactory sessions;


    public List getCboList(String lgaId) throws Exception
     {
        List list = new ArrayList();
        List cboList = new ArrayList();
         try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from Cbos cbo where cbo.lga_code = :lgaCode").setString("lgaCode",lgaId).list();
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
            Cbos cbo=(Cbos)s;
            cboList.add(cbo);
        }
        return cboList;
    }
    public List getCboFromEnrollment(String stateName,String lgaName) throws Exception
    {
        List list = new ArrayList();
         try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct hhe.orgCode from HouseholdEnrollment hhe where hhe.stateCode like '%"+stateName+"%' and hhe.lgaCode like '%"+lgaName+"%'").list();
           // System.err.println("select distinct ovc.completedbyCbo from Ovc ovc where ovc.state like '%"+stateName+"%' and ovc.lga like '%"+lgaName+"%'");
            tx.commit();
            session.close();
        }
        catch (HibernateException he)
        {
            throw new Exception(he);
        }
        return list;
    }
    public List getCboByState(String statecode) throws Exception
    {
        List list = new ArrayList();
         try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from Cbos cbo where cbo.lga_code = :stateCode").setString("stateCode",statecode).list();
            tx.commit();
            session.close();
        }
        catch (HibernateException he)
        {
            throw new Exception(he);
        }
        return list;
    }
    public List getAllCbos() throws Exception
    {
        List list = new ArrayList();
         try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

        list = session.createQuery("from Cbos").list();

        tx.commit();
        session.close();
        } catch (HibernateException he) {
            throw new Exception(he);
        }
        return list;
    }
    public List getCbos(String cboCode) throws Exception
    {
        List list=new ArrayList();
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            list = session.createQuery("from Cbos cbo where cbo.cbo_code =:cbocode").setString("cbocode",cboCode).list();
            tx.commit();
            session.close();
        }
        catch(HibernateException hbe)
        {
            throw new Exception(hbe);
        }
        return list;
    }
    public List getCboCode(String cboName) throws Exception
    {
        List list=new ArrayList();
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            list = session.createQuery("from Cbos cbo where cbo.cbo_name =:cboname").setString("cboname",cboName).list();
            tx.commit();
            session.close();
        }
        catch(HibernateException hbe)
        {
            throw new Exception(hbe);
        }
        return list;
    }
    public Cbo getCbo(String name) throws Exception {

        return cbo;
    }
    public void saveCbo(Cbos cbo) throws Exception
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.save(cbo);
            tx.commit();
            session.close();
        }
        catch(HibernateException hbe)
        {
            throw new Exception(hbe);
        }

    }
    public void updateCbo(Cbos cbo) throws Exception
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.update(cbo);
            tx.commit();
            session.close();
        }
        catch(HibernateException hbe)
        {
            throw new Exception(hbe);
        }
    }
    public void deleteCbo(Cbos cbo) throws Exception 
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.delete(cbo);
            tx.commit();
            session.close();
        }
        catch(HibernateException hbe)
        {
            throw new Exception(hbe);
        }

    }

}
