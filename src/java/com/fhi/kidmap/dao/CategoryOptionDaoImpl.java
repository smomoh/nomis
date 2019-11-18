/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.CategoryOption;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class CategoryOptionDaoImpl implements CategoryOptionDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    AppUtility appUtil=new AppUtility();
    public void saveCategoryOption(CategoryOption co) throws Exception
    {
        try
        {
            if(co.getCategoryOptionId()==null)
            co.setCategoryOptionId(generateUniqueId());
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.save(co);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
        }
    }
    public String generateUniqueId() throws Exception
    {
        String uniqueId=appUtil.generateUniqueId();
        CategoryOption co=getCategoryOption(uniqueId);
        if(co !=null)
        generateUniqueId();
        return uniqueId;
    }
    public List getCategoryOptions() throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createQuery("from CategoryOption co order by co.categoryOptionName").list();
            tx.commit();
            session.close();
            
        }
        catch(Exception ex)
        {
            session.close();
        }
        return list;
    }
    public CategoryOption getCategoryOption(String uniqueId) throws Exception
    {
        List list=null;
        CategoryOption co=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createQuery("from CategoryOption co where co.categoryOptionId=:id").setString("id", uniqueId).list();
            tx.commit();
            session.close();
            if(list !=null && !list.isEmpty())
            {
                co=(CategoryOption)list.get(0);
            }
        }
        catch(Exception ex)
        {
            session.close();
        }
        return co;
    }
    public CategoryOption getCategoryOptionByName(String coptionName) throws Exception
    {
        List list=null;
        CategoryOption co=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createQuery("from CategoryOption co where co.categoryOptionName=:name").setString("name", coptionName).list();
            tx.commit();
            session.close();
            if(list !=null && !list.isEmpty())
            {
                co=(CategoryOption)list.get(0);
            }
        }
        catch(Exception ex)
        {
            session.close();
        }
        return co;
    }
    public void updateCategoryOption(CategoryOption co) throws Exception
    {
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.update(co);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
        }
    }
    public void deleteCategoryOption(CategoryOption co) throws Exception
    {
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.delete(co);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
        }
    }
}
