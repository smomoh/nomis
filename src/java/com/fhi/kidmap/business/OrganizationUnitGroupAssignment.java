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
public class OrganizationUnitGroupAssignment implements Serializable
{
    private int recordId;
    private String orgunitid;
    private String orgUnitGroupId;
    private String parentOrgUnitGroupId;

    public String getOrgUnitGroupId() {
        return orgUnitGroupId;
    }

    public void setOrgUnitGroupId(String orgUnitGroupId) {
        this.orgUnitGroupId = orgUnitGroupId;
    }

    public String getOrgunitid() {
        return orgunitid;
    }

    public void setOrgunitid(String orgunitid) {
        this.orgunitid = orgunitid;
    }

    public String getParentOrgUnitGroupId() {
        return parentOrgUnitGroupId;
    }

    public void setParentOrgUnitGroupId(String parentOrgUnitGroupId) {
        this.parentOrgUnitGroupId = parentOrgUnitGroupId;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }
    
}
