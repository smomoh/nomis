/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.DhisDataExport;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface DhisDataExportDao
{
    public List ExportToDhisFromDhisDataExport(String orgUnitId,String instance) throws Exception;
    public DhisDataExport getDhisDataExportByDataElementNameAndCategoryComboOptionName(String dataElementName,String categoryComboOptionName) throws Exception;
    public List getDistinctDataElementAndCategoryComboOption() throws Exception;
    public List getDistinctOrgUnitIdsFromDhisDataExport(String instance) throws Exception;
    public List getDhisDataExportList(String orgUnitName,String instance,String dataElementName,String startDate) throws Exception;
    public DhisDataExport getDhisDataExport(String orgUnitName,String instance,String dataElementName,String startDate) throws Exception;
    public List getDistinctRecordParametersFromDhisDataExport(String orgUnitName, String instance) throws Exception;
    public List getDhisDataExportByOrgUnitId(String orgUnitId,String instance) throws Exception;
    public List getDhisDataExports() throws Exception;
    public List getDistinctCategoryComboOption() throws Exception;
    public List getDistinctDataElementNames() throws Exception;
    public List getNonDuplicateRecordsFromDhisDataExport(String orgUnitId,String instance) throws Exception;
    public List getNonDuplicateRecordsFromDhisDataExportForOVC(String orgUnitId,String instance) throws Exception;
    public List changeOrgUnitNames(String nameParts,String instance) throws Exception;
    public List getDistinctOrgUnitNamesFromDhisDataExport(String instance) throws Exception;
    public List getAllDistinctOrgUnitNamesFromDhisDataExport(String instance) throws Exception;
    public List getDhisDataExportByOrgUnitName(String orgUnitName,String instance) throws Exception;
    public List getDistinctDataElementNamesFromDhisDataExport(String instance) throws Exception;
    public List getDhisDataExportByDataElementName(String dataElementName,String instance) throws Exception;
    public List getDhisDataExportDataByCategoryComboOptionName(String cocName,String instance) throws Exception;
    public void saveDhisDataExport(DhisDataExport dde) throws Exception;
    public void updateDhisDataExport(DhisDataExport dde) throws Exception;
    public void deleteDhisDataExport(DhisDataExport dde) throws Exception;
    public List getDhisDataExportList(String orgUnitName,String dataElementName,String startDate) throws Exception;
}
