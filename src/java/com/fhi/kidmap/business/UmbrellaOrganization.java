/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.business;

/**
 *
 * @author smomoh
 */
public class UmbrellaOrganization
{
    private String recordId;
    private String umbrellaOrganizationCode;
    private String subOrganizationCodes;
    private String dateModified;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getSubOrganizationCodes() {
        return subOrganizationCodes;
    }

    public void setSubOrganizationCodes(String subOrganizationCodes) {
        this.subOrganizationCodes = subOrganizationCodes;
    }

    public String getUmbrellaOrganizationCode() {
        return umbrellaOrganizationCode;
    }

    public void setUmbrellaOrganizationCode(String umbrellaOrganizationCode) {
        this.umbrellaOrganizationCode = umbrellaOrganizationCode;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }
    
}
