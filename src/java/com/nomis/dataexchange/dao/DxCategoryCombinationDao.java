/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomis.dataexchange.dao;

import com.nomis.dataexchange.business.DxCategoryCombination;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface DxCategoryCombinationDao 
{
    public void deleteCategoryCombination(DxCategoryCombination cc) throws Exception;
    public void saveOrUpdateCategoryCombination(DxCategoryCombination cc) throws Exception;
    public DxCategoryCombination getCategoryCombination(String categoryCombinationId) throws Exception;
    public DxCategoryCombination getCategoryCombinationByName(String categoryCombinationName) throws Exception;
    public DxCategoryCombination getCategoryCombinationByNameAndDhisInstance(String categoryCombinationName, String dhisInstance) throws Exception;
    public List getAllCategoryCombinations() throws Exception;
    public List getCategoryCombinationByDhisInstance(String dhisInstance) throws Exception;
}
