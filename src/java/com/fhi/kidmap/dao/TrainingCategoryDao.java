/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.TrainingCategory;
import java.util.List;

/**
 *
 * @author Siaka
 */
public interface TrainingCategoryDao 
{
    public void saveTrainingCategory(TrainingCategory trnCat) throws Exception;
    public void updateTrainingCategory(TrainingCategory trnCat) throws Exception;
    public void markedForDelete(TrainingCategory trnCat) throws Exception;
    public void deleteTrainingCategory(TrainingCategory trnCat) throws Exception;
    public  TrainingCategory getTrainingCategory(String categoryId) throws Exception;
    public  List getAllTrainingCategories() throws Exception;
}
