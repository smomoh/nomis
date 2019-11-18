/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.UmbrellaOrganization;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface UmbrellaOrganizationDao
{
    public void saveUmbrellaOrganization(UmbrellaOrganization uo) throws Exception;
    public void updateUmbrellaOrganization(UmbrellaOrganization uo) throws Exception;
    public void deleteUmbrellaOrganization(UmbrellaOrganization uo) throws Exception;
    public UmbrellaOrganization getUmbrellaOrganization(String umbrellaOrganizationCode) throws Exception;
    public UmbrellaOrganization getUmbrellaOrganization(String umbrellaOrganizationCode,String subOrganizationCode) throws Exception;
    public UmbrellaOrganization getUmbrellaOrganizationByUmbrellaOrganizationCodeOrSubOrganization(String orgCode) throws Exception;
    public UmbrellaOrganization getUmbrellaOrganizationBySubOrganizationCode(String subOrganizationCode) throws Exception;
    public List getListOfUmbrellaOrganization() throws Exception;
    public UmbrellaOrganization getSubOrganizationByRecordId(String recordId) throws Exception;
    public List filterOrganizationRecords(List allOrgList) throws Exception;
    public List filterOrganizationRecordsForSubOrganizationList(String umbrellaOrganizationCode,List allOrgList) throws Exception;
}
