/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nomis.databasemanagement;

import com.fhi.kidmap.business.Caregiver;
import com.fhi.kidmap.business.ChildStatusIndex;
import com.fhi.kidmap.business.FollowupSurvey;
import com.fhi.kidmap.business.HivRiskAssessmentChecklist;
import com.fhi.kidmap.business.HivStatusUpdate;
import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.kidmap.business.HouseholdFollowupAssessment;
import com.fhi.kidmap.business.HouseholdService;
import com.fhi.kidmap.business.HouseholdVulnerabilityAssessment;
import com.fhi.kidmap.business.NutritionAssessment;
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.business.OvcWithdrawal;
import com.fhi.kidmap.business.Wards;
import com.fhi.kidmap.dao.CaregiverDao;
import com.fhi.kidmap.dao.CaregiverDaoImpl;
import com.fhi.kidmap.dao.ChildStatusIndexDao;
import com.fhi.kidmap.dao.ChildStatusIndexDaoImpl;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.FollowupDao;
import com.fhi.kidmap.dao.FollowupDaoImpl;
import com.fhi.kidmap.dao.HouseholdEnrollmentDao;
import com.fhi.kidmap.dao.HouseholdEnrollmentDaoImpl;
import com.fhi.kidmap.dao.HouseholdFollowupAssessmentDao;
import com.fhi.kidmap.dao.HouseholdFollowupAssessmentDaoImpl;
import com.fhi.kidmap.dao.HouseholdServiceDao;
import com.fhi.kidmap.dao.HouseholdServiceDaoImpl;
import com.fhi.kidmap.dao.HouseholdVulnerabilityAssessmentDao;
import com.fhi.kidmap.dao.HouseholdVulnerabilityAssessmentDaoImpl;
import com.fhi.kidmap.dao.NutritionAssessmentDao;
import com.fhi.kidmap.dao.NutritionAssessmentDaoImpl;
import com.fhi.kidmap.dao.OvcDao;
import com.fhi.kidmap.dao.OvcDaoImpl;
import com.fhi.kidmap.dao.OvcWithdrawalDao;
import com.fhi.kidmap.dao.OvcWithdrawalDaoImpl;
import com.fhi.nomis.OperationsManagement.OvcServiceUtilityManager;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.nomis.nomisutils.DateManager;
import com.fhi.nomis.nomisutils.NomisConstant;
import com.fhi.nomis.nomisutils.TaskManager;
import com.nomis.upgrade.NomisUpgrade;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author smomoh
 */
public class DatabaseCleanup
{
    String msg="";
    
