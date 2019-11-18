/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomis.dataexchange.dao;

import com.nomis.dataexchange.business.DxDataElement;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface DxDataElementDao 
{
    public void deleteDataElement(DxDataElement de) throws Exception;
    public void saveOrUpdateDataElement(DxDataElement de) throws Exception;
    public DxDataElement getDataElement(String dataElementId) throws Exception;
    public DxDataElement getDataElementByName(String dataElementName) throws Exception;
    public DxDataElement getDataElementByNameAndDhisInstance(String dataElementName, String dhisInstance) throws Exception;
    public List getAllDataElements() throws Exception;
    public List getDataElementByDhisInstance(String dhisInstance) throws Exception;
}
