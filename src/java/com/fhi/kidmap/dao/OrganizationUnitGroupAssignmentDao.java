/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.OrganizationUnitGroupAssignment;
import java.util.List;

/**
 *
 * @author Siaka
 */
public interface OrganizationUnitGroupAssignmentDao 
{
    public void saveOrganizationUnitGroupAssignment(String[] orgUnitIdArray,String orgUnitGroupId,String parentOrgUnitGroupId) throws Exception;
    public void saveOrganizationUnitGroupAssignment(OrganizationUnitGroupAssignment oug) throws Exception;
    public void updateOrganizationUnitGroupAssignment(OrganizationUnitGroupAssignment oug) throws Exception;
    public void deleteOrganizationUnitGroupAssignment(OrganizationUnitGroupAssignment oug) throws Exception;
    public void deleteOrganizationUnitGroupAssignmentByGroupId(String orgUnitGroupId) throws Exception;
    public List getOrganizationUnitGroupAssignmentByGroupId(String orgUnitGroupId) throws Exception;
    public OrganizationUnitGroupAssignment getOrganizationUnitGroupAssignment(String orgUnitId,String orgUnitGroupId) throws Exception;
    public List getOrganizationUnitGroupAssignmentByOrgUnitId(String orgUnitId) throws Exception;
    public OrganizationUnitGroupAssignment getOrganizationUnitGroupAssignmentByRecordId(int recordId) throws Exception;
    public List getAllOrganizationUnitGroupAssignments() throws Exception;
    public List filterOrganizationUnits(List orgList,String orgUnitGroupId) throws Exception;
    public List getDistinctOrgUnitGroupIdByParentId(String parentOrgUnitGroupId) throws Exception;
    public List getDistinctParentIdFromOrganizationUnitGroupAssignments() throws Exception;
    public List getDistinctOrgUnitGroupIdFromOrganizationUnitGroupAssignments() throws Exception;
    public OrganizationUnitGroupAssignment getOrganizationUnitGroupAssignmentByOrgUnitIdAndParentOrgUnitGroupId(String orgUnitId,String parentId) throws Exception;
    public List getDistinctOrgUnitGroupIds() throws Exception;
    public List getListOfOrgUnitGrpAssignmentByOrgUnitIdAndParentId(String orgUnitId,String parentId) throws Exception;
    public List filterCommunityList(List communityList,String orgUnitGroupId) throws Exception;
}
