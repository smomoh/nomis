/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.nomis.nomisutils.LoadUpInfo;
import com.fhi.kidmap.business.User;
import com.fhi.kidmap.dao.UserDao;
import com.fhi.kidmap.dao.UserDaoImpl;
import com.fhi.nomis.nomisutils.AccessManager;
import com.fhi.nomis.nomisutils.NomisConstant;
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
public class AdminUserSetupAction extends org.apache.struts.action.Action {
    
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
       UserSetupForm userForm=(UserSetupForm)form;
        User user=new User();
        UserDao dao=new UserDaoImpl();
        String requiredAction=userForm.getActionName();
        HttpSession session=request.getSession();
        AppUtility appUtil=new AppUtility();
        String msg=" ";
        //This action is called when the user logs in with username:admin, Password:admin to create a new super user account after deployment
        if(requiredAction.equals("save"))
        {
            List list=dao.getUserInfo(userForm.getUser_name().trim());
            int listSize=list.size();
            if(listSize > 0 )
            {
                ActionErrors errors = new ActionErrors();
                errors.add(ActionErrors.GLOBAL_MESSAGE,
                new ActionMessage("errors.user_name.exist","user_name"));
                if (!errors.isEmpty())
                {
                    saveErrors(request, errors);
                }
                return mapping.findForward(FAILURE);
            }
        }
            user.setFirstName(userForm.getSurname().trim());
            user.setLastName(userForm.getOther_names().trim());
            user.setUserGroupId(NomisConstant.DEFAULTGRPID);
            user.setAssignedGroupId(NomisConstant.DEFAULTGRPID);
            user.setUserroleId(NomisConstant.SUPERUSER_ROLEID);
            user.setUsername(userForm.getUser_name().trim());
            user.setAddress(userForm.getAddress());
            user.setPhone(userForm.getPhone());
            user.setEmail(userForm.getEmail());
            user.setPassword(userForm.getPwd().trim());
            user.setChangePwd("yes");
            user.setViewClientDetails("yes");
            user.setDateCreated(appUtil.getCurrentDate());
            user.setLastModified(appUtil.getCurrentDate());
            user.setAccountStatus(NomisConstant.ENABLEDACCOUNTSTATUS);
                       
        try
        {
            if(requiredAction.equals("save"))
            dao.saveUser(user);
            else
            {
                ActionErrors errors = new ActionErrors();
                errors.add(ActionErrors.GLOBAL_MESSAGE,
                new ActionMessage("admin.accountCreationError","accountError"));
                saveErrors(request, errors);
                return mapping.findForward(FAILURE);
            }
            AccessManager.enableMetaDataAccess(session);
            /*DatabaseUtilities dbUtils=new DatabaseUtilities();
            dbUtils.runDatabaseUpdateForVersion21();*/
            appUtil.disableDefaultAccount();
            String userRole=userForm.getUser_role();
            String userRealNames=userForm.getSurname().trim()+" "+userForm.getOther_names().trim();
            
            LoadUpInfo loadup=new LoadUpInfo();
            String userName=userForm.getUser_name().trim();
            loadup.setNewLoginInfo(session,userName,userRole,userRealNames,"defaultgrp");
        }
        catch(Exception ex)
        {
            ex.getMessage();
        }
        form.reset(mapping, request);
        return mapping.findForward(SUCCESS);
    }
}
