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
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author smomoh
 */
public class TrainingInformationSetupForm extends org.apache.struts.action.ActionForm {
    
    private String actionName;
    private String hiddenId;
    private String recordId;
    private String trainingName;
    private List trainingInfoList=new ArrayList();

    public TrainingInformationSetupForm() {
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

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public List getTrainingInfoList() {
        return trainingInfoList;
    }

    public void setTrainingInfoList(List trainingInfoList) {
        this.trainingInfoList = trainingInfoList;
    }
    
    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        actionName=null;
        hiddenId=null;
        recordId=null;
        trainingName=null;
        //trainingInfoList=null;
    }
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if(getActionName()==null || getActionName().equalsIgnoreCase("details") || getActionName().equalsIgnoreCase("delete"))
        return errors;
        else if(getTrainingName()==null || getTrainingName().length() <1)
        errors.add("trainingName", new ActionMessage("name.invalid"));
        return errors;
    }
}
