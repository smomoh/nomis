/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.ReferralDirectory;
import com.fhi.nomis.nomisutils.AppUtility;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class ReferralDirectoryDaoImpl implements ReferralDirectoryDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    DaoUtil util=new DaoUtil();
    AppUtility appUtil=new AppUtility();
    
    public String saveOrUpdateReferralDirectory(ReferralDirectory rd) throws Exception
    {
        String msg="Unable to save record";
        if(rd !=null)
        {
            
            ReferralDirectory rdByName=this.getReferralDirectoryByFacilityName(rd.getFacilityName());
            if(rd.getFacilityId()==null && rd.getFacilityName() !=null)
            {
                if(rdByName==null)
                {
                    rd.setFacilityId(generateFacilityId());
                    saveReferralDirectory(rd);
                    msg="record saved";
                }
            }
            else if(rd.getFacilityId() !=null && rd.getFacilityName() !=null)
            {
                ReferralDirectory rdById=getReferralDirectory(rd.getFacilityId());
                if(rdById ==null && rdByName ==null)
                {
                    saveReferralDirectory(rd);
                    msg="record saved";
                }
                else if(rdById !=null)
                {
                    updateReferralDirectory(rd);
                    msg="record updated";
                    /*if(rdByName ==null)
                    {
                        updateReferralDirectory(rd);
                        msg="record updated";
                    }
                    else
                    msg="Facility with same name already exist";*/
                }
            }
        }
        return msg;
    }
    public void saveReferralDirectory(ReferralDirectory rd) throws Exception
    {
        try
        {
            if(rd !=null)
            {
                if(rd.getFacilityName() !=null)
                {
                    ReferralDirectory rdByName=getReferralDirectoryByFacilityName(rd.getFacilityName());
                    System.err.println("Facility id is "+rd.getFacilityId());
                    if(rdByName==null)
                    {
                        AppUtility appUtil=new AppUtility();
                        if(rd.getFacilityId()==null || !appUtil.isString(rd.getFacilityId()))
                        rd.setFacilityId(generateFacilityId());
                        /*else if(getReferralDirectory(rd.getFacilityId()) !=null)
                        {
                            updateReferralDirectory(rd);
                            return;
                        }*/
                        saveReferralDirectoryOnly(rd);
                        //msg="record saved";
                    }
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    private void saveReferralDirectoryOnly(ReferralDirectory rd) throws Exception
    {
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.save(rd);
            tx.commit();
            closeSession(session);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void updateReferralDirectory(ReferralDirectory rd) throws Exception
    {
        try
        {
            if(rd !=null)
            {
                if(rd.getFacilityId() !=null && rd.getFacilityName() !=null)
                {
                    ReferralDirectory rdByName=this.getReferralDirectoryByFacilityName(rd.getFacilityName());
                    if(rdByName==null)
                    {
                        if(getReferralDirectory(rd.getFacilityId()) !=null)
                        {
                            updateReferralDirectoryOnly(rd);
                        }
                    }
                    else
                    {
                        if(rd.getFacilityId().equalsIgnoreCase(rdByName.getFacilityId()))
                        {
                            updateReferralDirectoryOnly(rd);
                        }
                    }
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    private void updateReferralDirectoryOnly(ReferralDirectory rd) throws Exception
    {
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.update(rd);
            tx.commit();
            closeSession(session); 
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void deleteReferralDirectory(ReferralDirectory rd) throws Exception
    {
        try
        {
            if(rd !=null && getReferralDirectory(rd.getFacilityId()) !=null)
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.delete(rd);
                tx.commit();
                closeSession(session);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public ReferralDirectory getReferralDirectory(String facilityId) throws Exception
    {
        ReferralDirectory rd=null;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from ReferralDirectory rd where rd.facilityId=:fid").setString("fid", facilityId).list();
            tx.commit();
            closeSession(session);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
       if(list !=null && !list.isEmpty())
       {
           rd=(ReferralDirectory)list.get(0);
       }
       return rd;
    }
    public List getAllReferralDirectories() throws Exception
    {
        List list=null;
       try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from ReferralDirectory rd order by rd.facilityName").list();
            tx.commit();
            closeSession(session);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
       return list;
    }
    public ReferralDirectory getReferralDirectoryByFacilityName(String facilityName) throws Exception
    {
       ReferralDirectory rd=null;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from ReferralDirectory rd where rd.facilityName=:fn").setString("fn", facilityName).list();
            tx.commit();
            closeSession(session);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
       if(list !=null && !list.isEmpty())
       {
           rd=(ReferralDirectory)list.get(0);
       }
       return rd; 
    }
    public List getReferralDirectories(String communityCode) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from ReferralDirectory rd where rd.community=:wd").setString("wd", communityCode).list();
            tx.commit();
            closeSession(session);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        return list;
    }
    public String generateFacilityId() throws Exception
    {
        String facilityId=appUtil.generateUniqueId(11);
        ReferralDirectory rd=getReferralDirectory(facilityId);
        while(rd !=null)
        {
            facilityId=appUtil.generateUniqueId(11);
            rd=getReferralDirectory(facilityId);
        }  
        return facilityId;
    }
    public void closeSession(Session session)
    {
        if(session !=null && session.isOpen())
        {
            session.close();
        }
    }
}
