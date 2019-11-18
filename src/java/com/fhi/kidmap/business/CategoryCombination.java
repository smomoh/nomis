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
public class CategoryCombination implements Serializable
{
    private String categoryId;
    private String categoryComboId;
    private String categoryComboName;
    private String description;
    private String query;
    private String programArea;
    private String type;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
    public String getCategoryComboName() {
        return categoryComboName;
    }

    public void setCategoryComboName(String categoryComboName) {
        this.categoryComboName = categoryComboName;
    }

    
    public String getCategoryComboId() {
        return categoryComboId;
    }

    public void setCategoryComboId(String categoryComboId) {
        this.categoryComboId = categoryComboId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getType() {
        return type;
    }

    public String getProgramArea() {
        return programArea;
    }

    public void setProgramArea(String programArea) {
        this.programArea = programArea;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
