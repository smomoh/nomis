/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

/**
 *
 * @author HP USER
 */
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

public class OvcForm extends ActionForm {

    public OvcForm()
    {

    }
    
private String state;
private String stateCode;
private String lga;
private String ward;
private String wardName;
private String dateEnrollment;
private String ovcSno;
private String ovcId;
private String hhUniqueId;
private String surname;
private String otherNames1;
private String otherNames2;
private String gender;
private int age;
private String ageUnit;
private String address;
private String phone;
private String[] eligibility;

private String hivStatus;
private String birthCertificate;
private String schoolStatus;
private String schoolName;
private String currentClass;
private String schoolType;
private String orphanage;
private String orphanageName;

private String hhSurname;
private String hhFirstname;
private String hhGender;
private int hhAge;
private int numOfChildrenInHh;
private int numOfOVCInHh;
private String caregiverId;
/*
private String caregiverName1;
private String caregiverName2;
private String caregiverGender;
private int caregiverAge;
private String caregiverAddress;
private String caregiverPhone;
private String caregiverOccupation;
private String caregiverMaritalStatus;
 private String cgiverHivStatus;
 private String cgiverEnrolledInCare;
private String organizationCaregiverIsReferred;*/
private String caregiverRelationships;
private String caregiverRelationships2;


private String hhSerialNo;
private int emotionalHealth;
private int socialBehaviour;
private int foodSecurity;
private int nutritionAndGrowth;
private int wellness;
private int healthCareServices;
private int developmentAndPerformance;
private int educationAndWork;
private int abuseAndExploitation;
private int legalProtection;
private int shelter;
private int care;
private String[] sourceOfInfo;
private String otherSourceOfInfo;
//private String csiDate;
private String serviceEnrollment1;
private String serviceEnrollment2;
private String serviceEnrollment3;
private String completedbyName1;
private String completedbyName2;
private String completedbyDesignation;
private String completedbyCbo;

private double weight;
private double height;
private String verifiedBy;
private String actionName;
private int childIndexId;
private String partnerCode;
private String childEnrolledInCare;
private String cgiverEnrolledOnART;
private String childEnrolledOnART;
private String organizationChildIsReferred;
private List hivStatusList=new ArrayList();
//private List caregiverList=new ArrayList();
private List caregiverHivStatusList=new ArrayList();
private List referralDirectoryList=new ArrayList();


    public int getChildIndexId() {
        return childIndexId;
    }

