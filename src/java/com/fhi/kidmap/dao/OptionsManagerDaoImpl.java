/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.nomis.nomisutils.OptionsManager;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class OptionsManagerDaoImpl implements OptionsManagerDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    public OptionsManager getOptionsManager(String optionId) throws Exception
    {
        OptionsManager opm=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from OptionsManager opm where opm.optionId =:oid").setString("oid", optionId).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                opm=(OptionsManager)list.get(0);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        return opm;
    }
    public void saveOptionsManager(OptionsManager opm) throws Exception
    {
        try
        {
            if(opm !=null && opm.getOptionId() !=null && opm.getValue() !=null)
            {
                if(getOptionsManager(opm.getOptionId()) ==null)
                {
                    session = HibernateUtil.getSession();
                    tx = session.beginTransaction();
                    session.save(opm);
                    tx.commit();
                    closeSession(session);
                }
                else
                updateOptionsManager(opm);
            }
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
    }
    public void updateOptionsManager(OptionsManager opm) throws Exception
    {
        try
        {
            if(opm !=null && opm.getOptionId() !=null && opm.getValue() !=null)
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.update(opm);
                tx.commit();
                closeSession(session);  
            }
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
    }
    public void deleteOptionsManager(OptionsManager opm) throws Exception
    {
        try
        {
            if(opm !=null && opm.getOptionId() !=null)
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.delete(opm);
                tx.commit();
                closeSession(session); 
            }
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
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
