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
public class BirthRegistration implements Serializable
{
    private String recordId;
    private String clientId;
    private String birthRegistrationStatus;
    private String recordStatus;
    private String dateOfRegistration;
    private String dateModified;
    private String clientType;
    private String pointOfUpdate;
    private int markedForDelete;

    public String getBirthRegistrationStatus() {
        return birthRegistrationStatus;
    }

    public void setBirthRegistrationStatus(String birthRegistrationStatus) {
        this.birthRegistrationStatus = birthRegistrationStatus;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(String dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

    public String getPointOfUpdate() {
        return pointOfUpdate;
    }

    public void setPointOfUpdate(String pointOfUpdate) {
        this.pointOfUpdate = pointOfUpdate;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }

    public int getMarkedForDelete() {
        return markedForDelete;
    }

    public void setMarkedForDelete(int markedForDelete) {
        this.markedForDelete = markedForDelete;
    }
    
}
