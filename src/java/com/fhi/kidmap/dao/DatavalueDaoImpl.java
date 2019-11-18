/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.nomis.business.Datavalue;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class DatavalueDaoImpl implements DatavalueDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    public void saveDatavalue(Datavalue dv) throws Exception
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.save(dv);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
    }
    public void updateDatavalue(Datavalue dv) throws Exception
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.update(dv);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
    }
    public void deleteDatavalue(Datavalue dv) throws Exception
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.delete(dv);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
    }
    public List getDatavalueList() throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from Datavalue dv").list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        return list;
    }
    public List getDatavalues(String startDate, String endDate) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from Datavalue dv where dv.startdate between '"+startDate+"' and '"+endDate+"'")
            .list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        return list;
    }
    public Datavalue getDatavalue(String orgUnitId, String indicatorId,String categoryCombinationId,String startDate) throws Exception
    {
        List list=null;
        Datavalue dv=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from Datavalue dv where dv.orgunitid=:ouId and dv.indicatorid=:indId and dv.categoryComboId=:ccId and dv.startdate=:sdate")
            .setString("ouId", orgUnitId).setString("indId", indicatorId).setString("ccId", categoryCombinationId).setString("sdate", startDate).list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        if(list !=null && !list.isEmpty())
        {
            dv=(Datavalue)list.get(0);
        }
        return dv;
    }
}
