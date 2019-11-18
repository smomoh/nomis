/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author HP USER
 */
public class OvcFollowupSurveyForm extends ActionForm {

    public OvcFollowupSurveyForm() {

    }

    private String state;
private String lga;
private String ward;
private String dateEnrollment;
private String surname;
private String otherNames1;
private String otherNames2;
private String combinedNames;
private String gender;
private int age;
private String ageUnit;
private String address;
private String phone;
private double weight;
private double height;

private String hivStatusAtEnrollment;
private String birthCertificateAtEnrollment;
private String schoolStatusAtEnrollment;
private String schoolName;
private String combinedCaregiverName;
private String caregiverPhone;
private String actionName;
private String ovcSno;
private String ovcId;
private String hhSerialNo;
private String hhUniqueId;


    int followupId;
    int followupCsiId;
    private String completedbyCbo;
    private String dateOfSurvey;
    private String updatedAddress;
    private int updatedAge;
    private String updatedAgeUnit;
    private String updatedGender;
    private String childCurrentHivStatus;
    private String updatedBirthCertStatus;
    private String updatedSchoolStatus;
    private String updatedSchoolName;
    private String updatedClass;
    private String updatedCaregiverName1;
    private String updatedCaregiverName2;
    private String updatedCaregiverGender;
    private int updatedCaregiverAge;
    private String updatedCaregiverAddress;
    private String updatedCaregiverPhone;
    private String updatedCaregiverOccupation;
    private String updatedCaregiverRelationship;
    private String updatedOtherCaregiverRelationship;
    private String cgiverHivStatus;
    private String cgiverCurrentHivStatus;
    private String reasonWithdrawal;
    private String completedbyName;
    private String completedbyDesignation;
    private String genderAtEnrollemnt;
    private int ageAtEnrollemnt;
    private String ageUnitAtEnrollemnt;
    private String caregiverId;
    

    private int csiFactor1;
    private int csiFactor2;
    private int csiFactor3;
    private int csiFactor4;
    private int csiFactor5;
    private int csiFactor6;
    private int csiFactor7;
    private int csiFactor8;
    private int csiFactor9;
    private int csiFactor10;
    private int csiFactor11;
    private int csiFactor12;
    private List schoolList=new ArrayList();
    private List caregiverList=new ArrayList();
    private String childUpdatedHivStatus;
    private String enrolledInCare;
    private String cgiverEnrolledInCare;
    private String enrolledOnArt;
    private String cgiverEnrolledOnArt;
    private String vcFacilityId;
    private String cgiverFacilityId;
    private List hivStatusList=new ArrayList();
    private List caregiverHivStatusList=new ArrayList();
    private List referralDirectoryList=new ArrayList();


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

    public String getCombinedNames() {
        return combinedNames;
    }

    public void setCombinedNames(String combinedNames) {
        this.combinedNames = combinedNames;
    }

    public String getDateEnrollment() {
        return dateEnrollment;
    }

