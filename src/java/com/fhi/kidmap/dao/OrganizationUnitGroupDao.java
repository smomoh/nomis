/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.OrganizationUnitGroup;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface OrganizationUnitGroupDao
{
    public void saveOrganizationUnitGroup(OrganizationUnitGroup oug) throws Exception;
    public void updateOrganizationUnitGroup(OrganizationUnitGroup oug) throws Exception;
    public void deleteOrganizationUnitGroup(OrganizationUnitGroup oug) throws Exception;
    public OrganizationUnitGroup getOrganizationUnitGroup(String orgUnitGroupId) throws Exception;
    public OrganizationUnitGroup getOrganizationUnitGroupByName(String orgUnitGroupName) throws Exception;
    public List getAllOrganizationUnitGroups() throws Exception;
}
