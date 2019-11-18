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

/**
 *
 * @author Siaka
 */
public class TrainingForm extends org.apache.struts.action.ActionForm 
{
    private String actionName;
    //private String orgName;
    private String hiddenId;
    private String recordId;
    private String trainingId;
    private String nameOfTraining;
    private String participantId;
    private String participantName;
    private String gender;
    private int age;
    private String category;
    private String designation;
    private String phone;
    private String email;
    private String startDate;
    private String endDate;
    private String trainingStatus;
    private String organizationCode;
    private List trainingList=new ArrayList();
    private List trainingStatusList=new ArrayList();
    private List trainingDesignationList=new ArrayList();
    private List organizationList=new ArrayList();
    private List trainingCategoryList=new ArrayList();
    private List trainingInfoList=new ArrayList();
    
    public TrainingForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getHiddenId() {
        return hiddenId;
    }

    public void setHiddenId(String hiddenId) {
        this.hiddenId = hiddenId;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(String trainingId) {
        this.trainingId = trainingId;
    }

    public String getParticipantId() {
        return participantId;
    }

    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getNameOfTraining() {
        return nameOfTraining;
    }

    public void setNameOfTraining(String nameOfTraining) {
        this.nameOfTraining = nameOfTraining;
    }

    public String getTrainingStatus() {
        return trainingStatus;
    }

    public void setTrainingStatus(String trainingStatus) {
        this.trainingStatus = trainingStatus;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }
       
    public List getTrainingList() {
        return trainingList;
    }

    public void setTrainingList(List trainingList) {
        this.trainingList = trainingList;
    }

    public List getTrainingCategoryList() {
        return trainingCategoryList;
    }

    public void setTrainingCategoryList(List trainingCategoryList) {
        this.trainingCategoryList = trainingCategoryList;
    }

    public List getTrainingInfoList() {
        return trainingInfoList;
    }

    public void setTrainingInfoList(List trainingInfoList) {
        this.trainingInfoList = trainingInfoList;
    }

    public List getTrainingStatusList() {
        return trainingStatusList;
    }

    public void setTrainingStatusList(List trainingStatusList) {
        this.trainingStatusList = trainingStatusList;
    }

    public List getOrganizationList() {
        return organizationList;
    }

    public void setOrganizationList(List organizationList) {
        this.organizationList = organizationList;
    }

    public List getTrainingDesignationList() {
        return trainingDesignationList;
    }

    public void setTrainingDesignationList(List trainingDesignationList) {
        this.trainingDesignationList = trainingDesignationList;
    }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        actionName=null;
        participantId=null;
        participantName=null;
        gender=null;
        age=0;
        category=null;
        designation=null;
        phone=null;
        email=null;
        startDate=null;
        endDate=null;
        nameOfTraining=null;
        trainingStatus=null;
    }
    /**
     * This is the action called from the Struts framework.
     *
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if(getActionName()==null || getActionName().equalsIgnoreCase("details") || getActionName().equalsIgnoreCase("delete"))
        return errors;
        return errors;
    }
}
