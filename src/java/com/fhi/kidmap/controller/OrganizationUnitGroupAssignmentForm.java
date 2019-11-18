/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author Siaka
 */
public class OrganizationUnitGroupAssignmentForm extends org.apache.struts.action.ActionForm {
    
    private String actionName;
    private String hiddenId;
    private int recordId;
    private String stateCode;
    private String[] orgUnitIds;
    private String orgUnitGroupId;
    private List orgUnitGroupList=new ArrayList();
    private List stateList=new ArrayList();
    
    public OrganizationUnitGroupAssignmentForm() {
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

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String[] getOrgUnitIds() {
        return orgUnitIds;
    }

    public void setOrgUnitIds(String[] orgUnitIds) {
        this.orgUnitIds = orgUnitIds;
    }

          
    public String getOrgUnitGroupId() {
        return orgUnitGroupId;
    }

    public void setOrgUnitGroupId(String orgUnitGroupId) {
        this.orgUnitGroupId = orgUnitGroupId;
    }

    public List getOrgUnitGroupList() {
        return orgUnitGroupList;
    }

    public void setOrgUnitGroupList(List orgUnitGroupList) {
        this.orgUnitGroupList = orgUnitGroupList;
    }
    public List getStateList() {
        return stateList;
    }

    public void setStateList(List stateList) {
        this.stateList = stateList;
    }
@Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        actionName=null;
        hiddenId=null;
        recordId=0;
        stateCode=null;
        orgUnitIds=null;
        orgUnitGroupId=null;
    }
    /**
     * This is the action called from the Struts framework.
     *
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) 
    {
        AppUtility appUtil=new AppUtility();
        ActionErrors errors = new ActionErrors();
        if(getActionName() !=null && (getActionName().equalsIgnoreCase("save") || getActionName().equalsIgnoreCase("modify")))
        {
            if(getActionName().equalsIgnoreCase("save"))
            {
                if(getOrgUnitIds()==null || getOrgUnitIds().length <1)
                {
                    errors.add("orgUnitIds", new ActionMessage("error.orgUnitIds.required"));
                    return errors;
                }
            }
            if(getOrgUnitGroupId()==null || !appUtil.isString(getOrgUnitGroupId()))
            errors.add("orgUnitGroupId", new ActionMessage("error.orgUnitGroupId.required"));
        }
        
        return errors;
    }
}
