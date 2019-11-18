/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.TrainingInformationSetup;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface TrainingInformationSetupDao
{
    public void saveTrainingInformationSetup(TrainingInformationSetup tis) throws Exception;
    public void updateTrainingInformationSetup(TrainingInformationSetup tis) throws Exception;
    public void markedForDelete(TrainingInformationSetup tis) throws Exception;
    public void deleteTrainingInformationSetup(TrainingInformationSetup tis) throws Exception;
    public List getAllTrainingInformationSetups() throws Exception;
    public TrainingInformationSetup getTrainingInformationSetup(String recordId) throws Exception;
    public TrainingInformationSetup getTrainingInformationSetupByStatusName(String trainingName) throws Exception;
}
