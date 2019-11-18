/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomis.upgrade;

import com.fhi.kidmap.business.BirthRegistration;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.Caregiver;
import com.fhi.nomis.nomisutils.DatabaseUtilities;
import com.fhi.kidmap.business.FollowupSurvey;
import com.fhi.kidmap.business.HivStatusUpdate;
import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.business.OvcService;
import com.fhi.kidmap.business.OvcWithdrawal;
import com.fhi.kidmap.dao.BirthRegistrationDao;
import com.fhi.kidmap.dao.BirthRegistrationDaoImpl;
import com.fhi.kidmap.dao.CaregiverDao;
import com.fhi.kidmap.dao.CaregiverDaoImpl;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.FollowupDao;
import com.fhi.kidmap.dao.FollowupDaoImpl;
import com.fhi.kidmap.dao.HivStatusUpdateDao;
import com.fhi.kidmap.dao.HivStatusUpdateDaoImpl;
import com.fhi.kidmap.dao.HouseholdEnrollmentDao;
import com.fhi.kidmap.dao.HouseholdEnrollmentDaoImpl;
import com.fhi.kidmap.dao.OvcDao;
import com.fhi.kidmap.dao.OvcDaoImpl;
import com.fhi.kidmap.dao.OvcServiceDao;
import com.fhi.kidmap.dao.OvcServiceDaoImpl;
import com.fhi.kidmap.dao.OvcWithdrawalDao;
import com.fhi.kidmap.dao.OvcWithdrawalDaoImpl;
import com.fhi.nomis.OperationsManagement.HivRecordsManager;
import com.fhi.nomis.OperationsManagement.OvcServiceUtilityManager;
import com.fhi.nomis.nomisutils.DateManager;
import com.fhi.nomis.nomisutils.NomisConstant;
import com.fhi.nomis.nomisutils.TaskManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author smomoh
 */
