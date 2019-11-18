/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.Partners;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class PartnersDaoImpl implements PartnersDao
{
    Session session;
    Transaction tx;
    List list;

    public List getAllPartners() throws Exception
    {
        List partnerList=new ArrayList();
        Partners partner=null;
        try
        {
        session=HibernateUtil.getSession();
        tx=session.beginTransaction();
        list=session.createQuery("from Partners").list();
        tx.commit();
        closeSession(session);
        }
        catch(Exception ex)
        {
            closeSession(session);
        }
        for(int i=0; i<list.size(); i++)
        {
            partner=(Partners)list.get(i);
            partnerList.add(partner);
        }
        return partnerList;
    }
    public Partners getPartner(String partnercode) throws Exception
    {
        Partners partner=null;
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            list=session.createQuery("from Partners partner where partner.partnerCode= :pcode").setString("pcode", partnercode).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                partner=(Partners)list.get(0);
            }
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
        return partner;
    }
    public List getPartnerList(String partnercode) throws Exception
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            list=session.createQuery("from Partners partner where partner.partnerCode= :pcode").setString("pcode", partnercode).list();
            tx.commit();
            closeSession(session);
        }
        catch(Exception ex)
        {
            closeSession(session);
        }
        return list;
    }
    public void savePartners(Partners partner) throws Exception
    {
        try
        {
        session=HibernateUtil.getSession();
        tx=session.beginTransaction();
        session.save(partner);
        tx.commit();
        closeSession(session);
        }
        catch(Exception ex)
        {
            closeSession(session);
        }
    }
    public void updatePartners(Partners partner) throws Exception
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.update(partner);
            tx.commit();
            closeSession(session);
        }
        catch(Exception ex)
        {
            closeSession(session);
        }
    }
    public void deletePartners(Partners partner) throws Exception
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.delete(partner);
            tx.commit();
            closeSession(session);
        }
        catch(Exception ex)
        {
            closeSession(session);
        }
    }
    public void closeSession(Session session)
    {
        if(session !=null && session.isOpen())
        {
            session.close();
        }
    }
}
