/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.User;
import com.fhi.kidmap.dao.UserDao;
import com.fhi.kidmap.dao.UserDaoImpl;
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
public class ChangePasswordAction extends org.apache.struts.action.Action {
    
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
        ChangePasswordForm cpf=(ChangePasswordForm)form;
        
        AppUtility appUtil=new AppUtility();
        HttpSession session=request.getSession();
        String currentUser=appUtil.getCurrentUser(session);

        String requiredAction=cpf.getActionName();
        UserDao dao=new UserDaoImpl();
        User user=dao.getUser(currentUser);


        String user_name=cpf.getUser_name();
        if(user !=null)
        user_name=user.getUsername();
        cpf.setUser_name(user_name);
        String oldPwd=cpf.getOldPwd();
        String pwd=cpf.getPwd();
        String confirm_pwd=cpf.getConfirm_pwd();

        if(requiredAction==null)
        {
            cpf.reset(mapping, request);
            cpf.setUser_name(user_name);
            request.setAttribute("wrongPwd", " ");
            return mapping.findForward("changePassword");
        }
        else if(requiredAction.equalsIgnoreCase("changePassword"))
        {
            cpf.setUser_name(user_name);
            if(oldPwd==null)
            {
                request.setAttribute("wrongPwd", "Error: Enter old password");
                return mapping.findForward("changePassword");
            }
            
                if(user !=null)
                {
                    if(oldPwd.equals(user.getPassword()))
                    {
                        if(pwd ==null || pwd.length()<5)
                        {
                            request.setAttribute("wrongPwd", "Error: Password must not be less than 5 characters");
                            return mapping.findForward("changePassword");
                        }
                        else if(pwd.equals(confirm_pwd))
                        {
                            user.setPassword(pwd);
                            dao.updateUser(user);
                            cpf.reset(mapping, request);
                        }
                        else
                        request.setAttribute("wrongPwd", "Error: Password and Confirm password does not match");
                        return mapping.findForward("changePassword");
                    }
                    else
                    {
                        cpf.reset(mapping, request);
                        request.setAttribute("wrongPwd", "Error: Old password is wrong");
                        return mapping.findForward("changePassword");
                    }
                }
                cpf.reset(mapping, request);
        }
        return mapping.findForward("changePassword");
    }
}
