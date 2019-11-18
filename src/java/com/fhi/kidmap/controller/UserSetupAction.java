/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import com.fhi.nomis.nomisutils.AjaxProcessor;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.User;
import com.fhi.kidmap.business.UserGroup;
import com.fhi.kidmap.business.UserRole;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.UserDao;
import com.fhi.kidmap.dao.UserDaoImpl;
import com.fhi.kidmap.dao.UserRoleDao;
import com.fhi.kidmap.dao.UserRoleDaoImpl;
import com.fhi.kidmap.report.ReportUtility;
import com.fhi.nomis.OperationsManagement.UserGroupManager;
import com.fhi.nomis.nomisutils.AccessManager;
import com.fhi.nomis.nomisutils.LoadUpInfo;
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
 * @author COMPAQ USER
 */
public class UserSetupAction extends org.apache.struts.action.Action {
    
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
        UserRoleDao urDao=new UserRoleDaoImpl();
        AppUtility appUtil=new AppUtility();
        DaoUtil util=new DaoUtil();
        HttpSession session=request.getSession();
        String currentUser=appUtil.getCurrentUser(session);
        String requiredAction=userForm.getActionName();
        AccessManager acm=new AccessManager();
        boolean userInRole=acm.isUserInRole("003",currentUser);
        System.err.println("requiredAction is "+requiredAction);
        if(!userInRole)
        {
            if(acm.userCanChangePassword(currentUser))
            {
                session.setAttribute("cpwcurrentUser", currentUser);
                request.setAttribute("wrongPwd", " ");
                return mapping.findForward("changePassword");
            }
            else
            {
                    request.setAttribute("errorMsg", appUtil.getAccessDeniedMessage());
                    return mapping.findForward("accessDenied");
            }
        }
        UserRole ur=new UserRole();
        List urList=urDao.getAllUserRoles();
        
        String username=userForm.getUserList();
        
        
        /*if(!appUtil.hasPriviledgeToAccessPage(session))
        return mapping.findForward("Nouserpriviledge");*/
        LoadUpInfo loadup=new LoadUpInfo();
        loadup.getAllUsers(session);
        //loadup.getAllStatesAsObjects(session);
        //loadup.setParamAttributes(request);
        //List communityList=util.getCommunityListByOrganizationUnitHierachy();
        //request.setAttribute("userAccountCommunityList", communityList);
        List userGroupList=util.getAllUserGroups();
        List orgUnitGroupList=ReportUtility.getOrgUnitGroup("All");
        //if(orgUnitGroupList !=null && !orgUnitGroupList.isEmpty())
        //Collections.sort(orgUnitGroupList);
        session.setAttribute("userGroupList", userGroupList);
        session.setAttribute("orgUnitGroupList", orgUnitGroupList);
        session.setAttribute("urList", urList);
        if(userForm.getUser_role() !=null && userForm.getUser_role().equalsIgnoreCase(appUtil.getSuperUserRoleId()))
        {
            userForm.setChangePwd("yes");
            userForm.setViewClientDetails("yes");
        }
        
