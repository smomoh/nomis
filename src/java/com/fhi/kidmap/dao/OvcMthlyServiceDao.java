/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.OvcMonthlyService;

/**
 *
 * @author smomoh
 */
public interface OvcMthlyServiceDao
{
    public void saveOvcService(OvcMonthlyService ovcService) throws Exception;
    public void updateOvcService(OvcMonthlyService ovcService) throws Exception;
    public void deleteOvcService(OvcMonthlyService ovcService) throws Exception;
}
