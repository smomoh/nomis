/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.chart.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author smomoh
 */
public class ChartForm extends org.apache.struts.action.ActionForm {
    
    private String chartType;
    private String chartTitle;
    private String month1;
    private String month2;
    private String year1;
    private String year2;

    public ChartForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getChartType() {
        return chartType;
    }

    public void setChartType(String chartType) {
        this.chartType = chartType;
    }

    public String getChartTitle() {
        return chartTitle;
    }

    public void setChartTitle(String chartTitle) {
        this.chartTitle = chartTitle;
    }
    
    public String getMonth1() {
        return month1;
    }

    public void setMonth1(String month1) {
        this.month1 = month1;
    }

    public String getMonth2() {
        return month2;
    }

    public void setMonth2(String month2) {
        this.month2 = month2;
    }

    public String getYear1() {
        return year1;
    }

    public void setYear1(String year1) {
        this.year1 = year1;
    }

    public String getYear2() {
        return year2;
    }

    public void setYear2(String year2) {
        this.year2 = year2;
    }

    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();


        if(getMonth1()==null || getMonth1().length()<1 )
        errors.add("month1", new ActionMessage("error.month1.required"));
        else if(getYear1()==null || getYear1().length()<1 )
        errors.add("year1", new ActionMessage("error.year1.required"));
        else if(getMonth2()==null || getMonth2().length()<1 )
        errors.add("month2", new ActionMessage("error.month2.required"));
        else if(getYear2()==null || getYear2().length()<1 )
        errors.add("year2", new ActionMessage("error.year2.required"));
        else
        {
            int mth1=Integer.parseInt(getMonth1());
            int mth2=Integer.parseInt(getMonth2());
            int yr1=Integer.parseInt(getYear1());
            int yr2=Integer.parseInt(getYear2());

            if(yr2<yr1)
            errors.add("wrongyear2", new ActionMessage("error.year2lessthanyear1"));
            else if(mth2<mth1 && yr2==yr1)
            errors.add("wrongmonth2", new ActionMessage("error.month2lessthanmonth1"));
        }
        return errors;
    }
}
