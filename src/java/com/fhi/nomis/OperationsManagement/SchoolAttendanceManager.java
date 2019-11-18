/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.nomis.OperationsManagement;

import com.fhi.kidmap.business.CaregiverExpenditureAndSchoolAttendance;
import com.fhi.kidmap.business.ChildSchoolStatus;
import com.fhi.kidmap.business.FollowupSurvey;
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.business.OvcService;
import com.fhi.kidmap.business.SchoolAttendanceTracker;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.SchoolAttendanceTrackerDao;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.nomis.nomisutils.DateManager;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 *
 * @author smomoh
 */
public class SchoolAttendanceManager 
{
    
    public static int moveSchoolAttendanceTrackerRecordsFromCaregiverExpenditureRecord()
    {
        DaoUtil util=new DaoUtil();
        int count=0;
        try
        {
            List list=util.getCaregiverExpenditureAndSchoolAttendanceDaoInstance().getRecordsWithOvcId("");
            if(list !=null && !list.isEmpty())
            {
                CaregiverExpenditureAndSchoolAttendance ceasa=null;
                for(Object obj:list)
                {
                    ceasa=(CaregiverExpenditureAndSchoolAttendance)obj;
                    saveOrUpdateSchoolAttendanceTracker(ceasa);
                    count++;
                }
                if(count>=list.size()-1)
                {
                    String tagName="ceasaSaved";
                    int tagValue=1;
                    BackgroundProcessManager bpm=new BackgroundProcessManager();
                    if(!bpm.backgroundProcessExecuted(tagName,tagValue))
                    {
                        bpm.markBackgroundProcessAsExecuted(tagName,tagValue);
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
    public static int populateChildSchoolStatusRecords()
    {
        DaoUtil util=new DaoUtil();
        int count=0;
        try
        {
            List communityList=util.getHouseholdEnrollmentDaoInstance().getDistinctCommunityCodes();
            String communityCode=null;
            List ovcList=null;
            if(communityList !=null && !communityList.isEmpty())
            {
                for(Object obj:communityList)
                {
                    if(obj !=null)
                    communityCode=obj.toString();
                    else
                    communityCode=(String)obj;
                    ovcList=util.getOvcDaoInstance().getListOfOvcByCommunity(communityCode);
                    if(ovcList !=null && !ovcList.isEmpty())
                    {
                        for(Object ovcObj:ovcList)
                        {
                            Ovc ovc=(Ovc)ovcObj;
                            saveOrUpdateChildSchoolStatus(ovc);
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
    public int childInSchool(String ovcId)
    {
        int childInSchool=0;
        try
        {
            DaoUtil util=new DaoUtil();
            ChildSchoolStatus csc=util.getChildSchoolStatusDaoInstance().getChildSchoolStatus(ovcId);
            if(csc!=null)
            {
                if(csc.getOvcInSchool()==null || csc.getOvcInSchool().equalsIgnoreCase("No"))
                childInSchool=1;
                else if(csc.getOvcInSchool().equalsIgnoreCase("Yes"))
                childInSchool=2;
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return childInSchool; 
    }
    public static void saveOrUpdateChildSchoolStatus(Ovc ovc)
    {
        try
        {
            if(ovc !=null)
            {
                AppUtility appUtil=new AppUtility();
                DaoUtil util=new DaoUtil();
                ChildSchoolStatus osa=new ChildSchoolStatus();
                FollowupSurvey fs=null;
                osa.setCurrentClass(ovc.getCurrentClass());
                osa.setCurrentSchoolName(ovc.getSchoolName());
                osa.setDateOfAssessment(DateManager.getDateInstance(ovc.getDateEnrollment()));
                osa.setLastModifiedDate(DateManager.getDateInstance(ovc.getDateOfEntry()));
                osa.setLastPointOfUpdate("b");
                osa.setOvcId(ovc.getOvcId());
                osa.setOvcInSchool(ovc.getSchoolStatus());
                /*if(ovc.getSchoolName() !=null && appUtil.isString(ovc.getSchoolName()))
                {
                    osa.setOvcInSchool("Yes");
                }*/
                //if(ovc.getSchoolStatus()==null || !appUtil.isString(ovc.getSchoolStatus()))
                osa.setRecordedBy(ovc.getRecordedBy());
                List followupList=util.getFollowupDaoInstance().getFollowedupRecordsOrderedByDateDesc(ovc.getOvcId());
                if(followupList !=null && !followupList.isEmpty())
                {
                    fs=(FollowupSurvey)followupList.get(0);
                    if(fs !=null)
                    {
                        if(fs.getUpdatedSchoolStatus() !=null && (fs.getUpdatedSchoolStatus().equalsIgnoreCase("Yes") || fs.getUpdatedSchoolStatus().equalsIgnoreCase("No")))
                        {
                            osa.setOvcInSchool(fs.getUpdatedSchoolStatus());
                            if(fs.getUpdatedSchoolName() !=null && appUtil.isString(fs.getUpdatedSchoolName()))
                            osa.setCurrentSchoolName(fs.getUpdatedSchoolName());
                            if(fs.getUpdatedClass() !=null && appUtil.isString(fs.getUpdatedClass()))
                            osa.setCurrentClass(fs.getUpdatedClass());
                            osa.setLastPointOfUpdate("f");
                        }
                    }
                }
                if(util.getChildSchoolStatusDaoInstance().getChildSchoolStatus(ovc.getOvcId()) ==null)
                {
                    util.getChildSchoolStatusDaoInstance().saveChildSchoolStatus(osa);
                    System.err.println("Child school status record saved ");
                }
                else
                {
                    util.getChildSchoolStatusDaoInstance().updateChildSchoolStatus(osa);
                    System.err.println("Child school status record updated ");
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public static void saveOrUpdateSchoolAttendanceTracker(CaregiverExpenditureAndSchoolAttendance ceasa)
    {
        try
        {
            DaoUtil util=new DaoUtil();
            SchoolAttendanceTracker sat=new SchoolAttendanceTracker();
            SchoolAttendanceTrackerDao sadao=util.getSchoolAttendanceTrackerDaoInstance();
            String concatenatedOvcIds=ceasa.getOvcId();
            if(ceasa.getSchAttendance() !=null)
            {
                if(concatenatedOvcIds !=null)
                {
                    String[] ovcIdArray=concatenatedOvcIds.split(",");
                    for(int i=0; i<ovcIdArray.length; i++)
                    {
                        sat=new SchoolAttendanceTracker();
                        if(ceasa.getSchAttendance() ==null || ceasa.getSchAttendance().trim().length()==0 || ceasa.getSchAttendance().trim().equalsIgnoreCase("N/A"))
                        continue;
                        //sat.setCaregiverId(ceasa.getCaregiverId());
                        sat.setChildMissSchool(ceasa.getSchAttendance());
                        sat.setDateOfAssessment(DateManager.getDateInstance(ceasa.getDateOfAssessment()));
                        sat.setLastModifiedDate(DateManager.getDateInstance(DateManager.getCurrentDate()));
                        sat.setOvcId(ovcIdArray[i]);
                        sat.setReasonsForMissingSchool(ceasa.getReasonsForMissingSch());
                        sat.setRecordedBy(sat.getRecordedBy());
                        SimpleDateFormat sdf = new SimpleDateFormat(DateManager.DB_DATE_FORMAT); 
                        String dateOfAssessment=sdf.format(sat.getDateOfAssessment());
                        //System.err.println("sat.getDateOfAssessment().toString() is "+dateOfAssessment);
                        SchoolAttendanceTracker existingSa=sadao.getSchoolAttendanceTracker(sat.getOvcId(), dateOfAssessment);
                        if(existingSa==null)
                        {
                            //if(sat.getChildMissSchool() !=null && !sat.getChildMissSchool().trim().equalsIgnoreCase("N/A"))
                            sadao.saveSchoolAttendanceTracker(sat);
                            //count++;
                            System.err.println("School attendance tracker record saved ");
                        }
                        else
                        {
                            sat.setRecordId(existingSa.getRecordId());
                            sadao.updateSchoolAttendanceTracker(sat);
                            //count++;
                            System.err.println("School attendance tracker record updated ");
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
    public static void saveOrUpdateSchoolAttendanceTracker(OvcService service)
    {
        try
        {
            //if(service !=null && service.getChildMissedSchool() !=null && !service.getChildMissedSchool().equalsIgnoreCase("N/A"))
            if(service !=null && service.getChildMissedSchool() !=null && (service.getChildMissedSchool().equalsIgnoreCase("No") || service.getChildMissedSchool().equalsIgnoreCase("Yes")))
            {
                DaoUtil util=new DaoUtil();
                SchoolAttendanceTracker sat=new SchoolAttendanceTracker();
                SchoolAttendanceTrackerDao sadao=util.getSchoolAttendanceTrackerDaoInstance();
                String ovcId=service.getOvcId();
                if(ovcId !=null)
                {
                    sat=new SchoolAttendanceTracker();
                    //sat.setCaregiverId(ceasa.getCaregiverId());
                    sat.setChildMissSchool(service.getChildMissedSchool());
                    sat.setDateOfAssessment(DateManager.getDateInstance(service.getServicedate()));
                    sat.setLastModifiedDate(DateManager.getDateInstance(DateManager.getCurrentDate()));
                    sat.setOvcId(ovcId);
                    sat.setRecordedBy(service.getRecordedBy());
                    SimpleDateFormat sdf = new SimpleDateFormat(DateManager.DB_DATE_FORMAT); 
                    String dateOfAssessment=sdf.format(sat.getDateOfAssessment());
                    //System.err.println("sat.getDateOfAssessment().toString() is "+dateOfAssessment);
                    SchoolAttendanceTracker existingSa=sadao.getSchoolAttendanceTracker(sat.getOvcId(), dateOfAssessment);
                    if(existingSa==null)
                    {
                        sadao.saveSchoolAttendanceTracker(sat);
                        System.err.println("School attendance tracker record saved ");
                    }
                    else
                    {
                        sat.setRecordId(existingSa.getRecordId());
                        sadao.updateSchoolAttendanceTracker(sat);
                        System.err.println("School attendance tracker record updated ");
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
