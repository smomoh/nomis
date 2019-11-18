/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.CaregiverTBHIVScreeningChecklist;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface CaregiverTBHIVScreeningChecklistDao 
{
    public void saveCaregiverTBHIVScreeningChecklist(CaregiverTBHIVScreeningChecklist tbhivChecklist) throws Exception;
    //public void saveOrUpdateCaregiverTBHIVScreeningChecklist(CaregiverTBHIVScreeningChecklist tbhivChecklist) throws Exception;
    public void updateCaregiverTBHIVScreeningChecklist(CaregiverTBHIVScreeningChecklist tbhivChecklist) throws Exception;
    public void markedForDelete(CaregiverTBHIVScreeningChecklist tbhivChecklist) throws Exception;
    public void deleteCaregiverTBHIVScreeningChecklist(CaregiverTBHIVScreeningChecklist tbhivChecklist) throws Exception;
    public List getAllCaregiverTBHIVScreeningChecklist() throws Exception;
    public List getCaregiverTBHIVScreeningChecklistPerCaregiver(String caregiverId) throws Exception;
    public CaregiverTBHIVScreeningChecklist getCaregiverTBHIVScreeningChecklist(int id) throws Exception;
    public CaregiverTBHIVScreeningChecklist getCaregiverTBHIVScreeningChecklist(String caregiverId,String dateOfAssessment) throws Exception;
    public int getAssessmentNumber(CaregiverTBHIVScreeningChecklist tbhivChecklist) throws Exception;
    public List getAssessmentsByCaregiverId(String caregiverId) throws Exception;
    public int getNumberOfCaregiverTBHIVScreeningChecklist(String additionalCriteria) throws Exception;
    public List getListOfCaregiversScreenedForTBHIV(String additionalCriteria) throws Exception;
    public List getTBHIVScreeningRecords(String additionalCriteria) throws Exception;
}
