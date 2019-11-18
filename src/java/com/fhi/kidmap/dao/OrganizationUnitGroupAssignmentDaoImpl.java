/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.OrganizationRecords;
import com.fhi.kidmap.business.OrganizationUnitGroupAssignment;
import com.fhi.kidmap.business.Wards;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author Siaka
 */
public class OrganizationUnitGroupAssignmentDaoImpl implements OrganizationUnitGroupAssignmentDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    AppUtility appUtil=new AppUtility();

    public void saveOrganizationUnitGroupAssignment(String[] orgUnitIdArray,String orgUnitGroupId,String parentOrgUnitGroupId) throws Exception
    {
        try
        {
            if(orgUnitIdArray !=null)
            {
                OrganizationUnitGroupAssignment ouga=null;
                for(int i=0; i<orgUnitIdArray.length; i++)
                {
                    System.err.println("parentOrgUnitGroupId "+parentOrgUnitGroupId);
                    ouga=new OrganizationUnitGroupAssignment();
                    ouga.setParentOrgUnitGroupId(parentOrgUnitGroupId);
                    ouga.setOrgUnitGroupId(orgUnitGroupId);
                    ouga.setOrgunitid(orgUnitIdArray[i]);
                    saveOrganizationUnitGroupAssignment(ouga);
                }
            }
        }
        catch(HibernateException hbe)
        {
            throw new Exception(hbe);
        }
    }
    public void saveOrganizationUnitGroupAssignment(OrganizationUnitGroupAssignment ouga) throws Exception
    {
        try
        {
            if(getOrganizationUnitGroupAssignment(ouga.getOrgunitid(),ouga.getOrgUnitGroupId())==null)
            {
                session=HibernateUtil.getSession();
                tx=session.beginTransaction();
                session.save(ouga);
                tx.commit();
                session.close();
            }
        }
        catch(HibernateException hbe)
        {
            throw new Exception(hbe);
        }
    }
    public void updateOrganizationUnitGroupAssignment(OrganizationUnitGroupAssignment ouga) throws Exception
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.update(ouga);
            tx.commit();
            session.close();
        }
        catch(HibernateException hbe)
        {
            throw new Exception(hbe);
        }
    }
    public void deleteOrganizationUnitGroupAssignmentByGroupId(String orgUnitGroupId) throws Exception
    {
        try
        {
            List list=getOrganizationUnitGroupAssignmentByGroupId(orgUnitGroupId);
            if(list !=null)
            {
                OrganizationUnitGroupAssignment ouga=null;
                for(int i=0; i<list.size(); i++)
                {
                    ouga=(OrganizationUnitGroupAssignment)list.get(i);
                    deleteOrganizationUnitGroupAssignment(ouga);
                }
            }
        }
        catch(HibernateException hbe)
        {
            throw new Exception(hbe);
        }
    }
    public void deleteOrganizationUnitGroupAssignment(OrganizationUnitGroupAssignment ouga) throws Exception
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.delete(ouga);
            tx.commit();
            session.close();
        }
        catch(HibernateException hbe)
        {
            throw new Exception(hbe);
        }
    }
    public OrganizationUnitGroupAssignment getOrganizationUnitGroupAssignmentByOrgUnitIdAndParentOrgUnitGroupId(String orgUnitId,String parentId) throws Exception
    {
        List list = null;
        OrganizationUnitGroupAssignment ouga=null;
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from OrganizationUnitGroupAssignment ouga where ouga.orgunitid=:ouId and ouga.parentOrgUnitGroupId=:parentId").setString("ouId", orgUnitId).setString("parentId", parentId).list();
            tx.commit();
            session.close();
        }
        catch (HibernateException he)
        {
            throw new Exception(he);
        }
        if(list !=null && !list.isEmpty())
        {
            ouga=(OrganizationUnitGroupAssignment)list.get(0);
        }
        return ouga;
    }
    public List getListOfOrgUnitGrpAssignmentByOrgUnitIdAndParentId(String orgUnitId,String parentId) throws Exception
    {
        List list = null;
        OrganizationUnitGroupAssignment ouga=null;
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from OrganizationUnitGroupAssignment ouga where ouga.orgunitid=:ouId and ouga.parentOrgUnitGroupId=:parentId").setString("ouId", orgUnitId).setString("parentId", parentId).list();
            tx.commit();
            session.close();
        }
        catch (HibernateException he)
        {
            throw new Exception(he);
        }
        
        return list;
    }
    public List getDistinctOrgUnitGroupIds() throws Exception
    {
        List list = null;
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct ouga.orgUnitGroupId from OrganizationUnitGroupAssignment ouga").list();
            tx.commit();
            session.close();
        }
        catch (HibernateException he)
        {
            throw new Exception(he);
        }
        return list;
    }
    public List getDistinctOrgUnitGroupIdByParentId(String parentOrgUnitGroupId) throws Exception
    {
        List list = null;
        String query="select distinct ouga.orgUnitGroupId from OrganizationUnitGroupAssignment ouga";
        if(parentOrgUnitGroupId !=null && !parentOrgUnitGroupId.equalsIgnoreCase("All"))
        query="select distinct ouga.orgUnitGroupId from OrganizationUnitGroupAssignment ouga where ouga.parentOrgUnitGroupId='"+parentOrgUnitGroupId+"'";
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(query).list();
            //list = session.createQuery("select distinct ouga.orgUnitGroupId from OrganizationUnitGroupAssignment ouga where ouga.parentOrgUnitGroupId=:pid").setString("pid", parentOrgUnitGroupId).list();
            tx.commit();
            session.close();
        }
        catch (HibernateException he)
        {
            throw new Exception(he);
        }
        return list;
    }
    public List getDistinctParentIdFromOrganizationUnitGroupAssignments() throws Exception
    {
        List list = null;
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct ouga.parentOrgUnitGroupId from OrganizationUnitGroupAssignment ouga").list();
            tx.commit();
            session.close();
        }
        catch (HibernateException he)
        {
            throw new Exception(he);
        }
        return list;
    }
    public List getDistinctOrgUnitGroupIdFromOrganizationUnitGroupAssignments() throws Exception
    {
        List list = null;
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct ouga.orgUnitGroupId from OrganizationUnitGroupAssignment ouga").list();
            tx.commit();
            session.close();
        }
        catch (HibernateException he)
        {
            throw new Exception(he);
        }
        return list;
    }
    public List getOrganizationUnitGroupAssignmentByGroupId(String orgUnitGroupId) throws Exception
    {
        List list = null;
        
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from OrganizationUnitGroupAssignment ouga where ouga.orgUnitGroupId=:groupId").setString("groupId", orgUnitGroupId).list();
            tx.commit();
            session.close();
        }
        catch (HibernateException he)
        {
            throw new Exception(he);
        }
        return list;   
    }
    public List getOrganizationUnitGroupAssignmentByOrgUnitId(String orgUnitId) throws Exception
    {
        List list = null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from OrganizationUnitGroupAssignment ouga where ouga.orgunitid=:ouId").setString("ouId", orgUnitId).list();
            tx.commit();
            session.close();
        }
        catch (HibernateException he)
        {
            throw new Exception(he);
        }
        return list;   
    }
    public OrganizationUnitGroupAssignment getOrganizationUnitGroupAssignment(String orgUnitId,String orgUnitGroupId) throws Exception
    {
        List list = null;
        OrganizationUnitGroupAssignment ouga=null;
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from OrganizationUnitGroupAssignment ouga where ouga.orgunitid=:ouId and ouga.orgUnitGroupId=:groupId").setString("ouId", orgUnitId).setString("groupId", orgUnitGroupId).list();
            tx.commit();
            session.close();
        }
        catch (HibernateException he)
        {
            throw new Exception(he);
        }
        if(list !=null && !list.isEmpty())
        {
            ouga=(OrganizationUnitGroupAssignment)list.get(0);
        }
        return ouga;
    }
    public OrganizationUnitGroupAssignment getOrganizationUnitGroupAssignmentByRecordId(int recordId) throws Exception
    {
        List list = null;
        OrganizationUnitGroupAssignment ouga=null;
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from OrganizationUnitGroupAssignment ouga where ouga.recordId=:rId").setInteger("rId", recordId).list();
            tx.commit();
            session.close();
        }
        catch (HibernateException he)
        {
            throw new Exception(he);
        }
        if(list !=null && !list.isEmpty())
        {
            ouga=(OrganizationUnitGroupAssignment)list.get(0);
        }
        return ouga;
    }
    public List getAllOrganizationUnitGroupAssignments() throws Exception
    {
        List list = null;
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from OrganizationUnitGroupAssignment ouga").list();
            tx.commit();
            session.close();
        }
        catch (HibernateException he)
        {
            throw new Exception(he);
        }
        return list;
    }//filterOrganizationUnits
    public List filterCommunityList(List communityList,String orgUnitGroupId) throws Exception
    {
        /*
            Organization unit group membership is mutually exclusive. If an organization unit belongs 
            to any other group aside this current group, it should not be loaded again.
         */
        List list=new ArrayList();
        if(communityList !=null && !communityList.isEmpty())
        {
            Wards ward=null;
            String orgUnitId=null;
            List ougaList=null;
            /*Iterate over the list of organization units provided*/
            for(int i=0; i<communityList.size(); i++)
            {
                ward=(Wards)communityList.get(i);
                orgUnitId=ward.getWard_code();
                /*Check if this organization unit already belong to a group*/
                ougaList=getOrganizationUnitGroupAssignmentByOrgUnitId(orgUnitId);
                if(ougaList !=null && !ougaList.isEmpty())
                {
                    /*If it belongs to the current group, load else do no load*/
                    if(getOrganizationUnitGroupAssignment(orgUnitId,orgUnitGroupId)!=null)
                    {
                        list.add(ward);
                    }
                }
                /*Load it if it does not belong to any group*/
                else
                list.add(ward);    
            }
        }
        return list;
    }
    public List filterOrganizationUnits(List orgList,String orgUnitGroupId) throws Exception
    {
        /*
            Organization unit group membership is mutually exclusive. If an organization unit belongs 
            to any other group aside this current group, it should not be loaded again.
         */
        List list=new ArrayList();
        if(orgList !=null && !orgList.isEmpty())
        {
            OrganizationRecords orgRecord=null;
            String orgUnitId=null;
            List ougaList=null;
            /*Iterate over the list of organization units provided*/
            for(int i=0; i<orgList.size(); i++)
            {
                orgRecord=(OrganizationRecords)orgList.get(i);
                orgUnitId=orgRecord.getOrgCode();
                /*Check if this organization unit already belong to a group*/
                ougaList=getOrganizationUnitGroupAssignmentByOrgUnitId(orgUnitId);
                if(ougaList !=null && !ougaList.isEmpty())
                {
                    /*If it belongs to the current group, load else do no load*/
                    if(getOrganizationUnitGroupAssignment(orgUnitId,orgUnitGroupId)!=null)
                    {
                        list.add(orgRecord);
                    }
                }
                /*Load it if it does not belong to any group*/
                else
                list.add(orgRecord);    
            }
        }
        return list;
    }
    /*public String generateUniqueId() throws Exception
    {
        String uniqueId=appUtil.generateUniqueId();
        OrganizationUnitGroupAssignment ouga=getOrganizationUnitGroupAssignment(uniqueId);
        if(ouga !=null)
        generateUniqueId();
        return uniqueId;
    }*/
}
