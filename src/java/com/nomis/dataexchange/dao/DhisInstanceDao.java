/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomis.dataexchange.dao;

import com.nomis.dataexchange.business.DhisInstance;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface DhisInstanceDao 
{
    public void saveDhisInstance(DhisInstance dhisInstance) throws Exception;
    public void updateDhisInstance(DhisInstance dhisInstance) throws Exception;
    public void deleteDhisInstance(DhisInstance dhisInstance) throws Exception;
    public DhisInstance getDhisInstanceById(String id) throws Exception;
    public List getAllDhisInstances() throws Exception;
    public DhisInstance[] getDhisInstanceComboItems();
    public DhisInstance getDhisInstanceByInstanceName(String instanceName) throws Exception;
}
