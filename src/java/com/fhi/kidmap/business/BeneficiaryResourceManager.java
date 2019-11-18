/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.business;

import com.fhi.kidmap.dao.BirthRegistrationDao;
import com.fhi.kidmap.dao.BirthRegistrationDaoImpl;
import com.fhi.kidmap.dao.CaregiverDao;
import com.fhi.kidmap.dao.CaregiverDaoImpl;
import com.fhi.kidmap.dao.ChildSchoolStatusDao;
import com.fhi.kidmap.dao.ChildSchoolStatusDaoImpl;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.HouseholdEnrollmentDao;
import com.fhi.kidmap.dao.HouseholdEnrollmentDaoImpl;
import com.fhi.kidmap.dao.HouseholdFollowupAssessmentDao;
import com.fhi.kidmap.dao.HouseholdFollowupAssessmentDaoImpl;
import com.fhi.kidmap.dao.OvcDao;
import com.fhi.kidmap.dao.OvcDaoImpl;
import com.fhi.kidmap.dao.OvcWithdrawalDao;
import com.fhi.kidmap.dao.OvcWithdrawalDaoImpl;
import com.fhi.kidmap.dao.ReferralDirectoryDao;
import com.fhi.kidmap.dao.ReferralDirectoryDaoImpl;
import com.fhi.nomis.nomisutils.AppUtility;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author smomoh
 */
