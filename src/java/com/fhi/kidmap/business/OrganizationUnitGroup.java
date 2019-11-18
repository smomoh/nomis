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
public class OrganizationUnitGroup implements Serializable
{
    private String orgUnitGroupId;
    private String orgUnitGroupName;
    private String description;
    
    public String getOrgUnitGroupId() {
        return orgUnitGroupId;
    }

    public void setOrgUnitGroupId(String orgUnitGroupId) {
        this.orgUnitGroupId = orgUnitGroupId;
    }

    public String getOrgUnitGroupName() {
        return orgUnitGroupName;
    }

    public void setOrgUnitGroupName(String orgUnitGroupName) {
        this.orgUnitGroupName = orgUnitGroupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
