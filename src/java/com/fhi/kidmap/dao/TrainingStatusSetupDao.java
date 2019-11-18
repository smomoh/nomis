/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.TrainingStatusSetup;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface TrainingStatusSetupDao
{
    public void saveTrainingStatusSetup(TrainingStatusSetup tss) throws Exception;
    public void updateTrainingStatusSetup(TrainingStatusSetup tss) throws Exception;
    public void deleteTrainingStatusSetup(TrainingStatusSetup tss) throws Exception;
    public List getAllTrainingStatusSetups() throws Exception;
    public TrainingStatusSetup getTrainingStatusSetup(String statusId) throws Exception;
    public TrainingStatusSetup getTrainingStatusSetupByStatusName(String trainingStatusName) throws Exception;
}
