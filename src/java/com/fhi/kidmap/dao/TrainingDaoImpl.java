/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.Training;
import com.fhi.kidmap.business.TrainingParticipant;
import com.fhi.kidmap.report.ReportUtility;
import com.fhi.nomis.nomisutils.NomisConstant;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Siaka
 */
public class TrainingDaoImpl implements TrainingDao
{
    Session session;
    Transaction tx;
    AppUtility appUtil=new AppUtility();
    ReportUtility rutil=new ReportUtility();
    public void saveTraining(Training trn) throws Exception
    {
        try
        {
            if(trn ==null)
            return;
            if(trn.getRecordId()==null)
            trn.setRecordId(generateTrainingId());
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.save(trn);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            session.close();
        }
    }
    public void updateTraining(Training trn) throws Exception
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.update(trn);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            session.close();
        }
    }
    public void markedForDelete(Training trn) throws Exception
    {
        try
        {
            trn.setMarkedForDelete(NomisConstant.MARKEDFORDELETE);
            updateTraining(trn);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void deleteTraining(Training trn) throws Exception
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.delete(trn);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            session.close();
        }
    }
    public String generateTrainingId() throws Exception
    {
        String recordId=appUtil.generateUniqueId();
        if(getTraining(recordId) !=null)
        generateTrainingId();
         return recordId;
    }
    
    public Training getTraining(String recordId) throws Exception
    {
        Training trn=null;
        List list=null;
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from Training trn where trn.recordId =:id").setString("id", recordId).list();

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
            trn=(Training)list.get(0);
        }
        return trn;
    }
    public List getTrainings() throws Exception
    {
        List list=null;
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from Training trn order by trn.startDate ").list();

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
    public List getAllTrainingReports(String[] params) throws Exception
    {
        List list=null;
        List trainingList=new ArrayList();
        String queryCriteria=rutil.getTrainingReportCriteria(params);
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from Training trn where trn.recordId is not null "+queryCriteria+" order by trn.startDate ").list();

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
    public List getTrainingReport(String[] params) throws Exception
    {
        List list=null;
        List trainingList=new ArrayList();
        String queryCriteria=rutil.getTrainingReportCriteria(params);
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from Training trn,TrainingParticipant tp where trn.participantId=tp.participantId "+queryCriteria+" order by trn.startDate ").list();

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
            Training trn=null;
            TrainingParticipant tp=null;
            for(Object s:list)
            {
                Object[] obj=(Object[])s;
                trn=(Training)obj[0];
                tp=(TrainingParticipant)obj[1];
                trn.setOrganizationCode(tp.getOrganizationCode());
                trainingList.add(trn);
            }
        }
        return trainingList;
    }
}
