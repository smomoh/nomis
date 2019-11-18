/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomis.dataexchange.dao;

import com.fhi.kidmap.dao.HibernateUtil;
import com.fhi.nomis.nomisutils.AppUtility;
import com.nomis.dataexchange.business.DataExchangeOrganizationUnit;
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
public class DxOrganizationUnitDaoImpl implements DxOrganizationUnitDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    public void saveOrganizationUnit(DataExchangeOrganizationUnit ou) throws Exception
    {
        if(ou !=null)
        {
            try
            {
                System.out.println("ou is "+ou.getOrgunitId()+": "+ou.getOrgunitName()+": "+ou.getInstanceName()+": "+ou.getParentId()+": "+ou.getLastModifiedDate()+": "+ou.getLevel());
                if(ou.getOrgunitId() !=null && getOrganizationUnit(ou.getOrgunitId()) !=null)
                updateOrganizationUnit(ou);
                else
                {
                    DataExchangeOrganizationUnit ou2=getOrganizationUnitByName(ou.getOrgunitName());
                    if(ou2==null)
                    {
                        if(ou.getOrgunitId()==null)
                        ou.setOrgunitId(generateId());
                        session = HibernateUtil.getSession();
                        tx = session.beginTransaction();
                        session.save(ou);
                        tx.commit();
                        closeSession(session);  
                    }
                }
            }
            catch(Exception ex)
            {
                throw new Exception(ex);
            }
        }
    }
    public void updateOrganizationUnit(DataExchangeOrganizationUnit ou) throws Exception
    {
        if(ou !=null)
        {
            try
            {
                if(ou.getOrgunitId() !=null)
                {
                    DataExchangeOrganizationUnit ou2=getOrganizationUnitByName(ou.getOrgunitName());
                    if(ou2==null || (ou.getOrgunitId().equalsIgnoreCase(ou2.getOrgunitId())))
                    {
                        session = HibernateUtil.getSession();
                        tx = session.beginTransaction();
                        session.update(ou);
                        tx.commit();
                        closeSession(session);
                    }
                }
            }
            catch(Exception ex)
            {
                throw new Exception(ex);
            }
        }
    }
    public void deleteOrganizationUnit(DataExchangeOrganizationUnit ou) throws Exception
    {
        if(ou !=null)
        {
            try
            {
                if(ou.getOrgunitId() !=null)
                {
                    session = HibernateUtil.getSession();
                    tx = session.beginTransaction();
                    session.delete(ou);
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
    public String[] getOrganizationUnitLevels(String dhisInstance) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct ou.level from DataExchangeOrganizationUnit ou where ou.instanceName=:dinst").setString("dinst", dhisInstance).list();
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
        if(list ==null)
        list=new ArrayList();
        String[] items=new String[list.size()];
        for(int i=0; i<list.size(); i++)
        {
            items[i]=list.get(i).toString();
        }
        return items;
    }
    
    public DataExchangeOrganizationUnit getOrganizationUnit(String orgunitId) throws Exception
    {
        DataExchangeOrganizationUnit ou=null;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DataExchangeOrganizationUnit ou where ou.orgunitId=:ouid ").setString("ouid", orgunitId).list();
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
        return ou;
    }
    public DataExchangeOrganizationUnit getOrganizationUnitByName(String orgunitName) throws Exception
    {
        DataExchangeOrganizationUnit ou=null;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DataExchangeOrganizationUnit ou where ou.orgunitName=:ouid ").setString("ouid", orgunitName).list();
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
            ou=(DataExchangeOrganizationUnit)list.get(0);
        }
        return ou;
    }
    public List getOrganizationUnits(String dhisInstance) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DataExchangeOrganizationUnit ou where ou.instanceName=:dinst ").setString("dinst", dhisInstance).list();
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
    public List getOrganizationUnits(String dhisInstance, int level) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DataExchangeOrganizationUnit ou where ou.instanceName=:dinst and ou.level=:lv").setString("dinst", dhisInstance).setInteger("lv", level).list();
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
    public DataExchangeOrganizationUnit[] getOrganizationUnitsNotMatched(String dhisInstance) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DataExchangeOrganizationUnit ou where ou.instanceName=:dinst").setString("dinst", dhisInstance).list();
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
        if(list ==null)
        list=new ArrayList();
        DataExchangeOrganizationUnit[] items=new DataExchangeOrganizationUnit[list.size()];
        for(int i=0; i<list.size(); i++)
        {
            DataExchangeOrganizationUnit instance=(DataExchangeOrganizationUnit)list.get(i);
            items[i]=instance;
        }
        return items;
    }
    public List getOrganizationUnitsByParentId(String parentId) throws Exception
    {
       List list=null;
       try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DataExchangeOrganizationUnit ou where ou.parentId=:pid ").setString("pid", parentId).list();
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
    public String generateId() throws Exception
    {
        AppUtility appUtil=new AppUtility();
        String id=appUtil.generateUniqueId(11);
        DataExchangeOrganizationUnit ou=getOrganizationUnit(id);
        if(ou!=null)
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
