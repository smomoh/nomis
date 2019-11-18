/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.Lgas;
import java.util.List;

/**
 *
 * @author HP USER
 */
public interface LgaDao {

    public List getLgaByLgaCode(String lgaCode) throws Exception;
    public Lgas getLgaByCode(String lgaCode) throws Exception;
    //public List getLgaFromEnrollment(String stateName) throws Exception;
    public List getAllLgas() throws Exception;
    public List getLgas() throws Exception;
    public Lgas getLga(String name) throws Exception;
    public List getLgaList(String stateId) throws Exception;
    public void saveLga(Lgas lga) throws Exception;
    public void updateLga(Lgas lga) throws Exception;
    public void deleteLga(Lgas lga) throws Exception;
    public Lgas getLgaByStateAndLgaCode(String stateCode,String lgaCode) throws Exception;
}
