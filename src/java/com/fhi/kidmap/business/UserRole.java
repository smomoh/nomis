/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.business;

import java.io.Serializable;

/**
 *
 * @author Siaka
 */
public class UserRole implements Serializable
{
    private String roleId;
    private String roleName;
    private String assignedRights;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getAssignedRights() {
        return assignedRights;
    }

    public void setAssignedRights(String assignedRights) {
        this.assignedRights = assignedRights;
    }
        
}
