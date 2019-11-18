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
public class OrganizationRecordsForm extends org.apache.struts.action.ActionForm {

    private String orgCode;
    private String hiddenOrgCode;
    private String state;
    private String stateCode;
    private String lgaCode;
    private String actionName;
    private String lga;
    private String orgName;
    private String orgList;
    private String orgType;
    private String orgSubtype;
    private String dhisId;
    private boolean dataEntryAllowed;
    private String address;
    private String contactName1;
    private String contactPhone1;
    private String contactEmail1;
    private String contactName2;
    private String contactPhone2;
    private String contactEmail2;
    private String contactName3;
    private String contactPhone3;
    private String contactEmail3;
    private double latitude;
    private double longitude;
    private String[] assignedLgas;
    private String[] services;
    
    public OrganizationRecordsForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactEmail1() {
        return contactEmail1;
    }

    public void setContactEmail1(String contactEmail1) {
        this.contactEmail1 = contactEmail1;
    }

    public String getContactEmail2() {
        return contactEmail2;
    }

    public void setContactEmail2(String contactEmail2) {
        this.contactEmail2 = contactEmail2;
    }

    public String getContactEmail3() {
        return contactEmail3;
    }

    public void setContactEmail3(String contactEmail3) {
        this.contactEmail3 = contactEmail3;
    }

    public String getContactName1() {
        return contactName1;
    }

    public void setContactName1(String contactName1) {
        this.contactName1 = contactName1;
    }

    public String getContactName2() {
        return contactName2;
    }

    public void setContactName2(String contactName2) {
        this.contactName2 = contactName2;
    }

    public String getContactName3() {
        return contactName3;
    }

    public void setContactName3(String contactName3) {
        this.contactName3 = contactName3;
    }

    public String getContactPhone1() {
        return contactPhone1;
    }

    public void setContactPhone1(String contactPhone1) {
        this.contactPhone1 = contactPhone1;
    }

    public String getContactPhone2() {
        return contactPhone2;
    }

    public void setContactPhone2(String contactPhone2) {
        this.contactPhone2 = contactPhone2;
    }

    public String getContactPhone3() {
        return contactPhone3;
    }

    public void setContactPhone3(String contactPhone3) {
        this.contactPhone3 = contactPhone3;
    }

    public String getDhisId() {
        return dhisId;
    }

    public void setDhisId(String dhisId) {
        this.dhisId = dhisId;
    }

    public boolean isDataEntryAllowed() {
        return dataEntryAllowed;
    }

    public void setDataEntryAllowed(boolean dataEntryAllowed) {
        this.dataEntryAllowed = dataEntryAllowed;
    }

    
    public String getLga() {
        return lga;
    }

    public void setLga(String lga) {
        this.lga = lga;
    }

    public String getLgaCode() {
        return lgaCode;
    }

    public void setLgaCode(String lgaCode) {
        this.lgaCode = lgaCode;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }
    
    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }


    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgSubtype() {
        return orgSubtype;
    }

    public void setOrgSubtype(String orgSubtype) {
        this.orgSubtype = orgSubtype;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String[] getAssignedLgas() {
        return assignedLgas;
    }

    public void setAssignedLgas(String[] assignedLgas) {
        this.assignedLgas = assignedLgas;
    }

    public String[] getServices() {
        return services;
    }

    public void setServices(String[] services) {
        this.services = services;
    }

    public String getHiddenOrgCode() {
        return hiddenOrgCode;
    }

    public void setHiddenOrgCode(String hiddenOrgCode) {
        this.hiddenOrgCode = hiddenOrgCode;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getOrgList() {
        return orgList;
    }

    public void setOrgList(String orgList) {
        this.orgList = orgList;
    }

    @Override
    public void reset(ActionMapping mapping,HttpServletRequest request)
    {
        orgCode=null;
        state=null;
        actionName=null;
        lga=null;
        orgName=null;
        orgList=null;
        orgType=null;
        orgSubtype=null;
        dhisId=null;
        address=null;
        contactName1=null;
        contactPhone1=null;
        contactEmail1=null;
        contactName2=null;
        contactPhone2=null;
        contactEmail2=null;
        contactName3=null;
        contactPhone3=null;
        contactEmail3=null;
        assignedLgas=null;
        services=null;
        latitude=0;
        longitude=0;
    }
    

    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if(getActionName() ==null || getActionName().equalsIgnoreCase("refresh") || getActionName().equalsIgnoreCase("orgDetails") || getActionName().equalsIgnoreCase("delete"))
        return errors;
        else if(getOrgCode()==null || getOrgCode().trim().length() < 1)
        errors.add("orgCode", new ActionMessage("error.orgCode.required"));
        else if(getOrgCode() !=null && getOrgCode().length() !=3)
        errors.add("orgCode", new ActionMessage("error.orgCode.length"));
        else if(getOrgName() ==null || getOrgName().trim().length() <1)
        errors.add("orgName", new ActionMessage("error.orgName.required"));
        
        return errors;
    }
}
