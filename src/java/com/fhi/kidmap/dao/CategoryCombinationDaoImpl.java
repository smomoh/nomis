/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.CategoryCombination;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class CategoryCombinationDaoImpl implements CategoryCombinationDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    AppUtility appUtil=new AppUtility();
    public void saveCategoryCombination(CategoryCombination cc) throws Exception
    {
        try
        {
            if(cc.getCategoryComboId()==null)
            cc.setCategoryComboId(generateUniqueId());
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.save(cc);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
    }
    public void updateCategoryCombination(CategoryCombination cc) throws Exception
    {
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.update(cc);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
    }
    public void deleteCategoryCombination(CategoryCombination cc) throws Exception
    {
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.delete(cc);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
    }
    public String generateUniqueId() throws Exception
    {
        String uniqueId=appUtil.generateUniqueId();
        CategoryCombination cc=getCategoryCombination(uniqueId);
        if(cc !=null)
        generateUniqueId();
        return uniqueId;
    }
    public CategoryCombination getCategoryCombination(String categoryComboId) throws Exception
    {
            List list = null;
            CategoryCombination cc=null;
            try
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                list = session.createQuery("from CategoryCombination cc where cc.categoryComboId =:ccId").setString("ccId", categoryComboId).list();
                tx.commit();
                session.close();
            }
            catch(Exception ex)
            {
                session.close();
            }
            if(list !=null && !list.isEmpty())
            {
                cc=(CategoryCombination)list.get(0);
            }
            return cc;
    }
    public CategoryCombination getCategoryCombinationByName(String categoryComboName) throws Exception
    {
            List list = null;
            CategoryCombination cc=null;
            try
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                list = session.createQuery("from CategoryCombination cc where cc.categoryComboName =:ccId").setString("ccId", categoryComboName).list();
                tx.commit();
                session.close();
            }
            catch(Exception ex)
            {
                session.close();
            }
            if(list !=null && !list.isEmpty())
            {
                cc=(CategoryCombination)list.get(0);
            }
            return cc;
    }
    public List getCategoryCombinationList() throws Exception
    {
            List list = new ArrayList();
            try
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                list = session.createQuery("from CategoryCombination cc order by cc.categoryComboName").list();
                tx.commit();
                session.close();
            }
            catch(Exception ex)
            {
                session.close();
                ex.printStackTrace();
            }
            return list;
    }
}
