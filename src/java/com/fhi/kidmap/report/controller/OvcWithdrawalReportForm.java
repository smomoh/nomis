/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.report.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author COMPAQ USER
 */
public class OvcWithdrawalReportForm extends org.apache.struts.action.ActionForm {
    
    private String state;
    private String lga;
    private String orgUnitGroupId;
    private String cbo;
    private String partnerCode;
    private String type;
    private String reasonWithdrawn;
    private int serviceMonth;
    private int serviceYear;
    private int serviceMonth2;
    private int serviceYear2;
    private String actionName;


    /**
     * @return
     */
    
    /**
     *
     */
    public OvcWithdrawalReportForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getCbo() {
        return cbo;
    }

    public void setCbo(String cbo) {
        this.cbo = cbo;
    }

    public String getLga() {
        return lga;
    }

    public void setLga(String lga) {
        this.lga = lga;
    }

    public String getOrgUnitGroupId() {
        return orgUnitGroupId;
    }

    public void setOrgUnitGroupId(String orgUnitGroupId) {
        this.orgUnitGroupId = orgUnitGroupId;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getReasonWithdrawn() {
        return reasonWithdrawn;
    }

    public void setReasonWithdrawn(String reasonWithdrawn) {
        this.reasonWithdrawn = reasonWithdrawn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getServiceMonth() {
        return serviceMonth;
    }

    public void setServiceMonth(int serviceMonth) {
        this.serviceMonth = serviceMonth;
    }

    public int getServiceMonth2() {
        return serviceMonth2;
    }

    public void setServiceMonth2(int serviceMonth2) {
        this.serviceMonth2 = serviceMonth2;
    }

    public int getServiceYear() {
        return serviceYear;
    }

    public void setServiceYear(int serviceYear) {
        this.serviceYear = serviceYear;
    }

    public int getServiceYear2() {
        return serviceYear2;
    }

    public void setServiceYear2(int serviceYear2) {
        this.serviceYear2 = serviceYear2;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        state=null;
        String lga=null;
        String cbo=null;
        String partnerCode=null;
        String type=null;
        actionName=null;
        /*serviceMonth=0;
        serviceYear=0;
        serviceMonth2;
        serviceYear2;*/
    }
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        
        return errors;
    }
}
