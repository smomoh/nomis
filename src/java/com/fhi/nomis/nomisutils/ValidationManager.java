/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.nomis.nomisutils;

import com.fhi.kidmap.business.Caregiver;
import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.dao.DaoUtil;

/**
 *
 * @author smomoh
 */
public class ValidationManager 
{
  public static boolean compareDateWithCurrentDate(String mthDayYear)
  {
      return DateManager.compareDates(DateManager.processMthDayYearToMysqlFormat(mthDayYear), DateManager.getCurrentDate());
  }
  public static boolean enrollmentDateBeforeCurrentDate(String followupDate,String uniqueId,String beneficiaryType)
  {
      boolean datebefore=false;
      try
      {
          if(beneficiaryType !=null)
          {
            DaoUtil util=new DaoUtil();
            String enrollmentDate=null;
            if(beneficiaryType.equalsIgnoreCase(NomisConstant.OVC_TYPE))
            {
                Ovc ovc=util.getOvcDaoInstance().getOvc(uniqueId);
                if(ovc !=null)
                enrollmentDate=ovc.getDateEnrollment();
            }
            else if(beneficiaryType.equalsIgnoreCase(NomisConstant.Caregiver_TYPE))
            {
                Caregiver cgiver=util.getCaregiverDaoInstance().getCaregiverByCaregiverId(uniqueId);
                if(cgiver !=null)
                enrollmentDate=cgiver.getDateOfEnrollment();
            }
            else if(beneficiaryType.equalsIgnoreCase(NomisConstant.HOUSEHOLD_TYPE))
            {
                HouseholdEnrollment hhe=util.getHouseholdEnrollmentDaoInstance().getHouseholdEnrollment(uniqueId);
                if(hhe !=null)
                enrollmentDate=hhe.getDateOfAssessment();
            }
            //System.err.println("enrollmentDate is "+enrollmentDate+" and followupDate is "+followupDate);
            datebefore=DateManager.compareDates(enrollmentDate,DateManager.processMthDayYearToMysqlFormat(followupDate));
        }
      }
      catch(Exception ex)
      {
          ex.printStackTrace();
      }
      return datebefore;
  }
}
