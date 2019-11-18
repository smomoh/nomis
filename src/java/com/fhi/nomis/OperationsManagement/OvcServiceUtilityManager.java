/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.nomis.OperationsManagement;

import com.fhi.kidmap.business.BirthRegistration;
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.business.OvcService;
import com.fhi.kidmap.business.OvcWithdrawal;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.nomis.nomisutils.DateManager;
import com.fhi.nomis.nomisutils.NomisConstant;
import com.fhi.nomis.nomisutils.OvcServiceManager;
import java.util.List;

/**
 *
 * @author smomoh
 */
public class OvcServiceUtilityManager 
{
    public void setOvcBirthRegistrationStatus(OvcService service)
    {
        try
        {
                DaoUtil util=new DaoUtil();
                if(service !=null && service.getOvcId() !=null)
                {
                    String birthRegistrationServiceName=OvcServiceManager.getBirthRegistrationService().getServiceName();
                    String birthRegistrationServiceCode=OvcServiceManager.getBirthRegistrationService().getServiceCode();
                    if(service.getServiceAccessed5() !=null && (service.getServiceAccessed5().indexOf(birthRegistrationServiceName) !=-1 || service.getServiceAccessed5().indexOf(birthRegistrationServiceCode) !=-1))
                    {
                        BirthRegistration br=util.getBirthRegistrationDaoInstance().getActiveBirthRegistration(service.getOvcId());
                        if(br==null)
                        {
                            br=new BirthRegistration();
                            br.setClientId(service.getOvcId());
                            br.setBirthRegistrationStatus("Yes");
                            br.setDateOfRegistration(service.getServicedate());
                            br.setPointOfUpdate(NomisConstant.SERVICE_POINTOFUPDATE);
                            br.setRecordStatus(NomisConstant.ACTIVE);
                            br.setClientType(NomisConstant.OVC_TYPE);
                            br.setDateModified(DateManager.getCurrentDate());
                            util.getBirthRegistrationDaoInstance().saveBirthRegistration(br);
                        }
                        else
                        {
                            if(br.getBirthRegistrationStatus().equalsIgnoreCase("No"))
                            {
                                br.setBirthRegistrationStatus("Yes");
                                br.setDateOfRegistration(service.getServicedate());
                                br.setPointOfUpdate(NomisConstant.SERVICE_POINTOFUPDATE);
                                br.setRecordStatus(NomisConstant.ACTIVE);
                                br.setClientType(NomisConstant.OVC_TYPE);
                                br.setDateModified(DateManager.getCurrentDate());
                                util.getBirthRegistrationDaoInstance().updateBirthRegistration(br);
                            }
                        }
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public String getLastServiceDate(String ovcId)
    {
        String lastServiceDate="1900-01-01";
        try
        {
            DaoUtil util=new DaoUtil();
            List serviceDateList=util.getOvcServiceDaoInstance().getDistinctServiceDatesPerOvc(ovcId);
            if(serviceDateList !=null && !serviceDateList.isEmpty())
            {
                lastServiceDate=(String)serviceDateList.get(0);               
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return lastServiceDate;
    }
    public int getLastServiceInterval(String lastServiceDate)
    {
        int monthDiffrence=0;
        try
        {
            String currentDate=DateManager.getCurrentDate();
            if(lastServiceDate !=null && lastServiceDate.indexOf("-") !=-1)
            {
                String[] lastServiceDateArray=lastServiceDate.split("-");
                String[] currentDateArray=currentDate.split("-");
                int basemth=Integer.parseInt(lastServiceDateArray[1]);
                int baseyear=Integer.parseInt(lastServiceDateArray[0]);
                int currentMonth=Integer.parseInt(currentDateArray[1]);
                int currentYear=Integer.parseInt(currentDateArray[0]);
                monthDiffrence=DateManager.getDateDifference(basemth, baseyear, currentMonth, currentYear);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return monthDiffrence;
    }
    public int getLastServicePeriod(String ovcId)
    {
        int monthDiffrence=0;
        try
        {
            String lastServiceDate=getLastServiceDate(ovcId);
            monthDiffrence=getLastServiceInterval(lastServiceDate);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return monthDiffrence;
    }
    public String getServiceStatusColor(int value)
    {
        String serviceColor="green";
        if(value <0)
        {
            serviceColor="black";
        }
        else if(value==3)
        {
            serviceColor="yellow";
        }
        else if(value>3)
        {
            serviceColor="red";
        }
        return serviceColor;
    }
    public int getOvcEnrollmentStatusCode(int monthDifference)
    {
        int enrollmentStatusCode=0;
        
        if(monthDifference>3)
        {
            enrollmentStatusCode=NomisConstant.INACTIVECODE;
        }
        return enrollmentStatusCode;
    }
   /* public String getOvcEnrollmentStatus(int value)
    {
        String status=NomisConstant.ACTIVE;
        if(value>3)
        {
            status=NomisConstant.INACTIVE;
        }
        return status;
    }*/
    public void updateOvcWithLastServiceDate(String ovcId,String serviceDate)
    {
        try
        {
            DaoUtil util=new DaoUtil();
            Ovc ovc=getOvcWithLastServiceDate(ovcId, serviceDate);
            if(ovc !=null)
            {
                util.getOvcDaoInstance().updateOvc(ovc, false, false);
                withdrawInactiveClient(ovc);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }  
    }
    public void updateOvcWithLastServiceDate(Ovc ovc)
    {
        try
        {
            DaoUtil util=new DaoUtil();
            ovc=getOvcWithLastServiceDate(ovc);
            if(ovc !=null)
            {
                util.getOvcDaoInstance().updateOvc(ovc, true, true);
                withdrawInactiveClient(ovc);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }  
    }
    public Ovc getOvcWithLastServiceDate(Ovc ovc)
    {
       if(ovc !=null)
       {
           ovc.setLastServiceDate(getLastServiceDate(ovc.getOvcId()));
           int monthDifference=getLastServicePeriod(ovc.getOvcId());
           ovc.setServiceStatusColor(getServiceStatusColor(monthDifference));
           ovc.setCurrentEnrollmentStatusCode(getOvcEnrollmentStatusCode(monthDifference));
           //ovc.setReasonForWithdrawal(getOvcEnrollmentStatus(monthDifference));
           
       }
       return ovc;
    }
    public Ovc getOvcWithLastServiceDate(String ovcId,String serviceDate)
    {
        Ovc ovc=null;
        try
        {
            DaoUtil util=new DaoUtil();
            ovc=util.getOvcDaoInstance().getOvc(ovcId);
           if(ovc !=null)
           {
               if(ovc.getLastServiceDate()==null || ovc.getLastServiceDate().equalsIgnoreCase("1900-01-01"))
               ovc.setLastServiceDate(serviceDate);
               else if(DateManager.compareDates(ovc.getLastServiceDate(), serviceDate))
               ovc.setLastServiceDate(serviceDate);
               int monthDifference=getLastServiceInterval(ovc.getLastServiceDate());
               ovc.setServiceStatusColor(getServiceStatusColor(monthDifference));
               ovc.setCurrentEnrollmentStatusCode(getOvcEnrollmentStatusCode(monthDifference));
           }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
       return ovc;
    }
    public void withdrawInactiveClient(Ovc ovc)
    {
        try
        {
            DaoUtil util=new DaoUtil();
            if(ovc.getReasonForWithdrawal() !=null && ovc.getCurrentEnrollmentStatusCode()==NomisConstant.INACTIVECODE)
            util.getOvcWithdrawalDaoInstance().withdrawClient(ovc.getOvcId(), DateManager.getCurrentDate(),NomisConstant.INACTIVE, NomisConstant.OVC_TYPE, 0);
            else 
            {
                OvcWithdrawal withdrawal=util.getOvcWithdrawalDaoInstance().getOvcWithdrawal(ovc.getOvcId());
                if(withdrawal !=null && withdrawal.getReasonWithdrawal() !=null && withdrawal.getReasonWithdrawal().equalsIgnoreCase(NomisConstant.INACTIVE))
                util.getOvcWithdrawalDaoInstance().deleteOvcWithdrawal(withdrawal);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
