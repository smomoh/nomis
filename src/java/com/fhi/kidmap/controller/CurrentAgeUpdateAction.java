/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import com.fhi.kidmap.business.States;
import com.fhi.kidmap.dao.HouseholdEnrollmentDao;
import com.fhi.kidmap.dao.HouseholdEnrollmentDaoImpl;
import com.fhi.kidmap.dao.OvcDao;
import com.fhi.kidmap.dao.OvcDaoImpl;
import com.fhi.kidmap.dao.StatesDao;
import com.fhi.kidmap.dao.StatesDaoImpl;
import com.fhi.nomis.nomisutils.AccessManager;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.nomis.nomisutils.DateManager;
import com.fhi.nomis.nomisutils.TaskManager;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author smomoh
 */
public class CurrentAgeUpdateAction extends org.apache.struts.action.Action {

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
        CurrentAgeUpdateForm cauf=(CurrentAgeUpdateForm)form;
        String[] selectedStates=cauf.getSelectedStates();
        StatesDao stateDao=new StatesDaoImpl();
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        String requiredAction=cauf.getActionName();
        OvcDao dao=new OvcDaoImpl();
        HttpSession session=request.getSession();
        setResourceAvailability(session);
        String msg=" ";
        TaskManager tm=new TaskManager();
        AppUtility appUtil=new AppUtility();
        AccessManager acm=new AccessManager();
        String userName=appUtil.getCurrentUser(session);
        if(!acm.isUserInRole("curagen", userName))
        session.setAttribute("curagenButtonDisabled", "true");
        else
        session.setAttribute("curagenButtonDisabled", "false");
        
        
        //System.err.println("TaskManager.isDatabaseLocked() is "+tm.isDatabaseLocked());
        if(requiredAction==null)
        {
            List stateCodeList=hhedao.getDistinctStateCodes();
            List stateList=new ArrayList();
                for(int i=0; i<stateCodeList.size(); i++)
                {
                    States state=stateDao.getStateByStateCode((String)stateCodeList.get(i));
                    stateList.add(state);
                }
            session.setAttribute("stateCodeListForCurrentAge", stateList);
            
            if(TaskManager.isDatabaseLocked())
            {
                msg=tm.getStatusMessage();
                request.setAttribute("msg", msg);
                return mapping.findForward(SUCCESS);
            }
            cauf.reset(mapping, request);
            return mapping.findForward("paramPage");
        }
        else if(requiredAction.equalsIgnoreCase("generateCurrentAge"))
        {
            String ageGroup=cauf.getAgeGroup();
            TaskManager.setDatabaseLocked(true); 
            if(selectedStates !=null)
            {
                List selectedStateList=new ArrayList();
                for(int i=0; i<selectedStates.length; i++)
                {
                    selectedStateList.add(selectedStates[i]);
                }
                if(ageGroup==null || ageGroup.equalsIgnoreCase("All"))
                msg= dao.updateOvcCurrentAge(selectedStateList);
                else
                {
                    int startAge=0; int endAge=0;
                   if(ageGroup.indexOf("-") !=-1)
                   {
                       String[] ageGroupArray=ageGroup.split("-");
                       startAge=Integer.parseInt(ageGroupArray[0]);
                       endAge=Integer.parseInt(ageGroupArray[1]);
                   }
                   else if(ageGroup.indexOf("+") !=-1)
                   {
                       ageGroup=ageGroup.replace("+", "");
                       startAge=Integer.parseInt(ageGroup);
                       endAge=100;
                   }
                   System.err.println("startAge is "+startAge+" and endAge is "+endAge);
                   msg= dao.updateOvcCurrentAge(selectedStateList,startAge,endAge); 
                }
                
                request.setAttribute("requestMsg", msg);
                cauf.reset(mapping, request); 
            }
            TaskManager.setDatabaseLocked(false);
            return mapping.findForward(SUCCESS);
        }
        return mapping.findForward(SUCCESS);
    }
    private void setResourceAvailability(HttpSession session)
    {
        String currentDate=DateManager.getCurrentDate();
        System.err.println("currentDate is "+currentDate);
        if(currentDate !=null && currentDate.indexOf("-") !=-1)
        {
            String[] currentDateArray=currentDate.split("-");
            int year=Integer.parseInt(currentDateArray[0]);
            int month=Integer.parseInt(currentDateArray[1]);
            if(month==10 && year==2018)
            {
                session.setAttribute("ageGroupForCA", "ageGroupForCA");
            }
            else
            {
                session.removeAttribute("ageGroupForCA");
            }
        }
    }
}
