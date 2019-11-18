/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.OrganizationUnit;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface OrganizationUnitDao 
{
    public void saveOrganizationalUnit(OrganizationUnit ou) throws Exception;
    public void updateOrganizationalUnit(OrganizationUnit ou) throws Exception;
    public void deleteOrganizationalUnit(OrganizationUnit ou) throws Exception;
    public OrganizationUnit getOrganizationUnit(String orgunitId) throws Exception;
    public OrganizationUnit getOrganizationUnitByName(String orgunitName) throws Exception;
    public OrganizationUnit getOrganizationUnitByShortCode(String orgunitShortCode) throws Exception;
    public List getAllOrganizationUnit() throws Exception;
    public List getOrganizationUnitByLevel(int level) throws Exception;
    
}
