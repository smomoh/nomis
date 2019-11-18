/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author smomoh
 */
public class CaregiverTBHIVScreeningForm extends org.apache.struts.action.ActionForm {
    
    private String actionName;
private int hhSerialNo;
private String stateCode;
private String lgaCode;
private String orgCode;
private String communityCode;
private String hhUniqueId;
private String caregiverId;
private String gender;
private int age;
private String ageUnit;
private String dateOfAssessment;
private int id;
private int assessmentNo;
private String caregiverCoughing;
private String caregiverLossindWeight;
private String caregiverHavingFever;
private String caregiverHavingNightSweat;
private String familyMemberHadTB;
private String dosesOfARVMissedByCaregiver;
private String caregiverEnrolledInPMTCT;
private String eidTestingDoneForChild;
private String caregiverOnSeptrin;
private String hivStatusDisclosedToPartner;
private String hivStatusOfPartnerKnown;
private String dosesOfSeptrinMissedByCaregiver;
private String childAboveSixWksAndStartedOnSeptrin;
private String caregiverSleepInTreatedNet;
private String beneficiariesHasSoresOrBleeding;
private String numberOfClubsAttndedInThreeMths;
private String caregiverSwellingOnNeck;
private String volunteerName;
private String designation;

    public CaregiverTBHIVScreeningForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
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

    public int getAssessmentNo() {
        return assessmentNo;
    }

    public void setAssessmentNo(int assessmentNo) {
        this.assessmentNo = assessmentNo;
    }

    public String getBeneficiariesHasSoresOrBleeding() {
        return beneficiariesHasSoresOrBleeding;
    }

    public void setBeneficiariesHasSoresOrBleeding(String beneficiariesHasSoresOrBleeding) {
        this.beneficiariesHasSoresOrBleeding = beneficiariesHasSoresOrBleeding;
    }

    public String getChildAboveSixWksAndStartedOnSeptrin() {
        return childAboveSixWksAndStartedOnSeptrin;
    }

    public void setChildAboveSixWksAndStartedOnSeptrin(String childAboveSixWksAndStartedOnSeptrin) {
        this.childAboveSixWksAndStartedOnSeptrin = childAboveSixWksAndStartedOnSeptrin;
    }

    public String getCaregiverCoughing() {
        return caregiverCoughing;
    }

    public void setCaregiverCoughing(String caregiverCoughing) {
        this.caregiverCoughing = caregiverCoughing;
    }

    public String getCaregiverEnrolledInPMTCT() {
        return caregiverEnrolledInPMTCT;
    }

    public void setCaregiverEnrolledInPMTCT(String caregiverEnrolledInPMTCT) {
        this.caregiverEnrolledInPMTCT = caregiverEnrolledInPMTCT;
    }

    public String getCaregiverHavingFever() {
        return caregiverHavingFever;
    }

    public void setCaregiverHavingFever(String caregiverHavingFever) {
        this.caregiverHavingFever = caregiverHavingFever;
    }

    public String getCaregiverHavingNightSweat() {
        return caregiverHavingNightSweat;
    }

    public void setCaregiverHavingNightSweat(String caregiverHavingNightSweat) {
        this.caregiverHavingNightSweat = caregiverHavingNightSweat;
    }

    public String getCaregiverId() {
        return caregiverId;
    }

    public void setCaregiverId(String caregiverId) {
        this.caregiverId = caregiverId;
    }

    public String getCaregiverLossindWeight() {
        return caregiverLossindWeight;
    }

    public void setCaregiverLossindWeight(String caregiverLossindWeight) {
        this.caregiverLossindWeight = caregiverLossindWeight;
    }

    public String getCaregiverOnSeptrin() {
        return caregiverOnSeptrin;
    }

    public void setCaregiverOnSeptrin(String caregiverOnSeptrin) {
        this.caregiverOnSeptrin = caregiverOnSeptrin;
    }

    public String getCaregiverSleepInTreatedNet() {
        return caregiverSleepInTreatedNet;
    }

    public void setCaregiverSleepInTreatedNet(String caregiverSleepInTreatedNet) {
        this.caregiverSleepInTreatedNet = caregiverSleepInTreatedNet;
    }

    public String getCaregiverSwellingOnNeck() {
        return caregiverSwellingOnNeck;
    }

