/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.report.controller;

import com.fhi.kidmap.business.States;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.nomis.nomisutils.DatabaseImportTracker;
import com.fhi.kidmap.business.User;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.DatabaseImportTrackerDao;
import com.fhi.kidmap.dao.DatabaseImportTrackerDaoImpl;
import com.fhi.kidmap.dao.UserDao;
import com.fhi.kidmap.dao.UserDaoImpl;
import com.fhi.nomis.nomisutils.AccessManager;
import com.fhi.nomis.nomisutils.DateManager;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author smomoh
 */
public class DatabaseImportTrackerReportAction extends org.apache.struts.action.Action {
    
    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    
    /**
     * This is the action called from the Struts framework.
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
        DatabaseImportTrackerReportForm ditrf=(DatabaseImportTrackerReportForm)form;
        DatabaseImportTrackerDao ditdao=new DatabaseImportTrackerDaoImpl();
        AppUtility appUtil=new AppUtility();
        DaoUtil util=new DaoUtil();
        DateManager dm=new DateManager();
        String requiredAction=ditrf.getActionName();
        String userName=ditrf.getUserName();
        String startMonth=ditrf.getStartMonth();
        String endMonth=ditrf.getEndMonth();
        int startYear=ditrf.getStartYear();
        int endYear=ditrf.getEndYear();
        String id=request.getParameter("id");
        if(id !=null)
        requiredAction="details";
        String firstDate=appUtil.getCurrentDate();
        String lastDate=appUtil.getCurrentDate();
        List list=ditdao.getDistinctUserNamesFromDatabaseImportTracker();
        request.setAttribute("userNameList", list);
        List dateList=ditdao.getDistinctImportDateFromDatabaseImportTracker();
        if(dateList !=null && !dateList.isEmpty())
        {
            firstDate=(String)dateList.get(0);
            lastDate=(String)dateList.get(dateList.size()-1);
        }
        String[] firstDateArray=firstDate.split("-");
        String[] lastDateArray=lastDate.split("-");
        List monthList=dm.generateMonths();
        List yearList=appUtil.generateYears(Integer.parseInt(firstDateArray[0]), Integer.parseInt(lastDateArray[0]));
        request.setAttribute("monthList", monthList);
        request.setAttribute("yearList", yearList);
        if(requiredAction==null)
        {
            ditrf.reset(mapping, request);
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("report"))
        {
            List reportList=null;
            List ditList=new ArrayList();
            String[] dateParams={startMonth,startYear+"",endMonth,endYear+""};
            String startDate=util.getStartDate(dateParams);
            String endDate=util.getEndDate(dateParams);
            String[] periodParams={startDate,endDate};
            try
            {
                if(userName ==null || userName.equalsIgnoreCase("All"))
                {
                    reportList=ditdao.getDatabaseImportTrackersByPeriod(startDate, endDate);
                }
                else
                {
                    reportList=ditdao.getDatabaseImportTrackersByUserNameAndPeriod(userName, startDate, endDate);
                }
                if(reportList !=null)
                {
                    DatabaseImportTracker dit=null;
                    for(int i=0; i<reportList.size(); i++)
                    {
                        dit=(DatabaseImportTracker)reportList.get(i);
                        dit.setSnumber(i);
                        dit.setDateOfImport(appUtil.getDisplayDate(dit.getDateOfImport()));
                        ditList.add(dit);
                    }
                }
            }
            catch(Exception ex)
            {

            }
            request.setAttribute("ditperiodParams", periodParams);
            request.setAttribute("ditreportList", ditList);
            return mapping.findForward("reportpage");
        }
        else if(requiredAction.equalsIgnoreCase("details"))
        {
            String[] paramArray=id.split(":");
            String userInfo=null;
            int recordId=Integer.parseInt(paramArray[0]);
            DatabaseImportTracker dit=ditdao.getDatabaseImportTracker(recordId);
            List ditList=new ArrayList();
            List summaryList=new ArrayList();

            if(dit !=null)
            {
                UserDao userdao=new UserDaoImpl();
                userName=dit.getUserName();
                User user=userdao.getUser(userName);
                String assignedUserGrpIds=user.getAssignedGroupId();
                String assignedUserGrpId=null;
                String stateName=null;
                if(assignedUserGrpIds !=null && assignedUserGrpIds.indexOf(",") !=-1)
                assignedUserGrpId=assignedUserGrpIds.substring(0,assignedUserGrpIds.indexOf(","));
                List stateList=AccessManager.getInstance().getStateIdsFromUserGroupId(assignedUserGrpId);
                if(stateList !=null && !stateList.isEmpty())
                {
                    States state=(States)stateList.get(0);
                    stateName=state.getName();
                }
                userInfo="State: "+stateName+"            Name: "+user.getFirstName()+" "+user.getLastName();
                ditList.add(dit);// =ditdao.getDatabaseImportTrackersByUserNameAndPeriod(userName, paramArray[1], paramArray[2]);
            }
            if(ditList !=null)
            {
                String dateOfImport=null;
                String summary=null;
                for(int i=0; i<ditList.size(); i++)
                {
                   dit=(DatabaseImportTracker)ditList.get(i);
                   summary=dit.getSummary();
                   if(summary !=null)
                   {
                       //add the date to the array
                       dateOfImport=dit.getDateAndTime();
                       if(dateOfImport==null)
                       dateOfImport=dit.getDateOfImport();
                       summary="Date: "+dateOfImport+";File name: "+dit.getFileName()+";"+summary;
                        String[] summaryArray=summary.split(";");
                        summaryList.add(summaryArray);
                   }
                }
            }
            request.setAttribute("dituserInfo", userInfo);
            request.setAttribute("ditsummaryList", summaryList);
            return mapping.findForward("details");
        }
        return mapping.findForward(SUCCESS);
    }
}
