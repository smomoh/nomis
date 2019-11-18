/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.business;

import java.io.Serializable;

/**
 *
 * @author smomoh
 */
public class UserGroup implements Serializable 
{
    private String groupId;
    private String groupName;
    private String groupType;
    private String orgUnitGroup;
    private String selectedUserGroup;
    private String description;
    private String dateCreated;
    private String dateModified;

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getOrgUnitGroup() {
        return orgUnitGroup;
    }

    public void setOrgUnitGroup(String orgUnitGroup) {
        this.orgUnitGroup = orgUnitGroup;
    }

    public String getSelectedUserGroup() {
        return selectedUserGroup;
    }

    public void setSelectedUserGroup(String selectedUserGroup) {
        this.selectedUserGroup = selectedUserGroup;
    }
    
}