public class NomisUpgrade implements Serializable
{
    DatabaseUtilities dbutils=new DatabaseUtilities();
    AppUtility appUtil=new AppUtility();
    List hivOnTreatmentList=new ArrayList();
    int hivPosActiveCount=0;
    int hivPosOnTreatmentCount=0;
    public List getHivOnTreatmentList(HivStatusUpdate hsu)
    {
        if(hsu.getHivStatus().equalsIgnoreCase(NomisConstant.HIV_POSITIVE) && hsu.getRecordStatus().equalsIgnoreCase("active"))
        {
            hivPosActiveCount++;
            if((hsu.getClientEnrolledInCare() !=null && hsu.getClientEnrolledInCare().equalsIgnoreCase("Yes")) || (hsu.getEnrolledOnART() !=null && hsu.getEnrolledOnART().equalsIgnoreCase("Yes")))
            {
                hivOnTreatmentList.add(hsu);
                hivPosOnTreatmentCount++;
            }
            else
            {
                System.err.println("Enrolled in Care is "+hsu.getClientEnrolledInCare()+" Enrolled in Care is "+hsu.getEnrolledOnART());
            }
            System.err.println("Number of Active positive records imported is "+hivPosActiveCount);
            System.err.println("Number of Active positive on treatment is "+hivPosOnTreatmentCount);
        }
        return hivOnTreatmentList;
    }
    public String correctLgaCodesInHouseholdEnrollmentTable()
    {
        String msg=" ";
       if(!TaskManager.isDatabaseLocked())
        {
           TaskManager.setDatabaseLocked(true);
           try
           {
                HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
                hhedao.correctLgaCodesInHouseholdEnrollmentRecords();
                msg="Task completed";
           }
           catch(Exception ex)
           {
               ex.printStackTrace();
           }
           TaskManager.setDatabaseLocked(false);
        }
       return msg;
    }
    public String updateOvcEnrollmentHivInformationStatus(boolean forceUpdate)
    {
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        HivStatusUpdateDao hsudao=new HivStatusUpdateDaoImpl();
        OvcDao dao=new OvcDaoImpl();
        String msg=" ";
        int totalNumberOfRecords=0;
        try
        {
        System.err.println("inside updateOvcEnrollmentHivInformationStatus in NomisUpgrade class is ");
         if(!TaskManager.isDatabaseLocked())
         {
             try
             {
                 List communityList=hhedao.getDistinctCommunityCodes();
                 if(communityList !=null)
                 {
                     System.err.println(" communityList records in NomisUpgrade class is "+communityList.size());
                     String communityCode=null;
                     System.err.println(" communityCode in NomisUpgrade class is "+communityCode);
                         
                        List list=hsudao.getHivStatusRecordsFromOvcEnrollment(communityCode);
                        TaskManager.setDatabaseLocked(true);
                        if(list !=null)
                        {
                            System.err.println(" list size in NomisUpgrade class is "+list.size());
                            Ovc ovc=null;
                            for(Object obj:list)
                            {
                                Object[] objArray=(Object[])obj;
                                HivStatusUpdate hsu=(HivStatusUpdate)objArray[1];
                                
                                ovc=(Ovc)objArray[2];
                                if(ovc !=null)
                                {
                                    System.err.println(" hsu.getHivStatus() in NomisUpgrade class is "+hsu.getHivStatus()+" and NomisConstant.HIV_POSITIVE is "+NomisConstant.HIV_POSITIVE);
                                    if(hsu.getHivStatus().equalsIgnoreCase(NomisConstant.HIV_POSITIVE))
                                    {
                                        ovc.setDateOfEntry(appUtil.getCurrentDate());
                                        ovc.setEnrolledInCare(hsu.getClientEnrolledInCare());
                                        ovc.setEnrolledOnART(hsu.getEnrolledOnART());
                                        ovc.setFacilityId(hsu.getOrganizationClientIsReferred());
                                        dao.updateOvc(ovc, forceUpdate, forceUpdate);
                                        addHivOnTreatmentToList(hsu);
                                        System.err.println(totalNumberOfRecords+" enrollment records update with hiv information in NomisUpgrade class "+ovc.getOvcId()+" "+ovc.getDateEnrollment());
                                    }
                                    totalNumberOfRecords++;

                                }
                            }
                        } 
                    //}
                 }
             }
             catch(Exception ex)
             {
                 ex.printStackTrace();
             }
            if(totalNumberOfRecords > 0)
            msg=totalNumberOfRecords+" OVC enrollment records update with hiv information in NomisUpgrade class ";
         }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
         TaskManager.setDatabaseLocked(false);
        return msg;
    }
    public String updateOvcFollowupHivInformationStatus(boolean forceUpdate)
    {
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        HivStatusUpdateDao hsudao=new HivStatusUpdateDaoImpl();
        FollowupDao fudao=new FollowupDaoImpl();
        String msg=" ";
        System.err.println("inside updateOvcFollowupHivInformationStatus in NomisUpgrade class is ");
        int totalNumberOfRecords=0;
         if(!TaskManager.isDatabaseLocked())
         {
             try
             {
                List communityList=hhedao.getDistinctCommunityCodes();
                 if(communityList !=null)
                 {
                     System.err.println(" communityList records in NomisUpgrade class is "+communityList.size());
                     String communityCode=null;
                     
                        List list=hsudao.getHivStatusRecordsFromOvcFollowup(communityCode);
                        TaskManager.setDatabaseLocked(true);
                        if(list !=null)
                        {
                            FollowupSurvey fu=null;
                            for(Object obj:list)
                            {
                                Object[] objArray=(Object[])obj;
                                HivStatusUpdate hsu=(HivStatusUpdate)objArray[1];
                                addHivOnTreatmentToList(hsu);
                                fu=(FollowupSurvey)objArray[3];
                                if(fu !=null)
                                {
                                    if(hsu.getHivStatus().equalsIgnoreCase(NomisConstant.HIV_POSITIVE))
                                    {
                                        fu.setDateOfEntry(appUtil.getCurrentDate());
                                        fu.setEnrolledInCare(hsu.getClientEnrolledInCare());
                                        fu.setEnrolledOnART(hsu.getEnrolledOnART());
                                        fu.setFacilityId(hsu.getOrganizationClientIsReferred());
                                        
                                        fudao.updateFollowup(fu, forceUpdate, forceUpdate);
                                    }
                                    totalNumberOfRecords++;
                                    System.err.println(totalNumberOfRecords+" followup records update with hiv information in NomisUpgrade class "+fu.getDateOfSurvey());
                                }
                            }

                        }
                     //}
                 }
             }
             catch(Exception ex)
             {
                 ex.printStackTrace();
             }
            if(totalNumberOfRecords > 0)
            msg=totalNumberOfRecords+" OVC follow up records update with hiv information in NomisUpgrade class ";
         }
         TaskManager.setDatabaseLocked(false);
        return msg;
    }
    public String updateServiceHivInformationStatus(boolean forceUpdate)
    {
        HivStatusUpdateDao hsudao=new HivStatusUpdateDaoImpl();
        OvcServiceDao sdao=new OvcServiceDaoImpl();
        //HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        HivStatusUpdate hsu=null;
        String msg=" ";
        int totalNumberOfRecords=0;
         if(!TaskManager.isDatabaseLocked())
         {
             try
             {
                 List hsuClientIdList=sdao.getDistinctOvcIdForHivRecordsUpdate(true);//hsudao.getDistinctClientIdByPointOfUpdate("service",true);
                 if(hsuClientIdList !=null)
                 {
                     int count=0;
                     int savedCount=0;
                     System.err.println("hsuClientIdList size is "+hsuClientIdList.size());
                     for(Object s:hsuClientIdList)
                     {
                         String ovcId=(String)s;
                         List list=hsudao.getHivStatusUpdatesByClientIdAndPointOfUpdate(ovcId, "service");
                         //List serviceList=sdao.getOvcServices(ovcId);
                         if(list !=null)
                         {
                             count++;
                             System.err.println("hsuClientIdList processed is "+count);
                             for(int i=0; i<list.size(); i++)
                             {
                                 hsu=(HivStatusUpdate)list.get(i);
                                 addHivOnTreatmentToList(hsu);
                                 OvcService service=sdao.getOvcServiceWithoutHivInformation(ovcId, hsu.getDateOfCurrentStatus());
                                 if(service !=null)
                                 {
                                     service.setCurrentHivStatus(hsu.getHivStatus());
                                     if(hsu.getHivStatus() !=null && hsu.getHivStatus().equalsIgnoreCase(NomisConstant.HIV_POSITIVE))
                                     {
                                        service.setEnrolledInCare(hsu.getClientEnrolledInCare());
                                        service.setEnrolledOnART(hsu.getEnrolledOnART());
                                        //addHivOnTreatmentToList(hsu);
                                     }
                                     sdao.updateOvcService(service, false, false);
                                     savedCount++;
                                     System.err.println("Service record "+savedCount+" with OvcId "+service.getOvcId()+" updated");
                                 }
                             }
                         }
                     }
                 }                
             }
             catch(Exception ex)
             {
                 ex.printStackTrace();
             }
            if(totalNumberOfRecords > 0)
            msg=totalNumberOfRecords+" service records update with hiv information in NomisUpgrade class ";
         }
         TaskManager.setDatabaseLocked(false);
        return msg;
    }
    public void addHivOnTreatmentToList(HivStatusUpdate hsu)
    {
        if(hsu !=null && hsu.getHivStatus().equalsIgnoreCase(NomisConstant.HIV_POSITIVE) && hsu.getRecordStatus().equalsIgnoreCase("active"))
        {
            //hivPosActiveCount++;
            if((hsu.getClientEnrolledInCare() !=null && hsu.getClientEnrolledInCare().equalsIgnoreCase("Yes")) || (hsu.getEnrolledOnART() !=null && hsu.getEnrolledOnART().equalsIgnoreCase("Yes")))
            {
                hivOnTreatmentList.add(hsu);
                //hivPosOnTreatmentCount++;
            }
            System.err.println("Number of Active positive on treatment is "+hivPosOnTreatmentCount);
        }
    }
    public String updateWithdrawalStatus(boolean forceUpdate)
    {
        String msg=" ";
        int totalNumberOfRecords=0;
         if(!TaskManager.isDatabaseLocked())
         {
            TaskManager.setDatabaseLocked(true);
            totalNumberOfRecords+=updateWithdrawalStatusOfOvc(forceUpdate);
            totalNumberOfRecords+=updateWithdrawalStatusOfHouseholds(forceUpdate);
            totalNumberOfRecords+=updateWithdrawalStatusOfCaregivers(forceUpdate);
            
            //updateHouseholdWithdrawal();
            //withdrawHouseholdsWithoutOvc();
            if(totalNumberOfRecords > 0)
            msg=totalNumberOfRecords+" withdrawal status records updated ";
         }
         TaskManager.setDatabaseLocked(false);
        return msg;
    }
    public void updateHouseholdWithdrawal()
    {
        try
        {
            OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl();
            wdao.withdrawHouseholdMembers();
            wdao.resetHouseholdAgeAbove17ToGraduation();
            //withdrawHouseholdsWithoutOvc();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void withdrawHouseholdsWithoutOvc()
    {
        try
        {
            HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
            OvcDao dao=new OvcDaoImpl();
            OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl();
            List hheList=hhedao.getListOfHouseholdsCurrentlyEnrolled(" ");
            if(hheList !=null && !hheList.isEmpty())
            {
                HouseholdEnrollment hhe=null;
                String hhUniqueId=null;
                String additionalQuery="";
                for(Object obj:hheList)
                {
                   hhe=(HouseholdEnrollment)obj;
                   hhUniqueId=hhe.getHhUniqueId();
                   additionalQuery=" and ovc.hhUniqueId='"+hhUniqueId+"'";
                   List list=dao.getListOfOvcCurrentlyEnrolled(additionalQuery);
                   if(list==null || list.isEmpty())
                   {
                       wdao.withdrawHousehold(hhUniqueId, appUtil.getCurrentDate(), NomisConstant.INACTIVE,NomisConstant.HOUSEHOLD_TYPE , 0);
                   }
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void resetOvcWithdrawalStatus()
    {
        OvcDao ovcdao=new OvcDaoImpl();
        OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl();
        try
        {
            List list = ovcdao.getOvcWithYesWithdrawalStatus();
            if(list !=null)
            {
                String ovcId=null;
                for(Object s:list)
                {
                    Ovc ovc=(Ovc)s;
                    ovcId=ovc.getOvcId();
                    OvcWithdrawal withdrawal=wdao.getOvcWithdrawal(ovcId);
                    if(withdrawal==null)
                    {
                        ovc.setWithdrawnFromProgram("No");
                        ovcdao.updateOvc(ovc, false, false);
                        System.err.println("ovc with id "+ovc.getOvcId()+" updated");
                    }
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
}
    public void resetCaregiverWithdrawalStatus()
    {
        CaregiverDao cgiverdao=new CaregiverDaoImpl();
        OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl();
        try
        {
            List list = cgiverdao.getCaregiversWithYesWithdrawalStatus();
            if(list !=null)
            {
                String cgiverId=null;
                for(Object s:list)
                {
                    Caregiver cgiver=(Caregiver)s;
                    cgiverId=cgiver.getCaregiverId();
                    OvcWithdrawal withdrawal=wdao.getOvcWithdrawal(cgiverId);
                    if(withdrawal==null)
                    {
                        cgiver.setWithdrawnFromProgram("No");
                        cgiverdao.updateCaregiver(cgiver);
                        System.err.println("Caregiver with id "+cgiver.getCaregiverId()+" updated");
                    }
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
}
    public void resetHouseholdWithdrawalStatus()
    {
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl();
        try
        {
            List list = hhedao.getHouseholdsWithYesWithdrawalStatus();
            if(list !=null)
            {
                String hhUniqueId=null;
                for(Object s:list)
                {
                    HouseholdEnrollment hhe=(HouseholdEnrollment)s;
                    hhUniqueId=hhe.getHhUniqueId();
                    OvcWithdrawal withdrawal=wdao.getOvcWithdrawal(hhUniqueId);
                    if(withdrawal==null)
                    {
                        hhe.setWithdrawnFromProgram("No");
                        hhedao.updateHouseholdEnrollment(hhe);
                        System.err.println("HouseholdEnrollment with id "+hhe.getHhUniqueId()+" updated");
                    }
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
}
    public Ovc getOvcWithUpdatedWithdrawalStatus(Ovc ovc)
    {
       try
       {
             if(ovc !=null)
             {
                 OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl();
                 OvcWithdrawal withdrawal=wdao.getOvcWithdrawal(ovc.getOvcId());
                 if(withdrawal !=null)
                 ovc.setWithdrawnFromProgram("Yes");
                 else
                 ovc.setWithdrawnFromProgram("No");                 
             } 
             //resetOvcWithdrawalStatus();
       }
       catch(Exception ex)
       {
           ex.printStackTrace();
       }
       return ovc;
    }
       
    public int updateWithdrawalStatusOfOvc(boolean forceUpdate)
    {
        int numberOfRecords=0;
       OvcDao ovcdao=new OvcDaoImpl();
       try
       {
             List ovcList=ovcdao.getActiveOvcFromWithdrawalRecords();
             if(ovcList !=null)
             {
                 Ovc ovc=null;
                 for(int i=0; i<ovcList.size(); i++)
                 {
                     ovc=(Ovc)ovcList.get(i);
                     ovc=getOvcWithUpdatedWithdrawalStatus(ovc);
                     //ovc.setWithdrawnFromProgram("Yes");
                     ovcdao.updateOvc(ovc,false,false);
                     numberOfRecords++;
                     System.err.println("Ovc with id "+ovc.getOvcId()+" at "+i+" updated in NomisUpgrade.updateWithdrawalStatusInEnrollment");
                 }
             } 
             resetOvcWithdrawalStatus();
       }
       catch(Exception ex)
       {
           ex.printStackTrace();
       }
       return numberOfRecords;
    }
    public int updateWithdrawalStatusOfCaregivers(boolean forceUpdate)
    {
       int numberOfRecords=0;
       CaregiverDao cgiverdao=new CaregiverDaoImpl();
       try
       {
             List ovcList=cgiverdao.getActiveCaregiversFromWithdrawalRecords();
             if(ovcList !=null)
             {
                 Caregiver cgiver=null;
                 for(int i=0; i<ovcList.size(); i++)
                 {
                     cgiver=(Caregiver)ovcList.get(i);
                     cgiver.setWithdrawnFromProgram("Yes");
                     cgiverdao.updateCaregiver(cgiver);
                     numberOfRecords++;
                     System.err.println("Caregiver with id "+cgiver.getCaregiverId()+" at "+i+" updated in NomisUpgrade.updateWithdrawalStatusOfCaregivers");
                 }
             }
             resetCaregiverWithdrawalStatus();
       }
       catch(Exception ex)
       {
           ex.printStackTrace();
       }
       return numberOfRecords;
    }
    public int updateWithdrawalStatusOfHouseholds(boolean forceUpdate)
    {
        int numberOfRecords=0;
       HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
       try
       {
             List hheList=hhedao.getActiveHouseholdsFromWithdrawalRecords();
             if(hheList !=null)
             {
                 HouseholdEnrollment hhe=null;
                 for(int i=0; i<hheList.size(); i++)
                 {
                     hhe=(HouseholdEnrollment)hheList.get(i);
                     hhe.setWithdrawnFromProgram("Yes");
                     hhedao.updateHouseholdEnrollment(hhe);
                     numberOfRecords++;
                     System.err.println("Household with id "+hhe.getHhUniqueId()+" at "+i+" updated in NomisUpgrade.updateWithdrawalStatusOfHouseholds");
                 }
             } 
             resetHouseholdWithdrawalStatus();
       }
       catch(Exception ex)
       {
           ex.printStackTrace();
       }
       return numberOfRecords;
    }
    /*public void cleanupBirthRegistrationRecords(boolean forceUpdate)
    {
        try
        {
            DaoUtil util=new DaoUtil();
            OvcDao dao=new OvcDaoImpl();
            BirthRegistrationDao brdao=new BirthRegistrationDaoImpl();
            List list=dao.getListOfOvcWithBirthCertificateAtBaseline();
            List communityList=util.getHouseholdEnrollmentDaoInstance().getDistinctCommunityCodes();
            if(communityList !=null && !communityList.isEmpty())
            {
                for(Object obj:communityList)
                {
                    String community=(String)obj;
                }
                
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }*/
    public void updateBirthRegistrationStatusForOvcThatHasAtBaseline(boolean forceUpdate)
    {
        try
        {
            OvcDao dao=new OvcDaoImpl();
            BirthRegistrationDao brdao=new BirthRegistrationDaoImpl();
            List list=dao.getListOfOvcWithBirthCertificateAtBaseline();
            if(list !=null && !list.isEmpty())
            {
                System.err.println("list.size() in in updateBirthRegistrationStatusForOvcThatHasAtBaseline is "+list.size());
                int count=0;
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    Ovc ovc=(Ovc)objArray[1];
                    BirthRegistration activeBr=(BirthRegistration)objArray[2];
                    if(activeBr !=null)
                    {
                        activeBr.setBirthRegistrationStatus(ovc.getBirthCertificate());
                        activeBr.setPointOfUpdate(NomisConstant.ENROLLMENT_POINTOFUPDATE);
                        activeBr.setDateOfRegistration(ovc.getDateEnrollment());
                        activeBr.setDateModified(appUtil.getCurrentDate());
                        brdao.updateBirthRegistration(activeBr);
                        count++;
                        System.err.println("Updating Ovc birth registration in updateBirthRegistrationStatusForOvcThatHasAtBaseline "+count);
                    }
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
       //setActiveBirthRegistrationInformation(forceUpdate);
    }
    public void updateBirthRegistrationStatus(boolean forceUpdate)
    {
       DatabaseUtilities dbUtils=new DatabaseUtilities();
       dbUtils.dropAndCreateBirthRegistrationTable();
       upgradeBaselineBirthRegistrationInformation(forceUpdate);
       upgradeBirthRegistrationInformationAtServiceProvision(forceUpdate);
       upgradeBirthRegistrationInformationAtFollowupAssessment(forceUpdate);
       updateBirthRegistrationStatusForOvcThatHasAtBaseline(forceUpdate);
       //setActiveBirthRegistrationInformation(forceUpdate);
    }
    public void setActiveBirthRegistrationInformation(boolean forceUpdate)
    {
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        BirthRegistrationDao brdao=new BirthRegistrationDaoImpl();
        int count=0;
       try
       {
            List listOfCommunityCodes=hhedao.getDistinctCommunityCodes();
            List brList=null;   
            if(listOfCommunityCodes !=null)
            {
                String communityCode=null;
                for(int i=0; i<listOfCommunityCodes.size(); i++)
                {
                    if(listOfCommunityCodes.get(i) !=null)
                    {
                        communityCode=listOfCommunityCodes.get(i).toString();
                    }
                    else
                    if(listOfCommunityCodes.get(i) !=null)
                    {
                        communityCode=(String)listOfCommunityCodes.get(i);
                    }  
                    brList=brdao.getDistinctClientIdFromBirthRegistration(communityCode);
                    
                    //System.err.println("communityCode is "+communityCode);
                    if(brList !=null && !brList.isEmpty())
                    {
                        for(int j=0; j<brList.size(); j++)
                        {
                            brdao.setActiveBirthRegistration(brList.get(j).toString());
                            count++;
                            System.err.println("clientId "+brList.get(j).toString()+" at "+count+" reordered");
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
    public void upgradeBaselineBirthRegistrationInformation(boolean forceUpdate)
    {
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        OvcDao ovcdao=new OvcDaoImpl();
        BirthRegistrationDao brdao=new BirthRegistrationDaoImpl();
        BirthRegistration br=null;
        int count=0;
        try
       {
            int noOfBirthRegistrationRecords=brdao.getCountOfBirthRegistration("enrollment");
            int noOfOvc=ovcdao.getNumberOfOvcEnrolledPerCohort(" ");
            if((noOfOvc>noOfBirthRegistrationRecords) || forceUpdate)
            {
                List listOfCommunityCodes=hhedao.getDistinctCommunityCodes();
                
                if(listOfCommunityCodes !=null)
                {
                    String additionalQuery=null;
                    Ovc ovc=null;
                    for(int i=0; i<listOfCommunityCodes.size(); i++)
                    {
                        additionalQuery=" and hhe.communityCode='"+listOfCommunityCodes.get(i).toString() +"'";
                        List ovcList=ovcdao.getListOfOvcEnrolledPerCohort(additionalQuery);
                        if(ovcList !=null)
                        {
                            for(int j=0; j<ovcList.size(); j++)
                            {
                                ovc=(Ovc)ovcList.get(j);
                                br=new BirthRegistration();
                                br.setClientId(ovc.getOvcId());
                                br.setClientType("ovc");
                                br.setDateModified(appUtil.getCurrentDate());
                                br.setDateOfRegistration(ovc.getDateEnrollment());
                                if(ovc.getBirthCertificate() !=null && (ovc.getBirthCertificate().equalsIgnoreCase("No") || ovc.getBirthCertificate().equalsIgnoreCase("Yes")))
                                br.setBirthRegistrationStatus(ovc.getBirthCertificate());
                                else
                                br.setBirthRegistrationStatus("No");
                                br.setRecordStatus("active");
                                br.setPointOfUpdate("enrollment");
                                brdao.saveBirthRegistration(br);
                                count++;
                                System.err.println("ovc at "+count+" with ovcId "+ovc.getOvcId()+" updated with birthRegDetails");
                            }
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
    public void upgradeBirthRegistrationInformationAtServiceProvision(boolean forceUpdate)
    {
        System.err.println("Inside upgradeBirthRegistrationInformationAtServiceProvision");
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        OvcServiceDao servicedao=new OvcServiceDaoImpl();
        BirthRegistrationDao brdao=new BirthRegistrationDaoImpl();
        BirthRegistration br=null;
        int count=0;
        try
       {
            
            if(forceUpdate)
            {
                List listOfCommunityCodes=hhedao.getDistinctCommunityCodes();
                OvcServiceUtilityManager osm=new OvcServiceUtilityManager();
                if(listOfCommunityCodes !=null)
                {
                    String additionalQuery=null;
                    OvcService service=null;
                    for(int i=0; i<listOfCommunityCodes.size(); i++)
                    {
                        additionalQuery=" and hhe.communityCode='"+listOfCommunityCodes.get(i).toString() +"'";
                        List serviceList=servicedao.getServiceRecordsWithBirthRegistrationDetails(additionalQuery);
                        if(serviceList !=null)
                        {
                            for(int j=0; j<serviceList.size(); j++)
                            {
                                service=(OvcService)serviceList.get(j);
                                osm.setOvcBirthRegistrationStatus(service);
                                /*br=new BirthRegistration();
                                br.setClientId(service.getOvcId());
                                br.setClientType(NomisConstant.OVC_TYPE);
                                br.setDateModified(DateManager.getCurrentDate());
                                br.setDateOfRegistration(service.getServicedate());
                                br.setBirthRegistrationStatus("Yes");
                                br.setRecordStatus("active");
                                br.setPointOfUpdate(NomisConstant.SERVICE_POINTOFUPDATE);
                                brdao.saveBirthRegistration(br);*/
                                count++;
                                System.err.println("service record at "+count+" with ovcId "+service.getOvcId()+" updated with birthRegDetails");
                            }
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
    /*public void upgradeBirthRegistrationInformationAtServiceProvision(boolean forceUpdate)
    {
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        OvcServiceDao servicedao=new OvcServiceDaoImpl();
        BirthRegistrationDao brdao=new BirthRegistrationDaoImpl();
        BirthRegistration br=null;
        int count=0;
        try
       {
            int noOfBirthRegistrationRecordsAtService=servicedao.getNumberOfOvcProvidedBirthRegistrationServices(" ");
            int noOfBirthRegistrationRecords=brdao.getCountOfBirthRegistration("service");
            if((noOfBirthRegistrationRecordsAtService > noOfBirthRegistrationRecords) || forceUpdate)
            {
                List listOfCommunityCodes=hhedao.getDistinctCommunityCodes();
                
                if(listOfCommunityCodes !=null)
                {
                    String additionalQuery=null;
                    OvcService service=null;
                    for(int i=0; i<listOfCommunityCodes.size(); i++)
                    {
                        additionalQuery=" and hhe.communityCode='"+listOfCommunityCodes.get(i).toString() +"'";
                        List serviceList=servicedao.getServiceRecordsWithBirthRegistrationDetails(additionalQuery);
                        if(serviceList !=null)
                        {
                            for(int j=0; j<serviceList.size(); j++)
                            {
                                service=(OvcService)serviceList.get(j);
                                br=new BirthRegistration();
                                br.setClientId(service.getOvcId());
                                br.setClientType("ovc");
                                br.setDateModified(appUtil.getCurrentDate());
                                br.setDateOfRegistration(service.getServicedate());
                                br.setBirthRegistrationStatus("Yes");
                                br.setRecordStatus("active");
                                br.setPointOfUpdate("service");
                                brdao.saveBirthRegistration(br);
                                count++;
                                System.err.println("service record at "+count+" with ovcId "+service.getOvcId()+" updated with birthRegDetails");
                            }
                        }
                    }

                }
            }
       }
       catch(Exception ex)
       {
           ex.printStackTrace();
       }
    }*/
    public void upgradeBirthRegistrationInformationAtFollowupAssessment(boolean forceUpdate)
    {
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        FollowupDao fudao=new FollowupDaoImpl();
        BirthRegistrationDao brdao=new BirthRegistrationDaoImpl();
        BirthRegistration br=null;
        int count=0;
        try
       {
           int numberOfOvcWithBirthRegistrationDetails=fudao.getNumberOfFollowedupRecordsWithBirthRegistrationDetails(" ");
            int noOfBirthRegistrationRecords=brdao.getCountOfBirthRegistration("followup");
            if((numberOfOvcWithBirthRegistrationDetails > noOfBirthRegistrationRecords) || forceUpdate)
            {
                List listOfCommunityCodes=hhedao.getDistinctCommunityCodes();
                
                if(listOfCommunityCodes !=null)
                {
                    String additionalQuery=null;
                    FollowupSurvey fu=null;
                    for(int i=0; i<listOfCommunityCodes.size(); i++)
                    {
                        additionalQuery=" and hhe.communityCode='"+listOfCommunityCodes.get(i).toString() +"'";
                        List followupList=fudao.getFollowedupRecordsWithBirthRegistrationDetails(additionalQuery);
                        if(followupList !=null)
                        {
                            for(int j=0; j<followupList.size(); j++)
                            {
                                fu=(FollowupSurvey)followupList.get(j);
                                br=new BirthRegistration();
                                br.setClientId(fu.getOvcId());
                                br.setClientType("ovc");
                                br.setDateModified(appUtil.getCurrentDate());
                                br.setDateOfRegistration(fu.getDateOfSurvey());
                                br.setBirthRegistrationStatus(fu.getUpdatedBirthCertStatus());
                                br.setRecordStatus("active");
                                br.setPointOfUpdate("followup");
                                brdao.saveBirthRegistration(br);
                                count++;
                                System.err.println("followup record at "+count+" with ovcId "+fu.getOvcId()+" updated with birthRegDetails");
                            }
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
    public void updateHivStatusInFormsAndRecreateHivStatusTable()
    {
        try
        {
       if(!TaskManager.isDatabaseLocked())
        {
            DaoUtil util=new DaoUtil();
           TaskManager.setDatabaseLocked(true);
                      
           updateOvcEnrollmentHivInformationStatus(false);
           updateServiceHivInformationStatus(false);
           updateOvcFollowupHivInformationStatus(false);
           
           DatabaseUtilities dbUtil=new DatabaseUtilities();
           dbUtil.dropAndCreateHivStatusUpdateTable();
           updateHivStatus(false);
           util.getHivStatusUpdateDaoInstance().updateHivTreatmentStatus(hivOnTreatmentList);
           TaskManager.setDatabaseLocked(false);
        }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void setActiveHivStatus()
    {
        DaoUtil util=new DaoUtil();
        try
        {
            if(!TaskManager.isDatabaseLocked())
            {
                TaskManager.setDatabaseLocked(true); 
                    List communityCodeList=util.getHouseholdEnrollmentDaoInstance().getDistinctCommunityCodes();
                    if(communityCodeList !=null && !communityCodeList.isEmpty())
                    {
                        setActiveHivStatusForOvc(communityCodeList);
                        setActiveHivStatusForCaregivers(communityCodeList);
                        setActiveHivStatusForHouseholdHeads(communityCodeList);
                    }
                TaskManager.setDatabaseLocked(false); 
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void setActiveHivStatusForOvc(List communityCodeList)
    {
        DaoUtil util=new DaoUtil();
        try
        {
            //List communityCodeList=util.getHouseholdEnrollmentDaoInstance().getDistinctCommunityCodes();
            if(communityCodeList !=null && !communityCodeList.isEmpty())
            {
                String communityCode=null;
                for(Object obj:communityCodeList)
                {
                    communityCode=(String)obj;
                    List ovcIdList=util.getOvcDaoInstance().getDistinctOvcIdsByCommunity(communityCode);
                    util.getHivStatusUpdateDaoInstance().setActiveHivStatus(ovcIdList);
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void setActiveHivStatusForCaregivers(List communityCodeList)
    {
        DaoUtil util=new DaoUtil();
        try
        {
            if(communityCodeList !=null && !communityCodeList.isEmpty())
            {
                String communityCode=null;
                for(Object obj:communityCodeList)
                {
                    communityCode=(String)obj;
                    List idList=util.getCaregiverDaoInstance().getDistinctCaregiverIdsByCommunity(communityCode);
                    util.getHivStatusUpdateDaoInstance().setActiveHivStatus(idList);
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void setActiveHivStatusForHouseholdHeads(List communityCodeList)
    {
        DaoUtil util=new DaoUtil();
        try
        {
            if(communityCodeList !=null && !communityCodeList.isEmpty())
            {
                String communityCode=null;
                for(Object obj:communityCodeList)
                {
                    communityCode=(String)obj;
                    List idList=util.getHouseholdEnrollmentDaoInstance().getDistinctHouseholdUniqueIdByCommunityCode(communityCode);
                    util.getHivStatusUpdateDaoInstance().setActiveHivStatus(idList);
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void updateHivStatus(boolean forceUpdate)
    {
       updateBaselineHivStatus(forceUpdate);
       updateOvcServiceHivStatus(forceUpdate);
       updateOvcFollowupHivStatus(forceUpdate);
       updateCaregiverBaselineHivStatus(forceUpdate);
       updateCaregiverHivStatusAtFollowup(forceUpdate);
    }
    public void updateBaselineHivStatus(boolean forceUpdate)
    {
       dbutils.createHivStatusUpdateTable();
       HivStatusUpdateDao hsudao=new HivStatusUpdateDaoImpl();
       HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
       OvcDao ovcdao=new OvcDaoImpl();
       
       try
       {
            int noOfOVC=ovcdao.getNumberOfOvcEnrolledPerCohort(" ");
            int noOfOvcRecordsInHivStatusUpdate=hsudao.getNumberOfHivStatusUpdatesByClientType("ovc","enrollment");
            if(noOfOVC > noOfOvcRecordsInHivStatusUpdate || forceUpdate)
            {
                List listOfCommunityCodes=hhedao.getDistinctCommunityCodes();
                HivStatusUpdate hsu=null;
                if(listOfCommunityCodes !=null)
                {
                    String additionalQuery=null;
                    Ovc ovc=null;
                    for(int i=0; i<listOfCommunityCodes.size(); i++)
                    {
                        additionalQuery=" and hhe.communityCode='"+listOfCommunityCodes.get(i).toString() +"'";
                        List ovcList=ovcdao.getListOfOvcEnrolledPerCohort(additionalQuery);
                        if(ovcList !=null)
                        {
                            for(int j=0; j<ovcList.size(); j++)
                            {
                                ovc=(Ovc)ovcList.get(j);
                                hsu=new HivStatusUpdate();
                                if(ovc.getEnrolledInCare()!=null)
                                hsu.setClientEnrolledInCare(ovc.getEnrolledInCare());
                                hsu.setEnrolledOnART(ovc.getEnrolledOnART());
                                hsu.setClientId(ovc.getOvcId());
                                hsu.setClientType(NomisConstant.OVC_TYPE);
                                hsu.setDateModified(DateManager.getCurrentDate());
                                hsu.setDateOfCurrentStatus(ovc.getDateEnrollment());
                                hsu.setHivStatus(HivRecordsManager.getHivStatusScode(ovc.getHivStatus()));
                                hsu.setRecordStatus("active");
                                hsu.setPointOfUpdate(NomisConstant.ENROLLMENT_POINTOFUPDATE);
                                hsudao.saveHivStatusUpdate(hsu);
                                System.err.println("ovc with ovcId "+ovc.getOvcId()+" updated with HIV Details");
                            }
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
    public void updateOvcServiceHivStatus(boolean forceUpdate)
    {
       //dbutils.createHivStatusUpdateTable();
       HivStatusUpdateDao hsudao=new HivStatusUpdateDaoImpl();
       OvcServiceDao sdao=new OvcServiceDaoImpl();
       AppUtility appUtil=new AppUtility();
       try
       {
           HivStatusUpdate hsu=null;
                  
            List list=sdao.getServiceRecordsWithHivInformation();
            if(list !=null)
            {
                OvcService service=null;
                for(int j=0; j<list.size(); j++)
                {
                    service=(OvcService)list.get(j);
                    hsu=new HivStatusUpdate();
                    hsu.setClientEnrolledInCare(service.getEnrolledInCare());
                    hsu.setEnrolledOnART(service.getEnrolledOnART());
                    hsu.setClientId(service.getOvcId());
                    hsu.setClientType(NomisConstant.OVC_TYPE);
                    hsu.setDateModified(appUtil.getCurrentDate());
                    hsu.setDateOfCurrentStatus(service.getServicedate());
                    hsu.setHivStatus(HivRecordsManager.getHivStatusScode(service.getCurrentHivStatus()));
                    hsu.setRecordStatus("active");
                    hsu.setPointOfUpdate(NomisConstant.SERVICE_POINTOFUPDATE);
                    hsudao.saveHivStatusUpdate(hsu);
                    System.err.println("Service with ovcId "+service.getOvcId()+" updated with HIV Details");
                }
            }
   
       }
       catch(Exception ex)
       {
           ex.printStackTrace();
       }
    }
    public void updateOvcFollowupHivStatus(boolean forceUpdate)
    {
       //dbutils.createHivStatusUpdateTable();
       HivStatusUpdateDao hsudao=new HivStatusUpdateDaoImpl();
       HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
       FollowupDao fudao=new FollowupDaoImpl();
       AppUtility appUtil=new AppUtility();
       try
       {
           //int noOfOVC=fudao.getNoOfOVCNotFollowedupPerCohort(" ");
                int noOfOvcRecordsInHivStatusUpdate=hsudao.getNumberOfHivStatusUpdatesByClientType("ovc","followup");
            
            if(noOfOvcRecordsInHivStatusUpdate==0 || forceUpdate)
            {
                List listOfCommunityCodes=hhedao.getDistinctCommunityCodes();
                HivStatusUpdate hsu=null;
                if(listOfCommunityCodes !=null)
                {
                    String additionalQuery=null;
                    FollowupSurvey fu=null;
                    for(int i=0; i<listOfCommunityCodes.size(); i++)
                    {
                        additionalQuery=" and hhe.communityCode='"+listOfCommunityCodes.get(i).toString() +"'";
                        List followupList=fudao.getFollowupSurvey(additionalQuery);
                        if(followupList !=null)
                        {
                            for(int j=0; j<followupList.size(); j++)
                            {
                                fu=(FollowupSurvey)followupList.get(j);
                                hsu=new HivStatusUpdate();
                                hsu.setClientEnrolledInCare(fu.getEnrolledInCare());
                                hsu.setEnrolledOnART(fu.getEnrolledOnART());
                                hsu.setClientId(fu.getOvcId());
                                hsu.setClientType(NomisConstant.OVC_TYPE);
                                hsu.setDateModified(appUtil.getCurrentDate());
                                hsu.setDateOfCurrentStatus(fu.getDateOfSurvey());
                                hsu.setHivStatus(HivRecordsManager.getHivStatusScode(fu.getUpdatedHivStatus()));
                                hsu.setRecordStatus("active");
                                hsu.setPointOfUpdate(NomisConstant.FOLLOWUP_POINTOFUPDATE);
                                hsudao.saveHivStatusUpdate(hsu);
                                System.err.println("Followup with ovcId "+fu.getOvcId()+" updated with HIV Details");
                            }
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
    public void updateCaregiverBaselineHivStatus(boolean forceUpdate)
    {
       //dbutils.createHivStatusUpdateTable();
       HivStatusUpdateDao hsudao=new HivStatusUpdateDaoImpl();
       HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
       CaregiverDao cgiverdao=new CaregiverDaoImpl();
       AppUtility appUtil=new AppUtility();
       try
       {
                //int noOfCaregivers=cgiverdao.getNoOfCaregiversPerCohort(" ");
                int noOfCaregiverRecordsInHivStatusUpdate=hsudao.getNumberOfHivStatusUpdatesByClientType("caregiver","enrollment");
            if(noOfCaregiverRecordsInHivStatusUpdate==0 || forceUpdate)
            {
                List listOfCommunityCodes=hhedao.getDistinctCommunityCodes();
                HivStatusUpdate hsu=null;
                if(listOfCommunityCodes !=null)
                {
                    String additionalQuery=null;
                    Caregiver cgiver=null;
                    for(int i=0; i<listOfCommunityCodes.size(); i++)
                    {
                        additionalQuery=" and hhe.communityCode='"+listOfCommunityCodes.get(i).toString() +"'";
                        List list=cgiverdao.getListOfCaregivers(additionalQuery);
                        if(list !=null)
                        {
                            for(int j=0; j<list.size(); j++)
                            {
                                cgiver=(Caregiver)list.get(j);
                                hsu=new HivStatusUpdate();
                                hsu.setClientEnrolledInCare(cgiver.getEnrolledInCare());
                                hsu.setEnrolledOnART(cgiver.getEnrolledOnART());
                                hsu.setClientId(cgiver.getCaregiverId());
                                hsu.setClientType(NomisConstant.Caregiver_TYPE);
                                hsu.setDateModified(appUtil.getCurrentDate());
                                hsu.setDateOfCurrentStatus(cgiver.getDateOfEnrollment());
                                hsu.setHivStatus(HivRecordsManager.getHivStatusScode(cgiver.getCgiverHivStatus()));
                                hsu.setRecordStatus("active");
                                hsu.setPointOfUpdate(NomisConstant.ENROLLMENT_POINTOFUPDATE);
                                hsudao.saveHivStatusUpdate(hsu);
                                System.err.println("Caregiver with caregiverId "+cgiver.getCaregiverId()+" updated with HIV Details");
                            }
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
    public void updateCaregiverHivStatusAtFollowup(boolean forceUpdate)
    {
       //dbutils.createHivStatusUpdateTable();
       HivStatusUpdateDao hsudao=new HivStatusUpdateDaoImpl();
       HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
       CaregiverDao cgiverdao=new CaregiverDaoImpl();
       AppUtility appUtil=new AppUtility();
       try
       {
                int noOfCaregivers=cgiverdao.getNoOfCaregiversFollowedupPerCohort(" ");
                int noOfCaregiverRecordsInHivStatusUpdate=hsudao.getNumberOfHivStatusUpdatesByClientType("caregiver","followup");
            if(noOfCaregiverRecordsInHivStatusUpdate==0 || forceUpdate)
            {
                List listOfCommunityCodes=hhedao.getDistinctCommunityCodes();
                HivStatusUpdate hsu=null;
                if(listOfCommunityCodes !=null)
                {
                    String additionalQuery=null;
                    Caregiver cgiver=null;
                    for(int i=0; i<listOfCommunityCodes.size(); i++)
                    {
                        additionalQuery=" and hhe.communityCode='"+listOfCommunityCodes.get(i).toString() +"'";
                        List list=cgiverdao.getListOfCaregiversFollowedupPerCohort(additionalQuery);
                        if(list !=null)
                        {
                            for(int j=0; j<list.size(); j++)
                            {
                                cgiver=(Caregiver)list.get(j);
                                hsu=new HivStatusUpdate();
                                hsu.setClientEnrolledInCare(cgiver.getEnrolledInCare());
                                hsu.setEnrolledOnART(cgiver.getEnrolledOnART());
                                hsu.setClientId(cgiver.getCaregiverId());
                                hsu.setClientType(NomisConstant.Caregiver_TYPE);
                                hsu.setDateModified(appUtil.getCurrentDate());
                                hsu.setDateOfCurrentStatus(cgiver.getDateOfEnrollment());
                                hsu.setHivStatus(HivRecordsManager.getHivStatusScode(cgiver.getCgiverHivStatus()));
                                hsu.setRecordStatus("active");
                                hsu.setPointOfUpdate(NomisConstant.FOLLOWUP_POINTOFUPDATE);
                                hsudao.saveHivStatusUpdate(hsu);
                                System.err.println("Caregiver with caregiverId "+cgiver.getCaregiverId()+" at followup updated with HIV Details");
                            }
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
    
}
