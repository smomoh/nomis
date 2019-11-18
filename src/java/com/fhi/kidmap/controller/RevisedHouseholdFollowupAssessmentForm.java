/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author smomoh
 */
public class RevisedHouseholdFollowupAssessmentForm extends org.apache.struts.action.ActionForm 
{
    
    private String actionName;
    private String serialNo;
    private String recordId;
    private int assessmentId;
    private String hhUniqueId;
    private String hhSurname;
    private String hhFirstname;
    private String address;
    private String phone;
    private int noOfChildrenInhh;
    private int noOfPeopleInhh;
    private String dateOfAssessment;
    private int hhAge;
    private String hhGender;
    private String maritalStatus;
    private String occupation;
    private int hhEducationLevel;

    private int hhHeadship;
    private int health;
    private int educationLevel;
    private int childEducationLevel;
    private int shelterAndHousing;
    private int foodSecurityAndNutrition;
    private int meansOfLivelihood;
    private int hhIncome;
    private int protection;
    private String nameOfAssessor;
    private String designation;
    private String reasonWithdrawal;
    private String stateCode;
    private String lgaCode;
    private String orgCode;
    private String communityCode;
    private String partnerCode;
    private List lgaList=new ArrayList();
    private List organizationList=new ArrayList();
    private String hivStatus;
    private String enrolledOnART;
    private String facilityId;
    private List referralDirectoryList=new ArrayList();
    
    public RevisedHouseholdFollowupAssessmentForm() {
        super();
        // TODO Auto-generated constructor stub
    }

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

    public int getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getCommunityCode() {
        return communityCode;
    }

    public void setCommunityCode(String communityCode) {
        this.communityCode = communityCode;
    }

    public String getDateOfAssessment() {
        return dateOfAssessment;
    }

