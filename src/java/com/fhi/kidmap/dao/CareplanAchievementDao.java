/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.CareplanAchievement;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface CareplanAchievementDao 
{
    public void saveCareplanAchievement(CareplanAchievement gcl) throws Exception;
    public void updateCareplanAchievement(CareplanAchievement gcl) throws Exception;
    public void markedForDelete(CareplanAchievement cpa) throws Exception;
    public void deleteCareplanAchievement(CareplanAchievement gcl) throws Exception;
    public List getAllCareplanAchievements() throws Exception;
    public CareplanAchievement getCareplanAchievement(int id) throws Exception;
    public List getCareplanAchievementsPerClient(String clientId) throws Exception;
    public CareplanAchievement getCareplanAchievement(String clientId,String dateOfAssessment) throws Exception;
    public List getCareplanAchievements(String additionalQueryCriteria) throws Exception;
    public int getGraduationScore(CareplanAchievement gcl) throws Exception;
    public void saveCareplanAchievementForImport(CareplanAchievement gcl) throws Exception;
    public void updateCareplanAchievementForImport(CareplanAchievement gcl) throws Exception;
    public List getCareplanAchievementsNotGraduated(String additionalQueryCriteria) throws Exception;
}
