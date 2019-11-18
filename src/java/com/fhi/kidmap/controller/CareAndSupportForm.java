/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.nomis.nomisutils.NomisConstant;
import com.fhi.nomis.nomisutils.ValidationManager;
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
public class CareAndSupportForm extends org.apache.struts.action.ActionForm {
    
    private String actionName;
    private int recordId;
    private String hhUniqueId;
    private String dateOfAssessment;
    private String dateOfHivTest;
    private String receivedTestResult;
    private String hivStatus;
    private String enrolledOnTreatment;
    private String dateEnrolledOnART;
    private String treatmentFacility;
    private String viralLoadTest;
    private String dateOfViralLoadTest;
    private String medicationPickedUp;
    private String skippedARV;
    private String[] reasonsPeopleSkipARV;
    private String transportationSupport;
    private String childExperiencedSoresOrRash;
    private String stateCode;
    private String lgaCode;
    private String orgCode;
    private String communityCode;
    private int hhSerialNo;
    private String ovcId;
    private String ovcGender;
    private int age;
    private String ageUnit;
    private String volunteerName;
    private String designation;
    private String providerPhone;
    private List referralDirectoryList=new ArrayList();
    
    public CareAndSupportForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getAgeUnit() {
        return ageUnit;
    }

    public void setAgeUnit(String ageUnit) {
        this.ageUnit = ageUnit;
    }

    public String getCommunityCode() {
        return communityCode;
    }

    public void setCommunityCode(String communityCode) {
        this.communityCode = communityCode;
    }

    public String getDateEnrolledOnART() {
        return dateEnrolledOnART;
    }

    public void setDateEnrolledOnART(String dateEnrolledOnART) {
        this.dateEnrolledOnART = dateEnrolledOnART;
    }

    public String getDateOfAssessment() {
        return dateOfAssessment;
    }

    public void setDateOfAssessment(String dateOfAssessment) {
        this.dateOfAssessment = dateOfAssessment;
    }

    public String getDateOfHivTest() {
        return dateOfHivTest;
    }

    public void setDateOfHivTest(String dateOfHivTest) {
        this.dateOfHivTest = dateOfHivTest;
    }

    public String getDateOfViralLoadTest() {
        return dateOfViralLoadTest;
    }

    public void setDateOfViralLoadTest(String dateOfViralLoadTest) {
        this.dateOfViralLoadTest = dateOfViralLoadTest;
    }

    public String getEnrolledOnTreatment() {
        return enrolledOnTreatment;
    }

