/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.CaregiverTBHIVScreeningChecklist;
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
public class CaregiverTBHIVScreeningChecklistDaoImpl implements CaregiverTBHIVScreeningChecklistDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    DaoUtil util=new DaoUtil();
    AppUtility appUtil=new AppUtility();
    String hhCaregiverTBChecklistQuery="from HouseholdEnrollment hhe, Caregiver cgiver, CaregiverTBHIVScreeningChecklist tbhivChecklist where hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=tbhivChecklist.caregiverId ";
    String hhCaregiverQuery="";
    public void saveCaregiverTBHIVScreeningChecklist(CaregiverTBHIVScreeningChecklist tbhivChecklist) throws Exception
    {
        try 
        {   
            if(getCaregiverTBHIVScreeningChecklist(tbhivChecklist.getCaregiverId(),tbhivChecklist.getDateOfAssessment()) ==null)
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
    /*public void saveOrUpdateCaregiverTBHIVScreeningChecklist(CaregiverTBHIVScreeningChecklist tbhivChecklist) throws Exception
    {
        try 
        {   
            CaregiverTBHIVScreeningChecklist tbhivChecklist2=getCaregiverTBHIVScreeningChecklist(tbhivChecklist.getCaregiverId(),tbhivChecklist.getDateOfAssessment());
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
    public void updateCaregiverTBHIVScreeningChecklist(CaregiverTBHIVScreeningChecklist tbhivChecklist) throws Exception
    {
        try 
        {   
            CaregiverTBHIVScreeningChecklist tbhivChecklist2=getCaregiverTBHIVScreeningChecklist(tbhivChecklist.getCaregiverId(),tbhivChecklist.getDateOfAssessment());
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
    public void updateCaregiverTBHIVScreeningChecklistForAssessmentNumber(CaregiverTBHIVScreeningChecklist tbhivChecklist) throws Exception
    {
        try 
        {   
            CaregiverTBHIVScreeningChecklist tbhivChecklist2=getCaregiverTBHIVScreeningChecklist(tbhivChecklist.getCaregiverId(),tbhivChecklist.getDateOfAssessment());
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
    public void markedForDelete(CaregiverTBHIVScreeningChecklist tbhivChecklist) throws Exception
    {
        try 
        {   
            CaregiverTBHIVScreeningChecklist tbhivChecklist2=getCaregiverTBHIVScreeningChecklist(tbhivChecklist.getCaregiverId(),tbhivChecklist.getDateOfAssessment());
            if(tbhivChecklist2 !=null)
            {
                tbhivChecklist.setId(tbhivChecklist2.getId());
                tbhivChecklist.setMarkedForDelete(NomisConstant.MARKEDFORDELETE);
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
    public void deleteCaregiverTBHIVScreeningChecklist(CaregiverTBHIVScreeningChecklist tbhivChecklist) throws Exception
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
    public int getAssessmentNumber(CaregiverTBHIVScreeningChecklist tbhivChecklist) throws Exception
    {
        int assessmentNumber=1;
        List list=getAssessmentsByCaregiverId(tbhivChecklist.getCaregiverId());
        if(list !=null && !list.isEmpty())
        {
            assessmentNumber=list.size()+1;
        }
        return assessmentNumber;
    }
    public void assignAssessmentNumber(CaregiverTBHIVScreeningChecklist tbhivChecklist) throws Exception
    {
        int assessmentNumber=0;
        List list=getAssessmentsByCaregiverId(tbhivChecklist.getCaregiverId());
        if(list !=null && !list.isEmpty())
        {
            CaregiverTBHIVScreeningChecklist tbhivChecklist2=null;
            if(list.size()>0)
            {
                for(Object s:list)
                {
                    tbhivChecklist2=(CaregiverTBHIVScreeningChecklist)s;
                    assessmentNumber++;
                    tbhivChecklist2.setAssessmentNo(assessmentNumber);
                    updateCaregiverTBHIVScreeningChecklistForAssessmentNumber(tbhivChecklist2);
                }
            }
        }
    }
    public List getAssessmentsByCaregiverId(String caregiverId) throws Exception
    {
        List list=null;
            try
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                list = session.createQuery("from CaregiverTBHIVScreeningChecklist tbhivChecklist where tbhivChecklist.caregiverId =:id order by tbhivChecklist.dateOfAssessment").setString("id", caregiverId).list();
                tx.commit();
                session.close();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }

        return list;
    }
    public List getAllCaregiverTBHIVScreeningChecklist() throws Exception
    {
        List list = new ArrayList();
    
         try
         {
           session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list= session.createQuery("from CaregiverTBHIVScreeningChecklist tbhivChecklist ").list();
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
    public int getNumberOfCaregiverTBHIVScreeningChecklist(String additionalCriteria) throws Exception
    {
        List list = new ArrayList();
        int numberOfCaregivers=0;
         try
         {
           session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            //System.err.println("select count(distinct tbhivChecklist.caregiverId) "+hhCaregiverTBChecklistQuery+additionalCriteria);
            list= session.createQuery("select count(distinct tbhivChecklist.caregiverId) "+hhCaregiverTBChecklistQuery+additionalCriteria).list();
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
            numberOfCaregivers=(Integer.parseInt(list.get(0).toString()));
        }
        return numberOfCaregivers;
    }
    public List getListOfCaregiversScreenedForTBHIV(String additionalCriteria) throws Exception
    {
        List list = new ArrayList();
        List cgiverList = new ArrayList();
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
                cgiverList.add(obj[1]);
            }
        }
        return cgiverList;
    }
    public List getTBHIVScreeningRecords(String additionalCriteria) throws Exception
    {
        List list = new ArrayList();
        List tbhivList = new ArrayList();
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
                tbhivList.add(obj[2]);
            }
        }
        return tbhivList;
    }
    public List getCaregiverTBHIVScreeningChecklistPerCaregiver(String caregiverId) throws Exception
    {
        List list = new ArrayList();
    
         try
         {
           session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list= session.createQuery("from CaregiverTBHIVScreeningChecklist tbhivChecklist where tbhivChecklist.caregiverId=:id").setString("id", caregiverId).list();
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
    public CaregiverTBHIVScreeningChecklist getCaregiverTBHIVScreeningChecklist(int id) throws Exception
    {
        List list = new ArrayList();
        CaregiverTBHIVScreeningChecklist tbhivChecklist=null;
         try
         {
           session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list= session.createQuery("from CaregiverTBHIVScreeningChecklist tbhivChecklist where tbhivChecklist.id=:uid").setInteger("uid", id).list();
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
            tbhivChecklist=(CaregiverTBHIVScreeningChecklist)list.get(0);
        }
        return tbhivChecklist;
    }
    public CaregiverTBHIVScreeningChecklist getCaregiverTBHIVScreeningChecklist(String caregiverId,String dateOfAssessment) throws Exception
    {
        List list = new ArrayList();
        CaregiverTBHIVScreeningChecklist tbhivChecklist=null;
         try
         {
           session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list= session.createQuery("from CaregiverTBHIVScreeningChecklist tbhivChecklist where tbhivChecklist.caregiverId=:id and tbhivChecklist.dateOfAssessment=:doa").setString("id", caregiverId).setString("doa", dateOfAssessment).list();
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
            tbhivChecklist=(CaregiverTBHIVScreeningChecklist)list.get(0);
        }
        return tbhivChecklist;
    }
}
