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
public class CategoryOption implements Serializable
{
    private String categoryOptionId;
    private String categoryOptionName;
    private String categoryOptionDescription;
    private String disaggregation;
    private String categoryDefinition;

    public String getCategoryOptionId() {
        return categoryOptionId;
    }

    public void setCategoryOptionId(String categoryOptionId) {
        this.categoryOptionId = categoryOptionId;
    }

    public String getCategoryOptionName() {
        return categoryOptionName;
    }

    public void setCategoryOptionName(String categoryOptionName) {
        this.categoryOptionName = categoryOptionName;
    }

    public String getCategoryOptionDescription() {
        return categoryOptionDescription;
    }

    public void setCategoryOptionDescription(String categoryOptionDescription) {
        this.categoryOptionDescription = categoryOptionDescription;
    }

    public String getDisaggregation() {
        return disaggregation;
    }

    public void setDisaggregation(String disaggregation) {
        this.disaggregation = disaggregation;
    }

    public String getCategoryDefinition() {
        return categoryDefinition;
    }

    public void setCategoryDefinition(String categoryDefinition) {
        this.categoryDefinition = categoryDefinition;
    }
    
    
}