    public void setEnrolledOnTreatment(String enrolledOnTreatment) {
        this.enrolledOnTreatment = enrolledOnTreatment;
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

    public String getMedicationPickedUp() {
        return medicationPickedUp;
    }

    public void setMedicationPickedUp(String medicationPickedUp) {
        this.medicationPickedUp = medicationPickedUp;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
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

    public String[] getReasonsPeopleSkipARV() {
        return reasonsPeopleSkipARV;
    }

    public void setReasonsPeopleSkipARV(String[] reasonsPeopleSkipARV) {
        this.reasonsPeopleSkipARV = reasonsPeopleSkipARV;
    }

    public String getReceivedTestResult() {
        return receivedTestResult;
    }

    public void setReceivedTestResult(String receivedTestResult) {
        this.receivedTestResult = receivedTestResult;
    }

    public String getSkippedARV() {
        return skippedARV;
    }

    public void setSkippedARV(String skippedARV) {
        this.skippedARV = skippedARV;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getTransportationSupport() {
        return transportationSupport;
    }

    public void setTransportationSupport(String transportationSupport) {
        this.transportationSupport = transportationSupport;
    }

    public String getTreatmentFacility() {
        return treatmentFacility;
    }

    public void setTreatmentFacility(String treatmentFacility) {
        this.treatmentFacility = treatmentFacility;
    }

    public String getViralLoadTest() {
        return viralLoadTest;
    }

    public void setViralLoadTest(String viralLoadTest) {
        this.viralLoadTest = viralLoadTest;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getChildExperiencedSoresOrRash() {
        return childExperiencedSoresOrRash;
    }

    public void setChildExperiencedSoresOrRash(String childExperiencedSoresOrRash) {
        this.childExperiencedSoresOrRash = childExperiencedSoresOrRash;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public List getReferralDirectoryList() {
        return referralDirectoryList;
    }

    public void setReferralDirectoryList(List referralDirectoryList) {
        this.referralDirectoryList = referralDirectoryList;
    }

    public String getProviderPhone() {
        return providerPhone;
    }

    public void setProviderPhone(String providerPhone) {
        this.providerPhone = providerPhone;
    }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        actionName=null;
        //hhUniqueId=null;
        //dateOfAssessment=null;
        dateOfHivTest=null;
        receivedTestResult=null;
        hivStatus=null;
        enrolledOnTreatment=null;
        dateEnrolledOnART=null;
        treatmentFacility=null;
        viralLoadTest=null;
        dateOfViralLoadTest=null;
        medicationPickedUp=null;
        skippedARV=null;
        reasonsPeopleSkipARV=null;
        transportationSupport=null;
        providerPhone=null;
        /*stateCode=null;
        lgaCode=null;
        orgCode=null;
        communityCode=null;
        hhSerialNo=0;
        ovcId=null;
         age=0;*/
        volunteerName=null;
        designation=null;
        
        recordId=0;
    }
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) 
    {
        AppUtility appUtil=new AppUtility();
        ActionErrors errors = new ActionErrors();
        if(getActionName()==null || getActionName().equalsIgnoreCase("hhDetails") || getActionName().equalsIgnoreCase("cboList") || getActionName().equalsIgnoreCase("baselineDetails") || getActionName().equalsIgnoreCase("delete") || getActionName().equalsIgnoreCase("assessmentDetails"))
        return errors;
        else if(getDateOfAssessment()==null || getDateOfAssessment().length() < 1 )
        errors.add("dateOfAssessment", new ActionMessage("errors.dateOfAssessment.required"));
        else if(!ValidationManager.enrollmentDateBeforeCurrentDate(getDateOfAssessment(), getOvcId(), NomisConstant.OVC_TYPE))
        errors.add("dateOfAssessment", new ActionMessage("errors.dateOfAssessment.beforeEnrollment"));
        else if(this.getOvcId()==null || !appUtil.isString(getOvcId()))
        errors.add("ovcId", new ActionMessage("errors.ovcId.null"));
        else if(getDateOfHivTest().indexOf("/") == -1 && getDateOfHivTest().indexOf("-") ==-1 )
        errors.add("dateOfHivTest", new ActionMessage("errors.dateOfHivTest.required"));
        else if(this.getReceivedTestResult() !=null && getReceivedTestResult().equalsIgnoreCase("Yes") )
        {
            if(this.getHivStatus()==null || !appUtil.isString(getHivStatus()))
            errors.add("hivStatus", new ActionMessage("errors.hivStatus.required"));
        }
        else if(this.getEnrolledOnTreatment() !=null && getEnrolledOnTreatment().equalsIgnoreCase("Yes") )
        {
            if(this.getDateEnrolledOnART()==null || getDateEnrolledOnART().length() < 1 )
            errors.add("dateEnrolledOnART", new ActionMessage("errors.dateEnrolledOnART.required"));
        }
        else if(this.getViralLoadTest() !=null && getViralLoadTest().equalsIgnoreCase("Yes") )
        {
            if(this.getDateOfViralLoadTest()==null || getDateOfViralLoadTest().length() < 1 )
            errors.add("dateOfViralLoadTest", new ActionMessage("errors.dateOfViralLoadTest.required"));
        }
        else if(this.getSkippedARV() !=null && getSkippedARV().equalsIgnoreCase("Yes") )
        {
            if(this.getReasonsPeopleSkipARV()==null || getReasonsPeopleSkipARV().length < 1 )
            errors.add("reasonsPeopleSkipARV", new ActionMessage("errors.reasonsPeopleSkipARV.required"));
        }
        else if(this.getVolunteerName()==null || getVolunteerName().length() < 1 )
        errors.add("volunteerName", new ActionMessage("errors.volunteerName.required"));
        return errors;
    }
}
