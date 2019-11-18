/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.TrainingParticipant;
import java.util.List;

/**
 *
 * @author Siaka
 */
public interface TrainingParticipantDao 
{
    public void saveTrainingParticipant(TrainingParticipant tp) throws Exception;
    public void updateTrainingParticipant(TrainingParticipant tp) throws Exception;
    public void markedForDelete(TrainingParticipant tp) throws Exception;
    public void deleteTrainingParticipant(TrainingParticipant tp) throws Exception;
    public TrainingParticipant getTrainingParticipant(String participantId) throws Exception;
    public TrainingParticipant getTrainingParticipantByName(String organizationCode,String participantName) throws Exception;
    public void saveOrUpdateTrainingParticipant(TrainingParticipant tp) throws Exception;
    public TrainingParticipant getTrainingParticipantByTraineeId(String traineeId) throws Exception;
    public int getNoOfTraineesByState(String organizationCode) throws Exception;
    public String generateUniqueTrainingId(TrainingParticipant tp) throws Exception;
    public List getAllTrainingParticipants() throws Exception;
    public String generateUniqueId() throws Exception;
    public List getDistinctOrganizationRecordsFromTraining() throws Exception;
}
