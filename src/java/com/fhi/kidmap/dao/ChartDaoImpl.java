/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.nomis.nomisutils.AppUtility;
import com.nomis.chart.Chart;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class ChartDaoImpl implements ChartDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    public void saveChart(Chart chart) throws Exception
    {
        try
        {
            if(chart !=null && chart.getChartName() !=null)
            {
                //System.err.println("startDate is "+chart.getStartDate()+" endDate is "+chart.getEndDate()+" chart.getDateCreated() is "+chart.getDateCreated()+" chart.getLastModifiedDate() is "+chart.getLastModifiedDate());
                if(chart.getChartId()==null)
                chart.setChartId(getUniqueId());
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.save(chart);
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
    public void updateChart(Chart chart) throws Exception
    {
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.update(chart);
            tx.commit();
            closeSession(session);
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
    }
    public void deleteChart(Chart chart) throws Exception
    {
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.delete(chart);
            tx.commit();
            closeSession(session);
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
    }
    public Chart getChart(String chartId) throws Exception
    {
        Chart chart=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from Chart chart where chart.chartId = :id").setString("id", chartId).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                chart=(Chart)list.get(0);
            }
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
        return chart;
    }
    public List getAllCharts() throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from Chart chart").list();
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
        String chartId=appUtil.generateUniqueId();
        try
        {
            if(getChart(chartId) !=null)
            getUniqueId();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return chartId;
    }
}
