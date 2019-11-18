/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.Caregiver;
import com.fhi.kidmap.business.ChildStatusIndex;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.DeletedRecord;
import com.fhi.kidmap.business.FollowupSurvey;
import com.fhi.kidmap.business.HivStatusUpdate;
import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.kidmap.business.HouseholdFollowupAssessment;
import com.fhi.kidmap.business.HouseholdReferral;
import com.fhi.kidmap.business.HouseholdService;
import com.fhi.kidmap.business.HouseholdVulnerabilityAssessment;
import com.fhi.kidmap.business.NutritionAssessment;
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.business.OvcReferral;
import com.fhi.kidmap.business.OvcService;
import com.fhi.kidmap.business.OvcWithdrawal;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class DeletedRecordDaoImpl implements DeletedRecordDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    AppUtility appUtil=new AppUtility();
    public void saveDeletedRecord(DeletedRecord dr) throws Exception
    {
        try 
        {
            if(getDeletedRecord(dr.getRecordId(),dr.getTypeOfRecord(),dr.getDateRecordCreated()) ==null)
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.save(dr);
                tx.commit();
                session.close();
            }
            
        }
        catch(Exception ex)
        {
            if(session !=null)
            session.close();
            ex.printStackTrace();
        }
    }
    public void removeDeletedRecord(DeletedRecord dr) throws Exception
    {
        DeletedRecord existingDr=getDeletedRecord(dr.getRecordId(),dr.getTypeOfRecord(),dr.getDateRecordCreated());
        if(existingDr !=null)
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.delete(existingDr);
            tx.commit();
            session.close();
        }
    }
    public void createDeletedRecord(String recordId,String newId,String typeOfRecord,String dateRecordCreated) throws Exception
    {
        DeletedRecord dr=new DeletedRecord();
        dr.setDateRecordCreated(dateRecordCreated);
        dr.setRecordId(recordId);
        dr.setNewUniqueId(newId);
        dr.setTypeOfRecord(typeOfRecord);
        dr.setDateRecordDeleted(appUtil.getCurrentDate());
        saveDeletedRecord(dr);
    }
    public DeletedRecord getDeletedRecord(String recordId,String typeOfRecord,String dateRecordCreated) throws Exception
    {
        DeletedRecord dr=null;
        List list = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DeletedRecord dr where dr.recordId=:rd and dr.typeOfRecord=:rt and dr.dateRecordCreated=:dr").setString("rd", recordId).setString("rt", typeOfRecord).setString("dr", dateRecordCreated).list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        if(list !=null && !list.isEmpty())
        {
            dr=(DeletedRecord)list.get(0);
        }
        return dr;
    }
    public List getDistinctPeriodsFromDateCreated(String startDateDeleted,String endDateDeleted) throws Exception
    {
        DaoUtil util=new DaoUtil();
        List periodList=new ArrayList();
        List list=getDistinctMthAndYrFromDateCreated(startDateDeleted,endDateDeleted);
        
        if(list !=null && !list.isEmpty())
        {
            String month=null;
            String year=null;
            for(int i=0; i<list.size(); i++)
            {
                if(list.get(i) !=null)
                {
                    Object[] obj=(Object[])list.get(i);
                    if(obj[0] !=null && obj[1] !=null)
                    {
                        month=obj[0].toString();
                        year=obj[1].toString();
                        //Create an array of four elements to be used to create the startdate and end endDate
                        String[] dateArray={month,year,month,year};
                        //get the start date and end date (i.e the first day of the month and the last day of the month) and add them to the list, 
                        periodList.add(util.getStartDate(dateArray));
                        periodList.add(util.getEndDate(dateArray));
                    }
                }
            }
        }
        return periodList;
    }
    public List getDistinctMthAndYrFromDateCreated(String startDate,String endDate) throws Exception
    {
        List list = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct MONTH(dr.dateRecordCreated),YEAR(dr.dateRecordCreated) from DeletedRecord dr where dr.dateRecordDeleted between '"+startDate+"' and '"+endDate+"'").list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        return list;
    }
    public List getDistinctDateCreated(String startDate,String endDate) throws Exception
    {
        List list = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct dr.dateRecordCreated from DeletedRecord dr where dr.dateRecordCreated between '"+startDate+"' and '"+endDate+"'").list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        return list;
    }
    public List getDeletedRecordByDateDeleted(String startDate,String endDate) throws Exception
    {
        List list = new ArrayList();
        if((startDate !=null && !startDate.equalsIgnoreCase("All")) && (endDate !=null && !endDate.equalsIgnoreCase("All")))
        {
            List periodList=getDistinctPeriodsFromDateCreated(startDate,endDate);
            if(periodList !=null)
            {
                for(int i=0; i<periodList.size(); i+=2)
                {
                    list.addAll(getRecordsByDateCreated(periodList.get(i).toString(),periodList.get(i+1).toString()));
                }
            }
        }
        else
        list=getAllDeletedRecord();
        return list;
    }
    public int getNumberOfRecordsByDateCreated(String startDateRecordCreated,String endDateRecordCreated) throws Exception
    {
        int count=0;
        List list = new ArrayList();
        if((startDateRecordCreated !=null && !startDateRecordCreated.equalsIgnoreCase("All")) && (endDateRecordCreated !=null && !endDateRecordCreated.equalsIgnoreCase("All")))
        {
            try
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                list = session.createQuery("select count(distinct dr.recordId) from DeletedRecord dr where dr.dateRecordCreated between '"+startDateRecordCreated+"' and '"+endDateRecordCreated+"'").list();
                tx.commit();
                session.close();
            }
            catch(Exception ex)
            {
                session.close();
                ex.printStackTrace();
            }
        }
        if(list !=null && !list.isEmpty())
        {
            count=(Integer.parseInt(list.get(0).toString()));
        }
        return count;
    }
    public List getRecordsByDateCreated(String query) throws Exception
    {
        List list = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(query).list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        
        return list;
    }
    public List getRecordsByDateCreated(String startDate,String endDate) throws Exception
    {
        List mainList = new ArrayList();
        List list = new ArrayList();
        if((startDate !=null && !startDate.equalsIgnoreCase("All")) && (endDate !=null && !endDate.equalsIgnoreCase("All")))
        {
            String query="from DeletedRecord dr where dr.dateRecordCreated between '"+startDate+"' and '"+endDate+"'";
            //System.err.println("Inside getRecordsByDateCreated: startDate is "+startDate+" and endDate is "+endDate+" number of records "+getNumberOfRecordsByDateCreated(startDate,endDate));
            int count=getNumberOfRecordsByDateCreated(startDate,endDate);
            //For memory management, if records more than 50000, split them by dateRecordCreated for that period
            if(count >50000)
            {
                System.err.println("Inside getRecordsByDateCreated: count= "+count);
                List dateList=getDistinctDateCreated(startDate,endDate);
                if(dateList !=null && !dateList.isEmpty())
                {
                    String dateRecordCreated=null;
                    for(int i=0; i<dateList.size(); i++)
                    {
                        dateRecordCreated=dateList.get(i).toString();
                        query="from DeletedRecord dr where dr.dateRecordCreated='"+dateRecordCreated+"'";
                        list=getRecordsByDateCreated(query);
                        mainList.addAll(list);
                        System.err.println("Inside getRecordsByDateCreated: "+query+", count on "+dateRecordCreated+" is "+list.size());
                    }
                }
            }
            else
            {
                list=getRecordsByDateCreated(query);
                mainList.addAll(list);
                System.err.println("Inside getRecordsByDateCreated: "+query+", count between "+startDate+" and "+endDate+" is "+list.size());
            }
            
        }
        return mainList;
    }
    public List getAllDeletedRecord() throws Exception
    {
        List list = new ArrayList();
                
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DeletedRecord dr").list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        return list;
    }
    public void removeDeletedRecord(String recordId, String dateRecordCreated,String recordType,boolean update) throws Exception
    {
        if(recordType==null)
        return;
        else if(recordType.equalsIgnoreCase("householdEnrollment"))
        {
            HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
            HouseholdEnrollment hhe=hhedao.getHouseholdEnrollment(recordId);
            if(hhe !=null)
            hhedao.deleteHouseholdEnrollment(hhe);
        }
        else if(recordType.equalsIgnoreCase("householdFollowupAssessment") || recordType.equalsIgnoreCase("hhFollowupAssessment"))
        {
            HouseholdFollowupAssessmentDao hhfadao=new HouseholdFollowupAssessmentDaoImpl();
            HouseholdFollowupAssessment hhfa=hhfadao.getHouseholdFollowupAssessmentByIdAndDate(recordId, dateRecordCreated);
            if(hhfa !=null)
            hhfadao.deleteHouseholdFollowupAssessment(hhfa);
        }
        else if(recordType.equalsIgnoreCase("followup"))
        {
            FollowupDao fudao=new FollowupDaoImpl();
            FollowupSurvey fs=fudao.getFollowup(recordId, dateRecordCreated);
            if(fs !=null)
            fudao.deleteFollowup(fs);
        }
        else if(recordType.equalsIgnoreCase("caregiver"))
        {
            CaregiverDao cgiverdao=new CaregiverDaoImpl();
            Caregiver cgiver=cgiverdao.getCaregiverByCaregiverId(recordId);
            if(cgiver !=null)
            cgiverdao.deleteCaregiver(cgiver);
        }
        else if(recordType.equalsIgnoreCase("csi"))
        {
            ChildStatusIndexDao csidao=new ChildStatusIndexDaoImpl();
            ChildStatusIndex csi=csidao.getChildStatusIndex(recordId, dateRecordCreated);
            if(csi !=null)
            csidao.deleteChildStatusIndex(csi);
        }
        else if(recordType.equalsIgnoreCase("hhreferral"))
        {
            HhReferralDao hhrefdao=new HhReferralDaoImpl();
            HouseholdReferral hhr=hhrefdao.getHouseholdReferral(recordId, dateRecordCreated);
            if(hhr !=null)
            hhrefdao.deleteHhReferral(hhr);
        }
        else if(recordType.equalsIgnoreCase("hivstatusupdate"))
        {
            HivStatusUpdateDao hsudao=new HivStatusUpdateDaoImpl();
            HivStatusUpdate hsu=hsudao.getHivStatusUpdatesByClientIdAndDateOfStatus(recordId, dateRecordCreated);
            if(hsu !=null)
            hsudao.deleteHivStatusUpdate(hsu);
        }
        else if(recordType.equalsIgnoreCase("householdService"))
        {
            HouseholdServiceDao hhsdao=new HouseholdServiceDaoImpl();
            HouseholdService hhs=hhsdao.getHouseholdService(recordId, dateRecordCreated);
            if(hhs !=null)
            hhsdao.deleteHouseholdService(hhs);
        }
        else if(recordType.equalsIgnoreCase("hhvulnerabilityAssessment"))
        {
            HouseholdVulnerabilityAssessmentDao hvadao=new HouseholdVulnerabilityAssessmentDaoImpl();
            HouseholdVulnerabilityAssessment hva=hvadao.getHouseholdVulnerabilityAssessment(recordId, dateRecordCreated);
            if(hva !=null)
            hvadao.deleteHouseholdVulnerabilityAssessment(hva);
        }
        else if(recordType.equalsIgnoreCase("nutritionAssessment"))
        {
            NutritionAssessmentDao nadao=new NutritionAssessmentDaoImpl();
            NutritionAssessment na=nadao.getNutritionAssessment(recordId, dateRecordCreated);
            if(na !=null)
            nadao.deleteNutritionAssessment(na);
        }
        else if(recordType.equalsIgnoreCase("ovc"))
        {
            OvcDao ovcdao=new OvcDaoImpl();
            Ovc ovc=ovcdao.getOvc(recordId);
            if(ovc !=null)
            ovcdao.deleteOvc(ovc);
        }
        else if(recordType.equalsIgnoreCase("ovcReferal"))
        {
            OvcReferralDao refdao=new OvcReferralDaoImpl();
            OvcReferral ref=refdao.getOvcReferral(recordId, dateRecordCreated);
            if(ref !=null)
            refdao.deleteOvcReferral(ref);
        }
        else if(recordType.equalsIgnoreCase("ovcService"))
        {
            OvcServiceDao servicedao=new OvcServiceDaoImpl();
            OvcService service=servicedao.getOvcServiceForTheMth(recordId, dateRecordCreated);
            if(service !=null)
            servicedao.deleteService(service);
        }
        else if(recordType.equalsIgnoreCase("ovcWithdrawal") && update)
        {
            OvcWithdrawalDao dao=new OvcWithdrawalDaoImpl();
            OvcWithdrawal withdrawal=dao.getOvcWithdrawal(recordId);
            if(withdrawal !=null)
            dao.deleteOvcWithdrawal(withdrawal);
        }
    }
}

