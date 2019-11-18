/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.business;

import java.util.Date;

/**
 *
 * @author smomoh
 */
public class SchoolAttendanceTracker 
{
    private int recordId;
    private String ovcId;
    private Date dateOfAssessment;
    private String childMissSchool;
    private String reasonsForMissingSchool;
    private Date lastModifiedDate;
    private String recordedBy;
    private int markedForDelete;

    public String getChildMissSchool() {
        return childMissSchool;
    }

    public void setChildMissSchool(String childMissSchool) {
        this.childMissSchool = childMissSchool;
    }

    public Date getDateOfAssessment() {
        return dateOfAssessment;
    }

    public void setDateOfAssessment(Date dateOfAssessment) {
        this.dateOfAssessment = dateOfAssessment;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getOvcId() {
        return ovcId;
    }

    public void setOvcId(String ovcId) {
        this.ovcId = ovcId;
    }

    public String getReasonsForMissingSchool() {
        return reasonsForMissingSchool;
    }

    public void setReasonsForMissingSchool(String reasonsForMissingSchool) {
        this.reasonsForMissingSchool = reasonsForMissingSchool;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getRecordedBy() {
        return recordedBy;
    }

    public void setRecordedBy(String recordedBy) {
        this.recordedBy = recordedBy;
    }

    public int getMarkedForDelete() {
        return markedForDelete;
    }

    public void setMarkedForDelete(int markedForDelete) {
        this.markedForDelete = markedForDelete;
    }
    
}
