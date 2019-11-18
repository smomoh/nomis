/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomis.upgrade;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.Caregiver;
import com.fhi.nomis.nomisutils.DatabaseUtilities;
import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.kidmap.business.HouseholdVulnerabilityAssessment;
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.dao.CaregiverDao;
import com.fhi.kidmap.dao.CaregiverDaoImpl;
import com.fhi.kidmap.dao.CompatibilityHviDao;
import com.fhi.kidmap.dao.CompatibilityHviDaoImpl;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.HouseholdEnrollmentDao;
import com.fhi.kidmap.dao.HouseholdEnrollmentDaoImpl;
import com.fhi.kidmap.dao.HouseholdServiceDao;
import com.fhi.kidmap.dao.HouseholdServiceDaoImpl;
import com.fhi.kidmap.dao.HouseholdVulnerabilityAssessmentDao;
import com.fhi.kidmap.dao.HouseholdVulnerabilityAssessmentDaoImpl;
import com.fhi.kidmap.dao.OvcDao;
import com.fhi.kidmap.dao.OvcDaoImpl;
import com.nomis.databasemanagement.DatabaseCleanup;
import ImportExport.XmlProcessor;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Siaka
 */
public class UpgradeManager implements Serializable
{
    String currentDate=null;
    AppUtility appUtils=new AppUtility();
    public UpgradeManager()
    {
        currentDate=appUtils.getCurrentDate();
    }
    public void upgradeVersion20()
    {

    }public void upgradeVersion21()
    {

    }
    public void populateHouseholdEnrollmentAndAssessment()
    {
        String version=XmlProcessor.getTagValue(appUtils.getXmlConfigurationFile(), "NomisInfo","version");
        if(version !=null && version.equalsIgnoreCase("2.4"))
        return;
        CompatibilityHviDao hviDao=new CompatibilityHviDaoImpl();
        HouseholdEnrollmentDao hheDao=new HouseholdEnrollmentDaoImpl();
        HouseholdVulnerabilityAssessmentDao hvaDao=new HouseholdVulnerabilityAssessmentDaoImpl();
        CompatibilityHvi hvi=null;
        HouseholdEnrollment hhe=null;
        HouseholdEnrollment duplicateHhe=null;
        HouseholdVulnerabilityAssessment hva=null;
        HouseholdVulnerabilityAssessment duplicateHva=null;
        int totalRecords=0;
        try
        {
            int noOfRecordsInHhEnrollment=hheDao.getNoOfHouseholdsEnrolled();
            int noOfHhInHvi=hviDao.getNoOfHouseholdsEnrolled();
            //System.err.println("inside populateHouseholdEnrollmentAndAssessment() ");
            if(noOfHhInHvi<1)
            {
                System.err.println("inside populateHouseholdEnrollmentAndAssessment() noOfHhInHvi <1");
                if(noOfHhInHvi==-1)
                {
                    /*This is an old database, update field length*/
                    DatabaseUtilities dbUtils=new DatabaseUtilities();
                    dbUtils.updateEnrollmentForCompatibility();
                }
                //generateHouseholdFromCompatibilityOvc();
            }
            else
            {
                if(noOfRecordsInHhEnrollment < 1)
                {
                    AppUtility appUtil=new AppUtility();
                    int count=0;
                    int updateCount=0;
                    List orgList=hviDao.getDistinctOrgCodes();
                    //System.err.println("inside populateHouseholdEnrollmentAndAssessment() orgList size is "+orgList.size());
                    if(orgList !=null)
                    {
                        List list=null;
                        String additionalQuery=" ";
                        for(int i=0; i<orgList.size(); i++)
                        {
                            additionalQuery=" and hvi.orgCode = '"+((String)orgList.get(i))+"'";
                            list=hviDao.getHouseholdVulnerabilityIndexList(additionalQuery);
                            System.err.println("inside populateHouseholdEnrollmentAndAssessment() list size is "+list.size());
                            if(list !=null)
                            {
                                totalRecords+=list.size();
                                String hhFirstName="None";
                                String hhSurName="None";
                                String caregiverFirstname="Unknown";
                                String caregiverSurname="Unknown";
                                //System.err.println("inside populateHouseholdEnrollmentAndAssessment() orgList !=null");
                                int vulnerabilityScore=0;
                                for(int j=0; j<list.size(); j++)
                                {
                                    hhFirstName="None";
                                    hhSurName="None";
                                    hvi=(CompatibilityHvi)list.get(j);
                                    duplicateHhe=hheDao.getHouseholdEnrollment(hvi.getHhUniqueId());
                                    if(duplicateHhe==null)
                                    {
                                        hhe=new HouseholdEnrollment();
                                        if(hvi.getHhFirstname() !=null && appUtil.isString(hvi.getHhFirstname()))
                                        hhFirstName=hvi.getHhFirstname();
                                        if(hvi.getHhSurname() !=null && appUtil.isString(hvi.getHhSurname()))
                                        hhSurName=hvi.getHhSurname();
                                        System.err.println("hhFirstName is "+hhFirstName+" and hhSurName is "+hhSurName);
                                        if(hhFirstName==null)
                                        hhFirstName="None";
                                        if(hhSurName==null)
                                        hhSurName="None";
                                        if(hvi.getCaregiverFirstname() !=null && appUtil.isString(hvi.getCaregiverFirstname()))
                                        caregiverFirstname=hvi.getCaregiverFirstname();
                                        if(hvi.getCaregiverSurname() !=null && appUtil.isString(hvi.getCaregiverSurname()))
                                        caregiverSurname=hvi.getCaregiverSurname();
                                        if(hhFirstName.trim().equalsIgnoreCase("none") && hhSurName.trim().equalsIgnoreCase("none"))
                                        {
                                            if(!caregiverFirstname.equalsIgnoreCase("Unknown") && appUtil.isString(caregiverFirstname))
                                            hhFirstName=caregiverFirstname;
                                            if(!caregiverSurname.trim().equalsIgnoreCase("Unknown")&& appUtil.isString(caregiverSurname))
                                            hhSurName=caregiverSurname;
                                        }
                                        /*If the household head is the caregiver, then they should have the same gender*/
                                        if(hhFirstName.trim().equalsIgnoreCase(caregiverFirstname.trim()) && hhSurName.trim().equalsIgnoreCase(caregiverSurname.trim()))
                                        hhe.setHhGender(hvi.getCaregiverGender());
                                        else
                                        hhe.setHhGender("Male");
                                        
                                        hhe.setAddress(hvi.getAddress());
                                        hhe.setCommunityCode(hvi.getCommunityCode());
                                        hhe.setCurrentAge(hvi.getCurrentAge());
                                        hhe.setDateOfAssessment(hvi.getDateOfAssessment());
                                        hhe.setDateOfEntry(hvi.getDateOfEntry());
                                        hhe.setEligibleForEnrollment(hvi.getEligibleForEnrollment());
                                        hhe.setHhAge(hvi.getCaregiverAge());
                                        hhe.setHhFirstname(hhFirstName);
                                        hhe.setHhSurname(hhSurName);
                                        hhe.setHhUniqueId(hvi.getHhUniqueId());
                                        hhe.setLgaCode(hvi.getLgaCode());
                                        //if(hvi.)
                                        
                                        hhe.setMaritalStatus(hvi.getMaritalStatus());
                                        hhe.setNoOfChildrenInhh(hvi.getNoOfChildrenInhh());
                                        hhe.setNoOfPeopleInhh(hvi.getNoOfPeopleInhh());
                                        hhe.setOccupation(hvi.getOccupation());
                                        hhe.setOrgCode(hvi.getOrgCode());
                                        hhe.setPartnerCode(hvi.getPartnerCode());
                                        hhe.setPhone(hvi.getPhone());
                                        hhe.setRecordedBy(hvi.getRecordedBy());
                                        hhe.setStateCode(hvi.getStateCode());
                                        hheDao.saveHouseholdEnrollment(hhe);
                                    }
                                    //check for duplicate hva
                                    duplicateHva=hvaDao.getHouseholdVulnerabilityAssessment(hvi.getHhUniqueId(), hvi.getDateOfAssessment());
                                    if(duplicateHva==null)
                                    {
                                        hva=new HouseholdVulnerabilityAssessment();
                                        hva.setAssessmentNo(1);
                                        hva.setDateOfAssessment(hvi.getDateOfAssessment());
                                        hva.setDateOfEntry(hvi.getDateOfEntry());
                                        hva.setDesignation(hvi.getDesignation());
                                        hva.setEducationLevel(hvi.getEducationLevel());
                                        hva.setFoodSecurityAndNutrition(hvi.getFoodSecurityAndNutrition());
                                        hva.setHealth(hvi.getHealth());
                                        hva.setHhHeadship(hvi.getHhHeadship());
                                        hva.setHhIncome(hvi.getHhIncome());
                                        hva.setMeansOfLivelihood(hvi.getMeansOfLivelihood());
                                        hva.setShelterAndHousing(hvi.getShelterAndHousing());
                                        hva.setHhUniqueId(hvi.getHhUniqueId());
                                        hva.setNameOfAssessor(hvi.getNameOfAssessor());
                                        hva.setRecordedBy(hvi.getRecordedBy());
                                        vulnerabilityScore=hvaDao.getHouseholdVulnerabilityScore(hva);
                                        hva.setVulnerabilityScore(vulnerabilityScore);
                                        hvaDao.saveHouseholdVulnerabilityAssessment(hva);

                                        //System.err.println("HH assessment saved -------- ");
                                    }
                                    else
                                    {
                                        hva.setId(duplicateHva.getId());
                                        if(hva.getVulnerabilityScore() >= duplicateHva.getVulnerabilityScore())
                                        hvaDao.updateHouseholdVulnerabilityAssessmentByuniqueIdAndDate(hva);
                                        updateCount++;
                                    }
                                    count++;
                                    //System.err.println("Household records number "+count+" saved in new format. updateCount is "+updateCount+", totalRecords is "+totalRecords);
                                }
                            }
                            System.err.println("Household records number "+count+" saved in new format. totalRecords is "+totalRecords);
                        }
                        //updateCaregiverInfoAndEnrollmentForCompatibility();
                    }
                }
            }
            updateCaregiverInfoAndEnrollmentForCompatibility();
            updateHouseholdServiceForCompatibility();
        }
        catch(Exception ex)
        {
            ex.getMessage();
        }
    }
    public void updateCaregiverInfoAndEnrollmentForCompatibility() throws Exception
    {
        DaoUtil util=new DaoUtil();
        DatabaseUtilities dbUtils=new DatabaseUtilities();
        if(!dbUtils.tableExists("HHVULNERABILITYINDEX"))
        return;
        CompatibilityHviDao hvaDao=new CompatibilityHviDaoImpl();
        CaregiverDao cgiverDao=new CaregiverDaoImpl();
        Caregiver cgiver=null;
        Caregiver duplicateCgiver=null;
        CompatibilityHvi chva=null;
        OvcDao ovcDao=new OvcDaoImpl();
        Ovc ovc=null;
        try
        {
            List list=ovcDao.getOvcWithNoCaregiverId();
            System.err.println("Number of Ovc without caregiver Id is "+list.size());
            if(list !=null && !list.isEmpty())
            {
                String caregiverId=null;
                String caregiverFirstname=null;
                String caregiverSurname=null;
                for(int i=0; i<list.size(); i++)
                {
                    ovc=(Ovc)list.get(i);
                    String hhUniqueId=null;
                    if(ovc.getOvcId() !=null && ovc.getOvcId().indexOf("/") !=-1)
                    {
                        System.err.println("OVC and caregiver Id is "+ovc.getOvcId()+" "+ovc.getCaregiverId());
                        hhUniqueId=ovc.getOvcId().substring(0, ovc.getOvcId().lastIndexOf("/"));
                        chva=(CompatibilityHvi)hvaDao.getCompatibleHouseholdVulnerabilityIndex(hhUniqueId);
                        cgiver=new Caregiver();
                        if(chva !=null)
                        {
                            caregiverFirstname=chva.getCaregiverFirstname();
                            caregiverSurname=chva.getCaregiverSurname();
                            if(caregiverFirstname==null)
                            caregiverFirstname=chva.getHhFirstname();
                            if(caregiverSurname==null)
                            caregiverSurname=chva.getHhSurname();
                            if(caregiverFirstname==null)
                            caregiverFirstname="Unknown";
                            if(caregiverSurname==null)
                            caregiverSurname="Unknown";
                            cgiver.setCaregiverAddress(chva.getAddress());
                            cgiver.setCaregiverAge(chva.getCaregiverAge());
                            cgiver.setCaregiverFirstname(caregiverFirstname);
                            cgiver.setCaregiverLastName(caregiverSurname);
                            if(chva.getCaregiverGender() !=null)
                            cgiver.setCaregiverGender(chva.getCaregiverGender());
                            else
                            cgiver.setCaregiverGender("Female");
                            cgiver.setCaregiverOccupation(chva.getOccupation());
                            cgiver.setCaregiverMaritalStatus(chva.getMaritalStatus());
                            cgiver.setCaregiverPhone(chva.getPhone());
                            cgiver.setDateOfEnrollment(ovc.getDateEnrollment());
                            cgiver.setDateModified(currentDate);
                            //cgiver.setHouseholdhead(false);
                            cgiver.setCurrentAge(util.getCurrentAge(chva.getDateOfAssessment(), chva.getCaregiverAge(),"Year"));
                            duplicateCgiver=cgiverDao.getCaregiverByName(hhUniqueId, chva.getCaregiverFirstname(),chva.getCaregiverSurname());
                            if(duplicateCgiver==null)
                            {
                                caregiverId=cgiverDao.generateCaregiverId(hhUniqueId);
                                cgiver.setCaregiverId(caregiverId);
                                cgiver.setHhUniqueId(hhUniqueId);
                                cgiverDao.saveCaregiver(cgiver);
                                System.err.println("Adding caregiver "+cgiver.getCaregiverFirstname()+" "+cgiver.getCaregiverLastName());
                            }
                            else
                            caregiverId=duplicateCgiver.getCaregiverId();  
                        }
                        else
                        caregiverId=getCaregiverId(hhUniqueId);
                        ovc.setCaregiverId(caregiverId);
                        ovc.setHhUniqueId(hhUniqueId);
                        ovc=util.getOvcWithCurrentAge(ovc);
                        //ovc.setCurrentAge(util.getCurrentAge(ovc));
                        ovcDao.updateOvc(ovc,true,true);
                        System.err.println("OVC at "+i+" has caregiverId: "+ovc.getCaregiverId());
                    }
                }
            }
        }
        catch(Exception ex)
        {
            //ex.printStackTrace();
            throw new Exception();
        }
        
    }
    public void updateHouseholdServiceForCompatibility()
    {
        HouseholdServiceDao hhsDao=new HouseholdServiceDaoImpl();
        try
        {
            hhsDao.setCaregiverIdAndRecipientTypeInHhs();
            hhsDao.fixHhUniqueIdInHouseholdService();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public String generateNewOvcIdFromOldId(String oldOvcId)
    {
        DaoUtil util=new DaoUtil();
        String newId=null;
        try
        {
            if(oldOvcId !=null && oldOvcId.indexOf("/") !=-1)
            {
                oldOvcId=oldOvcId.replace(" ", "");
                oldOvcId=oldOvcId.trim();
                newId=util.generateNewIdFromOldId(oldOvcId);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return newId;
    }
    public void changeOvcId(CompatibilityOvc covc)
    {
        OvcDao dao=new OvcDaoImpl();
        String oldOvcId=covc.getOvcId();
        if(oldOvcId==null || oldOvcId.indexOf("/")==-1)
        return;
        String newId=generateNewOvcIdFromOldId(oldOvcId);
        try
        {
            if(newId !=null)
            dao.changeOvcId(oldOvcId, newId);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void generateHouseholdFromCompatibilityOvc()
    {
        System.err.println("inside generateHouseholdFromCompatibilityOvc() ");
        AppUtility appUtil=new AppUtility();
        DaoUtil util=new DaoUtil();
        OvcDao dao=new OvcDaoImpl();
        int count=1;
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        HouseholdVulnerabilityAssessmentDao hvaDao=new HouseholdVulnerabilityAssessmentDaoImpl();
        CompatibilityOvc covc=null;
        HouseholdEnrollment hhe=null;
        HouseholdVulnerabilityAssessment hva=null;
        try
        {
            List list=dao.getDistinctCboCodeFromCompatibilityOvc();
            System.err.println("dao.getDistinctCboCodeFromCompatibilityOvc() size is "+list.size());
            List cOvcList=null;
            if(list !=null && !list.isEmpty())
            {
                System.err.println("inside generateHouseholdFromCompatibilityOvc() list !=null");
                String cboCode=null;
                String hhUniqueId=null;
                String hhFirstName="Unknown";
                String hhSurname="Unknown";

                for(Object obj:list)
                {
                    cboCode=(String)obj;
                    cOvcList=dao.getListOfCompatibilityOvc(cboCode);
                    if(cOvcList !=null && !cOvcList.isEmpty())
                    {
                        System.err.println("inside generateHouseholdFromCompatibilityOvc() cOvcList !=null");
                        int counter=0;
                        for(Object s:cOvcList)
                        {
                            if(counter > 50)
                            break;
                            covc=(CompatibilityOvc)s;
                            hhUniqueId=covc.getOvcId();
                            if(hhUniqueId !=null && hhUniqueId.indexOf("/") !=-1)
                            {
                                String[] hhUniqueIdArray=hhUniqueId.split("/");
                                if(hhUniqueIdArray.length>4)
                                {
                                    counter++;
                                    continue;
                                }
                            }
                            else
                            {
                                continue;
                            }
                            hhe=hhedao.getHouseholdEnrollment(hhUniqueId);
                            if(hhe==null)
                            {
                                hhe=new HouseholdEnrollment();
                                hhe.setStateCode(covc.getState());
                                hhe.setLgaCode(covc.getLga());
                                hhe.setOrgCode(covc.getCboCode());
                                hhe.setCommunityCode(covc.getWard());
                                hhe.setHhUniqueId(hhUniqueId);
                                if(covc.getHhFirstname() !=null)
                                hhFirstName=covc.getHhFirstname();
                                if(covc.getHhSurname() !=null)
                                hhSurname=covc.getHhSurname();
                                hhe.setHhFirstname(hhFirstName);
                                hhe.setHhSurname(hhSurname);
                                Integer caregiverAge=covc.getCaregiverAge();

                                if(caregiverAge ==null || caregiverAge >199)
                                caregiverAge=0;

                                hhe.setHhAge(caregiverAge);
                                hhe.setCurrentAge(util.getCurrentAge(covc.getDateEnrollment(), caregiverAge,"Year"));
                                hhe.setAddress(covc.getCaregiverAddress());
                                hhe.setDateOfAssessment(covc.getDateEnrollment());
                                hhe.setEligibleForEnrollment("Yes");
                                hhe.setPartnerCode(covc.getPartner());
                                hhe.setPhone(covc.getCaregiverPhone());
                                //hhe.setOccupation(covc.getCaregiverOccupation());
                                //hhe.setMaritalStatus(covc.get)
                                hhe.setDateOfEntry(appUtil.getCurrentDate());
                                hhe.setRecordedBy(covc.getRecordedBy());
                                hhe.setNoOfChildrenInhh(covc.getNumOfOVCInHh());
                                hhe.setNoOfPeopleInhh(covc.getNumOfChildrenInHh());
                                hhedao.saveHouseholdEnrollment(hhe);
                                System.err.println("Household Enrollment record "+count+" saved");
                            }
                            hva=hvaDao.getHouseholdVulnerabilityAssessment(hhUniqueId, covc.getDateEnrollment());
                            if(hva==null)
                            {
                                hva=new HouseholdVulnerabilityAssessment();
                                hva.setAssessmentNo(1);
                                hva.setDateOfAssessment(covc.getDateEnrollment());
                                hva.setDateOfEntry(covc.getDateOfEntry());
                                hva.setDesignation(covc.getCompletedbyDesignation());
                                hva.setNameOfAssessor(covc.getCompletedbyName());
                                hva.setRecordedBy(covc.getRecordedBy());
                                hva.setHhUniqueId(hhUniqueId);
                                hvaDao.saveHouseholdVulnerabilityAssessment(hva);
                                System.err.println("Household Assessment record "+count+" saved");
                            }
                            count++;
                            /*Now create the caregiver information from old Ovc records*/
                            boolean complete=createCaregiverInfoFromCompatibilityOvc(covc);
                            if(complete)
                            changeOvcId(covc);
                         }
                    }
                }
            }
        }
        catch(Exception ex)
        {
            System.err.println(ex.getMessage());
        }
    }
    /*This method generates the Caregiver information from the enrollment table and save them in the new CaregiverInfo table
     It also updates the Ovc hhUniqueId and the CaregiverId*/
    public boolean createCaregiverInfoFromCompatibilityOvc(CompatibilityOvc covc)
    {
        boolean complete=false;
        OvcDao ovcDao=new OvcDaoImpl();
        CaregiverDao cgiverDao=new CaregiverDaoImpl();
        DaoUtil util=new DaoUtil();
        Ovc ovc=null;
        Caregiver cgiver=new Caregiver();
        try
        {
            cgiver.setCaregiverAddress(covc.getCaregiverAddress());
            Integer caregiverAge=covc.getCaregiverAge();
            if(caregiverAge ==null)
            caregiverAge=0;
            else if(caregiverAge >199)
            caregiverAge=200;
            cgiver.setCaregiverAge(caregiverAge);

            String hhFirstName="Unknown";
            String hhSurname="Unknown";
            if(covc.getHhFirstname() !=null)
            hhFirstName=covc.getHhFirstname();
            if(covc.getHhSurname() !=null)
            hhSurname=covc.getHhSurname();
            String caregiverName=covc.getCaregiverName();
            if(caregiverName !=null)
            {
                if(caregiverName.indexOf(",") !=-1)
                {
                      String[] caregiverNames=caregiverName.split(",");
                      cgiver.setCaregiverFirstname(caregiverNames[1]);
                      cgiver.setCaregiverLastName(caregiverNames[0]);
                }
                else if(caregiverName.indexOf(" ") !=-1)
                {
                     String[] caregiverNames=caregiverName.split(" ");
                     cgiver.setCaregiverLastName(caregiverNames[0]);
                     if(caregiverNames.length>1)
                     cgiver.setCaregiverFirstname(caregiverNames[1]);
                     else
                     cgiver.setCaregiverFirstname("Unknown");
                }
                else
                {
                     cgiver.setCaregiverFirstname(caregiverName);
                     cgiver.setCaregiverLastName("Unknown");
                }
           }
           else
           {
                cgiver.setCaregiverFirstname(hhFirstName);
                cgiver.setCaregiverLastName(hhSurname);
           }
           cgiver.setCaregiverGender(covc.getCaregiverGender());
           cgiver.setCaregiverOccupation(covc.getCaregiverOccupation());
           cgiver.setCaregiverPhone(covc.getCaregiverPhone());
           cgiver.setCurrentAge(util.getCurrentAge(covc.getDateEnrollment(), covc.getCaregiverAge(),"Year"));
           cgiver.setDateOfEnrollment(covc.getDateEnrollment());
           cgiver.setDateModified(currentDate);
           String hhUniqueId=covc.getOvcId();
           String caregiverId=null;
           /*Check if caregiver does not already exist*/
           Caregiver duplicateCgiver=cgiverDao.getCaregiverByName(hhUniqueId, cgiver.getCaregiverFirstname(),cgiver.getCaregiverLastName());
            if(duplicateCgiver==null)
            {
                caregiverId=cgiverDao.generateCaregiverId(hhUniqueId);
                cgiver.setCaregiverId(caregiverId);
                cgiver.setHhUniqueId(hhUniqueId);
                cgiverDao.saveCaregiver(cgiver);
                System.err.println("Caregiver "+cgiver.getCaregiverFirstname()+" "+cgiver.getCaregiverLastName()+" added");
            }
            else
            caregiverId=duplicateCgiver.getCaregiverId();
           /*Update the Ovc with hhUniqueId and CaregiverId*/
            ovc=ovcDao.getOvc(hhUniqueId);
            ovc.setHhUniqueId(hhUniqueId);
            ovc.setCaregiverId(caregiverId);
            ovc=util.getOvcWithCurrentAge(ovc);
            //ovc.setCurrentAge(util.getCurrentAge(ovc));
            ovcDao.updateOvc(ovc,false,false);
            complete=true;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            complete=false;
        }
        return complete;
    }
public String getCaregiverId(String hhUniqueId)
{
    CaregiverDao cgiverdao=new CaregiverDaoImpl();
    AppUtility appUtil=new AppUtility();
    String caregiverId=null;
    try
    {
        List caregiverList=cgiverdao.getListOfCaregiversFromSameHousehold(hhUniqueId);
        if(caregiverList==null || caregiverList.isEmpty())
        {
            HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
            HouseholdEnrollment hhe=hhedao.getHouseholdEnrollment(hhUniqueId);
            if(hhe !=null)
            {
                caregiverId=hhUniqueId;//cgiverdao.generateCaregiverId(hhUniqueId);
                Caregiver cgiver=new Caregiver();
                cgiver.setCaregiverAddress(hhe.getAddress());
                cgiver.setCaregiverAge(hhe.getHhAge());
                cgiver.setCaregiverFirstname(hhe.getHhFirstname());
                cgiver.setCaregiverLastName(hhe.getHhSurname());
                cgiver.setHhUniqueId(hhUniqueId);
                cgiver.setCaregiverGender(hhe.getHhGender());
                cgiver.setCaregiverId(caregiverId);
                cgiver.setCaregiverMaritalStatus(hhe.getMaritalStatus());
                cgiver.setCaregiverOccupation(hhe.getOccupation());
                cgiver.setDateOfEnrollment(hhe.getDateOfAssessment());
                cgiver.setDateModified(appUtil.getCurrentDate());
                cgiver.setCaregiverPhone(hhe.getPhone());
                cgiver.setHouseholdhead("1");
                cgiverdao.saveCaregiver(cgiver);
            }
        }
        else
        {
            Caregiver cgiver=(Caregiver)caregiverList.get(0);
            caregiverId=cgiver.getCaregiverId();
        }
    }
    catch(Exception ex)
    {
        ex.printStackTrace();
    }
    return caregiverId;
}
public void upgradeBirthRegistration(boolean forceUpdate)
{
    NomisUpgrade nu=new NomisUpgrade();
    nu.updateBirthRegistrationStatus(forceUpdate);
}
}
