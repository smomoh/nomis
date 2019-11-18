/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report.controller;

import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.nomis.nomisutils.DatabaseUtilities;
import com.fhi.nomis.nomisutils.LoadUpInfo;
import com.fhi.kidmap.report.ServiceRecords;
import com.fhi.nomis.nomisutils.TaskManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Siaka
 */
public class ServiceReportGeneratorAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    /**
     * This is the action called from the Struts framework.
     *
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
        ServiceReportGeneratorForm gsf=(ServiceReportGeneratorForm)form;
        String stateCode=gsf.getStateCode();
        String startMth=gsf.getStartMth();
        String endMth=gsf.getEndMth();
        String startYear=gsf.getStartYear();
        String endYear=gsf.getEndYear();
        String updateRecord=gsf.getUpdateRecord();
        String requiredAction=gsf.getActionName();
        HttpSession session=request.getSession();
        LoadUpInfo loadup=new LoadUpInfo();
        loadup.getAllStates(session);
        String msg=" ";
        request.setAttribute("msg", msg);
        TaskManager tm=new TaskManager();
        if(TaskManager.isDatabaseLocked())
        {
            msg=tm.getStatusMessage();
            request.setAttribute("msg", msg);
            return mapping.findForward(SUCCESS);
        }
        if(requiredAction==null)
        {
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("report"))
        {
            DaoUtil util=new DaoUtil();
            TaskManager.setDatabaseLocked(true); 
            DatabaseUtilities dbUtils=new DatabaseUtilities();
            if(updateRecord !=null && updateRecord.equalsIgnoreCase("on"))
            {
                dbUtils.dropServiceReportTable();
                dbUtils.dropSpecificServicesReportTable();
                dbUtils.dropHouseholdUniqueServiceReportTable();
            }
            dbUtils.createServiceReportTable();
            dbUtils.createHouseholdUniqueServiceReportTable();
            dbUtils.createSpecificServiceReportTable();
            
            String[] params={startMth,startYear,endMth,endYear};
            String startDate=util.getStartDate(params);
            String endDate=util.getEndDate(params);
            /*String endDay="31";
            //String startDate=startYear+"-"+startMth+"-01";
            if(endMth.equalsIgnoreCase("2") || endMth.equalsIgnoreCase("02"))
            endDay="28";
            else if((endMth.equalsIgnoreCase("4") || endMth.equalsIgnoreCase("04"))||(endMth.equalsIgnoreCase("6") || endMth.equalsIgnoreCase("06")) || (endMth.equalsIgnoreCase("9") || endMth.equalsIgnoreCase("09")) || (endMth.equalsIgnoreCase("11")))
            endDay="30";*/
            //String endDate=endYear+"-"+endMth+"-"+endDay;
            System.err.println("endDate is "+endDate);
            ServiceRecords srec=new ServiceRecords();
            msg=srec.generateServiceReportData(stateCode,startDate, endDate);
            msg=msg+"; "+srec.generateHivServicesSupportData(stateCode, startDate, endDate);
            msg=msg+"; "+srec.generateCaregiverServiceReportData(stateCode, startDate, endDate);
            request.setAttribute("msg", msg);
            TaskManager.setDatabaseLocked(false);
            return mapping.findForward(SUCCESS);
        }
        return mapping.findForward(SUCCESS);
    }
}
