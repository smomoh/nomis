/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.nomis.nomisutils.OptionsManager;

/**
 *
 * @author smomoh
 */
public interface OptionsManagerDao 
{
    public void saveOptionsManager(OptionsManager opm) throws Exception;
    public void updateOptionsManager(OptionsManager opm) throws Exception;
    public void deleteOptionsManager(OptionsManager opm) throws Exception;
    public OptionsManager getOptionsManager(String optionId) throws Exception;
}
