/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomis.chart.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author smomoh
 */
public class DashboardForm extends org.apache.struts.action.ActionForm {
    
    private String actionName;
    private String dashboardId;
    private String dashboardName;
    private String dashboardType;
    private String dashboardListId;
    private String systemdashboard;
    private String[] chartIds;
    
    public DashboardForm() 
    {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getDashboardId() {
        return dashboardId;
    }

    public void setDashboardId(String dashboardId) {
        this.dashboardId = dashboardId;
    }

    public String getDashboardName() {
        return dashboardName;
    }

    public void setDashboardName(String dashboardName) {
        this.dashboardName = dashboardName;
    }

    public String getDashboardType() {
        return dashboardType;
    }

    public void setDashboardType(String dashboardType) {
        this.dashboardType = dashboardType;
    }

    public String getDashboardListId() {
        return dashboardListId;
    }

    public void setDashboardListId(String dashboardListId) {
        this.dashboardListId = dashboardListId;
    }

    public String getSystemdashboard() {
        return systemdashboard;
    }

    public void setSystemdashboard(String systemdashboard) {
        this.systemdashboard = systemdashboard;
    }

    public String[] getChartIds() {
        return chartIds;
    }

    public void setChartIds(String[] chartIds) {
        this.chartIds = chartIds;
    }

    
@Override
public void reset(ActionMapping mapping, HttpServletRequest request)
{
    actionName=null;
    dashboardId=null;
    dashboardName=null;
    dashboardType=null;
    systemdashboard=null;
    dashboardListId=null;
}
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if(getActionName()==null || getActionName().equalsIgnoreCase("delete") || getActionName().equalsIgnoreCase("details"))
        return errors;
        else if(this.getDashboardName()==null || getDashboardName().length() <3)
        errors.add("dashboardName", new ActionMessage("errors.dashboardName.required"));
        return errors;
    }
}
