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
public class NationalHivRiskAssessmentForm extends org.apache.struts.action.ActionForm {
    
    private int recordId;
    private String actionName;
    private String stateCode;
    private String stateName;
    private String lgaCode;
    private String orgCode;
    private String community;
    
    private int age;
    private String ageUnit;
    private String hhUniqueId;
    private String ovcId;
    private String surname;
    private String firstname;
    private String gender;
    private String address;
    private String phone;
    private String occupation;
    private String dateOfAssessment;
    private int hhSerialNo;    
    private String childAgeQuestion;
    private String childAgeComment;
    private String childTestedQuestion;
    private String childTestedComment;
    private String hivParentQuestion;
    private String hivParentComment;
    private String childSickQuestion;
    private String childSickComment;
    private String skinProblemQuestion="N/A";
    private String skinProblemComment;
    private String parentDeceasedQuestion="N/A";
    private String parentDeceasedComment;
    private String chronicallyIllQuestion;
    private String chronicallyIllComment;
    private String schoolGradeQuestion="N/A";
    private String schoolGradeComment;
    private String sexualAbuseQuestion;
    private String sexualAbuseComment;
    private String sexuallyActiveQuestion;
    private String sexuallyActiveComment;
    private String genitalDischargeQuestion;
    private String genitalDischargeComment;
    private String childAtRiskQuestion;
    private String bloodTransfusionQuestion;
    private String bloodTransfusionComment;
    private String muacbmiQuestion;
    private String muacbmiComment;
    private String hivStatusQuestion;
    private String hivStatus;
    private String serviceProviderName;
    private String designation;
    private String serviceProviderPhone;
    private List ovcList=new ArrayList();
    private List lgaList=new ArrayList();
    private List cboList=new ArrayList();
    
    
    private String parentKnowHivStatusQuestion;
    private String parentKnowHivStatusComment;
    private String tbOrNutritionQuestion;
    private String tbOrNutritionComment;
    private String childHeadedHhQuestion;
    private String childHeadedHhComment;
    private String teenMotherQuestion;
    private String teenMotherComment;
    private String diverseSexQuestion;
    private String diverseSexComment;
    private String outOfSchoolQuestion;
    private String outOfSchoolComment;
    
    
    public NationalHivRiskAssessmentForm() {
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

    public String getBloodTransfusionComment() {
        return bloodTransfusionComment;
    }

    public void setBloodTransfusionComment(String bloodTransfusionComment) {
        this.bloodTransfusionComment = bloodTransfusionComment;
    }

    public String getBloodTransfusionQuestion() {
        return bloodTransfusionQuestion;
    }

    public void setBloodTransfusionQuestion(String bloodTransfusionQuestion) {
        this.bloodTransfusionQuestion = bloodTransfusionQuestion;
    }

    public List getCboList() {
        return cboList;
    }

    public void setCboList(List cboList) {
        this.cboList = cboList;
    }

    public String getChildAgeComment() {
        return childAgeComment;
    }

    public void setChildAgeComment(String childAgeComment) {
        this.childAgeComment = childAgeComment;
    }

    public String getChildAgeQuestion() {
        return childAgeQuestion;
    }

    public void setChildAgeQuestion(String childAgeQuestion) {
        this.childAgeQuestion = childAgeQuestion;
    }

    public String getChildAtRiskQuestion() {
        return childAtRiskQuestion;
    }

    public void setChildAtRiskQuestion(String childAtRiskQuestion) {
        this.childAtRiskQuestion = childAtRiskQuestion;
    }

    public String getChildSickComment() {
        return childSickComment;
    }

    public void setChildSickComment(String childSickComment) {
        this.childSickComment = childSickComment;
    }

    public String getChildSickQuestion() {
        return childSickQuestion;
    }

    public void setChildSickQuestion(String childSickQuestion) {
        this.childSickQuestion = childSickQuestion;
    }

    public String getChildTestedComment() {
        return childTestedComment;
    }

    public void setChildTestedComment(String childTestedComment) {
        this.childTestedComment = childTestedComment;
    }

    public String getChildTestedQuestion() {
        return childTestedQuestion;
    }

    public void setChildTestedQuestion(String childTestedQuestion) {
        this.childTestedQuestion = childTestedQuestion;
    }

    public String getChronicallyIllComment() {
        return chronicallyIllComment;
    }

    public void setChronicallyIllComment(String chronicallyIllComment) {
        this.chronicallyIllComment = chronicallyIllComment;
    }

    public String getChronicallyIllQuestion() {
        return chronicallyIllQuestion;
    }

    public void setChronicallyIllQuestion(String chronicallyIllQuestion) {
        this.chronicallyIllQuestion = chronicallyIllQuestion;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGenitalDischargeComment() {
        return genitalDischargeComment;
    }

    public void setGenitalDischargeComment(String genitalDischargeComment) {
        this.genitalDischargeComment = genitalDischargeComment;
    }

    public String getGenitalDischargeQuestion() {
        return genitalDischargeQuestion;
    }

    public void setGenitalDischargeQuestion(String genitalDischargeQuestion) {
        this.genitalDischargeQuestion = genitalDischargeQuestion;
    }

    public int getHhSerialNo() {
        return hhSerialNo;
    }

    public void setHhSerialNo(int hhSerialNo) {
        this.hhSerialNo = hhSerialNo;
    }

    public String getHhUniqueId() {
        return hhUniqueId;
    }

    public void setHhUniqueId(String hhUniqueId) {
        this.hhUniqueId = hhUniqueId;
    }

    public String getHivParentComment() {
        return hivParentComment;
    }

    public void setHivParentComment(String hivParentComment) {
        this.hivParentComment = hivParentComment;
    }

    public String getHivParentQuestion() {
        return hivParentQuestion;
    }

    public void setHivParentQuestion(String hivParentQuestion) {
        this.hivParentQuestion = hivParentQuestion;
    }

    public String getHivStatus() {
        return hivStatus;
    }

    public void setHivStatus(String hivStatus) {
        this.hivStatus = hivStatus;
    }

    public String getHivStatusQuestion() {
        return hivStatusQuestion;
    }

    public void setHivStatusQuestion(String hivStatusQuestion) {
        this.hivStatusQuestion = hivStatusQuestion;
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

    public String getMuacbmiComment() {
        return muacbmiComment;
    }

    public void setMuacbmiComment(String muacbmiComment) {
        this.muacbmiComment = muacbmiComment;
    }

    public String getMuacbmiQuestion() {
        return muacbmiQuestion;
    }

    public void setMuacbmiQuestion(String muacbmiQuestion) {
        this.muacbmiQuestion = muacbmiQuestion;
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

    public String getOvcId() {
        return ovcId;
    }

    public void setOvcId(String ovcId) {
        this.ovcId = ovcId;
    }

    public List getOvcList() {
        return ovcList;
    }

    public void setOvcList(List ovcList) {
        this.ovcList = ovcList;
    }

    public String getParentDeceasedComment() {
        return parentDeceasedComment;
    }

    public void setParentDeceasedComment(String parentDeceasedComment) {
        this.parentDeceasedComment = parentDeceasedComment;
    }

    public String getParentDeceasedQuestion() {
        return parentDeceasedQuestion;
    }

    public void setParentDeceasedQuestion(String parentDeceasedQuestion) {
        this.parentDeceasedQuestion = parentDeceasedQuestion;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getSchoolGradeComment() {
        return schoolGradeComment;
    }

    public void setSchoolGradeComment(String schoolGradeComment) {
        this.schoolGradeComment = schoolGradeComment;
    }

    public String getSchoolGradeQuestion() {
        return schoolGradeQuestion;
    }

    public void setSchoolGradeQuestion(String schoolGradeQuestion) {
        this.schoolGradeQuestion = schoolGradeQuestion;
    }

    public String getServiceProviderName() {
        return serviceProviderName;
    }

    public void setServiceProviderName(String serviceProviderName) {
        this.serviceProviderName = serviceProviderName;
    }

    public String getServiceProviderPhone() {
        return serviceProviderPhone;
    }

    public void setServiceProviderPhone(String serviceProviderPhone) {
        this.serviceProviderPhone = serviceProviderPhone;
    }

    public String getSexualAbuseComment() {
        return sexualAbuseComment;
    }

    public void setSexualAbuseComment(String sexualAbuseComment) {
        this.sexualAbuseComment = sexualAbuseComment;
    }

    public String getSexualAbuseQuestion() {
        return sexualAbuseQuestion;
    }

    public void setSexualAbuseQuestion(String sexualAbuseQuestion) {
        this.sexualAbuseQuestion = sexualAbuseQuestion;
    }

    public String getSexuallyActiveComment() {
        return sexuallyActiveComment;
    }

    public void setSexuallyActiveComment(String sexuallyActiveComment) {
        this.sexuallyActiveComment = sexuallyActiveComment;
    }

    public String getSexuallyActiveQuestion() {
        return sexuallyActiveQuestion;
    }

    public void setSexuallyActiveQuestion(String sexuallyActiveQuestion) {
        this.sexuallyActiveQuestion = sexuallyActiveQuestion;
    }

    public String getSkinProblemComment() {
        return skinProblemComment;
    }

    public void setSkinProblemComment(String skinProblemComment) {
        this.skinProblemComment = skinProblemComment;
    }

    public String getSkinProblemQuestion() {
        return skinProblemQuestion;
    }

    public void setSkinProblemQuestion(String skinProblemQuestion) {
        this.skinProblemQuestion = skinProblemQuestion;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getChildHeadedHhComment() {
        return childHeadedHhComment;
    }

    public void setChildHeadedHhComment(String childHeadedHhComment) {
        this.childHeadedHhComment = childHeadedHhComment;
    }

    public String getChildHeadedHhQuestion() {
        return childHeadedHhQuestion;
    }

    public void setChildHeadedHhQuestion(String childHeadedHhQuestion) {
        this.childHeadedHhQuestion = childHeadedHhQuestion;
    }

    public String getDiverseSexComment() {
        return diverseSexComment;
    }

    public void setDiverseSexComment(String diverseSexComment) {
        this.diverseSexComment = diverseSexComment;
    }

    public String getDiverseSexQuestion() {
        return diverseSexQuestion;
    }

    public void setDiverseSexQuestion(String diverseSexQuestion) {
        this.diverseSexQuestion = diverseSexQuestion;
    }

    public String getParentKnowHivStatusComment() {
        return parentKnowHivStatusComment;
    }

    public void setParentKnowHivStatusComment(String parentKnowHivStatusComment) {
        this.parentKnowHivStatusComment = parentKnowHivStatusComment;
    }

    public String getParentKnowHivStatusQuestion() {
        return parentKnowHivStatusQuestion;
    }

    public void setParentKnowHivStatusQuestion(String parentKnowHivStatusQuestion) {
        this.parentKnowHivStatusQuestion = parentKnowHivStatusQuestion;
    }

    public String getTbOrNutritionComment() {
        return tbOrNutritionComment;
    }

    public void setTbOrNutritionComment(String tbOrNutritionComment) {
        this.tbOrNutritionComment = tbOrNutritionComment;
    }

    public String getTbOrNutritionQuestion() {
        return tbOrNutritionQuestion;
    }

    public void setTbOrNutritionQuestion(String tbOrNutritionQuestion) {
        this.tbOrNutritionQuestion = tbOrNutritionQuestion;
    }

    public String getTeenMotherComment() {
        return teenMotherComment;
    }

    public void setTeenMotherComment(String teenMotherComment) {
        this.teenMotherComment = teenMotherComment;
    }

    public String getTeenMotherQuestion() {
        return teenMotherQuestion;
    }

    public void setTeenMotherQuestion(String teenMotherQuestion) {
        this.teenMotherQuestion = teenMotherQuestion;
    }

    public String getOutOfSchoolComment() {
        return outOfSchoolComment;
    }

    public void setOutOfSchoolComment(String outOfSchoolComment) {
        this.outOfSchoolComment = outOfSchoolComment;
    }

    public String getOutOfSchoolQuestion() {
        return outOfSchoolQuestion;
    }

    public void setOutOfSchoolQuestion(String outOfSchoolQuestion) {
        this.outOfSchoolQuestion = outOfSchoolQuestion;
    }
    
@Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        actionName=null;
    
    
     age=0;
    ageUnit=null;
    hhUniqueId=null;
    ovcId=null;
    surname=null;
    firstname=null;
    gender=null;
    address=null;
    phone=null;
    occupation=null;
    dateOfAssessment=null;
     hhSerialNo=0;    
    //childAgeQuestion="No";
    childAgeComment=null;
    childTestedQuestion=null;
    childTestedComment=null;
    hivParentQuestion=null;
    hivParentComment=null;
    childSickQuestion=null;
    childSickComment=null;
    skinProblemQuestion="N/A";
    skinProblemComment=null;
    parentDeceasedQuestion="N/A";
    parentDeceasedComment=null;
    chronicallyIllQuestion=null;
    chronicallyIllComment=null;
    schoolGradeQuestion=null;
    schoolGradeComment="N/A";
    sexualAbuseQuestion=null;
    sexualAbuseComment=null;
    sexuallyActiveQuestion="N/A";
    sexuallyActiveComment=null;
    muacbmiQuestion=null;
    muacbmiComment=null;
    bloodTransfusionQuestion=null;
    bloodTransfusionComment=null;
    hivStatusQuestion=null;
    hivStatus=null;
    genitalDischargeQuestion="N/A";
    genitalDischargeComment=null;
    childAtRiskQuestion="N/A";
    serviceProviderName=null;
    designation=null;
    serviceProviderPhone=null;
    
    parentKnowHivStatusQuestion=null;
    parentKnowHivStatusComment=null;
    tbOrNutritionQuestion=null;
    tbOrNutritionComment=null;
    childHeadedHhQuestion=null;
    childHeadedHhComment=null;
    teenMotherQuestion=null;
    teenMotherComment=null;
    diverseSexQuestion=null;
    diverseSexComment=null;
    outOfSchoolQuestion=null;
    outOfSchoolComment=null;
    }
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if(getActionName()==null || getActionName().equalsIgnoreCase("hhDetails") || getActionName().equalsIgnoreCase("childDetails") || getActionName().equalsIgnoreCase("cboList") || getActionName().equalsIgnoreCase("delete") || getActionName().equalsIgnoreCase("assessmentDetails"))
        return errors;
        else if(getDateOfAssessment()==null || getDateOfAssessment().length() < 1 )
        errors.add("dateOfAssessment", new ActionMessage("errors.dateOfAssessment.required"));
        else if(getDateOfAssessment().indexOf("/") == -1 && getDateOfAssessment().indexOf("-") ==-1 )
        errors.add("dateOfAssessment", new ActionMessage("errors.dateOfAssessment.required"));
        else if(getChildSickQuestion()==null || getChildSickQuestion().length() < 1 )
        errors.add("childSickQuestion", new ActionMessage("errors.childSickQuestion.required"));
        else if(this.getHivStatusQuestion()==null || getHivStatusQuestion().length() < 1 )
        errors.add("hivStatusQuestion", new ActionMessage("errors.hivStatusQuestion.required"));
        else if(getChronicallyIllQuestion()==null || getChronicallyIllQuestion().length() < 1 )
        errors.add("chronicallyIllQuestion", new ActionMessage("errors.chronicallyIllQuestion.required"));
        else if(this.getBloodTransfusionQuestion()==null || this.getBloodTransfusionQuestion().length() < 1 )
        errors.add("bloodTransfusionQuestion", new ActionMessage("errors.bloodTransfusionQuestion.required"));
        else if(getSexualAbuseQuestion()==null || getSexualAbuseQuestion().length() < 1 )
        errors.add("sexualAbuseQuestion", new ActionMessage("errors.sexualAbuseQuestion.required"));
        else if(this.getHivStatusQuestion() !=null && this.getHivStatusQuestion().equalsIgnoreCase("Yes") )
        {
            if(this.getHivStatus()==null || this.getHivStatus().length() < 1)
            errors.add("hivStatus", new ActionMessage("errors.hivStatus.required"));
        }
        return errors;
    }
}
