/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.nomis.OperationsManagement;

import com.fhi.kidmap.dao.HouseholdEnrollmentDao;
import com.fhi.kidmap.dao.HouseholdEnrollmentDaoImpl;

/**
 *
 * @author smomoh
 */
public class HouseholdEnrollmentManager 
{
    public static HouseholdEnrollmentDao getHouseholdEnrollmentDaoInstance()
    {
        return new HouseholdEnrollmentDaoImpl();
    }
}
