/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nomis.business;

import java.io.Serializable;

/**
 *
 * @author smomoh
 */
public class Datavalue implements Serializable
{
    private int recordId;
    private String orgunitid;
    private String indicatorid;
    private String categoryComboId;
    private String startdate;
    private int value;

    public String getIndicatorid() {
        return indicatorid;
    }

    public void setIndicatorid(String indicatorid) {
        this.indicatorid = indicatorid;
    }

    public String getCategoryComboId() {
        return categoryComboId;
    }

    public void setCategoryComboId(String categoryComboId) {
        this.categoryComboId = categoryComboId;
    }

    public String getOrgunitid() {
        return orgunitid;
    }

    public void setOrgunitid(String orgunitid) {
        this.orgunitid = orgunitid;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
