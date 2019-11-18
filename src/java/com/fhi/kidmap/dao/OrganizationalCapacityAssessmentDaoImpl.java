/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.OrganizationalCapacityAssessment;
import com.fhi.nomis.nomisutils.NomisConstant;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class OrganizationalCapacityAssessmentDaoImpl implements OrganizationalCapacityAssessmentDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    public void saveOrganizationalCapacityAssessment(OrganizationalCapacityAssessment oca) throws Exception
    {
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.save(oca);
            tx.commit();
            session.close();
        }
        catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
    }
    public void updateOrganizationalCapacityAssessment(OrganizationalCapacityAssessment oca) throws Exception
    {
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.update(oca);
            tx.commit();
            session.close();
        }
        catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
    }
    public void markedForDelete(OrganizationalCapacityAssessment oca) throws Exception
    {
        try
        {
            oca.setMarkedForDelete(NomisConstant.MARKEDFORDELETE);
            updateOrganizationalCapacityAssessment(oca);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void deleteOrganizationalCapacityAssessment(OrganizationalCapacityAssessment oca) throws Exception
    {
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.delete(oca);
            tx.commit();
            session.close();
        }
        catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
    }
    public int getTotalScore(OrganizationalCapacityAssessment oCapAssessment)
    {
        int totalScore=0;
        try
        {
            totalScore=oCapAssessment.getActionplanning()+oCapAssessment.getAdvocacy()+oCapAssessment.getBanking()+oCapAssessment.getBookkeeping()+oCapAssessment.getBudgeting()+oCapAssessment.getGoalsandobjectives()+oCapAssessment.getInternalrules()+oCapAssessment.getMeetings()+oCapAssessment.getMonitoring()+oCapAssessment.getNetworking()+oCapAssessment.getNoofassessment()+oCapAssessment.getNoofassessors()+oCapAssessment.getProposalwriting()+oCapAssessment.getReportingandrecordkeeping()+oCapAssessment.getRoleandresponsibilities()+oCapAssessment.getTeambuilding()+oCapAssessment.getVisionandmission();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return totalScore;
    }
    public int compareTotalScore(List list)
    {
        int j=0;
        try
        {
            int baseLineCsiScore=Integer.parseInt(list.get(0).toString());
            for(int i=1; i<list.size(); i++)
            {
                int otherScore=Integer.parseInt(list.get(i).toString());
                if(baseLineCsiScore < otherScore)
                {
                   j=1;
                   break;
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return j;
    }
    public List getOrganizationalCapacityAssessment() throws Exception {
        List list = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from OrganizationalCapacityAssessment oca order by oca.recordId").list();
            tx.commit();
            session.close();
        }
         catch (HibernateException he)
         {
             session.close();
            throw new Exception(he);
         }
         return list;
    }
    public OrganizationalCapacityAssessment getOrganizationalCapacityAssessment(String organizationCode, String dateOfAssessment) throws Exception
    {
        List list = new ArrayList();
        OrganizationalCapacityAssessment oca=null;
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from OrganizationalCapacityAssessment oca where oca.orgCode = :id and oca.dateofcapacityassessment = :dt")
            .setString("id", organizationCode).setString("dt", dateOfAssessment).list();
            tx.commit();
            session.close();
        }
         catch (HibernateException he)
         {
             session.close();
            throw new Exception(he);
         }
         if(list.size()>0)
         {
           oca = (OrganizationalCapacityAssessment)list.get(0);
         }
        return oca;
    }
    public List getOrganizationalCapacityAssessmentRecords(String organizationCode) throws Exception
    {
        List list = new ArrayList();
        String orgCodeCriteria=" ";
        if(organizationCode !=null)
        orgCodeCriteria=" where oca.orgCode like '%"+organizationCode+"%' ";
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from OrganizationalCapacityAssessment oca "+orgCodeCriteria).list();
            System.err.println("from OrganizationalCapacityAssessment oca "+orgCodeCriteria);
            
            tx.commit();
            session.close();
        }
         catch (HibernateException he)
         {
             session.close();
            throw new Exception(he);
         }
         return list;
    }
    public int getNoOfCapacityAssessment(String organizationCode) throws Exception {
        List list = new ArrayList();
        int count=0;
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select count(oca.orgCode) from OrganizationalCapacityAssessment oca where oca.orgCode = :id")
            .setString("id", organizationCode).list();
            tx.commit();
            session.close();
        }
         catch (HibernateException he)
         {
             session.close();
            throw new Exception(he);
         }
         if(list.size()>0)
         {
           count=Integer.parseInt(list.get(0).toString());
         }
        return count;
    }
    public String getDateOfFirstCapacityAssessment(String organizationCode) throws Exception {
        List list = new ArrayList();
        String firstAssessmentDate=null;
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select oca.dateofcapacityassessment from OrganizationalCapacityAssessment oca where oca.orgCode = :id order by oca.dateofcapacityassessment asc")
            .setString("id", organizationCode).list();
            tx.commit();
            session.close();
        }
         catch (HibernateException he)
         {
             session.close();
            throw new Exception(he);
         }
         if(list.size()>0)
         {
           firstAssessmentDate=list.get(0).toString();
         }
        return firstAssessmentDate;
    }
}
