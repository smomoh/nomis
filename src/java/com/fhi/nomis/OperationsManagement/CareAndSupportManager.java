/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.nomis.OperationsManagement;

import com.fhi.kidmap.business.CareAndSupportChecklist;
import com.fhi.kidmap.business.HivStatusUpdate;
import com.fhi.kidmap.dao.CareAndSupportDao;
import com.fhi.kidmap.dao.CareAndSupportDaoImpl;
import com.fhi.kidmap.dao.HivStatusUpdateDao;
import com.fhi.kidmap.dao.HivStatusUpdateDaoImpl;
import com.fhi.nomis.nomisutils.NomisConstant;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author smomoh
 */
public class CareAndSupportManager implements Serializable
{
    CareAndSupportDao cscdao=new CareAndSupportDaoImpl();
    public int updateHivStatusUpdateWithCareAndSupportInformation()
    {
        int count=0;
        try
        {
            //get all care and support records
            List cscList=cscdao.getAllCareAndSupportCheclist();
            if(cscList !=null && !cscList.isEmpty())
            {
                List idList=new ArrayList();
                CareAndSupportChecklist csc=null;
                HivStatusUpdateDao hsudao=new HivStatusUpdateDaoImpl();
                for(Object obj:cscList)
                {
                    csc=(CareAndSupportChecklist)obj;
                    if(!idList.contains(csc.getClientId()))
                    {
                        HivStatusUpdate hsu=hsudao.getActiveHivStatusUpdatesByClientId(csc.getClientId());//.getAllHivStatusUpdatesPerClient(csc.getClientId());
                        updateHivStatusUpdateWithCareAndSupportInformation(hsu);
                        idList.add(csc.getClientId());
                        
                        /*if(hsu.getLastHivTrackingDate()==null || hsu.getLastHivTrackingDate().equalsIgnoreCase("1900-01-01"))
                        hsu.setLastHivTrackingDate(hsu.getDateOfCurrentStatus());
                        hsu.setDateOfCurrentStatus(csc.getDateOfHivTest());
                        hsu.setRecordedBy(NomisConstant.AUTO_ACTION);
                        hsudao.updateHivStatusUpdate(hsu);*/
                    }
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return count;
    }
    public int updateHivStatusUpdateWithCareAndSupportInformation(HivStatusUpdate hsu)
    {
        int count=0;
        try
        {
            if(hsu !=null)
            {
                //get all care and support records
                List cscList=cscdao.getCareAndSupportCheclistByUniqueId(hsu.getClientId());
                if(cscList !=null && !cscList.isEmpty())
                {
                    CareAndSupportChecklist csc=null;
                    HivStatusUpdateDao hsudao=new HivStatusUpdateDaoImpl();
                    csc=(CareAndSupportChecklist)cscList.get(0);
                    if(hsu.getLastHivTrackingDate()==null || hsu.getLastHivTrackingDate().equalsIgnoreCase("1900-01-01"))
                    hsu.setLastHivTrackingDate(hsu.getDateOfCurrentStatus());
                    hsu.setDateOfCurrentStatus(csc.getDateOfHivTest());
                    hsu.setRecordedBy(NomisConstant.AUTO_ACTION);
                    hsudao.updateHivStatusUpdate(hsu); 
                    System.err.println("Csc with Id "+csc.getClientId()+" updated");
                    count++;
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return count;
    }
}
