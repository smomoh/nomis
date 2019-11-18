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
public class IndicatorsForm extends org.apache.struts.action.ActionForm {

    String dataElementId;
    int dataElementHiddenId;
    String indicatorName;
    String indicatorType;
    String indicatorSubtype;
    String query;
    String queryCriteria;
    String categoryOptionCombo;
    String indicatorList;
    String actionName;
    /**
     *
     */
    public IndicatorsForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getCategoryOptionCombo() {
        return categoryOptionCombo;
    }

    public void setCategoryOptionCombo(String categoryOptionCombo) {
        this.categoryOptionCombo = categoryOptionCombo;
    }

    public int getDataElementHiddenId() {
        return dataElementHiddenId;
    }

    public void setDataElementHiddenId(int dataElementHiddenId) {
        this.dataElementHiddenId = dataElementHiddenId;
    }

    public String getDataElementId() {
        return dataElementId;
    }

    public void setDataElementId(String dataElementId) {
        this.dataElementId = dataElementId;
    }



    public String getIndicatorName() {
        return indicatorName;
    }

    public void setIndicatorName(String indicatorName) {
        this.indicatorName = indicatorName;
    }

    public String getIndicatorList() {
        return indicatorList;
    }

    public void setIndicatorList(String indicatorList) {
        this.indicatorList = indicatorList;
    }

    public String getIndicatorSubtype() {
        return indicatorSubtype;
    }

    public void setIndicatorSubtype(String indicatorSubtype) {
        this.indicatorSubtype = indicatorSubtype;
    }

    public String getIndicatorType() {
        return indicatorType;
    }

    public void setIndicatorType(String indicatorType) {
        this.indicatorType = indicatorType;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getQueryCriteria() {
        return queryCriteria;
    }

    public void setQueryCriteria(String queryCriteria) {
        this.queryCriteria = queryCriteria;
    }

    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    @Override
    public void reset(ActionMapping mapping,HttpServletRequest requestt)
    {
        dataElementId=null;
        dataElementHiddenId=0;
        indicatorName=null;
        indicatorType=null;
        indicatorSubtype=null;
        query=null;
        queryCriteria=null;
        categoryOptionCombo=null;
        indicatorList=null;
        actionName=null;
    }
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();

        if(getActionName() !=null && !getActionName().equalsIgnoreCase("delete") && !getActionName().equalsIgnoreCase("indicatorDetails"))
        {
            if(getIndicatorName()==null || getIndicatorName().length()<1)
            errors.add("indicatorName", new ActionMessage("error.indicatorName.length"));
            else if(getIndicatorType()==null || getIndicatorType().length()<1)
            errors.add("indicatorType", new ActionMessage("error.indicatorType.length"));
            else if(getIndicatorSubtype()==null || getIndicatorSubtype().length()<1)
            errors.add("indicatorSubtype", new ActionMessage("error.indicatorSubtype.length"));
            else if(getQuery()==null || getQuery().length()<1)
            errors.add("query", new ActionMessage("error.query.length"));
        }
        return errors;
    }
}
