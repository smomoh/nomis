/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.nomis.OperationsManagement;

import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.HouseholdEnrollmentDao;
import com.fhi.kidmap.dao.HouseholdEnrollmentDaoImpl;
import java.util.List;

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
    public static boolean householdHasAdolescentsAndYoungWomen(String hhUniqueId)
    {
        boolean householdHasAdolescentsAndYoungWomen=false;
        try
        {
           DaoUtil util=new DaoUtil(); 
           int count=util.getOvcDaoInstance().getNumberOfOvcAge5To20InHousehold(hhUniqueId);
           if(count>0)
           {
               householdHasAdolescentsAndYoungWomen=true;
           }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return householdHasAdolescentsAndYoungWomen;
    }
}
