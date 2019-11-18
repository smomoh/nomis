/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.business;

import com.fhi.kidmap.dao.HivStatusUpdateDao;
import com.fhi.kidmap.dao.HivStatusUpdateDaoImpl;
import com.fhi.nomis.nomisutils.NomisConstant;
import java.io.Serializable;

/**
 *
 * @author smomoh
 */
public class HivStatus implements Serializable
{
    private String hivStatusCode;
    private String hivStatusName;
    private HivStatusUpdate activeHivStatus;

    public String getHivStatusCode() {
        return hivStatusCode;
    }

    public void setHivStatusCode(String hivStatusCode) {
        this.hivStatusCode = hivStatusCode;
    }

    public String getHivStatusName() {
        return hivStatusName;
    }

    public void setHivStatusName(String hivStatusName) {
        this.hivStatusName = hivStatusName;
    }

    public HivStatusUpdate getActiveHivStatus(String clientId) 
    {
        HivStatusUpdateDao hsudao=new HivStatusUpdateDaoImpl();
        HivStatusUpdate hsu=null;
        try
        {
            hsu=hsudao.getActiveHivStatusUpdatesByClientId(clientId);
            if(hsu !=null)
            activeHivStatus=hsu;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return hsu;
    }
    public void setActiveHivStatus(HivStatusUpdate activeHivStatus) 
    {
        this.activeHivStatus = activeHivStatus;
    }
    public HivStatusUpdate getNewHivStatus(String clientId,String enrollmentDate,String pointOfService)
    {
        HivStatusUpdate newHivStatus=new HivStatusUpdate();
        newHivStatus.setClientEnrolledInCare("No");
        newHivStatus.setEnrolledOnART("No");
        newHivStatus.setPointOfUpdate(pointOfService);
        newHivStatus.setDateOfCurrentStatus(enrollmentDate);
        newHivStatus.setHivStatus(NomisConstant.HIV_UNKNOWN);    
        return newHivStatus;
    }
    public HivStatusUpdate getDefaultHivStatus(String clientId,String enrollmentDate,String pointOfService)
    {
        HivStatusUpdate defaultHivStatus=new HivStatusUpdate();
        defaultHivStatus=getActiveHivStatus(clientId);
        if(defaultHivStatus==null)
        defaultHivStatus=getNewHivStatus(clientId, enrollmentDate, pointOfService);
        return defaultHivStatus;
    }
    public HivStatusUpdate getCurrentHivStatus(String clientId,String dateOfStatus) 
    {
        HivStatusUpdateDao hsudao=new HivStatusUpdateDaoImpl();
        HivStatusUpdate hsu=null;
        try
        {
            hsu=hsudao.getHivStatusUpdatesByClientIdAndDateOfStatus(clientId, dateOfStatus);
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return hsu;
    }
    public static int getStatusWeight(String status)
    {
        int statusCode=0;
        if(status !=null)
        {
            if(status.equalsIgnoreCase(NomisConstant.HIV_UNKNOWN) || status.equalsIgnoreCase(NomisConstant.HIV_TEST_NOT_INDICATED) || status.equalsIgnoreCase(NomisConstant.HIV_TEST_NOT_DONE) || status.equalsIgnoreCase(NomisConstant.HIV_RESULT_NOT_DISCLOSED))
            statusCode=1;
            else if(status.equalsIgnoreCase(NomisConstant.HIV_NEGATIVE))
            statusCode=2;
            else if(status.equalsIgnoreCase(NomisConstant.HIV_POSITIVE))
            statusCode=3;
        }
        return statusCode;
    }
}
