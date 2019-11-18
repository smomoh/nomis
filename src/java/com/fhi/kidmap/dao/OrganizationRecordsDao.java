/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.OrganizationRecords;
import com.fhi.kidmap.business.ServiceList;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface OrganizationRecordsDao {
    public void saveOrgRecords(OrganizationRecords orgRecords) throws Exception;
    public void updateOrgRecords(OrganizationRecords orgRecords) throws Exception;
    public void deleteOrgRecords(OrganizationRecords orgRecords) throws Exception;
    public List getServiceList() throws Exception;
    public List getOrganizationList() throws Exception;
    public List getOrganizationList(String lgaCode) throws Exception;
    public List getListOfOrganizationsAssignedToLga(String lgaCode) throws Exception;
    public List getStateOrganizationList(String stateCode) throws Exception;
    public OrganizationRecords getOrganizationRecord(String orgCode) throws Exception;
    public List getOrganizationRecordsLgaList(String stateCode) throws Exception;
    public String getTypeOfOrganization(String orgCode) throws Exception;
    public String getOrganizationName(String orgCode) throws Exception;
    public String getOrganizationCode(String orgName) throws Exception;
    public List getOrganizationRecordsStateList() throws Exception;
    public int getNumberOfOrganizations(String orgCriteria) throws Exception;
    //public List getOrganizationListForExport(String stateCode,String lgaCode,String orgCode) throws Exception;
    public List getOrganizationListForExport(String orgCriteria) throws Exception;
    public List getOrgUnitsNotInDhisLinkTable() throws Exception;
    public void saveServiceList(ServiceList serviceList) throws Exception;
}
