/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;


import com.fhi.kidmap.business.Ward;
import com.fhi.kidmap.business.Wards;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HP USER
 */
public interface WardDao {

    public List getWardByWardCode(String id) throws Exception;
    public List getWardByCboId(String id) throws Exception;
    public List getWardByLgaCodeAndCboId(String lgaCode,String cboId) throws Exception;
    public List getWardsFromEnrollment(String stateName,String cboName) throws Exception;
    public List getAllWards() throws Exception;
    public List getWards() throws Exception;
    public Ward getWard(String name) throws Exception;
    public void saveWard(Wards ward) throws Exception;
    public void updateWard(Wards ward) throws Exception;
    public void deleteWard(Wards ward) throws Exception;
    public void setId(String Id) throws Exception;
    public Wards getWards(String wardCode) throws Exception;
    public List getWardListByOrgUnits(String orgCriteria) throws Exception;
    public String getId();
    public List getWardsByStateAndLgaCodes(String stateCode,String lgaCode) throws Exception;
    public List getWardsByStateCode(String stateCode) throws Exception;
    public List getWardsWithSpacesInCode() throws Exception;
}
