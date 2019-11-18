/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.EligibilityCriteria;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface EligibilityCriteriaDao {

    public void saveEligibilityCriteria(EligibilityCriteria eligibilityCriteria) throws Exception;
    public void updateEligibilityCriteria(EligibilityCriteria eligibilityCriteria) throws Exception;
    public void deleteEligibilityCriteria(EligibilityCriteria eligibilityCriteria) throws Exception;
    public List getEligibilityCriteria() throws Exception;
    public EligibilityCriteria getEligibilityCriteria(int id) throws Exception;
    public EligibilityCriteria getEligibilityCriteria(String name) throws Exception;
    public void markedForDelete(EligibilityCriteria eligibilityCriteria) throws Exception;
}
