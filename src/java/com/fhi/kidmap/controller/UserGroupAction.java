/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import com.fhi.kidmap.business.UserGroup;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.PartnersDao;
import com.fhi.kidmap.dao.PartnersDaoImpl;
import com.fhi.kidmap.dao.UserGroupDao;
import com.fhi.kidmap.dao.UserGroupDaoImpl;
import com.fhi.nomis.nomisutils.AccessManager;
import com.fhi.nomis.nomisutils.AppUtility;
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
public class UserGroupAction extends org.apache.struts.action.Action {

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
        UserGroupForm ugform=(UserGroupForm)form;
        AppUtility appUtil=new AppUtility();
        AccessManager acm=new AccessManager();
        //DaoUtil util=new DaoUtil();
        UserGroupDao usgdao=new UserGroupDaoImpl();
        HttpSession session=request.getSession();
        String requiredAction=ugform.getActionName();
        String groupId=ugform.getGroupId();
        String groupName=ugform.getGroupName();
        String groupType=ugform.getGroupType();
        String description=ugform.getDescription();
        String[] selectedOrganizationUnitGroups=ugform.getSelectedOrganizationUnitGroups();
        String selectedUserGroup=ugform.getSelectedUserGroup();
        String assignedOrgUnitGrps=appUtil.concatStr(selectedOrganizationUnitGroups, null);
        
        UserGroup usg=new UserGroup();
        usg.setOrgUnitGroup(assignedOrgUnitGrps);
        usg.setGroupId(groupId);
        usg.setGroupName(groupName);
        usg.setDescription(description);
        usg.setSelectedUserGroup(selectedUserGroup);
        usg.setDateCreated(appUtil.getCurrentDate());
        usg.setDateModified(appUtil.getCurrentDate());
        usg.setGroupType(groupType);
        System.err.println("usg.getGroupType() is "+usg.getGroupType());
        setUserGroupListByGroupType(session,groupType);
        setSelectedUserGroupList(session);
        /*List orgUnitGroupList=acm.getAllOrgUnitGroup();
        session.setAttribute("userAccountOrgUnitGroupList", orgUnitGroupList);*/
        
        
        session.setAttribute("userGroupSaveDisabled", "false");
        session.setAttribute("userGroupModifyDisabled", "true");
        if(requiredAction==null)
        {
            ugform.reset(mapping, request);
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("groupdetails"))
        {
            usg=usgdao.getUserGroup(selectedUserGroup);
            if(usg !=null)
            {
                //System.err.println("User group id is "+usg.getGroupId());
                if(usg.getOrgUnitGroup() !=null)
                ugform.setSelectedOrganizationUnitGroups(usg.getOrgUnitGroup().split(","));
                ugform.setGroupName(usg.getGroupName());
                ugform.setGroupType(usg.getGroupType());
                ugform.setSelectedUserGroup(groupId);
                ugform.setGroupId(usg.getGroupId());
                ugform.setDescription(usg.getDescription());
                session.setAttribute("userGroupSaveDisabled", "true");
                session.setAttribute("userGroupModifyDisabled", "false");
                setUserGroupListByGroupType(session,usg.getGroupType());
            }
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("partnerList"))
        {
            setUserGroupListByGroupType(session,groupType);
            ugform.setGroupType(groupType);
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("save"))
        {
            usgdao.saveUserGroup(usg);
            //System.err.println("usg.getGroupType() in save is "+usg.getGroupType());
            setUserGroupListByGroupType(session,usg.getGroupType());
            //setSelectedUserGroupList(session);
        }
        else if(requiredAction.equalsIgnoreCase("modify"))
        {
            usgdao.updateUserGroup(usg);
            //System.err.println("usg.getGroupType() in modify is "+usg.getGroupType());
            setUserGroupListByGroupType(session,usg.getGroupType());
            //setSelectedUserGroupList(session);
        }
        else if(requiredAction.equalsIgnoreCase("delete"))
        {
            usgdao.deleteUserGroup(usg);
            //System.err.println("usg.getGroupType() in delete is "+usg.getGroupType());
            setUserGroupListByGroupType(session,usg.getGroupType());
            //setSelectedUserGroupList(session);
        }
        ugform.reset(mapping, request);
        setSelectedUserGroupList(session);
        return mapping.findForward(SUCCESS);
    }
    public void setSelectedUserGroupList(HttpSession session)
    {
        DaoUtil util=new DaoUtil();
        List userGroupList=util.getAllUserGroups();
        session.setAttribute("existingUserGroup", userGroupList);
    }
    public void setUserGroupListByGroupType(HttpSession session,String groupType)
    {
        try
        {
            AccessManager acm=new AccessManager();
            PartnersDao pdao=new PartnersDaoImpl();
            List partnerList=pdao.getAllPartners();
            List orgUnitGroupList=acm.getAllOrgUnitGroup();
            if(groupType !=null && groupType.equalsIgnoreCase("FB"))
            {
                session.removeAttribute("userAccountOrgUnitGroupList");
                session.setAttribute("userAccountPartnerList", partnerList);
                
            }
            else
            {
                session.removeAttribute("userAccountPartnerList");
                session.setAttribute("userAccountOrgUnitGroupList", orgUnitGroupList);
                
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
