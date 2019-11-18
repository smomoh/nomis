/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.GraduationCheckList;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface GraduationCheckListDao 
{
    public void saveGraduationCheckList(GraduationCheckList gcl) throws Exception;
    public void updateGraduationCheckList(GraduationCheckList gcl) throws Exception;
    public void markedForDelete(GraduationCheckList gcl) throws Exception;
    public void deleteGraduationCheckList(GraduationCheckList gcl) throws Exception;
    public List getAllGraduationCheckLists() throws Exception;
    public GraduationCheckList getGraduationCheckList(int id) throws Exception;
    public GraduationCheckList getGraduationCheckList(String clientId) throws Exception;
    public List getGraduationCheckLists(String additionalQueryCriteria) throws Exception;
    public int getGclScore(GraduationCheckList gcl) throws Exception;
    public void saveGraduationCheckListForImport(GraduationCheckList gcl) throws Exception;
    public void updateGraduationCheckListForImport(GraduationCheckList gcl) throws Exception;
}
