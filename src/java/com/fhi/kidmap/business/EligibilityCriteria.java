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
public class EligibilityCriteria implements Serializable
{
    private int id;
    private String eligibilityName;
    private String code;
    private int markedForDelete;

    public String getEligibilityName() {
        return eligibilityName;
    }

    public void setEligibilityName(String eligibilityName) {
        this.eligibilityName = eligibilityName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMarkedForDelete() {
        return markedForDelete;
    }

    public void setMarkedForDelete(int markedForDelete) {
        this.markedForDelete = markedForDelete;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
