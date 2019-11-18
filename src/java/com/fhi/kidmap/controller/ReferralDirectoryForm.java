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
public class ReferralDirectoryForm extends org.apache.struts.action.ActionForm {
    
    private String actionName;
    private String facilityName;
    private String facilityId;
    private String typeOfOrganization;
    private String sourceOfFunding;
    private String address;
    private String contactNumber;
    private String contactEmail;
    private String nameOfContact;
    private String typeOfServices;
    private String serviceComponents;
    private String targetGroups;
    private String latitude;
    private String longitude;
    private String dateModified;
    private String state;
    private String lga;
    private String ward;
    private String community;
    
    public ReferralDirectoryForm() {
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

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public String getLga() {
        return lga;
    }

    public void setLga(String lga) {
        this.lga = lga;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getNameOfContact() {
        return nameOfContact;
    }

    public void setNameOfContact(String nameOfContact) {
        this.nameOfContact = nameOfContact;
    }

    public String getServiceComponents() {
        return serviceComponents;
    }

    public void setServiceComponents(String serviceComponents) {
        this.serviceComponents = serviceComponents;
    }

    public String getSourceOfFunding() {
        return sourceOfFunding;
    }

    public void setSourceOfFunding(String sourceOfFunding) {
        this.sourceOfFunding = sourceOfFunding;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTargetGroups() {
        return targetGroups;
    }

    public void setTargetGroups(String targetGroups) {
        this.targetGroups = targetGroups;
    }

    public String getTypeOfOrganization() {
        return typeOfOrganization;
    }

    public void setTypeOfOrganization(String typeOfOrganization) {
        this.typeOfOrganization = typeOfOrganization;
    }

    public String getTypeOfServices() {
        return typeOfServices;
    }

    public void setTypeOfServices(String typeOfServices) {
        this.typeOfServices = typeOfServices;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        actionName=null;
        facilityName=null;
        facilityId=null;
        typeOfOrganization=null;
        sourceOfFunding=null;
        address=null;
        contactNumber=null;
        contactEmail=null;
        nameOfContact=null;
        typeOfServices=null;
        serviceComponents=null;
        targetGroups=null;
        latitude="0.0";
        longitude="0.0";
        dateModified=null;
    }
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        AppUtility appUtil=new AppUtility();
        String[] exceptions={"."};
        String[] nameExceptions={".",","};
        if(getActionName()==null || getActionName().equalsIgnoreCase("lga") || getActionName().equalsIgnoreCase("ward") || getActionName().equalsIgnoreCase("delete"))
        return errors;
        else if(getCommunity()==null || !appUtil.isString(getCommunity()))
        errors.add("community", new ActionMessage("refdir.community.required"));
        else if(this.getFacilityName()==null || !appUtil.isString(getFacilityName()))
        errors.add("facilityName", new ActionMessage("refdir.facilityName.required"));
        else if(appUtil.hasSpecialCharacters(getFacilityName(),nameExceptions))
        errors.add("facilityName", new ActionMessage("refdir.facilityName.invalid"));
        else if(getTypeOfOrganization()==null || !appUtil.isString(getTypeOfOrganization()))
        errors.add("typeOfOrganization", new ActionMessage("refdir.typeOfOrganization.required"));
        else if(getNameOfContact() !=null && appUtil.hasSpecialCharacters(getNameOfContact(),exceptions))
        errors.add("nameOfContact", new ActionMessage("refdir.nameOfContact.invalid"));
        else if(getLatitude() !=null && (appUtil.hasSpecialCharacters(getLatitude(),exceptions)))
        errors.add("latitude", new ActionMessage("refdir.latitude.invalid"));
        else if(getLongitude() !=null && (appUtil.hasSpecialCharacters(getLongitude(),exceptions)))
        errors.add("longitude", new ActionMessage("refdir.longitude.invalid"));
        return errors;
    }
}
