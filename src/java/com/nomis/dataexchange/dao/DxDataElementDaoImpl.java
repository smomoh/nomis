/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomis.dataexchange.dao;


import com.fhi.kidmap.dao.HibernateUtil;
import com.nomis.dataexchange.business.DxDataElement;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class DxDataElementDaoImpl implements DxDataElementDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    public void saveOrUpdateDataElement(DxDataElement de) throws Exception
    {
        if(de !=null)
        {
            if(de.getDataElementId() !=null && de.getDataElementName() !=null)
            {
                DxDataElement deById=getDataElement(de.getDataElementId());
                DxDataElement deByName=getDataElementByNameAndDhisInstance(de.getDataElementId(),de.getDhisInstance());
                if(deById ==null && deByName==null)
                {
                   saveDataElement(de); 
                }
                else if(deById !=null)
                {
                    if(deByName !=null)
                    {
                        //check to be sure they have the same id, if not these are two data elements with same name but different ids
                        if(deById.getDataElementId().equalsIgnoreCase(deByName.getDataElementId()))
                        {
                           updateDataElement(de); 
                        }
                    }
                }
            }
        }
    }
    private void saveDataElement(DxDataElement de) throws Exception
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        session.save(de);
        tx.commit();
        closeSession(session);
    }
    private void updateDataElement(DxDataElement de) throws Exception
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        session.update(de);
        tx.commit();
        closeSession(session);
    }
    public void deleteDataElement(DxDataElement de) throws Exception
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        session.delete(de);
        tx.commit();
        closeSession(session);
    }
    public DxDataElement getDataElement(String dataElementId) throws Exception
    {
        DxDataElement de=null;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DxDataElement de where de.dataElementId=:deid ").setString("deid", dataElementId).list();
            tx.commit();
            closeSession(session);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
       if(list !=null && !list.isEmpty())
       {
           de=(DxDataElement)list.get(0);
       }
       return de;
    }
    public DxDataElement getDataElementByName(String dataElementName) throws Exception
    {
        DxDataElement de=null;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DxDataElement de where de.dataElementName=:name ").setString("name", dataElementName).list();
            tx.commit();
            closeSession(session);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
       if(list !=null && !list.isEmpty())
       {
           de=(DxDataElement)list.get(0);
       }
       return de;
    }
    public DxDataElement getDataElementByNameAndDhisInstance(String dataElementName, String dhisInstance) throws Exception
    {
        DxDataElement de=null;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DxDataElement de where de.dataElementName=:name and de.dhisInstance=:inst").setString("name", dataElementName).setString("inst", dhisInstance).list();
            tx.commit();
            closeSession(session);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
       if(list !=null && !list.isEmpty())
       {
           de=(DxDataElement)list.get(0);
       }
       return de;
    }
    public List getAllDataElements() throws Exception
    {
        List list=null;
       try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DxDataElement de order by de.dataElementName").list();
            tx.commit();
            closeSession(session);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
       return list;
    }
    public List getDataElementByDhisInstance(String dhisInstance) throws Exception
    {
        List list=null;
       try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DxDataElement de where de.dhisInstance=:inst ").setString("inst", dhisInstance).list();
            tx.commit();
            closeSession(session);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
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
}
