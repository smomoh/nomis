/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomis.chart;

/**
 *
 * @author smomoh
 */
public class DashboardItem 
{
    private String recordId;
    private String objectId;
    private String objectType;
    private String dateCreated;
    private String lastModifiedDate;
    private String dashboardId;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getDashboardId() {
        return dashboardId;
    }

    public void setDashboardId(String dashBoardId) {
        this.dashboardId = dashBoardId;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }
    
}
