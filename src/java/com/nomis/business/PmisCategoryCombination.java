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
public class PmisCategoryCombination implements Serializable
{
    private String categoryComboId_1;
    private String categoryComboName_1;

    public String getCategoryComboId_1() {
        return categoryComboId_1;
    }

    public void setCategoryComboId_1(String categoryComboId_1) {
        this.categoryComboId_1 = categoryComboId_1;
    }

    public String getCategoryComboName_1() {
        return categoryComboName_1;
    }

    public void setCategoryComboName_1(String categoryComboName_1) {
        this.categoryComboName_1 = categoryComboName_1;
    }

    
}
