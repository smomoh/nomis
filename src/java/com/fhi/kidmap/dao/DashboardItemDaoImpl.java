/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.nomis.nomisutils.AppUtility;
import com.nomis.chart.DashboardItem;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class DashboardItemDaoImpl implements DashboardItemDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    public List getDistinctObjectId(String dashboardId) throws Exception
    {
        String additionalQuery="";
        if(dashboardId !=null && !dashboardId.equalsIgnoreCase("All"))
        {
            additionalQuery=" where dbi.recordId ='"+dashboardId+"'";
        }
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct dbi.objectId from DashboardItem dbi"+additionalQuery).list();
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
    public DashboardItem getDashboardItem(String recordId) throws Exception
    {
        DashboardItem dbi=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from DashboardItem dbi where dbi.recordId = :id").setString("id", recordId).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                dbi=(DashboardItem)list.get(0);
            }
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
        return dbi;
    }
    public List getDashboardItemsByDashboardId(String dashboardId) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DashboardItem dbi where dbi.dashboardId=:id").setString("id", dashboardId).list();
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
    public List getAllDashboardItems() throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DashboardItem dbi").list();
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
    public void saveDashboardItem(DashboardItem dbi) throws Exception
    {
        try
        {
            if(dbi !=null && dbi.getObjectId() !=null)
            {
                if(dbi.getRecordId()==null)
                dbi.setRecordId(getUniqueId());
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.save(dbi);
                tx.commit();
                closeSession(session);
                //System.err.println("Chart with name "+chart.getChartName()+" saved");
            }
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
    }
    public void updateDashboardItem(DashboardItem dbi) throws Exception
    {
        try
        {
            if(dbi !=null && dbi.getRecordId() !=null)
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.update(dbi);
                tx.commit();
                closeSession(session);
                //System.err.println("Chart with name "+chart.getChartName()+" saved");
            }
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
    }
    public void deleteDashboardItem(DashboardItem dbi) throws Exception
    {
        try
        {
            if(dbi !=null && dbi.getRecordId() !=null)
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.delete(dbi);
                tx.commit();
                closeSession(session);
                //System.err.println("Chart with name "+chart.getChartName()+" saved");
            }
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
    }
    public void deleteDashboardItemsByDashboard(String dashboardId) throws Exception
    {
        List dbiList=getDashboardItemsByDashboardId(dashboardId);
        if(dbiList !=null && !dbiList.isEmpty())
        {
            DashboardItem dbi=null;
            for(int j=0; j<dbiList.size(); j++)
            {
                dbi=(DashboardItem)dbiList.get(j);
                this.deleteDashboardItem(dbi);
            }
        }
    }
    public void saveOrUpdateDashboardItems(String dashboardId,String[] itemArray) throws Exception
    {
        System.err.println("dashboardId is "+dashboardId);
        try
        {
            deleteDashboardItemsByDashboard(dashboardId);
            if(itemArray !=null && itemArray.length >0)
            {
                AppUtility appUtil=new AppUtility();
                DashboardItem dbItem=null;
                
                for(int i=0; i<itemArray.length; i++)
                {
                    System.err.println("itemArray[i] is "+itemArray[i]);
                    dbItem=new DashboardItem();
                    dbItem.setObjectId(itemArray[i]);
                    dbItem.setObjectType("chart");
                    dbItem.setDashboardId(dashboardId);
                    dbItem.setDateCreated(appUtil.getCurrentDate());
                    dbItem.setLastModifiedDate(appUtil.getCurrentDate());                
                    saveDashboardItem(dbItem);
                }
            }
        }
        catch(Exception ex)
        {
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
            if(getDashboardItem(dashboardId) !=null)
            getUniqueId();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return dashboardId;
    }
}
