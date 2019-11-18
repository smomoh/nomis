/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nomis.business;

import java.io.Serializable;

/**
 *
 * @author smomoh
 */
public class OrganizationUnit implements Serializable
{
    private String orgunitid;
    private String parentid;
    private String orgunitname;

    public String getOrgunitid() {
        return orgunitid;
    }

    public void setOrgunitid(String orgunitid) {
        this.orgunitid = orgunitid;
    }

    public String getOrgunitname() {
        return orgunitname;
    }

    public void setOrgunitname(String orgunitname) {
        this.orgunitname = orgunitname;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }
}
