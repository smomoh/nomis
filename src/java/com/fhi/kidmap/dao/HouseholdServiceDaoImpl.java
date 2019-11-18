/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.Caregiver;
import com.fhi.kidmap.business.HivStatus;
import com.fhi.kidmap.business.HivStatusUpdate;
import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.kidmap.business.HouseholdService;
import com.fhi.nomis.OperationsManagement.HivRecordsManager;
import com.fhi.nomis.nomisutils.DateManager;
import com.fhi.nomis.nomisutils.NomisConstant;
import com.fhi.nomis.nomisutils.OvcServiceManager;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Siaka
 */
public class HouseholdServiceDaoImpl implements HouseholdServiceDao
{
    Session session;
    Transaction tx;
    DaoUtil util=new DaoUtil();
    AppUtility appUtil=new AppUtility();
    HouseholdEnrollmentDao hheDao=new HouseholdEnrollmentDaoImpl();
    CaregiverDao cgiverDao=new CaregiverDaoImpl();
public int getNoOfCaregiversNewlyEnrolledAndServedWithinTheReportPeriod(String additionalQuery,String sex,String startDate,String endDate) throws Exception
{
    int count=0;
     try 
     {
        //String malesql="select count(distinct hhs.caregiverId) from HouseholdEnrollment hhe, Caregiver cgiver, HouseholdService hhs where hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=hhs.caregiverId and "+util.getCaregiverGenderCriteria(maleGender)+enrollmentQuery+withdrawnFromProgramQuery+" "+additionalServiceQuery;
        String query="select count(distinct hhs.caregiverId) from HouseholdEnrollment hhe, Caregiver cgiver, HouseholdService hhs where hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=hhs.caregiverId and "+util.getCaregiverGenderCriteria(sex)+additionalQuery+util.getCaregiverPeriodCriteria(startDate, endDate)+util.getHouseholdServiceDateQuery(startDate, endDate);
        System.err.println(query);
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        List list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            count=Integer.parseInt(list.get(0).toString());
        }
    } 
    catch (Exception ex) 
    {
        session.close();
        ex.printStackTrace();
    } 
    return count;
}
public List getListOfHouseholdServiceRecordsMalformedHhUniqueId() throws Exception
{
    List list=null;
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("from HouseholdService hhs where hhs.caregiverId is not null and SUBSTR(hhs.caregiverId, 1,17) !=hhs.hhUniqueId").list();
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
public List getListOfHouseholdServiceRecordsWithoutCaregiverId() throws Exception
{
    List list=null;
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("from HouseholdService hhs where hhs.caregiverId not like '%/%'").list();
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
public int getNumberOfCaregiversTestedForHiv(String additionalQuery,String startDate,String endDate) throws Exception
{
    int numberOfCaregivers=0;
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query="select count (distinct hhs.caregiverId) "+util.getHouseholdCaregiverServiceReportQueryPart("HouseholdService") +additionalQuery+util.getHouseholdServiceTestedForHivQueryPart()+util.getHouseholdServiceDateQuery(startDate,endDate);
        System.err.println(query);
        List list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            numberOfCaregivers=Integer.parseInt(list.get(0).toString());
        }
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }
    return numberOfCaregivers;
}
public List getListOfCaregiversTestedForHiv(String additionalQuery,String startDate,String endDate) throws Exception
{
    List mainList=new ArrayList();
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query=util.getHouseholdCaregiverServiceReportQueryPart("HouseholdService") +additionalQuery+util.getHouseholdServiceTestedForHivQueryPart()+util.getHouseholdServiceDateQuery(startDate,endDate);
        System.err.println(query);
        List list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            mainList.addAll(list);
            /*HouseholdService hhs=null;
            List cgiverIdList=new ArrayList();
            for(Object obj:list)
            {
                Object[] objArray=(Object[])obj;
                hhs=(HouseholdService)objArray[2];
                if(!cgiverIdList.contains(hhs.getCaregiverId()))
                {
                    cgiverIdList.add(hhs.getCaregiverId());
                    mainList.add(hhs);
                }
            }*/
        }
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }
    return mainList;
}
public int getNumberOfCaregiversProvidedHES(String additionalQuery,String startDate,String endDate,String hesType) throws Exception
{
    int numberOfCaregivers=0;
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query="select count (distinct hhs.caregiverId) "+util.getHouseholdCaregiverServiceReportQueryPart("HouseholdService")+util.getHESTypeQuery(hesType)+additionalQuery+util.getHouseholdServiceDateQuery(startDate,endDate);
        System.err.println("query in getNumberOfCaregiversProvidedHES is "+query);
        List list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            numberOfCaregivers=Integer.parseInt(list.get(0).toString());
        }
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }
    return numberOfCaregivers;
}
public List getListOfCaregiversProvidedHES(String additionalQuery,String startDate,String endDate,String hesType) throws Exception
{
    List mainList=new ArrayList();
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query=util.getHouseholdCaregiverServiceReportQueryPart("HouseholdService")+util.getHESTypeQuery(hesType)+additionalQuery+util.getHouseholdServiceDateQuery(startDate,endDate);
        System.err.println(query);
        List list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            mainList.addAll(list);
        }
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }
    return mainList;
}
public int getNumberOfHouseholdsProvidedHES(String additionalQuery,String currentlyEnrolledQuery,String startDate,String endDate) throws Exception
{
    int numberOfHh=0;
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query="select count (distinct hhs.hhUniqueId) "+util.getHouseholdAndHouseholdServiceQueryPart()+additionalQuery+currentlyEnrolledQuery+util.getHouseholdEconomicStrentheningServiceQueryPart()+util.getHouseholdServiceDateQuery(startDate,endDate);
        //System.err.println(query);
        List list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            numberOfHh=Integer.parseInt(list.get(0).toString());
        }
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }
    return numberOfHh;
}
public List getListOfHouseholdsProvidedHES(String additionalQuery,String currentlyEnrolledQuery,String startDate,String endDate) throws Exception
{
    List mainList=new ArrayList();
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        List list = session.createQuery(util.getHouseholdAndHouseholdServiceQueryPart()+additionalQuery+currentlyEnrolledQuery+util.getHouseholdEconomicStrentheningServiceQueryPart()+util.getHouseholdServiceDateQuery(startDate,endDate)).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            List hhUniqueIdList=new ArrayList();
            for(Object obj:list)
            {
                Object[] objArray=(Object[])obj;
                HouseholdEnrollment hhe=(HouseholdEnrollment)objArray[0];
                if(!hhUniqueIdList.contains(hhe.getHhUniqueId()))
                {
                    hhUniqueIdList.add(hhe.getHhUniqueId());
                    mainList.add(hhe);
                }
            }
        }
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }
    return mainList;
}
public List getNumberOfCaregiversProvidedMicrofinanceSupportPerMonthByCBO(String indicatorName,String stateCode,String startDate,String endDate) throws Exception
{
    List list=null;
    List mainList=new ArrayList();
    String additionalQuery=util.getHouseholdServiceDateQuery(startDate, endDate);
    mainList.add(indicatorName);
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query="select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,cgiver.caregiverGender,cgiver.currentAge,MONTH(hhs.serviceDate),YEAR(hhs.serviceDate), count(distinct hhs.caregiverId) "+util.getHouseholdCaregiverServiceReportQueryPart("HouseholdUniqueServiceReport")+util.getHouseholdEconomicStrentheningServiceQueryPart()+" and hhe.stateCode=:state"+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,cgiver.caregiverGender,cgiver.currentAge,MONTH(hhs.serviceDate),YEAR(hhs.serviceDate)";
        System.err.println(query);
        list = session.createQuery(query).setString("state", stateCode).list();
        tx.commit();
        session.close();
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }
    mainList.addAll(list);
    return mainList;
}
public List getNumberOfCaregiversServedHESPerMonthByCBO(String indicatorName,String stateCode,String startDate,String endDate,String hesType) throws Exception
{
    List list=null;
    List mainList=new ArrayList();
    String additionalQuery=util.getHouseholdServiceDateQuery(startDate, endDate);
    mainList.add(indicatorName);
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query="select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,cgiver.caregiverGender,cgiver.currentAge, count(distinct hhs.caregiverId) "+util.getHouseholdCaregiverServiceReportQueryPart("HouseholdService")+util.getHESTypeQuery(hesType)+" and hhe.stateCode=:state"+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,cgiver.caregiverGender,cgiver.currentAge";
        //System.err.println(query);
        list = session.createQuery(query).setString("state", stateCode).list();
        tx.commit();
        session.close();
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }
    mainList.addAll(list);
    return mainList;
}
public List getNumberOfCaregiversServedPerMonthByCBO(String indicatorName,String stateCode,String startDate,String endDate) throws Exception
{
    List list=null;
    List mainList=new ArrayList();
    String additionalQuery=util.getHouseholdServiceDateQuery(startDate, endDate);
    mainList.add(indicatorName);
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,cgiver.caregiverGender,cgiver.currentAge,MONTH(hhs.serviceDate),YEAR(hhs.serviceDate), count(distinct hhs.caregiverId) "+util.getHouseholdCaregiverServiceReportQueryPart("HouseholdUniqueServiceReport")+" and hhe.stateCode=:state"+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,cgiver.caregiverGender,cgiver.currentAge,MONTH(hhs.serviceDate),YEAR(hhs.serviceDate)").setString("state", stateCode).list();
        tx.commit();
        session.close();
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }
    mainList.addAll(list);
    return mainList;
}
public List getServiceByPeriod(String begDate,String endDate) throws Exception
{
    List list=new ArrayList();
    try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery(util.getHouseholdQueryPart()+"Caregiver cgiver, HouseholdService hhs where hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=hhs.caregiverId and hhs.serviceDate between '"+begDate+"' and '"+endDate+"' order by hhs.serviceDate").list();
        
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
public List getServiceByCBOAndPeriod(String orgCode,String begDate,String endDate) throws Exception
{
    List list=new ArrayList();
    List serviceList=new ArrayList();
    try {
            String query=(util.getHouseholdQueryPart()+"Caregiver cgiver, HouseholdService hhs where hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=hhs.caregiverId and hhe.orgCode='"+orgCode+"' and hhs.serviceDate between '"+begDate+"' and '"+endDate+"' order by hhs.serviceDate");
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(query).list();

        tx.commit();
        session.close();
        } catch (HibernateException he) {
            session.close();
            throw new Exception(he);
        }
    if(list !=null && !list.isEmpty())
    {
        for(Object s:list)
        {
            Object[] obj=(Object[])s;
            serviceList.add((HouseholdService)obj[2]);
        }
    }
    return serviceList;
}
public void setCaregiverIdAndRecipientTypeInHhs() throws Exception
{
    List list = new ArrayList();
    HouseholdService hhs=null;
    int count=0;
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from HouseholdService hhs where hhs.caregiverId is null or hhs.serviceRecipientType is null").list();
            tx.commit();
            session.close();
        }
         catch (Exception ex)
         {
             session.close();
             ex.printStackTrace();
         }
         if( list !=null && list.size()>0)
         {
             for(Object s:list)
             {
                hhs = (HouseholdService)s;
                updateHouseholdService(hhs);
                count++;
                System.err.println("hhs number "+count+" updated");
             }
         }
}
public List getOvcServicesByOvcIdAndEndDate(String hhUniqueId,String endDate) throws Exception
{
    List list=new ArrayList();
    String periodQuery=" ";
    if(endDate !=null)
        periodQuery=" and hhs.serviceDate <= '"+endDate+"'";
    try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from HouseholdService hhs where hhs.hhUniqueId=:id "+periodQuery+" order by hhs.serviceDate").setString("id", hhUniqueId).list();

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
public HouseholdService getHouseholdServiceWithCaregiverIdAndRecipientType(HouseholdService hhs) throws Exception
{
    if(hhs !=null)
    {
        Caregiver cgiver=null;
        if(hhs.getCaregiverId()==null)
        {
            List list=cgiverDao.getListOfCaregiversFromSameHousehold(hhs.getHhUniqueId());
            if(list !=null && !list.isEmpty())
            {
                cgiver=(Caregiver)list.get(0);
                hhs.setCaregiverId(cgiver.getCaregiverId());
            }
        }
        if(cgiver==null)
        cgiver=cgiverDao.getCaregiverByCaregiverId(hhs.getCaregiverId());//.getCaregiverByName(hhe.getHhUniqueId(), hhe.getHhFirstname(), hhe.getHhSurname());
        if(cgiver !=null)
        {
            hhs.setServiceRecipientType(appUtil.getHhsRecipientType("c"));
        }
        else
        {
            hhs.setServiceRecipientType(appUtil.getHhsRecipientType("h"));
            hhs.setCaregiverId(hhs.getHhUniqueId());
        }
        //Code 2 is hiv positive
        if(HivStatus.getStatusWeight(hhs.getCurrentHivStatus()) !=2)
        {
            hhs.setFacilityId(null);
            hhs.setEnrolledInCare("No");
            hhs.setEnrolledOnART("No");
        }
        
    }
    return hhs;
}
public int getNumberOfServicesPerServiceRecord(HouseholdService hhs) throws Exception
{
    int numberOfServices=0;
    if(hhs !=null)
    {
        if(hhs.getEconomicStrengtheningServices() !=null && !hhs.getEconomicStrengtheningServices().equals("") && !hhs.getEconomicStrengtheningServices().equals(" "))
        numberOfServices++;
        if(hhs.getEducationalServices() !=null && !hhs.getEducationalServices().equals("") && !hhs.getEducationalServices().equals(" "))
        numberOfServices++;
        if(hhs.getHealthServices() !=null && !hhs.getHealthServices().equals("") && !hhs.getHealthServices().equals(" "))
        numberOfServices++;
        if(hhs.getNutritionalServices() !=null && !hhs.getNutritionalServices().equals("") && !hhs.getNutritionalServices().equals(" "))
        numberOfServices++;
        if(hhs.getProtectionServices() !=null && !hhs.getProtectionServices().equals("") && !hhs.getProtectionServices().equals(" "))
        numberOfServices++;
        if(hhs.getPsychosocialSupportServices() !=null && !hhs.getPsychosocialSupportServices().equals("") && !hhs.getPsychosocialSupportServices().equals(" "))
        numberOfServices++;
        if(hhs.getShelterAndCareServices() !=null && !hhs.getShelterAndCareServices().equals("") && !hhs.getShelterAndCareServices().equals(" "))
        numberOfServices++;

    }
    return numberOfServices;
}
public void saveCaregiverHIVStatus(HouseholdService hhs) throws Exception
{
    if(hhs==null || hhs.getCaregiverId()==null)
    return;
    AppUtility appUtil=new AppUtility();
    HivStatusUpdateDao hsudao=new HivStatusUpdateDaoImpl();
    HivStatusUpdate hsu=new HivStatusUpdate();
    hsu.setClientEnrolledInCare(hhs.getEnrolledInCare());
    hsu.setEnrolledOnART(hhs.getEnrolledOnART());
    hsu.setOrganizationClientIsReferred(hhs.getFacilityId());
    hsu.setClientId(hhs.getCaregiverId());
    hsu.setClientType("caregiver");
    hsu.setDateModified(DateManager.getCurrentDate());
    hsu.setDateOfCurrentStatus(hhs.getServiceDate());
    hsu.setHivStatus(HivRecordsManager.getHivStatusScode(hhs.getCurrentHivStatus()));
    hsu.setRecordStatus("active");
    hsu.setPointOfUpdate(hhs.getPointOfService());
    hsudao.saveHivStatusUpdate(hsu);
}
    public void saveHouseholdService(HouseholdService hhs) throws Exception
    {
        try
        {
            hhs=getServicesByServiceCode(hhs);
           hhs=getHouseholdServiceWithCaregiverIdAndRecipientType(hhs);
           hhs.setNumberOfServices(getNumberOfServicesPerServiceRecord(hhs));
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.save(hhs);
            tx.commit();
            session.close();
            //saveCaregiverHIVStatus(hhs);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            session.close();
        }
    }
    public void updateHouseholdService(HouseholdService hhs) throws Exception
    {
        try
        {
            if(hhs !=null)
            {
                HouseholdService hhs2=this.getHouseholdService(hhs.getCaregiverId(), hhs.getServiceDate());
                if(hhs2 !=null)
                {
                    hhs=getServicesByServiceCode(hhs);
                    HouseholdService hhs3=getHouseholdServiceWithCaregiverIdAndRecipientType(hhs);
                    hhs3.setId(hhs2.getId());
                    hhs3.setNumberOfServices(getNumberOfServicesPerServiceRecord(hhs));
                    //System.err.println("hhs.getHealthServices()3 is "+hhs3.getHealthServices());
                    session=HibernateUtil.getSession();
                    tx=session.beginTransaction();
                    //System.err.println("hhs3.getFacilityId() 2 is "+hhs3.getFacilityId());
                    session.update(hhs3);
                    tx.commit();
                    session.close();
                    //saveCaregiverHIVStatus(hhs3);
                }
                else
                {
                    saveHouseholdService(hhs);
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            session.close();
        }
    }
    public void markedForDelete(HouseholdService hhs) throws Exception
    {
        try
        {
            hhs.setMarkedForDelete(NomisConstant.MARKEDFORDELETE);
            updateHouseholdService(hhs);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void deleteHouseholdService(HouseholdService hhs) throws Exception
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.delete(hhs);
            tx.commit();
            session.close();
            util.saveDeletedRecord(hhs.getCaregiverId(),null, "householdService", hhs.getServiceDate());
            HivRecordsManager.deleteHivStatusRecord(hhs.getCaregiverId(), hhs.getServiceDate(), NomisConstant.Caregiver_TYPE);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            session.close();
        }
    }
    public void deleteAllHouseholdServices(String hhUniqueId) throws Exception
    {
        try
        {
            List list=getAllServicesPerHousehold(hhUniqueId);
            for(Object obj:list)
            {
                HouseholdService hhs=(HouseholdService)obj;
                deleteHouseholdService(hhs);
            }
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            session.close();
        }
    }
    public List getHouseholdServicesPerCaregiver(String caregiverId, String startDate,String endDate) throws Exception
    {
        List list = new ArrayList();
         try 
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            String query="from HouseholdService hhs where hhs.caregiverId = :mid"+util.getHhsPeriodQuery(startDate,endDate);
            //System.err.println("query in getHouseholdServicesPerCaregiver is "+query);
            list = session.createQuery(query).setString("mid", caregiverId).list();
            //list = session.createQuery("from HouseholdService hhs where hhs.caregiverId = :mid and hhs.serviceDate between '"+startDate+"' and '"+endDate+"'").setString("mid", caregiverId).list();
            tx.commit();
            session.close();
        }
         catch (Exception ex)
         {
             ex.printStackTrace();
             session.close();
         }
         return list; 
    }
    public HouseholdService getHouseholdService(String caregiverId, String date) throws Exception
    {
        HouseholdService hhs=null;
        List list = new ArrayList();
         try 
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from HouseholdService hhs where hhs.caregiverId = :mid and hhs.serviceDate=:d").setString("mid", caregiverId).setString("d", date).list();
            tx.commit();
            session.close();
        }
         catch (Exception ex)
         {
             session.close();
         }
         if( list !=null && list.size()>0)
         {
           hhs = (HouseholdService)list.get(0);
         }
        return hhs; 
    }
    public List getAllServicesPerHousehold(String uniqueId) throws Exception
    {
        List list = new ArrayList();
         try 
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from HouseholdService hhs where hhs.hhUniqueId=:id").setString("id", uniqueId).list();
            tx.commit();
            session.close();
        }
         catch (Exception ex)
         {
             session.close();
         }
         return list;
    }
    public List getAllHouseholdServices() throws Exception
    {
        List list = new ArrayList();
        List serviceList = new ArrayList();
         try 
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from HouseholdService hhs ").list();
            tx.commit();
            session.close();
        }
         catch (Exception ex)
         {
             session.close();
         }
         if(list !=null)
         {
             for(int i=0; i<list.size(); i++)
             {
                 serviceList.add((HouseholdService)list.get(i));
             }
         }
         return serviceList; 
    }
    
    public List getHouseholdServiceList(String additionalQuery) throws Exception
    {
        List list = new ArrayList();
        List hhsList = new ArrayList();
        String householdQueryPart=util.getHouseholdQueryPart();
         try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery(householdQueryPart+"HouseholdService hhs where hhe.hhUniqueId=hhs.hhUniqueId "+additionalQuery+" order by hhs.hhUniqueId").list();
        System.err.println(householdQueryPart+"HouseholdService hhs where hhe.hhUniqueId=hhs.hhUniqueId "+additionalQuery+" order by hhs.hhUniqueId");
        tx.commit();
        session.close();
        }
         catch (Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        for(int i=0; i<list.size(); i++)
        {
            Object[] obj=(Object[])list.get(i);
            hhsList.add((HouseholdService)obj[1]);
        }
        return hhsList;
    }
    public List getAllServicesPerCaregiver(String caregiverId) throws Exception
    {
        List list = new ArrayList();
        try 
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from HouseholdService hhs where hhs.caregiverId=:cId").setString("cId", caregiverId).list();
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
    public List getAllServicesPerCaregiverOrderdByServiceDate(String caregiverId,String order) throws Exception
    {
        List list = new ArrayList();
        String orderQuery=" order by hhs.serviceDate";
        if(order !=null && order.equalsIgnoreCase("desc"))
        {
           orderQuery=" order by hhs.serviceDate desc"; 
        }
        try 
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from HouseholdService hhs where hhs.caregiverId=:cId"+orderQuery).setString("cId", caregiverId).list();
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
    public List getAllServicesWithoutHhUniqueId() throws Exception
    {
        List list = new ArrayList();
        try 
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from HouseholdService hhs where hhs.hhUniqueId not like '%/%'").list();
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
    public void changeCaregiverId(String oldCaregiverId, String newHhUniqueId,String newCaregiverId) throws Exception
    {
        List hhsList=getAllServicesPerCaregiver(oldCaregiverId);
        if(hhsList !=null)
        {
            HouseholdService hhs=null;
            for(int i=0; i<hhsList.size(); i++)
            {
               hhs=(HouseholdService)hhsList.get(i);
               hhs.setHhUniqueId(newHhUniqueId); 
               hhs.setCaregiverId(newCaregiverId);
               updateHouseholdService(hhs);
            }
        }
    }
    public void changeHhUniqueIdInHouseholdService(String oldHhUniqueId,String newHhUniqueId)
    {
        try
        {
            AppUtility appUtil=new AppUtility();
            List list=getAllServicesPerHousehold(oldHhUniqueId);
            String oldId=null;
            String newId=null;
            String newCaregiverId=null;
            if(list !=null)
            {
                HouseholdService hhs=null;
                for(int i=0; i<list.size(); i++)
                {
                    newCaregiverId=null;
                    hhs=(HouseholdService)list.get(i);
                    oldId=hhs.getHhUniqueId();
                    newId=oldId.replace(oldHhUniqueId, newHhUniqueId);
                    String caregiverId=hhs.getCaregiverId();
                    if(caregiverId !=null)
                    newCaregiverId=caregiverId.replace(oldHhUniqueId, newHhUniqueId);
                    deleteHouseholdService(hhs);
                    if(getHouseholdService(newCaregiverId, hhs.getServiceDate())==null)
                    {
                        hhs.setDateOfEntry(appUtil.getCurrentDate());
                        hhs.setHhUniqueId(newId);
                        hhs.setHhUniqueId(newCaregiverId);
                        saveHouseholdService(hhs);
                    }
                    System.err.println("HouseholdService with "+hhs.getHhUniqueId()+" processed");
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
public HouseholdService getServicesByServiceCode(HouseholdService service)
{
    
    if(service !=null)
    {
        if(service.getPsychosocialSupportServices() !=null)
        service.setPsychosocialSupportServices(OvcServiceManager.getConcatenatedServiceCodes(service.getPsychosocialSupportServices().split(","),false));
        if(service.getHealthServices() !=null)
        service.setHealthServices(OvcServiceManager.getConcatenatedServiceCodes(service.getHealthServices().split(","),false));
        if(service.getNutritionalServices() !=null)
        service.setNutritionalServices(OvcServiceManager.getConcatenatedServiceCodes(service.getNutritionalServices().split(","),false));
        if(service.getEducationalServices() !=null)
        service.setEducationalServices(OvcServiceManager.getConcatenatedServiceCodes(service.getEducationalServices().split(","),false));
        if(service.getProtectionServices() !=null)
        service.setProtectionServices(OvcServiceManager.getConcatenatedServiceCodes(service.getProtectionServices().split(","),false));
        if(service.getShelterAndCareServices() !=null)
        service.setShelterAndCareServices(OvcServiceManager.getConcatenatedServiceCodes(service.getShelterAndCareServices().split(","),false));
        if(service.getEconomicStrengtheningServices() !=null)
        service.setEconomicStrengtheningServices(OvcServiceManager.getConcatenatedServiceCodes(service.getEconomicStrengtheningServices().split(","),false));
    }
    return service;
}
    public void fixHhUniqueIdInHouseholdService() throws Exception
    {
        try
        {
            List list=getAllServicesWithoutHhUniqueId();
            String caregiverId=null;
            String hhUniqueId=null;
            if(list !=null)
            {
                HouseholdService hhs=null;
                for(int i=0; i<list.size(); i++)
                {
                    hhs=(HouseholdService)list.get(i);
                    caregiverId=hhs.getCaregiverId();
                    if(caregiverId !=null && caregiverId.length()>16)
                    {
                        hhUniqueId=caregiverId.substring(0, 17);
                        hhs.setHhUniqueId(hhUniqueId);
                        updateHouseholdService(hhs);
                        System.err.println("record number "+i+" with hhUniqueId in HouseholdService "+hhUniqueId+" and caregiverId is "+caregiverId+" processed");
                        
                    }
                    //System.err.println("HouseholdService with "+hhs.getHhUniqueId()+" processed");
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
