/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.OrganizationUnitHirachy;
import com.fhi.nomis.nomisutils.AppUtility;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class OrganizationUnitHirachyDaoImpl implements OrganizationUnitHirachyDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    public void saveOrganizationUnitHirachy(OrganizationUnitHirachy ouh) throws Exception
    {
        AppUtility appUtil=new AppUtility();
        if(ouh !=null && appUtil.isString(ouh.getHirachyName()))
        {
            ouh=cleanOrganizationUnitHirachy(ouh);
            ouh.setHirachyId("level"+ouh.getLevel());
            if(getOrganizationUnitHirachy(ouh.getHirachyId())==null && getOrganizationUnitHirachyByName(ouh.getHirachyName()) ==null)
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.save(ouh);
                tx.commit();
                session.close();
            }
        }
    }
    public void updateOrganizationUnitHirachy(OrganizationUnitHirachy ouh) throws Exception
    {
        AppUtility appUtil=new AppUtility();
        if(ouh !=null && appUtil.isString(ouh.getHirachyName()))
        {
            ouh=cleanOrganizationUnitHirachy(ouh);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.update(ouh);
            tx.commit();
            session.close(); 
        }
    }
    public void deleteOrganizationUnitHirachy(OrganizationUnitHirachy ouh) throws Exception
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        session.delete(ouh);
        tx.commit();
        session.close();
        reorderOrganizationUnitHirachy();
    }
    public void deleteOrganizationUnitHirachyWithoutReordering(OrganizationUnitHirachy ouh) throws Exception
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        session.delete(ouh);
        tx.commit();
        session.close();
    }
    public List getAllOrganizationUnitHirachy() throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from OrganizationUnitHirachy ouh order by ouh.level").list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            closeSession();
            ex.printStackTrace();
        }
        return list;
    }
    public OrganizationUnitHirachy getOrganizationUnitHirachy(String hirachyId) throws Exception
    {
        OrganizationUnitHirachy ouh=null;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from OrganizationUnitHirachy ouh where ouh.hirachyId=:id").setString("id", hirachyId).list();
            tx.commit();
            session.close();
            if(list !=null && !list.isEmpty())
           {
               ouh=(OrganizationUnitHirachy)list.get(0);
           }
        }
        catch(Exception ex)
        {
            closeSession();
            ex.printStackTrace();
        }
        return ouh;
    }
    public OrganizationUnitHirachy getOrganizationUnitHirachyByName(String hirachyName) throws Exception
    {
        OrganizationUnitHirachy ouh=null;
        List list=null;
        try
        {
            if(hirachyName !=null)
            hirachyName=hirachyName.trim();
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from OrganizationUnitHirachy ouh where TRIM(ouh.hirachyName)=:name").setString("name", hirachyName).list();
            tx.commit();
            session.close();
           if(list !=null && !list.isEmpty())
           {
               ouh=(OrganizationUnitHirachy)list.get(0);
           }
        }
        catch(Exception ex)
        {
            closeSession();
            ex.printStackTrace();
        }
        return ouh;
    }
    private OrganizationUnitHirachy cleanOrganizationUnitHirachy(OrganizationUnitHirachy ouh)
    {
        AppUtility appUtil=new AppUtility();
        if(ouh !=null && appUtil.isString(ouh.getHirachyName()))
        {
            String ouhName=ouh.getHirachyName().trim();
            ouh.setHirachyName(ouhName);
        }
        return ouh;
    }
    private void reorderOrganizationUnitHirachy()
    {
        try
        {
            List list=getAllOrganizationUnitHirachy();
            for(int i=0; i<list.size(); i++)
            {
                int level=(i+1);
                String hirachyId="level"+level;
                OrganizationUnitHirachy ouh=(OrganizationUnitHirachy)list.get(i);
                if(ouh !=null)
                {
                    deleteOrganizationUnitHirachyWithoutReordering(ouh);
                    ouh.setHirachyId(hirachyId);
                    ouh.setLevel(level);
                    saveOrganizationUnitHirachy(ouh);
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    private void closeSession()
    {
        try
        {
            if(session!=null && session.isOpen())
            session.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
