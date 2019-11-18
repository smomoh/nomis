/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.nomis.nomisutils.AppUtility;
import com.nomis.chart.Dashboard;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class DashboardDaoImpl implements DashboardDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    public Dashboard getDashboard(String dashboardId) throws Exception
    {
        Dashboard dboard=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from Dashboard dboard where dboard.dashboardId = :id").setString("id", dashboardId).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                dboard=(Dashboard)list.get(0);
            }
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
        return dboard;
    }
    public List getAllDashboards() throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from Dashboard dboard").list();
            tx.commit();
            closeSession(session);
            
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
        return list;
    }
    public void saveDashboard(Dashboard dboard) throws Exception
    {
        try
        {
            if(dboard !=null && dboard.getDashboardName() !=null)
            {
                //System.err.println("startDate is "+chart.getStartDate()+" endDate is "+chart.getEndDate()+" chart.getDateCreated() is "+chart.getDateCreated()+" chart.getLastModifiedDate() is "+chart.getLastModifiedDate());
                if(dboard.getDashboardId()==null)
                dboard.setDashboardId(getUniqueId());
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.save(dboard);
                tx.commit();
                closeSession(session);
                DashboardItemDao dbidao=new DashboardItemDaoImpl();
                dbidao.saveOrUpdateDashboardItems(dboard.getDashboardId(), dboard.getObjectIds());
            }
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
    }
    public void updateDashboard(Dashboard dboard) throws Exception
    {
        try
        {
            if(dboard !=null && dboard.getDashboardName() !=null)
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.update(dboard);
                tx.commit();
                closeSession(session);
                DashboardItemDao dbidao=new DashboardItemDaoImpl();
                dbidao.saveOrUpdateDashboardItems(dboard.getDashboardId(), dboard.getObjectIds());
            }
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
    }
    public void deleteDashboard(Dashboard dboard) throws Exception
    {
        try
        {
            //System.err.println("startDate is "+chart.getStartDate()+" endDate is "+chart.getEndDate()+" chart.getDateCreated() is "+chart.getDateCreated()+" chart.getLastModifiedDate() is "+chart.getLastModifiedDate());
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.delete(dboard);
                tx.commit();
                closeSession(session);
               DashboardItemDao dbidao=new DashboardItemDaoImpl();
                dbidao.deleteDashboardItemsByDashboard(dboard.getDashboardId());
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
    }
    public void closeSession(Session session)
    {
        if(session !=null && session.isOpen())
        {
            session.close();
        }
    }
    public String getUniqueId()
    {
        AppUtility appUtil=new AppUtility();
        String dashboardId=appUtil.generateUniqueId();
        try
        {
            if(getDashboard(dashboardId) !=null)
            getUniqueId();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return dashboardId;
    }
}
