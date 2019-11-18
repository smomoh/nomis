/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.business;

import java.io.Serializable;

/**
 *
 * @author smomoh
 */
public class GraduationCheckList implements Serializable
{
    BeneficiaryResourceManager bnf=new BeneficiaryResourceManager();
    private String cboCode;
    private String community;
    private String clientId;
    private String clientType;
    private String dateOfAssessment;
    private String dateModified;
    private String surname;
    private String firstName;
    private String gender;
    private String address;
    private int age;
    private String phone;
    private int id;
    private String health;
    private String lgaCode;
    private String safety;
    private String school;
    private String stability;
    private String stateCode;
    private String vulnerability;
    private String graduated;
    private String volunteerId;
    private String recordedby;
    private int gclscore;
    private int markedForDelete;
    
    public String getCboCode() {
        return cboCode;
    }

    public void setCboCode(String cboCode) {
        this.cboCode = cboCode;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getDateOfAssessment() {
        return dateOfAssessment;
    }

    public void setDateOfAssessment(String dateOfAssessment) {
        this.dateOfAssessment = dateOfAssessment;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
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

    public String getSafety() {
        return safety;
    }

    public void setSafety(String safety) {
        this.safety = safety;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getStability() {
        return stability;
    }

    public void setStability(String stability) {
        this.stability = stability;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getVulnerability() {
        return vulnerability;
    }

    public void setVulnerability(String vulnerability) {
        this.vulnerability = vulnerability;
    }

    public String getGraduated() {
        return graduated;
    }

    public void setGraduated(String graduated) {
        this.graduated = graduated;
    }

    public String getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(String volunteerId) {
        this.volunteerId = volunteerId;
    }

    public String getRecordedby() {
        return recordedby;
    }

    public void setRecordedby(String recordedby) {
        this.recordedby = recordedby;
    }

    public String getAddress() {
        if(bnf.getHouseholdHead(clientId) !=null)
        address=bnf.getHouseholdHead(clientId).getAddress();
        return address;
    }

    public void setAddress(String address) 
    {
        
        this.address = address;
    }

    public int getAge() 
    {
        if(bnf.getHouseholdHead(clientId) !=null)
        age=bnf.getHouseholdHead(clientId).getHhAge();
        return age;
    }

    public void setAge(int age) 
    {
        
        this.age = age;
    }

    public String getFirstName() 
    {
        if(bnf.getHouseholdHead(clientId) !=null)
        firstName=bnf.getHouseholdHead(clientId).getHhFirstname();
        return firstName;
    }

    public void setFirstName(String firstName) 
    {
        
        this.firstName = firstName;
    }

    public String getGender() 
    {
        if(bnf.getHouseholdHead(clientId) !=null)
        gender=bnf.getHouseholdHead(clientId).getHhGender();
        return gender;
    }

    public void setGender(String gender) 
    {
        
        this.gender = gender;
    }

    public String getPhone() 
    {
        if(bnf.getHouseholdHead(clientId) !=null)
            phone=bnf.getHouseholdHead(clientId).getPhone();
        return phone;
    }

    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getSurname() 
    {
        if(bnf.getHouseholdHead(clientId) !=null)
            surname=bnf.getHouseholdHead(clientId).getHhSurname();
        return surname;
    }

    public void setSurname(String surname) 
    {
        this.surname = surname;
    }

    public int getGclscore() {
        return gclscore;
    }

    public void setGclscore(int gclscore) {
        this.gclscore = gclscore;
    }

    public int getMarkedForDelete() {
        return markedForDelete;
    }

    public void setMarkedForDelete(int markedForDelete) {
        this.markedForDelete = markedForDelete;
    }
    
}
