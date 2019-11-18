/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.OrganizationalCapacityAssessment;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface OrganizationalCapacityAssessmentDao
{
    public void saveOrganizationalCapacityAssessment(OrganizationalCapacityAssessment oca) throws Exception;
    public void updateOrganizationalCapacityAssessment(OrganizationalCapacityAssessment oca) throws Exception;
    public void markedForDelete(OrganizationalCapacityAssessment oca) throws Exception;
    public void deleteOrganizationalCapacityAssessment(OrganizationalCapacityAssessment oca) throws Exception;
    public OrganizationalCapacityAssessment getOrganizationalCapacityAssessment(String organizationCode, String dateOfAssessment) throws Exception;
    public List getOrganizationalCapacityAssessmentRecords(String organizationCode) throws Exception;
    public int getNoOfCapacityAssessment(String organizationCode) throws Exception;
    public String getDateOfFirstCapacityAssessment(String organizationCode) throws Exception;
    public int getTotalScore(OrganizationalCapacityAssessment oCapAssessment) throws Exception;
    public List getOrganizationalCapacityAssessment() throws Exception;
}
