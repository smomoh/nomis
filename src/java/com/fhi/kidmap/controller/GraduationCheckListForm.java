/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author smomoh
 */
public class GraduationCheckListForm extends org.apache.struts.action.ActionForm {
    
    private String actionName;
    private String hhUniqueId;
    private String hhSurname;
    private String hhFirstname;
    private String hhGender;
    private String address;
    private String phone;
    private String occupation;
    private String graduated;
    private int hhAge;
    private String stateCode;
    private String lgaCode;
    private String orgCode;
    private String community;
    private String dateOfEnrollment;
    private String dateOfAssessment;
    private int id;
    private int hhSerialNo;
    private String health;
    
    private String safety;
    private String school;
    private String stability;
    
    private String vulnerability;
    private String volunteerId;
    private String designation;
    
    public GraduationCheckListForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
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

    public String getDateOfEnrollment() {
        return dateOfEnrollment;
    }

    public void setDateOfEnrollment(String dateOfEnrollment) {
        this.dateOfEnrollment = dateOfEnrollment;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getHhSurname() {
        return hhSurname;
    }

    public void setHhSurname(String hhSurname) {
        this.hhSurname = hhSurname;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        hhSurname=null;
        hhFirstname=null;
        hhGender=null;
        address=null;
        phone=null;
        occupation=null;
        hhAge=0;
        dateOfEnrollment=null;
        actionName=null;
        dateOfAssessment=null;
        id=0;
        hhSerialNo=0;
        health=null;
        safety=null;
        school=null;
        stability=null;
        vulnerability=null;
        graduated=null;
        volunteerId=null;
        designation=null;
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
        if(getActionName()==null || getActionName().equalsIgnoreCase("hhDetails") || getActionName().equalsIgnoreCase("gcfdetails") || getActionName().equalsIgnoreCase("delete") || getActionName().equalsIgnoreCase("cboList"))
        return errors;
        else if( getDateOfAssessment()==null ||  getDateOfAssessment().indexOf("/")==-1)
        errors.add("dateOfAssessment", new ActionMessage("errors.dateOfAssessment.required"));
        else if( this.getHealth()==null || !appUtil.isString(getHealth()))
        errors.add("health", new ActionMessage("errors.gclhealth.required"));
        else if(getSafety()==null || !appUtil.isString(getSafety()))
        errors.add("safety", new ActionMessage("errors.gclsafety.required"));
        else if( this.getSchool()==null || !appUtil.isString(getSchool()))
        errors.add("school", new ActionMessage("errors.gclschool.required"));
        else if( this.getStability()==null || !appUtil.isString(getStability()))
        errors.add("stability", new ActionMessage("errors.gclstability.required"));
        else if( this.getVulnerability()==null || !appUtil.isString(getVulnerability()))
        errors.add("vulnerability", new ActionMessage("errors.gclvulnerability.required"));
        return errors;
    }
}
