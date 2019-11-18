/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.report.HouseholdUniqueServiceReport;
import com.fhi.kidmap.report.ReportUtility;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class HouseholdUniqueServiceReportDaoImpl implements HouseholdUniqueServiceReportDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    DaoUtil util=new DaoUtil();
    ReportUtility rutil=new ReportUtility();
    
    public HouseholdUniqueServiceReport getServiceReportByIdAndPeriod(String caregiverId,String startDate,String endDate) throws Exception
    {
        HouseholdUniqueServiceReport hhs=null;
        List list=new ArrayList();
       try 
       {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from HouseholdUniqueServiceReport hhs where hhs.caregiverId=:id and hhs.serviceDate between '"+startDate+"' and '"+endDate+"'").setString("id", caregiverId).list();

        tx.commit();
        session.close();
       } 
       catch (HibernateException he) 
       {
            session.close();
            throw new Exception(he);
       }
        if(list !=null && !list.isEmpty())
        {
            hhs=(HouseholdUniqueServiceReport)list.get(0);
        }
        return hhs;
    }
    public void saveServiceReport(HouseholdUniqueServiceReport hhs) throws Exception
    {
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.save(hhs);
            tx.commit();
            session.close();
        } 
        catch (Exception ex) 
        {
            session.close();
            ex.printStackTrace();
        }
    }
}
