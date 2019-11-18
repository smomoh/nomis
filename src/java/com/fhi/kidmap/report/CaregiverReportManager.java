/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report;

import com.fhi.kidmap.business.Caregiver;
import com.fhi.kidmap.dao.DaoUtil;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author smomoh
 */
public class CaregiverReportManager 
{
    DaoUtil util=new DaoUtil();
    public Caregiver getCaregiverBioData(String caregiverId)
   {
       Caregiver cgiver=null;
       try
       {
            cgiver=util.getCaregiverDaoInstance().getCaregiverByCaregiverId(caregiverId);
            if(cgiver ==null)
            {
                cgiver=new Caregiver();
            }
       }
       catch(Exception ex)
       {
           ex.printStackTrace();
       }
       return cgiver;
   }
   public List getServicesProvidedToCaregiver(String caregiverId)
   {
       List mainList=new ArrayList();
       try
       {
            List list=util.getHouseholdServiceDaoInstance().getAllServicesPerCaregiverOrderdByServiceDate(caregiverId,"asc");
            if(list !=null && !list.isEmpty())
            {
                mainList.addAll(list);
            }
       }
       catch(Exception ex)
       {
           ex.printStackTrace();
       }
       return mainList;
   }
   public List getAllCaregiversInHousehold(String hhUniqueId)
   {
       List mainList=new ArrayList();
       try
       {
            List list=util.getCaregiverDaoInstance().getListOfCaregiversFromSameHousehold(hhUniqueId);
            if(list !=null && !list.isEmpty())
            {
                mainList.addAll(list);
            }
       }
       catch(Exception ex)
       {
           ex.printStackTrace();
       }
       return mainList;
   }
}
