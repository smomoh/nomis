/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.OvcTBHIVScreeningChecklist;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface OvcTBHIVScreeningChecklistDao 
{
    public void saveOvcTBHIVScreeningChecklist(OvcTBHIVScreeningChecklist tbhivChecklist) throws Exception;
    //public void saveOrUpdateOvcTBHIVScreeningChecklist(OvcTBHIVScreeningChecklist tbhivChecklist) throws Exception;
    public void updateOvcTBHIVScreeningChecklist(OvcTBHIVScreeningChecklist tbhivChecklist) throws Exception;
    public void markedForDelete(OvcTBHIVScreeningChecklist tbhivChecklist) throws Exception;
    public void deleteOvcTBHIVScreeningChecklist(OvcTBHIVScreeningChecklist tbhivChecklist) throws Exception;
    public List getAllOvcTBHIVScreeningChecklist() throws Exception;
    //public List getOvcTBHIVScreeningChecklist(String additionalCriteria) throws Exception;
    public List getOvcTBHIVScreeningChecklistPerOvc(String ovcId) throws Exception;
    public OvcTBHIVScreeningChecklist getOvcTBHIVScreeningChecklist(int id) throws Exception;
    public OvcTBHIVScreeningChecklist getOvcTBHIVScreeningChecklist(String ovcId,String dateOfAssessment) throws Exception;
    public int getAssessmentNumber(OvcTBHIVScreeningChecklist tbhivChecklist) throws Exception;
    public List getAssessmentsByOvcId(String ovcId) throws Exception;
    public int getNumberOfOvcTBHIVScreeningChecklist(String additionalCriteria) throws Exception;
    public List getListOfOvcScreenedForTBHIV(String additionalCriteria) throws Exception;
    public List getTBHIVScreeningRecords(String additionalCriteria) throws Exception;
}
