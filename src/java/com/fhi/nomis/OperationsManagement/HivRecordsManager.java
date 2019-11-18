/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.nomis.OperationsManagement;

import com.fhi.kidmap.business.Caregiver;
import com.fhi.kidmap.business.FollowupSurvey;
import com.fhi.kidmap.business.HivStatus;
import com.fhi.kidmap.business.HivStatusUpdate;
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.business.OvcReferral;
import com.fhi.kidmap.business.OvcService;
import com.fhi.kidmap.dao.CaregiverDao;
import com.fhi.kidmap.dao.CaregiverDaoImpl;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.FollowupDao;
import com.fhi.kidmap.dao.HivStatusUpdateDao;
import com.fhi.kidmap.dao.OvcDao;
import com.fhi.nomis.nomisutils.NomisConstant;
import com.fhi.nomis.nomisutils.TaskManager;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author smomoh
 */
public class HivRecordsManager 
{
    DaoUtil util=new DaoUtil();
    int hivPosActiveCount=0;
    int hivPosOnTreatmentCount=0;
    List hivOnTreatmentList=new ArrayList();
    HivStatusUpdateDao hsudao=util.getHivStatusUpdateDaoInstance();
    public void updateOvcReferralWithHTCReferralService()
    {
        try
        {
            List list=util.getOvcServiceDaoInstance().getHTCReferralServiceRecordsFromOvcService();
            if(list !=null)
            {
                OvcService service=null;
                OvcReferral referral=null;
                OvcReferral htcServiceReferral=null;
                for(int i=0; i<list.size(); i++)
                {
                    service=(OvcService)list.get(i);
                    referral=util.getOvcReferralDaoInstance().getOvcReferral(service.getOvcId(), service.getServicedate());
                    if(referral==null)
                    {
                        htcServiceReferral=new OvcReferral();
                        htcServiceReferral.setDateOfReferral(service.getServicedate());
                        htcServiceReferral.setOvcId(service.getOvcId());
                        htcServiceReferral.setHealthServices("HIV counselling and testing");
                        htcServiceReferral.setType(NomisConstant.OVC_TYPE);
                        htcServiceReferral.setReferralCompleted("No");
                        util.getOvcReferralDaoInstance().saveOvcReferral(htcServiceReferral);
                        System.err.println("OvcReferral with id "+htcServiceReferral.getOvcId()+"updated");
                    }
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    public void updateLastHivTrackingDate()
    {
        
        DaoUtil util=new DaoUtil();
        HivStatusUpdateDao hsudao=util.getHivStatusUpdateDaoInstance();
        try
        {
            if(!TaskManager.isHivUpdateInProgress())
            {
                List communityCodeList=util.getHouseholdEnrollmentDaoInstance().getDistinctCommunityCodes();
                if(communityCodeList !=null)
                {
                    int count=0;
                    TaskManager.setHivUpdateInProgress(true);
                    List hivList=null;
                    HivStatusUpdate hsu=null;
                    CareAndSupportManager cscm=new CareAndSupportManager();
                    for(Object obj:communityCodeList)
                    {
                        String communityCode=(String)obj;
                        System.err.println("communityCode in HivRecordsManager.updateLastHivTrackingDate() is "+communityCode);
                        hivList=hsudao.getHivStatusUpdateWithDefaultLastHivTrackingDateByCommunity(communityCode);
                        if(hivList !=null)
                        {
                            for(int i=0; i<hivList.size(); i++)
                            {
                                count ++;
                                hsu=(HivStatusUpdate)hivList.get(i);
                                if(cscm.updateHivStatusUpdateWithCareAndSupportInformation(hsu)<1)
                                {
                                    hsudao.updateHivStatusUpdate(hsu);
                                    /*if(updateHivDetailsWithHTCDetails(hsu)<1);
                                    {
                                        //if(updateHivDetailsWithHTCReferralDetails(hsu)<1)
                                        
                                    }*/
                                }
                                System.err.println("hsu with id "+hsu.getClientId()+" updated--"+count);
                            }
                        }
                    }
                    TaskManager.setHivUpdateInProgress(false);
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public String updateClientTypeInHivStatusUpdate()
    {
        String message="";
        try
        {
            message=hsudao.updateClientTypeOnOvcHivStatusUpdate()+" OVC types corrected";
            message+=", "+hsudao.updateClientTypeOnCaregiverHivStatusUpdate()+" caregiver types corrected";
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return message;
    }
    public String resetClientIdForHouseholdHeadsInHivStatusUpdate()
    {
        String msg="";
        try
        {
            DaoUtil util=new DaoUtil();
            List hivRecordsList=util.getHivStatusUpdateDaoInstance().getHouseholdAndCaregiverHivStatusRecords(null);
            if(hivRecordsList !=null && !hivRecordsList.isEmpty())
            {
                System.err.println("hivRecordsList size is "+hivRecordsList.size());
                int count=0;
                HivStatusUpdate hsu=null;
                String hsuClientId=null;
                for(Object obj:hivRecordsList)
                {
                    hsu=(HivStatusUpdate)obj;
                    //System.err.println("hsu id is "+hsu.getClientId());
                    hsuClientId=hsu.getClientId();
                    Caregiver cgiver=util.getCaregiverDaoInstance().getCaregiverByCaregiverId(hsuClientId);//.getCaregiverByHhUniqueIdAndHhHeadStatus(hsuClientId,"1");
                    if(cgiver==null)
                    cgiver=util.getCaregiverDaoInstance().getCaregiverByHhUniqueIdAndHhHeadStatus(hsuClientId,"1");
                    if(cgiver !=null)
                    {
                        //if(util.getHivStatusUpdateDaoInstance().getActiveHivStatusUpdatesByClientId(cgiver.getCaregiverId())==null)
                        //{
                            count++;
                            //set the caregiver id for this household head and save a new hiv status record for it
                            hsu.setClientId(cgiver.getCaregiverId());
                            hsu.setClientType(NomisConstant.Caregiver_TYPE);
                            util.getHivStatusUpdateDaoInstance().updateHivStatusOnly(hsu);
                            
                            msg=count+" Caregiver HIV status records Id corrected";
                            System.err.println("Record Id changed from "+hsuClientId+" to "+hsu.getClientId()+" at "+count);
                        //}
                    }
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return msg;
    }
    public void updateCaregiverHivStatusWithBaselineStatus()
    {
        try
        {
            CaregiverDao cgiverdao=new CaregiverDaoImpl();
            //cgiverdao.updateCaregiverHivStatusWithCorrectLabel();
            //int updateCount=hsudao.updateHivStatusWithCorrectLabel();
            //System.err.println(updateCount+" records updated with correct hiv status");
            String msg=resetClientIdForHouseholdHeadsInHivStatusUpdate();
            int cgiverCount=cgiverdao.processHiVStatusForCaregiversWithoutHIVStatusRecordInHivStatusUpdate();
            int count=hsudao.updateCaregiverHivStatusWithBaselineHivStatus();
            int hhscount=hsudao.updateCaregiverHivStatusWithHivStatusAtService();
            int hsucount=hsudao.updateCaregiverActiveHivStatusWithPreviousHivStatus();
            //updateCount=hsudao.updateHivStatusWithCorrectLabel();
            //System.err.println(updateCount+" records updated with correct hiv status");
            System.err.println(msg);
            System.err.println(cgiverCount+" new caregiver hiv records inserted in hiv status update");
            System.err.println(count+" caregiver records updated with baseline hiv status");
            System.err.println(hhscount+" caregiver records updated with household service hiv status");
            System.err.println(hsucount+" caregiver hiv status update records updated with previous hiv status");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void updateOvcHivStatusWithBaselineStatus()
    {
        try
        {
            List list=hsudao.getOvcHivStatusByStatusAndClientType(NomisConstant.HIV_UNKNOWN, NomisConstant.OVC_TYPE);
            if(list !=null && !list.isEmpty())
            {
                System.err.println("List size inupdateOvcHivStatusWithBaselineStatus()  is "+list.size());
                OvcDao dao=util.getOvcDaoInstance();
                Ovc ovc=null;
                HivStatusUpdate hsu=null;
                String hivStatus=null;
                for(Object obj:list)
                {
                    hsu=(HivStatusUpdate)obj;
                    ovc=dao.getOvc(hsu.getClientId());
                    if(ovc !=null)
                    {
                        //System.err.println("Ovc with "+ovc.getOvcId()+" about to be updated in inupdateOvcHivStatusWithBaselineStatus() ");
                        hivStatus=ovc.getHivStatus();
                        if(hivStatus !=null && hivStatus.indexOf("unknown") ==-1)
                        {
                            if(hivStatus.indexOf(NomisConstant.HIV_NEGATIVE) !=-1 || hivStatus.indexOf(NomisConstant.HIV_POSITIVE) !=-1)
                            {
                                hsu.setHivStatus(hivStatus);
                                hsudao.updateHivStatusUpdate(hsu);
                                System.err.println("Hsu with "+hsu.getClientId()+" updated with "+hsu.getHivStatus());
                            }
                        }
                        else
                        {
                            updateOvcHivStatusWithHivServiceRecords(hsu);
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
    /*public int setHivStatusRecordForAllOvcInHivStatusUpdate()
    {
        int count=0;
        try
        {
            
                    List ovcList=util.getOvcDaoInstance().getListOfOvcWithoutHivRecords();
                    if(ovcList !=null && !ovcList.isEmpty())
                    {
                        Ovc ovc=null;
                        for(Object ovcObj:ovcList)
                        {
                            ovc=(Ovc)ovcObj;
                            util.getOvcDaoInstance().saveOvcHIVStatus(ovc);
                            System.err.println("Ovc with Id "+ovc.getOvcId()+" in HivRecordsManager.setHivStatusRecordForAllOvcInHivStatusUpdate() saved in HivStatusUpdate");
                            count++;
                        }
                    }
                
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        System.err.println(count+" Hiv records saved");
        return count;
    }*/
    public int setHivStatusRecordForAllOvcInHivStatusUpdate()
    {
        int count=0;
        try
        {
            List communityList=util.getHouseholdEnrollmentDaoInstance().getDistinctCommunityCodes();
            if(communityList !=null && !communityList.isEmpty())
            {
                String communityCode=null;
                for(Object obj:communityList)
                {
                    if(obj !=null)
                    communityCode=obj.toString();
                    else
                    communityCode=(String)obj;
                    List ovcList=util.getOvcDaoInstance().getListOfOvcByCommunity(communityCode);
                    List hsuList=null;
                    if(ovcList !=null && !ovcList.isEmpty())
                    {
                        System.err.println("ovcList size in HivRecordsManager.setHivStatusRecordForAllOvcInHivStatusUpdate() is "+ovcList.size());
                        Ovc ovc=null;
                        for(Object ovcObj:ovcList)
                        {
                            ovc=(Ovc)ovcObj;
                            hsuList=util.getHivStatusUpdateDaoInstance().getAllHivStatusUpdatesPerClient(ovc.getOvcId());
                            if(hsuList ==null || hsuList.isEmpty())
                            {
                                util.getOvcDaoInstance().saveOvcHIVStatus(ovc);
                                System.err.println("Ovc with Id "+ovc.getOvcId()+" in HivRecordsManager.setHivStatusRecordForAllOvcInHivStatusUpdate() saved in HivStatusUpdate");
                                count++;
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
        System.err.println(count+" Hiv records saved");
        return count;
    }
    public void updateOvcHivStatusWithHivServiceRecords(HivStatusUpdate hsu)
    {
        try
        {
            OvcDao dao=util.getOvcDaoInstance();
            Ovc ovc=null;
            String hivStatus=null;
                ovc=dao.getOvc(hsu.getClientId());
                if(ovc !=null)
                {
                    //System.err.println("Ovc with "+ovc.getOvcId()+" about to be updated in inupdateOvcHivStatusWithBaselineStatus() ");
                    hivStatus=ovc.getHivStatus();
                    if(hivStatus !=null && hivStatus.indexOf("unknown") !=-1)
                    {
                        List hivList=util.getOvcServiceDaoInstance().getServiceRecordsWithKnownHivStatusById(ovc.getOvcId());
                        if(hivList !=null && !hivList.isEmpty())
                        {
                            OvcService service=(OvcService)hivList.get(0);
                            hivStatus=service.getCurrentHivStatus();
                            if(service.getCurrentHivStatus() !=null)
                            {
                                if((service.getCurrentHivStatus().indexOf(NomisConstant.HIV_NEGATIVE) !=-1) || (service.getCurrentHivStatus().indexOf(NomisConstant.HIV_POSITIVE) !=-1))
                                {
                                    hsu.setHivStatus(service.getCurrentHivStatus());
                                    hsudao.updateHivStatusUpdate(hsu);
                                    System.err.println("Hsu with "+hsu.getClientId()+" in updateOvcHivStatusWithHivServiceRecords 1 updated with "+hsu.getHivStatus());
                                }
                            }
                        }
                        else
                        {
                            //updateHivDetailsWithHTCDetails(hsu);
                            
                        }
                    }
                }   
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    /*public int updateHivDetailsWithHTCDetails(HivStatusUpdate hsu)
    {
       int count=0;
       try
       {
           List list=util.getOvcServiceDaoInstance().getHTCReferralServiceRecordById(hsu.getClientId());
            if(list !=null && !list.isEmpty())
            {
                OvcService service=(OvcService)list.get(0);
                hsu.setHivStatus(NomisConstant.HIV_NEGATIVE);
                hsu.setDateOfCurrentStatus(service.getServicedate());
                hsudao.updateHivStatusUpdate(hsu);
                count++;
                System.err.println("Hsu with "+hsu.getClientId()+" in updateHivDetailsWithHTCDetails updated with "+hsu.getHivStatus());
            }
            else
            {
                count=updateHivDetailsWithHTCReferralDetails(hsu);
            }
       }
       catch(Exception ex)
       {
           ex.printStackTrace();
       }
       return count;
    }*/
    public int updateHivDetailsWithHTCReferralDetails(HivStatusUpdate hsu)
    {
       int count=0;
       try
       {
            List list=util.getOvcReferralDaoInstance().getHTCReferralServiceRecordById(hsu.getClientId());
            if(list !=null && !list.isEmpty())
            {
                OvcReferral ref=(OvcReferral)list.get(0);
                hsu.setHivStatus(NomisConstant.HIV_NEGATIVE);
                hsu.setDateOfCurrentStatus(ref.getDateOfReferral());
                hsudao.updateHivStatusUpdate(hsu);
                count++;
                System.err.println("Hsu with "+hsu.getClientId()+" in updateHivDetailsWithHTCReferralDetails updated with "+hsu.getHivStatus());
            }
       }
       catch(Exception ex)
       {
           ex.printStackTrace();
       }
       return count;
    }
    //getHTCReferralServiceRecordById(String ovcId,String startDate)
    public void updateOvcHivStatusWithFollowupStatus()
    {
        try
        {
            List list=hsudao.getOvcHivStatusByStatusAndClientType(NomisConstant.HIV_UNKNOWN, NomisConstant.OVC_TYPE);
            if(list !=null && !list.isEmpty())
            {
                System.err.println("List size in updateOvcHivStatusWithFollowupStatus()  is "+list.size());
                FollowupDao fudao=util.getFollowupDaoInstance();
                FollowupSurvey fu=null;
                //Ovc ovc=null;
                HivStatusUpdate hsu=null;
                String hivStatus=null;
                for(Object obj:list)
                {
                    hsu=(HivStatusUpdate)obj;
                    List fuList=fudao.getFollowedupRecordsWithKnownHivStatusOrderedByDateDesc(hsu.getClientId());
                    if(fuList !=null && !fuList.isEmpty())
                    {
                        fu=(FollowupSurvey)fuList.get(0);
                        //System.err.println("Ovc with "+ovc.getOvcId()+" about to be updated in inupdateOvcHivStatusWithBaselineStatus() ");
                        hivStatus=fu.getUpdatedHivStatus();
                        if(hivStatus !=null && hivStatus.indexOf("unknown") ==-1)
                        {
                            if(hivStatus.indexOf(NomisConstant.HIV_NEGATIVE) !=-1 || hivStatus.indexOf(NomisConstant.HIV_POSITIVE) !=-1)
                            {
                                hsu.setHivStatus(hivStatus);
                                hsudao.updateHivStatusUpdate(hsu);
                                System.err.println("Hsu with "+hsu.getClientId()+" updated with "+hsu.getHivStatus());
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
    
    public void setActiveHivStatusWithRecordsInHivStatusUpdate()
    {
        try
        {
            List list=hsudao.getOvcHivStatusByStatusAndClientType(NomisConstant.HIV_UNKNOWN, NomisConstant.OVC_TYPE);
            if(list !=null && !list.isEmpty())
            {
                System.err.println("List size setActiveHivStatusWithRecordsInHivStatusUpdate()  is "+list.size());
                
                List hivStatusList=null;
                HivStatusUpdate hsu=null;
                HivStatusUpdate hsu2=null;
                for(Object obj:list)
                {
                    hsu=(HivStatusUpdate)obj;
                    hivStatusList=hsudao.getAllHivStatusUpdatesPerClient(hsu.getClientId());
                    if(hivStatusList !=null && !hivStatusList.isEmpty())
                    {
                        for(int i=0; i<hivStatusList.size(); i++)
                        {
                            hsu2=(HivStatusUpdate)hivStatusList.get(i);
                            if(hsu2 !=null && hsu2.getHivStatus() !=null && !hsu2.getHivStatus().equalsIgnoreCase(NomisConstant.HIV_UNKNOWN))
                            { 
                                //System.err.println("HSU records with "+hsu.getClientId()+" about to be updated in setActiveHivStatusWithRecordsInHivStatusUpdate() ");
                                if(HivStatus.getStatusWeight(hsu2.getHivStatus()) >= HivStatus.getStatusWeight(hsu.getHivStatus()))
                                {
                                    hsu.setHivStatus(hsu2.getHivStatus());
                                    if(i==(hivStatusList.size()-1))
                                    {
                                        hsudao.updateHivStatusUpdate(hsu);
                                        System.err.println("Hsu with "+hsu.getClientId()+" in setActiveHivStatusWithRecordsInHivStatusUpdate() updated with "+hsu.getHivStatus());
                                    }
                                }
                                
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
    public static void deleteHivStatusRecord(String clientId, String dateOfStatus,String pointOfUpdate)
    {
        try
        {
            DaoUtil util=new DaoUtil();
            HivStatusUpdate hsu=util.getHivStatusUpdateDaoInstance().getHivStatusUpdateByClientIdDateOfStatusAndPointOfUpdate(clientId, dateOfStatus,pointOfUpdate);
            if(hsu !=null)
            util.getHivStatusUpdateDaoInstance().deleteHivStatusUpdate(hsu);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public static void deleteAllClientHivStatusRecord(String clientId)
    {
        try
        {
            DaoUtil util=new DaoUtil();
            HivStatusUpdate hsu=util.getHivStatusUpdateDaoInstance().getActiveHivStatusUpdatesByClientId(clientId);
            if(hsu !=null)
            util.getHivStatusUpdateDaoInstance().deleteAllHivStatusUpdatesPerClient(clientId);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public static String getHivStatusScode(String param)
    {
        String hivStatusCode=NomisConstant.HIV_UNKNOWN;
            if(param !=null)
            {
                if(param.indexOf(NomisConstant.HIV_POSITIVE) !=-1)
                hivStatusCode=NomisConstant.HIV_POSITIVE;
                else if(param.indexOf(NomisConstant.HIV_NEGATIVE) !=-1)
                hivStatusCode=NomisConstant.HIV_NEGATIVE;
                else if(param.indexOf(NomisConstant.HIV_TEST_NOT_DONE) !=-1)
                hivStatusCode=NomisConstant.HIV_TEST_NOT_DONE;
                else if(param.indexOf(NomisConstant.HIV_TEST_NOT_INDICATED) !=-1)
                hivStatusCode=NomisConstant.HIV_TEST_NOT_INDICATED;
                else if(param.indexOf(NomisConstant.HIV_RESULT_NOT_DISCLOSED) !=-1)
                hivStatusCode=NomisConstant.HIV_RESULT_NOT_DISCLOSED;
                //else
                //hivStatusCode=NomisConstant.HIV_UNKNOWN;
            }
        return hivStatusCode;
    }
    public static HivStatus getHivStatus(String hivStatusCode)
    {
        HivStatus hivStatus=new HivStatus();
        if(hivStatusCode==null)
        return null;
        else if(hivStatusCode.equals("positive"))
        {
            hivStatus.setHivStatusCode("positive");
            hivStatus.setHivStatusName("HIV positive");
        }
        else if(hivStatusCode.equals("negative"))
        {
            hivStatus.setHivStatusCode("negative");
            hivStatus.setHivStatusName("HIV negative");
        }
        else if(hivStatusCode.equals("unknown"))
        {
            hivStatus.setHivStatusCode("unknown");
            hivStatus.setHivStatusName("HIV unknown");
        }
        else if(hivStatusCode.equals("unk_tni"))
        {
            hivStatus.setHivStatusCode("unk_tni");
            hivStatus.setHivStatusName("HIV unknown(Test not indicated)");
        }
        else if(hivStatusCode.equals("unk_tnd"))
        {
            hivStatus.setHivStatusCode("unk_tnd");
            hivStatus.setHivStatusName("HIV unknown(Test not done)");
        }
        else if(hivStatusCode.equals("unk_rnd"))
        {
            hivStatus.setHivStatusCode("unk_rnd");
            hivStatus.setHivStatusName("HIV unknown(Result not disclosed)");
        }
        return hivStatus;
    }
    public static List getAllHivStatus()
    {
        List hivStatusList=new ArrayList();
        hivStatusList.add(getHivStatus("negative"));
        hivStatusList.add(getHivStatus("positive"));
        hivStatusList.add(getHivStatus("unknown"));
        return hivStatusList;
    }
    public static List loadNewHivStatus(String currentStatus)
    {
        List hivStatusList=new ArrayList();
        //System.err.println("currentStatus is "+currentStatus);
        if(currentStatus ==null || (currentStatus.indexOf("unknown")==-1 && currentStatus.indexOf("negative")==-1 && currentStatus.indexOf("positive")==-1 && currentStatus.indexOf("negpos")==-1))
        {
            //System.err.println("currentStatus 1 is "+currentStatus);
            hivStatusList.add(getHivStatus("negative"));
            hivStatusList.add(getHivStatus("positive"));
            hivStatusList.addAll(getHivUnknownStatusCategories());
            //hivStatusList.add(getHivStatus("unknown"));
            //hivStatusList.add(getHivStatus("unk_tni"));
            //hivStatusList.add(getHivStatus("unk_tnd"));
            //hivStatusList.add(getHivStatus("unk_rnd"));
            
        }
        else if(currentStatus.indexOf("unknown") !=-1)
        {
            hivStatusList.add(getHivStatus("negative"));
            hivStatusList.add(getHivStatus("positive"));
        }
        else if(currentStatus.indexOf("negative") !=-1)
        {
            hivStatusList.add(getHivStatus("negative"));
            hivStatusList.add(getHivStatus("positive"));
        }
        else if(currentStatus.indexOf("positive") !=-1)
        {
            hivStatusList.add(getHivStatus("positive"));
        }
        else if(currentStatus.indexOf("negpos") !=-1)
        {
            //hivStatusList.add(getHivStatus("unknown"));
            hivStatusList.add(getHivStatus("negative"));
            hivStatusList.add(getHivStatus("positive"));
            hivStatusList.addAll(getHivUnknownStatusCategories());
        }
        return hivStatusList;
    }
    public static List getHivUnknownStatusCategories()
    {
        List hivStatusList=new ArrayList();
        hivStatusList.add(getHivStatus("unknown"));
        //hivStatusList.add(getHivStatus("unk_tni"));
        //hivStatusList.add(getHivStatus("unk_tnd"));
        //hivStatusList.add(getHivStatus("unk_rnd"));
        return hivStatusList;
    }
    /*public String updateOvcEnrollmentHivInformationStatus(boolean forceUpdate)
    {
        AppUtility appUtil=new AppUtility();
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
        AppUtility appUtil=new AppUtility();
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
            //System.err.println("Number of Active positive on treatment is "+hivPosOnTreatmentCount);
        }
    }
    
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
    }*/
}
