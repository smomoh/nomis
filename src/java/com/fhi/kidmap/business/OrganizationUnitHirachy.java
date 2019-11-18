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
public class OrganizationUnitHirachy implements Serializable 
{
    private String hirachyId;
    private String hirachyName;
    private int level;
    private String dateModified;

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getHirachyId() {
        return hirachyId;
    }

    public void setHirachyId(String hirachyId) {
        this.hirachyId = hirachyId;
    }

    public String getHirachyName() {
        return hirachyName;
    }

    public void setHirachyName(String hirachyName) {
        this.hirachyName = hirachyName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    
}
