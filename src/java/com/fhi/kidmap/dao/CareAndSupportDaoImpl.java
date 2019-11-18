/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.CareAndSupportChecklist;
import com.fhi.kidmap.business.HivStatusUpdate;
import com.fhi.kidmap.business.Ovc;
import com.fhi.nomis.OperationsManagement.HivRecordsManager;
import com.fhi.nomis.nomisutils.AppUtility;
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
public class CareAndSupportDaoImpl implements CareAndSupportDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    DaoUtil util=new DaoUtil();
    AppUtility appUtil=new AppUtility();
    public int getNumberOfHIVPositiveOvcWhoReportedAdherenceToTreatmentWithinTheReportPeriod(String additionalQuery,String startDate,String endDate,boolean skippedARV,boolean currentlyEnrolled) throws Exception
    {
        int count=0;
        try
        {
            String currentlyEnrolledQuery="";
            if(currentlyEnrolled)
            currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
            String query="select count (distinct csc.clientId) from HouseholdEnrollment hhe, Ovc ovc, CareAndSupportChecklist csc,HivStatusUpdate hsu where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=csc.clientId and csc.clientId=hsu.clientId "+util.getEnrolledOnARTQueryCriteria(NomisConstant.OVC_TYPE)+util.getCareAndSupportDateOfAssessmentQuery(startDate, endDate)+util.getCareAndSupportSkippedARVQuery(skippedARV)+additionalQuery+currentlyEnrolledQuery;
            System.err.println(query);
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
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        return count;
    }
    public List getListOfHIVPositiveOvcWhoReportedAdherenceToTreatmentWithinTheReportPeriod(String additionalQuery,String startDate,String endDate,boolean skippedARV,boolean currentlyEnrolled) throws Exception
    {
        List mainList=new ArrayList();
        try
        {
            String currentlyEnrolledQuery="";
            if(currentlyEnrolled)
            currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
            String query="from HouseholdEnrollment hhe, Ovc ovc, CareAndSupportChecklist csc,HivStatusUpdate hsu where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=csc.clientId and csc.clientId=hsu.clientId "+util.getEnrolledOnARTQueryCriteria(NomisConstant.OVC_TYPE)+util.getCareAndSupportDateOfAssessmentQuery(startDate, endDate)+util.getCareAndSupportSkippedARVQuery(skippedARV)+additionalQuery+currentlyEnrolledQuery;
            System.err.println(query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(query).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                List idList=new ArrayList();
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    Ovc ovc=(Ovc)objArray[1];
                    if(!idList.contains(ovc.getOvcId()))
                    {
                        mainList.add(ovc);
                        idList.add(ovc.getOvcId());
                    }
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        return mainList;
    }
    public int getNumberOfNewlyTestedHIVPositiveOvcLinkedToTrestmentWithinTheReportPeriod(String additionalQuery,String startDate,String endDate,boolean currentlyEnrolled) throws Exception
    {
        int count=0;
        try
        {
            String currentlyEnrolledQuery="";
            if(currentlyEnrolled)
            currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
            String query="select count (distinct csc.clientId) from HouseholdEnrollment hhe, Ovc ovc, CareAndSupportChecklist csc,HivStatusUpdate hsu where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=csc.clientId and csc.clientId=hsu.clientId "+util.getEnrolledOnARTQueryCriteria(NomisConstant.OVC_TYPE)+util.getCareAndSupportDateEnrolledOnARTQuery(startDate, endDate)+additionalQuery+currentlyEnrolledQuery;
            System.err.println(query);
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
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        return count;
    }
    public List getListOfNewlyTestedHIVPositiveOvcLinkedToTrestmentWithinTheReportPeriod(String additionalQuery,String startDate,String endDate,boolean currentlyEnrolled) throws Exception
    {
        List mainList=new ArrayList();
        try
        {
            String currentlyEnrolledQuery="";
            if(currentlyEnrolled)
            currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
            String query="from HouseholdEnrollment hhe, Ovc ovc, CareAndSupportChecklist csc,HivStatusUpdate hsu where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=csc.clientId and csc.clientId=hsu.clientId "+util.getEnrolledOnARTQueryCriteria(NomisConstant.OVC_TYPE)+util.getCareAndSupportDateEnrolledOnARTQuery(startDate, endDate)+additionalQuery+currentlyEnrolledQuery;
            System.err.println(query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(query).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                System.err.println("list.size() is "+list.size());
                List idList=new ArrayList();
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    Ovc ovc=(Ovc)objArray[1];
                    if(!idList.contains(ovc.getOvcId()))
                    {
                        mainList.add(ovc);
                        idList.add(ovc.getOvcId());
                    }
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        return mainList;
    }
    public int getNumberOfOvcTestedAndReceivedResult(String additionalQuery,String startDate,String endDate,boolean currentlyEnrolled) throws Exception
    {
        int count=0;
        try
        {
            String currentlyEnrolledQuery="";
            if(currentlyEnrolled)
            currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
            String query="select count (distinct csc.clientId) from HouseholdEnrollment hhe, Ovc ovc, CareAndSupportChecklist csc where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=csc.clientId and csc.receivedTestResult='Yes'"+util.getCareAndSupportDateOfHivTestQuery(startDate, endDate)+additionalQuery+currentlyEnrolledQuery;
            System.err.println(query);
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
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        return count;
    }
    public List getListOfOvcTestedAndReceivedResult(String additionalQuery,String startDate,String endDate,boolean currentlyEnrolled) throws Exception
    {
        List mainList=new ArrayList();
        try
        {
            String currentlyEnrolledQuery="";
            if(currentlyEnrolled)
            currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
            String query="from HouseholdEnrollment hhe, Ovc ovc, CareAndSupportChecklist csc where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=csc.clientId and csc.receivedTestResult='Yes'"+util.getCareAndSupportDateOfHivTestQuery(startDate, endDate)+additionalQuery+currentlyEnrolledQuery;
            System.err.println(query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(query).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                List idList=new ArrayList();
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    Ovc ovc=(Ovc)objArray[1];
                    if(!idList.contains(ovc.getOvcId()))
                    {
                        mainList.add(ovc);
                        idList.add(ovc.getOvcId());
                    }
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        return mainList;
    }
    public List getCareAndSupportForExport(String additionalQuery,String startDate,String endDate,String reportOrder) throws Exception
    {
        List mainList=new ArrayList();
        try
        {
            String query="from HouseholdEnrollment hhe, Ovc ovc, CareAndSupportChecklist csc where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=csc.clientId "+util.getCareAndSupportLastModifiedDateQuery(startDate, endDate)+additionalQuery+" order by csc.clientId "+reportOrder;
            System.err.println(query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(query).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    mainList.add(objArray[2]);
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        return mainList;
    }
    public List getCareAndSupportChecklistByPeriod(String additionalQuery,String startDate,String endDate,String reportOrder) throws Exception
    {
        List mainList=new ArrayList();
        try
        {
            String query="from HouseholdEnrollment hhe, Ovc ovc, CareAndSupportChecklist csc where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=csc.clientId "+util.getCareAndSupportDateOfAssessmentQuery(startDate, endDate)+additionalQuery+" order by csc.clientId "+reportOrder;
            System.err.println(query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(query).list();
            tx.commit();
            closeSession(session);
            
            if(list !=null && !list.isEmpty())
            {
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    mainList.add(objArray[2]);
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        return mainList;
    }
    public CareAndSupportChecklist getCareAndSupportCheclist(int recordId) throws Exception
    {
        CareAndSupportChecklist csc=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from CareAndSupportChecklist csc where csc.recordId =:rid").setInteger("rid", recordId).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                csc=(CareAndSupportChecklist)list.get(0);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        return csc;
    }
    public CareAndSupportChecklist getCareAndSupportCheclist(String uniqueId, String dateOfAssessment) throws Exception
    {
        CareAndSupportChecklist csc=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from CareAndSupportChecklist csc where csc.clientId =:uid and csc.dateOfAssessment=:dt").setString("uid", uniqueId).setString("dt", dateOfAssessment).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                csc=(CareAndSupportChecklist)list.get(0);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        return csc;
    }
    public List getCareAndSupportCheclistByUniqueId(String clientId) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from CareAndSupportChecklist csc where csc.clientId =:uid order by csc.dateOfAssessment desc").setString("uid", clientId).list();
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
    public List getAllCareAndSupportCheclist() throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from CareAndSupportChecklist csc").list();
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
    public void saveCareAndSupportCheclist(CareAndSupportChecklist csc) throws Exception
    {
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.save(csc);
            tx.commit();
            closeSession(session);  
            saveOvcHIVStatus(csc);
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
    }
    public void updateCareAndSupportCheclist(CareAndSupportChecklist csc) throws Exception
    {
        try
        {
            CareAndSupportChecklist dupCsc=this.getCareAndSupportCheclist(csc.getClientId(), csc.getDateOfAssessment());
            if(dupCsc !=null)
            {
                csc.setRecordId(dupCsc.getRecordId());
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.update(csc);
                tx.commit();
                closeSession(session);  
                saveOvcHIVStatus(csc);
            }
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
    }
    public void markedForDelete(CareAndSupportChecklist csc) throws Exception
    {
        try
        {
            CareAndSupportChecklist dupCsc=this.getCareAndSupportCheclist(csc.getClientId(), csc.getDateOfAssessment());
            if(dupCsc !=null)
            {
                csc.setRecordId(dupCsc.getRecordId());
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                csc.setMarkedForDelete(NomisConstant.MARKEDFORDELETE);
                session.update(csc);
                tx.commit();
                closeSession(session);  
                saveOvcHIVStatus(csc);
            }  
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
    }
    public void deleteCareAndSupportCheclist(CareAndSupportChecklist csc) throws Exception
    {
        try
        {
            CareAndSupportChecklist dupCsc=this.getCareAndSupportCheclist(csc.getClientId(), csc.getDateOfAssessment());
            if(dupCsc !=null)
            {
                csc.setRecordId(dupCsc.getRecordId());
                csc.setRecordDeleted(1);
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.delete(csc);
                tx.commit();
                closeSession(session);
            }
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
    }
    public void saveOvcHIVStatus(CareAndSupportChecklist csc) throws Exception
    {
        HivStatusUpdateDao hsudao=new HivStatusUpdateDaoImpl();
        HivStatusUpdate hsu=new HivStatusUpdate();
        String dateOfCurrentHivStatus=csc.getDateOfHivTest();
        hsu.setClientEnrolledInCare(csc.getEnrolledOnTreatment());
        hsu.setEnrolledOnART(csc.getEnrolledOnTreatment());
        hsu.setClientId(csc.getClientId());
        hsu.setClientType(NomisConstant.OVC_TYPE);
        hsu.setDateModified(DateManager.getCurrentDate());
        hsu.setDateOfCurrentStatus(dateOfCurrentHivStatus);
        hsu.setHivStatus(HivRecordsManager.getHivStatusScode(csc.getHivStatus()));
        hsu.setRecordStatus("active");
        hsu.setPointOfUpdate("csc");
        hsu.setOrganizationClientIsReferred(csc.getTreatmentFacility());
        hsudao.saveHivStatusUpdate(hsu);
    }
    public void closeSession(Session session)
    {
        if(session !=null && session.isOpen())
        {
            session.close();
        }
    }
}
