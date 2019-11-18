/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.OrganizationRecords;
import com.fhi.kidmap.business.ServiceList;
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

public class OrganizationRecordsDaoImpl implements OrganizationRecordsDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;

    public List getOrganizationList() throws Exception
    {
        List list=new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createQuery("from OrganizationRecords").list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
        }
        return list;
    }
    public List getOrganizationRecordsStateList() throws Exception
    {
        List list=new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createQuery("select distinct orgRecords.state from OrganizationRecords orgRecords order by orgRecords.state").list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
        }
        return list;
    }
    public List getOrganizationRecordsLgaList(String stateCode) throws Exception
    {
        List list=new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createQuery("select distinct orgRecords.lga from OrganizationRecords orgRecords where orgRecords.state=:state_code order by orgRecords.lga").setString("state_code", stateCode).list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
        }
        return list;
    }
    public List getOrgUnitsNotInDhisLinkTable() throws Exception
    {
        List list=new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createQuery("from OrganizationRecords orgRecords where orgRecords.orgCode not in (select dhl.orgCode from DhisOrgUnitLink dhl)").list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
        }
        return list;
    }
    public int getNumberOfOrganizations(String orgCriteria) throws Exception
    {
        List list=new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createQuery("select count(distinct orgRecords.orgName)from OrganizationRecords orgRecords "+orgCriteria).list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            session.close();
        }
        if(!list.isEmpty())
        return Integer.parseInt(list.get(0).toString());
        return 0;
    }
    public List getOrganizationListForExport(String orgqueryCriteria) throws Exception
    {
        List list=new ArrayList();
        
        try
        {
           // System.err.println("queryCriteria is "+queryCriteria);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            String query="from OrganizationRecords orgRecords "+orgqueryCriteria+" order by orgRecords.state, orgRecords.lga asc";
            System.err.println("query in getOrganizationListForExport is "+query);
            list=session.createQuery(query).list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
        }
        return list;
        
    }
    public List getStateOrganizationList(String stateCode) throws Exception
    {
        List list=new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createQuery("from OrganizationRecords orgRecords where orgRecords.state=:state_code ").setString("state_code", stateCode).list();
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
    public List getListOfOrganizationsAssignedToLga(String lgaCode) throws Exception
    {
        List list=new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createQuery("from OrganizationRecords orgRecords where orgRecords.lga like '%"+lgaCode+"%' or orgRecords.lga=:lga_code").setString("lga_code", lgaCode).list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
        }
        return list;
    }
    public List getOrganizationList(String lgaCode) throws Exception
    {
        List list=new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createQuery("from OrganizationRecords orgRecords where orgRecords.lga like '%"+lgaCode+"%'").list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
        }
        return list;
    }
    public String getOrganizationName(String orgCode) throws Exception
    {
        String orgName=null;
        List list=new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createQuery("select orgRecords.orgName from OrganizationRecords orgRecords where orgRecords.orgCode = :code").setString("code",orgCode).list();
            tx.commit();
            session.close();
            if(list.size()>0)
            {
                orgName=(String)list.get(0);
            }
        }
        catch(Exception ex)
        {
            session.close();
        }
        return orgName;
    }
    public String getOrganizationCode(String orgName) throws Exception
    {
        String orgCode=null;
        List list=new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createQuery("select orgRecords.orgCode from OrganizationRecords orgRecords where orgRecords.orgName = :name").setString("name",orgName).list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
        }
            if(list.size()>0)
            {
                orgCode=(String)list.get(0);
            }
        return orgCode;
    }
    public String getTypeOfOrganization(String orgCode) throws Exception
    {
        String orgType=null;
        List list=new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createQuery("select orgRecords.orgType from OrganizationRecords orgRecords where orgRecords.orgCode = :code").setString("code",orgCode).list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
        }
            if(list.size()>0)
            {
                orgType=(String)list.get(0);
            }
        return orgType;
    }
    public OrganizationRecords getOrganizationRecord(String orgCode) throws Exception
    {
        OrganizationRecords orgRecords=null;

        List list=new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createQuery("From OrganizationRecords orgRecords where orgRecords.orgCode = :code").setString("code",orgCode).list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
        }
            if(list.size()>0)
            {
               orgRecords=(OrganizationRecords)list.get(0);
            }
        return orgRecords;
    }
    public List getServiceList() throws Exception
    {
       List list=new ArrayList();
       try
       {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createQuery("from ServiceList serviceList").list();
            tx.commit();
            session.close();
       }
       catch(Exception ex)
       {
           session.close();
       }
       return list;
    }
    public void saveServiceList(ServiceList serviceList) throws Exception
    {
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.save(serviceList);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
       {
           session.close();
       }
    }
    public void saveOrgRecords(OrganizationRecords orgRecords) throws Exception
    {
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.save(orgRecords);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
       {
           session.close();
       }
    }
    public void updateOrgRecords(OrganizationRecords orgRecords) throws Exception
    {
     try{
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.update(orgRecords);
                tx.commit();
                session.close();
        }
        catch(Exception ex)
       {
           session.close();
       }
    }
    public void deleteOrgRecords(OrganizationRecords orgRecords) throws Exception
    {
        try {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.delete(orgRecords);
                tx.commit();
                session.close();
        }
        catch(Exception ex)
       {
           session.close();
       }
    }

}