    public String updateHIVStatusInHivRiskAssessment()
    {
        int count=0;
        try
        {
            DaoUtil util=new DaoUtil();
            List communityList=util.getHouseholdEnrollmentDaoInstance().getDistinctCommunityCodes();
            if(communityList !=null && !communityList.isEmpty())
            {
                String communityCode=null;
                HivRiskAssessmentChecklist hrac=null;
                List hracList=null;
                for(Object obj:communityList)
                {
                    communityCode=(String)obj;
                    hracList=util.getHivRiskAssessmentChecklistDaoInstance().getAllHivRiskAssessmentByCommunity(communityCode);
                    if(hracList !=null)
                    {
                        for(Object obj2:hracList)
                        {
                            hrac=(HivRiskAssessmentChecklist)obj2;
                            /*if(hrac.getHivStatusQuestion() !=null && hrac.getHivStatusQuestion().equalsIgnoreCase("Yes"))
                            hrac.setHivStatus(NomisConstant.HIV_NEGATIVE);
                            else
                            hrac.setHivStatus(NomisConstant.HIV_UNKNOWN);*/
                            util.getHivRiskAssessmentChecklistDaoInstance().updateHivRiskAssessmentChecklist(hrac);
                            count++;
                            System.err.println(count+" HIV Risk Assessment records updated with HIV status");
                        }
                    }
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
       return count+" HIV Risk Assessment records updated"; 
    }
    /*public String updateHIVStatusInHivRiskAssessment()
    {
        int count=0;
        try
        {
            DaoUtil util=new DaoUtil();
            List communityList=util.getHouseholdEnrollmentDaoInstance().getDistinctCommunityCodes();
            if(communityList !=null && !communityList.isEmpty())
            {
                String communityCode=null;
                HivRiskAssessmentChecklist hrac=null;
                List hracList=null;
                for(Object obj:communityList)
                {
                    communityCode=(String)obj;
                    hracList=util.getHivRiskAssessmentChecklistDaoInstance().getListOfHivRiskAssessmentWithBlankHivStatus(communityCode);
                    if(hracList !=null)
                    {
                        for(Object obj2:hracList)
                        {
                            hrac=(HivRiskAssessmentChecklist)obj2;
                            if(hrac.getHivStatusQuestion() !=null && hrac.getHivStatusQuestion().equalsIgnoreCase("Yes"))
                            hrac.setHivStatus(NomisConstant.HIV_NEGATIVE);
                            else
                            hrac.setHivStatus(NomisConstant.HIV_UNKNOWN);
                            util.getHivRiskAssessmentChecklistDaoInstance().updateHivRiskAssessmentChecklist(hrac);
                            count++;
                            System.err.println(count+" HIV Risk Assessment records updated with HIV status");
                        }
                    }
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
       return count+" HIV Risk Assessment records updated with HIV status"; 
    }*/
    public String cleanCommunityCodes()
    {
        String msg="No Community code to cleaned up";
        int count=0;
        try
        {
            AppUtility appUtil=new AppUtility();
            DaoUtil util=new DaoUtil();
            List communityCodeList=util.getWardDaoInstance().getWardsWithSpacesInCode();
            if(communityCodeList !=null && !communityCodeList.isEmpty())
            {
                String communityCode=null;
                String cleanedCommunityCode="";
                for(Object obj:communityCodeList)
                {
                    cleanedCommunityCode="";
                    Wards ward=(Wards)obj;
                    communityCode=ward.getWard_code();
                    cleanedCommunityCode=appUtil.removeSpacesFromString(communityCode);
                    util.getWardDaoInstance().deleteWard(ward);
                    ward.setWard_code(cleanedCommunityCode);
                    Wards ward2=util.getWard(ward.getWard_code());
                    if(ward2==null)
                    util.getWardDaoInstance().saveWard(ward);
                    cleanCommunityCodeInHouseholdEnrollment(communityCode,cleanedCommunityCode);
                    System.err.println(communityCode+" cleaned to "+cleanedCommunityCode+" in dbc.cleanCommunityCodes()");
                    count++;
                }
            }
            msg=count+" Community codes cleaned";
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return msg;
    }
    public int correctHhUniqueIdInHouseholdService()
    {
        int count=0;
        try
        {
            DaoUtil util=new DaoUtil();
            List hhsList=util.getHouseholdServiceDaoInstance().getListOfHouseholdServiceRecordsMalformedHhUniqueId();
            if(hhsList !=null && !hhsList.isEmpty())
            {
                String hhUniqueId=null;
                String caregiverId=null;
                for(Object obj:hhsList)
                {
                    HouseholdService hhs=(HouseholdService)obj;
                    if(hhs !=null)
                    {
                        System.err.print(" "+hhs.getHhUniqueId()+"--"+hhs.getCaregiverId());
                        caregiverId=hhs.getCaregiverId();
                        if(caregiverId !=null && caregiverId.indexOf("/") !=-1)
                        {
                            String[] idArray=caregiverId.split("/");
                            if(idArray.length>3)
                            {
                                hhUniqueId=idArray[0]+"/"+idArray[1]+"/"+idArray[2]+"/"+idArray[3];
                                hhs.setHhUniqueId(hhUniqueId);
                                util.getHouseholdServiceDaoInstance().updateHouseholdService(hhs);
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
        return count;
    }
    public void createHouseholdEnrollmentId(String uncleanedCommunityCode,String cleanedCommunityCode)
    {
        try
        {
            DaoUtil util=new DaoUtil();
            List hheList=util.getHouseholdEnrollmentDaoInstance().getListOfHouseholdEnrollmentByCommunityCode(uncleanedCommunityCode);
            if(hheList !=null && !hheList.isEmpty())
            {
                for(Object obj:hheList)
                {
                    HouseholdEnrollment hhe=(HouseholdEnrollment)obj;
                    
                    //hhe.setCommunityCode(cleanedCommunityCode);
                    util.getHouseholdEnrollmentDaoInstance().updateHouseholdEnrollment(hhe);
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void cleanCommunityCodeInHouseholdEnrollment(String uncleanedCommunityCode,String cleanedCommunityCode)
    {
        try
        {
            DaoUtil util=new DaoUtil();
            List hheList=util.getHouseholdEnrollmentDaoInstance().getListOfHouseholdEnrollmentByCommunityCode(uncleanedCommunityCode);
            if(hheList !=null && !hheList.isEmpty())
            {
                for(Object obj:hheList)
                {
                    HouseholdEnrollment hhe=(HouseholdEnrollment)obj;
                    hhe.setCommunityCode(cleanedCommunityCode);
                    util.getHouseholdEnrollmentDaoInstance().updateHouseholdEnrollment(hhe);
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public String setCaregiverIdInHouseholdService()
    {
        String msg=" ";
        AppUtility appUtil=new AppUtility();
        HouseholdServiceDao hhsdao=new HouseholdServiceDaoImpl();
        int count=0;
        try
        {
            List hhsList=hhsdao.getListOfHouseholdServiceRecordsWithoutCaregiverId();
            List cgiverList=null;
            if(hhsList !=null && !hhsList.isEmpty())
            {
                CaregiverDao cgiverdao=new CaregiverDaoImpl();
                //HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
                String hhUniqueId=null;
                for(Object obj:hhsList)
                {
                    HouseholdService hhs=(HouseholdService)obj;
                    hhUniqueId=hhs.getHhUniqueId();
                    cgiverList=cgiverdao.getListOfCaregiversFromSameHousehold(hhUniqueId);
                    if(cgiverList !=null && !cgiverList.isEmpty())
                    {
                        Caregiver cgiver=(Caregiver)cgiverList.get(0);
                        hhs.setCaregiverId(cgiver.getCaregiverId());
                        hhs.setServiceRecipientType(appUtil.getHhsRecipientType("c"));
                        hhsdao.updateHouseholdService(hhs);
                        count++;
                    }
                    else
                    {
                        hhs.setCaregiverId(hhUniqueId);
                        hhs.setServiceRecipientType(appUtil.getHhsRecipientType("h"));
                        hhsdao.updateHouseholdService(hhs);
                        count++;
                    }
                }
                msg=count+" Household service records updated with caregiver Id out of "+hhsList.size()+" identified";
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return msg;
    }
    public void cleanFollowupRecords()
    {
        try
        {
            DaoUtil util=new DaoUtil();
            List list=util.getFollowupDaoInstance().getDistinctOvcIdAndDateOfFollowup();
            if(list !=null && !list.isEmpty())
            {
                String ovcId=null;
                String dateOfFollowup=null;
                List followupList=null;
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    ovcId=(String)objArray[0];
                    dateOfFollowup=(String)objArray[1];
                    followupList=util.getFollowupDaoInstance().getFollowupRecords(ovcId, dateOfFollowup);
                    if(followupList !=null && followupList.size()>1)
                    {
                        for(int i=0; i<followupList.size(); i++)
                        {
                            FollowupSurvey fu=(FollowupSurvey)followupList.get(i);
                            System.err.println("fu.getOvcId(): "+fu.getOvcId()+" fu.getDateOfSurvey(): "+fu.getDateOfSurvey()+", i= "+i);
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
    public int updateOvcWithLastServiceDate(boolean forceUpdate)
    {
        int count=0;
        DaoUtil util=new DaoUtil();
        try
        {
            List communityList=util.getHouseholdEnrollmentDaoInstance().getDistinctCommunityCodes();
            if(communityList !=null)
            {
                OvcServiceUtilityManager osm=new OvcServiceUtilityManager();
                String communityCode=null;
                List ovcList=null;
                for(int i=0; i<communityList.size(); i++)
                {
                   communityCode=(String)communityList.get(i);
                   if(forceUpdate)
                   ovcList=util.getOvcDaoInstance().getListOfOvcByCommunity(communityCode);
                   else
                   ovcList=util.getOvcDaoInstance().getListOfOvcWithoutLastServiceDateByCommunity(communityCode);
                   if(ovcList !=null)
                   {
                       for(int j=0; j<ovcList.size(); j++)
                       {
                           Ovc ovc=(Ovc)ovcList.get(j);
                           osm.updateOvcWithLastServiceDate(ovc);
                           osm.withdrawInactiveClient(ovc);
                           System.err.println("Ovc with id "+ovc.getOvcId()+" at "+count+" updated");
                           count++;
                       }
                   }
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return count;
    }
    public void removeDuplicatesFromCSI()
    {
        AppUtility appUtil=new AppUtility();
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        OvcDao ovcdao=new OvcDaoImpl();
        try
        {
            int count=0;
            List communityList=hhedao.getDistinctCommunityCodes();
            if(communityList !=null)
            {
                ChildStatusIndex csi=null;
                ChildStatusIndex duplicateCsi=null;
                ChildStatusIndexDao csidao=new ChildStatusIndexDaoImpl();
                List list=null;
                List csiList=null;
                //System.err.println("communityList size is "+communityList.size());
                String communityCode=null;
                for(int i=0; i<communityList.size(); i++)
                {
                   communityCode=(String)communityList.get(i);
                   csiList=csidao.getCSIByCommunityCode(communityCode);
                   //System.err.println("hhvaList size is "+hhvaList.size());
                   if(csiList !=null)
                   {
                       for(int j=0; j<csiList.size(); j++)
                       {
                           Object[] objArray=(Object[])csiList.get(j);
                           csi=(ChildStatusIndex)objArray[2];
                           if(!appUtil.isString(csi.getOvcId()) || csi.getCsiDate().indexOf("-")==-1)
                           {
                               csidao.deleteChildStatusIndex(csi);
                               continue;
                           }
                           list=csidao.getChildStatusIndexListByOvcIdAndDate(csi.getOvcId(), csi.getCsiDate());
                           //System.err.println("list size is "+list.size());
                           if(list !=null && !list.isEmpty())
                           {
                               if(list.size() > 1)
                               {
                                   for(int k=1; k<list.size(); k++)
                                   {
                                       duplicateCsi=(ChildStatusIndex)list.get(k);
                                       csidao.deleteChildStatusIndexWithoutReordering(duplicateCsi);
                                       count++;
                                       System.err.println("duplicateCsi with record number "+count+" with id "+duplicateCsi.getOvcId()+" and csi date "+duplicateCsi.getCsiDate()+" deleted");
                                   }
                                   csidao.reorderAssessmentNumber(duplicateCsi.getOvcId());
                               }
                               else
                               {
                                   /*check if this is the baseline assessment*/
                                   Ovc ovc=ovcdao.getOvc(csi.getOvcId());
                                   if(ovc !=null)
                                   {
                                       /*If the dates of assessment are the same, then this record was created at baseline. Just proceed to process the next record*/
                                       if(ovc.getDateEnrollment().trim().equalsIgnoreCase(csi.getCsiDate().trim()))
                                       continue;
                                       else
                                       {
                                           ChildStatusIndex baselinecsi=csidao.getChildStatusIndex(ovc.getOvcId(), ovc.getDateEnrollment());
                                           /*check if this record was created during followup assessment*/
                                           FollowupDao fudao=new FollowupDaoImpl();
                                           if(fudao.getFollowup(csi.getOvcId(), csi.getCsiDate()) !=null)
                                           {
                                               /*This record was created during followup assessment.*/
                                               
                                               /*If baseline assessment record does not exist, create a new one.*/
                                               if(baselinecsi==null)
                                               {
                                                   /*create an empty baseline assessment record and save*/
                                                   baselinecsi=new ChildStatusIndex();
                                                   baselinecsi.setOvcId(ovc.getOvcId());
                                                   baselinecsi.setDateOfEntry(appUtil.getCurrentDate());
                                                   baselinecsi.setCsiDate(ovc.getDateEnrollment());
                                                   baselinecsi.setSurveyNumber(1);
                                                   csidao.saveChildStatusIndex(baselinecsi);
                                               }
                                               continue;
                                           }
                                           /*if baseline assessment record exist, check if the assessment was done i.e score must be greater than 6*/
                                           
                                           if(baselinecsi !=null)
                                           {
                                               if(csidao.getTotalCsiScore(baselinecsi)==0)
                                               {
                                                   /*If baseline score is =0 and this record has a score of above 0, then make it the baseline record*/
                                                   if(csidao.getTotalCsiScore(csi) >0)
                                                   {
                                                       csi.setCsiDate(baselinecsi.getCsiDate());
                                                       csi.setSurveyNumber(1);
                                                       csidao.updateChildStatusIndex(csi);
                                                       csidao.deleteChildStatusIndex(baselinecsi);
                                                       csidao.reorderAssessmentNumber(csi.getOvcId());
                                                   }
                                                   else
                                                   {
                                                       csidao.deleteChildStatusIndex(csi);
                                                       if(duplicateCsi !=null)
                                                       csidao.reorderAssessmentNumber(duplicateCsi.getOvcId());
                                                   }
                                               }
                                               else
                                               {
                                                    csidao.deleteChildStatusIndex(csi);
                                                    System.err.println("Record number "+(++count)+" deleted");
                                               }
                                           }
                                       }
                                   }
                                   
                               }
                               //csidao.saveChildStatusIndex((ChildStatusIndex)list.get(0));
                               System.err.println("Record number "+count+" deleted");
                           }
                       }
                   }
                }
            }
            updateCsiDateOfEntry();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void assignCaregiverToOvc()
    {
        OvcDao dao=new OvcDaoImpl();
        CaregiverDao cgiverdao=new CaregiverDaoImpl();
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        AppUtility appUtil=new AppUtility();
        try
        {
            List list=dao.getOvcWithNoCaregiverId();
            if(list !=null && !list.isEmpty())
            {
                Ovc ovc=null;
                Caregiver cgiver=null;
                HouseholdEnrollment hhe=null;
                String hhUniqueId=null;
                for(int i=0; i<list.size(); i++)
                {
                    ovc=(Ovc)list.get(i);
                    hhUniqueId=ovc.getHhUniqueId();
                    List cgiverList=cgiverdao.getListOfCaregiversFromSameHousehold(hhUniqueId);
                    //if there are caregivers from this household, assign the first one to the children
                    if(cgiverList !=null && !cgiverList.isEmpty())
                    {
                        cgiver=(Caregiver)cgiverList.get(0);
                        ovc.setCaregiverId(cgiver.getCaregiverId());
                        dao.updateOvc(ovc,false,false);
                    }
                    else
                    {
                        //No caregivers, use the household head instead
                        hhe=hhedao.getHouseholdEnrollment(hhUniqueId);
                        if(hhe !=null)
                        {
                            cgiver=new Caregiver();
                            cgiver.setHhUniqueId(hhUniqueId);
                            cgiver.setCaregiverId(hhUniqueId);
                            cgiver.setCaregiverAge(hhe.getHhAge());
                            cgiver.setCaregiverFirstname(hhe.getHhFirstname());
                            cgiver.setCaregiverLastName(hhe.getHhSurname());
                            cgiver.setCaregiverGender(hhe.getHhGender());
                            cgiver.setCaregiverMaritalStatus(hhe.getMaritalStatus());
                            cgiver.setCaregiverPhone(hhe.getPhone());
                            cgiver.setDateModified(appUtil.getCurrentDate());
                            cgiver.setCgiverHivStatus("unknown");
                            cgiver.setHouseholdhead("1");
                            cgiver.setCaregiverOccupation(hhe.getOccupation());
                            cgiverdao.saveCaregiver(cgiver);
                            ovc.setCaregiverId(hhUniqueId);
                            dao.updateOvc(ovc,false,false);
                            System.err.println("Caregiver with "+ovc.getCaregiverId()+" saved");
                        }
                        
                    }
                    System.err.println("ovc with "+ovc.getOvcId()+" updated with caregiver id "+ovc.getCaregiverId());
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public int removeUnassignedCaregivers() throws Exception
    {
        assignCaregiverToOvc();
        int numberOfCaregiversCleaned=0;
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        CaregiverDao cgiverdao=new CaregiverDaoImpl();
        OvcDao dao=new OvcDaoImpl();
        List communityList=hhedao.getDistinctCommunityCodes();
        if(communityList !=null)
        {
            List caregiverList=null;
            String communityCode=null;
            Caregiver cgiver=null;
            Ovc ovc=null;
            List ovcList=null;
            for(int i=0; i<communityList.size(); i++)
            {
                if(communityList.get(i) !=null)
                communityCode=communityList.get(i).toString();
                else
                communityCode=(String)communityList.get(i);    
                caregiverList=cgiverdao.getCaregiversFromSameCommunity(communityCode);
                if(caregiverList==null)
                continue;
                for(int j=0; j<caregiverList.size(); j++)
                {
                    cgiver=(Caregiver)caregiverList.get(j);
                    /*check if this caregiver is associated with an OVC record*/
                    System.err.println("cgiver is "+cgiver.getCaregiverFirstname()+" id: "+cgiver.getCaregiverId());
                    ovcList=dao.getListOfOvcsPerCaregiver("", cgiver.getCaregiverId());
                    if(ovcList ==null || ovcList.isEmpty())
                    {
                        /*check if the household head is associated with an OVC record, if so, change it to this caregiver*/
                        ovcList=dao.getListOfOvcsPerCaregiver("", cgiver.getHhUniqueId());
                        if(ovcList !=null && !ovcList.isEmpty())
                        {
                            for(int k=0; k<ovcList.size(); k++)
                            {
                                ovc=(Ovc)ovcList.get(k); 
                                ovc.setCaregiverId(cgiver.getCaregiverId());
                                dao.updateOvc(ovc,false,false);
                            }
                            cgiver=cgiverdao.getCaregiverByCaregiverId(cgiver.getHhUniqueId());
                            if(cgiver !=null)
                            cgiverdao.deleteCaregiver(cgiver);
                        }
                        else
                        cgiverdao.deleteCaregiver(cgiver);
                        numberOfCaregiversCleaned++;
                    }
                }
            }
        } 
        return numberOfCaregiversCleaned;
    }
    public void updateCsiDateOfEntry()
    {
        ChildStatusIndexDaoImpl csidao=new ChildStatusIndexDaoImpl();
        try
        {
            csidao.updateCsiDateOfEntry();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public int repositionHouseholdEnrollmentAndFollowupAssessments()
    {
        int success=0;
        fillBaselineAssessmentInhouseholdEnrollment();
        fillAssessmentScoresForFollowupRecords();
        //correctHouseholdEnrollmentDate();
        return success;
    }
    public void removeDuplicatesFromHHVA()
    {
        AppUtility appUtil=new AppUtility();
        HouseholdEnrollment hhe=null;
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        try
        {
            //correctHouseholdEnrollmentDate();
            int count=0;
            List communityList=hhedao.getDistinctCommunityCodes();
            
            if(communityList !=null)
            {
                HouseholdVulnerabilityAssessment hhva=null;
                HouseholdVulnerabilityAssessment baselinehhva=null;
                HouseholdFollowupAssessment hhfa=null;
                HouseholdVulnerabilityAssessmentDao hhvadao=new HouseholdVulnerabilityAssessmentDaoImpl();
                HouseholdFollowupAssessmentDao hhfadao=new HouseholdFollowupAssessmentDaoImpl();
                List list=null;
                List hhvaList=null;
                List hheIdList=null;
                String communityCode=null;
                String hhUniqueId=null;
                int hhfaNumber=1;
                int highesthhvaScore=0;
                //List hhvaTobeDeletedList=new ArrayList();
                for(int i=0; i<communityList.size(); i++)
                {
                   communityCode=(String)communityList.get(i);
                   hheIdList=hhedao.getDistinctHouseholdUniqueIdByCommunityCode(communityCode);
                   //hhvaList=hhvadao.getHouseholdAssessmentsByCommunityCode(communityCode);
                   if(hheIdList !=null)
                   {
                       for(int j=0; j<hheIdList.size(); j++)
                       {
                           baselinehhva=null;
                           hhUniqueId=(String)hheIdList.get(j);
                           
                           hhe=hhedao.getHouseholdEnrollment(hhUniqueId);
                           if(hhe !=null)
                           hhvadao.createNewRecord(hhe);
                           hhvaList=hhvadao.getAllAssessmentsPerHouseholdOrderedByDateOfAssessmentAsc(hhUniqueId);
                           if(hhvaList !=null && !hhvaList.isEmpty())
                           {
                               if(hhe==null)
                               {
                                   //This may be old records, whose enrollment record does not exist. This should not happen, but if it does, handle it
                                    hhvadao.deleteAllAssessmentPerHousehold(hhUniqueId);
                                    continue;
                               }
                               for(int k=0; k<hhvaList.size(); k++)
                               {
                                   hhva=(HouseholdVulnerabilityAssessment)hhvaList.get(k);
                                   if(!appUtil.isString(hhva.getHhUniqueId()) || hhva.getDateOfAssessment().indexOf("-")==-1)
                                   {
                                       hhvadao.deleteHouseholdVulnerabilityAssessment(hhva);
                                       continue;
                                   }
                                   //look for baseline assessment
                                   if(hhe.getDateOfAssessment().trim().equalsIgnoreCase(hhva.getDateOfAssessment().trim()))
                                   {
                                       //check for multiple baseline record
                                       List baselineAssessmentList=hhvadao.getHouseholdAssessmentsByUniqueIdAndDate(hhUniqueId, hhva.getDateOfAssessment());
                                       if(baselineAssessmentList !=null && baselineAssessmentList.size()>1)
                                       {
                                           //Track the one with highest hhva score
                                           highesthhvaScore=0;
                                           for(int l=0; l<baselineAssessmentList.size(); l++)
                                           {
                                             baselinehhva=(HouseholdVulnerabilityAssessment)baselineAssessmentList.get(l);
                                             int score=hhvadao.getHouseholdVulnerabilityScore(baselinehhva);
                                             if(score>highesthhvaScore)
                                             highesthhvaScore=score; 
                                           }
                                           //check for assessment with lower scores and remove them
                                           if(highesthhvaScore>0)
                                           {
                                               for(int m=0; m<baselineAssessmentList.size(); m++)
                                               {
                                                 baselinehhva=(HouseholdVulnerabilityAssessment)baselineAssessmentList.get(m);
                                                 int score=hhvadao.getHouseholdVulnerabilityScore(baselinehhva);
                                                 if(score<highesthhvaScore)
                                                 highesthhvaScore=score;
                                                 hhvadao.deleteHouseholdVulnerabilityAssessment(baselinehhva);
                                               }
                                           }
                                           else
                                           {
                                               //They have zero values. delete all except 1
                                               for(int m=0; m<baselineAssessmentList.size()-1; m++)
                                               {
                                                 baselinehhva=(HouseholdVulnerabilityAssessment)baselineAssessmentList.get(m);
                                                 hhvadao.deleteHouseholdVulnerabilityAssessment(baselinehhva);
                                               }
                                           }
                                           
                                       }
                                       baselinehhva=hhva;
                                       baselinehhva.setAssessmentNo(1);
                                       hhvadao.updateHouseholdVulnerabilityAssessmentByuniqueIdAndDate(baselinehhva);
                                   }
                                   else
                                   {
                                       //check for baseline assessment
                                       baselinehhva=hhvadao.getHouseholdVulnerabilityAssessment(hhUniqueId, hhe.getDateOfAssessment());
                                       if(baselinehhva==null)
                                       {
                                           //create an empty baseline assessment record and save
                                           baselinehhva=new HouseholdVulnerabilityAssessment();
                                           baselinehhva.setAssessmentNo(1);
                                           baselinehhva.setHhUniqueId(hhe.getHhUniqueId());
                                           baselinehhva.setNameOfAssessor(hhe.getRecordedBy());
                                           baselinehhva.setDesignation("DEC");
                                           baselinehhva.setRecordedBy(hhe.getRecordedBy());
                                           baselinehhva.setDateOfEntry(appUtil.getCurrentDate());
                                           baselinehhva.setDateOfAssessment(hhe.getDateOfAssessment());
                                           hhvadao.saveHouseholdVulnerabilityAssessment(baselinehhva);
                                       }
                                       //check for a corressponding follow-up record
                                       hhfa=hhfadao.getHouseholdFollowupAssessmentByIdAndDate(hhUniqueId, hhva.getDateOfAssessment());
                                       if(hhfa !=null)
                                       {
                                           hhva.setAssessmentNo(++hhfaNumber);
                                           hhvadao.updateHouseholdVulnerabilityAssessmentByuniqueIdAndDate(hhva);
                                       }
                                       else if(baselinehhva.getVulnerabilityScore() < hhva.getVulnerabilityScore())
                                       {
                                           hhva.setAssessmentNo(1);
                                           hhva.setDateOfAssessment(baselinehhva.getDateOfAssessment());
                                           hhva.setId(baselinehhva.getId());
                                           hhvadao.deleteHouseholdVulnerabilityAssessment(baselinehhva);
                                           baselinehhva=hhva;
                                           hhvadao.updateHouseholdVulnerabilityAssessmentByuniqueIdAndDate(baselinehhva);
                                       }
                                       else
                                       {
                                           hhvadao.deleteHouseholdVulnerabilityAssessment(hhva);
                                       }
                                   } 
                                   count++;
                                   System.err.println("hhva record number "+count+" updated");
                               }
                               hhvadao.reorderAssessmentNumber(hhUniqueId);
                           } 
                       }
                       
                   }
                }
                //int success=repositionHouseholdEnrollmentAndFollowupAssessments();
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public int fillAssessmentScoresForFollowupRecords()
    {
        int success=0;
        try
        {
            DaoUtil util=new DaoUtil();
            
            List communityList=util.getHouseholdEnrollmentDaoInstance().getDistinctCommunityCodes();
            if(communityList !=null)
            {
                int count=0;
                List list=null;
                for(Object obj:communityList)
                {
                    String communityCode=(String)obj;
                    list=util.getHouseholdFollowupAssessmentDaoInstance().getFollowupAssessmentsWithoutScoresByCommunityCode(communityCode);//.getListOfHouseholdEnrollmentByCommunityCode(communityCode);
                    if(list !=null)
                    {
                        HouseholdEnrollment hhe=null;
                        HouseholdVulnerabilityAssessment hva=null;
                        HouseholdFollowupAssessment hhfa=null;
                        HouseholdFollowupAssessment hhfa2=null;
                        List hvaList=null;
                        String hhUniqueId=null;
                        boolean saved=false;
                        for(Object hhfaObj:list)
                        {
                            saved=false;
                            hhfa=(HouseholdFollowupAssessment)hhfaObj;
                            hhUniqueId=hhfa.getHhUniqueId();
                            hvaList=util.getHouseholdVulnerabilityAssessmentDaoInstance().getHouseholdAssessmentsByUniqueIdAndDateAndScore(hhUniqueId, hhfa.getDateOfAssessment(),NomisConstant.VULNERABLE_STARTVALUE);
                            if(hvaList !=null && !hvaList.isEmpty())
                            { 
                                for(int i=0; i<hvaList.size(); i++)
                                {
                                    hva=(HouseholdVulnerabilityAssessment)hvaList.get(0);
                                    hhe=util.getHouseholdEnrollmentDaoInstance().getHouseholdEnrollmentByUniqueIdAndHnvaId(hhUniqueId, hva.getId());
                                    hhfa2=util.getHouseholdFollowupAssessmentDaoInstance().getHouseholdFollowupAssessmentByUniqueIdAndHnvaId(hhUniqueId, hva.getId());
                                    if(hhe ==null && hhfa2 ==null)
                                    {
                                        hhfa=getHouseholdFollowupWithAssessmentDetails(hhfa,hva);
                                        util.getHouseholdFollowupAssessmentDaoInstance().updateHouseholdFollowupAssessment(hhfa);
                                        saved=true;
                                        count++;
                                        System.err.println("hhfa.getHhUniqueId() "+hhfa.getHhUniqueId()+" updated "+count);
                                    }
                                }
                            }
                            else if(!saved)
                            {
                                hvaList=util.getHouseholdVulnerabilityAssessmentDaoInstance().getAllAssessmentsPerHouseholdOrderedByDateOfAssessmentAsc(hhUniqueId,NomisConstant.VULNERABLE_STARTVALUE);
                                if(hvaList !=null && !hvaList.isEmpty())
                                {
                                    for(int i=0; i<hvaList.size(); i++)
                                    {
                                        hva=(HouseholdVulnerabilityAssessment)hvaList.get(0);
                                        hhe=util.getHouseholdEnrollmentDaoInstance().getHouseholdEnrollmentByUniqueIdAndHnvaId(hhUniqueId, hva.getId());
                                        hhfa2=util.getHouseholdFollowupAssessmentDaoInstance().getHouseholdFollowupAssessmentByUniqueIdAndHnvaId(hhUniqueId, hva.getId());
                                        if(hhe ==null && hhfa2 ==null)
                                        {
                                            hhfa=getHouseholdFollowupWithAssessmentDetails(hhfa,hva);
                                            util.getHouseholdFollowupAssessmentDaoInstance().updateHouseholdFollowupAssessment(hhfa);
                                            saved=true;
                                            count++;
                                            System.err.println("hhfa.getHhUniqueId() "+hhfa.getHhUniqueId()+" updated "+count);
                                        }
                                    }
                                }
                            }
                            
                        }
                    }
                }
            }
            success=1;
        }
        catch(Exception ex)
        {
            success=0;
            ex.printStackTrace();
        }
        return success;
    }
    public int fillBaselineAssessmentInhouseholdEnrollment()
    {
        int success=0;
        try
        {
            DaoUtil util=new DaoUtil();
            
            List communityList=util.getHouseholdEnrollmentDaoInstance().getDistinctCommunityCodes();
            if(communityList !=null)
            {
                int count=0;
                List list=null;
                for(Object obj:communityList)
                {
                    String communityCode=(String)obj;
                    list=util.getHouseholdEnrollmentDaoInstance().getListOfHouseholdEnrollmentWithIncompleteVulnerabilityScoreByCommunityCode(communityCode,7);
                    if(list !=null)
                    {
                        HouseholdEnrollment hhe=null;
                        HouseholdVulnerabilityAssessment hva=null;
                        List hvaList=null;
                        String hhUniqueId=null;
                        for(Object hheObj:list)
                        {
                            hhe=(HouseholdEnrollment)hheObj;
                            hhUniqueId=hhe.getHhUniqueId();
                            hvaList=util.getHouseholdVulnerabilityAssessmentDaoInstance().getHouseholdAssessmentsByUniqueIdAndDateAndScore(hhUniqueId, hhe.getDateOfAssessment(),7);
                            if(hvaList !=null && !hvaList.isEmpty())
                            { 
                                hva=(HouseholdVulnerabilityAssessment)hvaList.get(0);
                                hhe=getHouseholdEnrollmentWithAssessmentDetails(hhe,hva);
                                hhe.setHhvaId(hva.getId());
                                util.getHouseholdEnrollmentDaoInstance().updateHouseholdEnrollment(hhe);
                                count++;
                                System.err.println("hhe.getHhUniqueId() "+hhe.getHhUniqueId()+" updated "+count);
                            }
                            else
                            {
                                hvaList=util.getHouseholdVulnerabilityAssessmentDaoInstance().getAllAssessmentsPerHouseholdOrderedByDateOfAssessmentAsc(hhUniqueId,7);
                                if(hvaList !=null && !hvaList.isEmpty())
                                {
                                    hva=(HouseholdVulnerabilityAssessment)hvaList.get(0); 
                                    hhe=getHouseholdEnrollmentWithAssessmentDetails(hhe,hva);
                                    hhe.setHhvaId(hva.getId());
                                    //util.getHouseholdEnrollmentDaoInstance().updateHouseholdEnrollment(hhe);
                                    count++;
                                    
                                }
                                util.getHouseholdEnrollmentDaoInstance().updateHouseholdEnrollment(hhe);
                                System.err.println("hhe.getHhUniqueId() "+hhe.getHhUniqueId()+" updated "+count);
                            }
                            
                        }
                    }
                }
            }
            success=1;
        }
        catch(Exception ex)
        {
            success=0;
            ex.printStackTrace();
        }
        return success;
    }
    public HouseholdFollowupAssessment getHouseholdFollowupWithAssessmentDetails(HouseholdFollowupAssessment hhfa,HouseholdVulnerabilityAssessment hva)
    {
        try
        {
            if(hhfa !=null && hva !=null)
            {
                DaoUtil util=new DaoUtil();
                hhfa.setEducationLevel(hva.getEducationLevel());
                hhfa.setFoodSecurityAndNutrition(hva.getFoodSecurityAndNutrition());
                hhfa.setHealth(hva.getHealth());
                hhfa.setHhHeadship(hva.getHhHeadship());
                hhfa.setHhIncome(hva.getHhIncome());
                hhfa.setShelterAndHousing(hva.getShelterAndHousing());
                hhfa.setMeansOfLivelihood(hva.getMeansOfLivelihood());
                hhfa.setHhvaId(hva.getId());
                int vulnerabilityScore=util.getHouseholdFollowupAssessmentDaoInstance().getFollowupVulnerabilityScore(hhfa);
//.getHouseholdVulnerabilityAssessmentDaoInstance().getHouseholdVulnerabilityScore(hva);
                hhfa.setVulnerabilityScore(vulnerabilityScore);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return hhfa;
    }
    public HouseholdEnrollment getHouseholdEnrollmentWithAssessmentDetails(HouseholdEnrollment hhe,HouseholdVulnerabilityAssessment hva)
    {
        try
        {
            if(hhe !=null && hva !=null)
            {
                DaoUtil util=new DaoUtil();
                hhe.setEducationLevel(hva.getEducationLevel());
                hhe.setFoodSecurityAndNutrition(hva.getFoodSecurityAndNutrition());
                hhe.setHealth(hva.getHealth());
                hhe.setHhHeadship(hva.getHhHeadship());
                hhe.setHhIncome(hva.getHhIncome());
                hhe.setShelterAndHousing(hva.getShelterAndHousing());
                hhe.setMeansOfLivelihood(hva.getMeansOfLivelihood());
                hhe.setHhvaId(hva.getId());
                int baselineAssessmentScore=util.getHouseholdEnrollmentDaoInstance().getHouseholdVulnerabilityScore(hhe);//util.getHouseholdVulnerabilityAssessmentDaoInstance().getHouseholdVulnerabilityScore(hva);
                hhe.setBaselineAssessmentScore(baselineAssessmentScore);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return hhe;
    }
    public int correctHouseholdEnrollmentDate()
    {
        int success=0;
        try
        {
            DaoUtil util=new DaoUtil();
            List list=util.getHouseholdEnrollmentDaoInstance().getHouseholdsWithIncorrectDateOfAssessment();
            if(list !=null && !list.isEmpty())
            {
                int count=0;
                List hhIdList=new ArrayList();
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    HouseholdEnrollment hhe=(HouseholdEnrollment)objArray[0];
                    Ovc ovc=(Ovc)objArray[1];
                    
                    if(hhIdList.contains(hhe.getHhUniqueId()))
                    continue;
                    hhIdList.add(hhe.getHhUniqueId());
                    //System.err.println(hhe.getHhUniqueId()+": "+hhe.getDateOfAssessment()+" and "+ovc.getOvcId()+": "+ovc.getDateEnrollment()+" "+(++count));
                    correctHouseholdVulnerabilityAssessmentDate(hhe,ovc.getDateEnrollment());
                }
            }
            success=1;
        }
        catch(Exception ex)
        {
            success=0;
            ex.printStackTrace();
        }
        return success;
    }
    public void correctHouseholdVulnerabilityAssessmentDate(HouseholdEnrollment hhe,String correctDate)
    {
       try
       {
            DaoUtil util=new DaoUtil();
            hhe.setDateOfAssessment(correctDate);
            util.getHouseholdEnrollmentDaoInstance().updateHouseholdEnrollment(hhe);
            String hhUniqueId=hhe.getHhUniqueId();
            List list=util.getHouseholdVulnerabilityAssessmentDaoInstance().getHouseholdAssessmentsByUniqueIdAndDate(hhUniqueId, hhe.getDateOfAssessment());
            HouseholdVulnerabilityAssessment hva=null;
            if(list !=null && !list.isEmpty())
            { 
                hva=(HouseholdVulnerabilityAssessment)list.get(0);
            }
            else
            {
                List hvaList=util.getHouseholdVulnerabilityAssessmentDaoInstance().getAllAssessmentsPerHouseholdOrderedByDateOfAssessmentAsc(hhUniqueId);
                if(hvaList !=null && !hvaList.isEmpty())
                {
                    hva=(HouseholdVulnerabilityAssessment)hvaList.get(0);
                    if(hvaList.size()>0)
                    {
                        for(int i=1; i<hvaList.size(); i++)
                        {
                            hva=(HouseholdVulnerabilityAssessment)hvaList.get(i);
                            hva.setAssessmentNo(0);
                            util.getHouseholdVulnerabilityAssessmentDaoInstance().updateHouseholdVulnerabilityAssessmentById(hva);
                            System.err.println(hva.getHhUniqueId()+": "+hva.getDateOfAssessment()+" hva.getAssessmentNo(): "+hva.getAssessmentNo());
                        }
                    }
                }
                
            }
            if(hva==null)
            {
                //Create a new one for the household record without assessment score
                hva=new HouseholdVulnerabilityAssessment();
                hva.setHhUniqueId(hhUniqueId);
                hva.setDateOfAssessment(correctDate);
                hva.setAssessmentNo(1);
                hva.setDateOfEntry(DateManager.getCurrentDate());
                hva.setDesignation("auto");
                System.err.println(hva.getHhUniqueId()+": "+hva.getDateOfAssessment()+" "+hva.getDesignation());
                util.getHouseholdVulnerabilityAssessmentDaoInstance().saveHouseholdVulnerabilityAssessment(hva);
            }
            else
            {
                hva.setDateOfAssessment(correctDate);
                hva.setAssessmentNo(1);
                util.getHouseholdVulnerabilityAssessmentDaoInstance().updateHouseholdVulnerabilityAssessmentById(hva);
            }
            
       }
       catch(Exception ex)
       {
           ex.printStackTrace();
       }
    }
    public void reorderHouseholdAssessmentNumber() throws Exception
    {
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        HouseholdVulnerabilityAssessmentDao hhvadao=new HouseholdVulnerabilityAssessmentDaoImpl();
        try
        {
            List communityList=hhedao.getDistinctCommunityCodes();
            int count=0;
            if(communityList !=null)
            {
                List hhUniqueIdList=null;
                String communityCode=null;
                for(int i=0; i<communityList.size(); i++)
                {
                   communityCode=(String)communityList.get(i);
                    hhUniqueIdList=hhvadao.getDistinctHhUniqueId(communityCode);
                    if(hhUniqueIdList !=null)
                    {
                        String hhUniqueId=null;
                        for(int k=0; k<hhUniqueIdList.size(); k++)
                        {
                            hhUniqueId=(String)hhUniqueIdList.get(k);
                            hhvadao.reorderAssessmentNumber(hhUniqueId);
                            count++;
                            System.err.println("Household number "+count+" with hhUniqueId "+hhUniqueId+" reordered");
                        }
                    }
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            throw new Exception();
            
        }
    }
    public void setAppropriateWithdrawalType()
    {
        OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl();
        try
        {
            DaoUtil util=new DaoUtil();
            List list=wdao.getListOfWithrawalWithWrongType();
            int count=wdao.resetGraduationValue();
            int ageAbove18Count=wdao.resetAgeAbove18Value();
            int correctedAgeAbove18Count=wdao.resetAgeAbove18ToGraduation();
            System.err.println(count+" graduation values was reset");
            System.err.println(ageAbove18Count+" age Above 18 values was reset");
            System.err.println(correctedAgeAbove18Count+" withdrawal values corrected to graduation");
            if(list !=null && !list.isEmpty())
            {
                OvcWithdrawal withdrawal=null;
                //String uniqueId=null;
                for(Object obj: list)
                {
                    withdrawal=(OvcWithdrawal)obj;
                    withdrawal=util.getWithdrawal(withdrawal);
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void reorderCSINumber() throws Exception
    {
        ChildStatusIndexDao csidao=new ChildStatusIndexDaoImpl();
            HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
            try
            {
                int count=0;
                String communityCode=null;
                List communityCodeList=hhedao.getDistinctCommunityCodes();
                if(communityCodeList !=null && !communityCodeList.isEmpty())
                {
                    List list=null;
                    for(int i=0; i<communityCodeList.size(); i++)
                    {
                        communityCode=(String)communityCodeList.get(i);
                        list=csidao.getDistinctOvcIdsFromCsiByCommunityCode(communityCode);
                        if(list !=null)
                        {
                            count+=list.size();
                            for(int j=0; j<list.size(); j++)
                            {
                                csidao.reorderAssessmentNumber(list.get(j).toString());
                                //System.err.println();
                            }
                        }
                    }
                    msg=count+" records updated";
                    //request.setAttribute("msg", msg);
                }
            }
            catch(Exception ex)
            {
                msg=ex.getMessage();
            }
    }
    public void cleanupNutritionalAssessmentRecords() throws Exception
    {
        NutritionAssessmentDao nadao=new NutritionAssessmentDaoImpl();
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        
        try
        {
            //System.err.println("inside reorderHouseholdAssessmentNumber()");
            List communityList=hhedao.getDistinctCommunityCodes();
            int count=0;
            if(communityList !=null)
            {
                List objectList=null;
                String communityCode=null;
                for(int i=0; i<communityList.size(); i++)
                {
                   communityCode=(String)communityList.get(i);
                    objectList=nadao.getDistinctOvcIdAndDateOfAssessmentByCommunityCode(communityCode);
                    //System.err.println("hhUniqueIdList size is "+hhUniqueIdList.size());
                    if(objectList !=null)
                    {
                        for(int k=0; k<objectList.size(); k++)
                        {
                            Object[] objArray=(Object[])objectList.get(k);
                            List list=nadao.getNutritionAssessmentByDateOfAssessment((String)objArray[0],(String)objArray[1]);
                            //System.err.println("list size is "+list.size());
                            if(list !=null && list.size()>1)
                            {
                                for(int j=1; j<list.size(); j++)
                                {
                                    nadao.deleteNutritionAssessment((NutritionAssessment)list.get(j));
                                    count++;
                                    System.err.println("Nutrition assessment record "+count+" deleted");
                                }
                                //nadao.saveNutritionAssessment((NutritionAssessment)list.get(0));
                            }
                        }
                    }
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            throw new Exception();

        }
    }
    public void cleanupBMIInNutritionalAssessmentRecords() throws Exception
    {
        NutritionAssessmentDao nadao=new NutritionAssessmentDaoImpl();
        int totalNoOfRecords=nadao.cleanupBMI();
        if(totalNoOfRecords>0)
        {
            HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
            try
            {
                List communityList=hhedao.getDistinctCommunityCodes();
                if(communityList !=null)
                {
                    for(int i=0; i<communityList.size(); i++)
                    {
                        if(communityList.get(i) !=null)
                        nadao.setActiveAssessmentRecordPerCommunity(communityList.get(i).toString());
                        else
                        nadao.setActiveAssessmentRecordPerCommunity((String)communityList.get(i));
                    }
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }
    public void reorderNutritionalAssessmentRecords() throws Exception
    {
        NutritionAssessmentDao nadao=new NutritionAssessmentDaoImpl();
            HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
            NutritionAssessment na=null;

        try
        {
            //System.err.println("inside reorderHouseholdAssessmentNumber()");
            List communityList=hhedao.getDistinctCommunityCodes();
            int count=0;
            if(communityList !=null)
            {
                List ovcIdList=null;
                String communityCode=null;
                for(int i=0; i<communityList.size(); i++)
                {
                   communityCode=(String)communityList.get(i);
                    ovcIdList=nadao.getDistinctOvcIdByCommunityCode(communityCode);
                    //System.err.println("hhUniqueIdList size is "+hhUniqueIdList.size());
                    if(ovcIdList !=null)
                    {
                        int assessmentNo=0;
                        String ovcId=null;
                        for(int k=0; k<ovcIdList.size(); k++)
                        {
                            assessmentNo=0;
                            ovcId=(String)ovcIdList.get(k);
                            List list=nadao.getNutritionAssessmentsByOvcId(ovcId);
                            //System.err.println("list size is "+list.size());
                            if(list !=null)
                            {
                                for(int j=0; j<list.size(); j++)
                                {
                                    assessmentNo++;
                                    na=(NutritionAssessment)list.get(j);
                                    na.setAssessmentNo(assessmentNo);
                                    nadao.updateNutritionAssessment(na);
                                    count++;
                                    System.err.println("Nutrition assessment record "+count+" saved");
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
            throw new Exception();

        }
    }
    public String cleanUpDatabase()
    {
        String msg="Unable to cleanup records. database busy";
        if(!TaskManager.isDatabaseLocked())
        {
            //updateWithdrawalStatus already checks for database lock, so run before locking database
            NomisUpgrade nu=new NomisUpgrade();
            nu.updateWithdrawalStatus(true);
            //Block other critical operations while processing the tasks below
            TaskManager.setDatabaseLocked(true);
                setAppropriateWithdrawalType();
                DaoUtil util=new DaoUtil(); 
                util.updateHivStatusOfOvcNotAtRisk();
                //int success=repositionHouseholdEnrollmentAndFollowupAssessments();
            TaskManager.setDatabaseLocked(false);
            msg="Database records cleaned";
        }
        return msg;
    }
    public String cleanUpDatabase(String beneficiaryId)
    {
        String msg="Unable to cleanup records. database busy";
        if(!TaskManager.isDatabaseLocked())
        {
            //updateWithdrawalStatus already checks for database lock, so run before locking database
            NomisUpgrade nu=new NomisUpgrade();
            nu.updateWithdrawalStatus(true);
            //Block other critical operations while processing the tasks below
            TaskManager.setDatabaseLocked(true);
                setAppropriateWithdrawalType();
                DaoUtil util=new DaoUtil(); 
                util.updateHivStatusOfOvcNotAtRisk();
                //int success=repositionHouseholdEnrollmentAndFollowupAssessments();
            TaskManager.setDatabaseLocked(false);
            msg="Database records cleaned";
        }
        return msg;
    }
}
