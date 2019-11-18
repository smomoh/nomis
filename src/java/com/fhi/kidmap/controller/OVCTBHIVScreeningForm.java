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
public class OVCTBHIVScreeningForm extends org.apache.struts.action.ActionForm {
    
private String actionName;
private int hhSerialNo;
private String stateCode;
private String lgaCode;
private String orgCode;
private String communityCode;
private String hhUniqueId;
private String ovcId;
private String ovcGender;
private int ovcAge;
private String ageUnit;
private String dateOfAssessment;
private int id;
private int assessmentNo;
private String childCoughing;
private String childLossindWeight;
private String childHavingFever;
private String childHavingNightSweat;
private String familyMemberHadTB;
private String dosesOfARVMissedByChild;
private String caregiverEnrolledInPMTCT;
private String eidTestingDoneForChild;
private String childOnSeptrin;
private String hivStatusDisclosedToPartner;
private String hivStatusOfPartnerKnown;
private String dosesOfSeptrinMissedByChild;
private String childAboveSixWksAndStartedOnSeptrin;
private String childSleepInTreatedNet;
private String beneficiariesHasSoresOrBleeding;
private String numberOfClubsAttndedInThreeMths;
private String childSwellingOnNeck;
private String volunteerName;
private String designation;

    public OVCTBHIVScreeningForm() 
    {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public int getAssessmentNo() {
        return assessmentNo;
    }

    public void setAssessmentNo(int assessmentNo) {
        this.assessmentNo = assessmentNo;
    }

    public String getHhUniqueId() {
        return hhUniqueId;
    }

    public void setHhUniqueId(String hhUniqueId) {
        this.hhUniqueId = hhUniqueId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAgeUnit() {
        return ageUnit;
    }

    public void setAgeUnit(String ageUnit) {
        this.ageUnit = ageUnit;
    }

    public String getDateOfAssessment() {
        return dateOfAssessment;
    }

    public void setDateOfAssessment(String dateOfAssessment) {
        this.dateOfAssessment = dateOfAssessment;
    }

    public int getHhSerialNo() {
        return hhSerialNo;
    }

    public void setHhSerialNo(int hhSerialNo) {
        this.hhSerialNo = hhSerialNo;
    }

    public int getOvcAge() {
        return ovcAge;
    }

    public void setOvcAge(int ovcAge) {
        this.ovcAge = ovcAge;
    }

    public String getOvcGender() {
        return ovcGender;
    }

    public void setOvcGender(String ovcGender) {
        this.ovcGender = ovcGender;
    }

    public String getOvcId() {
        return ovcId;
    }

    public void setOvcId(String ovcId) {
        this.ovcId = ovcId;
    }

    public String getCommunityCode() {
        return communityCode;
    }

    public void setCommunityCode(String communityCode) {
        this.communityCode = communityCode;
    }

    public String getLgaCode() {
        return lgaCode;
    }

    public void setLgaCode(String lgaCode) {
        this.lgaCode = lgaCode;
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

    public String getBeneficiariesHasSoresOrBleeding() {
        return beneficiariesHasSoresOrBleeding;
    }

    public void setBeneficiariesHasSoresOrBleeding(String beneficiariesHasSoresOrBleeding) {
        this.beneficiariesHasSoresOrBleeding = beneficiariesHasSoresOrBleeding;
    }

    public String getCaregiverEnrolledInPMTCT() {
        return caregiverEnrolledInPMTCT;
    }

    public void setCaregiverEnrolledInPMTCT(String caregiverEnrolledInPMTCT) {
        this.caregiverEnrolledInPMTCT = caregiverEnrolledInPMTCT;
    }

    public String getChildAboveSixWksAndStartedOnSeptrin() {
        return childAboveSixWksAndStartedOnSeptrin;
    }

    public void setChildAboveSixWksAndStartedOnSeptrin(String childAboveSixWksAndStartedOnSeptrin) {
        this.childAboveSixWksAndStartedOnSeptrin = childAboveSixWksAndStartedOnSeptrin;
    }

    public String getChildCoughing() {
        return childCoughing;
    }

    public void setChildCoughing(String childCoughing) {
        this.childCoughing = childCoughing;
    }

    public String getChildHavingFever() {
        return childHavingFever;
    }

    public void setChildHavingFever(String childHavingFever) {
        this.childHavingFever = childHavingFever;
    }

    public String getChildHavingNightSweat() {
        return childHavingNightSweat;
    }

    public void setChildHavingNightSweat(String childHavingNightSweat) {
        this.childHavingNightSweat = childHavingNightSweat;
    }

    public String getChildLossindWeight() {
        return childLossindWeight;
    }

    public void setChildLossindWeight(String childLossindWeight) {
        this.childLossindWeight = childLossindWeight;
    }

    public String getChildOnSeptrin() {
        return childOnSeptrin;
    }

    public void setChildOnSeptrin(String childOnSeptrin) {
        this.childOnSeptrin = childOnSeptrin;
    }

    public String getChildSleepInTreatedNet() {
        return childSleepInTreatedNet;
    }

    public void setChildSleepInTreatedNet(String childSleepInTreatedNet) {
        this.childSleepInTreatedNet = childSleepInTreatedNet;
    }

    public String getChildSwellingOnNeck() {
        return childSwellingOnNeck;
    }

    public void setChildSwellingOnNeck(String childSwellingOnNeck) {
        this.childSwellingOnNeck = childSwellingOnNeck;
    }

    public String getDosesOfARVMissedByChild() {
        return dosesOfARVMissedByChild;
    }

    public void setDosesOfARVMissedByChild(String dosesOfARVMissedByChild) {
        this.dosesOfARVMissedByChild = dosesOfARVMissedByChild;
    }

    public String getDosesOfSeptrinMissedByChild() {
        return dosesOfSeptrinMissedByChild;
    }

    public void setDosesOfSeptrinMissedByChild(String dosesOfSeptrinMissedByChild) {
        this.dosesOfSeptrinMissedByChild = dosesOfSeptrinMissedByChild;
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

    public String getNumberOfClubsAttndedInThreeMths() {
        return numberOfClubsAttndedInThreeMths;
    }

    public void setNumberOfClubsAttndedInThreeMths(String numberOfClubsAttndedInThreeMths) {
        this.numberOfClubsAttndedInThreeMths = numberOfClubsAttndedInThreeMths;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
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
        ovcAge=0;
        stateCode=null;
        lgaCode=null;
        orgCode=null;
        communityCode=null;
        hhUniqueId=null;
        ovcId=null;
        ovcGender=null;
        ageUnit=null;
        dateOfAssessment=null;
        childCoughing=null;
        childLossindWeight=null;
        childHavingFever=null;
        childHavingNightSweat=null;
        familyMemberHadTB=null;
        dosesOfARVMissedByChild=null;
        caregiverEnrolledInPMTCT=null;
        eidTestingDoneForChild=null;
        childOnSeptrin=null;
        hivStatusDisclosedToPartner=null;
        hivStatusOfPartnerKnown=null;
        dosesOfSeptrinMissedByChild=null;
        childAboveSixWksAndStartedOnSeptrin=null;
        childSleepInTreatedNet=null;
        beneficiariesHasSoresOrBleeding=null;
        numberOfClubsAttndedInThreeMths=null;
        childSwellingOnNeck=null;
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
            if(getOvcId()==null || getOvcId().length() <1)
            errors.add("ovcId", new ActionMessage("error.ovcId.required"));
            else if(getDateOfAssessment()==null || getDateOfAssessment().indexOf("/")==-1)
            errors.add("dateOfAssessment", new ActionMessage("error.dateOfAssessment.required"));
        }
        return errors;
    }
}
