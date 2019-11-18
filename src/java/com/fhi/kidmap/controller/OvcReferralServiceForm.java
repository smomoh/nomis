/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author smomoh
 */
public class OvcReferralServiceForm extends org.apache.struts.action.ActionForm 
{
    
private String state;
private String lga;
private String ward;
private String cboName;
private String completedbyCbo;
private String dateOfReferral;
private String dateEnrollment;
private String actionName;
private String ovcId;
private String ovcSno;
private String hhSerialNo;
private String hhUniqueId;
private String surname;
private String otherNames;
private String gender;
private int age;
private String ageUnit;
private String address;
private String phone;
private String caregiverName;
private int recordId;
private String[] psychoServices;
private String[] nutritionalServices;
private String[] healthServices;
private String[] educationalServices;
private String[] protectionServices;
private String[] shelterServices;
private String[] economicServices;
private String psychosocialOther;
private String nutritionOther;
private String healthOther;
private String educationOther;
private String protectionOther;
private String shelterOther;
private String economicOther;
private String nameOfOrganizationChildIsreferred;
private String referralCompleted;
private String nameOfReferrer;
private String designationOfReferrer;
private String referrerPhoneNo;
private String referrerEmail;
private String combinedNames;
private String type;

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getCaregiverName() {
        return caregiverName;
    }

    public void setCaregiverName(String caregiverName) {
        this.caregiverName = caregiverName;
    }

    public String getCboName() {
        return cboName;
    }

    public void setCboName(String cboName) {
        this.cboName = cboName;
    }

    public String getCombinedNames() {
        return combinedNames;
    }

    public void setCombinedNames(String combinedNames) {
        this.combinedNames = combinedNames;
    }

    public String getCompletedbyCbo() {
        return completedbyCbo;
    }

    public void setCompletedbyCbo(String completedbyCbo) {
        this.completedbyCbo = completedbyCbo;
    }

    
    public String getDateEnrollment() {
        return dateEnrollment;
    }

    public void setDateEnrollment(String dateEnrollment) {
        this.dateEnrollment = dateEnrollment;
    }

    public String getEconomicOther() {
        return economicOther;
    }

    public void setEconomicOther(String economicOther) {
        this.economicOther = economicOther;
    }

    public String[] getEconomicServices() {
        return economicServices;
    }

    public void setEconomicServices(String[] economicServices) {
        this.economicServices = economicServices;
    }

    public String getEducationOther() {
        return educationOther;
    }

    public void setEducationOther(String educationOther) {
        this.educationOther = educationOther;
    }

    public String[] getEducationalServices() {
        return educationalServices;
    }

