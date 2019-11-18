/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.CustomDataElement;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface CustomDataElementsDao
{
    public void saveCustomDataElement(CustomDataElement cde) throws Exception;
    public void updateCustomDataElement(CustomDataElement cde) throws Exception;
    public void deleteCustomDataElement(CustomDataElement cde) throws Exception;
    public CustomDataElement getCustomDataElement(String customDataElementId) throws Exception;
    public List getCustomDataElementList() throws Exception;
    public CustomDataElement getCustomDataElemenByName(String customDataElementName) throws Exception;
}
