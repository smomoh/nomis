/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Siaka
 */
public class TrainingReportForm extends org.apache.struts.action.ActionForm {
    
    private String actionName;
    private String organizationCode;
    private String nameOfTraining;
    private String category;
    private String designation;
    private String partnerCode;
    private String startDate;
    private String endDate;
    private List trainingInfoList=new ArrayList();
    private List trainingCategoryList=new ArrayList();
    private List trainingDesignationList=new ArrayList();
    private List organizationList=new ArrayList();
    private List partnerList=new ArrayList();
    List trainingList=new ArrayList();

    
    public TrainingReportForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
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

    public List getTrainingCategoryList() {
        return trainingCategoryList;
    }

    public void setTrainingCategoryList(List trainingCategoryList) {
        this.trainingCategoryList = trainingCategoryList;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public List getTrainingDesignationList() {
        return trainingDesignationList;
    }

    public void setTrainingDesignationList(List trainingDesignationList) {
        this.trainingDesignationList = trainingDesignationList;
    }

    public List getOrganizationList() {
        return organizationList;
    }

    public void setOrganizationList(List organizationList) {
        this.organizationList = organizationList;
    }

    public List getPartnerList() {
        return partnerList;
    }

    public void setPartnerList(List partnerList) {
        this.partnerList = partnerList;
    }

    public String getNameOfTraining() {
        return nameOfTraining;
    }

    public void setNameOfTraining(String nameOfTraining) {
        this.nameOfTraining = nameOfTraining;
    }

    public List getTrainingInfoList() {
        return trainingInfoList;
    }

    public void setTrainingInfoList(List trainingInfoList)
    {
        this.trainingInfoList = trainingInfoList;
    }

    public List getTrainingList() {
        return trainingList;
    }

    public void setTrainingList(List trainingList) {
        this.trainingList = trainingList;
    }
    
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        actionName=null;
        organizationCode=null;
        nameOfTraining=null;
        category=null;
        designation=null;
        partnerCode=null;
        startDate=null;
        endDate=null;
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
        
        return errors;
    }
}