    public void setCaregiverSwellingOnNeck(String caregiverSwellingOnNeck) {
        this.caregiverSwellingOnNeck = caregiverSwellingOnNeck;
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

    public String getDosesOfARVMissedByCaregiver() {
        return dosesOfARVMissedByCaregiver;
    }

    public void setDosesOfARVMissedByCaregiver(String dosesOfARVMissedByCaregiver) {
        this.dosesOfARVMissedByCaregiver = dosesOfARVMissedByCaregiver;
    }

    public String getDosesOfSeptrinMissedByCaregiver() {
        return dosesOfSeptrinMissedByCaregiver;
    }

    public void setDosesOfSeptrinMissedByCaregiver(String dosesOfSeptrinMissedByCaregiver) {
        this.dosesOfSeptrinMissedByCaregiver = dosesOfSeptrinMissedByCaregiver;
    }

    public String getEidTestingDoneForChild() {
        return eidTestingDoneForChild;
    }

    public void setEidTestingDoneForChild(String eidTestingDoneForChild) {
        this.eidTestingDoneForChild = eidTestingDoneForChild;
    }
    
    public String getFamilyMemberHadTB() {
        return familyMemberHadTB;
    }

    public void setFamilyMemberHadTB(String familyMemberHadTB) {
        this.familyMemberHadTB = familyMemberHadTB;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getHivStatusDisclosedToPartner() {
        return hivStatusDisclosedToPartner;
    }

    public void setHivStatusDisclosedToPartner(String hivStatusDisclosedToPartner) {
        this.hivStatusDisclosedToPartner = hivStatusDisclosedToPartner;
    }

    public String getHivStatusOfPartnerKnown() {
        return hivStatusOfPartnerKnown;
    }

    public void setHivStatusOfPartnerKnown(String hivStatusOfPartnerKnown) {
        this.hivStatusOfPartnerKnown = hivStatusOfPartnerKnown;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLgaCode() {
        return lgaCode;
    }

    public void setLgaCode(String lgaCode) {
        this.lgaCode = lgaCode;
    }

    public String getNumberOfClubsAttndedInThreeMths() {
        return numberOfClubsAttndedInThreeMths;
    }

    public void setNumberOfClubsAttndedInThreeMths(String numberOfClubsAttndedInThreeMths) {
        this.numberOfClubsAttndedInThreeMths = numberOfClubsAttndedInThreeMths;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getVolunteerName() {
        return volunteerName;
    }

    public void setVolunteerName(String volunteerName) {
        this.volunteerName = volunteerName;
    }
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        actionName=null;
        assessmentNo=0;
        hhSerialNo=0;
        id=0;
        age=0;
        stateCode=null;
        lgaCode=null;
        orgCode=null;
        communityCode=null;
        hhUniqueId=null;
        caregiverId=null;
        gender=null;
        ageUnit=null;
        dateOfAssessment=null;
        caregiverCoughing=null;
        caregiverLossindWeight=null;
        caregiverHavingFever=null;
        caregiverHavingNightSweat=null;
        familyMemberHadTB=null;
        dosesOfARVMissedByCaregiver=null;
        caregiverEnrolledInPMTCT=null;
        caregiverOnSeptrin=null;
        hivStatusDisclosedToPartner=null;
        hivStatusOfPartnerKnown=null;
        dosesOfSeptrinMissedByCaregiver=null;
        childAboveSixWksAndStartedOnSeptrin=null;
        eidTestingDoneForChild=null;
        caregiverSleepInTreatedNet=null;
        beneficiariesHasSoresOrBleeding=null;
        numberOfClubsAttndedInThreeMths=null;
        caregiverSwellingOnNeck=null;
        volunteerName=null;
        designation=null;
    }
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if(getActionName()==null || getActionName().equalsIgnoreCase("refresh") || getActionName().equalsIgnoreCase("hhDetails") || getActionName().equalsIgnoreCase("baselineDetails") || getActionName().equalsIgnoreCase("assessmentDetails") || getActionName().equalsIgnoreCase("cboList") || getActionName().equalsIgnoreCase("delete"))
        return errors;
        else if(getActionName().equalsIgnoreCase("save") || getActionName().equalsIgnoreCase("update"))
        {
            if(getCaregiverId()==null || getCaregiverId().length() <1)
            errors.add("caregiverId", new ActionMessage("error.caregiverId.required"));
            else if(getDateOfAssessment()==null || getDateOfAssessment().indexOf("/")==-1)
            errors.add("dateOfAssessment", new ActionMessage("error.dateOfAssessment.required"));
        }
        return errors;
    }
}
