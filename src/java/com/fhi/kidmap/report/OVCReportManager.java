/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report;

import com.fhi.kidmap.business.ChildStatusIndex;
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.dao.DaoUtil;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author smomoh
 */
public class OVCReportManager 
{
    DaoUtil util=new DaoUtil();
    public Ovc getOvcBaselineData(String ovcId)
   {
       Ovc ovc=null;
       try
       {
            ovc=util.getOvcDaoInstance().getOvc(ovcId);
            if(ovc ==null)
            {
                ovc=new Ovc();
            }
       }
       catch(Exception ex)
       {
           ex.printStackTrace();
       }
       return ovc;
   }
   public ChildStatusIndex getBaselineAssessment(String ovcId)
   {
       ChildStatusIndex csi=null;
       try
       {
            csi=util.getChildStatusIndexDaoInstance().getBaselineChildStatusIndex(ovcId);
            if(csi ==null)
            {
                csi=new ChildStatusIndex();
            }
       }
       catch(Exception ex)
       {
           ex.printStackTrace();
       }
       return csi;
   }
   public List getOvcFollowup(String ovcId)
   {
       List mainList=new ArrayList();
       try
       {
            List list=util.getFollowupDaoInstance().getFollowupRecordsWithCSIByOvcId(ovcId);
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
   public List getServicesProvidedToOvc(String ovcId)
   {
       List mainList=new ArrayList();
       try
       {
            List list=util.getOvcServiceDaoInstance().getOvcServices(ovcId);
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
   public List getAllOvcInHousehold(String hhUniqueId)
   {
       List mainList=new ArrayList();
       try
       {
            List list=util.getOvcDaoInstance().getOvcListPerHousehold(hhUniqueId);
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
   public List getHivRiskAssessmentRecords(String ovcId)
   {
       List mainList=new ArrayList();
       try
       {
            List list=util.getHivRiskAssessmentChecklistDaoInstance().getHivRiskAssessmentChecklistByOvcId(ovcId);
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
