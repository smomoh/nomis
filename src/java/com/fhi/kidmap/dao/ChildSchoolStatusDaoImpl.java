/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.ChildSchoolStatus;
import com.fhi.kidmap.business.Ovc;
import com.fhi.nomis.nomisutils.DateManager;
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
public class ChildSchoolStatusDaoImpl implements ChildSchoolStatusDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    DaoUtil util=new DaoUtil();
    public int getNumberOfOvcCurrentlyOutOfSchool(String additionalQuery,boolean currentlyEnrolled) throws Exception
    {
        int count=0;
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
        try
        {
            String query="select count(distinct osa.ovcId) "+util.getHouseholdQueryPart()+"Ovc ovc, ChildSchoolStatus osa where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=osa.ovcId and osa.ovcInSchool='No'"+additionalQuery+currentlyEnrolledQuery;
            System.err.println("query in ChildStatusDaoImpl().getNumberOfOvcCurrentlyOutOfSchool is "+query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(query).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                count=Integer.parseInt(list.get(0).toString());
            }
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        return count;
    }
    public List getListOfOvcCurrentlyOutOfSchool(String additionalQuery,boolean currentlyEnrolled) throws Exception
    {
        List mainList = new ArrayList();
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
        try
        {
            String query=util.getHouseholdQueryPart()+"Ovc ovc, ChildSchoolStatus osa where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=osa.ovcId and osa.ovcInSchool ='No'"+additionalQuery+currentlyEnrolledQuery;
            System.err.println("query in ChildStatusDaoImpl().getListOfOvcCurrentlyOutOfSchool is "+query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(query).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                List idList=new ArrayList();
                Ovc ovc=null;
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    ovc=(Ovc)objArray[1];
                    if(!idList.contains(ovc.getOvcId()))
                    {
                        mainList.add(ovc);
                        idList.add(ovc.getOvcId());
                    }
                }
            }
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        return mainList;
    }
    public int getNumberOfOvcCurrentlyInSchool(String additionalQuery,boolean currentlyEnrolled) throws Exception
    {
        int count=0;
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
        try
        {
            String query="select count(distinct osa.ovcId) "+util.getHouseholdQueryPart()+"Ovc ovc, ChildSchoolStatus osa where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=osa.ovcId and osa.ovcInSchool='Yes'"+additionalQuery+currentlyEnrolledQuery;
            System.err.println("query in ChildSchoolStatusDaoImpl().getNumberOfOvcCurrentlyInSchool is "+query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(query).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                count=Integer.parseInt(list.get(0).toString());
            }
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        return count;
    }
    public List getListOfOvcCurrentlyInSchool(String additionalQuery,boolean currentlyEnrolled) throws Exception
    {
        List mainList = new ArrayList();
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
        try
        {
            String query=util.getHouseholdQueryPart()+"Ovc ovc, ChildSchoolStatus osa where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=osa.ovcId and osa.ovcInSchool='Yes'"+additionalQuery+currentlyEnrolledQuery;
            System.err.println("query in ChildSchoolStatusDaoImpl().getListOfOvcCurrentlyInSchool is "+query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(query).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                List idList=new ArrayList();
                Ovc ovc=null;
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    ovc=(Ovc)objArray[1];
                    if(!idList.contains(ovc.getOvcId()))
                    {
                        mainList.add(ovc);
                        idList.add(ovc.getOvcId());
                    }
                }
            }
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        return mainList;
    }
    /*public List getListOfOvcNotCurrentlyInSchool(String additionalQuery,boolean currentlyEnrolled) throws Exception
    {
        List mainList = new ArrayList();
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
        try
        {
            String query=util.getHouseholdQueryPart()+"Ovc ovc, ChildSchoolStatus osa where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=osa.ovcId and osa.ovcInSchool!='Yes'"+additionalQuery+currentlyEnrolledQuery;
            System.err.println("query in ChildSchoolStatusDaoImpl().getListOfOvcNotCurrentlyInSchool is "+query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(query).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                List idList=new ArrayList();
                Ovc ovc=null;
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    ovc=(Ovc)objArray[1];
                    if(!idList.contains(ovc.getOvcId()))
                    {
                        mainList.add(ovc);
                        idList.add(ovc.getOvcId());
                    }
                }
            }
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        return mainList;
    }*/
    public int getNumberOfOvcRegularlyAttendingSchool(String additionalQuery,String startDate,String endDate,boolean currentlyEnrolled) throws Exception
    {
        int count=0;
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
        try
        {
            String query="select count(distinct osa.ovcId) "+util.getHouseholdQueryPart()+"Ovc ovc, ChildSchoolStatus osa where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=osa.ovcId and osa.ovcInSchool='Yes'"+additionalQuery+currentlyEnrolledQuery+" and osa.ovcId not in (select distinct sat.ovcId from ChildSchoolStatusTracker sat where sat.ovcId is not null"+util.getSchoolAttendanceTrackerDateOfAssessmentQuery(startDate, endDate)+")";
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(query).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                count=Integer.parseInt(list.get(0).toString());
            }
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        return count;
    }
    public List getListOfOvcRegularlyAttendingSchool(String additionalQuery,String startDate,String endDate,boolean currentlyEnrolled) throws Exception
    {
        List mainList = new ArrayList();
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(util.getHouseholdQueryPart()+"Ovc ovc, ChildSchoolStatus osa where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=osa.ovcId and osa.ovcInSchool='Yes'"+additionalQuery+currentlyEnrolledQuery+" and osa.ovcId not in (select distinct sat.ovcId from ChildSchoolStatusTracker sat where sat.childMissSchool='No'"+util.getSchoolAttendanceTrackerDateOfAssessmentQuery(startDate, endDate)+")").list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                List idList=new ArrayList();
                Ovc ovc=null;
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    ovc=(Ovc)objArray[1];
                    if(!idList.contains(ovc.getOvcId()))
                    {
                        mainList.add(ovc);
                        idList.add(ovc.getOvcId());
                    }
                }
            }
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        return mainList;
    }
    public ChildSchoolStatus getChildSchoolStatus(String ovcId) throws Exception
    {
        ChildSchoolStatus sat=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from ChildSchoolStatus sat where sat.ovcId=:id").setString("id", ovcId).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                sat=(ChildSchoolStatus)list.get(0);
            }
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        
        return sat;
    }
    public ChildSchoolStatus getChildSchoolStatus(String ovcId,String dateOfAssessment) throws Exception
    {
        ChildSchoolStatus sat=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from ChildSchoolStatus sat where sat.ovcId=:id and sat.dateOfAssessment=:doa").setString("id", ovcId).setDate("doa", DateManager.getDateInstance(dateOfAssessment)).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                sat=(ChildSchoolStatus)list.get(0);
            }
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        return sat;
    }
    /*public List getChildSchoolStatusRecords(String additionalQuery,String startDate,String endDate) throws Exception
    {
        List mainList = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(util.getHouseholdQueryPart()+"Ovc ovc, ChildSchoolStatus sat where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=sat.ovcId "+additionalQuery+util.getChildSchoolStatusDateOfAssessmentQuery(startDate, endDate)).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                List idList=new ArrayList();
                ChildSchoolStatus sat=null;
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    sat=(ChildSchoolStatus)objArray[2];
                    if(!idList.contains(sat.getOvcId()))
                    {
                        mainList.add(sat);
                        idList.add(sat.getOvcId());
                    }
                }
            }
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        return mainList;
    }*/
    public List getAllChildSchoolStatusRecords() throws Exception
    {
        List mainList = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(util.getHouseholdQueryPart()+"Ovc ovc, ChildSchoolStatus sat where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=sat.ovcId ").list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                List idList=new ArrayList();
                ChildSchoolStatus sat=null;
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    sat=(ChildSchoolStatus)objArray[2];
                    if(!idList.contains(sat.getOvcId()))
                    {
                        mainList.add(sat);
                        idList.add(sat.getOvcId());
                    }
                }
            }
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        return mainList;
    }
    public List getChildSchoolStatusRecordsPerCaregiver(String caregiverId) throws Exception
    {
        List mainList=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(util.getHouseholdQueryPart()+"Ovc ovc, ChildSchoolStatus sat where hhe.hhUniqueId=cgiver.hhUniqueId and ovc.ovcId=sat.ovcId  and ovc.caregiverId=:id ").setString("id", caregiverId).list();
            tx.commit();
            closeSession(session);
           if(list !=null && !list.isEmpty())
            {
                List idList=new ArrayList();
                ChildSchoolStatus sat=null;
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    sat=(ChildSchoolStatus)objArray[2];
                    if(!idList.contains(sat.getOvcId()))
                    {
                        mainList.add(sat);
                        idList.add(sat.getOvcId());
                    }
                }
            }
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        return mainList;
    }
    public List getChildSchoolStatusRecordsPerOvc(String ovcId) throws Exception
    {
        List mainList=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(util.getHouseholdQueryPart()+"Ovc ovc, ChildSchoolStatus sat where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=sat.ovcId  and sat.ovcId=:id ").setString("id", ovcId).list();
            tx.commit();
            closeSession(session);
           if(list !=null && !list.isEmpty())
            {
                List idList=new ArrayList();
                ChildSchoolStatus sat=null;
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    sat=(ChildSchoolStatus)objArray[2];
                    if(!idList.contains(sat.getOvcId()))
                    {
                        mainList.add(sat);
                        idList.add(sat.getOvcId());
                    }
                }
            }
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        return mainList;
    }
    public void saveChildSchoolStatus(ChildSchoolStatus sat) throws Exception
    {
        try
        {
            if(sat !=null)
            {
                sat=getCleanedChildSchoolStatus(sat);
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.save(sat);
                tx.commit();
                closeSession(session);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    private ChildSchoolStatus getCleanedChildSchoolStatus(ChildSchoolStatus sat)
    {
        if(sat.getOvcInSchool() ==null || sat.getOvcInSchool().trim().length()<2)
        {
            if(sat.getCurrentSchoolName()==null || sat.getCurrentSchoolName().trim().length()<1)
            {
                sat.setOvcInSchool("No");
                sat.setCurrentClass(null);
            }
            else
            sat.setOvcInSchool("Yes");    
        }
        return sat;
    }
    public void updateChildSchoolStatus(ChildSchoolStatus sat) throws Exception
    {
        try
        {
            if(sat !=null && this.getChildSchoolStatus(sat.getOvcId()) !=null)
            {
                sat=getCleanedChildSchoolStatus(sat);
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.update(sat);
                tx.commit();
                closeSession(session);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void markedForDelete(ChildSchoolStatus sat) throws Exception
    {
        try
        {
            sat.setMarkedForDelete(NomisConstant.MARKEDFORDELETE);
            updateChildSchoolStatus(sat);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void deleteChildSchoolStatus(ChildSchoolStatus sat) throws Exception
    {
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.delete(sat);
            tx.commit();
            closeSession(session);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void changeOvcId(String oldId,String newId) throws Exception
    {
        List list = new ArrayList();
        if(oldId==null || newId==null)
        return;
        oldId=oldId.trim();
        newId=newId.trim();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from ChildSchoolStatus osa where osa.ovcId like'%"+oldId+"%'").list();
            tx.commit();
            session.close();
            
         
            if(list !=null && !list.isEmpty())
            {
                //System.err.println("list size in changecsi is "+list.size());
                ChildSchoolStatus osa=null;
                for(int i=0; i<list.size(); i++)
                {
                    /*This is the csi record with the old ovc id*/
                    osa=(ChildSchoolStatus)list.get(i);
                    deleteChildSchoolStatus(osa);
                    osa.setOvcId(newId);
                    saveChildSchoolStatus(osa);
                                        
                    //System.err.println("csi.getOvcId() at "+i+" is "+csi.getOvcId());
                    /*check if a csi record with the new id already exist*/
                    if(this.getChildSchoolStatus(oldId) ==null)
                    {
                        util.saveDeletedRecord(oldId,null, "osa",DateManager.convertDateToString(osa.getDateOfAssessment(), DateManager.DB_DATE_FORMAT));
                        //System.err.println("csi.getOvcId() "+csi.getOvcId()+" updated");
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
    public void closeSession(Session session)
    {
        if(session !=null && session.isOpen())
        {
            session.close();
        }
    }
}
