/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.OvcDao;
import com.fhi.kidmap.dao.OvcDaoImpl;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author Siaka
 */
public class OvcServiceForm extends org.apache.struts.action.ActionForm {
    
    private String state;
private String lga;
private String ward;
private String cboName;
private String completedbyCbo;

private String serviceDate;
private String dateEnrollment;
private String actionName;

private String hhSerialNo;
private String hhUniqueId;
private String ovcId;
private String ovcSno;
private String surname;
private String otherNames1;
private String otherNames2;
private String gender;
private int age;
private String ageUnit;
private String address;
private String phone;

private double weight;
private double height;
private double currentWeight;
private double currentHeight;

private int ovcServiceId;

private String reasonWithdrawal;
private String completedbyName1;
private String completedbyName2;
private String completedbyDesignation;
private String completedbyDate;

private String[] psychoServices;
private String[] nutritionalServices;
private String[] healthServices;
private String[] educationalServices;
private String[] protectionServices;
private String[] shelterServices;
private String[] economicServices;
private String[] referralServices;

private String psychosocialOther;
private String nutritionOther;
private String healthOther;
private String educationOther;
private String protectionOther;
private String shelterOther;
private String economicOther;
private String providerName;
private String combinedNames;
private String currentHivStatus;
private String newHivStatus;
private String enrolledInCare;
private String enrolledOnART;
private String organizationClientIsReferred;
private int childAbused;
private String childMissedSchool;
private int childLinkedToGovt;
private List hivStatusList=new ArrayList();
private List referralDirectoryList=new ArrayList();
    public OvcServiceForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLga() {
        return lga;
    }

