/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.Indicators;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class IndicatorsDaoImpl implements IndicatorsDao {
DaoUtil util=new DaoUtil();
    Session session;
    Transaction tx;
    SessionFactory sessions;
    public List getDhisInstances() throws Exception
    {
        List list=new ArrayList();
        String query="select distinct indicator.indicatorSubtype from Indicators indicator";
        list=util.execReportQuery(query);
        return list;
    }
    public List getIndicatorsByDhisInstance(String dhisInstance) throws Exception
    {
        List list=new ArrayList();
        List<Indicators> indicatorList=new ArrayList<Indicators>();
        String query="from Indicators indicator where indicator.indicatorSubtype ='"+dhisInstance+"'";
        list=util.execReportQuery(query);
        for(int i=0; i<list.size(); i++)
        {
            indicatorList.add((Indicators)list.get(i));
        }
        return indicatorList;
    }
    public List getIndicators() throws Exception
    {
        List list=new ArrayList();
        List<Indicators> indicatorList=new ArrayList<Indicators>();
        String query="from Indicators indicator";
        list=util.execReportQuery(query);
        for(int i=0; i<list.size(); i++)
        {
            indicatorList.add((Indicators)list.get(i));
        }
        return indicatorList;
    }
    public int getCountOfIndicators() throws Exception
    {
        int count=0;
        List list=new ArrayList();
        list=util.execReportQuery("select count(distinct indicator.indicatorId) from Indicators indicator");
        if(list.size()>0)
        count=((Long)(list.get(0))).intValue();
        return count;
    }
    public List getIndicators(String indicatorId) throws Exception
    {
        Indicators indicator=null;
        List list=null;
        List indicatorList=new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx=session.beginTransaction();
            list=session.createQuery("from Indicators indicator where indicator.indicatorId=:id").setString("id", indicatorId).list();
            if(list.size()>0)
            indicator=(Indicators)list.get(0);
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        for(int i=0; i<list.size(); i++)
        {
            indicator=(Indicators)list.get(i);
            indicatorList.add(indicator);
        }
        return indicatorList;
    }
    public Indicators getIndicatorByName(String indicatorName,String dhisInstance) throws Exception
    {
        Indicators indicator=null;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx=session.beginTransaction();
            list=session.createQuery("from Indicators indicator where indicator.indicatorName=:name and indicator.indicatorSubtype=:instance").setString("name", indicatorName).setString("instance", dhisInstance).list();
            if(list.size()>0)
            indicator=(Indicators)list.get(0);
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        return indicator;
    }
    public Indicators getIndicator(String dhisId,String dhisInstance) throws Exception
    {
        Indicators indicator=null;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx=session.beginTransaction();
            list=session.createQuery("from Indicators indicator where indicator.indicatorId=:id and indicator.indicatorSubtype=:instance").setString("id", dhisId).setString("instance", dhisInstance).list();
            if(list.size()>0)
            indicator=(Indicators)list.get(0);
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        return indicator;
    }
    public Indicators getIndicator(int uid) throws Exception
    {
        Indicators indicator=null;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx=session.beginTransaction();
            list=session.createQuery("from Indicators indicator where indicator.id=:recordId").setInteger("recordId", uid).list();
            if(list.size()>0)
            indicator=(Indicators)list.get(0);
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        return indicator;
    }
    public void saveIndicators(Indicators indicator) throws Exception
    {
        try
        {
            session = HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.save(indicator);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
    }
    public void updateIndicators(Indicators indicator) throws Exception
    {
        session = HibernateUtil.getSession();
        tx=session.beginTransaction();
        session.update(indicator);
        tx.commit();
        session.close();
    }
    public void deleteIndicators(Indicators indicator) throws Exception
    {
        session = HibernateUtil.getSession();
        tx=session.beginTransaction();
        session.delete(indicator);
        tx.commit();
        session.close();
    }
}
