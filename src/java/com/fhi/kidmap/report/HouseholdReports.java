/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.Caregiver;
import com.fhi.nomis.nomisutils.DataEncryption;
import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.kidmap.business.HouseholdFollowupAssessment;
import com.fhi.kidmap.business.HouseholdService;
import com.fhi.kidmap.business.HouseholdVulnerabilityAssessment;
import com.fhi.kidmap.business.Wards;
import com.fhi.kidmap.dao.CaregiverDao;
import com.fhi.kidmap.dao.CaregiverDaoImpl;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.HouseholdEnrollmentDao;
import com.fhi.kidmap.dao.HouseholdEnrollmentDaoImpl;
import com.fhi.kidmap.dao.HouseholdFollowupAssessmentDao;
import com.fhi.kidmap.dao.HouseholdFollowupAssessmentDaoImpl;
import com.fhi.kidmap.dao.HouseholdServiceDao;
import com.fhi.kidmap.dao.HouseholdServiceDaoImpl;
import com.fhi.kidmap.dao.HouseholdVulnerabilityAssessmentDao;
import com.fhi.kidmap.dao.HouseholdVulnerabilityAssessmentDaoImpl;
import com.fhi.nomis.nomisutils.NomisConstant;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Siaka
 */
public class HouseholdReports implements Serializable
{
    //HouseholdVulnerabilityIndexDao hviDao=new HouseholdVulnerabilityIndexDaoImpl();
    HouseholdVulnerabilityAssessmentDao hvaDao=new HouseholdVulnerabilityAssessmentDaoImpl();
    DaoUtil util=new DaoUtil();
    AppUtility appUtil=new AppUtility();
    DataEncryption de=new DataEncryption();
    public List encryptCaregiverInformation(List cgiverList,HttpSession session)
    {
        List encryptedCaregiverList=de.encryptCaregiverInfo(cgiverList,session);
        return encryptedCaregiverList;
    }
    public List processCaregiverList(List list,HttpSession session)
    {
        List hviList=new ArrayList();
        List hheList=new ArrayList();
        List cgiverList=new ArrayList();
        List hhsList=new ArrayList();
        List idList=new ArrayList();
        Caregiver cgiver=null;

        try
        {
            HouseholdService hhs=null;
            for(Object s:list)
            {
                Object[] obj=(Object[])s;
                hhs=(HouseholdService)obj[2];
                //hhs.setCgiver((Caregiver)obj[1]);
                if(idList.contains(hhs.getCaregiverId()))
                continue;
                hheList.add((HouseholdEnrollment)obj[0]);
                cgiverList.add((Caregiver)obj[1]);
                idList.add(hhs.getCaregiverId());
                hhsList.add(hhs);
            }
            //System.err.println("cgiverList size is "+cgiverList.size());
            List encryptedCaregiverList=de.encryptCaregiverInfo(cgiverList,session);
            //System.err.println("encryptedCaregiverList size is "+encryptedCaregiverList.size());
            hhs=null;
            for(int i=0; i<encryptedCaregiverList.size(); i++)
            {
                cgiver=(Caregiver)encryptedCaregiverList.get(i);
                hhs=(HouseholdService)hhsList.get(i);
                hhs.setCgiver(cgiver);
                /*hhs.setCaregiverAge(cgiver.getCaregiverAge());
                hhs.setCaregiverFirstname(cgiver.getCaregiverFirstname());
                hhs.setCaregiverSurname(cgiver.getCaregiverLastName());
                hhs.setCareiverGender(hhs.getCaregiverGender());*/
                hhs.setCurrentHivStatus(cgiver.getCgiverHivStatus());
                cgiver.setDateOfEnrollment(appUtil.getDisplayDate(cgiver.getDateOfEnrollment()));
                //cgiver.setPartnerCode(util.getPartnerName(cgiver.getPartnerCode()));
                //cgiver.setDateModified(appUtil.getDisplayDate(cgiver.getDateModified()));
                hviList.add(hhs);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return hviList;
    }
    public List getCaregiverList(String additionalQueryCriteria,HttpSession session)
    {
        CaregiverDao cgiverdao=new CaregiverDaoImpl();
        List encryptedCgiverList=null;
        try
        {
            List list=cgiverdao.getListOfCaregivers(additionalQueryCriteria);
            encryptedCgiverList=de.encryptCaregiverInfo(list, session);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return encryptedCgiverList;
    }
    public List processHheList(List list,HttpSession session)
    {
        List hviList=new ArrayList();
        HouseholdEnrollment hhe=null;
        List encryptedHviList=new ArrayList();
        try
        {
            encryptedHviList.addAll(de.encryptHVIInfo(list,session));
            for(int i=0; i<encryptedHviList.size(); i++)
            {
                hhe=(HouseholdEnrollment)encryptedHviList.get(i);
                hhe.setDateOfAssessment(appUtil.getDisplayDate(hhe.getDateOfAssessment()));
                hhe.setPartnerCode(util.getPartnerName(hhe.getPartnerCode()));
                hhe.setDateOfEntry(appUtil.getDisplayDate(hhe.getDateOfEntry()));
                hviList.add(hhe);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return hviList;
    }
    public List processHhFollowupAssessmentList(List list,HttpSession session)
    {
        List hviList=new ArrayList();
        List hheList=new ArrayList();
        List hhfaList=new ArrayList();
        if(list !=null && !list.isEmpty())
        {
            for(Object s:list)
            {
                Object[] obj=(Object[])s;
                hheList.add(obj[0]);
                hhfaList.add(obj[1]);
            }
        }
        HouseholdEnrollment hhe=null;
        HouseholdFollowupAssessment hhfa=null;
        HouseholdVulnerabilityAssessment hhva=null;
       
        HouseholdVulnerabilityAssessmentDao hhvadao=new HouseholdVulnerabilityAssessmentDaoImpl();
        try
        {
            List encryptedHviList=de.encryptHVIInfo(hheList,session);
            for(int i=0; i<encryptedHviList.size(); i++)
            {
                hhe=(HouseholdEnrollment)encryptedHviList.get(i);
                hhfa=(HouseholdFollowupAssessment)hhfaList.get(i);
                
                hhfa.setHhFirstname(hhe.getHhFirstname());
                hhfa.setHhSurname(hhe.getHhSurname());
                hhfa.setHhGender(hhe.getHhGender());
                hhfa.setDateOfAssessment(appUtil.getDisplayDate(hhfa.getDateOfAssessment()));
                hhfa.setPartnerCode(util.getPartnerName(hhfa.getPartnerCode()));
                hhfa.setDateModified(appUtil.getDisplayDate(hhfa.getDateModified()));
                if(hhfa.getVulnerabilityScore() < NomisConstant.VULNERABLE_STARTVALUE)
                {
                     HouseholdFollowupAssessmentDao hhfadao=new HouseholdFollowupAssessmentDaoImpl();
                    hhva=(HouseholdVulnerabilityAssessment)hhvadao.getHouseholdVulnerabilityAssessment(hhfa.getHhUniqueId(), hhfa.getDateOfAssessment());
                    if(hhva !=null)
                    {
                        hhfa.setEducationLevel(hhva.getEducationLevel());
                        hhfa.setFoodSecurityAndNutrition(hhva.getFoodSecurityAndNutrition());
                        hhfa.setHealth(hhva.getHealth());
                        hhfa.setHhHeadship(hhva.getHhHeadship());
                        hhfa.setHhIncome(hhva.getHhIncome());
                        hhfa.setMeansOfLivelihood(hhva.getMeansOfLivelihood());
                        hhfa.setShelterAndHousing(hhva.getShelterAndHousing());
                        hhfa.setVulnerabilityScore(hhfadao.getFollowupVulnerabilityScore(hhfa));
                    }
                }
                //hhfa.setVulnerabilityStatus(hhfadao.);
                hviList.add(hhfa);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return hviList;
    }
    public List getListOfHouseholdsPerThematicAreaScore(String hvaProperty,int score,String queryCriteria)
    {
        System.err.println("hvaProperty is "+hvaProperty);
        List list=null;
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl(); 
        try
        {
            
            list=hvaDao.getListOfHouseholdsPerThematicAreaScore(hvaProperty, score, queryCriteria);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        //mainList.add(getHouseholdEnrollmentAndAssessmentInList(list));
        return list;
    }
    public List getListOfHouseholdsPerVulnerabilityStatus(int score,String queryCriteria)
    {
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl(); 
        List list=null;
        System.err.println("queryCriteria is "+queryCriteria);
        String[] rangeArray=getVulnerabilityScoreRange()[score].split("-");
        try
        {
            list=hhedao.getListOfHouseholdsByBaselineVulnerabilityStatus(queryCriteria, false, Integer.parseInt(rangeArray[0]), Integer.parseInt(rangeArray[1]));
            //list=hvaDao.getListOfHvaPerVulnerabilityStatus(queryCriteria, Integer.parseInt(rangeArray[0]), Integer.parseInt(rangeArray[1]));
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        //mainList.add(getHouseholdEnrollmentAndAssessmentInList(list));
        return list;
    }
    public List getHVAThematicAreaAnalysis(String queryCriteria)
    {
        String[] thematicAreas=getHVAThematicAreas();
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        List list=null;
        List mainList=new ArrayList();
        String[] propertyNames={"hhHeadship","health","educationLevel","shelterAndHousing","foodSecurityAndNutrition","meansOfLivelihood","hhIncome"};
        try
        {
            for(int i=0; i<propertyNames.length; i++)
            {
                list=new ArrayList();
                list.add(thematicAreas[i]);
                list.add(propertyNames[i]);
                for(int j=1; j<5; j++)
                {
                    list.add(hhedao.getHVAThematicAreaCount(propertyNames[i], j,queryCriteria));
                }
                mainList.add(list);
            }

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return mainList;
    }
    public String[] getHVAThematicAreas()
    {
        String[] thematicAreas={"Household headship","Health","Education Level (Household head)","Shelter and Housing","Food Security and Nutrition","Means of livelihood","Household income"};
        return thematicAreas;
    }
    public String[] getVulnerabilityScoreRange()
    {
        String[] vulnerabilityStatus={NomisConstant.NOTVULNERABLE_STARTVALUE+"-"+NomisConstant.NOTVULNERABLE_ENDVALUE,NomisConstant.VULNERABLE_STARTVALUE+"-"+NomisConstant.VULNERABLE_ENDVALUE,NomisConstant.MOREVULNERABLE_STARTVALUE+"-"+NomisConstant.MOREVULNERABLE_ENDVALUE,NomisConstant.MOSTVULNERABLE_STARTVALUE+"-"+NomisConstant.MOSTVULNERABLE_ENDVALUE};
        return vulnerabilityStatus;
    }
    public String[] getVulnerabilityRangeAndStatus()
    {
        String[] vulnerabilityStatus={NomisConstant.NOTASSESSED_VULNERABLE_STATUS,NomisConstant.NOTVULNERABLE_STARTVALUE+"-"+NomisConstant.NOTVULNERABLE_ENDVALUE,NomisConstant.VULNERABLE_STATUS,NomisConstant.VULNERABLE_STARTVALUE+"-"+NomisConstant.VULNERABLE_ENDVALUE,NomisConstant.MOREVULNERABLE_STATUS,NomisConstant.MOREVULNERABLE_STARTVALUE+"-"+NomisConstant.MOREVULNERABLE_ENDVALUE,NomisConstant.MOSTVULNERABLE_STATUS,NomisConstant.MOSTVULNERABLE_STARTVALUE+"-"+NomisConstant.MOSTVULNERABLE_ENDVALUE};
        return vulnerabilityStatus;
    }
    public List getVulnerabilityScoreAndStatus(String queryCriteria,boolean currentlyEnrolled)
    {
        List statusList=new ArrayList();
        List scoreList=new ArrayList();
        List mainList=new ArrayList();
        String[] rangeArray=null;
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        int score=0;
        String[] vulnerabilityRangeAndStatus=getVulnerabilityRangeAndStatus();
        try
        {
            scoreList.add(" ");
            scoreList.add("Vulnerability status");
            for(int i=0; i<vulnerabilityRangeAndStatus.length; i+=2)
            {
                scoreList.add(vulnerabilityRangeAndStatus[i]);
                rangeArray=vulnerabilityRangeAndStatus[i+1].split("-");
                score=hhedao.getNumberOfHouseholdsByBaselineVulnerabilityStatus(queryCriteria,currentlyEnrolled,Integer.parseInt(rangeArray[0]), Integer.parseInt(rangeArray[1]));
                //score=hvaDao.getNoOfHVIPerScoreRange(queryCriteria, Integer.parseInt(rangeArray[0]), Integer.parseInt(rangeArray[1]));
                scoreList.add(score);
            }
            mainList.add(scoreList);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return mainList;
    }
    public List getHouseholdEnrollmentAndAssessmentInList(List hheList)
    {
        List hhSubList=null;
        List hhMainList=new ArrayList();
        if(hheList !=null && !hheList.isEmpty())
        {
            HouseholdEnrollment hhe=null;
            HouseholdVulnerabilityAssessment hva=null;
            HouseholdVulnerabilityAssessmentDao hvaDao=new HouseholdVulnerabilityAssessmentDaoImpl();
            try
            {
                for(Object s:hheList)
                {
                   hhe=(HouseholdEnrollment)s;
                   if(hhe !=null)
                   {
                       hhSubList=new ArrayList();
                        hva=hvaDao.getHouseholdVulnerabilityAssessment(hhe.getHhUniqueId(), hhe.getDateOfAssessment());
                        if(hva !=null)
                        {
                            hhSubList.add(hhe);
                            hhSubList.add(hva);
                            hhMainList.add(hhSubList);
                        }
                   }
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        return hhMainList;
    }
    public void getHVIRecords(HttpServletRequest request,String[] param,String sortOrder)
    {
        HttpSession session=request.getSession();
        List list=new ArrayList();
        List hheList=new ArrayList();
        HouseholdEnrollmentDao hheDao=new HouseholdEnrollmentDaoImpl();
        String hviAgeParam=null;
        //String[] dateParam={param[6],param[7],param[8],param[9]};
        try
        {
            String additionalQueryCriteria=util.getHVIReportCriteria(param);
            list=hheDao.getListOfHouseholdEnrollment(additionalQueryCriteria,sortOrder);
            hheList=processHheList(list,session);
            //hheList=list;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        if(param.length > 10)
        hviAgeParam="Enrolled between start of "+appUtil.getMonthAsString(Integer.parseInt(param[6]))+" "+param[7]+" and end of "+appUtil.getMonthAsString(Integer.parseInt(param[8]))+" "+param[9];
        session.setAttribute("hviRecords", hheList);
        session.setAttribute("hviParam", param);
        session.setAttribute("hviAgeParam", hviAgeParam);
    }
    public void getHhRecords(HttpServletRequest request,String[] param,String sortOrder)
    {
        HttpSession session=request.getSession();
        List list=new ArrayList();
        List hheList=new ArrayList();
        HouseholdEnrollmentDao hheDao=new HouseholdEnrollmentDaoImpl();
        try
        {
            String additionalQueryCriteria=util.getHVIOrgUnitQuery(param);
            list=hheDao.getListOfHouseholdEnrollment(additionalQueryCriteria,sortOrder);
            hheList=processHheList(list,session);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        session.setAttribute("hviRecords", hheList);
        session.setAttribute("hviParam", param);
    }
    public List getHouseholdServiceRecordsForMultiQuery(HttpServletRequest request,String additionalQuery, boolean currentlyEnrolled)
    {
        List list=new ArrayList();
        List hviList=new ArrayList();
        HouseholdService hhs=null;
        HouseholdServiceDao hvisDao=new HouseholdServiceDaoImpl();
        //String[] dateParam={param[6],param[7],param[8],param[9]};
        try
        {
            //String additionalQueryCriteria=util.getHVIServiceReportCriteria(param);
            list=hvisDao.getHouseholdServiceList(additionalQuery);
            HouseholdEnrollment hvi=null;
            HouseholdEnrollmentDao hviDao=new HouseholdEnrollmentDaoImpl();
            CaregiverDao cgiverDao=new CaregiverDaoImpl();
            Caregiver cgiver=null;
            for(Object obj:list)
            {
                hhs=(HouseholdService)obj;

                if(hhs.getServiceRecipientType().equalsIgnoreCase("householdhead"))
                {
                    hvi=(HouseholdEnrollment)hviDao.getHouseholdEnrollment(hhs.getCaregiverId());
                    if(hvi !=null)
                    {
                        if(currentlyEnrolled && (hvi.getWithdrawnFromProgram() !=null && hvi.getWithdrawnFromProgram().equalsIgnoreCase("Yes")))
                        {
                            continue;
                        }
                        cgiver=new Caregiver();
                        
                        cgiver.setHhUniqueId(hvi.getHhUniqueId());
                        cgiver.setCaregiverFirstname(hvi.getHhFirstname());
                        cgiver.setCaregiverLastName(hvi.getHhSurname());
                        cgiver.setCaregiverAge(hvi.getCurrentAge());
                        cgiver.setCaregiverGender(hvi.getHhGender());
                        cgiver.setCgiverHivStatus(hvi.getActiveHivStatus().getHivStatus());
                        hhs.setCgiver(cgiver);
                        
                    }
                    else
                    {
                        cgiver=cgiverDao.getCaregiverByCaregiverId(hhs.getCaregiverId());
                        if(cgiver !=null)
                        {
                            if(currentlyEnrolled && (cgiver.getWithdrawnFromProgram() !=null && cgiver.getWithdrawnFromProgram().equalsIgnoreCase("Yes")))
                            {
                                continue;
                            }
                            hhs.setCgiver(cgiver);
                            
                        }
                    }
                }
                else
                {
                    cgiver=cgiverDao.getCaregiverByCaregiverId(hhs.getCaregiverId());
                    if(cgiver !=null)
                    {
                        if(currentlyEnrolled && (cgiver.getWithdrawnFromProgram() !=null && cgiver.getWithdrawnFromProgram().equalsIgnoreCase("Yes")))
                        {
                            continue;
                        }
                        hhs.setCgiver(cgiver); 
                    }
                }
                hhs.setServiceDate(appUtil.getDisplayDate(hhs.getServiceDate()));
                hhs.setDateOfEntry(appUtil.getDisplayDate(hhs.getDateOfEntry()));
                hviList.add(hhs);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return hviList;
    }
    public void getHVIServiceRecords(HttpServletRequest request,String[] param)
    {
        List list=new ArrayList();
        List hviList=new ArrayList();
        HouseholdService hhs=null;
        HouseholdServiceDao hvisDao=new HouseholdServiceDaoImpl();
        String[] dateParam={param[6],param[7],param[8],param[9]};
        String reportFilter="All";
        if(param.length>14)
        reportFilter=param[14];
        String reportFilterQuery="";
        if(reportFilter !=null && !reportFilter.equalsIgnoreCase("All"))
        reportFilterQuery=" and hhs.volunteerName like '%"+reportFilter+"%'";
        try
        {
            String additionalQueryCriteria=util.getHVIServiceReportCriteria(param);
            list=hvisDao.getHouseholdServiceList(additionalQueryCriteria+reportFilterQuery);
            HouseholdEnrollment hvi=null;
            HouseholdEnrollmentDao hviDao=new HouseholdEnrollmentDaoImpl();
            CaregiverDao cgiverDao=new CaregiverDaoImpl();
            Caregiver cgiver=null;
            for(Object obj:list)
            {
                hhs=(HouseholdService)obj;

                if(hhs.getServiceRecipientType().equalsIgnoreCase("householdhead"))
                {
                    hvi=(HouseholdEnrollment)hviDao.getHouseholdEnrollment(hhs.getCaregiverId());
                    if(hvi !=null)
                    {
                        cgiver=new Caregiver();
                        
                        cgiver.setHhUniqueId(hvi.getHhUniqueId());
                        cgiver.setCaregiverFirstname(hvi.getHhFirstname());
                        cgiver.setCaregiverLastName(hvi.getHhSurname());
                        cgiver.setCaregiverAge(hvi.getCurrentAge());
                        cgiver.setCaregiverGender(hvi.getHhGender());
                        cgiver.setCgiverHivStatus(hvi.getActiveHivStatus().getHivStatus());
                        hhs.setCgiver(cgiver);
                        //cgiver.(hvi.getCommunityCode());
                        /*Wards ward=util.getWardByorgCode(hvi.getCommunityCode());
                        if(ward !=null)
                        hhs.setCaregiverCommunity(ward.getWard_name());*/
                        //System.err.println("hvi.getCommunityCode() in hhr.getHVIServiceRecords is "+hvi.getCommunityCode());
                    }
                    else
                    {
                        cgiver=cgiverDao.getCaregiverByCaregiverId(hhs.getCaregiverId());
                        if(cgiver !=null)
                        {
                            hhs.setCgiver(cgiver);
                            /*hhs.setCaregiverFirstname(cgiver.getCaregiverFirstname());
                            hhs.setCaregiverSurname(cgiver.getCaregiverLastName());
                            hhs.setCaregiverAge(cgiver.getCurrentAge());
                            hhs.setCareiverGender(cgiver.getCaregiverGender());
                            hhs.setCaregiverHivStatus(cgiver.getActiveHivStatus().getHivStatus());
                            hhs.setCaregiverCommunity(cgiver.getHhe().getCommunityCode());
                            Wards ward=util.getWardByorgCode(cgiver.getHhe().getCommunityCode());
                            if(ward !=null)
                            hhs.setCaregiverCommunity(ward.getWard_name());*/
                            //System.err.println("cgiver.getHhe().getCommunityCode() in hhr.getHVIServiceRecords is "+cgiver.getHhe().getCommunityCode());
                        }
                    }
                }
                else
                {
                    cgiver=cgiverDao.getCaregiverByCaregiverId(hhs.getCaregiverId());
                    if(cgiver !=null)
                    {
                        hhs.setCgiver(cgiver);
                        /*hhs.setCaregiverFirstname(cgiver.getCaregiverFirstname());
                        hhs.setCaregiverSurname(cgiver.getCaregiverLastName());
                        hhs.setCaregiverAge(cgiver.getCurrentAge());
                        hhs.setCareiverGender(cgiver.getCaregiverGender());
                        hhs.setCaregiverHivStatus(cgiver.getActiveHivStatus().getHivStatus());
                        hhs.setCaregiverCommunity(cgiver.getHhe().getCommunityCode());
                        Wards ward=util.getWardByorgCode(cgiver.getHhe().getCommunityCode());
                        if(ward !=null)
                        hhs.setCaregiverCommunity(ward.getWard_name());*/
                        //System.err.println("cgiver.getHhe().getCommunityCode() in hhr.getHVIServiceRecords is "+cgiver.getHhe().getCommunityCode());
                    }
                }
                hhs.setServiceDate(appUtil.getDisplayDate(hhs.getServiceDate()));
                hhs.setDateOfEntry(appUtil.getDisplayDate(hhs.getDateOfEntry()));
                hviList.add(hhs);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        String partner="Partner: "+util.getPartnerName(param[10]);
        String hhsAgeParam="Served between start of "+appUtil.getMonthAsString(Integer.parseInt(param[6]))+" "+param[7]+" and end of "+appUtil.getMonthAsString(Integer.parseInt(param[8]))+" "+param[9];
        request.setAttribute("hhsRecords", hviList);
        request.setAttribute("hhsParam", param);
        request.setAttribute("hhsAgeParam", hhsAgeParam);
        request.setAttribute("hhsReportPartner", partner);
    }
    public void getHhFollowupAssessmentRecords(HttpServletRequest request,String[] param)
    {
        HttpSession session=request.getSession();
        List list=new ArrayList();
        List hhfaList=new ArrayList();
        
        HouseholdFollowupAssessmentDao hhfDao=new HouseholdFollowupAssessmentDaoImpl();
        String[] dateParam={param[6],param[7],param[8],param[9]};
        try
        {
            String additionalQueryCriteria=util.getHhFollowupAssessmentReportCriteria(param);
            list=hhfDao.getListOfAssessments(additionalQueryCriteria);
            hhfaList=processHhFollowupAssessmentList(list,session);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        String hviPeriodParam="Assessed between start of "+appUtil.getMonthAsString(Integer.parseInt(param[6]))+" "+param[7]+" and end of "+appUtil.getMonthAsString(Integer.parseInt(param[8]))+" "+param[9];
        session.setAttribute("hhfaRecords", hhfaList);
        session.setAttribute("hhfaParam", param);
        session.setAttribute("hhfaPeriodParam", hviPeriodParam);
    }
}
