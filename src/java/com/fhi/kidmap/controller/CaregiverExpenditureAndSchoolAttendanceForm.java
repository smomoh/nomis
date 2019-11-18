/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.nomis.nomisutils.ValidationManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author smomoh
 */
public class CaregiverExpenditureAndSchoolAttendanceForm extends org.apache.struts.action.ActionForm {
    
    private String actionName;
    private String stateCode;
    private int id;
    private int hhSerialNo;
    private String hhUniqueId;
    private String state;
    private String lgaCode;
    private String orgCode;
    private String ward;
    private String caregiverId;
    private String careiverGender;
    private int caregiverAge;
    private String dateOfAssessment;
    private String unexpectedExpenditure;
    private String accessMoney;
    private String[] sourceOfMoney;
    private String[] urgentHhNeeds;
    private String schAttendance;
    private String[] ovcId;
    private String ovcGender;
    private int ovcAge;
    private String reasonsForMissingSch;
    private String volunteerName;
    private String volunteerPhone;

    public CaregiverExpenditureAndSchoolAttendanceForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getAccessMoney() {
        return accessMoney;
    }

    public void setAccessMoney(String accessMoney) {
        this.accessMoney = accessMoney;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public int getCaregiverAge() {
        return caregiverAge;
    }

    public void setCaregiverAge(int caregiverAge) {
        this.caregiverAge = caregiverAge;
    }

    public String getCaregiverId() {
        return caregiverId;
    }

    public void setCaregiverId(String caregiverId) {
        this.caregiverId = caregiverId;
    }

    public String getCareiverGender() {
        return careiverGender;
    }

    public void setCareiverGender(String careiverGender) {
        this.careiverGender = careiverGender;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getDateOfAssessment() {
        return dateOfAssessment;
    }

    public void setDateOfAssessment(String dateOfAssessment) {
        this.dateOfAssessment = dateOfAssessment;
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

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public int getOvcAge() {
        return ovcAge;
    }

    public void setOvcAge(int ovcAge) {
        this.ovcAge = ovcAge;
    }

    public String getOvcGender() {
        return ovcGender;
    }

    public void setOvcGender(String ovcGender) {
        this.ovcGender = ovcGender;
    }

    public String[] getOvcId() {
        return ovcId;
    }

    public void setOvcId(String[] ovcId) {
        this.ovcId = ovcId;
    }

    public String getReasonsForMissingSch() {
        return reasonsForMissingSch;
    }

    public void setReasonsForMissingSch(String reasonsForMissingSch) {
        this.reasonsForMissingSch = reasonsForMissingSch;
    }

    public String getSchAttendance() {
        return schAttendance;
    }

    public void setSchAttendance(String schAttendance) {
        this.schAttendance = schAttendance;
    }

    public String[] getSourceOfMoney() {
        return sourceOfMoney;
    }

    public void setSourceOfMoney(String[] sourceOfMoney) {
        this.sourceOfMoney = sourceOfMoney;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getUnexpectedExpenditure() {
        return unexpectedExpenditure;
    }

    public void setUnexpectedExpenditure(String unexpectedExpenditure) {
        this.unexpectedExpenditure = unexpectedExpenditure;
    }

    public String[] getUrgentHhNeeds() {
        return urgentHhNeeds;
    }

    public void setUrgentHhNeeds(String[] urgentHhNeeds) {
        this.urgentHhNeeds = urgentHhNeeds;
    }

    public String getVolunteerName() {
        return volunteerName;
    }

    public void setVolunteerName(String volunteerName) {
        this.volunteerName = volunteerName;
    }

    public String getVolunteerPhone() {
        return volunteerPhone;
    }

    public void setVolunteerPhone(String volunteerPhone) {
        this.volunteerPhone = volunteerPhone;
    }
@Override
public void reset(ActionMapping mapping, HttpServletRequest request)
{
    actionName=null;
    stateCode=null;
    id=0;
    hhSerialNo=0;
    hhUniqueId=null;
    state=null;
    ward=null;
    caregiverId=null;
    careiverGender=null;
    caregiverAge=0;
    dateOfAssessment=null;
    unexpectedExpenditure=null;
    accessMoney=null;
    sourceOfMoney=null;
    urgentHhNeeds=null;
    schAttendance=null;
    ovcId=null;
    ovcGender=null;
    ovcAge=0;
    reasonsForMissingSch=null;
    volunteerName=null;
    volunteerPhone=null;
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
        if(getActionName()==null || getActionName().equalsIgnoreCase("cboList") || getActionName().equalsIgnoreCase("caregiverList") || getActionName().equalsIgnoreCase("caregiverDetails") || getActionName().equalsIgnoreCase("assessmentDetails") || getActionName().equalsIgnoreCase("delete"))
        return errors;
        else if(this.getCaregiverId()==null || !appUtil.isString(getCaregiverId()))
        errors.add("caregiverId", new ActionMessage("errors.caregiverName.required"));
        else if(getDateOfAssessment()==null || getDateOfAssessment().indexOf("/")==-1)
        errors.add("dateOfAssessment", new ActionMessage("errors.dateOfAssessment.required"));
        else if(!ValidationManager.compareDateWithCurrentDate(getDateOfAssessment()))
        errors.add("dateOfAssessment", new ActionMessage("errors.dateOfAssessment.postdated"));
        else if(getUnexpectedExpenditure() ==null || !appUtil.isString(getUnexpectedExpenditure()) || getUnexpectedExpenditure().equalsIgnoreCase("No"))
        {
            if(this.getAccessMoney() !=null && getAccessMoney().equalsIgnoreCase("Yes"))
            errors.add("unexpectedExpenditure", new ActionMessage("errors.unexpectedExpenditure.required"));
            else if(this.getSourceOfMoney() !=null && this.getSourceOfMoney().length>0)
            errors.add("unexpectedExpenditure", new ActionMessage("errors.unexpectedExpenditure.required"));
        }
        else if(this.getSchAttendance()!=null && this.getSchAttendance().equalsIgnoreCase("Yes"))
        {
            if(this.getOvcId()==null || this.getOvcId().length<1)
            errors.add("ovcId", new ActionMessage("errors.ovc.required"));
        }
        else if(getVolunteerName()==null || this.getVolunteerName().trim().length()==0)
        {
            if(this.getOvcId()==null || this.getOvcId().length<1)
            errors.add("volunteerName", new ActionMessage("errors.volunteerName.required"));
        }
        return errors;
    }
}
