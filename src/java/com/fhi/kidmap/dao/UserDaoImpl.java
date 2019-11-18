/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.User;
import com.fhi.nomis.logs.NomisLogManager;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author HP USER
 */
public class UserDaoImpl implements UserDao {

    Session session;
    Transaction tx;
    SessionFactory sessions;

public void updateUserGroupId() throws Exception 
{
    try 
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        List list=session.createQuery("from User user where user.userGroupId is null").list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            for(Object obj:list)
            {
                User user=(User)obj;
                if(user.getAssignedGroupId() !=null)
                {
                    System.err.println("user.getAssignedGroupId() is "+user.getAssignedGroupId());
                    user.setUserGroupId(user.getAssignedGroupId());
                    this.updateUser(user);
                }
            }
        }
    } 
    catch(Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }
        
}    
public List getUsersByUserGroupId(String userGrpId) throws Exception {
    List list=new ArrayList();
    try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createQuery("from User user where user.userGroupId=:grpId").setString("grpId",userGrpId).list();
           tx.commit();
        session.close();
        } 
        catch (HibernateException he) 
        {
            session.close();
            //throw new Exception(he);
        }
        catch(Exception ex)
        {
            session.close();
        }
        return list;
    }
public void saveUser(User user) throws Exception {
   try {
            if(user !=null)
          {
            User existingUser=getUser(user.getUsername());
            if(existingUser==null)
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.save(user);
                tx.commit();
                session.close();
            }
          }
        } 
        catch (HibernateException he) 
        {
            session.close();
            //throw new Exception(he);
        }
        catch(Exception ex)
        {
            session.close();
        }
}
    public User getUserByUserName(String username) throws Exception 
    {
        if(username !=null)
        username=username.trim();
        User user = null;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createQuery("from User user where TRIM(user.username)=:name").setString("name", username).list();

        tx.commit();
        session.close();
        } catch (HibernateException he)
        {
            session.close();
            //throw new Exception(he);
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        if(list !=null && !list.isEmpty())
        {
            user=(User)list.get(0);
        }
        return user;
    }
    public User getUser(String username) throws Exception 
    {
        User user = null;
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list=session.createQuery("from User user where user.username=:name").setString("name", username).list();
            //user = (User) session.load(User.class, username, LockMode.UPGRADE);
            tx.commit();
            session.close();
            if(list !=null && !list.isEmpty())
            {
                user=(User)list.get(0);
            }
        } 
        catch(Exception ex)
        {
            session.close();
        }
        return user;
    }
    public User getUser(String username,String pwd) throws Exception
    {
        List list=null;
        User localUser=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createQuery("from User user where user.username=:name and user.password=:pwd").setString("name", username).setString("pwd", pwd).list();

        tx.commit();
        session.close();
        } catch (HibernateException he)
        {
            session.close();
            //throw new Exception(he);
        }
        catch(Exception ex)
        {
            session.close();
        }
        if(list !=null && !list.isEmpty())
        {
            localUser=(User)list.get(0);
        }
        return localUser;
    }
    public List getUserByUserRole(String userRole) throws Exception {
    List list=new ArrayList();
    try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createQuery("from User user where user.userroleId=:user_role").setString("user_role",userRole).list();
           tx.commit();
        session.close();
        } catch (HibernateException he) 
        {
            session.close();
            //throw new Exception(he);
        }
        catch(Exception ex)
        {
            session.close();
        }
        return list;
    }
public List getUserInfo(String username) throws Exception {
    List list=new ArrayList();
    try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createQuery("from User user where user.username=:user_name").setString("user_name",username).list();
           tx.commit();
            session.close();
        } catch (HibernateException he) 
        {
            session.close();
            //throw new Exception(he);
        }
        catch(Exception ex)
        {
            session.close();
        }
        return list;
    }
public List getOldUsers() throws Exception 
{
    List list=new ArrayList();
    try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createQuery("from OldUser user").list();
           tx.commit();
        session.close();
        } catch (HibernateException he) 
        {
            session.close();
            //throw new Exception(he);
        }
        catch(Exception ex)
        {
            session.close();
        }
        return list;
    }
public List getAllUsers() throws Exception {
    List list=new ArrayList();
    try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createQuery("from User user").list();
           tx.commit();
        session.close();
        } 
        catch(Exception ex)
        {
            session.close();
            NomisLogManager.logStackTrace(ex);
        }
        return list;
    }
   public void updateUser(User user) throws Exception 
   {
        try 
        {
            if(user !=null)
            {
                //System.err.println("user.getUsername() is "+user.getUsername());
                User user2=getUser(user.getUsername());
                if(user2 !=null)
                {
                    //System.err.println(user.getUsername()+" with password "+user.getPassword()+"about to be saved");
                    if(user.getPassword()==null || user.getPassword().trim().length()==0)
                    {
                        user.setPassword(user2.getPassword());
                    }
                        session = HibernateUtil.getSession();
                        tx = session.beginTransaction();
                        session.update(user);
                        tx.commit();
                        session.close();
                        //System.err.println(user.getUsername()+" account updated");
                }
            }
        } 
        catch(Exception ex)
        {
            session.close();
            NomisLogManager.logStackTrace(ex);
        }
}
public void deleteUser(User user) throws Exception 
{
        try 
        {
            if(this.getUser(user.getUsername()) !=null)
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.delete(user);
                tx.commit();
                session.close();
            }
        } 
        catch(Exception ex)
        {
            NomisLogManager.logStackTrace(ex);
            session.close();
        }
}


}
