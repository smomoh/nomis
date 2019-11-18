/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomis.dataexchange.dao;

import com.nomis.dataexchange.business.DataExchangeOrganizationUnit;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface DxOrganizationUnitDao 
{
    public void saveOrganizationUnit(DataExchangeOrganizationUnit ou) throws Exception;
    public void updateOrganizationUnit(DataExchangeOrganizationUnit ou) throws Exception;
    public void deleteOrganizationUnit(DataExchangeOrganizationUnit ou) throws Exception;
    public DataExchangeOrganizationUnit getOrganizationUnit(String ourunitId) throws Exception;
    public List getOrganizationUnits(String dhisInstance) throws Exception;
    public List getOrganizationUnitsByParentId(String parentId) throws Exception;
    public DataExchangeOrganizationUnit getOrganizationUnitByName(String orgunitName) throws Exception;
    public DataExchangeOrganizationUnit[] getOrganizationUnitsNotMatched(String dhisInstance) throws Exception;
    public String[] getOrganizationUnitLevels(String dhisInstance) throws Exception;
    public List getOrganizationUnits(String dhisInstance, int level) throws Exception;
}
