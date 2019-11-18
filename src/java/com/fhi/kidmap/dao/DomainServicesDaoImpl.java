/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.DomainServices;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class DomainServicesDaoImpl implements DomainServicesDao
{
    private Session session=null;
    private Transaction tx=null;
    public void saveCbo(DomainServices ds) throws Exception
    {
        try
        {
        session=HibernateUtil.getSession();
        tx=session.beginTransaction();
        session.save(ds);
        tx.commit();
        session.close();
        }
        catch(Exception ex)
        {
            session.close();
        }
    }
    public List getDomainServices() throws Exception
    {
        List list=new ArrayList();
        List listOfDomains=new ArrayList();
        DomainServices ds=null;
        try
        {
        session=HibernateUtil.getSession();
        tx=session.beginTransaction();
        list=session.createQuery("from DomainServices").list();
        tx.commit();
        session.close();
        }
        catch(Exception ex)
        {
            session.close();
        }
        for(Object s:list)
        {
            ds=(DomainServices)s;
            listOfDomains.add(ds);
        }
        return listOfDomains;
    }
}
