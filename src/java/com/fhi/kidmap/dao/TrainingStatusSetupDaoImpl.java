/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.TrainingStatusSetup;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class TrainingStatusSetupDaoImpl implements TrainingStatusSetupDao
{
    Session session;
    Transaction tx;
    AppUtility appUtil=new AppUtility();
    public void saveTrainingStatusSetup(TrainingStatusSetup tss) throws Exception
    {
        try
        {
            if(tss ==null || getTrainingStatusSetupByStatusName(tss.getTrainingStatusName()) !=null)
            return;
            if(tss.getStatusId()==null)
            tss.setStatusId(generateUniqueId());
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.save(tss);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            session.close();
        }
    }
    public void updateTrainingStatusSetup(TrainingStatusSetup tss) throws Exception
    {
        try
        {
            TrainingStatusSetup tss2=getTrainingStatusSetupByStatusName(tss.getTrainingStatusName());
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            if(tss2 ==null)
            {
                session.update(tss);
            }
            else
            {
                if(tss2.getStatusId().equalsIgnoreCase(tss.getStatusId()))
                session.update(tss);
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
    public void deleteTrainingStatusSetup(TrainingStatusSetup tss) throws Exception
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.delete(tss);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            session.close();
        }
    }
    public List getAllTrainingStatusSetups() throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from TrainingStatusSetup tss order by tss.trainingStatusName").list();

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
    public TrainingStatusSetup getTrainingStatusSetup(String statusId) throws Exception
    {
        TrainingStatusSetup tss=null;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from TrainingStatusSetup tss where tss.statusId =:id").setString("id", statusId).list();

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
            tss=(TrainingStatusSetup)list.get(0);
        }
        return tss;
    }
    public TrainingStatusSetup getTrainingStatusSetupByStatusName(String trainingStatusName) throws Exception
    {
        TrainingStatusSetup tss=null;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from TrainingStatusSetup tss where tss.trainingStatusName =:name").setString("name", trainingStatusName).list();

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
            tss=(TrainingStatusSetup)list.get(0);
        }
        return tss;
    }
    public String generateUniqueId() throws Exception
    {
        String uniqueId=appUtil.generateUniqueId();
        if(getTrainingStatusSetup(uniqueId) !=null)
        generateUniqueId();
        return uniqueId;
    }
}
