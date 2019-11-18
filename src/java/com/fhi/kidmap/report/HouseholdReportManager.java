/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report;

import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.kidmap.business.HouseholdVulnerabilityAssessment;
import com.fhi.kidmap.dao.DaoUtil;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author smomoh
 */
public class HouseholdReportManager 
{
    DaoUtil util=new DaoUtil();
   public HouseholdEnrollment getHouseholdBaselineData(String hhUniqueId)
   {
       HouseholdEnrollment hhe=null;
       try
       {
            hhe=util.getHouseholdEnrollmentDaoInstance().getHouseholdEnrollment(hhUniqueId);
            if(hhe ==null)
            {
                hhe=new HouseholdEnrollment();
            }
       }
       catch(Exception ex)
       {
           ex.printStackTrace();
       }
       return hhe;
   }
   public HouseholdVulnerabilityAssessment getBaselineAssessment(String hhUniqueId)
   {
       HouseholdVulnerabilityAssessment hva=null;
       try
       {
            hva=util.getHouseholdVulnerabilityAssessmentDaoInstance().getBaselineAssessment(hhUniqueId);
            if(hva ==null)
            {
                hva=new HouseholdVulnerabilityAssessment();
            }
       }
       catch(Exception ex)
       {
           ex.printStackTrace();
       }
       return hva;
   }
   public List getHouseholdFollowup(String hhUniqueId)
   {
       List mainList=new ArrayList();
       try
       {
            List list=util.getHouseholdFollowupAssessmentDaoInstance().getFollowupAssessmentsAscOrder(hhUniqueId);
            if(list !=null && !list.isEmpty())
            {
                mainList.addAll(list);
            }
       }
       catch(Exception ex)
       {
           ex.printStackTrace();
       }
       return mainList;
   }
   public List getServicesProvidedToHouseholdHead(String hhUniqueId)
   {
       List mainList=new ArrayList();
       try
       {
            List list=util.getHouseholdServiceDaoInstance().getAllServicesPerCaregiverOrderdByServiceDate(hhUniqueId,"asc");
            if(list !=null && !list.isEmpty())
            {
                mainList.addAll(list);
            }
       }
       catch(Exception ex)
       {
           ex.printStackTrace();
       }
       return mainList;
   }
   public List getAllCaregiversInHousehold(String hhUniqueId)
   {
       List mainList=new ArrayList();
       try
       {
            List list=util.getCaregiverDaoInstance().getListOfCaregiversFromSameHousehold(hhUniqueId);
            if(list !=null && !list.isEmpty())
            {
                mainList.addAll(list);
            }
       }
       catch(Exception ex)
       {
           ex.printStackTrace();
       }
       return mainList;
   }
   public List getAllOvcInHousehold(String hhUniqueId)
   {
       List mainList=new ArrayList();
       try
       {
            List list=util.getOvcDaoInstance().getOvcListPerHousehold(hhUniqueId);
            if(list !=null && !list.isEmpty())
            {
                mainList.addAll(list);
            }
       }
       catch(Exception ex)
       {
           ex.printStackTrace();
       }
       return mainList;
   }
}
