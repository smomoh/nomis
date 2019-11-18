/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import com.fhi.nomis.nomisutils.AccessManager;
import com.fhi.nomis.nomisutils.NomisConstant;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author COMPAQ USER
 */
public class UserSetupForm extends org.apache.struts.action.ActionForm {
    

    private String[] assignedGroupId;
    private String userGroupId;
    private String[] orgUnitGroupId;
    private String existingStates;
    private String user_role;
    private String userRoleName;
    private String surname;
    private String other_names;
    private String user_name;
    private String address;
    private String phone;
    private String email;
    private String userId;
    private String pwd;
    private String oldPwd;
    private String confirm_pwd;
    private String userList;
    private String actionName;
    private String changePwd;
    private String viewClientDetails;
    private String accountStatus;
    private String resourceLocation;
    private String[] selectedPartners;

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
    public String getConfirm_pwd() {
        return confirm_pwd;
    }

    public void setConfirm_pwd(String confirm_pwd) {
        this.confirm_pwd = confirm_pwd;
    }

    public String getExistingStates() {
        return existingStates;
    }

    public void setExistingStates(String existingStates) {
        this.existingStates = existingStates;
    }


    public String getOther_names() {
        return other_names;
    }

    public void setOther_names(String other_names) {
        this.other_names = other_names;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String[] getAssignedGroupId() {
        return assignedGroupId;
    }

    public void setAssignedGroupId(String[] assignedGroupId) {
        this.assignedGroupId = assignedGroupId;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    public String getUserRoleName() {
        return userRoleName;
    }

    public void setUserRoleName(String userRoleName) {
        this.userRoleName = userRoleName;
    }
    
    public String getUserList() {
        return userList;
    }

    public void setUserList(String userList) {
        this.userList = userList;
    }

    public String getChangePwd() {
        return changePwd;
    }

    public void setChangePwd(String changePwd) {
        this.changePwd = changePwd;
    }

    public String getViewClientDetails() {
        return viewClientDetails;
    }

    public void setViewClientDetails(String viewClientDetails) {
        this.viewClientDetails = viewClientDetails;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     *
     */
    public UserSetupForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String[] getOrgUnitGroupId() {
        return orgUnitGroupId;
    }

    public void setOrgUnitGroupId(String[] orgUnitGroupId) {
        this.orgUnitGroupId = orgUnitGroupId;
    }

    public String getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(String userGroupId) {
        this.userGroupId = userGroupId;
    }

    public String getResourceLocation() {
        return resourceLocation;
    }

    public void setResourceLocation(String resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    public String[] getSelectedPartners() {
        return selectedPartners;
    }

    public void setSelectedPartners(String[] selectedPartners) {
        this.selectedPartners = selectedPartners;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        assignedGroupId=null;
        userGroupId=null;
        orgUnitGroupId=null;
        existingStates=null;
        oldPwd=null;
        user_role=null;
        surname=null;
        other_names=null;
        user_name=null;
        pwd=null;
        confirm_pwd=null;
        actionName=null;
        changePwd="no";
        viewClientDetails="no";
        address=null;
        phone=null;
        email=null;
        accountStatus="disabled";
        selectedPartners=null;
    }
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
     if(getActionName() !=null && (getActionName().equalsIgnoreCase("save") || getActionName().equalsIgnoreCase("modify")))
     {
        if(getSurname()==null || getSurname().length()<1)
            errors.add("surname", new ActionMessage("error.surname.required"));
        else if(getSurname()!=null && getSurname().length()>24)
            errors.add("surname", new ActionMessage("error.surname.morethanrequired"));
        else if(this.getOther_names()==null || this.getOther_names().trim().length() < 1)
            errors.add("othernames", new ActionMessage("error.othernames.required"));
        else if(this.getOther_names()!=null && this.getOther_names().trim().length() > 24)
            errors.add("othernames", new ActionMessage("error.othernames.morethanrequired"));
        else if(getUser_name() ==null || getUser_name().trim().length()<3)
            errors.add("user_name", new ActionMessage("error.username.required"));
        else if(getUser_name() !=null && getUser_name().trim().length() >24)
            errors.add("user_name", new ActionMessage("error.username.morethanrequired"));
        else if(getUser_role().trim()==null || getUser_role().trim().length()<1)
            errors.add("user_role", new ActionMessage("error.user_role.required"));
        else if(getPwd()==null || getPwd().trim().length()<5)
        {
            if(getActionName().equalsIgnoreCase("save"))
            {
                errors.add("pwd", new ActionMessage("error.pwd.required"));
            }
        }
        else if(getPwd() !=null && getPwd().trim().length() >24)
        errors.add("pwd", new ActionMessage("error.pwd.morethanrequired"));
        else if(getConfirm_pwd().trim()==null || !getConfirm_pwd().trim().equals(getPwd().trim()))
        errors.add("confirm_pwd", new ActionMessage("error.confirm_pwd.required"));
        if((!getUser_role().trim().equalsIgnoreCase(NomisConstant.SUPERUSER_ROLEID)))
        {
            if(getUserGroupId()==null || getUserGroupId().length() < 1)
            errors.add("userGroupId", new ActionMessage("error.user.usergroup.required"));
            else if(getUserGroupId().equalsIgnoreCase("defaultgpId"))
            return errors;
            /*if(getOrgUnitGroupId()==null || getOrgUnitGroupId().length < 1)
            errors.add("orgUnitGroupId", new ActionMessage("error.user.orgunitgroup.required"));*/
        }
     }
        return errors;
    }
    
}