    public void setLga(String lga) {
        this.lga = lga;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getCboName() {
        return cboName;
    }

    public void setCboName(String cboName) {
        this.cboName = cboName;
    }

    public String getCompletedbyCbo() {
        return completedbyCbo;
    }

    public void setCompletedbyCbo(String completedbyCbo) {
        this.completedbyCbo = completedbyCbo;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getDateEnrollment() {
        return dateEnrollment;
    }

    public void setDateEnrollment(String dateEnrollment) {
        this.dateEnrollment = dateEnrollment;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getHhSerialNo() {
        return hhSerialNo;
    }

    public void setHhSerialNo(String hhSerialNo) {
        this.hhSerialNo = hhSerialNo;
    }

    public String getHhUniqueId() {
        return hhUniqueId;
    }

    public void setHhUniqueId(String hhUniqueId) {
        this.hhUniqueId = hhUniqueId;
    }

    public String getOvcId() {
        return ovcId;
    }

    public void setOvcId(String ovcId) {
        this.ovcId = ovcId;
    }

    public String getOvcSno() {
        return ovcSno;
    }

    public void setOvcSno(String ovcSno) {
        this.ovcSno = ovcSno;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getOtherNames1() {
        return otherNames1;
    }

    public void setOtherNames1(String otherNames1) {
        this.otherNames1 = otherNames1;
    }

    public String getOtherNames2() {
        return otherNames2;
    }

    public void setOtherNames2(String otherNames2) {
        this.otherNames2 = otherNames2;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAgeUnit() {
        return ageUnit;
    }

    public void setAgeUnit(String ageUnit) {
        this.ageUnit = ageUnit;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(double currentWeight) {
        this.currentWeight = currentWeight;
    }

    public double getCurrentHeight() {
        return currentHeight;
    }

    public void setCurrentHeight(double currentHeight) {
        this.currentHeight = currentHeight;
    }

    public int getOvcServiceId() {
        return ovcServiceId;
    }

    public void setOvcServiceId(int ovcServiceId) {
        this.ovcServiceId = ovcServiceId;
    }

    public String getReasonWithdrawal() {
        return reasonWithdrawal;
    }

    public void setReasonWithdrawal(String reasonWithdrawal) {
        this.reasonWithdrawal = reasonWithdrawal;
    }

    public String getCompletedbyName1() {
        return completedbyName1;
    }

    public void setCompletedbyName1(String completedbyName1) {
        this.completedbyName1 = completedbyName1;
    }

    public String getCompletedbyName2() {
        return completedbyName2;
    }

    public void setCompletedbyName2(String completedbyName2) {
        this.completedbyName2 = completedbyName2;
    }

    public String getCompletedbyDesignation() {
        return completedbyDesignation;
    }

    public void setCompletedbyDesignation(String completedbyDesignation) {
        this.completedbyDesignation = completedbyDesignation;
    }

    public String getCompletedbyDate() {
        return completedbyDate;
    }

    public void setCompletedbyDate(String completedbyDate) {
        this.completedbyDate = completedbyDate;
    }

    public String[] getPsychoServices() {
        return psychoServices;
    }

    public void setPsychoServices(String[] psychoServices) {
        this.psychoServices = psychoServices;
    }

    public String[] getNutritionalServices() {
        return nutritionalServices;
    }

    public void setNutritionalServices(String[] nutritionalServices) {
        this.nutritionalServices = nutritionalServices;
    }

    public String[] getHealthServices() {
        return healthServices;
    }

    public void setHealthServices(String[] healthServices) {
        this.healthServices = healthServices;
    }

    public String[] getEducationalServices() {
        return educationalServices;
    }

    public void setEducationalServices(String[] educationalServices) {
        this.educationalServices = educationalServices;
    }

    public String[] getProtectionServices() {
        return protectionServices;
    }

    public void setProtectionServices(String[] protectionServices) {
        this.protectionServices = protectionServices;
    }

    public String[] getShelterServices() {
        return shelterServices;
    }

    public void setShelterServices(String[] shelterServices) {
        this.shelterServices = shelterServices;
    }

    public String[] getEconomicServices() {
        return economicServices;
    }

    public void setEconomicServices(String[] economicServices) {
        this.economicServices = economicServices;
    }

    public String[] getReferralServices() {
        return referralServices;
    }

    public void setReferralServices(String[] referralServices) {
        this.referralServices = referralServices;
    }

    public String getPsychosocialOther() {
        return psychosocialOther;
    }

    public void setPsychosocialOther(String psychosocialOther) {
        this.psychosocialOther = psychosocialOther;
    }

    public String getNutritionOther() {
        return nutritionOther;
    }

    public void setNutritionOther(String nutritionOther) {
        this.nutritionOther = nutritionOther;
    }

    public String getHealthOther() {
        return healthOther;
    }

    public void setHealthOther(String healthOther) {
        this.healthOther = healthOther;
    }

    public String getEducationOther() {
        return educationOther;
    }

    public void setEducationOther(String educationOther) {
        this.educationOther = educationOther;
    }

    public String getProtectionOther() {
        return protectionOther;
    }

    public void setProtectionOther(String protectionOther) {
        this.protectionOther = protectionOther;
    }

    public String getShelterOther() {
        return shelterOther;
    }

    public void setShelterOther(String shelterOther) {
        this.shelterOther = shelterOther;
    }

    public String getEconomicOther() {
        return economicOther;
    }

    public void setEconomicOther(String economicOther) {
        this.economicOther = economicOther;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getCombinedNames() {
        return combinedNames;
    }

    public void setCombinedNames(String combinedNames) {
        this.combinedNames = combinedNames;
    }

    public String getCurrentHivStatus() {
        return currentHivStatus;
    }

    public void setCurrentHivStatus(String currentHivStatus) {
        this.currentHivStatus = currentHivStatus;
    }

    public List getHivStatusList() {
        return hivStatusList;
    }

    public void setHivStatusList(List hivStatusList) {
        this.hivStatusList = hivStatusList;
    }

    public String getNewHivStatus() {
        return newHivStatus;
    }

    public void setNewHivStatus(String newHivStatus) {
        this.newHivStatus = newHivStatus;
    }

    public String getEnrolledInCare() {
        return enrolledInCare;
    }

    public void setEnrolledInCare(String enrolledInCare) {
        this.enrolledInCare = enrolledInCare;
    }

    public String getOrganizationClientIsReferred() {
        return organizationClientIsReferred;
    }

    public void setOrganizationClientIsReferred(String organizationClientIsReferred) {
        this.organizationClientIsReferred = organizationClientIsReferred;
    }

    public List getReferralDirectoryList() {
        return referralDirectoryList;
    }

    public void setReferralDirectoryList(List referralDirectoryList) {
        this.referralDirectoryList = referralDirectoryList;
    }

    public String getEnrolledOnART() {
        return enrolledOnART;
    }

    public void setEnrolledOnART(String enrolledOnART) {
        this.enrolledOnART = enrolledOnART;
    }

    public int getChildAbused() {
        return childAbused;
    }

    public void setChildAbused(int childAbused) {
        this.childAbused = childAbused;
    }

    public int getChildLinkedToGovt() {
        return childLinkedToGovt;
    }

    public void setChildLinkedToGovt(int childLinkedToGovt) {
        this.childLinkedToGovt = childLinkedToGovt;
    }

    public String getChildMissedSchool() {
        return childMissedSchool;
    }

    public void setChildMissedSchool(String childMissedSchool) {
        this.childMissedSchool = childMissedSchool;
    }
    private boolean childAssessed()
    {
        if (getChildAbused()==0 && getChildLinkedToGovt()==0
           && (getChildMissedSchool()==null || getChildMissedSchool().equalsIgnoreCase("N/A")))
            return false;
        return true;
    }
    private boolean hasServiceAccessed() {

        if((psychoServices ==null) &&
          (healthServices ==null) &&
          (educationalServices ==null) &&
          (protectionServices ==null) &&
          (shelterServices ==null) &&
          (economicServices ==null) && 
          (referralServices==null) &&
          (nutritionalServices==null) &&
          (reasonWithdrawal==null))
          

            /*psychosocialOther==null &&
            nutritionOther==null &&
            healthOther==null &&
            educationOther==null &&
            protectionOther==null &&
            shelterOther==null &&
            economicOther==null)*/
         return false;
        
        else if((psychoServices !=null && psychoServices.length==0) &&
          (healthServices !=null && healthServices.length==0) &&
          (educationalServices !=null && educationalServices.length ==0) &&
          (protectionServices !=null && protectionServices.length ==0) &&
          (shelterServices !=null && shelterServices.length ==0) &&
          (nutritionalServices !=null && nutritionalServices.length ==0) &&      
          (economicServices !=null && economicServices.length ==0)&&
          (referralServices !=null && referralServices.length ==0)&&
          (reasonWithdrawal !=null && reasonWithdrawal.trim().length() < 1))//&&
          /*(psychosocialOther !=null && psychosocialOther.trim().length() < 1)&&
          (nutritionOther !=null && nutritionOther.trim().length() < 1)&&
          (healthOther !=null && healthOther.trim().length() < 1)&&
          (educationOther !=null && educationOther.trim().length() < 1)&&
          (protectionOther !=null && protectionOther.trim().length() < 1)&&
          (shelterOther !=null && shelterOther.trim().length() < 1)&&
          (economicOther !=null && economicOther.trim().length() < 1))*/
         return false;
        return true;
    }

    @Override
public void reset(ActionMapping mapping,
HttpServletRequest request) {
actionName=null;
state = null;
lga = null;
ward = null;
ovcSno=null;
ovcId = null;
hhSerialNo=null;
hhUniqueId=null;
surname = null;
otherNames1 = null;
otherNames2 = null;
age = 0;
ageUnit = null;
address=null;
reasonWithdrawal = null;
completedbyName1 = null;
completedbyName2 = null;
completedbyDesignation = null;
completedbyDate = null;
providerName=null;
gender = null;
combinedNames = null;
phone = null;
height = 0.0;
weight = 0.0;
currentWeight=0.0;
currentHeight=0.0;
completedbyCbo = null;
dateEnrollment = null;
serviceDate=null;
referralServices=null;
psychoServices=null;
nutritionalServices=null;
healthServices=null;
educationalServices=null;
protectionServices=null;
shelterServices=null;
economicServices=null;
psychosocialOther=null;
nutritionOther=null;
healthOther=null;
educationOther=null;
protectionOther=null;
shelterOther=null;
economicOther=null;
currentHivStatus=null;
newHivStatus=null;
enrolledInCare="No";
enrolledOnART="No";
childAbused=0;
childLinkedToGovt=0;
childMissedSchool="N/A";
}
    /**
     * This is the action called from the Struts framework.
     *
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if(getActionName() ==null || getActionName().equalsIgnoreCase("cboList")|| getActionName().equalsIgnoreCase("hhDetails"))
    return errors;
    else if(getActionName() !=null && (getActionName().equalsIgnoreCase("save") || getActionName().equalsIgnoreCase("modify")))
    {
        if(getOvcId() == null || getOvcId().trim().length() < 1)
        errors.add("ovcId", new ActionMessage("error.ovcId.required"));
        else if(getServiceDate() ==null || getServiceDate().indexOf("/") ==-1 || (getServiceDate().split("/")).length !=3)
        errors.add("serviceDate", new ActionMessage("error.serviceDate.required"));
        else if(getReasonWithdrawal() ==null && !hasServiceAccessed() && !childAssessed())
        errors.add("serviceAccessed", new ActionMessage("error.serviceAccessed.required"));
    }
    if(this.getReasonWithdrawal() !=null && (getReasonWithdrawal().equalsIgnoreCase("Age > 18") || getReasonWithdrawal().equalsIgnoreCase("Age&gt;18")))
    {
        OvcDao dao=new OvcDaoImpl();
        DaoUtil util=new DaoUtil();
        try
        {
            Ovc ovc=dao.getOvc(ovcId);
            if(util.getCurrentAge(ovc) < 18)
            errors.add("reasonWithdrawal", new ActionMessage("error.reasonWithdrawal.ovcAgelessthan18"));
        }
        catch(Exception ex)
        {
            
        }
    }
    else if(getActionName() !=null && !getActionName().equals("baselineDetails") && !getActionName().equals("cbo") && !getActionName().equals("serviceDetails") && !getActionName().equals("delete"))
    {
        if(getOvcId() == null || getOvcId().trim().length() < 1)
        errors.add("ovcId", new ActionMessage("error.ovcId.required"));
        else if(getServiceDate() ==null || getServiceDate().indexOf("/") ==-1 || (getServiceDate().split("/")).length !=3)
        errors.add("serviceDate", new ActionMessage("error.serviceDate.required"));
        else if(getReasonWithdrawal() ==null && !hasServiceAccessed() && !childAssessed())
        errors.add("serviceAccessed", new ActionMessage("error.serviceAccessed.required"));
    }

        return errors;
    }
}
