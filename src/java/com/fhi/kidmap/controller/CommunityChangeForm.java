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
public class CommunityChangeForm extends org.apache.struts.action.ActionForm {
    
    private String actionName;
    private String currentState;
    private String currentLga;
    private String currentCbo;
    private String currentCommunity;
    private String sortOrder;
    private String newState;
    private String newLga;
    private String newCbo;
    private String newCommunity;
    private String partnerCode;
    private String hhUniqueId;
    private String recordToLoad;
    private String[] householdsToChange;
    
    public CommunityChangeForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getCurrentCbo() {
        return currentCbo;
    }

    public void setCurrentCbo(String currentCbo) {
        this.currentCbo = currentCbo;
    }

    public String getCurrentCommunity() {
        return currentCommunity;
    }

    public void setCurrentCommunity(String currentCommunity) {
        this.currentCommunity = currentCommunity;
    }

    public String getCurrentLga() {
        return currentLga;
    }

    public void setCurrentLga(String currentLga) {
        this.currentLga = currentLga;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public String getHhUniqueId() {
        return hhUniqueId;
    }

    public void setHhUniqueId(String hhUniqueId) {
        this.hhUniqueId = hhUniqueId;
    }

    public String[] getHouseholdsToChange() {
        return householdsToChange;
    }

    public void setHouseholdsToChange(String[] householdsToChange) {
        this.householdsToChange = householdsToChange;
    }

    
    public String getNewCbo() {
        return newCbo;
    }

    public void setNewCbo(String newCbo) {
        this.newCbo = newCbo;
    }

    public String getNewCommunity() {
        return newCommunity;
    }

    public void setNewCommunity(String newCommunity) {
        this.newCommunity = newCommunity;
    }

    public String getNewLga() {
        return newLga;
    }

    public void setNewLga(String newLga) {
        this.newLga = newLga;
    }

    public String getNewState() {
        return newState;
    }

    public void setNewState(String newState) {
        this.newState = newState;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getRecordToLoad() {
        return recordToLoad;
    }

    public void setRecordToLoad(String recordToLoad) {
        this.recordToLoad = recordToLoad;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
    
@Override
public void reset(ActionMapping mapping, HttpServletRequest request)
{
    actionName=null;
    householdsToChange=null;
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
