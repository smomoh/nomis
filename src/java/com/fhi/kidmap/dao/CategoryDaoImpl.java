/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.Category;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class CategoryDaoImpl implements CategoryDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    AppUtility appUtil=new AppUtility();

    public void saveCategory(Category cat) throws Exception
    {
        try
        {
            String categoryId=cat.getCategoryId();
            if(categoryId==null)
            categoryId=generateUniqueId();
            cat.setCategoryId(categoryId);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.save(cat);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
        }
    }
    
    public void deleteCategory(Category cat) throws Exception
    {
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.delete(cat);
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
        Category cat=getCategory(uniqueId);
        if(cat !=null)
        generateUniqueId();
        return uniqueId;
    }

    public void updateCategory(Category cat) throws Exception
    {
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.update(cat);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
        }
    }
    public Category getCategory(String categoryId) throws Exception
    {
        Category cat=null;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createQuery("from Category cat where cat.categoryId=:id").setString("id", categoryId).list();
            tx.commit();
            session.close();
            if(list !=null && !list.isEmpty())
            {
                cat=(Category)list.get(0);
            }
        }
        catch(Exception ex)
        {
            session.close();
        }
        return cat;
    }
    public Category getCategoryName(String categoryName) throws Exception
    {
        Category cat=null;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createQuery("from Category cat where cat.categoryName=:name").setString("name", categoryName).list();
            tx.commit();
            session.close();
            if(list !=null && !list.isEmpty())
            {
                cat=(Category)list.get(0);
            }
        }
        catch(Exception ex)
        {
            session.close();
        }
        return cat;
    }
    public  List getCategories() throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createQuery("from Category cat").list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
        }
        return list;
    }
}
