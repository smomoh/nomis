/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.Training;
import java.util.List;

/**
 *
 * @author Siaka
 */
public interface TrainingDao 
{
    public void saveTraining(Training trn) throws Exception;
    public void updateTraining(Training trn) throws Exception;
    public void markedForDelete(Training trn) throws Exception;
    public void deleteTraining(Training trn) throws Exception;
    public Training getTraining(String recordId) throws Exception;
    public List getTrainings() throws Exception;
    public List getTrainingReport(String[] params) throws Exception;
    public List getAllTrainingReports(String[] params) throws Exception;
}