    public void setDateEnrollment(String dateEnrollment) {
        this.dateEnrollment = dateEnrollment;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getLga() {
        return lga;
    }

    public void setLga(String lga) {
        this.lga = lga;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getDateOfSurvey() {
        return dateOfSurvey;
    }

    public void setDateOfSurvey(String dateOfSurvey) {
        this.dateOfSurvey = dateOfSurvey;
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
    
    public String getOvcSno() {
        return ovcSno;
    }

    public void setOvcSno(String ovcSno) {
        this.ovcSno = ovcSno;
    }

    public String getOvcId() {
        return ovcId;
    }

    public void setOvcId(String ovcId) {
        this.ovcId = ovcId;
    }

    public String getUpdatedAddress() {
        return updatedAddress;
    }

    public void setUpdatedAddress(String updatedAddress) {
        this.updatedAddress = updatedAddress;
    }

    public int getUpdatedAge() {
        return updatedAge;
    }

    public void setUpdatedAge(int updatedAge) {
        this.updatedAge = updatedAge;
    }

    public String getUpdatedAgeUnit() {
        return updatedAgeUnit;
    }

    public void setUpdatedAgeUnit(String updatedAgeUnit) {
        this.updatedAgeUnit = updatedAgeUnit;
    }

    public String getUpdatedGender() {
        return updatedGender;
    }

    public void setUpdatedGender(String updatedGender) {
        this.updatedGender = updatedGender;
    }

    public String getChildCurrentHivStatus() {
        return childCurrentHivStatus;
    }

    public void setChildCurrentHivStatus(String childCurrentHivStatus) {
        this.childCurrentHivStatus = childCurrentHivStatus;
    }

    public String getUpdatedBirthCertStatus() {
        return updatedBirthCertStatus;
    }

    public void setUpdatedBirthCertStatus(String updatedBirthCertStatus) {
        this.updatedBirthCertStatus = updatedBirthCertStatus;
    }

    public String getUpdatedCaregiverAddress() {
        return updatedCaregiverAddress;
    }

    public void setUpdatedCaregiverAddress(String updatedCaregiverAddress) {
        this.updatedCaregiverAddress = updatedCaregiverAddress;
    }

    public int getUpdatedCaregiverAge() {
        return updatedCaregiverAge;
    }

    public void setUpdatedCaregiverAge(int updatedCaregiverAge) {
        this.updatedCaregiverAge = updatedCaregiverAge;
    }

    public String getUpdatedCaregiverGender() {
        return updatedCaregiverGender;
    }

    public void setUpdatedCaregiverGender(String updatedCaregiverGender) {
        this.updatedCaregiverGender = updatedCaregiverGender;
    }

    public String getUpdatedCaregiverName1() {
        return updatedCaregiverName1;
    }

    public void setUpdatedCaregiverName1(String updatedCaregiverName1) {
        this.updatedCaregiverName1 = updatedCaregiverName1;
    }

    public String getUpdatedCaregiverName2() {
        return updatedCaregiverName2;
    }

    public void setUpdatedCaregiverName2(String updatedCaregiverName2) {
        this.updatedCaregiverName2 = updatedCaregiverName2;
    }

    
    public String getUpdatedCaregiverOccupation() {
        return updatedCaregiverOccupation;
    }

    public void setUpdatedCaregiverOccupation(String updatedCaregiverOccupation) {
        this.updatedCaregiverOccupation = updatedCaregiverOccupation;
    }

    public String getUpdatedCaregiverPhone() {
        return updatedCaregiverPhone;
    }

    public void setUpdatedCaregiverPhone(String updatedCaregiverPhone) {
        this.updatedCaregiverPhone = updatedCaregiverPhone;
    }

    public String getUpdatedCaregiverRelationship() {
        return updatedCaregiverRelationship;
    }

    public void setUpdatedCaregiverRelationship(String updatedCaregiverRelationship) {
        this.updatedCaregiverRelationship = updatedCaregiverRelationship;
    }

    public String getUpdatedSchoolName() {
        return updatedSchoolName;
    }

    public void setUpdatedSchoolName(String updatedSchoolName) {
        this.updatedSchoolName = updatedSchoolName;
    }

    public String getUpdatedClass() {
        return updatedClass;
    }

    public void setUpdatedClass(String updatedClass) {
        this.updatedClass = updatedClass;
    }

    public String getUpdatedSchoolStatus() {
        return updatedSchoolStatus;
    }

    public void setUpdatedSchoolStatus(String updatedSchoolStatus) {
        this.updatedSchoolStatus = updatedSchoolStatus;
    }

    public String getCompletedbyCbo() {
        return completedbyCbo;
    }

    public void setCompletedbyCbo(String completedbyCbo) {
        this.completedbyCbo = completedbyCbo;
    }

    public String getCgiverHivStatus() {
        return cgiverHivStatus;
    }

    public void setCgiverHivStatus(String cgiverHivStatus) {
        this.cgiverHivStatus = cgiverHivStatus;
    }

    public String getReasonWithdrawal() {
        return reasonWithdrawal;
    }

    public void setReasonWithdrawal(String reasonWithdrawal) {
        this.reasonWithdrawal = reasonWithdrawal;
    }

    public String getCaregiverId() {
        return caregiverId;
    }

    public void setCaregiverId(String caregiverId) {
        this.caregiverId = caregiverId;
    }
    

    public int getCsiFactor1() {
        return csiFactor1;
    }

    public void setCsiFactor1(int csiFactor1) {
        this.csiFactor1 = csiFactor1;
    }

    public int getCsiFactor10() {
        return csiFactor10;
    }

    public void setCsiFactor10(int csiFactor10) {
        this.csiFactor10 = csiFactor10;
    }

    public int getCsiFactor11() {
        return csiFactor11;
    }

    public void setCsiFactor11(int csiFactor11) {
        this.csiFactor11 = csiFactor11;
    }

    public int getCsiFactor12() {
        return csiFactor12;
    }

    public void setCsiFactor12(int csiFactor12) {
        this.csiFactor12 = csiFactor12;
    }

    public int getCsiFactor2() {
        return csiFactor2;
    }

    public void setCsiFactor2(int csiFactor2) {
        this.csiFactor2 = csiFactor2;
    }

    public int getCsiFactor3() {
        return csiFactor3;
    }

    public void setCsiFactor3(int csiFactor3) {
        this.csiFactor3 = csiFactor3;
    }

    public int getCsiFactor4() {
        return csiFactor4;
    }

    public void setCsiFactor4(int csiFactor4) {
        this.csiFactor4 = csiFactor4;
    }

    public int getCsiFactor5() {
        return csiFactor5;
    }

    public void setCsiFactor5(int csiFactor5) {
        this.csiFactor5 = csiFactor5;
    }

    public int getCsiFactor6() {
        return csiFactor6;
    }

    public void setCsiFactor6(int csiFactor6) {
        this.csiFactor6 = csiFactor6;
    }

    public int getCsiFactor7() {
        return csiFactor7;
    }

    public void setCsiFactor7(int csiFactor7) {
        this.csiFactor7 = csiFactor7;
    }

    public int getCsiFactor8() {
        return csiFactor8;
    }

    public void setCsiFactor8(int csiFactor8) {
        this.csiFactor8 = csiFactor8;
    }

    public int getCsiFactor9() {
        return csiFactor9;
    }

    public void setCsiFactor9(int csiFactor9) {
        this.csiFactor9 = csiFactor9;
    }

    public String getCompletedbyDesignation() {
        return completedbyDesignation;
    }

    public void setCompletedbyDesignation(String completedbyDesignation) {
        this.completedbyDesignation = completedbyDesignation;
    }

    public String getCompletedbyName() {
        return completedbyName;
    }

    public void setCompletedbyName(String completedbyName) {
        this.completedbyName = completedbyName;
    }

    public int getFollowupCsiId() {
        return followupCsiId;
    }

    public void setFollowupCsiId(int followupCsiId) {
        this.followupCsiId = followupCsiId;
    }

    public int getFollowupId() {
        return followupId;
    }

    public void setFollowupId(int followupId) {
        this.followupId = followupId;
    }

    public String getUpdatedOtherCaregiverRelationship() {
        return updatedOtherCaregiverRelationship;
    }

    public void setUpdatedOtherCaregiverRelationship(String updatedOtherCaregiverRelationship) {
        this.updatedOtherCaregiverRelationship = updatedOtherCaregiverRelationship;
    }

    public String getBirthCertificateAtEnrollment() {
        return birthCertificateAtEnrollment;
    }

    public void setBirthCertificateAtEnrollment(String birthCertificateAtEnrollment) {
        this.birthCertificateAtEnrollment = birthCertificateAtEnrollment;
    }

    public String getHivStatusAtEnrollment() {
        return hivStatusAtEnrollment;
    }

    public void setHivStatusAtEnrollment(String hivStatusAtEnrollment) {
        this.hivStatusAtEnrollment = hivStatusAtEnrollment;
    }

    public String getSchoolStatusAtEnrollment() {
        return schoolStatusAtEnrollment;
    }

    public void setSchoolStatusAtEnrollment(String schoolStatusAtEnrollment) {
        this.schoolStatusAtEnrollment = schoolStatusAtEnrollment;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getCaregiverPhone() {
        return caregiverPhone;
    }

    public void setCaregiverPhone(String caregiverPhone) {
        this.caregiverPhone = caregiverPhone;
    }

    public String getCombinedCaregiverName() {
        return combinedCaregiverName;
    }

    public void setCombinedCaregiverName(String combinedCaregiverName) {
        this.combinedCaregiverName = combinedCaregiverName;
    }

    public int getAgeAtEnrollemnt() {
        return ageAtEnrollemnt;
    }

    public void setAgeAtEnrollemnt(int ageAtEnrollemnt) {
        this.ageAtEnrollemnt = ageAtEnrollemnt;
    }

    public String getAgeUnitAtEnrollemnt() {
        return ageUnitAtEnrollemnt;
    }

    public void setAgeUnitAtEnrollemnt(String ageUnitAtEnrollemnt) {
        this.ageUnitAtEnrollemnt = ageUnitAtEnrollemnt;
    }

    public String getGenderAtEnrollemnt() {
        return genderAtEnrollemnt;
    }

    public void setGenderAtEnrollemnt(String genderAtEnrollemnt) {
        this.genderAtEnrollemnt = genderAtEnrollemnt;
    }

    public List getSchoolList() {
        return schoolList;
    }

    public void setSchoolList(List schoolList) {
        this.schoolList = schoolList;
    }

    public List getCaregiverList() {
        return caregiverList;
    }

    public void setCaregiverList(List caregiverList) {
        this.caregiverList = caregiverList;
    }

    public String getCgiverEnrolledInCare() {
        return cgiverEnrolledInCare;
    }

    public void setCgiverEnrolledInCare(String cgiverEnrolledInCare) {
        this.cgiverEnrolledInCare = cgiverEnrolledInCare;
    }

    public String getEnrolledInCare() {
        return enrolledInCare;
    }

    public void setEnrolledInCare(String enrolledInCare) {
        this.enrolledInCare = enrolledInCare;
    }

    public List getHivStatusList() {
        return hivStatusList;
    }

    public void setHivStatusList(List hivStatusList) {
        this.hivStatusList = hivStatusList;
    }

    public String getCgiverCurrentHivStatus() {
        return cgiverCurrentHivStatus;
    }

    public void setCgiverCurrentHivStatus(String cgiverCurrentHivStatus) {
        this.cgiverCurrentHivStatus = cgiverCurrentHivStatus;
    }

    public String getChildUpdatedHivStatus() {
        return childUpdatedHivStatus;
    }

    public void setChildUpdatedHivStatus(String childUpdatedHivStatus) {
        this.childUpdatedHivStatus = childUpdatedHivStatus;
    }

    public String getCgiverEnrolledOnArt() {
        return cgiverEnrolledOnArt;
    }

    public void setCgiverEnrolledOnArt(String cgiverEnrolledOnArt) {
        this.cgiverEnrolledOnArt = cgiverEnrolledOnArt;
    }

    public String getCgiverFacilityId() {
        return cgiverFacilityId;
    }

    public void setCgiverFacilityId(String cgiverFacilityId) {
        this.cgiverFacilityId = cgiverFacilityId;
    }

    public String getEnrolledOnArt() {
        return enrolledOnArt;
    }

    public void setEnrolledOnArt(String enrolledOnArt) {
        this.enrolledOnArt = enrolledOnArt;
    }

    public String getVcFacilityId() {
        return vcFacilityId;
    }

    public void setVcFacilityId(String vcFacilityId) {
        this.vcFacilityId = vcFacilityId;
    }

    public List getReferralDirectoryList() {
        return referralDirectoryList;
    }

    public void setReferralDirectoryList(List referralDirectoryList) {
        this.referralDirectoryList = referralDirectoryList;
    }

    public List getCaregiverHivStatusList() {
        return caregiverHivStatusList;
    }

    public void setCaregiverHivStatusList(List caregiverHivStatusList) {
        this.caregiverHivStatusList = caregiverHivStatusList;
    }



@Override
public void reset(ActionMapping mapping,HttpServletRequest request)
{
    ovcSno=null;
    ovcId=null;
    actionName=null;
    completedbyCbo=null;

    ward=null;
    dateEnrollment=null;
    surname=null;
    otherNames1=null;
    otherNames2=null;
    combinedNames=null;
    gender=null;
    hivStatusAtEnrollment=null;
    birthCertificateAtEnrollment=null;
    schoolStatusAtEnrollment=null;
    schoolName=null;
    age=1;
    ageUnit=null;
    address=null;
    phone=null;
    weight=0;
    height=0;
    hivStatusAtEnrollment=null;
    birthCertificateAtEnrollment=null;
    schoolStatusAtEnrollment=null;
    schoolName=null;
    combinedCaregiverName=null;
    caregiverPhone=null;
    genderAtEnrollemnt=null;
    ageAtEnrollemnt=0;
    ageUnitAtEnrollemnt=null;
    hhSerialNo=null;
    hhUniqueId=null;

    dateOfSurvey=null;
    updatedAddress=null;
    updatedAge=0;
    updatedAgeUnit=null;
    updatedGender=null;
    childCurrentHivStatus=null;
    updatedBirthCertStatus=null;
    updatedSchoolStatus=null;
    updatedSchoolName=null;
    updatedCaregiverName1=null;
    updatedCaregiverName2=null;
    updatedCaregiverGender=null;
    updatedCaregiverAge=0;
    updatedCaregiverAddress=null;
    updatedCaregiverPhone=null;
    updatedCaregiverOccupation=null;
    updatedCaregiverRelationship=null;
    updatedOtherCaregiverRelationship=null;
    cgiverHivStatus="unknown";
    reasonWithdrawal=null;
    completedbyName=null;
    completedbyDesignation=null;
    enrolledInCare="No";
    cgiverEnrolledInCare="No";
    enrolledOnArt="No";
    cgiverEnrolledOnArt="No";
    vcFacilityId=null;
    cgiverFacilityId=null;

    csiFactor1=0;
    csiFactor2=0;
    csiFactor3=0;
    csiFactor4=0;
    csiFactor5=0;
    csiFactor6=0;
    csiFactor7=0;
    csiFactor8=0;
    csiFactor9=0;
    csiFactor10=0;
    csiFactor11=0;
    csiFactor12=0;
}
private int getTotalCsiScore()
{
    int totalCsiScore=getCsiFactor1()+getCsiFactor2()+getCsiFactor3()+getCsiFactor4()+getCsiFactor5()+getCsiFactor6()+getCsiFactor7()+getCsiFactor8()+getCsiFactor9()+getCsiFactor10()+getCsiFactor11()+getCsiFactor12();
    return totalCsiScore;
}

    
    @Override
public ActionErrors validate(ActionMapping mapping,
HttpServletRequest request) {
ActionErrors errors = new ActionErrors();
OvcActionMapping ovcMapping =
(OvcActionMapping)mapping;
// Does this action require the user to login
if ( ovcMapping.isLoginRequired() ) {
HttpSession session = request.getSession();
if ( session.getAttribute("USER") == null ) {
// return null to force action to handle login
// error
return null;
}
if(getActionName()==null || getActionName().equalsIgnoreCase("delete") || getActionName().equalsIgnoreCase("cboList") || getActionName().equalsIgnoreCase("hhDetails"))
return errors;
else if(getActionName().equals("cbo") || getActionName().equals("baselineDetails") || getActionName().equals("followupDetails")|| getActionName().equals("caregiverDetails"))//
return errors;
else if(getDateOfSurvey()==null || getDateOfSurvey().length() < 1)
errors.add("dateOfSurvey", new ActionMessage("error.dateOfSurvey.required"));

else if(this.getCsiFactor6()==0)
errors.add("csiFactor6", new ActionMessage("error.healthCareServices.invalid"));
else if(this.getCsiFactor5()==0)
errors.add("csiFactor5", new ActionMessage("error.wellness.invalid"));
else if(this.getCsiFactor4()==0)
errors.add("csiFactor4", new ActionMessage("error.nutritionAndGrowth.invalid"));
else if(this.getCsiFactor3()==0)
errors.add("csiFactor3", new ActionMessage("error.foodSecurity.invalid"));
else if(this.getCsiFactor11()==0)
errors.add("csiFactor11", new ActionMessage("error.shelter.invalid"));
else if(this.getCsiFactor12()==0)
errors.add("csiFactor12", new ActionMessage("error.care.invalid"));
else if(this.getCsiFactor1()==0)
errors.add("csiFactor1", new ActionMessage("error.emotionalHealth.invalid"));
else if(this.getCsiFactor2()==0)
errors.add("csiFactor2", new ActionMessage("error.socialBehaviour.invalid"));
else if(this.getCsiFactor9()==0)
errors.add("csiFactor9", new ActionMessage("error.abuseAndExploitation.invalid"));
else if(this.getCsiFactor10()==0)
errors.add("csiFactor10", new ActionMessage("error.legalProtection.invalid"));
else if(this.getCsiFactor7()==0)
errors.add("csiFactor7", new ActionMessage("error.developmentAndPerformance.invalid"));
else if(this.getCsiFactor8()==0)
errors.add("csiFactor8", new ActionMessage("error.educationAndWork.invalid"));
//else if ( getUpdatedAgeUnit()==null || getUpdatedAge()< 1)
//errors.add("updatedAgeUnit", new ActionMessage("error.updatedAgeUnit.format"));
//else if ( getUpdatedAgeUnit().equalsIgnoreCase("Month") && getUpdatedAge() > 11)
//errors.add("age", new ActionMessage("incorrect.age.format"));
else if (getReasonWithdrawal() ==null && getTotalCsiScore() ==0)
errors.add("csiScore", new ActionMessage("incorrect.csiscore"));

}
return errors;

}
}
