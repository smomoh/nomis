/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.Cbo;

import com.fhi.kidmap.business.Cbos;
import java.util.List;

/**
 *
 * @author HP USER
 */
public interface CboDao
{
    public List getCboFromEnrollment(String stateName,String lgaName) throws Exception;
    public List getCbos(String cbo_code) throws Exception;
    //public List getCbos() throws Exception;
    public List getCboCode(String cboName) throws Exception;
    public List getAllCbos() throws Exception;
    public Cbo getCbo(String name) throws Exception;
    public void saveCbo(Cbos cbo) throws Exception;
    public void updateCbo(Cbos cbo) throws Exception;
    public void deleteCbo(Cbos cbo) throws Exception;
    public List getCboList(String lgaId) throws Exception;
    public List getCboByState(String state_code) throws Exception;

}
