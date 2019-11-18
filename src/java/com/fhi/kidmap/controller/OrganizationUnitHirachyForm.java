/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.nomis.nomisutils.AppUtility;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author smomoh
 */
public class OrganizationUnitHirachyForm extends org.apache.struts.action.ActionForm {
    
    private String actionName;
    private String hirachyId;
    private String hirachyName;
    private int level;
    private String dateModified;
    
    public OrganizationUnitHirachyForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getHirachyId() {
        return hirachyId;
    }

    public void setHirachyId(String hirachyId) {
        this.hirachyId = hirachyId;
    }

    public String getHirachyName() {
        return hirachyName;
    }

    public void setHirachyName(String hirachyName) {
        this.hirachyName = hirachyName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    @Override
public void reset(ActionMapping mapping, HttpServletRequest request)
{
    actionName=null;
    hirachyId=null;
    hirachyName=null;
    level=1;
    dateModified=null;
}
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) 
    {
        DaoUtil util=new DaoUtil();
        AppUtility appUtil=new AppUtility();
        ActionErrors errors = new ActionErrors();
        if(getActionName()==null || getActionName().equalsIgnoreCase("delete"))
        return errors;
        else if(getHirachyName()==null || !appUtil.isString(getHirachyName()))
        errors.add("hirachyName", new ActionMessage("errors.hirachyName.required"));
        else if(getActionName().equalsIgnoreCase("save") && getHirachyName() !=null && util.getOrganizationUnitHirachyByName(this.getHirachyName().trim()) !=null)
        errors.add("hirachyName", new ActionMessage("errors.hirachyName.exist"));
        return errors;   
    }
}
