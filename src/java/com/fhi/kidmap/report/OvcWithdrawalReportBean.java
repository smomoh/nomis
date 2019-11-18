/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.report;

import java.io.Serializable;

/**
 *
 * @author COMPAQ USER
 */
public class OvcWithdrawalReportBean implements Serializable
{
    private String ovcId;
    private String dateOfWithdrawal;
    private String reasonWithdrawal;
    private int surveyNumber;
    private String lastName;
    private String firstName;
    private String gender;
    private int age;
    private String ageUnit;

    public OvcWithdrawalReportBean() {
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

    public String getDateOfWithdrawal() {
        return dateOfWithdrawal;
    }

    public void setDateOfWithdrawal(String dateOfWithdrawal) {
        this.dateOfWithdrawal = dateOfWithdrawal;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getOvcId() {
        return ovcId;
    }

    public void setOvcId(String ovcId) {
        this.ovcId = ovcId;
    }

    public String getReasonWithdrawal() {
        return reasonWithdrawal;
    }

    public void setReasonWithdrawal(String reasonWithdrawal) {
        this.reasonWithdrawal = reasonWithdrawal;
    }

    public int getSurveyNumber() {
        return surveyNumber;
    }

    public void setSurveyNumber(int surveyNumber) {
        this.surveyNumber = surveyNumber;
    }

    }
