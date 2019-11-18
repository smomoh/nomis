/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.Caregiver;
import com.fhi.kidmap.business.HivStatusUpdate;
import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.kidmap.business.OvcWithdrawal;
import com.fhi.nomis.OperationsManagement.HivRecordsManager;
import com.fhi.nomis.nomisutils.DateManager;
import com.fhi.nomis.nomisutils.NomisConstant;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class CaregiverDaoImpl implements CaregiverDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    DaoUtil util=new DaoUtil();
    AppUtility appUtil=new AppUtility();
    String hhCaregiverQuery=util.getHouseholdQueryPart()+" Caregiver cgiver where hhe.hhUniqueId=cgiver.hhUniqueId ";
    String hhCaregiverHhFollowupQuery=util.getHouseholdQueryPart()+"Caregiver cgiver,HouseholdFollowupAssessment hhfa where hhe.hhUniqueId=cgiver.hhUniqueId and hhe.hhUniqueId=hhfa.hhUniqueId ";
    public int processHiVStatusForCaregiversWithoutHIVStatusRecordInHivStatusUpdate() throws Exception
    {
            int count=0;
            try
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                List list = session.createQuery("from HouseholdEnrollment hhe, Caregiver cgiver where hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId not in (select distinct hsu.clientId from HivStatusUpdate hsu)").list();
                tx.commit();
                closeSession(session);
               if(list !=null && !list.isEmpty())
               {
                   Caregiver cgiver=null;
                   for(Object obj:list)
                   {
                       Object[] objArray=(Object[])obj;
                       cgiver=(Caregiver)objArray[1];
                       saveCaregiverHIVStatus(cgiver);
                       count++;
                       System.err.println("Caregiver HIV status "+cgiver.getCaregiverId()+" status: "+cgiver.getCgiverHivStatus()+" processed in cgiverdao.processHiVStatusForCaregiversWithoutHIVStatusRecordInHivStatusUpdate()");
                   }
               }
            }
            catch (HibernateException he)
            {
                closeSession(session);
                he.printStackTrace();
            }
            catch(Exception ex)
            {
                closeSession(session);
            }
           return count;
      }
    public Caregiver getCaregiverByHhUniqueIdAndHhHeadStatus(String hhUniqueId,String householdHeadStatus) throws Exception
    {
        List list = new ArrayList();
        Caregiver c=null;
            try
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                list = session.createQuery("from Caregiver c where c.hhUniqueId =:hid and c.householdhead=:hh").setString("hid", hhUniqueId).setString("hh", householdHeadStatus).list();
                tx.commit();
                closeSession(session);
            }
            catch (HibernateException he)
            {
                closeSession(session);
                he.printStackTrace();
            }
            catch(Exception ex)
            {
                closeSession(session);
            }
           if(list !=null && !list.isEmpty())
           {
              c=(Caregiver)list.get(0);
           }
           return c;
    }
    public int updateCaregiverHivStatusWithCorrectLabel() throws Exception
    {
        int count=0;
               
        try 
        {
            String query="update CaregiverInfo set CGIVERHIVSTATUS ='unknown' where (UPPER(CGIVERHIVSTATUS) not like '%UNKNOWN%' and UPPER(CGIVERHIVSTATUS) not like '%NEGATIVE%' and UPPER(CGIVERHIVSTATUS) not like '%POSITIVE%')";
            
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            count=session.createSQLQuery(query).executeUpdate();
            tx.commit();
            closeSession(session);
            
        }
         catch (Exception ex)
         {
             closeSession(session);
             ex.printStackTrace();
         }
         return count;
    }
    public List getListOfCaregiversWithoutServiceRecords(String additionalQuery,String startDate,String endDate) throws Exception
{
    List list = new ArrayList();
    List cgiverNotServedList=new ArrayList();
    List uniqueCaregiverIdList=new ArrayList();
          
    try
    {
        String query=hhCaregiverQuery+additionalQuery+util.getCaregiverEndPeriodQuery(endDate);// and cgiver.caregiverId not in (select distinct hhs.caregiverId from HouseholdService hhs where hhs.serviceDate between '"+startDate+"' and '"+endDate+"')";
        //System.err.println("query in getListOfCaregiversWithoutServiceRecords is "+query);
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            //System.err.println("list size is "+list.size());
            String caregiverId=null;
            for(Object obj:list)
            {
                Object[] objArray=(Object[])obj;
                Caregiver cgiver=(Caregiver)objArray[1];
                if(cgiver !=null)
                {
                    caregiverId=cgiver.getCaregiverId();
                    if(uniqueCaregiverIdList.contains(caregiverId))
                    continue;
                    //if(!uniqueCaregiverIdList.contains(caregiverId))
                    //{
                        uniqueCaregiverIdList.add(caregiverId);
                        List cgiverList=util.getHouseholdServiceDaoInstance().getHouseholdServicesPerCaregiver(caregiverId, startDate, endDate);
                        if(cgiverList==null || cgiverList.isEmpty())
                        {
                          cgiverNotServedList.add(cgiver);  
                        }
                    //}
                }
            }
        }
    }
    catch(Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }
    return cgiverNotServedList;
}
public int getNoOfCaregiversWithoutServiceRecords(String additionalQuery,String startDate,String endDate) throws Exception
{
    List list = new ArrayList();
    int numberOfCaregivers=0;
    try
    {
        String query="select distinct cgiver.caregiverId "+hhCaregiverQuery+additionalQuery+util.getCaregiverEndPeriodQuery(endDate);
        //System.err.println("query in getNoOfCaregiversWithoutServiceRecords is "+query);
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            //System.err.println("list size in getNoOfCaregiversWithoutServiceRecords is "+list.size());
            String caregiverId=null;
            for(int i=0; i<list.size(); i++)
            {
                caregiverId=(String)list.get(i);
                List cgiverList=util.getHouseholdServiceDaoInstance().getHouseholdServicesPerCaregiver(caregiverId, startDate, endDate);
                if(cgiverList==null || cgiverList.isEmpty())
                {
                  numberOfCaregivers++;  
                }
            }
            //numberOfCaregivers=Integer.parseInt(list.get(0).toString());
        }
    }
    catch(Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }
    return numberOfCaregivers;
}
public List getListOfCaregiversCurrentlyEnrolledInCareOrOnART(String additionalQuery,String careStatus) throws Exception
{
    List cgiverList = new ArrayList();
    List idList = new ArrayList();
    String careStatusQuery=" ";
    if(careStatus.equalsIgnoreCase("Care"))
    careStatusQuery="and "+util.getHivPositiveEnrolledInCareStatusCriteria("Yes");
    else if(careStatus.equalsIgnoreCase("ART"))
    careStatusQuery=util.getHivPositiveEnrolledOnARTStatusCriteria("Yes");
    try
    {
        //System.err.println("query in getListOfCaregiversCurrentlyEnrolled is "+hhCaregiverQuery+" and cgiver.withdrawnFromProgram='No' "+additionalQuery);
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        List list = session.createQuery(util.getHouseholdQueryPart()+"Caregiver cgiver, HivStatusUpdate hsu where hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=hsu.clientId "+util.getActiveHivStatusCriteria(NomisConstant.HIV_POSITIVE,NomisConstant.Caregiver_TYPE)+careStatusQuery +additionalQuery).list();
        tx.commit();
        closeSession(session);
        if(list !=null)
        {
            for(Object obj:list)
            {
                Object[] objArr=(Object[])obj;
                Caregiver cgiver=(Caregiver)objArr[1];
                if(!idList.contains(cgiver.getCaregiverId()))
                {
                    idList.add(cgiver.getCaregiverId());
                    cgiverList.add(cgiver);
                }
            }
        }
    }
    catch (HibernateException he)
    {
        closeSession(session);
        he.printStackTrace();
    }
    catch(Exception ex)
    {
        closeSession(session);
        ex.printStackTrace();
    }

    return cgiverList;
}
public int getNoOfCaregiversCurrentlyEnrolledInCare(String additionalQuery) throws Exception
{
        List list = new ArrayList();
        int numberOfCaregivers=0;
        
        try
        {
            String query="select count(distinct cgiver.caregiverId)"+util.getHouseholdQueryPart()+"Caregiver cgiver, HivStatusUpdate hsu where hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=hsu.clientId "+util.getActiveHivStatusCriteria(NomisConstant.HIV_POSITIVE,NomisConstant.Caregiver_TYPE)+" and "+util.getHivPositiveEnrolledInCareStatusCriteria("Yes") +additionalQuery;
            System.err.println("query in getNoOfCaregiversCurrentlyEnrolledInCare is "+query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(query).list();
            tx.commit();
            session.close();
            if(list !=null && !list.isEmpty())
            {
                numberOfCaregivers=Integer.parseInt(list.get(0).toString());
            }
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        
        return numberOfCaregivers;
    }
public int getNoOfCaregiversCurrentlyEnrolledOnART(String additionalQuery) throws Exception
{
        int numberOfCaregivers=0;
        try
        {
            String query="select count(distinct cgiver.caregiverId)"+util.getHouseholdQueryPart()+"Caregiver cgiver, HivStatusUpdate hsu where hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=hsu.clientId "+util.getActiveHivStatusCriteria(NomisConstant.HIV_POSITIVE,NomisConstant.Caregiver_TYPE)+util.getHivPositiveEnrolledOnARTStatusCriteria("Yes") +additionalQuery;
            //System.err.println("query in getNoOfCaregiversCurrentlyEnrolledOnART is "+query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(query).list();
            tx.commit();
            session.close();
            if(list !=null && !list.isEmpty())
            {
                numberOfCaregivers=Integer.parseInt(list.get(0).toString());
            }
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        
        return numberOfCaregivers;
    }
    public int getNoOfCaregiversCurrentlyEnrolledBasedOnHivStatus(String additionalQuery,String hivStatus) throws Exception
    {
        List list = new ArrayList();
        int numberOfCaregivers=0;
        try
        {
            String query="select count(distinct cgiver.caregiverId)"+util.getHouseholdQueryPart()+"Caregiver cgiver, HivStatusUpdate hsu where hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=hsu.clientId "+util.getActiveHivStatusCriteria(hivStatus, NomisConstant.Caregiver_TYPE)+additionalQuery;
            System.err.println("query in getNoOfCaregiversCurrentlyEnrolledBasedOnHivStatus is "+query);
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
        if(list !=null && !list.isEmpty())
        {
            numberOfCaregivers=Integer.parseInt(list.get(0).toString());
        }
        return numberOfCaregivers;
    }
    public List getListOfCaregiversCurrentlyEnrolledBasedOnHivStatus(String additionalQuery,String hivStatus) throws Exception
    {
        List list = new ArrayList();
        List cgiverList = new ArrayList();
        List idList = new ArrayList();
        try
        {
            //System.err.println("query in getListOfCaregiversCurrentlyEnrolled is "+hhCaregiverQuery+" and cgiver.withdrawnFromProgram='No' "+additionalQuery);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(util.getHouseholdQueryPart()+"Caregiver cgiver, HivStatusUpdate hsu where hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=hsu.clientId "+util.getActiveHivStatusCriteria(hivStatus, NomisConstant.Caregiver_TYPE)+additionalQuery).list();
            tx.commit();
            closeSession(session);
        }
        catch (HibernateException he)
        {
            closeSession(session);
            he.printStackTrace();
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
        if(list !=null)
        {
            for(Object obj:list)
            {
                Object[] objArr=(Object[])obj;
                Caregiver cgiver=(Caregiver)objArr[1];
                if(!idList.contains(cgiver.getCaregiverId()))
                {
                    idList.add(cgiver.getCaregiverId());
                    cgiverList.add(cgiver);
                }
            }
        }
        return cgiverList;
}
public List getListOfHivPosCaregiverCurrentlyEnrolledBasedOnVulnerability(String additionalQuery,int startValue,int endValue) throws Exception
{
    List list = new ArrayList();
    List mainList = new ArrayList();
    List idList = new ArrayList();
        try
        {
            String query=util.getHouseholdQueryPart()+"HouseholdVulnerabilityAssessment hva, Caregiver cgiver, HivStatusUpdate hsu where hhe.hhUniqueId=hva.hhUniqueId and hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=hsu.clientId "+util.getActiveHivStatusCriteria(NomisConstant.HIV_POSITIVE,NomisConstant.Caregiver_TYPE)+"'"+additionalQuery+" and (hva.assessmentNo=1 and hva.vulnerabilityScore between "+startValue+" and "+endValue+") ";
            //System.err.println("query in getNumberOfHivPosCaregiverCurrentlyEnrolledBasedOnVulnerability is "+query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(query).list();
            tx.commit();
            session.close();
            if(list !=null && !list.isEmpty())
            {
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    Caregiver cgiver=(Caregiver)objArray[2];
                    if(!idList.contains(cgiver.getCaregiverId()))
                    {
                        mainList.add(objArray[2]);
                        idList.add(cgiver.getCaregiverId());
                    }
                }
            }
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        return mainList;
}
    public int getNumberOfHivPosCaregiverCurrentlyEnrolledBasedOnVulnerability(String additionalQuery,int startValue,int endValue) throws Exception
{
    List list = new ArrayList();
    int numberOfCaregivers=0;
        try
        {
            String query="select count(distinct cgiver.caregiverId)"+util.getHouseholdQueryPart()+"HouseholdVulnerabilityAssessment hva, Caregiver cgiver, HivStatusUpdate hsu where hhe.hhUniqueId=hva.hhUniqueId and hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=hsu.clientId "+util.getActiveHivStatusCriteria(NomisConstant.HIV_POSITIVE,NomisConstant.Caregiver_TYPE)+additionalQuery+" and (hva.assessmentNo=1 and hva.vulnerabilityScore between "+startValue+" and "+endValue+") ";
            //System.err.println("query in getNumberOfHivPosCaregiverCurrentlyEnrolledBasedOnVulnerability is "+query);
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
        if(list !=null && !list.isEmpty())
        {
            numberOfCaregivers=Integer.parseInt(list.get(0).toString());
        }
        return numberOfCaregivers;
}
public List getCaregiversWithYesWithdrawalStatus() throws Exception
{
    List list=null;
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("from Caregiver cgiver where cgiver.withdrawnFromProgram='Yes'").list();
        tx.commit();
        session.close();
        
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }
   return list;
}
public List getActiveCaregiversFromWithdrawalRecords() throws Exception
{
    List list=null;
    List listOfCaregivers=new ArrayList();
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("from Caregiver cgiver, OvcWithdrawal withdrawal where cgiver.caregiverId=withdrawal.ovcId and cgiver.withdrawnFromProgram='No'").list();
        tx.commit();
        session.close();
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }
    if(list !=null)
    {
        for(Object s:list)
        {
            Object[] obj=(Object[])s;
            listOfCaregivers.add(obj[0]);
        }
    }
    return listOfCaregivers;
}
    public List getCaregiversFromSameCommunity(String communityCode) throws Exception
    {
        String additionalQuery=" and hhe.communityCode='"+communityCode+"'";
        return getListOfCaregiversPerCohort(additionalQuery);
    }
    public List getHouseholdMembers(String caregiverId) throws Exception
    {
        List mainList=new ArrayList();
        List hheList=new ArrayList();
        List cgiverList=new ArrayList();
        List ovcMainList=new ArrayList();
        Caregiver cgiver=this.getCaregiverByCaregiverId(caregiverId);
        if(cgiver !=null)
        {
            cgiverList.add(cgiver);
            HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
            HouseholdEnrollment hhe=hhedao.getHouseholdEnrollment(cgiver.getHhUniqueId());
            if(hhe !=null)
            {
                hheList.add(hhe);
            }
                
                OvcDao ovcdao=new OvcDaoImpl();
               
                List ovcList=ovcdao.getOvcListPerCaregiver(caregiverId);
                if(ovcList !=null)
                ovcMainList.addAll(ovcList);
            
        }
        mainList.add(hheList);
        mainList.add(cgiverList);
        mainList.add(ovcMainList);
        return mainList;
    }
    public Caregiver setHouseholdheadStatus(Caregiver cgiver) throws Exception
    {
        try
        {
            HouseholdEnrollmentDao hheDao=new HouseholdEnrollmentDaoImpl();
            HouseholdEnrollment hhe=hheDao.getHouseholdEnrollmentByNameAndUniqueId(cgiver.getHhUniqueId(),cgiver.getCaregiverFirstname(),cgiver.getCaregiverLastName());
            if(hhe !=null)
            cgiver.setHouseholdhead("1");
            else
            cgiver.setHouseholdhead("0");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return cgiver;
    }
    public Caregiver getCaregiverWithUpdatedWithdrawalStatus(Caregiver cgiver)
    {
       try
       {
             if(cgiver !=null)
             {
                 OvcWithdrawal withdrawal=util.getOvcWithdrawalDaoInstance().getOvcWithdrawal(cgiver.getCaregiverId());
                 if(withdrawal !=null)
                 cgiver.setWithdrawnFromProgram("Yes");
                 else
                 cgiver.setWithdrawnFromProgram("No");                 
             } 
       }
       catch(Exception ex)
       {
           ex.printStackTrace();
       }
       return cgiver;
    }
    public void saveCaregiver(Caregiver cgiver) throws Exception
    {
        try
        {
            if(caregiverSavable(cgiver))
            {
                cgiver=setHouseholdheadStatus(cgiver);
                cgiver=setCaregiverId(cgiver);
                if(getCaregiverByCaregiverId(cgiver.getCaregiverId()) ==null)
                {
                    if(cgiver.getCgEnrollmentId()==null)
                    cgiver.setCgEnrollmentId(cgiver.getCaregiverId());
                    cgiver=getCaregiverWithUpdatedWithdrawalStatus(cgiver);
                    session = HibernateUtil.getSession();
                    tx = session.beginTransaction();
                    session.save(cgiver);
                    tx.commit();
                    closeSession(session);
                    saveCaregiverHIVStatus(cgiver);
                }
                else
                updateCaregiver(cgiver);
            }
        }
        catch (HibernateException he)
        {
            closeSession(session);
            he.printStackTrace();
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
    }
    private Caregiver setCaregiverId(Caregiver cgiver) throws Exception
    {
        if(cgiver !=null && (cgiver.getCaregiverId() ==null || cgiver.getCaregiverId().trim().length()==0))
        {
            if(cgiver.getHhUniqueId() !=null && cgiver.getHhUniqueId().trim().length()>0)
            {
                int i=1;
                String caregiverId=cgiver.getHhUniqueId()+i;
                Caregiver cgiver2=getCaregiverByCaregiverId(caregiverId);
                while(cgiver2 !=null)
                {
                    i++;
                    caregiverId=cgiver.getHhUniqueId()+i;
                    cgiver2=this.getCaregiverByCaregiverId(caregiverId);
                }
                cgiver.setCaregiverId(caregiverId);
            }
        }
        return cgiver;
    }
    public void saveCaregiverHIVStatus(Caregiver cgiver) throws Exception
    {
        //System.err.println("cgiver.getCgiverHivStatus() is "+cgiver.getCgiverHivStatus());
        HivStatusUpdateDao hsudao=new HivStatusUpdateDaoImpl();
        HivStatusUpdate hsu=new HivStatusUpdate();
        String dateOfCurrentHivStatus=cgiver.getDateOfCurrentHivStatus();
        if(dateOfCurrentHivStatus==null)
        dateOfCurrentHivStatus=cgiver.getDateOfEnrollment();
        hsu.setClientEnrolledInCare(cgiver.getEnrolledInCare());
        hsu.setEnrolledOnART(cgiver.getEnrolledOnART());
        hsu.setOrganizationClientIsReferred(cgiver.getFacilityId());
        hsu.setClientId(cgiver.getCaregiverId());
        hsu.setClientType(NomisConstant.Caregiver_TYPE);
        hsu.setDateModified(DateManager.getCurrentDate());
        hsu.setDateOfCurrentStatus(dateOfCurrentHivStatus);
        hsu.setHivStatus(HivRecordsManager.getHivStatusScode(cgiver.getCgiverHivStatus()));
        hsu.setRecordStatus("active");
        hsu.setPointOfUpdate(cgiver.getPointOfService());
        hsudao.saveHivStatusUpdate(hsu);
    }
    public boolean caregiverSavable(Caregiver cgiver) throws Exception
    {
        if(cgiver==null || cgiver.getCaregiverId()==null)    
        return false;
        if(cgiver.getDateOfEnrollment()==null || cgiver.getDateOfEnrollment().indexOf("-") ==-1)
        {
            //this may be from an old record, set the date of enrollment
            HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
            HouseholdEnrollment hhe=hhedao.getHouseholdEnrollment(cgiver.getHhUniqueId());
            if(hhe==null)
            return false;
            cgiver.setDateOfEnrollment(hhe.getDateOfAssessment());
        }
        if(cgiver.getDateModified()==null || cgiver.getDateModified().indexOf("-") ==-1)
          cgiver.setDateModified(appUtil.getCurrentDate());
        
        return true;
    }
    public void updateCaregiver(Caregiver cgiver) throws Exception
    {
        try
        {
            if(caregiverSavable(cgiver))
            {
                Caregiver cgiver2=getCaregiverByCaregiverId(cgiver.getCaregiverId());
                if(cgiver2 !=null)
                {
                    if(cgiver.getCgEnrollmentId()==null)
                    {
                        if(cgiver2.getCgEnrollmentId()!=null)
                        cgiver.setCgEnrollmentId(cgiver2.getCgEnrollmentId());
                        else
                        cgiver.setCgEnrollmentId(cgiver.getCaregiverId());   
                    }
                    cgiver=setHouseholdheadStatus(cgiver);
                    cgiver=getCaregiverWithUpdatedWithdrawalStatus(cgiver);
                    session = HibernateUtil.getSession();
                    tx = session.beginTransaction();
                    session.update(cgiver);
                    tx.commit();
                    closeSession(session);
                    saveCaregiverHIVStatus(cgiver);
                }
            }
        }
        catch (HibernateException he)
        {
            closeSession(session);
            he.printStackTrace();
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
    }
    public void markedForDelete(Caregiver cgiver) throws Exception
    {
        try
        {
            if(caregiverSavable(cgiver))
            {
                Caregiver cgiver2=getCaregiverByCaregiverId(cgiver.getCaregiverId());
                if(cgiver2 !=null)
                {
                    if(cgiver.getCgEnrollmentId()==null)
                    {
                        if(cgiver2.getCgEnrollmentId()!=null)
                        cgiver.setCgEnrollmentId(cgiver2.getCgEnrollmentId());
                        else
                        cgiver.setCgEnrollmentId(cgiver.getCaregiverId());   
                    }
                    cgiver=setHouseholdheadStatus(cgiver); 
                    cgiver.setMarkedForDelete(NomisConstant.MARKEDFORDELETE);
                    session = HibernateUtil.getSession();
                    tx = session.beginTransaction();
                    session.update(cgiver);
                    tx.commit();
                    closeSession(session);
                    saveCaregiverHIVStatus(cgiver);
                }
            }
        }
        catch (HibernateException he)
        {
            closeSession(session);
            he.printStackTrace();
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
    }
    public void deleteCaregiver(Caregiver cgiver) throws Exception
    {
        try
        {
            OvcDao dao=new OvcDaoImpl();
            if(cgiver !=null)
            {
                if(getCaregiverByCaregiverId(cgiver.getCaregiverId()) !=null)
                {
                    session = HibernateUtil.getSession();
                    tx = session.beginTransaction();
                    session.delete(cgiver);
                    tx.commit();
                    closeSession(session);
                    util.saveDeletedRecord(cgiver.getCaregiverId(), cgiver.getNewCaregiverId(),"caregiver", cgiver.getDateOfEnrollment());
                    HivRecordsManager.deleteAllClientHivStatusRecord(cgiver.getCaregiverId());
                    dao.changeCaregiverId(cgiver.getCaregiverId(), cgiver.getNewCaregiverId());
                }
            }
        }
        catch (HibernateException he)
        {
            closeSession(session);
            he.printStackTrace();
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
    }
    public void deleteAllCaregiversInHousehold(String hhUniqueId) throws Exception
   {
       List list=getListOfCaregiversFromSameHousehold(hhUniqueId);
        if(list !=null)
        {
            for(int i=0; i<list.size(); i++)
            {
                Caregiver cgiver=(Caregiver)list.get(i);
                deleteCaregiver(cgiver);
            }
        }
   }
    public void deleteAllCaregiverRecords(String caregiverId) throws Exception
    {
        
    }
    public int getNoOfCaregivers(String hhUniqueId) throws Exception
    {
        List list = new ArrayList();
        int count=0;
            try
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                list = session.createQuery("from Caregiver c where c.hhUniqueId =:hhId").setString("hhId", hhUniqueId).list();
                tx.commit();
                closeSession(session);
            }
            catch (HibernateException he)
            {
                closeSession(session);
                he.printStackTrace();
            }
            catch(Exception ex)
            {
                closeSession(session);
            }
            if(list !=null && !list.isEmpty())
            {
                count=list.size();
            }
        return count;
    }
    public List getDistinctCaregiverIdsByCommunity(String communityCode) throws Exception
    {
        List list = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct cgiver.caregiverId "+hhCaregiverQuery+" and hhe.communityCode=:cc").setString("cc", communityCode).list();
            tx.commit();
            closeSession(session);
        }
        catch (HibernateException he)
        {
            closeSession(session);
            he.printStackTrace();
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }   
        return list;
    }
    public List getDistinctCaregiverIds(String additionalQuery) throws Exception
    {
        List list = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct cgiver.caregiverId "+hhCaregiverQuery+additionalQuery).list();
            tx.commit();
            closeSession(session);
        }
        catch (HibernateException he)
        {
            closeSession(session);
            he.printStackTrace();
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }   
        return list;
    }
    public int getNoOfCaregiversCurrentlyEnrolled(String additionalQuery) throws Exception
    {
        List list = null;
        int count=0;
        try
        {
            String query="select count(distinct cgiver.caregiverId)"+util.getHouseholdQueryPart()+"Caregiver cgiver where hhe.hhUniqueId=cgiver.hhUniqueId" +additionalQuery+util.getCaregiverWithdrawnFromProgramQuery("No");
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(query).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                count=Integer.parseInt(list.get(0).toString());
            }
            /*OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl();
            list=getDistinctCaregiverIds(additionalQuery);
            List cgiverIdList=wdao.getListOfIdsNotWithdrawn(list);
            if(cgiverIdList !=null && !cgiverIdList.isEmpty())
            count=cgiverIdList.size();*/
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
        return count;
    }
    public List getListOfCaregiversCurrentlyEnrolled(String additionalQuery) throws Exception
    {
        List list = new ArrayList();
        List cgiverList = new ArrayList();
        try
        {
            //System.err.println("query in getListOfCaregiversCurrentlyEnrolled is "+hhCaregiverQuery+" and cgiver.withdrawnFromProgram='No' "+additionalQuery);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(hhCaregiverQuery+" and cgiver.withdrawnFromProgram='No' "+additionalQuery).list();
            tx.commit();
            closeSession(session);
        }
        catch (HibernateException he)
        {
            closeSession(session);
            he.printStackTrace();
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
        if(list !=null)
        {
            for(Object obj:list)
            {
                Object[] objArr=(Object[])obj;
                cgiverList.add(objArr[1]);
            }
        }
        return cgiverList;
    }
    public int getNoOfCaregiversPerCohort(String additionalQuery) throws Exception
    {
        List list = new ArrayList();
        int count=0;
            try
            {
                //System.err.println("select count(distinct cgiver.caregiverId) "+hhCaregiverQuery+additionalQuery);
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                list = session.createQuery("select count(distinct cgiver.caregiverId) "+hhCaregiverQuery+additionalQuery).list();
                tx.commit();
                closeSession(session);
            }
            catch (HibernateException he)
            {
                closeSession(session);
                he.printStackTrace();
            }
            catch(Exception ex)
            {
                closeSession(session);
            }
            if(list !=null && !list.isEmpty())
            {
                count=Integer.parseInt(list.get(0).toString());
            }
        return count;
    }
    public int getNoOfCaregiversFollowedupPerCohort(String additionalQuery) throws Exception
    {
        List list = new ArrayList();
        int count=0;
            try
            {
                //System.err.println("select count(distinct cgiver.caregiverId) "+hhCaregiverHhFollowupQuery+additionalQuery);
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                list = session.createQuery("select count(distinct cgiver.caregiverId) "+hhCaregiverHhFollowupQuery+additionalQuery).list();
                tx.commit();
                closeSession(session);
            }
            catch (HibernateException he)
            {
                closeSession(session);
                he.printStackTrace();
            }
            catch(Exception ex)
            {
                closeSession(session);
            }
            if(list !=null && !list.isEmpty())
            {
                count=Integer.parseInt(list.get(0).toString());
            }
        return count;
    }
    public int getNoOfHivPositiveCaregiversFollowedupPerCohort(String additionalQuery) throws Exception
    {
        List list = new ArrayList();
        int count=0;
            try
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                list = session.createQuery("select count(distinct cgiver.caregiverId) "+hhCaregiverHhFollowupQuery+additionalQuery+" and UPPER(cgiver.cgiverHivStatus) like '%POSITIVE%'").list();
                tx.commit();
                closeSession(session);
            }
            catch (HibernateException he)
            {
                closeSession(session);
                he.printStackTrace();
            }
            catch(Exception ex)
            {
                closeSession(session);
            }
            if(list !=null && !list.isEmpty())
            {
                count=Integer.parseInt(list.get(0).toString());
            }
        return count;
    }
    public int getNoOfHivNegativeCaregiversFollowedupPerCohort(String additionalQuery) throws Exception
    {
        List list = new ArrayList();
        int count=0;
            try
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                list = session.createQuery("select count(distinct cgiver.caregiverId) "+hhCaregiverHhFollowupQuery+additionalQuery+" and UPPER(cgiver.cgiverHivStatus) like '%NEGATIVE%'").list();
                tx.commit();
                closeSession(session);
            }
            catch (HibernateException he)
            {
                closeSession(session);
                he.printStackTrace();
            }
            catch(Exception ex)
            {
                closeSession(session);
            }
            if(list !=null && !list.isEmpty())
            {
                count=Integer.parseInt(list.get(0).toString());
            }
        return count;
    }
    public int getNoOfHivUnknownCaregiversFollowedupPerCohort(String additionalQuery) throws Exception
    {
        List list = new ArrayList();
        int count=0;
            try
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                list = session.createQuery("select count(distinct cgiver.caregiverId) "+hhCaregiverHhFollowupQuery+additionalQuery+" and (UPPER(cgiver.cgiverHivStatus) like '%UNKNOWN%' or (cgiver.cgiverHivStatus) is null or (cgiver.cgiverHivStatus)='' or (cgiver.cgiverHivStatus)=' ' or (cgiver.cgiverHivStatus)='  ' or (cgiver.cgiverHivStatus)='   ')").list();
                tx.commit();
                closeSession(session);
            }
            catch (HibernateException he)
            {
                closeSession(session);
                he.printStackTrace();
            }
            catch(Exception ex)
            {
                closeSession(session);
            }
            if(list !=null && !list.isEmpty())
            {
                count=Integer.parseInt(list.get(0).toString());
            }
        return count;
    }
    public List getListOfCaregiversPerCohort(String additionalQuery) throws Exception
    {
        List list = new ArrayList();
        List caregiverList = new ArrayList();
            try
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                list = session.createQuery(hhCaregiverQuery+additionalQuery).list();
                tx.commit();
                closeSession(session);
            }
            catch (HibernateException he)
            {
                closeSession(session);
                he.printStackTrace();
            }
            catch(Exception ex)
            {
                closeSession(session);
            }
            if(list !=null && !list.isEmpty())
            {
                Caregiver cgiver=null;
                List idList=new ArrayList();
                for(int i=0; i<list.size(); i++)
                {
                    Object[] obj=(Object[])list.get(i);
                    cgiver=(Caregiver)obj[1];
                    if(idList.contains(cgiver.getCaregiverId()))
                    continue;
                    caregiverList.add(cgiver);
                    idList.add(cgiver.getCaregiverId());
                }
            }
        return caregiverList;
    }
    public List getListOfCaregiversFollowedupPerCohort(String additionalQuery) throws Exception
    {
        List list = new ArrayList();
        List caregiverList = new ArrayList();
            try
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                list = session.createQuery(hhCaregiverHhFollowupQuery+additionalQuery).list();
                tx.commit();
                closeSession(session);
            }
            catch (HibernateException he)
            {
                closeSession(session);
                he.printStackTrace();
            }
            catch(Exception ex)
            {
                closeSession(session);
            }
            if(list !=null && !list.isEmpty())
            {
                Caregiver cgiver=null;
                List idList=new ArrayList();
                for(int i=0; i<list.size(); i++)
                {
                    Object[] obj=(Object[])list.get(i);
                    cgiver=(Caregiver)obj[1];
                    if(idList.contains(cgiver.getCaregiverId()))
                    continue;
                    caregiverList.add(cgiver);
                    idList.add(cgiver.getCaregiverId());
                }
            }
        return caregiverList;
    }
    public List getListOfHivPositiveCaregiversFollowedupPerCohort(String additionalQuery) throws Exception
    {
        List list = new ArrayList();
        List caregiverList = new ArrayList();
            try
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                list = session.createQuery(hhCaregiverHhFollowupQuery+additionalQuery+" and UPPER(cgiver.cgiverHivStatus) like '%POSITIVE%'").list();
                tx.commit();
                closeSession(session);
            }
            catch (HibernateException he)
            {
                closeSession(session);
                he.printStackTrace();
            }
            catch(Exception ex)
            {
                closeSession(session);
            }
            if(list !=null && !list.isEmpty())
            {
                Caregiver cgiver=null;
                List idList=new ArrayList();
                for(int i=0; i<list.size(); i++)
                {
                    Object[] obj=(Object[])list.get(i);
                    cgiver=(Caregiver)obj[1];
                    if(idList.contains(cgiver.getCaregiverId()))
                    continue;
                    caregiverList.add(cgiver);
                    idList.add(cgiver.getCaregiverId());
                }
            }
        return caregiverList;
    }
    public List getListOfHivNegativeCaregiversFollowedupPerCohort(String additionalQuery) throws Exception
    {
        List list = new ArrayList();
        List caregiverList = new ArrayList();
            try
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                list = session.createQuery(hhCaregiverHhFollowupQuery+additionalQuery+" and UPPER(cgiver.cgiverHivStatus) like '%NEGATIVE%'").list();
                tx.commit();
                closeSession(session);
            }
            catch (HibernateException he)
            {
                closeSession(session);
                he.printStackTrace();
            }
            catch(Exception ex)
            {
                closeSession(session);
            }
            if(list !=null && !list.isEmpty())
            {
                Caregiver cgiver=null;
                List idList=new ArrayList();
                for(int i=0; i<list.size(); i++)
                {
                    Object[] obj=(Object[])list.get(i);
                    cgiver=(Caregiver)obj[1];
                    if(idList.contains(cgiver.getCaregiverId()))
                    continue;
                    caregiverList.add(cgiver);
                    idList.add(cgiver.getCaregiverId());
                }
            }
        return caregiverList;
    }
    public List getListOfHivUnknownCaregiversFollowedupPerCohort(String additionalQuery) throws Exception
    {
        List list = new ArrayList();
        List caregiverList = new ArrayList();
            try
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                list = session.createQuery(hhCaregiverHhFollowupQuery+additionalQuery+" and (UPPER(cgiver.cgiverHivStatus) like '%UNKNOWN%' or (cgiver.cgiverHivStatus) is null or (cgiver.cgiverHivStatus)='' or (cgiver.cgiverHivStatus)=' ' or (cgiver.cgiverHivStatus)='  ' or (cgiver.cgiverHivStatus)='   ')").list();
                tx.commit();
                closeSession(session);
            }
            catch (HibernateException he)
            {
                closeSession(session);
                he.printStackTrace();
            }
            catch(Exception ex)
            {
                closeSession(session);
            }
            if(list !=null && !list.isEmpty())
            {
                Caregiver cgiver=null;
                List idList=new ArrayList();
                for(int i=0; i<list.size(); i++)
                {
                    Object[] obj=(Object[])list.get(i);
                    cgiver=(Caregiver)obj[1];
                    if(idList.contains(cgiver.getCaregiverId()))
                    continue;
                    caregiverList.add(cgiver);
                    idList.add(cgiver.getCaregiverId());
                }
            }
        return caregiverList;
    }
    public String generateCaregiverId(String hhUniqueId) throws Exception
    {
        Caregiver c=null;
        String caregiverId=null;
        if(hhUniqueId==null || hhUniqueId.indexOf("/")==-1)
        return hhUniqueId;
        hhUniqueId=hhUniqueId.trim();
        int count=getNoOfCaregivers(hhUniqueId);
        count++;
        caregiverId=hhUniqueId+"/"+count;
        c=getCaregiverByCaregiverId(caregiverId);
        while(c!=null)
        {
            count++;
            caregiverId=hhUniqueId+"/"+count;
            c=getCaregiverByCaregiverId(caregiverId);
        }  
        return caregiverId;
    }
    public Caregiver getCaregiverByCaregiverId(String caregiverId) throws Exception
    {
        List list = new ArrayList();
        Caregiver c=null;
            try
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                list = session.createQuery("from Caregiver c where c.caregiverId =:cid").setString("cid", caregiverId).list();
                tx.commit();
                closeSession(session);
            }
            catch (HibernateException he)
            {
                closeSession(session);
                he.printStackTrace();
            }
            catch(Exception ex)
            {
                closeSession(session);
            }
           if(list !=null && !list.isEmpty())
           {
              c=(Caregiver)list.get(0);
           }
           return c;
    }
    public Caregiver getCaregiverByName(String hhUniqueId,String firstName,String lastName) throws Exception
    {
        List list = new ArrayList();
        if(firstName !=null)
        firstName=firstName.toUpperCase();
        if(lastName !=null)
        lastName=lastName.toUpperCase();

        Caregiver c=null;
            try
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                list = session.createQuery("from Caregiver c where c.hhUniqueId =:hhId and (UPPER(c.caregiverFirstname) = :fName and UPPER(c.caregiverLastName) = :lName)")
                        .setString("hhId", hhUniqueId).setString("fName", firstName).setString("lName", lastName).list();
                tx.commit();
                closeSession(session);
            }
            catch (HibernateException he)
            {
                closeSession(session);
                he.printStackTrace();
            }
            catch(Exception ex)
            {
                closeSession(session);
            }
        if(list !=null && !list.isEmpty())
        {
            c=(Caregiver)list.get(0);
        }
            return c;
    }
    public Caregiver getCaregiverByHhUniqueIdAndCaregiverId(String hhUniqueId,String caregiverId) throws Exception
    {
        List list = new ArrayList();
        Caregiver cgiver=null;
            try
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                list = session.createQuery("from Caregiver cgiver where cgiver.hhUniqueId =:hhId and cgiver.caregiverId=:cgiverId").setString("hhId", hhUniqueId).setString("cgiverId", caregiverId).list();
                tx.commit();
                closeSession(session);
            }
            catch (HibernateException he)
            {
                closeSession(session);
                he.printStackTrace();
            }
            catch(Exception ex)
            {
                closeSession(session);
            }
        if(list !=null && !list.isEmpty())
        {
            cgiver=(Caregiver)list.get(0);
        }
            return cgiver;
    }
    public List searchCaregiversFromTheSameHouseholdByPartsOfName(String hhUniqueId,String searchString) throws Exception
    {
        if(searchString !=null)
        searchString=searchString.toUpperCase();
        List list = new ArrayList();
            try
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                list = session.createQuery("from Caregiver c where c.hhUniqueId =:hhId and (UPPER(c.caregiverFirstname) like '%"+searchString+"%' or UPPER(c.caregiverLastName) like '%"+searchString+"%')").setString("hhId", hhUniqueId).list();
                tx.commit();
                closeSession(session);
            }
            catch (HibernateException he)
            {
                closeSession(session);
                he.printStackTrace();
            }
            catch(Exception ex)
            {
                closeSession(session);
            }
            return list;
    }
    public List getListOfCaregiversFromSameHousehold(String hhUniqueId) throws Exception
    {
        List list = null;
        List caregiverList = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            String query="From Caregiver cgiver where cgiver.hhUniqueId=:id order by cgiver.caregiverFirstname";
            list = session.createQuery(query).setString("id", hhUniqueId).list();
        tx.commit();
        closeSession(session);
        }
         catch (HibernateException he)
         {
             closeSession(session);
            throw new Exception(he);
         }
        for(Object s:list)
        {
            caregiverList.add((Caregiver)s);
        }
        return caregiverList;
    }
    public List getListOfCaregivers(String additionalQuery) throws Exception
    {
        List list = null;
        List caregiverList = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            String query=util.getHouseholdQueryPart()+"Caregiver cgiver where hhe.hhUniqueId=cgiver.hhUniqueId "+additionalQuery+" order by cgiver.caregiverId";
            System.err.println("query in getListOfCaregivers is "+query);
            list = session.createQuery(query).list();
        tx.commit();
        closeSession(session);
        }
         catch (HibernateException he)
         {
             closeSession(session);
            throw new Exception(he);
         }
        for(Object s:list)
        {
            Object[] obj=(Object[])s;
            caregiverList.add((Caregiver)obj[1]);
        }
        return caregiverList;
    }
    public void closeSession(Session session)
    {
        if(session !=null && session.isOpen())
        {
            session.close();
        }
    }
    public Caregiver getCaregiverInstance(String caregiverId)
    {
        Caregiver cgiver=null;
        try
        {
            cgiver=getCaregiverByCaregiverId(caregiverId);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return cgiver;
    }
     public void changeCaregiverId(String oldId,String newId) throws Exception
    {
        
        if(newId==null || newId.indexOf("/")==-1)
        return;
        try
        {
            Caregiver cgiver=this.getCaregiverByCaregiverId(oldId);
            String newCaregiverId=newId;
            if(cgiver !=null)
            {
                HouseholdServiceDao hhsdao=new HouseholdServiceDaoImpl();
                OvcDao dao=new OvcDaoImpl();
                String[] newIdArray=newId.split("/");
                String hhUniqueId=newId.substring(0, newId.lastIndexOf("/"));
                if(newIdArray !=null)
                {
                    if(newIdArray.length<5)
                    hhUniqueId=newId.substring(0, newId.length()-1);
                }
                
                System.err.println("hhUniqueId in cgiverdao.changeCaregiverId is "+hhUniqueId);
                Caregiver newCgiver=this.getCaregiverByCaregiverId(newId);
                if(newCgiver !=null)
                {
                    //if Caregiver exist with new Id, change the Id
                    newCaregiverId=generateCaregiverId(hhUniqueId);  
                }
                cgiver.setCaregiverId(newCaregiverId);
                cgiver.setHhUniqueId(hhUniqueId);
                saveCaregiver(cgiver);
                
                cgiver.setCaregiverId(oldId);
                cgiver.setNewCaregiverId(newCaregiverId);
                deleteCaregiver(cgiver);
                hhsdao.changeCaregiverId(oldId,hhUniqueId, newCaregiverId);
                //Remove this Caregiver from all the OVC assigned
                dao.changeCaregiverId(oldId, newCaregiverId);
            }
        }
        catch (Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
      
    }
}
