/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomis.dataexchange.dao;

import com.fhi.kidmap.dao.HibernateUtil;
import com.fhi.nomis.nomisutils.AppUtility;
import com.nomis.dataexchange.business.DhisInstance;
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
public class DhisInstanceDaoImpl implements DhisInstanceDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    public DhisInstance getDhisInstanceByInstanceName(String instanceName) throws Exception
    {
        DhisInstance dhisInstance=null;
        if(instanceName !=null)
        instanceName=instanceName.trim();
        List list = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DhisInstance dhisInstance where TRIM(dhisInstance.instanceName)=:name ").setString("name", instanceName).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                dhisInstance=(DhisInstance)list.get(0);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        
       return dhisInstance;
    }
    public List getAllDhisInstances() throws Exception
    {
        List list = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DhisInstance dhisInstance order by dhisInstance.instanceName ").list();
            tx.commit();
            closeSession(session);
        }
        catch (HibernateException he)
        {
            closeSession(session);
            he.printStackTrace();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }

       return list;
    }
    public DhisInstance getDhisInstanceById(String id) throws Exception
    {
        List list = new ArrayList();
        DhisInstance dhisInstance=null;
            try
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                list = session.createQuery("from DhisInstance dhisInstance where dhisInstance.id =:did").setString("did", id).list();
                tx.commit();
                closeSession(session);
            }
            catch (HibernateException he)
            {
                closeSession(session);
                he.printStackTrace();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
                closeSession(session);
            }
           if(list !=null && !list.isEmpty())
           {
              dhisInstance=(DhisInstance)list.get(0);
           }
           return dhisInstance;
    }
    public void saveDhisInstance(DhisInstance dhisInstance) throws Exception
    {
        if(dhisInstance !=null)
        {
            try
            {
                if(dhisInstance.getId()==null)
                dhisInstance.setId(generateId());
                System.out.println("dhisInstance id is "+dhisInstance.getId()+" and name is "+dhisInstance.getInstanceName());
                if(getDhisInstanceByInstanceName(dhisInstance.getInstanceName()) ==null)
                {
                    session = HibernateUtil.getSession();
                    tx = session.beginTransaction();
                    session.save(dhisInstance);
                    tx.commit();
                    closeSession(session);
                }
            }
            catch(Exception ex)
            {
                throw new Exception(ex);
            }
        }
    }
    public void updateDhisInstance(DhisInstance dhisInstance) throws Exception
    {
        if(dhisInstance !=null && dhisInstance.getId() !=null)
        {
             if(this.getDhisInstanceById(dhisInstance.getId()) !=null)
             {
                 DhisInstance existingInstance=getDhisInstanceByInstanceName(dhisInstance.getInstanceName());
                if(existingInstance ==null)
                {
                    session = HibernateUtil.getSession();
                    tx = session.beginTransaction();
                    session.update(dhisInstance);
                    tx.commit();
                    closeSession(session);
                }
                else if(existingInstance.getId().equals(dhisInstance.getId()))
                {
                    session = HibernateUtil.getSession();
                    tx = session.beginTransaction();
                    session.update(dhisInstance);
                    tx.commit();
                    closeSession(session);
                }
             }
            
        }
    }
    public void deleteDhisInstance(DhisInstance dhisInstance) throws Exception
    {
        if(dhisInstance !=null && dhisInstance.getId() !=null)
        {
             if(this.getDhisInstanceById(dhisInstance.getId()) !=null)
             {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.delete(dhisInstance);
                tx.commit();
                closeSession(session);
             }
            
        }
    }
    public DhisInstance[] getDhisInstanceComboItems()
    {
        List list=null;
        try
        {
            list=getAllDhisInstances();
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        if(list ==null)
        list=new ArrayList();
        DhisInstance[] items=new DhisInstance[list.size()];
        for(int i=0; i<list.size(); i++)
        {
            DhisInstance instance=(DhisInstance)list.get(i);
            items[i]=instance;
        }
        return items;
    }
    public String generateId() throws Exception
    {
        AppUtility appUtil=new AppUtility();
        String id=appUtil.generateUniqueId(11);
        DhisInstance dhisInstance=getDhisInstanceById(id);
        if(dhisInstance!=null)
        generateId();
        return id;
    }
    public void closeSession(Session session)
    {
        if(session !=null && session.isOpen())
        {
            session.close();
        }
    }
}
