/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.nomis.business.FingerPrintService;

/**
 *
 * @author smomoh
 */
public interface FingerPrintServiceDao
{
    public void saveFingerPrintService(FingerPrintService fingerPrintService) throws Exception;
    public void updateFingerPrintService(FingerPrintService fingerPrintService) throws Exception;
    public void deleteFingerPrintService(FingerPrintService fingerPrintService) throws Exception;
    public FingerPrintService getFingerPrintService(String machineId,String serviceDate) throws Exception;
    public FingerPrintService getFingerPrintService(long uid) throws Exception;
}