    public void setEducationalServices(String[] educationalServices) {
        this.educationalServices = educationalServices;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHealthOther() {
        return healthOther;
    }

    public void setHealthOther(String healthOther) {
        this.healthOther = healthOther;
    }

    public String[] getHealthServices() {
        return healthServices;
    }

    public void setHealthServices(String[] healthServices) {
        this.healthServices = healthServices;
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

    
    public String getLga() {
        return lga;
    }

    public void setLga(String lga) {
        this.lga = lga;
    }

    public String getNutritionOther() {
        return nutritionOther;
    }

    public void setNutritionOther(String nutritionOther) {
        this.nutritionOther = nutritionOther;
    }

    public String[] getNutritionalServices() {
        return nutritionalServices;
    }

    public void setNutritionalServices(String[] nutritionalServices) {
        this.nutritionalServices = nutritionalServices;
    }

    public String getOtherNames() {
        return otherNames;
    }

    public void setOtherNames(String otherNames) {
        this.otherNames = otherNames;
    }

    public String getOvcId() {
        return ovcId;
    }

    public void setOvcId(String ovcId) {
        this.ovcId = ovcId;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getOvcSno() {
        return ovcSno;
    }

    public void setOvcSno(String ovcSno) {
        this.ovcSno = ovcSno;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProtectionOther() {
        return protectionOther;
    }

    public void setProtectionOther(String protectionOther) {
        this.protectionOther = protectionOther;
    }

    public String[] getProtectionServices() {
        return protectionServices;
    }

    public void setProtectionServices(String[] protectionServices) {
        this.protectionServices = protectionServices;
    }

    public String[] getPsychoServices() {
        return psychoServices;
    }

    public void setPsychoServices(String[] psychoServices) {
        this.psychoServices = psychoServices;
    }

    public String getPsychosocialOther() {
        return psychosocialOther;
    }

    public void setPsychosocialOther(String psychosocialOther) {
        this.psychosocialOther = psychosocialOther;
    }

    public String getDateOfReferral() {
        return dateOfReferral;
    }

    public void setDateOfReferral(String dateOfReferral) {
        this.dateOfReferral = dateOfReferral;
    }

    public String getShelterOther() {
        return shelterOther;
    }

    public void setShelterOther(String shelterOther) {
        this.shelterOther = shelterOther;
    }

    public String[] getShelterServices() {
        return shelterServices;
    }

    public void setShelterServices(String[] shelterServices) {
        this.shelterServices = shelterServices;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getNameOfOrganizationChildIsreferred() {
        return nameOfOrganizationChildIsreferred;
    }

    public void setNameOfOrganizationChildIsreferred(String nameOfOrganizationChildIsreferred) {
        this.nameOfOrganizationChildIsreferred = nameOfOrganizationChildIsreferred;
    }

    public String getReferralCompleted() {
        return referralCompleted;
    }

    public void setReferralCompleted(String referralCompleted) {
        this.referralCompleted = referralCompleted;
    }

    public String getDesignationOfReferrer() {
        return designationOfReferrer;
    }

    public void setDesignationOfReferrer(String designationOfReferrer) {
        this.designationOfReferrer = designationOfReferrer;
    }

    public String getNameOfReferrer() {
        return nameOfReferrer;
    }

    public void setNameOfReferrer(String nameOfReferrer) {
        this.nameOfReferrer = nameOfReferrer;
    }

    public String getReferrerEmail() {
        return referrerEmail;
    }

    public void setReferrerEmail(String referrerEmail) {
        this.referrerEmail = referrerEmail;
    }

    public String getReferrerPhoneNo() {
        return referrerPhoneNo;
    }

    public void setReferrerPhoneNo(String referrerPhoneNo) {
        this.referrerPhoneNo = referrerPhoneNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    

    private boolean hasServiceAccessed() 
    {
        AppUtility appUtil=new AppUtility();
        if((getPsychoServices() ==null || appUtil.isEmptyArray(getPsychoServices())) &&
          (getHealthServices() ==null || appUtil.isEmptyArray(getHealthServices())) &&
          (getEducationalServices() ==null || appUtil.isEmptyArray(getEducationalServices())) &&
          (getProtectionServices() ==null || appUtil.isEmptyArray(getProtectionServices())) &&
          (getShelterServices() ==null || appUtil.isEmptyArray(getShelterServices())) &&
          (getEconomicServices() ==null || appUtil.isEmptyArray(getEconomicServices())))
         return false;
        else
        /*else if((psychoServices !=null && psychoServices.length==0) &&
          (healthServices !=null && healthServices.length==0) &&
          (educationalServices !=null && educationalServices.length ==0) &&
          (protectionServices !=null && protectionServices.length ==0) &&
          (shelterServices !=null && shelterServices.length ==0) &&
          (economicServices !=null && economicServices.length ==0))
         return false;*/
        return true;
    }

@Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        state=null;
        lga=null;
        ward=null;
        cboName=null;
        completedbyCbo=null;
        dateOfReferral=null;
        dateEnrollment=null;
        actionName=null;
        ovcId=null;
        ovcSno=null;
        hhSerialNo=null;
        hhUniqueId=null;
        surname=null;
        otherNames=null;
        gender=null;
        age=0;
        ageUnit=null;
        address=null;
        phone=null;

        recordId=0;
        nameOfOrganizationChildIsreferred=null;
        referralCompleted=null;
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
        caregiverName=null;
        nameOfReferrer=null;
        designationOfReferrer=null;
        referrerPhoneNo=null;
        referrerEmail=null;
        combinedNames=null;
        //type=null;
    }
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
@Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        //System.err.println("Inside validate referral form");
        //System.err.println("Referral date in validate referral form is "+this.getDateOfReferral());
        if(getActionName() ==null || getActionName().equalsIgnoreCase("cboList") || getActionName().equalsIgnoreCase("hhDetails"))
        return errors;
        if(getActionName() !=null && (getActionName().equals("save") || getActionName().equals("modify")))
        {
            if(this.getDateOfReferral() ==null)
            {
                //System.err.println("Referral date is null Inside validate referral form");
                errors.add("dateOfReferral", new ActionMessage("error.dateOfReferral.required"));
            }
            else if(getDateOfReferral().indexOf("/") ==-1 || (getDateOfReferral().split("/")).length !=3)
            {
                //System.err.println("Referral date is invalid Inside validate referral form");
                errors.add("dateOfReferral", new ActionMessage("error.dateOfReferral.required"));
            }
            else if(getType() == null || this.getType().trim().length() < 1)
            errors.add("type", new ActionMessage("error.type.required"));
            //
            else if(getType() !=null && (getType().equalsIgnoreCase("VC") || getType().equalsIgnoreCase("household")))
            {
                if(this.getOvcId() == null || this.getOvcId().trim().length() < 1)
                errors.add("ovcId", new ActionMessage("error.ovcId.required"));
            }
            else if(!hasServiceAccessed())
            errors.add("serviceAccessed", new ActionMessage("error.serviceAccessed.required"));
            else if(this.getNameOfReferrer() == null || this.getNameOfReferrer().length() < 1)
            errors.add("nameOfReferrer", new ActionMessage("error.nameOfReferrer.required"));
            else if(this.getNameOfOrganizationChildIsreferred() == null || this.getNameOfOrganizationChildIsreferred().length() < 1)
            errors.add("nameOfOrganizationChildIsreferred", new ActionMessage("error.nameOfOrganizationChildIsreferred.required"));
            
            
            
            
            //System.err.println("end of validate in referral form");
        }
        return errors;
    }
}
