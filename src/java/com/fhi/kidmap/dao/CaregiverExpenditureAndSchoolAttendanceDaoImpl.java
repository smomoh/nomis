/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.CaregiverExpenditureAndSchoolAttendance;
import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.nomis.OperationsManagement.SchoolAttendanceManager;
import com.fhi.nomis.nomisutils.AppUtility;
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
public class CaregiverExpenditureAndSchoolAttendanceDaoImpl implements CaregiverExpenditureAndSchoolAttendanceDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    DaoUtil util=new DaoUtil();
    AppUtility appUtil=new AppUtility();
    
    public List getRecordsWithOvcId(String additionalQuery) throws Exception
    {
        List reportList=new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(util.getHouseholdQueryPart()+" Caregiver cgiver, CaregiverExpenditureAndSchoolAttendance ceasa where hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=ceasa.caregiverId and ceasa.ovcId is not null)"+additionalQuery).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    reportList.add((CaregiverExpenditureAndSchoolAttendance)objArray[2]);
                }
            }
        }
         catch (Exception ex)
         {
            closeSession(session);
            throw new Exception(ex);
         }
        return reportList;
    }
    public int getNumberOfHouseholdsThatAreAbleToPayForUnexpectedExpenses(String additionalQuery,String startDate,String endDate,boolean currentlyEnrolled) throws Exception
    {
        int count=0;
        try
        {
            String currentlyEnrolledQuery="";
            if(currentlyEnrolled)
            currentlyEnrolledQuery=util.getHouseholdWithdrawnFromProgramQuery("No");
            String query="select count (distinct hhe.hhUniqueId) "+ util.getHouseholdQueryPart()+" Caregiver cgiver, CaregiverExpenditureAndSchoolAttendance ceasa where (hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=ceasa.caregiverId and ceasa.unexpectedExpenditure='Yes' and ceasa.accessMoney='Yes')"+util.getCaregiverExpenditureAndSchoolAttendanceDateOfAssessmentQuery(startDate, endDate) +additionalQuery+currentlyEnrolledQuery;
            //System.err.println("query in getNumberOfHouseholdsThatAreAbleToPayForUnexpectedExpenses is "+query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(query).list();
            //List list = session.createQuery("select count (distinct cgiver.hhUniqueId) "+ util.getHouseholdQueryPart()+" Caregiver cgiver, CaregiverExpenditureAndSchoolAttendance ceasa where hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=ceasa.caregiverId and ceasa.unexpectedExpenditure='Yes' and ceasa.accessMoney='Yes')"+util.getCaregiverExpenditureAndSchoolAttendanceDateOfAssessmentQuery(startDate, endDate) +additionalQuery+currentlyEnrolledQuery).list();
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
    public List getListOfHouseholdsThatAreAbleToPayForUnexpectedExpenses(String additionalQuery,String startDate,String endDate,boolean currentlyEnrolled) throws Exception
    {
        List reportList=new ArrayList();
        try
        {
            String currentlyEnrolledQuery="";
            if(currentlyEnrolled)
            currentlyEnrolledQuery=util.getHouseholdWithdrawnFromProgramQuery("No");
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(util.getHouseholdQueryPart()+" Caregiver cgiver, CaregiverExpenditureAndSchoolAttendance ceasa where hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=ceasa.caregiverId and ceasa.unexpectedExpenditure='Yes' and ceasa.accessMoney='Yes')"+util.getCaregiverExpenditureAndSchoolAttendanceDateOfAssessmentQuery(startDate, endDate) +additionalQuery+currentlyEnrolledQuery).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    reportList.add((HouseholdEnrollment)objArray[0]);
                }
            }
        }
         catch (Exception ex)
         {
            closeSession(session);
            throw new Exception(ex);
         }
        return reportList;
    }
    public void saveCaregiverExpenditureAndSchoolAttendance(CaregiverExpenditureAndSchoolAttendance ceasa) throws Exception
    {
        try
        {
            if(ceasa.getSchAttendance()==null)
            ceasa.setSchAttendance("N/A");
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.save(ceasa);
            tx.commit();
            closeSession(session);
            SchoolAttendanceManager.saveOrUpdateSchoolAttendanceTracker(ceasa);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void updateCaregiverExpenditureAndSchoolAttendance(CaregiverExpenditureAndSchoolAttendance ceasa) throws Exception
    {
        try
        {
            if(ceasa !=null)
            {
                CaregiverExpenditureAndSchoolAttendance ceasa2=getCaregiverExpenditureAndSchoolAttendance(ceasa.getCaregiverId(),ceasa.getDateOfAssessment());
                if(ceasa2 !=null)
                {
                    if(ceasa.getSchAttendance()==null)
                    ceasa.setSchAttendance("N/A");
                    ceasa.setRecordId(ceasa2.getRecordId()); 
                    session = HibernateUtil.getSession();
                    tx = session.beginTransaction();
                    session.update(ceasa);
                    tx.commit();
                    closeSession(session);
                    SchoolAttendanceManager.saveOrUpdateSchoolAttendanceTracker(ceasa);
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void markedForDelete(CaregiverExpenditureAndSchoolAttendance ceasa) throws Exception
    {
        try
        {
            if(ceasa !=null)
            {
                CaregiverExpenditureAndSchoolAttendance ceasa2=getCaregiverExpenditureAndSchoolAttendance(ceasa.getCaregiverId(),ceasa.getDateOfAssessment());
                if(ceasa2 !=null)
                {
                    ceasa.setRecordId(ceasa2.getRecordId());
                    ceasa.setMarkedForDelete(NomisConstant.MARKEDFORDELETE);
                    session = HibernateUtil.getSession();
                    tx = session.beginTransaction();
                    session.update(ceasa);
                    tx.commit();
                    closeSession(session);
                    SchoolAttendanceManager.saveOrUpdateSchoolAttendanceTracker(ceasa);
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void deleteCaregiverExpenditureAndSchoolAttendance(CaregiverExpenditureAndSchoolAttendance ceasa) throws Exception
    {
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.delete(ceasa);
            tx.commit();
            closeSession(session);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public CaregiverExpenditureAndSchoolAttendance getCaregiverExpenditureAndSchoolAttendance(String caregiverId,String dateOfAssessment) throws Exception
    {
        CaregiverExpenditureAndSchoolAttendance ceasa=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(util.getHouseholdQueryPart()+" Caregiver cgiver, CaregiverExpenditureAndSchoolAttendance ceasa where hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=ceasa.caregiverId and ceasa.caregiverId=:cgId and ceasa.dateOfAssessment=:doa )").setString("cgId", caregiverId).setString("doa", dateOfAssessment).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                Object[] objArray=(Object[])list.get(0);
                ceasa=(CaregiverExpenditureAndSchoolAttendance)objArray[2];
            }
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        return ceasa;
    }
    public CaregiverExpenditureAndSchoolAttendance getCaregiverExpenditureAndSchoolAttendance(int recordId) throws Exception
    {
        CaregiverExpenditureAndSchoolAttendance ceasa=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(util.getHouseholdQueryPart()+" Caregiver cgiver, CaregiverExpenditureAndSchoolAttendance ceasa where hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=ceasa.caregiverId and ceasa.recordId=:rid )").setInteger("rid", recordId).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                Object[] objArray=(Object[])list.get(0);
                ceasa=(CaregiverExpenditureAndSchoolAttendance)objArray[2];
            }
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        return ceasa;
    }
    public List getCaregiverExpenditureAndSchoolAttendanceList(String additionalQuery) throws Exception
    {
        List reportList=new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(util.getHouseholdQueryPart()+" Caregiver cgiver, CaregiverExpenditureAndSchoolAttendance ceasa where hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=ceasa.caregiverId)"+additionalQuery).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    reportList.add((CaregiverExpenditureAndSchoolAttendance)objArray[2]);
                }
            }
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        return reportList;
    }
    public void closeSession(Session session)
    {
        if(session !=null && session.isOpen())
        {
            session.close();
        }
    }
}
