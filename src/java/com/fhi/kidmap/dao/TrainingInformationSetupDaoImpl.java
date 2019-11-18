/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.TrainingInformationSetup;
import com.fhi.nomis.nomisutils.NomisConstant;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class TrainingInformationSetupDaoImpl implements TrainingInformationSetupDao
{
    Session session;
    Transaction tx;
    AppUtility appUtil=new AppUtility();
    public void saveTrainingInformationSetup(TrainingInformationSetup tis) throws Exception
    {
        try
        {
            if(tis ==null || getTrainingInformationSetupByStatusName(tis.getTrainingName()) !=null)
            return;
            if(tis.getRecordId()==null)
            tis.setRecordId(generateUniqueId());
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.save(tis);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            session.close();
        }
    }
    public void updateTrainingInformationSetup(TrainingInformationSetup tis) throws Exception
    {
        try
        {
            TrainingInformationSetup tis2=getTrainingInformationSetupByStatusName(tis.getTrainingName());
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            if(tis2 ==null)
            {
                session.update(tis);
            }
            else
            {
                if(tis2.getRecordId().equalsIgnoreCase(tis.getRecordId()))
                session.update(tis);
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
    public void markedForDelete(TrainingInformationSetup tis) throws Exception
    {
        try
        {
            tis.setMarkedForDelete(NomisConstant.MARKEDFORDELETE);
            updateTrainingInformationSetup(tis);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void deleteTrainingInformationSetup(TrainingInformationSetup tis) throws Exception
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.delete(tis);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            session.close();
        }
    }
    public List getAllTrainingInformationSetups() throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from TrainingInformationSetup tis order by tis.trainingName").list();

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
    public TrainingInformationSetup getTrainingInformationSetup(String recordId) throws Exception
    {
        TrainingInformationSetup tis=null;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from TrainingInformationSetup tis where tis.recordId =:id").setString("id", recordId).list();

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
            tis=(TrainingInformationSetup)list.get(0);
        }
        return tis;
    }
    public TrainingInformationSetup getTrainingInformationSetupByStatusName(String trainingName) throws Exception
    {
        TrainingInformationSetup tis=null;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from TrainingInformationSetup tis where tis.trainingName =:name").setString("name", trainingName).list();

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
            tis=(TrainingInformationSetup)list.get(0);
        }
        return tis;
    }
    public String generateUniqueId() throws Exception
    {
        String uniqueId=appUtil.generateUniqueId();
        if(getTrainingInformationSetup(uniqueId) !=null)
        generateUniqueId();
        return uniqueId;
    }
}
