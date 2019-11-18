/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.Category;
import com.nomis.business.Indicator;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class IndicatorDaoImpl implements IndicatorDao
{
    Session session;
    Transaction tx;
    AppUtility appUtil=new AppUtility();
    public void saveIndicator(Indicator indicator) throws Exception
    {
        try
        {
            indicator.setIndicatorId(generateUniqueId());
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.save(indicator);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void updateIndicator(Indicator indicator) throws Exception
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.update(indicator);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void deleteIndicator(Indicator indicator) throws Exception
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.delete(indicator);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public List getAllIndicators() throws Exception
    {
        List list=new ArrayList();
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            list = session.createQuery("from Indicator indicator").list();
            tx.commit();
            session.close();
        }
        catch(HibernateException hbe)
        {
            session.close();
            throw new Exception(hbe);
        }
        return list;
    }
    public Indicator getIndicator(String indicatorId) throws Exception
    {
        List list=new ArrayList();
        Indicator indicator=null;
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            list = session.createQuery("from Indicator indicator where indicator.indicatorId=:id").setString("id", indicatorId).list();
            tx.commit();
            session.close();
        }
        catch(HibernateException hbe)
        {
            session.close();
            throw new Exception(hbe);
        }
        if(list !=null && !list.isEmpty())
        {
            indicator=(Indicator)list.get(0);
        }
        return indicator;
    }
    public Indicator getIndicatorByName(String indicatorName) throws Exception
    {
        List list=new ArrayList();
        Indicator indicator=null;
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            list = session.createQuery("from Indicator indicator where indicator.indicatorName=:name").setString("name", indicatorName).list();
            tx.commit();
            session.close();
        }
        catch(HibernateException hbe)
        {
            session.close();
            throw new Exception(hbe);
        }
        if(list !=null && !list.isEmpty())
        {
            indicator=(Indicator)list.get(0);
        }
        return indicator;
    }
    public String generateUniqueId() throws Exception
    {
        String uniqueId=appUtil.generateUniqueId();
        Indicator indicator=getIndicator(uniqueId);
        if(indicator !=null)
        generateUniqueId();
        return uniqueId;
    }
}
