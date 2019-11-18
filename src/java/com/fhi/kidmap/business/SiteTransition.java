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
public class SiteTransition implements Serializable
{
    private String siteCode;
    private String dateOfTransition;
    private String dateModified;
    private String organizationSiteTransitionedTo;
    private String recordedBy;

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getDateOfTransition() {
        return dateOfTransition;
    }

    public void setDateOfTransition(String dateOfTransition) {
        this.dateOfTransition = dateOfTransition;
    }

    public String getOrganizationSiteTransitionedTo() {
        return organizationSiteTransitionedTo;
    }

    public void setOrganizationSiteTransitionedTo(String organizationSiteTransitionedTo) {
        this.organizationSiteTransitionedTo = organizationSiteTransitionedTo;
    }

    public String getRecordedBy() {
        return recordedBy;
    }

    public void setRecordedBy(String recordedBy) {
        this.recordedBy = recordedBy;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }
    
}
