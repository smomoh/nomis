/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.CareplanAchievement;
import com.fhi.nomis.nomisutils.NomisConstant;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class CareplanAchievementDaoImpl implements CareplanAchievementDao
{
    Session session;
    Transaction tx;
    DaoUtil util=new DaoUtil();
       
    public List getCareplanAchievementsNotGraduated(String additionalQueryCriteria) throws Exception
    {
        List list=null;
        List cpaList=new ArrayList();
        try
        {
            String query=util.getHouseholdQueryPart()+" CareplanAchievement cpa where hhe.hhUniqueId=cpa.clientId "+additionalQueryCriteria+" and cpa.clientId not in (select distinct withdrawal.ovcId from OvcWithdrawal withdrawal)";
            System.err.println("query in getCareplanAchievements is "+query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createQuery(query).list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            session.close();
        }
        if(list !=null)
        {
            for(Object obj:list)
            {
                Object[] objArray=(Object[])obj;
                cpaList.add(objArray[1]);
            }
        }
        return cpaList;
    }
    public List getCareplanAchievements(String additionalQueryCriteria) throws Exception
    {
        List list=null;
        List cpaList=new ArrayList();
        try
        {
            String query=util.getHouseholdQueryPart()+" CareplanAchievement cpa where hhe.hhUniqueId=cpa.clientId "+additionalQueryCriteria;
            System.err.println("query in getCareplanAchievements is "+query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createQuery(query).list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            session.close();
        }
        if(list !=null)
        {
            for(Object obj:list)
            {
                Object[] objArray=(Object[])obj;
                cpaList.add(objArray[1]);
            }
        }
        return cpaList;
    }
    public List getAllCareplanAchievements() throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createQuery("from CareplanAchievement cpa").list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            closeSession(session);
        }
        return list;
    }
    public CareplanAchievement getCareplanAchievement(int id) throws Exception
    {
        CareplanAchievement cpa=null;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createQuery("from CareplanAchievement cpa where cpa.id=:uid").setInteger("uid", id).list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            closeSession(session);
        }
        if(list !=null && !list.isEmpty())
        {
           cpa=(CareplanAchievement)list.get(0); 
        }
        return cpa;
    }
    public CareplanAchievement getCareplanAchievement(String clientId,String dateOfAssessment) throws Exception
    {
        CareplanAchievement cpa=null;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createQuery("from CareplanAchievement cpa where cpa.clientId=:cid and cpa.dateOfAssessment=:dt").setString("cid", clientId).setString("dt", dateOfAssessment).list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            closeSession(session);
        }
        if(list !=null && !list.isEmpty())
        {
           cpa=(CareplanAchievement)list.get(0); 
        }
        return cpa;
    }
    public List getCareplanAchievementsPerClient(String clientId) throws Exception
    {
       List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createQuery("from CareplanAchievement cpa where cpa.clientId=:cid order by cpa.dateOfAssessment desc").setString("cid", clientId).list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        return list;
    }
    public int getGraduationScore(CareplanAchievement cpa) throws Exception
    {
        int cpascore=0;
        if(cpa !=null)
        {
            String enrolledInVocationalTraining=cpa.getSch_adolInVoctraining();
            
            cpascore+=DaoUtil.getGraduationScore(cpa.getHth_hivOnArt());
            cpascore+=DaoUtil.getGraduationScore(cpa.getHth_hivdisclosed());
            cpascore+=DaoUtil.getGraduationScore(cpa.getHth_hivknowledge());
            cpascore+=DaoUtil.getGraduationScore(cpa.getHth_vcInneedOfHthServices());
            cpascore+=DaoUtil.getGraduationScore(cpa.getHth_vchivrisk());
            cpascore+=DaoUtil.getGraduationScore(cpa.getHth_vcreftested());
            cpascore+=DaoUtil.getGraduationScore(enrolledInVocationalTraining);
            cpascore+=DaoUtil.getGraduationScore(cpa.getSch_earlyChildCare());
            cpascore+=DaoUtil.getGraduationScore(cpa.getSch_schAttendance());
            cpascore+=DaoUtil.getGraduationScore(cpa.getSch_vcEnrolledInSecondarySch());
            cpascore+=DaoUtil.getGraduationScore(cpa.getSft_cgcompletedTwoModules());
            cpascore+=DaoUtil.getGraduationScore(cpa.getSft_childHeadedLinkedToServices());
            cpascore+=DaoUtil.getGraduationScore(cpa.getSft_birthCert());
            cpascore+=DaoUtil.getGraduationScore(cpa.getSft_vcSafeFromAbuse());
            cpascore+=DaoUtil.getGraduationScore(cpa.getSft_vcreferredForCps());
            cpascore+=DaoUtil.getGraduationScore(cpa.getSft_vcsad());
            cpascore+=DaoUtil.getGraduationScore(cpa.getStb_cgPartEconServ());
            cpascore+=DaoUtil.getGraduationScore(cpa.getStb_hungryNoFood());
            cpascore+=DaoUtil.getGraduationScore(cpa.getStb_resiliency()); 
        }
        return cpascore;
    }
    public void saveCareplanAchievementForImport(CareplanAchievement cpa) throws Exception
    {
        try
        {
            if(cpa !=null)
            {
                cpa.setScore(getGraduationScore(cpa));
                session=HibernateUtil.getSession();
                tx=session.beginTransaction();
                session.save(cpa);
                tx.commit();
                session.close();
            }
            else
            {
                System.err.println("cpa is null in saveCareplanAchievement");
            }
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
    }
    public void updateCareplanAchievementForImport(CareplanAchievement cpa) throws Exception
    {
        try
        {
            if(cpa !=null)
            {
                cpa.setScore(getGraduationScore(cpa));
                session=HibernateUtil.getSession();
                tx=session.beginTransaction();
                session.update(cpa);
                tx.commit();
                session.close();
            }
            else
            {
                System.err.println("cpa is null in updateCareplanAchievement");
            }
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
    }
    public void saveCareplanAchievement(CareplanAchievement cpa) throws Exception
    {
        try
        {
            if(cpa !=null)
            {
                cpa.setScore(getGraduationScore(cpa));
                session=HibernateUtil.getSession();
                tx=session.beginTransaction();
                session.save(cpa);
                tx.commit();
                session.close();
                //if(cpa.getGraduated() !=null && cpa.getGraduated().equalsIgnoreCase("graduated"))
                //withdrawHousehold(cpa.getClientId(),cpa.getDateOfAssessment(),cpa.getGraduated(),"household",0);
            }
            else
            {
                System.err.println("cpa is null in saveCareplanAchievement");
            }
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
    }
    public void updateCareplanAchievement(CareplanAchievement cpa) throws Exception
    {
        try
        {
            if(cpa !=null)
            {
                cpa.setScore(getGraduationScore(cpa));
                session=HibernateUtil.getSession();
                tx=session.beginTransaction();
                session.update(cpa);
                tx.commit();
                session.close();
                //if(cpa.getGraduated() !=null && cpa.getGraduated().equalsIgnoreCase("graduated"))
                //withdrawHousehold(cpa.getClientId(),cpa.getDateOfAssessment(),cpa.getGraduated(),"household",0);
            }
            else
            {
                System.err.println("cpa is null in updateCareplanAchievement");
            }
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
    }
    public void markedForDelete(CareplanAchievement cpa) throws Exception
    {
        try
        {
            if(cpa !=null)
            {
                cpa.setScore(getGraduationScore(cpa));
                cpa.setMarkedForDelete(NomisConstant.MARKEDFORDELETE);
                session=HibernateUtil.getSession();
                tx=session.beginTransaction();
                session.update(cpa);
                tx.commit();
                session.close();
            }
            else
            {
                System.err.println("cpa is null in updateCareplanAchievement");
            }
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
    }
    public void deleteCareplanAchievement(CareplanAchievement cpa) throws Exception
    {
        try
        {
            if(cpa !=null)
            {
                session=HibernateUtil.getSession();
                tx=session.beginTransaction();
                session.delete(cpa);
                tx.commit();
                session.close();
            }
            else
            {
                System.err.println("cpa is null in deleteCareplanAchievement");
            }
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
    }
    public void withdrawHousehold(String hhUniqueId,String dateOfWithdrawal,String reasonForWithdrawal,String type,int surveyNo) throws Exception
    {
        OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl();
        wdao.withdrawHousehold(hhUniqueId, dateOfWithdrawal, reasonForWithdrawal, type, surveyNo);
    }
    private void closeSession(Session session)
    {
        if(session !=null && session.isOpen())
        session.close();
    }
}
