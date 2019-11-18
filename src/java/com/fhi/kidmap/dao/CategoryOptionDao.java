/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.CategoryOption;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface CategoryOptionDao
{
    public void saveCategoryOption(CategoryOption co) throws Exception;
    public void updateCategoryOption(CategoryOption co) throws Exception;
    public void deleteCategoryOption(CategoryOption co) throws Exception;
    public CategoryOption getCategoryOption(String uniqueId) throws Exception;
    public List getCategoryOptions() throws Exception;
    public CategoryOption getCategoryOptionByName(String coptionName) throws Exception;
}
