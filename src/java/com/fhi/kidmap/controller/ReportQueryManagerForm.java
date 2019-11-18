/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Siaka
 */
public class ReportQueryManagerForm extends org.apache.struts.action.ActionForm {
    
    public String uniqueId;
    public String indicatorId;
    public String categoryComboId;
    public String name;
    public String description;
    public String programArea;
    public String query;
    public String type;
    private String actionName;
    private String hiddenId;
    List indicatorList=new ArrayList();
    List categoryCombinationList=new ArrayList();
    List reportQueryList=new ArrayList();
    
    /**
     *
     */
    public ReportQueryManagerForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(String indicatorId) {
        this.indicatorId = indicatorId;
    }

    public String getCategoryComboId() {
        return categoryComboId;
    }

    public void setCategoryComboId(String categoryComboId) {
        this.categoryComboId = categoryComboId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProgramArea() {
        return programArea;
    }

    public void setProgramArea(String programArea) {
        this.programArea = programArea;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public List getIndicatorList() {
        return indicatorList;
    }

    public void setIndicatorList(List indicatorList) {
        this.indicatorList = indicatorList;
    }

    public List getCategoryCombinationList() {
        return categoryCombinationList;
    }

    public void setCategoryCombinationList(List categoryCombinationList) {
        this.categoryCombinationList = categoryCombinationList;
    }

    public List getReportQueryList() {
        return reportQueryList;
    }

    public void setReportQueryList(List reportQueryList) {
        this.reportQueryList = reportQueryList;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        uniqueId=null;
        indicatorId=null;
        categoryComboId=null;
        name=null;
        description=null;
        programArea=null;
        query=null;
        type=null;
        actionName=null;
        hiddenId=null;
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
