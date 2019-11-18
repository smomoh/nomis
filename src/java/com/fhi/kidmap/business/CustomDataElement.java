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
public class CustomDataElement implements Serializable
{
    private String categoryComboId;
    private String customDeId;
    private String customDeName;
    private String customDeDescription;
    private String stdDeId;
    private String stdDeDescription;

    public String getCategoryComboId() {
        return categoryComboId;
    }

    public void setCategoryComboId(String categoryComboId) {
        this.categoryComboId = categoryComboId;
    }

    public String getCustomDeDescription() {
        return customDeDescription;
    }

    public void setCustomDeDescription(String customDeDescription) {
        this.customDeDescription = customDeDescription;
    }

    public String getCustomDeId() {
        return customDeId;
    }

    public void setCustomDeId(String customDeId) {
        this.customDeId = customDeId;
    }

    public String getCustomDeName() {
        return customDeName;
    }

    public void setCustomDeName(String customDeName) {
        this.customDeName = customDeName;
    }

    public String getStdDeDescription() {
        return stdDeDescription;
    }

    public void setStdDeDescription(String stdDeDescription) {
        this.stdDeDescription = stdDeDescription;
    }

    public String getStdDeId() {
        return stdDeId;
    }

    public void setStdDeId(String stdDeId) {
        this.stdDeId = stdDeId;
    }
}
