/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.CboSetup;
import com.fhi.kidmap.business.User;
import com.fhi.nomis.nomisutils.LoadUpInfo;
import com.fhi.kidmap.dao.CboSetupDao;
import com.fhi.kidmap.dao.CboSetupDaoImpl;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.LgaDao;
import com.fhi.kidmap.dao.LgaDaoImpl;
import com.fhi.kidmap.dao.UserDao;
import com.fhi.kidmap.dao.UserDaoImpl;
import com.fhi.nomis.nomisutils.AccessManager;
import com.fhi.nomis.nomisutils.LoadUpInfo;
import java.util.ArrayList;
import java.util.List;
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
public class CboSetupAction extends org.apache.struts.action.Action {
    
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
            throws Exception {
        //This action manages site setup
        CboSetupForm setupForm=(CboSetupForm)form;
        CboSetup cboSetup=new CboSetup();
        CboSetupDao dao=new CboSetupDaoImpl();
        UserDao userdao=new UserDaoImpl();
        LgaDao ldao=new LgaDaoImpl();
        AppUtility appUtil=new AppUtility();

        String msg=" ";
        /*if(TaskManager.isDatabaseLocked())
        {
            msg="System busy. Kindly try again later";
            request.setAttribute("msg", msg);
            return mapping.findForward(SUCCESS);
        }*/
        HttpSession session=request.getSession();
        String currentUserName=(String)session.getAttribute("USER");
        
        if(currentUserName==null)
        {
            ActionErrors errors = new ActionErrors();
            errors.add(ActionErrors.GLOBAL_MESSAGE,
            new ActionMessage("errors.session.expired",
            "loginrequired"));
            if (!errors.isEmpty())
            {
                saveErrors(request, errors);
            }
            return mapping.findForward(FAILURE);
        }
        AccessManager acm=new AccessManager();
        boolean userInRole=acm.isUserInRole("008",appUtil.getCurrentUser(session));
        if(!userInRole)
        {
            request.setAttribute("errorMsg", appUtil.getAccessDeniedMessage());
            return mapping.findForward("accessDenied");
        }
        //else if(!appUtil.hasPriviledgeToAccessPage(session))
        //return mapping.findForward("Nouserpriviledge");
        String userName=setupForm.getUserName();
        System.err.println("userName is "+userName);
        //cboSetup.setUserName(userName);
        if(userName ==null)
        userName=currentUserName;
        setupForm.setUserName(userName);
        cboSetup.setUserName(userName);
        cboSetup.setLga_code(setupForm.getLga_code());
        String stateCode=setupForm.getState_code();
        cboSetup.setState_code(stateCode);
        cboSetup.setPartner(setupForm.getPartner());
        
        
        String requiredAction=setupForm.getActionName();
        List listOfStates=new ArrayList();
        User currentUser=appUtil.getUser(session);
        List userList=new ArrayList();
        if(currentUser !=null)
        {
            listOfStates=acm.getStateListForStartup(session);//getStateIdsFromUserGroupId(currentUser.getAssignedGroupId());
            if(currentUser.getUserroleId().equalsIgnoreCase("superuser") || (currentUser.getUserGroupId()==null || currentUser.getUserGroupId().equalsIgnoreCase("defaultgpId")))
            userList=userdao.getAllUsers();
            else
            userList=userdao.getUsersByUserGroupId(currentUser.getUserGroupId());
        }
        List emptyList=new ArrayList();
        session.setAttribute("siteSetupUserList", userList);
        session.setAttribute("statesWithRights", listOfStates);
        session.setAttribute("siteSetupLgaList", emptyList);
        try
        {
           
        if(requiredAction==null)
        {
            LoadUpInfo loadup=new LoadUpInfo();
            loadup.setParamAttributes(request);
            loadup.getPartners(session);
            
            return mapping.findForward(SUCCESS);
        }
        
        else if(requiredAction.equals("save"))
        {
            CboSetup cboSetup2=dao.getCboSetup(userName);

            if(cboSetup2==null)
            dao.saveCboSetupInfo(cboSetup);
            else
            dao.updateCboSetupInfo(cboSetup);
            session.setAttribute("siteSetupLgaList", emptyList);
        }
        else if(requiredAction.equals("stateList"))
        {
            userName=setupForm.getUserName();
            listOfStates=acm.getStateListForStartupByUserName(setupForm.getUserName());
            session.setAttribute("statesWithRights", listOfStates);
            return mapping.findForward(SUCCESS);
        }
      }
        catch(Exception ex)
        {
            ActionErrors errors = new ActionErrors();
            errors.add(ActionErrors.GLOBAL_MESSAGE,
            new ActionMessage("errors.database.error",
            ex.getMessage()));
            if (!errors.isEmpty())
            {
                saveErrors(request, errors);
            }
            return mapping.findForward(FAILURE);
        }

        form.reset(mapping, request);
        if(session.getAttribute("statesWithRights")==null)
        session.setAttribute("statesWithRights", listOfStates);
        return mapping.findForward(SUCCESS);
    }
}
