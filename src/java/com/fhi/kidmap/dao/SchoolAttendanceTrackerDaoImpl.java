/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.business.SchoolAttendanceTracker;
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
public class SchoolAttendanceTrackerDaoImpl implements SchoolAttendanceTrackerDao
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
            //select count(distinct osa.ovcId) from HouseholdEnrollment hhe, Ovc ovc, ChildSchoolStatus osa where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=osa.ovcId and osa.ovcInSchool='Yes' and hhe.stateCode = 'CRS' and hhe.lgaCode = 'CAS' and hhe.partnerCode='SCI' and ((ovc.currentAge >=0 and ovc.currentAge <=100) or UPPER(ovc.currentAgeUnit) like '%MONTH%') and  UPPER(ovc.gender)='MALE' and UPPER(ovc.withdrawnFromProgram)='NO' and osa.ovcId not in (select distinct sat.ovcId from SchoolAttendanceTracker sat where sat.ovcId is not null and sat.dateOfAssessment between '2017-10-01' and '2018-9-30')
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            String query="select count(distinct sat.ovcId) "+util.getHouseholdQueryPart()+"Ovc ovc, SchoolAttendanceTracker sat where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=sat.ovcId "+additionalQuery+currentlyEnrolledQuery+util.getSchoolAttendanceTrackerDateOfAssessmentQuery(startDate, endDate);
           System.err.println("query in SchoolAttendanceTrackerDaoImpl.getNumberOfOvcRegularlyAttendingSchool is "+query);
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
            List list = session.createQuery(util.getHouseholdQueryPart()+"Ovc ovc, SchoolAttendanceTracker sat where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=sat.ovcId and sat.childMissSchool='No' "+additionalQuery+currentlyEnrolledQuery+util.getSchoolAttendanceTrackerDateOfAssessmentQuery(startDate, endDate)).list();
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
    public SchoolAttendanceTracker getSchoolAttendanceTracker(int recordId) throws Exception
    {
        SchoolAttendanceTracker sat=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from SchoolAttendanceTracker sat where sat.recordId=:id").setInteger("id", recordId).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                sat=(SchoolAttendanceTracker)list.get(0);
            }
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        
        return sat;
    }
    public SchoolAttendanceTracker getSchoolAttendanceTracker(String ovcId,String dateOfAssessment) throws Exception
    {
        SchoolAttendanceTracker sat=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from SchoolAttendanceTracker sat where sat.ovcId=:id and sat.dateOfAssessment=:doa").setString("id", ovcId).setDate("doa", DateManager.getDateInstance(dateOfAssessment)).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                sat=(SchoolAttendanceTracker)list.get(0);
            }
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        return sat;
    }
    public List getSchoolAttendanceTrackerRecords(String additionalQuery,String startDate,String endDate) throws Exception
    {
        List mainList = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(util.getHouseholdQueryPart()+"Ovc ovc, SchoolAttendanceTracker sat where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=sat.ovcId "+additionalQuery+util.getSchoolAttendanceTrackerDateOfAssessmentQuery(startDate, endDate)).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                List idList=new ArrayList();
                SchoolAttendanceTracker sat=null;
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    sat=(SchoolAttendanceTracker)objArray[2];
                    if(!idList.contains(sat.getRecordId()))
                    {
                        mainList.add(sat);
                        idList.add(sat.getRecordId());
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
    public List getAllSchoolAttendanceTrackerRecords() throws Exception
    {
        List mainList = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(util.getHouseholdQueryPart()+"Ovc ovc, SchoolAttendanceTracker sat where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=sat.ovcId ").list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                List idList=new ArrayList();
                SchoolAttendanceTracker sat=null;
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    sat=(SchoolAttendanceTracker)objArray[2];
                    if(!idList.contains(sat.getRecordId()))
                    {
                        mainList.add(sat);
                        idList.add(sat.getRecordId());
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
    public List getSchoolAttendanceTrackerRecordsPerCaregiver(String caregiverId) throws Exception
    {
        List mainList=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(util.getHouseholdQueryPart()+"Caregiver cgiver, SchoolAttendanceTracker sat where hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=sat.caregiverId  and sat.caregiverId=:id ").setString("id", caregiverId).list();
            tx.commit();
            closeSession(session);
           if(list !=null && !list.isEmpty())
            {
                List idList=new ArrayList();
                SchoolAttendanceTracker sat=null;
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    sat=(SchoolAttendanceTracker)objArray[2];
                    if(!idList.contains(sat.getRecordId()))
                    {
                        mainList.add(sat);
                        idList.add(sat.getRecordId());
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
    public List getSchoolAttendanceTrackerRecordsPerOvc(String ovcId) throws Exception
    {
        List mainList=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(util.getHouseholdQueryPart()+"Ovc ovc, SchoolAttendanceTracker sat where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=sat.ovcId  and sat.ovcId=:id ").setString("id", ovcId).list();
            tx.commit();
            closeSession(session);
           if(list !=null && !list.isEmpty())
            {
                List idList=new ArrayList();
                SchoolAttendanceTracker sat=null;
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    sat=(SchoolAttendanceTracker)objArray[2];
                    if(!idList.contains(sat.getRecordId()))
                    {
                        mainList.add(sat);
                        idList.add(sat.getRecordId());
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
    public void saveSchoolAttendanceTracker(SchoolAttendanceTracker sat) throws Exception
    {
        try
        {
            if(validateSchoolAttendanceTracker(sat))
            {
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
    public void updateSchoolAttendanceTracker(SchoolAttendanceTracker sat) throws Exception
    {
        try
        {
            if(validateSchoolAttendanceTracker(sat))
            {
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
    public void markedForDelete(SchoolAttendanceTracker sat) throws Exception
    {
        try
        {
            sat.setMarkedForDelete(NomisConstant.MARKEDFORDELETE);
            updateSchoolAttendanceTracker(sat);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void deleteSchoolAttendanceTracker(SchoolAttendanceTracker sat) throws Exception
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
    public void closeSession(Session session)
    {
        if(session !=null && session.isOpen())
        {
            session.close();
        }
    }
    private boolean validateSchoolAttendanceTracker(SchoolAttendanceTracker sat)
    {
        boolean validated=false;
        if(sat !=null)
        {
            if(sat.getChildMissSchool() !=null && sat.getDateOfAssessment() !=null && sat.getLastModifiedDate() !=null && sat.getOvcId() !=null && sat.getRecordedBy() !=null)
            {
                if(sat.getChildMissSchool().equalsIgnoreCase("No") || sat.getChildMissSchool().equalsIgnoreCase("Yes"))
                validated=true;
            }
        }
        return validated;
    }
}