    public void setDateOfAssessment(String dateOfAssessment) {
        this.dateOfAssessment = dateOfAssessment;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(int educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getEnrolledOnART() {
        return enrolledOnART;
    }

    public void setEnrolledOnART(String enrolledOnART) {
        this.enrolledOnART = enrolledOnART;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public int getFoodSecurityAndNutrition() {
        return foodSecurityAndNutrition;
    }

    public void setFoodSecurityAndNutrition(int foodSecurityAndNutrition) {
        this.foodSecurityAndNutrition = foodSecurityAndNutrition;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHhAge() {
        return hhAge;
    }

    public void setHhAge(int hhAge) {
        this.hhAge = hhAge;
    }

    public String getHhFirstname() {
        return hhFirstname;
    }

    public void setHhFirstname(String hhFirstname) {
        this.hhFirstname = hhFirstname;
    }

    public String getHhGender() {
        return hhGender;
    }

    public void setHhGender(String hhGender) {
        this.hhGender = hhGender;
    }

    public int getHhHeadship() {
        return hhHeadship;
    }

    public void setHhHeadship(int hhHeadship) {
        this.hhHeadship = hhHeadship;
    }

    public int getHhIncome() {
        return hhIncome;
    }

    public void setHhIncome(int hhIncome) {
        this.hhIncome = hhIncome;
    }

    public String getHhSurname() {
        return hhSurname;
    }

    public void setHhSurname(String hhSurname) {
        this.hhSurname = hhSurname;
    }

    public String getHhUniqueId() {
        return hhUniqueId;
    }

    public void setHhUniqueId(String hhUniqueId) {
        this.hhUniqueId = hhUniqueId;
    }

    public int getHhEducationLevel() {
        return hhEducationLevel;
    }

    public void setHhEducationLevel(int hhEducationLevel) {
        this.hhEducationLevel = hhEducationLevel;
    }

    public String getHivStatus() {
        return hivStatus;
    }

    public void setHivStatus(String hivStatus) {
        this.hivStatus = hivStatus;
    }

    public String getLgaCode() {
        return lgaCode;
    }

    public void setLgaCode(String lgaCode) {
        this.lgaCode = lgaCode;
    }

    public List getLgaList() {
        return lgaList;
    }

    public void setLgaList(List lgaList) {
        this.lgaList = lgaList;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public int getMeansOfLivelihood() {
        return meansOfLivelihood;
    }

    public void setMeansOfLivelihood(int meansOfLivelihood) {
        this.meansOfLivelihood = meansOfLivelihood;
    }

    public String getNameOfAssessor() {
        return nameOfAssessor;
    }

    public void setNameOfAssessor(String nameOfAssessor) {
        this.nameOfAssessor = nameOfAssessor;
    }

    public int getNoOfChildrenInhh() {
        return noOfChildrenInhh;
    }

    public void setNoOfChildrenInhh(int noOfChildrenInhh) {
        this.noOfChildrenInhh = noOfChildrenInhh;
    }

    public int getNoOfPeopleInhh() {
        return noOfPeopleInhh;
    }

    public void setNoOfPeopleInhh(int noOfPeopleInhh) {
        this.noOfPeopleInhh = noOfPeopleInhh;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public List getOrganizationList() {
        return organizationList;
    }

    public void setOrganizationList(List organizationList) {
        this.organizationList = organizationList;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getProtection() {
        return protection;
    }

    public void setProtection(int protection) {
        this.protection = protection;
    }

    public String getReasonWithdrawal() {
        return reasonWithdrawal;
    }

    public void setReasonWithdrawal(String reasonWithdrawal) {
        this.reasonWithdrawal = reasonWithdrawal;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public List getReferralDirectoryList() {
        return referralDirectoryList;
    }

    public void setReferralDirectoryList(List referralDirectoryList) {
        this.referralDirectoryList = referralDirectoryList;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public int getShelterAndHousing() {
        return shelterAndHousing;
    }

    public void setShelterAndHousing(int shelterAndHousing) {
        this.shelterAndHousing = shelterAndHousing;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public int getChildEducationLevel() {
        return childEducationLevel;
    }

    public void setChildEducationLevel(int childEducationLevel) {
        this.childEducationLevel = childEducationLevel;
    }
    
    private boolean isHouseholdAccessed()
    {
        /*if(getEducationLevel()==0 && getFoodSecurityAndNutrition()==0
         && getHealth()==0 && getMeansOfLivelihood()==0 && getShelterAndHousing()==0
         && getHhHeadship()==0 && getHhIncome()==0 && getReasonWithdrawal()==null)*/
        if(getReasonWithdrawal()==null && (getChildEducationLevel()==0 || getFoodSecurityAndNutrition()==0
         || getHealth()==0 || getMeansOfLivelihood()==0 || getShelterAndHousing()==0
         || getHhHeadship()==0 || getHhIncome()==0 || this.getProtection()==0))
        return false;
        return true;//getReasonWithdrawal()==null && (
    }
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        actionName=null;
        hhSurname=null;
        hhFirstname=null;
        address=null;
        phone=null;
        noOfChildrenInhh=0;
        noOfPeopleInhh=0;
        hhAge=0;
        hhGender=null;
        hhUniqueId=null;
        recordId=null;
        assessmentId=0;
        serialNo=null;
        dateOfAssessment=null;
        maritalStatus=null;
        occupation=null;
        hhHeadship=0;
        health=0;
        educationLevel=0;
        shelterAndHousing=0;
        foodSecurityAndNutrition=0;
        meansOfLivelihood=0;
        hhIncome=0;
        protection=0;
        nameOfAssessor=null;
        designation=null;
        reasonWithdrawal=null;
        stateCode=null;
        lgaCode=null;
        orgCode=null;
        communityCode=null;
        hivStatus="select";
        enrolledOnART="No";
        facilityId="select";
    }
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if(getActionName()==null || getActionName().equalsIgnoreCase("hhdetails") || getActionName().equalsIgnoreCase("hhFollowupDetails") || getActionName().equalsIgnoreCase("delete") || getActionName().equalsIgnoreCase("cboList"))
        return errors;
        else if(getHhUniqueId()==null || getHhUniqueId().length()<1)
        errors.add("hhUniqueId", new ActionMessage("errors.hhUniqueId.required"));
        else if(!isHouseholdAccessed())
        errors.add("thematicAreas", new ActionMessage("errors.thematicAreas.required"));
        else if( getDateOfAssessment()==null ||  getDateOfAssessment().indexOf("/")==-1)
        errors.add("dateOfAssessment", new ActionMessage("errors.dateOfAssessment.required"));
        else if( getDateOfAssessment()!=null &&  getDateOfAssessment().indexOf("/")!=-1)
        {
            String[] dateArray=getDateOfAssessment().split("/");
            if(dateArray.length != 3)
            errors.add("dateOfAssessment", new ActionMessage("errors.dateOfAssessment.required"));
        }
        else if(getHhEducationLevel()==0)
        errors.add("hhEducationLevel", new ActionMessage("errors.hhEducationLevel.invalid"));
        return errors;
    }
}
