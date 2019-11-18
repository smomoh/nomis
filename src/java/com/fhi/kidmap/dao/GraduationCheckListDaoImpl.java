/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.GraduationCheckList;
import com.fhi.nomis.nomisutils.NomisConstant;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class GraduationCheckListDaoImpl implements GraduationCheckListDao
{
    Session session;
    Transaction tx;
    DaoUtil util=new DaoUtil();
    public void saveGraduationCheckListForImport(GraduationCheckList gcl) throws Exception
    {
        try
        {
            if(gcl !=null)
            {
                gcl.setGclscore(getGclScore(gcl));
                session=HibernateUtil.getSession();
                tx=session.beginTransaction();
                session.save(gcl);
                tx.commit();
                session.close();
            }
            else
            {
                System.err.println("gcl is null in saveGraduationCheckList");
            }
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
    }
    public void updateGraduationCheckListForImport(GraduationCheckList gcl) throws Exception
    {
        try
        {
            if(gcl !=null)
            {
                gcl.setGclscore(getGclScore(gcl));
                session=HibernateUtil.getSession();
                tx=session.beginTransaction();
                session.update(gcl);
                tx.commit();
                session.close();
            }
            else
            {
                System.err.println("gcl is null in updateGraduationCheckList");
            }
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
    }
    public void saveGraduationCheckList(GraduationCheckList gcl) throws Exception
    {
        try
        {
            if(gcl !=null)
            {
                gcl.setGclscore(getGclScore(gcl));
                session=HibernateUtil.getSession();
                tx=session.beginTransaction();
                session.save(gcl);
                tx.commit();
                session.close();
                if(gcl.getGraduated() !=null && gcl.getGraduated().equalsIgnoreCase("graduated"))
                withdrawHousehold(gcl.getClientId(),gcl.getDateOfAssessment(),gcl.getGraduated(),"household",0);
            }
            else
            {
                System.err.println("gcl is null in saveGraduationCheckList");
            }
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
    }
    public void updateGraduationCheckList(GraduationCheckList gcl) throws Exception
    {
        try
        {
            if(gcl !=null)
            {
                gcl.setGclscore(getGclScore(gcl));
                session=HibernateUtil.getSession();
                tx=session.beginTransaction();
                session.update(gcl);
                tx.commit();
                session.close();
                if(gcl.getGraduated() !=null && gcl.getGraduated().equalsIgnoreCase("graduated"))
                withdrawHousehold(gcl.getClientId(),gcl.getDateOfAssessment(),gcl.getGraduated(),"household",0);
            }
            else
            {
                System.err.println("gcl is null in updateGraduationCheckList");
            }
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
    }
    public void markedForDelete(GraduationCheckList gcl) throws Exception
    {
        try
        {
            gcl.setMarkedForDelete(NomisConstant.MARKEDFORDELETE);
            updateGraduationCheckList(gcl);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    public void deleteGraduationCheckList(GraduationCheckList gcl) throws Exception
    {
        try
        {
            if(gcl !=null)
            {
                session=HibernateUtil.getSession();
                tx=session.beginTransaction();
                session.delete(gcl);
                tx.commit();
                session.close();
            }
            else
            {
                System.err.println("gcl is null in deleteGraduationCheckList");
            }
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
    }
    public List getAllGraduationCheckLists() throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createQuery("from GraduationCheckList gcl").list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            closeSession(session);
        }
        return list;
    }
    public List getGraduationCheckLists(String additionalQueryCriteria) throws Exception
    {
        List list=null;
        List gclList=new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createQuery(util.getHouseholdQueryPart()+" GraduationCheckList gcl where hhe.hhUniqueId=gcl.clientId "+additionalQueryCriteria).list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
        }
        if(list !=null)
        {
            for(Object obj:list)
            {
                Object[] objArray=(Object[])obj;
                gclList.add(objArray[1]);
            }
        }
        return gclList;
    }
    public GraduationCheckList getGraduationCheckList(int id) throws Exception
    {
        GraduationCheckList gcl=null;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createQuery("from GraduationCheckList gcl where gcl.id=:uid").setInteger("uid", id).list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            closeSession(session);
        }
        if(list !=null && !list.isEmpty())
        {
           gcl=(GraduationCheckList)list.get(0); 
        }
        return gcl;
    }
    public GraduationCheckList getGraduationCheckList(String clientId) throws Exception
    {
        GraduationCheckList gcl=null;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createQuery("from GraduationCheckList gcl where gcl.clientId=:cid").setString("cid", clientId).list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            closeSession(session);
        }
        if(list !=null && !list.isEmpty())
        {
           gcl=(GraduationCheckList)list.get(0); 
        }
        return gcl;
    }
    public int getGclScore(GraduationCheckList gcl) throws Exception
    {
        int gclscore=0;
        if(gcl !=null)
        {
            if(gcl.getHealth() !=null && gcl.getHealth().equalsIgnoreCase("yes"))
            gclscore++;
            if(gcl.getSafety() !=null && gcl.getSafety().equalsIgnoreCase("yes"))
            gclscore++;
            if(gcl.getSchool() !=null && gcl.getSchool().equalsIgnoreCase("yes"))
            gclscore++;
            if(gcl.getStability() !=null && gcl.getStability().equalsIgnoreCase("yes"))
            gclscore++;
            if(gcl.getVulnerability() !=null && gcl.getVulnerability().equalsIgnoreCase("yes"))
            gclscore++;
        }
        return gclscore;
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
