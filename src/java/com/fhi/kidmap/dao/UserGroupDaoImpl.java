/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.UserGroup;
import com.fhi.nomis.nomisutils.AppUtility;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class UserGroupDaoImpl implements UserGroupDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    AppUtility appUtil=new AppUtility();
    DaoUtil util=new DaoUtil();
    public void saveUserGroup(UserGroup usg) throws Exception
    {
        if(usg !=null && getUserGroupByGroupName(usg.getGroupName()) ==null)
        {
            usg.setGroupId(getUniqueRecordId());
            session = HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.save(usg);
            tx.commit();
            closeSession(session); 
        }
    }
    public void updateUserGroup(UserGroup usg) throws Exception
    {
        if(usg !=null && getUserGroup(usg.getGroupId()) !=null)
        {
            UserGroup dupUsg=getUserGroupByGroupName(usg.getGroupName());
            if(dupUsg==null) 
            {
                //System.err.println("dupUsg is null");
                session = HibernateUtil.getSession();
                tx=session.beginTransaction();
                session.update(usg);
                tx.commit();
                closeSession(session);
            }
            else if(dupUsg.getGroupId().equalsIgnoreCase(usg.getGroupId()))
            {
                //System.err.println("usg.getOrgUnitGroup() is "+usg.getOrgUnitGroup());
                session = HibernateUtil.getSession();
                tx=session.beginTransaction();
                session.update(usg);
                tx.commit();
                closeSession(session);
            }
        }
    }
    public void deleteUserGroup(UserGroup usg) throws Exception
    {
        if(usg !=null && getUserGroup(usg.getGroupId()) !=null)
        {
            session = HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.delete(usg);
            tx.commit();
            closeSession(session);
        }
    }
    public UserGroup getUserGroupByGroupName(String groupName) throws Exception
    {
        UserGroup usg=null;
        List list = new ArrayList();
        try 
        {
            if(groupName !=null)
            groupName=groupName.trim();    
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from UserGroup usg where TRIM(usg.groupName)=:name").setString("name", groupName).list();
            tx.commit();
            closeSession(session);
        }
         catch (Exception ex)
         {
             closeSession(session);
             throw new Exception(ex);
         }
        if(list !=null && !list.isEmpty())
        {
            usg=(UserGroup)list.get(0);
        }
         return usg;
    }
    public UserGroup getUserGroup(String groupId) throws Exception
    {
        UserGroup usg=null;
        List list = new ArrayList();
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from UserGroup usg where usg.groupId=:id").setString("id", groupId).list();
            tx.commit();
            closeSession(session);
        }
         catch (Exception ex)
         {
             closeSession(session);
             throw new Exception(ex);
         }
        if(list !=null && !list.isEmpty())
        {
            usg=(UserGroup)list.get(0);
        }
         return usg;
    }
    public List getAllUserGroups() throws Exception
    {
        List list = new ArrayList();
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from UserGroup usg").list();
            tx.commit();
            closeSession(session);
        }
         catch (Exception ex)
         {
             closeSession(session);
             throw new Exception(ex);
         }
         return list;
    }
    public String getUniqueRecordId()
    {
        String uniqueId=appUtil.generateUniqueId();
        try
        {
            if(getUserGroup(uniqueId) !=null)
            getUniqueRecordId();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return uniqueId;
    }
    public void closeSession(Session session)
    {
        if(session !=null && session.isOpen())
        {
            session.close();
        }
    }
}
