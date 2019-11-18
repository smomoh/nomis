/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.OvcTBHIVScreeningChecklist;
import com.fhi.nomis.nomisutils.NomisConstant;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class OvcTBHIVScreeningChecklistDaoImpl implements OvcTBHIVScreeningChecklistDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    DaoUtil util=new DaoUtil();
    AppUtility appUtil=new AppUtility();
    String hhCaregiverTBChecklistQuery="from HouseholdEnrollment hhe, Ovc ovc, OvcTBHIVScreeningChecklist tbhivChecklist where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=tbhivChecklist.ovcId ";
    public void saveOvcTBHIVScreeningChecklist(OvcTBHIVScreeningChecklist tbhivChecklist) throws Exception
    {
        try 
        {   
            if(getOvcTBHIVScreeningChecklist(tbhivChecklist.getOvcId(),tbhivChecklist.getDateOfAssessment()) ==null)
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.save(tbhivChecklist);
                tx.commit();
                session.close();
                assignAssessmentNumber(tbhivChecklist);
            }
        }
       catch (Exception ex)
       {
           System.err.println("Exception: "+ex.getMessage());
           ex.printStackTrace();
       }
    }
    /*public void saveOrUpdateOvcTBHIVScreeningChecklist(OvcTBHIVScreeningChecklist tbhivChecklist) throws Exception
    {
        try 
        {   
            OvcTBHIVScreeningChecklist tbhivChecklist2=getOvcTBHIVScreeningChecklist(tbhivChecklist.getOvcId(),tbhivChecklist.getDateOfAssessment());
            if(tbhivChecklist2 ==null)
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.save(tbhivChecklist);
                tx.commit();
                session.close();
            }
            else
            {
                tbhivChecklist.setId(tbhivChecklist2.getId());
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.update(tbhivChecklist);
                tx.commit();
                session.close();
            }
            assignAssessmentNumber(tbhivChecklist);
        }
       catch (Exception ex)
       {
           System.err.println("Exception: "+ex.getMessage());
           ex.printStackTrace();
       }
    }*/
    public void updateOvcTBHIVScreeningChecklistForAssessmentNumber(OvcTBHIVScreeningChecklist tbhivChecklist) throws Exception
    {
        try 
        {   
            OvcTBHIVScreeningChecklist tbhivChecklist2=getOvcTBHIVScreeningChecklist(tbhivChecklist.getOvcId(),tbhivChecklist.getDateOfAssessment());
            if(tbhivChecklist2 !=null)
            {
                tbhivChecklist.setId(tbhivChecklist2.getId());
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.update(tbhivChecklist);
                tx.commit();
                session.close();
                //assignAssessmentNumber(tbhivChecklist);
            }
        }
       catch (Exception ex)
       {
           System.err.println("Exception: "+ex.getMessage());
           ex.printStackTrace();
       }
    }
    public void updateOvcTBHIVScreeningChecklist(OvcTBHIVScreeningChecklist tbhivChecklist) throws Exception
    {
        try 
        {   
            OvcTBHIVScreeningChecklist tbhivChecklist2=getOvcTBHIVScreeningChecklist(tbhivChecklist.getOvcId(),tbhivChecklist.getDateOfAssessment());
            if(tbhivChecklist2 !=null)
            {
                tbhivChecklist.setId(tbhivChecklist2.getId());
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.update(tbhivChecklist);
                tx.commit();
                session.close();
                assignAssessmentNumber(tbhivChecklist);
            }
        }
       catch (Exception ex)
       {
           System.err.println("Exception: "+ex.getMessage());
           ex.printStackTrace();
       }
    }
