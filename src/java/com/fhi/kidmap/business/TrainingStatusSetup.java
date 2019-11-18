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
public class TrainingStatusSetup implements Serializable
{
    private String statusId;
    private String trainingStatusName;
    private int markedForDelete;

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getTrainingStatusName() {
        return trainingStatusName;
    }

    public void setTrainingStatusName(String trainingStatusName) {
        this.trainingStatusName = trainingStatusName;
    }

    public int getMarkedForDelete() {
        return markedForDelete;
    }

    public void setMarkedForDelete(int markedForDelete) {
        this.markedForDelete = markedForDelete;
    }
    
}
