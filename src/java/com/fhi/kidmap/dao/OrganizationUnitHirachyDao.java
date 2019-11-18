/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.OrganizationUnitHirachy;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface OrganizationUnitHirachyDao 
{
    public void saveOrganizationUnitHirachy(OrganizationUnitHirachy ouh) throws Exception;
    public void updateOrganizationUnitHirachy(OrganizationUnitHirachy ouh) throws Exception;
    public void deleteOrganizationUnitHirachy(OrganizationUnitHirachy ouh) throws Exception;
    public List getAllOrganizationUnitHirachy() throws Exception;
    public OrganizationUnitHirachy getOrganizationUnitHirachy(String hirachyId) throws Exception;
    public OrganizationUnitHirachy getOrganizationUnitHirachyByName(String hirachyName) throws Exception;
}