    public void setChildIndexId(int childIndexId) {
        this.childIndexId = childIndexId;
    }

public String getActionName() {
        return actionName;
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

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
public String getState() {
return state;
}
public void setState(String state) {
this.state = state;
}
public String getStateCode() {
    return stateCode;
}

public void setStateCode(String stateCode) {
     this.stateCode = stateCode;
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

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

public String getDateEnrollment() {
return dateEnrollment;
}
public void setDateEnrollment(String dateEnrollment) {

this.dateEnrollment = dateEnrollment;
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

    public String[] getEligibility() {
        return eligibility;
    }

    public void setEligibility(String[] eligibility) {
        this.eligibility = eligibility;
    }


public String getHivStatus() {
return hivStatus;
}
public void setHivStatus(String hivStatus) {
this.hivStatus = hivStatus;
}

public String getBirthCertificate() {
return birthCertificate;
}
public void setBirthCertificate(String birthCertificate) {
this.birthCertificate = birthCertificate;
}

public String getSchoolStatus() {
return schoolStatus;
}
public void setSchoolStatus(String schoolStatus) {
this.schoolStatus = schoolStatus;
}

public String getSchoolName() {
return schoolName;
}
public void setSchoolName(String schoolName) {
this.schoolName = schoolName;
}

public String getCurrentClass() {
return currentClass;
}
public void setCurrentClass(String currentClass) {
this.currentClass = currentClass;
}

public String getSchoolType() {
return schoolType;
}
public void setSchoolType(String schoolType) {
this.schoolType = schoolType;
}

public String getOrphanage() {
return orphanage;
}
public void setOrphanage(String orphanage) {
this.orphanage = orphanage;
}

public String getOrphanageName() {
return orphanageName;
}
public void setOrphanageName(String orphanageName) {
this.orphanageName = orphanageName;
}

    public int getHhAge() {
        return hhAge;
    }

    public void setHhAge(int hhAge) {
        this.hhAge = hhAge;
    }

    public String getHhGender() {
        return hhGender;
    }

    public void setHhGender(String hhGender) {
        this.hhGender = hhGender;
    }

    public String getHhFirstname() {
        return hhFirstname;
    }

    public void setHhFirstname(String hhFirstname) {
        this.hhFirstname = hhFirstname;
    }

    public String getHhSurname() {
        return hhSurname;
    }

    public void setHhSurname(String hhSurname) {
        this.hhSurname = hhSurname;
    }

    public int getNumOfChildrenInHh() {
        return numOfChildrenInHh;
    }

    public void setNumOfChildrenInHh(int numOfChildrenInHh) {
        this.numOfChildrenInHh = numOfChildrenInHh;
    }

    public int getNumOfOVCInHh() {
        return numOfOVCInHh;
    }

    public void setNumOfOVCInHh(int numOfOVCInHh) {
        this.numOfOVCInHh = numOfOVCInHh;
    }

    public String getCaregiverId() {
        return caregiverId;
    }

    public void setCaregiverId(String caregiverId) {
        this.caregiverId = caregiverId;
    }

public String getCaregiverRelationships() {
return caregiverRelationships;
}
public void setCaregiverRelationships(String caregiverRelationships) {
this.caregiverRelationships = caregiverRelationships;
}

public String getCaregiverRelationships2() {
return caregiverRelationships2;
}
public void setCaregiverRelationships2(String caregiverRelationships2) {
this.caregiverRelationships2 = caregiverRelationships2;
}

    public int getAbuseAndExploitation() {
        return abuseAndExploitation;
    }

    public void setAbuseAndExploitation(int abuseAndExploitation) {
        this.abuseAndExploitation = abuseAndExploitation;
    }

    public int getCare() {
        return care;
    }

    public void setCare(int care) {
        this.care = care;
    }

    public int getDevelopmentAndPerformance() {
        return developmentAndPerformance;
    }

    public void setDevelopmentAndPerformance(int developmentAndPerformance) {
        this.developmentAndPerformance = developmentAndPerformance;
    }

    public int getEducationAndWork() {
        return educationAndWork;
    }

    public void setEducationAndWork(int educationAndWork) {
        this.educationAndWork = educationAndWork;
    }

    public int getEmotionalHealth() {
        return emotionalHealth;
    }

    public void setEmotionalHealth(int emotionalHealth) {
        this.emotionalHealth = emotionalHealth;
    }

    public int getFoodSecurity() {
        return foodSecurity;
    }

    public void setFoodSecurity(int foodSecurity) {
        this.foodSecurity = foodSecurity;
    }

    public int getHealthCareServices() {
        return healthCareServices;
    }

    public void setHealthCareServices(int healthCareServices) {
        this.healthCareServices = healthCareServices;
    }

    public int getLegalProtection() {
        return legalProtection;
    }

    public void setLegalProtection(int legalProtection) {
        this.legalProtection = legalProtection;
    }

    public int getNutritionAndGrowth() {
        return nutritionAndGrowth;
    }

    public void setNutritionAndGrowth(int nutritionAndGrowth) {
        this.nutritionAndGrowth = nutritionAndGrowth;
    }

    public int getShelter() {
        return shelter;
    }

    public void setShelter(int shelter) {
        this.shelter = shelter;
    }

    public int getSocialBehaviour() {
        return socialBehaviour;
    }

    public void setSocialBehaviour(int socialBehaviour) {
        this.socialBehaviour = socialBehaviour;
    }

    public int getWellness() {
        return wellness;
    }

    public void setWellness(int wellness) {
        this.wellness = wellness;
    }

/*public int getCsiFactor1() {
return emotionalHealth;
}
public void setCsiFactor1(int emotionalHealth) {
this.emotionalHealth = emotionalHealth;
}

public int getCsiFactor2() {
return socialBehaviour;
}
public void setCsiFactor2(int socialBehaviour) {
this.socialBehaviour = socialBehaviour;
}

public int getCsiFactor3() {
return foodSecurity;
}
public void setCsiFactor3(int foodSecurity) {
this.foodSecurity = foodSecurity;
}

public int getCsiFactor4() {
return nutritionAndGrowth;
}
public void setCsiFactor4(int nutritionAndGrowth) {
this.nutritionAndGrowth = nutritionAndGrowth;
}

public int getCsiFactor5() {
return wellness;
}
public void setCsiFactor5(int wellness) {
this.wellness = wellness;
}

public int getCsiFactor6() {
return healthCareServices;
}
public void setCsiFactor6(int healthCareServices) {
this.healthCareServices = healthCareServices;
}

public int getCsiFactor7() {
return developmentAndPerformance;
}
public void setCsiFactor7(int developmentAndPerformance) {
this.developmentAndPerformance = developmentAndPerformance;
}

public int getCsiFactor8() {
return educationAndWork;
}
public void setCsiFactor8(int educationAndWork) {
this.educationAndWork = educationAndWork;
}

public int getCsiFactor9() {
return abuseAndExploitation;
}
public void setCsiFactor9(int abuseAndExploitation) {
this.abuseAndExploitation = abuseAndExploitation;
}

public int getCsiFactor10() {
return legalProtection;
}
public void setCsiFactor10(int legalProtection) {
this.legalProtection = legalProtection;
}

public int getCsiFactor11() {
return shelter;
}
public void setCsiFactor11(int shelter) {
this.shelter = shelter;
}

public int getCsiFactor12() {
return care;
}
public void setCsiFactor12(int care) {
this.care = care;
}*/

    public String[] getSourceOfInfo() {
        return sourceOfInfo;
    }

    public void setSourceOfInfo(String[] sourceOfInfo) {
        this.sourceOfInfo = sourceOfInfo;
    }



public String getServiceEnrollment1() {
return serviceEnrollment1;
}
public void setServiceEnrollment1(String serviceEnrollment1) {
this.serviceEnrollment1 = serviceEnrollment1;
}

public String getServiceEnrollment2() {
return serviceEnrollment2;
}
public void setServiceEnrollment2(String serviceEnrollment2) {
this.serviceEnrollment2 = serviceEnrollment2;
}

public String getServiceEnrollment3() {
return serviceEnrollment3;
}
public void setServiceEnrollment3(String serviceEnrollment3) {
this.serviceEnrollment3 = serviceEnrollment3;
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

public String getCompletedbyCbo() {

return completedbyCbo;
}
public void setCompletedbyCbo(String completedbyCbo) {
this.completedbyCbo = completedbyCbo;
}

    public List getHivStatusList() {
        return hivStatusList;
    }

    public void setHivStatusList(List hivStatusList) {
        this.hivStatusList = hivStatusList;
    }

    public List getReferralDirectoryList() {
        return referralDirectoryList;
    }

    public void setReferralDirectoryList(List referralDirectoryList) {
        this.referralDirectoryList = referralDirectoryList;
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


     public String getVerifiedBy() {
        return verifiedBy;
    }

    public void setVerifiedBy(String verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    public String getOtherSourceOfInfo() {
        return otherSourceOfInfo;
    }

    public void setOtherSourceOfInfo(String otherSourceOfInfo) {
        this.otherSourceOfInfo = otherSourceOfInfo;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }
    

public void setLastWard(HttpSession session,String lastWard)
{
    session.setAttribute("lastWard", lastWard);
}

    public String getChildEnrolledInCare() {
        return childEnrolledInCare;
    }

    public void setChildEnrolledInCare(String childEnrolledInCare) {
        this.childEnrolledInCare = childEnrolledInCare;
    }

    public String getOrganizationChildIsReferred() {
        return organizationChildIsReferred;
    }

    public void setOrganizationChildIsReferred(String organizationChildIsReferred) {
        this.organizationChildIsReferred = organizationChildIsReferred;
    }

    public String getCgiverEnrolledOnART() {
        return cgiverEnrolledOnART;
    }

    public void setCgiverEnrolledOnART(String cgiverEnrolledOnART) {
        this.cgiverEnrolledOnART = cgiverEnrolledOnART;
    }

    public String getChildEnrolledOnART() {
        return childEnrolledOnART;
    }

    public void setChildEnrolledOnART(String childEnrolledOnART) {
        this.childEnrolledOnART = childEnrolledOnART;
    }
private boolean childFullyAssessed()
{
    boolean childFullyAssessed=false;
    
    return childFullyAssessed;
}
    @Override
public void reset(ActionMapping mapping,
HttpServletRequest request) {

this.state = null;
this.lga = null;
this.ward = null;
this.dateEnrollment = null;
this.ovcId = null;
this.ovcSno = null;
this.surname = null;
this.otherNames1 = null;
this.otherNames2 = null;
this.gender = null;
this.age = 0;
this.ageUnit = null;
this.address = null;
this.phone = null;
eligibility=null;
sourceOfInfo=null;
otherSourceOfInfo=null;
hhSurname=null;
hhFirstname=null;
hhGender=null;
hhAge=0;
numOfChildrenInHh=0;
numOfOVCInHh=0;

this.hivStatus = null;
hhUniqueId=null;
this.birthCertificate = null;
this.schoolStatus = null;
this.schoolName = null;
this.currentClass = null;
this.schoolType = null;
this.orphanage = null;
this.orphanageName = null;
this.caregiverRelationships = null;
this.caregiverRelationships2 = null;
hhSerialNo=null;
this.emotionalHealth = 0;
this.socialBehaviour = 0;
this.foodSecurity = 0;
this.nutritionAndGrowth = 0;
this.wellness = 0;
this.healthCareServices = 0;
this.developmentAndPerformance = 0;
this.educationAndWork = 0;
this.abuseAndExploitation = 0;
this.legalProtection = 0;
this.shelter = 0;
this.care = 0;
//this.csiDate = null;
this.serviceEnrollment1 = null;
this.serviceEnrollment2 = null;
this.serviceEnrollment3 = null;
this.completedbyName1 = null;
this.completedbyName2 = null;
this.completedbyDesignation = null;
this.completedbyCbo = null;
wardName=null;
childEnrolledInCare="No";
cgiverEnrolledOnART="No";
childEnrolledOnART="No";
this.weight = 0;
this.height = 0;
this.verifiedBy = null;


}


    @Override
public ActionErrors validate(ActionMapping mapping,
HttpServletRequest request)
{
ActionErrors errors = new ActionErrors();
//OvcActionMapping ovcMapping =(OvcActionMapping)mapping;
HttpSession session = request.getSession();

if(getActionName() ==null || (getActionName().equalsIgnoreCase("cboList")) || (getActionName().equalsIgnoreCase("wardList")) || (getActionName().equalsIgnoreCase("delete")) || getActionName().equalsIgnoreCase("hhDetails") || getActionName().equalsIgnoreCase("ovcDetails"))
{
        return errors;
}

if(getState()==null || getState().length() < 1)
errors.add("state", new ActionMessage("error.state.required"));
else if(getLga()==null || getLga().length() < 1)
errors.add("lga", new ActionMessage("error.lga.required"));
else if(getCompletedbyCbo()==null || getCompletedbyCbo().length() < 1)
errors.add("cbo", new ActionMessage("error.cbo.required"));
/*else if(getWard()==null || getWard().length() < 1)
errors.add("ward", new ActionMessage("error.ward.required"));*/
else if(getSurname()==null || getSurname().length() < 1)
errors.add("surname", new ActionMessage("error.surname.required"));
else if(getOtherNames1()==null || getOtherNames1().length() < 1)
errors.add("othernames1", new ActionMessage("error.othername.required"));
else if(this.getOtherNames1().length()> 38)
errors.add("othernamelength", new ActionMessage("error.othernames1.length"));
else if(getDateEnrollment()==null || getDateEnrollment().length() < 1)
errors.add("date", new ActionMessage("error.enrollmentDate.required"));
else if(getDateEnrollment().indexOf("/") == -1)
errors.add("date", new ActionMessage("error.enrollmentDate.wrong"));

else if(getGender()==null || getGender().length() < 1)
errors.add("gender", new ActionMessage("error.gender.required"));
else if(getAgeUnit()==null || getAgeUnit().length() < 1)
errors.add("ageUnit", new ActionMessage("error.ageUnit.required"));
else if ( getAgeUnit() != null && getAgeUnit().equalsIgnoreCase("Month") && getAge() > 11)
errors.add("age", new ActionMessage("incorrect.age.format"));

else if(this.getAddress() !=null && getAddress().length()>300)
errors.add("address", new ActionMessage("error.address.length"));
else if(getCurrentClass()!=null && getCurrentClass().length()> 25)
errors.add("currentclass", new ActionMessage("error.currentclass.length"));
else if(getPhone() !=null && getPhone().length()> 15)
errors.add("phone", new ActionMessage("error.phone.length"));
else if(getSurname() !=null && getSurname().length()> 25)
errors.add("surname", new ActionMessage("error.surname.length"));
else if(this.getOtherNames2() !=null && (this.getOtherNames1().length()+this.getOtherNames2().length())> 38)
errors.add("othernames", new ActionMessage("error.othernames.length"));

else if(this.getHivStatus() ==null || this.getHivStatus().trim().equalsIgnoreCase("select"))
errors.add("hivStatus", new ActionMessage("error.childhivstatus.required"));

/*else if(this.getCaregiverName1() ==null || this.getCaregiverName1().length() < 3)
errors.add("caregivernames", new ActionMessage("error.caregivernames.required"));
else if(this.getCaregiverName2() ==null || this.getCaregiverName2().length() < 3)
errors.add("caregivername2", new ActionMessage("error.caregivernames.required"));
else if(this.caregiverAge <10)
errors.add("caregiverAge", new ActionMessage("error.caregiverAge.required"));
else if(this.caregiverAge >150)
errors.add("caregiverAge", new ActionMessage("error.caregiverAge.overrated"));
else if(this.getCaregiverGender() ==null || this.getCaregiverGender().length() <1)
errors.add("caregiverGender", new ActionMessage("error.caregiverGender.length"));

else if(this.getCaregiverName1() !=null && this.getCaregiverName1().length() > 38)
errors.add("caregivernames", new ActionMessage("error.caregivernames.length"));
else if(this.getCaregiverName2() !=null && this.getCaregiverName2().length() > 38)
errors.add("caregivernames2", new ActionMessage("error.caregivernames.length"));

else if((this.getCaregiverName1() !=null && this.getCaregiverName2() !=null) && (this.getCaregiverName1().length()+this.getCaregiverName2().length()) > 38)
errors.add("caregivernames12", new ActionMessage("error.caregivernames.length"));

else if(getCaregiverPhone() !=null && getCaregiverPhone().length() > 15)
errors.add("caregiverphone", new ActionMessage("error.caregiverphone.length"));
else if(getCaregiverOccupation() !=null && getCaregiverOccupation().length() > 24)
errors.add("occupation", new ActionMessage("error.occupation.length"));*/

else if(this.getCaregiverId()==null || getCaregiverId().trim().length()==0)
errors.add("caregiverId", new ActionMessage("error.caregivernames.required"));
else if(getCaregiverRelationships2() !=null && getCaregiverRelationships2().length() > 40)
errors.add("caregiverRelationship2", new ActionMessage("error.relationship.length"));
else if(getCaregiverRelationships() ==null || getCaregiverRelationships().trim().length()<3)
errors.add("caregiverRelationship", new ActionMessage("error.relationship.length"));

else if(getCompletedbyName1() !=null && getCompletedbyName1().length() > 38) 
errors.add("completedbyname1", new ActionMessage("error.completedbyname.length"));
else if(getCompletedbyName2() !=null && getCompletedbyName2().length() > 38) 
errors.add("completedbyname2", new ActionMessage("error.completedbyname.length"));

else if((getCompletedbyName1() !=null && getCompletedbyName2() !=null) &&(getCompletedbyName1().length()+getCompletedbyName2().length()) > 38)
errors.add("completedbyname", new ActionMessage("error.completedbyname.length"));

else if(getCompletedbyDesignation() !=null && getCompletedbyDesignation().length() > 25)
errors.add("completedbydesignation", new ActionMessage("error.completedbydesignation.length"));

else if(this.getHealthCareServices()==0)
errors.add("healthCareServices", new ActionMessage("error.healthCareServices.invalid"));
else if(this.getWellness()==0)
errors.add("wellness", new ActionMessage("error.wellness.invalid"));
else if(this.getNutritionAndGrowth()==0)
errors.add("nutritionAndGrowth", new ActionMessage("error.nutritionAndGrowth.invalid"));
else if(this.getFoodSecurity()==0)
errors.add("foodSecurity", new ActionMessage("error.foodSecurity.invalid"));
else if(this.getShelter()==0)
errors.add("shelter", new ActionMessage("error.shelter.invalid"));
else if(this.getCare()==0)
errors.add("care", new ActionMessage("error.care.invalid"));
else if(this.getEmotionalHealth()==0)
errors.add("emotionalHealth", new ActionMessage("error.emotionalHealth.invalid"));
else if(this.getSocialBehaviour()==0)
errors.add("socialBehaviour", new ActionMessage("error.socialBehaviour.invalid"));
else if(this.getAbuseAndExploitation()==0)
errors.add("abuseAndExploitation", new ActionMessage("error.abuseAndExploitation.invalid"));
else if(this.getLegalProtection()==0)
errors.add("legalProtection", new ActionMessage("error.legalProtection.invalid"));
else if(this.getDevelopmentAndPerformance()==0)
errors.add("developmentAndPerformance", new ActionMessage("error.developmentAndPerformance.invalid"));
else if(this.getEducationAndWork()==0)
errors.add("educationAndWork", new ActionMessage("error.educationAndWork.invalid"));

if(!errors.isEmpty())
{
    if(getActionName().equalsIgnoreCase("modify") || getActionName().equalsIgnoreCase("delete"))
    {
        session.setAttribute("enableModify", "false");
        session.setAttribute("enableSave", "true");
    }
    else if(getActionName().equalsIgnoreCase("save"))
    {
        session.setAttribute("enableSave", "false");
        session.setAttribute("enableModify", "true");
    }
}
else if(getDateEnrollment().indexOf("/") != -1)
{
    String[] dateArray=getDateEnrollment().split("/");
    if(dateArray.length != 3)
    errors.add("date", new ActionMessage("error.enrollmentDate.wrong"));
}
return errors;

}
}