public void markedForDelete(OvcTBHIVScreeningChecklist tbhivChecklist) throws Exception
{
    try
    {
        tbhivChecklist.setMarkedForDelete(NomisConstant.MARKEDFORDELETE);
        updateOvcTBHIVScreeningChecklist(tbhivChecklist);
    }
    catch(Exception ex)
    {
        ex.printStackTrace();
    }
}
    public void deleteOvcTBHIVScreeningChecklist(OvcTBHIVScreeningChecklist tbhivChecklist) throws Exception
    {
        try 
        {   
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.delete(tbhivChecklist);
            tx.commit();
            session.close();
            assignAssessmentNumber(tbhivChecklist);
        }
       catch (Exception ex)
       {
           System.err.println("Exception: "+ex.getMessage());
           ex.printStackTrace();
       }
    }
    public int getAssessmentNumber(OvcTBHIVScreeningChecklist tbhivChecklist) throws Exception
    {
        int assessmentNumber=1;
        List list=getAssessmentsByOvcId(tbhivChecklist.getOvcId());
        if(list !=null && !list.isEmpty())
        {
            assessmentNumber=list.size()+1;
        }
        return assessmentNumber;
    }
    public void assignAssessmentNumber(OvcTBHIVScreeningChecklist tbhivChecklist) throws Exception
    {
        int assessmentNumber=0;
        List list=getAssessmentsByOvcId(tbhivChecklist.getOvcId());
        if(list !=null && !list.isEmpty())
        {
            OvcTBHIVScreeningChecklist tbhivChecklist2=null;
            if(list.size()>0)
            {
                for(Object s:list)
                {
                    tbhivChecklist2=(OvcTBHIVScreeningChecklist)s;
                    assessmentNumber++;
                    tbhivChecklist2.setAssessmentNo(assessmentNumber);
                    updateOvcTBHIVScreeningChecklistForAssessmentNumber(tbhivChecklist2);
                }
            }
        }
    }
    public List getAssessmentsByOvcId(String ovcId) throws Exception
    {
        List list=null;
            try
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                list = session.createQuery("from OvcTBHIVScreeningChecklist tbhivChecklist where tbhivChecklist.ovcId =:id order by tbhivChecklist.dateOfAssessment").setString("id", ovcId).list();
                tx.commit();
                session.close();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }

        return list;
    }
    public List getAllOvcTBHIVScreeningChecklist() throws Exception
    {
        List list = new ArrayList();
    
         try
         {
           session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list= session.createQuery("from OvcTBHIVScreeningChecklist tbhivChecklist ").list();
            tx.commit();
            session.close();
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
            session.close();
        }
        return list;
    }
    public int getNumberOfOvcTBHIVScreeningChecklist(String additionalCriteria) throws Exception
    {
        List list = new ArrayList();
        int numberOfOvc=0;
         try
         {
           session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            //System.err.println("select count(distinct tbhivChecklist.ovcId) "+hhCaregiverTBChecklistQuery+additionalCriteria);
            list= session.createQuery("select count(distinct tbhivChecklist.ovcId) "+hhCaregiverTBChecklistQuery+additionalCriteria).list();
            tx.commit();
            session.close();
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
            session.close();
        }
        if(list !=null && !list.isEmpty())
        {
            numberOfOvc=(Integer.parseInt(list.get(0).toString()));
        }
        return numberOfOvc;
    }
    public List getListOfOvcScreenedForTBHIV(String additionalCriteria) throws Exception
    {
        List list = new ArrayList();
        List ovcList = new ArrayList();
         try
         {
           session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list= session.createQuery(hhCaregiverTBChecklistQuery+additionalCriteria).list();
            tx.commit();
            session.close();
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
            session.close();
        }
        if(list !=null && !list.isEmpty())
        {
            for(int i=0; i<list.size(); i++)
            {
                Object[] obj=(Object[])list.get(i);
                ovcList.add(obj[1]);
            }
        }
        return ovcList;
    }
    public List getTBHIVScreeningRecords(String additionalCriteria) throws Exception
    {
        List list = new ArrayList();
        List ovcList = new ArrayList();
         try
         {
           session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list= session.createQuery(hhCaregiverTBChecklistQuery+additionalCriteria).list();
            tx.commit();
            session.close();
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
            session.close();
        }
        if(list !=null && !list.isEmpty())
        {
            for(int i=0; i<list.size(); i++)
            {
                Object[] obj=(Object[])list.get(i);
                ovcList.add(obj[2]);
            }
        }
        return ovcList;
    }
    public List getOvcTBHIVScreeningChecklistPerOvc(String ovcId) throws Exception
    {
        List list = new ArrayList();
    
         try
         {
           session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list= session.createQuery("from OvcTBHIVScreeningChecklist tbhivChecklist where tbhivChecklist.ovcId=:id").setString("id", ovcId).list();
            tx.commit();
            session.close();
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
            session.close();
        }
        return list;
    }
    public OvcTBHIVScreeningChecklist getOvcTBHIVScreeningChecklist(int id) throws Exception
    {
        List list = new ArrayList();
        OvcTBHIVScreeningChecklist tbhivChecklist=null;
         try
         {
           session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list= session.createQuery("from OvcTBHIVScreeningChecklist tbhivChecklist where tbhivChecklist.id=:uid").setInteger("uid", id).list();
            tx.commit();
            session.close();
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
            session.close();
        }
        if(list !=null && !list.isEmpty())
        {
            tbhivChecklist=(OvcTBHIVScreeningChecklist)list.get(0);
        }
        return tbhivChecklist;
    }
    public OvcTBHIVScreeningChecklist getOvcTBHIVScreeningChecklist(String ovcId,String dateOfAssessment) throws Exception
    {
        List list = new ArrayList();
        OvcTBHIVScreeningChecklist tbhivChecklist=null;
         try
         {
           session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list= session.createQuery("from OvcTBHIVScreeningChecklist tbhivChecklist where tbhivChecklist.ovcId=:id and tbhivChecklist.dateOfAssessment=:doa").setString("id", ovcId).setString("doa", dateOfAssessment).list();
            tx.commit();
            session.close();
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
            session.close();
        }
        if(list !=null && !list.isEmpty())
        {
            tbhivChecklist=(OvcTBHIVScreeningChecklist)list.get(0);
        }
        return tbhivChecklist;
    }
}
