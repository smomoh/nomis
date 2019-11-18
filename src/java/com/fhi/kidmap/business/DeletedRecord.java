/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.business;

/**
 *
 * @author smomoh
 */
public class DeletedRecord 
{
    private int id;
    private String recordId;
    private String typeOfRecord;
    private String dateRecordCreated;
    private String dateRecordDeleted;
    private String newUniqueId;

    public String getDateRecordCreated() {
        return dateRecordCreated;
    }

    public void setDateRecordCreated(String dateRecordCreated) {
        this.dateRecordCreated = dateRecordCreated;
    }

    public String getDateRecordDeleted() {
        return dateRecordDeleted;
    }

    public void setDateRecordDeleted(String dateRecordDeleted) {
        this.dateRecordDeleted = dateRecordDeleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getNewUniqueId() {
        return newUniqueId;
    }

    public void setNewUniqueId(String newUniqueId) {
        this.newUniqueId = newUniqueId;
    }

    public String getTypeOfRecord() {
        return typeOfRecord;
    }

    public void setTypeOfRecord(String typeOfRecord) {
        this.typeOfRecord = typeOfRecord;
    }
}
