/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.TrainingDesignation;
import com.fhi.nomis.nomisutils.NomisConstant;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Siaka
 */
public class TrainingDesignationDaoImpl implements TrainingDesignationDao 
{
    Session session;
    Transaction tx;
    AppUtility appUtil=new AppUtility();
    
    public void saveTrainingDesignation(TrainingDesignation td) throws Exception
    {
        try
        {
            if(td ==null || getTrainingDesignationByName(td.getDesignationName()) !=null)
            return;
            if(td.getDesignationId()==null)
            td.setDesignationId(generateTrainingDesignationId());
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.save(td);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            session.close();
        }
    }
    public void updateTrainingDesignation(TrainingDesignation td) throws Exception
    {
        try
        {
            TrainingDesignation td2=getTrainingDesignationByName(td.getDesignationName());
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            if(td2 ==null)
            {
                session.update(td);
            }
            else
            {
                if(td2.getDesignationId().equalsIgnoreCase(td.getDesignationId()))
                session.update(td);
                else
                return;
            }
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            session.close();
        }
    }
    public void markedForDelete(TrainingDesignation td) throws Exception
    {
        try
        {
            td.setMarkedForDelete(NomisConstant.MARKEDFORDELETE);
            updateTrainingDesignation(td);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void deleteTrainingDesignation(TrainingDesignation td) throws Exception
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.delete(td);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            session.close();
        }
    }
    public String generateTrainingDesignationId() throws Exception
    {
        String uniqueId=appUtil.generateUniqueId();
        if(getTrainingDesignation(uniqueId) !=null)
        generateTrainingDesignationId();
         return uniqueId;
    }
    public  TrainingDesignation getTrainingDesignation(String designationId) throws Exception
    {
        TrainingDesignation td=null;
        List list=null;
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from TrainingDesignation td where td.designationId =:id").setString("id", designationId).list();

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
            td=(TrainingDesignation)list.get(0);
        }
        return td;
    }
    public  List getTrainingDesignations(String categoryId) throws Exception
    {
       List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from TrainingDesignation td where td.categoryId =:cId order by td.designationName").setString("cId", categoryId).list();

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
    public  List getAllTrainingDesignations() throws Exception
    {
       List list=null;
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from TrainingDesignation td order by td.designationName").list();

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
    public TrainingDesignation getTrainingDesignationByName(String designationName) throws Exception
    {
        TrainingDesignation td=null;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from TrainingDesignation td where td.designationName =:name").setString("name", designationName).list();

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
            td=(TrainingDesignation)list.get(0);
        }
        return td;
    }
}
