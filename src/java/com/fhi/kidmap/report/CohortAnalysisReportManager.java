/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report;

import com.fhi.kidmap.dao.CaregiverDao;
import com.fhi.kidmap.dao.CaregiverDaoImpl;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.FollowupDao;
import com.fhi.kidmap.dao.FollowupDaoImpl;
import com.fhi.kidmap.dao.HouseholdEnrollmentDao;
import com.fhi.kidmap.dao.HouseholdEnrollmentDaoImpl;
import com.fhi.kidmap.dao.HouseholdFollowupAssessmentDao;
import com.fhi.kidmap.dao.HouseholdFollowupAssessmentDaoImpl;
import com.fhi.kidmap.dao.OvcDao;
import com.fhi.kidmap.dao.OvcDaoImpl;
import com.fhi.kidmap.dao.OvcServiceDao;
import com.fhi.kidmap.dao.OvcServiceDaoImpl;
import com.fhi.kidmap.dao.OvcWithdrawalDao;
import com.fhi.kidmap.dao.OvcWithdrawalDaoImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author smomoh
 */
public class CohortAnalysisReportManager implements Serializable
{
    public List getCohortAnalysisIndicators()
    {
        IndicatorDictionary ind=new IndicatorDictionary();
        List list=new ArrayList();
        list.add(ind.getIndicatorForNumberOfHouseholdsEnrolledPerCohort());
        //list.add(ind.getIndicatorForNumberOfHouseholdsCurrentlyEnrolled());
        list.add(ind.getIndicatorForNumberOfHouseholdsFollowedupPerCohort());
        list.add(ind.getIndicatorForNumberOfHouseholdsNotFollowedupPerCohort());
        list.add(ind.getIndicatorForNumberOfHouseholdsWithdrawnDueToGraduation());
        list.add(ind.getIndicatorForNumberOfHouseholdsWithdrawnDueToLostToFollowup());
        list.add(ind.getIndicatorForNumberOfHouseholdsWithdrawnDueToMigration());
        list.add(ind.getIndicatorForNumberOfHouseholdsWithdrawnDueToTransfer());
        list.add(ind.getIndicatorForNumberOfCaregiversEnrolledPerCohort());
        list.add(ind.getIndicatorForNumberOfCaregiversFollowedup());
        list.add(ind.getIndicatorForNumberOfHivPositiveCaregiversAtFollowedup());
        list.add(ind.getIndicatorForNumberOfHivNegativeCaregiversAtFollowedup());
        list.add(ind.getIndicatorForNumberOfHivUnknownCaregiversAtFollowedup());
        list.add(ind.getIndicatorForNumberOfOvcEnrolled());
        //list.add(ind.getIndicatorForNumberOfOvcCurrentlyEnrolled());
        //list.add(ind.getIndicatorForNumberOfHivUnknownOvcAtBaseline());
        list.add(ind.getIndicatorForNumberOfOvcFollowedup());
        list.add(ind.getIndicatorForNumberOfOvcNotFollowedup());
        list.add(ind.getIndicatorForNumberOfHivPositiveOvcAtFollowedup());
        list.add(ind.getIndicatorForNumberOfHivNegativeOvcAtFollowedup());
        list.add(ind.getIndicatorForNumberOfHivUnknownOvcAtFollowedup());
        list.add(ind.getIndicatorForNumberOfOvcWithdrawnDueToAgingOut());
        list.add(ind.getIndicatorForNumberOfOvcGraduated());
        list.add(ind.getIndicatorForNumberOfOvcWithdrawnDueToKnownDeath());
        list.add(ind.getIndicatorForNumberOfOvcWithdrawnDueToLostToFollowup());
        list.add(ind.getIndicatorForNumberOfOvcWithdrawnDueToTransfer());
        list.add(ind.getIndicatorForNumberOfOvcWithdrawnDueToMigration());
        list.add(ind.getIndicatorForNumberOfOvcTestedForHIV());
        return list;
    }
    public List getCohortAnalysisReport(String indicatorId,List paramList,String reporttype,String gender)
    {
        DaoUtil util=new DaoUtil();
        String stateCode=paramList.get(0).toString();
        String lgaCode=paramList.get(1).toString();
        String cboCode=paramList.get(2).toString();
        String wardCode=paramList.get(3).toString();
        String partnerCode=paramList.get(4).toString();
        String startMth=paramList.get(5).toString();
        String startYear=paramList.get(6).toString();
        String endMth=paramList.get(7).toString();
        String endYear=paramList.get(8).toString();
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        HouseholdFollowupAssessmentDao hhfdao=new HouseholdFollowupAssessmentDaoImpl();
        CaregiverDao cgiverdao=new CaregiverDaoImpl();
        OvcDao ovcdao=new OvcDaoImpl();
        FollowupDao fdao=new FollowupDaoImpl();
        OvcWithdrawalDao withdrawaldao=new OvcWithdrawalDaoImpl();
        OvcServiceDao servicedao=new OvcServiceDaoImpl();
        List list=new ArrayList();
        if(indicatorId !=null)
        {
            try
            {
                String[] dateParam={startMth,startYear,endMth,endYear};
                String[] hheParams={stateCode,lgaCode,cboCode,wardCode,null,null,startMth,startYear,endMth,endYear,partnerCode};
                String additionalQueryCriteria=util.getHVIReportCriteria(hheParams);
                SummaryStatisticsBean stb=new SummaryStatisticsBean();
                IndicatorDictionary ind=new IndicatorDictionary();
                String genderQueryCriteria=" ";
                if(indicatorId.equalsIgnoreCase("noOfHouseholdsEnrolledPerCohort"))
                {
                    String indicatorName=ind.getIndicatorForNumberOfHouseholdsEnrolledPerCohort().getIndicatorName();
                    stb.setCategoryOptionCombo("household");
                    if(reporttype !=null && reporttype.equalsIgnoreCase("list"))
                    {
                        //Both gender
                        list.add(hhedao.getListOfHouseholdEnrollment(additionalQueryCriteria,null));
                        list.add(indicatorName);
                    }
                    else if(reporttype !=null && reporttype.equalsIgnoreCase("number"))
                    {
                       stb.setIndicatorId(indicatorId);
                       stb.setIndicatorName(indicatorName);
                       stb.setTotNoOfOvc(hhedao.getNumberOfHouseholdsEnrolled(additionalQueryCriteria));
                       list.add(stb);
                    }
                   //System.err.println("stb is "+stb.getIndicatorName()+" "+stb.getTotNoOfOvc());
                }
                if(indicatorId.equalsIgnoreCase("noOfHouseholdsFollowedUpPerCohort"))
                {
                    String indicatorName=ind.getIndicatorForNumberOfHouseholdsFollowedupPerCohort().getIndicatorName();
                    stb.setCategoryOptionCombo("household");
                    if(reporttype !=null && reporttype.equalsIgnoreCase("list"))
                    {
                        //Both gender
                        list.add(hhfdao.getListOfHouseholdFollowupAssessmentsPerCohort(additionalQueryCriteria));
                        list.add(indicatorName);
                    }
                    else if(reporttype !=null && reporttype.equalsIgnoreCase("number"))
                    {
                       stb.setIndicatorId(indicatorId);
                       stb.setIndicatorName(indicatorName);
                       stb.setTotNoOfOvc(hhfdao.getNumberOfHouseholdFollowupAssessmentsPerCohort(additionalQueryCriteria));
                       list.add(stb);
                    }
                }
                if(indicatorId.equalsIgnoreCase("noOfHouseholdsNotFollowedUpPerCohort"))
                {
                    String indicatorName=ind.getIndicatorForNumberOfHouseholdsNotFollowedupPerCohort().getIndicatorName();
                    stb.setCategoryOptionCombo("household");
                   if(reporttype !=null && reporttype.equalsIgnoreCase("list"))
                    {
                        //Both gender
                        list.add(hhfdao.getListOfHouseholdWithoutFollowupAssessmentsPerCohort(additionalQueryCriteria));
                        list.add(indicatorName);
                    }
                    else if(reporttype !=null && reporttype.equalsIgnoreCase("number"))
                    {
                       stb.setIndicatorId(indicatorId);
                       stb.setIndicatorName(indicatorName);
                       stb.setTotNoOfOvc(hhfdao.getNumberOfHouseholdWithoutFollowupAssessmentsPerCohort(additionalQueryCriteria)); 
                       list.add(stb);
                    }
                }
                if(indicatorId.equalsIgnoreCase("noOfCaregiverEnrolledPerCohort"))
                {
                    String indicatorName=ind.getIndicatorForNumberOfCaregiversEnrolledPerCohort().getIndicatorName();
                    stb.setCategoryOptionCombo("caregiver");
                   if(reporttype !=null && reporttype.equalsIgnoreCase("list"))
                    {
                        if(gender.equalsIgnoreCase("Male"))
                        genderQueryCriteria=" and UPPER(cgiver.caregiverGender)='MALE'";
                        else if(gender.equalsIgnoreCase("Female"))
                        genderQueryCriteria=" and UPPER(cgiver.caregiverGender)='FEMALE'";
                        list.add(cgiverdao.getListOfCaregiversPerCohort(additionalQueryCriteria+genderQueryCriteria));
                        list.add(indicatorName);
                    }
                    else if(reporttype !=null && reporttype.equalsIgnoreCase("number"))
                    {
                       genderQueryCriteria=" and UPPER(cgiver.caregiverGender)='MALE'";
                       stb.setIndicatorId(indicatorId);
                       stb.setIndicatorName(indicatorName);
                       stb.setValue3(cgiverdao.getNoOfCaregiversPerCohort(additionalQueryCriteria+genderQueryCriteria)); 
                       genderQueryCriteria=" and UPPER(cgiver.caregiverGender)='FEMALE'";
                       stb.setValue4(cgiverdao.getNoOfCaregiversPerCohort(additionalQueryCriteria+genderQueryCriteria)); 
                       stb.setTotNoOfOvc(cgiverdao.getNoOfCaregiversPerCohort(additionalQueryCriteria)); 
                       list.add(stb);
                    }
                }
                if(indicatorId.equalsIgnoreCase("noOfCaregiversFollowedUp"))
                {
                    String indicatorName=ind.getIndicatorForNumberOfCaregiversFollowedup().getIndicatorName();
                    stb.setCategoryOptionCombo("caregiver");
                   if(reporttype !=null && reporttype.equalsIgnoreCase("list"))
                    {
                        if(gender.equalsIgnoreCase("Male"))
                        genderQueryCriteria=" and UPPER(cgiver.caregiverGender)='MALE'";
                        else if(gender.equalsIgnoreCase("Female"))
                        genderQueryCriteria=" and UPPER(cgiver.caregiverGender)='FEMALE'";
                        list.add(cgiverdao.getListOfCaregiversFollowedupPerCohort(additionalQueryCriteria+genderQueryCriteria));
                        list.add(indicatorName);
                    }
                    else if(reporttype !=null && reporttype.equalsIgnoreCase("number"))
                    {
                       genderQueryCriteria=" and UPPER(cgiver.caregiverGender)='MALE'";
                       stb.setIndicatorId(indicatorId);
                       stb.setIndicatorName(indicatorName);
                       stb.setValue3(cgiverdao.getNoOfCaregiversFollowedupPerCohort(additionalQueryCriteria+genderQueryCriteria));
                       genderQueryCriteria=" and UPPER(cgiver.caregiverGender)='FEMALE'";
                       stb.setValue4(cgiverdao.getNoOfCaregiversFollowedupPerCohort(additionalQueryCriteria+genderQueryCriteria));
                       stb.setTotNoOfOvc(cgiverdao.getNoOfCaregiversFollowedupPerCohort(additionalQueryCriteria));
                       list.add(stb);
                    }
                }
                if(indicatorId.equalsIgnoreCase("noOfHivPositiveCaregiversFollowedUp"))
                {
                    String indicatorName=ind.getIndicatorForNumberOfHivPositiveCaregiversAtFollowedup().getIndicatorName();
                    stb.setCategoryOptionCombo("caregiver");
                   if(reporttype !=null && reporttype.equalsIgnoreCase("list"))
                    {
                        if(gender.equalsIgnoreCase("Male"))
                        genderQueryCriteria=" and UPPER(cgiver.caregiverGender)='MALE'";
                        else if(gender.equalsIgnoreCase("Female"))
                        genderQueryCriteria=" and UPPER(cgiver.caregiverGender)='FEMALE'";
                        list.add(cgiverdao.getListOfHivPositiveCaregiversFollowedupPerCohort(additionalQueryCriteria+genderQueryCriteria));
                        list.add(indicatorName);
                    }
                    else if(reporttype !=null && reporttype.equalsIgnoreCase("number"))
                    {
                       genderQueryCriteria=" and UPPER(cgiver.caregiverGender)='MALE'";
                       stb.setIndicatorId(indicatorId);
                       stb.setIndicatorName(indicatorName);
                       stb.setValue3(cgiverdao.getNoOfHivPositiveCaregiversFollowedupPerCohort(additionalQueryCriteria+genderQueryCriteria));
                       genderQueryCriteria=" and UPPER(cgiver.caregiverGender)='FEMALE'";
                       stb.setValue4(cgiverdao.getNoOfHivPositiveCaregiversFollowedupPerCohort(additionalQueryCriteria+genderQueryCriteria));
                       stb.setTotNoOfOvc(cgiverdao.getNoOfHivPositiveCaregiversFollowedupPerCohort(additionalQueryCriteria));
                       list.add(stb);
                    }
                }
                if(indicatorId.equalsIgnoreCase("noOfHivNegativeCaregiversFollowedUp"))
                {
                    String indicatorName=ind.getIndicatorForNumberOfHivNegativeCaregiversAtFollowedup().getIndicatorName();
                    stb.setCategoryOptionCombo("caregiver");
                   if(reporttype !=null && reporttype.equalsIgnoreCase("list"))
                    {
                        if(gender.equalsIgnoreCase("Male"))
                        genderQueryCriteria=" and UPPER(cgiver.caregiverGender)='MALE'";
                        else if(gender.equalsIgnoreCase("Female"))
                        genderQueryCriteria=" and UPPER(cgiver.caregiverGender)='FEMALE'";
                        list.add(cgiverdao.getListOfHivNegativeCaregiversFollowedupPerCohort(additionalQueryCriteria+genderQueryCriteria));
                        list.add(indicatorName);
                    }
                    else if(reporttype !=null && reporttype.equalsIgnoreCase("number"))
                    {
                       genderQueryCriteria=" and UPPER(cgiver.caregiverGender)='MALE'";
                       stb.setIndicatorId(indicatorId);
                       stb.setIndicatorName(indicatorName);
                       stb.setValue3(cgiverdao.getNoOfHivNegativeCaregiversFollowedupPerCohort(additionalQueryCriteria+genderQueryCriteria));
                       genderQueryCriteria=" and UPPER(cgiver.caregiverGender)='FEMALE'";
                       stb.setValue4(cgiverdao.getNoOfHivNegativeCaregiversFollowedupPerCohort(additionalQueryCriteria+genderQueryCriteria));
                       stb.setTotNoOfOvc(cgiverdao.getNoOfHivNegativeCaregiversFollowedupPerCohort(additionalQueryCriteria));
                       list.add(stb);
                    }
                }
                if(indicatorId.equalsIgnoreCase("noOfHivUnknownCaregiversFollowedUp"))
                {
                    String indicatorName=ind.getIndicatorForNumberOfHivUnknownCaregiversAtFollowedup().getIndicatorName();
                    stb.setCategoryOptionCombo("caregiver");
                    if(reporttype !=null && reporttype.equalsIgnoreCase("list"))
                    {
                        if(gender.equalsIgnoreCase("Male"))
                        genderQueryCriteria=" and UPPER(cgiver.caregiverGender)='MALE'";
                        else if(gender.equalsIgnoreCase("Female"))
                        genderQueryCriteria=" and UPPER(cgiver.caregiverGender)='FEMALE'";
                        list.add(cgiverdao.getListOfHivUnknownCaregiversFollowedupPerCohort(additionalQueryCriteria+genderQueryCriteria));
                        list.add(indicatorName);
                    }
                    else if(reporttype !=null && reporttype.equalsIgnoreCase("number"))
                    {
                       genderQueryCriteria=" and UPPER(cgiver.caregiverGender)='MALE'";
                       stb.setIndicatorId(indicatorId);
                       stb.setIndicatorName(indicatorName);
                       stb.setValue3(cgiverdao.getNoOfHivUnknownCaregiversFollowedupPerCohort(additionalQueryCriteria+genderQueryCriteria));
                       genderQueryCriteria=" and UPPER(cgiver.caregiverGender)='FEMALE'";
                       stb.setValue4(cgiverdao.getNoOfHivUnknownCaregiversFollowedupPerCohort(additionalQueryCriteria+genderQueryCriteria));
                       stb.setTotNoOfOvc(cgiverdao.getNoOfHivUnknownCaregiversFollowedupPerCohort(additionalQueryCriteria));
                       list.add(stb);
                    }
                }
                if(indicatorId.equalsIgnoreCase("ovcEnrolled"))
                {
                    String indicatorName=ind.getIndicatorForNumberOfOvcEnrolled().getIndicatorName();
                    stb.setCategoryOptionCombo("ovc");
                    if(reporttype !=null && reporttype.equalsIgnoreCase("list"))
                    {
                        if(gender.equalsIgnoreCase("Male"))
                        genderQueryCriteria=" and UPPER(ovc.gender)='MALE'";
                        else if(gender.equalsIgnoreCase("Female"))
                        genderQueryCriteria=" and UPPER(ovc.gender)='FEMALE'";
                        list.add(ovcdao.getListOfOvcEnrolledPerCohort(additionalQueryCriteria+genderQueryCriteria));
                        list.add(indicatorName);
                    }
                    else if(reporttype !=null && reporttype.equalsIgnoreCase("number"))
                    {
                       genderQueryCriteria=" and UPPER(ovc.gender)='MALE'";
                       stb.setIndicatorId(indicatorId);
                       stb.setIndicatorName(indicatorName);
                       stb.setValue3(ovcdao.getNumberOfOvcEnrolledPerCohort(additionalQueryCriteria+genderQueryCriteria));
                       genderQueryCriteria=" and UPPER(ovc.gender)='FEMALE'";
                       stb.setValue4(ovcdao.getNumberOfOvcEnrolledPerCohort(additionalQueryCriteria+genderQueryCriteria));
                       stb.setTotNoOfOvc(ovcdao.getNumberOfOvcEnrolledPerCohort(additionalQueryCriteria));
                       list.add(stb);
                    }
                   
                }
                if(indicatorId.equalsIgnoreCase("noOfOvcFollowedUp"))
                {
                    String indicatorName=ind.getIndicatorForNumberOfOvcFollowedup().getIndicatorName();
                    stb.setCategoryOptionCombo("ovc");
                    if(reporttype !=null && reporttype.equalsIgnoreCase("list"))
                    {
                        if(gender.equalsIgnoreCase("Male"))
                        genderQueryCriteria=" and UPPER(ovc.gender)='MALE'";
                        else if(gender.equalsIgnoreCase("Female"))
                        genderQueryCriteria=" and UPPER(ovc.gender)='FEMALE'";
                        list.add(fdao.getListOfOVCFollowedupPerCohort(additionalQueryCriteria+genderQueryCriteria));
                        list.add(indicatorName);
                    }
                    else if(reporttype !=null && reporttype.equalsIgnoreCase("number"))
                    {
                       genderQueryCriteria=" and UPPER(ovc.gender)='MALE'";
                       stb.setIndicatorId(indicatorId);
                       stb.setIndicatorName(indicatorName);
                       stb.setValue3(fdao.getNoOfOVCFollowedupPerCohort(additionalQueryCriteria+genderQueryCriteria));
                       genderQueryCriteria=" and UPPER(ovc.gender)='FEMALE'";
                       stb.setValue4(fdao.getNoOfOVCFollowedupPerCohort(additionalQueryCriteria+genderQueryCriteria));
                       stb.setTotNoOfOvc(fdao.getNoOfOVCFollowedupPerCohort(additionalQueryCriteria));
                       list.add(stb);
                    }
                }
                if(indicatorId.equalsIgnoreCase("noOfOvcNotFollowedUp"))
                {
                    String indicatorName=ind.getIndicatorForNumberOfOvcNotFollowedup().getIndicatorName();
                    stb.setCategoryOptionCombo("ovc");
                    if(reporttype !=null && reporttype.equalsIgnoreCase("list"))
                    {
                        if(gender.equalsIgnoreCase("Male"))
                        genderQueryCriteria=" and UPPER(ovc.gender)='MALE'";
                        else if(gender.equalsIgnoreCase("Female"))
                        genderQueryCriteria=" and UPPER(ovc.gender)='FEMALE'";
                        list.add(fdao.getListOfOVCNotFollowedupPerCohort(additionalQueryCriteria+genderQueryCriteria));
                        list.add(indicatorName);
                    }
                    else if(reporttype !=null && reporttype.equalsIgnoreCase("number"))
                    {
                       genderQueryCriteria=" and UPPER(ovc.gender)='MALE'";
                       stb.setIndicatorId(indicatorId);
                       stb.setIndicatorName(indicatorName);
                       stb.setValue3(fdao.getNoOfOVCNotFollowedupPerCohort(additionalQueryCriteria+genderQueryCriteria));
                       genderQueryCriteria=" and UPPER(ovc.gender)='FEMALE'";
                       stb.setValue4(fdao.getNoOfOVCNotFollowedupPerCohort(additionalQueryCriteria+genderQueryCriteria));
                       stb.setTotNoOfOvc(fdao.getNoOfOVCNotFollowedupPerCohort(additionalQueryCriteria));
                       list.add(stb);
                    }
                }
                if(indicatorId.equalsIgnoreCase("noOfHivPositiveOvcFollowedUp"))
                {
                    String indicatorName=ind.getIndicatorForNumberOfHivPositiveOvcAtFollowedup().getIndicatorName();
                    stb.setCategoryOptionCombo("ovc");
                    if(reporttype !=null && reporttype.equalsIgnoreCase("list"))
                    {
                        if(gender.equalsIgnoreCase("Male"))
                        genderQueryCriteria=" and UPPER(ovc.gender)='MALE'";
                        else if(gender.equalsIgnoreCase("Female"))
                        genderQueryCriteria=" and UPPER(ovc.gender)='FEMALE'";
                        list.add(fdao.getListOfHivPositiveOVCFollowedupPerCohort(additionalQueryCriteria+genderQueryCriteria));
                        list.add(indicatorName);
                    }
                    else if(reporttype !=null && reporttype.equalsIgnoreCase("number"))
                    {
                       genderQueryCriteria=" and UPPER(ovc.gender)='MALE'";
                       stb.setIndicatorId(indicatorId);
                       stb.setIndicatorName(indicatorName);
                       stb.setValue3(fdao.getNoOfHivPositiveOVCFollowedupPerCohort(additionalQueryCriteria+genderQueryCriteria));
                       genderQueryCriteria=" and UPPER(ovc.gender)='FEMALE'";
                       stb.setValue4(fdao.getNoOfHivPositiveOVCFollowedupPerCohort(additionalQueryCriteria+genderQueryCriteria));
                       stb.setTotNoOfOvc(fdao.getNoOfHivPositiveOVCFollowedupPerCohort(additionalQueryCriteria));
                       list.add(stb);
                    }
                }
                if(indicatorId.equalsIgnoreCase("noOfHivNegativeOvcFollowedUp"))
                {
                    String indicatorName=ind.getIndicatorForNumberOfHivNegativeOvcAtFollowedup().getIndicatorName();
                    stb.setCategoryOptionCombo("ovc");
                    if(reporttype !=null && reporttype.equalsIgnoreCase("list"))
                    {
                        if(gender.equalsIgnoreCase("Male"))
                        genderQueryCriteria=" and UPPER(ovc.gender)='MALE'";
                        else if(gender.equalsIgnoreCase("Female"))
                        genderQueryCriteria=" and UPPER(ovc.gender)='FEMALE'";
                        list.add(fdao.getListOfHivNegativeOVCFollowedupPerCohort(additionalQueryCriteria+genderQueryCriteria));
                        list.add(indicatorName);
                    }
                    else if(reporttype !=null && reporttype.equalsIgnoreCase("number"))
                    {
                       genderQueryCriteria=" and UPPER(ovc.gender)='MALE'";
                       stb.setIndicatorId(indicatorId);
                       stb.setIndicatorName(indicatorName);
                       stb.setValue3(fdao.getNoOfHivNegativeOVCFollowedupPerCohort(additionalQueryCriteria+genderQueryCriteria));
                       genderQueryCriteria=" and UPPER(ovc.gender)='FEMALE'";
                       stb.setValue4(fdao.getNoOfHivNegativeOVCFollowedupPerCohort(additionalQueryCriteria+genderQueryCriteria));
                       stb.setTotNoOfOvc(fdao.getNoOfHivNegativeOVCFollowedupPerCohort(additionalQueryCriteria));
                       list.add(stb);
                    }
                }
                if(indicatorId.equalsIgnoreCase("noOfHivUnknownOvcFollowedUp"))
                {
                    String indicatorName=ind.getIndicatorForNumberOfHivUnknownOvcAtFollowedup().getIndicatorName();
                    stb.setCategoryOptionCombo("ovc");
                    if(reporttype !=null && reporttype.equalsIgnoreCase("list"))
                    {
                        if(gender.equalsIgnoreCase("Male"))
                        genderQueryCriteria=" and UPPER(ovc.gender)='MALE'";
                        else if(gender.equalsIgnoreCase("Female"))
                        genderQueryCriteria=" and UPPER(ovc.gender)='FEMALE'";
                        list.add(fdao.getListOfHivUnknownOVCFollowedupPerCohort(additionalQueryCriteria+genderQueryCriteria));
                        list.add(indicatorName);
                    }
                    else if(reporttype !=null && reporttype.equalsIgnoreCase("number"))
                    {
                       genderQueryCriteria=" and UPPER(ovc.gender)='MALE'";
                       stb.setIndicatorId(indicatorId);
                       stb.setIndicatorName(indicatorName);
                       stb.setValue3(fdao.getNoOfHivUnknownOVCFollowedupPerCohort(additionalQueryCriteria+genderQueryCriteria));
                       genderQueryCriteria=" and UPPER(ovc.gender)='FEMALE'";
                       stb.setValue4(fdao.getNoOfHivUnknownOVCFollowedupPerCohort(additionalQueryCriteria+genderQueryCriteria));
                       stb.setTotNoOfOvc(fdao.getNoOfHivUnknownOVCFollowedupPerCohort(additionalQueryCriteria));
                       list.add(stb);
                    }
                }
                if(indicatorId.equalsIgnoreCase("hhgraduated"))
                {//noOfHouseholdsWithdrawnDueToGraduation
                    String indicatorName=ind.getIndicatorForNumberOfHouseholdsWithdrawnDueToGraduation().getIndicatorName();
                    stb.setCategoryOptionCombo("household");
                    if(reporttype !=null && reporttype.equalsIgnoreCase("list"))
                    {
                        list.add(withdrawaldao.getListOfHouseholdsWithdrawnPerCohort(additionalQueryCriteria+genderQueryCriteria,"graduated"));
                        list.add(indicatorName);
                    }
                    else if(reporttype !=null && reporttype.equalsIgnoreCase("number"))
                    {
                       stb.setIndicatorId(indicatorId);
                       stb.setIndicatorName(indicatorName);
                       stb.setTotNoOfOvc(withdrawaldao.getNumberOfHouseholdsWithdrawnPerCohort(additionalQueryCriteria+genderQueryCriteria,"graduated"));
                       list.add(stb);
                    }
                   //list.add(withdrawaldao.getNumberOfHouseholdsWithdrawnPerCohort(householdQueryPart,"graduated")); 
                }
                if(indicatorId.equalsIgnoreCase("hhmigrated1"))
                {
                   String indicatorName=ind.getIndicatorForNumberOfHouseholdsWithdrawnDueToMigration().getIndicatorName();
                    stb.setCategoryOptionCombo("household");
                    if(reporttype !=null && reporttype.equalsIgnoreCase("list"))
                    {
                        list.add(withdrawaldao.getListOfHouseholdsWithdrawnPerCohort(additionalQueryCriteria+genderQueryCriteria,"migrated"));
                        list.add(indicatorName);
                    }
                    else if(reporttype !=null && reporttype.equalsIgnoreCase("number"))
                    {
                       stb.setIndicatorId(indicatorId);
                       stb.setIndicatorName(indicatorName);
                       stb.setTotNoOfOvc(withdrawaldao.getNumberOfHouseholdsWithdrawnPerCohort(additionalQueryCriteria+genderQueryCriteria,"migrated"));
                       list.add(stb);
                    }
                   
                }
                if(indicatorId.equalsIgnoreCase("hhlosttofup"))
                {
                   String indicatorName=ind.getIndicatorForNumberOfHouseholdsWithdrawnDueToLostToFollowup().getIndicatorName();
                    stb.setCategoryOptionCombo("household");
                    if(reporttype !=null && reporttype.equalsIgnoreCase("list"))
                    {
                        list.add(withdrawaldao.getListOfHouseholdsWithdrawnPerCohort(additionalQueryCriteria+genderQueryCriteria,"Loss to follow-up"));
                        list.add(indicatorName);
                    }
                    else if(reporttype !=null && reporttype.equalsIgnoreCase("number"))
                    {
                       stb.setIndicatorId(indicatorId);
                       stb.setIndicatorName(indicatorName);
                       stb.setTotNoOfOvc(withdrawaldao.getNumberOfHouseholdsWithdrawnPerCohort(additionalQueryCriteria+genderQueryCriteria,"Loss to follow-up"));
                       list.add(stb);
                    }
                   //list.add(withdrawaldao.getNumberOfHouseholdsWithdrawnPerCohort(householdQueryPart,"Loss to follow-up")); 
                }
                if(indicatorId.equalsIgnoreCase("hhtransferd"))
                {
                   String indicatorName=ind.getIndicatorForNumberOfHouseholdsWithdrawnDueToTransfer().getIndicatorName();
                    stb.setCategoryOptionCombo("household");
                    if(reporttype !=null && reporttype.equalsIgnoreCase("list"))
                    {
                        list.add(withdrawaldao.getListOfHouseholdsWithdrawnPerCohort(additionalQueryCriteria+genderQueryCriteria,"transfer"));
                        list.add(indicatorName);
                    }
                    else if(reporttype !=null && reporttype.equalsIgnoreCase("number"))
                    {
                       stb.setIndicatorId(indicatorId);
                       stb.setIndicatorName(indicatorName);
                       stb.setTotNoOfOvc(withdrawaldao.getNumberOfHouseholdsWithdrawnPerCohort(additionalQueryCriteria+genderQueryCriteria,"transfer"));
                       list.add(stb);
                    }
                   //list.add(withdrawaldao.getNumberOfHouseholdsWithdrawnPerCohort(householdQueryPart,"transfer")); 
                }
                if(indicatorId.equalsIgnoreCase("vcknowndeat"))
                {
                    String reasonWithdrawal="Known death";
                    String indicatorName=ind.getIndicatorForNumberOfOvcWithdrawnDueToKnownDeath().getIndicatorName();
                    stb.setCategoryOptionCombo("ovc");
                    if(reporttype !=null && reporttype.equalsIgnoreCase("list"))
                    {
                        if(gender.equalsIgnoreCase("Male"))
                        genderQueryCriteria=" and UPPER(ovc.gender)='MALE'";
                        else if(gender.equalsIgnoreCase("Female"))
                        genderQueryCriteria=" and UPPER(ovc.gender)='FEMALE'";
                        list.add(withdrawaldao.getListOfOvcWithdrawnPerCohort(additionalQueryCriteria+genderQueryCriteria,reasonWithdrawal));
                        list.add(indicatorName);
                    }
                    else if(reporttype !=null && reporttype.equalsIgnoreCase("number"))
                    {
                       genderQueryCriteria=" and UPPER(ovc.gender)='MALE'";
                       stb.setIndicatorId(indicatorId);
                       stb.setIndicatorName(indicatorName);
                       stb.setValue3(withdrawaldao.getNumberOfOvcWithdrawnPerCohort(additionalQueryCriteria+genderQueryCriteria,reasonWithdrawal));
                       genderQueryCriteria=" and UPPER(ovc.gender)='FEMALE'";
                       stb.setValue4(withdrawaldao.getNumberOfOvcWithdrawnPerCohort(additionalQueryCriteria+genderQueryCriteria,reasonWithdrawal));
                       stb.setTotNoOfOvc(withdrawaldao.getNumberOfOvcWithdrawnPerCohort(additionalQueryCriteria,reasonWithdrawal));
                       list.add(stb);
                    }
                   //list.add(withdrawaldao.getNumberOfOvcWithdrawnPerCohort(householdQueryPart,"Known death")); 
                }
                if(indicatorId.equalsIgnoreCase("ovcagingout"))
                {
                   String reasonWithdrawal="ageabove17";
                   String indicatorName=ind.getIndicatorForNumberOfOvcWithdrawnDueToAgingOut().getIndicatorName();
                    stb.setCategoryOptionCombo("ovc");
                    if(reporttype !=null && reporttype.equalsIgnoreCase("list"))
                    {
                        if(gender.equalsIgnoreCase("Male"))
                        genderQueryCriteria=" and UPPER(ovc.gender)='MALE'";
                        else if(gender.equalsIgnoreCase("Female"))
                        genderQueryCriteria=" and UPPER(ovc.gender)='FEMALE'";
                        list.add(withdrawaldao.getListOfOvcWithdrawnPerCohort(additionalQueryCriteria+genderQueryCriteria,reasonWithdrawal));
                        list.add(indicatorName);
                    }
                    else if(reporttype !=null && reporttype.equalsIgnoreCase("number"))
                    {
                       genderQueryCriteria=" and UPPER(ovc.gender)='MALE'";
                       stb.setIndicatorId(indicatorId);
                       stb.setIndicatorName(indicatorName);
                       stb.setValue3(withdrawaldao.getNumberOfOvcWithdrawnPerCohort(additionalQueryCriteria+genderQueryCriteria,reasonWithdrawal));
                       genderQueryCriteria=" and UPPER(ovc.gender)='FEMALE'";
                       stb.setValue4(withdrawaldao.getNumberOfOvcWithdrawnPerCohort(additionalQueryCriteria+genderQueryCriteria,reasonWithdrawal));
                       stb.setTotNoOfOvc(withdrawaldao.getNumberOfOvcWithdrawnPerCohort(additionalQueryCriteria,reasonWithdrawal));
                       list.add(stb);
                    }
                   //list.add(withdrawaldao.getNumberOfOvcWithdrawnPerCohort(householdQueryPart,"Age > 18")); 
                }
                if(indicatorId.equalsIgnoreCase("ovcmigrated"))
                {
                    String reasonWithdrawal="Migrated";
                    String indicatorName=ind.getIndicatorForNumberOfOvcWithdrawnDueToMigration().getIndicatorName();
                    stb.setCategoryOptionCombo("ovc");
                    if(reporttype !=null && reporttype.equalsIgnoreCase("list"))
                    {
                        if(gender.equalsIgnoreCase("Male"))
                        genderQueryCriteria=" and UPPER(ovc.gender)='MALE'";
                        else if(gender.equalsIgnoreCase("Female"))
                        genderQueryCriteria=" and UPPER(ovc.gender)='FEMALE'";
                        list.add(withdrawaldao.getListOfOvcWithdrawnPerCohort(additionalQueryCriteria+genderQueryCriteria,reasonWithdrawal));
                        list.add(indicatorName);
                    }
                    else if(reporttype !=null && reporttype.equalsIgnoreCase("number"))
                    {
                       genderQueryCriteria=" and UPPER(ovc.gender)='MALE'";
                       stb.setIndicatorId(indicatorId);
                       stb.setIndicatorName(indicatorName);
                       stb.setValue3(withdrawaldao.getNumberOfOvcWithdrawnPerCohort(additionalQueryCriteria+genderQueryCriteria,reasonWithdrawal));
                       genderQueryCriteria=" and UPPER(ovc.gender)='FEMALE'";
                       stb.setValue4(withdrawaldao.getNumberOfOvcWithdrawnPerCohort(additionalQueryCriteria+genderQueryCriteria,reasonWithdrawal));
                       stb.setTotNoOfOvc(withdrawaldao.getNumberOfOvcWithdrawnPerCohort(additionalQueryCriteria,reasonWithdrawal));
                       list.add(stb);
                    }
                   //list.add(withdrawaldao.getNumberOfOvcWithdrawnPerCohort(householdQueryPart,"Migrated")); 
                }
                if(indicatorId.equalsIgnoreCase("vclosttofup"))
                {
                    String reasonWithdrawal="Loss to follow-up";
                    String indicatorName=ind.getIndicatorForNumberOfOvcWithdrawnDueToLostToFollowup().getIndicatorName();
                    stb.setCategoryOptionCombo("ovc");
                    if(reporttype !=null && reporttype.equalsIgnoreCase("list"))
                    {
                        if(gender.equalsIgnoreCase("Male"))
                        genderQueryCriteria=" and UPPER(ovc.gender)='MALE'";
                        else if(gender.equalsIgnoreCase("Female"))
                        genderQueryCriteria=" and UPPER(ovc.gender)='FEMALE'";
                        list.add(withdrawaldao.getListOfOvcWithdrawnPerCohort(additionalQueryCriteria+genderQueryCriteria,reasonWithdrawal));
                        list.add(indicatorName);
                    }
                    else if(reporttype !=null && reporttype.equalsIgnoreCase("number"))
                    {
                       genderQueryCriteria=" and UPPER(ovc.gender)='MALE'";
                       stb.setIndicatorId(indicatorId);
                       stb.setIndicatorName(indicatorName);
                       stb.setValue3(withdrawaldao.getNumberOfOvcWithdrawnPerCohort(additionalQueryCriteria+genderQueryCriteria,reasonWithdrawal));
                       genderQueryCriteria=" and UPPER(ovc.gender)='FEMALE'";
                       stb.setValue4(withdrawaldao.getNumberOfOvcWithdrawnPerCohort(additionalQueryCriteria+genderQueryCriteria,reasonWithdrawal));
                       stb.setTotNoOfOvc(withdrawaldao.getNumberOfOvcWithdrawnPerCohort(additionalQueryCriteria,reasonWithdrawal));
                       list.add(stb);
                    }
                   //list.add(withdrawaldao.getNumberOfOvcWithdrawnPerCohort(householdQueryPart,"Loss to follow-up")); 
                }
                if(indicatorId.equalsIgnoreCase("vcgraduated"))
                {
                    String reasonWithdrawal="Graduated";
                    String indicatorName=ind.getIndicatorForNumberOfOvcGraduated().getIndicatorName();
                    stb.setCategoryOptionCombo("ovc");
                    if(reporttype !=null && reporttype.equalsIgnoreCase("list"))
                    {
                        if(gender.equalsIgnoreCase("Male"))
                        genderQueryCriteria=" and UPPER(ovc.gender)='MALE'";
                        else if(gender.equalsIgnoreCase("Female"))
                        genderQueryCriteria=" and UPPER(ovc.gender)='FEMALE'";
                        list.add(withdrawaldao.getListOfOvcWithdrawnPerCohort(additionalQueryCriteria+genderQueryCriteria,reasonWithdrawal));
                        list.add(indicatorName);
                    }
                    else if(reporttype !=null && reporttype.equalsIgnoreCase("number"))
                    {
                       genderQueryCriteria=" and UPPER(ovc.gender)='MALE'";
                       stb.setIndicatorId(indicatorId);
                       stb.setIndicatorName(indicatorName);
                       stb.setValue3(withdrawaldao.getNumberOfOvcWithdrawnPerCohort(additionalQueryCriteria+genderQueryCriteria,reasonWithdrawal));
                       genderQueryCriteria=" and UPPER(ovc.gender)='FEMALE'";
                       stb.setValue4(withdrawaldao.getNumberOfOvcWithdrawnPerCohort(additionalQueryCriteria+genderQueryCriteria,reasonWithdrawal));
                       stb.setTotNoOfOvc(withdrawaldao.getNumberOfOvcWithdrawnPerCohort(additionalQueryCriteria,reasonWithdrawal));
                       list.add(stb);
                    }
                   //list.add(withdrawaldao.getNumberOfOvcWithdrawnPerCohort(householdQueryPart,"graduation")); 
                }
                if(indicatorId.equalsIgnoreCase("vctransferd"))
                {
                    String reasonWithdrawal="transfer";
                    String indicatorName=ind.getIndicatorForNumberOfOvcWithdrawnDueToTransfer().getIndicatorName();
                    stb.setCategoryOptionCombo("ovc");
                    if(reporttype !=null && reporttype.equalsIgnoreCase("list"))
                    {
                        if(gender.equalsIgnoreCase("Male"))
                        genderQueryCriteria=" and UPPER(ovc.gender)='MALE'";
                        else if(gender.equalsIgnoreCase("Female"))
                        genderQueryCriteria=" and UPPER(ovc.gender)='FEMALE'";
                        list.add(withdrawaldao.getListOfOvcWithdrawnPerCohort(additionalQueryCriteria+genderQueryCriteria,reasonWithdrawal));
                        list.add(indicatorName);
                    }
                    else if(reporttype !=null && reporttype.equalsIgnoreCase("number"))
                    {
                       genderQueryCriteria=" and UPPER(ovc.gender)='MALE'";
                       stb.setIndicatorId(indicatorId);
                       stb.setIndicatorName(indicatorName);
                       stb.setValue3(withdrawaldao.getNumberOfOvcWithdrawnPerCohort(additionalQueryCriteria+genderQueryCriteria,reasonWithdrawal));
                       genderQueryCriteria=" and UPPER(ovc.gender)='FEMALE'";
                       stb.setValue4(withdrawaldao.getNumberOfOvcWithdrawnPerCohort(additionalQueryCriteria+genderQueryCriteria,reasonWithdrawal));
                       stb.setTotNoOfOvc(withdrawaldao.getNumberOfOvcWithdrawnPerCohort(additionalQueryCriteria,reasonWithdrawal));
                       list.add(stb);
                    }
                   //list.add(withdrawaldao.getNumberOfOvcWithdrawnPerCohort(householdQueryPart,"transfer")); 
                }
                if(indicatorId.equalsIgnoreCase("vctestedhiv"))
                {
                    String periodQuery=" and ovc.dateEnrollment between '"+util.getStartDate(dateParam)+"' and '"+util.getEndDate(dateParam)+"'";
                    additionalQueryCriteria=util.getHVIOrgUnitQuery(hheParams)+periodQuery;
                    String indicatorName=ind.getIndicatorForNumberOfOvcTestedForHIV().getIndicatorName();
                    stb.setCategoryOptionCombo("ovc");
                    if(reporttype !=null && reporttype.equalsIgnoreCase("list"))
                    {
                        if(gender.equalsIgnoreCase("Male"))
                        genderQueryCriteria=" and UPPER(ovc.gender)='MALE'";
                        else if(gender.equalsIgnoreCase("Female"))
                        genderQueryCriteria=" and UPPER(ovc.gender)='FEMALE'";
                        list.add(withdrawaldao.getListOfOvcWithdrawnPerCohort(additionalQueryCriteria+genderQueryCriteria,"transfer"));
                        list.add(indicatorName);
                    }
                    else if(reporttype !=null && reporttype.equalsIgnoreCase("number"))
                    {
                        String startDate=util.getStartDate(dateParam);
                        String endDate=util.getStartDate(dateParam);
                       genderQueryCriteria=" and UPPER(ovc.gender)='MALE'";
                       stb.setIndicatorId(indicatorId);
                       stb.setIndicatorName(indicatorName);
                       stb.setValue3(servicedao.getNumberOfOvcTestedHivPerCohort(additionalQueryCriteria+genderQueryCriteria,startDate,endDate));
                       genderQueryCriteria=" and UPPER(ovc.gender)='FEMALE'";
                       stb.setValue4(servicedao.getNumberOfOvcTestedHivPerCohort(additionalQueryCriteria+genderQueryCriteria,startDate,endDate));
                       stb.setTotNoOfOvc(stb.getValue3()+stb.getValue4());
                       list.add(stb);
                    }
                   //list.add(withdrawaldao.getNumberOfOvcWithdrawnPerCohort(householdQueryPart,"transfer")); 
                }
                
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        return list;
    }
}
