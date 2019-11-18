/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.CategoryCombination;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface CategoryCombinationDao
{
    public void saveCategoryCombination(CategoryCombination cc) throws Exception;
    public void updateCategoryCombination(CategoryCombination cc) throws Exception;
    public void deleteCategoryCombination(CategoryCombination cc) throws Exception;
    public List getCategoryCombinationList() throws Exception;
    public CategoryCombination getCategoryCombination(String categoryComboId) throws Exception;
    public CategoryCombination getCategoryCombinationByName(String categoryComboName) throws Exception;
}
