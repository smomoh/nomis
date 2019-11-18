/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.nomis.business.FingerPrint;

/**
 *
 * @author smomoh
 */
public interface FingerPrintDao
{
    public void saveFingerPrint(FingerPrint fingerPrint) throws Exception;
    public void updateFingerPrint(FingerPrint fingerPrint) throws Exception;
    public void deleteFingerPrint(FingerPrint fingerPrint) throws Exception;
    public FingerPrint getFingerPrint(String id) throws Exception;
    public FingerPrint getFingerPrint(long uid) throws Exception;
}
