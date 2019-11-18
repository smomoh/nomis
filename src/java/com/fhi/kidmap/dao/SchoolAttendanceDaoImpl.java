/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.business.SchoolAttendance;
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
public class SchoolAttendanceDaoImpl implements SchoolAttendanceDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    DaoUtil util=new DaoUtil();
    public int getNumberOfOvcRegularlyAttendingSchool(String additionalQuery,String startDate,String endDate,boolean currentlyEnrolled) throws Exception
    {
        int count=0;
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("select count(distinct sa.ovcId) "+util.getHouseholdQueryPart()+"Ovc ovc, SchoolAttendance sa where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=sa.ovcId "+additionalQuery+currentlyEnrolledQuery+util.getSchoolAttendanceDateOfAssessmentQuery(startDate, endDate)).list();
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
            List list = session.createQuery(util.getHouseholdQueryPart()+"Ovc ovc, SchoolAttendance sa where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=sa.ovcId and sa.childMissSchool='No' "+additionalQuery+currentlyEnrolledQuery+util.getSchoolAttendanceDateOfAssessmentQuery(startDate, endDate)).list();
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
    public SchoolAttendance getSchoolAttendance(int recordId) throws Exception
    {
        SchoolAttendance sa=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from SchoolAttendance sa where sa.recordId=:id").setInteger("id", recordId).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                sa=(SchoolAttendance)list.get(0);
            }
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        
        return sa;
    }
    public SchoolAttendance getSchoolAttendance(String ovcId,String dateOfAssessment) throws Exception
    {
        SchoolAttendance sa=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from SchoolAttendance sa where sa.ovcId=:id and sa.dateOfAssessment=:doa").setString("id", ovcId).setDate("doa", DateManager.getDateInstance(dateOfAssessment)).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                sa=(SchoolAttendance)list.get(0);
            }
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        return sa;
    }
    public List getSchoolAttendanceRecords(String additionalQuery,String startDate,String endDate) throws Exception
    {
        List mainList = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(util.getHouseholdQueryPart()+"Ovc ovc, SchoolAttendance sa where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=sa.ovcId "+additionalQuery+util.getSchoolAttendanceDateOfAssessmentQuery(startDate, endDate)).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                List idList=new ArrayList();
                SchoolAttendance sa=null;
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    sa=(SchoolAttendance)objArray[2];
                    if(!idList.contains(sa.getRecordId()))
                    {
                        mainList.add(sa);
                        idList.add(sa.getRecordId());
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
    public List getAllSchoolAttendanceRecords() throws Exception
    {
        List mainList = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(util.getHouseholdQueryPart()+"Ovc ovc, SchoolAttendance sa where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=sa.ovcId ").list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                List idList=new ArrayList();
                SchoolAttendance sa=null;
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    sa=(SchoolAttendance)objArray[2];
                    if(!idList.contains(sa.getRecordId()))
                    {
                        mainList.add(sa);
                        idList.add(sa.getRecordId());
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
    public List getSchoolAttendanceRecordsPerCaregiver(String caregiverId) throws Exception
    {
        List mainList=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(util.getHouseholdQueryPart()+"Caregiver cgiver, SchoolAttendance sa where hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=sa.caregiverId  and sa.caregiverId=:id ").setString("id", caregiverId).list();
            tx.commit();
            closeSession(session);
           if(list !=null && !list.isEmpty())
            {
                List idList=new ArrayList();
                SchoolAttendance sa=null;
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    sa=(SchoolAttendance)objArray[2];
                    if(!idList.contains(sa.getRecordId()))
                    {
                        mainList.add(sa);
                        idList.add(sa.getRecordId());
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
    public List getSchoolAttendanceRecordsPerOvc(String ovcId) throws Exception
    {
        List mainList=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(util.getHouseholdQueryPart()+"Ovc ovc, SchoolAttendance sa where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=sa.ovcId  and sa.ovcId=:id ").setString("id", ovcId).list();
            tx.commit();
            closeSession(session);
           if(list !=null && !list.isEmpty())
            {
                List idList=new ArrayList();
                SchoolAttendance sa=null;
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    sa=(SchoolAttendance)objArray[2];
                    if(!idList.contains(sa.getRecordId()))
                    {
                        mainList.add(sa);
                        idList.add(sa.getRecordId());
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
    public void saveSchoolAttendance(SchoolAttendance sa) throws Exception
    {
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.save(sa);
            tx.commit();
            closeSession(session);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void updateSchoolAttendance(SchoolAttendance sa) throws Exception
    {
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.update(sa);
            tx.commit();
            closeSession(session);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void markedForDelete(SchoolAttendance sa) throws Exception
    {
        try
        {
            sa.setMarkedForDelete(NomisConstant.MARKEDFORDELETE);
            updateSchoolAttendance(sa);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void deleteSchoolAttendance(SchoolAttendance sa) throws Exception
    {
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.delete(sa);
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
            list = session.createQuery("from OvcSchoolAttendance osa where osa.ovcId like'%"+oldId+"%'").list();
            tx.commit();
            session.close();
            
         
            if(list !=null && !list.isEmpty())
            {
                //System.err.println("list size in changecsi is "+list.size());
                SchoolAttendance sa=null;
                for(int i=0; i<list.size(); i++)
                {
                    /*This is the csi record with the old ovc id*/
                    sa=(SchoolAttendance)list.get(i);
                    deleteSchoolAttendance(sa);
                    sa.setOvcId(newId);
                    saveSchoolAttendance(sa);
                                        
                    //System.err.println("csi.getOvcId() at "+i+" is "+csi.getOvcId());
                    /*check if a csi record with the new id already exist*/
                    /*if(this.getSchoolAttendance(oldId) ==null)
                    {
                        util.saveDeletedRecord(oldId,null, "osa",DateManager.convertDateToString(osa.getDateOfAssessment(), DateManager.DB_DATE_FORMAT));
                        //System.err.println("csi.getOvcId() "+csi.getOvcId()+" updated");
                    }*/
                   
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
