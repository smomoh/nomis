/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.nomis.nomisutils.DatabaseUtilities;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.HouseholdEnrollmentDao;
import com.fhi.kidmap.dao.HouseholdEnrollmentDaoImpl;
import com.fhi.kidmap.dao.OvcDao;
import com.fhi.kidmap.dao.OvcDaoImpl;
import com.fhi.kidmap.report.OvcRecords;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author smomoh
 */
public class DatabaseUpdateAction extends org.apache.struts.action.Action {
    
    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    private static final String FAILURE = "failure";
    
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
        HttpSession session=request.getSession();
        AppUtility appUtil=new AppUtility();
        OvcDao ovcDao=new OvcDaoImpl();
        DaoUtil util=new DaoUtil();
        /*if(!appUtil.hasPriviledgeToAccessPage(session))
        return mapping.findForward("Nouserpriviledge");*/
        
        String requestAction=request.getParameter("name");
        DatabaseUtilities dbUtils=new DatabaseUtilities();
        OvcRecords records=new OvcRecords();
        /*StatesDao sdao=new StatesDaoImpl();
        LgaDao ldao=new LgaDaoImpl();
        List stateList=sdao.getStates();
        List lgaList=ldao.getLgas();
        appUtil.writeObjectsToFile("C:\\Misc\\states.dat", stateList);
        appUtil.writeObjectsToFile("C:\\Misc\\lgas.dat", lgaList);*/
        
        try
        {
            if(requestAction==null)
            {
                
            }
            else if(requestAction.equalsIgnoreCase("currentAge"))
            {
               String msg= ovcDao.updateOvcCurrentAge();
               HouseholdEnrollmentDao hheDao=new HouseholdEnrollmentDaoImpl();
               String hvimsg=hheDao.updateHheCurrentAge();
               request.setAttribute("requestMsg", msg);
               return mapping.findForward(SUCCESS);
            }
            
            //dbUtils.addCurrentAgeColumn();
            //ReportTableGenerator rtg=new ReportTableGenerator();
            //rtg.cleanUpFollowupSurveyTable();
            //rtg.populateService1Table();
            //rtg.setNoOfServicesInService1Table();
            String oldStateLgaCodes="EDO/EKC";
            String newStateLgaCodes="EDO/EWT";
            //util.correctHhIdInHouseholdEnrollmentData(oldStateLgaCodes, newStateLgaCodes, "TYE","TYE");
            //util.correctOvcIdInEnrollmentData(oldStateLgaCodes, newStateLgaCodes,"UYO","HEL");
            

            //util.correctLGACodesInCsiData(oldStateLgaCodes, newStateLgaCodes);
            //util.correctLGACodesInServiceData(oldStateLgaCodes, newStateLgaCodes);
           // util.correctWardCodesInEnrollmentData();
            request.setAttribute("requestMsg", "Database update completed");

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            ActionErrors errors=new ActionErrors();
            errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("errors.dbupdate.failed"));
            return mapping.findForward(FAILURE);
        }
        return mapping.findForward(SUCCESS);
    }
}
