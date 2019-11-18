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
public class TrainingStatusSetupForm extends org.apache.struts.action.ActionForm {
    
    private String actionName;
    private String hiddenId;
    private String statusId;
    private String trainingStatusName;
    private List trainingStatusList=new ArrayList();
    public TrainingStatusSetupForm() {
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

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public List getTrainingStatusList() {
        return trainingStatusList;
    }

    public void setTrainingStatusList(List trainingStatusList) {
        this.trainingStatusList = trainingStatusList;
    }

    public String getTrainingStatusName() {
        return trainingStatusName;
    }

    public void setTrainingStatusName(String trainingStatusName) {
        this.trainingStatusName = trainingStatusName;
    }
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        actionName=null;
        hiddenId=null;
        statusId=null;
        trainingStatusName=null;
        //trainingStatusList=null;
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
        else if(getTrainingStatusName()==null || getTrainingStatusName().length() <1)
        errors.add("trainingStatusName", new ActionMessage("name.invalid"));
        return errors;
    }
}
