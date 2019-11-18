/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.EligibilityCriteria;
import com.fhi.nomis.nomisutils.NomisConstant;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class EligibilityCriteriaDaoImpl implements EligibilityCriteriaDao
{
    Session session;
    Transaction tx;
    SessionFactory sessionFactory;
    public void saveEligibilityCriteria(EligibilityCriteria eligibilityCriteria) throws Exception
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.save(eligibilityCriteria);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
           session.close();
        }
    }
    public void updateEligibilityCriteria(EligibilityCriteria eligibilityCriteria) throws Exception
    {
        try
        {
        session=HibernateUtil.getSession();
        tx=session.beginTransaction();
        session.update(eligibilityCriteria);
        tx.commit();
        session.close();
        }
        catch(Exception ex)
        {
           session.close();
        }
    }
    public void markedForDelete(EligibilityCriteria eligibilityCriteria) throws Exception
    {
        try
        {
            eligibilityCriteria.setMarkedForDelete(NomisConstant.MARKEDFORDELETE);
            updateEligibilityCriteria(eligibilityCriteria);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void deleteEligibilityCriteria(EligibilityCriteria eligibilityCriteria) throws Exception
    {
        try
        {
        session=HibernateUtil.getSession();
        tx=session.beginTransaction();
        session.delete(eligibilityCriteria);
        tx.commit();
        session.close();
        }
        catch(Exception ex)
        {
           session.close();
        }
    }
    public List getEligibilityCriteria() throws Exception
    {
        List list=new ArrayList();
        List eligibilityList=new ArrayList();
        try
        {
        session=HibernateUtil.getSession();
        tx=session.beginTransaction();
        list=session.createQuery("from EligibilityCriteria").list();
        tx.commit();
        session.close();
        }
        catch(Exception ex)
        {
           session.close();
        }
        for(Object s:list)
        {
            EligibilityCriteria eligibilityCriteria=(EligibilityCriteria)s;
            eligibilityList.add(eligibilityCriteria);
        }
        return eligibilityList;
    }
    public EligibilityCriteria getEligibilityCriteria(String name) throws Exception
    {
        EligibilityCriteria eligibilityCritria=null;
        List list=new ArrayList();
        List eligibilityList=new ArrayList();
        try
        {
        session=HibernateUtil.getSession();
        tx=session.beginTransaction();
        list=session.createQuery("from EligibilityCriteria eligibilityCritria where eligibilityCritria.eligibilityName=:eligname").setString("eligname", name).list();
        tx.commit();
        session.close();
        }
        catch(Exception ex)
        {
           session.close();
        }
        if(list !=null && !list.isEmpty())
        {
            eligibilityCritria=(EligibilityCriteria)list.get(0);
        }
        /*for(Object s:list)
        {
            EligibilityCriteria eligibilityCriteria=(EligibilityCriteria)s;
            eligibilityList.add(eligibilityCriteria);
        }*/
        return eligibilityCritria;
    }
    public EligibilityCriteria getEligibilityCriteria(int id) throws Exception
    {
        EligibilityCriteria eligCriteria=null;
        List list=new ArrayList();
        try
        {
        session=HibernateUtil.getSession();
        tx=session.beginTransaction();
        list=session.createQuery("from EligibilityCriteria eligibilityCriteria where eligibilityCriteria.id=:eligId").setInteger("eligId", id).list();
        tx.commit();
        session.close();
        }
        catch(Exception ex)
        {
           session.close();
        }
        if(!list.isEmpty())
        {
            eligCriteria=(EligibilityCriteria)list.get(0);
        }
        return eligCriteria;
    }
}