        List allPartneLlist=util.getPartnerDaoInstance().getAllPartners();
        session.setAttribute("allPartneLlist", allPartneLlist);
        if(requiredAction==null)
        {
           session.setAttribute("userAccountSaveDisabled", "false");
           session.setAttribute("userAccountModifyDisabled", "true");
           userForm.reset(mapping, request);
           return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equals("userdetails"))
        {
            user=dao.getUser(username);
            
            if(user !=null)
            {
                //session.setAttribute("userdetails", values);
                String[] orgUnitGrpIds=null;
                if(user.getOrgUnitGroupId() !=null)
                orgUnitGrpIds=appUtil.splitString(user.getOrgUnitGroupId(), ",");

                userForm.setSurname(user.getLastName());
                userForm.setOther_names(user.getFirstName());
                userForm.setUser_name(user.getUsername());
                userForm.setUserId(user.getUsername());
                userForm.setOrgUnitGroupId(orgUnitGrpIds);
                userForm.setUser_role(user.getUserroleId());
                userForm.setChangePwd(user.getChangePwd());
                userForm.setViewClientDetails(user.getViewClientDetails());
                userForm.setAddress(user.getAddress());
                userForm.setPhone(user.getPhone());
                userForm.setEmail(user.getEmail());
                userForm.setAccountStatus(user.getAccountStatus());
                userForm.setUserGroupId(user.getUserGroupId());
                if(user.getPartnerCodes() !=null)
                userForm.setSelectedPartners(appUtil.splitString(user.getPartnerCodes(),","));
                session.setAttribute("userAccountSaveDisabled", "true");
                session.setAttribute("userAccountModifyDisabled", "false");
            }
            return mapping.findForward(SUCCESS);
        }
        /*else if(requiredAction.equals("userdetails"))
        {
            user=dao.getUser(username);
            /*AjaxProcessor aproc=new AjaxProcessor();
            String[] values = aproc.getUserDetails(username).split(";");
            String[] orgUnitGrpIds=null;
            if(values[3] !=null)
            orgUnitGrpIds=values[3].split(",");
            session.setAttribute("userdetails", values);
            session.setAttribute("userAccountSaveDisabled", "true");
            session.setAttribute("userAccountModifyDisabled", "false");
            userForm.setSurname(values[0]);
            userForm.setOther_names(values[1]);
            userForm.setUser_name(values[2].trim());
            userForm.setUserId(values[2].trim());
            userForm.setOrgUnitGroupId(orgUnitGrpIds);
            userForm.setUser_role(values[4]);
            userForm.setChangePwd(values[5]);
            userForm.setViewClientDetails(values[6]);
            userForm.setAddress(values[7]);
            userForm.setPhone(values[8]);
            userForm.setEmail(values[9]);
            userForm.setAccountStatus(values[10]);
            userForm.setUserGroupId(values[11]);
            userForm.setSelectedPartners(appUtil.splitString(values[12],","));
            return mapping.findForward(SUCCESS);
        }*/
        else if(requiredAction.equals("save"))
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
                userForm.reset(mapping, request);
                return mapping.findForward(FAILURE);
            }
            
        }

            user.setFirstName(userForm.getSurname().trim());
            user.setLastName(userForm.getOther_names().trim());
            user.setUserGroupId(userForm.getUserGroupId());
            if(userForm.getUser_role().equalsIgnoreCase(NomisConstant.SUPERUSER_ROLEID))
            user.setOrgUnitGroupId(NomisConstant.DEFAULTGRPID);
            else
            {
                UserGroup usg=UserGroupManager.getUserGroupDaoInstance().getUserGroup(user.getUserGroupId());
                if(usg !=null)
                user.setOrgUnitGroupId(usg.getOrgUnitGroup());
                else
                user.setOrgUnitGroupId("N/A");
                //user.setOrgUnitGroupId(appUtil.concatStr(userForm.getOrgUnitGroupId(), null));
            }
            user.setUserroleId(userForm.getUser_role());
            user.setUsername(userForm.getUser_name().trim());
            user.setAddress(userForm.getAddress());
            user.setPhone(userForm.getPhone());
            user.setEmail(userForm.getEmail());
            user.setPassword(userForm.getPwd().trim());
            user.setChangePwd(userForm.getChangePwd());
            user.setViewClientDetails(userForm.getViewClientDetails());
            user.setDateCreated(appUtil.getCurrentDate());
            user.setLastModified(appUtil.getCurrentDate());
            user.setAccountStatus(userForm.getAccountStatus());
            user.setPartnerCodes(appUtil.concatStr(userForm.getSelectedPartners(), null));
            String userId=userForm.getUserId();
            
        try
        {
            if(requiredAction.equals("save"))
            {
                dao.saveUser(user);
                session.setAttribute("userAccountSaveDisabled", "false");
                session.setAttribute("userAccountModifyDisabled", "true");
                userForm.reset(mapping, request);
                loadup.getAllUsers(session);
            }
            else if(requiredAction.equals("modify"))
            {
                user.setUsername(userId);
                dao.updateUser(user);
                session.setAttribute("userAccountSaveDisabled", "false");
                session.setAttribute("userAccountModifyDisabled", "true");
                userForm.reset(mapping, request);
                loadup.getAllUsers(session);
            }
            else if(requiredAction.equals("delete"))
            {
                userForm.setUser_name(userId);
                dao.deleteUser(user);
                session.setAttribute("userAccountSaveDisabled", "false");
                session.setAttribute("userAccountModifyDisabled", "true");
                userForm.reset(mapping, request);
                loadup.getAllUsers(session);
            }
            
        }
        catch(Exception ex)
        {
            ex.getMessage();
        }
        //loadup.getAllUsers(session);
        userForm.reset(mapping, request);
        return mapping.findForward(SUCCESS);
    }
    private void setUserList()
    {
        //List userList=userForm.getUserList();
    }
}
