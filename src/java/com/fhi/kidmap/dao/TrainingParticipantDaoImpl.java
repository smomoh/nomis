/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.TrainingParticipant;
import com.fhi.nomis.nomisutils.NomisConstant;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class TrainingParticipantDaoImpl implements TrainingParticipantDao
{
    Session session;
    Transaction tx;
    AppUtility appUtil=new AppUtility();
    
    public void saveTrainingParticipant(TrainingParticipant tp) throws Exception
    {
        try
        {
            if(tp ==null)
            return;
            //if(tp.getParticipantId()==null)
            //tp.setParticipantId(generateUniqueId());
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.save(tp);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            session.close();
        }
    }
    public void updateTrainingParticipant(TrainingParticipant tp) throws Exception
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.update(tp);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            session.close();
        }
    }
    public void markedForDelete(TrainingParticipant tp) throws Exception
    {
        try
        {
            tp.setMarkedForDelete(NomisConstant.MARKEDFORDELETE);
            updateTrainingParticipant(tp);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void deleteTrainingParticipant(TrainingParticipant tp) throws Exception
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.delete(tp);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            session.close();
        }
    }
    public List getDistinctOrganizationRecordsFromTraining() throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct tp.organizationCode from TrainingParticipant tp ").list();

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
    public TrainingParticipant getTrainingParticipant(String participantId) throws Exception
    {
        TrainingParticipant tp=null;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from TrainingParticipant tp where tp.participantId =:id").setString("id", participantId).list();

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
            tp=(TrainingParticipant)list.get(0);
        }
        return tp;
    }
    public TrainingParticipant getTrainingParticipantByTraineeId(String traineeId) throws Exception
    {
        TrainingParticipant tp=null;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from TrainingParticipant tp where tp.traineeId =:id").setString("id", traineeId).list();

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
            tp=(TrainingParticipant)list.get(0);
        }
        return tp;
    }
    public TrainingParticipant getTrainingParticipantByName(String organizationCode,String participantName) throws Exception
    {
        TrainingParticipant tp=null;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from TrainingParticipant tp where tp.organizationCode=:orgCode and tp.participantName =:name").setString("orgCode", organizationCode).setString("name", participantName).list();

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
            tp=(TrainingParticipant)list.get(0);
        }
        return tp;
    }
    public List getAllTrainingParticipants() throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from TrainingParticipant tp order by tp.participantName").list();
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
    public int getNoOfTraineesByState(String organizationCode) throws Exception
    {
        int count=0;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select count(distinct tp.participantId) from TrainingParticipant tp where tp.organizationCode=:orgCode").setString("orgCode", organizationCode).list();
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
            count=((Long)list.get(0)).intValue();
        }
        return count;
    }
    public void saveOrUpdateTrainingParticipant(TrainingParticipant tp) throws Exception
    {
        if(tp ==null)
        return;
        TrainingParticipant duplicateTp=null;
        try
        {
            if(tp.getParticipantId() !=null)
            {
                duplicateTp=getTrainingParticipant(tp.getParticipantId());
                if(duplicateTp !=null)
                updateTrainingParticipant(tp);
                else
                saveTrainingParticipant(tp);
            }
            else
            {
                duplicateTp=getTrainingParticipantByName(tp.getOrganizationCode(), tp.getParticipantName());
                if(duplicateTp !=null)
                {
                    tp.setParticipantId(duplicateTp.getParticipantId());
                    updateTrainingParticipant(tp);
                }
                else
                {
                    saveTrainingParticipant(tp);
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public String generateUniqueTrainingId(TrainingParticipant tp) throws Exception
    {
        int count=getNoOfTraineesByState(tp.getOrganizationCode());
        count++;
        String uniqueOrgCode=tp.getOrganizationCode();
        String uniqueId=uniqueOrgCode+"/"+count;
        while(getTrainingParticipantByTraineeId(uniqueId) !=null)
        {
            count++;
            uniqueId=uniqueOrgCode+"/"+count;
        }
        return uniqueId;
    }
    public String generateUniqueId() throws Exception
    {
        String uniqueId=appUtil.generateUniqueId();
        if(getTrainingParticipant(uniqueId) !=null)
        generateUniqueId();
        return uniqueId;
    }
}
