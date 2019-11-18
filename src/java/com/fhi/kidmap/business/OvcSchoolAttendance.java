/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.business;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author smomoh
 */
public class OvcSchoolAttendance implements Serializable
{
    private String ovcId;
    private String currentSchoolName;
    private String currentClass;
    private Date dateOfAssessment;
    private String ovcInSchool;
    private String lastPointOfUpdate;
    private Date lastModifiedDate;
    private String recordedBy;
    private int markedForDelete;

    public String getCurrentClass() {
        return currentClass;
    }

    public void setCurrentClass(String currentClass) {
        this.currentClass = currentClass;
    }

    public String getCurrentSchoolName() {
        return currentSchoolName;
    }

    public void setCurrentSchoolName(String currentSchoolName) {
        this.currentSchoolName = currentSchoolName;
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

    public String getLastPointOfUpdate() {
        return lastPointOfUpdate;
    }

    public void setLastPointOfUpdate(String lastPointOfUpdate) {
        this.lastPointOfUpdate = lastPointOfUpdate;
    }

    public String getOvcId() {
        return ovcId;
    }

    public void setOvcId(String ovcId) {
        this.ovcId = ovcId;
    }

    public String getOvcInSchool() {
        return ovcInSchool;
    }

    public void setOvcInSchool(String ovcInSchool) {
        this.ovcInSchool = ovcInSchool;
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
