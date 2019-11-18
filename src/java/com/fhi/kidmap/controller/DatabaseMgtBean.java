/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author COMPAQ USER
 */
public class DatabaseMgtBean extends org.apache.struts.action.ActionForm {
    
    private String actionName;
    private String state;
    private String lga;
    private String cbo;
    private String partnerCode;
    private boolean entirePeriod;
    private String month1;
    private String year1;
    private String month2;
    private String year2;
    private String dbActionName;
    private String importFileName;
    private FormFile uploadedFile;

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

    public boolean isEntirePeriod() {
        return entirePeriod;
    }

    public void setEntirePeriod(boolean entirePeriod) {
        this.entirePeriod = entirePeriod;
    }

    public String getLga() {
        return lga;
    }

    public void setLga(String lga) {
        this.lga = lga;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public String getDbActionName() {
        return dbActionName;
    }

    public void setDbActionName(String dbActionName) {
        this.dbActionName = dbActionName;
    }

    public String getImportFileName() {
        return importFileName;
    }

    public void setImportFileName(String importFileName) {
        this.importFileName = importFileName;
    }

    public FormFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(FormFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }
    

    /**
     *
     */
    public DatabaseMgtBean() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public void reset(ActionMapping mapping,
HttpServletRequest request)
    {
       importFileName=null;
       actionName=null;
       dbActionName=null;
       entirePeriod=false;
    }
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if(getDbActionName()==null || getDbActionName().length()<1)
        return errors;
        //errors.add("dbActionName", new ActionMessage("error.dbactionrequired"));
        else if(getDbActionName().equalsIgnoreCase("dbexport"))
        {
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
        }
        else if(getDbActionName().equalsIgnoreCase("dbimport"))
        {
            if(getImportFileName()==null || getImportFileName().equals("") || getImportFileName().equals(" "))
            errors.add("importFileName", new ActionMessage("error.importFileName.required"));
        }
        return errors;
    }
}