public class BeneficiaryResourceManager implements Serializable
{
    public ReferralDirectory getReferralDirectory(String facilityId)
    {
        ReferralDirectory rd=new ReferralDirectory();
        try
        {
            if(facilityId !=null && facilityId.trim().length()>0)
            {
                ReferralDirectoryDao rddao=new ReferralDirectoryDaoImpl();
                rd=rddao.getReferralDirectory(facilityId);
                if(rd==null)
                rd=new ReferralDirectory();
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return rd;
    }
    public ChildSchoolStatus getCurrentChildSchoolStatus(String ovcId)
    {
        ChildSchoolStatus css=null;
        try
        {
            DaoUtil util=new DaoUtil();
            css=util.getChildSchoolStatusDaoInstance().getChildSchoolStatus(ovcId);
            if(css ==null)
            css=new ChildSchoolStatus();
            if(css.getOvcInSchool()==null || !css.getOvcInSchool().equalsIgnoreCase("Yes"))
            {
                css.setCurrentClass("");
                css.setCurrentSchoolName("");
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        if(css ==null)
        css=new ChildSchoolStatus();
        return css;
    }
    /*public String getOvcCurrentSchoolStatus(String ovcId)
    {
        String currentSchoolStatus=null;
        try
        {
            DaoUtil util=new DaoUtil();
            FollowupSurvey fu=util.getFollowupDaoInstance().getMostRecentFollowupRecord(ovcId);
            if(fu !=null)
            currentSchoolStatus=fu.getUpdatedSchoolStatus();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return currentSchoolStatus;
    }*/
    public int getOvcBaselineVulnerabilityScore(String ovcId,String dateOfEnrollment)
    {
        DaoUtil util=new DaoUtil();
        int baselineVulnerabilityScore=0;
        try
        {
            ChildStatusIndex csi=util.getChildStatusIndexDaoInstance().getChildStatusIndex(ovcId, dateOfEnrollment);
            if(csi !=null)
            baselineVulnerabilityScore=util.getChildStatusIndexDaoInstance().getTotalCsiScore(csi);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return baselineVulnerabilityScore;
    }
    public int getOvcCurrentVulnerabilityScore(String ovcId)
    {
        DaoUtil util=new DaoUtil();
        int currentVulnerabilityScore=0;
        try
        {
            ChildStatusIndex csi=util.getChildStatusIndexDaoInstance().getMostRecentCsiScore(ovcId);
            if(csi !=null)
            currentVulnerabilityScore=util.getChildStatusIndexDaoInstance().getTotalCsiScore(csi);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return currentVulnerabilityScore;
    }
    public String getOvcBaselineVulnerabilityStatus(String ovcId,String dateOfEnrollment)
    {
        AppUtility appUtil=new AppUtility();
        String baselineVulnerabilityStatus=null;
        baselineVulnerabilityStatus=appUtil.getOvcVulnerabilityStatusByCSI(getOvcBaselineVulnerabilityScore(ovcId,dateOfEnrollment));
        return baselineVulnerabilityStatus;
    }
    public int getCalculatedHouseholdFollowupVulnerabilityScore(HouseholdFollowupAssessment hhfa)
    {
        int calculatedScore=0;
        try
        {
            DaoUtil util=new DaoUtil();
            calculatedScore=util.getHouseholdFollowupAssessmentDaoInstance().getFollowupVulnerabilityScore(hhfa);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return calculatedScore;
    }
    public String getOvcCurrentVulnerabilityStatus(String ovcId)
    {
        String currentVulnerabilityStatus=null;
        AppUtility appUtil=new AppUtility();
        currentVulnerabilityStatus=appUtil.getOvcVulnerabilityStatusByCSI(getOvcCurrentVulnerabilityScore(ovcId));
        return currentVulnerabilityStatus;
    }
    /*public int getHouseholdBaselineVulnerabilityScore(String hhUniqueId,String dateOfEnrollment)
    {
        DaoUtil util=new DaoUtil();
        int baselineVulnerabilityScore=0;
        try
        {
            HouseholdVulnerabilityAssessment hva=util.getHouseholdVulnerabilityAssessmentDaoInstance().getHouseholdVulnerabilityAssessment(hhUniqueId, dateOfEnrollment);
            if(hva !=null)
            baselineVulnerabilityScore=util.getHouseholdVulnerabilityAssessmentDaoInstance().getHouseholdVulnerabilityScore(hva);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return baselineVulnerabilityScore;
    }*/
    //this return a new HouseholdFollowupAssessment if none exist. Used for HouseholdEnrollment class
    public HouseholdFollowupAssessment getMostRecentHouseholdFollowupAssessmentForHhe(String hhUniqueId)
    {
        HouseholdFollowupAssessment hhfa=getMostRecentHouseholdFollowupAssessment(hhUniqueId);
        if(hhfa==null)
        {
            hhfa=new HouseholdFollowupAssessment();
        }
        hhfa.setHhUniqueId(hhUniqueId);
        hhfa.setVulnerabilityScore(getCalculatedHouseholdFollowupVulnerabilityScore(hhfa));
        return hhfa;
    }
    //this returns null if none exist.
    public HouseholdFollowupAssessment getMostRecentHouseholdFollowupAssessment(String hhUniqueId)
    {
        HouseholdFollowupAssessment hhfa=null;
        try
        {
            DaoUtil util=new DaoUtil();
            hhfa=util.getHouseholdFollowupAssessmentDaoInstance().getMostRecentHouseholdFollowupAssessment(hhUniqueId);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return hhfa;
    }
    public int getHouseholdCurrentVulnerabilityScore(String hhUniqueId,String dateOfEnrollment)
    {
        int currentVulnerabilityScore=0;
        try
        {
            HouseholdFollowupAssessment hhfa=getMostRecentHouseholdFollowupAssessment(hhUniqueId);
            if(hhfa !=null)
            currentVulnerabilityScore=getCalculatedHouseholdFollowupVulnerabilityScore(hhfa);
            //else
            //currentVulnerabilityScore=getHouseholdBaselineVulnerabilityScore(hhUniqueId,dateOfEnrollment);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return currentVulnerabilityScore;
    }
    /*public String getHouseholdBaselineVulnerabilityStatus(String hhUniqueId,String dateOfAssessment)
    {
        AppUtility appUtil=new AppUtility(); //appUtil.getHVAStatus(score);
        String baselineVulnerabilityStatus=null;
        baselineVulnerabilityStatus=appUtil.getHVAStatus(getHouseholdBaselineVulnerabilityScore(hhUniqueId,dateOfAssessment));
        return baselineVulnerabilityStatus;
    }*/
    public String getHouseholdCurrentVulnerabilityStatus(int currentVulnerabilityScore)
    {
        AppUtility appUtil=new AppUtility();
        String currentVulnerabilityStatus=appUtil.getHVAStatus(currentVulnerabilityScore);      
        return currentVulnerabilityStatus;
    }
    /*public String getHouseholdCurrentVulnerabilityStatus(String hhUniqueId,String dateOfAssessment)
    {
        String currentVulnerabilityStatus=null;
        AppUtility appUtil=new AppUtility();
        currentVulnerabilityStatus=appUtil.getHVAStatus(getHouseholdCurrentVulnerabilityScore(hhUniqueId,dateOfAssessment));
        return currentVulnerabilityStatus;
    }*/
    public HivStatusUpdate getActiveHivStatus(String clientId,String enrollmentDate)
    {
        //AppUtility appUtil=new AppUtility();
        HivStatus hsu=new HivStatus();
        HivStatusUpdate activeHivStatus=hsu.getActiveHivStatus(clientId);
        if(activeHivStatus==null)
        {
            activeHivStatus=hsu.getNewHivStatus(clientId,enrollmentDate,"enrollment");
            /*activeHivStatus=new HivStatusUpdate();
            activeHivStatus.setClientEnrolledInCare("No");
            activeHivStatus.setEnrolledOnART("No");
            activeHivStatus.setPointOfUpdate("enrollment");
            activeHivStatus.setDateOfCurrentStatus(enrollmentDate);
            activeHivStatus.setHivStatus(NomisConstant.HIV_UNKNOWN);*/
            
        }
        return activeHivStatus;
    }
    public OvcWithdrawal getWithdrawalRecord(String uniqueId,String withdrawalStatus)
    {
        OvcWithdrawal withdrawal=null;
        if(withdrawalStatus !=null && withdrawalStatus.equalsIgnoreCase("Yes"))
        {
            OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl();
            try
            {
                withdrawal=wdao.getOvcWithdrawal(uniqueId);
                if(withdrawal==null)
                withdrawal=new OvcWithdrawal();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        return withdrawal;
    }
    public String getActiveSchoolStatus(String uniqueId)
    {
        ChildSchoolStatusDao sstdao=new ChildSchoolStatusDaoImpl(); 
        String currentSchoolStatus=null;
        try
        {
            ChildSchoolStatus csst=sstdao.getChildSchoolStatus(uniqueId);
            if(csst !=null)
            {
                currentSchoolStatus=csst.getOvcInSchool();
            } 
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return currentSchoolStatus;
    }
    /*public String getActiveSchoolStatus(String uniqueId)
    {
        ChildSchoolStatus csst=null;
        //ChildSchoolStatus
        String currentSchoolStatus=null;
        FollowupDao fdao=new FollowupDaoImpl();
        try
        {
            List fuList=fdao.getFollowedupRecordsWithSchoolDetails(uniqueId);
            if(fuList !=null && !fuList.isEmpty())
            {
                FollowupSurvey fu=(FollowupSurvey)fuList.get(0);
                currentSchoolStatus=fu.getUpdatedSchoolStatus();
            }
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return currentSchoolStatus;
    }*/
    public BirthRegistration getCurrentBirthRegistration(String uniqueId,String dateOfRegistration,String pointOfUpdate)
    {
        BirthRegistration br=null;
        BirthRegistrationDao brdao=new BirthRegistrationDaoImpl();
        try
        {
            br=brdao.getBirthRegistrationByIdDateAndPointOfService(uniqueId, dateOfRegistration, pointOfUpdate); 
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return br;
    }
    public String getCurrentBirthRegistrationId(String uniqueId,String dateOfRegistration,String pointOfUpdate)
    {
        String currentBirthRegistrationId=null;
        BirthRegistration br=getCurrentBirthRegistration(uniqueId,dateOfRegistration,pointOfUpdate);
        if(br !=null)
        currentBirthRegistrationId=br.getRecordId();
        return currentBirthRegistrationId;
    }
    public String getCurrentBirthRegistrationStatus(String uniqueId,String dateOfRegistration,String pointOfUpdate)
    {
        String currentBirthRegistrationStatus=null;
        BirthRegistration br=getCurrentBirthRegistration(uniqueId,dateOfRegistration,pointOfUpdate);
        if(br !=null)
        currentBirthRegistrationStatus=br.getBirthRegistrationStatus();
        return currentBirthRegistrationStatus;
    }
    public String getActiveBirthRegistrationStatus(String uniqueId)
    {
        String currentBirthRegistrationStatus=null;
        BirthRegistrationDao brdao=new BirthRegistrationDaoImpl();
        try
        {
            BirthRegistration br=brdao.getActiveBirthRegistration(uniqueId);
            if(br !=null)
            currentBirthRegistrationStatus=br.getBirthRegistrationStatus();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return currentBirthRegistrationStatus;
    }
    public BirthRegistration getActiveBirthRegistration(String uniqueId)
    {
        BirthRegistration br=null;
        BirthRegistrationDao brdao=new BirthRegistrationDaoImpl();
        try
        {
            br=brdao.getActiveBirthRegistration(uniqueId); 
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return br;
    }
    public HouseholdEnrollment getHouseholdHead(String hhUniqueId)
    {
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        HouseholdEnrollment hhe=null;
        try
        {
            hhe=hhedao.getHouseholdEnrollment(hhUniqueId);
            if(hhe==null)
            hhe=new HouseholdEnrollment();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return hhe;
    }
    public Caregiver getCaregiver(String caregiverId)
    {
       CaregiverDao cgiverdao=new CaregiverDaoImpl();
       Caregiver cgiver=null;
       try
       {
           cgiver=cgiverdao.getCaregiverByCaregiverId(caregiverId);
           if(cgiver==null)
           {
                cgiver=new Caregiver();
                cgiver.setCaregiverId(caregiverId);
           }
       }
       catch(Exception ex)
       {
           ex.printStackTrace();
       }
       return cgiver;
    }
    public static Ovc getOvc(String ovcId)
    {
        OvcDao ovcdao=new OvcDaoImpl();
        Ovc ovc=null;
        try
        {
            ovc=ovcdao.getOvc(ovcId);
            if(ovc==null)
            {
                ovc=new Ovc();
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return ovc;
    }
    public String getOrganizationUnitName(String hhUniqueId,String type)
    {
        DaoUtil util=new DaoUtil();
        String organizationUnitName=null;
        try
        {
            HouseholdEnrollment hhe=getHouseholdHead(hhUniqueId);
            if(hhe !=null)
            {
                if(type.equalsIgnoreCase("state"))
                organizationUnitName=util.getStateName(hhe.getStateCode());
                else if(type.equalsIgnoreCase("lga"))
                {
                    Lgas lga=util.getLgaByStateAndLgaCode(hhe.getStateCode(), hhe.getLgaCode());
                    if(lga !=null)
                    organizationUnitName=lga.getLga_name();
                }
                else if(type.equalsIgnoreCase("cbo"))
                organizationUnitName=util.getOrganizationName(hhe.getOrgCode());
                else if(type.equalsIgnoreCase("ward"))
                organizationUnitName=util.getWardName(hhe.getCommunityCode());
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return organizationUnitName;
    }
    public Wards getOrganizationUnitCommunity(String communityCode)
    {
        DaoUtil util=new DaoUtil();
        Wards community=null;
        try
        {
            if(getHouseholdHead(communityCode) !=null)
            {
                community=util.getWard(communityCode);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return community;
    }
    public HouseholdVulnerabilityAssessment getHouseholdVulnerabilityAssessment(String hhUniqueId,String dateOfAssessment)
    {
        DaoUtil util=new DaoUtil();
        HouseholdVulnerabilityAssessment hva=util.getHouseholdVulnerabilityAssessment(hhUniqueId, dateOfAssessment);
        if(hva==null)
        {
            hva=new HouseholdVulnerabilityAssessment();
            hva.setHhUniqueId(hhUniqueId);
            hva.setDateOfAssessment(dateOfAssessment);
        }
        return hva;
    }
    public HouseholdVulnerabilityAssessment getLastHouseholdFollowupAssessment(String hhUniqueId)
    {
        HouseholdFollowupAssessmentDao hfudao=new HouseholdFollowupAssessmentDaoImpl();
        HouseholdFollowupAssessment hfa=null;
        HouseholdVulnerabilityAssessment hva=null;
        DaoUtil util=new DaoUtil();
        try
        {
            List list=hfudao.getListOfAssessmentsPerHousehold(hhUniqueId);
            if(list !=null && !list.isEmpty())
            {
                hfa=(HouseholdFollowupAssessment)list.get(0);
                hva=util.getHouseholdVulnerabilityAssessment(hhUniqueId, hfa.getDateOfAssessment());

            }
            if(hva==null)
            {
                hva=new HouseholdVulnerabilityAssessment();
                hva.setHhUniqueId(hhUniqueId);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return hva;
    }//lastFollowupAssessment
    public String getHouseholdVulnerabilityStatus(int score)
    {
        DaoUtil util=new DaoUtil();
        String vulnerabilityStatus=util.getVulnerabilityStatus(score);
        return vulnerabilityStatus;
    }
    public String getOvcCurrentAgeUnit(String ovcId)
    {
        Ovc ovc=getOvc(ovcId);
        DaoUtil util=new DaoUtil();
        String currentAgeUnit=" ";
        if(ovc !=null)
        {
            currentAgeUnit=ovc.getAgeUnit();
            if(ovc.getAgeUnit() !=null && ovc.getAgeUnit().equalsIgnoreCase("Month"))
            {
               currentAgeUnit=util.getCurrentAgeUnit(ovc);
            }
        }
        //System.err.println("currentAgeUnit in beneficiary is "+currentAgeUnit);
        return currentAgeUnit;
    }
    public String getOvcCurrentAgeUnit(Ovc ovc)
    {
        DaoUtil util=new DaoUtil();
        String currentAgeUnit=" ";
        if(ovc !=null)
        {
            currentAgeUnit=ovc.getAgeUnit();
            if(ovc.getAgeUnit() !=null && ovc.getAgeUnit().equalsIgnoreCase("Month"))
            {
               currentAgeUnit=util.getCurrentAgeUnit(ovc);
            }
        }
        System.err.println("currentAgeUnit in beneficiary is "+currentAgeUnit);
        return currentAgeUnit;
    }
    public int getMonthFromDate(String date)
    {
        int month=0;
        if(date !=null && date.indexOf("-") !=-1)
        {
            String[] dateArray=date.split("-");
            month=Integer.parseInt(dateArray[1]);
        }
        return month;
    }
    public int getYearFromDate(String date)
    {
        int year=0;
        if(date !=null && date.indexOf("-") !=-1)
        {
            String[] dateArray=date.split("-");
            year=Integer.parseInt(dateArray[0]);
        }
        return year;
    }
}
