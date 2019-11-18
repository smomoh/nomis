/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.business;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author smomoh
 */
public class OrganizationUnit implements Serializable
{
    private String orgunitId;
    private String orgunitName;
    private String orgunitShortCode;
    private String parentId;
    private int level;
    private OrganizationUnit parent;
    private List children;

    public List getChildren() {
        return children;
    }

    public void setChildren(List children) {
        this.children = children;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getOrgunitId() {
        return orgunitId;
    }

    public void setOrgunitId(String orgunitId) {
        this.orgunitId = orgunitId;
    }

    public String getOrgunitName() {
        return orgunitName;
    }

    public void setOrgunitName(String orgunitName) {
        this.orgunitName = orgunitName;
    }

    public String getOrgunitShortCode() {
        return orgunitShortCode;
    }

    public void setOrgunitShortCode(String orgunitShortCode) {
        this.orgunitShortCode = orgunitShortCode;
    }

    public OrganizationUnit getParent() {
        return parent;
    }

    public void setParent(OrganizationUnit parent) {
        this.parent = parent;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    
}
