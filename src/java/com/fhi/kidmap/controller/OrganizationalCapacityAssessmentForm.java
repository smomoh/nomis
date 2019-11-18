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
public class OrganizationalCapacityAssessmentForm extends org.apache.struts.action.ActionForm
{
    private String actionName;
    private int recordId;
    private String orgCode;
    private int noofassessment;
    private String typeoforganization;
    private String isorganizationasubgrantee;
    private String dateofcapacityassessment;
    private String assessedbefore;
    private String dateoffirstcapassessment;
    private String leadassessorname;
    private int noofassessors;
    private int visionandmission;
    private int goalsandobjectives;
    private int actionplanning;
    private int roleandresponsibilities;
    private int internalrules;
    private int meetings;
    private int leadership;
    private int teambuilding;
    private int monitoring;
    private int reportingandrecordkeeping;
    private int budgeting;
    private int bookkeeping;
    private int banking;
    private int localresourcemobilization;
    private int proposalwriting;
    private int networking;
    private int advocacy;

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
    
    
    /**
     *
     */

    public OrganizationalCapacityAssessmentForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public int getActionplanning() {
        return actionplanning;
    }

    public void setActionplanning(int actionplanning) {
        this.actionplanning = actionplanning;
    }

    public int getAdvocacy() {
        return advocacy;
    }

    public void setAdvocacy(int advocacy) {
        this.advocacy = advocacy;
    }

    public String getAssessedbefore() {
        return assessedbefore;
    }

    public void setAssessedbefore(String assessedbefore) {
        this.assessedbefore = assessedbefore;
    }

    public int getBanking() {
        return banking;
    }

    public void setBanking(int banking) {
        this.banking = banking;
    }

    public int getBookkeeping() {
        return bookkeeping;
    }

    public void setBookkeeping(int bookkeeping) {
        this.bookkeeping = bookkeeping;
    }

    public int getBudgeting() {
        return budgeting;
    }

    public void setBudgeting(int budgeting) {
        this.budgeting = budgeting;
    }

    public String getDateofcapacityassessment() {
        return dateofcapacityassessment;
    }

    public void setDateofcapacityassessment(String dateofcapacityassessment) {
        this.dateofcapacityassessment = dateofcapacityassessment;
    }

    public String getDateoffirstcapassessment() {
        return dateoffirstcapassessment;
    }

    public void setDateoffirstcapassessment(String dateoffirstcapassessment) {
        this.dateoffirstcapassessment = dateoffirstcapassessment;
    }

    public int getGoalsandobjectives() {
        return goalsandobjectives;
    }

    public void setGoalsandobjectives(int goalsandobjectives) {
        this.goalsandobjectives = goalsandobjectives;
    }

    public int getInternalrules() {
        return internalrules;
    }

    public void setInternalrules(int internalrules) {
        this.internalrules = internalrules;
    }

    public String getIsorganizationasubgrantee() {
        return isorganizationasubgrantee;
    }

    public void setIsorganizationasubgrantee(String isorganizationasubgrantee) {
        this.isorganizationasubgrantee = isorganizationasubgrantee;
    }

    public String getLeadassessorname() {
        return leadassessorname;
    }

    public void setLeadassessorname(String leadassessorname) {
        this.leadassessorname = leadassessorname;
    }

    public int getLeadership() {
        return leadership;
    }

    public void setLeadership(int leadership) {
        this.leadership = leadership;
    }

    public int getLocalresourcemobilization() {
        return localresourcemobilization;
    }

    public void setLocalresourcemobilization(int localresourcemobilization) {
        this.localresourcemobilization = localresourcemobilization;
    }

    public int getMeetings() {
        return meetings;
    }

    public void setMeetings(int meetings) {
        this.meetings = meetings;
    }

    public int getMonitoring() {
        return monitoring;
    }

    public void setMonitoring(int monitoring) {
        this.monitoring = monitoring;
    }

    public int getNetworking() {
        return networking;
    }

    public void setNetworking(int networking) {
        this.networking = networking;
    }

    public int getNoofassessment() {
        return noofassessment;
    }

    public void setNoofassessment(int noofassessment) {
        this.noofassessment = noofassessment;
    }

    public int getNoofassessors() {
        return noofassessors;
    }

    public void setNoofassessors(int noofassessors) {
        this.noofassessors = noofassessors;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public int getProposalwriting() {
        return proposalwriting;
    }

    public void setProposalwriting(int proposalwriting) {
        this.proposalwriting = proposalwriting;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }


    public int getReportingandrecordkeeping() {
        return reportingandrecordkeeping;
    }

    public void setReportingandrecordkeeping(int reportingandrecordkeeping) {
        this.reportingandrecordkeeping = reportingandrecordkeeping;
    }

    public int getRoleandresponsibilities() {
        return roleandresponsibilities;
    }

    public void setRoleandresponsibilities(int roleandresponsibilities) {
        this.roleandresponsibilities = roleandresponsibilities;
    }

    public int getTeambuilding() {
        return teambuilding;
    }

    public void setTeambuilding(int teambuilding) {
        this.teambuilding = teambuilding;
    }

    public String getTypeoforganization() {
        return typeoforganization;
    }

    public void setTypeoforganization(String typeoforganization) {
        this.typeoforganization = typeoforganization;
    }

    public int getVisionandmission() {
        return visionandmission;
    }

    public void setVisionandmission(int visionandmission) {
        this.visionandmission = visionandmission;
    }

public void reset(ActionMapping mapping,
HttpServletRequest request)
{
    actionName=null;
    recordId=0;
    orgCode=null;
    noofassessment=0;
    typeoforganization=null;
    isorganizationasubgrantee=null;
    dateofcapacityassessment=null;
    assessedbefore=null;
    dateoffirstcapassessment=null;
    leadassessorname=null;
    noofassessors=0;
    visionandmission=0;
    goalsandobjectives=0;
    actionplanning=0;
    roleandresponsibilities=0;
    internalrules=0;
    meetings=0;
    leadership=0;
    teambuilding=0;
    monitoring=0;
    reportingandrecordkeeping=0;
    budgeting=0;
    bookkeeping=0;
    banking=0;
    localresourcemobilization=0;
    proposalwriting=0;
    networking=0;
    advocacy=0;

}

    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();

        if(getActionName()==null || getActionName().equalsIgnoreCase("delete") || getActionName().equalsIgnoreCase("noOfAssessmentAndDateOfFirstAssessment"))
        return errors;
        if (getOrgCode() == null || getOrgCode().length() < 1)
            errors.add("orgname", new ActionMessage("error.orgname.required"));
        else if (getDateofcapacityassessment() == null || getDateofcapacityassessment().length() < 1 || getDateofcapacityassessment().indexOf("/")==-1)
            errors.add("dateofcapacityassessment", new ActionMessage("error.dateofcapacityassessment.required"));

        if(getActionName().equalsIgnoreCase("assessmentDetails"))
        return errors;
        
        /*if (getTypeoforganization() == null || getTypeoforganization().length() < 1)
            errors.add("typeoforganization", new ActionMessage("error.typeoforganization.required"));*/
        
        if (this.getNoofassessors() < 1)
            errors.add("noofassessors", new ActionMessage("error.noofassessors.required"));
               
        return errors;
    }
}
