/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.OrganizationUnitGroup;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class OrganizationUnitGroupDaoImpl implements OrganizationUnitGroupDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    AppUtility appUtil=new AppUtility();

    public void saveOrganizationUnitGroup(OrganizationUnitGroup oug) throws Exception
    {
        try
        {
            if(oug.getOrgUnitGroupId()==null)
            oug.setOrgUnitGroupId(generateUniqueId());
            if(getOrganizationUnitGroup(oug.getOrgUnitGroupId()) ==null && getOrganizationUnitGroupByName(oug.getOrgUnitGroupName())==null)
            {
                session=HibernateUtil.getSession();
                tx=session.beginTransaction();
                session.save(oug);
                tx.commit();
                session.close();
            }
        }
        catch(HibernateException hbe)
        {
            throw new Exception(hbe);
        }
    }
    public void updateOrganizationUnitGroup(OrganizationUnitGroup oug) throws Exception
    {
        try
        {
            if(getOrganizationUnitGroup(oug.getOrgUnitGroupId()) !=null)
            {
                session=HibernateUtil.getSession();
                tx=session.beginTransaction();
                session.update(oug);
                tx.commit();
                session.close();
            }
        }
        catch(HibernateException hbe)
        {
            throw new Exception(hbe);
        }
    }
    public void deleteOrganizationUnitGroup(OrganizationUnitGroup oug) throws Exception
    {
        try
        {
            if(getOrganizationUnitGroup(oug.getOrgUnitGroupId()) !=null)
            {
                session=HibernateUtil.getSession();
                tx=session.beginTransaction();
                session.delete(oug);
                tx.commit();
                session.close();
            }
        }
        catch(HibernateException hbe)
        {
            throw new Exception(hbe);
        }
    }
    public OrganizationUnitGroup getOrganizationUnitGroup(String orgUnitGroupId) throws Exception
    {
        List list = null;
        OrganizationUnitGroup oug=null;
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from OrganizationUnitGroup oug where oug.orgUnitGroupId=:ouId").setString("ouId", orgUnitGroupId).list();
            tx.commit();
            session.close();
        }
        catch (HibernateException he)
        {
            throw new Exception(he);
        }
        if(list !=null && !list.isEmpty())
        {
            oug=(OrganizationUnitGroup)list.get(0);
        }
        return oug;   
    }
    public OrganizationUnitGroup getOrganizationUnitGroupByName(String orgUnitGroupName) throws Exception
    {
        List list = null;
        OrganizationUnitGroup oug=null;
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from OrganizationUnitGroup oug where oug.orgUnitGroupName=:ougName").setString("ougName", orgUnitGroupName).list();
            tx.commit();
            session.close();
        }
        catch (HibernateException he)
        {
            throw new Exception(he);
        }
        if(list !=null && !list.isEmpty())
        {
            oug=(OrganizationUnitGroup)list.get(0);
        }
        return oug;
    }
    public List getAllOrganizationUnitGroups() throws Exception
    {
        List list = null;
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from OrganizationUnitGroup oug order by oug.orgUnitGroupName").list();
            tx.commit();
            session.close();
        }
        catch (HibernateException he)
        {
            throw new Exception(he);
        }
        return list;
    }
    public String generateUniqueId() throws Exception
    {
        String uniqueId=appUtil.generateUniqueId();
        OrganizationUnitGroup oug=getOrganizationUnitGroup(uniqueId);
        if(oug !=null)
        generateUniqueId();
        return uniqueId;
    }
}
