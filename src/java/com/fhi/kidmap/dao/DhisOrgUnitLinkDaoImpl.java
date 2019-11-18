/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.DhisOrgUnitLink;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
/**
 *
 * @author smomoh
 */
public class DhisOrgUnitLinkDaoImpl implements DhisOrgUnitLinkDao
{
    private Session session=null;
    private Transaction tx=null;

    public DhisOrgUnitLink getDhisOrgUnitLink(String orgCode) throws Exception
    {
        List list=new ArrayList();
        DhisOrgUnitLink dhisOrgUnitLink=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createQuery("from DhisOrgUnitLink dhl where dhl.orgCode =:code").setString("code", orgCode).list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
        }
        if(list !=null && !list.isEmpty())
        dhisOrgUnitLink=(DhisOrgUnitLink)list.get(0);
        return dhisOrgUnitLink;
    }
    public List getAllDhisOrgUnitLinks() throws Exception
    {
        List list=new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createQuery("from DhisOrgUnitLinks").list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
        }
        return list;
    }
    public void saveDhisOrgUnitLink(DhisOrgUnitLink dhisLink) throws Exception
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.save(dhisLink);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
        }
    }
    public void updateDhisOrgUnitLink(DhisOrgUnitLink dhisLink) throws Exception
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.update(dhisLink);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
        }
    }
    public void deleteDhisOrgUnitLink(DhisOrgUnitLink dhisLink) throws Exception
    {
        try
        {
            dhisLink=getDhisOrgUnitLink(dhisLink.getOrgCode());
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            
            if(dhisLink !=null)
            {
                session.delete(dhisLink);
                tx.commit();
            }
            session.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            //session.close();
        }
    }
}
