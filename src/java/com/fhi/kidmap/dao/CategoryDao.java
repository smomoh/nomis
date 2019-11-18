/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.Category;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface CategoryDao
{
    public void saveCategory(Category cat) throws Exception;
    public void updateCategory(Category cat) throws Exception;
    public void deleteCategory(Category cat) throws Exception;
    public  Category getCategory(String categoryId) throws Exception;
    public  List getCategories() throws Exception;
    public Category getCategoryName(String categoryName) throws Exception;
}
