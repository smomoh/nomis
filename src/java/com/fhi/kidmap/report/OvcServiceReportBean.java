/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.report;

import com.fhi.kidmap.business.BeneficiaryResourceManager;
import com.fhi.kidmap.business.Ovc;
import java.io.Serializable;

/**
 *
 * @author smomoh
 */
public class OvcServiceReportBean implements Serializable{


private String ovcId;
private String state;
private String lga;
private String ward;
private String completedbyCbo;
private int serviceMonth;
private int serviceYear;
private String dateEnrollment;
private String surname;
private String otherNames1;
private String otherNames2;
private String gender;
private int age;
private String ageUnit;
private String address;
private String phone;
private double currentWeight;
private double currentHeight;
private String withdrawnFromProgram="No";
private String reasonForWithdrawal;
private String dateOfWithdrawal;

private String serviceDate;
private String dateOfEntry;
private String serviceAccessed1;
private String serviceAccessed2;
private String serviceAccessed3;
private String serviceAccessed4;
private String serviceAccessed5;
private String serviceAccessed6;
private String serviceAccessed7;
private int surveyNumber;
private Ovc ovc;
private String providerName;
private String childMissedSchool;
private String childAbusedAnswer;
private String childLinkedToGovtAnswer;
//Beneficiary beneficiary=new BeneficiaryResourceManager();
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

    public String getCompletedbyCbo() {
        return completedbyCbo;
    }

    public void setCompletedbyCbo(String completedbyCbo) {
        this.completedbyCbo = completedbyCbo;
    }

    public double getCurrentHeight() {
        return currentHeight;
    }

    public void setCurrentHeight(double currentHeight) {
        this.currentHeight = currentHeight;
    }

    public double getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(double currentWeight) {
        this.currentWeight = currentWeight;
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

    public String getOvcId() {
        return ovcId;
    }

    public void setOvcId(String ovcId) {
        this.ovcId = ovcId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDateOfWithdrawal() {
        return dateOfWithdrawal;
    }

    public void setDateOfWithdrawal(String dateOfWithdrawal) {
        this.dateOfWithdrawal = dateOfWithdrawal;
    }

    public String getReasonForWithdrawal() {
        return reasonForWithdrawal;
    }

    public void setReasonForWithdrawal(String reasonForWithdrawal) {
        this.reasonForWithdrawal = reasonForWithdrawal;
    }

    public String getWithdrawnFromProgram() {
        return withdrawnFromProgram;
    }

    public void setWithdrawnFromProgram(String withdrawnFromProgram) {
        this.withdrawnFromProgram = withdrawnFromProgram;
    }

    public int getServiceMonth() {
        return serviceMonth;
    }

    public void setServiceMonth(int serviceMonth) {
        this.serviceMonth = serviceMonth;
    }

    public int getServiceYear() {
        return serviceYear;
    }

    public void setServiceYear(int serviceYear) {
        this.serviceYear = serviceYear;
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

    public String getDateOfEntry() {
        return dateOfEntry;
    }

    public void setDateOfEntry(String dateOfEntry) {
        this.dateOfEntry = dateOfEntry;
    }

    public String getServiceAccessed1() {
        return serviceAccessed1;
    }

    public void setServiceAccessed1(String serviceAccessed1) {
        this.serviceAccessed1 = serviceAccessed1;
    }

    public String getServiceAccessed2() {
        return serviceAccessed2;
    }

    public void setServiceAccessed2(String serviceAccessed2) {
        this.serviceAccessed2 = serviceAccessed2;
    }

    public String getServiceAccessed3() {
        return serviceAccessed3;
    }

    public void setServiceAccessed3(String serviceAccessed3) {
        this.serviceAccessed3 = serviceAccessed3;
    }

    public String getServiceAccessed4() {
        return serviceAccessed4;
    }

    public void setServiceAccessed4(String serviceAccessed4) {
        this.serviceAccessed4 = serviceAccessed4;
    }

    public String getServiceAccessed5() {
        return serviceAccessed5;
    }

    public void setServiceAccessed5(String serviceAccessed5) {
        this.serviceAccessed5 = serviceAccessed5;
    }

    public String getServiceAccessed6() {
        return serviceAccessed6;
    }

    public void setServiceAccessed6(String serviceAccessed6) {
        this.serviceAccessed6 = serviceAccessed6;
    }

    public String getServiceAccessed7() {
        return serviceAccessed7;
    }

    public void setServiceAccessed7(String serviceAccessed7) {
        this.serviceAccessed7 = serviceAccessed7;
    }

    public int getSurveyNumber() {
        return surveyNumber;
    }

    public void setSurveyNumber(int surveyNumber) {
        this.surveyNumber = surveyNumber;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }

    public Ovc getOvc() 
    {
        if(ovc==null)
        ovc=BeneficiaryResourceManager.getOvc(ovcId);
        return ovc;
    }

    public void setOvc(Ovc ovc) {
        this.ovc = ovc;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getChildAbusedAnswer() {
        return childAbusedAnswer;
    }

    public void setChildAbusedAnswer(String childAbusedAnswer) {
        this.childAbusedAnswer = childAbusedAnswer;
    }

    public String getChildLinkedToGovtAnswer() {
        return childLinkedToGovtAnswer;
    }

    public void setChildLinkedToGovtAnswer(String childLinkedToGovtAnswer) {
        this.childLinkedToGovtAnswer = childLinkedToGovtAnswer;
    }

    public String getChildMissedSchool() {
        return childMissedSchool;
    }

    public void setChildMissedSchool(String childMissedSchool) {
        this.childMissedSchool = childMissedSchool;
    }
    
}
