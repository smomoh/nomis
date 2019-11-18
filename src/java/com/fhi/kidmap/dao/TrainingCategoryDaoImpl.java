/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.TrainingCategory;
import com.fhi.nomis.nomisutils.NomisConstant;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Siaka
 */
public class TrainingCategoryDaoImpl implements TrainingCategoryDao
{
    Session session;
    Transaction tx;
    AppUtility appUtil=new AppUtility();
    public void saveTrainingCategory(TrainingCategory trnCat) throws Exception
    {
        try
        {
            if(trnCat ==null)
            return;
            if(trnCat.getCategoryId()==null)
            trnCat.setCategoryId(generateTrainingCategoryId());
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.save(trnCat);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            session.close();
        }
    }
    public void updateTrainingCategory(TrainingCategory trnCat) throws Exception
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.update(trnCat);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            session.close();
        }
    }
    public void markedForDelete(TrainingCategory trnCat) throws Exception
    {
        try
        {
            trnCat.setMarkedForDelete(NomisConstant.MARKEDFORDELETE);
            updateTrainingCategory(trnCat);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void deleteTrainingCategory(TrainingCategory trnCat) throws Exception
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.delete(trnCat);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            session.close();
        }
    }
    public String generateTrainingCategoryId() throws Exception
    {
        String categoryId=appUtil.generateUniqueId();
        if(getTrainingCategory(categoryId) !=null)
        generateTrainingCategoryId();
         return categoryId;
    }
    public  TrainingCategory getTrainingCategory(String categoryId) throws Exception
    {
        TrainingCategory trnCat=null;
        List list=null;
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from TrainingCategory trnCat where trnCat.categoryId =:id").setString("id", categoryId).list();

        tx.commit();
        session.close();
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
            session.close();
        }
        if(list !=null && !list.isEmpty())
        {
            trnCat=(TrainingCategory)list.get(0);
        }
        return trnCat;
    }
    public  List getAllTrainingCategories() throws Exception
    {
        List list=null;
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from TrainingCategory trnCat ").list();

        tx.commit();
        session.close();
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
            session.close();
        }
        return list;
    }
}
