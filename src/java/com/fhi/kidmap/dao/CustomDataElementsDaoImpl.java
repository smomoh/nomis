/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.CategoryOption;
import com.fhi.kidmap.business.CustomDataElement;
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
public class CustomDataElementsDaoImpl implements CustomDataElementsDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    AppUtility appUtil=new AppUtility();
    public String generateUniqueId() throws Exception
    {
        String uniqueId=appUtil.generateUniqueId();
        CustomDataElement cde=getCustomDataElement(uniqueId);
        if(cde !=null)
        generateUniqueId();
        return uniqueId;
    }
    public void saveCustomDataElement(CustomDataElement cde) throws Exception
    {
        try
        {
            if(cde.getCustomDeId()==null)
            cde.setCustomDeId(generateUniqueId());
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.save(cde);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
    }
    public void updateCustomDataElement(CustomDataElement cde) throws Exception
    {
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.update(cde);
            tx.commit();
            session.close();
        }
        catch (HibernateException he)
        {
            session.close();
            he.printStackTrace();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
    }
    public void deleteCustomDataElement(CustomDataElement cde) throws Exception
    {
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.delete(cde);
            tx.commit();
            session.close();
        }
        catch (HibernateException he)
        {
            session.close();
            he.printStackTrace();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
    }
    public CustomDataElement getCustomDataElement(String customDataElementId) throws Exception
    {
            List list = null;
            CustomDataElement cde=null;
            try
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                list = session.createQuery("from CustomDataElement cde where cde.customDeId =:cdeId").setString("cdeId", customDataElementId).list();
                tx.commit();
                session.close();
            }
            catch (HibernateException he)
            {
                session.close();
                he.printStackTrace();
            }
            catch(Exception ex)
            {
                session.close();
            }
            if(list !=null && !list.isEmpty())
            {
                cde=(CustomDataElement)list.get(0);
            }
            return cde;
    }
    public CustomDataElement getCustomDataElemenByName(String customDataElementName) throws Exception
    {
            List list = null;
            CustomDataElement cde=null;
            try
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                list = session.createQuery("from CustomDataElement cde where cde.customDeName =:cdeName").setString("cdeName", customDataElementName).list();
                tx.commit();
                session.close();
            }
            catch (HibernateException he)
            {
                session.close();
                he.printStackTrace();
            }
            catch(Exception ex)
            {
                session.close();
            }
            if(list !=null && !list.isEmpty())
            {
                cde=(CustomDataElement)list.get(0);
            }
            return cde;
    }
    public List getCustomDataElementList() throws Exception
    {
            List list = new ArrayList();
            try
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                list = session.createQuery("from CustomDataElement cde order by cde.customDeName").list();
                tx.commit();
                session.close();
            }
            catch (HibernateException he)
            {
                session.close();
                he.printStackTrace();
            }
            catch(Exception ex)
            {
                session.close();
                ex.printStackTrace();
            }
            return list;
    }
}
