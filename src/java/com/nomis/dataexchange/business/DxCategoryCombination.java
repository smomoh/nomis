/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomis.dataexchange.business;

import java.io.Serializable;

/**
 *
 * @author smomoh
 */
public class DxCategoryCombination implements Serializable
{
    private String catComboId;
    private String catComboName;
    private String lastModifiedDate;
    private String dhisInstance;

    public String getCatComboId() {
        return catComboId;
    }

    public void setCatComboId(String catComboId) {
        this.catComboId = catComboId;
    }

    public String getCatComboName() {
        return catComboName;
    }

    public void setCatComboName(String catComboName) {
        this.catComboName = catComboName;
    }

    public String getDhisInstance() {
        return dhisInstance;
    }

    public void setDhisInstance(String dhisInstance) {
        this.dhisInstance = dhisInstance;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
    
}
