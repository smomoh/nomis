/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.CboSetup;
import java.util.List;

/**
 *
 * @author COMPAQ USER
 */
public interface CboSetupDao
{
    public void saveCboSetupInfo(CboSetup cboSetup) throws Exception;
    public void updateCboSetupInfo(CboSetup cboSetup) throws Exception;
    public void deleteCboSetupInfo(CboSetup cboSetup) throws Exception;
    public List getStateListFromCboSetup() throws Exception;
    public List getLgaListFromCboSetup(String stateCode) throws Exception;
    public CboSetup getCboSetup(String userLoginName) throws Exception;
    public List getListOfSiteSetup(String stateCode, String lgaCode) throws Exception;
    public List getCboSetupInfo() throws Exception;
}
