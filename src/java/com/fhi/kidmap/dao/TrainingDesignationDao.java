/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.TrainingDesignation;
import java.util.List;

/**
 *
 * @author Siaka
 */
public interface TrainingDesignationDao 
{
    public void saveTrainingDesignation(TrainingDesignation td) throws Exception;
    public void updateTrainingDesignation(TrainingDesignation td) throws Exception;
    public void markedForDelete(TrainingDesignation td) throws Exception;
    public void deleteTrainingDesignation(TrainingDesignation td) throws Exception;
    public  TrainingDesignation getTrainingDesignation(String designationId) throws Exception;
    public  List getAllTrainingDesignations() throws Exception;
    public  List getTrainingDesignations(String categoryId) throws Exception;
}
